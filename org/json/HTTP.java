package org.json;

import java.util.Iterator;
import org.json.HTTPTokener;
import org.json.JSONException;
import org.json.JSONObject;

public class HTTP {

   public static final String CRLF = "\r\n";


   public static JSONObject toJSONObject(String var0) throws JSONException {
      JSONObject var1 = new JSONObject();
      HTTPTokener var3 = new HTTPTokener(var0);
      String var2 = var3.nextToken();
      if(var2.toUpperCase().startsWith("HTTP")) {
         var1.put("HTTP-Version", (Object)var2);
         var1.put("Status-Code", (Object)var3.nextToken());
         var1.put("Reason-Phrase", (Object)var3.nextTo('\u0000'));
         var3.next();
      } else {
         var1.put("Method", (Object)var2);
         var1.put("Request-URI", (Object)var3.nextToken());
         var1.put("HTTP-Version", (Object)var3.nextToken());
      }

      while(var3.more()) {
         var2 = var3.nextTo(':');
         var3.next(':');
         var1.put(var2, (Object)var3.nextTo('\u0000'));
         var3.next();
      }

      return var1;
   }

   public static String toString(JSONObject var0) throws JSONException {
      Iterator var1 = var0.keys();
      StringBuffer var2 = new StringBuffer();
      if(var0.has("Status-Code") && var0.has("Reason-Phrase")) {
         var2.append(var0.getString("HTTP-Version"));
         var2.append(' ');
         var2.append(var0.getString("Status-Code"));
         var2.append(' ');
         var2.append(var0.getString("Reason-Phrase"));
      } else {
         if(!var0.has("Method") || !var0.has("Request-URI")) {
            throw new JSONException("Not enough material for an HTTP header.");
         }

         var2.append(var0.getString("Method"));
         var2.append(' ');
         var2.append('\"');
         var2.append(var0.getString("Request-URI"));
         var2.append('\"');
         var2.append(' ');
         var2.append(var0.getString("HTTP-Version"));
      }

      var2.append("\r\n");

      while(var1.hasNext()) {
         String var3 = var1.next().toString();
         if(!"HTTP-Version".equals(var3) && !"Status-Code".equals(var3) && !"Reason-Phrase".equals(var3) && !"Method".equals(var3) && !"Request-URI".equals(var3) && !var0.isNull(var3)) {
            var2.append(var3);
            var2.append(": ");
            var2.append(var0.getString(var3));
            var2.append("\r\n");
         }
      }

      var2.append("\r\n");
      return var2.toString();
   }
}
