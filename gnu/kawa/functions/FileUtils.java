package gnu.kawa.functions;

import java.io.File;
import java.io.IOException;

public class FileUtils {

   public static File createTempFile(String var0) throws IOException {
      String var1 = var0;
      if(var0 == null) {
         var1 = "kawa~d.tmp";
      }

      int var4 = var1.indexOf(126);
      File var3 = null;
      if(var4 < 0) {
         var0 = var1;
         var1 = ".tmp";
      } else {
         var0 = var1.substring(0, var4);
         var1 = var1.substring(var4 + 2);
      }

      var4 = var0.indexOf(File.separatorChar);
      String var2 = var0;
      if(var4 >= 0) {
         var3 = new File(var0.substring(0, var4));
         var2 = var0.substring(var4 + 1);
      }

      return File.createTempFile(var2, var1, var3);
   }
}
