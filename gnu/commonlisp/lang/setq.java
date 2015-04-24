package gnu.commonlisp.lang;

import gnu.commonlisp.lang.CommonLisp;
import gnu.expr.BeginExp;
import gnu.expr.Expression;
import gnu.expr.SetExp;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Symbol;
import java.util.Vector;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class setq extends Syntax {

   public Expression rewriteForm(Pair var1, Translator var2) {
      Object var4 = var1.getCdr();
      Vector var6 = null;

      Object var3;
      while(true) {
         if(var4 == LList.Empty) {
            if(var6 == null) {
               return CommonLisp.nilExpr;
            }

            Expression[] var7 = new Expression[var6.size()];
            var6.copyInto(var7);
            return new BeginExp(var7);
         }

         if(!(var4 instanceof Pair)) {
            var3 = var2.syntaxError("invalid syntax for setq");
            break;
         }

         Pair var8 = (Pair)var4;
         var3 = var8.getCar();
         if(!(var3 instanceof Symbol) && !(var3 instanceof String)) {
            if(var3 != CommonLisp.FALSE) {
               return var2.syntaxError("invalid variable name in setq");
            }

            var3 = "nil";
         }

         var4 = var8.getCdr();
         if(!(var4 instanceof Pair)) {
            return var2.syntaxError("wrong number of arguments for setq");
         }

         var8 = (Pair)var4;
         Expression var5 = var2.rewrite(var8.getCar());
         var4 = var8.getCdr();
         SetExp var10 = new SetExp(var3, var5);
         var10.setFlag(8);
         if(var4 == LList.Empty) {
            var10.setHasValue(true);
            var3 = var10;
            if(var6 == null) {
               break;
            }
         }

         Vector var9 = var6;
         if(var6 == null) {
            var9 = new Vector(10);
         }

         var9.addElement(var10);
         var6 = var9;
      }

      return (Expression)var3;
   }
}
