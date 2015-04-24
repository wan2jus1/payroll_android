package gnu.kawa.functions;

import gnu.kawa.functions.ArithOp;
import gnu.kawa.functions.Arithmetic;
import gnu.mapping.Procedure;
import gnu.math.DFloNum;
import gnu.math.IntNum;
import gnu.math.Numeric;
import gnu.math.RatNum;

public class MultiplyOp extends ArithOp {

   public static final MultiplyOp $St = new MultiplyOp("*");


   public MultiplyOp(String var1) {
      super(var1, 3);
      this.setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileArith:validateApplyArithOp");
      Procedure.compilerKey.set(this, "*gnu.kawa.functions.CompileArith:forMul");
   }

   public static Object apply(Object var0, Object var1) {
      return ((Numeric)var0).mul(var1);
   }

   public Object applyN(Object[] var1) {
      int var8 = var1.length;
      Object var3;
      if(var8 == 0) {
         var3 = IntNum.one();
      } else {
         Object var2 = (Number)var1[0];
         int var5 = Arithmetic.classifyValue(var2);
         int var4 = 1;

         while(true) {
            var3 = var2;
            if(var4 >= var8) {
               break;
            }

            var3 = var1[var4];
            int var7 = Arithmetic.classifyValue(var3);
            int var6 = var5;
            if(var5 < var7) {
               var6 = var7;
            }

            switch(var6) {
            case 1:
               var2 = new Integer(Arithmetic.asInt(var2) * Arithmetic.asInt(var3));
               break;
            case 2:
               var2 = new Long(Arithmetic.asLong(var2) * Arithmetic.asLong(var3));
               break;
            case 3:
               var2 = Arithmetic.asBigInteger(var2).multiply(Arithmetic.asBigInteger(var3));
               break;
            case 4:
               var2 = IntNum.times(Arithmetic.asIntNum((Object)var2), Arithmetic.asIntNum((Object)var3));
               break;
            case 5:
               var2 = Arithmetic.asBigDecimal(var2).multiply(Arithmetic.asBigDecimal(var3));
               break;
            case 6:
               var2 = RatNum.times(Arithmetic.asRatNum(var2), Arithmetic.asRatNum(var3));
               break;
            case 7:
               var2 = new Float(Arithmetic.asFloat(var2) * Arithmetic.asFloat(var3));
               break;
            case 8:
               var2 = new Double(Arithmetic.asDouble(var2) * Arithmetic.asDouble(var3));
               break;
            case 9:
               var2 = new DFloNum(Arithmetic.asDouble(var2) * Arithmetic.asDouble(var3));
               break;
            default:
               var2 = Arithmetic.asNumeric(var2).mul(Arithmetic.asNumeric(var3));
            }

            ++var4;
            var5 = var6;
         }
      }

      return var3;
   }

   public Object defaultResult() {
      return IntNum.one();
   }
}
