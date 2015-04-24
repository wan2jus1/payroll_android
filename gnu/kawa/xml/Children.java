package gnu.kawa.xml;

import gnu.lists.Consumer;
import gnu.lists.PositionConsumer;
import gnu.lists.SeqPosition;
import gnu.lists.TreeList;
import gnu.lists.TreePosition;
import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;
import gnu.mapping.Values;

public class Children extends MethodProc {

   public static final Children children = new Children();


   public static void children(TreeList var0, int var1, Consumer var2) {
      int var3 = var0.gotoChildrenStart(var1);
      if(var3 >= 0) {
         int var5 = var0.nextDataIndex(var1);

         while(true) {
            int var4 = var0.nextNodeIndex(var3, var5);
            var1 = var4;
            if(var4 == var3) {
               var1 = var0.nextDataIndex(var3);
            }

            if(var1 < 0) {
               break;
            }

            if(var2 instanceof PositionConsumer) {
               ((PositionConsumer)var2).writePosition(var0, var3 << 1);
            } else {
               var0.consumeIRange(var3, var1, var2);
            }

            var3 = var1;
         }
      }

   }

   public static void children(Object var0, Consumer var1) {
      if(var0 instanceof TreeList) {
         children((TreeList)var0, 0, var1);
      } else if(var0 instanceof SeqPosition && !(var0 instanceof TreePosition)) {
         SeqPosition var2 = (SeqPosition)var0;
         if(var2.sequence instanceof TreeList) {
            children((TreeList)var2.sequence, var2.ipos >> 1, var1);
            return;
         }
      }

   }

   public void apply(CallContext var1) {
      Consumer var2 = var1.consumer;
      Object var3 = var1.getNextArg();
      var1.lastArg();
      if(var3 instanceof Values) {
         TreeList var6 = (TreeList)var3;
         int var4 = 0;

         while(true) {
            int var5 = var6.getNextKind(var4 << 1);
            if(var5 == 0) {
               return;
            }

            if(var5 == 32) {
               children(var6.getPosNext(var4 << 1), var2);
            } else {
               children(var6, var4, var2);
            }

            var4 = var6.nextDataIndex(var4);
         }
      } else {
         children(var3, var2);
      }
   }

   public int numArgs() {
      return 4097;
   }
}
