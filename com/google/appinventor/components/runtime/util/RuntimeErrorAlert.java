package com.google.appinventor.components.runtime.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;

public final class RuntimeErrorAlert {

   public static void alert(final Object var0, String var1, String var2, String var3) {
      Log.i("RuntimeErrorAlert", "in alert");
      AlertDialog var4 = (new Builder((Context)var0)).create();
      var4.setTitle(var2);
      var4.setMessage(var1);
      var4.setButton(var3, new OnClickListener() {
         public void onClick(DialogInterface var1, int var2) {
            ((Activity)var0).finish();
         }
      });
      if(var1 == null) {
         Log.e(RuntimeErrorAlert.class.getName(), "No error message available");
      } else {
         Log.e(RuntimeErrorAlert.class.getName(), var1);
      }

      var4.show();
   }
}
