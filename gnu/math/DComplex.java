package gnu.math;

import gnu.math.CComplex;
import gnu.math.Complex;
import gnu.math.DFloNum;
import gnu.math.Dimensions;
import gnu.math.Numeric;
import gnu.math.RealNum;
import gnu.math.Unit;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class DComplex extends Complex implements Externalizable {

   double imag;
   double real;


   public DComplex() {
   }

   public DComplex(double var1, double var3) {
      this.real = var1;
      this.imag = var3;
   }

   public static DComplex div(double var0, double var2, double var4, double var6) {
      double var8;
      if(Math.abs(var4) <= Math.abs(var6)) {
         var8 = var4 / var6;
         var6 *= 1.0D + var8 * var8;
         var4 = var0 * var8 + var2;
         var2 = var2 * var8 - var0;
         var0 = var6;
      } else {
         var8 = var6 / var4;
         var6 = var4 * (1.0D + var8 * var8);
         var4 = var0 + var2 * var8;
         var2 -= var0 * var8;
         var0 = var6;
      }

      return new DComplex(var4 / var0, var2 / var0);
   }

   public static Complex log(double var0, double var2) {
      return make(Math.log(Math.hypot(var0, var2)), Math.atan2(var2, var0));
   }

   public static DComplex power(double var0, double var2, double var4, double var6) {
      double var8 = Math.log(Math.hypot(var0, var2));
      var0 = Math.atan2(var2, var0);
      return Complex.polar(Math.exp(var8 * var4 - var6 * var0), var6 * var8 + var4 * var0);
   }

   public static Complex sqrt(double var0, double var2) {
      double var4 = Math.hypot(var0, var2);
      if(var4 == 0.0D) {
         var0 = var4;
         var2 = var4;
      } else if(var0 > 0.0D) {
         var4 = Math.sqrt(0.5D * (var4 + var0));
         var0 = var2 / var4 / 2.0D;
         var2 = var4;
      } else {
         var4 = Math.sqrt(0.5D * (var4 - var0));
         var0 = var4;
         if(var2 < 0.0D) {
            var0 = -var4;
         }

         var2 = var2 / var0 / 2.0D;
      }

      return new DComplex(var2, var0);
   }

   public Numeric add(Object var1, int var2) {
      if(var1 instanceof Complex) {
         Complex var3 = (Complex)var1;
         if(var3.dimensions() != Dimensions.Empty) {
            throw new ArithmeticException("units mis-match");
         } else {
            return new DComplex(this.real + (double)var2 * var3.reValue(), this.imag + (double)var2 * var3.imValue());
         }
      } else {
         return ((Numeric)var1).addReversed(this, var2);
      }
   }

   public Numeric div(Object var1) {
      if(var1 instanceof Complex) {
         Complex var2 = (Complex)var1;
         return div(this.real, this.imag, var2.doubleValue(), var2.doubleImagValue());
      } else {
         return ((Numeric)var1).divReversed(this);
      }
   }

   public double doubleImagValue() {
      return this.imag;
   }

   public double doubleValue() {
      return this.real;
   }

   public boolean equals(Object var1) {
      if(var1 != null && var1 instanceof Complex) {
         Complex var2 = (Complex)var1;
         if(var2.unit() == Unit.Empty && Double.doubleToLongBits(this.real) == Double.doubleToLongBits(var2.reValue()) && Double.doubleToLongBits(this.imag) == Double.doubleToLongBits(var2.imValue())) {
            return true;
         }
      }

      return false;
   }

   public RealNum im() {
      return new DFloNum(this.imag);
   }

   public boolean isExact() {
      return false;
   }

   public Numeric mul(Object var1) {
      if(var1 instanceof Complex) {
         Complex var6 = (Complex)var1;
         if(var6.unit() == Unit.Empty) {
            double var2 = var6.reValue();
            double var4 = var6.imValue();
            return new DComplex(this.real * var2 - this.imag * var4, this.real * var4 + this.imag * var2);
         } else {
            return Complex.times(this, var6);
         }
      } else {
         return ((Numeric)var1).mulReversed(this);
      }
   }

   public final Numeric neg() {
      return new DComplex(-this.real, -this.imag);
   }

   public RealNum re() {
      return new DFloNum(this.real);
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      this.real = var1.readDouble();
      this.imag = var1.readDouble();
   }

   public Complex toExact() {
      return new CComplex(DFloNum.toExact(this.real), DFloNum.toExact(this.imag));
   }

   public String toString() {
      String var2 = "";
      String var4;
      if(this.real == Double.POSITIVE_INFINITY) {
         var2 = "#i";
         var4 = "1/0";
      } else if(this.real == Double.NEGATIVE_INFINITY) {
         var2 = "#i";
         var4 = "-1/0";
      } else if(Double.isNaN(this.real)) {
         var2 = "#i";
         var4 = "0/0";
      } else {
         var4 = Double.toString(this.real);
      }

      if(Double.doubleToLongBits(this.imag) == 0L) {
         return var2 + var4;
      } else {
         String var1;
         String var3;
         if(this.imag == Double.POSITIVE_INFINITY) {
            var3 = "#i";
            var1 = "+1/0i";
         } else if(this.imag == Double.NEGATIVE_INFINITY) {
            var3 = "#i";
            var1 = "-1/0i";
         } else if(Double.isNaN(this.imag)) {
            var3 = "#i";
            var1 = "+0/0i";
         } else {
            String var5 = Double.toString(this.imag) + "i";
            var1 = var5;
            var3 = var2;
            if(var5.charAt(0) != 45) {
               var1 = "+" + var5;
               var3 = var2;
            }
         }

         StringBuilder var6 = new StringBuilder();
         if(Double.doubleToLongBits(this.real) != 0L) {
            var3 = var3 + var4;
         }

         return var6.append(var3).append(var1).toString();
      }
   }

   public String toString(int var1) {
      return var1 == 10?this.toString():"#d" + this.toString();
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      var1.writeDouble(this.real);
      var1.writeDouble(this.imag);
   }
}
