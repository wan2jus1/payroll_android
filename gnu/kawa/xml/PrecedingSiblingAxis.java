package gnu.kawa.xml;

import gnu.kawa.xml.TreeScanner;
import gnu.lists.AbstractSequence;
import gnu.lists.NodePredicate;
import gnu.lists.PositionConsumer;

public class PrecedingSiblingAxis extends TreeScanner {

   public static PrecedingSiblingAxis make(NodePredicate var0) {
      PrecedingSiblingAxis var1 = new PrecedingSiblingAxis();
      var1.type = var0;
      return var1;
   }

   public void scan(AbstractSequence var1, int var2, PositionConsumer var3) {
      int var4 = var1.endPos();
      int var5 = var1.parentPos(var2);
      if(var5 != var4) {
         var5 = var1.firstChildPos(var5);
         if(var5 != 0) {
            var4 = var5;
            if(this.type.isInstancePos(var1, var5)) {
               var3.writePosition(var1, var5);
               var4 = var5;
            }

            while(true) {
               var4 = var1.nextMatching(var4, this.type, var2, false);
               if(var4 == 0) {
                  break;
               }

               var3.writePosition(var1, var4);
            }
         }
      }

   }
}
