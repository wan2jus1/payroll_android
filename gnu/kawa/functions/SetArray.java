package gnu.kawa.functions;

import gnu.bytecode.Type;
import gnu.expr.Language;
import gnu.mapping.Procedure2;
import gnu.mapping.Values;
import java.lang.reflect.Array;

class SetArray extends Procedure2 {

   Object array;
   Type elementType;


   public SetArray(Object var1, Language var2) {
      this.elementType = var2.getTypeFor((Class)var1.getClass().getComponentType());
      this.array = var1;
   }

   public Object apply2(Object var1, Object var2) {
      var2 = this.elementType.coerceFromObject(var2);
      Array.set(this.array, ((Number)var1).intValue(), var2);
      return Values.empty;
   }
}
