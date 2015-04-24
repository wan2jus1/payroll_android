package gnu.mapping;

import gnu.bytecode.ArrayType;
import gnu.bytecode.Type;
import gnu.mapping.CallContext;
import gnu.mapping.Procedure;
import gnu.mapping.ProcedureN;
import gnu.mapping.WrongArguments;
import gnu.mapping.WrongType;

public abstract class MethodProc extends ProcedureN {

   public static final int NO_MATCH = -1;
   public static final int NO_MATCH_AMBIGUOUS = -851968;
   public static final int NO_MATCH_BAD_TYPE = -786432;
   public static final int NO_MATCH_TOO_FEW_ARGS = -983040;
   public static final int NO_MATCH_TOO_MANY_ARGS = -917504;
   static final Type[] unknownArgTypes = new Type[]{Type.pointer_type};
   protected Object argTypes;


   public static RuntimeException matchFailAsException(int var0, Procedure var1, Object[] var2) {
      short var3 = (short)var0;
      if((var0 & -65536) != -786432) {
         return new WrongArguments(var1, var2.length);
      } else {
         Object var4;
         if(var3 > 0) {
            var4 = var2[var3 - 1];
         } else {
            var4 = null;
         }

         return new WrongType(var1, var3, var4);
      }
   }

   public static int mostSpecific(MethodProc[] var0, int var1) {
      int var7;
      if(var1 <= 1) {
         var7 = var1 - 1;
      } else {
         MethodProc var4 = var0[0];
         MethodProc[] var3 = null;
         int var8 = 1;

         MethodProc var2;
         for(var7 = 0; var8 < var1; var4 = var2) {
            MethodProc var5 = var0[var8];
            if(var4 != null) {
               var2 = mostSpecific(var4, var5);
               if(var2 == null) {
                  MethodProc[] var10 = var3;
                  if(var3 == null) {
                     var10 = new MethodProc[var1];
                  }

                  var10[0] = var4;
                  var10[1] = var5;
                  var7 = 2;
                  var4 = null;
                  var3 = var10;
                  var2 = var4;
               } else if(var2 == var5) {
                  var2 = var5;
                  var7 = var8;
               } else {
                  var2 = var4;
               }
            } else {
               int var9 = 0;

               while(true) {
                  if(var9 >= var7) {
                     var2 = var5;
                     var7 = var8;
                     break;
                  }

                  Object var11 = var3[var9];
                  MethodProc var6 = mostSpecific((MethodProc)var11, var5);
                  if(var6 == var11) {
                     var2 = var4;
                     break;
                  }

                  if(var6 == null) {
                     var9 = var7 + 1;
                     var3[var7] = var5;
                     var2 = var4;
                     var7 = var9;
                     break;
                  }

                  ++var9;
               }
            }

            ++var8;
         }

         if(var4 == null) {
            return -1;
         }
      }

      return var7;
   }

   public static MethodProc mostSpecific(MethodProc var0, MethodProc var1) {
      boolean var2 = false;
      boolean var4 = false;
      boolean var6 = false;
      int var7 = var0.minArgs();
      int var8 = var1.minArgs();
      int var10 = var0.maxArgs();
      int var9 = var1.maxArgs();
      if((var10 < 0 || var10 >= var8) && (var9 < 0 || var9 >= var7)) {
         int var5 = var0.numParameters();
         int var3 = var1.numParameters();
         if(var5 <= var3) {
            var5 = var3;
         }

         boolean var12 = var6;
         if(var10 != var9) {
            if(var10 < 0) {
               var4 = true;
            }

            var2 = var4;
            var12 = var6;
            if(var9 < 0) {
               var12 = true;
               var2 = var4;
            }
         }

         if(var7 < var8) {
            var4 = true;
         } else {
            var4 = var2;
            if(var7 > var8) {
               var12 = true;
               var4 = var2;
            }
         }

         for(int var11 = 0; var11 < var5; ++var11) {
            int var13 = var0.getParameterType(var11).compare(var1.getParameterType(var11));
            if(var13 == -1) {
               var12 = true;
               if(var4) {
                  return null;
               }
            }

            if(var13 == 1) {
               var4 = true;
               if(var12) {
                  return null;
               }
            }
         }

         if(!var12) {
            if(var4) {
               return var1;
            }

            return null;
         }
      } else {
         var0 = null;
      }

      return var0;
   }

   public Object applyN(Object[] var1) throws Throwable {
      checkArgCount(this, var1.length);
      CallContext var2 = CallContext.getInstance();
      this.checkN(var1, var2);
      return var2.runUntilValue();
   }

   public Type getParameterType(int var1) {
      if(!(this.argTypes instanceof Type[])) {
         this.resolveParameterTypes();
      }

      Type[] var2 = (Type[])((Type[])this.argTypes);
      if(var1 < var2.length && (var1 < var2.length - 1 || this.maxArgs() >= 0)) {
         return var2[var1];
      } else {
         if(this.maxArgs() < 0) {
            Type var3 = var2[var2.length - 1];
            if(var3 instanceof ArrayType) {
               return ((ArrayType)var3).getComponentType();
            }
         }

         return Type.objectType;
      }
   }

   public int isApplicable(Type[] var1) {
      int var3 = var1.length;
      int var2 = this.numArgs();
      byte var7;
      if(var3 >= (var2 & 4095) && (var2 < 0 || var3 <= var2 >> 12)) {
         byte var6 = 1;

         while(true) {
            int var4 = var3 - 1;
            var7 = var6;
            if(var4 < 0) {
               break;
            }

            int var5 = this.getParameterType(var4).compare(var1[var4]);
            if(var5 == -3) {
               return -1;
            }

            var3 = var4;
            if(var5 < 0) {
               var6 = 0;
               var3 = var4;
            }
         }
      } else {
         var7 = -1;
      }

      return var7;
   }

   public int numParameters() {
      int var1 = this.numArgs();
      int var2 = var1 >> 12;
      return var2 >= 0?var2:(var1 & 4095) + 1;
   }

   protected void resolveParameterTypes() {
      this.argTypes = unknownArgTypes;
   }
}
