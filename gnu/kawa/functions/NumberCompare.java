package gnu.kawa.functions;

import gnu.bytecode.CodeAttr;
import gnu.bytecode.Label;
import gnu.bytecode.PrimType;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.ConditionalTarget;
import gnu.expr.Expression;
import gnu.expr.IfExp;
import gnu.expr.Inlineable;
import gnu.expr.Language;
import gnu.expr.PrimProcedure;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.StackTarget;
import gnu.expr.Target;
import gnu.kawa.functions.Arithmetic;
import gnu.mapping.Procedure;
import gnu.mapping.ProcedureN;
import gnu.math.IntNum;
import gnu.math.RatNum;

public class NumberCompare extends ProcedureN implements Inlineable {

   static final int RESULT_EQU = 0;
   static final int RESULT_GRT = 1;
   static final int RESULT_LSS = -1;
   static final int RESULT_NAN = -2;
   static final int RESULT_NEQ = -3;
   public static final int TRUE_IF_EQU = 8;
   public static final int TRUE_IF_GRT = 16;
   public static final int TRUE_IF_LSS = 4;
   public static final int TRUE_IF_NAN = 2;
   public static final int TRUE_IF_NEQ = 1;
   int flags;
   Language language;


   public static boolean $Eq(Object var0, Object var1) {
      return apply2(8, var0, var1);
   }

   public static boolean $Eq$V(Object var0, Object var1, Object var2, Object[] var3) {
      boolean var5 = false;
      boolean var4 = var5;
      if($Eq(var0, var1)) {
         var4 = var5;
         if($Eq(var1, var2)) {
            if(var3.length != 0) {
               var4 = var5;
               if(!$Eq(var2, var3[0])) {
                  return var4;
               }

               var4 = var5;
               if(!applyN(8, var3)) {
                  return var4;
               }
            }

            var4 = true;
         }
      }

      return var4;
   }

   public static boolean $Gr(Object var0, Object var1) {
      return apply2(16, var0, var1);
   }

   public static boolean $Gr$Eq(Object var0, Object var1) {
      return apply2(24, var0, var1);
   }

   public static boolean $Gr$Eq$V(Object var0, Object var1, Object var2, Object[] var3) {
      boolean var5 = false;
      boolean var4 = var5;
      if($Gr$Eq(var0, var1)) {
         var4 = var5;
         if($Gr$Eq(var1, var2)) {
            if(var3.length != 0) {
               var4 = var5;
               if(!$Gr$Eq(var2, var3[0])) {
                  return var4;
               }

               var4 = var5;
               if(!applyN(24, var3)) {
                  return var4;
               }
            }

            var4 = true;
         }
      }

      return var4;
   }

   public static boolean $Gr$V(Object var0, Object var1, Object var2, Object[] var3) {
      boolean var5 = false;
      boolean var4 = var5;
      if($Gr(var0, var1)) {
         var4 = var5;
         if($Gr(var1, var2)) {
            if(var3.length != 0) {
               var4 = var5;
               if(!$Gr(var2, var3[0])) {
                  return var4;
               }

               var4 = var5;
               if(!applyN(16, var3)) {
                  return var4;
               }
            }

            var4 = true;
         }
      }

      return var4;
   }

   public static boolean $Ls(Object var0, Object var1) {
      return apply2(4, var0, var1);
   }

   public static boolean $Ls$Eq(Object var0, Object var1) {
      return apply2(12, var0, var1);
   }

   public static boolean $Ls$Eq$V(Object var0, Object var1, Object var2, Object[] var3) {
      boolean var5 = false;
      boolean var4 = var5;
      if($Ls$Eq(var0, var1)) {
         var4 = var5;
         if($Ls$Eq(var1, var2)) {
            if(var3.length != 0) {
               var4 = var5;
               if(!$Ls$Eq(var2, var3[0])) {
                  return var4;
               }

               var4 = var5;
               if(!applyN(12, var3)) {
                  return var4;
               }
            }

            var4 = true;
         }
      }

      return var4;
   }

   public static boolean $Ls$V(Object var0, Object var1, Object var2, Object[] var3) {
      boolean var5 = false;
      boolean var4 = var5;
      if($Ls(var0, var1)) {
         var4 = var5;
         if($Ls(var1, var2)) {
            if(var3.length != 0) {
               var4 = var5;
               if(!$Ls(var2, var3[0])) {
                  return var4;
               }

               var4 = var5;
               if(!applyN(4, var3)) {
                  return var4;
               }
            }

            var4 = true;
         }
      }

      return var4;
   }

