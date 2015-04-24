package gnu.text;

import gnu.text.WriterRef;
import java.io.Writer;

public class WriterManager implements Runnable {

   public static final WriterManager instance = new WriterManager();
   WriterRef first;


   public WriterRef register(Writer param1) {
      // $FF: Couldn't be decompiled
   }

   public boolean registerShutdownHook() {
      try {
         Runtime var1 = Runtime.getRuntime();
         var1.getClass().getDeclaredMethod("addShutdownHook", new Class[]{Thread.class}).invoke(var1, new Object[]{new Thread(this)});
         return true;
      } catch (Throwable var2) {
         return false;
      }
   }

   public void run() {
      // $FF: Couldn't be decompiled
   }

   public void unregister(Object param1) {
      // $FF: Couldn't be decompiled
   }
}
