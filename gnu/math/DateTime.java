package gnu.math;

import gnu.math.Complex;
import gnu.math.Duration;
import gnu.math.Numeric;
import gnu.math.Quantity;
import gnu.math.Unit;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateTime extends Quantity implements Cloneable {

   public static final int DATE_MASK = 14;
   static final int DAY_COMPONENT = 3;
   public static final int DAY_MASK = 8;
   public static TimeZone GMT = TimeZone.getTimeZone("GMT");
   static final int HOURS_COMPONENT = 4;
   public static final int HOURS_MASK = 16;
   static final int MINUTES_COMPONENT = 5;
   public static final int MINUTES_MASK = 32;
   static final int MONTH_COMPONENT = 2;
   public static final int MONTH_MASK = 4;
   static final int SECONDS_COMPONENT = 6;
   public static final int SECONDS_MASK = 64;
   static final int TIMEZONE_COMPONENT = 7;
   public static final int TIMEZONE_MASK = 128;
   public static final int TIME_MASK = 112;
   static final int YEAR_COMPONENT = 1;
   public static final int YEAR_MASK = 2;
   private static final Date minDate = new Date(Long.MIN_VALUE);
   GregorianCalendar calendar;
   int mask;
   int nanoSeconds;
   Unit unit;


   public DateTime(int var1) {
      this.unit = Unit.date;
      this.calendar = new GregorianCalendar();
      this.calendar.setGregorianChange(minDate);
      this.calendar.clear();
      this.mask = var1;
   }

   public DateTime(int var1, GregorianCalendar var2) {
      this.unit = Unit.date;
      this.calendar = var2;
      this.mask = var1;
   }

   public static DateTime add(DateTime var0, Duration var1, int var2) {
      if(var1.unit != Unit.duration && (var1.unit != Unit.month || (var0.mask & 14) == 14)) {
         DateTime var3 = new DateTime(var0.mask, (GregorianCalendar)var0.calendar.clone());
         if(var1.months != 0) {
            int var4 = var3.getYear() * 12 + var3.calendar.get(2) + var1.months * var2;
            int var8 = var3.calendar.get(5);
            int var5;
            int var6;
            if(var4 >= 12) {
               var6 = var4 / 12;
               var5 = var4 % 12;
               var3.calendar.set(0, 1);
               var4 = daysInMonth(var5, var6);
            } else {
               var4 = 11 - var4;
               var3.calendar.set(0, 0);
               var6 = var4 / 12 + 1;
               var5 = 11 - var4 % 12;
               var4 = daysInMonth(var5, 1);
            }

            int var7 = var8;
            if(var8 > var4) {
               var7 = var4;
            }

            var3.calendar.set(var6, var5, var7);
         }

         long var11 = (long)var0.nanoSeconds + (long)var2 * (var1.seconds * 1000000000L + (long)var1.nanos);
         if(var11 != 0L) {
            long var9 = var11;
            long var13;
            if((var0.mask & 112) == 0) {
               var13 = var11 % 86400000000000L;
               var9 = var13;
               if(var13 < 0L) {
                  var9 = var13 + 86400000000000L;
               }

               var9 = var11 - var9;
            }

            var11 = var3.calendar.getTimeInMillis();
            var13 = var9 / 1000000000L;
            var3.calendar.setTimeInMillis(var11 + var13 * 1000L);
            var3.nanoSeconds = (int)(var9 % 1000000000L);
         }

         return var3;
      } else {
         throw new IllegalArgumentException("invalid date/time +/- duration combinatuion");
      }
   }

   public static DateTime addMinutes(DateTime var0, int var1) {
      return addSeconds(var0, var1 * 60);
   }

   public static DateTime addSeconds(DateTime var0, int var1) {
      DateTime var2 = new DateTime(var0.mask, (GregorianCalendar)var0.calendar.clone());
      long var3 = (long)var1 * 1000000000L;
      if(var3 != 0L) {
         var3 += (long)var0.nanoSeconds;
         long var5 = var0.calendar.getTimeInMillis();
         long var7 = var3 / 1000000L;
         var2.calendar.setTimeInMillis(var5 + var7);
         var2.nanoSeconds = (int)(var3 % 1000000L);
      }

      return var2;
   }

   private static void append(int var0, StringBuffer var1, int var2) {
      int var3 = var1.length();
      var1.append(var0);
      var0 = var3 + var2 - var1.length();

      while(true) {
         --var0;
         if(var0 < 0) {
            return;
         }

         var1.insert(var3, '0');
      }
   }

   public static int compare(DateTime var0, DateTime var1) {
      long var8 = var0.calendar.getTimeInMillis();
      long var12 = var1.calendar.getTimeInMillis();
      long var10 = var8;
      long var6 = var12;
      long var4;
      if(((var0.mask | var1.mask) & 14) == 0) {
         var4 = var8;
         if(var8 < 0L) {
            var4 = var8 + 86400000L;
         }

         var10 = var4;
         var6 = var12;
         if(var12 < 0L) {
            var6 = var12 + 86400000L;
            var10 = var4;
         }
      }

      int var3 = var0.nanoSeconds;
      int var2 = var1.nanoSeconds;
      var4 = var10 + (long)(var3 / 1000000);
      var6 += (long)(var2 / 1000000);
      var3 %= 1000000;
      var2 %= 1000000;
      return var4 < var6?-1:(var4 > var6?1:(var3 < var2?-1:(var3 > var2?1:0)));
   }

   public static int daysInMonth(int var0, int var1) {
      switch(var0) {
      case 1:
         if(isLeapYear(var1)) {
            return 29;
         }

         return 28;
      case 2:
      case 4:
      case 6:
      case 7:
      case 9:
      default:
         return 31;
      case 3:
      case 5:
      case 8:
      case 10:
         return 30;
      }
   }

   public static boolean isLeapYear(int var0) {
      return var0 % 4 == 0 && (var0 % 100 != 0 || var0 % 400 == 0);
   }

   public static TimeZone minutesToTimeZone(int var0) {
      if(var0 == 0) {
         return GMT;
      } else {
         StringBuffer var1 = new StringBuffer("GMT");
         toStringZone(var0, var1);
         return TimeZone.getTimeZone(var1.toString());
      }
   }

   public static DateTime parse(String var0, int var1) {
      boolean var4 = true;
      DateTime var2 = new DateTime(var1);
      var0 = var0.trim();
      int var6 = var0.length();
      int var3 = 0;
      boolean var5;
      if((var1 & 14) != 0) {
         var5 = true;
      } else {
         var5 = false;
      }

      if((var1 & 112) == 0) {
         var4 = false;
      }

      if(var5) {
         var1 = var2.parseDate(var0, 0, var1);
         var3 = var1;
         if(var4) {
            if(var1 >= 0 && var1 < var6 && var0.charAt(var1) == 84) {
               var3 = var1 + 1;
            } else {
               var3 = -1;
            }
         }
      }

      var1 = var3;
      if(var4) {
         var1 = var2.parseTime(var0, var3);
      }

      if(var2.parseZone(var0, var1) != var6) {
         throw new NumberFormatException("Unrecognized date/time \'" + var0 + '\'');
      } else {
         return var2;
      }
   }

   private static int parseDigits(String var0, int var1) {
      int var2 = var1;
      var1 = -1;

      for(int var4 = var0.length(); var2 < var4; ++var2) {
         int var3 = Character.digit(var0.charAt(var2), 10);
         if(var3 < 0) {
            break;
         }

         if(var1 > 20000) {
            return 0;
         }

         if(var1 < 0) {
            var1 = var3;
         } else {
            var1 = var1 * 10 + var3;
         }
      }

      return var1 < 0?var2:var2 | var1 << 16;
   }

   public static Duration sub(DateTime var0, DateTime var1) {
      long var4 = var0.calendar.getTimeInMillis();
      long var6 = var1.calendar.getTimeInMillis();
      int var2 = var0.nanoSeconds;
      int var3 = var1.nanoSeconds;
      long var8 = (long)(var2 / 1000000);
      long var10 = (long)(var3 / 1000000);
      var2 = var3 % 1000000;
      var4 = var4 + var8 - (var6 + var10);
      var6 = var4 / 1000L;
      var2 = (int)(var4 % 1000L * 1000000L + (long)var2 - (long)var2);
      return Duration.make(0, var6 + (long)(var2 / 1000000000), var2 % 1000000000, Unit.second);
   }

   public static void toStringZone(int var0, StringBuffer var1) {
      if(var0 == 0) {
         var1.append('Z');
      } else {
         if(var0 < 0) {
            var1.append('-');
            var0 = -var0;
         } else {
            var1.append('+');
         }

         append(var0 / 60, var1, 2);
         var1.append(':');
         append(var0 % 60, var1, 2);
      }
   }

   public Numeric add(Object var1, int var2) {
      if(var1 instanceof Duration) {
         return add(this, (Duration)var1, var2);
      } else if(var1 instanceof DateTime && var2 == -1) {
         return sub(this, (DateTime)var1);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public Numeric addReversed(Numeric var1, int var2) {
      if(var1 instanceof Duration && var2 == 1) {
         return add(this, (Duration)var1, var2);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public DateTime adjustTimezone(int var1) {
      DateTime var3 = new DateTime(this.mask, (GregorianCalendar)this.calendar.clone());
      TimeZone var2;
      if(var1 == 0) {
         var2 = GMT;
      } else {
         StringBuffer var6 = new StringBuffer("GMT");
         toStringZone(var1, var6);
         var2 = TimeZone.getTimeZone(var6.toString());
      }

      var3.calendar.setTimeZone(var2);
      if((var3.mask & 128) != 0) {
         long var4 = this.calendar.getTimeInMillis();
         var3.calendar.setTimeInMillis(var4);
         if((this.mask & 112) == 0) {
            var3.calendar.set(11, 0);
            var3.calendar.set(12, 0);
            var3.calendar.set(13, 0);
            var3.nanoSeconds = 0;
         }

         return var3;
      } else {
         var3.mask |= 128;
         return var3;
      }
   }

   public DateTime cast(int var1) {
      int var3 = this.mask & -129;
      if(var1 == var3) {
         return this;
      } else {
         DateTime var2 = new DateTime(var1, (GregorianCalendar)this.calendar.clone());
         if((~var3 & var1) != 0 && (var3 != 14 || var1 != 126)) {
            throw new ClassCastException("cannot cast DateTime - missing conponents");
         } else {
            if(this.isZoneUnspecified()) {
               var2.mask &= -129;
            } else {
               var2.mask |= 128;
            }

            var1 = var3 & ~var1;
            if((var1 & 112) != 0) {
               var2.calendar.clear(11);
               var2.calendar.clear(12);
               var2.calendar.clear(13);
            } else {
               var2.nanoSeconds = this.nanoSeconds;
            }

            if((var1 & 2) != 0) {
               var2.calendar.clear(1);
               var2.calendar.clear(0);
            }

            if((var1 & 4) != 0) {
               var2.calendar.clear(2);
            }

            if((var1 & 8) != 0) {
               var2.calendar.clear(5);
            }

            return var2;
         }
      }
   }

   public int compare(Object var1) {
      return var1 instanceof DateTime?compare(this, (DateTime)var1):((Numeric)var1).compareReversed(this);
   }

   public int components() {
      return this.mask & -129;
   }

   public int getDay() {
      return this.calendar.get(5);
   }

   public int getHours() {
      return this.calendar.get(11);
   }

   public int getMinutes() {
      return this.calendar.get(12);
   }

   public int getMonth() {
      return this.calendar.get(2) + 1;
   }

   public int getNanoSecondsOnly() {
      return this.nanoSeconds;
   }

   public int getSecondsOnly() {
      return this.calendar.get(13);
   }

   public int getWholeSeconds() {
      return this.calendar.get(13);
   }

   public int getYear() {
      int var2 = this.calendar.get(1);
      int var1 = var2;
      if(this.calendar.get(0) == 0) {
         var1 = 1 - var2;
      }

      return var1;
   }

   public int getZoneMinutes() {
      return this.calendar.getTimeZone().getRawOffset() / '\uea60';
   }

   public boolean isExact() {
      return (this.mask & 112) == 0;
   }

   public boolean isZero() {
      throw new Error("DateTime.isZero not meaningful!");
   }

   public boolean isZoneUnspecified() {
      return (this.mask & 128) == 0;
   }

   public Complex number() {
      throw new Error("number needs to be implemented!");
   }

   int parseDate(String var1, int var2, int var3) {
      int var4;
      if(var2 < 0) {
         var4 = var2;
      } else {
         int var8 = var1.length();
         boolean var6 = false;
         boolean var5 = var6;
         var4 = var2;
         if(var2 < var8) {
            var5 = var6;
            var4 = var2;
            if(var1.charAt(var2) == 45) {
               var4 = var2 + 1;
               var5 = true;
            }
         }

         int var9;
         int var10;
         if((var3 & 2) == 0) {
            if(!var5) {
               return -1;
            }

            var2 = -1;
            var9 = var4;
         } else {
            var10 = parseDigits(var1, var4);
            var2 = var10 >> 16;
            var10 &= '\uffff';
            if(var10 != var4 + 4 && (var10 <= var4 + 4 || var1.charAt(var4) == 48)) {
               return -1;
            }

            if(!var5 && var2 != 0) {
               this.calendar.set(1, var2);
               var9 = var10;
            } else {
               this.calendar.set(0, 0);
               this.calendar.set(1, var2 + 1);
               var9 = var10;
            }
         }

         var4 = var9;
         if((var3 & 12) != 0) {
            if(var9 >= var8 || var1.charAt(var9) != 45) {
               return -1;
            }

            var10 = var9 + 1;
            int var7;
            if((var3 & 4) != 0) {
               var4 = parseDigits(var1, var10);
               var7 = var4 >> 16;
               var9 = var4 & '\uffff';
               if(var7 <= 0 || var7 > 12 || var9 != var10 + 2) {
                  return -1;
               }

               this.calendar.set(2, var7 - 1);
               var4 = var9;
               if((var3 & 8) == 0) {
                  return var4;
               }

               var4 = var7;
            } else {
               var4 = -1;
               var9 = var10;
            }

            if(var9 < var8 && var1.charAt(var9) == 45) {
               var7 = var9 + 1;
               var10 = parseDigits(var1, var7);
               var9 = var10 >> 16;
               var10 &= '\uffff';
               if(var9 > 0 && var10 == var7 + 2) {
                  if((var3 & 4) == 0) {
                     var2 = 31;
                  } else {
                     if((var3 & 2) == 0) {
                        var2 = 2000;
                     }

                     var2 = daysInMonth(var4 - 1, var2);
                  }

                  if(var9 <= var2) {
                     this.calendar.set(5, var9);
                     return var10;
                  }
               }

               return -1;
            }

            return -1;
         }
      }

      return var4;
   }

   int parseTime(String var1, int var2) {
      if(var2 < 0) {
         return var2;
      } else {
         int var8 = var1.length();
         int var3 = parseDigits(var1, var2);
         int var6 = var3 >> 16;
         var3 &= '\uffff';
         if(var6 <= 24 && var3 == var2 + 2 && var3 != var8 && var1.charAt(var3) == 58) {
            var2 = var3 + 1;
            var3 = parseDigits(var1, var2);
            int var7 = var3 >> 16;
            var3 &= '\uffff';
            if(var7 < 60 && var3 == var2 + 2 && var3 != var8 && var1.charAt(var3) == 58) {
               ++var3;
               var2 = parseDigits(var1, var3);
               int var9 = var2 >> 16;
               var2 &= '\uffff';
               if(var9 < 60 && var2 == var3 + 2) {
                  var3 = var2;
                  if(var2 + 1 < var8) {
                     var3 = var2;
                     if(var1.charAt(var2) == 46) {
                        var3 = var2;
                        if(Character.digit(var1.charAt(var2 + 1), 10) >= 0) {
                           var3 = var2 + 1;
                           int var4 = 0;

                           int var5;
                           for(var2 = 0; var3 < var8; var4 = var5) {
                              int var10 = Character.digit(var1.charAt(var3), 10);
                              if(var10 < 0) {
                                 break;
                              }

                              if(var2 < 9) {
                                 var5 = var4 * 10 + var10;
                              } else {
                                 var5 = var4;
                                 if(var2 == 9) {
                                    var5 = var4;
                                    if(var10 >= 5) {
                                       var5 = var4 + 1;
                                    }
                                 }
                              }

                              ++var2;
                              ++var3;
                           }

                           while(var2 < 9) {
                              var4 *= 10;
                              ++var2;
                           }

                           this.nanoSeconds = var4;
                        }
                     }
                  }

                  if(var6 == 24 && (var7 != 0 || var9 != 0 || this.nanoSeconds != 0)) {
                     return -1;
                  }

                  this.calendar.set(11, var6);
                  this.calendar.set(12, var7);
                  this.calendar.set(13, var9);
                  return var3;
               }
            }
         }

         return -1;
      }
   }

   int parseZone(String var1, int var2) {
      if(var2 >= 0) {
         int var3 = this.parseZoneMinutes(var1, var2);
         if(var3 == 0) {
            return -1;
         }

         if(var3 != var2) {
            int var4 = var3 & '\uffff';
            TimeZone var5;
            if(var3 >> 16 == 0) {
               var5 = GMT;
            } else {
               var5 = TimeZone.getTimeZone("GMT" + var1.substring(var2, var4));
            }

            this.calendar.setTimeZone(var5);
            this.mask |= 128;
            return var4;
         }
      }

      return var2;
   }

   int parseZoneMinutes(String var1, int var2) {
      byte var5 = 0;
      int var4 = var1.length();
      int var3;
      if(var2 != var4 && var2 >= 0) {
         char var8 = var1.charAt(var2);
         if(var8 == 90) {
            return var2 + 1;
         }

         if(var8 != 43 && var8 != 45) {
            return var2;
         }

         ++var2;
         int var7 = parseDigits(var1, var2);
         int var9 = var7 >> 16;
         var3 = var5;
         if(var9 <= 14) {
            int var6 = var9 * 60;
            var7 &= '\uffff';
            var3 = var5;
            if(var7 == var2 + 2) {
               var3 = var5;
               if(var7 < var4) {
                  var2 = var6;
                  var4 = var7;
                  if(var1.charAt(var7) == 58) {
                     ++var7;
                     var2 = parseDigits(var1, var7);
                     var4 = var2 & '\uffff';
                     var2 >>= 16;
                     if(var2 > 0) {
                        var3 = var5;
                        if(var2 >= 60) {
                           return var3;
                        }

                        var3 = var5;
                        if(var9 == 14) {
                           return var3;
                        }
                     }

                     var2 += var6;
                     var3 = var5;
                     if(var4 != var7 + 2) {
                        return var3;
                     }
                  }

                  var3 = var5;
                  if(var2 <= 840) {
                     var3 = var2;
                     if(var8 == 45) {
                        var3 = -var2;
                     }

                     return var3 << 16 | var4;
                  }
               }
            }
         }
      } else {
         var3 = var2;
      }

      return var3;
   }

   public void setTimeZone(TimeZone var1) {
      this.calendar.setTimeZone(var1);
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      this.toString(var1);
      return var1.toString();
   }

   public void toString(StringBuffer var1) {
      boolean var3 = true;
      int var4 = this.components();
      boolean var2;
      if((var4 & 14) != 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      if((var4 & 112) == 0) {
         var3 = false;
      }

      if(var2) {
         this.toStringDate(var1);
         if(var3) {
            var1.append('T');
         }
      }

      if(var3) {
         this.toStringTime(var1);
      }

      this.toStringZone(var1);
   }

   public void toStringDate(StringBuffer var1) {
      int var4 = this.components();
      if((var4 & 2) != 0) {
         int var3 = this.calendar.get(1);
         int var2 = var3;
         if(this.calendar.get(0) == 0) {
            --var3;
            var2 = var3;
            if(var3 != 0) {
               var1.append('-');
               var2 = var3;
            }
         }

         append(var2, var1, 4);
      } else {
         var1.append('-');
      }

      if((var4 & 12) != 0) {
         var1.append('-');
         if((var4 & 4) != 0) {
            append(this.getMonth(), var1, 2);
         }

         if((var4 & 8) != 0) {
            var1.append('-');
            append(this.getDay(), var1, 2);
         }
      }

   }

   public void toStringTime(StringBuffer var1) {
      append(this.getHours(), var1, 2);
      var1.append(':');
      append(this.getMinutes(), var1, 2);
      var1.append(':');
      append(this.getWholeSeconds(), var1, 2);
      Duration.appendNanoSeconds(this.nanoSeconds, var1);
   }

   public void toStringZone(StringBuffer var1) {
      if(!this.isZoneUnspecified()) {
         toStringZone(this.getZoneMinutes(), var1);
      }
   }

   public Unit unit() {
      return this.unit;
   }

   public DateTime withZoneUnspecified() {
      if(this.isZoneUnspecified()) {
         return this;
      } else {
         DateTime var1 = new DateTime(this.mask, (GregorianCalendar)this.calendar.clone());
         var1.calendar.setTimeZone(TimeZone.getDefault());
         var1.mask &= -129;
         return var1;
      }
   }
}
