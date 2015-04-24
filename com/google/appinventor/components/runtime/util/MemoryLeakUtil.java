package com.google.appinventor.components.runtime.util;

import android.util.Log;
import com.google.appinventor.components.runtime.collect.Maps;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryLeakUtil {

   private static final String LOG_TAG = "MemoryLeakUtil";
   private static final Map TRACKED_OBJECTS = Maps.newTreeMap();
   private static final AtomicInteger prefixGenerator = new AtomicInteger(0);


   public static void checkAllTrackedObjects(boolean var0, boolean var1) {
      Log.i("MemoryLeakUtil", "Checking Tracked Objects ----------------------------------------");
      System.gc();
      int var5 = 0;
      int var6 = 0;
      Iterator var3 = TRACKED_OBJECTS.entrySet().iterator();

      while(var3.hasNext()) {
         Entry var2 = (Entry)var3.next();
         String var4 = (String)var2.getKey();
         Object var9 = ((WeakReference)var2.getValue()).get();
         int var7;
         int var8;
         if(var9 != null) {
            var8 = var5 + 1;
            var7 = var6;
         } else {
            ++var6;
            var7 = var6;
            var8 = var5;
            if(var1) {
               var3.remove();
               var7 = var6;
               var8 = var5;
            }
         }

         var6 = var7;
         var5 = var8;
         if(var0) {
            var4 = var4.substring(var4.indexOf("_") + 1);
            StringBuilder var11 = (new StringBuilder()).append("Object with tag ").append(var4).append(" has ");
            String var10;
            if(var9 != null) {
               var10 = "not ";
            } else {
               var10 = "";
            }

            Log.i("MemoryLeakUtil", var11.append(var10).append("been garbage collected.").toString());
            var6 = var7;
            var5 = var8;
         }
      }

      Log.i("MemoryLeakUtil", "summary: collected " + var6);
      Log.i("MemoryLeakUtil", "summary: remaining " + var5);
      Log.i("MemoryLeakUtil", "-----------------------------------------------------------------");
   }

   public static boolean isTrackedObjectCollected(String var0, boolean var1) {
      System.gc();
      WeakReference var2 = (WeakReference)TRACKED_OBJECTS.get(var0);
      if(var2 != null) {
         Object var3 = var2.get();
         String var5 = var0.substring(var0.indexOf("_") + 1);
         StringBuilder var4 = (new StringBuilder()).append("Object with tag ").append(var5).append(" has ");
         if(var3 != null) {
            var5 = "not ";
         } else {
            var5 = "";
         }

         Log.i("MemoryLeakUtil", var4.append(var5).append("been garbage collected.").toString());
         if(var1 && var3 == null) {
            TRACKED_OBJECTS.remove(var0);
         }

         return var3 == null;
      } else {
         throw new IllegalArgumentException("key not found");
      }
   }

   public static String trackObject(String var0, Object var1) {
      if(var0 == null) {
         var0 = prefixGenerator.incrementAndGet() + "_";
      } else {
         var0 = prefixGenerator.incrementAndGet() + "_" + var0;
      }

      TRACKED_OBJECTS.put(var0, new WeakReference(var1));
      return var0;
   }
}
