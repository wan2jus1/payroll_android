package gnu.lists;

import gnu.lists.Consumer;
import gnu.lists.Convert;
import gnu.lists.Sequence;
import gnu.lists.SimpleVector;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class BitVector extends SimpleVector implements Externalizable {

   protected static boolean[] empty = new boolean[0];
   boolean[] data;


   public BitVector() {
      this.data = empty;
   }

   public BitVector(int var1) {
      this.data = new boolean[var1];
      this.size = var1;
   }

   public BitVector(int var1, boolean var2) {
      boolean[] var3 = new boolean[var1];
      this.data = var3;
      this.size = var1;
      if(var2) {
         while(true) {
            --var1;
            if(var1 < 0) {
               break;
            }

            var3[var1] = true;
         }
      }

   }

   public BitVector(Sequence var1) {
      this.data = new boolean[var1.size()];
      this.addAll(var1);
   }

   public BitVector(boolean[] var1) {
      this.data = var1;
      this.size = var1.length;
   }

   public final boolean booleanAt(int var1) {
      if(var1 > this.size) {
         throw new IndexOutOfBoundsException();
      } else {
         return this.data[var1];
      }
   }

   public final boolean booleanAtBuffer(int var1) {
      return this.data[var1];
   }

   protected void clearBuffer(int var1, int var2) {
      while(true) {
         --var2;
         if(var2 < 0) {
            return;
         }

         this.data[var1] = false;
         ++var1;
      }
   }

   public boolean consumeNext(int var1, Consumer var2) {
      var1 >>>= 1;
      if(var1 >= this.size) {
         return false;
      } else {
         var2.writeBoolean(this.data[var1]);
         return true;
      }
   }

   public void consumePosRange(int var1, int var2, Consumer var3) {
      if(!var3.ignoring()) {
         for(var1 >>>= 1; var1 < var2 >>> 1; ++var1) {
            var3.writeBoolean(this.data[var1]);
         }
      }

   }

   public final Object get(int var1) {
      if(var1 > this.size) {
         throw new IndexOutOfBoundsException();
      } else {
         return Convert.toObject(this.data[var1]);
      }
   }

   protected Object getBuffer() {
      return this.data;
   }

   public final Object getBuffer(int var1) {
      return Convert.toObject(this.data[var1]);
   }

   public int getBufferLength() {
      return this.data.length;
   }

   public int getElementKind() {
      return 27;
   }

   public String getTag() {
      return "b";
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      int var4 = var1.readInt();
      boolean[] var2 = new boolean[var4];

      for(int var3 = 0; var3 < var4; ++var3) {
         var2[var3] = var1.readBoolean();
      }

      this.data = var2;
      this.size = var4;
   }

   public final void setBooleanAt(int var1, boolean var2) {
      if(var1 > this.size) {
         throw new IndexOutOfBoundsException();
      } else {
         this.data[var1] = var2;
      }
   }

   public final void setBooleanAtBuffer(int var1, boolean var2) {
      this.data[var1] = var2;
   }

   public Object setBuffer(int var1, Object var2) {
      boolean var3 = this.data[var1];
      this.data[var1] = Convert.toBoolean(var2);
      return Convert.toObject(var3);
   }

   public void setBufferLength(int var1) {
      int var4 = this.data.length;
      if(var4 != var1) {
         boolean[] var2 = new boolean[var1];
         boolean[] var3 = this.data;
         if(var4 < var1) {
            var1 = var4;
         }

         System.arraycopy(var3, 0, var2, 0, var1);
         this.data = var2;
      }

   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      int var3 = this.size;
      var1.writeInt(var3);

      for(int var2 = 0; var2 < var3; ++var2) {
         var1.writeBoolean(this.data[var2]);
      }

   }
}
