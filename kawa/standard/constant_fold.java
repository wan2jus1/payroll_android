package kawa.standard;

import gnu.expr.ApplyExp;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.mapping.Environment;
import gnu.mapping.Procedure;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class constant_fold extends Syntax {

   public static final constant_fold constant_fold = new constant_fold();


   static {
      constant_fold.setName("constant-fold");
   }

   static Object checkConstant(Expression var0, Translator var1) {
      Object var3 = null;
      if(var0 instanceof QuoteExp) {
         var3 = ((QuoteExp)var0).getValue();
      } else if(var0 instanceof ReferenceExp) {
         ReferenceExp var2 = (ReferenceExp)var0;
         Declaration var4 = var2.getBinding();
         if(var4 != null && !var4.getFlag(65536L)) {
            return Declaration.followAliases(var4).getConstantValue();
         }

         return Environment.user().get((String)var2.getName(), (Object)null);
      }

      return var3;
   }

   public Expression rewrite(Object var1, Translator var2) {
      Expression var9 = var2.rewrite(var1);
      if(!(var9 instanceof ApplyExp)) {
         return var9;
      } else {
         ApplyExp var4 = (ApplyExp)var9;
         Object var3 = checkConstant(var4.getFunction(), var2);
         if(!(var3 instanceof Procedure)) {
            return var9;
         } else {
            Expression[] var12 = var4.getArgs();
            int var7 = var12.length;
            Object[] var5 = new Object[var7];

            while(true) {
               --var7;
               if(var7 < 0) {
                  try {
                     QuoteExp var10 = new QuoteExp(((Procedure)var3).applyN(var5));
                     return var10;
                  } catch (Throwable var8) {
                     Expression var11 = var2.syntaxError("caught exception in constant-fold:");
                     var2.syntaxError(var8.toString());
                     return var11;
                  }
               }

               Object var6 = checkConstant(var12[var7], var2);
               if(var6 == null) {
                  return var9;
               }

               var5[var7] = var6;
            }
         }
      }
   }
}
