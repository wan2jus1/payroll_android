package com.google.appinventor.components.runtime.util;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import com.google.appinventor.components.runtime.util.EclairUtil;
import com.google.appinventor.components.runtime.util.SdkLevel;

public final class AnimationUtil {

   public static void ApplyAnimation(View var0, String var1) {
      if(var1.equals("ScrollRightSlow")) {
         ApplyHorizontalScrollAnimation(var0, false, 8000);
      } else {
         if(var1.equals("ScrollRight")) {
            ApplyHorizontalScrollAnimation(var0, false, 4000);
            return;
         }

         if(var1.equals("ScrollRightFast")) {
            ApplyHorizontalScrollAnimation(var0, false, 1000);
            return;
         }

         if(var1.equals("ScrollLeftSlow")) {
            ApplyHorizontalScrollAnimation(var0, true, 8000);
            return;
         }

         if(var1.equals("ScrollLeft")) {
            ApplyHorizontalScrollAnimation(var0, true, 4000);
            return;
         }

         if(var1.equals("ScrollLeftFast")) {
            ApplyHorizontalScrollAnimation(var0, true, 1000);
            return;
         }

         if(var1.equals("Stop")) {
            var0.clearAnimation();
            return;
         }
      }

   }

   public static void ApplyCloseScreenAnimation(Activity var0, String var1) {
      if(var1 != null) {
         if(SdkLevel.getLevel() <= 4) {
            Log.e("AnimationUtil", "Screen animations are not available on android versions less than 2.0.");
         } else {
            int var2 = 0;
            int var3 = 0;
            if(var1.equalsIgnoreCase("fade")) {
               var3 = var0.getResources().getIdentifier("fadeout", "anim", var0.getPackageName());
               var2 = var0.getResources().getIdentifier("hold", "anim", var0.getPackageName());
            } else if(var1.equalsIgnoreCase("zoom")) {
               var3 = var0.getResources().getIdentifier("zoom_exit_reverse", "anim", var0.getPackageName());
               var2 = var0.getResources().getIdentifier("zoom_enter_reverse", "anim", var0.getPackageName());
            } else if(var1.equalsIgnoreCase("slidehorizontal")) {
               var3 = var0.getResources().getIdentifier("slide_exit_reverse", "anim", var0.getPackageName());
               var2 = var0.getResources().getIdentifier("slide_enter_reverse", "anim", var0.getPackageName());
            } else if(var1.equalsIgnoreCase("slidevertical")) {
               var3 = var0.getResources().getIdentifier("slide_v_exit_reverse", "anim", var0.getPackageName());
               var2 = var0.getResources().getIdentifier("slide_v_enter_reverse", "anim", var0.getPackageName());
            } else if(!var1.equalsIgnoreCase("none")) {
               return;
            }

            EclairUtil.overridePendingTransitions(var0, var2, var3);
         }
      }
   }

   private static void ApplyHorizontalScrollAnimation(View var0, boolean var1, int var2) {
      float var3;
      if(var1) {
         var3 = 1.0F;
      } else {
         var3 = -1.0F;
      }

      AnimationSet var4 = new AnimationSet(true);
      var4.setRepeatCount(-1);
      var4.setRepeatMode(1);
      TranslateAnimation var5 = new TranslateAnimation(2, 0.7F * var3, 2, var3 * -0.7F, 2, 0.0F, 2, 0.0F);
      var5.setStartOffset(0L);
      var5.setDuration((long)var2);
      var5.setFillAfter(true);
      var4.addAnimation(var5);
      var0.startAnimation(var4);
   }

   public static void ApplyOpenScreenAnimation(Activity var0, String var1) {
      if(var1 != null) {
         if(SdkLevel.getLevel() <= 4) {
            Log.e("AnimationUtil", "Screen animations are not available on android versions less than 2.0.");
         } else {
            int var2 = 0;
            int var3 = 0;
            if(var1.equalsIgnoreCase("fade")) {
               var2 = var0.getResources().getIdentifier("fadein", "anim", var0.getPackageName());
               var3 = var0.getResources().getIdentifier("hold", "anim", var0.getPackageName());
            } else if(var1.equalsIgnoreCase("zoom")) {
               var3 = var0.getResources().getIdentifier("zoom_exit", "anim", var0.getPackageName());
               var2 = var0.getResources().getIdentifier("zoom_enter", "anim", var0.getPackageName());
            } else if(var1.equalsIgnoreCase("slidehorizontal")) {
               var3 = var0.getResources().getIdentifier("slide_exit", "anim", var0.getPackageName());
               var2 = var0.getResources().getIdentifier("slide_enter", "anim", var0.getPackageName());
            } else if(var1.equalsIgnoreCase("slidevertical")) {
               var3 = var0.getResources().getIdentifier("slide_v_exit", "anim", var0.getPackageName());
               var2 = var0.getResources().getIdentifier("slide_v_enter", "anim", var0.getPackageName());
            } else if(!var1.equalsIgnoreCase("none")) {
               return;
            }

            EclairUtil.overridePendingTransitions(var0, var2, var3);
         }
      }
   }
}
