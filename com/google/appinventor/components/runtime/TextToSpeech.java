package com.google.appinventor.components.runtime;

import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.OnDestroyListener;
import com.google.appinventor.components.runtime.OnResumeListener;
import com.google.appinventor.components.runtime.OnStopListener;
import com.google.appinventor.components.runtime.collect.Maps;
import com.google.appinventor.components.runtime.util.ExternalTextToSpeech;
import com.google.appinventor.components.runtime.util.ITextToSpeech;
import com.google.appinventor.components.runtime.util.InternalTextToSpeech;
import com.google.appinventor.components.runtime.util.SdkLevel;
import com.google.appinventor.components.runtime.util.YailList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;

@DesignerComponent(
   category = ComponentCategory.MEDIA,
   description = "The TestToSpeech component speaks a given text aloud.  You can set the pitch and the rate of speech. <p>You can also set a language by supplying a language code.  This changes the pronounciation of words, not the actual language spoken.  For example, setting the language to French and speaking English text will sound like someone speaking English (en) with a French accent.</p> <p>You can also specify a country by supplying a country code. This can affect the pronounciation.  For example, British English (GBR) will sound different from US English (USA).  Not every country code will affect every language.</p> <p>The languages and countries available depend on the particular device, and can be listed with the AvailableLanguages and AvailableCountries properties.</p>",
   iconName = "images/textToSpeech.png",
   nonVisible = true,
   version = 3
)
@SimpleObject
public class TextToSpeech extends AndroidNonvisibleComponent implements Component, OnStopListener, OnResumeListener, OnDestroyListener {

   private static final String LOG_TAG = "TextToSpeech";
   private static final Map iso3CountryToLocaleMap = Maps.newHashMap();
   private static final Map iso3LanguageToLocaleMap = Maps.newHashMap();
   private YailList allCountries;
   private YailList allLanguages;
   private String country;
   private ArrayList countryList;
   private boolean isTtsPrepared;
   private String iso2Country;
   private String iso2Language;
   private String language;
   private ArrayList languageList;
   private float pitch = 1.0F;
   private boolean result = false;
   private float speechRate = 1.0F;
   private final ITextToSpeech tts;


   static {
      initLocaleMaps();
   }

   public TextToSpeech(ComponentContainer var1) {
      super(var1.$form());
      this.Language("");
      this.Country("");
      boolean var4;
      if(SdkLevel.getLevel() < 4) {
         var4 = true;
      } else {
         var4 = false;
      }

      StringBuilder var3 = (new StringBuilder()).append("Using ");
      String var2;
      if(var4) {
         var2 = "external";
      } else {
         var2 = "internal";
      }

      Log.v("TextToSpeech", var3.append(var2).append(" TTS library.").toString());
      ITextToSpeech.TextToSpeechCallback var6 = new ITextToSpeech.TextToSpeechCallback() {
         public void onFailure() {
            TextToSpeech.this.result = false;
            TextToSpeech.this.AfterSpeaking(false);
         }
         public void onSuccess() {
            TextToSpeech.this.result = true;
            TextToSpeech.this.AfterSpeaking(true);
         }
      };
      Object var5;
      if(var4) {
         var5 = new ExternalTextToSpeech(var1, var6);
      } else {
         var5 = new InternalTextToSpeech(var1.$context(), var6);
      }

      this.tts = (ITextToSpeech)var5;
      this.form.registerForOnStop(this);
      this.form.registerForOnResume(this);
      this.form.registerForOnDestroy(this);
      this.form.setVolumeControlStream(3);
      this.isTtsPrepared = false;
      this.languageList = new ArrayList();
      this.countryList = new ArrayList();
      this.allLanguages = YailList.makeList((List)this.languageList);
      this.allCountries = YailList.makeList((List)this.countryList);
   }

   private void getLanguageAndCountryLists() {
      if(SdkLevel.getLevel() >= 4) {
         Locale[] var1 = Locale.getAvailableLocales();
         int var5 = var1.length;

         for(int var4 = 0; var4 < var5; ++var4) {
            Locale var3 = var1[var4];
            if(this.tts.isLanguageAvailable(var3) != -2) {
               String var2 = var3.getLanguage();
               String var6 = var3.getISO3Country();
               if(!var2.equals("") && !this.languageList.contains(var2)) {
                  this.languageList.add(var2);
               }

               if(!var6.equals("") && !this.countryList.contains(var6)) {
                  this.countryList.add(var6);
               }
            }
         }

         Collections.sort(this.languageList);
         Collections.sort(this.countryList);
         this.allLanguages = YailList.makeList((List)this.languageList);
         this.allCountries = YailList.makeList((List)this.countryList);
      }

   }

   private static void initLocaleMaps() {
      Locale[] var0 = Locale.getAvailableLocales();
      int var4 = var0.length;

      for(int var3 = 0; var3 < var4; ++var3) {
         Locale var1 = var0[var3];

         String var2;
         try {
            var2 = var1.getISO3Country();
            if(var2.length() > 0) {
               iso3CountryToLocaleMap.put(var2, var1);
            }
         } catch (MissingResourceException var6) {
            ;
         }

         try {
            var2 = var1.getISO3Language();
            if(var2.length() > 0) {
               iso3LanguageToLocaleMap.put(var2, var1);
            }
         } catch (MissingResourceException var5) {
            ;
         }
      }

   }

   private static Locale iso3CountryToLocale(String var0) {
      Locale var2 = (Locale)iso3CountryToLocaleMap.get(var0);
      Locale var1 = var2;
      if(var2 == null) {
         var1 = (Locale)iso3CountryToLocaleMap.get(var0.toUpperCase(Locale.ENGLISH));
      }

      Locale var3 = var1;
      if(var1 == null) {
         var3 = Locale.getDefault();
      }

      return var3;
   }

