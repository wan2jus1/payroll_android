package gnu.commonlisp.lang;

import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.LambdaExp;
import gnu.expr.ModuleExp;
import gnu.expr.ScopeExp;
import gnu.expr.SetExp;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.Symbol;
import java.util.Vector;
import kawa.lang.Lambda;
import kawa.lang.Syntax;
import kawa.lang.TemplateScope;
import kawa.lang.Translator;

public class defun extends Syntax {

   Lambda lambdaSyntax;


   public defun(Lambda var1) {
      this.lambdaSyntax = var1;
   }

   public Expression rewriteForm(Pair var1, Translator var2) {
      Object var4 = var1.getCdr();
      Object var6 = null;
      Declaration var3 = null;
      if(var4 instanceof Pair) {
         Pair var9 = (Pair)var4;
         Object var5 = var9.getCar();
         if(!(var5 instanceof Symbol) && !(var5 instanceof String)) {
            if(var5 instanceof Declaration) {
               var3 = (Declaration)var5;
               var6 = var3.getSymbol();
            }
         } else {
            var6 = var5.toString();
         }

         if(var6 != null && var9.getCdr() instanceof Pair) {
            Pair var11 = (Pair)var9.getCdr();
            LambdaExp var10 = new LambdaExp();
            this.lambdaSyntax.rewrite(var10, var11.getCar(), var11.getCdr(), var2, (TemplateScope)null);
            var10.setSymbol(var6);
            if(var11 instanceof PairWithPosition) {
               var10.setLocation((PairWithPosition)var11);
            }

            LambdaExp var7 = var10;
            SetExp var12 = new SetExp(var6, var10);
            var12.setDefining(true);
            var12.setFuncDef(true);
            if(var3 != null) {
               var12.setBinding(var3);
               LambdaExp var8 = var7;
               if(var3.context instanceof ModuleExp) {
                  var8 = var7;
                  if(var3.getCanWrite()) {
                     var8 = null;
                  }
               }

               var3.noteValue(var8);
            }

            return var12;
         }
      }

      return var2.syntaxError("invalid syntax for " + this.getName());
   }

   public boolean scanForDefinitions(Pair var1, Vector var2, ScopeExp var3, Translator var4) {
      if(var1.getCdr() instanceof Pair) {
         Pair var6 = (Pair)var1.getCdr();
         if(var6.getCar() instanceof String || var6.getCar() instanceof Symbol) {
            Object var7 = var6.getCar();
            Declaration var5 = var3.lookup(var7);
            Declaration var8;
            if(var5 == null) {
               var8 = new Declaration(var7);
               var8.setProcedureDecl(true);
               var3.addDeclaration((Declaration)var8);
            } else {
               var4.error('w', "duplicate declaration for `" + var7 + "\'");
               var8 = var5;
            }

            if(var3 instanceof ModuleExp) {
               var8.setCanRead(true);
            }

            var2.addElement(Translator.makePair(var1, this, Translator.makePair(var6, var8, var6.getCdr())));
            return true;
         }
      }

      return super.scanForDefinitions(var1, var2, var3, var4);
   }
}
