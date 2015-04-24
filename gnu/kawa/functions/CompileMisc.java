package gnu.kawa.functions;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.expr.ApplyExp;
import gnu.expr.BeginExp;
import gnu.expr.Compilation;
import gnu.expr.ConditionalTarget;
import gnu.expr.ConsumerTarget;
import gnu.expr.Declaration;
import gnu.expr.ExpVisitor;
import gnu.expr.Expression;
import gnu.expr.IfExp;
import gnu.expr.IgnoreTarget;
import gnu.expr.InlineCalls;
import gnu.expr.Inlineable;
import gnu.expr.Keyword;
import gnu.expr.LambdaExp;
import gnu.expr.Language;
import gnu.expr.LetExp;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.StackTarget;
import gnu.expr.Target;
import gnu.expr.TryExp;
import gnu.kawa.functions.CompileTimeContinuation;
import gnu.kawa.functions.ConstantFunction0;
import gnu.kawa.functions.Convert;
import gnu.kawa.functions.Map;
import gnu.kawa.functions.Not;
import gnu.kawa.functions.ValuesMap;
import gnu.kawa.lispexpr.LangObjType;
import gnu.kawa.reflect.CompileReflect;
import gnu.kawa.reflect.Invoke;
import gnu.kawa.reflect.SlotGet;
import gnu.lists.LList;
import gnu.mapping.Namespace;
import gnu.mapping.Procedure;
import gnu.mapping.WrongArguments;
import kawa.standard.Scheme;

public class CompileMisc implements Inlineable {

   static final int CONVERT = 2;
   static final int NOT = 3;
   static Method coerceMethod;
   public static final ClassType typeContinuation = ClassType.make("kawa.lang.Continuation");
   static ClassType typeType;
   int code;
   Procedure proc;


   public CompileMisc(Procedure var1, int var2) {
      this.proc = var1;
      this.code = var2;
   }

   private static LambdaExp canInlineCallCC(ApplyExp var0) {
      Expression[] var1 = var0.getArgs();
      if(var1.length == 1) {
         Expression var2 = var1[0];
         if(var2 instanceof LambdaExp) {
            LambdaExp var3 = (LambdaExp)var2;
            if(var3.min_args == 1 && var3.max_args == 1 && !var3.firstDecl().getCanWrite()) {
               return var3;
            }
         }
      }

      return null;
   }

   public static void compileCallCC(ApplyExp var0, Compilation var1, Target var2, Procedure var3) {
      LambdaExp var9 = canInlineCallCC(var0);
      if(var9 == null) {
         ApplyExp.compile(var0, var1, var2);
      } else {
         CodeAttr var4 = var1.getCode();
         Declaration var5 = var9.firstDecl();
         if(var5.isSimple() && !var5.getCanRead() && !var5.getCanWrite()) {
            CompileTimeContinuation var11 = new CompileTimeContinuation();
            Type var8;
            if(var2 instanceof StackTarget) {
               var8 = var2.getType();
            } else {
               var8 = null;
            }

            var11.exitableBlock = var4.startExitableBlock(var8, CompileMisc.ExitThroughFinallyChecker.check(var5, var9.body));
            var11.blockTarget = var2;
            var5.setValue(new QuoteExp(var11));
            var9.body.compile(var1, (Target)var2);
            var4.endExitableBlock();
         } else {
            Variable var10 = var4.pushScope().addVariable(var4, typeContinuation, (String)null);
            Declaration var6 = new Declaration(var10);
            var4.emitNew(typeContinuation);
            var4.emitDup(typeContinuation);
            var1.loadCallContext();
            var4.emitInvokeSpecial(typeContinuation.getDeclaredMethod("<init>", 1));
            var4.emitStore(var10);
            ClassType var7;
            if(!(var2 instanceof IgnoreTarget) && !(var2 instanceof ConsumerTarget)) {
               var7 = Type.objectType;
            } else {
               var7 = null;
            }

            var4.emitTryStart(false, var7);
            (new ApplyExp(var9, new Expression[]{new ReferenceExp(var6)})).compile(var1, var2);
            if(var4.reachableHere()) {
               var4.emitLoad(var10);
               var4.emitPushInt(1);
               var4.emitPutField(typeContinuation.getField("invoked"));
            }

            var4.emitTryEnd();
            var4.emitCatchStart((Variable)null);
            var4.emitLoad(var10);
            if(var2 instanceof ConsumerTarget) {
               var1.loadCallContext();
               var4.emitInvokeStatic(typeContinuation.getDeclaredMethod("handleException$X", 3));
            } else {
               var4.emitInvokeStatic(typeContinuation.getDeclaredMethod("handleException", 2));
               var2.compileFromStack(var1, Type.objectType);
            }

            var4.emitCatchEnd();
            var4.emitTryCatchEnd();
            var4.popScope();
         }
      }
   }

