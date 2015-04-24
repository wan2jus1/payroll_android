package kawa.standard;

import gnu.bytecode.ClassType;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.ModuleExp;
import gnu.expr.QuoteExp;
import gnu.expr.ScopeExp;
import gnu.lists.FString;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Symbol;
import kawa.lang.Syntax;
import kawa.lang.SyntaxForm;
import kawa.lang.Translator;

public class module_name extends Syntax {

   public static final module_name module_name = new module_name();


   static {
      module_name.setName("module-name");
   }

   public void scanForm(Pair var1, ScopeExp var2, Translator var3) {
      Object var10 = var1.getCdr();

      SyntaxForm var4;
      for(var4 = null; var10 instanceof SyntaxForm; var10 = var4.getDatum()) {
         var4 = (SyntaxForm)var10;
      }

      SyntaxForm var5;
      if(var10 instanceof Pair) {
         var10 = ((Pair)var10).getCar();
         var5 = var4;
      } else {
         var10 = null;
         var5 = var4;
      }

      while(var10 instanceof SyntaxForm) {
         var5 = (SyntaxForm)var10;
         var10 = var5.getDatum();
      }

      String var6;
      int var9;
      String var11;
      String var12;
      Declaration var14;
      label90: {
         String var7 = null;
         var6 = null;
         var4 = null;
         if(var10 instanceof Pair) {
            Pair var8 = (Pair)var10;
            if(var8.getCar() == "quote") {
               var10 = var8.getCdr();
               if(var10 instanceof Pair) {
                  var1 = (Pair)var10;
                  if(var1.getCdr() == LList.Empty && var1.getCar() instanceof String) {
                     var12 = (String)var1.getCar();
                     var14 = var4;
                     var11 = var6;
                     break label90;
                  }
               }

               var11 = "invalid quoted symbol for \'module-name\'";
               var12 = var7;
               var14 = var4;
               break label90;
            }
         }

         if(!(var10 instanceof FString) && !(var10 instanceof String)) {
            if(var10 instanceof Symbol) {
               var7 = var10.toString();
               var9 = var7.length();
               String var13 = var7;
               if(var9 > 2) {
                  var13 = var7;
                  if(var7.charAt(0) == 60) {
                     var13 = var7;
                     if(var7.charAt(var9 - 1) == 62) {
                        var13 = var7.substring(1, var9 - 1);
                     }
                  }
               }

               var14 = var3.define(var10, var5, var2);
               var11 = var6;
               var12 = var13;
            } else {
               var11 = "un-implemented expression in module-name";
               var14 = var4;
               var12 = var7;
            }
         } else {
            var12 = var10.toString();
            var14 = var4;
            var11 = var6;
         }
      }

      if(var11 != null) {
         var3.formStack.add(var3.syntaxError(var11));
      } else {
         var9 = var12.lastIndexOf(46);
         if(var9 >= 0) {
            var3.classPrefix = var12.substring(0, var9 + 1);
            var11 = var12;
            var12 = var12;
         } else {
            var11 = var3.classPrefix + var12;
            var12 = var3.classPrefix + Compilation.mangleName(var11);
         }

         ModuleExp var15 = var3.getModule();
         if(var3.mainClass == null) {
            var3.mainClass = new ClassType(var12);
         } else {
            var6 = var3.mainClass.getName();
            if(var6 == null) {
               var3.mainClass.setName(var12);
            } else if(!var6.equals(var12)) {
               var3.syntaxError("duplicate module-name: old name: " + var6);
            }
         }

         var15.setType(var3.mainClass);
         var15.setName(var11);
         if(var14 != null) {
            var14.noteValue(new QuoteExp(var3.mainClass, Compilation.typeClass));
            var14.setFlag(16793600L);
            if(var15.outer == null) {
               var14.setFlag(2048L);
            }

            var14.setPrivate(true);
            var14.setType(Compilation.typeClass);
         }

         var3.mustCompileHere();
      }
   }
}
