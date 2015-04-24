package gnu.kawa.functions;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;
import gnu.bytecode.PrimType;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.IgnoreTarget;
import gnu.expr.InlineCalls;
import gnu.expr.Inlineable;
import gnu.expr.PrimProcedure;
import gnu.expr.QuoteExp;
import gnu.expr.StackTarget;
import gnu.expr.Target;
import gnu.kawa.functions.AddOp;
import gnu.kawa.functions.ArithOp;
import gnu.kawa.functions.Arithmetic;
import gnu.kawa.functions.BitwiseOp;
import gnu.kawa.functions.DivideOp;
import gnu.kawa.functions.NumberPredicate;
import gnu.kawa.lispexpr.LangObjType;
import gnu.kawa.lispexpr.LangPrimType;
import gnu.mapping.Procedure;
import gnu.math.IntNum;

public class CompileArith implements Inlineable {

   public static CompileArith $Mn = new CompileArith(AddOp.$Mn, 2);
   public static CompileArith $Pl = new CompileArith(AddOp.$Pl, 1);
   int op;
   Procedure proc;


   CompileArith(Object var1, int var2) {
      this.proc = (Procedure)var1;
      this.op = var2;
   }

   static int adjustReturnKind(int var0, int var1) {
      if(var1 >= 4 && var1 <= 7 && var0 > 0) {
         switch(var1) {
         case 4:
            if(var0 <= 4) {
               return 6;
            }
            break;
         case 5:
            if(var0 <= 10 && var0 != 7) {
               return 8;
            }
         case 6:
         default:
            break;
         case 7:
            if(var0 <= 10) {
               return 4;
            }
         }
      }

      return var0;
   }

   public static boolean appropriateIntConstant(Expression[] var0, int var1, InlineCalls var2) {
      QuoteExp var3 = var2.fixIntValue(var0[var1]);
      if(var3 != null) {
         var0[var1] = var3;
         return true;
      } else {
         return false;
      }
   }

   public static boolean appropriateLongConstant(Expression[] var0, int var1, InlineCalls var2) {
      QuoteExp var3 = var2.fixLongValue(var0[var1]);
      if(var3 != null) {
         var0[var1] = var3;
         return true;
      } else {
         return false;
      }
   }

   public static CompileArith forBitwise(Object var0) {
      return new CompileArith(var0, ((BitwiseOp)var0).op);
   }

   public static CompileArith forDiv(Object var0) {
      return new CompileArith(var0, ((DivideOp)var0).op);
   }

   public static CompileArith forMul(Object var0) {
      return new CompileArith(var0, 3);
   }

   public static int getReturnKind(int var0, int var1, int var2) {
      if(var2 >= 9 && var2 <= 12) {
         return var0;
      } else {
         if(var0 > 0) {
            var2 = var1;
            if(var0 <= var1) {
               return var2;
            }

            var2 = var1;
            if(var1 <= 0) {
               return var2;
            }
         }

         var2 = var0;
         return var2;
      }
   }

   public static Expression pairwise(Procedure var0, Expression var1, Expression[] var2, InlineCalls var3) {
      int var7 = var2.length;
      Object var4 = var2[0];

      for(int var6 = 1; var6 < var7; ++var6) {
         var4 = new ApplyExp(var1, new Expression[]{(Expression)var4, var2[var6]});
         Expression var5 = var3.maybeInline((ApplyExp)var4, (Type)null, var0);
         if(var5 != null) {
            var4 = var5;
         }
      }

      return (Expression)var4;
   }

   public static Expression validateApplyAdd(AddOp var0, ApplyExp var1, InlineCalls var2) {
      Expression[] var3 = var1.getArgs();
      ApplyExp var9 = var1;
      if(var3.length == 1) {
         var9 = var1;
         if(var0.plusOrMinus < 0) {
            Type var7 = var3[0].getType();
            var9 = var1;
            if(var7 instanceof PrimType) {
               char var6 = var7.getSignature().charAt(0);
               var2 = null;
               byte var5 = 0;
               byte var4 = var5;
               PrimType var8 = var2;
               if(var6 != 86) {
                  var4 = var5;
                  var8 = var2;
                  if(var6 != 90) {
                     if(var6 == 67) {
                        var8 = var2;
                        var4 = var5;
                     } else if(var6 == 68) {
                        var4 = 119;
                        var8 = LangPrimType.doubleType;
                     } else if(var6 == 70) {
                        var4 = 118;
                        var8 = LangPrimType.floatType;
                     } else if(var6 == 74) {
                        var4 = 117;
                        var8 = LangPrimType.longType;
                     } else {
                        var4 = 116;
                        var8 = LangPrimType.intType;
                     }
                  }
               }

               var9 = var1;
               if(var8 != null) {
                  var9 = new ApplyExp(PrimProcedure.makeBuiltinUnary(var4, var8), var3);
               }
            }
         }
      }

      return var9;
   }

