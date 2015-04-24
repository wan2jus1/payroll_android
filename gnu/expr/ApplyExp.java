package gnu.expr;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.PrimType;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.expr.AccessExp;
import gnu.expr.Compilation;
import gnu.expr.ConsumerTarget;
import gnu.expr.Declaration;
import gnu.expr.ExpVisitor;
import gnu.expr.Expression;
import gnu.expr.IgnoreTarget;
import gnu.expr.InlineCalls;
import gnu.expr.Inlineable;
import gnu.expr.LambdaExp;
import gnu.expr.PrimProcedure;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.SetExp;
import gnu.expr.StackTarget;
import gnu.expr.Target;
import gnu.kawa.util.IdentityHashTable;
import gnu.mapping.CallContext;
import gnu.mapping.OutPort;
import gnu.mapping.Procedure;
import gnu.text.SourceMessages;

public class ApplyExp extends Expression {

   public static final int INLINE_IF_CONSTANT = 4;
   public static final int MAY_CONTAIN_BACK_JUMP = 8;
   public static final int TAILCALL = 2;
   Expression[] args;
   LambdaExp context;
   Expression func;
   public ApplyExp nextCall;
   protected Type type;


   public ApplyExp(Method var1, Expression[] var2) {
      this.func = new QuoteExp(new PrimProcedure(var1));
      this.args = var2;
   }

   public ApplyExp(Expression var1, Expression[] var2) {
      this.func = var1;
      this.args = var2;
   }

   public ApplyExp(Procedure var1, Expression[] var2) {
      this.func = new QuoteExp(var1);
      this.args = var2;
   }

   public static Inlineable asInlineable(Procedure var0) {
      return var0 instanceof Inlineable?(Inlineable)var0:(Inlineable)Procedure.compilerKey.get(var0);
   }

   public static void compile(ApplyExp var0, Compilation var1, Target var2) {
      compile(var0, var1, var2, false);
   }

