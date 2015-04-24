package com.google.appinventor.components.runtime.util;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.extensions.android2.auth.GoogleAccountManager;

public class OAuth2Helper {

   public static final String PREF_ACCOUNT_NAME = "accountName";
   public static final String PREF_AUTH_TOKEN = "authToken";
   public static final String TAG = "OAuthHelper";
   private static String errorMessage = "Error during OAuth";


   private AccountManagerFuture getAccountManagerResult(Activity var1, GoogleCredential var2, String var3, String var4) {
      GoogleAccountManager var5 = new GoogleAccountManager(var1);
      var5.invalidateAuthToken(var2.getAccessToken());
      AccountManager.get(var1).invalidateAuthToken(var3, (String)null);
      Account var6 = var5.getAccountByName(var4);
      if(var6 != null) {
         Log.i("OAuthHelper", "Getting token by account");
         return var5.getAccountManager().getAuthToken(var6, var3, true, (AccountManagerCallback)null, (Handler)null);
      } else {
         Log.i("OAuthHelper", "Getting token by features, possibly prompting user to select an account");
         return var5.getAccountManager().getAuthTokenByFeatures("com.google", var3, (String[])null, var1, (Bundle)null, (Bundle)null, (AccountManagerCallback)null, (Handler)null);
      }
   }

   public static String getErrorMessage() {
      Log.i("OAuthHelper", "getErrorMessage = " + errorMessage);
      return errorMessage;
   }

   private boolean isUiThread() {
      return Looper.getMainLooper().getThread().equals(Thread.currentThread());
   }

   private void persistCredentials(SharedPreferences var1, String var2, String var3) {
      Log.i("OAuthHelper", "Persisting credentials, acct =" + var2);
      Editor var4 = var1.edit();
      var4.putString("accountName", var2);
      var4.putString("authToken", var3);
      var4.commit();
   }

   public static void resetAccountCredential(Activity var0) {
      Log.i("OAuthHelper", "Reset credentials");
      Editor var1 = var0.getPreferences(0).edit();
      var1.remove("authToken");
      var1.remove("accountName");
      var1.commit();
   }

   public String getRefreshedAuthToken(Activity param1, String param2) {
      // $FF: Couldn't be decompiled
   }
}
