package com.google.appinventor.components.runtime.util;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

public final class ViewUtil {

   public static void setBackgroundDrawable(View var0, Drawable var1) {
      var0.setBackgroundDrawable(var1);
      var0.invalidate();
   }

   public static void setBackgroundImage(View var0, Drawable var1) {
      var0.setBackgroundDrawable(var1);
      var0.requestLayout();
   }

   public static void setChildHeightForHorizontalLayout(View var0, int var1) {
      LayoutParams var2 = var0.getLayoutParams();
      if(var2 instanceof android.widget.LinearLayout.LayoutParams) {
         android.widget.LinearLayout.LayoutParams var3 = (android.widget.LinearLayout.LayoutParams)var2;
         switch(var1) {
         case -2:
            var3.height = -1;
            break;
         case -1:
            var3.height = -2;
            break;
         default:
            var3.height = var1;
         }

         var0.requestLayout();
      } else {
         Log.e("ViewUtil", "The view does not have linear layout parameters");
      }
   }

   public static void setChildHeightForTableLayout(View var0, int var1) {
      LayoutParams var2 = var0.getLayoutParams();
      if(var2 instanceof android.widget.TableRow.LayoutParams) {
         android.widget.TableRow.LayoutParams var3 = (android.widget.TableRow.LayoutParams)var2;
         switch(var1) {
         case -2:
            var3.height = -1;
            break;
         case -1:
            var3.height = -2;
            break;
         default:
            var3.height = var1;
         }

         var0.requestLayout();
      } else {
         Log.e("ViewUtil", "The view does not have table layout parameters");
      }
   }

   public static void setChildHeightForVerticalLayout(View var0, int var1) {
      LayoutParams var2 = var0.getLayoutParams();
      if(var2 instanceof android.widget.LinearLayout.LayoutParams) {
         android.widget.LinearLayout.LayoutParams var3 = (android.widget.LinearLayout.LayoutParams)var2;
         switch(var1) {
         case -2:
            var3.height = 0;
            var3.weight = 1.0F;
            break;
         case -1:
            var3.height = -2;
            var3.weight = 0.0F;
            break;
         default:
            var3.height = var1;
            var3.weight = 0.0F;
         }

         var0.requestLayout();
      } else {
         Log.e("ViewUtil", "The view does not have linear layout parameters");
      }
   }

   public static void setChildWidthForHorizontalLayout(View var0, int var1) {
      LayoutParams var2 = var0.getLayoutParams();
      if(var2 instanceof android.widget.LinearLayout.LayoutParams) {
         android.widget.LinearLayout.LayoutParams var3 = (android.widget.LinearLayout.LayoutParams)var2;
         switch(var1) {
         case -2:
            var3.width = 0;
            var3.weight = 1.0F;
            break;
         case -1:
            var3.width = -2;
            var3.weight = 0.0F;
            break;
         default:
            var3.width = var1;
            var3.weight = 0.0F;
         }

         var0.requestLayout();
      } else {
         Log.e("ViewUtil", "The view does not have linear layout parameters");
      }
   }

   public static void setChildWidthForTableLayout(View var0, int var1) {
      LayoutParams var2 = var0.getLayoutParams();
      if(var2 instanceof android.widget.TableRow.LayoutParams) {
         android.widget.TableRow.LayoutParams var3 = (android.widget.TableRow.LayoutParams)var2;
         switch(var1) {
         case -2:
            var3.width = -1;
            break;
         case -1:
            var3.width = -2;
            break;
         default:
            var3.width = var1;
         }

         var0.requestLayout();
      } else {
         Log.e("ViewUtil", "The view does not have table layout parameters");
      }
   }

   public static void setChildWidthForVerticalLayout(View var0, int var1) {
      LayoutParams var2 = var0.getLayoutParams();
      if(var2 instanceof android.widget.LinearLayout.LayoutParams) {
         android.widget.LinearLayout.LayoutParams var3 = (android.widget.LinearLayout.LayoutParams)var2;
         switch(var1) {
         case -2:
            var3.width = -1;
            break;
         case -1:
            var3.width = -2;
            break;
         default:
            var3.width = var1;
         }

         var0.requestLayout();
      } else {
         Log.e("ViewUtil", "The view does not have linear layout parameters");
      }
   }

   public static void setImage(ImageView var0, Drawable var1) {
      var0.setImageDrawable(var1);
      if(var1 != null) {
         var0.setAdjustViewBounds(true);
      }

      var0.requestLayout();
   }
}
