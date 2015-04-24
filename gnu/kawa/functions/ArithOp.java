package gnu.kawa.functions;

import gnu.bytecode.PrimType;
import gnu.bytecode.Type;
import gnu.kawa.functions.Arithmetic;
import gnu.mapping.ProcedureN;
import gnu.math.IntNum;

public abstract class ArithOp extends ProcedureN {

   static final int ADD = 1;
   public static final int AND = 13;
   public static final int ASHIFT_GENERAL = 9;
   public static final int ASHIFT_LEFT = 10;
   public static final int ASHIFT_RIGHT = 11;
   public static final int DIVIDE_GENERIC = 4;
   public static final int DIVIDE_INEXACT = 5;
   public static final int IOR = 14;
   public static final int LSHIFT_RIGHT = 12;
   public static final int MODULO = 8;
   static final int MUL = 3;
   public static final int NOT = 16;
   public static final int QUOTIENT = 6;
   public static final int QUOTIENT_EXACT = 7;
   static final int SUB = 2;
   public static final int XOR = 15;
   final int op;


   public ArithOp(String var1, int var2) {
      super(var1);
      this.op = var2;
   }

   public static int classify(Type var0) {
      byte var1 = 4;
      if(var0 instanceof PrimType) {
         char var2 = var0.getSignature().charAt(0);
         if(var2 != 86 && var2 != 90 && var2 != 67) {
            if(var2 == 68 || var2 == 70) {
               return 3;
            }
         } else {
            var1 = 0;
         }
      } else if(!var0.isSubtype(Arithmetic.typeIntNum)) {
         if(var0.isSubtype(Arithmetic.typeDFloNum)) {
            return 3;
         }

         if(var0.isSubtype(Arithmetic.typeRealNum)) {
            return 2;
         }

         if(var0.isSubtype(Arithmetic.typeNumeric)) {
            return 1;
         }

         return 0;
      }

      return var1;
   }

   public Object defaultResult() {
      return IntNum.zero();
   }

   public boolean isSideEffectFree() {
      return true;
   }
}
