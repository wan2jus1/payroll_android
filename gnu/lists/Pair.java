package gnu.lists;

import gnu.lists.LList;
import gnu.lists.LListPosition;
import gnu.lists.PositionManager;
import gnu.lists.Sequence;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;

public class Pair extends LList implements Externalizable {

   protected Object car;
   protected Object cdr;


   public Pair() {
   }

   public Pair(Object var1, Object var2) {
      this.car = var1;
      this.cdr = var2;
   }

   public static int compareTo(Pair var0, Pair var1) {
      int var4;
      if(var0 == var1) {
         var4 = 0;
      } else {
         if(var0 == null) {
            return -1;
         }

         Pair var2 = var0;
         var0 = var1;
         if(var1 == null) {
            return 1;
         }

         while(true) {
            Object var7 = var2.car;
            Object var3 = var0.car;
            int var5 = ((Comparable)var7).compareTo((Comparable)var3);
            var4 = var5;
            if(var5 != 0) {
               break;
            }

            var7 = var2.cdr;
            Object var6 = var0.cdr;
            if(var7 == var6) {
               return 0;
            }

            if(var7 == null) {
               return -1;
            }

            if(var6 == null) {
               return 1;
            }

            if(!(var7 instanceof Pair) || !(var6 instanceof Pair)) {
               return ((Comparable)var7).compareTo((Comparable)var6);
            }

            var2 = (Pair)var7;
            var0 = (Pair)var6;
         }
      }

      return var4;
   }

   public static boolean equals(Pair var0, Pair var1) {
      if(var0 != var1) {
         if(var0 != null) {
            Pair var2 = var0;
            var0 = var1;
            if(var1 != null) {
               while(true) {
                  Object var5 = var2.car;
                  Object var3 = var0.car;
                  if(var5 != var3 && (var5 == null || !var5.equals(var3))) {
                     return false;
                  }

                  var5 = var2.cdr;
                  Object var4 = var0.cdr;
                  if(var5 == var4) {
                     return true;
                  }

                  if(var5 != null && var4 != null) {
                     if(var5 instanceof Pair && var4 instanceof Pair) {
                        var2 = (Pair)var5;
                        var0 = (Pair)var4;
                        continue;
                     }

                     return var5.equals(var4);
                  }

                  return false;
               }
            }
         }

         return false;
      } else {
         return true;
      }
   }

   public static Pair make(Object var0, Object var1) {
      return new Pair(var0, var1);
   }

   public int compareTo(Object var1) {
      return var1 == Empty?1:compareTo(this, (Pair)var1);
   }

   public boolean equals(Object var1) {
      return var1 != null && var1 instanceof Pair?equals(this, (Pair)var1):false;
   }

