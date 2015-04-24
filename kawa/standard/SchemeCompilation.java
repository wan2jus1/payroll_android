package kawa.standard;

import gnu.expr.Special;
import kawa.repl;
import kawa.lang.Lambda;
import kawa.standard.Scheme;

public class SchemeCompilation {

   public static final Lambda lambda = new Lambda();
   public static final repl repl = new repl(Scheme.instance);


   static {
      lambda.setKeywords(Special.optional, Special.rest, Special.key);
   }

}
