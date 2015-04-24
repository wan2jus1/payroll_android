package org.json;

import java.util.Iterator;
import org.json.Cookie;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class CookieList {

   public static JSONObject toJSONObject(String var0) throws JSONException {
      JSONObject var1 = new JSONObject();
      JSONTokener var3 = new JSONTokener(var0);

      while(var3.more()) {
         String var2 = Cookie.unescape(var3.nextTo('='));
         var3.next((char)'=');
         var1.put(var2, (Object)Cookie.unescape(var3.nextTo(';')));
         var3.next();
      }

      return var1;
   }

   public static String toString(JSONObject var0) throws JSONException {
      boolean var4 = false;
      Iterator var1 = var0.keys();
      StringBuffer var2 = new StringBuffer();

      while(var1.hasNext()) {
         String var3 = var1.next().toString();
         if(!var0.isNull(var3)) {
            if(var4) {
               var2.append(';');
            }

            var2.append(Cookie.escape(var3));
            var2.append("=");
            var2.append(Cookie.escape(var0.getString(var3)));
            var4 = true;
         }
      }

      return var2.toString();
   }
}
