package gnu.text;

import gnu.text.LineBufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;

public class LineInputStreamReader extends LineBufferedReader {

   byte[] barr = new byte[8192];
   ByteBuffer bbuf;
   char[] carr;
   CharBuffer cbuf = null;
   Charset cset;
   CharsetDecoder decoder;
   InputStream istrm;


   public LineInputStreamReader(InputStream var1) {
      super((Reader)((Reader)null));
      this.bbuf = ByteBuffer.wrap(this.barr);
      this.bbuf.position(this.barr.length);
      this.istrm = var1;
   }

   private int fillBytes(int var1) throws IOException {
      int var3 = 0;
      int var4 = this.istrm.read(this.barr, var1, this.barr.length - var1);
      this.bbuf.position(0);
      ByteBuffer var2 = this.bbuf;
      if(var4 >= 0) {
         var3 = var4;
      }

      var2.limit(var3 + var1);
      return var4;
   }

   public void close() throws IOException {
      if(this.in != null) {
         this.in.close();
      }

      this.istrm.close();
   }

   public int fill(int var1) throws IOException {
      if(this.cset == null) {
         this.setCharset((String)"UTF-8");
      }

      if(this.buffer != this.carr) {
         this.cbuf = CharBuffer.wrap(this.buffer);
         this.carr = this.buffer;
      }

      this.cbuf.limit(this.pos + var1);
      this.cbuf.position(this.pos);
      boolean var3 = false;

      int var4;
      boolean var5;
      while(true) {
         CoderResult var2 = this.decoder.decode(this.bbuf, this.cbuf, false);
         var4 = this.cbuf.position() - this.pos;
         var5 = var3;
         if(var4 > 0) {
            break;
         }

         if(!var2.isUnderflow()) {
            var5 = var3;
            break;
         }

         var1 = this.bbuf.remaining();
         if(var1 > 0) {
            this.bbuf.compact();
         }

         if(this.fillBytes(var1) < 0) {
            var5 = true;
            break;
         }
      }

      int var6 = var4;
      if(var4 == 0) {
         var6 = var4;
         if(var5) {
            var6 = -1;
         }
      }

      return var6;
   }

   public int getByte() throws IOException {
      return !this.bbuf.hasRemaining() && this.fillBytes(0) <= 0?-1:this.bbuf.get() & 255;
   }

   public void markStart() throws IOException {
   }

   public boolean ready() throws IOException {
      return this.pos < this.limit || this.bbuf.hasRemaining() || this.istrm.available() > 0;
   }

   public void resetStart(int var1) throws IOException {
      this.bbuf.position(var1);
   }

   public void setCharset(String var1) {
      Charset var2 = Charset.forName(var1);
      if(this.cset == null) {
         this.setCharset((Charset)var2);
      } else if(!var2.equals(this.cset)) {
         throw new RuntimeException("encoding " + var1 + " does not match previous " + this.cset);
      }

   }

   public void setCharset(Charset var1) {
      this.cset = var1;
      this.decoder = var1.newDecoder();
   }
}
