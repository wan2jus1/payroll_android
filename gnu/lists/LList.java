package gnu.lists;

import gnu.lists.Consumer;
import gnu.lists.ExtSequence;
import gnu.lists.LListPosition;
import gnu.lists.Pair;
import gnu.lists.PositionManager;
import gnu.lists.SeqPosition;
import gnu.lists.Sequence;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;
import java.util.Iterator;
import java.util.List;

public class LList extends ExtSequence implements Sequence, Externalizable, Comparable {

   public static final LList Empty = new LList();


   public static Pair chain1(Pair var0, Object var1) {
      Pair var2 = new Pair(var1, Empty);
      var0.cdr = var2;
      return var2;
   }

   public static Pair chain4(Pair var0, Object var1, Object var2, Object var3, Object var4) {
      Pair var5 = new Pair(var4, Empty);
      var0.cdr = new Pair(var1, new Pair(var2, new Pair(var3, var5)));
      return var5;
   }

   public static Object checkNonList(Object var0) {
      Object var1 = var0;
      if(var0 instanceof LList) {
         var1 = "#<not a pair>";
      }

      return var1;
   }

   public static Object consX(Object[] var0) {
      Object var1 = var0[0];
      int var5 = var0.length - 1;
      if(var5 <= 0) {
         return var1;
      } else {
         Pair var2 = new Pair(var1, (Object)null);
         Pair var6 = var2;

         for(int var4 = 1; var4 < var5; ++var4) {
            Pair var3 = new Pair(var0[var4], (Object)null);
            var6.cdr = var3;
            var6 = var3;
         }

         var6.cdr = var0[var5];
         return var2;
      }
   }

   public static final int length(Object var0) {
      int var1;
      for(var1 = 0; var0 instanceof Pair; var0 = ((Pair)var0).cdr) {
         ++var1;
      }

      return var1;
   }

   public static Pair list1(Object var0) {
      return new Pair(var0, Empty);
   }

   public static Pair list2(Object var0, Object var1) {
      return new Pair(var0, new Pair(var1, Empty));
   }

   public static Pair list3(Object var0, Object var1, Object var2) {
      return new Pair(var0, new Pair(var1, new Pair(var2, Empty)));
   }

   public static Pair list4(Object var0, Object var1, Object var2, Object var3) {
      return new Pair(var0, new Pair(var1, new Pair(var2, new Pair(var3, Empty))));
   }

   public static int listLength(Object var0, boolean var1) {
      int var4 = 0;
      Object var2 = var0;

      while(var0 != Empty) {
         if(!(var0 instanceof Pair)) {
            if(var0 instanceof Sequence && var1) {
               int var6 = ((Sequence)var0).size();
               int var5 = var6;
               if(var6 >= 0) {
                  var5 = var6 + var4;
               }

               return var5;
            }

            return -2;
         }

         Pair var3 = (Pair)var0;
         if(var3.cdr == Empty) {
            return var4 + 1;
         }

         if(var0 == var2 && var4 > 0) {
            return -1;
         }

         if(!(var3.cdr instanceof Pair)) {
            ++var4;
            var0 = var3.cdr;
         } else {
            if(!(var2 instanceof Pair)) {
               return -2;
            }

            var2 = ((Pair)var2).cdr;
            var0 = ((Pair)var3.cdr).cdr;
            var4 += 2;
         }
      }

      return var4;
   }

   public static Object listTail(Object var0, int var1) {
      while(true) {
         --var1;
         if(var1 < 0) {
            return var0;
         }

         if(!(var0 instanceof Pair)) {
            throw new IndexOutOfBoundsException("List is too short.");
         }

         var0 = ((Pair)var0).cdr;
      }
   }

   public static LList makeList(List var0) {
      Iterator var3 = var0.iterator();
      Object var2 = Empty;

      Pair var4;
      for(Pair var1 = null; var3.hasNext(); var1 = var4) {
         var4 = new Pair(var3.next(), Empty);
         if(var1 == null) {
            var2 = var4;
         } else {
            var1.cdr = var4;
         }
      }

      return (LList)var2;
   }

   public static LList makeList(Object[] var0, int var1) {
      Object var2 = Empty;
      int var3 = var0.length - var1;

      while(true) {
         --var3;
         if(var3 < 0) {
            return (LList)var2;
         }

         var2 = new Pair(var0[var1 + var3], var2);
      }
   }

   public static LList makeList(Object[] var0, int var1, int var2) {
      Object var3 = Empty;

      while(true) {
         --var2;
         if(var2 < 0) {
            return (LList)var3;
         }

         var3 = new Pair(var0[var1 + var2], var3);
      }
   }

