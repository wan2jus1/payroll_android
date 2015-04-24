package gnu.kawa.models;

import gnu.kawa.models.ModelListener;
import gnu.kawa.models.Viewable;
import gnu.kawa.models.WeakListener;

public abstract class Model implements Viewable {

   transient WeakListener listeners;


   public void addListener(ModelListener var1) {
      this.listeners = new WeakListener(var1, this.listeners);
   }

   public void addListener(WeakListener var1) {
      var1.next = this.listeners;
      this.listeners = var1;
   }

   public void notifyListeners(String var1) {
      WeakListener var3 = null;

      WeakListener var4;
      for(WeakListener var2 = this.listeners; var2 != null; var2 = var4) {
         Object var5 = var2.get();
         var4 = var2.next;
         if(var5 == null) {
            var2 = var3;
            if(var3 != null) {
               var3.next = var4;
               var2 = var3;
            }
         } else {
            var2.update(var5, this, var1);
            var2 = var2;
         }

         var3 = var2;
      }

   }
}
