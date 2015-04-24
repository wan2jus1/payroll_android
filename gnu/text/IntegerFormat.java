package gnu.text;

import gnu.text.ReportFormat;
import java.io.IOException;
import java.io.Writer;
import java.math.BigInteger;
import java.text.FieldPosition;

public class IntegerFormat extends ReportFormat {

   public static final int MIN_DIGITS = 64;
   public static final int PAD_RIGHT = 16;
   public static final int SHOW_BASE = 8;
   public static final int SHOW_GROUPS = 1;
   public static final int SHOW_PLUS = 2;
   public static final int SHOW_SPACE = 4;
   public static final int UPPERCASE = 32;
   public int base = 10;
   public int commaChar = 44;
   public int commaInterval = 3;
   public int flags = 0;
   public int minWidth = 1;
   public int padChar = 32;


   public String convertToIntegerString(Object var1, int var2) {
      return !(var1 instanceof Number)?null:(var1 instanceof BigInteger?((BigInteger)var1).toString(var2):Long.toString(((Number)var1).longValue(), var2));
   }

   public int format(Object var1, int var2, Writer var3, FieldPosition var4) throws IOException {
      Object[] var20;
      if(var1 instanceof Object[]) {
         var20 = (Object[])((Object[])var1);
      } else {
         var20 = null;
      }

      int var8 = getParam(this.minWidth, 1, var20, var2);
      int var7 = var2;
      if(this.minWidth == -1610612736) {
         var7 = var2 + 1;
      }

      char var16 = getParam(this.padChar, ' ', var20, var7);
      int var6 = var7;
      if(this.padChar == -1610612736) {
         var6 = var7 + 1;
      }

      char var17 = getParam(this.commaChar, ',', var20, var6);
      var2 = var6;
      if(this.commaChar == -1610612736) {
         var2 = var6 + 1;
      }

      int var18 = getParam(this.commaInterval, 3, var20, var2);
      int var9 = var2;
      if(this.commaInterval == -1610612736) {
         var9 = var2 + 1;
      }

      boolean var10;
      if((this.flags & 1) != 0) {
         var10 = true;
      } else {
         var10 = false;
      }

      boolean var11;
      if((this.flags & 16) != 0) {
         var11 = true;
      } else {
         var11 = false;
      }

      boolean var12;
      if(var16 == 48) {
         var12 = true;
      } else {
         var12 = false;
      }

      if(var20 != null) {
         if(var9 >= var20.length) {
            var3.write("#<missing format argument>");
            return var9;
         }

         var1 = var20[var9];
      }

      String var21 = this.convertToIntegerString(var1, this.base);
      if(var21 != null) {
         char var19 = var21.charAt(0);
         boolean var14;
         if(var19 == 45) {
            var14 = true;
         } else {
            var14 = false;
         }

         int var15 = var21.length();
         if(var14) {
            var6 = var15 - 1;
         } else {
            var6 = var15;
         }

         if(var10) {
            var2 = (var6 - 1) / var18;
         } else {
            var2 = 0;
         }

         label171: {
            var2 += var6;
            if(!var14) {
               var7 = var2;
               if((this.flags & 6) == 0) {
                  break label171;
               }
            }

            var7 = var2 + 1;
         }

         var2 = var7;
         if((this.flags & 8) != 0) {
            if(this.base == 16) {
               var2 = var7 + 2;
            } else {
               var2 = var7;
               if(this.base == 8) {
                  var2 = var7;
                  if(var19 != 48) {
                     var2 = var7 + 1;
                  }
               }
            }
         }

         var7 = var15;
         int var13 = var2;
         if((this.flags & 64) != 0) {
            var7 = var15;
            var13 = var6;
            if(var15 == 1) {
               var7 = var15;
               var13 = var6;
               if(var19 == 48) {
                  var7 = var15;
                  var13 = var6;
                  if(var8 == 0) {
                     var7 = 0;
                     var13 = var6;
                  }
               }
            }
         }

         var2 = var8;
         if(!var11) {
            var2 = var8;
            if(!var12) {
               var6 = var8;

               while(true) {
                  var2 = var6;
                  if(var6 <= var13) {
                     break;
                  }

                  var3.write(var16);
                  --var6;
               }
            }
         }

         byte var24 = 0;
         if(var14) {
            var3.write(45);
            var8 = 0 + 1;
            var6 = var7 - 1;
         } else if((this.flags & 2) != 0) {
            var3.write(43);
            var8 = var24;
            var6 = var7;
         } else {
            var8 = var24;
            var6 = var7;
            if((this.flags & 4) != 0) {
               var3.write(32);
               var8 = var24;
               var6 = var7;
            }
         }

         if(this.base > 10 && (this.flags & 32) != 0) {
            var14 = true;
         } else {
            var14 = false;
         }

         if((this.flags & 8) != 0) {
            if(this.base == 16) {
               var3.write(48);
               byte var23;
               if(var14) {
                  var23 = 88;
               } else {
                  var23 = 120;
               }

               var3.write(var23);
            } else if(this.base == 8 && var19 != 48) {
               var3.write(48);
            }
         }

         var7 = var2;
         if(var12) {
            while(true) {
               var7 = var2;
               if(var2 <= var13) {
                  break;
               }

               var3.write(var16);
               --var2;
            }
         }

         for(var2 = var8; var6 != 0; ++var2) {
            char var5 = var21.charAt(var2);
            char var22 = var5;
            if(var14) {
               var22 = Character.toUpperCase(var5);
            }

            var3.write(var22);
            --var6;
            if(var10 && var6 > 0 && var6 % var18 == 0) {
               var3.write(var17);
            }
         }

         if(var11) {
            while(var7 > var13) {
               var3.write(var16);
               --var7;
            }
         }
      } else {
         print(var3, var1.toString());
      }

      return var9 + 1;
   }

   public int format(Object[] var1, int var2, Writer var3, FieldPosition var4) throws IOException {
      return this.format((Object)var1, var2, var3, var4);
   }
}
