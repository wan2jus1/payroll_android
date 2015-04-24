package com.google.appinventor.components.runtime;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesLibraries;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.OnPauseListener;
import com.google.appinventor.components.runtime.OnResumeListener;
import com.google.appinventor.components.runtime.OnStopListener;
import com.google.appinventor.components.runtime.util.OAuth2Helper;
import com.google.appinventor.components.runtime.util.OnInitializeListener;
import com.google.appinventor.components.runtime.util.SdkLevel;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@DesignerComponent(
   category = ComponentCategory.SOCIAL,
   description = "<p>A component that will, when the <code>SendMessage</code> method is called, send the text message specified in the <code>Message</code> property to the phone number specified in the <code>PhoneNumber</code> property.</p> <p>If the <code>ReceivingEnabled</code> property is set to 1 messages will <b>not</b> be received. If <code>ReceivingEnabled</code> is set to 2 messages will be received only when the application is running. Finally if <code>ReceivingEnabled</code> is set to 3, messages will be received when the application is running <b>and</b> when the application is not running they will be queued and a notification displayed to the user.</p> <p>When a message arrives, the <code>MessageReceived</code> event is raised and provides the sending number and message.</p> <p> An app that includes this component will receive messages even when it is in the background (i.e. when it\'s not visible on the screen) and, moreso, even if the app is not running, so long as it\'s installed on the phone. If the phone receives a text message when the app is not in the foreground, the phone will show a notification in the notification bar.  Selecting the notification will bring up the app.  As an app developer, you\'ll probably want to give your users the ability to control ReceivingEnabled so that they can make the phone ignore text messages.</p> <p>If the GoogleVoiceEnabled property is true, messages can be sent over Wifi using Google Voice. This option requires that the user have a Google Voice account and that the mobile Voice app is installed on the phone. The Google Voice option works only on phones that support Android 2.0 (Eclair) or higher.</p> <p>To specify the phone number (e.g., 650-555-1212), set the <code>PhoneNumber</code> property to a Text string with the specified digits (e.g., 6505551212).  Dashes, dots, and parentheses may be included (e.g., (650)-555-1212) but will be ignored; spaces may not be included.</p> <p>Another way for an app to specify a phone number would be to include a <code>PhoneNumberPicker</code> component, which lets the users select a phone numbers from the ones stored in the the phone\'s contacts.</p>",
   iconName = "images/texting.png",
   nonVisible = true,
   version = 3
)
@SimpleObject
@UsesLibraries(
   libraries = "google-api-client-beta.jar,google-api-client-android2-beta.jar,google-http-client-beta.jar,google-http-client-android2-beta.jar,google-http-client-android3-beta.jar,google-oauth-client-beta.jar,guava-14.0.1.jar"
)
@UsesPermissions(
   permissionNames = "android.permission.RECEIVE_SMS, android.permission.SEND_SMS, com.google.android.apps.googlevoice.permission.RECEIVE_SMS, com.google.android.apps.googlevoice.permission.SEND_SMS, android.permission.ACCOUNT_MANAGER, android.permission.MANAGE_ACCOUNTS, android.permission.GET_ACCOUNTS, android.permission.USE_CREDENTIALS"
)
public class Texting extends AndroidNonvisibleComponent implements Component, OnResumeListener, OnPauseListener, OnInitializeListener, OnStopListener {

   private static final String CACHE_FILE = "textingmsgcache";
   public static final String GV_INTENT_FILTER = "com.google.android.apps.googlevoice.SMS_RECEIVED";
   public static final String GV_PACKAGE_NAME = "com.google.android.apps.googlevoice";
   private static final String GV_SERVICE = "grandcentral";
   public static final String GV_SMS_RECEIVED = "com.google.android.apps.googlevoice.SMS_RECEIVED";
   public static final String GV_SMS_SEND_URL = "https://www.google.com/voice/b/0/sms/send/";
   public static final String GV_URL = "https://www.google.com/voice/b/0";
   private static final String MESSAGE_DELIMITER = "\u0001";
   public static final String MESSAGE_TAG = "com.google.android.apps.googlevoice.TEXT";
   public static final String META_DATA_SMS_KEY = "sms_handler_component";
   public static final String META_DATA_SMS_VALUE = "Texting";
   public static final String PHONE_NUMBER_TAG = "com.google.android.apps.googlevoice.PHONE_NUMBER";
   private static final String PREF_FILE = "TextingState";
   private static final String PREF_GVENABLED = "gvenabled";
   private static final String PREF_RCVENABLED = "receiving2";
   private static final String PREF_RCVENABLED_LEGACY = "receiving";
   private static final String SENT = "SMS_SENT";
   private static final int SERVER_TIMEOUT_MS = 30000;
   public static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
   public static final String TAG = "Texting Component";
   public static final String TELEPHONY_INTENT_FILTER = "android.provider.Telephony.SMS_RECEIVED";
   private static final String USER_AGENT = "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/525.13 (KHTML, like Gecko) Chrome/0.A.B.C Safari/525.13";
   private static final String UTF8 = "UTF-8";
   private static Activity activity;
   private static Object cacheLock = new Object();
   private static Component component;
   private static boolean isRunning;
   private static int messagesCached;
   private static int receivingEnabled = 2;
   private String authToken;
   private ComponentContainer container;
   private boolean googleVoiceEnabled;
   private Texting.GoogleVoiceUtil gvHelper;
   private boolean isInitialized;
   private String message;
   private Queue pendingQueue = new ConcurrentLinkedQueue();
   private String phoneNumber;
   private SmsManager smsManager;


