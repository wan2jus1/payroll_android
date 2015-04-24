package gnu.lists;

import gnu.lists.Consumer;
import gnu.lists.GapVector;
import gnu.lists.SimpleVector;

public class StableVector extends GapVector {

   static final int END_POSITION = 1;
   protected static final int FREE_POSITION = -2;
   static final int START_POSITION = 0;
   protected int free;
   protected int[] positions;


   protected StableVector() {
   }

   public StableVector(SimpleVector var1) {
      super(var1);
      this.positions = new int[16];
      this.positions[0] = 0;
      this.positions[1] = var1.getBufferLength() << 1 | 1;
      this.free = -1;
      int var2 = this.positions.length;

      while(true) {
         --var2;
         if(var2 <= 1) {
            return;
         }

         this.positions[var2] = this.free;
         this.free = var2;
      }
   }

   protected int addPos(int var1, Object var2) {
      int var5 = this.positions[var1];
      int var4 = var5 >>> 1;
      int var3 = var4;
      if(var4 >= this.gapStart) {
         var3 = var4 + (this.gapEnd - this.gapStart);
      }

      var4 = var1;
      if((var5 & 1) == 0) {
         if(var1 == 0) {
            var4 = this.createPos(0, true);
         } else {
            this.positions[var1] = var5 | 1;
            var4 = var1;
         }
      }

      this.add(var3, var2);
      return var4;
   }

   protected void adjustPositions(int var1, int var2, int var3) {
      if(this.free >= -1) {
         this.unchainFreelist();
      }

      int var4 = this.positions.length;

      while(true) {
         int var5 = var4 - 1;
         if(var5 <= 0) {
            return;
         }

         int var6 = this.positions[var5];
         var4 = var5;
         if(var6 != -2) {
            int var7 = var6 ^ Integer.MIN_VALUE;
            var4 = var5;
            if(var7 >= (var1 ^ Integer.MIN_VALUE)) {
               var4 = var5;
               if(var7 <= (var2 ^ Integer.MIN_VALUE)) {
                  this.positions[var5] = var6 + var3;
                  var4 = var5;
               }
            }
         }
      }
   }

   protected int allocPositionIndex() {
      if(this.free == -2) {
         this.chainFreelist();
      }

      int var2;
      if(this.free < 0) {
         int var3 = this.positions.length;
         int[] var1 = new int[var3 * 2];
         System.arraycopy(this.positions, 0, var1, 0, var3);
         var2 = var3 * 2;

         while(true) {
            --var2;
            if(var2 < var3) {
               this.positions = var1;
               break;
            }

            var1[var2] = this.free;
            this.free = var2;
         }
      }

      var2 = this.free;
      this.free = this.positions[this.free];
      return var2;
   }

   protected void chainFreelist() {
      this.free = -1;
      int var1 = this.positions.length;

      while(true) {
         int var2 = var1 - 1;
         if(var2 <= 1) {
            return;
         }

         var1 = var2;
         if(this.positions[var2] == -2) {
            this.positions[var2] = this.free;
            this.free = var2;
            var1 = var2;
         }
      }
   }

   public void consumePosRange(int var1, int var2, Consumer var3) {
      super.consumePosRange(this.positions[var1], this.positions[var2], var3);
   }

   public int copyPos(int var1) {
      int var2 = var1;
      if(var1 > 1) {
         var2 = this.allocPositionIndex();
         this.positions[var2] = this.positions[var1];
      }

      return var2;
   }

   public int createPos(int var1, boolean var2) {
      byte var5 = 1;
      if(var1 == 0 && !var2) {
         return 0;
      } else if(var2 && var1 == this.size()) {
         return 1;
      } else {
         int var4;
         label28: {
            if(var1 <= this.gapStart) {
               var4 = var1;
               if(var1 != this.gapStart) {
                  break label28;
               }

               var4 = var1;
               if(!var2) {
                  break label28;
               }
            }

            var4 = var1 + (this.gapEnd - this.gapStart);
         }

         int var6 = this.allocPositionIndex();
         int[] var3 = this.positions;
         byte var7;
         if(var2) {
            var7 = var5;
         } else {
            var7 = 0;
         }

         var3[var6] = var7 | var4 << 1;
         return var6;
      }
   }

   public int endPos() {
      return 1;
   }

