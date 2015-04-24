package gnu.math;

import gnu.math.BaseUnit;
import gnu.math.Unit;

public class Dimensions {

   public static Dimensions Empty = new Dimensions();
   private static Dimensions[] hashTable = new Dimensions[100];
   BaseUnit[] bases;
   private Dimensions chain;
   int hash_code;
   short[] powers;


   private Dimensions() {
      this.bases = new BaseUnit[1];
      this.bases[0] = Unit.Empty;
      this.enterHash(0);
   }

   Dimensions(BaseUnit var1) {
      this.bases = new BaseUnit[2];
      this.powers = new short[1];
      this.bases[0] = var1;
      this.bases[1] = Unit.Empty;
      this.powers[0] = 1;
      this.enterHash(var1.index);
   }

   private Dimensions(Dimensions var1, int var2, Dimensions var3, int var4, int var5) {
      this.hash_code = var5;

      int var8;
      for(var8 = 0; var1.bases[var8] != Unit.Empty; ++var8) {
         ;
      }

      int var9;
      for(var9 = 0; var3.bases[var9] != Unit.Empty; ++var9) {
         ;
      }

      var8 = var8 + var9 + 1;
      this.bases = new BaseUnit[var8];
      this.powers = new short[var8];
      int var11 = 0;
      var8 = 0;
      var9 = 0;

      while(true) {
         while(true) {
            BaseUnit var6 = var1.bases[var9];
            BaseUnit var7 = var3.bases[var8];
            int var10;
            if(var6.index < var7.index) {
               var10 = var1.powers[var9] * var2;
               ++var9;
            } else if(var7.index < var6.index) {
               var6 = var7;
               var10 = var3.powers[var8] * var4;
               ++var8;
            } else {
               if(var7 == Unit.Empty) {
                  this.bases[var11] = Unit.Empty;
                  this.enterHash(var5);
                  return;
               }

               int var14 = var1.powers[var9] * var2 + var3.powers[var8] * var4;
               int var12 = var9 + 1;
               int var13 = var8 + 1;
               var9 = var12;
               var8 = var13;
               var10 = var14;
               if(var14 == 0) {
                  var9 = var12;
                  var8 = var13;
                  continue;
               }
            }

            if((short)var10 != var10) {
               throw new ArithmeticException("overflow in dimensions");
            }

            this.bases[var11] = var6;
            this.powers[var11] = (short)var10;
            ++var11;
         }
      }
   }

   private void enterHash(int var1) {
      this.hash_code = var1;
      var1 = (Integer.MAX_VALUE & var1) % hashTable.length;
      this.chain = hashTable[var1];
      hashTable[var1] = this;
   }

   private boolean matchesProduct(Dimensions var1, int var2, Dimensions var3, int var4) {
      int var8 = 0;
      int var7 = 0;
      int var10 = 0;

      while(true) {
         BaseUnit var5 = var1.bases[var8];
         BaseUnit var6 = var3.bases[var7];
         int var9;
         if(var5.index < var6.index) {
            var9 = var1.powers[var8] * var2;
            ++var8;
         } else if(var6.index < var5.index) {
            var5 = var6;
            var9 = var3.powers[var7] * var4;
            ++var7;
         } else {
            if(var6 == Unit.Empty) {
               if(this.bases[var10] == var6) {
                  return true;
               }
               break;
            }

            int var13 = var1.powers[var8] * var2 + var3.powers[var7] * var4;
            int var11 = var8 + 1;
            int var12 = var7 + 1;
            var8 = var11;
            var7 = var12;
            var9 = var13;
            if(var13 == 0) {
               var8 = var11;
               var7 = var12;
               continue;
            }
         }

         if(this.bases[var10] != var5 || this.powers[var10] != var9) {
            break;
         }

         ++var10;
      }

      return false;
   }

   public static Dimensions product(Dimensions var0, int var1, Dimensions var2, int var3) {
      int var5 = var0.hashCode() * var1 + var2.hashCode() * var3;
      int var6 = hashTable.length;

      for(Dimensions var4 = hashTable[(Integer.MAX_VALUE & var5) % var6]; var4 != null; var4 = var4.chain) {
         if(var4.hash_code == var5 && var4.matchesProduct(var0, var1, var2, var3)) {
            return var4;
         }
      }

      return new Dimensions(var0, var1, var2, var3, var5);
   }

   public int getPower(BaseUnit var1) {
      for(int var2 = 0; this.bases[var2].index <= var1.index; ++var2) {
         if(this.bases[var2] == var1) {
            return this.powers[var2];
         }
      }

      return 0;
   }

   public final int hashCode() {
      return this.hash_code;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();

      for(int var2 = 0; this.bases[var2] != Unit.Empty; ++var2) {
         if(var2 > 0) {
            var1.append('*');
         }

         var1.append(this.bases[var2]);
         short var3 = this.powers[var2];
         if(var3 != 1) {
            var1.append('^');
            var1.append(var3);
         }
      }

      return var1.toString();
   }
}
