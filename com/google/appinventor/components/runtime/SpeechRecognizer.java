package com.google.appinventor.components.runtime;

import android.content.Intent;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.ActivityResultListener;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.EventDispatcher;

@DesignerComponent(
   category = ComponentCategory.MEDIA,
   description = "Component for using Voice Recognition to convert from speech to text",
   iconName = "images/speechRecognizer.png",
   nonVisible = true,
   version = 1
)
@SimpleObject
public class SpeechRecognizer extends AndroidNonvisibleComponent implements Component, ActivityResultListener {

   private final ComponentContainer container;
   private int requestCode;
   private String result;


   public SpeechRecognizer(ComponentContainer var1) {
      super(var1.$form());
      this.container = var1;
      this.result = "";
   }

   @SimpleEvent
   public void AfterGettingText(String var1) {
      EventDispatcher.dispatchEvent(this, "AfterGettingText", new Object[]{var1});
   }

   @SimpleEvent
   public void BeforeGettingText() {
      EventDispatcher.dispatchEvent(this, "BeforeGettingText", new Object[0]);
   }

   @SimpleFunction
   public void GetText() {
      this.BeforeGettingText();
      Intent var1 = new Intent("android.speech.action.RECOGNIZE_SPEECH");
      var1.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form");
      if(this.requestCode == 0) {
         this.requestCode = this.form.registerForActivityResult(this);
      }

      this.container.$context().startActivityForResult(var1, this.requestCode);
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public String Result() {
      return this.result;
   }

   public void resultReturned(int var1, int var2, Intent var3) {
      if(var1 == this.requestCode && var2 == -1) {
         if(var3.hasExtra("android.speech.extra.RESULTS")) {
            this.result = (String)var3.getExtras().getStringArrayList("android.speech.extra.RESULTS").get(0);
         } else {
            this.result = "";
         }

         this.AfterGettingText(this.result);
      }

   }
}
