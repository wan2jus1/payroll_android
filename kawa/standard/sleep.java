package kawa.standard;

import gnu.math.Quantity;
import gnu.math.Unit;
import kawa.lang.GenericError;

public class sleep {

   public static void sleep(Quantity var0) {
      Unit var3 = var0.unit();
      if(var3 != Unit.Empty && var3.dimensions() != Unit.second.dimensions()) {
         throw new GenericError("bad unit for sleep");
      } else {
         double var1 = var0.doubleValue();
         long var5 = (long)(1000.0D * var1);
         int var4 = (int)(1.0E9D * var1 - (double)var5 * 1000000.0D);

         try {
            Thread.sleep(var5, var4);
         } catch (InterruptedException var7) {
            throw new GenericError("sleep was interrupted");
         }
      }
   }
}
