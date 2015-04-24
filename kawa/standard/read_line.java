package kawa.standard;

import gnu.expr.Special;
import gnu.lists.FString;
import gnu.mapping.Values;
import gnu.text.LineBufferedReader;
import java.io.IOException;

public class read_line {

   public static Object apply(LineBufferedReader var0, String var1) throws IOException {
      Object var12;
      if(var0.read() < 0) {
         var12 = Special.eof;
      } else {
         int var9 = var0.pos - 1;
         int var10 = var0.limit;
         char[] var3 = var0.buffer;
         byte var7 = -1;
         int var5 = var9;

         while(true) {
            int var6;
            int var8;
            byte var14;
            if(var5 < var10) {
               label102: {
                  var6 = var5 + 1;
                  char var11 = var3[var5];
                  if(var11 != 13 && var11 != 10) {
                     var5 = var6;
                     continue;
                  }

                  --var6;
                  if(var1 != "trim" && var1 != "peek") {
                     var14 = var7;
                     var8 = var6;
                     if(var1 != "concat") {
                        break label102;
                     }

                     var14 = var7;
                     var8 = var6;
                     if(var11 != 10) {
                        break label102;
                     }

                     ++var6;
                     var0.pos = var6;
                  } else {
                     var14 = var7;
                     if(var1 == "peek") {
                        var14 = 0;
                     }

                     byte var15;
                     if(var11 == 10) {
                        var15 = 1;
                     } else {
                        var8 = var6;
                        if(var6 + 1 >= var10) {
                           break label102;
                        }

                        if(var3[var6 + 1] == 10) {
                           var15 = 2;
                        } else {
                           var15 = 1;
                        }
                     }

                     var0.pos = var6 + var15;
                  }

                  return new FString(var3, var9, var6 - var9);
               }
            } else {
               var8 = var5;
               var14 = var7;
            }

            StringBuffer var4 = new StringBuffer(100);
            if(var8 > var9) {
               var4.append(var3, var9, var8 - var9);
            }

            var0.pos = var8;
            char var2;
            if(var1 == "peek") {
               var2 = 80;
            } else if(var1 != "concat" && var1 != "split") {
               var2 = 73;
            } else {
               var2 = 65;
            }

            var0.readLine(var4, var2);
            int var16 = var4.length();
            var6 = var16;
            if(var1 == "split") {
               if(var16 == 0) {
                  var14 = 0;
                  var6 = var16;
               } else {
                  char var17 = var4.charAt(var16 - 1);
                  if(var17 == 13) {
                     var14 = 1;
                  } else if(var17 != 10) {
                     var14 = 0;
                  } else if(var17 > 2 && var4.charAt(var16 - 2) == 13) {
                     var14 = 2;
                  } else {
                     var14 = 1;
                  }

                  var6 = var16 - var14;
               }
            }

            FString var13 = new FString(var4, 0, var6);
            var12 = var13;
            if(var1 == "split") {
               return new Values(new Object[]{var13, new FString(var4, var6 - var14, var14)});
            }
            break;
         }
      }

      return var12;
   }
}
