package com.google.appinventor.components.runtime;

import android.os.Handler;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.Deleteable;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.LegoMindstormsNxtSensor;

@DesignerComponent(
   category = ComponentCategory.LEGOMINDSTORMS,
   description = "A component that provides a high-level interface to a light sensor on a LEGO MINDSTORMS NXT robot.",
   iconName = "images/legoMindstormsNxt.png",
   nonVisible = true,
   version = 1
)
@SimpleObject
public class NxtLightSensor extends LegoMindstormsNxtSensor implements Deleteable {

   private static final int DEFAULT_BOTTOM_OF_RANGE = 256;
   private static final String DEFAULT_SENSOR_PORT = "3";
   private static final int DEFAULT_TOP_OF_RANGE = 767;
   private boolean aboveRangeEventEnabled;
   private boolean belowRangeEventEnabled;
   private int bottomOfRange;
   private boolean generateLight;
   private Handler handler = new Handler();
   private NxtLightSensor.State previousState;
   private final Runnable sensorReader;
   private int topOfRange;
   private boolean withinRangeEventEnabled;


   public NxtLightSensor(ComponentContainer var1) {
      super(var1, "NxtLightSensor");
      this.previousState = NxtLightSensor.State.UNKNOWN;
      this.sensorReader = new Runnable() {
         public void run() {
            if(NxtLightSensor.this.bluetooth != null && NxtLightSensor.this.bluetooth.IsConnected()) {
               LegoMindstormsNxtSensor.SensorValue var1 = NxtLightSensor.this.getLightValue("");
               if(var1.valid) {
                  NxtLightSensor.State var2;
                  if(((Integer)var1.value).intValue() < NxtLightSensor.this.bottomOfRange) {
                     var2 = NxtLightSensor.State.BELOW_RANGE;
                  } else if(((Integer)var1.value).intValue() > NxtLightSensor.this.topOfRange) {
                     var2 = NxtLightSensor.State.ABOVE_RANGE;
                  } else {
                     var2 = NxtLightSensor.State.WITHIN_RANGE;
                  }

                  if(var2 != NxtLightSensor.this.previousState) {
                     if(var2 == NxtLightSensor.State.BELOW_RANGE && NxtLightSensor.this.belowRangeEventEnabled) {
                        NxtLightSensor.this.BelowRange();
                     }

                     if(var2 == NxtLightSensor.State.WITHIN_RANGE && NxtLightSensor.this.withinRangeEventEnabled) {
                        NxtLightSensor.this.WithinRange();
                     }

                     if(var2 == NxtLightSensor.State.ABOVE_RANGE && NxtLightSensor.this.aboveRangeEventEnabled) {
                        NxtLightSensor.this.AboveRange();
                     }
                  }

                  NxtLightSensor.this.previousState = var2;
               }
            }

            if(NxtLightSensor.this.isHandlerNeeded()) {
               NxtLightSensor.this.handler.post(NxtLightSensor.this.sensorReader);
            }

         }
      };
      this.SensorPort("3");
      this.BottomOfRange(256);
      this.TopOfRange(767);
      this.BelowRangeEventEnabled(false);
      this.WithinRangeEventEnabled(false);
      this.AboveRangeEventEnabled(false);
      this.GenerateLight(false);
   }

   private LegoMindstormsNxtSensor.SensorValue getLightValue(String var1) {
      byte[] var2 = this.getInputValues(var1, this.port);
      return var2 != null && this.getBooleanValueFromBytes(var2, 4)?new LegoMindstormsNxtSensor.SensorValue(true, Integer.valueOf(this.getUWORDValueFromBytes(var2, 10))):new LegoMindstormsNxtSensor.SensorValue(false, (Object)null);
   }

   private boolean isHandlerNeeded() {
      return this.belowRangeEventEnabled || this.withinRangeEventEnabled || this.aboveRangeEventEnabled;
   }

   @SimpleEvent(
      description = "Light level has gone above the range."
   )
   public void AboveRange() {
      EventDispatcher.dispatchEvent(this, "AboveRange", new Object[0]);
   }

   @DesignerProperty(
      defaultValue = "False",
      editorType = "boolean"
   )
   @SimpleProperty
   public void AboveRangeEventEnabled(boolean var1) {
      boolean var2 = this.isHandlerNeeded();
      this.aboveRangeEventEnabled = var1;
      var1 = this.isHandlerNeeded();
      if(var2 && !var1) {
         this.handler.removeCallbacks(this.sensorReader);
      }

      if(!var2 && var1) {
         this.previousState = NxtLightSensor.State.UNKNOWN;
         this.handler.post(this.sensorReader);
      }

   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "Whether the AboveRange event should fire when the light level goes above the TopOfRange."
   )
   public boolean AboveRangeEventEnabled() {
      return this.aboveRangeEventEnabled;
   }

