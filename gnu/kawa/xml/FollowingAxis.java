package gnu.kawa.xml;

import gnu.kawa.xml.TreeScanner;
import gnu.lists.AbstractSequence;
import gnu.lists.NodePredicate;
import gnu.lists.PositionConsumer;

public class FollowingAxis extends TreeScanner {

   public static FollowingAxis make(NodePredicate var0) {
      FollowingAxis var1 = new FollowingAxis();
      var1.type = var0;
      return var1;
   }

   public void scan(AbstractSequence var1, int var2, PositionConsumer var3) {
      int var5 = var1.endPos();
      int var4 = var1.nextPos(var2);
      var2 = var4;
      if(var4 != 0) {
         var2 = var4;
         if(this.type.isInstancePos(var1, var4)) {
            var3.writePosition(var1, var4);
            var2 = var4;
         }
      }

      while(true) {
         var2 = var1.nextMatching(var2, this.type, var5, true);
         if(var2 == 0) {
            return;
         }

         var3.writePosition(var1, var2);
      }
   }
}
