package gnu.kawa.util;

import gnu.kawa.util.AbstractHashTable;
import gnu.kawa.util.AbstractWeakHashTable;
import gnu.kawa.util.WeakHashNode;
import java.lang.ref.ReferenceQueue;

public class WeakIdentityHashMap extends AbstractHashTable {

   ReferenceQueue rqueue = new ReferenceQueue();


   public WeakIdentityHashMap() {
      super(64);
   }

   public WeakIdentityHashMap(int var1) {
      super(var1);
   }

   protected WeakHashNode[] allocEntries(int var1) {
      return (WeakHashNode[])(new WeakHashNode[var1]);
   }

   void cleanup() {
      AbstractWeakHashTable.cleanup(this, this.rqueue);
   }

   public Object get(Object var1, Object var2) {
      this.cleanup();
      return super.get(var1, var2);
   }

   protected int getEntryHashCode(WeakHashNode var1) {
      return var1.hash;
   }

   protected WeakHashNode getEntryNext(WeakHashNode var1) {
      return var1.next;
   }

   public int hash(Object var1) {
      return System.identityHashCode(var1);
   }

   protected WeakHashNode makeEntry(Object var1, int var2, Object var3) {
      WeakHashNode var4 = new WeakHashNode(var1, this.rqueue, var2);
      var4.value = var3;
      return var4;
   }

   protected boolean matches(Object var1, Object var2) {
      return var1 == var2;
   }

   public Object put(Object var1, int var2, Object var3) {
      this.cleanup();
      return super.put(var1, var2, var3);
   }

   public Object remove(Object var1) {
      this.cleanup();
      return super.remove(var1);
   }

   protected void setEntryNext(WeakHashNode var1, WeakHashNode var2) {
      var1.next = var2;
   }
}
