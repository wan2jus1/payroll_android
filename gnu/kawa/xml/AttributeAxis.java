package gnu.kawa.xml;

import gnu.kawa.xml.TreeScanner;
import gnu.lists.AbstractSequence;
import gnu.lists.NodePredicate;
import gnu.lists.PositionConsumer;

public class AttributeAxis extends TreeScanner {

   public static AttributeAxis make(NodePredicate var0) {
      AttributeAxis var1 = new AttributeAxis();
      var1.type = var0;
      return var1;
   }

   public void scan(AbstractSequence var1, int var2, PositionConsumer var3) {
      for(var2 = var1.firstAttributePos(var2); var2 != 0 && var1.getNextKind(var2) == 35; var2 = var1.nextPos(var2)) {
         if(this.type.isInstancePos(var1, var2)) {
            var3.writePosition(var1, var2);
         } else if(var1.getNextKind(var2) != 35) {
            break;
         }
      }

   }
}
