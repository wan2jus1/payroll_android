package gnu.lists;

import gnu.lists.AbstractSequence;
import gnu.lists.Sequence;

public class SubSequence extends AbstractSequence implements Sequence {

   AbstractSequence base;
   int ipos0;
   int ipos1;


   public SubSequence() {
   }

   public SubSequence(AbstractSequence var1) {
      this.base = var1;
   }

   public SubSequence(AbstractSequence var1, int var2, int var3) {
      this.base = var1;
      this.ipos0 = var2;
      this.ipos1 = var3;
   }

   public void clear() {
      this.removePosRange(this.ipos0, this.ipos1);
   }

   public int compare(int var1, int var2) {
      return this.base.compare(var1, var2);
   }

   public int createPos(int var1, boolean var2) {
      return this.base.createRelativePos(this.ipos0, var1, var2);
   }

   public int createRelativePos(int var1, int var2, boolean var3) {
      return this.base.createRelativePos(var1, var2, var3);
   }

   public int endPos() {
      return this.ipos1;
   }

   public void finalize() {
      this.base.releasePos(this.ipos0);
      this.base.releasePos(this.ipos1);
   }

   public Object get(int var1) {
      if(var1 >= 0 && var1 < this.size()) {
         int var2 = this.base.nextIndex(this.ipos0);
         return this.base.get(var2 + var1);
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   protected int getIndexDifference(int var1, int var2) {
      return this.base.getIndexDifference(var1, var2);
   }

   public int getNextKind(int var1) {
      return this.base.compare(var1, this.ipos1) >= 0?0:this.base.getNextKind(var1);
   }

   public Object getPosNext(int var1) {
      return this.base.compare(var1, this.ipos1) >= 0?eofValue:this.base.getPosNext(var1);
   }

   public Object getPosPrevious(int var1) {
      return this.base.compare(var1, this.ipos0) <= 0?eofValue:this.base.getPosPrevious(var1);
   }

   protected boolean isAfterPos(int var1) {
      return this.base.isAfterPos(var1);
   }

   protected int nextIndex(int var1) {
      return this.getIndexDifference(var1, this.ipos0);
   }

   public void releasePos(int var1) {
      this.base.releasePos(var1);
   }

   public void removePosRange(int var1, int var2) {
      AbstractSequence var3 = this.base;
      int var4;
      if(var1 == 0) {
         var4 = this.ipos0;
      } else {
         var4 = var1;
         if(var1 == -1) {
            var4 = this.ipos1;
         }
      }

      if(var2 == -1) {
         var1 = this.ipos1;
      } else {
         var1 = var2;
         if(var2 == 0) {
            var1 = this.ipos0;
         }
      }

      var3.removePosRange(var4, var1);
   }

   public int size() {
      return this.base.getIndexDifference(this.ipos1, this.ipos0);
   }

   public int startPos() {
      return this.ipos0;
   }

   protected Sequence subSequencePos(int var1, int var2) {
      return new SubSequence(this.base, var1, var2);
   }
}
