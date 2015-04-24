package kawa.standard;

import gnu.expr.Expression;
import gnu.lists.Pair;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class fluid_let extends Syntax {

   public static final fluid_let fluid_let = new fluid_let();
   Expression defaultInit;
   boolean star;


   static {
      fluid_let.setName("fluid-set");
   }

   public fluid_let() {
      this.star = false;
   }

   public fluid_let(boolean var1, Expression var2) {
      this.star = var1;
      this.defaultInit = var2;
   }

   public Expression rewrite(Object param1, Object param2, Translator param3) {
      // $FF: Couldn't be decompiled
   }

   public Expression rewrite(Object var1, Translator var2) {
      if(!(var1 instanceof Pair)) {
         return var2.syntaxError("missing let arguments");
      } else {
         Pair var3 = (Pair)var1;
         return this.rewrite(var3.getCar(), var3.getCdr(), var2);
      }
   }
}
