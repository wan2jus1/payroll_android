package gnu.bytecode;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ZipArchive {

   public static long copy(InputStream var0, OutputStream var1, byte[] var2) throws IOException {
      long var4 = 0L;

      while(true) {
         int var3 = var0.read(var2);
         if(var3 <= 0) {
            return var4;
         }

         var1.write(var2, 0, var3);
         var4 += (long)var3;
      }
   }

   public static void copy(InputStream var0, String var1, byte[] var2) throws IOException {
      File var3 = new File(var1);
      String var4 = var3.getParent();
      if(var4 != null) {
         File var6 = new File(var4);
         if(!var6.exists()) {
            System.err.println("mkdirs:" + var6.mkdirs());
         }
      }

      if(var1.charAt(var1.length() - 1) != 47) {
         BufferedOutputStream var5 = new BufferedOutputStream(new FileOutputStream(var3));
         copy(var0, (OutputStream)var5, var2);
         var5.close();
      }

   }

   public static void main(String[] param0) throws IOException {
      // $FF: Couldn't be decompiled
   }

   private static void usage() {
      System.err.println("zipfile [ptxq] archive [file ...]");
      System.exit(-1);
   }
}
