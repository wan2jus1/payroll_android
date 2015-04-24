package gnu.math;


class MPN {

   public static int add_1(int[] var0, int[] var1, int var2, int var3) {
      long var4 = (long)var3 & 4294967295L;

      for(var3 = 0; var3 < var2; ++var3) {
         var4 += (long)var1[var3] & 4294967295L;
         var0[var3] = (int)var4;
         var4 >>= 32;
      }

      return (int)var4;
   }

   public static int add_n(int[] var0, int[] var1, int[] var2, int var3) {
      long var5 = 0L;

      for(int var4 = 0; var4 < var3; ++var4) {
         var5 += ((long)var1[var4] & 4294967295L) + ((long)var2[var4] & 4294967295L);
         var0[var4] = (int)var5;
         var5 >>>= 32;
      }

      return (int)var5;
   }

   public static int chars_per_word(int var0) {
      byte var1 = 16;
      if(var0 < 10) {
         if(var0 < 8) {
            if(var0 <= 2) {
               var1 = 32;
            } else {
               if(var0 == 3) {
                  return 20;
               }

               if(var0 != 4) {
                  return 18 - var0;
               }
            }

            return var1;
         } else {
            return 10;
         }
      } else {
         return var0 < 12?9:(var0 <= 16?8:(var0 <= 23?7:(var0 <= 40?6:(var0 <= 256?4:1))));
      }
   }

   public static int cmp(int[] var0, int var1, int[] var2, int var3) {
      return var1 > var3?1:(var1 < var3?-1:cmp(var0, var2, var1));
   }

   public static int cmp(int[] var0, int[] var1, int var2) {
      while(true) {
         --var2;
         if(var2 >= 0) {
            int var3 = var0[var2];
            int var4 = var1[var2];
            if(var3 == var4) {
               continue;
            }

            if((var3 ^ Integer.MIN_VALUE) > (Integer.MIN_VALUE ^ var4)) {
               return 1;
            }

            return -1;
         }

         return 0;
      }
   }

   public static int count_leading_zeros(int var0) {
      int var3;
      if(var0 == 0) {
         var3 = 32;
      } else {
         byte var4 = 0;
         int var1 = 16;
         int var2 = var0;
         var0 = var4;

         while(true) {
            var3 = var0;
            if(var1 <= 0) {
               break;
            }

            var3 = var2 >>> var1;
            if(var3 == 0) {
               var0 += var1;
            } else {
               var2 = var3;
            }

            var1 >>= 1;
         }
      }

      return var3;
   }

   public static void divide(int[] var0, int var1, int[] var2, int var3) {
      int var4 = var1;

      do {
         if(var0[var4] == var2[var3 - 1]) {
            var1 = -1;
         } else {
            var1 = (int)udiv_qrnnd(((long)var0[var4] << 32) + ((long)var0[var4 - 1] & 4294967295L), var2[var3 - 1]);
         }

         int var5 = var1;
         if(var1 != 0) {
            var5 = submul_1(var0, var4 - var3, var2, var3, var1);
            long var6 = ((long)var0[var4] & 4294967295L) - ((long)var5 & 4294967295L);

            while(true) {
               var5 = var1;
               if(var6 == 0L) {
                  break;
               }

               var5 = var1 - 1;
               var6 = 0L;

               for(var1 = 0; var1 < var3; ++var1) {
                  var6 += ((long)var0[var4 - var3 + var1] & 4294967295L) + ((long)var2[var1] & 4294967295L);
                  var0[var4 - var3 + var1] = (int)var6;
                  var6 >>>= 32;
               }

               var0[var4] = (int)((long)var0[var4] + var6);
               --var6;
               var1 = var5;
            }
         }

         var0[var4] = var5;
         var1 = var4 - 1;
         var4 = var1;
      } while(var1 >= var3);

   }

   public static int divmod_1(int[] var0, int[] var1, int var2, int var3) {
      --var2;
      long var4 = (long)var1[var2];
      if((var4 & 4294967295L) >= ((long)var3 & 4294967295L)) {
         var4 = 0L;
      } else {
         var0[var2] = 0;
         var4 <<= 32;
         --var2;
      }

      while(var2 >= 0) {
         var4 = udiv_qrnnd(-4294967296L & var4 | (long)var1[var2] & 4294967295L, var3);
         var0[var2] = (int)var4;
         --var2;
      }

      return (int)(var4 >> 32);
   }

