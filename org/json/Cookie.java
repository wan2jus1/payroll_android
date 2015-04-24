package org.json;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Cookie {

   public static String escape(String var0) {
      var0 = var0.trim();
      StringBuffer var2 = new StringBuffer();
      int var4 = var0.length();

      for(int var3 = 0; var3 < var4; ++var3) {
         char var1 = var0.charAt(var3);
         if(var1 >= 32 && var1 != 43 && var1 != 37 && var1 != 61 && var1 != 59) {
            var2.append(var1);
         } else {
            var2.append('%');
            var2.append(Character.forDigit((char)(var1 >>> 4 & 15), 16));
            var2.append(Character.forDigit((char)(var1 & 15), 16));
         }
      }

      return var2.toString();
   }

   public static JSONObject toJSONObject(String var0) throws JSONException {
      JSONObject var1 = new JSONObject();
      JSONTokener var2 = new JSONTokener(var0);
      var1.put("name", (Object)var2.nextTo('='));
      var2.next((char)'=');
      var1.put("value", (Object)var2.nextTo(';'));
      var2.next();

      String var3;
      Object var4;
      for(; var2.more(); var1.put(var3, (Object)var4)) {
         var3 = unescape(var2.nextTo("=;"));
         if(var2.next() != 61) {
            if(!var3.equals("secure")) {
               throw var2.syntaxError("Missing \'=\' in cookie parameter.");
            }

            var4 = Boolean.TRUE;
         } else {
            var4 = unescape(var2.nextTo(';'));
            var2.next();
         }
      }

      return var1;
   }

   public static String toString(JSONObject var0) throws JSONException {
      StringBuffer var1 = new StringBuffer();
      var1.append(escape(var0.getString("name")));
      var1.append("=");
      var1.append(escape(var0.getString("value")));
      if(var0.has("expires")) {
         var1.append(";expires=");
         var1.append(var0.getString("expires"));
      }

      if(var0.has("domain")) {
         var1.append(";domain=");
         var1.append(escape(var0.getString("domain")));
      }

      if(var0.has("path")) {
         var1.append(";path=");
         var1.append(escape(var0.getString("path")));
      }

      if(var0.optBoolean("secure")) {
         var1.append(";secure");
      }

      return var1.toString();
   }

   public static String unescape(String var0) {
      int var6 = var0.length();
      StringBuffer var3 = new StringBuffer();

      int var5;
      for(int var4 = 0; var4 < var6; var4 = var5 + 1) {
         char var2 = var0.charAt(var4);
         char var1;
         if(var2 == 43) {
            var1 = 32;
            var5 = var4;
         } else {
            var1 = var2;
            var5 = var4;
            if(var2 == 37) {
               var1 = var2;
               var5 = var4;
               if(var4 + 2 < var6) {
                  int var7 = JSONTokener.dehexchar(var0.charAt(var4 + 1));
                  int var8 = JSONTokener.dehexchar(var0.charAt(var4 + 2));
                  var1 = var2;
                  var5 = var4;
                  if(var7 >= 0) {
                     var1 = var2;
                     var5 = var4;
                     if(var8 >= 0) {
                        var1 = (char)(var7 * 16 + var8);
                        var5 = var4 + 2;
                     }
                  }
               }
            }
         }

         var3.append(var1);
      }

      return var3.toString();
   }
}
