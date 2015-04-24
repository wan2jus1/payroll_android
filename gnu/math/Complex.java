package gnu.math;

import gnu.math.CComplex;
import gnu.math.DComplex;
import gnu.math.DFloNum;
import gnu.math.IntNum;
import gnu.math.Numeric;
import gnu.math.Quantity;
import gnu.math.RatNum;
import gnu.math.RealNum;

public abstract class Complex extends Quantity {

   private static CComplex imMinusOne;
   private static CComplex imOne;


   public static Complex add(Complex var0, Complex var1, int var2) {
      return make(RealNum.add(var0.re(), var1.re(), var2), RealNum.add(var0.im(), var1.im(), var2));
   }

   public static int compare(Complex var0, Complex var1) {
      int var2 = var0.im().compare(var1.im());
      return var2 != 0?var2:var0.re().compare(var1.re());
   }

   public static Complex divide(Complex var0, Complex var1) {
      if(var0.isExact() && var1.isExact()) {
         RealNum var2 = var0.re();
         RealNum var6 = var0.im();
         RealNum var3 = var1.re();
         RealNum var5 = var1.im();
         RealNum var7 = RealNum.add(RealNum.times(var3, var3), RealNum.times(var5, var5), 1);
         RealNum var4 = RealNum.add(RealNum.times(var2, var3), RealNum.times(var6, var5), 1);
         var6 = RealNum.add(RealNum.times(var6, var3), RealNum.times(var2, var5), -1);
         return make(RealNum.divide(var4, var7), RealNum.divide(var6, var7));
      } else {
         return DComplex.div(var0.doubleRealValue(), var0.doubleImagValue(), var1.doubleRealValue(), var1.doubleImagValue());
      }
   }

   public static boolean equals(Complex var0, Complex var1) {
      return var0.re().equals(var1.re()) && var0.im().equals(var0.im());
   }

   public static CComplex imMinusOne() {
      if(imMinusOne == null) {
         imMinusOne = new CComplex(IntNum.zero(), IntNum.minusOne());
      }

      return imMinusOne;
   }

   public static CComplex imOne() {
      if(imOne == null) {
         imOne = new CComplex(IntNum.zero(), IntNum.one());
      }

      return imOne;
   }

   public static Complex make(double var0, double var2) {
      return (Complex)(var2 == 0.0D?new DFloNum(var0):new DComplex(var0, var2));
   }

   public static Complex make(RealNum var0, RealNum var1) {
      return (Complex)(var1.isZero()?var0:(var0.isExact() && var1.isExact()?new CComplex(var0, var1):new DComplex(var0.doubleValue(), var1.doubleValue())));
   }

   public static Complex neg(Complex var0) {
      return make(var0.re().rneg(), var0.im().rneg());
   }

   public static DComplex polar(double var0, double var2) {
      return new DComplex(Math.cos(var2) * var0, Math.sin(var2) * var0);
   }

   public static DComplex polar(RealNum var0, RealNum var1) {
      return polar(var0.doubleValue(), var1.doubleValue());
   }

   public static Complex power(Complex var0, Complex var1) {
      if(var1 instanceof IntNum) {
         return (Complex)var0.power((IntNum)var1);
      } else {
         double var2 = var0.doubleRealValue();
         double var4 = var0.doubleImagValue();
         double var6 = var1.doubleRealValue();
         double var8 = var1.doubleImagValue();
         return (Complex)(var4 == 0.0D && var8 == 0.0D && (var2 >= 0.0D || Double.isInfinite(var2) || Double.isNaN(var2))?new DFloNum(Math.pow(var2, var6)):DComplex.power(var2, var4, var6, var8));
      }
   }

   public static Complex times(Complex var0, Complex var1) {
      RealNum var2 = var0.re();
      RealNum var4 = var0.im();
      RealNum var3 = var1.re();
      RealNum var5 = var1.im();
      return make(RealNum.add(RealNum.times(var2, var3), RealNum.times(var4, var5), -1), RealNum.add(RealNum.times(var2, var5), RealNum.times(var4, var3), 1));
   }

   public Numeric abs() {
      return new DFloNum(Math.hypot(this.doubleRealValue(), this.doubleImagValue()));
   }

   public Numeric add(Object var1, int var2) {
      return (Numeric)(var1 instanceof Complex?add(this, (Complex)var1, var2):((Numeric)var1).addReversed(this, var2));
   }

   public Numeric addReversed(Numeric var1, int var2) {
      if(var1 instanceof Complex) {
         return add((Complex)var1, this, var2);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public RealNum angle() {
      return new DFloNum(Math.atan2(this.doubleImagValue(), this.doubleRealValue()));
   }

   public int compare(Object var1) {
      return !(var1 instanceof Complex)?((Numeric)var1).compareReversed(this):compare(this, (Complex)var1);
   }

   public Numeric div(Object var1) {
      return (Numeric)(var1 instanceof Complex?divide(this, (Complex)var1):((Numeric)var1).divReversed(this));
   }

   public Numeric divReversed(Numeric var1) {
      if(var1 instanceof Complex) {
         return divide((Complex)var1, this);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public double doubleImagValue() {
      return this.im().doubleValue();
   }

   public final double doubleRealValue() {
      return this.doubleValue();
   }

   public double doubleValue() {
      return this.re().doubleValue();
   }

   public boolean equals(Object var1) {
      return var1 != null && var1 instanceof Complex?equals(this, (Complex)var1):false;
   }

   public Complex exp() {
      return polar(Math.exp(this.doubleRealValue()), this.doubleImagValue());
   }

   public boolean isExact() {
      return this.re().isExact() && this.im().isExact();
   }

   public boolean isZero() {
      return this.re().isZero() && this.im().isZero();
   }

   public Complex log() {
      return DComplex.log(this.doubleRealValue(), this.doubleImagValue());
   }

   public long longValue() {
      return this.re().longValue();
   }

   public Numeric mul(Object var1) {
      return (Numeric)(var1 instanceof Complex?times(this, (Complex)var1):((Numeric)var1).mulReversed(this));
   }

   public Numeric mulReversed(Numeric var1) {
      if(var1 instanceof Complex) {
         return times((Complex)var1, this);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public Numeric neg() {
      return neg(this);
   }

   public Complex number() {
      return this;
   }

   public Complex sqrt() {
      return DComplex.sqrt(this.doubleRealValue(), this.doubleImagValue());
   }

   public Complex toExact() {
      RealNum var1 = this.re();
      RealNum var2 = this.im();
      RatNum var3 = var1.toExact();
      RatNum var4 = var2.toExact();
      return (Complex)(var3 == var1 && var4 == var2?this:new CComplex(var3, var4));
   }

   public Complex toInexact() {
      return (Complex)(this.isExact()?this:new DComplex(this.re().doubleValue(), this.im().doubleValue()));
   }

   public String toString(int var1) {
      String var3;
      if(this.im().isZero()) {
         var3 = this.re().toString(var1);
      } else {
         var3 = this.im().toString(var1) + "i";
         String var2 = var3;
         if(var3.charAt(0) != 45) {
            var2 = "+" + var3;
         }

         var3 = var2;
         if(!this.re().isZero()) {
            return this.re().toString(var1) + var2;
         }
      }

      return var3;
   }
}
