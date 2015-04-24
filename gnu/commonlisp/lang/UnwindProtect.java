package gnu.commonlisp.lang;

import gnu.expr.Expression;
import gnu.expr.TryExp;
import gnu.lists.Pair;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class UnwindProtect extends Syntax {

   public Expression rewrite(Object var1, Translator var2) {
      if(!(var1 instanceof Pair)) {
         return var2.syntaxError("invalid syntax for unwind-protect");
      } else {
         Pair var3 = (Pair)var1;
         return new TryExp(var2.rewrite(var3.getCar()), var2.rewrite_body(var3.getCdr()));
      }
   }
}
