package com.google.appinventor.components.runtime.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Parcelable;
import android.util.Log;
import com.google.appinventor.components.runtime.NearField;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookieStore;
import java.nio.charset.Charset;
import java.util.Locale;

public class GingerbreadUtil {

   public static boolean clearCookies(CookieHandler var0) {
      if(var0 instanceof CookieManager) {
         CookieStore var1 = ((CookieManager)var0).getCookieStore();
         if(var1 != null) {
            var1.removeAll();
            return true;
         }
      }

      return false;
   }

   public static NdefRecord createTextRecord(String var0, boolean var1) {
      byte[] var3 = Locale.getDefault().getLanguage().getBytes(Charset.forName("US-ASCII"));
      Charset var2;
      if(var1) {
         var2 = Charset.forName("UTF-8");
      } else {
         var2 = Charset.forName("UTF-16");
      }

      byte[] var5 = var0.getBytes(var2);
      short var4;
      if(var1) {
         var4 = 0;
      } else {
         var4 = 128;
      }

      char var7 = (char)(var3.length + var4);
      byte[] var6 = new byte[var3.length + 1 + var5.length];
      var6[0] = (byte)var7;
      System.arraycopy(var3, 0, var6, 1, var3.length);
      System.arraycopy(var5, 0, var6, var3.length + 1, var5.length);
      return new NdefRecord((short)1, NdefRecord.RTD_TEXT, new byte[0], var6);
   }

   public static void disableNFCAdapter(Activity var0, NfcAdapter var1) {
      var1.disableForegroundNdefPush(var0);
   }

   public static void enableNFCWriteMode(Activity var0, NfcAdapter var1, String var2) {
      var1.enableForegroundNdefPush(var0, new NdefMessage(new NdefRecord[]{createTextRecord(var2, true)}));
   }

   public static CookieHandler newCookieManager() {
      return new CookieManager();
   }

   public static NfcAdapter newNfcAdapter(Context var0) {
      return NfcAdapter.getDefaultAdapter(var0);
   }

   public static void resolveNFCIntent(Intent var0, NearField var1) {
      if(!"android.nfc.action.NDEF_DISCOVERED".equals(var0.getAction())) {
         Log.e("nearfield", "Unknown intent " + var0);
      } else {
         if(var1.ReadMode()) {
            Parcelable[] var3 = var0.getParcelableArrayExtra("android.nfc.extra.NDEF_MESSAGES");
            NdefMessage[] var5;
            if(var3 != null) {
               NdefMessage[] var2 = new NdefMessage[var3.length];
               int var4 = 0;

               while(true) {
                  var5 = var2;
                  if(var4 >= var3.length) {
                     break;
                  }

                  var2[var4] = (NdefMessage)var3[var4];
                  ++var4;
               }
            } else {
               byte[] var6 = new byte[0];
               NdefMessage var8 = new NdefMessage(new NdefRecord[]{new NdefRecord((short)5, var6, var6, var6)});
               var5 = new NdefMessage[]{var8};
            }

            var1.TagRead((new String(var5[0].getRecords()[0].getPayload())).substring(3));
         } else {
            Tag var9 = (Tag)var0.getParcelableExtra("android.nfc.extra.TAG");
            NdefMessage var7 = null;
            if(var1.WriteType() == 1) {
               var7 = new NdefMessage(new NdefRecord[]{createTextRecord(var1.TextToWrite(), true)});
            }

            if(writeNFCTag(var7, var9)) {
               var1.TagWritten();
               return;
            }
         }

      }
   }

   public static boolean writeNFCTag(NdefMessage param0, Tag param1) {
      // $FF: Couldn't be decompiled
   }
}