   public static void compileConvert(Convert var0, ApplyExp var1, Compilation var2, Target var3) {
      Expression[] var6 = var1.getArgs();
      if(var6.length != 2) {
         throw new Error("wrong number of arguments to " + var0.getName());
      } else {
         CodeAttr var5 = var2.getCode();
         Type var4 = Scheme.getTypeValue(var6[0]);
         if(var4 != null) {
            var6[1].compile(var2, (Target)Target.pushValue(var4));
            if(var5.reachableHere()) {
               var3.compileFromStack(var2, var4);
            }

         } else {
            if(typeType == null) {
               typeType = ClassType.make("gnu.bytecode.Type");
            }

            if(coerceMethod == null) {
               coerceMethod = typeType.addMethod("coerceFromObject", Compilation.apply1args, Type.pointer_type, 1);
            }

            var6[0].compile(var2, (Type)LangObjType.typeClassType);
            var6[1].compile(var2, (Target)Target.pushObject);
            var5.emitInvokeVirtual(coerceMethod);
            var3.compileFromStack(var2, Type.pointer_type);
         }
      }
   }

   public static CompileMisc forConvert(Object var0) {
      return new CompileMisc((Procedure)var0, 2);
   }

   public static CompileMisc forNot(Object var0) {
      return new CompileMisc((Procedure)var0, 3);
   }

   public static Expression validateApplyAppendValues(ApplyExp var0, InlineCalls var1, Type var2, Procedure var3) {
      var0.visitArgs(var1);
      Expression[] var5 = var0.getArgs();
      Expression var4;
      if(var5.length == 1) {
         var4 = var5[0];
      } else {
         if(var5.length == 0) {
            return QuoteExp.voidExp;
         }

         Expression var6 = var0.inlineIfConstant(var3, (InlineCalls)var1);
         var4 = var6;
         if(var6 == var0) {
            return var0;
         }
      }

      return var4;
   }

   public static Expression validateApplyCallCC(ApplyExp var0, InlineCalls var1, Type var2, Procedure var3) {
      LambdaExp var4 = canInlineCallCC(var0);
      if(var4 != null) {
         var4.setInlineOnly(true);
         var4.returnContinuation = var0;
         var4.inlineHome = var1.getCurrentLambda();
         Declaration var5 = var4.firstDecl();
         if(!var5.getFlag(8192L)) {
            var5.setType(typeContinuation);
         }
      }

      var0.visitArgs(var1);
      return var0;
   }

   public static Expression validateApplyConstantFunction0(ApplyExp var0, InlineCalls var1, Type var2, Procedure var3) {
      var0.visitArgs(var1);
      int var4 = var0.getArgCount();
      return (Expression)(var4 != 0 && var1 != null?var1.noteError(WrongArguments.checkArgCount(var3, var4)):((ConstantFunction0)var3).constant);
   }

   public static Expression validateApplyConvert(ApplyExp var0, InlineCalls var1, Type var2, Procedure var3) {
      Compilation var5 = var1.getCompilation();
      Language var4 = var5.getLanguage();
      Expression[] var6 = var0.getArgs();
      if(var6.length == 2) {
         var6[0] = var1.visit(var6[0], (Type)null);
         Type var7 = var4.getTypeFor((Expression)var6[0]);
         if(var7 instanceof Type) {
            var6[0] = new QuoteExp(var7);
            var6[1] = var1.visit(var6[1], (Type)var7);
            CompileReflect.checkKnownClass(var7, var5);
            var0.setType(var7);
            return var0;
         }
      }

      var0.visitArgs(var1);
      return var0;
   }

