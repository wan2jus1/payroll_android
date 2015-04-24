package gnu.kawa.xml;

import gnu.kawa.xml.BinaryObject;

public class Base64Binary extends BinaryObject {

   public static final String ENCODING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";


   public Base64Binary(String var1) {
      int var10 = var1.length();
      int var5 = 0;

      char var2;
      int var4;
      int var6;
      for(var4 = 0; var4 < var10; var5 = var6) {
         var2 = var1.charAt(var4);
         var6 = var5;
         if(!Character.isWhitespace(var2)) {
            var6 = var5;
            if(var2 != 61) {
               var6 = var5 + 1;
            }
         }

         ++var4;
      }

      byte[] var3 = new byte[var5 * 3 / 4];
      int var9 = 0;
      var6 = 0;
      int var8 = 0;
      int var7 = 0;

      for(var5 = 0; var7 < var10; var5 = var4) {
         label110: {
            var2 = var1.charAt(var7);
            if(var2 >= 65 && var2 <= 90) {
               var4 = var2 - 65;
            } else if(var2 >= 97 && var2 <= 122) {
               var4 = var2 - 97 + 26;
            } else if(var2 >= 48 && var2 <= 57) {
               var4 = var2 - 48 + 52;
            } else if(var2 == 43) {
               var4 = 62;
            } else if(var2 == 47) {
               var4 = 63;
            } else {
               if(Character.isWhitespace(var2)) {
                  var4 = var5;
                  break label110;
               }

               if(var2 == 61) {
                  ++var8;
                  var4 = var5;
                  break label110;
               }

               var4 = -1;
            }

            if(var4 < 0 || var8 > 0) {
               throw new IllegalArgumentException("illegal character in base64Binary string at position " + var7);
            }

            var9 = (var9 << 6) + var4;
            ++var6;
            if(var6 == 4) {
               var4 = var5 + 1;
               var3[var5] = (byte)(var9 >> 16);
               var5 = var4 + 1;
               var3[var4] = (byte)(var9 >> 8);
               var4 = var5 + 1;
               var3[var5] = (byte)var9;
               var6 = 0;
            } else {
               var4 = var5;
            }
         }

         ++var7;
      }

      label62: {
         if(var6 + var8 > 0) {
            if(var6 + var8 == 4 && ((1 << var8) - 1 & var9) == 0 && var5 + 3 - var8 == var3.length) {
               break label62;
            }
         } else if(var5 == var3.length) {
            break label62;
         }

         throw new IllegalArgumentException();
      }

      switch(var8) {
      case 1:
         var4 = var5 + 1;
         var3[var5] = (byte)(var9 << 10);
         var3[var4] = (byte)(var9 >> 2);
         break;
      case 2:
         var3[var5] = (byte)(var9 >> 4);
      }

      this.data = var3;
   }

   public Base64Binary(byte[] var1) {
      this.data = var1;
   }

   public static Base64Binary valueOf(String var0) {
      return new Base64Binary(var0);
   }

   public String toString() {
      return this.toString(new StringBuffer()).toString();
   }

   public StringBuffer toString(StringBuffer var1) {
      byte[] var2 = this.data;
      int var7 = var2.length;
      int var3 = 0;
      int var4 = 0;

      while(var4 < var7) {
         int var5 = var3 << 8 | var2[var4] & 255;
         int var6 = var4 + 1;
         var4 = var6;
         var3 = var5;
         if(var6 % 3 == 0) {
            var1.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".charAt(var5 >> 18 & 63));
            var1.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".charAt(var5 >> 12 & 63));
            var1.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".charAt(var5 >> 6 & 63));
            var1.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".charAt(var5 & 63));
            var4 = var6;
            var3 = var5;
         }
      }

      switch(var7 % 3) {
      case 1:
         var1.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".charAt(var3 >> 2 & 63));
         var1.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".charAt(var3 << 4 & 63));
         var1.append("==");
         return var1;
      case 2:
         var1.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".charAt(var3 >> 10 & 63));
         var1.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".charAt(var3 >> 4 & 63));
         var1.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".charAt(var3 << 2 & 63));
         var1.append('=');
         return var1;
      default:
         return var1;
      }
   }
}
