package com.google.appinventor.components.runtime.util;

import com.google.appinventor.components.runtime.util.YailList;
import gnu.lists.FString;
import gnu.math.IntFraction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JsonUtil {

   public static Object convertJsonItem(Object var0) throws JSONException {
      Object var1;
      if(var0 == null) {
         var1 = "null";
         return var1;
      } else if(var0 instanceof JSONObject) {
         return getListFromJsonObject((JSONObject)var0);
      } else if(var0 instanceof JSONArray) {
         return getListFromJsonArray((JSONArray)var0);
      } else if(!var0.equals(Boolean.FALSE) && (!(var0 instanceof String) || !((String)var0).equalsIgnoreCase("false"))) {
         if(!var0.equals(Boolean.TRUE) && (!(var0 instanceof String) || !((String)var0).equalsIgnoreCase("true"))) {
            var1 = var0;
            if(!(var0 instanceof Number)) {
               return var0.toString();
            } else {
               return var1;
            }
         } else {
            return Boolean.valueOf(true);
         }
      } else {
         return Boolean.valueOf(false);
      }
   }

   public static String getJsonRepresentation(Object var0) throws JSONException {
      if(var0 != null && !var0.equals((Object)null)) {
         if(var0 instanceof FString) {
            return JSONObject.quote(var0.toString());
         } else if(var0 instanceof YailList) {
            return ((YailList)var0).toJSONString();
         } else if(var0 instanceof IntFraction) {
            return JSONObject.numberToString(Double.valueOf(((IntFraction)var0).doubleValue()));
         } else if(var0 instanceof Number) {
            return JSONObject.numberToString((Number)var0);
         } else if(var0 instanceof Boolean) {
            return var0.toString();
         } else if(!var0.getClass().isArray()) {
            return JSONObject.quote(var0.toString());
         } else {
            StringBuilder var2 = new StringBuilder();
            var2.append("[");
            String var1 = "";
            Object[] var3 = (Object[])((Object[])var0);
            int var5 = var3.length;
            int var4 = 0;

            for(String var6 = var1; var4 < var5; ++var4) {
               Object var7 = var3[var4];
               var2.append(var6).append(getJsonRepresentation(var7));
               var6 = ",";
            }

            var2.append("]");
            return var2.toString();
         }
      } else {
         return "null";
      }
   }

   public static List getListFromJsonArray(JSONArray var0) throws JSONException {
      ArrayList var1 = new ArrayList();

      for(int var2 = 0; var2 < var0.length(); ++var2) {
         var1.add(convertJsonItem(var0.get(var2)));
      }

      return var1;
   }

   public static List getListFromJsonObject(JSONObject var0) throws JSONException {
      ArrayList var1 = new ArrayList();
      Iterator var2 = var0.keys();
      ArrayList var3 = new ArrayList();

      while(var2.hasNext()) {
         var3.add(var2.next());
      }

      Collections.sort(var3);
      var2 = var3.iterator();

      while(var2.hasNext()) {
         String var5 = (String)var2.next();
         ArrayList var4 = new ArrayList();
         var4.add(var5);
         var4.add(convertJsonItem(var0.get(var5)));
         var1.add(var4);
      }

      return var1;
   }

   public static Object getObjectFromJson(String var0) throws JSONException {
      Object var2;
      if(var0 != null && !var0.equals("")) {
         Object var1 = (new JSONTokener(var0)).nextValue();
         if(var1 == null || var1.equals((Object)null)) {
            return null;
         }

         var2 = var1;
         if(!(var1 instanceof String)) {
            var2 = var1;
            if(!(var1 instanceof Number)) {
               var2 = var1;
               if(!(var1 instanceof Boolean)) {
                  if(var1 instanceof JSONArray) {
                     return getListFromJsonArray((JSONArray)var1);
                  }

                  if(var1 instanceof JSONObject) {
                     return getListFromJsonObject((JSONObject)var1);
                  }

                  throw new JSONException("Invalid JSON string.");
               }
            }
         }
      } else {
         var2 = "";
      }

      return var2;
   }

   public static List getStringListFromJsonArray(JSONArray var0) throws JSONException {
      ArrayList var1 = new ArrayList();

      for(int var2 = 0; var2 < var0.length(); ++var2) {
         var1.add(var0.getString(var2));
      }

      return var1;
   }
}
