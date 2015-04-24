package gnu.kawa.xml;

import gnu.kawa.xml.BinaryObject;

public class HexBinary extends BinaryObject {

   public HexBinary(byte[] var1) {
      this.data = var1;
   }

   static char forHexDigit(int var0) {
      return var0 < 10?(char)(var0 + 48):(char)(var0 - 10 + 65);
   }

   static byte[] parseHexBinary(String var0) {
      var0 = var0.trim();
      int var2 = var0.length();
      if((var2 & 1) != 0) {
         throw new IllegalArgumentException("hexBinary string length not a multiple of 2");
      } else {
         int var4 = var2 >> 1;
         byte[] var1 = new byte[var4];

         for(int var3 = 0; var3 < var4; ++var3) {
            int var5 = Character.digit(var0.charAt(var3 * 2), 16);
            int var6 = Character.digit(var0.charAt(var3 * 2 + 1), 16);
            var2 = -1;
            if(var5 < 0) {
               var2 = var3 * 2;
            } else if(var6 < 0) {
               var2 = var3 * 2 + 1;
            }

            if(var2 >= 0) {
               throw new IllegalArgumentException("invalid hexBinary character at position " + var2);
            }

            var1[var3] = (byte)(var5 * 16 + var6);
         }

         return var1;
      }
   }

   static HexBinary valueOf(String var0) {
      return new HexBinary(parseHexBinary(var0));
   }

   public String toString() {
      return this.toString(new StringBuffer()).toString();
   }

   public StringBuffer toString(StringBuffer var1) {
      byte[] var2 = this.data;
      int var4 = var2.length;

      for(int var3 = 0; var3 < var4; ++var3) {
         byte var5 = var2[var3];
         var1.append(forHexDigit(var5 >> 4 & 15));
         var1.append(forHexDigit(var5 & 15));
      }

      return var1;
   }
}