   public Texting(ComponentContainer var1) {
      super(var1.$form());
      Log.d("Texting Component", "Texting constructor");
      this.container = var1;
      component = this;
      activity = var1.$context();
      SharedPreferences var2 = activity.getSharedPreferences("TextingState", 0);
      if(var2 != null) {
         receivingEnabled = var2.getInt("receiving2", -1);
         if(receivingEnabled == -1) {
            if(var2.getBoolean("receiving", true)) {
               receivingEnabled = 2;
            } else {
               receivingEnabled = 1;
            }
         }

         this.googleVoiceEnabled = var2.getBoolean("gvenabled", false);
         Log.i("Texting Component", "Starting with receiving Enabled=" + receivingEnabled + " GV enabled=" + this.googleVoiceEnabled);
      } else {
         receivingEnabled = 2;
         this.googleVoiceEnabled = false;
      }

      if(this.googleVoiceEnabled) {
         (new Texting.AsyncAuthenticate()).execute(new Void[0]);
      }

      this.smsManager = SmsManager.getDefault();
      this.PhoneNumber("");
      this.isInitialized = false;
      isRunning = false;
      var1.$form().registerForOnInitialize(this);
      var1.$form().registerForOnResume(this);
      var1.$form().registerForOnPause(this);
      var1.$form().registerForOnStop(this);
   }

   @SimpleEvent
   public static void MessageReceived(String param0, String param1) {
      // $FF: Couldn't be decompiled
   }

   // $FF: synthetic method
   static String access$300(Texting var0) {
      return var0.authToken;
   }

   // $FF: synthetic method
   static Texting.GoogleVoiceUtil access$500(Texting var0) {
      return var0.gvHelper;
   }

   // $FF: synthetic method
   static Texting.GoogleVoiceUtil access$502(Texting var0, Texting.GoogleVoiceUtil var1) {
      var0.gvHelper = var1;
      return var1;
   }

   private static void addMessageToCache(Context var0, String var1, String var2) {
      try {
         var1 = var1 + ":" + var2 + "\u0001";
         Log.i("Texting Component", "Caching " + var1);
         FileOutputStream var5 = var0.openFileOutput("textingmsgcache", '耀');
         var5.write(var1.getBytes());
         var5.close();
         ++messagesCached;
         Log.i("Texting Component", "Cached " + var1);
      } catch (FileNotFoundException var3) {
         Log.e("Texting Component", "File not found error writing to cache file");
         var3.printStackTrace();
      } catch (IOException var4) {
         Log.e("Texting Component", "I/O Error writing to cache file");
         var4.printStackTrace();
      }
   }

   public static int getCachedMsgCount() {
      return messagesCached;
   }

   public static SmsMessage[] getMessagesFromIntent(Intent var0) {
      Object[] var1 = (Object[])((Object[])var0.getSerializableExtra("pdus"));
      byte[][] var5 = new byte[var1.length][];

      int var3;
      for(var3 = 0; var3 < var1.length; ++var3) {
         var5[var3] = (byte[])((byte[])var1[var3]);
      }

      byte[][] var6 = new byte[var5.length][];
      int var4 = var6.length;
      SmsMessage[] var2 = new SmsMessage[var4];

      for(var3 = 0; var3 < var4; ++var3) {
         var6[var3] = var5[var3];
         var2[var3] = SmsMessage.createFromPdu(var6[var3]);
      }

      return var2;
   }

   private void handleSentMessage(Context param1, BroadcastReceiver param2, int param3, String param4) {
      // $FF: Couldn't be decompiled
   }

   public static void handledReceivedMessage(Context param0, String param1, String param2) {
      // $FF: Couldn't be decompiled
   }

   public static int isReceivingEnabled(Context var0) {
      SharedPreferences var3 = var0.getSharedPreferences("TextingState", 0);
      int var2 = var3.getInt("receiving2", -1);
      int var1 = var2;
      if(var2 == -1) {
         if(!var3.getBoolean("receiving", true)) {
            return 1;
         }

         var1 = 2;
      }

      return var1;
   }

