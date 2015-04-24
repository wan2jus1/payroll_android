package gnu.math;

import gnu.math.RealNum;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

public class ExponentialFormat extends Format {

   static final double LOG10 = Math.log(10.0D);
   public int expDigits;
   public char exponentChar = 69;
   public boolean exponentShowSign;
   public int fracDigits = -1;
   public boolean general;
   public int intDigits;
   public char overflowChar;
   public char padChar;
   public boolean showPlus;
   public int width;


   static boolean addOne(StringBuffer var0, int var1, int var2) {
      while(var2 != var1) {
         --var2;
         char var3 = var0.charAt(var2);
         if(var3 != 57) {
            var0.setCharAt(var2, (char)(var3 + 1));
            return false;
         }

         var0.setCharAt(var2, '0');
      }

      var0.insert(var2, '1');
      return true;
   }

   StringBuffer format(double var1, String var3, StringBuffer var4, FieldPosition var5) {
      int var19 = this.intDigits;
      int var23 = this.fracDigits;
      boolean var12;
      if(var1 < 0.0D) {
         var12 = true;
      } else {
         var12 = false;
      }

      double var6 = var1;
      if(var12) {
         var6 = -var1;
      }

      int var26 = var4.length();
      byte var9 = 1;
      byte var20;
      if(var12) {
         var20 = var9;
         if(var23 >= 0) {
            var4.append('-');
            var20 = var9;
         }
      } else if(this.showPlus) {
         var4.append('+');
         var20 = var9;
      } else {
         var20 = 0;
      }

      int var11 = var4.length();
      boolean var18;
      if(!Double.isNaN(var6) && !Double.isInfinite(var6)) {
         var18 = false;
      } else {
         var18 = true;
      }

      int var10;
      int var15;
      int var29;
      int var28;
      if(var23 >= 0 && !var18) {
         if(var19 > 0) {
            var28 = 1;
         } else {
            var28 = var19;
         }

         var10 = var23 + var28;
         var28 = (int)(Math.log(var6) / LOG10 + 1000.0D);
         if(var28 == Integer.MIN_VALUE) {
            var28 = 0;
         } else {
            var28 -= 1000;
         }

         var15 = var10 - var28 - 1;
         RealNum.toScaledInt(var6, var15).format(10, (StringBuffer)var4);
         var28 = var10 - 1 - var15;
         var29 = var11;
         var11 = var10;
      } else {
         String var27 = var3;
         if(var3 == null) {
            var27 = Double.toString(var6);
         }

         var28 = var27.indexOf(69);
         if(var28 >= 0) {
            var4.append(var27);
            var15 = var28 + var11;
            boolean var13;
            if(var27.charAt(var15 + 1) == 45) {
               var13 = true;
            } else {
               var13 = false;
            }

            byte var14 = 0;
            if(var13) {
               var9 = 2;
            } else {
               var9 = 1;
            }

            var10 = var15 + var9;

            for(var28 = var14; var10 < var4.length(); ++var10) {
               var28 = var28 * 10 + (var4.charAt(var10) - 48);
            }

            var10 = var28;
            if(var13) {
               var10 = -var28;
            }

            var4.setLength(var15);
            var28 = var10;
         } else {
            var28 = RealNum.toStringScientific(var27, var4);
         }

         var29 = var11;
         if(var12) {
            var29 = var11 + 1;
         }

         var4.deleteCharAt(var29 + 1);
         var10 = var4.length() - var29;
         var11 = var10;
         if(var10 > 1) {
            var11 = var10;
            if(var4.charAt(var29 + var10 - 1) == 48) {
               var11 = var10 - 1;
               var4.setLength(var29 + var11);
            }
         }

         var15 = var11 - var28 - 1;
      }

      int var24 = var28 - (var19 - 1);
      int var21;
      if(var24 < 0) {
         var21 = -var24;
      } else {
         var21 = var24;
      }

      if(var21 >= 1000) {
         var9 = 4;
      } else if(var21 >= 100) {
         var9 = 3;
      } else if(var21 >= 10) {
         var9 = 2;
      } else {
         var9 = 1;
      }

      int var31 = var9;
      if(this.expDigits > var9) {
         var31 = this.expDigits;
      }

      boolean var25 = true;
      if(!this.general) {
         var10 = 0;
      } else if(this.expDigits > 0) {
         var10 = this.expDigits + 2;
      } else {
         var10 = 4;
      }

      boolean var22;
      if(var23 < 0) {
         var22 = true;
      } else {
         var22 = false;
      }

      boolean var17;
      int var16;
      int var30;
      label320: {
         if(!this.general) {
            var30 = var11;
            var16 = var19;
            var17 = var25;
            if(!var22) {
               break label320;
            }
         }

         var16 = var11 - var15;
         var28 = var23;
         if(var22) {
            if(var16 < 7) {
               var30 = var16;
            } else {
               var30 = 7;
            }

            var28 = var30;
            if(var11 > var30) {
               var28 = var11;
            }
         }

         if(this.general && var16 >= 0 && var28 - var16 >= 0) {
            var17 = false;
            var30 = var28;
         } else {
            var30 = var11;
            var16 = var19;
            var17 = var25;
            if(var22) {
               if(this.width <= 0) {
                  var11 = var28;
               } else {
                  var11 = this.width - var20 - var31 - 3;
                  var30 = var11;
                  if(var19 < 0) {
                     var30 = var11 - var19;
                  }

                  var11 = var30;
                  if(var30 > var28) {
                     var11 = var28;
                  }
               }

               var30 = var11;
               var16 = var19;
               var17 = var25;
               if(var11 <= 0) {
                  var30 = 1;
                  var16 = var19;
                  var17 = var25;
               }
            }
         }
      }

      var30 += var29;

      while(var4.length() < var30) {
         var4.append('0');
      }

      char var32;
      if(var30 == var4.length()) {
         var32 = 48;
      } else {
         var32 = var4.charAt(var30);
      }

      boolean var33;
      if(var32 >= 53) {
         var33 = true;
      } else {
         var33 = false;
      }

      if(var33 && addOne(var4, var29, var30)) {
         var11 = var15 + 1;
      }

      var4.length();
      var4.setLength(var30);
      var11 = var29;
      var28 = var30;
      if(var16 < 0) {
         var28 = var16;

         while(true) {
            var30 = var28 + 1;
            var28 = var11;
            if(var30 > 0) {
               break;
            }

            var4.insert(var29, '0');
            var28 = var30;
         }
      } else {
         while(var29 + var16 > var28) {
            var4.append('0');
            ++var28;
         }

         var28 = var29 + var16;
      }

      if(var18) {
         var17 = false;
      } else {
         var4.insert(var28, '.');
      }

      if(var17) {
         var4.append(this.exponentChar);
         if(this.exponentShowSign || var24 < 0) {
            char var8;
            if(var24 >= 0) {
               var8 = 43;
            } else {
               var8 = 45;
            }

            var4.append(var8);
         }

         var15 = var4.length();
         var4.append(var21);
         var11 = var4.length();
         var11 = this.expDigits - (var11 - var15);
         var30 = var31;
         if(var11 > 0) {
            while(true) {
               --var11;
               var30 = var31;
               if(var11 < 0) {
                  break;
               }

               var4.insert(var15, '0');
            }
         }
      } else {
         var30 = 0;
      }

      var11 = var4.length();
      var31 = this.width - (var11 - var26);
      var11 = var31;
      if(var22) {
         label322: {
            if(var28 + 1 != var4.length()) {
               var11 = var31;
               if(var4.charAt(var28 + 1) != this.exponentChar) {
                  break label322;
               }
            }

            if(this.width > 0) {
               var11 = var31;
               if(var31 <= 0) {
                  break label322;
               }
            }

            var11 = var31 - 1;
            var4.insert(var28 + 1, '0');
         }
      }

      if((var11 >= 0 || this.width <= 0) && (!var17 || var30 <= this.expDigits || this.expDigits <= 0 || this.overflowChar == 0)) {
         var28 = var11;
         if(var16 <= 0) {
            label220: {
               if(var11 <= 0) {
                  var28 = var11;
                  if(this.width > 0) {
                     break label220;
                  }
               }

               var4.insert(var29, '0');
               var28 = var11 - 1;
            }
         }

         var11 = var28;
         if(!var17) {
            var11 = var28;
            if(this.width > 0) {
               while(true) {
                  --var10;
                  var11 = var28;
                  if(var10 < 0) {
                     break;
                  }

                  var4.append(' ');
                  --var28;
               }
            }
         }

         while(true) {
            --var11;
            if(var11 < 0) {
               break;
            }

            var4.insert(var26, this.padChar);
         }
      } else if(this.overflowChar != 0) {
         var4.setLength(var26);
         var28 = this.width;

         while(true) {
            --var28;
            if(var28 < 0) {
               break;
            }

            var4.append(this.overflowChar);
         }
      }

      return var4;
   }

   public StringBuffer format(double var1, StringBuffer var3, FieldPosition var4) {
      String var5;
      if(this.fracDigits < 0) {
         var5 = Double.toString(var1);
      } else {
         var5 = null;
      }

      return this.format(var1, var5, var3, var4);
   }

   public StringBuffer format(float var1, StringBuffer var2, FieldPosition var3) {
      double var4 = (double)var1;
      String var6;
      if(this.fracDigits < 0) {
         var6 = Float.toString(var1);
      } else {
         var6 = null;
      }

      return this.format(var4, var6, var2, var3);
   }

   public StringBuffer format(long var1, StringBuffer var3, FieldPosition var4) {
      return this.format((double)var1, var3, var4);
   }

   public StringBuffer format(Object var1, StringBuffer var2, FieldPosition var3) {
      return this.format(((RealNum)var1).doubleValue(), var2, var3);
   }

   public Number parse(String var1, ParsePosition var2) {
      throw new Error("ExponentialFormat.parse - not implemented");
   }

   public Object parseObject(String var1, ParsePosition var2) {
      throw new Error("ExponentialFormat.parseObject - not implemented");
   }
}
