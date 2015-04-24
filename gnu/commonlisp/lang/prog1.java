package gnu.commonlisp.lang;

import gnu.expr.BeginExp;
import gnu.expr.Expression;
import gnu.expr.LetExp;
import gnu.expr.ReferenceExp;
import gnu.lists.LList;
import gnu.lists.Pair;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class prog1 extends Syntax {

   public static final prog1 prog1 = new prog1("prog1", 1);
   public static final prog1 prog2 = new prog1("prog2", 2);
   int index;


   public prog1(String var1, int var2) {
      this.index = var2;
      this.setName(var1);
   }

   public Expression rewrite(Object var1, Translator var2) {
      int var7 = LList.length(var1);
      if(var7 < this.index) {
         return var2.syntaxError("too few expressions in " + this.getName());
      } else {
         Pair var8;
         if(this.index == 2) {
            var8 = (Pair)var1;
            return new BeginExp(var2.rewrite(var8.getCar()), prog1.rewrite(var8.getCdr(), var2));
         } else {
            Expression[] var5 = new Expression[1];
            LetExp var3 = new LetExp(var5);
            Expression[] var4 = new Expression[var7];
            var8 = (Pair)var1;
            var5[0] = var2.rewrite(var8.getCar());
            var1 = var8.getCdr();

            for(int var6 = 0; var6 < var7 - 1; ++var6) {
               var8 = (Pair)var1;
               var4[var6] = var2.rewrite(var8.getCar());
               var1 = var8.getCdr();
            }

            var4[var7 - 1] = new ReferenceExp(var3.addDeclaration((Object)null));
            var3.body = BeginExp.canonicalize((Expression[])var4);
            var2.mustCompileHere();
            return var3;
         }
      }
   }
}