   public static boolean isRunning() {
      return isRunning;
   }

   private void processCachedMessages() {
      // $FF: Couldn't be decompiled
   }

   private void processPendingQueue() {
      while(this.pendingQueue.size() != 0) {
         String var2 = (String)this.pendingQueue.remove();
         String var1 = var2.substring(0, var2.indexOf(":::"));
         var2 = var2.substring(var2.indexOf(":::") + 3);
         Log.i("Texting Component", "Sending queued message " + var1 + " " + var2);
         (new Texting.AsyncSendMessage()).execute(new String[]{var1, var2});
      }

   }

   private String[] retrieveCachedMessages() {
      // $FF: Couldn't be decompiled
   }

   private void sendViaSms() {
      Log.i("Texting Component", "Sending via built-in Sms");
      ArrayList var1 = this.smsManager.divideMessage(this.message);
      int var5 = var1.size();
      ArrayList var2 = new ArrayList();

      for(int var4 = 0; var4 < var5; ++var4) {
         var2.add(PendingIntent.getBroadcast(activity, 0, new Intent("SMS_SENT"), 0));
      }

      BroadcastReceiver var3 = new BroadcastReceiver() {
         public void onReceive(Context var1, Intent var2) {
            synchronized(this){}

            try {
               Texting.this.handleSentMessage(var1, (BroadcastReceiver)null, this.getResultCode(), Texting.this.message);
               Texting.activity.unregisterReceiver(this);
            } catch (Exception var5) {
               Log.e("BroadcastReceiver", "Error in onReceive for msgId " + var2.getAction());
               Log.e("BroadcastReceiver", var5.getMessage());
               var5.printStackTrace();
            } finally {
               ;
            }

         }
      };
      activity.registerReceiver(var3, new IntentFilter("SMS_SENT"));
      this.smsManager.sendMultipartTextMessage(this.phoneNumber, (String)null, var1, var2, (ArrayList)null);
   }

   @DesignerProperty(
      defaultValue = "False",
      editorType = "boolean"
   )
   @SimpleProperty
   public void GoogleVoiceEnabled(boolean var1) {
      if(SdkLevel.getLevel() >= 5) {
         this.googleVoiceEnabled = var1;
         Editor var2 = activity.getSharedPreferences("TextingState", 0).edit();
         var2.putBoolean("gvenabled", var1);
         var2.commit();
      } else {
         Toast.makeText(activity, "Sorry, your phone\'s system does not support this option.", 1).show();
      }
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "If true, then SendMessage will attempt to send messages over Wifi using Google Voice.  This requires that the Google Voice app must be installed and set up on the phone or tablet, with a Google Voice account.  If GoogleVoiceEnabled is false, the device must have phone and texting service in order to send or receive messages with this component."
   )
   public boolean GoogleVoiceEnabled() {
      return this.googleVoiceEnabled;
   }

   @SimpleProperty
   public String Message() {
      return this.message;
   }

