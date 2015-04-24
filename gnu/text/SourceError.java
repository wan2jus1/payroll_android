package gnu.text;

import gnu.text.LineBufferedReader;
import gnu.text.SourceLocator;
import java.io.PrintStream;
import java.io.PrintWriter;

public class SourceError implements SourceLocator {

   public String code;
   public int column;
   public Throwable fakeException;
   public String filename;
   public int line;
   public String message;
   public SourceError next;
   public char severity;


   public SourceError(char var1, SourceLocator var2, String var3) {
      this(var1, var2.getFileName(), var2.getLineNumber(), var2.getColumnNumber(), var3);
   }

   public SourceError(char var1, String var2, int var3, int var4, String var5) {
      this.severity = var1;
      this.filename = var2;
      this.line = var3;
      this.column = var4;
      this.message = var5;
   }

   public SourceError(LineBufferedReader var1, char var2, String var3) {
      this(var2, var1.getName(), var1.getLineNumber() + 1, var1.getColumnNumber(), var3);
      if(this.column >= 0) {
         ++this.column;
      }

   }

   public int getColumnNumber() {
      return this.column == 0?-1:this.column;
   }

   public String getFileName() {
      return this.filename;
   }

   public int getLineNumber() {
      return this.line == 0?-1:this.line;
   }

   public String getPublicId() {
      return null;
   }

   public String getSystemId() {
      return this.filename;
   }

   public boolean isStableSourceLocation() {
      return true;
   }

   public void print(PrintWriter var1) {
      var1.print(this);
   }

   public void println(PrintStream var1) {
      String var2 = this.toString();

      while(true) {
         int var3 = var2.indexOf(10);
         if(var3 < 0) {
            var1.println(var2);
            return;
         }

         var1.println(var2.substring(0, var3));
         var2 = var2.substring(var3 + 1);
      }
   }

   public void println(PrintWriter var1) {
      String var2 = this.toString();

      while(true) {
         int var3 = var2.indexOf(10);
         if(var3 < 0) {
            var1.println(var2);
            return;
         }

         var1.println(var2.substring(0, var3));
         var2 = var2.substring(var3 + 1);
      }
   }

   public String toString() {
      StringBuffer var2 = new StringBuffer();
      String var1;
      if(this.filename == null) {
         var1 = "<unknown>";
      } else {
         var1 = this.filename;
      }

      var2.append(var1);
      if(this.line > 0 || this.column > 0) {
         var2.append(':');
         var2.append(this.line);
         if(this.column > 0) {
            var2.append(':');
            var2.append(this.column);
         }
      }

      var2.append(": ");
      if(this.severity == 119) {
         var2.append("warning - ");
      }

      var2.append(this.message);
      if(this.code != null) {
         var2.append(" [");
         var2.append(this.code);
         var2.append("]");
      }

      if(this.fakeException != null) {
         StackTraceElement[] var4 = this.fakeException.getStackTrace();

         for(int var3 = 0; var3 < var4.length; ++var3) {
            var2.append("\n");
            var2.append("    ");
            var2.append(var4[var3].toString());
         }
      }

      return var2.toString();
   }
}
