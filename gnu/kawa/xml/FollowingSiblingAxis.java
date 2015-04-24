package gnu.kawa.xml;

import gnu.kawa.xml.TreeScanner;
import gnu.lists.AbstractSequence;
import gnu.lists.NodePredicate;
import gnu.lists.PositionConsumer;

public class FollowingSiblingAxis extends TreeScanner {

   public static FollowingSiblingAxis make(NodePredicate var0) {
      FollowingSiblingAxis var1 = new FollowingSiblingAxis();
      var1.type = var0;
      return var1;
   }

   public void scan(AbstractSequence var1, int var2, PositionConsumer var3) {
      int var4 = var1.endPos();

      while(true) {
         var2 = var1.nextMatching(var2, this.type, var4, false);
         if(var2 == 0) {
            return;
         }

         var3.writePosition(var1, var2);
      }
   }
}
