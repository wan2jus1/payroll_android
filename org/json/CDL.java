package org.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class CDL {

   private static String getValue(JSONTokener var0) throws JSONException {
      char var1;
      do {
         var1 = var0.next();
      } while(var1 == 32 || var1 == 9);

      switch(var1) {
      case 0:
         return null;
      case 34:
      case 39:
         StringBuffer var3 = new StringBuffer();

         while(true) {
            char var2 = var0.next();
            if(var2 == var1) {
               return var3.toString();
            }

            if(var2 == 0 || var2 == 10 || var2 == 13) {
               throw var0.syntaxError("Missing close quote \'" + var1 + "\'.");
            }

            var3.append(var2);
         }
      case 44:
         var0.back();
         return "";
      default:
         var0.back();
         return var0.nextTo(',');
      }
   }

   public static JSONArray rowToJSONArray(JSONTokener var0) throws JSONException {
      JSONArray var3 = new JSONArray();

      label36:
      while(true) {
         String var2 = getValue(var0);
         char var1 = var0.next();
         JSONArray var4;
         if(var2 != null && (var3.length() != 0 || var2.length() != 0 || var1 == 44)) {
            var3.put((Object)var2);

            while(true) {
               if(var1 == 44) {
                  continue label36;
               }

               if(var1 != 32) {
                  var4 = var3;
                  if(var1 != 10) {
                     var4 = var3;
                     if(var1 != 13) {
                        var4 = var3;
                        if(var1 != 0) {
                           throw var0.syntaxError("Bad character \'" + var1 + "\' (" + var1 + ").");
                        }

                        return var4;
                     }
                  }

                  return var4;
               }

               var1 = var0.next();
            }
         }

         var4 = null;
         return var4;
      }
   }

   public static JSONObject rowToJSONObject(JSONArray var0, JSONTokener var1) throws JSONException {
      JSONArray var2 = rowToJSONArray(var1);
      return var2 != null?var2.toJSONObject(var0):null;
   }

   public static String rowToString(JSONArray var0) {
      StringBuffer var2 = new StringBuffer();

      for(int var4 = 0; var4 < var0.length(); ++var4) {
         if(var4 > 0) {
            var2.append(',');
         }

         Object var3 = var0.opt(var4);
         if(var3 != null) {
            String var7 = var3.toString();
            if(var7.length() > 0 && (var7.indexOf(44) >= 0 || var7.indexOf(10) >= 0 || var7.indexOf(13) >= 0 || var7.indexOf(0) >= 0 || var7.charAt(0) == 34)) {
               var2.append('\"');
               int var6 = var7.length();

               for(int var5 = 0; var5 < var6; ++var5) {
                  char var1 = var7.charAt(var5);
                  if(var1 >= 32 && var1 != 34) {
                     var2.append(var1);
                  }
               }

               var2.append('\"');
            } else {
               var2.append(var7);
            }
         }
      }

      var2.append('\n');
      return var2.toString();
   }

   public static JSONArray toJSONArray(String var0) throws JSONException {
      return toJSONArray((JSONTokener)(new JSONTokener(var0)));
   }

   public static JSONArray toJSONArray(JSONArray var0, String var1) throws JSONException {
      return toJSONArray(var0, (JSONTokener)(new JSONTokener(var1)));
   }

   public static JSONArray toJSONArray(JSONArray var0, JSONTokener var1) throws JSONException {
      if(var0 != null && var0.length() != 0) {
         JSONArray var2 = new JSONArray();

         while(true) {
            JSONObject var3 = rowToJSONObject(var0, var1);
            if(var3 == null) {
               var0 = var2;
               if(var2.length() == 0) {
                  return null;
               }
               break;
            }

            var2.put((Object)var3);
         }
      } else {
         var0 = null;
      }

      return var0;
   }

   public static JSONArray toJSONArray(JSONTokener var0) throws JSONException {
      return toJSONArray(rowToJSONArray(var0), (JSONTokener)var0);
   }

   public static String toString(JSONArray var0) throws JSONException {
      JSONObject var1 = var0.optJSONObject(0);
      if(var1 != null) {
         JSONArray var2 = var1.names();
         if(var2 != null) {
            return rowToString(var2) + toString(var2, var0);
         }
      }

      return null;
   }

   public static String toString(JSONArray var0, JSONArray var1) throws JSONException {
      if(var0 != null && var0.length() != 0) {
         StringBuffer var2 = new StringBuffer();

         for(int var4 = 0; var4 < var1.length(); ++var4) {
            JSONObject var3 = var1.optJSONObject(var4);
            if(var3 != null) {
               var2.append(rowToString(var3.toJSONArray(var0)));
            }
         }

         return var2.toString();
      } else {
         return null;
      }
   }
}