   static int findLowestBit(int var0) {
      byte var2 = 0;
      int var1 = var0;

      for(var0 = var2; (var1 & 15) == 0; var0 += 4) {
         var1 >>= 4;
      }

      int var4 = var0;
      int var3 = var1;
      if((var1 & 3) == 0) {
         var3 = var1 >> 2;
         var4 = var0 + 2;
      }

      var0 = var4;
      if((var3 & 1) == 0) {
         var0 = var4 + 1;
      }

      return var0;
   }

   static int findLowestBit(int[] var0) {
      int var1;
      for(var1 = 0; var0[var1] == 0; ++var1) {
         ;
      }

      return var1 * 32 + findLowestBit(var0[var1]);
   }

   public static int gcd(int[] var0, int[] var1, int var2) {
      int var6 = 0;

      while(true) {
         int var7 = var0[var6] | var1[var6];
         if(var7 != 0) {
            int var10 = findLowestBit(var7);
            var2 -= var6;
            rshift0(var0, var0, var6, var2, var10);
            rshift0(var1, var1, var6, var2, var10);
            int[] var3;
            if((var0[0] & 1) != 0) {
               var3 = var1;
               var1 = var0;
            } else {
               var3 = var0;
            }

            while(true) {
               int var8;
               for(var8 = 0; var3[var8] == 0; ++var8) {
                  ;
               }

               if(var8 > 0) {
                  var7 = 0;

                  while(true) {
                     int var9 = var7;
                     if(var7 >= var2 - var8) {
                        while(var9 < var2) {
                           var3[var9] = 0;
                           ++var9;
                        }
                        break;
                     }

                     var3[var7] = var3[var7 + var8];
                     ++var7;
                  }
               }

               var7 = findLowestBit(var3[0]);
               if(var7 > 0) {
                  rshift(var3, var3, 0, var2, var7);
               }

               var7 = cmp(var1, var3, var2);
               if(var7 == 0) {
                  var7 = var2;
                  if(var6 + var10 > 0) {
                     if(var10 > 0) {
                        var8 = lshift(var0, var6, var0, var2, var10);
                        var7 = var2;
                        if(var8 != 0) {
                           var0[var2 + var6] = var8;
                           var7 = var2 + 1;
                        }
                     } else {
                        var7 = var2;

                        while(true) {
                           var8 = var7 - 1;
                           var7 = var2;
                           if(var8 < 0) {
                              break;
                           }

                           var0[var8 + var6] = var0[var8];
                           var7 = var8;
                        }
                     }

                     var2 = var6;

                     while(true) {
                        --var2;
                        if(var2 < 0) {
                           var7 += var6;
                           break;
                        }

                        var0[var2] = 0;
                     }
                  }

                  return var7;
               }

               int[] var4;
               int[] var5;
               if(var7 > 0) {
                  sub_n(var1, var1, var3, var2);
                  var7 = var2;
                  var4 = var1;
                  var5 = var3;
               } else {
                  sub_n(var3, var3, var1, var2);
                  var5 = var1;
                  var4 = var3;
                  var7 = var2;
               }

               while(true) {
                  var1 = var5;
                  var3 = var4;
                  var2 = var7;
                  if(var5[var7 - 1] != 0) {
                     break;
                  }

                  var1 = var5;
                  var3 = var4;
                  var2 = var7;
                  if(var4[var7 - 1] != 0) {
                     break;
                  }

                  --var7;
               }
            }
         }

         ++var6;
      }
   }

   public static int intLength(int var0) {
      int var1 = var0;
      if(var0 < 0) {
         var1 = ~var0;
      }

      return 32 - count_leading_zeros(var1);
   }

   public static int intLength(int[] var0, int var1) {
      --var1;
      return intLength(var0[var1]) + var1 * 32;
   }

   public static int lshift(int[] var0, int var1, int[] var2, int var3, int var4) {
      int var7 = 32 - var4;
      --var3;
      int var5 = var2[var3];
      int var8 = var1 + 1;
      var1 = var5;

      while(true) {
         --var3;
         if(var3 < 0) {
            var0[var8 + var3] = var1 << var4;
            return var5 >>> var7;
         }

         int var6 = var2[var3];
         var0[var8 + var3] = var1 << var4 | var6 >>> var7;
         var1 = var6;
      }
   }

