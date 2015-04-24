package kawa.standard;

import gnu.expr.Expression;
import gnu.expr.QuoteExp;
import gnu.lists.Pair;
import kawa.lang.Syntax;
import kawa.lang.SyntaxForm;
import kawa.lang.SyntaxPattern;
import kawa.lang.SyntaxRules;
import kawa.lang.Translator;

public class syntax_rules extends Syntax {

   public static final syntax_rules syntax_rules = new syntax_rules();


   static {
      syntax_rules.setName("syntax-rules");
   }

   public Expression rewriteForm(Pair var1, Translator var2) {
      var1 = (Pair)var1.getCdr();
      return new QuoteExp(new SyntaxRules(SyntaxPattern.getLiteralsList(var1.getCar(), (SyntaxForm)null, var2), var1.getCdr(), var2));
   }
}
