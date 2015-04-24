package gnu.kawa.reflect;

import gnu.bytecode.CodeAttr;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.Inlineable;
import gnu.expr.LambdaExp;
import gnu.expr.Target;
import gnu.expr.TypeValue;
import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;
import gnu.mapping.Procedure;

public class TypeSwitch extends MethodProc implements Inlineable {

   public static final TypeSwitch typeSwitch = new TypeSwitch("typeswitch");


   public TypeSwitch(String var1) {
      this.setName(var1);
      this.setProperty(Procedure.validateApplyKey, "gnu.kawa.reflect.CompileReflect:validateApplyTypeSwitch");
   }

   public void apply(CallContext var1) throws Throwable {
      Object[] var2 = var1.getArgs();
      Object var3 = var2[0];
      int var5 = var2.length - 1;

      for(int var4 = 1; var4 < var5; ++var4) {
         if(((MethodProc)var2[var4]).match1(var3, var1) >= 0) {
            return;
         }
      }

      ((Procedure)var2[var5]).check1(var3, var1);
   }

   public void compile(ApplyExp var1, Compilation var2, Target var3) {
      Expression[] var4 = var1.getArgs();
      CodeAttr var5 = var2.getCode();
      var5.pushScope();
      Variable var6 = var5.addLocal(Type.pointer_type);
      var4[0].compile(var2, (Target)Target.pushObject);
      var5.emitStore(var6);

      int var9;
      int var10;
      for(var9 = 1; var9 < var4.length; var9 = var10) {
         if(var9 > 1) {
            var5.emitElse();
         }

         var10 = var9 + 1;
         Expression var11 = var4[var9];
         if(!(var11 instanceof LambdaExp)) {
            throw new Error("not implemented: typeswitch arg not LambdaExp");
         }

         LambdaExp var7 = (LambdaExp)var11;
         Declaration var12 = var7.firstDecl();
         Type var8 = var12.getType();
         if(!var12.getCanRead()) {
            var12 = null;
         } else {
            var12.allocateVariable(var5);
         }

         if(var8 instanceof TypeValue) {
            ((TypeValue)var8).emitTestIf(var6, var12, var2);
         } else {
            if(var10 < var4.length) {
               var5.emitLoad(var6);
               var8.emitIsInstance(var5);
               var5.emitIfIntNotZero();
            }

            if(var12 != null) {
               var5.emitLoad(var6);
               var12.compileStore(var2);
            }
         }

         var7.allocChildClasses(var2);
         var7.body.compileWithPosition(var2, var3);
      }

      var9 = var4.length - 2;

      while(true) {
         --var9;
         if(var9 < 0) {
            var5.popScope();
            return;
         }

         var5.emitFi();
      }
   }

   public Type getReturnType(Expression[] var1) {
      return Type.pointer_type;
   }

   public int numArgs() {
      return -4094;
   }
}