   public static Expression validateApplyArithOp(ApplyExp var0, InlineCalls var1, Type var2, Procedure var3) {
      int var8 = ((ArithOp)var3).op;
      var0.visitArgs(var1);
      Expression[] var10 = var0.getArgs();
      Object var11;
      if(var10.length > 2) {
         var11 = pairwise(var3, var0.getFunction(), var10, var1);
      } else {
         Expression var4 = var0.inlineIfConstant(var3, (InlineCalls)var1);
         if(var4 != var0) {
            return var4;
         }

         int var5 = 0;
         if(var10.length == 2 || var10.length == 1) {
            int var6 = Arithmetic.classifyType(var10[0].getType());
            if(var10.length == 2 && (var8 < 9 || var8 > 12)) {
               int var9 = Arithmetic.classifyType(var10[1].getType());
               int var7 = getReturnKind(var6, var9, var8);
               var5 = var7;
               if(var7 == 4) {
                  if(var6 == 1 && appropriateIntConstant(var10, 1, var1)) {
                     var5 = 1;
                  } else if(var9 == 1 && appropriateIntConstant(var10, 0, var1)) {
                     var5 = 1;
                  } else if(var6 == 2 && appropriateLongConstant(var10, 1, var1)) {
                     var5 = 2;
                  } else {
                     var5 = var7;
                     if(var9 == 2) {
                        var5 = var7;
                        if(appropriateLongConstant(var10, 0, var1)) {
                           var5 = 2;
                        }
                     }
                  }
               }
            } else {
               var5 = var6;
            }

            var5 = adjustReturnKind(var5, var8);
            var0.setType(Arithmetic.kindType(var5));
         }

         var11 = var0;
         if(var1.getCompilation().mustCompile) {
            switch(var8) {
            case 1:
            case 2:
               return validateApplyAdd((AddOp)var3, var0, var1);
            case 3:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            default:
               return var0;
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
               return validateApplyDiv((DivideOp)var3, var0, var1);
            case 16:
               var11 = var0;
               if(var5 > 0) {
                  return validateApplyNot(var0, var5, var1);
               }
            }
         }
      }

      return (Expression)var11;
   }

   public static Expression validateApplyDiv(DivideOp var0, ApplyExp var1, InlineCalls var2) {
      Expression[] var5 = var1.getArgs();
      ApplyExp var3 = var1;
      if(var5.length == 1) {
         QuoteExp var4 = QuoteExp.getInstance(IntNum.one());
         Expression var6 = var5[0];
         var3 = new ApplyExp(var1.getFunction(), new Expression[]{var4, var6});
      }

      return var3;
   }

   public static Expression validateApplyNot(ApplyExp var0, int var1, InlineCalls var2) {
      Object var3 = var0;
      if(var0.getArgCount() == 1) {
         Expression var6 = var0.getArg(0);
         if(var1 != 1 && var1 != 2) {
            String var5;
            if(var1 == 4) {
               var5 = "gnu.math.BitOps";
            } else if(var1 == 3) {
               var5 = "java.meth.BigInteger";
            } else {
               var5 = null;
            }

            var3 = var0;
            if(var5 != null) {
               return new ApplyExp(ClassType.make(var5).getDeclaredMethod("not", 1), var0.getArgs());
            }
         } else {
            QuoteExp var4 = QuoteExp.getInstance(IntNum.minusOne());
            var3 = var2.visitApplyOnly(new ApplyExp(BitwiseOp.xor, new Expression[]{var6, var4}), (Type)null);
         }
      }

      return (Expression)var3;
   }

