package gnu.kawa.xml;

import gnu.kawa.xml.SortedNodes;
import gnu.lists.AbstractSequence;
import gnu.mapping.Procedure2;
import gnu.mapping.Values;

public class IntersectNodes extends Procedure2 {

   public static final IntersectNodes exceptNodes = new IntersectNodes(true);
   public static final IntersectNodes intersectNodes = new IntersectNodes(false);
   boolean isExcept;


   public IntersectNodes(boolean var1) {
      this.isExcept = var1;
   }

   public Object apply2(Object var1, Object var2) {
      SortedNodes var3 = new SortedNodes();
      SortedNodes var4 = new SortedNodes();
      SortedNodes var5 = new SortedNodes();
      Values.writeValues(var1, var3);
      Values.writeValues(var2, var4);
      int var6 = 0;
      AbstractSequence var13 = null;
      int var8 = 0;
      int var7 = 0;
      int var9 = 0;

      while(true) {
         AbstractSequence var14 = var3.getSeq(var9);
         if(var14 == null) {
            return var5;
         }

         int var11 = var3.getPos(var9);
         int var10;
         if(var7 == -1) {
            var7 = AbstractSequence.compare(var14, var11, var13, var8);
            var10 = var8;
         } else if(var7 == 0) {
            var7 = 1;
            var10 = var8;
         } else {
            var10 = var8;
         }

         while(true) {
            var8 = var7;
            if(var7 > 0) {
               var13 = var4.getSeq(var6);
               if(var13 != null) {
                  var10 = var4.getPos(var6);
                  var7 = AbstractSequence.compare(var14, var11, var13, var10);
                  ++var6;
                  continue;
               }

               var8 = -2;
            }

            boolean var12;
            if(var8 == 0) {
               var12 = true;
            } else {
               var12 = false;
            }

            if(var12 != this.isExcept) {
               var5.writePosition(var14, var11);
            }

            ++var9;
            var7 = var8;
            var8 = var10;
            break;
         }
      }
   }
}
