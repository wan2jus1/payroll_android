package org.json.zip;

import org.json.JSONException;
import org.json.zip.BitReader;
import org.json.zip.BitWriter;
import org.json.zip.JSONzip;
import org.json.zip.None;
import org.json.zip.PostMortem;

public class Huff implements None, PostMortem {

   private final int domain;
   private final Huff.Symbol[] symbols;
   private Huff.Symbol table;
   private boolean upToDate = false;
   private int width;


   public Huff(int var1) {
      this.domain = var1;
      int var3 = var1 * 2 - 1;
      this.symbols = new Huff.Symbol[var3];

      for(int var2 = 0; var2 < var1; ++var2) {
         this.symbols[var2] = new Huff.Symbol(var2);
      }

      while(var1 < var3) {
         this.symbols[var1] = new Huff.Symbol(-1);
         ++var1;
      }

   }

   private boolean postMortem(int var1) {
      boolean var6 = true;
      int[] var4 = new int[this.domain];
      Huff.Symbol var2 = this.symbols[var1];
      if(var2.integer == var1) {
         int var5 = 0;

         while(true) {
            Huff.Symbol var3 = var2.back;
            if(var3 == null) {
               if(var2 == this.table) {
                  this.width = 0;
                  var2 = this.table;

                  while(var2.integer == -1) {
                     --var5;
                     if(var4[var5] != 0) {
                        var2 = var2.one;
                     } else {
                        var2 = var2.zero;
                     }
                  }

                  if(var2.integer != var1 || var5 != 0) {
                     var6 = false;
                  }

                  return var6;
               }
               break;
            }

            if(var3.zero == var2) {
               var4[var5] = 0;
            } else {
               if(var3.one != var2) {
                  break;
               }

               var4[var5] = 1;
            }

            ++var5;
            var2 = var3;
         }
      }

      return false;
   }

   private void write(Huff.Symbol param1, BitWriter param2) throws JSONException {
      // $FF: Couldn't be decompiled
   }

   public void generate() {
      if(!this.upToDate) {
         Huff.Symbol var1 = this.symbols[0];
         Huff.Symbol var2 = var1;
         this.table = null;
         var1.next = null;

         Huff.Symbol var3;
         Huff.Symbol var4;
         int var6;
         for(var6 = 1; var6 < this.domain; ++var6) {
            var4 = this.symbols[var6];
            if(var4.weight < var1.weight) {
               var4.next = var1;
               var1 = var4;
            } else {
               var3 = var2;
               if(var4.weight < var2.weight) {
                  var3 = var1;
               }

               while(true) {
                  var2 = var3.next;
                  if(var2 == null || var4.weight < var2.weight) {
                     var4.next = var2;
                     var3.next = var4;
                     var2 = var4;
                     break;
                  }

                  var3 = var2;
               }
            }
         }

         var6 = this.domain;
         var2 = var1;

         while(true) {
            var4 = var1;
            Huff.Symbol var5 = var1.next;
            var3 = var5.next;
            var1 = this.symbols[var6];
            ++var6;
            var1.weight = var4.weight + var5.weight;
            var1.zero = var4;
            var1.one = var5;
            var1.back = null;
            var4.back = var1;
            var5.back = var1;
            if(var3 == null) {
               this.table = var1;
               this.upToDate = true;
               break;
            }

            if(var1.weight < var3.weight) {
               var1.next = var3;
               var2 = var1;
            } else {
               while(true) {
                  var4 = var2.next;
                  if(var4 == null || var1.weight < var4.weight) {
                     var1.next = var4;
                     var2.next = var1;
                     var2 = var1;
                     var1 = var3;
                     break;
                  }

                  var2 = var4;
               }
            }
         }
      }

   }

   public boolean postMortem(PostMortem var1) {
      for(int var2 = 0; var2 < this.domain; ++var2) {
         if(!this.postMortem(var2)) {
            JSONzip.log("\nBad huff ");
            JSONzip.logchar(var2, var2);
            return false;
         }
      }

      return this.table.postMortem(((Huff)var1).table);
   }

   public int read(BitReader param1) throws JSONException {
      // $FF: Couldn't be decompiled
   }

   public void tick(int var1) {
      Huff.Symbol var2 = this.symbols[var1];
      ++var2.weight;
      this.upToDate = false;
   }

   public void tick(int var1, int var2) {
      while(var1 <= var2) {
         this.tick(var1);
         ++var1;
      }

   }

   public void write(int var1, BitWriter var2) throws JSONException {
      this.width = 0;
      this.write(this.symbols[var1], var2);
      this.tick(var1);
   }

   private static class Symbol implements PostMortem {

      public Huff.Symbol back;
      public final int integer;
      public Huff.Symbol next;
      public Huff.Symbol one;
      public long weight;
      public Huff.Symbol zero;


      public Symbol(int var1) {
         this.integer = var1;
         this.weight = 0L;
         this.next = null;
         this.back = null;
         this.one = null;
         this.zero = null;
      }

      public boolean postMortem(PostMortem var1) {
         boolean var5 = true;
         boolean var6 = true;
         Huff.Symbol var7 = (Huff.Symbol)var1;
         if(this.integer == var7.integer && this.weight == var7.weight) {
            boolean var4;
            if(this.back != null) {
               var4 = true;
            } else {
               var4 = false;
            }

            if(var7.back == null) {
               var5 = false;
            }

            if(var4 == var5) {
               Huff.Symbol var2 = this.zero;
               Huff.Symbol var3 = this.one;
               if(var2 == null) {
                  if(var7.zero != null) {
                     return false;
                  }
               } else {
                  var6 = var2.postMortem(var7.zero);
               }

               if(var3 == null) {
                  if(var7.one != null) {
                     return false;
                  }
               } else {
                  var6 = var3.postMortem(var7.one);
               }

               return var6;
            }
         }

         return false;
      }
   }
}
