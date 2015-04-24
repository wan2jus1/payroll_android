package org.json.zip;

import org.json.zip.JSONzip;
import org.json.zip.None;
import org.json.zip.PostMortem;

abstract class Keep implements None, PostMortem {

   protected int capacity;
   protected int length;
   protected int power;
   protected long[] uses;


   public Keep(int var1) {
      this.capacity = JSONzip.twos[var1];
      this.length = 0;
      this.power = 0;
      this.uses = new long[this.capacity];
   }

   public static long age(long var0) {
      return var0 >= 32L?16L:var0 / 2L;
   }

   public int bitsize() {
      while(JSONzip.twos[this.power] < this.length) {
         ++this.power;
      }

      return this.power;
   }

   public void tick(int var1) {
      long[] var2 = this.uses;
      ++var2[var1];
   }

   public abstract Object value(int var1);
}
