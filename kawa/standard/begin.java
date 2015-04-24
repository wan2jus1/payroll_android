package kawa.standard;

import gnu.expr.Expression;
import gnu.expr.ScopeExp;
import gnu.lists.LList;
import gnu.lists.Pair;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class begin extends Syntax {

   public static final begin begin = new begin();


   static {
      begin.setName("begin");
   }

   public Expression rewrite(Object var1, Translator var2) {
      return var2.rewrite_body(var1);
   }

   public void scanForm(Pair var1, ScopeExp var2, Translator var3) {
      LList var4 = var3.scanBody(var1.getCdr(), var2, true);
      if(var4 != LList.Empty) {
         var3.formStack.add(Translator.makePair(var1, var1.getCar(), var4));
      }

   }
}
