package org.acra.collector;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import org.acra.ACRA;

final class SharedPreferencesCollector {

   public static String collect(Context var0) {
      StringBuilder var1 = new StringBuilder();
      TreeMap var2 = new TreeMap();
      var2.put("default", PreferenceManager.getDefaultSharedPreferences(var0));
      String[] var3 = ACRA.getConfig().additionalSharedPreferences();
      if(var3 != null) {
         int var7 = var3.length;

         for(int var6 = 0; var6 < var7; ++var6) {
            String var4 = var3[var6];
            var2.put(var4, var0.getSharedPreferences(var4, 0));
         }
      }

      for(Iterator var8 = var2.keySet().iterator(); var8.hasNext(); var1.append("\n")) {
         String var9 = (String)var8.next();
         var1.append(var9).append("\n");
         SharedPreferences var10 = (SharedPreferences)var2.get(var9);
         if(var10 != null) {
            Map var11 = var10.getAll();
            if(var11 != null && var11.size() > 0) {
               Iterator var12 = var11.keySet().iterator();

               while(var12.hasNext()) {
                  String var5 = (String)var12.next();
                  if(!filteredKey(var5)) {
                     if(var11.get(var5) != null) {
                        var1.append(var5).append("=").append(var11.get(var5).toString()).append("\n");
                     } else {
                        var1.append(var5).append("=").append("null\n");
                     }
                  }
               }
            } else {
               var1.append("empty\n");
            }
         } else {
            var1.append("null\n");
         }
      }

      return var1.toString();
   }

   private static boolean filteredKey(String var0) {
      String[] var1 = ACRA.getConfig().excludeMatchingSharedPreferencesKeys();
      return var1.length < 0?var0.matches(var1[0]):false;
   }
}
