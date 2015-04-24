package gnu.math;

import gnu.math.IntNum;
import gnu.math.MPN;

public class BitOps {

   static final byte[] bit4_count = new byte[]{(byte)0, (byte)1, (byte)1, (byte)2, (byte)1, (byte)2, (byte)2, (byte)3, (byte)1, (byte)2, (byte)2, (byte)3, (byte)2, (byte)3, (byte)3, (byte)4};


   public static IntNum and(IntNum var0, int var1) {
      if(var0.words == null) {
         return IntNum.make(var0.ival & var1);
      } else if(var1 >= 0) {
         return IntNum.make(var0.words[0] & var1);
      } else {
         int var3 = var0.ival;
         int[] var2 = new int[var3];
         var2[0] = var0.words[0] & var1;
         var1 = var3;

         while(true) {
            --var1;
            if(var1 <= 0) {
               return IntNum.make(var2, var0.ival);
            }

            var2[var1] = var0.words[var1];
         }
      }
   }

   public static IntNum and(IntNum var0, IntNum var1) {
      if(var1.words == null) {
         return and(var0, var1.ival);
      } else if(var0.words == null) {
         return and(var1, var0.ival);
      } else {
         IntNum var3 = var0;
         IntNum var2 = var1;
         if(var0.ival < var1.ival) {
            var2 = var0;
            var3 = var1;
         }

         int var5;
         if(var2.isNegative()) {
            var5 = var3.ival;
         } else {
            var5 = var2.ival;
         }

         int[] var7 = new int[var5];
         int var4 = 0;

         while(true) {
            int var6 = var4;
            if(var4 >= var2.ival) {
               while(var6 < var5) {
                  var7[var6] = var3.words[var6];
                  ++var6;
               }

               return IntNum.make(var7, var5);
            }

            var7[var4] = var3.words[var4] & var2.words[var4];
            ++var4;
         }
      }
   }

   public static int bitCount(int var0) {
      int var1;
      for(var1 = 0; var0 != 0; var0 >>>= 4) {
         var1 += bit4_count[var0 & 15];
      }

      return var1;
   }

   public static int bitCount(IntNum var0) {
      int[] var1 = var0.words;
      int var2;
      int var3;
      if(var1 == null) {
         var3 = 1;
         var2 = bitCount(var0.ival);
      } else {
         var3 = var0.ival;
         var2 = bitCount(var1, var3);
      }

      int var4 = var2;
      if(var0.isNegative()) {
         var4 = var3 * 32 - var2;
      }

      return var4;
   }

   public static int bitCount(int[] var0, int var1) {
      byte var3 = 0;
      int var2 = var1;
      var1 = var3;

      while(true) {
         --var2;
         if(var2 < 0) {
            return var1;
         }

         var1 += bitCount(var0[var2]);
      }
   }

   public static IntNum bitOp(int var0, IntNum var1, IntNum var2) {
      IntNum var3 = var1;
      switch(var0) {
      case 0:
         return IntNum.zero();
      case 1:
         return and(var1, var2);
      case 5:
         return var2;
      case 15:
         return IntNum.minusOne();
      default:
         var3 = new IntNum();
         setBitOp(var3, var0, var1, var2);
         var3 = var3.canonicalize();
      case 3:
         return var3;
      }
   }

   public static boolean bitValue(IntNum var0, int var1) {
      int var2 = var0.ival;
      if(var0.words == null) {
         if(var1 >= 32) {
            if(var2 >= 0) {
               return false;
            }
         } else if((var2 >> var1 & 1) == 0) {
            return false;
         }
      } else {
         int var3 = var1 >> 5;
         if(var3 >= var2) {
            if(var0.words[var2 - 1] >= 0) {
               return false;
            }
         } else if((var0.words[var3] >> var1 & 1) == 0) {
            return false;
         }
      }

      return true;
   }

