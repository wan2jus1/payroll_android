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
import java.util.HashMap;
import java.util.Map;

@DesignerComponent(
   category = ComponentCategory.LEGOMINDSTORMS,
   description = "A component that provides a high-level interface to a color sensor on a LEGO MINDSTORMS NXT robot.",
   iconName = "images/legoMindstormsNxt.png",
   nonVisible = true,
   version = 1
)
@SimpleObject
public class NxtColorSensor extends LegoMindstormsNxtSensor implements Deleteable {

   private static final int DEFAULT_BOTTOM_OF_RANGE = 256;
   private static final String DEFAULT_SENSOR_PORT = "3";
   private static final int DEFAULT_TOP_OF_RANGE = 767;
   static final int SENSOR_TYPE_COLOR_BLUE = 16;
   static final int SENSOR_TYPE_COLOR_FULL = 13;
   static final int SENSOR_TYPE_COLOR_GREEN = 15;
   static final int SENSOR_TYPE_COLOR_NONE = 17;
   static final int SENSOR_TYPE_COLOR_RED = 14;
   private static final Map mapColorToSensorType = new HashMap();
   private static final Map mapSensorValueToColor;
   private boolean aboveRangeEventEnabled;
   private boolean belowRangeEventEnabled;
   private int bottomOfRange;
   private boolean colorChangedEventEnabled;
   private boolean detectColor;
   private int generateColor;
   private Handler handler = new Handler();
   private int previousColor;
   private NxtColorSensor.State previousState;
   private final Runnable sensorReader;
   private int topOfRange;
   private boolean withinRangeEventEnabled;


   static {
      mapColorToSensorType.put(Integer.valueOf(-65536), Integer.valueOf(14));
      mapColorToSensorType.put(Integer.valueOf(-16711936), Integer.valueOf(15));
      mapColorToSensorType.put(Integer.valueOf(-16776961), Integer.valueOf(16));
      mapColorToSensorType.put(Integer.valueOf(16777215), Integer.valueOf(17));
      mapSensorValueToColor = new HashMap();
      mapSensorValueToColor.put(Integer.valueOf(1), Integer.valueOf(-16777216));
      mapSensorValueToColor.put(Integer.valueOf(2), Integer.valueOf(-16776961));
      mapSensorValueToColor.put(Integer.valueOf(3), Integer.valueOf(-16711936));
      mapSensorValueToColor.put(Integer.valueOf(4), Integer.valueOf(-256));
      mapSensorValueToColor.put(Integer.valueOf(5), Integer.valueOf(-65536));
      mapSensorValueToColor.put(Integer.valueOf(6), Integer.valueOf(-1));
   }

   public NxtColorSensor(ComponentContainer var1) {
      super(var1, "NxtColorSensor");
      this.previousState = NxtColorSensor.State.UNKNOWN;
      this.previousColor = 16777215;
      this.sensorReader = new Runnable() {
         public void run() {
            if(NxtColorSensor.this.bluetooth != null && NxtColorSensor.this.bluetooth.IsConnected()) {
               LegoMindstormsNxtSensor.SensorValue var1;
               if(NxtColorSensor.this.detectColor) {
                  var1 = NxtColorSensor.this.getColorValue("");
                  if(var1.valid) {
                     int var2 = ((Integer)var1.value).intValue();
                     if(var2 != NxtColorSensor.this.previousColor) {
                        NxtColorSensor.this.ColorChanged(var2);
                     }

                     NxtColorSensor.this.previousColor = var2;
                  }
               } else {
                  var1 = NxtColorSensor.this.getLightValue("");
                  if(var1.valid) {
                     NxtColorSensor.State var3;
                     if(((Integer)var1.value).intValue() < NxtColorSensor.this.bottomOfRange) {
                        var3 = NxtColorSensor.State.BELOW_RANGE;
                     } else if(((Integer)var1.value).intValue() > NxtColorSensor.this.topOfRange) {
                        var3 = NxtColorSensor.State.ABOVE_RANGE;
                     } else {
                        var3 = NxtColorSensor.State.WITHIN_RANGE;
                     }

                     if(var3 != NxtColorSensor.this.previousState) {
                        if(var3 == NxtColorSensor.State.BELOW_RANGE && NxtColorSensor.this.belowRangeEventEnabled) {
                           NxtColorSensor.this.BelowRange();
                        }

                        if(var3 == NxtColorSensor.State.WITHIN_RANGE && NxtColorSensor.this.withinRangeEventEnabled) {
                           NxtColorSensor.this.WithinRange();
                        }

                        if(var3 == NxtColorSensor.State.ABOVE_RANGE && NxtColorSensor.this.aboveRangeEventEnabled) {
                           NxtColorSensor.this.AboveRange();
                        }
                     }

                     NxtColorSensor.this.previousState = var3;
                  }
               }
            }

            if(NxtColorSensor.this.isHandlerNeeded()) {
               NxtColorSensor.this.handler.post(NxtColorSensor.this.sensorReader);
            }

         }
      };
      this.SensorPort("3");
      this.DetectColor(true);
      this.ColorChangedEventEnabled(false);
      this.BottomOfRange(256);
      this.TopOfRange(767);
      this.BelowRangeEventEnabled(false);
      this.WithinRangeEventEnabled(false);
      this.AboveRangeEventEnabled(false);
      this.GenerateColor(16777215);
   }

