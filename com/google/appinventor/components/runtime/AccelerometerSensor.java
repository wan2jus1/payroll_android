package com.google.appinventor.components.runtime;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.Deleteable;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.OnResumeListener;
import com.google.appinventor.components.runtime.OnStopListener;
import com.google.appinventor.components.runtime.SensorComponent;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

@DesignerComponent(
   category = ComponentCategory.SENSORS,
   description = "Non-visible component that can detect shaking and measure acceleration approximately in three dimensions using SI units (m/s<sup>2</sup>).  The components are: <ul>\n<li> <strong>xAccel</strong>: 0 when the phone is at rest on a flat      surface, positive when the phone is tilted to the right (i.e.,      its left side is raised), and negative when the phone is tilted      to the left (i.e., its right size is raised).</li>\n <li> <strong>yAccel</strong>: 0 when the phone is at rest on a flat      surface, positive when its bottom is raised, and negative when      its top is raised. </li>\n <li> <strong>zAccel</strong>: Equal to -9.8 (earth\'s gravity in meters per      second per second when the device is at rest parallel to the ground      with the display facing up,      0 when perpindicular to the ground, and +9.8 when facing down.       The value can also be affected by accelerating it with or against      gravity. </li></ul>",
   iconName = "images/accelerometersensor.png",
   nonVisible = true,
   version = 3
)
@SimpleObject
public class AccelerometerSensor extends AndroidNonvisibleComponent implements OnStopListener, OnResumeListener, SensorComponent, SensorEventListener, Deleteable {

   private static final int SENSOR_CACHE_SIZE = 10;
   private static final double moderateShakeThreshold = 13.0D;
   private static final double strongShakeThreshold = 20.0D;
   private static final double weakShakeThreshold = 5.0D;
   private final Queue X_CACHE = new LinkedList();
   private final Queue Y_CACHE = new LinkedList();
   private final Queue Z_CACHE = new LinkedList();
   private Sensor accelerometerSensor;
   private int accuracy;
   private boolean enabled;
   private int minimumInterval;
   private int sensitivity;
   private final SensorManager sensorManager;
   private long timeLastShook;
   private float xAccel;
   private float yAccel;
   private float zAccel;


   public AccelerometerSensor(ComponentContainer var1) {
      super(var1.$form());
      this.form.registerForOnResume(this);
      this.form.registerForOnStop(this);
      this.enabled = true;
      this.sensorManager = (SensorManager)var1.$context().getSystemService("sensor");
      this.accelerometerSensor = this.sensorManager.getDefaultSensor(1);
      this.startListening();
      this.MinimumInterval(400);
      this.Sensitivity(2);
   }

   private void addToSensorCache(Queue var1, float var2) {
      if(var1.size() >= 10) {
         var1.remove();
      }

      var1.add(Float.valueOf(var2));
   }

   private boolean isShaking(Queue var1, float var2) {
      boolean var5 = true;
      float var3 = 0.0F;

      for(Iterator var4 = var1.iterator(); var4.hasNext(); var3 += ((Float)var4.next()).floatValue()) {
         ;
      }

      var3 /= (float)var1.size();
      if(this.Sensitivity() == 1) {
         if((double)Math.abs(var3 - var2) > 20.0D) {
            var5 = true;
         } else {
            var5 = false;
         }
      } else if(this.Sensitivity() == 2) {
         if((double)Math.abs(var3 - var2) <= 13.0D || (double)Math.abs(var3 - var2) >= 20.0D) {
            return false;
         }
      } else if((double)Math.abs(var3 - var2) <= 5.0D || (double)Math.abs(var3 - var2) >= 13.0D) {
         return false;
      }

      return var5;
   }

   private void startListening() {
      this.sensorManager.registerListener(this, this.accelerometerSensor, 1);
   }

   private void stopListening() {
      this.sensorManager.unregisterListener(this);
   }

   @SimpleEvent
   public void AccelerationChanged(float var1, float var2, float var3) {
      this.xAccel = var1;
      this.yAccel = var2;
      this.zAccel = var3;
      this.addToSensorCache(this.X_CACHE, var1);
      this.addToSensorCache(this.Y_CACHE, var2);
      this.addToSensorCache(this.Z_CACHE, var3);
      long var4 = System.currentTimeMillis();
      if((this.isShaking(this.X_CACHE, var1) || this.isShaking(this.Y_CACHE, var2) || this.isShaking(this.Z_CACHE, var3)) && (this.timeLastShook == 0L || var4 >= this.timeLastShook + (long)this.minimumInterval)) {
         this.timeLastShook = var4;
         this.Shaking();
      }

      EventDispatcher.dispatchEvent(this, "AccelerationChanged", new Object[]{Float.valueOf(var1), Float.valueOf(var2), Float.valueOf(var3)});
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public boolean Available() {
      return this.sensorManager.getSensorList(1).size() > 0;
   }

   @DesignerProperty(
      defaultValue = "True",
      editorType = "boolean"
   )
   @SimpleProperty
   public void Enabled(boolean var1) {
      if(this.enabled != var1) {
         this.enabled = var1;
         if(var1) {
            this.startListening();
         } else {
            this.stopListening();
         }
      }
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public boolean Enabled() {
      return this.enabled;
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "The minimum interval between phone shakes"
   )
   public int MinimumInterval() {
      return this.minimumInterval;
   }

   @DesignerProperty(
      defaultValue = "400",
      editorType = "non_negative_integer"
   )
   @SimpleProperty
   public void MinimumInterval(int var1) {
      this.minimumInterval = var1;
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "A number that encodes how sensitive the accelerometer is. The choices are: 1 = weak, 2 = moderate,  3 = strong."
   )
   public int Sensitivity() {
      return this.sensitivity;
   }

   @DesignerProperty(
      defaultValue = "2",
      editorType = "accelerometer_sensitivity"
   )
   @SimpleProperty
   public void Sensitivity(int var1) {
      if(var1 != 1 && var1 != 2 && var1 != 3) {
         this.form.dispatchErrorOccurredEvent(this, "Sensitivity", 1901, new Object[]{Integer.valueOf(var1)});
      } else {
         this.sensitivity = var1;
      }
   }

   @SimpleEvent
   public void Shaking() {
      EventDispatcher.dispatchEvent(this, "Shaking", new Object[0]);
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public float XAccel() {
      return this.xAccel;
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public float YAccel() {
      return this.yAccel;
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public float ZAccel() {
      return this.zAccel;
   }

   public void onAccuracyChanged(Sensor var1, int var2) {
   }

   public void onDelete() {
      if(this.enabled) {
         this.stopListening();
      }

   }

   public void onResume() {
      if(this.enabled) {
         this.startListening();
      }

   }

   public void onSensorChanged(SensorEvent var1) {
      if(this.enabled) {
         float[] var2 = var1.values;
         this.xAccel = var2[0];
         this.yAccel = var2[1];
         this.zAccel = var2[2];
         this.accuracy = var1.accuracy;
         this.AccelerationChanged(this.xAccel, this.yAccel, this.zAccel);
      }

   }

   public void onStop() {
      if(this.enabled) {
         this.stopListening();
      }

   }
}
