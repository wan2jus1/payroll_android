package gnu.text;

import gnu.text.ReportFormat;
import java.io.IOException;
import java.io.Writer;
import java.text.FieldPosition;

public class FlushFormat extends ReportFormat {

   private static FlushFormat flush;


   public static FlushFormat getInstance() {
      if(flush == null) {
         flush = new FlushFormat();
      }

      return flush;
   }

   public int format(Object[] var1, int var2, Writer var3, FieldPosition var4) throws IOException {
      var3.flush();
      return var2;
   }
}
