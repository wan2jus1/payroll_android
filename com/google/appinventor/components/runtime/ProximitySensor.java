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
import com.google.appinventor.components.runtime.OnPauseListener;
import com.google.appinventor.components.runtime.OnResumeListener;
import com.google.appinventor.components.runtime.OnStopListener;
import com.google.appinventor.components.runtime.SensorComponent;

@DesignerComponent(
   category = ComponentCategory.SENSORS,
   description = "<p>Non-visible component that can measures the proximity of an object in cm relative to the view screen of a device. This sensor is typically used to determine whether a handset is being held up to a persons ear; i.e. lets you determine how far away an object is from a device. Many devices return the absolute distance, in cm, but some return only near and far values. In this case, the sensor usually reports its maximum range value in the far state and a lesser value in the near state.</p>",
   iconName = "images/proximitysensor.png",
   nonVisible = true,
   version = 1
)
@SimpleObject
public class ProximitySensor extends AndroidNonvisibleComponent implements OnStopListener, OnResumeListener, SensorComponent, OnPauseListener, SensorEventListener, Deleteable {

   private float distance = 0.0F;
   private boolean enabled;
   private boolean keepRunningWhenOnPause;
   private Sensor proximitySensor;
   private final SensorManager sensorManager;


   public ProximitySensor(ComponentContainer var1) {
      super(var1.$form());
      this.form.registerForOnResume(this);
      this.form.registerForOnStop(this);
      this.form.registerForOnPause(this);
      this.enabled = true;
      this.sensorManager = (SensorManager)var1.$context().getSystemService("sensor");
      this.proximitySensor = this.sensorManager.getDefaultSensor(8);
      this.startListening();
   }

   private void startListening() {
      this.sensorManager.registerListener(this, this.proximitySensor, 3);
   }

   private void stopListening() {
      this.sensorManager.unregisterListener(this);
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "Reports whether or not the device has a proximity sensor"
   )
   public boolean Available() {
      return this.sensorManager.getSensorList(8).size() > 0;
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "Returns the distance from the object to the device"
   )
   public float Distance() {
      return this.distance;
   }

   @DesignerProperty(
      defaultValue = "True",
      editorType = "boolean"
   )
   @SimpleProperty(
      description = "If enabled, then device will listen for changes in proximity"
   )
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

   @DesignerProperty(
      defaultValue = "False",
      editorType = "boolean"
   )
   @SimpleProperty(
      description = "If set to true, it will keep sensing for proximity changes even when the app is not visible"
   )
   public void KeepRunningWhenOnPause(boolean var1) {
      this.keepRunningWhenOnPause = var1;
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public boolean KeepRunningWhenOnPause() {
      return this.keepRunningWhenOnPause;
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "Reports the Maximum Range of the device\'s ProximitySensor"
   )
   public float MaximumRange() {
      return this.proximitySensor.getMaximumRange();
   }

   @SimpleEvent(
      description = "Triggered when distance (in cm) of the object to the device changes. "
   )
   public void ProximityChanged(float var1) {
      this.distance = var1;
      EventDispatcher.dispatchEvent(this, "ProximityChanged", new Object[]{Float.valueOf(this.distance)});
   }

   public void onAccuracyChanged(Sensor var1, int var2) {
   }

   public void onDelete() {
      if(this.enabled) {
         this.stopListening();
      }

   }

   public void onPause() {
      if(this.enabled && !this.keepRunningWhenOnPause) {
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
         this.distance = ((float[])var1.values.clone())[0];
         this.ProximityChanged(this.distance);
      }

   }

   public void onStop() {
      if(this.enabled) {
         this.stopListening();
      }

   }
}
