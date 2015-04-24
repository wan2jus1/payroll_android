package gnu.kawa.xml;

import gnu.kawa.xml.NodeType;
import gnu.kawa.xml.TreeScanner;
import gnu.lists.AbstractSequence;
import gnu.lists.NodePredicate;
import gnu.lists.PositionConsumer;
import gnu.lists.TreeList;

public class DescendantOrSelfAxis extends TreeScanner {

   public static final DescendantOrSelfAxis anyNode = new DescendantOrSelfAxis(NodeType.anyNodeTest);


   private DescendantOrSelfAxis(NodePredicate var1) {
      this.type = var1;
   }

   public static DescendantOrSelfAxis make(NodePredicate var0) {
      return var0 == NodeType.anyNodeTest?anyNode:new DescendantOrSelfAxis(var0);
   }

   public void scan(AbstractSequence var1, int var2, PositionConsumer var3) {
      if(this.type.isInstancePos(var1, var2)) {
         var3.writePosition(var1, var2);
      }

      if(!(var1 instanceof TreeList)) {
         for(var2 = var1.firstChildPos(var2); var2 != 0; var2 = var1.nextPos(var2)) {
            this.scan(var1, var2, var3);
         }
      } else {
         int var4 = var1.nextPos(var2);

         while(true) {
            var2 = var1.nextMatching(var2, this.type, var4, true);
            if(var2 == 0) {
               break;
            }

            var3.writePosition(var1, var2);
         }
      }

   }
}
