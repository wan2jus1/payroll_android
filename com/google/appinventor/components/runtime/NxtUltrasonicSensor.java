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
   description = "A component that provides a high-level interface to an ultrasonic sensor on a LEGO MINDSTORMS NXT robot.",
   iconName = "images/legoMindstormsNxt.png",
   nonVisible = true,
   version = 1
)
@SimpleObject
public class NxtUltrasonicSensor extends LegoMindstormsNxtSensor implements Deleteable {

   private static final int DEFAULT_BOTTOM_OF_RANGE = 30;
   private static final String DEFAULT_SENSOR_PORT = "4";
   private static final int DEFAULT_TOP_OF_RANGE = 90;
   private boolean aboveRangeEventEnabled;
   private boolean belowRangeEventEnabled;
   private int bottomOfRange;
   private Handler handler = new Handler();
   private NxtUltrasonicSensor.State previousState;
   private final Runnable sensorReader;
   private int topOfRange;
   private boolean withinRangeEventEnabled;


   public NxtUltrasonicSensor(ComponentContainer var1) {
      super(var1, "NxtUltrasonicSensor");
      this.previousState = NxtUltrasonicSensor.State.UNKNOWN;
      this.sensorReader = new Runnable() {
         public void run() {
            if(NxtUltrasonicSensor.this.bluetooth != null && NxtUltrasonicSensor.this.bluetooth.IsConnected()) {
               LegoMindstormsNxtSensor.SensorValue var1 = NxtUltrasonicSensor.this.getDistanceValue("");
               if(var1.valid) {
                  NxtUltrasonicSensor.State var2;
                  if(((Integer)var1.value).intValue() < NxtUltrasonicSensor.this.bottomOfRange) {
                     var2 = NxtUltrasonicSensor.State.BELOW_RANGE;
                  } else if(((Integer)var1.value).intValue() > NxtUltrasonicSensor.this.topOfRange) {
                     var2 = NxtUltrasonicSensor.State.ABOVE_RANGE;
                  } else {
                     var2 = NxtUltrasonicSensor.State.WITHIN_RANGE;
                  }

                  if(var2 != NxtUltrasonicSensor.this.previousState) {
                     if(var2 == NxtUltrasonicSensor.State.BELOW_RANGE && NxtUltrasonicSensor.this.belowRangeEventEnabled) {
                        NxtUltrasonicSensor.this.BelowRange();
                     }

                     if(var2 == NxtUltrasonicSensor.State.WITHIN_RANGE && NxtUltrasonicSensor.this.withinRangeEventEnabled) {
                        NxtUltrasonicSensor.this.WithinRange();
                     }

                     if(var2 == NxtUltrasonicSensor.State.ABOVE_RANGE && NxtUltrasonicSensor.this.aboveRangeEventEnabled) {
                        NxtUltrasonicSensor.this.AboveRange();
                     }
                  }

                  NxtUltrasonicSensor.this.previousState = var2;
               }
            }

            if(NxtUltrasonicSensor.this.isHandlerNeeded()) {
               NxtUltrasonicSensor.this.handler.post(NxtUltrasonicSensor.this.sensorReader);
            }

         }
      };
      this.SensorPort("4");
      this.BottomOfRange(30);
      this.TopOfRange(90);
      this.BelowRangeEventEnabled(false);
      this.WithinRangeEventEnabled(false);
      this.AboveRangeEventEnabled(false);
   }

   private void configureUltrasonicSensor(String var1) {
      this.lsWrite(var1, this.port, new byte[]{(byte)2, (byte)65, (byte)2}, 0);
   }

   private LegoMindstormsNxtSensor.SensorValue getDistanceValue(String var1) {
      this.lsWrite(var1, this.port, new byte[]{(byte)2, (byte)66}, 1);

      for(int var2 = 0; var2 < 3; ++var2) {
         if(this.lsGetStatus(var1, this.port) > 0) {
            byte[] var3 = this.lsRead(var1, this.port);
            if(var3 != null) {
               var2 = this.getUBYTEValueFromBytes(var3, 4);
               if(var2 >= 0 && var2 <= 254) {
                  return new LegoMindstormsNxtSensor.SensorValue(true, Integer.valueOf(var2));
               }
            }
            break;
         }
      }

      return new LegoMindstormsNxtSensor.SensorValue(false, (Object)null);
   }

   private boolean isHandlerNeeded() {
      return this.belowRangeEventEnabled || this.withinRangeEventEnabled || this.aboveRangeEventEnabled;
   }

   @SimpleEvent(
      description = "Distance has gone above the range."
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
         this.previousState = NxtUltrasonicSensor.State.UNKNOWN;
         this.handler.post(this.sensorReader);
      }

   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "Whether the AboveRange event should fire when the distance goes above the TopOfRange."
   )
   public boolean AboveRangeEventEnabled() {
      return this.aboveRangeEventEnabled;
   }

   @SimpleEvent(
      description = "Distance has gone below the range."
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
         this.previousState = NxtUltrasonicSensor.State.UNKNOWN;
         this.handler.post(this.sensorReader);
      }

   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "Whether the BelowRange event should fire when the distance goes below the BottomOfRange."
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
      defaultValue = "30",
      editorType = "non_negative_integer"
   )
   @SimpleProperty
   public void BottomOfRange(int var1) {
      this.bottomOfRange = var1;
      this.previousState = NxtUltrasonicSensor.State.UNKNOWN;
   }

   @SimpleFunction(
      description = "Returns the current distance in centimeters as a value between 0 and 254, or -1 if the distance can not be read."
   )
   public int GetDistance() {
      if(this.checkBluetooth("GetDistance")) {
         LegoMindstormsNxtSensor.SensorValue var1 = this.getDistanceValue("GetDistance");
         if(var1.valid) {
            return ((Integer)var1.value).intValue();
         }
      }

      return -1;
   }

   @DesignerProperty(
      defaultValue = "4",
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
      defaultValue = "90",
      editorType = "non_negative_integer"
   )
   @SimpleProperty
   public void TopOfRange(int var1) {
      this.topOfRange = var1;
      this.previousState = NxtUltrasonicSensor.State.UNKNOWN;
   }

   @SimpleEvent(
      description = "Distance has gone within the range."
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
         this.previousState = NxtUltrasonicSensor.State.UNKNOWN;
         this.handler.post(this.sensorReader);
      }

   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "Whether the WithinRange event should fire when the distance goes between the BottomOfRange and the TopOfRange."
   )
   public boolean WithinRangeEventEnabled() {
      return this.withinRangeEventEnabled;
   }

   protected void initializeSensor(String var1) {
      this.setInputMode(var1, this.port, 11, 0);
      this.configureUltrasonicSensor(var1);
   }

   public void onDelete() {
      this.handler.removeCallbacks(this.sensorReader);
      super.onDelete();
   }

   private static enum State {

      // $FF: synthetic field
      private static final NxtUltrasonicSensor.State[] $VALUES = new NxtUltrasonicSensor.State[]{UNKNOWN, BELOW_RANGE, WITHIN_RANGE, ABOVE_RANGE};
      ABOVE_RANGE,
      BELOW_RANGE,
      UNKNOWN,
      WITHIN_RANGE;


   }
}