   public static Expression validateApplyFormat(ApplyExp var0, InlineCalls var1, Type var2, Procedure var3) {
      var0.visitArgs(var1);
      ClassType var11 = Type.objectType;
      Expression[] var10 = var0.getArgs();
      Object var8 = var11;
      if(var10.length > 0) {
         ClassType var9 = ClassType.make("gnu.kawa.functions.Format");
         Object var4 = var10[0].valueIfConstant();
         Type var5 = var10[0].getType();
         Expression[] var7;
         if(var4 == Boolean.FALSE || var5.isSubtype(LangObjType.stringType)) {
            byte var6;
            if(var4 == Boolean.FALSE) {
               var6 = 1;
            } else {
               var6 = 0;
            }

            var7 = new Expression[var10.length + 1 - var6];
            var7[0] = new QuoteExp(Integer.valueOf(0), Type.intType);
            System.arraycopy(var10, var6, var7, 1, var7.length - 1);
            var0 = new ApplyExp(var9.getDeclaredMethod("formatToString", 2), var7);
            var0.setType(Type.javalangStringType);
            return var0;
         }

         if(var4 == Boolean.TRUE || var5.isSubtype(ClassType.make("java.io.Writer"))) {
            var7 = var10;
            if(var4 == Boolean.TRUE) {
               var7 = new Expression[var10.length];
               var7[0] = QuoteExp.nullExp;
               System.arraycopy(var10, 1, var7, 1, var10.length - 1);
            }

            var0 = new ApplyExp(var9.getDeclaredMethod("formatToWriter", 3), var7);
            var0.setType(Type.voidType);
            return var0;
         }

         var8 = var11;
         if(var5.isSubtype(ClassType.make("java.io.OutputStream"))) {
            var8 = Type.voidType;
         }
      }

      var0.setType((Type)var8);
      return null;
   }

   public static Expression validateApplyMakeProcedure(ApplyExp var0, InlineCalls var1, Type var2, Procedure var3) {
      ((ApplyExp)var0).visitArgs(var1);
      Expression[] var6 = ((ApplyExp)var0).getArgs();
      int var12 = var6.length;
      Expression var14 = null;
      int var10 = 0;
      String var17 = null;

      int var8;
      int var9;
      String var18;
      for(var9 = 0; var9 < var12; var17 = var18) {
         Expression var16;
         label48: {
            var16 = var6[var9];
            if(var16 instanceof QuoteExp) {
               Object var4 = ((QuoteExp)var16).getValue();
               if(var4 instanceof Keyword) {
                  String var7 = ((Keyword)var4).getName();
                  int var11 = var9 + 1;
                  Expression var5 = var6[var11];
                  if(var7 == "name") {
                     var8 = var10;
                     var9 = var11;
                     var16 = var14;
                     var18 = var17;
                     if(var5 instanceof QuoteExp) {
                        var18 = ((QuoteExp)var5).getValue().toString();
                        var16 = var14;
                        var9 = var11;
                        var8 = var10;
                     }
                  } else {
                     var8 = var10;
                     var9 = var11;
                     var16 = var14;
                     var18 = var17;
                     if(var7 == "method") {
                        var8 = var10 + 1;
                        var16 = var5;
                        var9 = var11;
                        var18 = var17;
                     }
                  }
                  break label48;
               }
            }

            var8 = var10 + 1;
            var18 = var17;
         }

         ++var9;
         var10 = var8;
         var14 = var16;
      }

      if(var10 == 1 && var14 instanceof LambdaExp) {
         LambdaExp var19 = (LambdaExp)var14;
         var8 = 0;

         while(true) {
            var0 = var14;
            if(var8 >= var12) {
               break;
            }

            Expression var13 = var6[var8];
            var9 = var8;
            if(var13 instanceof QuoteExp) {
               var0 = ((QuoteExp)var13).getValue();
               var9 = var8;
               if(var0 instanceof Keyword) {
                  String var15 = ((Keyword)var0).getName();
                  ++var8;
                  Expression var20 = var6[var8];
                  if(var15 == "name") {
                     var19.setName(var17);
                     var9 = var8;
                  } else {
                     var9 = var8;
                     if(var15 != "method") {
                        var19.setProperty(Namespace.EmptyNamespace.getSymbol(var15), var20);
                        var9 = var8;
                     }
                  }
               }
            }

            var8 = var9 + 1;
         }
      }

      return (Expression)var0;
   }

