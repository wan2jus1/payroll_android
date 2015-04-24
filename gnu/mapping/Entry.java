package gnu.mapping;

import java.lang.ref.WeakReference;

class Entry {

   Entry chain;
   Object key1;
   Object key2;
   Object value;


   public Object getKey1() {
      return this.key1 instanceof WeakReference?((WeakReference)this.key1).get():this.key1;
   }

   public Object getKey2() {
      return this.key2 instanceof WeakReference?((WeakReference)this.key2).get():this.key2;
   }

   public Object getValue() {
      return this.value == this?null:this.value;
   }

   public boolean matches(Object var1, Object var2) {
      return var1 == this.getKey1() && var2 == this.getKey2();
   }
}
