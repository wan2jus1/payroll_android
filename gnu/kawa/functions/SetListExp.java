package gnu.kawa.functions;

import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.InlineCalls;
import gnu.expr.QuoteExp;
import gnu.kawa.reflect.Invoke;

class SetListExp extends ApplyExp {

   public SetListExp(Expression var1, Expression[] var2) {
      super((Expression)var1, var2);
   }

   public Expression validateApply(ApplyExp var1, InlineCalls var2, Type var3, Declaration var4) {
      ((ApplyExp)var1).visitArgs(var2);
      Expression[] var8 = ((ApplyExp)var1).getArgs();
      if(var8.length == 2) {
         Expression var7 = this.getArgs()[0];
         QuoteExp var5 = QuoteExp.getInstance("set");
         Expression var6 = Compilation.makeCoercion(var8[0], (Type)Type.intType);
         Expression var9 = var8[1];
         var1 = Compilation.makeCoercion(var2.visitApplyOnly(new ApplyExp(Invoke.invoke, new Expression[]{var7, var5, var6, var9}), var3), (Type)Type.voidType);
      }

      return (Expression)var1;
   }
}
