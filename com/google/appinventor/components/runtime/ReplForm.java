package com.google.appinventor.components.runtime;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.Toast;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.PhoneStatus;
import com.google.appinventor.components.runtime.util.AppInvHTTPD;
import com.google.appinventor.components.runtime.util.RetValManager;
import java.io.IOException;
import java.util.Iterator;

public class ReplForm extends Form {

   private static final String REPL_ASSET_DIR = "/sdcard/AppInventor/assets/";
   public static ReplForm topform;
   private boolean IsUSBRepl = false;
   private boolean assetsLoaded = false;
   private AppInvHTTPD httpdServer = null;
   private boolean isDirect = false;
   private Object replResult = null;
   private String replResultFormName = null;


   public ReplForm() {
      topform = this;
   }

   private void checkAssetDir() {
      java.io.File var1 = new java.io.File("/sdcard/AppInventor/assets/");
      if(!var1.exists()) {
         var1.mkdirs();
      }

   }

   void HandleReturnValues() {
      Log.d("ReplForm", "HandleReturnValues() Called, replResult = " + this.replResult);
      if(this.replResult != null) {
         this.OtherScreenClosed(this.replResultFormName, this.replResult);
         Log.d("ReplForm", "Called OtherScreenClosed");
         this.replResult = null;
      }

   }

   public void addSettingsButton(Menu var1) {
      var1.add(0, 0, 3, "Settings").setOnMenuItemClickListener(new OnMenuItemClickListener() {
         public boolean onMenuItemClick(MenuItem var1) {
            PhoneStatus.doSettings();
            return true;
         }
      }).setIcon(17301651);
   }

   protected void closeApplicationFromBlocks() {
      this.runOnUiThread(new Runnable() {
         public void run() {
            Toast.makeText(ReplForm.this, "Closing forms is not currently supported during development.", 1).show();
         }
      });
   }

   protected void closeForm(Intent var1) {
      RetValManager.popScreen("Not Yet");
   }

   public boolean isAssetsLoaded() {
      return this.assetsLoaded;
   }

   public boolean isDirect() {
      return this.isDirect;
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      Log.d("ReplForm", "onCreate");
      this.processExtras(this.getIntent(), false);
   }

   public boolean onCreateOptionsMenu(Menu var1) {
      super.onCreateOptionsMenu(var1);
      this.addSettingsButton(var1);
      return true;
   }

   protected void onDestroy() {
      super.onDestroy();
      if(this.httpdServer != null) {
         this.httpdServer.stop();
         this.httpdServer = null;
      }

      this.finish();
      System.exit(0);
   }

   protected void onNewIntent(Intent var1) {
      super.onNewIntent(var1);
      Log.d("ReplForm", "onNewIntent Called");
      this.processExtras(var1, true);
   }

   protected void onResume() {
      super.onResume();
   }

   protected void onStop() {
      super.onStop();
   }

   protected void processExtras(Intent var1, boolean var2) {
      Bundle var4 = var1.getExtras();
      if(var4 != null) {
         Log.d("ReplForm", "extras: " + var4);
         Iterator var3 = var4.keySet().iterator();

         while(var3.hasNext()) {
            Log.d("ReplForm", "Extra Key: " + (String)var3.next());
         }
      }

      if(var4 != null && var4.getBoolean("rundirect")) {
         Log.d("ReplForm", "processExtras rundirect is true and restart is " + var2);
         this.isDirect = true;
         this.assetsLoaded = true;
         if(var2) {
            this.clear();
            if(this.httpdServer == null) {
               this.startHTTPD(true);
               AppInvHTTPD var5 = this.httpdServer;
               AppInvHTTPD.setHmacKey("emulator");
               return;
            }

            this.httpdServer.resetSeq();
         }
      }

   }

   public void setAssetsLoaded() {
      this.assetsLoaded = true;
   }

   public void setFormName(String var1) {
      this.formName = var1;
      Log.d("ReplForm", "formName is now " + var1);
   }

   public void setIsUSBrepl() {
      this.IsUSBRepl = true;
   }

   protected void setResult(Object var1) {
      Log.d("ReplForm", "setResult: " + var1);
      this.replResult = var1;
      this.replResultFormName = this.formName;
   }

   public void startHTTPD(boolean var1) {
      try {
         if(this.httpdServer == null) {
            this.checkAssetDir();
            this.httpdServer = new AppInvHTTPD(8001, new java.io.File("/sdcard/AppInventor/assets/"), var1, this);
            Log.i("ReplForm", "started AppInvHTTPD");
         }

      } catch (IOException var3) {
         Log.e("ReplForm", "Setting up NanoHTTPD: " + var3.toString());
      }
   }

   protected void startNewForm(String var1, Object var2) {
      if(var2 != null) {
         this.startupValue = jsonEncodeForForm(var2, "open another screen with start value");
      }

      RetValManager.pushScreen(var1, var2);
   }
}
