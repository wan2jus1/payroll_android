package com.google.appinventor.components.runtime.util;

import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.graphics.PorterDuff.Mode;

public class PaintUtil {

   public static void changePaint(Paint var0, int var1) {
      var0.setColor(16777215 & var1);
      var0.setAlpha(var1 >> 24 & 255);
      var0.setXfermode((Xfermode)null);
   }

   public static void changePaintTransparent(Paint var0) {
      var0.setAlpha(0);
      var0.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
   }
}
