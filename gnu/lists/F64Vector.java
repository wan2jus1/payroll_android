package gnu.lists;

import gnu.lists.Consumer;
import gnu.lists.Convert;
import gnu.lists.Sequence;
import gnu.lists.SimpleVector;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class F64Vector extends SimpleVector implements Externalizable, Comparable {

   protected static double[] empty = new double[0];
   double[] data;


   public F64Vector() {
      this.data = empty;
   }

   public F64Vector(int var1) {
      this.data = new double[var1];
      this.size = var1;
   }

   public F64Vector(int var1, double var2) {
      double[] var4 = new double[var1];
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

   public F64Vector(Sequence var1) {
      this.data = new double[var1.size()];
      this.addAll(var1);
   }

   public F64Vector(double[] var1) {
      this.data = var1;
      this.size = var1.length;
   }

   protected void clearBuffer(int var1, int var2) {
      while(true) {
         --var2;
         if(var2 < 0) {
            return;
         }

         this.data[var1] = 0.0D;
         ++var1;
      }
   }

   public int compareTo(Object var1) {
      F64Vector var12 = (F64Vector)var1;
      double[] var6 = this.data;
      double[] var7 = var12.data;
      int var10 = this.size;
      int var11 = var12.size;
      int var8;
      if(var10 > var11) {
         var8 = var11;
      } else {
         var8 = var10;
      }

      for(int var9 = 0; var9 < var8; ++var9) {
         double var2 = var6[var9];
         double var4 = var7[var9];
         if(var2 != var4) {
            if(var2 > var4) {
               return 1;
            }

            return -1;
         }
      }

      return var10 - var11;
   }

   public boolean consumeNext(int var1, Consumer var2) {
      var1 >>>= 1;
      if(var1 >= this.size) {
         return false;
      } else {
         var2.writeDouble(this.data[var1]);
         return true;
      }
   }

   public void consumePosRange(int var1, int var2, Consumer var3) {
      if(!var3.ignoring()) {
         for(var1 >>>= 1; var1 < var2 >>> 1; ++var1) {
            var3.writeDouble(this.data[var1]);
         }
      }

   }

   public final double doubleAt(int var1) {
      if(var1 >= this.size) {
         throw new ArrayIndexOutOfBoundsException();
      } else {
         return this.data[var1];
      }
   }

   public final double doubleAtBuffer(int var1) {
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
      return 26;
   }

   public String getTag() {
      return "f64";
   }

   public final int intAtBuffer(int var1) {
      return (int)this.data[var1];
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      int var4 = var1.readInt();
      double[] var2 = new double[var4];

      for(int var3 = 0; var3 < var4; ++var3) {
         var2[var3] = var1.readDouble();
      }

      this.data = var2;
      this.size = var4;
   }

   public final Object setBuffer(int var1, Object var2) {
      Object var3 = Convert.toObject(this.data[var1]);
      this.data[var1] = Convert.toDouble(var2);
      return var3;
   }

   public void setBufferLength(int var1) {
      int var4 = this.data.length;
      if(var4 != var1) {
         double[] var2 = new double[var1];
         double[] var3 = this.data;
         if(var4 < var1) {
            var1 = var4;
         }

         System.arraycopy(var3, 0, var2, 0, var1);
         this.data = var2;
      }

   }

   public final void setDoubleAt(int var1, double var2) {
      if(var1 > this.size) {
         throw new IndexOutOfBoundsException();
      } else {
         this.data[var1] = var2;
      }
   }

   public final void setDoubleAtBuffer(int var1, double var2) {
      this.data[var1] = var2;
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      int var3 = this.size;
      var1.writeInt(var3);

      for(int var2 = 0; var2 < var3; ++var2) {
         var1.writeDouble(this.data[var2]);
      }

   }
}
