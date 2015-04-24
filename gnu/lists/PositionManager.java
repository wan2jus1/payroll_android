package gnu.lists;

import gnu.lists.ExtPosition;
import gnu.lists.SeqPosition;

public class PositionManager {

   static final PositionManager manager = new PositionManager();
   int freeListHead = -1;
   int[] ivals = new int[50];
   SeqPosition[] positions = new SeqPosition[50];


   public PositionManager() {
      this.addToFreeList(this.ivals, 1, this.ivals.length);
   }

   private void addToFreeList(int[] var1, int var2, int var3) {
      int var4;
      for(var4 = this.freeListHead; var2 < var3; var4 = var2++) {
         var1[var2] = var4;
      }

      this.freeListHead = var4;
   }

   private int getFreeSlot() {
      int var4 = this.freeListHead;
      int var3 = var4;
      if(var4 < 0) {
         var3 = this.positions.length;
         SeqPosition[] var1 = new SeqPosition[var3 * 2];
         int[] var2 = new int[var3 * 2];
         System.arraycopy(this.positions, 0, var1, 0, var3);
         System.arraycopy(this.ivals, 0, var2, 0, var3);
         this.positions = var1;
         this.ivals = var2;
         this.addToFreeList(var2, var3, var3 * 2);
         var3 = this.freeListHead;
      }

      this.freeListHead = this.ivals[var3];
      return var3;
   }

   public static SeqPosition getPositionObject(int param0) {
      // $FF: Couldn't be decompiled
   }

   public int register(SeqPosition var1) {
      synchronized(this){}

      int var2;
      try {
         var2 = this.getFreeSlot();
         this.positions[var2] = var1;
         this.ivals[var2] = -1;
      } finally {
         ;
      }

      return var2;
   }

   public void release(int var1) {
      synchronized(this){}

      try {
         SeqPosition var2 = this.positions[var1];
         if(var2 instanceof ExtPosition) {
            ((ExtPosition)var2).position = -1;
         }

         this.positions[var1] = null;
         this.ivals[var1] = this.freeListHead;
         this.freeListHead = var1;
         var2.release();
      } finally {
         ;
      }

   }
}
