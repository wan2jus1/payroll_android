package org.json;

import org.json.JSONException;
import org.json.JSONTokener;

public class HTTPTokener extends JSONTokener {

   public HTTPTokener(String var1) {
      super((String)var1);
   }

   public String nextToken() throws JSONException {
      StringBuffer var3 = new StringBuffer();

      char var2;
      do {
         var2 = this.next();
      } while(Character.isWhitespace(var2));

      char var1;
      if(var2 != 34) {
         var1 = var2;
         if(var2 != 39) {
            while(var1 != 0 && !Character.isWhitespace(var1)) {
               var3.append(var1);
               var1 = this.next();
            }

            return var3.toString();
         }
      }

      while(true) {
         var1 = this.next();
         if(var1 < 32) {
            throw this.syntaxError("Unterminated string.");
         }

         if(var1 == var2) {
            return var3.toString();
         }

         var3.append(var1);
      }
   }
}
