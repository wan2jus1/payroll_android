package kawa.standard;

import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.lists.LList;
import gnu.lists.Pair;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class syntax_error extends Syntax {

   public static final syntax_error syntax_error = new syntax_error();


   static {
      syntax_error.setName("%syntax-error");
   }

   public static Expression error(Object var0, Object[] var1) {
      StringBuffer var2 = new StringBuffer();
      int var4 = var1.length;
      if(var1 != null && var4 != 0) {
         for(int var3 = 0; var3 < var4; ++var3) {
            var2.append(var1[var3]);
         }
      } else {
         var2.append("invalid syntax");
      }

      Translator var7 = (Translator)Compilation.getCurrent();
      if(var7 == null) {
         throw new RuntimeException(var2.toString());
      } else {
         var0 = var7.pushPositionOf(var0);

         Expression var8;
         try {
            var8 = var7.syntaxError(var2.toString());
         } finally {
            var7.popPositionOf(var0);
         }

         return var8;
      }
   }

   public Expression rewrite(Object var1, Translator var2) {
      StringBuffer var3 = new StringBuffer();

      int var4;
      for(var4 = 0; var1 instanceof Pair; ++var4) {
         Pair var5 = (Pair)var1;
         if(var4 > 0) {
            var3.append(' ');
         }

         var3.append(var5.getCar());
         var1 = var5.getCdr();
      }

      if(var1 != LList.Empty) {
         if(var4 > 0) {
            var3.append(' ');
         }

         var3.append(var1);
      }

      return var2.syntaxError(var3.toString());
   }
}
