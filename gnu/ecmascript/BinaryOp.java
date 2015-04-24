package gnu.ecmascript;

import gnu.ecmascript.Convert;
import gnu.mapping.Procedure2;

public class BinaryOp extends Procedure2 {

   int op;


   public BinaryOp(String var1, int var2) {
      this.setName(var1);
      this.op = var2;
   }

   public double apply(double var1, double var3) {
      switch(this.op) {
      case 1:
         return var1 + var3;
      case 2:
         return var1 - var3;
      case 3:
         return var1 * var3;
      case 4:
         return (double)((int)var1 << ((int)var3 & 31));
      default:
         return Double.NaN;
      }
   }

   public Object apply2(Object var1, Object var2) {
      return this.op == 5?(Convert.toNumber(var1) < Convert.toNumber(var2)?Boolean.TRUE:Boolean.FALSE):new Double(this.apply(Convert.toNumber(var1), Convert.toNumber(var2)));
   }
}
