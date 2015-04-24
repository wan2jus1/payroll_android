package gnu.lists;

import gnu.lists.AbstractSequence;
import gnu.lists.Consumer;
import gnu.lists.Sequence;
import gnu.lists.SimpleVector;

public class GapVector extends AbstractSequence implements Sequence {

   public SimpleVector base;
   public int gapEnd;
   public int gapStart;


   protected GapVector() {
   }

   public GapVector(SimpleVector var1) {
      this.base = var1;
      this.gapStart = 0;
      this.gapEnd = var1.size;
   }

   public void add(int var1, Object var2) {
      this.gapReserve(var1, 1);
      this.base.set(var1, var2);
      ++this.gapStart;
   }

   protected int addPos(int var1, Object var2) {
      int var3 = var1 >>> 1;
      var1 = var3;
      if(var3 >= this.gapStart) {
         var1 = var3 + (this.gapEnd - this.gapStart);
      }

      this.add(var1, var2);
      return var1 + 1 << 1 | 1;
   }

   public void consumePosRange(int var1, int var2, Consumer var3) {
      if(!var3.ignoring()) {
         int var5 = var1 >>> 1;
         int var6 = var2 >>> 1;
         if(var5 < this.gapStart) {
            int var4;
            if(var6 > this.gapStart) {
               var4 = var6;
            } else {
               var4 = this.gapStart;
            }

            this.consumePosRange(var1, var4 << 1, var3);
         }

         if(var6 > this.gapEnd) {
            var1 = var5;
            if(var5 < this.gapEnd) {
               var1 = this.gapEnd;
            }

            this.consumePosRange(var1 << 1, var2, var3);
            return;
         }
      }

   }

   public int createPos(int var1, boolean var2) {
      int var3 = var1;
      if(var1 > this.gapStart) {
         var3 = var1 + (this.gapEnd - this.gapStart);
      }

      byte var4;
      if(var2) {
         var4 = 1;
      } else {
         var4 = 0;
      }

      return var4 | var3 << 1;
   }

   public void fill(Object var1) {
      this.base.fill(this.gapEnd, this.base.size, var1);
      this.base.fill(0, this.gapStart, var1);
   }

   public void fillPosRange(int var1, int var2, Object var3) {
      int var4;
      if(var1 == -1) {
         var4 = this.base.size;
      } else {
         var4 = var1 >>> 1;
      }

      if(var2 == -1) {
         var1 = this.base.size;
      } else {
         var1 = var2 >>> 1;
      }

      if(this.gapStart < var1) {
         var2 = this.gapStart;
      } else {
         var2 = var1;
      }

      while(var4 < var2) {
         this.base.setBuffer(var4, var3);
         ++var4;
      }

      for(var2 = this.gapEnd; var2 < var1; ++var2) {
         this.base.setBuffer(var2, var3);
      }

   }

   protected final void gapReserve(int var1) {
      this.gapReserve(this.gapStart, var1);
   }

   protected void gapReserve(int var1, int var2) {
      int var3 = 16;
      if(var2 > this.gapEnd - this.gapStart) {
         int var4 = this.base.size;
         if(var4 >= 16) {
            var3 = var4 * 2;
         }

         int var5 = var4 - (this.gapEnd - this.gapStart);
         var4 = var5 + var2;
         var2 = var3;
         if(var3 < var4) {
            var2 = var4;
         }

         var2 = var2 - var5 + var1;
         this.base.resizeShift(this.gapStart, this.gapEnd, var1, var2);
         this.gapStart = var1;
         this.gapEnd = var2;
      } else if(var1 != this.gapStart) {
         this.shiftGap(var1);
         return;
      }

   }

   public Object get(int var1) {
      int var2 = var1;
      if(var1 >= this.gapStart) {
         var2 = var1 + (this.gapEnd - this.gapStart);
      }

      return this.base.get(var2);
   }

   public int getNextKind(int var1) {
      return this.hasNext(var1)?this.base.getElementKind():0;
   }

   public int getSegment(int var1, int var2) {
      int var4 = this.size();
      if(var1 >= 0 && var1 <= var4) {
         int var3;
         if(var2 < 0) {
            var3 = 0;
         } else {
            var3 = var2;
            if(var1 + var2 > var4) {
               var3 = var4 - var1;
            }
         }

         var2 = var1;
         if(var1 + var3 > this.gapStart) {
            if(var1 >= this.gapStart) {
               return var1 + (this.gapEnd - this.gapStart);
            }

            if(this.gapStart - var1 > var3 >> 1) {
               this.shiftGap(var1 + var3);
               return var1;
            }

            this.shiftGap(var1);
            return var1 + (this.gapEnd - this.gapStart);
         }
      } else {
         var2 = -1;
      }

      return var2;
   }

   public boolean hasNext(int var1) {
      int var2 = var1 >>> 1;
      var1 = var2;
      if(var2 >= this.gapStart) {
         var1 = var2 + (this.gapEnd - this.gapStart);
      }

      return var1 < this.base.size;
   }

   protected boolean isAfterPos(int var1) {
      return (var1 & 1) != 0;
   }

   protected int nextIndex(int var1) {
      if(var1 == -1) {
         var1 = this.base.size;
      } else {
         var1 >>>= 1;
      }

      int var2 = var1;
      if(var1 > this.gapStart) {
         var2 = var1 - (this.gapEnd - this.gapStart);
      }

      return var2;
   }

   protected void removePosRange(int var1, int var2) {
      var1 >>>= 1;
      var2 >>>= 1;
      if(var1 > this.gapEnd) {
         this.shiftGap(var1 - this.gapEnd + this.gapStart);
      } else if(var2 < this.gapStart) {
         this.shiftGap(var2);
      }

      if(var1 < this.gapStart) {
         this.base.clearBuffer(var1, this.gapStart - var1);
         this.gapStart = var1;
      }

      if(var2 > this.gapEnd) {
         this.base.clearBuffer(this.gapEnd, var2 - this.gapEnd);
         this.gapEnd = var2;
      }

   }

   public Object set(int var1, Object var2) {
      int var3 = var1;
      if(var1 >= this.gapStart) {
         var3 = var1 + (this.gapEnd - this.gapStart);
      }

      return this.base.set(var3, var2);
   }

   protected void shiftGap(int var1) {
      int var2 = var1 - this.gapStart;
      if(var2 > 0) {
         this.base.shift(this.gapEnd, this.gapStart, var2);
      } else if(var2 < 0) {
         this.base.shift(var1, this.gapEnd + var2, -var2);
      }

      this.gapEnd += var2;
      this.gapStart = var1;
   }

   public int size() {
      return this.base.size - (this.gapEnd - this.gapStart);
   }
}
