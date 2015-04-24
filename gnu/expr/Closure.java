package gnu.expr;

import gnu.bytecode.ArrayType;
import gnu.bytecode.Type;
import gnu.expr.Declaration;
import gnu.expr.Keyword;
import gnu.expr.LambdaExp;
import gnu.expr.Special;
import gnu.lists.LList;
import gnu.mapping.CallContext;
import gnu.mapping.Location;
import gnu.mapping.MethodProc;
import java.lang.reflect.Array;

class Closure extends MethodProc {

   Object[][] evalFrames;
   LambdaExp lambda;


   public Closure(LambdaExp var1, CallContext var2) {
      this.lambda = var1;
      Object[][] var4 = var2.evalFrames;
      if(var4 != null) {
         int var3;
         for(var3 = var4.length; var3 > 0 && var4[var3 - 1] == null; --var3) {
            ;
         }

         this.evalFrames = new Object[var3][];
         System.arraycopy(var4, 0, this.evalFrames, 0, var3);
      }

      this.setSymbol(this.lambda.getSymbol());
   }

   public void apply(CallContext param1) throws Throwable {
      // $FF: Couldn't be decompiled
   }

   public Object getProperty(Object var1, Object var2) {
      Object var4 = super.getProperty(var1, var2);
      Object var3 = var4;
      if(var4 == null) {
         var3 = this.lambda.getProperty(var1, var2);
      }

      return var3;
   }

   public int match0(CallContext var1) {
      return this.matchN(new Object[0], var1);
   }

   public int match1(Object var1, CallContext var2) {
      return this.matchN(new Object[]{var1}, var2);
   }

   public int match2(Object var1, Object var2, CallContext var3) {
      return this.matchN(new Object[]{var1, var2}, var3);
   }

   public int match3(Object var1, Object var2, Object var3, CallContext var4) {
      return this.matchN(new Object[]{var1, var2, var3}, var4);
   }

   public int match4(Object var1, Object var2, Object var3, Object var4, CallContext var5) {
      return this.matchN(new Object[]{var1, var2, var3, var4}, var5);
   }

   public int matchN(Object[] var1, CallContext var2) {
      int var8 = this.numArgs();
      int var13 = var1.length;
      int var9 = var8 & 4095;
      if(var13 < var9) {
         return -983040 | var9;
      } else {
         var8 >>= 12;
         if(var13 > var8 && var8 >= 0) {
            return -917504 | var8;
         } else {
            Object[] var6 = new Object[this.lambda.frameSize];
            if(this.lambda.keywords == null) {
               var8 = 0;
            } else {
               var8 = this.lambda.keywords.length;
            }

            int var11;
            if(this.lambda.defaultArgs == null) {
               var11 = 0;
            } else {
               var11 = this.lambda.defaultArgs.length - var8;
            }

            int var10 = 0;
            int var14 = this.lambda.min_args;
            Declaration var5 = this.lambda.firstDecl();
            var9 = 0;

            for(var8 = 0; var5 != null; var5 = var5.nextDecl()) {
               Object var3;
               Object var4;
               int var12;
               if(var8 < var14) {
                  var12 = var8 + 1;
                  var3 = var1[var8];
                  var8 = var12;
               } else if(var8 < var14 + var11) {
                  if(var8 < var13) {
                     var12 = var8 + 1;
                     var3 = var1[var8];
                     var8 = var12;
                  } else {
                     var3 = this.lambda.evalDefaultArg(var10, var2);
                  }

                  ++var10;
               } else if(this.lambda.max_args < 0 && var8 == var14 + var11) {
                  if(var5.type instanceof ArrayType) {
                     int var15 = var13 - var8;
                     Type var7 = ((ArrayType)var5.type).getComponentType();
                     if(var7 == Type.objectType) {
                        var3 = new Object[var15];
                        System.arraycopy(var1, var8, var3, 0, var15);
                     } else {
                        var4 = Array.newInstance(var7.getReflectClass(), var15);
                        var12 = 0;

                        while(true) {
                           var3 = var4;
                           if(var12 >= var15) {
                              break;
                           }

                           try {
                              var3 = var7.coerceFromObject(var1[var8 + var12]);
                           } catch (ClassCastException var17) {
                              return -786432 | var8 + var12;
                           }

                           Array.set(var4, var12, var3);
                           ++var12;
                        }
                     }
                  } else {
                     var3 = LList.makeList(var1, var8);
                  }
               } else {
                  Keyword[] var18 = this.lambda.keywords;
                  var12 = var9 + 1;
                  var4 = Keyword.searchForKeyword(var1, var14 + var11, var18[var9]);
                  var3 = var4;
                  if(var4 == Special.dfault) {
                     var3 = this.lambda.evalDefaultArg(var10, var2);
                  }

                  ++var10;
                  var9 = var12;
               }

               var4 = var3;
               if(var5.type != null) {
                  try {
                     var4 = var5.type.coerceFromObject(var3);
                  } catch (ClassCastException var16) {
                     return -786432 | var8;
                  }
               }

               var3 = var4;
               if(var5.isIndirectBinding()) {
                  var3 = var5.makeIndirectLocationFor();
                  ((Location)var3).set(var4);
               }

               var6[var5.evalIndex] = var3;
            }

            var2.values = var6;
            var2.where = 0;
            var2.next = 0;
            var2.proc = this;
            return 0;
         }
      }
   }

   public int numArgs() {
      return this.lambda.min_args | this.lambda.max_args << 12;
   }
}
