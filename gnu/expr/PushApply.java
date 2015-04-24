package gnu.expr;

import gnu.expr.ApplyExp;
import gnu.expr.BeginExp;
import gnu.expr.ExpVisitor;
import gnu.expr.Expression;
import gnu.expr.FluidLetExp;
import gnu.expr.LetExp;

public class PushApply extends ExpVisitor {

   public static void pushApply(Expression var0) {
      (new PushApply()).visit(var0, (Object)null);
   }

   protected Expression defaultValue(Expression var1, Void var2) {
      return var1;
   }

   protected Expression update(Expression var1, Expression var2) {
      return var2;
   }

   protected Expression visitApplyExp(ApplyExp var1, Void var2) {
      Expression var3 = var1.func;
      if(var3 instanceof LetExp && !(var3 instanceof FluidLetExp)) {
         LetExp var7 = (LetExp)var3;
         Expression var8 = var7.body;
         var7.body = var1;
         var1.func = var8;
         return (Expression)this.visit(var7, var2);
      } else if(var3 instanceof BeginExp) {
         BeginExp var6 = (BeginExp)var3;
         Expression[] var4 = var6.exps;
         int var5 = var6.exps.length - 1;
         var1.func = var4[var5];
         var4[var5] = var1;
         return (Expression)this.visit(var6, var2);
      } else {
         var1.visitChildren(this, var2);
         return var1;
      }
   }
}