   static int[] dataBufferFor(IntNum var0, int var1) {
      int var3 = var0.ival;
      int var4 = var1 + 1 >> 5;
      int[] var2;
      int[] var5;
      if(var0.words == null) {
         var1 = var4;
         if(var4 == 0) {
            var1 = 1;
         }

         var2 = new int[var1];
         var2[0] = var3;
         var5 = var2;
         if(var3 < 0) {
            var3 = 1;

            while(true) {
               var5 = var2;
               if(var3 >= var1) {
                  break;
               }

               var2[var3] = -1;
               ++var3;
            }
         }
      } else {
         var4 = var1 + 1 >> 5;
         if(var4 > var3) {
            var1 = var4;
         } else {
            var1 = var3;
         }

         var2 = new int[var1];
         var1 = var3;

         while(true) {
            --var1;
            if(var1 < 0) {
               var5 = var2;
               if(var2[var3 - 1] < 0) {
                  while(true) {
                     var5 = var2;
                     if(var3 >= var4) {
                        return var5;
                     }

                     var2[var3] = -1;
                     ++var3;
                  }
               }
               break;
            }

            var2[var1] = var0.words[var1];
         }
      }

      return var5;
   }

   public static IntNum extract(IntNum var0, int var1, int var2) {
      IntNum var3;
      int var4;
      if(var2 < 32) {
         if(var0.words == null) {
            var4 = var0.ival;
         } else {
            var4 = var0.words[0];
         }

         var3 = IntNum.make((~(-1 << var2) & var4) >> var1);
         return var3;
      } else {
         if(var0.words == null) {
            if(var0.ival >= 0) {
               if(var1 >= 31) {
                  var1 = 0;
               } else {
                  var1 = var0.ival >> var1;
               }

               return IntNum.make(var1);
            }

            var4 = 1;
         } else {
            var4 = var0.ival;
         }

         boolean var9 = var0.isNegative();
         int var5;
         int var6;
         if(var2 > var4 * 32) {
            var6 = var4 * 32;
            var5 = var4;
            var2 = var6;
            if(!var9) {
               var3 = var0;
               if(var1 == 0) {
                  return var3;
               }

               var2 = var6;
               var5 = var4;
            }
         } else {
            var5 = var2 + 31 >> 5;
         }

         var4 = var2 - var1;
         if(var4 < 64) {
            long var7;
            if(var0.words == null) {
               var2 = var0.ival;
               if(var1 >= 32) {
                  var1 = 31;
               }

               var7 = (long)(var2 >> var1);
            } else {
               var7 = MPN.rshift_long(var0.words, var5, var1);
            }

            return IntNum.make(~(-1L << var4) & var7);
         } else {
            var6 = var1 >> 5;
            int[] var10 = new int[(var2 >> 5) + 1 - var6];
            if(var0.words == null) {
               if(var1 >= 32) {
                  var1 = -1;
               } else {
                  var1 = var0.ival >> var1;
               }

               var10[0] = var1;
            } else {
               MPN.rshift0(var10, var0.words, var6, var5 - var6, var1 & 31);
            }

            var1 = var4 >> 5;
            var10[var1] &= ~(-1 << var4);
            return IntNum.make(var10, var1 + 1);
         }
      }
   }

   public static IntNum ior(IntNum var0, IntNum var1) {
      return bitOp(7, var0, var1);
   }

   public static int lowestBitSet(int var0) {
      int var1;
      if(var0 == 0) {
         var1 = -1;
      } else {
         var1 = 0;
         int var2 = var0;

         while(true) {
            var0 = var1;
            int var3 = var2;
            if((var2 & 255) != 0) {
               while((var3 & 3) == 0) {
                  var3 >>>= 2;
                  var0 += 2;
               }

               var1 = var0;
               if((var3 & 1) == 0) {
                  return var0 + 1;
               }
               break;
            }

            var2 >>>= 8;
            var1 += 8;
         }
      }

      return var1;
   }

   public static int lowestBitSet(IntNum var0) {
      int[] var1 = var0.words;
      if(var1 == null) {
         return lowestBitSet(var0.ival);
      } else {
         int var2 = var0.ival;

         int var3;
         do {
            if(var2 >= 0) {
               return -1;
            }

            var3 = lowestBitSet(var1[0]);
         } while(var3 < 0);

         return var3 + 0;
      }
   }

