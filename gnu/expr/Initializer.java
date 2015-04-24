package gnu.expr;

import gnu.bytecode.Field;
import gnu.expr.Compilation;

public abstract class Initializer {

   public Field field;
   Initializer next;


   public static Initializer reverse(Initializer var0) {
      Initializer var1;
      Initializer var2;
      for(var1 = null; var0 != null; var0 = var2) {
         var2 = var0.next;
         var0.next = var1;
         var1 = var0;
      }

      return var1;
   }

   public abstract void emit(Compilation var1);

   public void reportError(String var1, Compilation var2) {
      var2.error('e', var1 + "field " + this.field);
   }
}
