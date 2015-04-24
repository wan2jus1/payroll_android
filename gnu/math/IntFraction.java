package gnu.math;

import gnu.math.IntNum;
import gnu.math.Numeric;
import gnu.math.RatNum;
import gnu.math.RealNum;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class IntFraction extends RatNum implements Externalizable {

   IntNum den;
   IntNum num;


   IntFraction() {
   }

   public IntFraction(IntNum var1, IntNum var2) {
      this.num = var1;
      this.den = var2;
   }

   public static IntFraction neg(IntFraction var0) {
      return new IntFraction(IntNum.neg(var0.numerator()), var0.denominator());
   }

   public Numeric add(Object var1, int var2) {
      if(var1 instanceof RatNum) {
         return RatNum.add(this, (RatNum)var1, var2);
      } else if(!(var1 instanceof Numeric)) {
         throw new IllegalArgumentException();
      } else {
         return ((Numeric)var1).addReversed(this, var2);
      }
   }

   public Numeric addReversed(Numeric var1, int var2) {
      if(!(var1 instanceof RatNum)) {
         throw new IllegalArgumentException();
      } else {
         return RatNum.add((RatNum)var1, this, var2);
      }
   }

   public final int compare(Object var1) {
      return var1 instanceof RatNum?RatNum.compare(this, (RatNum)var1):((RealNum)var1).compareReversed(this);
   }

   public int compareReversed(Numeric var1) {
      return RatNum.compare((RatNum)var1, this);
   }

   public final IntNum denominator() {
      return this.den;
   }

   public Numeric div(Object var1) {
      if(var1 instanceof RatNum) {
         return RatNum.divide(this, (RatNum)var1);
      } else if(!(var1 instanceof Numeric)) {
         throw new IllegalArgumentException();
      } else {
         return ((Numeric)var1).divReversed(this);
      }
   }

   public Numeric divReversed(Numeric var1) {
      if(!(var1 instanceof RatNum)) {
         throw new IllegalArgumentException();
      } else {
         return RatNum.divide((RatNum)var1, this);
      }
   }

   public double doubleValue() {
      boolean var8 = this.num.isNegative();
      if(this.den.isZero()) {
         return var8?Double.NEGATIVE_INFINITY:(this.num.isZero()?Double.NaN:Double.POSITIVE_INFINITY);
      } else {
         IntNum var2 = this.num;
         IntNum var1 = var2;
         if(var8) {
            var1 = IntNum.neg(var2);
         }

         int var5 = var1.intLength();
         int var6 = this.den.intLength();
         int var4 = 0;
         var2 = var1;
         if(var5 < var6 + 54) {
            var4 = var6 + 54 - var5;
            var2 = IntNum.shift(var1, var4);
            var4 = -var4;
         }

         IntNum var3 = new IntNum();
         var1 = new IntNum();
         IntNum.divide(var2, this.den, var3, var1, 3);
         var2 = var3.canonicalize();
         boolean var7;
         if(!var1.canonicalize().isZero()) {
            var7 = true;
         } else {
            var7 = false;
         }

         return var2.roundToDouble(var4, var8, var7);
      }
   }

   public final boolean isNegative() {
      return this.num.isNegative();
   }

   public long longValue() {
      return this.toExactInt(4).longValue();
   }

   public Numeric mul(Object var1) {
      if(var1 instanceof RatNum) {
         return RatNum.times(this, (RatNum)var1);
      } else if(!(var1 instanceof Numeric)) {
         throw new IllegalArgumentException();
      } else {
         return ((Numeric)var1).mulReversed(this);
      }
   }

   public Numeric mulReversed(Numeric var1) {
      if(!(var1 instanceof RatNum)) {
         throw new IllegalArgumentException();
      } else {
         return RatNum.times((RatNum)var1, this);
      }
   }

   public Numeric neg() {
      return neg(this);
   }

   public final IntNum numerator() {
      return this.num;
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      this.num = (IntNum)var1.readObject();
      this.den = (IntNum)var1.readObject();
   }

   public final int sign() {
      return this.num.sign();
   }

   public String toString(int var1) {
      return this.num.toString(var1) + '/' + this.den.toString(var1);
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      var1.writeObject(this.num);
      var1.writeObject(this.den);
   }
}
