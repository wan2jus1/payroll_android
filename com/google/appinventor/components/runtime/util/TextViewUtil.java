package com.google.appinventor.components.runtime.util;

import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.widget.TextView;

public class TextViewUtil {

   public static float getFontSize(TextView var0) {
      return var0.getTextSize();
   }

   public static String getText(TextView var0) {
      return var0.getText().toString();
   }

   public static boolean isEnabled(TextView var0) {
      return var0.isEnabled();
   }

   public static void setAlignment(TextView var0, int var1, boolean var2) {
      byte var4;
      switch(var1) {
      case 0:
         var4 = 3;
         break;
      case 1:
         var4 = 1;
         break;
      case 2:
         var4 = 5;
         break;
      default:
         throw new IllegalArgumentException();
      }

      byte var3;
      if(var2) {
         var3 = 16;
      } else {
         var3 = 48;
      }

      var0.setGravity(var4 | var3);
      var0.invalidate();
   }

   public static void setBackgroundColor(TextView var0, int var1) {
      var0.setBackgroundColor(var1);
      var0.invalidate();
   }

   public static void setEnabled(TextView var0, boolean var1) {
      var0.setEnabled(var1);
      var0.invalidate();
   }

   public static void setFontSize(TextView var0, float var1) {
      var0.setTextSize(var1);
      var0.requestLayout();
   }

   public static void setFontTypeface(TextView var0, int var1, boolean var2, boolean var3) {
      Typeface var4;
      switch(var1) {
      case 0:
         var4 = Typeface.DEFAULT;
         break;
      case 1:
         var4 = Typeface.SANS_SERIF;
         break;
      case 2:
         var4 = Typeface.SERIF;
         break;
      case 3:
         var4 = Typeface.MONOSPACE;
         break;
      default:
         throw new IllegalArgumentException();
      }

      var1 = 0;
      if(var2) {
         var1 = 0 | 1;
      }

      int var5 = var1;
      if(var3) {
         var5 = var1 | 2;
      }

      var0.setTypeface(Typeface.create(var4, var5));
      var0.requestLayout();
   }

   public static void setPadding(TextView var0, int var1) {
      var0.setPadding(var1, var1, 0, 0);
      var0.requestLayout();
   }

   public static void setText(TextView var0, String var1) {
      var0.setText(var1);
      var0.requestLayout();
   }

   public static void setTextColor(TextView var0, int var1) {
      var0.setTextColor(var1);
      var0.invalidate();
   }

   public static void setTextColors(TextView var0, ColorStateList var1) {
      var0.setTextColor(var1);
   }
}