   static void compile(ApplyExp var0, Compilation var1, Target var2, boolean var3) {
      LambdaExp var4;
      Object var5;
      Declaration var6;
      Method var8;
      Expression var10;
      int var14;
      label248: {
         var14 = var0.args.length;
         var10 = var0.func;
         Object var9 = null;
         var4 = null;
         LambdaExp var7 = null;
         var5 = null;
         var6 = null;
         var8 = null;
         if(var10 instanceof LambdaExp) {
            var7 = (LambdaExp)var10;
            var4 = var7;
            if(var7.getName() == null) {
               var5 = null;
               var4 = var7;
               break label248;
            }
         } else {
            Object var24;
            if(var10 instanceof ReferenceExp) {
               ReferenceExp var19 = (ReferenceExp)var10;
               Declaration var18 = var19.contextDecl();

               ReferenceExp var23;
               for(var6 = var19.binding; var6 != null && var6.isAlias() && var6.value instanceof ReferenceExp; var18 = var23.contextDecl()) {
                  var23 = (ReferenceExp)var6.value;
                  if(var18 != null || var6.needsContext() || var23.binding == null) {
                     break;
                  }

                  var6 = var23.binding;
               }

               var24 = var8;
               if(!var6.getFlag(65536L)) {
                  Expression var11 = var6.getValue();
                  var6.getName();
                  LambdaExp var20 = (LambdaExp)var9;
                  if(var11 != null) {
                     var20 = (LambdaExp)var9;
                     if(var11 instanceof LambdaExp) {
                        var20 = (LambdaExp)var11;
                     }
                  }

                  var4 = var20;
                  var24 = var8;
                  if(var11 != null) {
                     var4 = var20;
                     var24 = var8;
                     if(var11 instanceof QuoteExp) {
                        var24 = ((QuoteExp)var11).getValue();
                        var4 = var20;
                     }
                  }
               }

               var6 = var18;
               var5 = var24;
               break label248;
            }

            var4 = var7;
            if(var10 instanceof QuoteExp) {
               var24 = ((QuoteExp)var10).getValue();
               var4 = (LambdaExp)var5;
               var5 = var24;
               break label248;
            }
         }

         var5 = null;
      }

      int var12;
      if(var3 && var5 instanceof Procedure) {
         label265: {
            Procedure var28 = (Procedure)var5;
            if(var2 instanceof IgnoreTarget && var28.isSideEffectFree()) {
               for(var12 = 0; var12 < var14; ++var12) {
                  var0.args[var12].compile(var1, (Target)var2);
               }
            } else {
               try {
                  var3 = inlineCompile(var28, var0, var1, var2);
               } catch (Throwable var15) {
                  var1.getMessages().error('e', (String)("caught exception in inline-compiler for " + var5 + " - " + var15), (Throwable)var15);
                  return;
               }

               if(!var3) {
                  break label265;
               }
            }

            return;
         }
      }

      CodeAttr var29 = var1.getCode();
      boolean var30;
      if(var4 != null) {
         if(var4.max_args >= 0 && var14 > var4.max_args || var14 < var4.min_args) {
            throw new Error("internal error - wrong number of parameters for " + var4);
         }

         var12 = var4.getCallConvention();
         if(var1.inlineOk((Expression)var4) && (var12 <= 2 || var12 == 3 && !var0.isTailCall())) {
            var8 = var4.getMethod(var14);
            if(var8 != null) {
               PrimProcedure var26 = new PrimProcedure(var8, var4);
               var3 = var8.getStaticFlag();
               var30 = false;
               boolean var31 = false;
               if(!var3 || var4.declareClosureEnv() != null) {
                  var30 = var31;
                  if(var3) {
                     var30 = true;
                  }

                  if(var1.curLambda == var4) {
                     Variable var21;
                     if(var4.closureEnv != null) {
                        var21 = var4.closureEnv;
                     } else {
                        var21 = var4.thisVariable;
                     }

                     var29.emitLoad(var21);
                  } else if(var6 != null) {
                     var6.load((AccessExp)null, 0, var1, Target.pushObject);
                  } else {
                     var4.getOwningLambda().loadHeapFrame(var1);
                  }
               }

               PrimType var27;
               if(var30) {
                  var27 = Type.voidType;
               } else {
                  var27 = null;
               }

               var26.compile(var27, var0, var1, var2);
               return;
            }
         }
      }

      if(var0.isTailCall() && var4 != null && var4 == var1.curLambda) {
         var30 = true;
      } else {
         var30 = false;
      }

      if(var4 != null && var4.getInlineOnly() && !var30 && var4.min_args == var14) {
         pushArgs(var4, var0.args, (int[])null, var1);
         if(var4.getFlag(128)) {
            popParams(var29, var4, (int[])null, false);
            var29.emitTailCall(false, var4.getVarScope());
         } else {
            var4.flags |= 128;
            LambdaExp var17 = var1.curLambda;
            var1.curLambda = var4;
            var4.allocChildClasses(var1);
            var4.allocParameters(var1);
            popParams(var29, var4, (int[])null, false);
            var4.enterFunction(var1);
            var4.body.compileWithPosition(var1, var2);
            var4.compileEnd(var1);
            var4.generateApplyMethods(var1);
            var29.popScope();
            var1.curLambda = var17;
         }
      } else if(var1.curLambda.isHandlingTailCalls() && (var0.isTailCall() || var2 instanceof ConsumerTarget) && !var1.curLambda.getInlineOnly()) {
         ClassType var22 = Compilation.typeCallContext;
         var10.compile(var1, (Target)(new StackTarget(Compilation.typeProcedure)));
         if(var14 <= 4) {
            for(var12 = 0; var12 < var14; ++var12) {
               var0.args[var12].compileWithPosition(var1, Target.pushObject);
            }

            var1.loadCallContext();
            var29.emitInvoke(Compilation.typeProcedure.getDeclaredMethod("check" + var14, var14 + 1));
         } else {
            compileToArray(var0.args, var1);
            var1.loadCallContext();
            var29.emitInvoke(Compilation.typeProcedure.getDeclaredMethod("checkN", 2));
         }

         if(var0.isTailCall()) {
            var29.emitReturn();
         } else if(((ConsumerTarget)var2).isContextTarget()) {
            var1.loadCallContext();
            var29.emitInvoke(var22.getDeclaredMethod("runUntilDone", 0));
         } else {
            var1.loadCallContext();
            var29.emitLoad(((ConsumerTarget)var2).getConsumerVariable());
            var29.emitInvoke(var22.getDeclaredMethod("runUntilValue", 1));
         }
      } else {
         if(!var30) {
            var10.compile(var1, (Target)(new StackTarget(Compilation.typeProcedure)));
         }

         if(var30) {
            if(var4.min_args != var4.max_args) {
               var3 = true;
            } else {
               var3 = false;
            }
         } else if(var14 > 4) {
            var3 = true;
         } else {
            var3 = false;
         }

         int[] var25 = null;
         Method var16;
         if(var3) {
            compileToArray(var0.args, var1);
            var16 = Compilation.applyNmethod;
         } else if(var30) {
            var25 = new int[var0.args.length];
            pushArgs(var4, var0.args, var25, var1);
            var16 = null;
         } else {
            for(int var13 = 0; var13 < var14; ++var13) {
               var0.args[var13].compileWithPosition(var1, Target.pushObject);
               if(!var29.reachableHere()) {
                  break;
               }
            }

            var16 = Compilation.applymethods[var14];
         }

         if(!var29.reachableHere()) {
            var1.error('e', "unreachable code");
         } else if(var30) {
            popParams(var29, var4, var25, var3);
            var29.emitTailCall(false, var4.getVarScope());
         } else {
            var29.emitInvokeVirtual(var16);
            var2.compileFromStack(var1, Type.pointer_type);
         }
      }
   }

