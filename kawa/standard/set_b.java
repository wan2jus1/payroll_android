package kawa.standard;

import gnu.expr.ApplyExp;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.ModuleExp;
import gnu.expr.ReferenceExp;
import gnu.expr.SetExp;
import gnu.kawa.functions.CompilationHelpers;
import gnu.lists.LList;
import gnu.lists.Pair;
import kawa.lang.Syntax;
import kawa.lang.SyntaxForm;
import kawa.lang.Translator;
import kawa.standard.Scheme;

public class set_b extends Syntax {

   public static final set_b set = new set_b();


   static {
      set.setName("set!");
   }

   public Expression rewriteForm(Pair var1, Translator var2) {
      Object var3 = var1.getCdr();

      SyntaxForm var10;
      for(var10 = null; var3 instanceof SyntaxForm; var3 = var10.getDatum()) {
         var10 = (SyntaxForm)var3;
      }

      Object var11;
      if(!(var3 instanceof Pair)) {
         var11 = var2.syntaxError("missing name");
         return (Expression)var11;
      } else {
         Pair var15 = (Pair)var3;
         Expression var5 = var2.rewrite_car(var15, var10);

         for(var3 = var15.getCdr(); var3 instanceof SyntaxForm; var3 = var10.getDatum()) {
            var10 = (SyntaxForm)var3;
         }

         if(var3 instanceof Pair) {
            var15 = (Pair)var3;
            if(var15.getCdr() == LList.Empty) {
               Expression var4 = var2.rewrite_car(var15, var10);
               if(var5 instanceof ApplyExp) {
                  ApplyExp var19 = (ApplyExp)var5;
                  Expression[] var17 = var19.getArgs();
                  int var8 = var17.length;
                  byte var9 = 0;
                  Expression var12 = var19.getFunction();
                  Expression var16 = var12;
                  int var7 = var8;
                  byte var6 = var9;
                  if(var17.length > 0) {
                     var16 = var12;
                     var7 = var8;
                     var6 = var9;
                     if(var12 instanceof ReferenceExp) {
                        var16 = var12;
                        var7 = var8;
                        var6 = var9;
                        if(((ReferenceExp)var12).getBinding() == Scheme.applyFieldDecl) {
                           var6 = 1;
                           var7 = var8 - 1;
                           var16 = var17[0];
                        }
                     }
                  }

                  Expression[] var13 = new Expression[var7 + 1];
                  System.arraycopy(var17, var6, var13, 0, var7);
                  var13[var7] = var4;
                  return new ApplyExp(new ApplyExp(new ReferenceExp(CompilationHelpers.setterDecl), new Expression[]{var16}), var13);
               }

               if(!(var5 instanceof ReferenceExp)) {
                  return var2.syntaxError("first set! argument is not a variable name");
               }

               ReferenceExp var14 = (ReferenceExp)var5;
               Declaration var18 = var14.getBinding();
               SetExp var20 = new SetExp(var14.getSymbol(), var4);
               var20.setContextDecl(var14.contextDecl());
               var11 = var20;
               if(var18 != null) {
                  var18.setCanWrite(true);
                  var20.setBinding(var18);
                  var18 = Declaration.followAliases(var18);
                  if(var18 != null) {
                     var18.noteValue(var4);
                  }

                  if(var18.getFlag(16384L)) {
                     return var2.syntaxError("constant variable " + var18.getName() + " is set!");
                  }

                  var11 = var20;
                  if(var18.context != var2.mainLambda) {
                     var11 = var20;
                     if(var18.context instanceof ModuleExp) {
                        var11 = var20;
                        if(!var18.getFlag(268435456L)) {
                           var11 = var20;
                           if(!var18.context.getFlag(1048576)) {
                              var2.error('w', var18, "imported variable ", " is set!");
                              return var20;
                           }

                           return (Expression)var11;
                        }
                     }

                     return (Expression)var11;
                  }
               }

               return (Expression)var11;
            }
         }

         return var2.syntaxError("missing or extra arguments to set!");
      }
   }
}
