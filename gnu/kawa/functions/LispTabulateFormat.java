package gnu.kawa.functions;

import gnu.mapping.OutPort;
import gnu.text.ReportFormat;
import java.io.IOException;
import java.io.Writer;
import java.text.FieldPosition;

class LispTabulateFormat extends ReportFormat {

   int colinc;
   int colnum;
   int padChar;
   boolean relative;


   public LispTabulateFormat(int var1, int var2, int var3, boolean var4) {
      this.colnum = var1;
      this.colinc = var2;
      this.relative = var4;
      this.padChar = var3;
   }

   public int format(Object[] var1, int var2, Writer var3, FieldPosition var4) throws IOException {
      int var6 = getParam(this.colnum, 1, var1, var2);
      int var5 = var2;
      if(this.colnum == -1610612736) {
         var5 = var2 + 1;
      }

      int var8 = getParam(this.colinc, 1, var1, var5);
      var2 = var5;
      if(this.colinc == -1610612736) {
         var2 = var5 + 1;
      }

      char var7 = getParam(this.padChar, ' ', var1, var2);
      var5 = var2;
      if(this.padChar == -1610612736) {
         var5 = var2 + 1;
      }

      var2 = -1;
      if(var3 instanceof OutPort) {
         var2 = ((OutPort)var3).getColumnNumber();
      }

      if(var2 >= 0) {
         if(!this.relative) {
            if(var2 < var6) {
               var2 = var6 - var2;
            } else if(var8 <= 0) {
               var2 = 0;
            } else {
               var2 = var8 - (var2 - var6) % var8;
            }
         } else {
            var2 = var6 + var8 - (var2 + var6) % var8;
         }
      } else if(this.relative) {
         var2 = var6;
      } else {
         var2 = 2;
      }

      while(true) {
         --var2;
         if(var2 < 0) {
            return var5;
         }

         var3.write(var7);
      }
   }
}