   public static Expression validateApplyMap(ApplyExp var0, InlineCalls var1, Type var2, Procedure var3) {
      Map var5 = (Map)var3;
      boolean var18 = var5.collect;
      var0.visitArgs(var1);
      Expression[] var6 = var0.getArgs();
      int var15 = var6.length;
      if(var15 < 2) {
         return var0;
      } else {
         --var15;
         Object var22 = var6[0];
         boolean var16;
         if(!((Expression)var22).side_effects()) {
            var16 = true;
         } else {
            var16 = false;
         }

         LetExp var24 = new LetExp(new Expression[]{(Expression)var22});
         Declaration var11 = var24.addDeclaration("%proc", Compilation.typeProcedure);
         var11.noteValue(var6[0]);
         Expression[] var19 = new Expression[1];
         LetExp var4 = new LetExp(var19);
         var24.setBody(var4);
         int var17;
         if(var18) {
            var17 = var15 + 1;
         } else {
            var17 = var15;
         }

         LambdaExp var7 = new LambdaExp(var17);
         var19[0] = var7;
         Declaration var8 = var4.addDeclaration("%loop");
         var8.noteValue(var7);
         var19 = new Expression[var15];
         LetExp var10 = new LetExp(var19);
         Declaration[] var9 = new Declaration[var15];
         Declaration[] var12 = new Declaration[var15];

         for(var17 = 0; var17 < var15; ++var17) {
            String var13 = "arg" + var17;
            var9[var17] = var7.addDeclaration(var13);
            var12[var17] = var10.addDeclaration(var13, Compilation.typePair);
            var19[var17] = new ReferenceExp(var9[var17]);
            var12[var17].noteValue(var19[var17]);
         }

         Declaration var20;
         if(var18) {
            var20 = var7.addDeclaration("result");
         } else {
            var20 = null;
         }

         Expression[] var14 = new Expression[var15 + 1];
         if(var18) {
            var17 = var15 + 1;
         } else {
            var17 = var15;
         }

         Expression[] var30 = new Expression[var17];

         for(var17 = 0; var17 < var15; ++var17) {
            var14[var17 + 1] = var1.visitApplyOnly(SlotGet.makeGetField(new ReferenceExp(var12[var17]), "car"), (Type)null);
            var30[var17] = var1.visitApplyOnly(SlotGet.makeGetField(new ReferenceExp(var12[var17]), "cdr"), (Type)null);
         }

         if(!var16) {
            var22 = new ReferenceExp(var11);
         }

         var14[0] = (Expression)var22;
         Expression var26 = var1.visitApplyOnly(new ApplyExp(new ReferenceExp(var5.applyFieldDecl), var14), (Type)null);
         if(var18) {
            ReferenceExp var25 = new ReferenceExp(var20);
            var30[var15] = Invoke.makeInvokeStatic(Compilation.typePair, "make", new Expression[]{var26, var25});
         }

         var22 = var1.visitApplyOnly(new ApplyExp(new ReferenceExp(var8), var30), (Type)null);
         if(!var18) {
            var22 = new BeginExp(var26, (Expression)var22);
         }

         var7.body = (Expression)var22;
         var10.setBody(var7.body);
         var7.body = var10;
         if(var18) {
            var17 = var15 + 1;
         } else {
            var17 = var15;
         }

         Expression[] var27 = new Expression[var17];
         QuoteExp var29 = new QuoteExp(LList.Empty);
         var17 = var15;

         while(true) {
            --var17;
            if(var17 < 0) {
               if(var18) {
                  var27[var15] = var29;
               }

               Expression var21 = var1.visitApplyOnly(new ApplyExp(new ReferenceExp(var8), var27), (Type)null);
               Object var23 = var21;
               if(var18) {
                  var23 = Invoke.makeInvokeStatic(Compilation.scmListType, "reverseInPlace", new Expression[]{var21});
               }

               var4.setBody((Expression)var23);
               if(var16) {
                  return var4;
               }

               return var24;
            }

            ReferenceExp var28 = new ReferenceExp(var9[var17]);
            if(var18) {
               var22 = new ReferenceExp(var20);
            } else {
               var22 = QuoteExp.voidExp;
            }

            var7.body = new IfExp(var1.visitApplyOnly(new ApplyExp(var5.isEq, new Expression[]{var28, var29}), (Type)null), (Expression)var22, var7.body);
            var27[var17] = var6[var17 + 1];
         }
      }
   }

