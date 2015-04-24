package gnu.math;

import gnu.math.DFloNum;
import gnu.math.IntNum;
import gnu.math.RatNum;
import java.math.BigDecimal;
import java.math.BigInteger;

public abstract class Numeric extends Number {

   public static final int CEILING = 2;
   public static final int FLOOR = 1;
   public static final int NONNEG_MOD = 5;
   public static final int ROUND = 4;
   public static final int TRUNCATE = 3;


   public static Numeric asNumericOrNull(Object var0) {
      return (Numeric)(var0 instanceof Numeric?(Numeric)var0:(!(var0 instanceof BigInteger) && !(var0 instanceof Long) && !(var0 instanceof Short) && !(var0 instanceof Byte) && !(var0 instanceof Integer)?(var0 instanceof BigDecimal?RatNum.asRatNumOrNull(var0):(!(var0 instanceof Float) && !(var0 instanceof Double)?null:new DFloNum(((Number)var0).doubleValue()))):IntNum.asIntNumOrNull(var0)));
   }

   public abstract Numeric abs();

   public final Numeric add(Object var1) {
      return this.add(var1, 1);
   }

   public abstract Numeric add(Object var1, int var2);

   public Numeric addReversed(Numeric var1, int var2) {
      throw new IllegalArgumentException();
   }

   public int compare(Object var1) {
      return -3;
   }

   public int compareReversed(Numeric var1) {
      throw new IllegalArgumentException();
   }

   public abstract Numeric div(Object var1);

   public Numeric divReversed(Numeric var1) {
      throw new IllegalArgumentException();
   }

   public Numeric div_inv() {
      return IntNum.one().div(this);
   }

   public boolean equals(Object var1) {
      return var1 != null && var1 instanceof Numeric && this.compare(var1) == 0;
   }

   public float floatValue() {
      return (float)this.doubleValue();
   }

   public boolean geq(Object var1) {
      return this.compare(var1) >= 0;
   }

   public boolean grt(Object var1) {
      return this.compare(var1) > 0;
   }

   public int intValue() {
      return (int)this.longValue();
   }

   public abstract boolean isExact();

   public abstract boolean isZero();

   public long longValue() {
      return (long)this.doubleValue();
   }

   public abstract Numeric mul(Object var1);

   public Numeric mulReversed(Numeric var1) {
      throw new IllegalArgumentException();
   }

   public Numeric mul_ident() {
      return IntNum.one();
   }

   public abstract Numeric neg();

   public Numeric power(IntNum var1) {
      Numeric var2;
      if(var1.isNegative()) {
         var2 = this.power(IntNum.neg(var1)).div_inv();
      } else {
         var2 = this;
         Numeric var4 = null;
         IntNum var3 = var1;

         while(true) {
            Numeric var5 = var4;
            if(var3.isOdd()) {
               if(var4 == null) {
                  var5 = var2;
               } else {
                  var5 = var4.mul(var2);
               }
            }

            var3 = IntNum.shift(var3, -1);
            if(var3.isZero()) {
               var2 = var5;
               if(var5 == null) {
                  return this.mul_ident();
               }
               break;
            }

            var2 = var2.mul(var2);
            var4 = var5;
         }
      }

      return var2;
   }

   public final Numeric sub(Object var1) {
      return this.add(var1, -1);
   }

   public Numeric toExact() {
      return this;
   }

   public Numeric toInexact() {
      return this;
   }

   public String toString() {
      return this.toString(10);
   }

   public abstract String toString(int var1);
}
