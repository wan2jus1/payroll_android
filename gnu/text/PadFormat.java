package gnu.text;

import gnu.text.ReportFormat;
import java.io.IOException;
import java.io.Writer;
import java.text.FieldPosition;
import java.text.Format;
import java.text.MessageFormat;

public class PadFormat extends ReportFormat {

   Format fmt;
   int minWidth;
   char padChar;
   int where;


   public PadFormat(Format var1, int var2) {
      this(var1, var2, ' ', 100);
   }

   public PadFormat(Format var1, int var2, char var3, int var4) {
      this.fmt = var1;
      this.minWidth = var2;
      this.padChar = var3;
      this.where = var4;
   }

   public static int format(Format var0, Object[] var1, int var2, Writer var3, char var4, int var5, int var6, int var7, int var8, FieldPosition var9) throws IOException {
      StringBuffer var10 = new StringBuffer(200);
      if(var0 instanceof ReportFormat) {
         var2 = ((ReportFormat)var0).format((Object[])var1, var2, (StringBuffer)var10, var9);
      } else if(var0 instanceof MessageFormat) {
         var0.format(var1, var10, var9);
         var2 = var1.length;
      } else {
         var0.format(var1[var2], var10, var9);
         ++var2;
      }

      int var11 = var10.length();
      var7 = padNeeded(var11, var5, var6, var7);
      var6 = 0;
      String var13 = var10.toString();
      if(var7 > 0) {
         String var12 = var13;
         var5 = var8;
         if(var8 == -1) {
            var12 = var13;
            if(var11 > 0) {
               char var14 = var13.charAt(0);
               if(var14 == 45 || var14 == 43) {
                  var6 = 0 + 1;
                  var3.write(var14);
               }

               var5 = var6;
               if(var11 - var6 > 2) {
                  var5 = var6;
                  if(var13.charAt(var6) == 48) {
                     label48: {
                        var3.write(48);
                        ++var6;
                        char var15 = var13.charAt(var6);
                        if(var15 != 120) {
                           var5 = var6;
                           if(var15 != 88) {
                              break label48;
                           }
                        }

                        var5 = var6 + 1;
                        var3.write(var15);
                     }
                  }
               }

               var12 = var13;
               if(var5 > 0) {
                  var12 = var13.substring(var5);
               }
            }

            var5 = 0;
         }

         var6 = var7 * var5 / 100;
         var5 = var7 - var6;

         while(true) {
            --var5;
            if(var5 < 0) {
               var3.write(var12);
               var5 = var6;

               while(true) {
                  --var5;
                  if(var5 < 0) {
                     return var2;
                  }

                  var3.write(var4);
               }
            }

            var3.write(var4);
         }
      } else {
         var3.write(var13);
         return var2;
      }
   }

   public static int padNeeded(int var0, int var1, int var2, int var3) {
      int var5 = var0 + var3;
      int var4 = var5;
      var3 = var2;
      if(var2 <= 1) {
         var3 = var1 - var5;
         var4 = var5;
      }

      while(var4 < var1) {
         var4 += var3;
      }

      return var4 - var0;
   }

   public int format(Object[] var1, int var2, Writer var3, FieldPosition var4) throws IOException {
      return format(this.fmt, var1, var2, var3, this.padChar, this.minWidth, 1, 0, this.where, var4);
   }
}
