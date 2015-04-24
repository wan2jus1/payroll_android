package kawa.standard;

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

public class define_variable extends Syntax {

   public static final define_variable define_variable = new define_variable();


   static {
      define_variable.setName("define-variable");
   }

   public Expression rewriteForm(Pair var1, Translator var2) {
      Object var6 = var1.getCdr();
      SetExp var4 = null;
      Object var5 = null;
      Declaration var8 = (Declaration)var5;
      Expression var3 = var4;
      Object var9;
      if(var6 instanceof Pair) {
         Pair var12 = (Pair)var6;
         Object var7 = var12.getCar();
         if(var7 instanceof String || var7 instanceof Symbol) {
            var9 = var2.syntaxError(this.getName() + " is only allowed in a <body>");
            return (Expression)var9;
         }

         var8 = (Declaration)var5;
         var3 = var4;
         if(var7 instanceof Declaration) {
            label36: {
               var8 = (Declaration)var12.getCar();
               var5 = var12.getCdr();
               if(var5 instanceof Pair) {
                  Pair var11 = (Pair)var5;
                  if(var11.getCdr() == LList.Empty) {
                     var3 = var2.rewrite(var11.getCar());
                     break label36;
                  }
               }

               var3 = var4;
               if(var5 != LList.Empty) {
                  var8 = null;
                  var3 = var4;
               }
            }
         }
      }

      if(var8 == null) {
         return var2.syntaxError("invalid syntax for " + this.getName());
      } else if(var3 == null) {
         return QuoteExp.voidExp;
      } else {
         var4 = new SetExp(var8, var3);
         var4.setDefining(true);
         var4.setSetIfUnbound(true);
         var9 = var4;
         if(var8 != null) {
            var4.setBinding(var8);
            Expression var10 = var3;
            if(var8.context instanceof ModuleExp) {
               var10 = var3;
               if(var8.getCanWrite()) {
                  var10 = null;
               }
            }

            var8.noteValue(var10);
            return var4;
         } else {
            return (Expression)var9;
         }
      }
   }

   public boolean scanForDefinitions(Pair var1, Vector var2, ScopeExp var3, Translator var4) {
      if(!(var1.getCdr() instanceof Pair)) {
         return super.scanForDefinitions(var1, var2, var3, var4);
      } else {
         Pair var5;
         label22: {
            Pair var6 = (Pair)var1.getCdr();
            Object var7 = var6.getCar();
            if(!(var7 instanceof String)) {
               var5 = var1;
               if(!(var7 instanceof Symbol)) {
                  break label22;
               }
            }

            if(var3.lookup(var7) != null) {
               var4.error('e', "duplicate declaration for \'" + var7 + "\'");
            }

            Declaration var8 = var3.addDeclaration((Object)var7);
            var4.push(var8);
            var8.setSimple(false);
            var8.setPrivate(true);
            var8.setFlag(268697600L);
            var8.setCanRead(true);
            var8.setCanWrite(true);
            var8.setIndirectBinding(true);
            var5 = Translator.makePair(var1, this, Translator.makePair(var6, var8, var6.getCdr()));
         }

         var2.addElement(var5);
         return true;
      }
   }
}