   public static Expression validateApplyNot(ApplyExp var0, InlineCalls var1, Type var2, Procedure var3) {
      var0.visitArgs(var1);
      var0.setType(var1.getCompilation().getLanguage().getTypeFor((Class)Boolean.TYPE));
      return var0.inlineIfConstant(var3, (InlineCalls)var1);
   }

   public static Expression validateApplyValuesMap(ApplyExp var0, InlineCalls var1, Type var2, Procedure var3) {
      var0.visitArgs(var1);
      LambdaExp var4 = ValuesMap.canInline(var0, (ValuesMap)var3);
      if(var4 != null) {
         var4.setInlineOnly(true);
         var4.returnContinuation = var0;
         var4.inlineHome = var1.getCurrentLambda();
      }

      return var0;
   }

   public void compile(ApplyExp var1, Compilation var2, Target var3) {
      switch(this.code) {
      case 2:
         compileConvert((Convert)this.proc, var1, var2, var3);
         return;
      case 3:
         this.compileNot((Not)this.proc, var1, var2, var3);
         return;
      default:
         throw new Error();
      }
   }

   public void compileNot(Not var1, ApplyExp var2, Compilation var3, Target var4) {
      Expression var10 = var2.getArgs()[0];
      Language var8 = var1.language;
      if(var4 instanceof ConditionalTarget) {
         ConditionalTarget var11 = (ConditionalTarget)var4;
         ConditionalTarget var9 = new ConditionalTarget(var11.ifFalse, var11.ifTrue, var8);
         boolean var7;
         if(!var11.trueBranchComesFirst) {
            var7 = true;
         } else {
            var7 = false;
         }

         var9.trueBranchComesFirst = var7;
         var10.compile(var3, (Target)var9);
      } else {
         CodeAttr var5 = var3.getCode();
         Type var6 = var4.getType();
         if(var4 instanceof StackTarget && var6.getSignature().charAt(0) == 90) {
            var10.compile(var3, (Target)var4);
            var5.emitNot(var4.getType());
         } else {
            QuoteExp var12 = QuoteExp.getInstance(var8.booleanObject(true));
            IfExp.compile(var10, QuoteExp.getInstance(var8.booleanObject(false)), var12, var3, var4);
         }
      }
   }

   static class ExitThroughFinallyChecker extends ExpVisitor {

      Declaration decl;


      public static boolean check(Declaration var0, Expression var1) {
         CompileMisc.ExitThroughFinallyChecker var2 = new CompileMisc.ExitThroughFinallyChecker();
         var2.decl = var0;
         var2.visit(var1, (Object)null);
         return var2.exitValue != null;
      }

      protected Expression defaultValue(Expression var1, TryExp var2) {
         return var1;
      }

      protected Expression visitReferenceExp(ReferenceExp var1, TryExp var2) {
         if(this.decl == var1.getBinding() && var2 != null) {
            this.exitValue = Boolean.TRUE;
         }

         return var1;
      }

      protected Expression visitTryExp(TryExp var1, TryExp var2) {
         if(var1.getFinallyClause() != null) {
            var2 = var1;
         }

         this.visitExpression(var1, var2);
         return var1;
      }
   }
}
