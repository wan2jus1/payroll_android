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
   description = "A component that provides a high-level interface to a touch sensor on a LEGO MINDSTORMS NXT robot.",
   iconName = "images/legoMindstormsNxt.png",
   nonVisible = true,
   version = 1
)
@SimpleObject
public class NxtTouchSensor extends LegoMindstormsNxtSensor implements Deleteable {

   private static final String DEFAULT_SENSOR_PORT = "1";
   private Handler handler = new Handler();
   private boolean pressedEventEnabled;
   private NxtTouchSensor.State previousState;
   private boolean releasedEventEnabled;
   private final Runnable sensorReader;


   public NxtTouchSensor(ComponentContainer var1) {
      super(var1, "NxtTouchSensor");
      this.previousState = NxtTouchSensor.State.UNKNOWN;
      this.sensorReader = new Runnable() {
         public void run() {
            if(NxtTouchSensor.this.bluetooth != null && NxtTouchSensor.this.bluetooth.IsConnected()) {
               LegoMindstormsNxtSensor.SensorValue var1 = NxtTouchSensor.this.getPressedValue("");
               if(var1.valid) {
                  NxtTouchSensor.State var2;
                  if(((Boolean)var1.value).booleanValue()) {
                     var2 = NxtTouchSensor.State.PRESSED;
                  } else {
                     var2 = NxtTouchSensor.State.RELEASED;
                  }

                  if(var2 != NxtTouchSensor.this.previousState) {
                     if(var2 == NxtTouchSensor.State.PRESSED && NxtTouchSensor.this.pressedEventEnabled) {
                        NxtTouchSensor.this.Pressed();
                     }

                     if(var2 == NxtTouchSensor.State.RELEASED && NxtTouchSensor.this.releasedEventEnabled) {
                        NxtTouchSensor.this.Released();
                     }
                  }

                  NxtTouchSensor.this.previousState = var2;
               }
            }

            if(NxtTouchSensor.this.isHandlerNeeded()) {
               NxtTouchSensor.this.handler.post(NxtTouchSensor.this.sensorReader);
            }

         }
      };
      this.SensorPort("1");
      this.PressedEventEnabled(false);
      this.ReleasedEventEnabled(false);
   }

   private LegoMindstormsNxtSensor.SensorValue getPressedValue(String var1) {
      boolean var2 = false;
      byte[] var3 = this.getInputValues(var1, this.port);
      if(var3 != null && this.getBooleanValueFromBytes(var3, 4)) {
         if(this.getSWORDValueFromBytes(var3, 12) != 0) {
            var2 = true;
         }

         return new LegoMindstormsNxtSensor.SensorValue(true, Boolean.valueOf(var2));
      } else {
         return new LegoMindstormsNxtSensor.SensorValue(false, (Object)null);
      }
   }

   private boolean isHandlerNeeded() {
      return this.pressedEventEnabled || this.releasedEventEnabled;
   }

   @SimpleFunction(
      description = "Returns true if the touch sensor is pressed."
   )
   public boolean IsPressed() {
      if(this.checkBluetooth("IsPressed")) {
         LegoMindstormsNxtSensor.SensorValue var1 = this.getPressedValue("IsPressed");
         if(var1.valid) {
            return ((Boolean)var1.value).booleanValue();
         }
      }

      return false;
   }

   @SimpleEvent(
      description = "Touch sensor has been pressed."
   )
   public void Pressed() {
      EventDispatcher.dispatchEvent(this, "Pressed", new Object[0]);
   }

   @DesignerProperty(
      defaultValue = "False",
      editorType = "boolean"
   )
   @SimpleProperty
   public void PressedEventEnabled(boolean var1) {
      boolean var2 = this.isHandlerNeeded();
      this.pressedEventEnabled = var1;
      var1 = this.isHandlerNeeded();
      if(var2 && !var1) {
         this.handler.removeCallbacks(this.sensorReader);
      }

      if(!var2 && var1) {
         this.previousState = NxtTouchSensor.State.UNKNOWN;
         this.handler.post(this.sensorReader);
      }

   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "Whether the Pressed event should fire when the touch sensor is pressed."
   )
   public boolean PressedEventEnabled() {
      return this.pressedEventEnabled;
   }

   @SimpleEvent(
      description = "Touch sensor has been released."
   )
   public void Released() {
      EventDispatcher.dispatchEvent(this, "Released", new Object[0]);
   }

   @DesignerProperty(
      defaultValue = "False",
      editorType = "boolean"
   )
   @SimpleProperty
   public void ReleasedEventEnabled(boolean var1) {
      boolean var2 = this.isHandlerNeeded();
      this.releasedEventEnabled = var1;
      var1 = this.isHandlerNeeded();
      if(var2 && !var1) {
         this.handler.removeCallbacks(this.sensorReader);
      }

      if(!var2 && var1) {
         this.previousState = NxtTouchSensor.State.UNKNOWN;
         this.handler.post(this.sensorReader);
      }

   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "Whether the Released event should fire when the touch sensor is released."
   )
   public boolean ReleasedEventEnabled() {
      return this.releasedEventEnabled;
   }

   @DesignerProperty(
      defaultValue = "1",
      editorType = "lego_nxt_sensor_port"
   )
   @SimpleProperty(
      userVisible = false
   )
   public void SensorPort(String var1) {
      this.setSensorPort(var1);
   }

   protected void initializeSensor(String var1) {
      this.setInputMode(var1, this.port, 1, 32);
   }

   public void onDelete() {
      this.handler.removeCallbacks(this.sensorReader);
      super.onDelete();
   }

   private static enum State {

      // $FF: synthetic field
      private static final NxtTouchSensor.State[] $VALUES = new NxtTouchSensor.State[]{UNKNOWN, PRESSED, RELEASED};
      PRESSED,
      RELEASED,
      UNKNOWN;


   }
}
