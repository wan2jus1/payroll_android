package gnu.lists;

import gnu.lists.Consumer;
import gnu.lists.Convert;
import gnu.lists.Sequence;
import gnu.lists.SimpleVector;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class S16Vector extends SimpleVector implements Externalizable, Comparable {

   protected static short[] empty = new short[0];
   short[] data;


   public S16Vector() {
      this.data = empty;
   }

   public S16Vector(int var1) {
      this.data = new short[var1];
      this.size = var1;
   }

   public S16Vector(int var1, short var2) {
      short[] var3 = new short[var1];
      this.data = var3;
      this.size = var1;

      while(true) {
         --var1;
         if(var1 < 0) {
            return;
         }

         var3[var1] = var2;
      }
   }

   public S16Vector(Sequence var1) {
      this.data = new short[var1.size()];
      this.addAll(var1);
   }

   public S16Vector(short[] var1) {
      this.data = var1;
      this.size = var1.length;
   }

   protected void clearBuffer(int var1, int var2) {
      while(true) {
         --var2;
         if(var2 < 0) {
            return;
         }

         this.data[var1] = 0;
         ++var1;
      }
   }

   public int compareTo(Object var1) {
      return compareToInt(this, (S16Vector)var1);
   }

   public boolean consumeNext(int var1, Consumer var2) {
      var1 >>>= 1;
      if(var1 >= this.size) {
         return false;
      } else {
         var2.writeInt(this.data[var1]);
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
            var3.writeInt(this.data[var2]);
            ++var2;
         }
      }

   }

   public final Object get(int var1) {
      if(var1 > this.size) {
         throw new IndexOutOfBoundsException();
      } else {
         return Convert.toObject((short)this.data[var1]);
      }
   }

   protected Object getBuffer() {
      return this.data;
   }

   public final Object getBuffer(int var1) {
      return Convert.toObject((short)this.data[var1]);
   }

   public int getBufferLength() {
      return this.data.length;
   }

   public int getElementKind() {
      return 20;
   }

   public String getTag() {
      return "s16";
   }

   public final int intAtBuffer(int var1) {
      return this.data[var1];
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      int var4 = var1.readInt();
      short[] var2 = new short[var4];

      for(int var3 = 0; var3 < var4; ++var3) {
         var2[var3] = var1.readShort();
      }

      this.data = var2;
      this.size = var4;
   }

   public Object setBuffer(int var1, Object var2) {
      short var3 = this.data[var1];
      this.data[var1] = Convert.toShort(var2);
      return Convert.toObject((short)var3);
   }

   public void setBufferLength(int var1) {
      int var4 = this.data.length;
      if(var4 != var1) {
         short[] var2 = new short[var1];
         short[] var3 = this.data;
         if(var4 < var1) {
            var1 = var4;
         }

         System.arraycopy(var3, 0, var2, 0, var1);
         this.data = var2;
      }

   }

   public final void setShortAt(int var1, short var2) {
      if(var1 > this.size) {
         throw new IndexOutOfBoundsException();
      } else {
         this.data[var1] = var2;
      }
   }

   public final void setShortAtBuffer(int var1, short var2) {
      this.data[var1] = var2;
   }

   public final short shortAt(int var1) {
      if(var1 > this.size) {
         throw new IndexOutOfBoundsException();
      } else {
         return this.data[var1];
      }
   }

   public final short shortAtBuffer(int var1) {
      return this.data[var1];
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      int var3 = this.size;
      var1.writeInt(var3);

      for(int var2 = 0; var2 < var3; ++var2) {
         var1.writeShort(this.data[var2]);
      }

   }
}
