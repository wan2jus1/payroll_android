package gnu.math;

import gnu.math.Complex;
import gnu.math.DFloNum;
import gnu.math.Dimensions;
import gnu.math.Numeric;
import gnu.math.Quantity;
import gnu.math.RealNum;
import gnu.math.Unit;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class DQuantity extends Quantity implements Externalizable {

   double factor;
   Unit unt;


   public DQuantity(double var1, Unit var3) {
      this.factor = var1;
      this.unt = var3;
   }

   public static DQuantity add(DQuantity var0, DQuantity var1, double var2) {
      if(var0.dimensions() != var1.dimensions()) {
         throw new ArithmeticException("units mis-match");
      } else {
         double var4 = var1.unit().factor / var0.unit().factor;
         return new DQuantity(var0.factor + var2 * var4 * var1.factor, var0.unit());
      }
   }

   public static DQuantity divide(DQuantity var0, DQuantity var1) {
      return new DQuantity(var0.factor / var1.factor, Unit.divide(var0.unit(), var1.unit()));
   }

   public static DQuantity times(DQuantity var0, DQuantity var1) {
      return new DQuantity(var0.factor * var1.factor, Unit.times(var0.unit(), var1.unit()));
   }

   public Numeric add(Object var1, int var2) {
      if(var1 instanceof DQuantity) {
         return add(this, (DQuantity)var1, (double)var2);
      } else if(this.dimensions() == Dimensions.Empty && var1 instanceof RealNum) {
         return new DQuantity(this.factor + (double)var2 * ((RealNum)var1).doubleValue(), this.unit());
      } else if(!(var1 instanceof Numeric)) {
         throw new IllegalArgumentException();
      } else {
         return ((Numeric)var1).addReversed(this, var2);
      }
   }

   public Numeric addReversed(Numeric var1, int var2) {
      if(this.dimensions() == Dimensions.Empty && var1 instanceof RealNum) {
         return new DFloNum(((RealNum)var1).doubleValue() + (double)var2 * this.factor);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public Numeric div(Object var1) {
      if(var1 instanceof DQuantity) {
         DQuantity var2 = (DQuantity)var1;
         return (Numeric)(this.dimensions() == var2.dimensions()?new DFloNum(this.factor * this.unit().doubleValue() / (var2.factor * var2.unit().factor)):divide(this, var2));
      } else if(var1 instanceof RealNum) {
         return new DQuantity(this.factor / ((RealNum)var1).doubleValue(), this.unit());
      } else if(!(var1 instanceof Numeric)) {
         throw new IllegalArgumentException();
      } else {
         return ((Numeric)var1).divReversed(this);
      }
   }

   public Numeric divReversed(Numeric var1) {
      if(var1 instanceof RealNum) {
         return new DQuantity(((RealNum)var1).doubleValue() / this.factor, Unit.divide(Unit.Empty, this.unit()));
      } else {
         throw new IllegalArgumentException();
      }
   }

   public final double doubleValue() {
      return this.factor * this.unt.factor;
   }

   public boolean isExact() {
      return false;
   }

   public boolean isZero() {
      return this.factor == 0.0D;
   }

   public Numeric mul(Object var1) {
      if(var1 instanceof DQuantity) {
         return times(this, (DQuantity)var1);
      } else if(var1 instanceof RealNum) {
         return new DQuantity(this.factor * ((RealNum)var1).doubleValue(), this.unit());
      } else if(!(var1 instanceof Numeric)) {
         throw new IllegalArgumentException();
      } else {
         return ((Numeric)var1).mulReversed(this);
      }
   }

   public Numeric mulReversed(Numeric var1) {
      if(var1 instanceof RealNum) {
         return new DQuantity(((RealNum)var1).doubleValue() * this.factor, this.unit());
      } else {
         throw new IllegalArgumentException();
      }
   }

   public final Complex number() {
      return new DFloNum(this.factor);
   }

   public final RealNum re() {
      return new DFloNum(this.factor);
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      this.factor = var1.readDouble();
      this.unt = (Unit)var1.readObject();
   }

   public final Unit unit() {
      return this.unt;
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      var1.writeDouble(this.factor);
      var1.writeObject(this.unt);
   }
}
