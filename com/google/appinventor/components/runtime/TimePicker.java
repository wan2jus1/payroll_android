package com.google.appinventor.components.runtime;

import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Handler;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.ButtonBase;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;
import java.util.Calendar;

@DesignerComponent(
   category = ComponentCategory.USERINTERFACE,
   description = "<p>A button that, when clicked on, launches  a popup dialog to allow the user to select a time.</p>",
   version = 2
)
@SimpleObject
public class TimePicker extends ButtonBase {

   private Handler androidUIHandler;
   private boolean customTime = false;
   private Form form;
   private int hour = 0;
   private int minute = 0;
   private TimePickerDialog time;
   private OnTimeSetListener timePickerListener = new OnTimeSetListener() {
      public void onTimeSet(android.widget.TimePicker var1, int var2, int var3) {
         if(var1.isShown()) {
            TimePicker.this.hour = var2;
            TimePicker.this.minute = var3;
            TimePicker.this.androidUIHandler.post(new Runnable() {
               public void run() {
                  TimePicker.this.AfterTimeSet();
               }
            });
         }

      }
   };


   public TimePicker(ComponentContainer var1) {
      super(var1);
      this.form = var1.$form();
      Calendar var2 = Calendar.getInstance();
      this.hour = var2.get(11);
      this.minute = var2.get(12);
      this.time = new TimePickerDialog(this.container.$context(), this.timePickerListener, this.hour, this.minute, false);
      this.androidUIHandler = new Handler();
   }

   @SimpleEvent(
      description = "This event is run when a user has set the time in the popup dialog."
   )
   public void AfterTimeSet() {
      EventDispatcher.dispatchEvent(this, "AfterTimeSet", new Object[0]);
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "The hour of the last time set using the time picker. The hour is in a 24 hour format. If the last time set was 11:53 pm, this property will return 23."
   )
   public int Hour() {
      return this.hour;
   }

   @SimpleFunction(
      description = "Launches the TimePicker popup."
   )
   public void LaunchPicker() {
      this.click();
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "The minute of the last time set using the time picker"
   )
   public int Minute() {
      return this.minute;
   }

   @SimpleFunction(
      description = "Set the time to be shown in the Time Picker popup. Current time is shown by default."
   )
   public void SetTimeToDisplay(int var1, int var2) {
      if(var1 >= 0 && var1 <= 23) {
         if(var2 >= 0 && var2 <= 59) {
            this.time.updateTime(var1, var2);
            this.customTime = true;
         } else {
            this.form.dispatchErrorOccurredEvent(this, "SetTimeToDisplay", 2302, new Object[0]);
         }
      } else {
         this.form.dispatchErrorOccurredEvent(this, "SetTimeToDisplay", 2301, new Object[0]);
      }
   }

   public void click() {
      if(!this.customTime) {
         Calendar var1 = Calendar.getInstance();
         int var2 = var1.get(11);
         int var3 = var1.get(12);
         this.time.updateTime(var2, var3);
      } else {
         this.customTime = false;
      }

      this.time.show();
   }
}
