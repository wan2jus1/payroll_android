package gnu.kawa.functions;

import gnu.text.PadFormat;
import gnu.text.ReportFormat;
import java.io.IOException;
import java.io.Writer;
import java.text.FieldPosition;

class LispObjectFormat extends ReportFormat {

   ReportFormat base;
   int colInc;
   int minPad;
   int minWidth;
   int padChar;
   int where;


   public LispObjectFormat(ReportFormat var1, int var2, int var3, int var4, int var5, int var6) {
      this.base = var1;
      this.minWidth = var2;
      this.colInc = var3;
      this.minPad = var4;
      this.padChar = var5;
      this.where = var6;
   }

   public int format(Object[] var1, int var2, Writer var3, FieldPosition var4) throws IOException {
      int var7 = getParam(this.minWidth, 0, var1, var2);
      int var6 = var2;
      if(this.minWidth == -1610612736) {
         var6 = var2 + 1;
      }

      int var8 = getParam(this.colInc, 1, var1, var6);
      var2 = var6;
      if(this.colInc == -1610612736) {
         var2 = var6 + 1;
      }

      int var9 = getParam(this.minPad, 0, var1, var2);
      var6 = var2;
      if(this.minPad == -1610612736) {
         var6 = var2 + 1;
      }

      char var5 = getParam(this.padChar, ' ', var1, var6);
      var2 = var6;
      if(this.padChar == -1610612736) {
         var2 = var6 + 1;
      }

      return PadFormat.format(this.base, var1, var2, var3, var5, var7, var8, var9, this.where, var4);
   }
}
