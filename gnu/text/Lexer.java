package gnu.text;

import gnu.text.LineBufferedReader;
import gnu.text.SourceError;
import gnu.text.SourceMessages;
import gnu.text.SyntaxException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;

public class Lexer extends Reader {

   protected boolean interactive;
   SourceMessages messages = null;
   protected int nesting;
   protected LineBufferedReader port;
   private int saveTokenBufferLength = -1;
   public char[] tokenBuffer = new char[100];
   public int tokenBufferLength = 0;


   public Lexer(LineBufferedReader var1) {
      this.port = var1;
   }

   public Lexer(LineBufferedReader var1, SourceMessages var2) {
      this.port = var1;
      this.messages = var2;
   }

   public static long readDigitsInBuffer(LineBufferedReader var0, int var1) {
      long var6 = 0L;
      boolean var2 = false;
      long var10 = Long.MAX_VALUE / (long)var1;
      int var3 = var0.pos;
      int var4 = var3;
      if(var3 >= var0.limit) {
         return 0L;
      } else {
         while(true) {
            var3 = Character.digit(var0.buffer[var4], var1);
            if(var3 < 0) {
               break;
            }

            long var8;
            if(var6 > var10) {
               var2 = true;
               var8 = var6;
            } else {
               var8 = (long)var1 * var6 + (long)var3;
            }

            boolean var12 = var2;
            if(var8 < 0L) {
               var12 = true;
            }

            int var5 = var4 + 1;
            var4 = var5;
            var6 = var8;
            var2 = var12;
            if(var5 >= var0.limit) {
               var4 = var5;
               var6 = var8;
               var2 = var12;
               break;
            }
         }

         var0.pos = var4;
         return var2?-1L:var6;
      }
   }

   public boolean checkErrors(PrintWriter var1, int var2) {
      return this.messages != null && this.messages.checkErrors((PrintWriter)var1, var2);
   }

   public boolean checkNext(char var1) throws IOException {
      int var2 = this.port.read();
      if(var2 == var1) {
         return true;
      } else {
         if(var2 >= 0) {
            this.port.unread_quick();
         }

         return false;
      }
   }

   public void clearErrors() {
      if(this.messages != null) {
         this.messages.clearErrors();
      }

   }

   public void close() throws IOException {
      this.port.close();
   }

   public void eofError(String var1) throws SyntaxException {
      this.fatal(var1);
   }

   public void eofError(String var1, int var2, int var3) throws SyntaxException {
      this.error('f', this.port.getName(), var2, var3, var1);
      throw new SyntaxException(this.messages);
   }

   public void error(char var1, String var2) {
      int var5 = this.port.getLineNumber();
      int var4 = this.port.getColumnNumber();
      String var3 = this.port.getName();
      if(var4 >= 0) {
         ++var4;
      } else {
         var4 = 0;
      }

      this.error(var1, var3, var5 + 1, var4, var2);
   }

   public void error(char var1, String var2, int var3, int var4, String var5) {
      if(this.messages == null) {
         this.messages = new SourceMessages();
      }

      this.messages.error(var1, var2, var3, var4, var5);
   }

   public void error(String var1) {
      this.error('e', var1);
   }

   public void fatal(String var1) throws SyntaxException {
      this.error('f', var1);
      throw new SyntaxException(this.messages);
   }

   public int getColumnNumber() {
      return this.port.getColumnNumber();
   }

   public SourceError getErrors() {
      return this.messages == null?null:this.messages.getErrors();
   }

   public int getLineNumber() {
      return this.port.getLineNumber();
   }

   public SourceMessages getMessages() {
      return this.messages;
   }

   public String getName() {
      return this.port.getName();
   }

   public final LineBufferedReader getPort() {
      return this.port;
   }

   public boolean isInteractive() {
      return this.interactive;
   }

   public void mark() throws IOException {
      if(this.saveTokenBufferLength >= 0) {
         throw new Error("internal error: recursive call to mark not allowed");
      } else {
         this.port.mark(Integer.MAX_VALUE);
         this.saveTokenBufferLength = this.tokenBufferLength;
      }
   }

   public int peek() throws IOException {
      return this.port.peek();
   }

   public void popNesting(char var1) {
      this.getPort().readState = var1;
      --this.nesting;
   }

   public char pushNesting(char var1) {
      ++this.nesting;
      LineBufferedReader var3 = this.getPort();
      char var2 = var3.readState;
      var3.readState = var1;
      return var2;
   }

