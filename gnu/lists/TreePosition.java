package gnu.lists;

import gnu.lists.AbstractSequence;
import gnu.lists.SeqPosition;
import java.io.PrintStream;

public class TreePosition extends SeqPosition implements Cloneable {

   int depth;
   int[] istack;
   AbstractSequence[] sstack;
   int start;
   private Object xpos;


   public TreePosition() {
      this.depth = -1;
   }

   public TreePosition(AbstractSequence var1, int var2) {
      super(var1, var2, false);
   }

   public TreePosition(TreePosition var1) {
      this.set(var1);
   }

   public TreePosition(Object var1) {
      this.xpos = var1;
      this.depth = -1;
   }

   public Object clone() {
      return new TreePosition(this);
   }

   public void dump() {
      System.err.println("TreePosition dump depth:" + this.depth + " start:" + this.start);

      for(int var3 = 0; var3 <= this.depth; ++var3) {
         AbstractSequence var1;
         if(var3 == 0) {
            var1 = this.sequence;
         } else {
            var1 = this.sstack[this.depth - var3];
         }

         System.err.print("#" + var3 + " seq:" + var1);
         PrintStream var5 = System.err;
         StringBuilder var2 = (new StringBuilder()).append(" ipos:");
         int var4;
         if(var3 == 0) {
            var4 = this.ipos;
         } else {
            var4 = this.istack[this.depth - var3];
         }

         var5.println(var2.append(var4).toString());
      }

   }

   public Object getAncestor(int var1) {
      if(var1 == 0) {
         return this.sequence.getPosNext(this.ipos);
      } else {
         var1 = this.depth - var1;
         if(var1 <= 0) {
            return this.getRoot();
         } else {
            var1 += this.start;
            return this.sstack[var1].getPosNext(this.istack[var1]);
         }
      }
   }

   public int getDepth() {
      return this.depth + 1;
   }

   public Object getPosNext() {
      return this.sequence == null?this.xpos:this.sequence.getPosNext(this.ipos);
   }

   public AbstractSequence getRoot() {
      return this.depth == 0?this.sequence:this.sstack[this.start];
   }

   public boolean gotoAttributesStart() {
      if(this.sequence == null) {
         if(this.xpos instanceof AbstractSequence) {
            ;
         }

         return false;
      } else {
         return this.sequence.gotoAttributesStart(this);
      }
   }

   public boolean gotoChildrenStart() {
      if(this.sequence == null) {
         if(!(this.xpos instanceof AbstractSequence)) {
            return false;
         }

         this.depth = 0;
         this.sequence = (AbstractSequence)this.xpos;
         this.setPos(this.sequence.startPos());
      } else if(!this.sequence.gotoChildrenStart(this)) {
         return false;
      }

      return true;
   }

   public final boolean gotoParent() {
      return this.sequence == null?false:this.sequence.gotoParent(this);
   }

   public void pop() {
      this.sequence.releasePos(this.ipos);
      this.popNoRelease();
   }

   public void popNoRelease() {
      int var1 = this.depth - 1;
      this.depth = var1;
      if(var1 < 0) {
         this.xpos = this.sequence;
         this.sequence = null;
      } else {
         this.sequence = this.sstack[this.start + this.depth];
         this.ipos = this.istack[this.start + this.depth];
      }
   }

   public void push(AbstractSequence var1, int var2) {
      int var5 = this.depth + this.start;
      if(var5 >= 0) {
         if(var5 == 0) {
            this.istack = new int[8];
            this.sstack = new AbstractSequence[8];
         } else if(var5 >= this.istack.length) {
            int var6 = var5 * 2;
            int[] var3 = new int[var6];
            Object[] var4 = new Object[var6];
            AbstractSequence[] var7 = new AbstractSequence[var6];
            System.arraycopy(this.istack, 0, var3, 0, this.depth);
            System.arraycopy(this.sstack, 0, var7, 0, this.depth);
            this.istack = var3;
            this.sstack = var7;
         }

         this.sstack[var5] = this.sequence;
         this.istack[var5] = this.ipos;
      }

      ++this.depth;
      this.sequence = var1;
      this.ipos = var2;
   }

   public void release() {
      while(this.sequence != null) {
         this.sequence.releasePos(this.ipos);
         this.pop();
      }

      this.xpos = null;
   }

   public void set(TreePosition var1) {
      this.release();
      int var3 = var1.depth;
      this.depth = var3;
      if(var3 < 0) {
         this.xpos = var1.xpos;
      } else {
         if(this.sstack == null || this.sstack.length <= var3) {
            this.sstack = new AbstractSequence[var3 + 10];
         }

         if(this.istack == null || this.istack.length <= var3) {
            this.istack = new int[var3 + 10];
         }

         AbstractSequence var2;
         for(var3 = 0; var3 < this.depth; ++var3) {
            int var4 = var3 + var1.start;
            var2 = var1.sstack[var4];
            this.sstack[this.depth - 1] = var2;
            this.istack[this.depth - var3] = var2.copyPos(var1.istack[var4]);
         }

         var2 = var1.sequence;
         this.sequence = var2;
         this.ipos = var2.copyPos(var1.ipos);
      }
   }
}
