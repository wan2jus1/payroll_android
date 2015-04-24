package gnu.lists;

import gnu.lists.Consumable;
import gnu.lists.Consumer;
import gnu.lists.SimpleVector;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.List;

public class FVector extends SimpleVector implements Externalizable, Consumable, Comparable {

   protected static Object[] empty = new Object[0];
   public Object[] data;


   public FVector() {
      this.data = empty;
   }

   public FVector(int var1) {
      this.size = var1;
      this.data = new Object[var1];
   }

   public FVector(int var1, Object var2) {
      Object[] var3 = new Object[var1];
      if(var2 != null) {
         for(int var4 = 0; var4 < var1; ++var4) {
            var3[var4] = var2;
         }
      }

      this.data = var3;
      this.size = var1;
   }

   public FVector(List var1) {
      this.data = new Object[var1.size()];
      this.addAll(var1);
   }

   public FVector(Object[] var1) {
      this.size = var1.length;
      this.data = var1;
   }

   public static FVector make(Object ... var0) {
      return new FVector(var0);
   }

   protected void clearBuffer(int var1, int var2) {
      Object[] var3 = this.data;

      while(true) {
         --var2;
         if(var2 < 0) {
            return;
         }

         var3[var1] = null;
         ++var1;
      }
   }

   public int compareTo(Object var1) {
      FVector var9 = (FVector)var1;
      Object[] var2 = this.data;
      Object[] var3 = var9.data;
      int var6 = this.size;
      int var7 = var9.size;
      int var4;
      if(var6 > var7) {
         var4 = var7;
      } else {
         var4 = var6;
      }

      for(int var5 = 0; var5 < var4; ++var5) {
         int var8 = ((Comparable)var2[var5]).compareTo((Comparable)var3[var5]);
         if(var8 != 0) {
            return var8;
         }
      }

      return var6 - var7;
   }

   public void consume(Consumer var1) {
      var1.startElement("#vector");
      int var3 = this.size;

      for(int var2 = 0; var2 < var3; ++var2) {
         var1.writeObject(this.data[var2]);
      }

      var1.endElement();
   }

   public boolean consumeNext(int var1, Consumer var2) {
      var1 >>>= 1;
      if(var1 >= this.size) {
         return false;
      } else {
         var2.writeObject(this.data[var1]);
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
            var3.writeObject(this.data[var2]);
            ++var2;
         }
      }

   }

   public boolean equals(Object var1) {
      if(var1 != null && var1 instanceof FVector) {
         FVector var2 = (FVector)var1;
         int var4 = this.size;
         if(var2.data != null && var2.size == var4) {
            Object[] var5 = this.data;
            Object[] var6 = var2.data;
            int var3 = 0;

            while(true) {
               if(var3 >= var4) {
                  return true;
               }

               if(!var5[var3].equals(var6[var3])) {
                  break;
               }

               ++var3;
            }
         }
      }

      return false;
   }

   public final Object get(int var1) {
      if(var1 >= this.size) {
         throw new ArrayIndexOutOfBoundsException();
      } else {
         return this.data[var1];
      }
   }

   protected Object getBuffer() {
      return this.data;
   }

   public final Object getBuffer(int var1) {
      return this.data[var1];
   }

   public int getBufferLength() {
      return this.data.length;
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      int var4 = var1.readInt();
      Object[] var2 = new Object[var4];

      for(int var3 = 0; var3 < var4; ++var3) {
         var2[var3] = var1.readObject();
      }

      this.size = var4;
      this.data = var2;
   }

   public final void setAll(Object var1) {
      Object[] var2 = this.data;
      int var3 = this.size;

      while(true) {
         --var3;
         if(var3 < 0) {
            return;
         }

         var2[var3] = var1;
      }
   }

   public final Object setBuffer(int var1, Object var2) {
      Object var3 = this.data[var1];
      this.data[var1] = var2;
      return var3;
   }

   public void setBufferLength(int var1) {
      int var4 = this.data.length;
      if(var4 != var1) {
         Object[] var2 = new Object[var1];
         Object[] var3 = this.data;
         if(var4 < var1) {
            var1 = var4;
         }

         System.arraycopy(var3, 0, var2, 0, var1);
         this.data = var2;
      }

   }

   public void shift(int var1, int var2, int var3) {
      System.arraycopy(this.data, var1, this.data, var2, var3);
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      int var3 = this.size;
      var1.writeInt(var3);

      for(int var2 = 0; var2 < var3; ++var2) {
         var1.writeObject(this.data[var2]);
      }

   }
}
