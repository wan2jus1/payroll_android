package org.acra;

import android.content.Context;
import android.util.Log;
import java.io.File;
import java.io.FilenameFilter;
import org.acra.ACRA;

final class CrashReportFinder {

   private final Context context;


   public CrashReportFinder(Context var1) {
      this.context = var1;
   }

   public String[] getCrashReportFiles() {
      String[] var1;
      if(this.context == null) {
         Log.e(ACRA.LOG_TAG, "Trying to get ACRA reports but ACRA is not initialized.");
         var1 = new String[0];
      } else {
         File var3 = this.context.getFilesDir();
         if(var3 == null) {
            Log.w(ACRA.LOG_TAG, "Application files directory does not exist! The application may not be installed correctly. Please try reinstalling.");
            return new String[0];
         }

         Log.d(ACRA.LOG_TAG, "Looking for error files in " + var3.getAbsolutePath());
         String[] var2 = var3.list(new FilenameFilter() {
            public boolean accept(File var1, String var2) {
               return var2.endsWith(".stacktrace");
            }
         });
         var1 = var2;
         if(var2 == null) {
            return new String[0];
         }
      }

      return var1;
   }
}
