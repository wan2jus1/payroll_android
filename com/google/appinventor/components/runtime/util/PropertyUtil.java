package com.google.appinventor.components.runtime.util;

import com.google.appinventor.components.annotations.SimplePropertyCopier;
import com.google.appinventor.components.runtime.Component;
import java.lang.reflect.Method;

public class PropertyUtil {

   public static Component copyComponentProperties(Component param0, Component param1) throws Throwable {
      // $FF: Couldn't be decompiled
   }

   private static Method getPropertyCopierMethod(String var0, Class var1) {
      Class var5;
      do {
         label17: {
            Method var2;
            boolean var3;
            try {
               var2 = var1.getMethod(var0, new Class[]{var1});
               var3 = var2.isAnnotationPresent(SimplePropertyCopier.class);
            } catch (NoSuchMethodException var4) {
               break label17;
            }

            if(var3) {
               return var2;
            }
         }

         var5 = var1.getSuperclass();
         var1 = var5;
      } while(var5 != null);

      return null;
   }
}
