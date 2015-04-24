package gnu.lists;

import gnu.lists.AbstractSequence;
import gnu.lists.PositionManager;

public abstract class ExtSequence extends AbstractSequence {

   public int copyPos(int var1) {
      return var1 <= 0?var1:PositionManager.manager.register(PositionManager.getPositionObject(var1).copy());
   }

   protected boolean isAfterPos(int var1) {
      if(var1 <= 0) {
         if(var1 >= 0) {
            return false;
         }
      } else if((PositionManager.getPositionObject(var1).ipos & 1) == 0) {
         return false;
      }

      return true;
   }

   protected int nextIndex(int var1) {
      return var1 == -1?this.size():(var1 == 0?0:PositionManager.getPositionObject(var1).nextIndex());
   }

   protected void releasePos(int var1) {
      if(var1 > 0) {
         PositionManager.manager.release(var1);
      }

   }
}
