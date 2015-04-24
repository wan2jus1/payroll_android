package com.google.appinventor.components.runtime.util;

import android.util.Log;
import com.google.appinventor.components.runtime.util.AsyncCallbackPair;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WebServiceUtil {

   private static final WebServiceUtil INSTANCE = new WebServiceUtil();
   private static final String LOG_TAG = "WebServiceUtil";
   private static HttpClient httpClient = null;
   private static Object httpClientSynchronizer = new Object();


   public static WebServiceUtil getInstance() {
      // $FF: Couldn't be decompiled
   }

   public void postCommand(String var1, String var2, List var3, AsyncCallbackPair var4) {
      Log.d("WebServiceUtil", "Posting " + var2 + " to " + var1 + " with arguments " + var3);
      if(var1 == null || var1.equals("")) {
         var4.onFailure("No service url to post command to.");
      }

      HttpPost var9 = new HttpPost(var1 + "/" + var2);
      Object var8 = var3;
      if(var3 == null) {
         var8 = new ArrayList();
      }

      try {
         BasicResponseHandler var10 = new BasicResponseHandler();
         var9.setEntity(new UrlEncodedFormEntity((List)var8, "UTF-8"));
         var9.setHeader("Accept", "application/json");
         var4.onSuccess((String)httpClient.execute(var9, var10));
      } catch (UnsupportedEncodingException var5) {
         Log.w("WebServiceUtil", var5);
         var4.onFailure("Failed to encode params for web service call.");
      } catch (ClientProtocolException var6) {
         Log.w("WebServiceUtil", var6);
         var4.onFailure("Communication with the web service encountered a protocol exception.");
      } catch (IOException var7) {
         Log.w("WebServiceUtil", var7);
         var4.onFailure("Communication with the web service timed out.");
      }
   }

   public void postCommandReturningArray(String var1, String var2, List var3, final AsyncCallbackPair var4) {
      this.postCommand(var1, var2, var3, new AsyncCallbackPair() {
         public void onFailure(String var1) {
            var4.onFailure(var1);
         }
         public void onSuccess(String var1) {
            try {
               var4.onSuccess(new JSONArray(var1));
            } catch (JSONException var2) {
               var4.onFailure(var2.getMessage());
            }
         }
      });
   }

   public void postCommandReturningObject(String var1, String var2, List var3, final AsyncCallbackPair var4) {
      this.postCommand(var1, var2, var3, new AsyncCallbackPair() {
         public void onFailure(String var1) {
            var4.onFailure(var1);
         }
         public void onSuccess(String var1) {
            try {
               var4.onSuccess(new JSONObject(var1));
            } catch (JSONException var2) {
               var4.onFailure(var2.getMessage());
            }
         }
      });
   }
}
