package org.acra.collector;

import android.content.Context;
import android.os.Build.VERSION;
import java.lang.reflect.Field;

class Compatibility {

   public static int getAPILevel() {
      try {
         int var1 = VERSION.class.getField("SDK_INT").getInt((Object)null);
         return var1;
      } catch (SecurityException var2) {
         return Integer.parseInt(VERSION.SDK);
      } catch (NoSuchFieldException var3) {
         return Integer.parseInt(VERSION.SDK);
      } catch (IllegalArgumentException var4) {
         return Integer.parseInt(VERSION.SDK);
      } catch (IllegalAccessException var5) {
         return Integer.parseInt(VERSION.SDK);
      }
   }

   public static String getDropBoxServiceName() throws NoSuchFieldException, IllegalAccessException {
      String var0 = null;
      Field var1 = Context.class.getField("DROPBOX_SERVICE");
      if(var1 != null) {
         var0 = (String)var1.get((Object)null);
      }

      return var0;
   }
}
