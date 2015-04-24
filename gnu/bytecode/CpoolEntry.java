package gnu.bytecode;

import gnu.bytecode.ClassTypeWriter;
import gnu.bytecode.ConstantPool;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class CpoolEntry {

   int hash;
   public int index;
   CpoolEntry next;


   protected CpoolEntry() {
   }

   public CpoolEntry(ConstantPool var1, int var2) {
      this.hash = var2;
      if(var1.locked) {
         throw new Error("adding new entry to locked contant pool");
      } else {
         var2 = var1.count + 1;
         var1.count = var2;
         this.index = var2;
         if(var1.pool == null) {
            var1.pool = new CpoolEntry[60];
         } else if(this.index >= var1.pool.length) {
            int var4 = var1.pool.length;
            CpoolEntry[] var3 = new CpoolEntry[var1.pool.length * 2];

            for(var2 = 0; var2 < var4; ++var2) {
               var3[var2] = var1.pool[var2];
            }

            var1.pool = var3;
         }

         if(var1.hashTab == null || (double)this.index >= 0.6D * (double)var1.hashTab.length) {
            var1.rehash();
         }

         var1.pool[this.index] = this;
         this.add_hashed(var1);
      }
   }

   void add_hashed(ConstantPool var1) {
      CpoolEntry[] var3 = var1.hashTab;
      int var2 = (this.hash & Integer.MAX_VALUE) % var3.length;
      this.next = var3[var2];
      var3[var2] = this;
   }

   public int getIndex() {
      return this.index;
   }

   public abstract int getTag();

   public int hashCode() {
      return this.hash;
   }

   public abstract void print(ClassTypeWriter var1, int var2);

   abstract void write(DataOutputStream var1) throws IOException;
}