   public static boolean apply2(int var0, Object var1, Object var2) {
      return (1 << compare(var1, var2, true) + 3 & var0) != 0;
   }

   static boolean applyN(int var0, Object[] var1) {
      for(int var2 = 0; var2 < var1.length - 1; ++var2) {
         if(!apply2(var0, var1[var2], var1[var2 + 1])) {
            return false;
         }
      }

      return true;
   }

   public static boolean applyWithPromotion(int var0, Object var1, Object var2) {
      return checkCompareCode(compare(var1, var2, false), var0);
   }

   public static boolean checkCompareCode(int var0, int var1) {
      return (1 << var0 + 3 & var1) != 0;
   }

   static int classify(Expression var0) {
      int var2 = Arithmetic.classifyType(var0.getType());
      int var1 = var2;
      if(var2 == 4) {
         var1 = var2;
         if(var0 instanceof QuoteExp) {
            Object var4 = ((QuoteExp)var0).getValue();
            var1 = var2;
            if(var4 instanceof IntNum) {
               int var3 = ((IntNum)var4).intLength();
               if(var3 < 32) {
                  var1 = 1;
               } else {
                  var1 = var2;
                  if(var3 < 64) {
                     return 2;
                  }
               }
            }
         }
      }

      return var1;
   }

   public static int compare(Object var0, int var1, Object var2, int var3, boolean var4) {
      if(var1 >= 0 && var3 >= 0) {
         int var11;
         if(var1 < var3) {
            var11 = var3;
         } else {
            var11 = var1;
         }

         switch(var11) {
         case 1:
            var1 = Arithmetic.asInt(var0);
            var3 = Arithmetic.asInt(var2);
            if(var1 < var3) {
               return -1;
            } else {
               if(var1 > var3) {
                  return 1;
               }

               return 0;
            }
         case 2:
            long var12 = Arithmetic.asLong(var0);
            long var14 = Arithmetic.asLong(var2);
            if(var12 < var14) {
               return -1;
            } else {
               if(var12 > var14) {
                  return 1;
               }

               return 0;
            }
         case 3:
            return Arithmetic.asBigInteger(var0).compareTo(Arithmetic.asBigInteger(var2));
         case 4:
            return IntNum.compare(Arithmetic.asIntNum((Object)var0), Arithmetic.asIntNum((Object)var2));
         case 5:
            return Arithmetic.asBigDecimal(var0).compareTo(Arithmetic.asBigDecimal(var2));
         case 6:
            return RatNum.compare(Arithmetic.asRatNum(var0), Arithmetic.asRatNum(var2));
         case 7:
            if(!var4 || var1 > 6 && var3 > 6) {
               float var9 = Arithmetic.asFloat(var0);
               float var10 = Arithmetic.asFloat(var2);
               if(var9 > var10) {
                  return 1;
               } else if(var9 < var10) {
                  return -1;
               } else {
                  if(var9 == var10) {
                     return 0;
                  }

                  return -2;
               }
            }
         case 8:
         case 9:
            if(!var4 || var1 > 6 && var3 > 6) {
               double var5 = Arithmetic.asDouble(var0);
               double var7 = Arithmetic.asDouble(var2);
               if(var5 > var7) {
                  return 1;
               } else if(var5 < var7) {
                  return -1;
               } else {
                  if(var5 == var7) {
                     return 0;
                  }

                  return -2;
               }
            }
         default:
            return Arithmetic.asNumeric(var0).compare(Arithmetic.asNumeric(var2));
         }
      } else {
         return -3;
      }
   }

   public static int compare(Object var0, Object var1, boolean var2) {
      return compare(var0, Arithmetic.classifyValue(var0), var1, Arithmetic.classifyValue(var1), var2);
   }

   public static NumberCompare make(Language var0, String var1, int var2) {
      NumberCompare var3 = new NumberCompare();
      var3.language = var0;
      var3.setName(var1);
      var3.flags = var2;
      var3.setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileArith:validateApplyNumberCompare");
      return var3;
   }

   public Object apply2(Object var1, Object var2) {
      return this.getLanguage().booleanObject(apply2(this.flags, var1, var2));
   }

   public Object applyN(Object[] var1) {
      return this.getLanguage().booleanObject(applyN(this.flags, var1));
   }

