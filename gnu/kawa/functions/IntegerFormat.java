package gnu.kawa.functions;

import gnu.math.RealNum;
import gnu.text.EnglishIntegerFormat;
import gnu.text.RomanIntegerFormat;

public class IntegerFormat extends gnu.text.IntegerFormat {

   private static IntegerFormat plainDecimalFormat;


   public static IntegerFormat getInstance() {
      if(plainDecimalFormat == null) {
         plainDecimalFormat = new IntegerFormat();
      }

      return plainDecimalFormat;
   }

   public static java.text.Format getInstance(int var0, int var1, int var2, int var3, int var4, int var5) {
      boolean var8 = true;
      int var7 = var0;
      if(var0 == -1073741824) {
         if(var2 == -1073741824 && var2 == -1073741824 && var3 == -1073741824 && var4 == -1073741824) {
            if((var5 & 1) == 0) {
               var8 = false;
            }

            if((var5 & 2) != 0) {
               return RomanIntegerFormat.getInstance(var8);
            }

            return EnglishIntegerFormat.getInstance(var8);
         }

         var7 = 10;
      }

      var0 = var1;
      if(var1 == -1073741824) {
         var0 = 1;
      }

      var1 = var2;
      if(var2 == -1073741824) {
         var1 = 32;
      }

      var2 = var3;
      if(var3 == -1073741824) {
         var2 = 44;
      }

      var3 = var4;
      if(var4 == -1073741824) {
         var3 = 3;
      }

      if(var7 == 10 && var0 == 1 && var1 == 32 && var2 == 44 && var3 == 3 && var5 == 0) {
         return getInstance();
      } else {
         IntegerFormat var6 = new IntegerFormat();
         var6.base = var7;
         var6.minWidth = var0;
         var6.padChar = var1;
         var6.commaChar = var2;
         var6.commaInterval = var3;
         var6.flags = var5;
         return var6;
      }
   }

   public String convertToIntegerString(Object var1, int var2) {
      return var1 instanceof RealNum?((RealNum)var1).toExactInt(4).toString(var2):super.convertToIntegerString(var1, var2);
   }
}
