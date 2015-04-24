package com.google.appinventor.components.runtime.util;

import android.app.Activity;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener;
import android.util.Log;
import com.google.appinventor.components.runtime.util.ITextToSpeech;
import java.util.HashMap;
import java.util.Locale;

public class InternalTextToSpeech implements ITextToSpeech {

   private static final String LOG_TAG = "InternalTTS";
   private final Activity activity;
   private final ITextToSpeech.TextToSpeechCallback callback;
   private volatile boolean isTtsInitialized;
   private Handler mHandler = new Handler();
   private int nextUtteranceId = 1;
   private TextToSpeech tts;
   private int ttsMaxRetries = 20;
   private int ttsRetryDelay = 500;


   public InternalTextToSpeech(Activity var1, ITextToSpeech.TextToSpeechCallback var2) {
      this.activity = var1;
      this.callback = var2;
      this.initializeTts();
   }

   private void initializeTts() {
      if(this.tts == null) {
         Log.d("InternalTTS", "INTERNAL TTS is reinitializing");
         this.tts = new TextToSpeech(this.activity, new OnInitListener() {
            public void onInit(int var1) {
               if(var1 == 0) {
                  InternalTextToSpeech.this.isTtsInitialized = true;
               }

            }
         });
      }

   }

   private void speak(final String var1, final Locale var2, final int var3) {
      Log.d("InternalTTS", "InternalTTS speak called, message = " + var1);
      if(var3 > this.ttsMaxRetries) {
         Log.d("InternalTTS", "max number of speak retries exceeded: speak will fail");
         this.callback.onFailure();
      }

      if(this.isTtsInitialized) {
         Log.d("InternalTTS", "TTS initialized after " + var3 + " retries.");
         this.tts.setLanguage(var2);
         this.tts.setOnUtteranceCompletedListener(new OnUtteranceCompletedListener() {
            public void onUtteranceCompleted(String var1) {
               InternalTextToSpeech.this.activity.runOnUiThread(new Runnable() {
                  public void run() {
                     InternalTextToSpeech.this.callback.onSuccess();
                  }
               });
            }
         });
         HashMap var6 = new HashMap();
         var3 = this.nextUtteranceId;
         this.nextUtteranceId = var3 + 1;
         var6.put("utteranceId", Integer.toString(var3));
         TextToSpeech var4 = this.tts;
         TextToSpeech var5 = this.tts;
         if(var4.speak(var1, 0, var6) == -1) {
            Log.d("InternalTTS", "speak called and tts.speak result was an error");
            this.callback.onFailure();
         }

      } else {
         Log.d("InternalTTS", "speak called when TTS not initialized");
         this.mHandler.postDelayed(new Runnable() {
            public void run() {
               Log.d("InternalTTS", "delaying call to speak.  Retries is: " + var3 + " Message is: " + var1);
               InternalTextToSpeech.this.speak(var1, var2, var3 + 1);
            }
         }, (long)this.ttsRetryDelay);
      }
   }

   public boolean isInitialized() {
      return this.isTtsInitialized;
   }

   public int isLanguageAvailable(Locale var1) {
      return this.tts.isLanguageAvailable(var1);
   }

   public void onDestroy() {
      Log.d("InternalTTS", "Internal TTS got onDestroy");
      if(this.tts != null) {
         this.tts.shutdown();
         this.isTtsInitialized = false;
         this.tts = null;
      }

   }

   public void onResume() {
      Log.d("InternalTTS", "Internal TTS got onResume");
      this.initializeTts();
   }

   public void onStop() {
      Log.d("InternalTTS", "Internal TTS got onStop");
   }

   public void setPitch(float var1) {
      this.tts.setPitch(var1);
   }

   public void setSpeechRate(float var1) {
      this.tts.setSpeechRate(var1);
   }

   public void speak(String var1, Locale var2) {
      Log.d("InternalTTS", "Internal TTS got speak");
      this.speak(var1, var2, 0);
   }
}
