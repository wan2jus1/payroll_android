package gnu.kawa.xml;

import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;
import gnu.mapping.Values;
import java.util.Iterator;

public class IteratorItems extends MethodProc {

   public static IteratorItems iteratorItems = new IteratorItems();


   public void apply(CallContext var1) {
      Consumer var2 = var1.consumer;
      Object var3 = var1.getNextArg();
      var1.lastArg();
      Iterator var4 = (Iterator)var3;

      while(var4.hasNext()) {
         Values.writeValues(var4.next(), var2);
      }

   }
}
