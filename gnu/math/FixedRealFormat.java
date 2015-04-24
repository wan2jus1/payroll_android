package gnu.math;

import gnu.math.Complex;
import gnu.math.DFloNum;
import gnu.math.ExponentialFormat;
import gnu.math.IntNum;
import gnu.math.RatNum;
import gnu.math.RealNum;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

public class FixedRealFormat extends Format {

   private int d;
   private int i;
   public boolean internalPad;
   public char overflowChar;
   public char padChar;
   public int scale;
   public boolean showPlus;
   public int width;


   private void format(StringBuffer var1, FieldPosition var2, int var3, int var4, int var5, int var6, int var7) {
      int var8 = this.getMinimumIntegerDigits();
      int var9;
      if(var4 >= 0 && var4 > var8) {
         var9 = 0;
      } else {
         var9 = var8 - var4;
      }

      var8 = var9;
      if(var4 + var9 <= 0) {
         label53: {
            if(this.width > 0) {
               var8 = var9;
               if(this.width <= var5 + 1 + var6) {
                  break label53;
               }
            }

            var8 = var9 + 1;
         }
      }

      var9 = this.width - (var6 + var3 + var8 + 1);

      while(true) {
         --var8;
         if(var8 < 0) {
            if(var9 >= 0) {
               var3 = var7;
               var4 = var9;
               if(this.internalPad) {
                  var3 = var7;
                  var4 = var9;
                  if(var6 > 0) {
                     var3 = var7 + 1;
                     var4 = var9;
                  }
               }

               while(true) {
                  --var4;
                  if(var4 < 0) {
                     break;
                  }

                  var1.insert(var3, this.padChar);
               }
            } else if(this.overflowChar != 0) {
               var1.setLength(var7);
               this.i = this.width;

               while(true) {
                  var3 = this.i - 1;
                  this.i = var3;
                  if(var3 < 0) {
                     return;
                  }

                  var1.append(this.overflowChar);
               }
            }

            var1.insert(var1.length() - var5, '.');
            return;
         }

         var1.insert(var7 + var6, '0');
      }
   }

   public StringBuffer format(double var1, StringBuffer var3, FieldPosition var4) {
      if(!Double.isNaN(var1) && !Double.isInfinite(var1)) {
         if(this.getMaximumFractionDigits() >= 0) {
            this.format((RealNum)DFloNum.toExact(var1), var3, var4);
            return var3;
         } else {
            boolean var11;
            if(var1 < 0.0D) {
               var11 = true;
               var1 = -var1;
            } else {
               var11 = false;
            }

            int var17 = var3.length();
            byte var12 = 1;
            if(var11) {
               var3.append('-');
            } else if(this.showPlus) {
               var3.append('+');
            } else {
               var12 = 0;
            }

            String var6 = Double.toString(var1);
            int var8 = this.scale;
            int var10 = var6.indexOf(69);
            int var7 = var8;
            String var5 = var6;
            int var9;
            if(var10 >= 0) {
               var9 = var10 + 1;
               var7 = var9;
               if(var6.charAt(var9) == 43) {
                  var7 = var9 + 1;
               }

               var7 = var8 + Integer.parseInt(var6.substring(var7));
               var5 = var6.substring(0, var10);
            }

            var8 = var5.indexOf(46);
            var10 = var5.length();
            var9 = var7;
            var6 = var5;
            if(var8 >= 0) {
               var9 = var7 - (var10 - var8 - 1);
               var6 = var5.substring(0, var8) + var5.substring(var8 + 1);
            }

            var10 = var6.length();

            for(var8 = 0; var8 < var10 - 1 && var6.charAt(var8) == 48; ++var8) {
               ;
            }

            var7 = var10;
            var5 = var6;
            if(var8 > 0) {
               var5 = var6.substring(var8);
               var7 = var10 - var8;
            }

            var8 = var7 + var9;
            if(this.width > 0) {
               while(var8 < 0) {
                  var3.append('0');
                  ++var8;
                  ++var7;
               }

               var10 = this.width - var12 - 1 - var8;
            } else {
               if(var7 > 16) {
                  var10 = 16;
               } else {
                  var10 = var7;
               }

               var10 -= var8;
            }

            int var13 = var10;
            if(var10 < 0) {
               var13 = 0;
            }

            var3.append(var5);

            while(var9 > 0) {
               var3.append('0');
               --var9;
               ++var7;
            }

            int var18 = var17 + var12;
            var7 = var18 + var8 + var13;
            var9 = var3.length();
            char var19;
            if(var7 >= var9) {
               var7 = var9;
               var19 = 48;
            } else {
               var19 = var3.charAt(var7);
            }

            boolean var21;
            if(var19 >= 53) {
               var21 = true;
            } else {
               var21 = false;
            }

            int var14;
            byte var20;
            if(var21) {
               var20 = 57;
               var14 = var7;
            } else {
               var20 = 48;
               var14 = var7;
            }

            while(var14 > var18 + var8 && var3.charAt(var14 - 1) == var20) {
               --var14;
            }

            int var15 = var14 - var18;
            int var16 = var15 - var8;
            var9 = var15;
            var10 = var8;
            var7 = var16;
            if(var21) {
               var9 = var15;
               var10 = var8;
               var7 = var16;
               if(ExponentialFormat.addOne(var3, var18, var14)) {
                  var10 = var8 + 1;
                  var7 = 0;
                  var9 = var10;
               }
            }

            var13 = var9;
            var8 = var7;
            if(var7 == 0) {
               label95: {
                  if(this.width > 0) {
                     var13 = var9;
                     var8 = var7;
                     if(var12 + var10 + 1 >= this.width) {
                        break label95;
                     }
                  }

                  var8 = 1;
                  var13 = var9 + 1;
                  var3.insert(var18 + var10, '0');
               }
            }

            var3.setLength(var18 + var13);
            byte var22;
            if(var11) {
               var22 = 1;
            } else {
               var22 = 0;
            }

            this.format(var3, var4, var13, var10, var8, var22, var17);
            return var3;
         }
      } else {
         return var3.append(var1);
      }
   }

