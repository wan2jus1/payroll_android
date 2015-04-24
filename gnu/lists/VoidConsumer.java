package gnu.lists;

import gnu.lists.Consumer;
import gnu.lists.FilterConsumer;

public class VoidConsumer extends FilterConsumer {

   public static VoidConsumer instance = new VoidConsumer();


   public VoidConsumer() {
      super((Consumer)null);
   }

   public static VoidConsumer getInstance() {
      return instance;
   }

   public boolean ignoring() {
      return true;
   }
}
