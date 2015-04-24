package gnu.lists;

import gnu.lists.Consumer;
import gnu.lists.ItemPredicate;
import gnu.lists.SeqPosition;
import gnu.lists.Sequence;
import gnu.lists.SubSequence;
import gnu.lists.TreePosition;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public abstract class AbstractSequence {

   public static int compare(AbstractSequence var0, int var1, AbstractSequence var2, int var3) {
      return var0 == var2?var0.compare(var1, var3):var0.stableCompare(var2);
   }

   public static RuntimeException unsupportedException(String var0) {
      return new UnsupportedOperationException(var0);
   }

   public void add(int var1, Object var2) {
      var1 = this.createPos(var1, false);
      this.addPos(var1, var2);
      this.releasePos(var1);
   }

   public boolean add(Object var1) {
      this.addPos(this.endPos(), var1);
      return true;
   }

   public boolean addAll(int var1, Collection var2) {
      boolean var3 = false;
      var1 = this.createPos(var1, false);

      for(Iterator var4 = var2.iterator(); var4.hasNext(); var3 = true) {
         var1 = this.addPos(var1, var4.next());
      }

      this.releasePos(var1);
      return var3;
   }

   public boolean addAll(Collection var1) {
      return this.addAll(this.size(), var1);
   }

   protected int addPos(int var1, Object var2) {
      throw this.unsupported("addPos");
   }

   public void clear() {
      this.removePos(this.startPos(), this.endPos());
   }

   public int compare(int var1, int var2) {
      var1 = this.nextIndex(var1);
      var2 = this.nextIndex(var2);
      return var1 < var2?-1:(var1 > var2?1:0);
   }

   public final int compare(SeqPosition var1, SeqPosition var2) {
      return this.compare(var1.ipos, var2.ipos);
   }

   public void consume(Consumer var1) {
      boolean var2 = this instanceof Sequence;
      if(var2) {
         var1.startElement("#sequence");
      }

      this.consumePosRange(this.startPos(), this.endPos(), var1);
      if(var2) {
         var1.endElement();
      }

   }

   public boolean consumeNext(int var1, Consumer var2) {
      int var3 = this.nextPos(var1);
      if(var3 == 0) {
         return false;
      } else {
         this.consumePosRange(var1, var3, var2);
         return true;
      }
   }

   public void consumePosRange(int var1, int var2, Consumer var3) {
      if(!var3.ignoring()) {
         for(var1 = this.copyPos(var1); !this.equals(var1, var2); var1 = this.nextPos(var1)) {
            if(!this.hasNext(var1)) {
               throw new RuntimeException();
            }

            var3.writeObject(this.getPosNext(var1));
         }

         this.releasePos(var1);
      }
   }

   public boolean contains(Object var1) {
      return this.indexOf(var1) >= 0;
   }

   public boolean containsAll(Collection var1) {
      Iterator var2 = var1.iterator();

      do {
         if(!var2.hasNext()) {
            return true;
         }
      } while(this.contains(var2.next()));

      return false;
   }

   public int copyPos(int var1) {
      return var1;
   }

   public abstract int createPos(int var1, boolean var2);

   public int createRelativePos(int var1, int var2, boolean var3) {
      return this.createPos(this.nextIndex(var1) + var2, var3);
   }

   public final Enumeration elements() {
      return this.getIterator();
   }

   public int endPos() {
      return -1;
   }

   public boolean equals(int var1, int var2) {
      return this.compare(var1, var2) == 0;
   }

   public boolean equals(Object var1) {
      boolean var5 = true;
      boolean var6 = false;
      if(this instanceof List && var1 instanceof List) {
         Iterator var2 = this.iterator();
         Iterator var8 = ((List)var1).iterator();

         while(true) {
            boolean var7 = var2.hasNext();
            var5 = var6;
            if(var7 != var8.hasNext()) {
               break;
            }

            if(!var7) {
               return true;
            }

            Object var3 = var2.next();
            Object var4 = var8.next();
            if(var3 == null) {
               if(var4 != null) {
                  return false;
               }
            } else if(!var3.equals(var4)) {
               return false;
            }
         }
      } else if(this != var1) {
         var5 = false;
      }

      return var5;
   }

   public void fill(int var1, int var2, Object var3) {
      var1 = this.createPos(var1, false);

      for(var2 = this.createPos(var2, true); this.compare(var1, var2) < 0; var1 = this.nextPos(var1)) {
         this.setPosNext(var1, var3);
      }

      this.releasePos(var1);
      this.releasePos(var2);
   }

   public void fill(Object var1) {
      int var2 = this.startPos();

      while(true) {
         var2 = this.nextPos(var2);
         if(var2 == 0) {
            return;
         }

         this.setPosPrevious(var2, var1);
      }
   }

   public void fillPosRange(int var1, int var2, Object var3) {
      for(var1 = this.copyPos(var1); this.compare(var1, var2) < 0; var1 = this.nextPos(var1)) {
         this.setPosNext(var1, var3);
      }

      this.releasePos(var1);
   }

   public int firstAttributePos(int var1) {
      return 0;
   }

   public int firstChildPos(int var1) {
      return 0;
   }

   public int firstChildPos(int var1, ItemPredicate var2) {
      int var3 = this.firstChildPos(var1);
      if(var3 == 0) {
         var1 = 0;
      } else {
         var1 = var3;
         if(!var2.isInstancePos(this, var3)) {
            return this.nextMatching(var3, var2, this.endPos(), false);
         }
      }

      return var1;
   }

   protected int fromEndIndex(int var1) {
      return this.size() - this.nextIndex(var1);
   }

   public abstract Object get(int var1);

   public Object get(int[] var1) {
      return this.get(var1[0]);
   }

   public Object getAttribute(int var1) {
      return null;
   }

   public int getAttributeLength() {
      return 0;
   }

   protected int getContainingSequenceSize(int var1) {
      return this.size();
   }

   public int getEffectiveIndex(int[] var1) {
      return var1[0];
   }

   protected int getIndexDifference(int var1, int var2) {
      return this.nextIndex(var1) - this.nextIndex(var2);
   }

   public final SeqPosition getIterator() {
      return this.getIterator(0);
   }

   public SeqPosition getIterator(int var1) {
      return new SeqPosition(this, var1, false);
   }

   public SeqPosition getIteratorAtPos(int var1) {
      return new SeqPosition(this, this.copyPos(var1));
   }

   public int getLowBound(int var1) {
      return 0;
   }

   public int getNextKind(int var1) {
      return this.hasNext(var1)?32:0;
   }

   public String getNextTypeName(int var1) {
      return null;
   }

   public Object getNextTypeObject(int var1) {
      return null;
   }

   public Object getPosNext(int var1) {
      return !this.hasNext(var1)?Sequence.eofValue:this.get(this.nextIndex(var1));
   }

   public Object getPosPrevious(int var1) {
      var1 = this.nextIndex(var1);
      return var1 <= 0?Sequence.eofValue:this.get(var1 - 1);
   }

   public int getSize(int var1) {
      return var1 == 0?this.size():1;
   }

   protected boolean gotoAttributesStart(TreePosition var1) {
      return false;
   }

   public final boolean gotoChildrenStart(TreePosition var1) {
      int var2 = this.firstChildPos(var1.getPos());
      if(var2 == 0) {
         return false;
      } else {
         var1.push(this, var2);
         return true;
      }
   }

   protected boolean gotoParent(TreePosition var1) {
      if(var1.depth < 0) {
         return false;
      } else {
         var1.pop();
         return true;
      }
   }

   public boolean hasNext(int var1) {
      return this.nextIndex(var1) != this.size();
   }

   protected boolean hasPrevious(int var1) {
      return this.nextIndex(var1) != 0;
   }

   public int hashCode() {
      int var2 = 1;
      int var3 = this.startPos();

      while(true) {
         int var4 = this.nextPos(var3);
         if(var4 == 0) {
            return var2;
         }

         Object var1 = this.getPosPrevious(var4);
         if(var1 == null) {
            var3 = 0;
         } else {
            var3 = var1.hashCode();
         }

         var2 = var2 * 31 + var3;
         var3 = var4;
      }
   }

   public int indexOf(Object var1) {
      int var3 = 0;
      int var4 = this.startPos();

      while(true) {
         var4 = this.nextPos(var4);
         if(var4 == 0) {
            return -1;
         }

         Object var2 = this.getPosPrevious(var4);
         if(var1 == null) {
            if(var2 == null) {
               break;
            }
         } else if(var1.equals(var2)) {
            break;
         }

         ++var3;
      }

      this.releasePos(var4);
      return var3;
   }

   protected boolean isAfterPos(int var1) {
      return (var1 & 1) != 0;
   }

   public boolean isEmpty() {
      return this.size() == 0;
   }

   public final Iterator iterator() {
      return this.getIterator();
   }

   public int lastIndexOf(Object var1) {
      int var3 = this.size();

      while(true) {
         int var4 = var3 - 1;
         if(var4 < 0) {
            return -1;
         }

         Object var2 = this.get(var4);
         if(var1 == null) {
            var3 = var4;
            if(var2 == null) {
               return var4;
            }
         } else {
            var3 = var4;
            if(var1.equals(var2)) {
               return var4;
            }
         }
      }
   }

   public final ListIterator listIterator() {
      return this.getIterator(0);
   }

   public final ListIterator listIterator(int var1) {
      return this.getIterator(var1);
   }

   protected int nextIndex(int var1) {
      return this.getIndexDifference(var1, this.startPos());
   }

   public final int nextIndex(SeqPosition var1) {
      return this.nextIndex(var1.ipos);
   }

   public int nextMatching(int var1, ItemPredicate var2, int var3, boolean var4) {
      if(var4) {
         throw this.unsupported("nextMatching with descend");
      } else {
         while(this.compare(var1, var3) < 0) {
            int var5 = this.nextPos(var1);
            var1 = var5;
            if(var2.isInstancePos(this, var5)) {
               return var5;
            }
         }

         return 0;
      }
   }

   public int nextPos(int var1) {
      if(!this.hasNext(var1)) {
         return 0;
      } else {
         int var2 = this.createRelativePos(var1, 1, true);
         this.releasePos(var1);
         return var2;
      }
   }

   public int parentPos(int var1) {
      return this.endPos();
   }

   public int previousPos(int var1) {
      if(!this.hasPrevious(var1)) {
         return 0;
      } else {
         int var2 = this.createRelativePos(var1, -1, false);
         this.releasePos(var1);
         return var2;
      }
   }

   public int rank() {
      return 1;
   }

   protected void releasePos(int var1) {
   }

   public Object remove(int var1) {
      if(var1 >= 0 && var1 < this.size()) {
         var1 = this.createPos(var1, false);
         Object var2 = this.getPosNext(var1);
         this.removePos(var1, 1);
         this.releasePos(var1);
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
         var2 = this.createPos(var2, false);
         this.removePos(var2, 1);
         this.releasePos(var2);
         return true;
      }
   }

   public boolean removeAll(Collection var1) {
      boolean var4 = false;
      int var2 = this.startPos();

      while(true) {
         int var3 = this.nextPos(var2);
         if(var3 == 0) {
            return var4;
         }

         var2 = var3;
         if(var1.contains(this.getPosPrevious(var3))) {
            this.removePos(var3, -1);
            var4 = true;
            var2 = var3;
         }
      }
   }

   public void removePos(int var1, int var2) {
      int var3 = this.createRelativePos(var1, var2, false);
      if(var2 >= 0) {
         this.removePosRange(var1, var3);
      } else {
         this.removePosRange(var3, var1);
      }

      this.releasePos(var3);
   }

   protected void removePosRange(int var1, int var2) {
      throw this.unsupported("removePosRange");
   }

   public boolean retainAll(Collection var1) {
      boolean var4 = false;
      int var2 = this.startPos();

      while(true) {
         int var3 = this.nextPos(var2);
         if(var3 == 0) {
            return var4;
         }

         var2 = var3;
         if(!var1.contains(this.getPosPrevious(var3))) {
            this.removePos(var3, -1);
            var4 = true;
            var2 = var3;
         }
      }
   }

   public Object set(int var1, Object var2) {
      throw this.unsupported("set");
   }

   public Object set(int[] var1, Object var2) {
      return this.set(var1[0], var2);
   }

   protected void setPosNext(int var1, Object var2) {
      var1 = this.nextIndex(var1);
      if(var1 >= this.size()) {
         throw new IndexOutOfBoundsException();
      } else {
         this.set(var1, var2);
      }
   }

   protected void setPosPrevious(int var1, Object var2) {
      var1 = this.nextIndex(var1);
      if(var1 == 0) {
         throw new IndexOutOfBoundsException();
      } else {
         this.set(var1 - 1, var2);
      }
   }

   public abstract int size();

   public int stableCompare(AbstractSequence var1) {
      int var2 = System.identityHashCode(this);
      int var3 = System.identityHashCode(var1);
      return var2 < var3?-1:(var2 > var3?1:0);
   }

   public int startPos() {
      return 0;
   }

   public List subList(int var1, int var2) {
      return this.subSequencePos(this.createPos(var1, false), this.createPos(var2, true));
   }

   public Sequence subSequence(SeqPosition var1, SeqPosition var2) {
      return this.subSequencePos(var1.ipos, var2.ipos);
   }

   protected Sequence subSequencePos(int var1, int var2) {
      return new SubSequence(this, var1, var2);
   }

   public Object[] toArray() {
      Object[] var1 = new Object[this.size()];
      int var3 = this.startPos();
      int var2 = 0;

      while(true) {
         var3 = this.nextPos(var3);
         if(var3 == 0) {
            return var1;
         }

         var1[var2] = this.getPosPrevious(var3);
         ++var2;
      }
   }

   public Object[] toArray(Object[] var1) {
      int var4 = var1.length;
      int var6 = this.size();
      int var3 = var4;
      Object[] var2 = var1;
      if(var6 > var4) {
         var2 = (Object[])((Object[])java.lang.reflect.Array.newInstance(var1.getClass().getComponentType(), var6));
         var3 = var6;
      }

      int var5 = this.startPos();
      var4 = 0;

      while(true) {
         var5 = this.nextPos(var5);
         if(var5 == 0) {
            if(var6 < var3) {
               var2[var6] = null;
            }

            return var2;
         }

         var2[var4] = this.getPosPrevious(var5);
         ++var4;
      }
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer(100);
      if(this instanceof Sequence) {
         var1.append('[');
      }

      this.toString(", ", var1);
      if(this instanceof Sequence) {
         var1.append(']');
      }

      return var1.toString();
   }

   public void toString(String var1, StringBuffer var2) {
      boolean var3 = false;
      int var4 = this.startPos();

      while(true) {
         var4 = this.nextPos(var4);
         if(var4 == 0) {
            return;
         }

         if(var3) {
            var2.append(var1);
         } else {
            var3 = true;
         }

         var2.append(this.getPosPrevious(var4));
      }
   }

   protected RuntimeException unsupported(String var1) {
      return unsupportedException(this.getClass().getName() + " does not implement " + var1);
   }
}
