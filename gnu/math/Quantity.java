package gnu.math;

import gnu.math.CQuantity;
import gnu.math.Complex;
import gnu.math.DFloNum;
import gnu.math.DQuantity;
import gnu.math.Dimensions;
import gnu.math.Numeric;
import gnu.math.RealNum;
import gnu.math.Unit;

public abstract class Quantity extends Numeric {

   public static Quantity add(Quantity var0, Quantity var1, int var2) {
      if(var0.unit() == var1.unit()) {
         return make(Complex.add(var0.number(), var1.number(), var2), var0.unit());
      } else if(var0.dimensions() != var1.dimensions()) {
         throw new ArithmeticException("units mis-match");
      } else {
         double var3 = var0.unit().doubleValue();
         return make((var0.reValue() + (double)var2 * var1.reValue()) / var3, (var0.imValue() + (double)var2 * var1.imValue()) / var3, var0.unit());
      }
   }

   public static int compare(Quantity var0, Quantity var1) {
      return var0.unit() == var1.unit()?Complex.compare(var0.number(), var1.number()):(var0.dimensions() == var1.dimensions() && var0.imValue() == var1.imValue()?DFloNum.compare(var0.reValue(), var1.reValue()):-3);
   }

   public static Quantity divide(Quantity var0, Quantity var1) {
      Unit var2 = Unit.divide(var0.unit(), var1.unit());
      return make((Complex)var0.number().div(var1.number()), var2);
   }

   public static Quantity make(double var0, double var2, Unit var4) {
      return (Quantity)(var4 == Unit.Empty?Complex.make(var0, var2):(var2 == 0.0D?new DQuantity(var0, var4):new CQuantity(new DFloNum(var0), new DFloNum(var2), var4)));
   }

   public static Quantity make(Complex var0, Unit var1) {
      return (Quantity)(var1 == Unit.Empty?var0:(var0 instanceof DFloNum?new DQuantity(var0.doubleValue(), var1):new CQuantity(var0, var1)));
   }

   public static Quantity make(RealNum var0, RealNum var1, Unit var2) {
      return (Quantity)(var2 == Unit.Empty?Complex.make(var0, var1):(var1.isZero() && (!var0.isExact() || !var1.isExact())?new DQuantity(var0.doubleValue(), var2):new CQuantity(var0, var1, var2)));
   }

   public static Quantity times(Quantity var0, Quantity var1) {
      Unit var2 = Unit.times(var0.unit(), var1.unit());
      return make((Complex)var0.number().mul(var1.number()), var2);
   }

   public Numeric abs() {
      return make((Complex)this.number().abs(), this.unit());
   }

   public Numeric add(Object var1, int var2) {
      return (Numeric)(var1 instanceof Quantity?add(this, (Quantity)var1, var2):((Numeric)var1).addReversed(this, var2));
   }

   public Numeric addReversed(Numeric var1, int var2) {
      if(var1 instanceof Quantity) {
         return add((Quantity)var1, this, var2);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public int compare(Object var1) {
      return !(var1 instanceof Quantity)?((Numeric)var1).compareReversed(this):compare(this, (Quantity)var1);
   }

   public int compareReversed(Numeric var1) {
      if(var1 instanceof Quantity) {
         return compare((Quantity)var1, this);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public Dimensions dimensions() {
      return this.unit().dimensions();
   }

   public Numeric div(Object var1) {
      return (Numeric)(var1 instanceof Quantity?divide(this, (Quantity)var1):((Numeric)var1).divReversed(this));
   }

   public Numeric divReversed(Numeric var1) {
      if(var1 instanceof Quantity) {
         return divide((Quantity)var1, this);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public double doubleImagValue() {
      return this.unit().doubleValue() * this.im().doubleValue();
   }

   public double doubleValue() {
      return this.unit().doubleValue() * this.re().doubleValue();
   }

   public RealNum im() {
      return this.number().im();
   }

   public final double imValue() {
      return this.doubleImagValue();
   }

   public Numeric mul(Object var1) {
      return (Numeric)(var1 instanceof Quantity?times(this, (Quantity)var1):((Numeric)var1).mulReversed(this));
   }

   public Numeric mulReversed(Numeric var1) {
      if(var1 instanceof Quantity) {
         return times((Quantity)var1, this);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public Numeric neg() {
      return make((Complex)this.number().neg(), this.unit());
   }

   public abstract Complex number();

   public RealNum re() {
      return this.number().re();
   }

   public final double reValue() {
      return this.doubleValue();
   }

   public String toString(int var1) {
      String var2 = this.number().toString(var1);
      return this.unit() == Unit.Empty?var2:var2 + this.unit().toString();
   }

   public Unit unit() {
      return Unit.Empty;
   }
}
