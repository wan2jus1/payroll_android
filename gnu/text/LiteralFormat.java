package gnu.text;

import gnu.text.ReportFormat;
import java.io.IOException;
import java.io.Writer;
import java.text.FieldPosition;
import java.text.ParsePosition;

public class LiteralFormat extends ReportFormat {

   char[] text;


   public LiteralFormat(String var1) {
      this.text = var1.toCharArray();
   }

   public LiteralFormat(StringBuffer var1) {
      int var2 = var1.length();
      this.text = new char[var2];
      var1.getChars(0, var2, this.text, 0);
   }

   public LiteralFormat(char[] var1) {
      this.text = var1;
   }

   public String content() {
      return new String(this.text);
   }

   public int format(Object[] var1, int var2, Writer var3, FieldPosition var4) throws IOException {
      var3.write(this.text);
      return var2;
   }

   public Object parseObject(String var1, ParsePosition var2) {
      throw new Error("LiteralFormat.parseObject - not implemented");
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer("LiteralFormat[\"");
      var1.append(this.text);
      var1.append("\"]");
      return var1.toString();
   }
}
