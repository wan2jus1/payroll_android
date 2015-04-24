package com.google.appinventor.components.runtime;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.AlarmHandler;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.Deleteable;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.OnDestroyListener;
import com.google.appinventor.components.runtime.OnResumeListener;
import com.google.appinventor.components.runtime.OnStopListener;
import com.google.appinventor.components.runtime.ReplForm;
import com.google.appinventor.components.runtime.errors.YailRuntimeError;
import com.google.appinventor.components.runtime.util.Dates;
import com.google.appinventor.components.runtime.util.TimerInternal;
import java.util.Calendar;

@DesignerComponent(
   category = ComponentCategory.SENSORS,
   description = "Non-visible component that provides the instant in time using the internal clock on the phone. It can fire a timer at regularly set intervals and perform time calculations, manipulations, and conversions. Methods to format the date and time are also available.",
   iconName = "images/clock.png",
   nonVisible = true,
   version = 1
)
@SimpleObject
public final class Clock extends AndroidNonvisibleComponent implements Component, AlarmHandler, OnStopListener, OnResumeListener, OnDestroyListener, Deleteable {

   private static final boolean DEFAULT_ENABLED = true;
   private static final int DEFAULT_INTERVAL = 1000;
   private boolean onScreen = false;
   private boolean timerAlwaysFires = true;
   private TimerInternal timerInternal;


   public Clock() {
      super((Form)null);
   }

   public Clock(ComponentContainer var1) {
      super(var1.$form());
      this.timerInternal = new TimerInternal(this, true, 1000);
      this.form.registerForOnResume(this);
      this.form.registerForOnStop(this);
      this.form.registerForOnDestroy(this);
      if(this.form instanceof ReplForm) {
         this.onScreen = true;
      }

   }

   @SimpleFunction(
      description = "An instant in time some days after the argument"
   )
   public static Calendar AddDays(Calendar var0, int var1) {
      var0 = (Calendar)var0.clone();
      Dates.DateAdd(var0, 5, var1);
      return var0;
   }

   @SimpleFunction(
      description = "An instant in time some hours after the argument"
   )
   public static Calendar AddHours(Calendar var0, int var1) {
      var0 = (Calendar)var0.clone();
      Dates.DateAdd(var0, 11, var1);
      return var0;
   }

   @SimpleFunction(
      description = "An instant in time some minutes after the argument"
   )
   public static Calendar AddMinutes(Calendar var0, int var1) {
      var0 = (Calendar)var0.clone();
      Dates.DateAdd(var0, 12, var1);
      return var0;
   }

   @SimpleFunction(
      description = "An instant in time some months after the argument"
   )
   public static Calendar AddMonths(Calendar var0, int var1) {
      var0 = (Calendar)var0.clone();
      Dates.DateAdd(var0, 2, var1);
      return var0;
   }

   @SimpleFunction(
      description = "An instant in time some seconds after the argument"
   )
   public static Calendar AddSeconds(Calendar var0, int var1) {
      var0 = (Calendar)var0.clone();
      Dates.DateAdd(var0, 13, var1);
      return var0;
   }

   @SimpleFunction(
      description = "An instant in time some weeks after the argument"
   )
   public static Calendar AddWeeks(Calendar var0, int var1) {
      var0 = (Calendar)var0.clone();
      Dates.DateAdd(var0, 3, var1);
      return var0;
   }

   @SimpleFunction(
      description = "An instant in time some years after the argument"
   )
   public static Calendar AddYears(Calendar var0, int var1) {
      var0 = (Calendar)var0.clone();
      Dates.DateAdd(var0, 1, var1);
      return var0;
   }

   @SimpleFunction(
      description = "The day of the month"
   )
   public static int DayOfMonth(Calendar var0) {
      return Dates.Day(var0);
   }

   @SimpleFunction(
      description = "Milliseconds elapsed between instants"
   )
   public static long Duration(Calendar var0, Calendar var1) {
      return var1.getTimeInMillis() - var0.getTimeInMillis();
   }

   @SimpleFunction(
      description = "Text representing the date of an instant"
   )
   public static String FormatDate(Calendar var0) {
      return Dates.FormatDate(var0);
   }

   @SimpleFunction(
      description = "Text representing the date and time of an instant"
   )
   public static String FormatDateTime(Calendar var0) {
      return Dates.FormatDateTime(var0);
   }

