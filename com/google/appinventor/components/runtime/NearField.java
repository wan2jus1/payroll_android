package com.google.appinventor.components.runtime;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.Deleteable;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.OnNewIntentListener;
import com.google.appinventor.components.runtime.OnPauseListener;
import com.google.appinventor.components.runtime.OnResumeListener;
import com.google.appinventor.components.runtime.OnStopListener;
import com.google.appinventor.components.runtime.util.GingerbreadUtil;
import com.google.appinventor.components.runtime.util.SdkLevel;

@DesignerComponent(
   category = ComponentCategory.SENSORS,
   description = "<p>Non-visible component to provide NFC capabilities.  For now this component supports the reading and writing of text tags only (if supported by the device)</p><p>In order to read and write text tags, the component must have its <code>ReadMode</code> property set to True or False respectively.</p>",
   iconName = "images/nearfield.png",
   nonVisible = true,
   version = 1
)
@SimpleObject
@UsesPermissions(
   permissionNames = "android.permission.NFC"
)
public class NearField extends AndroidNonvisibleComponent implements OnStopListener, OnResumeListener, OnPauseListener, OnNewIntentListener, Deleteable {

   private static final String TAG = "nearfield";
   private Activity activity;
   private NfcAdapter nfcAdapter;
   private boolean readMode = true;
   protected int requestCode;
   private String tagContent = "";
   private String textToWrite = "";
   private int writeType;


   public NearField(ComponentContainer var1) {
      super(var1.$form());
      this.activity = var1.$context();
      this.writeType = 1;
      NfcAdapter var2;
      if(SdkLevel.getLevel() >= 9) {
         var2 = GingerbreadUtil.newNfcAdapter(this.activity);
      } else {
         var2 = null;
      }

      this.nfcAdapter = var2;
      this.form.registerForOnResume(this);
      this.form.registerForOnNewIntent(this);
      this.form.registerForOnPause(this);
      Log.d("nearfield", "Nearfield component created");
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public String LastMessage() {
      Log.d("nearfield", "String message method stared");
      return this.tagContent;
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public void ReadMode(boolean var1) {
      Log.d("nearfield", "Read mode set to" + var1);
      this.readMode = var1;
      if(!this.readMode && SdkLevel.getLevel() >= 9) {
         GingerbreadUtil.enableNFCWriteMode(this.activity, this.nfcAdapter, this.textToWrite);
      }

   }

   @DesignerProperty(
      defaultValue = "True",
      editorType = "boolean"
   )
   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public boolean ReadMode() {
      Log.d("nearfield", "boolean method stared");
      return this.readMode;
   }

   @SimpleEvent
   public void TagRead(String var1) {
      Log.d("nearfield", "Tag read: got message " + var1);
      this.tagContent = var1;
      EventDispatcher.dispatchEvent(this, "TagRead", new Object[]{var1});
   }

   @SimpleEvent
   public void TagWritten() {
      Log.d("nearfield", "Tag written: " + this.textToWrite);
      EventDispatcher.dispatchEvent(this, "TagWritten", new Object[0]);
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public String TextToWrite() {
      Log.d("nearfield", "String message method stared");
      return this.textToWrite;
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public void TextToWrite(String var1) {
      Log.d("nearfield", "Text to write set to" + var1);
      this.textToWrite = var1;
      if(!this.readMode && this.writeType == 1 && SdkLevel.getLevel() >= 9) {
         GingerbreadUtil.enableNFCWriteMode(this.activity, this.nfcAdapter, this.textToWrite);
      }

   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public int WriteType() {
      return this.writeType;
   }

   public void onDelete() {
   }

   public void onNewIntent(Intent var1) {
      Log.d("nearfield", "Nearfield on onNewIntent.  Intent is: " + var1);
      this.resolveIntent(var1);
   }

   public void onPause() {
      Log.d("nearfield", "OnPause method started.");
      if(this.nfcAdapter != null) {
         GingerbreadUtil.disableNFCAdapter(this.activity, this.nfcAdapter);
      }

   }

   public void onResume() {
      Intent var1 = this.activity.getIntent();
      Log.d("nearfield", "Nearfield on onResume.  Intent is: " + var1);
   }

   public void onStop() {
   }

   void resolveIntent(Intent var1) {
      Log.d("nearfield", "resolve intent. Intent is: " + var1);
      if(SdkLevel.getLevel() >= 9) {
         GingerbreadUtil.resolveNFCIntent(var1, this);
      }

   }
}
