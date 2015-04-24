package gnu.kawa.servlet;

import gnu.kawa.servlet.HttpOutputStream;
import gnu.kawa.servlet.HttpRequestContext;
import gnu.kawa.xml.HttpPrinter;
import java.io.IOException;
import java.io.OutputStream;

public class ServletPrinter extends HttpPrinter {

   HttpRequestContext hctx;


   public ServletPrinter(HttpRequestContext var1, int var2) throws IOException {
      super((OutputStream)(new HttpOutputStream(var1, var2)));
      this.hctx = var1;
   }

   public void addHeader(String var1, String var2) {
      if(var1.equalsIgnoreCase("Content-type")) {
         super.sawContentType = var2;
         this.hctx.setContentType(var2);
      } else {
         if(!var1.equalsIgnoreCase("Status")) {
            this.hctx.setResponseHeader(var1, var2);
            return;
         }

         int var6 = var2.length();
         int var5 = 0;

         for(int var4 = 0; var4 < var6; ++var4) {
            if(var4 >= var6) {
               this.hctx.statusCode = var5;
               return;
            }

            char var3 = var2.charAt(var4);
            int var7 = Character.digit(var3, 10);
            if(var7 < 0) {
               var6 = var4;
               if(var3 == 32) {
                  var6 = var4 + 1;
               }

               this.hctx.statusCode = var5;
               this.hctx.statusReasonPhrase = var2.substring(var6);
               return;
            }

            var5 = var5 * 10 + var7;
         }
      }

   }

   public void printHeaders() {
   }

   public boolean reset(boolean var1) {
      return ((HttpOutputStream)this.ostream).reset() & super.reset(var1);
   }
}
