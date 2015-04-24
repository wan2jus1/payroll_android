package gnu.math;

import gnu.math.MPN;
import gnu.math.Numeric;
import gnu.math.RatNum;
import gnu.math.RealNum;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;
import java.math.BigDecimal;
import java.math.BigInteger;

public class IntNum extends RatNum implements Externalizable {

   static final int maxFixNum = 1024;
   static final int minFixNum = -100;
   static final int numFixNum = 1125;
   static final IntNum[] smallFixNums = new IntNum[1125];
   public int ival;
   public int[] words;


   static {
      int var0 = 1125;

      while(true) {
         --var0;
         if(var0 < 0) {
            return;
         }

         smallFixNums[var0] = new IntNum(var0 - 100);
      }
   }

   public IntNum() {
   }

   public IntNum(int var1) {
      this.ival = var1;
   }

   public static IntNum abs(IntNum var0) {
      IntNum var1 = var0;
      if(var0.isNegative()) {
         var1 = neg(var0);
      }

      return var1;
   }

   public static final IntNum add(int var0, int var1) {
      return make((long)var0 + (long)var1);
   }

   public static IntNum add(IntNum var0, int var1) {
      if(var0.words == null) {
         return add(var0.ival, var1);
      } else {
         IntNum var2 = new IntNum(0);
         var2.setAdd(var0, var1);
         return var2.canonicalize();
      }
   }

   public static IntNum add(IntNum var0, IntNum var1) {
      return add(var0, var1, 1);
   }

   public static IntNum add(IntNum var0, IntNum var1, int var2) {
      if(var0.words == null && var1.words == null) {
         return make((long)var2 * (long)var1.ival + (long)var0.ival);
      } else {
         IntNum var3 = var1;
         if(var2 != 1) {
            if(var2 == -1) {
               var3 = neg(var1);
            } else {
               var3 = times(var1, make(var2));
            }
         }

         if(var0.words == null) {
            return add((IntNum)var3, var0.ival);
         } else if(var3.words == null) {
            return add((IntNum)var0, var3.ival);
         } else {
            IntNum var4 = var0;
            var1 = var3;
            if(var3.ival > var0.ival) {
               var1 = var0;
               var4 = var3;
            }

            var0 = alloc(var4.ival + 1);
            var2 = var1.ival;
            long var7 = (long)MPN.add_n(var0.words, var4.words, var1.words, var2);
            long var5;
            if(var1.words[var2 - 1] < 0) {
               var5 = 4294967295L;
            } else {
               var5 = 0L;
            }

            while(var2 < var4.ival) {
               var7 += ((long)var4.words[var2] & 4294967295L) + var5;
               var0.words[var2] = (int)var7;
               var7 >>>= 32;
               ++var2;
            }

            long var9 = var5;
            if(var4.words[var2 - 1] < 0) {
               var9 = var5 - 1L;
            }

            var0.words[var2] = (int)(var7 + var9);
            var0.ival = var2 + 1;
            return var0.canonicalize();
         }
      }
   }

   public static IntNum alloc(int var0) {
      if(var0 <= 1) {
         return new IntNum();
      } else {
         IntNum var1 = new IntNum();
         var1.words = new int[var0];
         return var1;
      }
   }

   public static IntNum asIntNumOrNull(Object var0) {
      return var0 instanceof IntNum?(IntNum)var0:(var0 instanceof BigInteger?valueOf(var0.toString(), 10):(var0 instanceof Number && (var0 instanceof Integer || var0 instanceof Long || var0 instanceof Short || var0 instanceof Byte)?make(((Number)var0).longValue()):null));
   }

   public static int compare(IntNum var0, long var1) {
      long var4;
      if(var0.words == null) {
         var4 = (long)var0.ival;
      } else {
         boolean var7 = var0.isNegative();
         boolean var6;
         if(var1 < 0L) {
            var6 = true;
         } else {
            var6 = false;
         }

         if(var7 != var6) {
            if(!var7) {
               return 1;
            }

            return -1;
         }

         int var3;
         if(var0.words == null) {
            var3 = 1;
         } else {
            var3 = var0.ival;
         }

         if(var3 == 1) {
            var4 = (long)var0.words[0];
         } else {
            if(var3 != 2) {
               if(!var7) {
                  return 1;
               }

               return -1;
            }

            var4 = var0.longValue();
         }
      }

      if(var4 >= var1) {
         if(var4 > var1) {
            return 1;
         } else {
            return 0;
         }
      } else {
         return -1;
      }
   }

   public static int compare(IntNum var0, IntNum var1) {
      boolean var5 = false;
      byte var4 = 1;
      if(var0.words == null && var1.words == null) {
         if(var0.ival >= var1.ival) {
            if(var0.ival > var1.ival) {
               return 1;
            }

            return 0;
         }
      } else {
         boolean var6 = var0.isNegative();
         if(var6 == var1.isNegative()) {
            int var2;
            if(var0.words == null) {
               var2 = 1;
            } else {
               var2 = var0.ival;
            }

            int var3;
            if(var1.words == null) {
               var3 = 1;
            } else {
               var3 = var1.ival;
            }

            if(var2 != var3) {
               if(var2 > var3) {
                  var5 = true;
               }

               byte var7;
               if(var5 != var6) {
                  var7 = var4;
               } else {
                  var7 = -1;
               }

               return var7;
            }

            return MPN.cmp(var0.words, var1.words, var2);
         }

         if(!var6) {
            return 1;
         }
      }

      return -1;
   }

