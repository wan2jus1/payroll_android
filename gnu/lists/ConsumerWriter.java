package gnu.lists;

import gnu.lists.Consumer;
import java.io.Writer;

public class ConsumerWriter extends Writer {

   protected Consumer out;


   public ConsumerWriter(Consumer var1) {
      this.out = var1;
   }

   public void close() {
      this.flush();
   }

   public void finalize() {
      this.close();
   }

   public void flush() {
   }

   public void write(char[] var1, int var2, int var3) {
      this.out.write((char[])var1, var2, var3);
   }
}
