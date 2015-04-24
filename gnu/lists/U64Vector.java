package gnu.lists;

import gnu.lists.Consumer;
import gnu.lists.Convert;
import gnu.lists.S64Vector;
import gnu.lists.Sequence;
import gnu.lists.SimpleVector;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class U64Vector extends SimpleVector implements Externalizable, Comparable {

   long[] data;


   public U64Vector() {
      this.data = S64Vector.empty;
   }

   public U64Vector(int var1) {
      this.data = new long[var1];
      this.size = var1;
   }

   public U64Vector(int var1, long var2) {
      long[] var4 = new long[var1];
      this.data = var4;
      this.size = var1;

      while(true) {
         --var1;
         if(var1 < 0) {
            return;
         }

         var4[var1] = var2;
      }
   }

   public U64Vector(Sequence var1) {
      this.data = new long[var1.size()];
      this.addAll(var1);
   }

   public U64Vector(long[] var1) {
      this.data = var1;
      this.size = var1.length;
   }

   protected void clearBuffer(int var1, int var2) {
      while(true) {
         --var2;
         if(var2 < 0) {
            return;
         }

         this.data[var1] = 0L;
         ++var1;
      }
   }

   public int compareTo(Object var1) {
      U64Vector var12 = (U64Vector)var1;
      long[] var2 = this.data;
      long[] var3 = var12.data;
      int var6 = this.size;
      int var7 = var12.size;
      int var4;
      if(var6 > var7) {
         var4 = var7;
      } else {
         var4 = var6;
      }

      for(int var5 = 0; var5 < var4; ++var5) {
         long var8 = var2[var5];
         long var10 = var3[var5];
         if(var8 != var10) {
            if((Long.MIN_VALUE ^ var8) > (Long.MIN_VALUE ^ var10)) {
               return 1;
            }

            return -1;
         }
      }

      return var6 - var7;
   }

   public boolean consumeNext(int var1, Consumer var2) {
      var1 >>>= 1;
      if(var1 >= this.size) {
         return false;
      } else {
         var2.writeLong(this.data[var1]);
         return true;
      }
   }

   public void consumePosRange(int var1, int var2, Consumer var3) {
      if(!var3.ignoring()) {
         int var4 = var1 >>> 1;
         int var5 = var2 >>> 1;
         var1 = var5;
         var2 = var4;
         if(var5 > this.size) {
            var1 = this.size;
            var2 = var4;
         }

         while(var2 < var1) {
            var3.writeLong(this.data[var2]);
            ++var2;
         }
      }

   }

   public final Object get(int var1) {
      if(var1 > this.size) {
         throw new IndexOutOfBoundsException();
      } else {
         return Convert.toObjectUnsigned(this.data[var1]);
      }
   }

   protected Object getBuffer() {
      return this.data;
   }

   public final Object getBuffer(int var1) {
      return Convert.toObjectUnsigned(this.data[var1]);
   }

   public int getBufferLength() {
      return this.data.length;
   }

   public int getElementKind() {
      return 23;
   }

   public String getTag() {
      return "u64";
   }

   public final int intAtBuffer(int var1) {
      return (int)this.data[var1];
   }

   public final long longAt(int var1) {
      if(var1 > this.size) {
         throw new IndexOutOfBoundsException();
      } else {
         return this.data[var1];
      }
   }

   public final long longAtBuffer(int var1) {
      return this.data[var1];
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      int var4 = var1.readInt();
      long[] var2 = new long[var4];

      for(int var3 = 0; var3 < var4; ++var3) {
         var2[var3] = var1.readLong();
      }

      this.data = var2;
      this.size = var4;
   }

   public Object setBuffer(int var1, Object var2) {
      long var3 = this.data[var1];
      this.data[var1] = Convert.toLongUnsigned(var2);
      return Convert.toObjectUnsigned(var3);
   }

   public void setBufferLength(int var1) {
      int var4 = this.data.length;
      if(var4 != var1) {
         long[] var2 = new long[var1];
         long[] var3 = this.data;
         if(var4 < var1) {
            var1 = var4;
         }

         System.arraycopy(var3, 0, var2, 0, var1);
         this.data = var2;
      }

   }

   public final void setLongAt(int var1, long var2) {
      if(var1 > this.size) {
         throw new IndexOutOfBoundsException();
      } else {
         this.data[var1] = var2;
      }
   }

   public final void setLongAtBuffer(int var1, long var2) {
      this.data[var1] = var2;
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      int var3 = this.size;
      var1.writeInt(var3);

      for(int var2 = 0; var2 < var3; ++var2) {
         var1.writeLong(this.data[var2]);
      }

   }
}