   @SimpleFunction(
      description = "Text representing the time of an instant"
   )
   public static String FormatTime(Calendar var0) {
      return Dates.FormatTime(var0);
   }

   @SimpleFunction(
      description = "The instant in time measured as milliseconds since 1970."
   )
   public static long GetMillis(Calendar var0) {
      return var0.getTimeInMillis();
   }

   @SimpleFunction(
      description = "The hour of the day"
   )
   public static int Hour(Calendar var0) {
      return Dates.Hour(var0);
   }

   @SimpleFunction(
      description = "An instant specified by MM/DD/YYYY hh:mm:ss or MM/DD/YYYY or hh:mm"
   )
   public static Calendar MakeInstant(String var0) {
      try {
         Calendar var2 = Dates.DateValue(var0);
         return var2;
      } catch (IllegalArgumentException var1) {
         throw new YailRuntimeError("Argument to MakeInstant should have form MM/DD/YYYY, hh:mm:ss, or MM/DD/YYYY or hh:mm", "Sorry to be so picky.");
      }
   }

   @SimpleFunction(
      description = "An instant in time specified by the milliseconds since 1970."
   )
   public static Calendar MakeInstantFromMillis(long var0) {
      Calendar var2 = Dates.Now();
      var2.setTimeInMillis(var0);
      return var2;
   }

   @SimpleFunction(
      description = "The minute of the hour"
   )
   public static int Minute(Calendar var0) {
      return Dates.Minute(var0);
   }

   @SimpleFunction(
      description = "The month of the year represented as a number from 1 to 12)"
   )
   public static int Month(Calendar var0) {
      return Dates.Month(var0) + 1;
   }

   @SimpleFunction(
      description = "The name of the month"
   )
   public static String MonthName(Calendar var0) {
      return Dates.MonthName(var0);
   }

   @SimpleFunction(
      description = "The current instant in time read from phone\'s clock"
   )
   public static Calendar Now() {
      return Dates.Now();
   }

   @SimpleFunction(
      description = "The second of the minute"
   )
   public static int Second(Calendar var0) {
      return Dates.Second(var0);
   }

   @SimpleFunction(
      description = "The phone\'s internal time"
   )
   public static long SystemTime() {
      return Dates.Timer();
   }

   @SimpleFunction(
      description = "The day of the week represented as a number from 1 (Sunday) to 7 (Saturday)"
   )
   public static int Weekday(Calendar var0) {
      return Dates.Weekday(var0);
   }

   @SimpleFunction(
      description = "The name of the day of the week"
   )
   public static String WeekdayName(Calendar var0) {
      return Dates.WeekdayName(var0);
   }

   @SimpleFunction(
      description = "The year"
   )
   public static int Year(Calendar var0) {
      return Dates.Year(var0);
   }

   @SimpleEvent(
      description = "Timer has gone off."
   )
   public void Timer() {
      if(this.timerAlwaysFires || this.onScreen) {
         EventDispatcher.dispatchEvent(this, "Timer", new Object[0]);
      }

   }

   @DesignerProperty(
      defaultValue = "True",
      editorType = "boolean"
   )
   @SimpleProperty
   public void TimerAlwaysFires(boolean var1) {
      this.timerAlwaysFires = var1;
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "Will fire even when application is not showing on the screen if true"
   )
   public boolean TimerAlwaysFires() {
      return this.timerAlwaysFires;
   }

   @DesignerProperty(
      defaultValue = "True",
      editorType = "boolean"
   )
   @SimpleProperty
   public void TimerEnabled(boolean var1) {
      this.timerInternal.Enabled(var1);
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "Fires timer if true"
   )
   public boolean TimerEnabled() {
      return this.timerInternal.Enabled();
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "Interval between timer events in ms"
   )
   public int TimerInterval() {
      return this.timerInternal.Interval();
   }

   @DesignerProperty(
      defaultValue = "1000",
      editorType = "non_negative_integer"
   )
   @SimpleProperty
   public void TimerInterval(int var1) {
      this.timerInternal.Interval(var1);
   }

   public void alarm() {
      this.Timer();
   }

   public void onDelete() {
      this.timerInternal.Enabled(false);
   }

   public void onDestroy() {
      this.timerInternal.Enabled(false);
   }

   public void onResume() {
      this.onScreen = true;
   }

   public void onStop() {
      this.onScreen = false;
   }
}
