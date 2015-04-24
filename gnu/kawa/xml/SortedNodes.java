package gnu.kawa.xml;

import gnu.kawa.xml.Nodes;
import gnu.lists.AbstractSequence;

public class SortedNodes extends Nodes {

   int nesting = 0;


   int compareIndex(int var1, AbstractSequence var2, int var3) {
      if(this.data[var1] != '\uf10f') {
         throw new RuntimeException("invalid kind of value to compare");
      } else {
         return AbstractSequence.compare((AbstractSequence)this.objects[this.getIntN(var1 + 1)], this.getIntN(var1 + 3), var2, var3);
      }
   }

   int find(int var1, int var2, AbstractSequence var3, int var4) {
      byte var6 = 0;
      int var5 = var2;
      var2 = var6;

      while(var2 < var5) {
         int var8 = var2 + var5 >>> 1;
         int var7 = this.compareIndex(var8 * 5 + var1, var3, var4);
         if(var7 == 0) {
            return -1;
         }

         if(var7 > 0) {
            var5 = var8;
         } else {
            var2 = var8 + 1;
         }
      }

      return var2 * 5 + var1;
   }

   public void writePosition(AbstractSequence var1, int var2) {
      if(this.count > 0) {
         label22: {
            int var3 = this.gapStart - 5;
            int var4 = this.compareIndex(var3, var1, var2);
            if(var4 < 0) {
               var3 = this.gapEnd;
               var3 = this.find(var3, (this.data.length - var3) / 5, var1, var2);
               if(var3 >= 0) {
                  var4 = var3 - this.gapEnd;
                  if(var4 > 0) {
                     System.arraycopy(this.data, this.gapEnd, this.data, this.gapStart, var4);
                     this.gapEnd = var3;
                     this.gapStart += var4;
                  }
                  break label22;
               }
            } else if(var4 != 0) {
               var3 = this.find(0, var3 / 5, var1, var2);
               if(var3 >= 0) {
                  var4 = this.gapStart - var3;
                  if(var4 > 0) {
                     System.arraycopy(this.data, var3, this.data, this.gapEnd - var4, var4);
                     this.gapStart = var3;
                     this.gapEnd -= var4;
                  }
                  break label22;
               }
            }

            return;
         }
      }

      super.writePosition(var1, var2);
   }
}
