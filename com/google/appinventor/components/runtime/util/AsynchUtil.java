package com.google.appinventor.components.runtime.util;

import android.os.Handler;

public class AsynchUtil {

   public static void runAsynchronously(final Handler var0, final Runnable var1, final Runnable var2) {
      (new Thread(new Runnable() {
         public void run() {
            var1.run();
            if(var2 != null) {
               var0.post(new Runnable() {
                  public void run() {
                     var2.run();
                  }
               });
            }

         }
      })).start();
   }

   public static void runAsynchronously(Runnable var0) {
      (new Thread(var0)).start();
   }
}
