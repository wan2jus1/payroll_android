package gnu.kawa.functions;

import gnu.kawa.functions.ArithOp;
import gnu.kawa.functions.Arithmetic;
import gnu.kawa.lispexpr.LangObjType;
import gnu.mapping.Procedure;
import gnu.mapping.WrongType;
import gnu.math.BitOps;
import gnu.math.IntNum;
import java.math.BigInteger;

public class BitwiseOp extends ArithOp {

   public static final BitwiseOp and = new BitwiseOp("bitwise-and", 13);
   public static final BitwiseOp ashift = new BitwiseOp("bitwise-arithmetic-shift", 9);
   public static final BitwiseOp ashiftl = new BitwiseOp("bitwise-arithmetic-shift-left", 10);
   public static final BitwiseOp ashiftr = new BitwiseOp("bitwise-arithmetic-shift-right", 11);
   public static final BitwiseOp ior = new BitwiseOp("bitwise-ior", 14);
   public static final BitwiseOp not = new BitwiseOp("bitwise-not", 16);
   public static final BitwiseOp xor = new BitwiseOp("bitwise-xor", 15);


   public BitwiseOp(String var1, int var2) {
      super(var1, var2);
      this.setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileArith:validateApplyArithOp");
      Procedure.compilerKey.set(this, "*gnu.kawa.functions.CompileArith:forBitwise");
   }

   public static int checkNonNegativeShift(Procedure var0, int var1) {
      if(var1 < 0) {
         throw new WrongType(var0, 2, Integer.valueOf(var1), "non-negative integer");
      } else {
         return var1;
      }
   }

   public static IntNum shiftLeft(IntNum var0, int var1) {
      return IntNum.shift(var0, checkNonNegativeShift(ashiftl, var1));
   }

   public static IntNum shiftRight(IntNum var0, int var1) {
      return IntNum.shift(var0, -checkNonNegativeShift(ashiftr, var1));
   }

   public Object adjustResult(IntNum var1, int var2) {
      switch(var2) {
      case 1:
         return Integer.valueOf(var1.intValue());
      case 2:
         return Long.valueOf(var1.longValue());
      case 3:
         return new BigInteger(var1.toString());
      default:
         return var1;
      }
   }

   public Object apply1(Object var1) {
      if(this.op == 16) {
         int var2 = Arithmetic.classifyValue(var1);
         return this.adjustResult(BitOps.not(LangObjType.coerceIntNum(var1)), var2);
      } else {
         return this.apply2(this.defaultResult(), var1);
      }
   }

   public Object apply2(Object var1, Object var2) {
      int var3 = Arithmetic.classifyValue(var1);
      int var4 = Arithmetic.classifyValue(var2);
      if((this.op < 9 || this.op > 12) && var3 > 0 && (var3 <= var4 || var4 <= 0)) {
         var3 = var4;
      }

      IntNum var6 = LangObjType.coerceIntNum(var1);
      IntNum var7 = LangObjType.coerceIntNum(var2);
      switch(this.op) {
      case 9:
      case 10:
      case 11:
         label25: {
            int var5 = var7.intValue();
            if(this.op != 11) {
               var4 = var5;
               if(this.op != 10) {
                  break label25;
               }
            }

            checkNonNegativeShift(this, var5);
            var4 = var5;
            if(this.op == 11) {
               var4 = -var5;
            }
         }

         var6 = IntNum.shift(var6, var4);
         break;
      case 12:
      default:
         throw new Error();
      case 13:
         var6 = BitOps.and(var6, var7);
         break;
      case 14:
         var6 = BitOps.ior(var6, var7);
         break;
      case 15:
         var6 = BitOps.xor(var6, var7);
      }

      return this.adjustResult(var6, var3);
   }

   public Object applyN(Object[] var1) {
      int var5 = var1.length;
      Object var3;
      if(var5 == 0) {
         var3 = this.defaultResult();
      } else {
         if(var5 == 1) {
            return this.apply1(var1[0]);
         }

         Object var2 = var1[0];
         int var4 = 1;

         while(true) {
            var3 = var2;
            if(var4 >= var5) {
               break;
            }

            var2 = this.apply2(var2, var1[var4]);
            ++var4;
         }
      }

      return var3;
   }

   public Object defaultResult() {
      return this.op == 13?IntNum.minusOne():IntNum.zero();
   }

   public int numArgs() {
      return this.op >= 9 && this.op <= 12?8194:(this.op == 16?4097:-4096);
   }
}
