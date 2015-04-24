package gnu.kawa.functions;

import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.Procedure1;
import gnu.mapping.Values;
import gnu.math.IntNum;

public class CountValues extends Procedure1 {

   public static final CountValues countValues = new CountValues();


   public static int countValues(Object var0) {
      return var0 instanceof Values?((Values)var0).size():1;
   }

   public void apply(CallContext var1) {
      Consumer var2 = var1.consumer;
      Object var3 = var1.getNextArg();
      var1.lastArg();
      var2.writeInt(countValues(var3));
   }

   public Object apply1(Object var1) {
      return IntNum.make(countValues(var1));
   }
}
