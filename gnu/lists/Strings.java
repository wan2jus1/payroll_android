package gnu.lists;

import gnu.lists.CharSeq;
import java.io.PrintWriter;

public class Strings {

   public static void makeCapitalize(CharSeq var0) {
      char var1 = 32;
      int var4 = var0.length();

      for(int var3 = 0; var3 < var4; ++var3) {
         char var2 = var0.charAt(var3);
         if(!Character.isLetterOrDigit(var1)) {
            var1 = Character.toTitleCase(var2);
         } else {
            var1 = Character.toLowerCase(var2);
         }

         var0.setCharAt(var3, var1);
      }

   }

   public static void makeLowerCase(CharSeq var0) {
      int var1 = var0.length();

      while(true) {
         --var1;
         if(var1 < 0) {
            return;
         }

         var0.setCharAt(var1, Character.toLowerCase(var0.charAt(var1)));
      }
   }

   public static void makeUpperCase(CharSeq var0) {
      int var1 = var0.length();

      while(true) {
         --var1;
         if(var1 < 0) {
            return;
         }

         var0.setCharAt(var1, Character.toUpperCase(var0.charAt(var1)));
      }
   }

   public static void printQuoted(CharSequence var0, PrintWriter var1, int var2) {
      int var5 = var0.length();
      var1.print('\"');

      for(int var4 = 0; var4 < var5; ++var4) {
         char var3 = var0.charAt(var4);
         if(var3 != 92 && var3 != 34) {
            if(var2 > 0) {
               if(var3 == 10) {
                  var1.print("\\n");
                  continue;
               }

               if(var3 == 13) {
                  var1.print("\\r");
                  continue;
               }

               if(var3 == 9) {
                  var1.print("\\t");
                  continue;
               }

               if(var3 == 7) {
                  var1.print("\\a");
                  continue;
               }

               if(var3 == 8) {
                  var1.print("\\b");
                  continue;
               }

               if(var3 == 11) {
                  var1.print("\\v");
                  continue;
               }

               if(var3 == 12) {
                  var1.print("\\f");
                  continue;
               }
            }
         } else {
            var1.print('\\');
         }

         var1.print(var3);
      }

      var1.print('\"');
   }
}
