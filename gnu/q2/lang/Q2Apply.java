package gnu.q2.lang;

import gnu.bytecode.Type;
import gnu.expr.Special;
import gnu.kawa.reflect.Invoke;
import gnu.lists.Consumable;
import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;
import gnu.mapping.Procedure;
import gnu.mapping.Values;
import java.util.Vector;

public class Q2Apply extends MethodProc {

   public static Q2Apply q2Apply = new Q2Apply();


   public void apply(CallContext var1) throws Throwable {
      Special var4 = Special.dfault;
      Object var3 = var1.getNextArg(var4);
      Object var2;
      if(!(var3 instanceof Procedure) && !(var3 instanceof Type)) {
         var2 = var3;
         if(!(var3 instanceof Class)) {
            for(; var2 != var4; var2 = var1.getNextArg(var4)) {
               if(var2 instanceof Consumable) {
                  ((Consumable)var2).consume(var1.consumer);
               } else {
                  var1.writeValue(var2);
               }
            }

            return;
         }
      }

      Vector var5 = new Vector();
      if(var3 instanceof Procedure) {
         var2 = (Procedure)var3;
      } else {
         var5.add(var3);
         var2 = Invoke.make;
      }

      while(true) {
         var3 = var1.getNextArg(var4);
         if(var3 == var4) {
            var2 = ((Procedure)var2).applyN(var5.toArray());
            if(var2 instanceof Consumable) {
               ((Consumable)var2).consume(var1.consumer);
               return;
            }

            var1.writeValue(var2);
            return;
         }

         if(var3 instanceof Values) {
            Object[] var7 = ((Values)var3).getValues();

            for(int var6 = 0; var6 < var7.length; ++var6) {
               var5.add(var7[var6]);
            }
         } else {
            var5.add(var3);
         }
      }
   }
}
