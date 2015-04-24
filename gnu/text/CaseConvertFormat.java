package gnu.text;

import gnu.text.ReportFormat;
import java.io.IOException;
import java.io.Writer;
import java.text.FieldPosition;
import java.text.Format;

public class CaseConvertFormat extends ReportFormat {

   Format baseFormat;
   char code;


   public CaseConvertFormat(Format var1, char var2) {
      this.baseFormat = var1;
      this.code = var2;
   }

   public int format(Object[] var1, int var2, Writer var3, FieldPosition var4) throws IOException {
      StringBuffer var7 = new StringBuffer(100);
      int var8 = format(this.baseFormat, var1, var2, var7, var4);
      int var9 = var7.length();
      char var5 = 32;

      for(var2 = 0; var2 < var9; var5 = var5) {
         char var6 = var7.charAt(var2);
         if(this.code == 85) {
            var5 = Character.toUpperCase(var6);
         } else if((this.code != 84 || var2 != 0) && (this.code != 67 || Character.isLetterOrDigit(var5))) {
            var5 = Character.toLowerCase(var6);
         } else {
            var5 = Character.toTitleCase(var6);
         }

         var3.write(var5);
         ++var2;
      }

      return var8;
   }

   public Format getBaseFormat() {
      return this.baseFormat;
   }

   public void setBaseFormat(Format var1) {
      this.baseFormat = var1;
   }
}