   public static void mul(int[] var0, int[] var1, int var2, int[] var3, int var4) {
      var0[var2] = mul_1(var0, var1, var2, var3[0]);

      for(int var5 = 1; var5 < var4; ++var5) {
         long var9 = (long)var3[var5];
         long var7 = 0L;

         for(int var6 = 0; var6 < var2; ++var6) {
            var7 += ((long)var1[var6] & 4294967295L) * (var9 & 4294967295L) + ((long)var0[var5 + var6] & 4294967295L);
            var0[var5 + var6] = (int)var7;
            var7 >>>= 32;
         }

         var0[var5 + var2] = (int)var7;
      }

   }

   public static int mul_1(int[] var0, int[] var1, int var2, int var3) {
      long var6 = (long)var3;
      long var4 = 0L;

      for(var3 = 0; var3 < var2; ++var3) {
         var4 += ((long)var1[var3] & 4294967295L) * (var6 & 4294967295L);
         var0[var3] = (int)var4;
         var4 >>>= 32;
      }

      return (int)var4;
   }

   public static int rshift(int[] var0, int[] var1, int var2, int var3, int var4) {
      int var9 = 32 - var4;
      int var7 = var1[var2];
      int var6 = 1;

      int var5;
      for(var5 = var7; var6 < var3; ++var6) {
         int var8 = var1[var2 + var6];
         var0[var6 - 1] = var5 >>> var4 | var8 << var9;
         var5 = var8;
      }

      var0[var6 - 1] = var5 >>> var4;
      return var7 << var9;
   }

   public static void rshift0(int[] var0, int[] var1, int var2, int var3, int var4) {
      if(var4 > 0) {
         rshift(var0, var1, var2, var3, var4);
      } else {
         for(var4 = 0; var4 < var3; ++var4) {
            var0[var4] = var1[var4 + var2];
         }
      }

   }

   public static long rshift_long(int[] var0, int var1, int var2) {
      int var4 = var2 >> 5;
      int var7 = var2 & 31;
      byte var9;
      if(var0[var1 - 1] < 0) {
         var9 = -1;
      } else {
         var9 = 0;
      }

      int var3;
      if(var4 >= var1) {
         var3 = var9;
      } else {
         var3 = var0[var4];
      }

      int var8 = var4 + 1;
      if(var8 >= var1) {
         var4 = var9;
      } else {
         var4 = var0[var8];
      }

      int var6 = var3;
      int var5 = var4;
      if(var7 != 0) {
         var5 = var8 + 1;
         if(var5 >= var1) {
            var1 = var9;
         } else {
            var1 = var0[var5];
         }

         var6 = var3 >>> var7 | var4 << 32 - var7;
         var5 = var4 >>> var7 | var1 << 32 - var7;
      }

      return (long)var5 << 32 | (long)var6 & 4294967295L;
   }

   public static int set_str(int[] var0, byte[] var1, int var2, int var3) {
      int var4;
      int var5;
      int var6;
      int var10;
      if((var3 - 1 & var3) == 0) {
         byte var7 = 0;
         var5 = 0;

         while(true) {
            var3 >>= 1;
            if(var3 == 0) {
               var4 = 0;
               var6 = var2;
               var2 = 0;
               var3 = var7;

               while(true) {
                  --var6;
                  if(var6 < 0) {
                     var6 = var2;
                     if(var4 != 0) {
                        var0[var2] = var4;
                        return var2 + 1;
                     }

                     return var6;
                  }

                  byte var8 = var1[var6];
                  var10 = var4 | var8 << var3;
                  var3 += var5;
                  if(var3 >= 32) {
                     var4 = var2 + 1;
                     var0[var2] = var10;
                     var3 -= 32;
                     var2 = var8 >> var5 - var3;
                  } else {
                     var4 = var2;
                     var2 = var10;
                  }

                  var10 = var4;
                  var4 = var2;
                  var2 = var10;
               }
            }

            ++var5;
         }
      } else {
         int var9 = chars_per_word(var3);
         var5 = 0;
         var4 = 0;

         while(true) {
            var6 = var4;
            if(var5 >= var2) {
               return var6;
            }

            var10 = var2 - var5;
            var6 = var10;
            if(var10 > var9) {
               var6 = var9;
            }

            var10 = var1[var5];
            int var11 = var3;
            ++var5;

            while(true) {
               --var6;
               if(var6 <= 0) {
                  if(var4 == 0) {
                     var6 = var10;
                  } else {
                     var6 = mul_1(var0, var0, var4, var11) + add_1(var0, var0, var4, var10);
                  }

                  if(var6 != 0) {
                     var10 = var4 + 1;
                     var0[var4] = var6;
                     var4 = var10;
                  }
                  break;
               }

               var10 = var10 * var3 + var1[var5];
               var11 *= var3;
               ++var5;
            }
         }
      }
   }

