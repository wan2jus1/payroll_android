package com.google.appinventor.components.runtime;

import android.app.Application;
import android.util.Log;
import com.google.appinventor.common.version.GitBuildId;
import org.acra.ACRA;
import org.acra.ACRAConfiguration;
import org.acra.annotation.ReportsCrashes;

@ReportsCrashes(
   formKey = ""
)
public class ReplApplication extends Application {

   private static ReplApplication thisInstance;
   private boolean active = false;


   public static void reportError(Throwable var0) {
      if(thisInstance != null && thisInstance.active) {
         ACRA.getErrorReporter().handleException(var0);
      }

   }

   public void onCreate() {
      super.onCreate();
      thisInstance = this;
      String var1 = GitBuildId.getAcraUri();
      if(var1.equals("")) {
         Log.i("ReplApplication", "ACRA Not Active");
      } else {
         Log.i("ReplApplication", "ACRA Active, URI = " + var1);
         ACRAConfiguration var2 = ACRA.getNewDefaultConfig(this);
         var2.setFormUri(var1);
         var2.setDisableSSLCertValidation(true);
         ACRA.setConfig(var2);
         ACRA.init(this);
         this.active = true;
      }
   }
}
