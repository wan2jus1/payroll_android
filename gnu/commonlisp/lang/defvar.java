package gnu.commonlisp.lang;

import gnu.commonlisp.lang.CommonLisp;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.ModuleExp;
import gnu.expr.QuoteExp;
import gnu.expr.ScopeExp;
import gnu.expr.SetExp;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Symbol;
import java.util.Vector;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class defvar extends Syntax {

   boolean force;


   public defvar(boolean var1) {
      this.force = var1;
   }

   public Expression rewriteForm(Pair var1, Translator var2) {
      Object var8 = var1.getCdr();
      Declaration var6 = null;
      Pair var5 = null;
      Object var7 = null;
      Declaration var4 = (Declaration)var7;
      Object var9 = var6;
      Expression var3 = var5;
      if(var8 instanceof Pair) {
         Pair var13 = (Pair)var8;
         var4 = (Declaration)var7;
         var9 = var6;
         var3 = var5;
         if(var13.getCar() instanceof Declaration) {
            var6 = (Declaration)var13.getCar();
            var9 = var6.getSymbol();
            if(var13.getCdr() instanceof Pair) {
               var5 = (Pair)var13.getCdr();
               var3 = var2.rewrite(var5.getCar());
               var4 = var6;
               if(var5.getCdr() != LList.Empty) {
                  ;
               }
            } else {
               var4 = var6;
               var3 = var5;
               if(var13.getCdr() != LList.Empty) {
                  var9 = null;
                  var4 = var6;
                  var3 = var5;
               }
            }
         }
      }

      if(var9 == null) {
         var9 = var2.syntaxError("invalid syntax for " + this.getName());
      } else {
         Expression var10 = var3;
         if(var3 == null) {
            if(!this.force) {
               return new QuoteExp(var9);
            }

            var10 = CommonLisp.nilExpr;
         }

         SetExp var12 = new SetExp(var9, var10);
         if(!this.force) {
            var12.setSetIfUnbound(true);
         }

         var12.setDefining(true);
         var9 = var12;
         if(var4 != null) {
            var12.setBinding(var4);
            Expression var11 = var10;
            if(var4.context instanceof ModuleExp) {
               var11 = var10;
               if(var4.getCanWrite()) {
                  var11 = null;
               }
            }

            var4.noteValue(var11);
            return var12;
         }
      }

      return (Expression)var9;
   }

   public boolean scanForDefinitions(Pair var1, Vector var2, ScopeExp var3, Translator var4) {
      if(!(var1.getCdr() instanceof Pair)) {
         return super.scanForDefinitions(var1, var2, var3, var4);
      } else {
         Pair var5;
         label25: {
            Pair var6 = (Pair)var1.getCdr();
            Object var7 = var6.getCar();
            if(!(var7 instanceof String)) {
               var5 = var1;
               if(!(var7 instanceof Symbol)) {
                  break label25;
               }
            }

            Declaration var9 = var3.lookup(var7);
            Declaration var8;
            if(var9 == null) {
               var8 = new Declaration(var7);
               var8.setFlag(268435456L);
               var3.addDeclaration((Declaration)var8);
            } else {
               var4.error('w', "duplicate declaration for `" + var7 + "\'");
               var8 = var9;
            }

            var1 = Translator.makePair(var1, this, Translator.makePair(var6, var8, var6.getCdr()));
            var5 = var1;
            if(var3 instanceof ModuleExp) {
               var8.setCanRead(true);
               var8.setCanWrite(true);
               var5 = var1;
            }
         }

         var2.addElement(var5);
         return true;
      }
   }
}