   public static void compileToArray(Expression[] var0, Compilation var1) {
      CodeAttr var2 = var1.getCode();
      if(var0.length == 0) {
         var2.emitGetStatic(Compilation.noArgsField);
      } else {
         var2.emitPushInt(var0.length);
         var2.emitNewArray(Type.pointer_type);

         for(int var4 = 0; var4 < var0.length; ++var4) {
            Expression var3 = var0[var4];
            if(var1.usingCPStyle() && !(var3 instanceof QuoteExp) && !(var3 instanceof ReferenceExp)) {
               var3.compileWithPosition(var1, Target.pushObject);
               var2.emitSwap();
               var2.emitDup(1, 1);
               var2.emitSwap();
               var2.emitPushInt(var4);
               var2.emitSwap();
            } else {
               var2.emitDup(Compilation.objArrayType);
               var2.emitPushInt(var4);
               var3.compileWithPosition(var1, Target.pushObject);
            }

            var2.emitArrayStore(Type.pointer_type);
         }
      }

   }

   static Expression derefFunc(Expression var0) {
      Expression var1 = var0;
      if(var0 instanceof ReferenceExp) {
         Declaration var2 = Declaration.followAliases(((ReferenceExp)var0).binding);
         var1 = var0;
         if(var2 != null) {
            var1 = var0;
            if(!var2.getFlag(65536L)) {
               var1 = var2.getValue();
            }
         }
      }

      return var1;
   }

   static boolean inlineCompile(Procedure var0, ApplyExp var1, Compilation var2, Target var3) throws Throwable {
      Inlineable var4 = asInlineable(var0);
      if(var4 == null) {
         return false;
      } else {
         var4.compile(var1, var2, var3);
         return true;
      }
   }

