package com.google.appinventor.components.runtime.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class PhoneCallUtil {

   public static void makePhoneCall(Context var0, String var1) {
      if(var1 != null && var1.length() > 0) {
         var0.startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + var1)));
      }

   }
}
