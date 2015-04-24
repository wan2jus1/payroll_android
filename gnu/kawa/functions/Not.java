package gnu.kawa.functions;

import gnu.expr.Language;
import gnu.mapping.Procedure;
import gnu.mapping.Procedure1;

public class Not extends Procedure1 {

   Language language;


   public Not(Language var1) {
      this.language = var1;
      this.setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileMisc:validateApplyNot");
      Procedure.compilerKey.set(this, "*gnu.kawa.functions.CompileMisc:forNot");
   }

   public Not(Language var1, String var2) {
      this(var1);
      this.setName(var2);
   }

   public Object apply1(Object var1) {
      Language var2 = this.language;
      boolean var3;
      if(!this.language.isTrue(var1)) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var2.booleanObject(var3);
   }
}