   private static Locale iso3LanguageToLocale(String var0) {
      Locale var2 = (Locale)iso3LanguageToLocaleMap.get(var0);
      Locale var1 = var2;
      if(var2 == null) {
         var1 = (Locale)iso3LanguageToLocaleMap.get(var0.toLowerCase(Locale.ENGLISH));
      }

      Locale var3 = var1;
      if(var1 == null) {
         var3 = Locale.getDefault();
      }

      return var3;
   }

   @SimpleEvent
   public void AfterSpeaking(boolean var1) {
      EventDispatcher.dispatchEvent(this, "AfterSpeaking", new Object[]{Boolean.valueOf(var1)});
   }

   @SimpleProperty(
      description = "List of the country codes available on this device for use with TextToSpeech.  Check the Android developer documentation under supported languages to find the meanings of these abbreviations."
   )
   public YailList AvailableCountries() {
      this.prepareLanguageAndCountryProperties();
      return this.allCountries;
   }

   @SimpleProperty(
      description = "List of the languages available on this device for use with TextToSpeech.  Check the Android developer documentation under supported languages to find the meanings of these abbreviations."
   )
   public YailList AvailableLanguages() {
      this.prepareLanguageAndCountryProperties();
      return this.allLanguages;
   }

   @SimpleEvent
   public void BeforeSpeaking() {
      EventDispatcher.dispatchEvent(this, "BeforeSpeaking", new Object[0]);
   }

   @SimpleProperty
   public String Country() {
      return this.country;
   }

   @DesignerProperty(
      defaultValue = "",
      editorType = "string"
   )
   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "Country code to use for speech generation.  This can affect the pronounciation.  For example, British English (GBR) will sound different from US English (USA).  Not every country code will affect every language."
   )
   public void Country(String var1) {
      Locale var2;
      switch(var1.length()) {
      case 2:
         var2 = new Locale(var1);
         this.country = var2.getCountry();
         break;
      case 3:
         var2 = iso3CountryToLocale(var1);
         this.country = var2.getISO3Country();
         break;
      default:
         var2 = Locale.getDefault();
         this.country = var2.getCountry();
      }

      this.iso2Country = var2.getCountry();
   }

   @SimpleProperty
   public String Language() {
      return this.language;
   }

   @DesignerProperty(
      defaultValue = "",
      editorType = "string"
   )
   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "Sets the language for TextToSpeech. This changes the way that words are pronounced, not the actual language that is spoken.  For example setting the language to and speaking English text with sound like someone speaking English with a Frernch accent."
   )
   public void Language(String var1) {
      Locale var2;
      switch(var1.length()) {
      case 2:
         var2 = new Locale(var1);
         this.language = var2.getLanguage();
         break;
      case 3:
         var2 = iso3LanguageToLocale(var1);
         this.language = var2.getISO3Language();
         break;
      default:
         var2 = Locale.getDefault();
         this.language = var2.getLanguage();
      }

      this.iso2Language = var2.getLanguage();
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "Returns current value of Pitch"
   )
   public float Pitch() {
      return this.pitch;
   }

   @DesignerProperty(
      defaultValue = "1.0",
      editorType = "float"
   )
   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "Sets the Pitch for TextToSpeech The values should be between 0 and 2 where lower values lower the tone of synthesized voice and greater values raise it."
   )
   public void Pitch(float var1) {
      if(var1 >= 0.0F && var1 <= 2.0F) {
         this.pitch = var1;
         ITextToSpeech var3 = this.tts;
         float var2 = var1;
         if(var1 == 0.0F) {
            var2 = 0.1F;
         }

         var3.setPitch(var2);
      } else {
         Log.i("TextToSpeech", "Pitch value should be between 0 and 2, but user specified: " + var1);
      }
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public boolean Result() {
      return this.result;
   }

   @SimpleFunction
   public void Speak(String var1) {
      this.BeforeSpeaking();
      Locale var2 = new Locale(this.iso2Language, this.iso2Country);
      this.tts.speak(var1, var2);
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "Returns current value of SpeechRate"
   )
   public float SpeechRate() {
      return this.speechRate;
   }

   @DesignerProperty(
      defaultValue = "1.0",
      editorType = "float"
   )
   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "Sets the SpeechRate for TextToSpeech. The values should be between 0 and 2 where lower values slow down the pitch and greater values accelerate it."
   )
   public void SpeechRate(float var1) {
      if(var1 >= 0.0F && var1 <= 2.0F) {
         this.speechRate = var1;
         ITextToSpeech var3 = this.tts;
         float var2 = var1;
         if(var1 == 0.0F) {
            var2 = 0.1F;
         }

         var3.setSpeechRate(var2);
      } else {
         Log.i("TextToSpeech", "speechRate value should be between 0 and 2, but user specified: " + var1);
      }
   }

   public void onDestroy() {
      this.tts.onDestroy();
   }

   public void onResume() {
      this.tts.onResume();
   }

   public void onStop() {
      this.tts.onStop();
   }

   public void prepareLanguageAndCountryProperties() {
      if(!this.isTtsPrepared) {
         if(this.tts.isInitialized()) {
            this.getLanguageAndCountryLists();
            this.isTtsPrepared = true;
            return;
         }

         this.form.dispatchErrorOccurredEvent(this, "TextToSpeech", 2701, new Object[0]);
         this.Speak("");
      }

   }
}
