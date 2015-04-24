package gnu.kawa.xml;

import gnu.kawa.xml.TreeScanner;
import gnu.lists.AbstractSequence;
import gnu.lists.NodePredicate;
import gnu.lists.PositionConsumer;

public class ParentAxis extends TreeScanner {

   public static ParentAxis make(NodePredicate var0) {
      ParentAxis var1 = new ParentAxis();
      var1.type = var0;
      return var1;
   }

   public void scan(AbstractSequence var1, int var2, PositionConsumer var3) {
      var2 = var1.parentPos(var2);
      if(var2 != var1.endPos() && this.type.isInstancePos(var1, var2)) {
         var3.writePosition(var1, var2);
      }

   }
}
