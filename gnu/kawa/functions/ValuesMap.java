package gnu.kawa.functions;

import gnu.bytecode.CodeAttr;
import gnu.bytecode.Label;
import gnu.bytecode.Method;
import gnu.bytecode.Scope;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.ConsumerTarget;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.IfExp;
import gnu.expr.IgnoreTarget;
import gnu.expr.Inlineable;
import gnu.expr.LambdaExp;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.StackTarget;
import gnu.expr.Target;
import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;
import gnu.mapping.Procedure;
import gnu.mapping.Values;
import gnu.math.IntNum;

public class ValuesMap extends MethodProc implements Inlineable {

   public static final ValuesMap valuesMap = new ValuesMap(-1);
   public static final ValuesMap valuesMapWithPos = new ValuesMap(1);
   private final int startCounter;


   private ValuesMap(int var1) {
      this.startCounter = var1;
      this.setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileMisc:validateApplyValuesMap");
   }

   static LambdaExp canInline(ApplyExp var0, ValuesMap var1) {
      byte var2 = 2;
      Expression[] var4 = var0.getArgs();
      if(var4.length == 2) {
         Expression var5 = var4[0];
         if(var5 instanceof LambdaExp) {
            LambdaExp var6 = (LambdaExp)var5;
            if(var6.min_args == var6.max_args) {
               int var3 = var6.min_args;
               if(var1.startCounter < 0) {
                  var2 = 1;
               }

               if(var3 == var2) {
                  return var6;
               }
            }
         }
      }

      return null;
   }

   public static void compileInlined(LambdaExp var0, Expression var1, int var2, Method var3, Compilation var4, Target var5) {
      Declaration var7 = var0.firstDecl();
      CodeAttr var10 = var4.getCode();
      Scope var6 = var10.pushScope();
      Type var11 = var7.getType();
      Declaration var8;
      Variable var17;
      if(var2 >= 0) {
         var17 = var6.addVariable(var10, Type.intType, "position");
         var10.emitPushInt(var2);
         var10.emitStore(var17);
         var8 = new Declaration(var17);
      } else {
         var17 = null;
         var8 = null;
      }

      if(var7.isSimple() && var3 == null) {
         var7.allocateVariable(var10);
      } else {
         String var18 = Compilation.mangleNameIfNeeded(var7.getName());
         var7 = new Declaration(var10.addLocal(var11.getImplementationType(), var18));
      }

      Expression[] var9;
      if(var2 >= 0) {
         var9 = new Expression[]{new ReferenceExp(var7), new ReferenceExp(var8)};
      } else {
         var9 = new Expression[]{new ReferenceExp(var7)};
      }

      ApplyExp var20 = new ApplyExp(var0, var9);
      Object var13 = var20;
      if(var3 != null) {
         ApplyExp var14 = var20;
         if(var20.getType().getImplementationType() != Type.booleanType) {
            var14 = new ApplyExp(var3, new Expression[]{var20, new ReferenceExp(var8)});
         }

         var13 = new IfExp(var14, new ReferenceExp(var7), QuoteExp.voidExp);
      }

      Variable var16 = var10.addLocal(Type.intType);
      Variable var19 = var10.addLocal(Type.pointer_type);
      Variable var21 = var10.addLocal(Type.intType);
      var1.compileWithPosition(var4, Target.pushObject);
      var10.emitStore(var19);
      var10.emitPushInt(0);
      var10.emitStore(var16);
      Label var15 = new Label(var10);
      Label var12 = new Label(var10);
      var15.define(var10);
      var10.emitLoad(var19);
      var10.emitLoad(var16);
      var10.emitInvokeStatic(Compilation.typeValues.getDeclaredMethod("nextIndex", 2));
      var10.emitDup(Type.intType);
      var10.emitStore(var21);
      var10.emitGotoIfIntLtZero(var12);
      var10.emitLoad(var19);
      var10.emitLoad(var16);
      var10.emitInvokeStatic(Compilation.typeValues.getDeclaredMethod("nextValue", 2));
      StackTarget.convert(var4, Type.objectType, var11);
      var7.compileStore(var4);
      ((Expression)var13).compile(var4, (Target)var5);
      if(var2 >= 0) {
         var10.emitInc(var17, (short)1);
      }

      var10.emitLoad(var21);
      var10.emitStore(var16);
      var10.emitGoto(var15);
      var12.define(var10);
      var10.popScope();
   }

   public void apply(CallContext var1) throws Throwable {
      Procedure var2 = (Procedure)var1.getNextArg();
      Consumer var3 = var1.consumer;
      Object var7 = var1.getNextArg();
      Procedure.checkArgCount(var2, 1);
      if(var7 instanceof Values) {
         int var6 = 0;
         int var5 = this.startCounter;
         Values var8 = (Values)var7;

         while(true) {
            var6 = var8.nextPos(var6);
            if(var6 == 0) {
               break;
            }

            Object var4 = var8.getPosPrevious(var6);
            if(this.startCounter >= 0) {
               var2.check2(var4, IntNum.make(var5), var1);
               ++var5;
            } else {
               var2.check1(var4, var1);
            }

            var1.runUntilDone();
         }
      } else {
         if(this.startCounter >= 0) {
            var2.check2(var7, IntNum.make(this.startCounter), var1);
         } else {
            var2.check1(var7, var1);
         }

         var1.runUntilDone();
      }

   }

   public void compile(ApplyExp var1, Compilation var2, Target var3) {
      LambdaExp var4 = canInline(var1, this);
      if(var4 == null) {
         ApplyExp.compile(var1, var2, var3);
      } else {
         Expression[] var5 = var1.getArgs();
         if(!(var3 instanceof IgnoreTarget) && !(var3 instanceof ConsumerTarget)) {
            ConsumerTarget.compileUsingConsumer(var1, var2, var3);
         } else {
            compileInlined(var4, var5[1], this.startCounter, (Method)null, var2, var3);
         }
      }
   }

   public Type getReturnType(Expression[] var1) {
      return Type.pointer_type;
   }

   public int numArgs() {
      return 8194;
   }
}
