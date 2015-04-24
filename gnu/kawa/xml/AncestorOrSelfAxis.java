package gnu.kawa.xml;

import gnu.kawa.xml.TreeScanner;
import gnu.lists.AbstractSequence;
import gnu.lists.NodePredicate;
import gnu.lists.PositionConsumer;

public class AncestorOrSelfAxis extends TreeScanner {

   public static AncestorOrSelfAxis make(NodePredicate var0) {
      AncestorOrSelfAxis var1 = new AncestorOrSelfAxis();
      var1.type = var0;
      return var1;
   }

   private static void scan(AbstractSequence var0, int var1, int var2, NodePredicate var3, PositionConsumer var4) {
      if(var1 != var2) {
         scan(var0, var0.parentPos(var1), var2, var3, var4);
         if(var3.isInstancePos(var0, var1)) {
            var4.writePosition(var0, var1);
         }
      }

   }

   public void scan(AbstractSequence var1, int var2, PositionConsumer var3) {
      scan(var1, var2, var1.endPos(), this.type, var3);
   }
}
