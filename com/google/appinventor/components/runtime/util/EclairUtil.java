package com.google.appinventor.components.runtime.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.GeolocationPermissions.Callback;
import com.google.appinventor.components.runtime.WebViewer;

public class EclairUtil {

   public static void clearWebViewGeoLoc() {
      GeolocationPermissions.getInstance().clearAll();
   }

   public static String getInstallerPackageName(String var0, Activity var1) {
      return var1.getPackageManager().getInstallerPackageName(var0);
   }

   public static void overridePendingTransitions(Activity var0, int var1, int var2) {
      var0.overridePendingTransition(var1, var2);
   }

   public static void setupWebViewGeoLoc(final WebViewer var0, WebView var1, final Activity var2) {
      var1.getSettings().setGeolocationDatabasePath(var2.getFilesDir().getAbsolutePath());
      var1.getSettings().setDatabaseEnabled(true);
      var1.setWebChromeClient(new WebChromeClient() {
         public void onGeolocationPermissionsShowPrompt(final String var1, final Callback var2x) {
            if(!var0.PromptforPermission()) {
               var2x.invoke(var1, true, true);
            } else {
               AlertDialog var4 = (new Builder(var2)).create();
               var4.setTitle("Permission Request");
               String var3 = var1;
               if(var1.equals("file://")) {
                  var3 = "This Application";
               }

               var4.setMessage(var3 + " would like to access your location.");
               var4.setButton(-1, "Allow", new OnClickListener() {
                  public void onClick(DialogInterface var1x, int var2xx) {
                     var2x.invoke(var1, true, true);
                  }
               });
               var4.setButton(-2, "Refuse", new OnClickListener() {
                  public void onClick(DialogInterface var1x, int var2xx) {
                     var2x.invoke(var1, false, true);
                  }
               });
               var4.show();
            }
         }
      });
   }
}