   public static IntNum not(IntNum var0) {
      return bitOp(12, var0, IntNum.zero());
   }

   public static IntNum reverseBits(IntNum var0, int var1, int var2) {
      int var3 = var0.ival;
      if(var0.words == null && var2 < 63) {
         long var8 = (long)var3;
         --var2;

         while(var1 < var2) {
            var8 = (var8 >> var1 & 1L) << var2 | var8 & ~(1L << var1 | 1L << var2) | (var8 >> var2 & 1L) << var1;
            ++var1;
            --var2;
         }

         return IntNum.make(var8);
      } else {
         int[] var10 = dataBufferFor(var0, var2 - 1);
         var3 = var1;
         var1 = var2 - 1;

         for(var2 = var3; var2 < var1; --var1) {
            int var4 = var2 >> 5;
            int var5 = var1 >> 5;
            var3 = var10[var4];
            int var6 = var3 >> var2 & 1;
            if(var4 == var5) {
               var3 = var6 << var1 | (int)((long)var3 & ~(1L << var2 | 1L << var1)) | (var3 >> var1 & 1) << var2;
            } else {
               int var7 = var10[var5];
               var3 = var3 & ~(1 << (var2 & 31)) | (var7 >> (var1 & 31) & 1) << (var2 & 31);
               var10[var5] = var7 & ~(1 << (var1 & 31)) | var6 << (var1 & 31);
            }

            var10[var4] = var3;
            ++var2;
         }

         return IntNum.make(var10, var10.length);
      }
   }

