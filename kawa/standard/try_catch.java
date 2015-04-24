package kawa.standard;

import gnu.expr.CatchClause;
import gnu.expr.Compilation;
import gnu.expr.ErrorExp;
import gnu.expr.Expression;
import gnu.expr.LambdaExp;
import gnu.expr.TryExp;
import gnu.lists.FVector;
import kawa.lang.Translator;
import kawa.standard.SchemeCompilation;

public class try_catch {

   public static Expression rewrite(Object var0, Object var1) {
      Translator var3 = (Translator)Compilation.getCurrent();
      Expression var4 = var3.rewrite(var0);
      Expression var2 = null;
      CatchClause var8 = null;
      FVector var5 = (FVector)var1;
      int var7 = var5.size();
      int var6 = 0;

      for(CatchClause var9 = var2; var6 < var7; ++var6) {
         var2 = SchemeCompilation.lambda.rewrite(var5.get(var6), var3);
         if(var2 instanceof ErrorExp) {
            return var2;
         }

         if(!(var2 instanceof LambdaExp)) {
            return var3.syntaxError("internal error with try-catch");
         }

         CatchClause var11 = new CatchClause((LambdaExp)var2);
         if(var9 == null) {
            var8 = var11;
         } else {
            var9.setNext(var11);
         }

         var9 = var11;
      }

      if(var4 instanceof ErrorExp) {
         return var4;
      } else {
         TryExp var10 = new TryExp(var4, (Expression)null);
         var10.setCatchClauses(var8);
         return var10;
      }
   }
}