   public static int sub_n(int[] var0, int[] var1, int[] var2, int var3) {
      int var5 = 0;

      for(int var4 = 0; var4 < var3; ++var4) {
         int var7 = var2[var4];
         int var6 = var1[var4];
         var7 += var5;
         byte var8;
         if((var7 ^ Integer.MIN_VALUE) < (var5 ^ Integer.MIN_VALUE)) {
            var8 = 1;
         } else {
            var8 = 0;
         }

         var7 = var6 - var7;
         byte var9;
         if((var7 ^ Integer.MIN_VALUE) > (var6 ^ Integer.MIN_VALUE)) {
            var9 = 1;
         } else {
            var9 = 0;
         }

         var5 = var8 + var9;
         var0[var4] = var7;
      }

      return var5;
   }

   public static int submul_1(int[] var0, int var1, int[] var2, int var3, int var4) {
      long var9 = (long)var4;
      int var6 = 0;
      int var5 = 0;

      int var7;
      do {
         long var11 = ((long)var2[var5] & 4294967295L) * (var9 & 4294967295L);
         var4 = (int)var11;
         int var8 = (int)(var11 >> 32);
         var7 = var4 + var6;
         byte var13;
         if((Integer.MIN_VALUE ^ var7) < (Integer.MIN_VALUE ^ var6)) {
            var13 = 1;
         } else {
            var13 = 0;
         }

         var6 = var13 + var8;
         var8 = var0[var1 + var5];
         var7 = var8 - var7;
         var4 = var6;
         if((Integer.MIN_VALUE ^ var7) > (Integer.MIN_VALUE ^ var8)) {
            var4 = var6 + 1;
         }

         var0[var1 + var5] = var7;
         var7 = var5 + 1;
         var6 = var4;
         var5 = var7;
      } while(var7 < var3);

      return var4;
   }

   public static long udiv_qrnnd(long var0, int var2) {
      long var5 = var0 >>> 32;
      long var7 = var0 & 4294967295L;
      long var3;
      if(var2 >= 0) {
         if(var5 < ((long)var2 - var5 - (var7 >>> 31) & 4294967295L)) {
            var3 = var0 / (long)var2;
            var0 %= (long)var2;
         } else {
            var0 -= (long)var2 << 31;
            var3 = var0 / (long)var2;
            var0 %= (long)var2;
            var3 -= 2147483648L;
         }
      } else {
         var3 = (long)(var2 >>> 1);
         var0 >>>= 1;
         if(var5 >= var3 && var5 >> 1 >= var3) {
            if(var7 >= ((long)(-var2) & 4294967295L)) {
               var3 = -1L;
               var0 = var7 + (long)var2;
            } else {
               var3 = -2L;
               var0 = (long)var2 + var7 + (long)var2;
            }
         } else {
            if(var5 < var3) {
               var5 = var0 / var3;
               var0 %= var3;
            } else {
               var0 = ~(var0 - (var3 << 32));
               var5 = ~(var0 / var3) & 4294967295L;
               var0 = var3 - 1L - var0 % var3;
            }

            var7 = 2L * var0 + (1L & var7);
            var3 = var5;
            var0 = var7;
            if((var2 & 1) != 0) {
               if(var7 >= var5) {
                  var0 = var7 - var5;
                  var3 = var5;
               } else if(var5 - var7 <= ((long)var2 & 4294967295L)) {
                  var0 = var7 - var5 + (long)var2;
                  var3 = var5 - 1L;
               } else {
                  var0 = var7 - var5 + (long)var2 + (long)var2;
                  var3 = var5 - 2L;
               }
            }
         }
      }

      return var0 << 32 | 4294967295L & var3;
   }
}
