package kawa.lang;

import gnu.lists.Consumer;
import gnu.mapping.Future;
import gnu.mapping.Procedure;
import gnu.text.Printable;

public class Promise implements Printable {

   Object result;
   Procedure thunk;


   public Promise(Procedure var1) {
      this.thunk = var1;
   }

   public static Object force(Object var0) throws Throwable {
      Object var1;
      if(var0 instanceof Promise) {
         var1 = ((Promise)var0).force();
      } else {
         if(var0 instanceof Future) {
            return ((Future)var0).waitForResult();
         }

         var1 = var0;
         if(var0 instanceof java.util.concurrent.Future) {
            return ((java.util.concurrent.Future)var0).get();
         }
      }

      return var1;
   }

   public Object force() throws Throwable {
      if(this.result == null) {
         Object var1 = this.thunk.apply0();
         if(this.result == null) {
            this.result = var1;
         }
      }

      return this.result;
   }

   public void print(Consumer var1) {
      if(this.result == null) {
         var1.write("#<promise - not forced yet>");
      } else {
         var1.write("#<promise - forced to a ");
         var1.write(this.result.getClass().getName());
         var1.write(62);
      }
   }
}
