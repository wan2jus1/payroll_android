package gnu.xml;

import gnu.mapping.Symbol;
import gnu.xml.NamespaceBinding;
import gnu.xml.XName;

final class MappingInfo {

   int index = -1;
   String local;
   NamespaceBinding namespaces;
   MappingInfo nextInBucket;
   String prefix;
   Symbol qname;
   int tagHash;
   XName type;
   String uri;


   static boolean equals(String var0, StringBuffer var1) {
      int var3 = var1.length();
      if(var0.length() == var3) {
         int var2 = 0;

         while(true) {
            if(var2 >= var3) {
               return true;
            }

            if(var1.charAt(var2) != var0.charAt(var2)) {
               break;
            }

            ++var2;
         }
      }

      return false;
   }

   static boolean equals(String var0, char[] var1, int var2, int var3) {
      if(var0.length() == var3) {
         int var4 = 0;

         while(true) {
            if(var4 >= var3) {
               return true;
            }

            if(var1[var2 + var4] != var0.charAt(var4)) {
               break;
            }

            ++var4;
         }
      }

      return false;
   }

   static int hash(String var0, String var1) {
      int var3 = var1.hashCode();
      int var2 = var3;
      if(var0 != null) {
         var2 = var3 ^ var0.hashCode();
      }

      return var2;
   }

   static int hash(char[] var0, int var1, int var2) {
      int var4 = 0;
      int var5 = 0;
      int var6 = -1;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var7 = var0[var1 + var3];
         if(var7 == 58 && var6 < 0) {
            var6 = var3;
            byte var8 = 0;
            var5 = var4;
            var4 = var8;
         } else {
            var4 = var4 * 31 + var7;
         }
      }

      return var5 ^ var4;
   }

   boolean match(char[] var1, int var2, int var3) {
      if(this.prefix != null) {
         int var4 = this.local.length();
         int var5 = this.prefix.length();
         return var3 == var5 + 1 + var4 && var1[var5] == 58 && equals(this.prefix, var1, var2, var5) && equals(this.local, var1, var2 + var5 + 1, var4);
      } else {
         return equals(this.local, var1, var2, var3);
      }
   }
}