   public static void divide(long var0, long var2, IntNum var4, IntNum var5, int var6) {
      int var7 = var6;
      if(var6 == 5) {
         if(var2 < 0L) {
            var7 = 2;
         } else {
            var7 = 1;
         }
      }

      boolean var15;
      if(var0 < 0L) {
         var15 = true;
         if(var0 == Long.MIN_VALUE) {
            divide(make(var0), make(var2), var4, var5, var7);
            return;
         }

         var0 = -var0;
      } else {
         var15 = false;
      }

      boolean var8;
      if(var2 < 0L) {
         var8 = true;
         if(var2 == Long.MIN_VALUE) {
            if(var7 != 3) {
               divide(make(var0), make(var2), var4, var5, var7);
               return;
            }

            if(var4 != null) {
               var4.set(0);
            }

            if(var5 != null) {
               var5.set(var0);
               return;
            }

            return;
         }

         var2 = -var2;
      } else {
         var8 = false;
      }

      long var11 = var0 / var2;
      long var13 = var0 % var2;
      boolean var10 = var15 ^ var8;
      boolean var9 = false;
      var8 = var9;
      boolean var16;
      if(var13 != 0L) {
         var8 = var9;
         switch(var7) {
         case 1:
         case 2:
            if(var7 == 1) {
               var16 = true;
            } else {
               var16 = false;
            }

            var8 = var9;
            if(var10 == var16) {
               var8 = true;
            }
         case 3:
            break;
         case 4:
            if(var13 > var2 - (1L & var11) >> 1) {
               var8 = true;
            } else {
               var8 = false;
            }
            break;
         default:
            var8 = var9;
         }
      }

      if(var4 != null) {
         var0 = var11;
         if(var8) {
            var0 = var11 + 1L;
         }

         var11 = var0;
         if(var10) {
            var11 = -var0;
         }

         var4.set(var11);
      }

      if(var5 != null) {
         var0 = var13;
         var16 = var15;
         if(var8) {
            var0 = var2 - var13;
            if(!var15) {
               var16 = true;
            } else {
               var16 = false;
            }
         }

         var2 = var0;
         if(var16) {
            var2 = -var0;
         }

         var5.set(var2);
      }
   }

   public static void divide(IntNum var0, IntNum var1, IntNum var2, IntNum var3, int var4) {
      if((var0.words == null || var0.ival <= 2) && (var1.words == null || var1.ival <= 2)) {
         long var15 = var0.longValue();
         long var17 = var1.longValue();
         if(var15 != Long.MIN_VALUE && var17 != Long.MIN_VALUE) {
            divide(var15, var17, var2, var3, var4);
            return;
         }
      }

      boolean var19 = var0.isNegative();
      boolean var20 = var1.isNegative();
      boolean var14 = var19 ^ var20;
      int var10;
      if(var1.words == null) {
         var10 = 1;
      } else {
         var10 = var1.ival;
      }

      int[] var7 = new int[var10];
      var1.getAbsolute(var7);

      while(var10 > 1 && var7[var10 - 1] == 0) {
         --var10;
      }

      int var8;
      if(var0.words == null) {
         var8 = 1;
      } else {
         var8 = var0.ival;
      }

      int[] var6 = new int[var8 + 2];
      var0.getAbsolute(var6);

      while(var8 > 1 && var6[var8 - 1] == 0) {
         --var8;
      }

      int var9 = MPN.cmp(var6, var8, var7, var10);
      int[] var5;
      int var11;
      int[] var21;
      if(var9 < 0) {
         var5 = var7;
         var21 = var6;
         var9 = 1;
         var7[0] = 0;
      } else if(var9 == 0) {
         var6[0] = 1;
         var9 = 1;
         var7[0] = 0;
         var8 = 1;
         var5 = var6;
         var21 = var7;
      } else if(var10 == 1) {
         var9 = var8;
         byte var25 = 1;
         var7[0] = MPN.divmod_1(var6, var6, var8, var7[0]);
         var8 = var25;
         var5 = var6;
         var21 = var7;
      } else {
         int var12 = MPN.count_leading_zeros(var7[var10 - 1]);
         var9 = var8;
         if(var12 != 0) {
            MPN.lshift(var7, 0, var7, var10, var12);
            var6[var8] = MPN.lshift(var6, 0, var6, var8, var12);
            var9 = var8 + 1;
         }

         if(var9 == var10) {
            var8 = var9 + 1;
            var6[var9] = 0;
         } else {
            var8 = var9;
         }

         MPN.divide(var6, var8, var7, var10);
         var11 = var10;
         MPN.rshift0(var7, var6, 0, var10, var12);
         int var13 = var8 + 1 - var10;
         var9 = var13;
         var8 = var10;
         var5 = var6;
         var21 = var7;
         if(var2 != null) {
            var12 = 0;

            while(true) {
               var9 = var13;
               var8 = var11;
               var5 = var6;
               var21 = var7;
               if(var12 >= var13) {
                  break;
               }

               var6[var12] = var6[var12 + var10];
               ++var12;
            }
         }
      }

      while(var8 > 1 && var21[var8 - 1] == 0) {
         --var8;
      }

      var11 = var8;
      if(var21[var8 - 1] < 0) {
         var21[var8] = 0;
         var11 = var8 + 1;
      }

      boolean var26;
      label205: {
         boolean var27 = false;
         if(var11 <= 1) {
            var26 = var27;
            if(var21[0] == 0) {
               break label205;
            }
         }

         var10 = var4;
         if(var4 == 5) {
            if(var20) {
               var10 = 2;
            } else {
               var10 = 1;
            }
         }

         var26 = var27;
         switch(var10) {
         case 1:
         case 2:
            boolean var22;
            if(var10 == 1) {
               var22 = true;
            } else {
               var22 = false;
            }

            var26 = var27;
            if(var14 == var22) {
               var26 = true;
            }
         case 3:
            break;
         case 4:
            IntNum var23;
            if(var3 == null) {
               var23 = new IntNum();
            } else {
               var23 = var3;
            }

            var23.set(var21, var11);
            var23 = shift(var23, 1);
            if(var20) {
               var23.setNegative();
            }

            var8 = compare(var23, var1);
            var4 = var8;
            if(var20) {
               var4 = -var8;
            }

            if(var4 != 1 && (var4 != 0 || (var5[0] & 1) == 0)) {
               var26 = false;
            } else {
               var26 = true;
            }
            break;
         default:
            var26 = var27;
         }
      }

      if(var2 != null) {
         var4 = var9;
         if(var5[var9 - 1] < 0) {
            var5[var9] = 0;
            var4 = var9 + 1;
         }

         var2.set(var5, var4);
         if(var14) {
            if(var26) {
               var2.setInvert();
            } else {
               var2.setNegative();
            }
         } else if(var26) {
            var2.setAdd(1);
         }
      }

      if(var3 != null) {
         var3.set(var21, var11);
         if(var26) {
            if(var1.words == null) {
               var2 = var3;
               if(var20) {
                  var4 = var21[0] + var1.ival;
               } else {
                  var4 = var21[0] - var1.ival;
               }

               var3.set(var4);
            } else {
               byte var24;
               if(var20) {
                  var24 = 1;
               } else {
                  var24 = -1;
               }

               var2 = add(var3, var1, var24);
            }

            if(var19) {
               var3.setNegative(var2);
               return;
            }

            var3.set(var2);
            return;
         }

         if(var19) {
            var3.setNegative();
            return;
         }
      }

   }

