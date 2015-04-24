package gnu.kawa.functions;

import gnu.lists.Array;
import gnu.lists.FVector;
import gnu.lists.GeneralArray;
import gnu.lists.SimpleVector;
import gnu.mapping.Procedure;
import gnu.mapping.Values;
import gnu.math.IntNum;

public class Arrays {

   static final int[] shapeStrides = new int[]{2, 1};
   static final int[] zeros2 = new int[2];


   public static int effectiveIndex(Array var0, Procedure var1, Object[] var2, int[] var3) throws Throwable {
      Object var6 = var1.applyN(var2);
      if(var6 instanceof Values) {
         Values var7 = (Values)var6;
         int var5 = 0;
         int var4 = 0;

         while(true) {
            var5 = var7.nextPos(var5);
            if(var5 == 0) {
               break;
            }

            var3[var4] = ((Number)var7.getPosPrevious(var5)).intValue();
            ++var4;
         }
      } else {
         var3[0] = ((Number)var6).intValue();
      }

      return var0.getEffectiveIndex(var3);
   }

   public static Array make(Array var0, Object var1) {
      int var7 = var0.getSize(0);
      int[] var4 = new int[var7];
      int[] var2 = null;
      int var5 = 1;
      int var6 = var7;

      while(true) {
         --var6;
         if(var6 < 0) {
            return GeneralArray.makeSimple(var2, var4, new FVector(var5, var1));
         }

         int var8 = ((Number)var0.getRowMajor(var6 * 2)).intValue();
         int var9 = ((Number)var0.getRowMajor(var6 * 2 + 1)).intValue() - var8;
         var4[var6] = var9;
         int[] var3 = var2;
         if(var8 != 0) {
            var3 = var2;
            if(var2 == null) {
               var3 = new int[var7];
            }

            var3[var6] = var8;
         }

         var5 *= var9;
         var2 = var3;
      }
   }

   public static Array makeSimple(Array var0, SimpleVector var1) {
      int var6 = var0.getSize(0);
      int[] var4 = new int[var6];
      int[] var2 = null;
      int var5 = var6;

      while(true) {
         int var7 = var5 - 1;
         if(var7 < 0) {
            return GeneralArray.makeSimple(var2, var4, var1);
         }

         int var8 = ((Number)var0.getRowMajor(var7 * 2)).intValue();
         var4[var7] = ((Number)var0.getRowMajor(var7 * 2 + 1)).intValue() - var8;
         var5 = var7;
         if(var8 != 0) {
            int[] var3 = var2;
            if(var2 == null) {
               var3 = new int[var6];
            }

            var3[var7] = var8;
            var5 = var7;
            var2 = var3;
         }
      }
   }

   public static Array shape(Object[] var0) {
      int var3 = var0.length;
      if((var3 & 1) != 0) {
         throw new RuntimeException("shape: not an even number of arguments");
      } else {
         FVector var4 = new FVector(var0);
         int[] var1 = zeros2;
         int[] var2 = shapeStrides;
         return var4.transpose(var1, new int[]{var3 >> 1, 2}, 0, var2);
      }
   }

   public static Array shareArray(Array var0, Array var1, Procedure var2) throws Throwable {
      int var8 = var1.getSize(0);
      Object[] var3 = new Object[var8];
      int[] var4 = new int[var8];
      int[] var5 = new int[var8];
      boolean var9 = false;
      int var10 = var8;

      while(true) {
         int var11 = var10 - 1;
         if(var11 < 0) {
            var10 = var0.rank();
            int[] var13 = new int[var8];
            if(var9) {
               var8 = 0;
            } else {
               int[] var14 = new int[var10];
               int var15 = effectiveIndex(var0, var2, var3, var14);

               while(true) {
                  var10 = var8 - 1;
                  var8 = var15;
                  if(var10 < 0) {
                     break;
                  }

                  var8 = var4[var10];
                  var11 = var5[var10];
                  if(var8 <= 1) {
                     var13[var10] = 0;
                     var8 = var10;
                  } else {
                     Object var7 = var3[var10];
                     var3[var10] = IntNum.make(var11 + 1);
                     var13[var10] = effectiveIndex(var0, var2, var3, var14) - var15;
                     var3[var10] = var7;
                     var8 = var10;
                  }
               }
            }

            return var0.transpose(var5, var4, var8, var13);
         }

         Object var6 = var1.getRowMajor(var11 * 2);
         var3[var11] = var6;
         var10 = ((Number)var6).intValue();
         var5[var11] = var10;
         int var12 = ((Number)var1.getRowMajor(var11 * 2 + 1)).intValue() - var10;
         var4[var11] = var12;
         var10 = var11;
         if(var12 <= 0) {
            var9 = true;
            var10 = var11;
         }
      }
   }
}
