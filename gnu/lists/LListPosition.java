package gnu.lists;

import gnu.lists.AbstractSequence;
import gnu.lists.ExtPosition;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.SeqPosition;

class LListPosition extends ExtPosition {

   Object xpos;


   public LListPosition(LList var1, int var2, boolean var3) {
      this.set((LList)var1, var2, var3);
   }

   public LListPosition(LListPosition var1) {
      this.sequence = var1.sequence;
      this.ipos = var1.ipos;
      this.xpos = var1.xpos;
   }

   public SeqPosition copy() {
      return new LListPosition(this);
   }

   public Object getNext() {
      Pair var1 = this.getNextPair();
      return var1 == null?LList.eofValue:var1.car;
   }

   public Pair getNextPair() {
      Object var1;
      if((this.ipos & 1) > 0) {
         if(this.xpos == null) {
            AbstractSequence var2 = this.sequence;
            var1 = var2;
            if(this.ipos >> 1 != 0) {
               var1 = ((Pair)var2).cdr;
            }
         } else {
            var1 = ((Pair)((Pair)((Pair)this.xpos).cdr)).cdr;
         }
      } else if(this.xpos == null) {
         var1 = this.sequence;
      } else {
         var1 = ((Pair)this.xpos).cdr;
      }

      return var1 == LList.Empty?null:(Pair)var1;
   }

   public Object getPrevious() {
      Pair var1 = this.getPreviousPair();
      return var1 == null?LList.eofValue:var1.car;
   }

   public Pair getPreviousPair() {
      int var3 = this.ipos;
      Object var2 = this.xpos;
      Object var1;
      if((var3 & 1) > 0) {
         if(var2 == null) {
            var1 = this.sequence;
         } else {
            var1 = ((Pair)var2).cdr;
         }
      } else {
         var1 = var2;
         if(var2 == null) {
            return null;
         }
      }

      return var1 == LList.Empty?null:(Pair)var1;
   }

   public boolean gotoNext() {
      boolean var3;
      if((this.ipos & 1) != 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      int var4 = this.ipos;
      Object var2 = this.xpos;
      if(var2 != null) {
         Object var1 = var2;
         if(var3) {
            var1 = ((Pair)var2).cdr;
         }

         if(((Pair)var1).cdr == LList.Empty) {
            return false;
         }

         this.xpos = var1;
         this.ipos = (this.ipos | 1) + 2;
      } else if(this.ipos >> 1 == 0) {
         if(this.sequence == LList.Empty) {
            return false;
         }

         this.ipos = 3;
      } else {
         AbstractSequence var5 = this.sequence;
         if(((Pair)var5).cdr == LList.Empty) {
            return false;
         }

         this.ipos = 5;
         this.xpos = var5;
      }

      return true;
   }

   public boolean gotoPrevious() {
      if(this.ipos >>> 1 == 0) {
         return false;
      } else if((this.ipos & 1) != 0) {
         this.ipos -= 3;
         return true;
      } else {
         int var1 = this.nextIndex();
         this.set((LList)((LList)this.sequence), var1 - 1, false);
         return true;
      }
   }

   public boolean hasNext() {
      boolean var3 = true;
      if(this.xpos == null) {
         if(this.ipos >> 1 == 0) {
            if(this.sequence != LList.Empty) {
               var3 = true;
            } else {
               var3 = false;
            }
         } else if(((Pair)this.sequence).cdr == LList.Empty) {
            return false;
         }
      } else {
         Object var2 = ((Pair)this.xpos).cdr;
         Object var1 = var2;
         if((this.ipos & 1) > 0) {
            var1 = ((Pair)var2).cdr;
         }

         if(var1 == LList.Empty) {
            return false;
         }
      }

      return var3;
   }

   public boolean hasPrevious() {
      return this.ipos >>> 1 != 0;
   }

   public int nextIndex() {
      return this.ipos >> 1;
   }

   public void set(AbstractSequence var1, int var2, boolean var3) {
      this.set((LList)((LList)var1), var2, var3);
   }

   public void set(LList var1, int var2, boolean var3) {
      this.sequence = (AbstractSequence)var1;
      byte var4;
      if(var3) {
         var4 = 1;
      } else {
         var4 = 0;
      }

      this.ipos = var4 | var2 << 1;
      if(var3) {
         var2 -= 2;
      } else {
         --var2;
      }

      if(var2 < 0) {
         this.xpos = null;
      } else {
         while(true) {
            --var2;
            if(var2 < 0) {
               this.xpos = var1;
               return;
            }

            var1 = ((Pair)var1).cdr;
         }
      }
   }

   public void setNext(Object var1) {
      this.getNextPair().car = var1;
   }

   public void setPrevious(Object var1) {
      this.getPreviousPair().car = var1;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append("LListPos[");
      var1.append("index:");
      var1.append(this.ipos);
      if(this.isAfter()) {
         var1.append(" after");
      }

      if(this.position >= 0) {
         var1.append(" position:");
         var1.append(this.position);
      }

      var1.append(']');
      return var1.toString();
   }
}
