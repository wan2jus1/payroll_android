package kawa.standard;

import gnu.bytecode.ClassType;
import gnu.expr.ClassExp;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.ModuleExp;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.ScopeExp;
import gnu.expr.SetExp;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Symbol;
import java.util.Vector;
import kawa.lang.Syntax;
import kawa.lang.SyntaxForm;
import kawa.lang.Translator;
import kawa.standard.location;

public class define_alias extends Syntax {

   public static final define_alias define_alias = new define_alias();


   static {
      define_alias.setName("define-alias");
   }

   public Expression rewrite(Object var1, Translator var2) {
      return var2.syntaxError("define-alias is only allowed in a <body>");
   }

   public boolean scanForDefinitions(Pair var1, Vector var2, ScopeExp var3, Translator var4) {
      Object var5 = var1.getCdr();

      SyntaxForm var8;
      for(var8 = null; var5 instanceof SyntaxForm; var5 = var8.getDatum()) {
         var8 = (SyntaxForm)var5;
      }

      if(var5 instanceof Pair) {
         Pair var7 = (Pair)var5;
         SyntaxForm var6 = var8;

         for(var5 = var7.getCar(); var5 instanceof SyntaxForm; var5 = var6.getDatum()) {
            var6 = (SyntaxForm)var5;
         }

         Object var14;
         for(var14 = var7.getCdr(); var14 instanceof SyntaxForm; var14 = var8.getDatum()) {
            var8 = (SyntaxForm)var14;
         }

         if((var5 instanceof String || var5 instanceof Symbol) && var14 instanceof Pair) {
            var7 = (Pair)var14;
            if(var7.getCdr() == LList.Empty) {
               Declaration var10 = var4.define(var5, var6, var3);
               var10.setIndirectBinding(true);
               var10.setAlias(true);
               Expression var9 = var4.rewrite_car(var7, var8);
               if(var9 instanceof ReferenceExp) {
                  label36: {
                     ReferenceExp var13 = (ReferenceExp)var9;
                     Declaration var11 = Declaration.followAliases(var13.getBinding());
                     if(var11 != null) {
                        Expression var15 = var11.getValue();
                        if(var15 instanceof ClassExp || var15 instanceof ModuleExp) {
                           var10.setIndirectBinding(false);
                           var10.setFlag(16384L);
                           break label36;
                        }
                     }

                     var13.setDontDereference(true);
                  }
               } else if(var9 instanceof QuoteExp) {
                  var10.setIndirectBinding(false);
                  var10.setFlag(16384L);
               } else {
                  var9 = location.rewrite((Expression)var9, var4);
                  var10.setType(ClassType.make("gnu.mapping.Location"));
               }

               var4.mustCompileHere();
               var4.push(var10);
               SetExp var12 = new SetExp(var10, var9);
               var4.setLineOf(var12);
               var10.noteValue(var9);
               var12.setDefining(true);
               var2.addElement(var12);
               return true;
            }
         }
      }

      var4.error('e', "invalid syntax for define-alias");
      return false;
   }
}
