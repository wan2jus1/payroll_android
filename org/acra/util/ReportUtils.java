package org.acra.util;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.WindowManager;
import java.io.File;
import org.acra.ACRA;

public final class ReportUtils {

   public static String getApplicationFilePath(Context var0) {
      File var1 = var0.getFilesDir();
      if(var1 != null) {
         return var1.getAbsolutePath();
      } else {
         Log.w(ACRA.LOG_TAG, "Couldn\'t retrieve ApplicationFilePath for : " + var0.getPackageName());
         return "Couldn\'t retrieve ApplicationFilePath";
      }
   }

   public static long getAvailableInternalMemorySize() {
      StatFs var0 = new StatFs(Environment.getDataDirectory().getPath());
      long var1 = (long)var0.getBlockSize();
      return (long)var0.getAvailableBlocks() * var1;
   }

   public static String getDeviceId(Context var0) {
      try {
         String var1 = ((TelephonyManager)var0.getSystemService("phone")).getDeviceId();
         return var1;
      } catch (RuntimeException var2) {
         Log.w(ACRA.LOG_TAG, "Couldn\'t retrieve DeviceId for : " + var0.getPackageName(), var2);
         return null;
      }
   }

   public static String getDisplayDetails(Context var0) {
      try {
         Display var1 = ((WindowManager)var0.getSystemService("window")).getDefaultDisplay();
         DisplayMetrics var2 = new DisplayMetrics();
         var1.getMetrics(var2);
         StringBuilder var3 = new StringBuilder();
         var3.append("width=").append(var1.getWidth()).append('\n');
         var3.append("height=").append(var1.getHeight()).append('\n');
         var3.append("pixelFormat=").append(var1.getPixelFormat()).append('\n');
         var3.append("refreshRate=").append(var1.getRefreshRate()).append("fps").append('\n');
         var3.append("metrics.density=x").append(var2.density).append('\n');
         var3.append("metrics.scaledDensity=x").append(var2.scaledDensity).append('\n');
         var3.append("metrics.widthPixels=").append(var2.widthPixels).append('\n');
         var3.append("metrics.heightPixels=").append(var2.heightPixels).append('\n');
         var3.append("metrics.xdpi=").append(var2.xdpi).append('\n');
         var3.append("metrics.ydpi=").append(var2.ydpi);
         String var5 = var3.toString();
         return var5;
      } catch (RuntimeException var4) {
         Log.w(ACRA.LOG_TAG, "Couldn\'t retrieve DisplayDetails for : " + var0.getPackageName(), var4);
         return "Couldn\'t retrieve Display Details";
      }
   }

   public static long getTotalInternalMemorySize() {
      StatFs var0 = new StatFs(Environment.getDataDirectory().getPath());
      long var1 = (long)var0.getBlockSize();
      return (long)var0.getBlockCount() * var1;
   }

   public static String sparseArrayToString(SparseArray var0) {
      StringBuilder var1 = new StringBuilder();
      if(var0 == null) {
         return "null";
      } else {
         var1.append('{');

         for(int var2 = 0; var2 < var0.size(); ++var2) {
            var1.append(var0.keyAt(var2));
            var1.append(" => ");
            if(var0.valueAt(var2) == null) {
               var1.append("null");
            } else {
               var1.append(var0.valueAt(var2).toString());
            }

            if(var2 < var0.size() - 1) {
               var1.append(", ");
            }
         }

         var1.append('}');
         return var1.toString();
      }
   }
}
