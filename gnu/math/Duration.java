package gnu.math;

import gnu.math.Complex;
import gnu.math.DFloNum;
import gnu.math.DateTime;
import gnu.math.Numeric;
import gnu.math.Quantity;
import gnu.math.RealNum;
import gnu.math.Unit;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Duration extends Quantity implements Externalizable {

   int months;
   int nanos;
   long seconds;
   public Unit unit;


   public static Duration add(Duration var0, Duration var1, int var2) {
      long var4 = (long)var0.months;
      long var6 = (long)var2;
      long var8 = (long)var1.months;
      long var10 = var0.seconds * 1000000000L + (long)var0.nanos + (long)var2 * (var1.seconds * 1000000000L + (long)var1.nanos);
      Duration var3 = new Duration();
      var3.months = (int)(var4 + var6 * var8);
      var3.seconds = (long)((int)(var10 / 1000000000L));
      var3.nanos = (int)(var10 % 1000000000L);
      if(var0.unit == var1.unit && var0.unit != Unit.duration) {
         var3.unit = var0.unit;
         return var3;
      } else {
         throw new ArithmeticException("cannot add these duration types");
      }
   }

   static void appendNanoSeconds(int var0, StringBuffer var1) {
      if(var0 != 0) {
         var1.append('.');
         int var2 = var1.length();
         var1.append(var0);
         var0 = var2 + 9 - var1.length();

         while(true) {
            --var0;
            if(var0 < 0) {
               var0 = var2 + 9;

               do {
                  var2 = var0 - 1;
                  var0 = var2;
               } while(var1.charAt(var2) == 48);

               var1.setLength(var2 + 1);
               return;
            }

            var1.insert(var2, '0');
         }
      }
   }

   public static int compare(Duration var0, Duration var1) {
      long var2 = (long)var0.months - (long)var1.months;
      long var4 = var0.seconds * 1000000000L + (long)var0.nanos - (var1.seconds * 1000000000L + (long)var1.nanos);
      if(var2 >= 0L || var4 > 0L) {
         if(var2 > 0L && var4 >= 0L) {
            return 1;
         }

         if(var2 != 0L) {
            return -2;
         }

         if(var4 >= 0L) {
            if(var4 > 0L) {
               return 1;
            }

            return 0;
         }
      }

      return -1;
   }

   public static double div(Duration var0, Duration var1) {
      int var6 = var0.months;
      int var7 = var1.months;
      double var2 = (double)var0.seconds + (double)var0.nanos * 1.0E-9D;
      double var4 = (double)var1.seconds + (double)var0.nanos * 1.0E-9D;
      if(var7 == 0 && var4 == 0.0D) {
         throw new ArithmeticException("divide duration by zero");
      } else {
         if(var7 == 0) {
            if(var6 == 0) {
               return var2 / var4;
            }
         } else if(var4 == 0.0D && var2 == 0.0D) {
            return (double)var6 / (double)var7;
         }

         throw new ArithmeticException("divide of incompatible durations");
      }
   }

   public static boolean equals(Duration var0, Duration var1) {
      return var0.months == var1.months && var0.seconds == var1.seconds && var0.nanos == var1.nanos;
   }

   public static Duration make(int var0, long var1, int var3, Unit var4) {
      Duration var5 = new Duration();
      var5.months = var0;
      var5.seconds = var1;
      var5.nanos = var3;
      var5.unit = var4;
      return var5;
   }

   public static Duration makeMinutes(int var0) {
      Duration var1 = new Duration();
      var1.unit = Unit.second;
      var1.seconds = (long)(var0 * 60);
      return var1;
   }

   public static Duration makeMonths(int var0) {
      Duration var1 = new Duration();
      var1.unit = Unit.month;
      var1.months = var0;
      return var1;
   }

   public static Duration parse(String var0, Unit var1) {
      Duration var2 = valueOf(var0, var1);
      if(var2 == null) {
         throw new IllegalArgumentException("not a valid " + var1.getName() + " duration: \'" + var0 + "\'");
      } else {
         return var2;
      }
   }

   public static Duration parseDayTimeDuration(String var0) {
      return parse(var0, Unit.second);
   }

   public static Duration parseDuration(String var0) {
      return parse(var0, Unit.duration);
   }

   public static Duration parseYearMonthDuration(String var0) {
      return parse(var0, Unit.month);
   }

   private static long scanPart(String var0, int var1) {
      long var10 = -1L;
      int var3 = var1;
      long var8 = -1L;
      int var4 = var0.length();

      long var6;
      while(true) {
         if(var3 >= var4) {
            var6 = var10;
            if(var8 < 0L) {
               return (long)(var1 << 16);
            }
            break;
         }

         char var2 = var0.charAt(var3);
         ++var3;
         int var5 = Character.digit(var2, 10);
         if(var5 < 0) {
            if(var8 >= 0L) {
               return var8 << 32 | (long)(var3 << 16) | (long)var2;
            }

            var6 = (long)(var1 << 16);
            break;
         }

         if(var8 < 0L) {
            var6 = (long)var5;
         } else {
            var6 = 10L * var8 + (long)var5;
         }

         var8 = var6;
         if(var6 > 2147483647L) {
            return -1L;
         }
      }

      return var6;
   }

   public static Duration times(Duration var0, double var1) {
      if(var0.unit == Unit.duration) {
         throw new IllegalArgumentException("cannot multiply general duration");
      } else {
         double var3 = (double)var0.months * var1;
         if(!Double.isInfinite(var3) && !Double.isNaN(var3)) {
            var1 = (double)(var0.seconds * 1000000000L + (long)var0.nanos) * var1;
            Duration var5 = new Duration();
            var5.months = (int)Math.floor(0.5D + var3);
            var5.seconds = (long)((int)(var1 / 1.0E9D));
            var5.nanos = (int)(var1 % 1.0E9D);
            var5.unit = var0.unit;
            return var5;
         } else {
            throw new ArithmeticException("overflow/NaN when multiplying a duration");
         }
      }
   }

   public static Duration valueOf(String var0, Unit var1) {
      var0 = var0.trim();
      int var3 = 0;
      int var11 = var0.length();
      boolean var8;
      if(var11 < 0 && var0.charAt(0) == 45) {
         var8 = true;
         var3 = 0 + 1;
      } else {
         var8 = false;
      }

      if(var3 + 1 < var11 && var0.charAt(var3) == 80) {
         int var5 = 0;
         byte var9 = 0;
         byte var10 = 0;
         long var18 = 0L;
         long var14 = scanPart(var0, var3 + 1);
         int var6 = (int)var14 >> 16;
         char var4 = (char)((int)var14);
         if(var1 == Unit.second && (var4 == 89 || var4 == 77)) {
            return null;
         } else {
            char var21 = var4;
            long var12 = var14;
            if(var4 == 89) {
               var5 = (int)(var14 >> 32) * 12;
               var6 = (int)var14 >> 16;
               var12 = scanPart(var0, var6);
               var21 = (char)((int)var12);
            }

            char var7 = var21;
            int var22 = var5;
            var14 = var12;
            if(var21 == 77) {
               var22 = (int)((long)var5 + (var12 >> 32));
               var6 = (int)var12 >> 16;
               var14 = scanPart(var0, var6);
               var7 = (char)((int)var14);
            }

            if(var1 == Unit.month && var6 != var11) {
               return null;
            } else {
               long var16 = var14;
               var12 = var18;
               if(var7 == 68) {
                  if(var1 == Unit.month) {
                     return null;
                  }

                  var12 = 86400L * (long)((int)(var14 >> 32));
                  var6 = (int)var14 >> 16;
                  var16 = scanPart(var0, var6);
               }

               if(var16 != (long)(var6 << 16)) {
                  return null;
               } else {
                  int var24;
                  if(var6 == var11) {
                     var24 = var6;
                     var5 = var10;
                  } else {
                     label111: {
                        if(var0.charAt(var6) == 84) {
                           var5 = var6 + 1;
                           if(var5 != var11) {
                              if(var1 == Unit.month) {
                                 return null;
                              }

                              var18 = scanPart(var0, var5);
                              char var23 = (char)((int)var18);
                              var21 = var23;
                              var14 = var18;
                              var16 = var12;
                              if(var23 == 72) {
                                 var16 = var12 + (long)((int)(var18 >> 32) * 3600);
                                 var5 = (int)var18 >> 16;
                                 var14 = scanPart(var0, var5);
                                 var21 = (char)((int)var14);
                              }

                              var23 = var21;
                              var18 = var14;
                              var12 = var16;
                              if(var21 == 77) {
                                 var12 = var16 + (long)((int)(var14 >> 32) * 60);
                                 var5 = (int)var14 >> 16;
                                 var18 = scanPart(var0, var5);
                                 var23 = (char)((int)var18);
                              }

                              label101: {
                                 if(var23 != 83) {
                                    var3 = var5;
                                    var14 = var12;
                                    if(var23 != 46) {
                                       break label101;
                                    }
                                 }

                                 var14 = var12 + (long)((int)(var18 >> 32));
                                 var3 = (int)var18 >> 16;
                              }

                              var5 = var10;
                              var24 = var3;
                              var12 = var14;
                              if(var23 == 46) {
                                 var5 = var10;
                                 var24 = var3;
                                 var12 = var14;
                                 if(var3 + 1 < var11) {
                                    var5 = var10;
                                    var24 = var3;
                                    var12 = var14;
                                    if(Character.digit(var0.charAt(var3), 10) >= 0) {
                                       byte var25 = 0;
                                       var24 = var3;
                                       var3 = var25;
                                       var5 = var9;
                                       char var26 = var23;

                                       while(true) {
                                          if(var24 >= var11) {
                                             var6 = var3;
                                             var3 = var24;
                                             break;
                                          }

                                          int var27 = var24 + 1;
                                          char var2 = var0.charAt(var24);
                                          var24 = Character.digit(var2, 10);
                                          if(var24 < 0) {
                                             var6 = var3;
                                             var3 = var27;
                                             var26 = var2;
                                             break;
                                          }

                                          if(var3 < 9) {
                                             var6 = var5 * 10 + var24;
                                          } else {
                                             var6 = var5;
                                             if(var3 == 9) {
                                                var6 = var5;
                                                if(var24 >= 5) {
                                                   var6 = var5 + 1;
                                                }
                                             }
                                          }

                                          ++var3;
                                          var24 = var27;
                                          var26 = var2;
                                          var5 = var6;
                                       }

                                       while(var6 < 9) {
                                          var5 *= 10;
                                          ++var6;
                                       }

                                       var24 = var3;
                                       var12 = var14;
                                       if(var26 != 83) {
                                          return null;
                                       }
                                    }
                                 }
                              }
                              break label111;
                           }
                        }

                        return null;
                     }
                  }

                  if(var24 != var11) {
                     return null;
                  } else {
                     Duration var20 = new Duration();
                     var6 = var22;
                     var3 = var5;
                     var14 = var12;
                     if(var8) {
                        var6 = -var22;
                        var14 = -var12;
                        var3 = -var5;
                     }

                     var20.months = var6;
                     var20.seconds = var14;
                     var20.nanos = var3;
                     var20.unit = var1;
                     return var20;
                  }
               }
            }
         }
      } else {
         return null;
      }
   }

   public Numeric add(Object var1, int var2) {
      if(var1 instanceof Duration) {
         return add(this, (Duration)var1, var2);
      } else if(var1 instanceof DateTime && var2 == 1) {
         return DateTime.add((DateTime)var1, this, 1);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public int compare(Object var1) {
      if(var1 instanceof Duration) {
         return compare(this, (Duration)var1);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public Numeric div(Object var1) {
      if(var1 instanceof RealNum) {
         double var2 = ((RealNum)var1).doubleValue();
         if(var2 != 0.0D && !Double.isNaN(var2)) {
            return times(this, 1.0D / var2);
         } else {
            throw new ArithmeticException("divide of duration by 0 or NaN");
         }
      } else {
         return (Numeric)(var1 instanceof Duration?new DFloNum(div(this, (Duration)var1)):((Numeric)var1).divReversed(this));
      }
   }

   public boolean equals(Object var1) {
      return var1 != null && var1 instanceof Duration?equals(this, (Duration)var1):false;
   }

   public int getDays() {
      return (int)(this.seconds / 86400L);
   }

   public int getHours() {
      return (int)(this.seconds / 3600L % 24L);
   }

   public int getMinutes() {
      return (int)(this.seconds / 60L % 60L);
   }

   public int getMonths() {
      return this.months % 12;
   }

   public long getNanoSeconds() {
      return this.seconds * 1000000000L + (long)this.nanos;
   }

   public int getNanoSecondsOnly() {
      return this.nanos;
   }

   public int getSecondsOnly() {
      return (int)(this.seconds % 60L);
   }

   public long getTotalMinutes() {
      return this.seconds / 60L;
   }

   public int getTotalMonths() {
      return this.months;
   }

   public long getTotalSeconds() {
      return this.seconds;
   }

   public int getYears() {
      return this.months / 12;
   }

   public int hashCode() {
      return this.months ^ (int)this.seconds ^ this.nanos;
   }

   public boolean isExact() {
      return false;
   }

   public boolean isZero() {
      return this.months == 0 && this.seconds == 0L && this.nanos == 0;
   }

   public Numeric mul(Object var1) {
      return (Numeric)(var1 instanceof RealNum?times(this, ((RealNum)var1).doubleValue()):((Numeric)var1).mulReversed(this));
   }

   public Numeric mulReversed(Numeric var1) {
      if(!(var1 instanceof RealNum)) {
         throw new IllegalArgumentException();
      } else {
         return times(this, ((RealNum)var1).doubleValue());
      }
   }

   public Complex number() {
      throw new Error("number needs to be implemented!");
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      this.months = var1.readInt();
      this.seconds = var1.readLong();
      this.nanos = var1.readInt();
      this.unit = (Unit)var1.readObject();
   }

   public String toString() {
      StringBuffer var2 = new StringBuffer();
      int var7 = this.months;
      long var8 = this.seconds;
      int var6 = this.nanos;
      boolean var5;
      if(var7 >= 0 && var8 >= 0L && var6 >= 0) {
         var5 = false;
      } else {
         var5 = true;
      }

      int var3 = var7;
      int var4 = var6;
      long var10 = var8;
      if(var5) {
         var3 = -var7;
         var10 = -var8;
         var4 = -var6;
         var2.append('-');
      }

      var2.append('P');
      var6 = var3 / 12;
      int var14 = var3;
      if(var6 != 0) {
         var2.append(var6);
         var2.append('Y');
         var14 = var3 - var6 * 12;
      }

      if(var14 != 0) {
         var2.append(var14);
         var2.append('M');
      }

      long var12 = var10 / 86400L;
      var8 = var10;
      if(var12 != 0L) {
         var2.append(var12);
         var2.append('D');
         var8 = var10 - 86400L * var12;
      }

      if(var8 == 0L && var4 == 0) {
         if(var2.length() == 1) {
            String var1;
            if(this.unit == Unit.month) {
               var1 = "0M";
            } else {
               var1 = "T0S";
            }

            var2.append(var1);
         }
      } else {
         var2.append('T');
         var12 = var8 / 3600L;
         var10 = var8;
         if(var12 != 0L) {
            var2.append(var12);
            var2.append('H');
            var10 = var8 - 3600L * var12;
         }

         var12 = var10 / 60L;
         var8 = var10;
         if(var12 != 0L) {
            var2.append(var12);
            var2.append('M');
            var8 = var10 - 60L * var12;
         }

         if(var8 != 0L || var4 != 0) {
            var2.append(var8);
            appendNanoSeconds(var4, var2);
            var2.append('S');
         }
      }

      return var2.toString();
   }

   public Unit unit() {
      return this.unit;
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      var1.writeInt(this.months);
      var1.writeLong(this.seconds);
      var1.writeInt(this.nanos);
      var1.writeObject(this.unit);
   }
}