   public void compile(ApplyExp var1, Compilation var2, Target var3) {
      Expression[] var7 = var1.getArgs();
      if(var7.length == 2) {
         Expression var5 = var7[0];
         Expression var6 = var7[1];
         int var14 = classify(var5);
         int var15 = classify(var6);
         CodeAttr var8 = var2.getCode();
         if(var14 > 0 && var15 > 0 && var14 <= 10 && var15 <= 10 && (var14 != 6 || var15 != 6)) {
            if(!(var3 instanceof ConditionalTarget)) {
               IfExp.compile(var1, QuoteExp.trueExp, QuoteExp.falseExp, var2, var3);
               return;
            }

            int var11 = this.flags;
            int var10 = var11;
            if(var11 == 1) {
               var10 = 20;
            }

            Object var4 = var5;
            Object var16 = var6;
            int var13 = var14;
            int var12 = var15;
            var11 = var10;
            if(var14 <= 4) {
               var4 = var5;
               var16 = var6;
               var13 = var14;
               var12 = var15;
               var11 = var10;
               if(var15 <= 4) {
                  label128: {
                     if(var14 <= 2) {
                        var4 = var5;
                        var16 = var6;
                        var13 = var14;
                        var12 = var15;
                        var11 = var10;
                        if(var15 <= 2) {
                           break label128;
                        }
                     }

                     Type[] var9 = new Type[]{Arithmetic.typeIntNum, null};
                     Expression[] var17;
                     if(var15 <= 2) {
                        var9[1] = Type.longType;
                        var11 = var10;
                        var17 = var7;
                     } else if(var14 <= 2 && (var5 instanceof QuoteExp || var6 instanceof QuoteExp || var5 instanceof ReferenceExp || var6 instanceof ReferenceExp)) {
                        var9[1] = Type.longType;
                        Expression[] var21 = new Expression[]{var6, var5};
                        var17 = var21;
                        var11 = var10;
                        if(var10 != 8) {
                           var17 = var21;
                           var11 = var10;
                           if(var10 != 20) {
                              var11 = var10 ^ 20;
                              var17 = var21;
                           }
                        }
                     } else {
                        var9[1] = Arithmetic.typeIntNum;
                        var17 = var7;
                        var11 = var10;
                     }

                     var4 = new ApplyExp(new PrimProcedure(Arithmetic.typeIntNum.getMethod("compare", var9)), var17);
                     var16 = new QuoteExp(IntNum.zero());
                     var12 = 1;
                     var13 = 1;
                  }
               }
            }

            PrimType var19;
            if(var13 <= 1 && var12 <= 1) {
               var19 = Type.intType;
            } else if(var13 <= 2 && var12 <= 2) {
               var19 = Type.longType;
            } else {
               var19 = Type.doubleType;
            }

            StackTarget var23 = new StackTarget(var19);
            ConditionalTarget var24 = (ConditionalTarget)var3;
            Object var22 = var4;
            Object var18 = var16;
            var10 = var11;
            if(var4 instanceof QuoteExp) {
               var22 = var4;
               var18 = var16;
               var10 = var11;
               if(!(var16 instanceof QuoteExp)) {
                  var22 = var16;
                  var18 = var4;
                  var10 = var11;
                  if(var11 != 8) {
                     var22 = var16;
                     var18 = var4;
                     var10 = var11;
                     if(var11 != 20) {
                        var10 = var11 ^ 20;
                        var18 = var4;
                        var22 = var16;
                     }
                  }
               }
            }

            Label var20;
            if(var24.trueBranchComesFirst) {
               var20 = var24.ifFalse;
            } else {
               var20 = var24.ifTrue;
            }

            var11 = var10;
            if(var24.trueBranchComesFirst) {
               var11 = var10 ^ 28;
            }

            short var25;
            switch(var11) {
            case 4:
               var25 = 155;
               break;
            case 8:
               var25 = 153;
               break;
            case 12:
               var25 = 158;
               break;
            case 16:
               var25 = 157;
               break;
            case 20:
               var25 = 154;
               break;
            case 24:
               var25 = 156;
               break;
            default:
               var25 = 0;
            }

            label79: {
               ((Expression)var22).compile(var2, (Target)var23);
               if(var13 <= 1 && var12 <= 1 && var18 instanceof QuoteExp) {
                  var4 = ((QuoteExp)var18).getValue();
                  if(var4 instanceof IntNum && ((IntNum)var4).isZero()) {
                     var8.emitGotoIfCompare1(var20, var25);
                     break label79;
                  }
               }

               ((Expression)var18).compile(var2, (Target)var23);
               var8.emitGotoIfCompare2(var20, var25);
            }

            var24.emitGotoFirstBranch(var8);
            return;
         }
      }

      ApplyExp.compile(var1, var2, var3);
   }

   protected final Language getLanguage() {
      return this.language;
   }

   public Type getReturnType(Expression[] var1) {
      return Type.booleanType;
   }

   public int numArgs() {
      return -4094;
   }
}
