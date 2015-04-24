package gnu.kawa.xml;

import gnu.lists.AbstractSequence;
import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;
import gnu.mapping.Values;
import java.util.Iterator;
import java.util.List;

public class ListItems extends MethodProc {

   public static ListItems listItems = new ListItems();


   public void apply(CallContext var1) {
      Consumer var2 = var1.consumer;
      Object var3 = var1.getNextArg();
      var1.lastArg();
      List var4 = (List)var3;
      if(var3 instanceof AbstractSequence) {
         ((AbstractSequence)var3).consumePosRange(0, -1, var2);
      } else {
         Iterator var5 = var4.iterator();

         while(var5.hasNext()) {
            Values.writeValues(var5.next(), var2);
         }
      }

   }
}
