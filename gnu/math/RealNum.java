package gnu.math;

import gnu.math.Complex;
import gnu.math.DComplex;
import gnu.math.DFloNum;
import gnu.math.IntNum;
import gnu.math.Numeric;
import gnu.math.RatNum;
import java.math.BigDecimal;

public abstract class RealNum extends Complex implements Comparable {

   public static RealNum add(RealNum var0, RealNum var1, int var2) {
      return (RealNum)((RealNum)var0.add(var1, var2));
   }

   public static RealNum asRealNumOrNull(Object var0) {
      return (RealNum)(var0 instanceof RealNum?(RealNum)var0:(!(var0 instanceof Float) && !(var0 instanceof Double)?RatNum.asRatNumOrNull(var0):new DFloNum(((Number)var0).doubleValue())));
   }

   public static RealNum divide(RealNum var0, RealNum var1) {
      return (RealNum)((RealNum)var0.div(var1));
   }

   public static RealNum times(RealNum var0, RealNum var1) {
      return (RealNum)((RealNum)var0.mul(var1));
   }

   public static IntNum toExactInt(double var0) {
      if(!Double.isInfinite(var0) && !Double.isNaN(var0)) {
         long var4 = Double.doubleToLongBits(var0);
         boolean var2;
         if(var4 < 0L) {
            var2 = true;
         } else {
            var2 = false;
         }

         int var3 = (int)(var4 >> 52) & 2047;
         var4 &= 4503599627370495L;
         if(var3 == 0) {
            var4 <<= 1;
         } else {
            var4 |= 4503599627370496L;
         }

         if(var3 <= 1075) {
            var3 = 1075 - var3;
            if(var3 > 53) {
               return IntNum.zero();
            } else {
               var4 >>= var3;
               if(var2) {
                  var4 = -var4;
               }

               return IntNum.make(var4);
            }
         } else {
            if(var2) {
               var4 = -var4;
            }

            return IntNum.shift(IntNum.make(var4), var3 - 1075);
         }
      } else {
         throw new ArithmeticException("cannot convert " + var0 + " to exact integer");
      }
   }

   public static IntNum toExactInt(double var0, int var2) {
      return toExactInt(toInt(var0, var2));
   }

   public static double toInt(double var0, int var2) {
      switch(var2) {
      case 1:
         return Math.floor(var0);
      case 2:
         return Math.ceil(var0);
      case 3:
         if(var0 < 0.0D) {
            return Math.ceil(var0);
         }

         return Math.floor(var0);
      case 4:
         return Math.rint(var0);
      default:
         return var0;
      }
   }

   public static IntNum toScaledInt(double var0, int var2) {
      return toScaledInt(DFloNum.toExact(var0), var2);
   }

   public static IntNum toScaledInt(RatNum var0, int var1) {
      RatNum var2 = var0;
      if(var1 != 0) {
         IntNum var6 = IntNum.ten();
         int var4;
         if(var1 < 0) {
            var4 = -var1;
         } else {
            var4 = var1;
         }

         IntNum var3 = IntNum.power(var6, var4);
         var6 = var0.numerator();
         IntNum var5 = var0.denominator();
         if(var1 >= 0) {
            var6 = IntNum.times(var6, var3);
         } else {
            var5 = IntNum.times(var5, var3);
         }

         var2 = RatNum.make(var6, var5);
      }

      return var2.toExactInt(4);
   }

   public static String toStringDecimal(String var0) {
      int var6 = var0.indexOf(69);
      if(var6 >= 0) {
         int var7 = var0.length();
         char var3 = var0.charAt(var7 - 1);
         if(var3 != 121 && var3 != 78) {
            StringBuffer var2 = new StringBuffer(var7 + 10);
            boolean var4;
            if(var0.charAt(0) == 45) {
               var4 = true;
            } else {
               var4 = false;
            }

            if(var0.charAt(var6 + 1) != 45) {
               throw new Error("not implemented: toStringDecimal given non-negative exponent: " + var0);
            }

            int var8 = 0;

            for(int var5 = var6 + 2; var5 < var7; ++var5) {
               var8 = var8 * 10 + (var0.charAt(var5) - 48);
            }

            if(var4) {
               var2.append('-');
            }

            var2.append("0.");

            while(true) {
               --var8;
               if(var8 <= 0) {
                  var8 = 0;

                  while(true) {
                     int var10 = var8 + 1;
                     char var1 = var0.charAt(var8);
                     if(var1 == 69) {
                        return var2.toString();
                     }

                     boolean var9;
                     if(var1 != 45) {
                        var9 = true;
                     } else {
                        var9 = false;
                     }

                     boolean var11;
                     if(var1 != 46) {
                        var11 = true;
                     } else {
                        var11 = false;
                     }

                     if(var11 & var9 && (var1 != 48 || var10 < var6)) {
                        var2.append(var1);
                        var8 = var10;
                     } else {
                        var8 = var10;
                     }
                  }
               }

               var2.append('0');
            }
         }
      }

      return var0;
   }

