package org.acra.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import org.acra.ACRA;

public final class ToastSender {

   public static void sendToast(Context var0, int var1, int var2) {
      try {
         Toast.makeText(var0, var1, var2).show();
      } catch (RuntimeException var3) {
         Log.e(ACRA.LOG_TAG, "Could not send crash Toast", var3);
      }
   }
}
