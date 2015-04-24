package org.acra.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import org.acra.ACRA;

public final class PackageManagerWrapper {

   private final Context context;


   public PackageManagerWrapper(Context var1) {
      this.context = var1;
   }

   public PackageInfo getPackageInfo() {
      PackageManager var1 = this.context.getPackageManager();
      if(var1 == null) {
         return null;
      } else {
         try {
            PackageInfo var4 = var1.getPackageInfo(this.context.getPackageName(), 0);
            return var4;
         } catch (NameNotFoundException var2) {
            Log.v(ACRA.LOG_TAG, "Failed to find PackageInfo for current App : " + this.context.getPackageName());
            return null;
         } catch (RuntimeException var3) {
            return null;
         }
      }
   }

   public boolean hasPermission(String var1) {
      PackageManager var2 = this.context.getPackageManager();
      if(var2 != null) {
         int var3;
         try {
            var3 = var2.checkPermission(var1, this.context.getPackageName());
         } catch (RuntimeException var4) {
            return false;
         }

         if(var3 == 0) {
            return true;
         }
      }

      return false;
   }
}
