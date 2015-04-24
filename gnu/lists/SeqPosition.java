package gnu.lists;

import gnu.lists.AbstractSequence;
import gnu.lists.Sequence;
import java.util.Enumeration;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class SeqPosition implements ListIterator, Enumeration {

   public int ipos;
   public AbstractSequence sequence;


   public SeqPosition() {
   }

   public SeqPosition(AbstractSequence var1) {
      this.sequence = var1;
   }

   public SeqPosition(AbstractSequence var1, int var2) {
      this.sequence = var1;
      this.ipos = var2;
   }

   public SeqPosition(AbstractSequence var1, int var2, boolean var3) {
      this.sequence = var1;
      this.ipos = var1.createPos(var2, var3);
   }

   public static SeqPosition make(AbstractSequence var0, int var1) {
      return new SeqPosition(var0, var0.copyPos(var1));
   }

   public void add(Object var1) {
      this.setPos(this.sequence.addPos(this.getPos(), var1));
   }

   public SeqPosition copy() {
      return new SeqPosition(this.sequence, this.sequence.copyPos(this.getPos()));
   }

   public void finalize() {
      this.release();
   }

   public final int fromEndIndex() {
      return this.sequence.fromEndIndex(this.getPos());
   }

   public int getContainingSequenceSize() {
      return this.sequence.getContainingSequenceSize(this.getPos());
   }

   public Object getNext() {
      return this.sequence.getPosNext(this.getPos());
   }

   public int getNextKind() {
      return this.sequence.getNextKind(this.getPos());
   }

   public String getNextTypeName() {
      return this.sequence.getNextTypeName(this.getPos());
   }

   public Object getNextTypeObject() {
      return this.sequence.getNextTypeObject(this.getPos());
   }

   public int getPos() {
      return this.ipos;
   }

   public Object getPrevious() {
      return this.sequence.getPosPrevious(this.getPos());
   }

   public boolean gotoChildrenStart() {
      int var1 = this.sequence.firstChildPos(this.getPos());
      if(var1 == 0) {
         return false;
      } else {
         this.ipos = var1;
         return true;
      }
   }

   public final void gotoEnd(AbstractSequence var1) {
      this.setPos(var1, var1.endPos());
   }

   public boolean gotoNext() {
      int var1 = this.sequence.nextPos(this.ipos);
      if(var1 != 0) {
         this.ipos = var1;
         return true;
      } else {
         this.ipos = -1;
         return false;
      }
   }

   public boolean gotoPrevious() {
      int var1 = this.sequence.previousPos(this.ipos);
      if(var1 != -1) {
         this.ipos = var1;
         return true;
      } else {
         this.ipos = 0;
         return false;
      }
   }

   public final void gotoStart(AbstractSequence var1) {
      this.setPos(var1, var1.startPos());
   }

   public final boolean hasMoreElements() {
      return this.hasNext();
   }

   public boolean hasNext() {
      return this.sequence.hasNext(this.getPos());
   }

   public boolean hasPrevious() {
      return this.sequence.hasPrevious(this.getPos());
   }

   public boolean isAfter() {
      return this.sequence.isAfterPos(this.getPos());
   }

   public Object next() {
      Object var1 = this.getNext();
      if(var1 != Sequence.eofValue && this.gotoNext()) {
         return var1;
      } else {
         throw new NoSuchElementException();
      }
   }

   public final Object nextElement() {
      return this.next();
   }

   public int nextIndex() {
      return this.sequence.nextIndex(this.getPos());
   }

   public Object previous() {
      Object var1 = this.getPrevious();
      if(var1 != Sequence.eofValue && this.gotoPrevious()) {
         return var1;
      } else {
         throw new NoSuchElementException();
      }
   }

   public final int previousIndex() {
      return this.sequence.nextIndex(this.getPos()) - 1;
   }

   public void release() {
      if(this.sequence != null) {
         this.sequence.releasePos(this.getPos());
         this.sequence = null;
      }

   }

   public void remove() {
      AbstractSequence var1 = this.sequence;
      int var3 = this.getPos();
      byte var2;
      if(this.isAfter()) {
         var2 = -1;
      } else {
         var2 = 1;
      }

      var1.removePos(var3, var2);
   }

   public void set(AbstractSequence var1, int var2, boolean var3) {
      if(this.sequence != null) {
         this.sequence.releasePos(this.ipos);
      }

      this.sequence = var1;
      this.ipos = var1.createPos(var2, var3);
   }

   public void set(SeqPosition var1) {
      if(this.sequence != null) {
         this.sequence.releasePos(this.ipos);
      }

      this.sequence = var1.sequence;
      var1.ipos = this.sequence.copyPos(var1.ipos);
   }

   public final void set(Object var1) {
      if(this.isAfter()) {
         this.setPrevious(var1);
      } else {
         this.setNext(var1);
      }
   }

   public void setNext(Object var1) {
      this.sequence.setPosNext(this.getPos(), var1);
   }

   public void setPos(int var1) {
      if(this.sequence != null) {
         this.sequence.releasePos(this.getPos());
      }

      this.ipos = var1;
   }

   public void setPos(AbstractSequence var1, int var2) {
      if(this.sequence != null) {
         this.sequence.releasePos(this.getPos());
      }

      this.ipos = var2;
      this.sequence = var1;
   }

   public void setPrevious(Object var1) {
      this.sequence.setPosPrevious(this.getPos(), var1);
   }

   public String toInfo() {
      StringBuffer var1 = new StringBuffer(60);
      var1.append('{');
      if(this.sequence == null) {
         var1.append("null sequence");
      } else {
         var1.append(this.sequence.getClass().getName());
         var1.append('@');
         var1.append(System.identityHashCode(this.sequence));
      }

      var1.append(" ipos: ");
      var1.append(this.ipos);
      var1.append('}');
      return var1.toString();
   }

   public String toString() {
      if(this.sequence == null) {
         return this.toInfo();
      } else {
         Object var1 = this.sequence.getPosNext(this.ipos);
         StringBuilder var2 = (new StringBuilder()).append("@").append(this.nextIndex()).append(": ");
         String var3;
         if(var1 == null) {
            var3 = "(null)";
         } else {
            var3 = var1.toString();
         }

         return var2.append(var3).toString();
      }
   }
}
