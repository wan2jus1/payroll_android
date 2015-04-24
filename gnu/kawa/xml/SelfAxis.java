package gnu.kawa.xml;

import gnu.kawa.xml.TreeScanner;
import gnu.lists.AbstractSequence;
import gnu.lists.NodePredicate;
import gnu.lists.PositionConsumer;

public class SelfAxis extends TreeScanner {

   public static SelfAxis make(NodePredicate var0) {
      SelfAxis var1 = new SelfAxis();
      var1.type = var0;
      return var1;
   }

   public void scan(AbstractSequence var1, int var2, PositionConsumer var3) {
      if(this.type.isInstancePos(var1, var2)) {
         var3.writePosition(var1, var2);
      }

   }
}
