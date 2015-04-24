package org.acra.collector;

import android.content.Context;
import java.lang.reflect.Field;

final class SettingsCollector {

   public static String collectSecureSettings(Context param0) {
      // $FF: Couldn't be decompiled
   }

   public static String collectSystemSettings(Context param0) {
      // $FF: Couldn't be decompiled
   }

   private static boolean isAuthorized(Field var0) {
      return var0 != null && !var0.getName().startsWith("WIFI_AP");
   }
}
