package kawa.standard;

import gnu.mapping.CallContext;
import gnu.mapping.Procedure;
import gnu.mapping.Procedure2;
import gnu.mapping.Values;

public class call_with_values extends Procedure2 {

   public static final call_with_values callWithValues = new call_with_values();


   static {
      callWithValues.setName("call-with-values");
   }

   public static Object callWithValues(Procedure var0, Procedure var1) throws Throwable {
      Object var2 = var0.apply0();
      return var2 instanceof Values?((Values)var2).call_with(var1):var1.apply1(var2);
   }

   public void apply(CallContext var1) throws Throwable {
      Procedure.checkArgCount(this, 2);
      Object[] var3 = var1.getArgs();
      Object var2 = ((Procedure)var3[0]).apply0();
      Procedure var4 = (Procedure)var3[1];
      if(var2 instanceof Values) {
         var4.checkN(((Values)var2).getValues(), var1);
      } else {
         var4.check1(var2, var1);
      }
   }

   public Object apply2(Object var1, Object var2) throws Throwable {
      return callWithValues((Procedure)var1, (Procedure)var2);
   }
}
