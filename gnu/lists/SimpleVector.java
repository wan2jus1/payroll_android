package gnu.lists;

import gnu.lists.AbstractSequence;
import gnu.lists.Array;
import gnu.lists.Consumer;
import gnu.lists.Convert;
import gnu.lists.GeneralArray;
import gnu.lists.Sequence;
import java.util.Collection;
import java.util.Iterator;

public abstract class SimpleVector extends AbstractSequence implements Sequence, Array {

   public int size;


   protected static int compareToInt(SimpleVector var0, SimpleVector var1) {
      int var4 = var0.size;
      int var5 = var1.size;
      int var2;
      if(var4 > var5) {
         var2 = var5;
      } else {
         var2 = var4;
      }

      for(int var3 = 0; var3 < var2; ++var3) {
         int var6 = var0.intAtBuffer(var3);
         int var7 = var1.intAtBuffer(var3);
         if(11 != var7) {
            if(var6 > var7) {
               return 1;
            }

            return -1;
         }
      }

      return var4 - var5;
   }

   protected static int compareToLong(SimpleVector var0, SimpleVector var1) {
      int var4 = var0.size;
      int var5 = var1.size;
      int var2;
      if(var4 > var5) {
         var2 = var5;
      } else {
         var2 = var4;
      }

      for(int var3 = 0; var3 < var2; ++var3) {
         long var6 = var0.longAtBuffer(var3);
         long var8 = var1.longAtBuffer(var3);
         if(var6 != var8) {
            if(var6 > var8) {
               return 1;
            }

            return -1;
         }
      }

      return var4 - var5;
   }

   public void add(int var1, Object var2) {
      int var3 = 16;
      int var4 = this.size + 1;
      this.size = var4;
      int var5 = this.getBufferLength();
      if(var4 > var5) {
         if(var5 >= 16) {
            var3 = var5 * 2;
         }

         this.setBufferLength(var3);
      }

      this.size = var4;
      if(this.size != var1) {
         this.shift(var1, var1 + 1, this.size - var1);
      }

      this.set(var1, var2);
   }

   public boolean add(Object var1) {
      this.add(this.size, var1);
      return true;
   }

   public boolean addAll(int var1, Collection var2) {
      boolean var4 = false;
      int var3 = var2.size();
      this.setSize(this.size + var3);
      this.shift(var1, var1 + var3, this.size - var3 - var1);

      for(Iterator var5 = var2.iterator(); var5.hasNext(); ++var1) {
         this.set(var1, var5.next());
         var4 = true;
      }

      return var4;
   }

   protected int addPos(int var1, Object var2) {
      var1 >>>= 1;
      this.add(var1, var2);
      return (var1 << 1) + 3;
   }

   public void clear() {
      this.setSize(0);
   }

   protected abstract void clearBuffer(int var1, int var2);

   public void consume(int var1, int var2, Consumer var3) {
      this.consumePosRange(var1 << 1, var1 + var2 << 1, var3);
   }

