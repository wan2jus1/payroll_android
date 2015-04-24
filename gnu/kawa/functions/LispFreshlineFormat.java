package gnu.kawa.functions;

import gnu.mapping.OutPort;
import gnu.text.ReportFormat;
import java.io.IOException;
import java.io.Writer;
import java.text.FieldPosition;

class LispFreshlineFormat extends ReportFormat {

   int count;


   public LispFreshlineFormat(int var1) {
      this.count = var1;
   }

   public int format(Object[] var1, int var2, Writer var3, FieldPosition var4) throws IOException {
      int var6 = getParam(this.count, 1, var1, var2);
      int var5 = var2;
      if(this.count == -1610612736) {
         var5 = var2 + 1;
      }

      if(var6 > 0) {
         var2 = var6;
         if(var3 instanceof OutPort) {
            ((OutPort)var3).freshLine();
            var2 = var6 - 1;
         }

         while(true) {
            --var2;
            if(var2 < 0) {
               break;
            }

            var3.write(10);
         }
      }

      return var5;
   }
}
