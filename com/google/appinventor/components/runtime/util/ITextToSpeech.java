package com.google.appinventor.components.runtime.util;

import java.util.Locale;

public interface ITextToSpeech {

   boolean isInitialized();

   int isLanguageAvailable(Locale var1);

   void onDestroy();

   void onResume();

   void onStop();

   void setPitch(float var1);

   void setSpeechRate(float var1);

   void speak(String var1, Locale var2);

   public interface TextToSpeechCallback {

      void onFailure();

      void onSuccess();
   }
}
