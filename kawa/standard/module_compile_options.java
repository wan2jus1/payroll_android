package kawa.standard;

import gnu.expr.Expression;
import gnu.expr.ScopeExp;
import gnu.lists.LList;
import gnu.lists.Pair;
import java.util.Stack;
import java.util.Vector;
import kawa.lang.Syntax;
import kawa.lang.Translator;
import kawa.standard.with_compile_options;

public class module_compile_options extends Syntax {

   public static final module_compile_options module_compile_options = new module_compile_options();


   static {
      module_compile_options.setName("module-compile-options");
   }

   public Expression rewriteForm(Pair var1, Translator var2) {
      return null;
   }

   public boolean scanForDefinitions(Pair var1, Vector var2, ScopeExp var3, Translator var4) {
      if(with_compile_options.getOptions(var1.getCdr(), (Stack)null, this, var4) != LList.Empty) {
         var4.error('e', this.getName() + " key must be a keyword");
      }

      return true;
   }
}
