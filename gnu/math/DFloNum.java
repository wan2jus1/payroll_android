package gnu.math;

import gnu.math.IntNum;
import gnu.math.Numeric;
import gnu.math.RatNum;
import gnu.math.RealNum;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class DFloNum extends RealNum implements Externalizable {

   private static final DFloNum one = new DFloNum(1.0D);
   double value;


   public DFloNum() {
   }

   public DFloNum(double var1) {
      this.value = var1;
   }

   public DFloNum(String var1) throws NumberFormatException {
      this.value = Double.valueOf(var1).doubleValue();
      if(this.value == 0.0D && var1.charAt(0) == 45) {
         this.value = 0.0D;
      }

   }

   public static DFloNum asDFloNumOrNull(Object var0) {
      return var0 instanceof DFloNum?(DFloNum)var0:(!(var0 instanceof RealNum) && !(var0 instanceof Number)?null:new DFloNum(((Number)var0).doubleValue()));
   }

   public static int compare(double var0, double var2) {
      return var0 > var2?1:(var0 < var2?-1:(var0 == var2?0:-2));
   }

   public static int compare(IntNum var0, IntNum var1, double var2) {
      byte var7 = 1;
      int var6;
      if(Double.isNaN(var2)) {
         var6 = -2;
      } else {
         int var13;
         if(!Double.isInfinite(var2)) {
            long var8 = Double.doubleToLongBits(var2);
            boolean var12;
            if(var8 < 0L) {
               var12 = true;
            } else {
               var12 = false;
            }

            var13 = (int)(var8 >> 52) & 2047;
            var8 &= 4503599627370495L;
            if(var13 == 0) {
               var8 <<= 1;
            } else {
               var8 |= 4503599627370496L;
            }

            long var10 = var8;
            if(var12) {
               var10 = -var8;
            }

            IntNum var4 = IntNum.make(var10);
            IntNum var5;
            if(var13 >= 1075) {
               var5 = IntNum.shift(var4, var13 - 1075);
               var4 = var0;
               var0 = var5;
            } else {
               var5 = IntNum.shift(var0, 1075 - var13);
               var0 = var4;
               var4 = var5;
            }

            return IntNum.compare(var4, IntNum.times(var0, var1));
         }

         if(var2 >= 0.0D) {
            var7 = -1;
         }

         var6 = var7;
         if(var1.isZero()) {
            if(var0.isZero()) {
               return -2;
            }

            var13 = var7 >> 1;
            var6 = var13;
            if(!var0.isNegative()) {
               return ~var13;
            }
         }
      }

      return var6;
   }

   public static DFloNum make(double var0) {
      return new DFloNum(var0);
   }

   public static final DFloNum one() {
      return one;
   }

   public static RatNum toExact(double var0) {
      byte var3 = 1;
      if(Double.isInfinite(var0)) {
         if(var0 < 0.0D) {
            var3 = -1;
         }

         return RatNum.infinity(var3);
      } else if(Double.isNaN(var0)) {
         throw new ArithmeticException("cannot convert NaN to exact rational");
      } else {
         long var5 = Double.doubleToLongBits(var0);
         boolean var9;
         if(var5 < 0L) {
            var9 = true;
         } else {
            var9 = false;
         }

         int var4 = (int)(var5 >> 52) & 2047;
         var5 &= 4503599627370495L;
         if(var4 == 0) {
            var5 <<= 1;
         } else {
            var5 |= 4503599627370496L;
         }

         long var7 = var5;
         if(var9) {
            var7 = -var5;
         }

         IntNum var2 = IntNum.make(var7);
         return (RatNum)(var4 >= 1075?IntNum.shift(var2, var4 - 1075):RatNum.make(var2, IntNum.shift(IntNum.one(), 1075 - var4)));
      }
   }

   public Numeric add(Object var1, int var2) {
      if(var1 instanceof RealNum) {
         return new DFloNum(this.value + (double)var2 * ((RealNum)var1).doubleValue());
      } else if(!(var1 instanceof Numeric)) {
         throw new IllegalArgumentException();
      } else {
         return ((Numeric)var1).addReversed(this, var2);
      }
   }

   public Numeric addReversed(Numeric var1, int var2) {
      if(var1 instanceof RealNum) {
         return new DFloNum(((RealNum)var1).doubleValue() + (double)var2 * this.value);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public int compare(Object var1) {
      if(var1 instanceof RatNum) {
         RatNum var3 = (RatNum)var1;
         int var2 = compare(var3.numerator(), var3.denominator(), this.value);
         return var2 < -1?var2:-var2;
      } else {
         return compare(this.value, ((RealNum)var1).doubleValue());
      }
   }

   public int compareReversed(Numeric var1) {
      if(var1 instanceof RatNum) {
         RatNum var2 = (RatNum)var1;
         return compare(var2.numerator(), var2.denominator(), this.value);
      } else {
         return compare(((RealNum)var1).doubleValue(), this.value);
      }
   }

   public Numeric div(Object var1) {
      if(var1 instanceof RealNum) {
         return new DFloNum(this.value / ((RealNum)var1).doubleValue());
      } else if(!(var1 instanceof Numeric)) {
         throw new IllegalArgumentException();
      } else {
         return ((Numeric)var1).divReversed(this);
      }
   }

   public Numeric divReversed(Numeric var1) {
      if(var1 instanceof RealNum) {
         return new DFloNum(((RealNum)var1).doubleValue() / this.value);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public final double doubleValue() {
      return this.value;
   }

   public boolean equals(Object var1) {
      return var1 != null && var1 instanceof DFloNum && Double.doubleToLongBits(((DFloNum)var1).value) == Double.doubleToLongBits(this.value);
   }

   public int hashCode() {
      return (int)this.value;
   }

   public boolean isExact() {
      return false;
   }

   public boolean isNegative() {
      return this.value < 0.0D;
   }

   public boolean isZero() {
      return this.value == 0.0D;
   }

   public long longValue() {
      return (long)this.value;
   }

   public Numeric mul(Object var1) {
      if(var1 instanceof RealNum) {
         return new DFloNum(this.value * ((RealNum)var1).doubleValue());
      } else if(!(var1 instanceof Numeric)) {
         throw new IllegalArgumentException();
      } else {
         return ((Numeric)var1).mulReversed(this);
      }
   }

   public Numeric mulReversed(Numeric var1) {
      if(var1 instanceof RealNum) {
         return new DFloNum(((RealNum)var1).doubleValue() * this.value);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public Numeric neg() {
      return new DFloNum(-this.value);
   }

   public Numeric power(IntNum var1) {
      return new DFloNum(Math.pow(this.doubleValue(), var1.doubleValue()));
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      this.value = var1.readDouble();
   }

   public int sign() {
      return this.value > 0.0D?1:(this.value < 0.0D?-1:(this.value == 0.0D?0:-2));
   }

   public String toString() {
      return this.value == Double.POSITIVE_INFINITY?"+inf.0":(this.value == Double.NEGATIVE_INFINITY?"-inf.0":(Double.isNaN(this.value)?"+nan.0":Double.toString(this.value)));
   }

   public String toString(int var1) {
      return var1 == 10?this.toString():"#d" + this.toString();
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      var1.writeDouble(this.value);
   }
}
