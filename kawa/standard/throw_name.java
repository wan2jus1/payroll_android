package kawa.standard;

import gnu.mapping.ProcedureN;
import gnu.mapping.Symbol;
import kawa.lang.GenericError;
import kawa.lang.NamedException;
import kawa.standard.prim_throw;

public class throw_name extends ProcedureN {

   public static final throw_name throwName = new throw_name();


   public Object applyN(Object[] var1) throws Throwable {
      if(var1.length > 0) {
         Object var2 = var1[0];
         if(var2 instanceof Throwable) {
            if(var1.length == 1) {
               prim_throw.throw_it(var2);
            }
         } else if(var2 instanceof Symbol) {
            throw new NamedException((Symbol)var2, var1);
         }
      }

      throw new GenericError("bad arguments to throw");
   }
}
