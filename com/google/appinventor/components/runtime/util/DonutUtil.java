package com.google.appinventor.components.runtime.util;

import android.graphics.Bitmap;
import android.view.View;

public class DonutUtil {

   public static void buildDrawingCache(View var0, boolean var1) {
      var0.buildDrawingCache(var1);
   }

   public static Bitmap getDrawingCache(View var0, boolean var1) {
      return var0.getDrawingCache(var1);
   }
}
