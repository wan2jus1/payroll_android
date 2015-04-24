package gnu.lists;

import gnu.lists.Consumer;
import gnu.lists.Convert;
import gnu.lists.Sequence;
import gnu.lists.SimpleVector;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class F32Vector extends SimpleVector implements Externalizable, Comparable {

   protected static float[] empty = new float[0];
   float[] data;


   public F32Vector() {
      this.data = empty;
   }

   public F32Vector(int var1) {
      this.data = new float[var1];
      this.size = var1;
   }

   public F32Vector(int var1, float var2) {
      float[] var3 = new float[var1];
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

   public F32Vector(Sequence var1) {
      this.data = new float[var1.size()];
      this.addAll(var1);
   }

   public F32Vector(float[] var1) {
      this.data = var1;
      this.size = var1.length;
   }

   protected void clearBuffer(int var1, int var2) {
      while(true) {
         --var2;
         if(var2 < 0) {
            return;
         }

         this.data[var1] = 0.0F;
         ++var1;
      }
   }

   public int compareTo(Object var1) {
      F32Vector var10 = (F32Vector)var1;
      float[] var4 = this.data;
      float[] var5 = var10.data;
      int var8 = this.size;
      int var9 = var10.size;
      int var6;
      if(var8 > var9) {
         var6 = var9;
      } else {
         var6 = var8;
      }

      for(int var7 = 0; var7 < var6; ++var7) {
         float var2 = var4[var7];
         float var3 = var5[var7];
         if(var2 != var3) {
            if(var2 > var3) {
               return 1;
            }

            return -1;
         }
      }

      return var8 - var9;
   }

   public boolean consumeNext(int var1, Consumer var2) {
      var1 >>>= 1;
      if(var1 >= this.size) {
         return false;
      } else {
         var2.writeFloat(this.data[var1]);
         return true;
      }
   }

   public void consumePosRange(int var1, int var2, Consumer var3) {
      if(!var3.ignoring()) {
         for(var1 >>>= 1; var1 < var2 >>> 1; ++var1) {
            var3.writeFloat(this.data[var1]);
         }
      }

   }

   public final float floatAt(int var1) {
      if(var1 >= this.size) {
         throw new ArrayIndexOutOfBoundsException();
      } else {
         return this.data[var1];
      }
   }

   public final float floatAtBuffer(int var1) {
      return this.data[var1];
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
      return 25;
   }

   public String getTag() {
      return "f32";
   }

   public final int intAtBuffer(int var1) {
      return (int)this.data[var1];
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      int var4 = var1.readInt();
      float[] var2 = new float[var4];

      for(int var3 = 0; var3 < var4; ++var3) {
         var2[var3] = var1.readFloat();
      }

      this.data = var2;
      this.size = var4;
   }

   public final Object setBuffer(int var1, Object var2) {
      Object var3 = Convert.toObject(this.data[var1]);
      this.data[var1] = Convert.toFloat(var2);
      return var3;
   }

   public void setBufferLength(int var1) {
      int var4 = this.data.length;
      if(var4 != var1) {
         float[] var2 = new float[var1];
         float[] var3 = this.data;
         if(var4 < var1) {
            var1 = var4;
         }

         System.arraycopy(var3, 0, var2, 0, var1);
         this.data = var2;
      }

   }

   public final void setFloatAt(int var1, float var2) {
      if(var1 > this.size) {
         throw new IndexOutOfBoundsException();
      } else {
         this.data[var1] = var2;
      }
   }

   public final void setFloatAtBuffer(int var1, float var2) {
      this.data[var1] = var2;
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      int var3 = this.size;
      var1.writeInt(var3);

      for(int var2 = 0; var2 < var3; ++var2) {
         var1.writeFloat(this.data[var2]);
      }

   }
}