   public static Expression validateApplyNumberCompare(ApplyExp var0, InlineCalls var1, Type var2, Procedure var3) {
      var0.visitArgs(var1);
      Expression var4 = var0.inlineIfConstant(var3, (InlineCalls)var1);
      return (Expression)(var4 != var0?var4:var0);
   }

   public static Expression validateApplyNumberPredicate(ApplyExp var0, InlineCalls var1, Type var2, Procedure var3) {
      int var4 = ((NumberPredicate)var3).op;
      Expression[] var5 = var0.getArgs();
      var5[0] = var1.visit(var5[0], (Type)LangObjType.integerType);
      var0.setType(Type.booleanType);
      return var0;
   }

   public void compile(ApplyExp var1, Compilation var2, Target var3) {
      Expression[] var6 = var1.getArgs();
      int var10 = var6.length;
      if(var10 == 0) {
         var2.compileConstant(((ArithOp)this.proc).defaultResult(), var3);
      } else if(var10 != 1 && !(var3 instanceof IgnoreTarget)) {
         int var11 = Arithmetic.classifyType(var6[0].getType());
         int var12 = Arithmetic.classifyType(var6[1].getType());
         int var9 = getReturnKind(var11, var12, this.op);
         Object var4 = Arithmetic.kindType(var9);
         if(var9 != 0 && var10 == 2) {
            int var8 = Arithmetic.classifyType(var3.getType());
            if((var8 == 1 || var8 == 2) && var9 >= 1 && var9 <= 4) {
               if(var8 == 1) {
                  var4 = LangPrimType.intType;
                  var8 = var8;
               } else {
                  var4 = LangPrimType.longType;
                  var8 = var8;
               }
            } else if((var8 == 8 || var8 == 7) && var9 > 2 && var9 <= 10) {
               if(var8 == 7) {
                  var4 = LangPrimType.floatType;
               } else {
                  var4 = LangPrimType.doubleType;
               }

               var8 = var8;
            } else if(var9 == 7) {
               var4 = LangPrimType.floatType;
               var8 = var9;
            } else if(var9 != 8 && var9 != 9) {
               var8 = var9;
            } else {
               var8 = 8;
               var4 = LangPrimType.doubleType;
            }

            var9 = var8;
            if(this.op >= 4) {
               var9 = var8;
               if(this.op <= 8) {
                  label256: {
                     DivideOp var5 = (DivideOp)this.proc;
                     if(var5.op == 4) {
                        var9 = var8;
                        if(var8 <= 4) {
                           break label256;
                        }

                        var9 = var8;
                        if(var8 >= 6) {
                           break label256;
                        }

                        if(var8 <= 9) {
                           var9 = var8;
                           break label256;
                        }
                     }

                     if((var5.op != 5 || var8 > 10 || var8 == 7) && (var5.op != 4 || var8 != 10)) {
                        label262: {
                           if(var5.op == 7 || var5.op == 6 && var8 <= 4) {
                              var9 = var8;
                              if(var5.getRoundingMode() == 3) {
                                 break label262;
                              }

                              var9 = var8;
                              if(var8 == 4) {
                                 break label262;
                              }

                              var9 = var8;
                              if(var8 == 7) {
                                 break label262;
                              }

                              var9 = var8;
                              if(var8 == 8) {
                                 break label262;
                              }
                           }

                           if(var5.op == 8) {
                              var9 = var8;
                              if(var5.getRoundingMode() == 3) {
                                 break label262;
                              }

                              var9 = var8;
                              if(var8 == 4) {
                                 break label262;
                              }
                           }

                           ApplyExp.compile(var1, var2, var3);
                           return;
                        }
                     } else {
                        var9 = 8;
                     }
                  }
               }
            }

            Target var17;
            Object var16;
            if(this.op == 4 && var9 <= 10 && var9 != 8 && var9 != 7) {
               LangObjType var15;
               Method var18;
               if(var9 != 6 && var9 <= 4) {
                  var15 = Arithmetic.typeIntNum;
                  var18 = Arithmetic.typeRatNum.getDeclaredMethod("make", 2);
               } else {
                  if(var9 == 6) {
                     var15 = Arithmetic.typeRatNum;
                  } else {
                     var15 = Arithmetic.typeRealNum;
                  }

                  Method var19 = var15.getDeclaredMethod("divide", 2);
                  var15 = var15;
                  var18 = var19;
               }

               var17 = StackTarget.getInstance(var15);
               var6[0].compile(var2, (Target)var17);
               var6[1].compile(var2, (Target)var17);
               var2.getCode().emitInvokeStatic(var18);
               var16 = var15;
            } else if(var9 == 4 && (this.op == 1 || this.op == 3 || this.op == 2 || this.op == 13 || this.op == 14 || this.op == 15 || this.op == 7 || this.op == 8 || this.op >= 9 && this.op <= 11)) {
               this.compileIntNum(var6[0], var6[1], var11, var12, var2);
               var16 = var4;
            } else {
               if(var9 != 1 && var9 != 2 && (var9 != 7 && var9 != 8 || this.op > 8 && this.op < 13)) {
                  ApplyExp.compile(var1, var2, var3);
                  return;
               }

               Target var13 = StackTarget.getInstance((Type)var4);
               CodeAttr var7 = var2.getCode();
               var8 = 0;

               while(true) {
                  var16 = var4;
                  if(var8 >= var10) {
                     break;
                  }

                  var17 = var13;
                  if(var8 == 1) {
                     var17 = var13;
                     if(this.op >= 9) {
                        var17 = var13;
                        if(this.op <= 12) {
                           var17 = StackTarget.getInstance(Type.intType);
                        }
                     }
                  }

                  var6[var8].compile(var2, (Target)var17);
                  if(var8 != 0) {
                     switch(var9) {
                     case 1:
                     case 2:
                     case 7:
                     case 8:
                        if(this.op == 9) {
                           PrimType var14 = Type.intType;
                           var7.emitInvokeStatic(ClassType.make("gnu.math.IntNum").getDeclaredMethod("shift", new Type[]{(Type)var4, var14}));
                        } else {
                           var7.emitBinop(this.primitiveOpcode(), (PrimType)((Type)var4).getImplementationType());
                        }
                     case 3:
                     case 4:
                     case 5:
                     case 6:
                     }
                  }

                  ++var8;
                  var13 = var17;
               }
            }

            var3.compileFromStack(var2, (Type)var16);
         } else {
            ApplyExp.compile(var1, var2, var3);
         }
      } else {
         ApplyExp.compile(var1, var2, var3);
      }
   }

