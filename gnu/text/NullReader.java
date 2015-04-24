package gnu.text;

import java.io.Reader;

public class NullReader extends Reader {

   public static final NullReader nullReader = new NullReader();


   public void close() {
   }

   public int read(char[] var1, int var2, int var3) {
      return -1;
   }

   public boolean ready() {
      return true;
   }
}
