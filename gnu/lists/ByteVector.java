package gnu.lists;

import gnu.lists.Consumer;
import gnu.lists.SimpleVector;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public abstract class ByteVector extends SimpleVector implements Externalizable, Comparable {

   protected static byte[] empty = new byte[0];
   byte[] data;


   public final byte byteAt(int var1) {
      if(var1 > this.size) {
         throw new IndexOutOfBoundsException();
      } else {
         return this.data[var1];
      }
   }

   public final byte byteAtBuffer(int var1) {
      return this.data[var1];
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

   public boolean consumeNext(int var1, Consumer var2) {
      var1 >>>= 1;
      if(var1 >= this.size) {
         return false;
      } else {
         var2.writeInt(this.intAtBuffer(var1));
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
            var3.writeInt(this.intAtBuffer(var2));
            ++var2;
         }
      }

   }

   protected Object getBuffer() {
      return this.data;
   }

   public int getBufferLength() {
      return this.data.length;
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      int var4 = var1.readInt();
      byte[] var2 = new byte[var4];

      for(int var3 = 0; var3 < var4; ++var3) {
         var2[var3] = var1.readByte();
      }

      this.data = var2;
      this.size = var4;
   }

   public void setBufferLength(int var1) {
      int var4 = this.data.length;
      if(var4 != var1) {
         byte[] var2 = new byte[var1];
         byte[] var3 = this.data;
         if(var4 < var1) {
            var1 = var4;
         }

         System.arraycopy(var3, 0, var2, 0, var1);
         this.data = var2;
      }

   }

   public final void setByteAt(int var1, byte var2) {
      if(var1 > this.size) {
         throw new IndexOutOfBoundsException();
      } else {
         this.data[var1] = var2;
      }
   }

   public final void setByteAtBuffer(int var1, byte var2) {
      this.data[var1] = var2;
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      int var3 = this.size;
      var1.writeInt(var3);

      for(int var2 = 0; var2 < var3; ++var2) {
         var1.writeByte(this.data[var2]);
      }

   }
}