   public static void setBitOp(IntNum var0, int var1, IntNum var2, IntNum var3) {
      IntNum var4;
      IntNum var5;
      int var8;
      if(var3.words == null) {
         var5 = var3;
         var4 = var2;
         var8 = var1;
      } else {
         label212: {
            if(var2.words != null) {
               var8 = var1;
               var4 = var2;
               var5 = var3;
               if(var2.ival >= var3.ival) {
                  break label212;
               }
            }

            var8 = swappedOp(var1);
            var4 = var3;
            var5 = var2;
         }
      }

      int var7;
      if(var5.words == null) {
         var1 = var5.ival;
         var7 = 1;
      } else {
         var1 = var5.words[0];
         var7 = var5.ival;
      }

      int var6;
      int var9;
      if(var4.words == null) {
         var6 = var4.ival;
         var9 = 1;
      } else {
         var6 = var4.words[0];
         var9 = var4.ival;
      }

      if(var9 > 1) {
         var0.realloc(var9);
      }

      int[] var13;
      byte var14;
      var13 = var0.words;
      byte var11 = 0;
      int var10;
      int var12;
      int var10000;
      label206:
      switch(var8) {
      case 0:
         var6 = 0;
         var1 = 0;
         var14 = var11;
         break;
      case 1:
         var8 = 0;
         var10 = var1;

         while(true) {
            var12 = var6 & var10;
            if(var8 + 1 >= var7) {
               var14 = var11;
               var1 = var8;
               var6 = var12;
               if(var10 < 0) {
                  var14 = 1;
                  var1 = var8;
                  var6 = var12;
               }
               break label206;
            }

            var1 = var8 + 1;
            var13[var8] = var12;
            var6 = var4.words[var1];
            var10 = var5.words[var1];
            var8 = var1;
         }
      case 2:
         var8 = 0;
         var10 = var1;

         while(true) {
            var12 = var6 & ~var10;
            if(var8 + 1 >= var7) {
               var14 = var11;
               var1 = var8;
               var6 = var12;
               if(var10 >= 0) {
                  var14 = 1;
                  var1 = var8;
                  var6 = var12;
               }
               break label206;
            }

            var1 = var8 + 1;
            var13[var8] = var12;
            var6 = var4.words[var1];
            var10 = var5.words[var1];
            var8 = var1;
         }
      case 3:
         var14 = 1;
         var1 = 0;
         break;
      case 4:
         var8 = 0;
         var10 = var1;

         while(true) {
            var12 = ~var6 & var10;
            if(var8 + 1 >= var7) {
               var14 = var11;
               var1 = var8;
               var6 = var12;
               if(var10 < 0) {
                  var14 = 2;
                  var1 = var8;
                  var6 = var12;
               }
               break label206;
            }

            var1 = var8 + 1;
            var13[var8] = var12;
            var6 = var4.words[var1];
            var10 = var5.words[var1];
            var8 = var1;
         }
      case 5:
         var6 = 0;

         while(true) {
            var8 = var1;
            if(var6 + 1 >= var7) {
               var14 = var11;
               var1 = var6;
               var6 = var8;
               break label206;
            }

            var1 = var6 + 1;
            var13[var6] = var8;
            var10000 = var4.words[var1];
            var8 = var5.words[var1];
            var6 = var1;
            var1 = var8;
         }
      case 6:
         var8 = 0;

         while(true) {
            var6 ^= var1;
            if(var8 + 1 >= var7) {
               if(var1 < 0) {
                  var14 = 2;
               } else {
                  var14 = 1;
               }

               var1 = var8;
               break label206;
            }

            var1 = var8 + 1;
            var13[var8] = var6;
            var6 = var4.words[var1];
            var10 = var5.words[var1];
            var8 = var1;
            var1 = var10;
         }
      case 7:
         var8 = 0;
         var10 = var1;

         while(true) {
            var12 = var6 | var10;
            if(var8 + 1 >= var7) {
               var14 = var11;
               var1 = var8;
               var6 = var12;
               if(var10 >= 0) {
                  var14 = 1;
                  var1 = var8;
                  var6 = var12;
               }
               break label206;
            }

            var1 = var8 + 1;
            var13[var8] = var12;
            var6 = var4.words[var1];
            var10 = var5.words[var1];
            var8 = var1;
         }
      case 8:
         var8 = 0;
         var10 = var1;

         while(true) {
            var12 = ~(var6 | var10);
            if(var8 + 1 >= var7) {
               var14 = var11;
               var1 = var8;
               var6 = var12;
               if(var10 >= 0) {
                  var14 = 2;
                  var1 = var8;
                  var6 = var12;
               }
               break label206;
            }

            var1 = var8 + 1;
            var13[var8] = var12;
            var6 = var4.words[var1];
            var10 = var5.words[var1];
            var8 = var1;
         }
      case 9:
         var8 = 0;

         while(true) {
            var6 = ~(var6 ^ var1);
            if(var8 + 1 >= var7) {
               if(var1 >= 0) {
                  var14 = 2;
               } else {
                  var14 = 1;
               }

               var1 = var8;
               break label206;
            }

            var1 = var8 + 1;
            var13[var8] = var6;
            var6 = var4.words[var1];
            var10 = var5.words[var1];
            var8 = var1;
            var1 = var10;
         }
      case 10:
         var6 = 0;

         while(true) {
            var8 = ~var1;
            if(var6 + 1 >= var7) {
               var14 = var11;
               var1 = var6;
               var6 = var8;
               break label206;
            }

            var1 = var6 + 1;
            var13[var6] = var8;
            var10000 = var4.words[var1];
            var8 = var5.words[var1];
            var6 = var1;
            var1 = var8;
         }
      case 11:
         var8 = 0;
         var10 = var1;

         while(true) {
            var12 = var6 | ~var10;
            if(var8 + 1 >= var7) {
               var14 = var11;
               var1 = var8;
               var6 = var12;
               if(var10 < 0) {
                  var14 = 1;
                  var1 = var8;
                  var6 = var12;
               }
               break label206;
            }

            var1 = var8 + 1;
            var13[var8] = var12;
            var6 = var4.words[var1];
            var10 = var5.words[var1];
            var8 = var1;
         }
      case 12:
         var6 = ~var6;
         var14 = 2;
         var1 = 0;
         break;
      case 13:
         var8 = 0;
         var10 = var1;

         while(true) {
            var12 = ~var6 | var10;
            if(var8 + 1 >= var7) {
               var14 = var11;
               var1 = var8;
               var6 = var12;
               if(var10 >= 0) {
                  var14 = 2;
                  var1 = var8;
                  var6 = var12;
               }
               break label206;
            }

            var1 = var8 + 1;
            var13[var8] = var12;
            var6 = var4.words[var1];
            var10 = var5.words[var1];
            var8 = var1;
         }
      case 14:
         var8 = 0;
         var10 = var1;

         while(true) {
            var12 = ~(var6 & var10);
            if(var8 + 1 >= var7) {
               var14 = var11;
               var1 = var8;
               var6 = var12;
               if(var10 < 0) {
                  var14 = 2;
                  var1 = var8;
                  var6 = var12;
               }
               break label206;
            }

            var1 = var8 + 1;
            var13[var8] = var12;
            var6 = var4.words[var1];
            var10 = var5.words[var1];
            var8 = var1;
         }
      default:
         var6 = -1;
         var1 = 0;
         var14 = var11;
      }

      if(var1 + 1 == var9) {
         var14 = 0;
      }

      label129:
      switch(var14) {
      case 0:
         if(var1 == 0 && var13 == null) {
            var0.ival = var6;
            return;
         }

         var7 = var1 + 1;
         var13[var1] = var6;
         var1 = var7;
         break;
      case 1:
         var13[var1] = var6;

         while(true) {
            var6 = var1 + 1;
            var1 = var6;
            if(var6 >= var9) {
               break label129;
            }

            var13[var6] = var4.words[var6];
            var1 = var6;
         }
      case 2:
         var13[var1] = var6;

         while(true) {
            var6 = var1 + 1;
            var1 = var6;
            if(var6 >= var9) {
               break;
            }

            var13[var6] = ~var4.words[var6];
            var1 = var6;
         }
      }

      var0.ival = var1;
   }

