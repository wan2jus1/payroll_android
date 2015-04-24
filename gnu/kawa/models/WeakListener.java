package gnu.kawa.models;

import gnu.kawa.models.Model;
import gnu.kawa.models.ModelListener;
import java.lang.ref.WeakReference;

public class WeakListener extends WeakReference {

   WeakListener next;


   public WeakListener(Object var1) {
      super(var1);
   }

   public WeakListener(Object var1, WeakListener var2) {
      super(var1);
      this.next = var2;
   }

   public void update(Object var1, Model var2, Object var3) {
      ((ModelListener)var1).modelUpdated(var2, var3);
   }
}
