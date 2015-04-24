package kawa.standard;

import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.LambdaExp;
import gnu.expr.ThisExp;
import gnu.lists.LList;
import gnu.lists.Pair;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class thisRef extends Syntax {

   public static final thisRef thisSyntax = new thisRef();


   static {
      thisSyntax.setName("this");
   }

   public Expression rewriteForm(Pair var1, Translator var2) {
      if(var1.getCdr() == LList.Empty) {
         LambdaExp var4 = var2.curMethodLambda;
         Declaration var5;
         if(var4 == null) {
            var5 = null;
         } else {
            var5 = var4.firstDecl();
         }

         Declaration var3;
         if(var5 != null) {
            var3 = var5;
            if(var5.isThisParameter()) {
               return new ThisExp(var3);
            }
         }

         var3 = null;
         if(var4 != null && var4.nameDecl != null) {
            if(var4.nameDecl.isStatic()) {
               var2.error('e', "use of \'this\' in a static method");
            } else {
               var3 = new Declaration(ThisExp.THIS_NAME);
               var4.add((Declaration)null, var3);
               var4.nameDecl.setFlag(4096L);
            }
         } else {
            var2.error('e', "use of \'this\' not in a named method");
         }

         return new ThisExp(var3);
      } else {
         return var2.syntaxError("this with parameter not implemented");
      }
   }
}
