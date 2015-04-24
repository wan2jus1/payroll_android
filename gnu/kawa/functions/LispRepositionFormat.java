package gnu.kawa.functions;

import gnu.text.ReportFormat;
import java.io.IOException;
import java.io.Writer;
import java.text.FieldPosition;

class LispRepositionFormat extends ReportFormat {

   boolean absolute;
   boolean backwards;
   int count;


   public LispRepositionFormat(int var1, boolean var2, boolean var3) {
      this.count = var1;
      this.backwards = var2;
      this.absolute = var3;
   }

   public int format(Object[] var1, int var2, Writer var3, FieldPosition var4) throws IOException {
      int var6 = this.count;
      byte var5;
      if(this.absolute) {
         var5 = 0;
      } else {
         var5 = 1;
      }

      int var7 = getParam(var6, var5, var1, var2);
      var6 = var7;
      if(!this.absolute) {
         var6 = var7;
         if(this.backwards) {
            var6 = -var7;
         }

         var6 += var2;
      }

      return var6 < 0?0:(var6 > var1.length?var1.length:var6);
   }
}
