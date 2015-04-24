package gnu.text;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;

public class RomanIntegerFormat extends NumberFormat {

   static final String codes = "IVXLCDM";
   private static RomanIntegerFormat newRoman;
   private static RomanIntegerFormat oldRoman;
   public boolean oldStyle;


   public RomanIntegerFormat() {
   }

   public RomanIntegerFormat(boolean var1) {
      this.oldStyle = var1;
   }

   public static String format(int var0) {
      return format(var0, false);
   }

   public static String format(int var0, boolean var1) {
      if(var0 > 0 && var0 < 4999) {
         StringBuffer var2 = new StringBuffer(20);
         int var3 = 3;

         int var6;
         for(int var4 = 1000; var3 >= 0; var0 = var6) {
            int var5 = var0 / var4;
            var6 = var0 - var5 * var4;
            if(var5 != 0) {
               if(!var1 && (var5 == 4 || var5 == 9)) {
                  var2.append("IVXLCDM".charAt(var3 * 2));
                  var2.append("IVXLCDM".charAt(var3 * 2 + (var5 + 1) / 5));
               } else {
                  var0 = var5;
                  if(var5 >= 5) {
                     var2.append("IVXLCDM".charAt(var3 * 2 + 1));
                     var0 = var5 - 5;
                  }

                  while(true) {
                     --var0;
                     if(var0 < 0) {
                        break;
                     }

                     var2.append("IVXLCDM".charAt(var3 * 2));
                  }
               }
            }

            var4 /= 10;
            --var3;
         }

         return var2.toString();
      } else {
         return Integer.toString(var0);
      }
   }

   public static RomanIntegerFormat getInstance(boolean var0) {
      if(var0) {
         if(oldRoman == null) {
            oldRoman = new RomanIntegerFormat(true);
         }

         return oldRoman;
      } else {
         if(newRoman == null) {
            newRoman = new RomanIntegerFormat(false);
         }

         return newRoman;
      }
   }

   public StringBuffer format(double var1, StringBuffer var3, FieldPosition var4) {
      long var5 = (long)var1;
      if((double)var5 == var1) {
         return this.format(var5, var3, var4);
      } else {
         var3.append(Double.toString(var1));
         return var3;
      }
   }

   public StringBuffer format(long var1, StringBuffer var3, FieldPosition var4) {
      String var5;
      label30: {
         if(var1 > 0L) {
            short var7;
            if(this.oldStyle) {
               var7 = 4999;
            } else {
               var7 = 3999;
            }

            if(var1 < (long)var7) {
               var5 = format((int)var1, this.oldStyle);
               break label30;
            }
         }

         var5 = Long.toString(var1);
      }

      if(var4 != null) {
         var1 = 1L;
         int var8 = var5.length();
         int var9 = var8;

         while(true) {
            --var9;
            if(var9 <= 0) {
               StringBuffer var6 = new StringBuffer(var8);
               (new DecimalFormat("0")).format(var1, var6, var4);
               break;
            }

            var1 = 10L * var1 + 9L;
         }
      }

      var3.append(var5);
      return var3;
   }

   public Number parse(String var1, ParsePosition var2) {
      throw new Error("RomanIntegerFormat.parseObject - not implemented");
   }
}
