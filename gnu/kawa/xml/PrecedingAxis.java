package gnu.kawa.xml;

import gnu.kawa.xml.TreeScanner;
import gnu.lists.AbstractSequence;
import gnu.lists.NodePredicate;
import gnu.lists.PositionConsumer;

public class PrecedingAxis extends TreeScanner {

   public static PrecedingAxis make(NodePredicate var0) {
      PrecedingAxis var1 = new PrecedingAxis();
      var1.type = var0;
      return var1;
   }

   private static void scan(AbstractSequence var0, int var1, int var2, NodePredicate var3, PositionConsumer var4) {
      int var5 = var0.parentPos(var1);
      if(var5 != var2) {
         scan(var0, var5, var2, var3, var4);
         var5 = var0.firstChildPos(var5);
         if(var5 != 0) {
            var2 = var5;
            if(var3.isInstancePos(var0, var5)) {
               var4.writePosition(var0, var5);
               var2 = var5;
            }

            while(true) {
               var2 = var0.nextMatching(var2, var3, var1, true);
               if(var2 == 0) {
                  break;
               }

               var4.writePosition(var0, var2);
            }
         }
      }

   }

   public void scan(AbstractSequence var1, int var2, PositionConsumer var3) {
      scan(var1, var2, var1.endPos(), this.type, var3);
   }
}
