package gnu.math;

import gnu.math.BaseUnit;
import gnu.math.Complex;
import gnu.math.DFloNum;
import gnu.math.DQuantity;
import gnu.math.Dimensions;
import gnu.math.IntNum;
import gnu.math.MulUnit;
import gnu.math.NamedUnit;
import gnu.math.Numeric;
import gnu.math.Quantity;
import gnu.math.RealNum;

public abstract class Unit extends Quantity {

   public static BaseUnit Empty = new BaseUnit();
   public static double NON_COMBINABLE;
   public static final Unit cm;
   public static final NamedUnit date;
   public static final BaseUnit duration;
   public static final BaseUnit gram;
   public static final Unit hour;
   public static final Unit in;
   public static final BaseUnit meter;
   public static final Unit minute;
   public static final Unit mm;
   public static final NamedUnit month;
   public static final Unit pica;
   public static final Unit pt;
   public static final Unit radian;
   public static final NamedUnit second;
   static NamedUnit[] table = new NamedUnit[100];
   Unit base;
   Dimensions dims;
   double factor = 1.0D;
   MulUnit products;


   static {
      Dimensions.Empty.bases[0] = Empty;
      NON_COMBINABLE = 0.0D;
      meter = new BaseUnit("m", "Length");
      duration = new BaseUnit("duration", "Time");
      gram = new BaseUnit("g", "Mass");
      cm = define("cm", 0.01D, meter);
      mm = define("mm", 0.1D, cm);
      in = define("in", 0.0254D, meter);
      pt = define("pt", 3.527778E-4D, meter);
      pica = define("pica", 0.004233333D, meter);
      radian = define("rad", 1.0D, Empty);
      date = new NamedUnit("date", NON_COMBINABLE, duration);
      second = new NamedUnit("s", NON_COMBINABLE, duration);
      month = new NamedUnit("month", NON_COMBINABLE, duration);
      minute = define("min", 60.0D, second);
      hour = define("hour", 60.0D, minute);
   }

   public static Unit define(String var0, double var1, Unit var3) {
      return new NamedUnit(var0, var1, var3);
   }

   public static Unit define(String var0, DQuantity var1) {
      return new NamedUnit(var0, var1);
   }

   public static Unit divide(Unit var0, Unit var1) {
      return times(var0, 1, var1, -1);
   }

   public static NamedUnit lookup(String var0) {
      return NamedUnit.lookup(var0);
   }

   public static NamedUnit make(String var0, Quantity var1) {
      return NamedUnit.make(var0, var1);
   }

   public static Unit pow(Unit var0, int var1) {
      return times(var0, var1, Empty, 0);
   }

   static Unit times(Unit var0, int var1, Unit var2, int var3) {
      int var7 = var1;
      Object var4 = var2;
      int var6 = var3;
      if(var0 == var2) {
         var7 = var1 + var3;
         var4 = Empty;
         var6 = 0;
      }

      Object var5;
      Object var9;
      label62: {
         if(var7 != 0) {
            var5 = var0;
            var9 = var4;
            var1 = var6;
            if(var0 != Empty) {
               break label62;
            }
         }

         var9 = Empty;
         var1 = 0;
         var7 = var6;
         var5 = var4;
      }

      if(var1 == 0 || var9 == Empty) {
         if(var7 == 1) {
            return (Unit)var5;
         }

         if(var7 == 0) {
            return Empty;
         }
      }

      MulUnit var8;
      if(var5 instanceof MulUnit) {
         var8 = (MulUnit)var5;
         if(var8.unit1 == var9) {
            return times((Unit)var9, var8.power1 * var7 + var1, var8.unit2, var8.power2 * var7);
         }

         if(var8.unit2 == var9) {
            return times(var8.unit1, var8.power1 * var7, (Unit)var9, var8.power2 * var7 + var1);
         }

         if(var9 instanceof MulUnit) {
            MulUnit var10 = (MulUnit)var9;
            if(var8.unit1 == var10.unit1 && var8.unit2 == var10.unit2) {
               return times(var8.unit1, var8.power1 * var7 + var10.power1 * var1, var8.unit2, var8.power2 * var7 + var10.power2 * var1);
            }

            if(var8.unit1 == var10.unit2 && var8.unit2 == var10.unit1) {
               return times(var8.unit1, var8.power1 * var7 + var10.power2 * var1, var8.unit2, var8.power2 * var7 + var10.power1 * var1);
            }
         }
      }

      if(var9 instanceof MulUnit) {
         var8 = (MulUnit)var9;
         if(var8.unit1 == var5) {
            return times((Unit)var5, var8.power1 * var1 + var7, var8.unit2, var8.power2 * var1);
         }

         if(var8.unit2 == var5) {
            return times(var8.unit1, var8.power1 * var1, (Unit)var5, var8.power2 * var1 + var7);
         }
      }

      return MulUnit.make((Unit)var5, var7, (Unit)var9, var1);
   }

   public static Unit times(Unit var0, Unit var1) {
      return times(var0, 1, var1, 1);
   }

   public final Dimensions dimensions() {
      return this.dims;
   }

   public final double doubleValue() {
      return this.factor;
   }

   public String getName() {
      return null;
   }

   public int hashCode() {
      return this.dims.hashCode();
   }

   public boolean isExact() {
      return false;
   }

   public final boolean isZero() {
      return false;
   }

   public Complex number() {
      return DFloNum.one();
   }

   public Numeric power(IntNum var1) {
      if(var1.words != null) {
         throw new ArithmeticException("Unit raised to bignum power");
      } else {
         return pow(this, var1.ival);
      }
   }

   public Unit sqrt() {
      if(this == Empty) {
         return this;
      } else {
         throw new RuntimeException("unimplemented Unit.sqrt");
      }
   }

   public String toString() {
      String var1 = this.getName();
      return var1 != null?var1:(this == Empty?"unit":Double.toString(this.factor) + "<unnamed unit>");
   }

   public String toString(double var1) {
      String var3 = Double.toString(var1);
      return this == Empty?var3:var3 + this.toString();
   }

   public String toString(RealNum var1) {
      return this.toString(var1.doubleValue());
   }

   public Unit unit() {
      return this;
   }
}
