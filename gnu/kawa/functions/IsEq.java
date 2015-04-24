package gnu.kawa.functions;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.ConditionalTarget;
import gnu.expr.Expression;
import gnu.expr.Inlineable;
import gnu.expr.Language;
import gnu.expr.Target;
import gnu.mapping.Procedure2;

public class IsEq extends Procedure2 implements Inlineable {

   Language language;


   public IsEq(Language var1, String var2) {
      this.language = var1;
      this.setName(var2);
   }

   public static void compile(Expression[] var0, Compilation var1, Target var2, Language var3) {
      CodeAttr var4 = var1.getCode();
      var0[0].compile(var1, (Target)Target.pushObject);
      var0[1].compile(var1, (Target)Target.pushObject);
      if(var2 instanceof ConditionalTarget) {
         ConditionalTarget var6 = (ConditionalTarget)var2;
         if(var6.trueBranchComesFirst) {
            var4.emitGotoIfNE(var6.ifFalse);
         } else {
            var4.emitGotoIfEq(var6.ifTrue);
         }

         var6.emitGotoFirstBranch(var4);
      } else {
         var4.emitIfEq();
         Object var5;
         if(var2.getType() instanceof ClassType) {
            var5 = var3.booleanObject(true);
            Object var7 = var3.booleanObject(false);
            var1.compileConstant(var5, Target.pushObject);
            var4.emitElse();
            var1.compileConstant(var7, Target.pushObject);
            if(var5 instanceof Boolean && var7 instanceof Boolean) {
               var5 = Compilation.scmBooleanType;
            } else {
               var5 = Type.pointer_type;
            }
         } else {
            var4.emitPushInt(1);
            var4.emitElse();
            var4.emitPushInt(0);
            var5 = var3.getTypeFor((Class)Boolean.TYPE);
         }

         var4.emitFi();
         var2.compileFromStack(var1, (Type)var5);
      }
   }

   public boolean apply(Object var1, Object var2) {
      return var1 == var2;
   }

   public Object apply2(Object var1, Object var2) {
      Language var3 = this.language;
      boolean var4;
      if(var1 == var2) {
         var4 = true;
      } else {
         var4 = false;
      }

      return var3.booleanObject(var4);
   }

   public void compile(ApplyExp var1, Compilation var2, Target var3) {
      compile(var1.getArgs(), var2, var3, this.language);
   }

   public Type getReturnType(Expression[] var1) {
      return this.language.getTypeFor((Class)Boolean.TYPE);
   }
}
