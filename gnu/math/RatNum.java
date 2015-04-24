package gnu.math;

import gnu.math.DFloNum;
import gnu.math.IntFraction;
import gnu.math.IntNum;
import gnu.math.Numeric;
import gnu.math.RealNum;
import java.math.BigDecimal;

public abstract class RatNum extends RealNum {

   public static final IntNum ten_exp_9 = IntNum.make(1000000000);


   public static RatNum add(RatNum var0, RatNum var1, int var2) {
      IntNum var3 = var0.numerator();
      IntNum var5 = var0.denominator();
      IntNum var4 = var1.numerator();
      IntNum var6 = var1.denominator();
      return IntNum.equals(var5, var6)?make(IntNum.add(var3, var4, var2), var5):make(IntNum.add(IntNum.times(var6, var3), IntNum.times(var4, var5), var2), IntNum.times(var5, var6));
   }

   public static RatNum asRatNumOrNull(Object var0) {
      return (RatNum)(var0 instanceof RatNum?(RatNum)var0:(var0 instanceof BigDecimal?valueOf((BigDecimal)var0):IntNum.asIntNumOrNull(var0)));
   }

   public static int compare(RatNum var0, RatNum var1) {
      return IntNum.compare(IntNum.times(var0.numerator(), var1.denominator()), IntNum.times(var1.numerator(), var0.denominator()));
   }

   public static RatNum divide(RatNum var0, RatNum var1) {
      return make(IntNum.times(var0.numerator(), var1.denominator()), IntNum.times(var0.denominator(), var1.numerator()));
   }

   public static boolean equals(RatNum var0, RatNum var1) {
      return IntNum.equals(var0.numerator(), var1.numerator()) && IntNum.equals(var0.denominator(), var1.denominator());
   }

   public static RatNum infinity(int var0) {
      return new IntFraction(IntNum.make(var0), IntNum.zero());
   }

   public static RatNum make(IntNum var0, IntNum var1) {
      IntNum var3 = IntNum.gcd(var0, var1);
      IntNum var2 = var3;
      if(var1.isNegative()) {
         var2 = IntNum.neg(var3);
      }

      IntNum var4 = var0;
      var3 = var1;
      if(!var2.isOne()) {
         var4 = IntNum.quotient(var0, var2);
         var3 = IntNum.quotient(var1, var2);
      }

      return (RatNum)(var3.isOne()?var4:new IntFraction(var4, var3));
   }

   public static RatNum neg(RatNum var0) {
      IntNum var1 = var0.numerator();
      IntNum var2 = var0.denominator();
      return make(IntNum.neg(var1), var2);
   }

   public static RealNum rationalize(RealNum var0, RealNum var1) {
      RealNum var2;
      if(var0.grt(var1)) {
         var2 = simplest_rational2(var1, var0);
      } else {
         var2 = var0;
         if(var1.grt(var0)) {
            if(var0.sign() > 0) {
               return simplest_rational2(var0, var1);
            }

            if(var1.isNegative()) {
               return (RealNum)simplest_rational2((RealNum)var1.neg(), (RealNum)var0.neg()).neg();
            }

            return IntNum.zero();
         }
      }

      return var2;
   }

   private static RealNum simplest_rational2(RealNum var0, RealNum var1) {
      RealNum var2 = var0.toInt(1);
      RealNum var3 = var1.toInt(1);
      if(!var0.grt(var2)) {
         return var2;
      } else if(var2.equals(var3)) {
         var1 = (RealNum)IntNum.one().div(var1.sub(var3));
         var0 = (RealNum)IntNum.one().div(var0.sub(var2));
         return (RealNum)var2.add(IntNum.one().div(simplest_rational2(var1, var0)), 1);
      } else {
         return (RealNum)var2.add(IntNum.one(), 1);
      }
   }

   public static RatNum times(RatNum var0, RatNum var1) {
      return make(IntNum.times(var0.numerator(), var1.numerator()), IntNum.times(var0.denominator(), var1.denominator()));
   }

   public static RatNum valueOf(BigDecimal var0) {
      IntNum var1 = IntNum.valueOf(var0.unscaledValue().toString(), 10);
      int var3 = var0.scale();
      Object var4 = var1;

      while(true) {
         int var2 = var3;
         Object var6 = var4;
         if(var3 < 9) {
            while(var2 <= -9) {
               var6 = times((RatNum)var6, ten_exp_9);
               var2 += 9;
            }

            if(var2 > 0) {
               var3 = var2;
            } else {
               var3 = -var2;
            }

            IntNum var5;
            switch(var3) {
            case 1:
               var5 = IntNum.make(10);
               break;
            case 2:
               var5 = IntNum.make(100);
               break;
            case 3:
               var5 = IntNum.make(1000);
               break;
            case 4:
               var5 = IntNum.make(10000);
               break;
            case 5:
               var5 = IntNum.make(100000);
               break;
            case 6:
               var5 = IntNum.make(1000000);
               break;
            case 7:
               var5 = IntNum.make(10000000);
               break;
            case 8:
               var5 = IntNum.make(100000000);
               break;
            default:
               return (RatNum)var6;
            }

            return var2 > 0?divide((RatNum)var6, var5):times((RatNum)var6, var5);
         }

         var4 = divide((RatNum)var4, ten_exp_9);
         var3 -= 9;
      }
   }

   public abstract IntNum denominator();

   public boolean equals(Object var1) {
      return var1 != null && var1 instanceof RatNum?equals(this, (RatNum)var1):false;
   }

   public boolean isExact() {
      return true;
   }

   public boolean isZero() {
      return this.numerator().isZero();
   }

   public abstract IntNum numerator();

   public Numeric power(IntNum var1) {
      boolean var7;
      if(var1.isNegative()) {
         var7 = true;
         var1 = IntNum.neg(var1);
      } else {
         var7 = false;
      }

      if(var1.words == null) {
         IntNum var6 = IntNum.power(this.numerator(), var1.ival);
         var1 = IntNum.power(this.denominator(), var1.ival);
         return var7?make(var1, var6):make(var6, var1);
      } else {
         double var2 = this.doubleValue();
         boolean var8;
         if(var2 < 0.0D && var1.isOdd()) {
            var8 = true;
         } else {
            var8 = false;
         }

         double var4 = Math.pow(var2, var1.doubleValue());
         var2 = var4;
         if(var7) {
            var2 = 1.0D / var4;
         }

         var4 = var2;
         if(var8) {
            var4 = -var2;
         }

         return new DFloNum(var4);
      }
   }

   public final RatNum rneg() {
      return neg(this);
   }

   public final RatNum toExact() {
      return this;
   }

   public IntNum toExactInt(int var1) {
      return IntNum.quotient(this.numerator(), this.denominator(), var1);
   }

   public RealNum toInt(int var1) {
      return IntNum.quotient(this.numerator(), this.denominator(), var1);
   }
}
