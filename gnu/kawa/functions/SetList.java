package gnu.kawa.functions;

import gnu.bytecode.Type;
import gnu.mapping.Procedure2;
import gnu.mapping.Values;
import java.util.List;

class SetList extends Procedure2 {

   Type elementType;
   List list;


   public SetList(List var1) {
      this.list = var1;
   }

   public Object apply2(Object var1, Object var2) {
      this.list.set(((Number)var1).intValue(), var2);
      return Values.empty;
   }
}
