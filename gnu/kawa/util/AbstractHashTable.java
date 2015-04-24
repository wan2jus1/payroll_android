package gnu.kawa.util;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Map.Entry;

public abstract class AbstractHashTable extends AbstractMap {

   public static final int DEFAULT_INITIAL_SIZE = 64;
   protected int mask;
   protected int num_bindings;
   protected Entry[] table;


   public AbstractHashTable() {
      this(64);
   }

   public AbstractHashTable(int var1) {
      int var2;
      for(var2 = 4; var1 > 1 << var2; ++var2) {
         ;
      }

      var1 = 1 << var2;
      this.table = this.allocEntries(var1);
      this.mask = var1 - 1;
   }

   protected abstract Entry[] allocEntries(int var1);

   public void clear() {
      Entry[] var3 = this.table;
      int var4 = var3.length;

      while(true) {
         --var4;
         if(var4 < 0) {
            this.num_bindings = 0;
            return;
         }

         Entry var2;
         for(Entry var1 = var3[var4]; var1 != null; var1 = var2) {
            var2 = this.getEntryNext(var1);
            this.setEntryNext(var1, (Entry)null);
         }

         var3[var4] = null;
      }
   }

   public Set entrySet() {
      return new AbstractHashTable.AbstractEntrySet(this);
   }

   public Object get(Object var1) {
      return this.get(var1, (Object)null);
   }

   public Object get(Object var1, Object var2) {
      Entry var3 = this.getNode(var1);
      return var3 == null?var2:var3.getValue();
   }

   protected abstract int getEntryHashCode(Entry var1);

   protected abstract Entry getEntryNext(Entry var1);

   public Entry getNode(Object var1) {
      int var3 = this.hash(var1);
      int var4 = this.hashToIndex(var3);

      for(Entry var2 = this.table[var4]; var2 != null; var2 = this.getEntryNext(var2)) {
         if(this.matches(var1, var3, var2)) {
            return var2;
         }
      }

      return null;
   }

   public int hash(Object var1) {
      return var1 == null?0:var1.hashCode();
   }

   protected int hashToIndex(int var1) {
      return this.mask & (var1 ^ var1 >>> 15);
   }

   protected abstract Entry makeEntry(Object var1, int var2, Object var3);

   protected boolean matches(Object var1, int var2, Entry var3) {
      return this.getEntryHashCode(var3) == var2 && this.matches(var3.getKey(), var1);
   }

   protected boolean matches(Object var1, Object var2) {
      return var1 == var2 || var1 != null && var1.equals(var2);
   }

   public Object put(Object var1, int var2, Object var3) {
      int var6 = this.hashToIndex(var2);
      Entry var4 = this.table[var6];

      for(Entry var5 = var4; var5 != null; var5 = this.getEntryNext(var5)) {
         if(this.matches(var1, var2, var5)) {
            var1 = var5.getValue();
            var5.setValue(var3);
            return var1;
         }
      }

      int var7 = this.num_bindings + 1;
      this.num_bindings = var7;
      if(var7 >= this.table.length) {
         this.rehash();
         var6 = this.hashToIndex(var2);
         var4 = this.table[var6];
      }

      Entry var8 = this.makeEntry(var1, var2, var3);
      this.setEntryNext(var8, var4);
      this.table[var6] = var8;
      return null;
   }

   public Object put(Object var1, Object var2) {
      return this.put(var1, this.hash(var1), var2);
   }

   protected void rehash() {
      Entry[] var5 = this.table;
      int var7 = var5.length;
      int var8 = var7 * 2;
      Entry[] var6 = this.allocEntries(var8);
      this.table = var6;
      this.mask = var8 - 1;

      while(true) {
         var8 = var7 - 1;
         if(var8 < 0) {
            return;
         }

         Entry var2 = var5[var8];
         Entry var1 = var2;
         if(var2 != null) {
            var1 = var2;
            if(this.getEntryNext(var2) != null) {
               Entry var3 = null;

               Entry var4;
               do {
                  var1 = var2;
                  var4 = this.getEntryNext(var2);
                  this.setEntryNext(var2, var3);
                  var2 = var4;
                  var3 = var1;
               } while(var4 != null);
            }
         }

         while(true) {
            var7 = var8;
            if(var1 == null) {
               break;
            }

            var2 = this.getEntryNext(var1);
            var7 = this.hashToIndex(this.getEntryHashCode(var1));
            this.setEntryNext(var1, var6[var7]);
            var6[var7] = var1;
            var1 = var2;
         }
      }
   }

   public Object remove(Object var1) {
      int var5 = this.hash(var1);
      int var6 = this.hashToIndex(var5);
      Entry var3 = null;

      Entry var4;
      for(Entry var2 = this.table[var6]; var2 != null; var2 = var4) {
         var4 = this.getEntryNext(var2);
         if(this.matches(var1, var5, var2)) {
            if(var3 == null) {
               this.table[var6] = var4;
            } else {
               this.setEntryNext(var3, var4);
            }

            --this.num_bindings;
            return var2.getValue();
         }

         var3 = var2;
      }

      return null;
   }

   protected abstract void setEntryNext(Entry var1, Entry var2);

   public int size() {
      return this.num_bindings;
   }

   static class AbstractEntrySet extends AbstractSet {

      AbstractHashTable htable;


      public AbstractEntrySet(AbstractHashTable var1) {
         this.htable = var1;
      }

      public Iterator iterator() {
         return new Iterator() {

            int curIndex = -1;
            Entry currentEntry;
            Entry nextEntry;
            int nextIndex;
            Entry previousEntry;

            private void advance() {
               while(true) {
                  if(this.nextEntry == null) {
                     int var1 = this.nextIndex - 1;
                     this.nextIndex = var1;
                     if(var1 >= 0) {
                        this.nextEntry = AbstractEntrySet.this.htable.table[this.nextIndex];
                        continue;
                     }
                  }

                  return;
               }
            }
            public boolean hasNext() {
               if(this.curIndex < 0) {
                  this.nextIndex = AbstractEntrySet.this.htable.table.length;
                  this.curIndex = this.nextIndex;
                  this.advance();
               }

               return this.nextEntry != null;
            }
            public Entry next() {
               if(this.nextEntry == null) {
                  throw new NoSuchElementException();
               } else {
                  this.previousEntry = this.currentEntry;
                  this.currentEntry = this.nextEntry;
                  this.curIndex = this.nextIndex;
                  this.nextEntry = AbstractEntrySet.this.htable.getEntryNext(this.currentEntry);
                  this.advance();
                  return this.currentEntry;
               }
            }
            public void remove() {
               if(this.previousEntry == this.currentEntry) {
                  throw new IllegalStateException();
               } else {
                  if(this.previousEntry == null) {
                     AbstractEntrySet.this.htable.table[this.curIndex] = this.nextEntry;
                  } else {
                     AbstractEntrySet.this.htable.setEntryNext(this.previousEntry, this.nextEntry);
                  }

                  AbstractHashTable var1 = AbstractEntrySet.this.htable;
                  --var1.num_bindings;
                  this.previousEntry = this.currentEntry;
               }
            }
         };
      }

      public int size() {
         return this.htable.size();
      }
   }
}
