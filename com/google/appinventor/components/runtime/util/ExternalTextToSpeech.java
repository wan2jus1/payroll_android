package com.google.appinventor.components.runtime.util;

import android.content.Intent;
import com.google.appinventor.components.runtime.ActivityResultListener;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.util.ITextToSpeech;
import java.util.Locale;

public class ExternalTextToSpeech implements ITextToSpeech, ActivityResultListener {

   private static final String TTS_INTENT = "com.google.tts.makeBagel";
   private final ITextToSpeech.TextToSpeechCallback callback;
   private final ComponentContainer container;
   private int requestCode;


   public ExternalTextToSpeech(ComponentContainer var1, ITextToSpeech.TextToSpeechCallback var2) {
      this.container = var1;
      this.callback = var2;
   }

   public boolean isInitialized() {
      return true;
   }

   public int isLanguageAvailable(Locale var1) {
      return -1;
   }

   public void onDestroy() {
   }

   public void onResume() {
   }

   public void onStop() {
   }

   public void resultReturned(int var1, int var2, Intent var3) {
      boolean var4;
      if(var1 == this.requestCode && var2 == -1) {
         var4 = true;
      } else {
         var4 = false;
      }

      if(var4) {
         this.callback.onSuccess();
      } else {
         this.callback.onFailure();
      }
   }

   public void setPitch(float var1) {
   }

   public void setSpeechRate(float var1) {
   }

   public void speak(String var1, Locale var2) {
      Intent var3 = new Intent("com.google.tts.makeBagel");
      var3.setFlags(131072);
      var3.setFlags(8388608);
      var3.setFlags(1073741824);
      var3.putExtra("message", var1);
      var3.putExtra("language", var2.getISO3Language());
      var3.putExtra("country", var2.getISO3Country());
      if(this.requestCode == 0) {
         this.requestCode = this.container.$form().registerForActivityResult(this);
      }

      this.container.$context().startActivityForResult(var3, this.requestCode);
   }
}