   public static boolean equals(IntNum var0, IntNum var1) {
      if(var0.words == null && var1.words == null) {
         if(var0.ival != var1.ival) {
            return false;
         }
      } else {
         if(var0.words == null || var1.words == null || var0.ival != var1.ival) {
            return false;
         }

         int var2 = var0.ival;

         while(true) {
            int var3 = var2 - 1;
            if(var3 < 0) {
               break;
            }

            var2 = var3;
            if(var0.words[var3] != var1.words[var3]) {
               return false;
            }
         }
      }

      return true;
   }

   public static final int gcd(int var0, int var1) {
      int var3 = var0;
      int var2 = var1;
      if(var1 > var0) {
         var2 = var0;
         var3 = var1;
      }

      while(var2 != 0) {
         if(var2 == 1) {
            return var2;
         }

         var0 = var3 % var2;
         var3 = var2;
         var2 = var0;
      }

      return var3;
   }

   public static IntNum gcd(IntNum var0, IntNum var1) {
      int var5 = var0.ival;
      int var6 = var1.ival;
      int var4 = var5;
      if(var0.words == null) {
         if(var5 == 0) {
            return abs(var1);
         }

         if(var1.words == null && var5 != Integer.MIN_VALUE && var6 != Integer.MIN_VALUE) {
            var4 = var5;
            if(var5 < 0) {
               var4 = -var5;
            }

            var5 = var6;
            if(var6 < 0) {
               var5 = -var6;
            }

            return make(gcd(var4, var5));
         }

         var4 = 1;
      }

      var5 = var6;
      if(var1.words == null) {
         if(var6 == 0) {
            return abs(var0);
         }

         var5 = 1;
      }

      if(var4 <= var5) {
         var4 = var5;
      }

      int[] var2 = new int[var4];
      int[] var3 = new int[var4];
      var0.getAbsolute(var2);
      var1.getAbsolute(var3);
      var5 = MPN.gcd(var2, var3, var4);
      var0 = new IntNum(0);
      var4 = var5;
      if(var2[var5 - 1] < 0) {
         var2[var5] = 0;
         var4 = var5 + 1;
      }

      var0.ival = var4;
      var0.words = var2;
      return var0.canonicalize();
   }

   public static int intValue(Object var0) {
      IntNum var1 = (IntNum)var0;
      if(var1.words != null) {
         throw new ClassCastException("integer too large");
      } else {
         return var1.ival;
      }
   }

   public static IntNum lcm(IntNum var0, IntNum var1) {
      if(!var0.isZero() && !var1.isZero()) {
         var0 = abs(var0);
         var1 = abs(var1);
         IntNum var2 = new IntNum();
         divide(times(var0, var1), gcd(var0, var1), var2, (IntNum)null, 3);
         return var2.canonicalize();
      } else {
         return zero();
      }
   }

   public static IntNum make(int var0) {
      return var0 >= -100 && var0 <= 1024?smallFixNums[var0 + 100]:new IntNum(var0);
   }

   public static IntNum make(long var0) {
      if(var0 >= -100L && var0 <= 1024L) {
         return smallFixNums[(int)var0 + 100];
      } else {
         int var3 = (int)var0;
         if((long)var3 == var0) {
            return new IntNum(var3);
         } else {
            IntNum var2 = alloc(2);
            var2.ival = 2;
            var2.words[0] = var3;
            var2.words[1] = (int)(var0 >> 32);
            return var2;
         }
      }
   }

   public static IntNum make(int[] var0) {
      return make(var0, var0.length);
   }

   public static IntNum make(int[] var0, int var1) {
      if(var0 == null) {
         return make(var1);
      } else {
         var1 = wordsNeeded(var0, var1);
         if(var1 <= 1) {
            return var1 == 0?zero():make(var0[0]);
         } else {
            IntNum var2 = new IntNum();
            var2.words = var0;
            var2.ival = var1;
            return var2;
         }
      }
   }

   public static IntNum makeU(long var0) {
      if(var0 >= 0L) {
         return make(var0);
      } else {
         IntNum var2 = alloc(3);
         var2.ival = 3;
         var2.words[0] = (int)var0;
         var2.words[1] = (int)(var0 >> 32);
         var2.words[2] = 0;
         return var2;
      }
   }

   public static IntNum minusOne() {
      return smallFixNums[99];
   }

   public static IntNum modulo(IntNum var0, IntNum var1) {
      return remainder(var0, var1, 1);
   }

   public static IntNum neg(IntNum var0) {
      if(var0.words == null && var0.ival != Integer.MIN_VALUE) {
         return make(-var0.ival);
      } else {
         IntNum var1 = new IntNum(0);
         var1.setNegative(var0);
         return var1.canonicalize();
      }
   }

