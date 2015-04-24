package gnu.text;

import gnu.text.SourceError;
import gnu.text.SourceMessages;
import java.io.PrintWriter;

public class SyntaxException extends Exception {

   String header;
   public int maxToPrint = 10;
   SourceMessages messages;


   public SyntaxException(SourceMessages var1) {
      this.messages = var1;
   }

   public SyntaxException(String var1, SourceMessages var2) {
      this.header = var1;
      this.messages = var2;
   }

   public void clear() {
      this.messages.clear();
   }

   public final String getHeader() {
      return this.header;
   }

   public String getMessage() {
      StringBuffer var2 = new StringBuffer();
      if(this.header != null) {
         var2.append(this.header);
      }

      int var3 = this.maxToPrint;

      for(SourceError var1 = this.messages.firstError; var1 != null; var1 = var1.next) {
         --var3;
         if(var3 < 0) {
            break;
         }

         var2.append('\n');
         var2.append(var1);
      }

      return var2.toString();
   }

   public SourceMessages getMessages() {
      return this.messages;
   }

   public void printAll(PrintWriter var1, int var2) {
      if(this.header != null) {
         var1.println(this.header);
      }

      this.messages.printAll((PrintWriter)var1, var2);
   }

   public final void setHeader(String var1) {
      this.header = var1;
   }
}
