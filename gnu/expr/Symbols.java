package gnu.expr;

import gnu.lists.Consumer;
import gnu.mapping.OutPort;
import gnu.mapping.SimpleSymbol;

public class Symbols {

   private static int gensym_counter;


   static int generateInt() {
      synchronized(Symbols.class){}

      int var1;
      try {
         var1 = gensym_counter + 1;
         gensym_counter = var1;
      } finally {
         ;
      }

      return var1;
   }

   public static final SimpleSymbol gentemp() {
      return SimpleSymbol.valueOf("GS." + Integer.toString(generateInt()));
   }

   public static final String intern(String var0) {
      return make(var0);
   }

   public static String make(String var0) {
      return var0.intern();
   }

   public static void print(String var0, Consumer var1) {
      boolean var3;
      if(var1 instanceof OutPort && ((OutPort)var1).printReadable) {
         var3 = true;
      } else {
         var3 = false;
      }

      if(var3) {
         int var4 = var0.length();

         for(int var5 = 0; var5 < var4; ++var5) {
            char var2 = var0.charAt(var5);
            if(!Character.isLowerCase(var2) && var2 != 33 && var2 != 36 && var2 != 37 && var2 != 38 && var2 != 42 && var2 != 47 && var2 != 58 && var2 != 60 && var2 != 61 && var2 != 62 && var2 != 63 && var2 != 126 && var2 != 95 && var2 != 94 && (var2 != 43 && var2 != 45 || var5 <= 0 && var4 != 1) && (!Character.isDigit(var2) || var5 <= 0) && (var2 != 46 || var5 != 0 && var0.charAt(var5 - 1) != 46)) {
               var1.write(92);
            }

            var1.write(var2);
         }
      } else {
         var1.write(var0);
      }

   }
}
