package com.google.appinventor.components.runtime;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public final class WebViewActivity extends Activity {

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      WebView var5 = new WebView(this);
      var5.getSettings().setJavaScriptEnabled(true);
      var5.setWebViewClient(new WebViewClient() {
         public boolean shouldOverrideUrlLoading(WebView var1, String var2) {
            Log.i("WebView", "Handling url " + var2);
            Uri var3 = Uri.parse(var2);
            if(var3.getScheme().equals("appinventor")) {
               Intent var4 = new Intent();
               var4.setData(var3);
               WebViewActivity.this.setResult(-1, var4);
               WebViewActivity.this.finish();
            } else {
               var1.loadUrl(var2);
            }

            return true;
         }
      });
      this.setContentView(var5);
      Intent var2 = this.getIntent();
      if(var2 != null && var2.getData() != null) {
         Uri var6 = var2.getData();
         String var3 = var6.getScheme();
         String var4 = var6.getHost();
         Log.i("WebView", "Got intent with URI: " + var6 + ", scheme=" + var3 + ", host=" + var4);
         var5.loadUrl(var6.toString());
      }

   }
}
