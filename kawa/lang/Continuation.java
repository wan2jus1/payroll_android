package kawa.lang;

import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;
import gnu.mapping.Values;
import kawa.lang.CalledContinuation;
import kawa.lang.GenericError;

public class Continuation extends MethodProc {

   static int counter;
   int id;
   public boolean invoked;


   public Continuation(CallContext var1) {
   }

   public static Object handleException(Throwable var0, Continuation var1) throws Throwable {
      if(var0 instanceof CalledContinuation) {
         CalledContinuation var2 = (CalledContinuation)var0;
         if(var2.continuation == var1) {
            var1.invoked = true;
            return Values.make((Object[])var2.values);
         }
      }

      throw var0;
   }

   public static void handleException$X(Throwable var0, Continuation var1, CallContext var2) throws Throwable {
      if(var0 instanceof CalledContinuation) {
         CalledContinuation var3 = (CalledContinuation)var0;
         if(var3.continuation == var1) {
            var1.invoked = true;
            Object[] var6 = var3.values;
            int var5 = var6.length;

            for(int var4 = 0; var4 < var5; ++var4) {
               var2.consumer.writeObject(var6[var4]);
            }

            return;
         }
      }

      throw var0;
   }

   public void apply(CallContext var1) {
      if(this.invoked) {
         throw new GenericError("implementation restriction: continuation can only be used once");
      } else {
         throw new CalledContinuation(var1.values, this, var1);
      }
   }

   public final String toString() {
      StringBuilder var2 = (new StringBuilder()).append("#<continuation ").append(this.id);
      String var1;
      if(this.invoked) {
         var1 = " (invoked)>";
      } else {
         var1 = ">";
      }

      return var2.append(var1).toString();
   }
}
