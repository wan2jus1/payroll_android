package gnu.kawa.functions;

import gnu.text.ReportFormat;
import java.io.IOException;
import java.io.Writer;
import java.text.FieldPosition;

class LispPrettyFormat extends ReportFormat {

   java.text.Format body;
   boolean perLine;
   String prefix;
   boolean seenAt;
   java.text.Format[] segments;
   String suffix;


   public int format(Object[] param1, int param2, Writer param3, FieldPosition param4) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append("LispPrettyFormat[");
      var1.append("prefix: \"");
      var1.append(this.prefix);
      var1.append("\", suffix: \"");
      var1.append(this.suffix);
      var1.append("\", body: ");
      var1.append(this.body);
      var1.append("]");
      return var1.toString();
   }
}
