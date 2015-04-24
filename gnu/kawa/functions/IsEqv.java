package gnu.kawa.functions;

import gnu.expr.Language;
import gnu.kawa.functions.IsEq;
import gnu.kawa.functions.IsEqual;
import gnu.mapping.Procedure;
import gnu.mapping.Procedure2;
import gnu.mapping.Symbol;
import gnu.text.Char;

public class IsEqv extends Procedure2 {

   IsEq isEq;
   Language language;


   public IsEqv(Language var1, String var2, IsEq var3) {
      this.language = var1;
      this.isEq = var3;
      this.setName(var2);
      this.setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompilationHelpers:validateIsEqv");
   }

   public static boolean apply(Object var0, Object var1) {
      return var0 == var1?true:(var0 instanceof Number && var1 instanceof Number?IsEqual.numberEquals((Number)var0, (Number)var1):(!(var0 instanceof Char) && !(var0 instanceof Symbol)?false:var0.equals(var1)));
   }

   public Object apply2(Object var1, Object var2) {
      return this.language.booleanObject(apply(var1, var2));
   }
}
