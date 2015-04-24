package gnu.kawa.functions;

import gnu.text.Char;
import gnu.text.ReportFormat;
import java.io.IOException;
import java.io.Writer;
import java.text.FieldPosition;

class LispCharacterFormat extends ReportFormat {

   int charVal;
   int count;
   boolean seenAt;
   boolean seenColon;


   public static LispCharacterFormat getInstance(int var0, int var1, boolean var2, boolean var3) {
      LispCharacterFormat var4 = new LispCharacterFormat();
      var4.count = var1;
      var4.charVal = var0;
      var4.seenAt = var2;
      var4.seenColon = var3;
      return var4;
   }

   public static void printChar(int var0, boolean var1, boolean var2, Writer var3) throws IOException {
      if(var1) {
         print(var3, Char.toScmReadableString(var0));
      } else if(var2) {
         if(var0 < 32) {
            var3.write(94);
            var3.write(var0 + 64);
         } else if(var0 >= 127) {
            print(var3, "#\\x");
            print(var3, Integer.toString(var0, 16));
         } else {
            var3.write(var0);
         }
      } else {
         var3.write(var0);
      }
   }

   public int format(Object[] var1, int var2, Writer var3, FieldPosition var4) throws IOException {
      int var7 = getParam(this.count, 1, var1, var2);
      int var5 = var2;
      if(this.count == -1610612736) {
         var5 = var2 + 1;
      }

      char var8 = getParam(this.charVal, '?', var1, var5);
      int var6 = var7;
      var2 = var5;
      if(this.charVal == -1610612736) {
         var2 = var5 + 1;
         var6 = var7;
      }

      while(true) {
         --var6;
         if(var6 < 0) {
            return var2;
         }

         printChar(var8, this.seenAt, this.seenColon, var3);
      }
   }
}
