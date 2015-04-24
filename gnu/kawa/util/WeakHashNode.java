package gnu.kawa.util;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Map.Entry;

public class WeakHashNode extends WeakReference implements Entry {

   public int hash;
   public WeakHashNode next;
   public Object value;


   public WeakHashNode(Object var1, ReferenceQueue var2, int var3) {
      super(var1, var2);
      this.hash = var3;
   }

   public Object getKey() {
      return this.get();
   }

   public Object getValue() {
      return this.value;
   }

   public Object setValue(Object var1) {
      Object var2 = this.value;
      this.value = var1;
      return var2;
   }
}