   public void fillPosRange(int var1, int var2, Object var3) {
      this.fillPosRange(this.positions[var1], this.positions[var2], var3);
   }

   protected void gapReserve(int var1, int var2) {
      int var3 = this.gapEnd;
      int var4 = this.gapStart;
      if(var2 > var3 - var4) {
         int var5 = this.base.size;
         super.gapReserve(var1, var2);
         var2 = this.base.size;
         if(var1 != var4) {
            this.adjustPositions(var3 << 1, var5 << 1 | 1, var4 - var3 << 1);
            this.adjustPositions(this.gapStart << 1, var2 << 1 | 1, this.gapEnd - this.gapStart << 1);
            return;
         }

         this.adjustPositions(var3 << 1, var2 << 1 | 1, var2 - var5 << 1);
      } else if(var1 != this.gapStart) {
         this.shiftGap(var1);
         return;
      }

   }

   public boolean hasNext(int var1) {
      int var2 = this.positions[var1] >>> 1;
      var1 = var2;
      if(var2 >= this.gapStart) {
         var1 = var2 + (this.gapEnd - this.gapStart);
      }

      return var1 < this.base.getBufferLength();
   }

   protected boolean isAfterPos(int var1) {
      return (this.positions[var1] & 1) != 0;
   }

   public int nextIndex(int var1) {
      int var2 = this.positions[var1] >>> 1;
      var1 = var2;
      if(var2 > this.gapStart) {
         var1 = var2 - (this.gapEnd - this.gapStart);
      }

      return var1;
   }

   public int nextPos(int var1) {
      int var4 = this.positions[var1];
      int var3 = var4 >>> 1;
      int var2 = var3;
      if(var3 >= this.gapStart) {
         var2 = var3 + (this.gapEnd - this.gapStart);
      }

      if(var2 >= this.base.getBufferLength()) {
         this.releasePos(var1);
         return 0;
      } else {
         var2 = var1;
         if(var1 == 0) {
            var2 = this.createPos(0, true);
         }

         this.positions[var2] = var4 | 1;
         return var2;
      }
   }

   public void releasePos(int var1) {
      if(var1 >= 2) {
         if(this.free == -2) {
            this.chainFreelist();
         }

         this.positions[var1] = this.free;
         this.free = var1;
      }

   }

   protected void removePosRange(int var1, int var2) {
      super.removePosRange(this.positions[var1], this.positions[var2]);
      int var3 = this.gapStart;
      int var4 = this.gapEnd;
      if(this.free >= -1) {
         this.unchainFreelist();
      }

      var1 = this.positions.length;

      while(true) {
         var2 = var1 - 1;
         if(var2 <= 0) {
            return;
         }

         int var6 = this.positions[var2];
         var1 = var2;
         if(var6 != -2) {
            int var5 = var6 >> 1;
            boolean var7;
            if((var6 & 1) != 0) {
               var7 = true;
            } else {
               var7 = false;
            }

            if(var7) {
               var1 = var2;
               if(var5 >= var3) {
                  var1 = var2;
                  if(var5 < var4) {
                     this.positions[var2] = this.gapEnd << 1 | 1;
                     var1 = var2;
                  }
               }
            } else {
               var1 = var2;
               if(var5 > var3) {
                  var1 = var2;
                  if(var5 <= var4) {
                     this.positions[var2] = this.gapStart << 1;
                     var1 = var2;
                  }
               }
            }
         }
      }
   }

   protected void shiftGap(int var1) {
      int var2 = this.gapStart;
      int var3 = var1 - var2;
      int var4;
      if(var3 > 0) {
         int var5 = this.gapEnd;
         var2 = var2 - var5 << 1;
         var4 = var5 << 1;
         var3 = (var5 + var3 << 1) - 1;
      } else {
         if(var1 == var2) {
            return;
         }

         var4 = (var1 << 1) + 1;
         var3 = var2 << 1;
         var2 = this.gapEnd - var2 << 1;
      }

      super.shiftGap(var1);
      this.adjustPositions(var4, var3, var2);
   }

   public int startPos() {
      return 0;
   }

   protected void unchainFreelist() {
      int var2;
      for(int var1 = this.free; var1 >= 0; var1 = var2) {
         var2 = this.positions[var1];
         this.positions[var1] = -2;
      }

      this.free = -2;
   }
}
