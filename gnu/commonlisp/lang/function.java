package gnu.commonlisp.lang;

import gnu.expr.Expression;
import gnu.expr.ReferenceExp;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Symbol;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class function extends Syntax {

   Syntax lambda;


   public function(Syntax var1) {
      this.lambda = var1;
   }

   public Expression rewriteForm(Pair var1, Translator var2) {
      Object var4 = var1.getCdr();
      if(var4 instanceof Pair) {
         var1 = (Pair)var4;
         if(var1.getCdr() != LList.Empty) {
            return var2.syntaxError("too many forms after \'function\'");
         }

         var4 = var1.getCar();
         if(var4 instanceof String || var4 instanceof Symbol) {
            ReferenceExp var5 = new ReferenceExp(var4);
            var5.setProcedureName(true);
            var5.setFlag(8);
            return var5;
         }

         if(var4 instanceof Pair) {
            var1 = (Pair)var4;
            Object var3 = var1.getCar();
            if(var3 instanceof String) {
               if(!"lambda".equals(var3)) {
                  return var2.syntaxError("function must be followed by name or lambda expression");
               }
            } else if(!(var3 instanceof Symbol) || !"lambda".equals(((Symbol)var3).getName())) {
               return var2.syntaxError("function must be followed by name or lambda expression");
            }

            return this.lambda.rewriteForm((Pair)var1, var2);
         }
      }

      return var2.syntaxError("function must be followed by name or lambda expression");
   }
}
