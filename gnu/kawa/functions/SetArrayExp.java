package gnu.kawa.functions;

import gnu.bytecode.ArrayType;
import gnu.bytecode.ClassType;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.InlineCalls;
import gnu.expr.QuoteExp;
import gnu.kawa.reflect.Invoke;
import gnu.mapping.Procedure;

class SetArrayExp extends ApplyExp {

   public static final ClassType typeSetArray = ClassType.make("gnu.kawa.functions.SetArray");
   Type elementType;


   public SetArrayExp(Expression var1, ArrayType var2) {
      super((Procedure)Invoke.make, new Expression[]{new QuoteExp(typeSetArray), var1});
      this.elementType = var2.getComponentType();
   }

   public Expression validateApply(ApplyExp var1, InlineCalls var2, Type var3, Declaration var4) {
      ((ApplyExp)var1).visitArgs(var2);
      Expression[] var7 = ((ApplyExp)var1).getArgs();
      if(var7.length == 2) {
         Expression var6 = this.getArgs()[1];
         Expression var5 = var7[0];
         Expression var8 = var7[1];
         var1 = var2.visitApplyOnly(new ApplyExp(new gnu.kawa.reflect.ArraySet(this.elementType), new Expression[]{var6, var5, var8}), var3);
      }

      return (Expression)var1;
   }
}
