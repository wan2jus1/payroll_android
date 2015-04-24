package kawa.standard;

import gnu.mapping.Procedure2;
import gnu.math.Complex;
import gnu.math.IntNum;
import gnu.math.Numeric;

public class expt extends Procedure2 {

   public static final expt expt = new expt("expt");


   public expt(String var1) {
      super(var1);
   }

   public static IntNum expt(IntNum var0, int var1) {
      return IntNum.power(var0, var1);
   }

   public static Numeric expt(Object var0, Object var1) {
      return (Numeric)(var1 instanceof IntNum?((Numeric)var0).power((IntNum)var1):Complex.power((Complex)var0, (Complex)var1));
   }

   public Object apply2(Object var1, Object var2) {
      return expt(var1, var2);
   }
}