   private LegoMindstormsNxtSensor.SensorValue getColorValue(String var1) {
      byte[] var3 = this.getInputValues(var1, this.port);
      if(var3 != null && this.getBooleanValueFromBytes(var3, 4)) {
         int var2 = this.getSWORDValueFromBytes(var3, 12);
         if(mapSensorValueToColor.containsKey(Integer.valueOf(var2))) {
            return new LegoMindstormsNxtSensor.SensorValue(true, Integer.valueOf(((Integer)mapSensorValueToColor.get(Integer.valueOf(var2))).intValue()));
         }
      }

      return new LegoMindstormsNxtSensor.SensorValue(false, (Object)null);
   }

   private LegoMindstormsNxtSensor.SensorValue getLightValue(String var1) {
      byte[] var2 = this.getInputValues(var1, this.port);
      return var2 != null && this.getBooleanValueFromBytes(var2, 4)?new LegoMindstormsNxtSensor.SensorValue(true, Integer.valueOf(this.getUWORDValueFromBytes(var2, 10))):new LegoMindstormsNxtSensor.SensorValue(false, (Object)null);
   }

   private boolean isHandlerNeeded() {
      return this.detectColor?this.colorChangedEventEnabled:this.belowRangeEventEnabled || this.withinRangeEventEnabled || this.aboveRangeEventEnabled;
   }

