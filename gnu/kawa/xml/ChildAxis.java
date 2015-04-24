package gnu.kawa.xml;

import gnu.kawa.xml.TreeScanner;
import gnu.lists.AbstractSequence;
import gnu.lists.NodePredicate;
import gnu.lists.PositionConsumer;

public class ChildAxis extends TreeScanner {

   public static ChildAxis make(NodePredicate var0) {
      ChildAxis var1 = new ChildAxis();
      var1.type = var0;
      return var1;
   }

   public void scan(AbstractSequence var1, int var2, PositionConsumer var3) {
      for(var2 = var1.firstChildPos(var2, this.type); var2 != 0; var2 = var1.nextMatching(var2, this.type, -1, false)) {
         var3.writePosition(var1, var2);
      }

   }
}
