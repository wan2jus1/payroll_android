package com.google.appinventor.components.runtime.util;


public class OrientationSensorUtil {

   static float mod(float var0, float var1) {
      float var2 = var0 % var1;
      return var2 != 0.0F && Math.signum(var0) != Math.signum(var1)?var2 + var1:var2;
   }

   public static float normalizeAzimuth(float var0) {
      return mod(var0, 360.0F);
   }

   public static float normalizePitch(float var0) {
      return mod(var0 + 180.0F, 360.0F) - 180.0F;
   }

   public static float normalizeRoll(float var0) {
      var0 = Math.max(Math.min(var0, 180.0F), -180.0F);
      if(var0 < -90.0F || var0 > 90.0F) {
         float var1 = 180.0F - var0;
         var0 = var1;
         if(var1 >= 270.0F) {
            return var1 - 360.0F;
         }
      }

      return var0;
   }
}
