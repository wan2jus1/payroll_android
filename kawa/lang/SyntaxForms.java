package kawa.lang;

import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.lists.ImmutablePair;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Symbol;
import kawa.lang.SyntaxForm;
import kawa.lang.TemplateScope;
import kawa.lang.Translator;

public class SyntaxForms {

   public static final boolean DEBUGGING = true;


   public static boolean freeIdentifierEquals(SyntaxForm var0, SyntaxForm var1) {
      Translator var2 = (Translator)Compilation.getCurrent();
      return var2.lexical.lookup(var0.getDatum(), -1) == var2.lexical.lookup(var1.getDatum(), -1);
   }

   public static Object fromDatum(Object var0, SyntaxForm var1) {
      return makeForm(var0, var1.getScope());
   }

   public static Object fromDatumIfNeeded(Object var0, SyntaxForm var1) {
      return var0 == var1.getDatum()?var1:(var0 instanceof SyntaxForm?(SyntaxForm)var0:fromDatum(var0, var1));
   }

   public static boolean isIdentifier(SyntaxForm var0) {
      return var0.getDatum() instanceof Symbol;
   }

   public static Object makeForm(Object var0, TemplateScope var1) {
      Object var2;
      if(var0 instanceof Pair) {
         var2 = new SyntaxForms.PairSyntaxForm((Pair)var0, var1);
      } else {
         var2 = var0;
         if(var0 != LList.Empty) {
            return new SyntaxForms.SimpleSyntaxForm(var0, var1);
         }
      }

      return var2;
   }

   public static Object makeWithTemplate(Object var0, Object var1) {
      Object var2;
      if(var1 instanceof SyntaxForm) {
         var2 = (SyntaxForm)var1;
      } else {
         var2 = var1;
         if(var0 instanceof SyntaxForm) {
            SyntaxForm var3 = (SyntaxForm)var0;
            if(var1 == var3.getDatum()) {
               return var3;
            }

            return fromDatum(var1, var3);
         }
      }

      return var2;
   }

   public static Expression rewrite(Object var0) {
      return ((Translator)Compilation.getCurrent()).rewrite(var0);
   }

   public static Expression rewriteBody(Object var0) {
      return ((Translator)Compilation.getCurrent()).rewrite_body(var0);
   }

   public static String toString(SyntaxForm var0, String var1) {
      StringBuilder var2 = new StringBuilder("#<syntax");
      if(var1 != null) {
         var2.append('#');
         var2.append(var1);
      }

      var2.append(' ');
      var2.append(var0.getDatum());
      TemplateScope var3 = var0.getScope();
      if(var3 == null) {
         var2.append(" in null");
      } else {
         var2.append(" in #");
         var2.append(var3.id);
      }

      var2.append(">");
      return var2.toString();
   }

   static class PairSyntaxForm extends ImmutablePair implements SyntaxForm {

      private Pair datum;
      private TemplateScope scope;


      public PairSyntaxForm(Pair var1, TemplateScope var2) {
         this.datum = var1;
         this.scope = var2;
      }

      public Object getCar() {
         if(this.car == null) {
            this.car = SyntaxForms.makeForm(this.datum.getCar(), this.scope);
         }

         return this.car;
      }

      public Object getCdr() {
         if(this.cdr == null) {
            this.cdr = SyntaxForms.makeForm(this.datum.getCdr(), this.scope);
         }

         return this.cdr;
      }

      public Object getDatum() {
         return this.datum;
      }

      public TemplateScope getScope() {
         return this.scope;
      }

      public String toString() {
         return SyntaxForms.toString(this, (String)null);
      }
   }

   static class SimpleSyntaxForm implements SyntaxForm {

      static int counter;
      private Object datum;
      int id;
      private TemplateScope scope;


      SimpleSyntaxForm(Object var1, TemplateScope var2) {
         int var3 = counter + 1;
         counter = var3;
         this.id = var3;
         this.datum = var1;
         this.scope = var2;
      }

      public Object getDatum() {
         return this.datum;
      }

      public TemplateScope getScope() {
         return this.scope;
      }

      public String toString() {
         return SyntaxForms.toString(this, Integer.toString(this.id));
      }
   }
}
