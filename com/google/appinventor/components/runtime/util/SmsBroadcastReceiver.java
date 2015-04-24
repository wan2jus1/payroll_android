package com.google.appinventor.components.runtime.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneNumberUtils;
import android.telephony.gsm.SmsMessage;
import android.util.Log;
import com.google.appinventor.components.runtime.ReplForm;
import com.google.appinventor.components.runtime.Texting;

public class SmsBroadcastReceiver extends BroadcastReceiver {

   public static final int NOTIFICATION_ID = 8647;
   public static final String TAG = "SmsBroadcastReceiver";


   private String getMessage(Intent var1) {
      String var2 = "";
      String var6;
      if(var1.getAction().equals("com.google.android.apps.googlevoice.SMS_RECEIVED")) {
         var6 = var1.getExtras().getString("com.google.android.apps.googlevoice.TEXT");
      } else {
         Object[] var3 = (Object[])((Object[])var1.getExtras().get("pdus"));
         int var5 = var3.length;
         int var4 = 0;

         for(var6 = var2; var4 < var5; ++var4) {
            var6 = SmsMessage.createFromPdu((byte[])((byte[])var3[var4])).getMessageBody();
         }
      }

      return var6;
   }

   private String getPhoneNumber(Intent var1) {
      String var2 = "";
      String var6;
      if(var1.getAction().equals("com.google.android.apps.googlevoice.SMS_RECEIVED")) {
         var6 = PhoneNumberUtils.formatNumber(var1.getExtras().getString("com.google.android.apps.googlevoice.PHONE_NUMBER"));
      } else {
         Object[] var3 = (Object[])((Object[])var1.getExtras().get("pdus"));
         int var5 = var3.length;
         int var4 = 0;

         for(var6 = var2; var4 < var5; ++var4) {
            var6 = PhoneNumberUtils.formatNumber(SmsMessage.createFromPdu((byte[])((byte[])var3[var4])).getOriginatingAddress());
         }
      }

      return var6;
   }

   private boolean isRepl(Context var1) {
      boolean var2 = false;

      boolean var3;
      try {
         String var5 = var1.getPackageName();
         var3 = Class.forName(var5 + ".Screen1").getSuperclass().equals(ReplForm.class);
      } catch (ClassNotFoundException var4) {
         var4.printStackTrace();
         return false;
      }

      if(var3) {
         var2 = true;
      }

      return var2;
   }

   private void sendNotification(Context var1, String var2, String var3) {
      Log.i("SmsBroadcastReceiver", "sendingNotification " + var2 + ":" + var3);
      String var4 = var1.getPackageName();
      Log.i("SmsBroadcastReceiver", "Package name : " + var4);

      ClassNotFoundException var10;
      label19: {
         Intent var7;
         try {
            var4 = var4 + ".Screen1";
            var7 = new Intent(var1, Class.forName(var4));
         } catch (ClassNotFoundException var9) {
            var10 = var9;
            break label19;
         }

         try {
            var7.setAction("android.intent.action.MAIN");
            var7.addCategory("android.intent.category.LAUNCHER");
            var7.addFlags(805306368);
            NotificationManager var5 = (NotificationManager)var1.getSystemService("notification");
            Notification var6 = new Notification(17301648, var2 + " : " + var3, System.currentTimeMillis());
            var6.flags |= 16;
            var6.defaults |= 1;
            PendingIntent var11 = PendingIntent.getActivity(var1, 0, var7, 134217728);
            var6.setLatestEventInfo(var1, "Sms from " + var2, var3, var11);
            var6.number = Texting.getCachedMsgCount();
            var5.notify((String)null, 8647, var6);
            Log.i("SmsBroadcastReceiver", "Notification sent, classname: " + var4);
            return;
         } catch (ClassNotFoundException var8) {
            var10 = var8;
         }
      }

      var10.printStackTrace();
   }

   public void onReceive(Context var1, Intent var2) {
      Log.i("SmsBroadcastReceiver", "onReceive");
      String var3 = this.getPhoneNumber(var2);
      String var5 = this.getMessage(var2);
      Log.i("SmsBroadcastReceiver", "Received " + var3 + " : " + var5);
      int var4 = Texting.isReceivingEnabled(var1);
      if(var4 == 1) {
         Log.i("SmsBroadcastReceiver", var1.getApplicationInfo().packageName + " Receiving is not enabled, ignoring message.");
      } else if((var4 == 2 || this.isRepl(var1)) && !Texting.isRunning()) {
         Log.i("SmsBroadcastReceiver", var1.getApplicationInfo().packageName + " Texting isn\'t running, and either receivingEnabled is FOREGROUND or we are the repl.");
      } else {
         Texting.handledReceivedMessage(var1, var3, var5);
         if(Texting.isRunning()) {
            Log.i("SmsBroadcastReceiver", var1.getApplicationInfo().packageName + " App in Foreground, delivering message.");
         } else {
            Log.i("SmsBroadcastReceiver", var1.getApplicationInfo().packageName + " Texting isn\'t running, but receivingEnabled == 2, sending notification.");
            this.sendNotification(var1, var3, var5);
         }
      }
   }
}
