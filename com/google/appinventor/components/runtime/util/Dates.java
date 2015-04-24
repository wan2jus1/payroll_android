package com.google.appinventor.components.runtime.util;

import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

@SimpleObject
public final class Dates {

   public static final int DATE_APRIL = 3;
   public static final int DATE_AUGUST = 7;
   public static final int DATE_DAY = 5;
   public static final int DATE_DECEMBER = 11;
   public static final int DATE_FEBRUARY = 1;
   public static final int DATE_FRIDAY = 6;
   public static final int DATE_HOUR = 11;
   public static final int DATE_JANUARY = 0;
   public static final int DATE_JULY = 6;
   public static final int DATE_JUNE = 5;
   public static final int DATE_MARCH = 2;
   public static final int DATE_MAY = 4;
   public static final int DATE_MINUTE = 12;
   public static final int DATE_MONDAY = 2;
   public static final int DATE_MONTH = 2;
   public static final int DATE_NOVEMBER = 10;
   public static final int DATE_OCTOBER = 9;
   public static final int DATE_SATURDAY = 7;
   public static final int DATE_SECOND = 13;
   public static final int DATE_SEPTEMBER = 8;
   public static final int DATE_SUNDAY = 1;
   public static final int DATE_THURSDAY = 5;
   public static final int DATE_TUESDAY = 3;
   public static final int DATE_WEDNESDAY = 4;
   public static final int DATE_WEEK = 3;
   public static final int DATE_YEAR = 1;


   @SimpleFunction
   public static void DateAdd(Calendar var0, int var1, int var2) {
      switch(var1) {
      case 1:
      case 2:
      case 3:
      case 5:
      case 11:
      case 12:
      case 13:
         var0.add(var1, var2);
         return;
      case 4:
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      default:
         throw new IllegalArgumentException("illegal date/time interval kind in function DateAdd()");
      }
   }

   @SimpleFunction
   public static Calendar DateValue(String var0) {
      GregorianCalendar var1 = new GregorianCalendar();

      SimpleDateFormat var2;
      try {
         var2 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
         var2.setLenient(true);
         var1.setTime(var2.parse(var0));
         return var1;
      } catch (ParseException var5) {
         try {
            var2 = new SimpleDateFormat("MM/dd/yyyy");
            var2.setLenient(true);
            var1.setTime(var2.parse(var0));
            return var1;
         } catch (ParseException var4) {
            try {
               var2 = new SimpleDateFormat("HH:mm");
               var2.setLenient(true);
               var1.setTime(var2.parse(var0));
               return var1;
            } catch (ParseException var3) {
               throw new IllegalArgumentException("illegal date/time format in function DateValue()");
            }
         }
      }
   }

   @SimpleFunction
   public static int Day(Calendar var0) {
      return var0.get(5);
   }

   @SimpleFunction
   public static String FormatDate(Calendar var0) {
      return DateFormat.getDateInstance(2).format(var0.getTime());
   }

   @SimpleFunction
   public static String FormatDateTime(Calendar var0) {
      return DateFormat.getDateTimeInstance(2, 2).format(var0.getTime());
   }

   @SimpleFunction
   public static String FormatTime(Calendar var0) {
      return DateFormat.getTimeInstance(2).format(var0.getTime());
   }

   @SimpleFunction
   public static int Hour(Calendar var0) {
      return var0.get(11);
   }

   @SimpleFunction
   public static int Minute(Calendar var0) {
      return var0.get(12);
   }

   @SimpleFunction
   public static int Month(Calendar var0) {
      return var0.get(2);
   }

   @SimpleFunction
   public static String MonthName(Calendar var0) {
      return String.format("%1$tB", new Object[]{var0});
   }

   @SimpleFunction
   public static Calendar Now() {
      return new GregorianCalendar();
   }

   @SimpleFunction
   public static int Second(Calendar var0) {
      return var0.get(13);
   }

   @SimpleFunction
   public static long Timer() {
      return System.currentTimeMillis();
   }

   @SimpleFunction
   public static int Weekday(Calendar var0) {
      return var0.get(7);
   }

   @SimpleFunction
   public static String WeekdayName(Calendar var0) {
      return String.format("%1$tA", new Object[]{var0});
   }

   @SimpleFunction
   public static int Year(Calendar var0) {
      return var0.get(1);
   }
}
