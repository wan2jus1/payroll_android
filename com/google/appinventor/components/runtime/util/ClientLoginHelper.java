package com.google.appinventor.components.runtime.util;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.google.appinventor.components.runtime.util.AccountChooser;
import com.google.appinventor.components.runtime.util.IClientLoginHelper;
import java.io.IOException;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

public class ClientLoginHelper implements IClientLoginHelper {

   private static final String ACCOUNT_TYPE = "com.google";
   private static final String AUTHORIZATION_HEADER_PREFIX = "GoogleLogin auth=";
   private static final String LOG_TAG = "ClientLoginHelper";
   private AccountChooser accountChooser;
   private AccountManager accountManager;
   private Activity activity;
   private String authToken;
   private HttpClient client;
   private boolean initialized = false;
   private String service;


   public ClientLoginHelper(Activity var1, String var2, String var3, HttpClient var4) {
      this.service = var2;
      Object var5 = var4;
      if(var4 == null) {
         var5 = new DefaultHttpClient();
      }

      this.client = (HttpClient)var5;
      this.activity = var1;
      this.accountManager = AccountManager.get(var1);
      this.accountChooser = new AccountChooser(var1, var2, var3, var2);
   }

   private static void addGoogleAuthHeader(HttpUriRequest var0, String var1) {
      if(var1 != null) {
         Log.i("ClientLoginHelper", "adding auth token token: " + var1);
         var0.addHeader("Authorization", "GoogleLogin auth=" + var1);
      }

   }

   private void initialize() throws ClientProtocolException {
      if(!this.initialized) {
         Log.i("ClientLoginHelper", "initializing");
         if(this.isUiThread()) {
            throw new IllegalArgumentException("Can\'t initialize login helper from UI thread");
         }

         this.authToken = this.getAuthToken();
         this.initialized = true;
      }

   }

   private boolean isUiThread() {
      return Looper.getMainLooper().getThread().equals(Thread.currentThread());
   }

   private static void removeGoogleAuthHeaders(HttpUriRequest var0) {
      Header[] var1 = var0.getAllHeaders();
      int var4 = var1.length;

      for(int var3 = 0; var3 < var4; ++var3) {
         Header var2 = var1[var3];
         if(var2.getName().equalsIgnoreCase("Authorization") && var2.getValue().startsWith("GoogleLogin auth=")) {
            Log.i("ClientLoginHelper", "Removing header:" + var2);
            var0.removeHeader(var2);
         }
      }

   }

   public HttpResponse execute(HttpUriRequest var1) throws ClientProtocolException, IOException {
      this.initialize();
      addGoogleAuthHeader(var1, this.authToken);
      HttpResponse var3 = this.client.execute(var1);
      HttpResponse var2 = var3;
      if(var3.getStatusLine().getStatusCode() == 401) {
         Log.i("ClientLoginHelper", "Invalid token: " + this.authToken);
         this.accountManager.invalidateAuthToken("com.google", this.authToken);
         this.authToken = this.getAuthToken();
         removeGoogleAuthHeaders(var1);
         addGoogleAuthHeader(var1, this.authToken);
         Log.i("ClientLoginHelper", "new token: " + this.authToken);
         var2 = this.client.execute(var1);
      }

      return var2;
   }

   public void forgetAccountName() {
      this.accountChooser.forgetAccountName();
   }

   public String getAuthToken() throws ClientProtocolException {
      Account var1 = this.accountChooser.findAccount();
      if(var1 != null) {
         AccountManagerFuture var5 = this.accountManager.getAuthToken(var1, this.service, (Bundle)null, this.activity, (AccountManagerCallback)null, (Handler)null);
         Log.i("ClientLoginHelper", "Have account, auth token: " + var5);

         try {
            String var6 = ((Bundle)var5.getResult()).getString("authtoken");
            return var6;
         } catch (AuthenticatorException var2) {
            var2.printStackTrace();
         } catch (IOException var3) {
            var3.printStackTrace();
         } catch (OperationCanceledException var4) {
            var4.printStackTrace();
         }
      }

      throw new ClientProtocolException("Can\'t get valid authentication token");
   }
}
