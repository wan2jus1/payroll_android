package gnu.kawa.functions;

import gnu.mapping.OutPort;
import gnu.text.ReportFormat;
import java.io.IOException;
import java.io.Writer;
import java.text.FieldPosition;

class LispIndentFormat extends ReportFormat {

   int columns;
   boolean current;


   public static LispIndentFormat getInstance(int var0, boolean var1) {
      LispIndentFormat var2 = new LispIndentFormat();
      var2.columns = var0;
      var2.current = var1;
      return var2;
   }

   public int format(Object[] var1, int var2, Writer var3, FieldPosition var4) throws IOException {
      int var6 = getParam(this.columns, 0, var1, var2);
      int var5 = var2;
      if(this.columns == -1610612736) {
         var5 = var2 + 1;
      }

      if(var3 instanceof OutPort) {
         ((OutPort)var3).setIndentation(var6, this.current);
      }

      return var5;
   }
}