   public static IntNum setBitValue(IntNum var0, int var1, int var2) {
      byte var3 = 31;
      int var4 = var2 & 1;
      int var5 = var0.ival;
      if(var0.words == null) {
         var2 = var3;
         if(var1 < 31) {
            var2 = var1;
         }

         if((var5 >> var2 & 1) == var4) {
            return var0;
         }

         if(var1 < 63) {
            return IntNum.make((long)var5 ^ (long)(1 << var1));
         }
      } else {
         var2 = var1 >> 5;
         if(var2 >= var5) {
            if(var0.words[var5 - 1] < 0) {
               var2 = 1;
            } else {
               var2 = 0;
            }
         } else {
            var2 = var0.words[var2] >> var1 & 1;
         }

         if(var2 == var4) {
            return var0;
         }
      }

      int[] var6 = dataBufferFor(var0, var1);
      var2 = var1 >> 5;
      var6[var2] ^= 1 << (var1 & 31);
      return IntNum.make(var6, var6.length);
   }

   public static int swappedOp(int var0) {
      return "\u0000\u0001\u0004\u0005\u0002\u0003\u0006\u0007\b\t\f\r\n\u000b\u000e\u000f".charAt(var0);
   }

   public static boolean test(IntNum var0, int var1) {
      boolean var2 = false;
      if(var0.words == null) {
         return (var0.ival & var1) != 0;
      } else {
         if(var1 < 0 || (var0.words[0] & var1) != 0) {
            var2 = true;
         }

         return var2;
      }
   }

   public static boolean test(IntNum var0, IntNum var1) {
      if(var1.words == null) {
         return test(var0, var1.ival);
      } else if(var0.words == null) {
         return test(var1, var0.ival);
      } else {
         IntNum var3 = var0;
         IntNum var2 = var1;
         if(var0.ival < var1.ival) {
            var2 = var0;
            var3 = var1;
         }

         for(int var4 = 0; var4 < var2.ival; ++var4) {
            if((var3.words[var4] & var2.words[var4]) != 0) {
               return true;
            }
         }

         return var2.isNegative();
      }
   }

   public static IntNum xor(IntNum var0, IntNum var1) {
      return bitOp(6, var0, var1);
   }
}
