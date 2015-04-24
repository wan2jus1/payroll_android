package com.google.appinventor.components.runtime;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
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
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.GregorianCalendar;

@DesignerComponent(
   category = ComponentCategory.USERINTERFACE,
   description = "<p>A button that, when clicked on, launches a popup dialog to allow the user to select a date.</p>",
   version = 2
)
@SimpleObject
public class DatePicker extends ButtonBase {

   private Handler androidUIHandler;
   private boolean customDate = false;
   private DatePickerDialog date;
   private OnDateSetListener datePickerListener = new OnDateSetListener() {
      public void onDateSet(android.widget.DatePicker var1, int var2, int var3, int var4) {
         if(var1.isShown()) {
            DatePicker.this.year = var2;
            DatePicker.this.javaMonth = var3;
            DatePicker.this.month = DatePicker.this.javaMonth + 1;
            DatePicker.this.day = var4;
            DatePicker.this.date.updateDate(DatePicker.this.year, DatePicker.this.javaMonth, DatePicker.this.day);
            DatePicker.this.androidUIHandler.post(new Runnable() {
               public void run() {
                  DatePicker.this.AfterDateSet();
               }
            });
         }

      }
   };
   private int day;
   private Form form;
   private int javaMonth;
   private String[] localizedMonths = (new DateFormatSymbols()).getMonths();
   private int month;
   private int year;


   public DatePicker(ComponentContainer var1) {
      super(var1);
      this.form = var1.$form();
      Calendar var2 = Calendar.getInstance();
      this.year = var2.get(1);
      this.javaMonth = var2.get(2);
      this.month = this.javaMonth + 1;
      this.day = var2.get(5);
      this.date = new DatePickerDialog(this.container.$context(), this.datePickerListener, this.year, this.javaMonth, this.day);
      this.androidUIHandler = new Handler();
   }

   @SimpleEvent(
      description = "Event that runs after the user chooses a Date in the dialog"
   )
   public void AfterDateSet() {
      EventDispatcher.dispatchEvent(this, "AfterDateSet", new Object[0]);
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "the Day of the month that was last picked using the DatePicker."
   )
   public int Day() {
      return this.day;
   }

   @SimpleFunction(
      description = "Launches the DatePicker popup."
   )
   public void LaunchPicker() {
      this.click();
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "the number of the Month that was last picked using the DatePicker. Note that months start in 1 = January, 12 = December."
   )
   public int Month() {
      return this.month;
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "Returns the name of the Month that was last picked using the DatePicker, in textual format."
   )
   public String MonthInText() {
      return this.localizedMonths[this.javaMonth];
   }

   @SimpleFunction(
      description = "Allows the user to set the date to be displayed when the date picker opens.\nValid values for the month field are 1-12 and 1-31 for the day field.\n"
   )
   public void SetDateToDisplay(int var1, int var2, int var3) {
      --var2;

      try {
         GregorianCalendar var4 = new GregorianCalendar(var1, var2, var3);
         var4.setLenient(false);
         var4.getTime();
      } catch (IllegalArgumentException var5) {
         this.form.dispatchErrorOccurredEvent(this, "SetDateToDisplay", 2401, new Object[0]);
      }

      this.date.updateDate(var1, var2, var3);
      this.customDate = true;
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "the Year that was last picked using the DatePicker"
   )
   public int Year() {
      return this.year;
   }

   public void click() {
      if(!this.customDate) {
         Calendar var1 = Calendar.getInstance();
         int var2 = var1.get(1);
         int var3 = var1.get(2);
         int var4 = var1.get(5);
         this.date.updateDate(var2, var3, var4);
      } else {
         this.customDate = false;
      }

      this.date.show();
   }
}
