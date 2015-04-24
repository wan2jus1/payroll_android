package kawa.standard;

import gnu.expr.Expression;
import gnu.expr.ScopeExp;
import gnu.lists.LList;
import gnu.lists.Pair;
import java.util.Stack;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class with_compile_options extends Syntax {

   public static final with_compile_options with_compile_options = new with_compile_options();


   static {
      with_compile_options.setName("with-compile-options");
   }

   public static Object getOptions(Object param0, Stack param1, Syntax param2, Translator param3) {
      // $FF: Couldn't be decompiled
   }

   public Expression rewriteForm(Pair param1, Translator param2) {
      // $FF: Couldn't be decompiled
   }

   public void scanForm(Pair var1, ScopeExp var2, Translator var3) {
      Stack var4 = new Stack();
      Object var5 = getOptions(var1.getCdr(), var4, this, var3);
      if(var5 != LList.Empty) {
         if(var5 == var1.getCdr()) {
            var3.scanBody(var5, var2, false);
         } else {
            Pair var6 = new Pair(var4, var3.scanBody(var5, var2, true));
            var3.currentOptions.popOptionValues(var4);
            var3.formStack.add(Translator.makePair(var1, var1.getCar(), var6));
         }
      }
   }
}
