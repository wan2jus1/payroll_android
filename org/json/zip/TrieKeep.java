package org.json.zip;

import org.json.Kim;
import org.json.zip.JSONzip;
import org.json.zip.Keep;
import org.json.zip.PostMortem;

class TrieKeep extends Keep {

   private int[] froms;
   private Kim[] kims;
   private TrieKeep.Node root;
   private int[] thrus;


   public TrieKeep(int var1) {
      super(var1);
      this.froms = new int[this.capacity];
      this.thrus = new int[this.capacity];
      this.kims = new Kim[this.capacity];
      this.root = new TrieKeep.Node();
   }

   public Kim kim(int var1) {
      Kim var3 = this.kims[var1];
      int var4 = this.froms[var1];
      int var5 = this.thrus[var1];
      Kim var2;
      if(var4 == 0) {
         var2 = var3;
         if(var5 == var3.length) {
            return var2;
         }
      }

      var2 = new Kim(var3, var4, var5);
      this.froms[var1] = 0;
      this.thrus[var1] = var2.length;
      this.kims[var1] = var2;
      return var2;
   }

   public int length(int var1) {
      return this.thrus[var1] - this.froms[var1];
   }

   public int match(Kim var1, int var2, int var3) {
      TrieKeep.Node var4 = this.root;
      int var6 = -1;

      for(int var5 = var2; var5 < var3; ++var5) {
         var4 = var4.get((int)var1.get(var5));
         if(var4 == null) {
            break;
         }

         if(var4.integer != -1) {
            var6 = var4.integer;
         }

         ++var2;
      }

      return var6;
   }

   public boolean postMortem(PostMortem var1) {
      boolean var5 = true;
      TrieKeep var6 = (TrieKeep)var1;
      if(this.length != var6.length) {
         JSONzip.log("\nLength " + this.length + " <> " + var6.length);
      } else {
         if(this.capacity != var6.capacity) {
            JSONzip.log("\nCapacity " + this.capacity + " <> " + var6.capacity);
            return false;
         }

         for(int var4 = 0; var4 < this.length; ++var4) {
            Kim var2 = this.kim(var4);
            Kim var3 = var6.kim(var4);
            if(!var2.equals(var3)) {
               JSONzip.log("\n[" + var4 + "] " + var2 + " <> " + var3);
               var5 = false;
            }
         }

         if(var5 && this.root.postMortem(var6.root)) {
            return true;
         }
      }

      return false;
   }

   public void registerMany(Kim var1) {
      int var8 = var1.length;
      int var4 = this.capacity - this.length;
      int var3 = var4;
      if(var4 > 40) {
         var3 = 40;
      }

      byte var9 = 0;
      int var5 = var3;

      for(var3 = var9; var3 < var8 - 2; ++var3) {
         int var6 = var8 - var3;
         var4 = var6;
         if(var6 > 10) {
            var4 = 10;
         }

         TrieKeep.Node var2 = this.root;

         for(int var7 = var3; var7 < var4 + var3; var5 = var6) {
            var2 = var2.vet((int)var1.get(var7));
            var6 = var5;
            if(var2.integer == -1) {
               var6 = var5;
               if(var7 - var3 >= 2) {
                  var2.integer = this.length;
                  this.uses[this.length] = 1L;
                  this.kims[this.length] = var1;
                  this.froms[this.length] = var3;
                  this.thrus[this.length] = var7 + 1;
                  ++this.length;
                  --var5;
                  var6 = var5;
                  if(var5 <= 0) {
                     return;
                  }
               }
            }

            ++var7;
         }
      }

   }

   public int registerOne(Kim var1, int var2, int var3) {
      byte var6 = -1;
      int var5 = var6;
      if(this.length < this.capacity) {
         TrieKeep.Node var4 = this.root;

         for(var5 = var2; var5 < var3; ++var5) {
            var4 = var4.vet((int)var1.get(var5));
         }

         var5 = var6;
         if(var4.integer == -1) {
            var5 = this.length;
            var4.integer = var5;
            this.uses[var5] = 1L;
            this.kims[var5] = var1;
            this.froms[var5] = var2;
            this.thrus[var5] = var3;
            ++this.length;
         }
      }

      return var5;
   }

   public void registerOne(Kim var1) {
      int var2 = this.registerOne(var1, 0, var1.length);
      if(var2 != -1) {
         this.kims[var2] = var1;
      }

   }

   public void reserve() {
      if(this.capacity - this.length < 40) {
         int var4 = 0;
         int var3 = 0;

         int var5;
         for(this.root = new TrieKeep.Node(); var4 < this.capacity; var3 = var5) {
            var5 = var3;
            if(this.uses[var4] > 1L) {
               Kim var2 = this.kims[var4];
               int var6 = this.thrus[var4];
               TrieKeep.Node var1 = this.root;

               for(var5 = this.froms[var4]; var5 < var6; ++var5) {
                  var1 = var1.vet((int)var2.get(var5));
               }

               var1.integer = var3;
               this.uses[var3] = age(this.uses[var4]);
               this.froms[var3] = this.froms[var4];
               this.thrus[var3] = var6;
               this.kims[var3] = var2;
               var5 = var3 + 1;
            }

            ++var4;
         }

         var4 = var3;
         if(this.capacity - var3 < 40) {
            this.power = 0;
            this.root = new TrieKeep.Node();
            var4 = 0;
         }

         for(this.length = var4; var4 < this.capacity; ++var4) {
            this.uses[var4] = 0L;
            this.kims[var4] = null;
            this.froms[var4] = 0;
            this.thrus[var4] = 0;
         }
      }

   }

   public Object value(int var1) {
      return this.kim(var1);
   }

   class Node implements PostMortem {

      private int integer = -1;
      private TrieKeep.Node[] next = null;


      public TrieKeep.Node get(byte var1) {
         return this.get((int)(var1 & 255));
      }

      public TrieKeep.Node get(int var1) {
         return this.next == null?null:this.next[var1];
      }

      public boolean postMortem(PostMortem var1) {
         TrieKeep.Node var4 = (TrieKeep.Node)var1;
         if(var4 == null) {
            JSONzip.log("\nMisalign");
         } else {
            if(this.integer != var4.integer) {
               JSONzip.log("\nInteger " + this.integer + " <> " + var4.integer);
               return false;
            }

            if(this.next == null) {
               if(var4.next == null) {
                  return true;
               }

               JSONzip.log("\nNext is null " + this.integer);
               return false;
            }

            int var3 = 0;

            while(true) {
               if(var3 >= 256) {
                  return true;
               }

               TrieKeep.Node var2 = this.next[var3];
               if(var2 != null) {
                  if(!var2.postMortem(var4.next[var3])) {
                     break;
                  }
               } else if(var4.next[var3] != null) {
                  JSONzip.log("\nMisalign " + var3);
                  return false;
               }

               ++var3;
            }
         }

         return false;
      }

      public void set(byte var1, TrieKeep.Node var2) {
         this.set((int)(var1 & 255), var2);
      }

      public void set(int var1, TrieKeep.Node var2) {
         if(this.next == null) {
            this.next = new TrieKeep.Node[256];
         }

         this.next[var1] = var2;
      }

      public TrieKeep.Node vet(byte var1) {
         return this.vet((int)(var1 & 255));
      }

      public TrieKeep.Node vet(int var1) {
         TrieKeep.Node var3 = this.get((int)var1);
         TrieKeep.Node var2 = var3;
         if(var3 == null) {
            var2 = TrieKeep.this.new Node();
            this.set((int)var1, var2);
         }

         return var2;
      }
   }
}