   public static LList reverseInPlace(Object var0) {
      LList var2 = Empty;
      Object var1 = var0;

      Pair var3;
      for(var0 = var2; var1 != Empty; var0 = var3) {
         var3 = (Pair)var1;
         var1 = var3.cdr;
         var3.cdr = var0;
      }

      return (LList)var0;
   }

   public int compareTo(Object var1) {
      return var1 == Empty?0:-1;
   }

   public void consume(Consumer var1) {
      Object var2 = this;
      var1.startElement("list");

      while(var2 instanceof Pair) {
         if(var2 != this) {
            var1.write(32);
         }

         Pair var3 = (Pair)var2;
         var1.writeObject(var3.car);
         var2 = var3.cdr;
      }

      if(var2 != Empty) {
         var1.write(32);
         var1.write(". ");
         var1.writeObject(checkNonList(var2));
      }

      var1.endElement();
   }

   public int createPos(int var1, boolean var2) {
      LListPosition var3 = new LListPosition(this, var1, var2);
      return PositionManager.manager.register(var3);
   }

   public int createRelativePos(int var1, int var2, boolean var3) {
      boolean var9 = this.isAfterPos(var1);
      if(var2 >= 0 && var1 != 0) {
         if(var2 == 0) {
            if(var3 == var9) {
               return this.copyPos(var1);
            }

            if(var3 && !var9) {
               return super.createRelativePos(var1, var2, var3);
            }
         }

         if(var1 < 0) {
            throw new IndexOutOfBoundsException();
         } else {
            LListPosition var4 = (LListPosition)PositionManager.getPositionObject(var1);
            if(var4.xpos == null) {
               return super.createRelativePos(var1, var2, var3);
            } else {
               LListPosition var6 = new LListPosition(var4);
               Object var5 = var6.xpos;
               int var8 = var6.ipos;
               var1 = var8;
               int var7 = var2;
               if(var3) {
                  var1 = var8;
                  var7 = var2;
                  if(!var9) {
                     var7 = var2 - 1;
                     var1 = var8 + 3;
                  }
               }

               var2 = var1;
               Object var10 = var5;
               var8 = var7;
               if(!var3) {
                  var2 = var1;
                  var10 = var5;
                  var8 = var7;
                  if(var9) {
                     var8 = var7 + 1;
                     var2 = var1 - 3;
                     var10 = var5;
                  }
               }

               while(var10 instanceof Pair) {
                  --var8;
                  if(var8 < 0) {
                     var6.ipos = var2;
                     var6.xpos = var10;
                     return PositionManager.manager.register(var6);
                  }

                  Pair var11 = (Pair)var10;
                  var2 += 2;
                  var10 = var11.cdr;
               }

               throw new IndexOutOfBoundsException();
            }
         }
      } else {
         return super.createRelativePos(var1, var2, var3);
      }
   }

   public boolean equals(Object var1) {
      return this == var1;
   }

   public Object get(int var1) {
      throw new IndexOutOfBoundsException();
   }

   public SeqPosition getIterator(int var1) {
      return new LListPosition(this, var1, false);
   }

   public Object getPosNext(int var1) {
      return eofValue;
   }

   public Object getPosPrevious(int var1) {
      return eofValue;
   }

   public boolean hasNext(int var1) {
      return false;
   }

   public boolean isEmpty() {
      return true;
   }

   public int nextPos(int var1) {
      return 0;
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
   }

   public Object readResolve() throws ObjectStreamException {
      return Empty;
   }

   protected void setPosNext(int var1, Object var2) {
      if(var1 <= 0) {
         if(var1 != -1 && this instanceof Pair) {
            ((Pair)this).car = var2;
         } else {
            throw new IndexOutOfBoundsException();
         }
      } else {
         PositionManager.getPositionObject(var1).setNext(var2);
      }
   }

   protected void setPosPrevious(int var1, Object var2) {
      if(var1 <= 0) {
         if(var1 != 0 && this instanceof Pair) {
            ((Pair)this).lastPair().car = var2;
         } else {
            throw new IndexOutOfBoundsException();
         }
      } else {
         PositionManager.getPositionObject(var1).setPrevious(var2);
      }
   }

   public int size() {
      return 0;
   }

   public String toString() {
      Object var1 = this;
      int var3 = 0;
      StringBuffer var2 = new StringBuffer(100);
      var2.append('(');

      while(var1 != Empty) {
         if(var3 > 0) {
            var2.append(' ');
         }

         if(var3 >= 10) {
            var2.append("...");
            break;
         }

         if(!(var1 instanceof Pair)) {
            var2.append(". ");
            var2.append(checkNonList(var1));
            break;
         }

         Pair var4 = (Pair)var1;
         var2.append(var4.car);
         var1 = var4.cdr;
         ++var3;
      }

      var2.append(')');
      return var2.toString();
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
   }
}
