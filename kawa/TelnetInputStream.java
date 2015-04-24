package kawa;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import kawa.Telnet;

public class TelnetInputStream extends FilterInputStream {

   static final int SB_IAC = 400;
   protected byte[] buf = new byte[512];
   Telnet connection;
   int count;
   int pos;
   int state = 0;
   int subCommandLength = 0;


   public TelnetInputStream(InputStream var1, Telnet var2) throws IOException {
      super(var1);
      this.connection = var2;
   }

   public int read() throws IOException {
      int var2;
      while(true) {
         int var3;
         if(this.pos >= this.count) {
            var3 = this.in.available();
            if(var3 <= 0) {
               var2 = 1;
            } else {
               var2 = var3;
               if(var3 > this.buf.length - this.subCommandLength) {
                  var2 = this.buf.length - this.subCommandLength;
               }
            }

            var2 = this.in.read(this.buf, this.subCommandLength, var2);
            this.pos = this.subCommandLength;
            this.count = var2;
            if(var2 <= 0) {
               var2 = -1;
               break;
            }
         }

         byte[] var1 = this.buf;
         var2 = this.pos;
         this.pos = var2 + 1;
         var3 = var1[var2] & 255;
         if(this.state != 0) {
            if(this.state == 255) {
               if(var3 == 255) {
                  this.state = 0;
                  return 255;
               }

               if(var3 != 251 && var3 != 252 && var3 != 253 && var3 != 254 && var3 != 250) {
                  if(var3 == 244) {
                     System.err.println("Interrupt Process");
                     this.state = 0;
                  } else {
                     if(var3 == 236) {
                        return -1;
                     }

                     this.state = 0;
                  }
               } else {
                  this.state = var3;
               }
            } else if(this.state != 251 && this.state != 252 && this.state != 253 && this.state != 254) {
               if(this.state == 250) {
                  if(var3 == 255) {
                     this.state = 400;
                  } else {
                     var1 = this.buf;
                     var2 = this.subCommandLength;
                     this.subCommandLength = var2 + 1;
                     var1[var2] = (byte)var3;
                  }
               } else if(this.state == 400) {
                  if(var3 == 255) {
                     var1 = this.buf;
                     var2 = this.subCommandLength;
                     this.subCommandLength = var2 + 1;
                     var1[var2] = (byte)var3;
                     this.state = 250;
                  } else if(var3 == 240) {
                     this.connection.subCommand(this.buf, 0, this.subCommandLength);
                     this.state = 0;
                     this.subCommandLength = 0;
                  } else {
                     this.state = 0;
                     this.subCommandLength = 0;
                  }
               } else {
                  System.err.println("Bad state " + this.state);
               }
            } else {
               this.connection.handle(this.state, var3);
               this.state = 0;
            }
         } else {
            var2 = var3;
            if(var3 == 255) {
               this.state = 255;
               continue;
            }
            break;
         }
      }

      return var2;
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      int var5;
      if(var3 <= 0) {
         var5 = 0;
         return var5;
      } else {
         int var6;
         label39: {
            var5 = 0;
            if(this.state == 0) {
               var6 = var2;
               if(this.pos < this.count) {
                  break label39;
               }
            }

            var6 = this.read();
            var5 = var6;
            if(var6 < 0) {
               return var5;
            }

            var1[var2] = (byte)var6;
            var5 = 0 + 1;
            var6 = var2 + 1;
         }

         var2 = var5;
         if(this.state == 0) {
            while(true) {
               var2 = var5;
               if(this.pos >= this.count) {
                  break;
               }

               var2 = var5;
               if(var5 >= var3) {
                  break;
               }

               byte var4 = this.buf[this.pos];
               if(var4 == -1) {
                  var2 = var5;
                  break;
               }

               var1[var6] = var4;
               ++var5;
               ++this.pos;
               ++var6;
            }
         }

         return var2;
      }
   }
}
