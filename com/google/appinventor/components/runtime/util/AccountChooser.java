package com.google.appinventor.components.runtime.util;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import java.io.IOException;
import java.util.concurrent.SynchronousQueue;

public class AccountChooser {

   private static final String ACCOUNT_PREFERENCE = "account";
   private static final String ACCOUNT_TYPE = "com.google";
   private static final String LOG_TAG = "AccountChooser";
   private static final String NO_ACCOUNT = "";
   private AccountManager accountManager;
   private Activity activity;
   private String chooseAccountPrompt;
   private String preferencesKey;
   private String service;


   public AccountChooser(Activity var1, String var2, String var3, String var4) {
      this.activity = var1;
      this.service = var2;
      this.chooseAccountPrompt = var3;
      this.preferencesKey = var4;
      this.accountManager = AccountManager.get(var1);
   }

   private Account chooseAccount(String var1, Account[] var2) {
      int var5 = var2.length;

      for(int var4 = 0; var4 < var5; ++var4) {
         Account var3 = var2[var4];
         if(var3.name.equals(var1)) {
            Log.i("AccountChooser", "chose account: " + var1);
            return var3;
         }
      }

      return null;
   }

   private String createAccount() {
      Log.i("AccountChooser", "Adding auth token account ...");
      AccountManagerFuture var1 = this.accountManager.addAccount("com.google", this.service, (String[])null, (Bundle)null, this.activity, (AccountManagerCallback)null, (Handler)null);

      try {
         String var5 = ((Bundle)var1.getResult()).getString("authAccount");
         Log.i("AccountChooser", "created: " + var5);
         return var5;
      } catch (OperationCanceledException var2) {
         var2.printStackTrace();
      } catch (AuthenticatorException var3) {
         var3.printStackTrace();
      } catch (IOException var4) {
         var4.printStackTrace();
      }

      return null;
   }

   private String getPersistedAccountName() {
      return this.getPreferences().getString("account", (String)null);
   }

   private SharedPreferences getPreferences() {
      return this.activity.getSharedPreferences(this.preferencesKey, 0);
   }

   private void persistAccountName(String var1) {
      Log.i("AccountChooser", "persisting account: " + var1);
      this.getPreferences().edit().putString("account", var1).commit();
   }

   private String selectAccount(Account[] var1) {
      SynchronousQueue var2 = new SynchronousQueue();
      (new AccountChooser.SelectAccount(var1, var2)).start();
      Log.i("AccountChooser", "Select: waiting for user...");
      String var4 = null;

      String var5;
      label17: {
         try {
            var5 = (String)var2.take();
         } catch (InterruptedException var3) {
            var3.printStackTrace();
            break label17;
         }

         var4 = var5;
      }

      Log.i("AccountChooser", "Selected: " + var4);
      var5 = var4;
      if(var4 == "") {
         var5 = null;
      }

      return var5;
   }

   public Account findAccount() {
      Account[] var3 = this.accountManager.getAccountsByType("com.google");
      Account var1;
      if(var3.length == 1) {
         this.persistAccountName(var3[0].name);
         var1 = var3[0];
         return var1;
      } else {
         String var4;
         if(var3.length == 0) {
            var4 = this.createAccount();
            if(var4 != null) {
               this.persistAccountName(var4);
               return this.accountManager.getAccountsByType("com.google")[0];
            } else {
               Log.i("AccountChooser", "User failed to create a valid account");
               return null;
            }
         } else {
            var4 = this.getPersistedAccountName();
            if(var4 != null) {
               Account var2 = this.chooseAccount(var4, var3);
               var1 = var2;
               if(var2 != null) {
                  return var1;
               }
            }

            var4 = this.selectAccount(var3);
            if(var4 != null) {
               this.persistAccountName(var4);
               return this.chooseAccount(var4, var3);
            } else {
               Log.i("AccountChooser", "User failed to choose an account");
               return null;
            }
         }
      }
   }

   public void forgetAccountName() {
      this.getPreferences().edit().remove("account").commit();
   }

   class SelectAccount extends Thread implements OnClickListener, OnCancelListener {

      private String[] accountNames;
      private SynchronousQueue queue;


      SelectAccount(Account[] var2, SynchronousQueue var3) {
         this.queue = var3;
         this.accountNames = new String[var2.length];

         for(int var4 = 0; var4 < var2.length; ++var4) {
            this.accountNames[var4] = var2[var4].name;
         }

      }

      public void onCancel(DialogInterface var1) {
         Log.i("AccountChooser", "Chose: canceled");
         this.onClick(var1, -1);
      }

      public void onClick(DialogInterface param1, int param2) {
         // $FF: Couldn't be decompiled
      }

      public void run() {
         AccountChooser.this.activity.runOnUiThread(new Runnable() {
            public void run() {
               (new Builder(AccountChooser.this.activity)).setTitle(Html.fromHtml(AccountChooser.this.chooseAccountPrompt)).setOnCancelListener(SelectAccount.this).setSingleChoiceItems(SelectAccount.this.accountNames, -1, SelectAccount.this).show();
               Log.i("AccountChooser", "Dialog showing!");
            }
         });
      }
   }
}