   @SimpleEvent(
      description = "Light level has gone above the range. The AboveRange event will not occur if the DetectColor property is set to True or if the AboveRangeEventEnabled property is set to False."
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
         this.previousState = NxtColorSensor.State.UNKNOWN;
         this.handler.post(this.sensorReader);
      }

   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "Whether the AboveRange event should fire when the DetectColor property is set to False and the light level goes above the TopOfRange."
   )
   public boolean AboveRangeEventEnabled() {
      return this.aboveRangeEventEnabled;
   }

   @SimpleEvent(
      description = "Light level has gone below the range. The BelowRange event will not occur if the DetectColor property is set to True or if the BelowRangeEventEnabled property is set to False."
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
         this.previousState = NxtColorSensor.State.UNKNOWN;
         this.handler.post(this.sensorReader);
      }

   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "Whether the BelowRange event should fire when the DetectColor property is set to False and the light level goes below the BottomOfRange."
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
      this.previousState = NxtColorSensor.State.UNKNOWN;
   }

   @SimpleEvent(
      description = "Detected color has changed. The ColorChanged event will not occur if the DetectColor property is set to False or if the ColorChangedEventEnabled property is set to False."
   )
   public void ColorChanged(int var1) {
      EventDispatcher.dispatchEvent(this, "ColorChanged", new Object[]{Integer.valueOf(var1)});
   }

   @DesignerProperty(
      defaultValue = "False",
      editorType = "boolean"
   )
   @SimpleProperty
   public void ColorChangedEventEnabled(boolean var1) {
      boolean var2 = this.isHandlerNeeded();
      this.colorChangedEventEnabled = var1;
      var1 = this.isHandlerNeeded();
      if(var2 && !var1) {
         this.handler.removeCallbacks(this.sensorReader);
      }

      if(!var2 && var1) {
         this.previousColor = 16777215;
         this.handler.post(this.sensorReader);
      }

   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "Whether the ColorChanged event should fire when the DetectColor property is set to True and the detected color changes."
   )
   public boolean ColorChangedEventEnabled() {
      return this.colorChangedEventEnabled;
   }

   @DesignerProperty(
      defaultValue = "True",
      editorType = "boolean"
   )
   @SimpleProperty
   public void DetectColor(boolean var1) {
      boolean var2 = this.isHandlerNeeded();
      this.detectColor = var1;
      if(this.bluetooth != null && this.bluetooth.IsConnected()) {
         this.initializeSensor("DetectColor");
      }

      var1 = this.isHandlerNeeded();
      if(var2 && !var1) {
         this.handler.removeCallbacks(this.sensorReader);
      }

      this.previousColor = 16777215;
      this.previousState = NxtColorSensor.State.UNKNOWN;
      if(!var2 && var1) {
         this.handler.post(this.sensorReader);
      }

   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "Whether the sensor should detect color or light. True indicates that the sensor should detect color; False indicates that the sensor should detect light. If the DetectColor property is set to True, the BelowRange, WithinRange, and AboveRange events will not occur and the sensor will not generate color. If the DetectColor property is set to False, the ColorChanged event will not occur."
   )
   public boolean DetectColor() {
      return this.detectColor;
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "The color that should generated by the sensor. Only None, Red, Green, or Blue are valid values. The sensor will not generate color when the DetectColor property is set to True."
   )
   public int GenerateColor() {
      return this.generateColor;
   }

   @DesignerProperty(
      defaultValue = "&H00FFFFFF",
      editorType = "lego_nxt_generated_color"
   )
   @SimpleProperty
   public void GenerateColor(int var1) {
      if(mapColorToSensorType.containsKey(Integer.valueOf(var1))) {
         this.generateColor = var1;
         if(this.bluetooth != null && this.bluetooth.IsConnected()) {
            this.initializeSensor("GenerateColor");
         }

      } else {
         this.form.dispatchErrorOccurredEvent(this, "GenerateColor", 419, new Object[0]);
      }
   }

   @SimpleFunction(
      description = "Returns the current detected color, or the color None if the color can not be read or if the DetectColor property is set to False."
   )
   public int GetColor() {
      if(this.checkBluetooth("GetColor")) {
         if(!this.detectColor) {
            this.form.dispatchErrorOccurredEvent(this, "GetColor", 417, new Object[0]);
            return 16777215;
         }

         LegoMindstormsNxtSensor.SensorValue var1 = this.getColorValue("GetColor");
         if(var1.valid) {
            return ((Integer)var1.value).intValue();
         }
      }

      return 16777215;
   }

   @SimpleFunction(
      description = "Returns the current light level as a value between 0 and 1023, or -1 if the light level can not be read or if the DetectColor property is set to True."
   )
   public int GetLightLevel() {
      if(this.checkBluetooth("GetLightLevel")) {
         if(this.detectColor) {
            this.form.dispatchErrorOccurredEvent(this, "GetLightLevel", 418, new Object[0]);
            return -1;
         }

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
      this.previousState = NxtColorSensor.State.UNKNOWN;
   }

   @SimpleEvent(
      description = "Light level has gone within the range. The WithinRange event will not occur if the DetectColor property is set to True or if the WithinRangeEventEnabled property is set to False."
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
         this.previousState = NxtColorSensor.State.UNKNOWN;
         this.handler.post(this.sensorReader);
      }

   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "Whether the WithinRange event should fire when the DetectColor property is set to False and the light level goes between the BottomOfRange and the TopOfRange."
   )
   public boolean WithinRangeEventEnabled() {
      return this.withinRangeEventEnabled;
   }

   protected void initializeSensor(String var1) {
      int var2;
      if(this.detectColor) {
         var2 = 13;
      } else {
         var2 = ((Integer)mapColorToSensorType.get(Integer.valueOf(this.generateColor))).intValue();
      }

      this.setInputMode(var1, this.port, var2, 0);
      this.resetInputScaledValue(var1, this.port);
   }

   public void onDelete() {
      this.handler.removeCallbacks(this.sensorReader);
      super.onDelete();
   }

   private static enum State {

      // $FF: synthetic field
      private static final NxtColorSensor.State[] $VALUES = new NxtColorSensor.State[]{UNKNOWN, BELOW_RANGE, WITHIN_RANGE, ABOVE_RANGE};
      ABOVE_RANGE,
      BELOW_RANGE,
      UNKNOWN,
      WITHIN_RANGE;


   }
}
