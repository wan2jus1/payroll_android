package gnu.kawa.functions;

import gnu.math.IntNum;
import gnu.text.ReportFormat;
import java.io.IOException;
import java.io.Writer;
import java.text.FieldPosition;

class LispPluralFormat extends ReportFormat {

   boolean backup;
   boolean y;


   public static LispPluralFormat getInstance(boolean var0, boolean var1) {
      LispPluralFormat var2 = new LispPluralFormat();
      var2.backup = var0;
      var2.y = var1;
      return var2;
   }

   public int format(Object[] var1, int var2, Writer var3, FieldPosition var4) throws IOException {
      int var5 = var2;
      if(this.backup) {
         var5 = var2 - 1;
      }

      int var6 = var5 + 1;
      boolean var8;
      if(var1[var5] != IntNum.one()) {
         var8 = true;
      } else {
         var8 = false;
      }

      if(this.y) {
         String var7;
         if(var8) {
            var7 = "ies";
         } else {
            var7 = "y";
         }

         print(var3, var7);
      } else if(var8) {
         var3.write(115);
         return var6;
      }

      return var6;
   }
}
