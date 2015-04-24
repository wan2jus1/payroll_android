package gnu.kawa.servlet;

import gnu.kawa.servlet.HttpRequestContext;
import java.io.IOException;
import java.io.OutputStream;

class HttpOutputStream extends OutputStream {

   byte[] buffer;
   HttpRequestContext context;
   int count;
   OutputStream out;


   public HttpOutputStream(HttpRequestContext var1, int var2) {
      this.context = var1;
      this.buffer = new byte[var2];
   }

   public void close() throws IOException {
      if(this.out == null) {
         this.maybeSendResponseHeaders(this.count);
         this.out = this.context.getResponseStream();
      }

      this.flush();
      this.out.close();
   }

   public void flush() throws IOException {
      if(this.out == null) {
         this.maybeSendResponseHeaders(-1);
         this.out = this.context.getResponseStream();
      }

      if(this.count > 0) {
         this.out.write(this.buffer, 0, this.count);
         this.count = 0;
      }

   }

   void maybeSendResponseHeaders(int var1) throws IOException {
      int var2 = this.context.statusCode;
      if(var2 != -999) {
         this.context.sendResponseHeaders(var2, this.context.statusReasonPhrase, (long)var1);
         this.context.statusCode = -999;
      }

   }

   public boolean reset() {
      boolean var1 = false;
      this.count = 0;
      if(this.out == null) {
         var1 = true;
      }

      return var1;
   }

   public void write(int var1) throws IOException {
      if(this.count >= this.buffer.length) {
         this.flush();
      }

      byte[] var2 = this.buffer;
      int var3 = this.count;
      this.count = var3 + 1;
      var2[var3] = (byte)var1;
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      int var5 = this.buffer.length - this.count;
      int var4 = var2;

      for(var2 = var5; var3 > var2; var2 = this.buffer.length) {
         System.arraycopy(var1, var4, this.buffer, this.count, var2);
         this.count += var2;
         this.flush();
         var4 += var2;
         var3 -= var2;
      }

      if(var3 > 0) {
         System.arraycopy(var1, var4, this.buffer, this.count, var3);
         this.count += var3;
      }

   }
}
