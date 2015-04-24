package gnu.kawa.functions;

import gnu.kawa.functions.ArithOp;
import gnu.kawa.functions.Arithmetic;
import gnu.mapping.Procedure;
import gnu.math.DFloNum;
import gnu.math.IntNum;
import gnu.math.RatNum;
import java.math.BigDecimal;
import java.math.BigInteger;

public class AddOp extends ArithOp {

   public static final AddOp $Mn = new AddOp("-", -1);
   public static final AddOp $Pl = new AddOp("+", 1);
   int plusOrMinus;


   public static Object $Mn(Object var0) {
      switch(Arithmetic.classifyValue(var0)) {
      case 1:
         return new Integer(-Arithmetic.asInt(var0));
      case 2:
         return new Long(-Arithmetic.asLong(var0));
      case 3:
         return Arithmetic.asBigInteger(var0).negate();
      case 4:
         return IntNum.neg(Arithmetic.asIntNum((Object)var0));
      case 5:
         return Arithmetic.asBigDecimal(var0).negate();
      case 6:
         return RatNum.neg(Arithmetic.asRatNum(var0));
      case 7:
         return new Float(-Arithmetic.asFloat(var0));
      case 8:
         return new Double(-Arithmetic.asDouble(var0));
      case 9:
         return new DFloNum(-Arithmetic.asDouble(var0));
      default:
         return Arithmetic.asNumeric(var0).neg();
      }
   }

   public static Object $Mn(Object var0, Object var1) {
      return apply2(-1, var0, var1);
   }

   public static Object $Mn$V(Object var0, Object var1, Object var2, Object[] var3) {
      return applyN(-1, apply2(-1, apply2(-1, var0, var1), var2), var3);
   }

   public static Object $Pl(Object var0, Object var1) {
      return apply2(1, var0, var1);
   }

   public static Object $Pl$V(Object var0, Object var1, Object var2, Object[] var3) {
      return applyN(1, apply2(1, apply2(1, var0, var1), var2), var3);
   }

   public AddOp(String var1, int var2) {
      byte var3;
      if(var2 > 0) {
         var3 = 1;
      } else {
         var3 = 2;
      }

      super(var1, var3);
      this.plusOrMinus = 1;
      this.plusOrMinus = var2;
      if(var2 > 0) {
         var1 = "gnu.kawa.functions.CompileArith:$Pl";
      } else {
         var1 = "gnu.kawa.functions.CompileArith:$Mn";
      }

      Procedure.compilerKey.set(this, var1);
      this.setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileArith:validateApplyArithOp");
   }

   public static Object apply2(int var0, Object var1, Object var2) {
      int var9 = Arithmetic.classifyValue(var1);
      int var10 = Arithmetic.classifyValue(var2);
      if(var9 < var10) {
         var9 = var10;
      }

      double var3;
      double var5;
      switch(var9) {
      case 1:
         var9 = Arithmetic.asInt(var1);
         var10 = Arithmetic.asInt(var2);
         if(var0 > 0) {
            var0 = var9 + var10;
         } else {
            var0 = var9 - var10;
         }

         return new Integer(var0);
      case 2:
         long var11 = Arithmetic.asLong(var1);
         long var13 = Arithmetic.asLong(var2);
         if(var0 > 0) {
            var11 += var13;
         } else {
            var11 -= var13;
         }

         return new Long(var11);
      case 3:
         BigInteger var16 = Arithmetic.asBigInteger(var1);
         BigInteger var18 = Arithmetic.asBigInteger(var2);
         if(var0 > 0) {
            return var16.add(var18);
         }

         return var16.subtract(var18);
      case 4:
         return IntNum.add(Arithmetic.asIntNum((Object)var1), Arithmetic.asIntNum((Object)var2), var0);
      case 5:
         BigDecimal var15 = Arithmetic.asBigDecimal(var1);
         BigDecimal var17 = Arithmetic.asBigDecimal(var2);
         if(var0 > 0) {
            return var15.add(var17);
         }

         return var15.subtract(var17);
      case 6:
         return RatNum.add(Arithmetic.asRatNum(var1), Arithmetic.asRatNum(var2), var0);
      case 7:
         float var7 = Arithmetic.asFloat(var1);
         float var8 = Arithmetic.asFloat(var2);
         if(var0 > 0) {
            var7 += var8;
         } else {
            var7 -= var8;
         }

         return new Float(var7);
      case 8:
         var3 = Arithmetic.asDouble(var1);
         var5 = Arithmetic.asDouble(var2);
         if(var0 > 0) {
            var3 += var5;
         } else {
            var3 -= var5;
         }

         return new Double(var3);
      case 9:
         var3 = Arithmetic.asDouble(var1);
         var5 = Arithmetic.asDouble(var2);
         if(var0 > 0) {
            var3 += var5;
         } else {
            var3 -= var5;
         }

         return new DFloNum(var3);
      default:
         return Arithmetic.asNumeric(var1).add(Arithmetic.asNumeric(var2), var0);
      }
   }

   public static Object applyN(int var0, Object var1, Object[] var2) {
      int var4 = var2.length;

      for(int var3 = 0; var3 < var4; ++var3) {
         var1 = apply2(var0, var1, var2[var3]);
      }

      return var1;
   }

   public static Object applyN(int var0, Object[] var1) {
      int var5 = var1.length;
      Object var3;
      if(var5 == 0) {
         var3 = IntNum.zero();
      } else {
         Object var2 = var1[0];
         if(var5 == 1 && var0 < 0) {
            return $Mn(var2);
         }

         int var4 = 1;

         while(true) {
            var3 = var2;
            if(var4 >= var5) {
               break;
            }

            var2 = apply2(var0, var2, var1[var4]);
            ++var4;
         }
      }

      return var3;
   }

   public Object applyN(Object[] var1) {
      return applyN(this.plusOrMinus, var1);
   }
}
