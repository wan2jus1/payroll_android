package com.google.appinventor.components.runtime;

import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.runtime.BluetoothConnectionBase;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.LegoMindstormsNxtBase;

@SimpleObject
public abstract class LegoMindstormsNxtSensor extends LegoMindstormsNxtBase {

   static final int SENSOR_MODE_ANGLESTEPMODE = 224;
   static final int SENSOR_MODE_BOOLEANMODE = 32;
   static final int SENSOR_MODE_CELSIUSMODE = 160;
   static final int SENSOR_MODE_FAHRENHEITMODE = 192;
   static final int SENSOR_MODE_MASK_MODE = 224;
   static final int SENSOR_MODE_MASK_SLOPE = 31;
   static final int SENSOR_MODE_PCTFULLSCALEMODE = 128;
   static final int SENSOR_MODE_PERIODCOUNTERMODE = 96;
   static final int SENSOR_MODE_RAWMODE = 0;
   static final int SENSOR_MODE_TRANSITIONCNTMODE = 64;
   static final int SENSOR_TYPE_ANGLE = 4;
   static final int SENSOR_TYPE_CUSTOM = 9;
   static final int SENSOR_TYPE_LIGHT_ACTIVE = 5;
   static final int SENSOR_TYPE_LIGHT_INACTIVE = 6;
   static final int SENSOR_TYPE_LOWSPEED = 10;
   static final int SENSOR_TYPE_LOWSPEED_9V = 11;
   static final int SENSOR_TYPE_NO_SENSOR = 0;
   static final int SENSOR_TYPE_REFLECTION = 3;
   static final int SENSOR_TYPE_SOUND_DB = 7;
   static final int SENSOR_TYPE_SOUND_DBA = 8;
   static final int SENSOR_TYPE_SWITCH = 1;
   static final int SENSOR_TYPE_TEMPERATURE = 2;
   protected int port;
   private String sensorPortLetter;


   protected LegoMindstormsNxtSensor(ComponentContainer var1, String var2) {
      super(var1, var2);
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "The sensor port that the sensor is connected to.",
      userVisible = false
   )
   public String SensorPort() {
      return this.sensorPortLetter;
   }

   public abstract void SensorPort(String var1);

   public void afterConnect(BluetoothConnectionBase var1) {
      this.initializeSensor("Connect");
   }

   protected abstract void initializeSensor(String var1);

   protected final void setSensorPort(String var1) {
      int var3;
      try {
         var3 = this.convertSensorPortLetterToNumber(var1);
      } catch (IllegalArgumentException var4) {
         this.form.dispatchErrorOccurredEvent(this, "SensorPort", 408, new Object[]{var1});
         return;
      }

      this.sensorPortLetter = var1;
      this.port = var3;
      if(this.bluetooth != null && this.bluetooth.IsConnected()) {
         this.initializeSensor("SensorPort");
      }

   }

   static class SensorValue {

      final boolean valid;
      final Object value;


      SensorValue(boolean var1, Object var2) {
         this.valid = var1;
         this.value = var2;
      }
   }
}
