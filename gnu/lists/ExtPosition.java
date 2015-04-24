package gnu.lists;

import gnu.lists.AbstractSequence;
import gnu.lists.PositionManager;
import gnu.lists.SeqPosition;

public class ExtPosition extends SeqPosition {

   int position = -1;


   public int getPos() {
      if(this.position < 0) {
         this.position = PositionManager.manager.register(this);
      }

      return this.position;
   }

   public final boolean isAfter() {
      return (this.ipos & 1) != 0;
   }

   public void release() {
      if(this.position >= 0) {
         PositionManager.manager.release(this.position);
      }

      this.sequence = null;
   }

   public void setPos(AbstractSequence var1, int var2) {
      throw var1.unsupported("setPos");
   }
}
