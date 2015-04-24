package org.json;

import java.util.Arrays;
import org.json.JSONException;

public class Kim {

   private byte[] bytes;
   private int hashcode;
   public int length;
   private String string;


   public Kim(String var1) throws JSONException {
      this.bytes = null;
      this.hashcode = 0;
      this.length = 0;
      this.string = null;
      int var8 = var1.length();
      this.hashcode = 0;
      this.length = 0;
      if(var8 > 0) {
         int var2;
         int var3;
         for(var2 = 0; var2 < var8; ++var2) {
            char var4 = var1.charAt(var2);
            if(var4 <= 127) {
               ++this.length;
            } else if(var4 <= 16383) {
               this.length += 2;
            } else {
               var3 = var2;
               if(var4 >= '\ud800') {
                  var3 = var2;
                  if(var4 <= '\udfff') {
                     var3 = var2 + 1;
                     char var9 = var1.charAt(var3);
                     if(var4 > '\udbff' || var9 < '\udc00' || var9 > '\udfff') {
                        throw new JSONException("Bad UTF16");
                     }
                  }
               }

               this.length += 3;
               var2 = var3;
            }
         }

         this.bytes = new byte[this.length];
         var3 = 0;
         int var5 = 1;

         int var10;
         for(var2 = 0; var2 < var8; var5 = var10) {
            char var7 = var1.charAt(var2);
            if(var7 <= 127) {
               this.bytes[var3] = (byte)var7;
               var10 = var5 + var7;
               this.hashcode += var10;
               ++var3;
            } else if(var7 <= 16383) {
               var10 = var7 >>> 7 | 128;
               this.bytes[var3] = (byte)var10;
               var10 += var5;
               this.hashcode += var10;
               ++var3;
               var5 = var7 & 127;
               this.bytes[var3] = (byte)var5;
               var10 += var5;
               this.hashcode += var10;
               ++var3;
            } else {
               int var6 = var7;
               var10 = var2;
               if(var7 >= '\ud800') {
                  var6 = var7;
                  var10 = var2;
                  if(var7 <= '\udbff') {
                     var10 = var2 + 1;
                     var6 = ((var7 & 1023) << 10 | var1.charAt(var10) & 1023) + 65536;
                  }
               }

               var2 = var6 >>> 14 | 128;
               this.bytes[var3] = (byte)var2;
               var2 += var5;
               this.hashcode += var2;
               ++var3;
               var5 = var6 >>> 7 & 255 | 128;
               this.bytes[var3] = (byte)var5;
               var2 += var5;
               this.hashcode += var2;
               ++var3;
               var5 = var6 & 127;
               this.bytes[var3] = (byte)var5;
               var5 += var2;
               this.hashcode += var5;
               ++var3;
               var2 = var10;
               var10 = var5;
            }

            ++var2;
         }

         this.hashcode += var5 << 16;
      }

   }

   public Kim(Kim var1, int var2, int var3) {
      this((byte[])var1.bytes, var2, var3);
   }

   public Kim(byte[] var1, int var2) {
      this((byte[])var1, 0, var2);
   }

   public Kim(byte[] var1, int var2, int var3) {
      this.bytes = null;
      this.hashcode = 0;
      this.length = 0;
      this.string = null;
      int var4 = 1;
      this.hashcode = 0;
      this.length = var3 - var2;
      if(this.length > 0) {
         this.bytes = new byte[this.length];

         for(var3 = 0; var3 < this.length; ++var3) {
            int var5 = var1[var3 + var2] & 255;
            var4 += var5;
            this.hashcode += var4;
            this.bytes[var3] = (byte)var5;
         }

         this.hashcode += var4 << 16;
      }

   }

   public static int characterSize(int var0) throws JSONException {
      if(var0 >= 0 && var0 <= 1114111) {
         return var0 <= 127?1:(var0 <= 16383?2:3);
      } else {
         throw new JSONException("Bad character " + var0);
      }
   }

   public int characterAt(int var1) throws JSONException {
      int var2 = this.get(var1);
      if((var2 & 128) != 0) {
         int var3 = this.get(var1 + 1);
         if((var3 & 128) == 0) {
            var3 |= (var2 & 127) << 7;
            var2 = var3;
            if(var3 > 127) {
               return var2;
            }
         } else {
            int var4 = this.get(var1 + 2);
            var3 = (var2 & 127) << 14 | (var3 & 127) << 7 | var4;
            if((var4 & 128) == 0 && var3 > 16383 && var3 <= 1114111) {
               var2 = var3;
               if(var3 < '\ud800') {
                  return var2;
               }

               if(var3 > '\udfff') {
                  return var3;
               }
            }
         }

         throw new JSONException("Bad character at " + var1);
      } else {
         return var2;
      }
   }

   public int copy(byte[] var1, int var2) {
      System.arraycopy(this.bytes, 0, var1, var2, this.length);
      return this.length + var2;
   }

   public boolean equals(Object var1) {
      if(var1 instanceof Kim) {
         Kim var2 = (Kim)var1;
         if(this == var2) {
            return true;
         }

         if(this.hashcode == var2.hashcode) {
            return Arrays.equals(this.bytes, var2.bytes);
         }
      }

      return false;
   }

   public int get(int var1) throws JSONException {
      if(var1 >= 0 && var1 <= this.length) {
         return this.bytes[var1] & 255;
      } else {
         throw new JSONException("Bad character at " + var1);
      }
   }

   public int hashCode() {
      return this.hashcode;
   }

   public String toString() throws JSONException {
      if(this.string == null) {
         int var2 = 0;
         char[] var1 = new char[this.length];

         int var4;
         for(int var3 = 0; var3 < this.length; var3 += characterSize(var4)) {
            var4 = this.characterAt(var3);
            if(var4 < 65536) {
               var1[var2] = (char)var4;
               ++var2;
            } else {
               var1[var2] = (char)('\ud800' | var4 - 65536 >>> 10);
               ++var2;
               var1[var2] = (char)('\udc00' | var4 & 1023);
               ++var2;
            }
         }

         this.string = new String(var1, 0, var2);
      }

      return this.string;
   }
}