   public static int toStringScientific(String var0, StringBuffer var1) {
      boolean var4;
      if(var0.charAt(0) == 45) {
         var4 = true;
      } else {
         var4 = false;
      }

      if(var4) {
         var1.append('-');
      }

      byte var3;
      if(var4) {
         var3 = 1;
      } else {
         var3 = 0;
      }

      int var5;
      int var8;
      int var9;
      label90: {
         int var7 = var0.length();
         char var2;
         if(var0.charAt(var3) == 48) {
            var5 = var3;

            label86:
            while(true) {
               if(var5 == var7) {
                  var1.append("0");
                  var8 = 0;
                  break label90;
               }

               var9 = var5 + 1;
               var2 = var0.charAt(var5);
               if(var2 >= 48 && var2 <= 57 && (var2 != 48 || var9 == var7)) {
                  var1.append(var2);
                  var1.append('.');
                  if(var2 == 48) {
                     var8 = 0;
                  } else {
                     var8 = var3 - var9 + 2;
                  }

                  if(var9 == var7) {
                     var1.append('0');
                     break label90;
                  }

                  var5 = var9;

                  while(true) {
                     var9 = var8;
                     if(var5 >= var7) {
                        break label86;
                     }

                     var1.append(var0.charAt(var5));
                     ++var5;
                  }
               }

               var5 = var9;
            }
         } else {
            byte var10;
            if(var4) {
               var10 = 2;
            } else {
               var10 = 1;
            }

            var5 = var7 - var10 - var7 + var0.indexOf(46);
            var9 = var3 + 1;
            var1.append(var0.charAt(var3));
            var1.append('.');
            var8 = var9;

            while(true) {
               var9 = var5;
               if(var8 >= var7) {
                  break;
               }

               var2 = var0.charAt(var8);
               if(var2 != 46) {
                  var1.append(var2);
               }

               ++var8;
            }
         }

         var8 = var9;
      }

      var5 = var1.length();
      var9 = -1;

      while(true) {
         --var5;
         char var6 = var1.charAt(var5);
         if(var6 != 48) {
            if(var6 == 46) {
               var9 = var5 + 2;
            }

            if(var9 >= 0) {
               var1.setLength(var9);
            }

            return var8;
         }

         var9 = var5;
      }
   }

   public static String toStringScientific(double var0) {
      return toStringScientific(Double.toString(var0));
   }

   public static String toStringScientific(float var0) {
      return toStringScientific(Float.toString(var0));
   }

   public static String toStringScientific(String var0) {
      if(var0.indexOf(69) < 0) {
         int var2 = var0.length();
         char var3 = var0.charAt(var2 - 1);
         if(var3 != 121 && var3 != 78) {
            StringBuffer var1 = new StringBuffer(var2 + 10);
            var2 = toStringScientific(var0, var1);
            var1.append('E');
            var1.append(var2);
            return var1.toString();
         }
      }

      return var0;
   }

   public Numeric abs() {
      Object var1 = this;
      if(this.isNegative()) {
         var1 = this.neg();
      }

      return (Numeric)var1;
   }

   public abstract Numeric add(Object var1, int var2);

   public BigDecimal asBigDecimal() {
      return new BigDecimal(this.doubleValue());
   }

   public int compareTo(Object var1) {
      return this.compare(var1);
   }

   public abstract Numeric div(Object var1);

   public Complex exp() {
      return new DFloNum(Math.exp(this.doubleValue()));
   }

   public final RealNum im() {
      return IntNum.zero();
   }

   public abstract boolean isNegative();

   public boolean isZero() {
      return this.sign() == 0;
   }

   public Complex log() {
      double var1 = this.doubleValue();
      return (Complex)(var1 < 0.0D?DComplex.log(var1, 0.0D):new DFloNum(Math.log(var1)));
   }

   public RealNum max(RealNum var1) {
      boolean var3;
      if(this.isExact() && var1.isExact()) {
         var3 = true;
      } else {
         var3 = false;
      }

      if(this.grt(var1)) {
         var1 = this;
      }

      Object var2 = var1;
      if(!var3) {
         var2 = var1;
         if(var1.isExact()) {
            var2 = new DFloNum(var1.doubleValue());
         }
      }

      return (RealNum)var2;
   }

   public RealNum min(RealNum var1) {
      boolean var3;
      if(this.isExact() && var1.isExact()) {
         var3 = true;
      } else {
         var3 = false;
      }

      if(!this.grt(var1)) {
         var1 = this;
      }

      Object var2 = var1;
      if(!var3) {
         var2 = var1;
         if(var1.isExact()) {
            var2 = new DFloNum(var1.doubleValue());
         }
      }

      return (RealNum)var2;
   }

   public abstract Numeric mul(Object var1);

   public final RealNum re() {
      return this;
   }

   public RealNum rneg() {
      return (RealNum)this.neg();
   }

   public abstract int sign();

   public final Complex sin() {
      return new DFloNum(Math.sin(this.doubleValue()));
   }

   public final Complex sqrt() {
      double var1 = this.doubleValue();
      return (Complex)(var1 >= 0.0D?new DFloNum(Math.sqrt(var1)):DComplex.sqrt(var1, 0.0D));
   }

   public RatNum toExact() {
      return DFloNum.toExact(this.doubleValue());
   }

   public IntNum toExactInt(int var1) {
      return toExactInt(this.doubleValue(), var1);
   }

   public RealNum toInexact() {
      Object var1 = this;
      if(this.isExact()) {
         var1 = new DFloNum(this.doubleValue());
      }

      return (RealNum)var1;
   }

   public RealNum toInt(int var1) {
      return new DFloNum(toInt(this.doubleValue(), var1));
   }

   public IntNum toScaledInt(int var1) {
      return toScaledInt(this.toExact(), var1);
   }
}
