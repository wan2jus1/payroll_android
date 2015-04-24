package kawa.standard;

import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.ModuleExp;
import gnu.expr.ScopeExp;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import java.util.Vector;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class module_static extends Syntax {

   public static final module_static module_static = new module_static();


   static {
      module_static.setName("module-static");
   }

   public Expression rewriteForm(Pair var1, Translator var2) {
      return null;
   }

   public boolean scanForDefinitions(Pair var1, Vector var2, ScopeExp var3, Translator var4) {
      Object var6 = var1.getCdr();
      if(!(var3 instanceof ModuleExp)) {
         var4.error('e', "\'" + this.getName() + "\' not at module level");
      } else {
         ModuleExp var7;
         label65: {
            var7 = (ModuleExp)var3;
            Pair var5;
            if(var6 instanceof Pair) {
               var5 = (Pair)var6;
               if(var5.getCdr() == LList.Empty && var5.getCar() instanceof Boolean) {
                  if(var5.getCar() == Boolean.FALSE) {
                     var7.setFlag(65536);
                  } else {
                     var7.setFlag('耀');
                  }
                  break label65;
               }
            }

            if(var6 instanceof Pair) {
               var5 = (Pair)var6;
               if(var5.getCdr() == LList.Empty && var5.getCar() instanceof Pair) {
                  var5 = (Pair)var5.getCar();
                  if(var4.matches(var5.getCar(), "quote")) {
                     var1 = (Pair)var5.getCdr();
                     if(var1 == LList.Empty || !(var1.getCar() instanceof SimpleSymbol) || var1.getCar().toString() != "init-run") {
                        var4.error('e', "invalid quoted symbol for \'" + this.getName() + '\'');
                        return false;
                     }

                     var7.setFlag('耀');
                     var7.setFlag(262144);
                     break label65;
                  }
               }
            }

            var7.setFlag(65536);

            while(var6 != LList.Empty) {
               if(var6 instanceof Pair) {
                  var1 = (Pair)var6;
                  if(var1.getCar() instanceof Symbol) {
                     Declaration var8 = var3.getNoDefine((Symbol)var1.getCar());
                     if(var8.getFlag(512L)) {
                        Translator.setLine((Declaration)var8, var1);
                     }

                     var8.setFlag(2048L);
                     var6 = var1.getCdr();
                     continue;
                  }
               }

               var4.error('e', "invalid syntax in \'" + this.getName() + '\'');
               return false;
            }
         }

         if(var7.getFlag(65536) && var7.getFlag('耀')) {
            var4.error('e', "inconsistent module-static specifiers");
            return true;
         }
      }

      return true;
   }
}
