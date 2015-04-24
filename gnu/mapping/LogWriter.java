package gnu.mapping;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FilterWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

public class LogWriter extends FilterWriter {

   private Writer log;


   public LogWriter(Writer var1) {
      super(var1);
   }

   public void close() throws IOException {
      if(this.log != null) {
         this.log.close();
      }

      super.close();
   }

   public void closeLogFile() throws IOException {
      if(this.log != null) {
         this.log.close();
      }

      this.log = null;
   }

   public void echo(char[] var1, int var2, int var3) throws IOException {
      if(this.log != null) {
         this.log.write(var1, var2, var3);
      }

   }

   public void flush() throws IOException {
      if(this.log != null) {
         this.log.flush();
      }

      super.flush();
   }

   public final Writer getLogFile() {
      return this.log;
   }

   public void setLogFile(Writer var1) {
      this.log = var1;
   }

   public void setLogFile(String var1) throws IOException {
      this.log = new PrintWriter(new BufferedWriter(new FileWriter(var1)));
   }

   public void write(int var1) throws IOException {
      if(this.log != null) {
         this.log.write(var1);
      }

      super.write(var1);
   }

   public void write(String var1, int var2, int var3) throws IOException {
      if(this.log != null) {
         this.log.write(var1, var2, var3);
      }

      super.write(var1, var2, var3);
   }

   public void write(char[] var1, int var2, int var3) throws IOException {
      if(this.log != null) {
         this.log.write(var1, var2, var3);
      }

      super.write(var1, var2, var3);
   }
}
