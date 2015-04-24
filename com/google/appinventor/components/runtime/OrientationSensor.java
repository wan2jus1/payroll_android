package com.google.appinventor.components.runtime;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
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
import com.google.appinventor.components.runtime.OnPauseListener;
import com.google.appinventor.components.runtime.OnResumeListener;
import com.google.appinventor.components.runtime.util.FroyoUtil;
import com.google.appinventor.components.runtime.util.OrientationSensorUtil;
import com.google.appinventor.components.runtime.util.SdkLevel;

@DesignerComponent(
   category = ComponentCategory.SENSORS,
   description = "<p>Non-visible component providing information about the device\'s physical orientation in three dimensions: <ul> <li> <strong>Roll</strong>: 0 degrees when the device is level, increases to      90 degrees as the device is tilted up on its left side, and      decreases to -90 degrees when the device is tilted up on its right side.      </li> <li> <strong>Pitch</strong>: 0 degrees when the device is level, up to      90 degrees as the device is tilted so its top is pointing down,      up to 180 degrees as it gets turned over.  Similarly, as the device      is tilted so its bottom points down, pitch decreases to -90      degrees, then further decreases to -180 degrees as it gets turned all the way      over.</li> <li> <strong>Azimuth</strong>: 0 degrees when the top of the device is      pointing north, 90 degrees when it is pointing east, 180 degrees      when it is pointing south, 270 degrees when it is pointing west,      etc.</li></ul>     These measurements assume that the device itself is not moving.</p>",
   iconName = "images/orientationsensor.png",
   nonVisible = true,
   version = 2
)
@SimpleObject
public class OrientationSensor extends AndroidNonvisibleComponent implements SensorEventListener, Deleteable, OnPauseListener, OnResumeListener {

   private static final int AZIMUTH = 0;
   private static final int DIMENSIONS = 3;
   private static final String LOG_TAG = "OrientationSensor";
   private static final int PITCH = 1;
   private static final int ROLL = 2;
   private final Sensor accelerometerSensor;
   private final float[] accels = new float[3];
   private boolean accelsFilled;
   private int accuracy;
   private float azimuth;
   private boolean enabled;
   private final float[] inclinationMatrix = new float[9];
   private boolean listening;
   private final Sensor magneticFieldSensor;
   private final float[] mags = new float[3];
   private boolean magsFilled;
   private float pitch;
   private float roll;
   private final float[] rotationMatrix = new float[9];
   private final SensorManager sensorManager;
   private final float[] values = new float[3];


   public OrientationSensor(ComponentContainer var1) {
      super(var1.$form());
      this.sensorManager = (SensorManager)var1.$context().getSystemService("sensor");
      this.accelerometerSensor = this.sensorManager.getDefaultSensor(1);
      this.magneticFieldSensor = this.sensorManager.getDefaultSensor(2);
      this.form.registerForOnResume(this);
      this.form.registerForOnPause(this);
      this.Enabled(true);
   }

   static float computeAngle(float var0, float var1) {
      return (float)Math.toDegrees(Math.atan2(Math.toRadians((double)var0), -Math.toRadians((double)var1)));
   }

   private int getScreenRotation() {
      Display var1 = ((WindowManager)this.form.getSystemService("window")).getDefaultDisplay();
      return SdkLevel.getLevel() >= 8?FroyoUtil.getRotation(var1):var1.getOrientation();
   }

   private void startListening() {
      if(!this.listening) {
         this.sensorManager.registerListener(this, this.accelerometerSensor, 3);
         this.sensorManager.registerListener(this, this.magneticFieldSensor, 3);
         this.listening = true;
      }

   }

   private void stopListening() {
      if(this.listening) {
         this.sensorManager.unregisterListener(this);
         this.listening = false;
         this.accelsFilled = false;
         this.magsFilled = false;
      }

   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public float Angle() {
      return computeAngle(this.pitch, this.roll);
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public boolean Available() {
      return this.sensorManager.getSensorList(1).size() > 0 && this.sensorManager.getSensorList(2).size() > 0;
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public float Azimuth() {
      return this.azimuth;
   }

   @DesignerProperty(
      defaultValue = "True",
      editorType = "boolean"
   )
   @SimpleProperty
   public void Enabled(boolean var1) {
      if(this.enabled != var1) {
         this.enabled = var1;
         if(!var1) {
            this.stopListening();
            return;
         }

         this.startListening();
      }

   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public boolean Enabled() {
      return this.enabled;
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public float Magnitude() {
      double var1 = Math.toRadians((double)Math.min(90.0F, Math.abs(this.pitch)));
      double var3 = Math.toRadians((double)Math.min(90.0F, Math.abs(this.roll)));
      return (float)(1.0D - Math.cos(var1) * Math.cos(var3));
   }

   @SimpleEvent
   public void OrientationChanged(float var1, float var2, float var3) {
      EventDispatcher.dispatchEvent(this, "OrientationChanged", new Object[]{Float.valueOf(var1), Float.valueOf(var2), Float.valueOf(var3)});
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public float Pitch() {
      return this.pitch;
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public float Roll() {
      return this.roll;
   }

   public void onAccuracyChanged(Sensor var1, int var2) {
   }

   public void onDelete() {
      this.stopListening();
   }

   public void onPause() {
      this.stopListening();
   }

   public void onResume() {
      if(this.enabled) {
         this.startListening();
      }

   }

   public void onSensorChanged(SensorEvent var1) {
      if(this.enabled) {
         int var3 = var1.sensor.getType();
         switch(var3) {
         case 1:
            System.arraycopy(var1.values, 0, this.accels, 0, 3);
            this.accelsFilled = true;
            this.accuracy = var1.accuracy;
            break;
         case 2:
            System.arraycopy(var1.values, 0, this.mags, 0, 3);
            this.magsFilled = true;
            break;
         default:
            Log.e("OrientationSensor", "Unexpected sensor type: " + var3);
            return;
         }

         if(this.accelsFilled && this.magsFilled) {
            SensorManager.getRotationMatrix(this.rotationMatrix, this.inclinationMatrix, this.accels, this.mags);
            SensorManager.getOrientation(this.rotationMatrix, this.values);
            this.azimuth = OrientationSensorUtil.normalizeAzimuth((float)Math.toDegrees((double)this.values[0]));
            this.pitch = OrientationSensorUtil.normalizePitch((float)Math.toDegrees((double)this.values[1]));
            this.roll = OrientationSensorUtil.normalizeRoll((float)(-Math.toDegrees((double)this.values[2])));
            var3 = this.getScreenRotation();
            float var2;
            switch(var3) {
            case 0:
               break;
            case 1:
               var2 = -this.pitch;
               this.pitch = -this.roll;
               this.roll = var2;
               break;
            case 2:
               this.roll = -this.roll;
               break;
            case 3:
               var2 = this.pitch;
               this.pitch = this.roll;
               this.roll = var2;
               break;
            default:
               Log.e("OrientationSensor", "Illegal value for getScreenRotation(): " + var3);
            }

            this.OrientationChanged(this.azimuth, this.pitch, this.roll);
            return;
         }
      }

   }
}
