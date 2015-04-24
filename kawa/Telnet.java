package kawa;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import kawa.TelnetInputStream;
import kawa.TelnetOutputStream;

public class Telnet implements Runnable {

   public static final int DO = 253;
   public static final int DONT = 254;
   public static final int ECHO = 1;
   static final int EOF = 236;
   static final int IAC = 255;
   static final int IP = 244;
   static final int LINEMODE = 34;
   static final int NAWS = 31;
   static final int NOP = 241;
   static final int OPTION_NO = 0;
   static final int OPTION_WANTNO = 1;
   static final int OPTION_WANTNO_OPPOSITE = 2;
   static final int OPTION_WANTYES = 3;
   static final int OPTION_WANTYES_OPPOSITE = 4;
   static final int OPTION_YES = 5;
   static final int SB = 250;
   static final int SE = 240;
   public static final int SUPPRESS_GO_AHEAD = 3;
   static final int TM = 6;
   static final int TTYPE = 24;
   public static final int WILL = 251;
   public static final int WONT = 252;
   TelnetInputStream in;
   boolean isServer;
   final byte[] optionsState = new byte[256];
   TelnetOutputStream out;
   final byte preferredLineMode = 3;
   InputStream sin;
   OutputStream sout;
   public byte[] terminalType;
   public short windowHeight;
   public short windowWidth;


   public Telnet(Socket var1, boolean var2) throws IOException {
      this.sin = var1.getInputStream();
      this.sout = var1.getOutputStream();
      this.out = new TelnetOutputStream(this.sout);
      this.in = new TelnetInputStream(this.sin, this);
      this.isServer = var2;
   }

   public static void main(String[] param0) {
      // $FF: Couldn't be decompiled
   }

   static void usage() {
      System.err.println("Usage:  [java] kawa.Telnet HOST [PORT#]");
      System.exit(-1);
   }

   boolean change(int var1, int var2) {
      if(var2 != 6 && (!this.isServer || var2 != 31)) {
         if(this.isServer && var1 == 251 && var2 == 34) {
            try {
               this.out.writeSubCommand(34, new byte[]{(byte)1, (byte)3});
               return true;
            } catch (IOException var4) {
               return true;
            }
         } else if(this.isServer && var1 == 251 && var2 == 24) {
            try {
               this.out.writeSubCommand(var2, new byte[]{(byte)1});
               return true;
            } catch (IOException var5) {
               return true;
            }
         } else {
            if(!this.isServer && var2 == 1) {
               if(var1 == 253) {
                  return false;
               }

               if(var1 == 251) {
                  return true;
               }
            }

            return false;
         }
      } else {
         return true;
      }
   }

   public TelnetInputStream getInputStream() {
      return this.in;
   }

   public TelnetOutputStream getOutputStream() {
      return this.out;
   }

   void handle(int var1, int var2) throws IOException {
      boolean var8 = true;
      short var7 = 254;
      short var9 = 253;
      boolean var6;
      if(var1 < 253) {
         var6 = true;
      } else {
         var6 = false;
      }

      if((var1 & 1) == 0) {
         var8 = false;
      }

      byte var10 = this.optionsState[var2];
      byte var5 = var10;
      if(var6) {
         var5 = (byte)(var10 >> 3);
      }

      TelnetOutputStream var4;
      short var11;
      byte var12;
      byte var13;
      switch(var5 >> 3 & 7) {
      case 0:
         if(!var8) {
            return;
         }

         if(this.change(var1, var2)) {
            var13 = 5;
            var4 = this.out;
            if(var6) {
               var11 = 253;
            } else {
               var11 = 251;
            }

            var4.writeCommand(var11, var2);
            var12 = var13;
         } else {
            var4 = this.out;
            if(!var6) {
               var7 = 252;
            }

            var4.writeCommand(var7, var2);
            var12 = var5;
         }
         break;
      case 1:
         var12 = 0;
         break;
      case 2:
         var13 = 3;
         var4 = this.out;
         if(var6) {
            var11 = var9;
         } else {
            var11 = 251;
         }

         var4.writeCommand(var11, var2);
         var12 = var13;
         break;
      case 3:
         if(var8) {
            var13 = 5;
            this.change(var1, var2);
            var12 = var13;
         } else {
            var12 = 0;
         }
         break;
      case 4:
         if(var8) {
            var12 = 1;
            var4 = this.out;
            if(!var6) {
               var7 = 252;
            }

            var4.writeCommand(var7, var2);
         } else {
            var12 = 0;
         }
         break;
      case 5:
         if(var8) {
            return;
         }

         var13 = 0;
         this.change(var1, var2);
         var4 = this.out;
         if(var6) {
            var11 = 254;
         } else {
            var11 = 252;
         }

         var4.writeCommand(var11, var2);
         var12 = var13;
         break;
      default:
         var12 = var5;
      }

      byte var3;
      if(var6) {
         var3 = (byte)(this.optionsState[var2] & 199 | var12 << 3);
      } else {
         var3 = (byte)(this.optionsState[var2] & 248 | var12);
      }

      this.optionsState[var2] = var3;
   }

   public void request(int var1, int var2) throws IOException {
      boolean var7 = true;
      boolean var6;
      if(var1 >= 253) {
         var6 = true;
      } else {
         var6 = false;
      }

      if((var1 & 1) == 0) {
         var7 = false;
      }

      byte var5 = this.optionsState[var2];
      int var4 = var5;
      if(var6) {
         var4 = (byte)(var5 >> 3);
      }

      int var8 = var4;
      switch(var4 & 7) {
      case 0:
         if(var7) {
            var4 = 3;
            this.out.writeCommand(var1, var2);
         }
         break;
      case 1:
         if(var7) {
            var4 = 2;
         }
         break;
      case 2:
         if(!var7) {
            var4 = 1;
         }
         break;
      case 3:
         var8 = var4;
         if(!var7) {
            var8 = 4;
         }
      case 4:
         var4 = var8;
         if(var7) {
            var4 = 3;
         }
         break;
      case 5:
         if(!var7) {
            var4 = 1;
            this.out.writeCommand(var1, var2);
         }
      }

      byte var3;
      if(var6) {
         var3 = (byte)(this.optionsState[var2] & 199 | var4 << 3);
      } else {
         var3 = (byte)(this.optionsState[var2] & 248 | var4);
      }

      this.optionsState[var2] = var3;
   }

   public void run() {
      // $FF: Couldn't be decompiled
   }

   public void subCommand(byte[] var1, int var2, int var3) {
      switch(var1[var2]) {
      case 24:
         byte[] var4 = new byte[var3 - 1];
         System.arraycopy(var1, 1, var4, 0, var3 - 1);
         this.terminalType = var4;
         System.err.println("terminal type: \'" + new String(var4) + "\'");
         return;
      case 31:
         if(var3 == 5) {
            this.windowWidth = (short)((var1[1] << 8) + (var1[2] & 255));
            this.windowHeight = (short)((var1[3] << 8) + (var1[4] & 255));
            return;
         }
         break;
      case 34:
         System.err.println("SBCommand LINEMODE " + var1[1] + " len:" + var3);
         if(var1[1] == 3) {
            for(var2 = 2; var2 + 2 < var3; var2 += 3) {
               System.err.println("  " + var1[var2] + "," + var1[var2 + 1] + "," + var1[var2 + 2]);
            }
         }
      }

   }
}
