package gnu.kawa.xml;

import gnu.lists.Consumer;
import gnu.lists.PositionConsumer;
import gnu.lists.SeqPosition;
import gnu.lists.TreeList;
import gnu.lists.TreePosition;
import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;
import gnu.mapping.Values;

public class Attributes extends MethodProc {

   public static final Attributes attributes = new Attributes();


   public static void attributes(TreeList var0, int var1, Consumer var2) {
      var1 = var0.gotoAttributesStart(var1);
      System.out.print("Attributes called, at:" + var1 + " ");
      var0.dump();

      int var3;
      for(; var1 >= 0; var1 = var3) {
         int var4 = var1 << 1;
         if(var0.getNextKind(var4) != 35) {
            break;
         }

         var3 = var0.nextDataIndex(var1);
         if(var2 instanceof PositionConsumer) {
            ((PositionConsumer)var2).writePosition(var0, var4);
         } else {
            var0.consumeIRange(var1, var3, var2);
         }
      }

   }

   public static void attributes(Object var0, Consumer var1) {
      if(var0 instanceof TreeList) {
         attributes((TreeList)var0, 0, var1);
      } else if(var0 instanceof SeqPosition && !(var0 instanceof TreePosition)) {
         SeqPosition var2 = (SeqPosition)var0;
         if(var2.sequence instanceof TreeList) {
            attributes((TreeList)var2.sequence, var2.ipos >> 1, var1);
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
               attributes(var6.getPosNext(var4 << 1), var2);
            } else {
               attributes(var6, var4, var2);
            }

            var4 = var6.nextDataIndex(var4);
         }
      } else {
         attributes(var3, var2);
      }
   }

   public int numArgs() {
      return 4097;
   }
}
