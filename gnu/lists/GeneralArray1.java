package gnu.lists;

import gnu.lists.Consumer;
import gnu.lists.GeneralArray;
import gnu.lists.Sequence;

public class GeneralArray1 extends GeneralArray implements Sequence {

   public void consumePosRange(int var1, int var2, Consumer var3) {
      if(!var3.ignoring()) {
         while(!this.equals(var1, var2)) {
            if(!this.hasNext(var1)) {
               throw new RuntimeException();
            }

            this.base.consume(this.offset + this.strides[0] * (var1 >>> 1), 1, var3);
            var1 = this.nextPos(var1);
         }
      }

   }

   public int createPos(int var1, boolean var2) {
      byte var3;
      if(var2) {
         var3 = 1;
      } else {
         var3 = 0;
      }

      return var3 | var1 << 1;
   }

   protected int nextIndex(int var1) {
      return var1 == -1?this.size():var1 >>> 1;
   }
}
