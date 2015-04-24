package org.acra.util;

import android.content.Context;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;
import org.acra.ACRA;

public class Installation {

   private static final String INSTALLATION = "ACRA-INSTALLATION";
   private static String sID;


   public static String id(Context var0) {
      synchronized(Installation.class){}
      boolean var5 = false;

      String var9;
      label78: {
         label77: {
            try {
               label75: {
                  var5 = true;
                  if(sID == null) {
                     File var1 = new File(var0.getFilesDir(), "ACRA-INSTALLATION");

                     try {
                        if(!var1.exists()) {
                           writeInstallationFile(var1);
                        }

                        sID = readInstallationFile(var1);
                     } catch (IOException var6) {
                        Log.w(ACRA.LOG_TAG, "Couldn\'t retrieve InstallationId for " + var0.getPackageName(), var6);
                        var5 = false;
                        break label77;
                     } catch (RuntimeException var7) {
                        Log.w(ACRA.LOG_TAG, "Couldn\'t retrieve InstallationId for " + var0.getPackageName(), var7);
                        var5 = false;
                        break label75;
                     }
                  }

                  var9 = sID;
                  var5 = false;
                  break label78;
               }
            } finally {
               if(var5) {
                  ;
               }
            }

            var9 = "Couldn\'t retrieve InstallationId";
            break label78;
         }

         var9 = "Couldn\'t retrieve InstallationId";
      }

      return var9;
   }

   private static String readInstallationFile(File var0) throws IOException {
      RandomAccessFile var4 = new RandomAccessFile(var0, "r");
      byte[] var1 = new byte[(int)var4.length()];

      try {
         var4.readFully(var1);
      } finally {
         var4.close();
      }

      return new String(var1);
   }

   private static void writeInstallationFile(File var0) throws IOException {
      FileOutputStream var4 = new FileOutputStream(var0);

      try {
         var4.write(UUID.randomUUID().toString().getBytes());
      } finally {
         var4.close();
      }

   }
}
