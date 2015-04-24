package gnu.kawa.functions;

import gnu.mapping.OutPort;
import gnu.text.ReportFormat;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.FieldPosition;

class LispNewlineFormat extends ReportFormat {

   static final String line_separator = System.getProperty("line.separator", "\n");
   int count;
   int kind;


   public static LispNewlineFormat getInstance(int var0, int var1) {
      LispNewlineFormat var2 = new LispNewlineFormat();
      var2.count = var0;
      var2.kind = var1;
      return var2;
   }

   public static void printNewline(int var0, Writer var1) throws IOException {
      if(var1 instanceof OutPort && var0 != 76) {
         ((OutPort)var1).writeBreak(var0);
      } else if(var1 instanceof PrintWriter) {
         ((PrintWriter)var1).println();
      } else {
         var1.write(line_separator);
      }
   }

   public int format(Object[] var1, int var2, Writer var3, FieldPosition var4) throws IOException {
      int var7 = getParam(this.count, 1, var1, var2);
      int var6 = var7;
      int var5 = var2;
      if(this.count == -1610612736) {
         var5 = var2 + 1;
         var6 = var7;
      }

      while(true) {
         --var6;
         if(var6 < 0) {
            return var5;
         }

         printNewline(this.kind, var3);
      }
   }
}