   private static void popParams(CodeAttr var0, int var1, int var2, int[] var3, Declaration var4, Variable var5) {
      if(var2 > 0) {
         Declaration var7 = var4.nextDecl();
         Variable var6;
         if(var4.getVariable() == null) {
            var6 = var5;
         } else {
            var6 = var5.nextVar();
         }

         popParams(var0, var1 + 1, var2 - 1, var3, var7, var6);
         if(!var4.ignorable()) {
            if(var3 == null || var3[var1] == 65536) {
               var0.emitStore(var5);
               return;
            }

            var0.emitInc(var5, (short)var3[var1]);
         }
      }

   }

   private static void popParams(CodeAttr var0, LambdaExp var1, int[] var2, boolean var3) {
      Variable var5 = var1.getVarScope().firstVar();
      Declaration var6 = var1.firstDecl();
      Variable var4 = var5;
      if(var5 != null) {
         var4 = var5;
         if(var5.getName() == "this") {
            var4 = var5.nextVar();
         }
      }

      var5 = var4;
      if(var4 != null) {
         var5 = var4;
         if(var4.getName() == "$ctx") {
            var5 = var4.nextVar();
         }
      }

      var4 = var5;
      if(var5 != null) {
         var4 = var5;
         if(var5.getName() == "argsArray") {
            if(var3) {
               popParams(var0, 0, 1, (int[])null, var6, var5);
               return;
            }

            var4 = var5.nextVar();
         }
      }

      popParams(var0, 0, var1.min_args, var2, var6, var4);
   }

   private static void pushArgs(LambdaExp var0, Expression[] var1, int[] var2, Compilation var3) {
      Declaration var8 = var0.firstDecl();
      int var6 = var1.length;

      for(int var5 = 0; var5 < var6; ++var5) {
         Expression var4 = var1[var5];
         if(var8.ignorable()) {
            var4.compile(var3, (Target)Target.Ignore);
         } else {
            label18: {
               if(var2 != null) {
                  int var7 = SetExp.canUseInc(var4, var8);
                  var2[var5] = var7;
                  if(var7 != 65536) {
                     break label18;
                  }
               }

               var4.compileWithPosition(var3, StackTarget.getInstance(var8.getType()));
            }
         }

         var8 = var8.nextDecl();
      }

   }

   public void apply(CallContext var1) throws Throwable {
      Object var2 = this.func.eval((CallContext)var1);
      int var5 = this.args.length;
      Object[] var3 = new Object[var5];

      for(int var4 = 0; var4 < var5; ++var4) {
         var3[var4] = this.args[var4].eval((CallContext)var1);
      }

      ((Procedure)var2).checkN(var3, var1);
   }

   public void compile(Compilation var1, Target var2) {
      compile(this, var1, var2, true);
   }

   public Expression deepCopy(IdentityHashTable var1) {
      Expression var2 = deepCopy(this.func, var1);
      Expression[] var3 = deepCopy(this.args, var1);
      if((var2 != null || this.func == null) && (var3 != null || this.args == null)) {
         ApplyExp var4 = new ApplyExp(var2, var3);
         var4.flags = this.getFlags();
         return var4;
      } else {
         return null;
      }
   }

   public Expression getArg(int var1) {
      return this.args[var1];
   }

   public final int getArgCount() {
      return this.args.length;
   }

   public final Expression[] getArgs() {
      return this.args;
   }

   public final Expression getFunction() {
      return this.func;
   }

   public final Object getFunctionValue() {
      return this.func instanceof QuoteExp?((QuoteExp)this.func).getValue():null;
   }

   public final Type getType() {
      if(this.type != null) {
         return this.type;
      } else {
         Expression var1 = derefFunc(this.func);
         this.type = Type.objectType;
         if(var1 instanceof QuoteExp) {
            Object var2 = ((QuoteExp)var1).getValue();
            if(var2 instanceof Procedure) {
               this.type = ((Procedure)var2).getReturnType(this.args);
            }
         } else if(var1 instanceof LambdaExp) {
            this.type = ((LambdaExp)var1).getReturnType();
         }

         return this.type;
      }
   }