   public int read() throws IOException {
      return this.port.read();
   }

   public int read(char[] var1, int var2, int var3) throws IOException {
      return this.port.read(var1, var2, var3);
   }

   public boolean readDelimited(String var1) throws IOException, SyntaxException {
      this.tokenBufferLength = 0;
      int var4 = var1.length();
      char var5 = var1.charAt(var4 - 1);

      while(true) {
         int var6 = this.read();
         if(var6 < 0) {
            return false;
         }

         if(var6 == var5) {
            int var3 = this.tokenBufferLength;
            int var2 = var4 - 1;
            int var7 = var3 - var2;
            if(var7 >= 0) {
               do {
                  if(var2 == 0) {
                     this.tokenBufferLength = var7;
                     return true;
                  }

                  var3 = var2 - 1;
                  var2 = var3;
               } while(this.tokenBuffer[var7 + var3] == var1.charAt(var3));
            }
         }

         this.tokenBufferAppend((char)var6);
      }
   }

   public int readOptionalExponent() throws IOException {
      int var4 = this.read();
      boolean var3 = false;
      boolean var5 = false;
      int var1;
      if(var4 != 43 && var4 != 45) {
         var1 = var4;
         var4 = 0;
      } else {
         var1 = this.read();
      }

      int var2;
      int var6;
      label55: {
         if(var1 >= 0) {
            var2 = Character.digit((char)var1, 10);
            if(var2 >= 0) {
               var1 = var2;

               while(true) {
                  var6 = this.read();
                  int var7 = Character.digit((char)var6, 10);
                  var5 = var3;
                  var2 = var1;
                  if(var7 < 0) {
                     break label55;
                  }

                  if(var1 > 214748363) {
                     var3 = true;
                  }

                  var1 = var1 * 10 + var7;
               }
            }
         }

         if(var4 != 0) {
            this.error("exponent sign not followed by digit");
         }

         var2 = 1;
         var6 = var1;
      }

      if(var6 >= 0) {
         this.unread(var6);
      }

      var1 = var2;
      if(var4 == 45) {
         var1 = -var2;
      }

      return var5?(var4 == 45?Integer.MIN_VALUE:Integer.MAX_VALUE):var1;
   }

   public int readUnicodeChar() throws IOException {
      int var2 = this.port.read();
      int var1 = var2;
      if(var2 >= '\ud800') {
         var1 = var2;
         if(var2 < '\udbff') {
            int var3 = this.port.read();
            var1 = var2;
            if(var3 >= '\udc00') {
               var1 = var2;
               if(var3 <= '\udfff') {
                  var1 = (var2 - '\ud800' << 10) + (var2 - '\udc00') + 65536;
               }
            }
         }
      }

      return var1;
   }

   public void reset() throws IOException {
      if(this.saveTokenBufferLength < 0) {
         throw new Error("internal error: reset called without prior mark");
      } else {
         this.port.reset();
         this.saveTokenBufferLength = -1;
      }
   }

   public boolean seenErrors() {
      return this.messages != null && this.messages.seenErrors();
   }

   public void setInteractive(boolean var1) {
      this.interactive = var1;
   }

   public void setMessages(SourceMessages var1) {
      this.messages = var1;
   }

   public void skip() throws IOException {
      this.port.skip();
   }

   protected void skip_quick() throws IOException {
      this.port.skip_quick();
   }

   public void tokenBufferAppend(int var1) {
      int var4 = var1;
      if(var1 >= 65536) {
         this.tokenBufferAppend((var1 - 65536 >> 10) + '\ud800');
         var4 = (var1 & 1023) + '\udc00';
      }

      var1 = this.tokenBufferLength;
      char[] var3 = this.tokenBuffer;
      char[] var2 = var3;
      if(var1 == this.tokenBuffer.length) {
         this.tokenBuffer = new char[var1 * 2];
         System.arraycopy(var3, 0, this.tokenBuffer, 0, var1);
         var2 = this.tokenBuffer;
      }

      var2[var1] = (char)var4;
      this.tokenBufferLength = var1 + 1;
   }

   public String tokenBufferString() {
      return new String(this.tokenBuffer, 0, this.tokenBufferLength);
   }

   protected void unread() throws IOException {
      this.port.unread();
   }

   public void unread(int var1) throws IOException {
      if(var1 >= 0) {
         this.port.unread();
      }

   }

   protected void unread_quick() throws IOException {
      this.port.unread_quick();
   }
}
