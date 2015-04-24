package com.google.appinventor.components.runtime;

import android.app.Activity;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.util.AsynchUtil;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import org.json.JSONException;
import org.json.JSONObject;

@DesignerComponent(
   category = ComponentCategory.MEDIA,
   description = "Use this component to translate words and sentences between different languages. This component needs Internet access, as it will request translations to the Yandex.Translate service. Specify the source and target language in the form source-target using two letter language codes. So\"en-es\" will translate from English to Spanish while \"es-ru\" will translate from Spanish to Russian. If you leave out the source language, the service will attempt to detect the source language. So providing just \"es\" will attempt to detect the source language and translate it to Spanish.<p /> This component is powered by the Yandex translation service.  See http://api.yandex.com/translate/ for more information, including the list of available languages and the meanings of the language codes and status codes. <p />Note: Translation happens asynchronously in the background. When the translation is complete, the \"GotTranslation\" event is triggered.",
   iconName = "images/yandex.png",
   nonVisible = true,
   version = 1
)
@SimpleObject
@UsesPermissions(
   permissionNames = "android.permission.INTERNET"
)
public final class YandexTranslate extends AndroidNonvisibleComponent {

   public static final String YANDEX_TRANSLATE_SERVICE_URL = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=";
   private final Activity activity;
   private final byte[] key1 = new byte[]{(byte)-127, (byte)-88, (byte)79, (byte)80, (byte)65, (byte)112, (byte)-80, (byte)87, (byte)-62, (byte)126, (byte)-125, (byte)-25, (byte)-31, (byte)55, (byte)107, (byte)-42, (byte)-63, (byte)-62, (byte)33, (byte)-122, (byte)1, (byte)89, (byte)-33, (byte)23, (byte)-19, (byte)18, (byte)-81, (byte)37, (byte)-67, (byte)114, (byte)92, (byte)-60, (byte)-76, (byte)-50, (byte)-59, (byte)-49, (byte)-114, (byte)-64, (byte)-96, (byte)-75, (byte)117, (byte)-116, (byte)53, (byte)-8, (byte)44, (byte)111, (byte)120, (byte)48, (byte)41, (byte)30, (byte)85, (byte)-116, (byte)-31, (byte)17, (byte)87, (byte)-89, (byte)-49, (byte)-51, (byte)47, (byte)92, (byte)121, (byte)-58, (byte)-80, (byte)-25, (byte)86, (byte)123, (byte)-36, (byte)-9, (byte)101, (byte)-112, (byte)-22, (byte)-28, (byte)-29, (byte)-14, (byte)-125, (byte)46, (byte)-103, (byte)-36, (byte)125, (byte)114, (byte)35, (byte)-31, (byte)1, (byte)123};
   private final byte[] key2 = new byte[]{(byte)-11, (byte)-38, (byte)33, (byte)35, (byte)45, (byte)94, (byte)-127, (byte)121, (byte)-13, (byte)80, (byte)-79, (byte)-41, (byte)-48, (byte)3, (byte)91, (byte)-29, (byte)-15, (byte)-9, (byte)117, (byte)-74, (byte)49, (byte)105, (byte)-26, (byte)34, (byte)-35, (byte)72, (byte)-127, (byte)64, (byte)-116, (byte)69, (byte)111, (byte)-12, (byte)-48, (byte)-81, (byte)-11, (byte)-83, (byte)-69, (byte)-12, (byte)-108, (byte)-42, (byte)65, (byte)-72, (byte)86, (byte)-42, (byte)27, (byte)12, (byte)26, (byte)2, (byte)28, (byte)122, (byte)51, (byte)-24, (byte)-45, (byte)36, (byte)54, (byte)-106, (byte)-87, (byte)-3, (byte)27, (byte)62, (byte)65, (byte)-16, (byte)-126, (byte)-42, (byte)99, (byte)77, (byte)-70, (byte)-49, (byte)83, (byte)-12, (byte)-114, (byte)-35, (byte)-44, (byte)-109, (byte)-77, (byte)28, (byte)-84, (byte)-66, (byte)72, (byte)22, (byte)18, (byte)-126, (byte)50, (byte)78};
   private final String yandexKey;


   public YandexTranslate(ComponentContainer var1) {
      super(var1.$form());
      this.form.setYandexTranslateTagline();
      this.yandexKey = this.gk();
      this.activity = var1.$context();
   }

   private static String getResponseContent(HttpURLConnection param0) throws IOException {
      // $FF: Couldn't be decompiled
   }

   private String gk() {
      byte[] var1 = new byte[this.key1.length];

      for(int var2 = 0; var2 < this.key1.length; ++var2) {
         var1[var2] = (byte)(this.key1[var2] ^ this.key2[var2]);
      }

      return new String(var1);
   }

   private void performRequest(String var1, final String var2) throws IOException, JSONException {
      HttpURLConnection var6 = (HttpURLConnection)(new URL("https://translate.yandex.net/api/v1.5/tr.json/translate?key=" + this.yandexKey + "&lang=" + var1 + "&text=" + URLEncoder.encode(var2, "UTF-8"))).openConnection();
      if(var6 != null) {
         try {
            JSONObject var3 = new JSONObject(getResponseContent(var6));
            var2 = var3.getString("code");
            final String var7 = (String)var3.getJSONArray("text").get(0);
            this.activity.runOnUiThread(new Runnable() {
               public void run() {
                  YandexTranslate.this.GotTranslation(var2, var7);
               }
            });
         } finally {
            var6.disconnect();
         }
      }

   }

   @SimpleEvent(
      description = "Event triggered when the Yandex.Translate service returns the translated text. This event also provides a response code for error handling. If the responseCode is not 200, then something went wrong with the call, and the translation will not be available."
   )
   public void GotTranslation(String var1, String var2) {
      EventDispatcher.dispatchEvent(this, "GotTranslation", new Object[]{var1, var2});
   }

   @SimpleFunction(
      description = "By providing a target language to translate to (for instance, \'es\' for Spanish, \'en\' for English, or \'ru\' for Russian), and a word or sentence to translate, this method will request a translation to the Yandex.Translate service.\nOnce the text is translated by the external service, the event GotTranslation will be executed.\nNote: Yandex.Translate will attempt to detect the source language. You can also specify prepending it to the language translation. I.e., es-ru will specify Spanish to Russian translation."
   )
   public void RequestTranslation(final String var1, final String var2) {
      if(this.yandexKey.equals("")) {
         this.form.dispatchErrorOccurredEvent(this, "RequestTranslation", 2201, new Object[0]);
      } else {
         AsynchUtil.runAsynchronously(new Runnable() {
            public void run() {
               try {
                  YandexTranslate.this.performRequest(var1, var2);
               } catch (IOException var2x) {
                  YandexTranslate.this.form.dispatchErrorOccurredEvent(YandexTranslate.this, "RequestTranslation", 2202, new Object[0]);
               } catch (JSONException var3) {
                  YandexTranslate.this.form.dispatchErrorOccurredEvent(YandexTranslate.this, "RequestTranslation", 2203, new Object[0]);
               }
            }
         });
      }
   }
}