   public static boolean negate(int[] var0, int[] var1, int var2) {
      long var5 = 1L;
      boolean var3;
      if(var1[var2 - 1] < 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      for(int var4 = 0; var4 < var2; ++var4) {
         var5 += (long)(~var1[var4]) & 4294967295L;
         var0[var4] = (int)var5;
         var5 >>= 32;
      }

      return var3 && var0[var2 - 1] < 0;
   }

   public static final IntNum one() {
      return smallFixNums[101];
   }

   public static IntNum power(IntNum var0, int var1) {
      IntNum var2;
      if(var1 <= 0) {
         if(var1 != 0) {
            throw new Error("negative exponent");
         }

         var2 = one();
      } else {
         var2 = var0;
         if(!var0.isZero()) {
            int var7;
            if(var0.words == null) {
               var7 = 1;
            } else {
               var7 = var0.ival;
            }

            int var9 = (var0.intLength() * var1 >> 5) + var7 * 2;
            boolean var8;
            if(var0.isNegative() && (var1 & 1) != 0) {
               var8 = true;
            } else {
               var8 = false;
            }

            int[] var14 = new int[var9];
            int[] var3 = new int[var9];
            int[] var4 = new int[var9];
            var0.getAbsolute(var14);
            int var11 = 1;
            var3[0] = 1;
            var9 = var1;
            int[] var13 = var4;
            int var10 = var7;

            while(true) {
               var1 = var11;
               int[] var5 = var3;
               var4 = var13;
               if((var9 & 1) != 0) {
                  MPN.mul(var13, var14, var10, var3, var11);
                  var7 = var11 + var10;

                  while(true) {
                     var1 = var7;
                     var5 = var13;
                     var4 = var3;
                     if(var13[var7 - 1] != 0) {
                        break;
                     }

                     --var7;
                  }
               }

               int var12 = var9 >> 1;
               if(var12 == 0) {
                  var7 = var1;
                  if(var5[var1 - 1] < 0) {
                     var7 = var1 + 1;
                  }

                  if(var8) {
                     negate(var5, var5, var7);
                  }

                  return make(var5, var7);
               }

               MPN.mul(var4, var14, var10, var14, var10);
               int[] var6 = var14;
               var7 = var10 * 2;

               while(true) {
                  var10 = var7;
                  var14 = var4;
                  var11 = var1;
                  var3 = var5;
                  var13 = var6;
                  var9 = var12;
                  if(var4[var7 - 1] != 0) {
                     break;
                  }

                  --var7;
               }
            }
         }
      }

      return var2;
   }

   public static IntNum quotient(IntNum var0, IntNum var1) {
      return quotient(var0, var1, 3);
   }

   public static IntNum quotient(IntNum var0, IntNum var1, int var2) {
      IntNum var3 = new IntNum();
      divide(var0, var1, var3, (IntNum)null, var2);
      return var3.canonicalize();
   }

   public static IntNum remainder(IntNum var0, IntNum var1) {
      return remainder(var0, var1, 3);
   }

   public static IntNum remainder(IntNum var0, IntNum var1, int var2) {
      if(var1.isZero()) {
         return var0;
      } else {
         IntNum var3 = new IntNum();
         divide(var0, var1, (IntNum)null, var3, var2);
         return var3.canonicalize();
      }
   }

   public static int shift(int var0, int var1) {
      if(var1 < 32) {
         if(var1 >= 0) {
            return var0 << var1;
         }

         var1 = -var1;
         if(var1 < 32) {
            return var0 >> var1;
         }

         if(var0 < 0) {
            return -1;
         }
      }

      return 0;
   }

   public static long shift(long var0, int var2) {
      if(var2 < 32) {
         if(var2 >= 0) {
            return var0 << var2;
         }

         var2 = -var2;
         if(var2 < 32) {
            return var0 >> var2;
         }

         if(var0 < 0L) {
            return -1L;
         }
      }

      return 0L;
   }

   public static IntNum shift(IntNum var0, int var1) {
      byte var3 = 0;
      IntNum var2;
      if(var0.words == null) {
         if(var1 <= 0) {
            if(var1 > -32) {
               var1 = var0.ival >> -var1;
            } else {
               var1 = var3;
               if(var0.ival < 0) {
                  var1 = -1;
               }
            }

            var2 = make(var1);
            return var2;
         }

         if(var1 < 32) {
            return make((long)var0.ival << var1);
         }
      }

      var2 = var0;
      if(var1 != 0) {
         var2 = new IntNum(0);
         var2.setShift(var0, var1);
         return var2.canonicalize();
      } else {
         return var2;
      }
   }

   public static IntNum sub(IntNum var0, IntNum var1) {
      return add(var0, var1, -1);
   }

   public static final IntNum ten() {
      return smallFixNums[110];
   }

   public static final IntNum times(int var0, int var1) {
      return make((long)var0 * (long)var1);
   }

   public static final IntNum times(IntNum var0, int var1) {
      IntNum var2;
      if(var1 == 0) {
         var2 = zero();
      } else {
         var2 = var0;
         if(var1 != 1) {
            int[] var9 = var0.words;
            int var7 = var0.ival;
            if(var9 == null) {
               return make((long)var7 * (long)var1);
            }

            IntNum var3 = alloc(var7 + 1);
            boolean var4;
            int[] var8;
            if(var9[var7 - 1] < 0) {
               var4 = true;
               negate(var3.words, var9, var7);
               var8 = var3.words;
            } else {
               var4 = false;
               var8 = var9;
            }

            boolean var6 = var4;
            int var5 = var1;
            if(var1 < 0) {
               if(!var4) {
                  var4 = true;
               } else {
                  var4 = false;
               }

               var5 = -var1;
               var6 = var4;
            }

            var3.words[var7] = MPN.mul_1(var3.words, var8, var7, var5);
            var3.ival = var7 + 1;
            if(var6) {
               var3.setNegative();
            }

            return var3.canonicalize();
         }
      }

      return var2;
   }

   public static final IntNum times(IntNum var0, IntNum var1) {
      if(var1.words == null) {
         return times(var0, var1.ival);
      } else if(var0.words == null) {
         return times(var1, var0.ival);
      } else {
         int var5 = var0.ival;
         int var6 = var1.ival;
         int[] var2;
         boolean var4;
         int[] var9;
         if(var0.isNegative()) {
            var4 = true;
            var2 = new int[var5];
            negate(var2, var0.words, var5);
            var9 = var2;
         } else {
            var4 = false;
            var9 = var0.words;
         }

         int[] var10;
         if(var1.isNegative()) {
            if(!var4) {
               var4 = true;
            } else {
               var4 = false;
            }

            var2 = new int[var6];
            negate(var2, var1.words, var6);
            var10 = var2;
         } else {
            var10 = var1.words;
         }

         int var8 = var5;
         int[] var3 = var9;
         int var7 = var6;
         var2 = var10;
         if(var5 < var6) {
            var2 = var9;
            var7 = var5;
            var3 = var10;
            var8 = var6;
         }

         var0 = alloc(var8 + var7);
         MPN.mul(var0.words, var3, var8, var2, var7);
         var0.ival = var8 + var7;
         if(var4) {
            var0.setNegative();
         }

         return var0.canonicalize();
      }
   }

   public static IntNum valueOf(String var0) throws NumberFormatException {
      return valueOf(var0, 10);
   }

   public static IntNum valueOf(String var0, int var1) throws NumberFormatException {
      int var7 = var0.length();
      if(var7 + var1 <= 28) {
         String var10 = var0;
         if(var7 > 1) {
            var10 = var0;
            if(var0.charAt(0) == 43) {
               var10 = var0;
               if(Character.digit(var0.charAt(1), var1) >= 0) {
                  var10 = var0.substring(1);
               }
            }
         }

         return make(Long.parseLong(var10, var1));
      } else {
         byte[] var3 = new byte[var7];
         boolean var9 = false;
         int var5 = 0;

         int var4;
         for(var4 = 0; var5 < var7; ++var5) {
            char var2 = var0.charAt(var5);
            if(var2 == 45 && var5 == 0) {
               var9 = true;
            } else if((var2 != 43 || var5 != 0) && var2 != 95 && (var4 != 0 || var2 != 32 && var2 != 9)) {
               int var8 = Character.digit(var2, var1);
               if(var8 < 0) {
                  throw new NumberFormatException("For input string: \"" + var0 + '\"');
               }

               int var6 = var4 + 1;
               var3[var4] = (byte)var8;
               var4 = var6;
            }
         }

         return valueOf(var3, var4, var9, var1);
      }
   }

   public static IntNum valueOf(byte[] var0, int var1, boolean var2, int var3) {
      int[] var4 = new int[var1 / MPN.chars_per_word(var3) + 1];
      var3 = MPN.set_str(var4, var0, var1, var3);
      if(var3 == 0) {
         return zero();
      } else {
         var1 = var3;
         if(var4[var3 - 1] < 0) {
            var4[var3] = 0;
            var1 = var3 + 1;
         }

         if(var2) {
            negate(var4, var4, var1);
         }

         return make(var4, var1);
      }
   }

   public static IntNum valueOf(char[] var0, int var1, int var2, int var3, boolean var4) {
      byte[] var6 = new byte[var2];
      int var8 = 0;

      int var7;
      for(var7 = 0; var8 < var2; ++var8) {
         char var5 = var0[var1 + var8];
         if(var5 == 45) {
            var4 = true;
         } else if(var5 != 95 && (var7 != 0 || var5 != 32 && var5 != 9)) {
            int var10 = Character.digit(var5, var3);
            if(var10 < 0) {
               break;
            }

            int var9 = var7 + 1;
            var6[var7] = (byte)var10;
            var7 = var9;
         }
      }

      return valueOf(var6, var7, var4, var3);
   }

   public static int wordsNeeded(int[] var0, int var1) {
      int var2 = var1;
      var1 = var1;
      if(var2 > 0) {
         var1 = var2 - 1;
         int var4 = var0[var1];
         var2 = var1;
         int var3 = var4;
         if(var4 == -1) {
            var2 = var1;

            do {
               var1 = var2;
               if(var2 <= 0) {
                  break;
               }

               var3 = var0[var2 - 1];
               var1 = var2;
               if(var3 >= 0) {
                  break;
               }

               var1 = var2 - 1;
               var2 = var1;
            } while(var3 == -1);
         } else {
            while(true) {
               var1 = var2;
               if(var3 != 0) {
                  break;
               }

               var1 = var2;
               if(var2 <= 0) {
                  break;
               }

               var3 = var0[var2 - 1];
               var1 = var2;
               if(var3 < 0) {
                  break;
               }

               --var2;
            }
         }
      }

      return var1 + 1;
   }

   public static final IntNum zero() {
      return smallFixNums[100];
   }

   public Numeric add(Object var1, int var2) {
      if(var1 instanceof IntNum) {
         return add(this, (IntNum)var1, var2);
      } else if(!(var1 instanceof Numeric)) {
         throw new IllegalArgumentException();
      } else {
         return ((Numeric)var1).addReversed(this, var2);
      }
   }

   public BigDecimal asBigDecimal() {
      return this.words == null?new BigDecimal(this.ival):(this.ival <= 2?BigDecimal.valueOf(this.longValue()):new BigDecimal(this.toString()));
   }

   public BigInteger asBigInteger() {
      return this.words != null && this.ival > 2?new BigInteger(this.toString()):BigInteger.valueOf(this.longValue());
   }

   public IntNum canonicalize() {
      if(this.words != null) {
         int var2 = wordsNeeded(this.words, this.ival);
         this.ival = var2;
         if(var2 <= 1) {
            if(this.ival == 1) {
               this.ival = this.words[0];
            }

            this.words = null;
         }
      }

      IntNum var1 = this;
      if(this.words == null) {
         var1 = this;
         if(this.ival >= -100) {
            var1 = this;
            if(this.ival <= 1024) {
               var1 = smallFixNums[this.ival + 100];
            }
         }
      }

      return var1;
   }

   boolean checkBits(int var1) {
      boolean var3 = true;
      if(var1 > 0) {
         if(this.words != null) {
            int var2;
            for(var2 = 0; var2 < var1 >> 5; ++var2) {
               if(this.words[var2] != 0) {
                  return true;
               }
            }

            if((var1 & 31) == 0 || (this.words[var2] & (1 << (var1 & 31)) - 1) == 0) {
               var3 = false;
            }

            return var3;
         }

         if(var1 > 31 || (this.ival & (1 << var1) - 1) != 0) {
            return true;
         }
      }

      return false;
   }

   public int compare(Object var1) {
      return var1 instanceof IntNum?compare(this, (IntNum)var1):((RealNum)var1).compareReversed(this);
   }

   public final IntNum denominator() {
      return one();
   }

   public Numeric div(Object var1) {
      if(var1 instanceof RatNum) {
         RatNum var2 = (RatNum)var1;
         return RatNum.make(times(this, var2.denominator()), var2.numerator());
      } else if(!(var1 instanceof Numeric)) {
         throw new IllegalArgumentException();
      } else {
         return ((Numeric)var1).divReversed(this);
      }
   }

   public double doubleValue() {
      return this.words == null?(double)this.ival:(this.ival <= 2?(double)this.longValue():(this.isNegative()?neg(this).roundToDouble(0, true, false):this.roundToDouble(0, false, false)));
   }

   public boolean equals(Object var1) {
      return var1 != null && var1 instanceof IntNum?equals(this, (IntNum)var1):false;
   }

   public void format(int var1, StringBuffer var2) {
      if(var1 == 10) {
         if(this.words == null) {
            var2.append(this.ival);
            return;
         }

         if(this.ival <= 2) {
            var2.append(this.longValue());
            return;
         }
      }

      var2.append(this.toString(var1));
   }

   public void format(int var1, StringBuilder var2) {
      if(this.words == null) {
         if(var1 != 10) {
            var2.append(Integer.toString(this.ival, var1));
            return;
         }

         var2.append(this.ival);
      } else {
         if(this.ival <= 2) {
            long var12 = this.longValue();
            if(var1 == 10) {
               var2.append(var12);
               return;
            }

            var2.append(Long.toString(var12, var1));
            return;
         }

         boolean var14 = this.isNegative();
         int[] var4;
         if(!var14 && var1 == 16) {
            var4 = this.words;
         } else {
            var4 = new int[this.ival];
            this.getAbsolute(var4);
         }

         int var5 = this.ival;
         int var6;
         int var7;
         int var8;
         int var9;
         if(var1 == 16) {
            if(var14) {
               var2.append('-');
            }

            var7 = var2.length();
            var1 = var5;

            while(true) {
               var5 = var1 - 1;
               if(var5 < 0) {
                  break;
               }

               var8 = var4[var5];
               var1 = 8;

               while(true) {
                  var6 = var1 - 1;
                  var1 = var5;
                  if(var6 < 0) {
                     break;
                  }

                  var9 = var8 >> var6 * 4 & 15;
                  if(var9 <= 0) {
                     var1 = var6;
                     if(var2.length() <= var7) {
                        continue;
                     }
                  }

                  var2.append(Character.forDigit(var9, 16));
                  var1 = var6;
               }
            }
         } else {
            var6 = MPN.chars_per_word(var1);
            var7 = var1;
            var8 = var6;

            while(true) {
               --var8;
               if(var8 <= 0) {
                  int var10 = var2.length();

                  do {
                     int var11 = MPN.divmod_1(var4, var4, var5, var7);

                     for(var8 = var5; var8 > 0 && var4[var8 - 1] == 0; --var8) {
                        ;
                     }

                     var9 = var6;
                     var5 = var11;

                     while(true) {
                        var11 = var9 - 1;
                        if(var11 < 0 || var8 == 0 && var5 == 0) {
                           var5 = var8;
                           break;
                        }

                        if(var5 < 0) {
                           var9 = (int)(((long)var5 & -1L) % (long)var1);
                           var5 /= var1;
                        } else {
                           var9 = var5 % var1;
                           var5 /= var1;
                        }

                        var2.append(Character.forDigit(var9, var1));
                        var9 = var11;
                     }
                  } while(var8 != 0);

                  if(var14) {
                     var2.append('-');
                  }

                  var1 = var2.length() - 1;

                  for(var5 = var10; var5 < var1; --var1) {
                     char var3 = var2.charAt(var5);
                     var2.setCharAt(var5, var2.charAt(var1));
                     var2.setCharAt(var1, var3);
                     ++var5;
                  }
                  break;
               }

               var7 *= var1;
            }
         }
      }

   }

   public void getAbsolute(int[] var1) {
      int var2;
      int var3;
      if(this.words == null) {
         var2 = 1;
         var1[0] = this.ival;
      } else {
         var3 = this.ival;
         var2 = var3;

         while(true) {
            int var4 = var2 - 1;
            var2 = var3;
            if(var4 < 0) {
               break;
            }

            var1[var4] = this.words[var4];
            var2 = var4;
         }
      }

      if(var1[var2 - 1] < 0) {
         negate(var1, var1, var2);
      }

      var3 = var1.length;

      while(true) {
         --var3;
         if(var3 <= var2) {
            return;
         }

         var1[var3] = 0;
      }
   }

   public int hashCode() {
      return this.words == null?this.ival:this.words[0] + this.words[this.ival - 1];
   }

   public boolean inIntRange() {
      return this.inRange(-2147483648L, 2147483647L);
   }

   public boolean inLongRange() {
      return this.inRange(Long.MIN_VALUE, Long.MAX_VALUE);
   }

   public boolean inRange(long var1, long var3) {
      return compare(this, var1) >= 0 && compare(this, var3) <= 0;
   }

   public int intLength() {
      return this.words == null?MPN.intLength(this.ival):MPN.intLength(this.words, this.ival);
   }

   public int intValue() {
      return this.words == null?this.ival:this.words[0];
   }

   public final boolean isMinusOne() {
      return this.words == null && this.ival == -1;
   }

   public final boolean isNegative() {
      int var1;
      if(this.words == null) {
         var1 = this.ival;
      } else {
         var1 = this.words[this.ival - 1];
      }

      return var1 < 0;
   }

   public final boolean isOdd() {
      boolean var2 = false;
      int var1;
      if(this.words == null) {
         var1 = this.ival;
      } else {
         var1 = this.words[0];
      }

      if((var1 & 1) != 0) {
         var2 = true;
      }

      return var2;
   }

   public final boolean isOne() {
      return this.words == null && this.ival == 1;
   }

   public final boolean isZero() {
      return this.words == null && this.ival == 0;
   }

   public long longValue() {
      return this.words == null?(long)this.ival:(this.ival == 1?(long)this.words[0]:((long)this.words[1] << 32) + ((long)this.words[0] & 4294967295L));
   }

   public Numeric mul(Object var1) {
      if(var1 instanceof IntNum) {
         return times(this, (IntNum)var1);
      } else if(!(var1 instanceof Numeric)) {
         throw new IllegalArgumentException();
      } else {
         return ((Numeric)var1).mulReversed(this);
      }
   }

   public Numeric neg() {
      return neg(this);
   }

   public final IntNum numerator() {
      return this;
   }

   public Numeric power(IntNum var1) {
      if(!this.isOne()) {
         if(this.isMinusOne()) {
            if(!var1.isOdd()) {
               return one();
            }
         } else {
            if(var1.words == null && var1.ival >= 0) {
               return power(this, var1.ival);
            }

            if(!this.isZero()) {
               return super.power(var1);
            }

            if(var1.isNegative()) {
               return RatNum.infinity(-1);
            }
         }
      }

      return this;
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      int var4 = var1.readInt();
      int var3 = var4;
      if(var4 <= -1073741824) {
         var3 = var4 & Integer.MAX_VALUE;
         if(var3 == 1) {
            var3 = var1.readInt();
         } else {
            int[] var2 = new int[var3];
            var4 = var3;

            while(true) {
               --var4;
               if(var4 < 0) {
                  this.words = var2;
                  break;
               }

               var2[var4] = var1.readInt();
            }
         }
      }

      this.ival = var3;
   }

   public Object readResolve() throws ObjectStreamException {
      return this.canonicalize();
   }

   public void realloc(int var1) {
      if(var1 == 0) {
         if(this.words != null) {
            if(this.ival > 0) {
               this.ival = this.words[0];
            }

            this.words = null;
         }
      } else if(this.words == null || this.words.length < var1 || this.words.length > var1 + 2) {
         int[] var2 = new int[var1];
         if(this.words == null) {
            var2[0] = this.ival;
            this.ival = 1;
         } else {
            if(var1 < this.ival) {
               this.ival = var1;
            }

            System.arraycopy(this.words, 0, var2, 0, this.ival);
         }

         this.words = var2;
         return;
      }

   }

   public double roundToDouble(int var1, boolean var2, boolean var3) {
      int var6 = this.intLength();
      int var5 = var1 + (var6 - 1);
      if(var5 < -1075) {
         return var2?0.0D:0.0D;
      } else if(var5 > 1023) {
         return var2?Double.NEGATIVE_INFINITY:Double.POSITIVE_INFINITY;
      } else {
         int var4;
         if(var5 >= -1022) {
            var4 = 53;
         } else {
            var4 = var5 + 53 + 1022;
         }

         int var7 = var6 - (var4 + 1);
         long var8;
         if(var7 > 0) {
            if(this.words == null) {
               var8 = (long)(this.ival >> var7);
            } else {
               var8 = MPN.rshift_long(this.words, this.ival, var7);
            }
         } else {
            var8 = this.longValue() << -var7;
         }

         if(var5 == 1023 && var8 >> 1 == 9007199254740991L) {
            return !var3 && !this.checkBits(var6 - var4)?(var2?-1.7976931348623157E308D:Double.MAX_VALUE):(var2?Double.NEGATIVE_INFINITY:Double.POSITIVE_INFINITY);
         } else {
            long var10 = var8;
            var1 = var5;
            if((1L & var8) == 1L) {
               label76: {
                  if((2L & var8) != 2L && !var3) {
                     var10 = var8;
                     var1 = var5;
                     if(!this.checkBits(var7)) {
                        break label76;
                     }
                  }

                  var8 += 2L;
                  if((18014398509481984L & var8) != 0L) {
                     var1 = var5 + 1;
                     var10 = var8 >> 1;
                  } else {
                     var10 = var8;
                     var1 = var5;
                     if(var4 == 52) {
                        var10 = var8;
                        var1 = var5;
                        if((9007199254740992L & var8) != 0L) {
                           var1 = var5 + 1;
                           var10 = var8;
                        }
                     }
                  }
               }
            }

            if(var2) {
               var8 = Long.MIN_VALUE;
            } else {
               var8 = 0L;
            }

            var1 += 1023;
            long var12;
            if(var1 <= 0) {
               var12 = 0L;
            } else {
               var12 = (long)var1 << 52;
            }

            return Double.longBitsToDouble(var8 | var12 | var10 >> 1 & -4503599627370497L);
         }
      }
   }

   public final void set(int var1) {
      this.words = null;
      this.ival = var1;
   }

   public final void set(long var1) {
      int var3 = (int)var1;
      if((long)var3 == var1) {
         this.ival = var3;
         this.words = null;
      } else {
         this.realloc(2);
         this.words[0] = var3;
         this.words[1] = (int)(var1 >> 32);
         this.ival = 2;
      }
   }

   public final void set(IntNum var1) {
      if(var1.words == null) {
         this.set(var1.ival);
      } else if(this != var1) {
         this.realloc(var1.ival);
         System.arraycopy(var1.words, 0, this.words, 0, var1.ival);
         this.ival = var1.ival;
         return;
      }

   }

   public final void set(int[] var1, int var2) {
      this.ival = var2;
      this.words = var1;
   }

   public final void setAdd(int var1) {
      this.setAdd(this, var1);
   }

   public void setAdd(IntNum var1, int var2) {
      if(var1.words == null) {
         this.set((long)var1.ival + (long)var2);
      } else {
         int var3 = var1.ival;
         this.realloc(var3 + 1);
         long var4 = (long)var2;

         for(var2 = 0; var2 < var3; ++var2) {
            var4 += (long)var1.words[var2] & 4294967295L;
            this.words[var2] = (int)var4;
            var4 >>= 32;
         }

         long var6 = var4;
         if(var1.words[var3 - 1] < 0) {
            var6 = var4 - 1L;
         }

         this.words[var3] = (int)var6;
         this.ival = wordsNeeded(this.words, var3 + 1);
      }
   }

   void setInvert() {
      if(this.words == null) {
         this.ival = ~this.ival;
      } else {
         int var1 = this.ival;

         while(true) {
            --var1;
            if(var1 < 0) {
               break;
            }

            this.words[var1] = ~this.words[var1];
         }
      }

   }

   public final void setNegative() {
      this.setNegative(this);
   }

   public void setNegative(IntNum var1) {
      int var3 = var1.ival;
      if(var1.words == null) {
         if(var3 == Integer.MIN_VALUE) {
            this.set(-((long)var3));
         } else {
            this.set(-var3);
         }
      } else {
         this.realloc(var3 + 1);
         int var2 = var3;
         if(negate(this.words, var1.words, var3)) {
            this.words[var3] = 0;
            var2 = var3 + 1;
         }

         this.ival = var2;
      }
   }

   void setShift(IntNum var1, int var2) {
      if(var2 > 0) {
         this.setShiftLeft(var1, var2);
      } else {
         this.setShiftRight(var1, -var2);
      }
   }

   void setShiftLeft(IntNum var1, int var2) {
      int[] var3;
      int var4;
      int[] var8;
      if(var1.words == null) {
         if(var2 < 32) {
            this.set((long)var1.ival << var2);
            return;
         }

         var3 = new int[]{var1.ival};
         var4 = 1;
         var8 = var3;
      } else {
         var3 = var1.words;
         var4 = var1.ival;
         var8 = var3;
      }

      int var5 = var2 >> 5;
      int var7 = var2 & 31;
      int var6 = var4 + var5;
      if(var7 == 0) {
         this.realloc(var6);

         while(true) {
            --var4;
            var2 = var6;
            if(var4 < 0) {
               break;
            }

            this.words[var4 + var5] = var8[var4];
         }
      } else {
         var2 = var6 + 1;
         this.realloc(var2);
         var4 = MPN.lshift(this.words, var5, var8, var4, var7);
         var6 = 32 - var7;
         this.words[var2 - 1] = var4 << var6 >> var6;
      }

      this.ival = var2;
      var2 = var5;

      while(true) {
         --var2;
         if(var2 < 0) {
            return;
         }

         this.words[var2] = 0;
      }
   }

   void setShiftRight(IntNum var1, int var2) {
      byte var3 = -1;
      if(var1.words == null) {
         if(var2 < 32) {
            var2 = var1.ival >> var2;
         } else {
            var2 = var3;
            if(var1.ival >= 0) {
               var2 = 0;
            }
         }

         this.set(var2);
      } else {
         if(var2 == 0) {
            this.set(var1);
            return;
         }

         boolean var6 = var1.isNegative();
         int var4 = var2 >> 5;
         var2 &= 31;
         int var5 = var1.ival - var4;
         if(var5 <= 0) {
            if(!var6) {
               var3 = 0;
            }

            this.set(var3);
            return;
         }

         if(this.words == null || this.words.length < var5) {
            this.realloc(var5);
         }

         MPN.rshift0(this.words, var1.words, var4, var5, var2);
         this.ival = var5;
         if(var6) {
            int[] var7 = this.words;
            int var8 = var5 - 1;
            var7[var8] |= -2 << 31 - var2;
            return;
         }
      }

   }

   public int sign() {
      int var2 = this.ival;
      int[] var1 = this.words;
      if(var1 == null) {
         if(var2 <= 0) {
            if(var2 < 0) {
               return -1;
            }

            return 0;
         }
      } else {
         --var2;
         int var3 = var1[var2];
         if(var3 <= 0) {
            if(var3 < 0) {
               return -1;
            }

            while(var2 != 0) {
               var3 = var2 - 1;
               var2 = var3;
               if(var1[var3] != 0) {
                  return 1;
               }
            }

            return 0;
         }
      }

      return 1;
   }

   public IntNum toExactInt(int var1) {
      return this;
   }

   public RealNum toInt(int var1) {
      return this;
   }

   public String toString(int var1) {
      if(this.words == null) {
         return Integer.toString(this.ival, var1);
      } else if(this.ival <= 2) {
         return Long.toString(this.longValue(), var1);
      } else {
         StringBuilder var2 = new StringBuilder(this.ival * (MPN.chars_per_word(var1) + 1));
         this.format(var1, (StringBuilder)var2);
         return var2.toString();
      }
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      byte var3 = 0;
      int var2;
      if(this.words == null) {
         var2 = 1;
      } else {
         var2 = wordsNeeded(this.words, this.ival);
      }

      if(var2 <= 1) {
         if(this.words == null) {
            var2 = this.ival;
         } else {
            var2 = var3;
            if(this.words.length != 0) {
               var2 = this.words[0];
            }
         }

         if(var2 < -1073741824) {
            var1.writeInt(-2147483647);
            var1.writeInt(var2);
            return;
         }

         var1.writeInt(var2);
      } else {
         var1.writeInt(Integer.MIN_VALUE | var2);

         while(true) {
            --var2;
            if(var2 < 0) {
               break;
            }

            var1.writeInt(this.words[var2]);
         }
      }

   }
}
