package com.google.appinventor.components.runtime;

import android.app.Activity;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.ReplForm;
import com.google.appinventor.components.runtime.util.AppInvHTTPD;
import com.google.appinventor.components.runtime.util.PackageInstaller;
import java.security.MessageDigest;
import java.util.Formatter;

@DesignerComponent(
   category = ComponentCategory.INTERNAL,
   description = "Component that returns information about the phone.",
   iconName = "images/phoneip.png",
   nonVisible = true,
   version = 1
)
@SimpleObject
public class PhoneStatus extends AndroidNonvisibleComponent implements Component {

   private static final String LOG_TAG = "PhoneStatus";
   private static Activity activity;
   private static PhoneStatus mainInstance = null;
   private final Form form;


   public PhoneStatus(ComponentContainer var1) {
      super(var1.$form());
      this.form = var1.$form();
      activity = var1.$context();
      if(mainInstance == null) {
         mainInstance = this;
      }

   }

   @SimpleFunction(
      description = "Returns the IP address of the phone in the form of a String"
   )
   public static String GetWifiIpAddress() {
      int var0 = ((WifiManager)activity.getSystemService("wifi")).getDhcpInfo().ipAddress;
      return isConnected()?intToIp(var0):"Error: No Wifi Connection";
   }

   @SimpleFunction(
      description = "Causes an Exception, used to debug exception processing."
   )
   public static void doFault() throws Exception {
      throw new Exception("doFault called!");
   }

   static void doSettings() {
      Log.d("PhoneStatus", "doSettings called.");
      if(mainInstance != null) {
         mainInstance.OnSettings();
      } else {
         Log.d("PhoneStatus", "mainStance is null on doSettings");
      }
   }

   public static String intToIp(int var0) {
      return (var0 & 255) + "." + (var0 >> 8 & 255) + "." + (var0 >> 16 & 255) + "." + (var0 >> 24 & 255);
   }

   @SimpleFunction(
      description = "Returns TRUE if the phone is on Wifi, FALSE otherwise"
   )
   public static boolean isConnected() {
      ConnectivityManager var1 = (ConnectivityManager)activity.getSystemService("connectivity");
      NetworkInfo var0 = null;
      if(var1 != null) {
         var0 = var1.getNetworkInfo(1);
      }

      return var0 == null?false:var0.isConnected();
   }

   @SimpleEvent
   public void OnSettings() {
      EventDispatcher.dispatchEvent(this, "OnSettings", new Object[0]);
   }

   @SimpleFunction(
      description = "Obtain the Android Application Version"
   )
   public String getVersionName() {
      try {
         String var1 = this.form.getPackageManager().getPackageInfo(this.form.getPackageName(), 0).versionName;
         return var1;
      } catch (NameNotFoundException var2) {
         Log.e("PhoneStatus", "Exception fetching package name.", var2);
         return "";
      }
   }

   @SimpleFunction(
      description = "Downloads the URL and installs it as an Android Package"
   )
   public void installURL(String var1) {
      PackageInstaller.doPackageInstall(this.form, var1);
   }

   @SimpleFunction(
      description = "Returns true if we are running in the emulator or USB Connection"
   )
   public boolean isDirect() {
      Log.d("PhoneStatus", "android.os.Build.VERSION.RELEASE = " + VERSION.RELEASE);
      Log.d("PhoneStatus", "android.os.Build.PRODUCT = " + Build.PRODUCT);
      return Build.PRODUCT.contains("google_sdk")?true:(this.form instanceof ReplForm?((ReplForm)this.form).isDirect():false);
   }

   @SimpleFunction(
      description = "Declare that we have loaded our initial assets and other assets should come from the sdcard"
   )
   public void setAssetsLoaded() {
      if(this.form instanceof ReplForm) {
         ((ReplForm)this.form).setAssetsLoaded();
      }

   }

   @SimpleFunction(
      description = "Establish the secret seed for HOTP generation. Return the SHA1 of the provided seed, this will be used to contact the rendezvous server."
   )
   public String setHmacSeedReturnCode(String var1) {
      AppInvHTTPD.setHmacKey(var1);

      MessageDigest var2;
      try {
         var2 = MessageDigest.getInstance("SHA1");
      } catch (Exception var7) {
         Log.e("PhoneStatus", "Exception getting SHA1 Instance", var7);
         return "";
      }

      var2.update(var1.getBytes());
      byte[] var8 = var2.digest();
      StringBuffer var3 = new StringBuffer(var8.length * 2);
      Formatter var4 = new Formatter(var3);
      int var6 = var8.length;

      for(int var5 = 0; var5 < var6; ++var5) {
         var4.format("%02x", new Object[]{Byte.valueOf(var8[var5])});
      }

      Log.d("PhoneStatus", "Seed = " + var1);
      Log.d("PhoneStatus", "Code = " + var3.toString());
      return var3.toString();
   }

   @SimpleFunction(
      description = "Really Exit the Application"
   )
   public void shutdown() {
      this.form.finish();
      System.exit(0);
   }

   @SimpleFunction(
      description = "Start the internal AppInvHTTPD to listen for incoming forms. FOR REPL USE ONLY!"
   )
   public void startHTTPD(boolean var1) {
      ReplForm.topform.startHTTPD(var1);
   }
}