   @DesignerProperty(
      defaultValue = "",
      editorType = "string"
   )
   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "The message that will be sent when the SendMessage method is called."
   )
   public void Message(String var1) {
      Log.i("Texting Component", "Message set: " + var1);
      this.message = var1;
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "The number that the message will be sent to when the SendMessage method is called. The number is a text string with the specified digits (e.g., 6505551212).  Dashes, dots, and parentheses may be included (e.g., (650)-555-1212) but will be ignored; spaces should not be included."
   )
   public String PhoneNumber() {
      return this.phoneNumber;
   }

   @DesignerProperty(
      defaultValue = "",
      editorType = "string"
   )
   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public void PhoneNumber(String var1) {
      Log.i("Texting Component", "PhoneNumber set: " + var1);
      this.phoneNumber = var1;
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "If set to 1 (OFF) no messages will be received.  If set to 2 (FOREGROUND) or3 (ALWAYS) the component will respond to messages if it is running. If the app is not running then the message will be discarded if set to 2 (FOREGROUND). If set to 3 (ALWAYS) and the app is not running the phone will show a notification.  Selecting the notification will bring up the app and signal the MessageReceived event.  Messages received when the app is dormant will be queued, and so several MessageReceived events might appear when the app awakens.  As an app developer, it would be a good idea to give your users control over this property, so they can make their phones ignore text messages when your app is installed."
   )
   public int ReceivingEnabled() {
      return receivingEnabled;
   }

   @DesignerProperty(
      defaultValue = "2",
      editorType = "text_receiving"
   )
   @SimpleProperty
   public void ReceivingEnabled(int var1) {
      if(var1 >= 1 && var1 <= 3) {
         receivingEnabled = var1;
         Editor var2 = activity.getSharedPreferences("TextingState", 0).edit();
         var2.putInt("receiving2", var1);
         var2.remove("receiving");
         var2.commit();
      } else {
         this.container.$form().dispatchErrorOccurredEvent(this, "Texting", 1701, new Object[]{Integer.valueOf(var1)});
      }
   }

   @SimpleFunction
   public void SendMessage() {
      Log.i("Texting Component", "Sending message " + this.message + " to " + this.phoneNumber);
      String var1 = this.phoneNumber;
      String var2 = this.message;
      if(this.googleVoiceEnabled) {
         if(this.authToken == null) {
            Log.i("Texting Component", "Need to get an authToken -- enqueing " + var1 + " " + var2);
            if(!this.pendingQueue.offer(var1 + ":::" + var2)) {
               Toast.makeText(activity, "Pending message queue full. Can\'t send message", 0).show();
            } else if(this.pendingQueue.size() == 1) {
               (new Texting.AsyncAuthenticate()).execute(new Void[0]);
               return;
            }

         } else {
            Log.i("Texting Component", "Creating AsyncSendMessage");
            (new Texting.AsyncSendMessage()).execute(new String[]{var1, var2});
         }
      } else {
         Log.i("Texting Component", "Sending via SMS");
         this.sendViaSms();
      }
   }

   public void onInitialize() {
      Log.i("Texting Component", "onInitialize()");
      this.isInitialized = true;
      isRunning = true;
      this.processCachedMessages();
      ((NotificationManager)activity.getSystemService("notification")).cancel(8647);
   }

   public void onPause() {
      Log.i("Texting Component", "onPause()");
      isRunning = false;
   }

   public void onResume() {
      Log.i("Texting Component", "onResume()");
      isRunning = true;
      if(this.isInitialized) {
         this.processCachedMessages();
         ((NotificationManager)activity.getSystemService("notification")).cancel(8647);
      }

   }

   public void onStop() {
      Editor var1 = activity.getSharedPreferences("TextingState", 0).edit();
      var1.putInt("receiving2", receivingEnabled);
      var1.putBoolean("gvenabled", this.googleVoiceEnabled);
      var1.commit();
   }

   class AsyncAuthenticate extends AsyncTask {

      protected String doInBackground(Void ... var1) {
         Log.i("Texting Component", "Authenticating");
         return (new OAuth2Helper()).getRefreshedAuthToken(Texting.activity, "grandcentral");
      }

      protected void onPostExecute(String var1) {
         Log.i("Texting Component", "authToken = " + var1);
         Texting.this.authToken = var1;
         Toast.makeText(Texting.activity, "Finished authentication", 0).show();
         Texting.this.processPendingQueue();
      }
   }

   class AsyncSendMessage extends AsyncTask {

      protected String doInBackground(String ... param1) {
         // $FF: Couldn't be decompiled
      }

      protected void onPostExecute(String param1) {
         // $FF: Couldn't be decompiled
      }
   }

   class GoogleVoiceUtil {

      private final int MAX_REDIRECTS = 5;
      String authToken;
      String general;
      private boolean isInitialized;
      int redirectCounter;
      String rnrSEE;


      public GoogleVoiceUtil(String var2) {
         Log.i("Texting Component", "Creating GV Util");
         this.authToken = var2;

         try {
            this.general = this.getGeneral();
            Log.i("Texting Component", "general = " + this.general);
            this.setRNRSEE();
            this.isInitialized = true;
         } catch (IOException var3) {
            var3.printStackTrace();
         }
      }

      // $FF: synthetic method
      static String access$600(Texting.GoogleVoiceUtil var0, String var1) {
         return var0.sendGvSms(var1);
      }

      private String sendGvSms(String param1) {
         // $FF: Couldn't be decompiled
      }

      private void setRNRSEE() throws IOException {
         Log.i("Texting Component", "setRNRSEE()");
         if(this.general != null) {
            if(this.general.contains("\'_rnr_se\': \'")) {
               this.rnrSEE = this.general.split("\'_rnr_se\': \'", 2)[1].split("\',", 2)[0];
               Log.i("Texting Component", "Successfully Received rnr_se.");
            } else {
               Log.i("Texting Component", "Answer did not contain rnr_se! " + this.general);
               throw new IOException("Answer did not contain rnr_se! " + this.general);
            }
         } else {
            Log.i("Texting Component", "setRNRSEE(): Answer was null!");
            throw new IOException("setRNRSEE(): Answer was null!");
         }
      }

      String get(String param1) throws IOException {
         // $FF: Couldn't be decompiled
      }

      public String getGeneral() throws IOException {
         Log.i("Texting Component", "getGeneral()");
         return this.get("https://www.google.com/voice/b/0");
      }

      public boolean isInitialized() {
         return this.isInitialized;
      }
   }
}
