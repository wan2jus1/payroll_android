package kawa.standard;

import gnu.bytecode.ClassType;
import gnu.bytecode.Method;
import gnu.expr.ApplyExp;
import gnu.expr.Expression;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.lists.Pair;
import kawa.lang.PatternScope;
import kawa.lang.Quote;
import kawa.lang.SyntaxForm;
import kawa.lang.SyntaxTemplate;
import kawa.lang.Translator;

public class syntax extends Quote {

   static final Method makeTemplateScopeMethod = typeTemplateScope.getDeclaredMethod("make", 0);
   public static final syntax quasiSyntax = new syntax("quasisyntax", true);
   public static final syntax syntax = new syntax("syntax", false);
   static final ClassType typeTemplateScope = ClassType.make("kawa.lang.TemplateScope");


   public syntax(String var1, boolean var2) {
      super(var1, var2);
   }

   static Expression makeSyntax(Object var0, Translator var1) {
      SyntaxTemplate var3 = new SyntaxTemplate(var0, (SyntaxForm)null, var1);
      QuoteExp var2 = QuoteExp.nullExp;
      PatternScope var4 = var1.patternScope;
      var0 = var2;
      if(var4 != null) {
         var0 = var2;
         if(var4.matchArray != null) {
            var0 = new ReferenceExp(var4.matchArray);
         }
      }

      var2 = new QuoteExp(var3);
      ReferenceExp var5 = new ReferenceExp(var1.templateScopeDecl);
      return new ApplyExp(ClassType.make("kawa.lang.SyntaxTemplate").getDeclaredMethod("execute", 2), new Expression[]{var2, (Expression)var0, var5});
   }

   protected boolean expandColonForms() {
      return false;
   }

   protected Expression leaf(Object var1, Translator var2) {
      return makeSyntax(var1, var2);
   }

   public Expression rewriteForm(Pair param1, Translator param2) {
      // $FF: Couldn't be decompiled
   }
}
