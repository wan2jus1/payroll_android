package com.google.appinventor.components.runtime.util;

import android.app.Activity;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.net.http.SslError;
import android.view.Display;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.Player;

public class FroyoUtil {

   public static void abandonFocus(AudioManager var0, Object var1) {
      var0.abandonAudioFocus((OnAudioFocusChangeListener)var1);
   }

   public static boolean focusRequestGranted(AudioManager var0, Object var1) {
      return var0.requestAudioFocus((OnAudioFocusChangeListener)var1, 3, 1) == 1;
   }

   public static int getRotation(Display var0) {
      return var0.getRotation();
   }

   public static WebViewClient getWebViewClient(final boolean var0, final boolean var1, final Form var2, final Component var3) {
      return new WebViewClient() {
         public void onReceivedSslError(WebView var1x, SslErrorHandler var2x, SslError var3x) {
            if(var0) {
               var2x.proceed();
            } else {
               var2x.cancel();
               var2.dispatchErrorOccurredEvent(var3, "WebView", 2501, new Object[0]);
            }
         }
         public boolean shouldOverrideUrlLoading(WebView var1x, String var2x) {
            return !var1;
         }
      };
   }

   public static Object setAudioFocusChangeListener(final Player var0) {
      return new OnAudioFocusChangeListener() {

         private boolean playbackFlag = false;

         public void onAudioFocusChange(int var1) {
            switch(var1) {
            case -3:
            case -2:
               if(var0 != null && var0.playerState == 2) {
                  var0.pause();
                  this.playbackFlag = true;
                  return;
               }
               break;
            case -1:
               this.playbackFlag = false;
               var0.OtherPlayerStarted();
               return;
            case 0:
            default:
               break;
            case 1:
               if(var0 != null && this.playbackFlag && var0.playerState == 4) {
                  var0.Start();
                  this.playbackFlag = false;
                  return;
               }
            }

         }
      };
   }

   public static AudioManager setAudioManager(Activity var0) {
      return (AudioManager)var0.getSystemService("audio");
   }
}
