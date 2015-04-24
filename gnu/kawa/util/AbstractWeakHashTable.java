package gnu.kawa.util;

import gnu.kawa.util.AbstractHashTable;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Map.Entry;

public abstract class AbstractWeakHashTable extends AbstractHashTable {

   ReferenceQueue rqueue = new ReferenceQueue();


   public AbstractWeakHashTable() {
      super(64);
   }

   public AbstractWeakHashTable(int var1) {
      super(var1);
   }

   static void cleanup(AbstractHashTable var0, ReferenceQueue var1) {
      while(true) {
         Entry var5 = (Entry)var1.poll();
         if(var5 == null) {
            return;
         }

         int var6 = var0.hashToIndex(var0.getEntryHashCode(var5));
         Entry var3 = null;

         Entry var4;
         for(Entry var2 = var0.table[var6]; var2 != null; var2 = var4) {
            var4 = var0.getEntryNext(var2);
            if(var2 == var5) {
               if(var3 == null) {
                  var0.table[var6] = var4;
               } else {
                  var0.setEntryNext(var3, var4);
               }
               break;
            }

            var3 = var2;
         }

         --var0.num_bindings;
      }
   }

   protected AbstractWeakHashTable.WEntry[] allocEntries(int var1) {
      return (AbstractWeakHashTable.WEntry[])(new AbstractWeakHashTable.WEntry[var1]);
   }

   protected void cleanup() {
      cleanup(this, this.rqueue);
   }

   public Object get(Object var1, Object var2) {
      this.cleanup();
      return super.get(var1, var2);
   }

   protected int getEntryHashCode(AbstractWeakHashTable.WEntry var1) {
      return var1.hash;
   }

   protected AbstractWeakHashTable.WEntry getEntryNext(AbstractWeakHashTable.WEntry var1) {
      return var1.next;
   }

   protected abstract Object getKeyFromValue(Object var1);

   protected Object getValueIfMatching(AbstractWeakHashTable.WEntry var1, Object var2) {
      Object var3 = var1.getValue();
      return var3 != null && this.matches(this.getKeyFromValue(var3), var2)?var3:null;
   }

   public int hash(Object var1) {
      return System.identityHashCode(var1);
   }

   protected AbstractWeakHashTable.WEntry makeEntry(Object var1, int var2, Object var3) {
      return new AbstractWeakHashTable.WEntry(var3, this, var2);
   }

   public Object put(Object var1, Object var2) {
      this.cleanup();
      int var9 = this.hash(var1);
      int var8 = this.hashToIndex(var9);
      AbstractWeakHashTable.WEntry var3 = ((AbstractWeakHashTable.WEntry[])this.table)[var8];
      AbstractWeakHashTable.WEntry var11 = var3;
      AbstractWeakHashTable.WEntry var5 = null;

      Object var4;
      AbstractWeakHashTable.WEntry var6;
      for(var4 = null; var11 != null; var11 = var6) {
         Object var7 = var11.getValue();
         if(var7 == var2) {
            return var7;
         }

         var6 = var11.next;
         if(var7 != null && this.valuesEqual(var7, var2)) {
            if(var5 == null) {
               ((AbstractWeakHashTable.WEntry[])this.table)[var8] = var6;
            } else {
               var5.next = var6;
            }

            var4 = var7;
         } else {
            var5 = var11;
         }
      }

      int var10 = this.num_bindings + 1;
      this.num_bindings = var10;
      if(var10 >= ((AbstractWeakHashTable.WEntry[])this.table).length) {
         this.rehash();
         var8 = this.hashToIndex(var9);
         var3 = ((AbstractWeakHashTable.WEntry[])this.table)[var8];
      }

      var11 = this.makeEntry((Object)null, var9, var2);
      var11.next = var3;
      ((AbstractWeakHashTable.WEntry[])this.table)[var8] = var11;
      return var4;
   }

   protected void setEntryNext(AbstractWeakHashTable.WEntry var1, AbstractWeakHashTable.WEntry var2) {
      var1.next = var2;
   }

   protected boolean valuesEqual(Object var1, Object var2) {
      return var1 == var2;
   }

   public static class WEntry extends WeakReference implements Entry {

      public int hash;
      AbstractWeakHashTable htable;
      public AbstractWeakHashTable.WEntry next;


      public WEntry(Object var1, AbstractWeakHashTable var2, int var3) {
         super(var1, var2.rqueue);
         this.htable = var2;
         this.hash = var3;
      }

      public Object getKey() {
         Object var1 = this.get();
         return var1 == null?null:this.htable.getKeyFromValue(var1);
      }

      public Object getValue() {
         return this.get();
      }

      public Object setValue(Object var1) {
         throw new UnsupportedOperationException();
      }
   }
}
