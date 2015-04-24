package org.acra.collector;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

final class ReflectionCollector {

   public static String collectConstants(Class var0) {
      StringBuilder var1 = new StringBuilder();
      Field[] var7 = var0.getFields();
      int var4 = var7.length;

      for(int var3 = 0; var3 < var4; ++var3) {
         Field var2 = var7[var3];
         var1.append(var2.getName()).append("=");

         try {
            var1.append(var2.get((Object)null).toString());
         } catch (IllegalArgumentException var5) {
            var1.append("N/A");
         } catch (IllegalAccessException var6) {
            var1.append("N/A");
         }

         var1.append("\n");
      }

      return var1.toString();
   }

   public static String collectStaticGettersResults(Class var0) {
      StringBuilder var1 = new StringBuilder();
      Method[] var8 = var0.getMethods();
      int var4 = var8.length;

      for(int var3 = 0; var3 < var4; ++var3) {
         Method var2 = var8[var3];
         if(var2.getParameterTypes().length == 0 && (var2.getName().startsWith("get") || var2.getName().startsWith("is")) && !var2.getName().equals("getClass")) {
            try {
               var1.append(var2.getName());
               var1.append('=');
               var1.append(var2.invoke((Object)null, (Object[])null));
               var1.append("\n");
            } catch (IllegalArgumentException var5) {
               ;
            } catch (IllegalAccessException var6) {
               ;
            } catch (InvocationTargetException var7) {
               ;
            }
         }
      }

      return var1.toString();
   }
}
