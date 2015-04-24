package org.json;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import org.json.JSONException;
import org.json.JSONObject;

public class Property {

   public static JSONObject toJSONObject(Properties var0) throws JSONException {
      JSONObject var1 = new JSONObject();
      if(var0 != null && !var0.isEmpty()) {
         Enumeration var2 = var0.propertyNames();

         while(var2.hasMoreElements()) {
            String var3 = (String)var2.nextElement();
            var1.put(var3, (Object)var0.getProperty(var3));
         }
      }

      return var1;
   }

   public static Properties toProperties(JSONObject var0) throws JSONException {
      Properties var1 = new Properties();
      if(var0 != null) {
         Iterator var2 = var0.keys();

         while(var2.hasNext()) {
            String var3 = var2.next().toString();
            var1.put(var3, var0.getString(var3));
         }
      }

      return var1;
   }
}