   public boolean compileIntNum(Expression var1, Expression var2, int var3, int var4, Compilation var5) {
      Object var6;
      if(this.op == 2 && var2 instanceof QuoteExp) {
         var6 = var2.valueIfConstant();
         long var14;
         boolean var16;
         if(var4 <= 2) {
            var14 = ((Number)var6).longValue();
            if(var14 > -2147483648L && var14 <= 2147483647L) {
               var16 = true;
            } else {
               var16 = false;
            }
         } else if(var6 instanceof IntNum) {
            IntNum var22 = (IntNum)var6;
            var14 = var22.longValue();
            var16 = var22.inRange(-2147483647L, 2147483647L);
         } else {
            var16 = false;
            var14 = 0L;
         }

         if(var16) {
            return $Pl.compileIntNum(var1, QuoteExp.getInstance(Integer.valueOf((int)(-var14))), var3, 1, var5);
         }
      }

      boolean var13;
      if(this.op != 1 && this.op != 3) {
         var13 = false;
      } else {
         var13 = true;
      }

      Object var7;
      boolean var19;
      if(var13) {
         int var25 = var3;
         if(InlineCalls.checkIntValue(var1) != null) {
            var25 = 1;
         }

         if(InlineCalls.checkIntValue(var2) != null) {
            var4 = 1;
         }

         if(var25 == 1 && var4 != 1) {
            var19 = true;
         } else {
            var19 = false;
         }

         if(var19 && (!var1.side_effects() || !var2.side_effects())) {
            return this.compileIntNum(var2, var1, var4, var25, var5);
         }

         if(var25 == 1) {
            var7 = Type.intType;
         } else {
            var7 = Arithmetic.typeIntNum;
         }

         if(var4 == 1) {
            var6 = Type.intType;
         } else {
            var6 = Arithmetic.typeIntNum;
         }
      } else if(this.op >= 9 && this.op <= 12) {
         var7 = Arithmetic.typeIntNum;
         var6 = Type.intType;
         var19 = false;
      } else {
         var6 = Arithmetic.typeIntNum;
         var7 = var6;
         var19 = false;
      }

      var1.compile(var5, (Type)var7);
      var2.compile(var5, (Type)var6);
      CodeAttr var11 = var5.getCode();
      Object var8 = var6;
      if(var19) {
         var11.emitSwap();
         var7 = Arithmetic.typeIntNum;
         var8 = LangPrimType.intType;
      }

      String var17 = null;
      String var18 = null;
      Object var9 = null;
      LangObjType var10 = Arithmetic.typeIntNum;
      Object var21;
      Type[] var20;
      String var23;
      switch(this.op) {
      case 1:
         var17 = "add";
         var21 = var10;
         var20 = (Type[])var9;
         break;
      case 2:
         var17 = "sub";
         var20 = (Type[])var9;
         var21 = var10;
         break;
      case 3:
         var17 = "times";
         var20 = (Type[])var9;
         var21 = var10;
         break;
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
         if(this.op == 8) {
            var23 = "remainder";
         } else {
            var23 = "quotient";
         }

         DivideOp var12 = (DivideOp)this.proc;
         if(this.op == 8 && var12.rounding_mode == 1) {
            var17 = "modulo";
            var20 = (Type[])var9;
            var21 = var10;
         } else {
            var20 = (Type[])var9;
            var21 = var10;
            var17 = var23;
            if(var12.rounding_mode != 3) {
               var11.emitPushInt(var12.rounding_mode);
               var20 = new Type[]{(Type)var7, (Type)var8, Type.intType};
               var21 = var10;
               var17 = var23;
            }
         }
         break;
      case 9:
         var17 = "shift";
         var20 = (Type[])var9;
         var21 = var10;
         break;
      case 10:
      case 11:
         if(this.op == 10) {
            var17 = "shiftLeft";
         } else {
            var17 = "shiftRight";
         }

         var21 = ClassType.make("gnu.kawa.functions.BitwiseOp");
         var20 = (Type[])var9;
         break;
      case 12:
      default:
         throw new Error();
      case 13:
         var18 = "and";
      case 14:
         var17 = var18;
         if(var18 == null) {
            var17 = "ior";
         }
      case 15:
         var23 = var17;
         if(var17 == null) {
            var23 = "xor";
         }

         var21 = ClassType.make("gnu.math.BitOps");
         var20 = (Type[])var9;
         var17 = var23;
      }

      Type[] var24 = var20;
      if(var20 == null) {
         var24 = new Type[]{(Type)var7, (Type)var8};
      }

      var11.emitInvokeStatic(((ObjectType)var21).getMethod(var17, var24));
      return true;
   }

