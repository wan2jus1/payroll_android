package com.google.appinventor.components.runtime.util;

import com.google.appinventor.components.runtime.errors.YailRuntimeError;
import com.google.appinventor.components.runtime.util.YailList;

public class ElementsUtil {

   public static YailList elements(YailList var0, String var1) {
      String[] var2 = var0.toStringArray();

      for(int var3 = 0; var3 < var2.length; ++var3) {
         if(!(var2[var3] instanceof String)) {
            throw new YailRuntimeError("Items passed to " + var1 + " must be Strings", "Error");
         }
      }

      return var0;
   }

   public static YailList elementsFromString(String var0) {
      YailList var1 = new YailList();
      if(var0.length() > 0) {
         var1 = YailList.makeList((Object[])((Object[])var0.split(" *, *")));
      }

      return var1;
   }

   public static int selectionIndex(int var0, YailList var1) {
      int var2;
      if(var0 > 0) {
         var2 = var0;
         if(var0 <= var1.size()) {
            return var2;
         }
      }

      var2 = 0;
      return var2;
   }

   public static int setSelectedIndexFromValue(String var0, YailList var1) {
      for(int var2 = 0; var2 < var1.size(); ++var2) {
         if(var1.getString(var2).equals(var0)) {
            return var2 + 1;
         }
      }

      return 0;
   }

   public static String setSelectionFromIndex(int var0, YailList var1) {
      return var0 == 0?"":var1.getString(var0 - 1);
   }
}
