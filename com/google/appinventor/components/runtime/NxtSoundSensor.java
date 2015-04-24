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
   description = "A component that provides a high-level interface to a sound sensor on a LEGO MINDSTORMS NXT robot.",
   iconName = "images/legoMindstormsNxt.png",
   nonVisible = true,
   version = 1
)
@SimpleObject
public class NxtSoundSensor extends LegoMindstormsNxtSensor implements Deleteable {

   private static final int DEFAULT_BOTTOM_OF_RANGE = 256;
   private static final String DEFAULT_SENSOR_PORT = "2";
   private static final int DEFAULT_TOP_OF_RANGE = 767;
   private boolean aboveRangeEventEnabled;
   private boolean belowRangeEventEnabled;
   private int bottomOfRange;
   private Handler handler = new Handler();
   private NxtSoundSensor.State previousState;
   private final Runnable sensorReader;
   private int topOfRange;
   private boolean withinRangeEventEnabled;


   public NxtSoundSensor(ComponentContainer var1) {
      super(var1, "NxtSoundSensor");
      this.previousState = NxtSoundSensor.State.UNKNOWN;
      this.sensorReader = new Runnable() {
         public void run() {
            if(NxtSoundSensor.this.bluetooth != null && NxtSoundSensor.this.bluetooth.IsConnected()) {
               LegoMindstormsNxtSensor.SensorValue var1 = NxtSoundSensor.this.getSoundValue("");
               if(var1.valid) {
                  NxtSoundSensor.State var2;
                  if(((Integer)var1.value).intValue() < NxtSoundSensor.this.bottomOfRange) {
                     var2 = NxtSoundSensor.State.BELOW_RANGE;
                  } else if(((Integer)var1.value).intValue() > NxtSoundSensor.this.topOfRange) {
                     var2 = NxtSoundSensor.State.ABOVE_RANGE;
                  } else {
                     var2 = NxtSoundSensor.State.WITHIN_RANGE;
                  }

                  if(var2 != NxtSoundSensor.this.previousState) {
                     if(var2 == NxtSoundSensor.State.BELOW_RANGE && NxtSoundSensor.this.belowRangeEventEnabled) {
                        NxtSoundSensor.this.BelowRange();
                     }

                     if(var2 == NxtSoundSensor.State.WITHIN_RANGE && NxtSoundSensor.this.withinRangeEventEnabled) {
                        NxtSoundSensor.this.WithinRange();
                     }

                     if(var2 == NxtSoundSensor.State.ABOVE_RANGE && NxtSoundSensor.this.aboveRangeEventEnabled) {
                        NxtSoundSensor.this.AboveRange();
                     }
                  }

                  NxtSoundSensor.this.previousState = var2;
               }
            }

            if(NxtSoundSensor.this.isHandlerNeeded()) {
               NxtSoundSensor.this.handler.post(NxtSoundSensor.this.sensorReader);
            }

         }
      };
      this.SensorPort("2");
      this.BottomOfRange(256);
      this.TopOfRange(767);
      this.BelowRangeEventEnabled(false);
      this.WithinRangeEventEnabled(false);
      this.AboveRangeEventEnabled(false);
   }

   private LegoMindstormsNxtSensor.SensorValue getSoundValue(String var1) {
      byte[] var2 = this.getInputValues(var1, this.port);
      return var2 != null && this.getBooleanValueFromBytes(var2, 4)?new LegoMindstormsNxtSensor.SensorValue(true, Integer.valueOf(this.getUWORDValueFromBytes(var2, 10))):new LegoMindstormsNxtSensor.SensorValue(false, (Object)null);
   }

   private boolean isHandlerNeeded() {
      return this.belowRangeEventEnabled || this.withinRangeEventEnabled || this.aboveRangeEventEnabled;
   }

   @SimpleEvent(
      description = "Sound level has gone above the range."
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
         this.previousState = NxtSoundSensor.State.UNKNOWN;
         this.handler.post(this.sensorReader);
      }

   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "Whether the AboveRange event should fire when the sound level goes above the TopOfRange."
   )
   public boolean AboveRangeEventEnabled() {
      return this.aboveRangeEventEnabled;
   }

   @SimpleEvent(
      description = "Sound level has gone below the range."
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
         this.previousState = NxtSoundSensor.State.UNKNOWN;
         this.handler.post(this.sensorReader);
      }

   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "Whether the BelowRange event should fire when the sound level goes below the BottomOfRange."
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
      this.previousState = NxtSoundSensor.State.UNKNOWN;
   }

   @SimpleFunction(
      description = "Returns the current sound level as a value between 0 and 1023, or -1 if the sound level can not be read."
   )
   public int GetSoundLevel() {
      if(this.checkBluetooth("GetSoundLevel")) {
         LegoMindstormsNxtSensor.SensorValue var1 = this.getSoundValue("GetSoundLevel");
         if(var1.valid) {
            return ((Integer)var1.value).intValue();
         }
      }

      return -1;
   }

   @DesignerProperty(
      defaultValue = "2",
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
      this.previousState = NxtSoundSensor.State.UNKNOWN;
   }

   @SimpleEvent(
      description = "Sound level has gone within the range."
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
         this.previousState = NxtSoundSensor.State.UNKNOWN;
         this.handler.post(this.sensorReader);
      }

   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "Whether the WithinRange event should fire when the sound level goes between the BottomOfRange and the TopOfRange."
   )
   public boolean WithinRangeEventEnabled() {
      return this.withinRangeEventEnabled;
   }

   protected void initializeSensor(String var1) {
      this.setInputMode(var1, this.port, 7, 0);
   }

   public void onDelete() {
      this.handler.removeCallbacks(this.sensorReader);
      super.onDelete();
   }

   private static enum State {

      // $FF: synthetic field
      private static final NxtSoundSensor.State[] $VALUES = new NxtSoundSensor.State[]{UNKNOWN, BELOW_RANGE, WITHIN_RANGE, ABOVE_RANGE};
      ABOVE_RANGE,
      BELOW_RANGE,
      UNKNOWN,
      WITHIN_RANGE;


   }
}