   @SimpleEvent(
      description = "Light level has gone below the range."
   )
   public void BelowRange() {
      EventDispatcher.dispatchEvent(this, "BelowRange", new Object[0]);
   }

   @DesignerProperty(
      defaultValue = "False",
      editorType = "boolean"
   )
   @SimpleProperty
   public void BelowRangeEventEnabled(boolean var1) {
      boolean var2 = this.isHandlerNeeded();
      this.belowRangeEventEnabled = var1;
      var1 = this.isHandlerNeeded();
      if(var2 && !var1) {
         this.handler.removeCallbacks(this.sensorReader);
      }

      if(!var2 && var1) {
         this.previousState = NxtLightSensor.State.UNKNOWN;
         this.handler.post(this.sensorReader);
      }

   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "Whether the BelowRange event should fire when the light level goes below the BottomOfRange."
   )
   public boolean BelowRangeEventEnabled() {
      return this.belowRangeEventEnabled;
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "The bottom of the range used for the BelowRange, WithinRange, and AboveRange events."
   )
   public int BottomOfRange() {
      return this.bottomOfRange;
   }

   @DesignerProperty(
      defaultValue = "256",
      editorType = "non_negative_integer"
   )
   @SimpleProperty
   public void BottomOfRange(int var1) {
      this.bottomOfRange = var1;
      this.previousState = NxtLightSensor.State.UNKNOWN;
   }

   @DesignerProperty(
      defaultValue = "False",
      editorType = "boolean"
   )
   @SimpleProperty
   public void GenerateLight(boolean var1) {
      this.generateLight = var1;
      if(this.bluetooth != null && this.bluetooth.IsConnected()) {
         this.initializeSensor("GenerateLight");
      }

   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "Whether the light sensor should generate light."
   )
   public boolean GenerateLight() {
      return this.generateLight;
   }

   @SimpleFunction(
      description = "Returns the current light level as a value between 0 and 1023, or -1 if the light level can not be read."
   )
   public int GetLightLevel() {
      if(this.checkBluetooth("GetLightLevel")) {
         LegoMindstormsNxtSensor.SensorValue var1 = this.getLightValue("GetLightLevel");
         if(var1.valid) {
            return ((Integer)var1.value).intValue();
         }
      }

      return -1;
   }

   @DesignerProperty(
      defaultValue = "3",
      editorType = "lego_nxt_sensor_port"
   )
   @SimpleProperty(
      userVisible = false
   )
   public void SensorPort(String var1) {
      this.setSensorPort(var1);
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "The top of the range used for the BelowRange, WithinRange, and AboveRange events."
   )
   public int TopOfRange() {
      return this.topOfRange;
   }

   @DesignerProperty(
      defaultValue = "767",
      editorType = "non_negative_integer"
   )
   @SimpleProperty
   public void TopOfRange(int var1) {
      this.topOfRange = var1;
      this.previousState = NxtLightSensor.State.UNKNOWN;
   }

   @SimpleEvent(
      description = "Light level has gone within the range."
   )
   public void WithinRange() {
      EventDispatcher.dispatchEvent(this, "WithinRange", new Object[0]);
   }

   @DesignerProperty(
      defaultValue = "False",
      editorType = "boolean"
   )
   @SimpleProperty
   public void WithinRangeEventEnabled(boolean var1) {
      boolean var2 = this.isHandlerNeeded();
      this.withinRangeEventEnabled = var1;
      var1 = this.isHandlerNeeded();
      if(var2 && !var1) {
         this.handler.removeCallbacks(this.sensorReader);
      }

      if(!var2 && var1) {
         this.previousState = NxtLightSensor.State.UNKNOWN;
         this.handler.post(this.sensorReader);
      }

   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "Whether the WithinRange event should fire when the light level goes between the BottomOfRange and the TopOfRange."
   )
   public boolean WithinRangeEventEnabled() {
      return this.withinRangeEventEnabled;
   }

   protected void initializeSensor(String var1) {
      int var3 = this.port;
      byte var2;
      if(this.generateLight) {
         var2 = 5;
      } else {
         var2 = 6;
      }

      this.setInputMode(var1, var3, var2, 128);
   }

   public void onDelete() {
      this.handler.removeCallbacks(this.sensorReader);
      super.onDelete();
   }

   private static enum State {

      // $FF: synthetic field
      private static final NxtLightSensor.State[] $VALUES = new NxtLightSensor.State[]{UNKNOWN, BELOW_RANGE, WITHIN_RANGE, ABOVE_RANGE};
      ABOVE_RANGE,
      BELOW_RANGE,
      UNKNOWN,
      WITHIN_RANGE;


   }
}