   public boolean consumeNext(int var1, Consumer var2) {
      var1 >>>= 1;
      if(var1 >= this.size) {
         return false;
      } else {
         var2.writeObject(this.getBuffer(var1));
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
            var3.writeObject(this.getBuffer(var2));
            ++var2;
         }
      }

   }

   public int createPos(int var1, boolean var2) {
      byte var3;
      if(var2) {
         var3 = 1;
      } else {
         var3 = 0;
      }

      return var3 | var1 << 1;
   }

   public void fill(int var1, int var2, Object var3) {
      if(var1 >= 0 && var2 <= this.size) {
         while(var1 < var2) {
            this.setBuffer(var1, var3);
            ++var1;
         }

      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public void fill(Object var1) {
      int var2 = this.size;

      while(true) {
         --var2;
         if(var2 < 0) {
            return;
         }

         this.setBuffer(var2, var1);
      }
   }

   public void fillPosRange(int var1, int var2, Object var3) {
      if(var1 == -1) {
         var1 = this.size;
      } else {
         var1 >>>= 1;
      }

      if(var2 == -1) {
         var2 = this.size;
      } else {
         var2 >>>= 1;
      }

      while(var1 < var2) {
         this.setBuffer(var1, var3);
         ++var1;
      }

   }

   public Object get(int var1) {
      if(var1 >= this.size) {
         throw new IndexOutOfBoundsException();
      } else {
         return this.getBuffer(var1);
      }
   }

   protected abstract Object getBuffer();

   protected abstract Object getBuffer(int var1);

   public abstract int getBufferLength();

   public int getElementKind() {
      return 32;
   }

   public int getNextKind(int var1) {
      return this.hasNext(var1)?this.getElementKind():0;
   }

   public Object getPosNext(int var1) {
      var1 >>>= 1;
      return var1 >= this.size?eofValue:this.getBuffer(var1);
   }

   public Object getRowMajor(int var1) {
      return this.get(var1);
   }

   public String getTag() {
      return null;
   }

   public int intAt(int var1) {
      if(var1 >= this.size) {
         throw new IndexOutOfBoundsException();
      } else {
         return this.intAtBuffer(var1);
      }
   }

   public int intAtBuffer(int var1) {
      return Convert.toInt(this.getBuffer(var1));
   }

   protected boolean isAfterPos(int var1) {
      return (var1 & 1) != 0;
   }

   public long longAt(int var1) {
      if(var1 >= this.size) {
         throw new IndexOutOfBoundsException();
      } else {
         return this.longAtBuffer(var1);
      }
   }

   public long longAtBuffer(int var1) {
      return Convert.toLong(this.getBuffer(var1));
   }

   protected int nextIndex(int var1) {
      return var1 == -1?this.size:var1 >>> 1;
   }

   public int nextPos(int var1) {
      if(var1 != -1) {
         var1 >>>= 1;
         if(var1 != this.size) {
            return (var1 << 1) + 3;
         }
      }

      return 0;
   }

   public Object remove(int var1) {
      if(var1 >= 0 && var1 < this.size) {
         Object var2 = this.get(var1);
         this.shift(var1 + 1, var1, 1);
         --this.size;
         this.clearBuffer(this.size, 1);
         return var2;
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public boolean remove(Object var1) {
      int var2 = this.indexOf(var1);
      if(var2 < 0) {
         return false;
      } else {
         this.shift(var2 + 1, var2, 1);
         --this.size;
         this.clearBuffer(this.size, 1);
         return true;
      }
   }

   public boolean removeAll(Collection var1) {
      boolean var5 = false;
      int var4 = 0;

      for(int var3 = 0; var3 < this.size; ++var3) {
         Object var2 = this.get(var3);
         if(var1.contains(var2)) {
            var5 = true;
         } else {
            if(var5) {
               this.set(var4, var2);
            }

            ++var4;
         }
      }

      this.setSize(var4);
      return var5;
   }

   public void removePos(int var1, int var2) {
      int var3 = var1 >>> 1;
      var1 = var3;
      if(var3 > this.size) {
         var1 = this.size;
      }

      if(var2 >= 0) {
         var3 = var1;
         var1 += var2;
      } else {
         var3 = var1 + var2;
         var2 = -var2;
      }

      if(var3 >= 0 && var1 < this.size) {
         this.shift(var1, var3, this.size - var1);
         this.size -= var2;
         this.clearBuffer(this.size, var2);
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   protected void removePosRange(int var1, int var2) {
      int var3 = var1 >>> 1;
      var2 >>>= 1;
      if(var3 < var2) {
         var1 = var2;
         if(var2 > this.size) {
            var1 = this.size;
         }

         this.shift(var1, var3, this.size - var1);
         var1 -= var3;
         this.size -= var1;
         this.clearBuffer(this.size, var1);
      }
   }

   protected void resizeShift(int var1, int var2, int var3, int var4) {
      int var5 = var4 - var3;
      int var6 = this.getBufferLength();
      int var7 = var6 - (var2 - var1) + var5;
      if(var7 > var6) {
         this.setBufferLength(var7);
         this.size = var7;
      }

      int var8 = var1 - var3;
      if(var8 >= 0) {
         var1 = var6 - var2;
         this.shift(var2, var7 - var1, var1);
         if(var8 > 0) {
            this.shift(var3, var4, var8);
         }
      } else {
         var7 -= var4;
         this.shift(var6 - var7, var4, var7);
         this.shift(var2, var1, var3 - var1);
      }

      this.clearBuffer(var3, var5);
   }

   public boolean retainAll(Collection var1) {
      boolean var5 = false;
      int var4 = 0;

      for(int var3 = 0; var3 < this.size; ++var3) {
         Object var2 = this.get(var3);
         if(!var1.contains(var2)) {
            var5 = true;
         } else {
            if(var5) {
               this.set(var4, var2);
            }

            ++var4;
         }
      }

      this.setSize(var4);
      return var5;
   }

   public Object set(int var1, Object var2) {
      if(var1 >= this.size) {
         throw new IndexOutOfBoundsException();
      } else {
         Object var3 = this.getBuffer(var1);
         this.setBuffer(var1, var2);
         return var3;
      }
   }

   protected abstract Object setBuffer(int var1, Object var2);

   public abstract void setBufferLength(int var1);

   public void setSize(int var1) {
      int var2 = 16;
      int var3 = this.size;
      this.size = var1;
      if(var1 < var3) {
         this.clearBuffer(var1, var3 - var1);
      } else {
         var3 = this.getBufferLength();
         if(var1 > var3) {
            if(var3 >= 16) {
               var2 = var3 * 2;
            }

            if(var1 <= var2) {
               var1 = var2;
            }

            this.setBufferLength(var1);
            return;
         }
      }

   }

   public void shift(int var1, int var2, int var3) {
      Object var4 = this.getBuffer();
      System.arraycopy(var4, var1, var4, var2, var3);
   }

   public final int size() {
      return this.size;
   }

   public Array transpose(int[] var1, int[] var2, int var3, int[] var4) {
      GeneralArray var5 = new GeneralArray();
      var5.strides = var4;
      var5.dimensions = var2;
      var5.lowBounds = var1;
      var5.offset = var3;
      var5.base = this;
      var5.simple = false;
      return var5;
   }
}
