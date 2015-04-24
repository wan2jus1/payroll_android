package gnu.lists;

import gnu.lists.AbstractSequence;
import gnu.lists.Array;
import gnu.lists.FVector;
import gnu.lists.GeneralArray1;
import gnu.lists.SimpleVector;

public class GeneralArray extends AbstractSequence implements Array {

   static final int[] zeros = new int[8];
   SimpleVector base;
   int[] dimensions;
   int[] lowBounds;
   int offset;
   boolean simple = true;
   int[] strides;


   public GeneralArray() {
   }

   public GeneralArray(int[] var1) {
      int var3 = 1;
      int var4 = var1.length;
      if(var4 <= zeros.length) {
         this.lowBounds = zeros;
      } else {
         this.lowBounds = new int[var4];
      }

      int[] var2 = new int[var4];

      while(true) {
         --var4;
         if(var4 < 0) {
            this.base = new FVector(var3);
            this.dimensions = var1;
            this.offset = 0;
            return;
         }

         var2[var4] = var3;
         var3 *= var1[var4];
      }
   }

   public static Array makeSimple(int[] var0, int[] var1, SimpleVector var2) {
      int var6 = var1.length;
      int[] var3 = var0;
      if(var0 == null) {
         var0 = zeros;
         var3 = var0;
         if(var6 > var0.length) {
            var3 = new int[var6];
         }
      }

      if(var6 == 1 && var3[0] == 0) {
         return var2;
      } else {
         GeneralArray var7 = new GeneralArray();
         int[] var4 = new int[var6];
         int var5 = 1;

         while(true) {
            --var6;
            if(var6 < 0) {
               var7.strides = var4;
               var7.dimensions = var1;
               var7.lowBounds = var3;
               var7.base = var2;
               return var7;
            }

            var4[var6] = var5;
            var5 *= var1[var6];
         }
      }
   }

   public static void toString(Array var0, StringBuffer var1) {
      var1.append("#<array");
      int var3 = var0.rank();

      for(int var2 = 0; var2 < var3; ++var2) {
         var1.append(' ');
         int var4 = var0.getLowBound(var2);
         int var5 = var0.getSize(var2);
         if(var4 != 0) {
            var1.append(var4);
            var1.append(':');
         }

         var1.append(var4 + var5);
      }

      var1.append('>');
   }

   public int createPos(int var1, boolean var2) {
      int var3 = this.offset;
      int var4 = this.dimensions.length;

      while(true) {
         int var5 = var1;
         --var4;
         if(var4 < 0) {
            byte var7;
            if(var2) {
               var7 = 1;
            } else {
               var7 = 0;
            }

            return var7 | var3 << 1;
         }

         int var6 = this.dimensions[var4];
         var1 /= var6;
         var3 += this.strides[var4] * (var5 % var6);
      }
   }

   public Object get(int var1) {
      return this.getRowMajor(var1);
   }

   public Object get(int[] var1) {
      return this.base.get(this.getEffectiveIndex(var1));
   }

   public int getEffectiveIndex(int[] var1) {
      int var2 = this.offset;
      int var3 = this.dimensions.length;

      while(true) {
         --var3;
         if(var3 < 0) {
            return var2;
         }

         int var4 = var1[var3];
         int var5 = this.lowBounds[var3];
         if(var4 < var5) {
            break;
         }

         var4 -= var5;
         if(var4 >= this.dimensions[var3]) {
            break;
         }

         var2 += this.strides[var3] * var4;
      }

      throw new IndexOutOfBoundsException();
   }

   public int getLowBound(int var1) {
      return this.lowBounds[var1];
   }

   public Object getRowMajor(int var1) {
      if(this.simple) {
         return this.base.get(var1);
      } else {
         int var2 = this.offset;
         int var3 = this.dimensions.length;

         while(true) {
            int var4 = var1;
            --var3;
            if(var3 < 0) {
               return this.base.get(var2);
            }

            int var5 = this.dimensions[var3];
            var1 /= var5;
            var2 += this.strides[var3] * (var4 % var5);
         }
      }
   }

   public int getSize(int var1) {
      return this.dimensions[var1];
   }

   public int rank() {
      return this.dimensions.length;
   }

   public Object set(int[] var1, Object var2) {
      return this.base.set(this.getEffectiveIndex(var1), var2);
   }

   public int size() {
      int var1 = 1;
      int var2 = this.dimensions.length;

      while(true) {
         --var2;
         if(var2 < 0) {
            return var1;
         }

         var1 *= this.dimensions[var2];
      }
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      toString(this, var1);
      return var1.toString();
   }

   public Array transpose(int[] var1, int[] var2, int var3, int[] var4) {
      Object var5;
      if(var2.length == 1 && var1[0] == 0) {
         var5 = new GeneralArray1();
      } else {
         var5 = new GeneralArray();
      }

      ((GeneralArray)var5).offset = var3;
      ((GeneralArray)var5).strides = var4;
      ((GeneralArray)var5).dimensions = var2;
      ((GeneralArray)var5).lowBounds = var1;
      ((GeneralArray)var5).base = this.base;
      ((GeneralArray)var5).simple = false;
      return (Array)var5;
   }
}