   public final Type getTypeRaw() {
      return this.type;
   }

   public final Expression inlineIfConstant(Procedure var1, InlineCalls var2) {
      return this.inlineIfConstant(var1, (SourceMessages)var2.getMessages());
   }

   public final Expression inlineIfConstant(Procedure var1, SourceMessages var2) {
      int var7 = this.args.length;
      Object[] var5 = new Object[var7];

      while(true) {
         --var7;
         if(var7 >= 0) {
            Expression var4 = this.args[var7];
            Expression var3 = var4;
            if(var4 instanceof ReferenceExp) {
               Declaration var6 = ((ReferenceExp)var4).getBinding();
               var3 = var4;
               if(var6 != null) {
                  var4 = var6.getValue();
                  var3 = var4;
                  if(var4 == QuoteExp.undefined_exp) {
                     return this;
                  }
               }
            }

            if(var3 instanceof QuoteExp) {
               var5[var7] = ((QuoteExp)var3).getValue();
               continue;
            }
         } else {
            try {
               QuoteExp var9 = new QuoteExp(var1.applyN(var5), this.type);
               return var9;
            } catch (Throwable var8) {
               if(var2 != null) {
                  var2.error('w', "call to " + var1 + " throws " + var8);
                  return this;
               }
            }
         }

         return this;
      }
   }

   public final boolean isTailCall() {
      return this.getFlag(2);
   }

   protected boolean mustCompile() {
      return false;
   }

   public void print(OutPort var1) {
      var1.startLogicalBlock("(Apply", ")", 2);
      if(this.isTailCall()) {
         var1.print((String)" [tailcall]");
      }

      if(this.type != null && this.type != Type.pointer_type) {
         var1.print((String)" => ");
         var1.print((Object)this.type);
      }

      var1.writeSpaceFill();
      this.printLineColumn(var1);
      this.func.print((OutPort)var1);

      for(int var2 = 0; var2 < this.args.length; ++var2) {
         var1.writeSpaceLinear();
         this.args[var2].print((OutPort)var1);
      }

      var1.endLogicalBlock(")");
   }

   public void setArg(int var1, Expression var2) {
      this.args[var1] = var2;
   }

   public void setArgs(Expression[] var1) {
      this.args = var1;
   }

   public void setFunction(Expression var1) {
      this.func = var1;
   }

   public final void setTailCall(boolean var1) {
      this.setFlag(var1, 2);
   }

   public final void setType(Type var1) {
      this.type = var1;
   }

   public boolean side_effects() {
      Object var1 = derefFunc(this.func).valueIfConstant();
      if(var1 instanceof Procedure && ((Procedure)var1).isSideEffectFree()) {
         Expression[] var4 = this.args;
         int var3 = var4.length;
         int var2 = 0;

         while(true) {
            if(var2 >= var3) {
               return false;
            }

            if(var4[var2].side_effects()) {
               break;
            }

            ++var2;
         }
      }

      return true;
   }

   public String toString() {
      if(this == LambdaExp.unknownContinuation) {
         return "ApplyExp[unknownContinuation]";
      } else {
         StringBuilder var1 = (new StringBuilder()).append("ApplyExp/");
         int var2;
         if(this.args == null) {
            var2 = 0;
         } else {
            var2 = this.args.length;
         }

         return var1.append(var2).append('[').append(this.func).append(']').toString();
      }
   }

   protected Object visit(ExpVisitor var1, Object var2) {
      return var1.visitApplyExp(this, var2);
   }

   public void visitArgs(InlineCalls var1) {
      this.args = var1.visitExps(this.args, this.args.length, (Object)null);
   }

   protected void visitChildren(ExpVisitor var1, Object var2) {
      this.func = var1.visitAndUpdate(this.func, var2);
      if(var1.exitValue == null) {
         this.args = var1.visitExps(this.args, this.args.length, var2);
      }

   }
}
