package kawa.lang;

import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.LetExp;
import java.util.Vector;
import kawa.lang.Translator;

public class PatternScope extends LetExp {

   public Declaration matchArray;
   public StringBuffer patternNesting;
   public Vector pattern_names;
   PatternScope previousSyntax;


   public PatternScope() {
      super((Expression[])null);
   }

   public static void pop(Translator var0) {
      var0.patternScope = var0.patternScope.previousSyntax;
   }

   public static PatternScope push(Translator var0) {
      PatternScope var1 = new PatternScope();
      PatternScope var2 = var0.patternScope;
      var1.previousSyntax = var2;
      var0.patternScope = var1;
      if(var2 == null) {
         var1.pattern_names = new Vector();
         var1.patternNesting = new StringBuffer();
      } else {
         var1.pattern_names = (Vector)var2.pattern_names.clone();
         var1.patternNesting = new StringBuffer(var2.patternNesting.toString());
      }

      var1.outer = var0.currentScope();
      return var1;
   }
}