   public StringBuffer format(long var1, StringBuffer var3, FieldPosition var4) {
      this.format((RealNum)IntNum.make(var1), var3, var4);
      return var3;
   }

   public StringBuffer format(Object var1, StringBuffer var2, FieldPosition var3) {
      RealNum var5 = RealNum.asRealNumOrNull(var1);
      RealNum var4 = var5;
      if(var5 == null) {
         if(var1 instanceof Complex) {
            String var7 = var1.toString();
            int var6 = this.width - var7.length();

            while(true) {
               --var6;
               if(var6 < 0) {
                  var2.append(var7);
                  return var2;
               }

               var2.append(' ');
            }
         }

         var4 = (RealNum)var1;
      }

      return this.format(var4.doubleValue(), var2, var3);
   }

   public void format(RealNum var1, StringBuffer var2, FieldPosition var3) {
      if(var1 instanceof RatNum) {
         int var6 = this.getMaximumFractionDigits();
         if(var6 >= 0) {
            RatNum var4 = (RatNum)var1;
            boolean var9 = var4.isNegative();
            RatNum var10 = var4;
            if(var9) {
               var10 = var4.rneg();
            }

            int var7 = var2.length();
            byte var5 = 1;
            if(var9) {
               var2.append('-');
            } else if(this.showPlus) {
               var2.append('+');
            } else {
               var5 = 0;
            }

            String var11 = RealNum.toScaledInt(var10, this.scale + var6).toString();
            var2.append(var11);
            int var8 = var11.length();
            this.format(var2, var3, var8, var8 - var6, var6, var5, var7);
            return;
         }
      }

      this.format(var1.doubleValue(), var2, var3);
   }

   public int getMaximumFractionDigits() {
      return this.d;
   }

   public int getMinimumIntegerDigits() {
      return this.i;
   }

   public Number parse(String var1, ParsePosition var2) {
      throw new Error("RealFixedFormat.parse - not implemented");
   }

   public Object parseObject(String var1, ParsePosition var2) {
      throw new Error("RealFixedFormat.parseObject - not implemented");
   }

   public void setMaximumFractionDigits(int var1) {
      this.d = var1;
   }

   public void setMinimumIntegerDigits(int var1) {
      this.i = var1;
   }
}