   public int getReturnKind(Expression[] var1) {
      int var7 = var1.length;
      int var5;
      if(var7 == 0) {
         var5 = 4;
      } else {
         ClassType var2 = Type.pointer_type;
         int var3 = 0;
         int var4 = 0;

         while(true) {
            var5 = var3;
            if(var4 >= var7) {
               break;
            }

            label20: {
               int var6 = Arithmetic.classifyType(var1[var4].getType());
               if(var4 != 0 && var6 != 0) {
                  var5 = var3;
                  if(var6 <= var3) {
                     break label20;
                  }
               }

               var5 = var6;
            }

            ++var4;
            var3 = var5;
         }
      }

      return var5;
   }

   public Type getReturnType(Expression[] var1) {
      return Arithmetic.kindType(adjustReturnKind(this.getReturnKind(var1), this.op));
   }

   public int primitiveOpcode() {
      switch(this.op) {
      case 1:
         return 96;
      case 2:
         return 100;
      case 3:
         return 104;
      case 4:
      case 5:
      case 6:
      case 7:
         return 108;
      case 8:
         return 112;
      case 9:
      default:
         return -1;
      case 10:
         return 120;
      case 11:
         return 122;
      case 12:
         return 124;
      case 13:
         return 126;
      case 14:
         return 128;
      case 15:
         return 130;
      }
   }
}