   public Object get(int var1) {
      Pair var2 = this;

      int var3;
      while(true) {
         var3 = var1;
         if(var1 <= 0) {
            break;
         }

         --var1;
         if(!(var2.cdr instanceof Pair)) {
            var3 = var1;
            if(var2.cdr instanceof Sequence) {
               return ((Sequence)var2.cdr).get(var1);
            }
            break;
         }

         var2 = (Pair)var2.cdr;
      }

      if(var3 == 0) {
         return var2.car;
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public Object getCar() {
      return this.car;
   }

   public Object getCdr() {
      return this.cdr;
   }

   public Object getPosNext(int var1) {
      return var1 <= 0?(var1 == 0?this.car:eofValue):PositionManager.getPositionObject(var1).getNext();
   }

   public Object getPosPrevious(int var1) {
      return var1 <= 0?(var1 == 0?eofValue:this.lastPair().car):PositionManager.getPositionObject(var1).getPrevious();
   }

   public boolean hasNext(int var1) {
      return var1 <= 0?var1 == 0:PositionManager.getPositionObject(var1).hasNext();
   }

   public int hashCode() {
      int var3 = 1;

      Object var1;
      int var4;
      Pair var5;
      for(var1 = this; var1 instanceof Pair; var1 = var5.cdr) {
         var5 = (Pair)var1;
         Object var2 = var5.car;
         if(var2 == null) {
            var4 = 0;
         } else {
            var4 = var2.hashCode();
         }

         var3 = var3 * 31 + var4;
      }

      var4 = var3;
      if(var1 != LList.Empty) {
         var4 = var3;
         if(var1 != null) {
            var4 = var3 ^ var1.hashCode();
         }
      }

      return var4;
   }

   public boolean isEmpty() {
      return false;
   }

   public final Pair lastPair() {
      Pair var1 = this;

      while(true) {
         Object var2 = var1.cdr;
         if(!(var2 instanceof Pair)) {
            return var1;
         }

         var1 = (Pair)var2;
      }
   }

   public int length() {
      int var4 = 0;
      Object var1 = this;
      Object var2 = this;

      while(var1 != Empty) {
         if(!(var1 instanceof Pair)) {
            if(var1 instanceof Sequence) {
               int var6 = ((Sequence)var1).size();
               int var5 = var6;
               if(var6 >= 0) {
                  var5 = var6 + var4;
               }

               return var5;
            }

            return -2;
         }

         Pair var3 = (Pair)var1;
         if(var3.cdr == Empty) {
            return var4 + 1;
         }

         if(var1 == var2 && var4 > 0) {
            return -1;
         }

         if(!(var3.cdr instanceof Pair)) {
            ++var4;
            var1 = var3.cdr;
         } else {
            if(!(var2 instanceof Pair)) {
               return -2;
            }

            var2 = ((Pair)var2).cdr;
            var1 = ((Pair)var3.cdr).cdr;
            var4 += 2;
         }
      }

      return var4;
   }

   public int nextPos(int var1) {
      if(var1 <= 0) {
         return var1 < 0?0:PositionManager.manager.register(new LListPosition(this, 1, true));
      } else {
         if(!((LListPosition)PositionManager.getPositionObject(var1)).gotoNext()) {
            var1 = 0;
         }

         return var1;
      }
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      this.car = var1.readObject();
      this.cdr = var1.readObject();
   }

   public Object readResolve() throws ObjectStreamException {
      return this;
   }

   public void setCar(Object var1) {
      this.car = var1;
   }

   public void setCdr(Object var1) {
      this.cdr = var1;
   }

   public void setCdrBackdoor(Object var1) {
      this.cdr = var1;
   }

   public int size() {
      int var1 = listLength(this, true);
      if(var1 >= 0) {
         return var1;
      } else if(var1 == -1) {
         return Integer.MAX_VALUE;
      } else {
         throw new RuntimeException("not a true list");
      }
   }

   public Object[] toArray() {
      int var5 = this.size();
      Object[] var2 = new Object[var5];
      int var3 = 0;

      Object var1;
      for(var1 = this; var3 < var5 && var1 instanceof Pair; ++var3) {
         Pair var6 = (Pair)var1;
         var2[var3] = var6.car;
         var1 = (Sequence)var6.cdr;
      }

      for(int var4 = var3; var4 < var5; ++var4) {
         var2[var4] = ((Sequence)var1).get(var4 - var3);
      }

      return var2;
   }

   public Object[] toArray(Object[] var1) {
      int var3 = var1.length;
      int var6 = this.length();
      int var5 = var3;
      if(var6 > var3) {
         var1 = new Object[var6];
         var5 = var6;
      }

      var3 = 0;

      Object var2;
      for(var2 = this; var3 < var6 && var2 instanceof Pair; ++var3) {
         Pair var7 = (Pair)var2;
         var1[var3] = var7.car;
         var2 = (Sequence)var7.cdr;
      }

      for(int var4 = var3; var4 < var6; ++var4) {
         var1[var4] = ((Sequence)var2).get(var4 - var3);
      }

      if(var6 < var5) {
         var1[var6] = null;
      }

      return var1;
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      var1.writeObject(this.car);
      var1.writeObject(this.cdr);
   }
}
