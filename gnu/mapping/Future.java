package gnu.mapping;

import gnu.mapping.CallContext;
import gnu.mapping.Environment;
import gnu.mapping.InPort;
import gnu.mapping.OutPort;
import gnu.mapping.Procedure;
import gnu.mapping.RunnableClosure;

public class Future extends Thread {

   public RunnableClosure closure;


   public Future(Procedure var1) {
      this.closure = new RunnableClosure(var1);
   }

   public Future(Procedure var1, CallContext var2) {
      this.closure = new RunnableClosure(var1, var2);
   }

   public Future(Procedure var1, InPort var2, OutPort var3, OutPort var4) {
      this.closure = new RunnableClosure(var1, var2, var3, var4);
   }

   public static Future make(Procedure var0, Environment var1, InPort var2, OutPort var3, OutPort var4) {
      var1 = Environment.setSaveCurrent(var1);

      Future var7;
      try {
         var7 = new Future(var0, var2, var3, var4);
      } finally {
         Environment.restoreCurrent(var1);
      }

      return var7;
   }

   public final CallContext getCallContext() {
      return this.closure.getCallContext();
   }

   public void run() {
      this.closure.run();
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append("#<future ");
      var1.append(this.getName());
      var1.append(">");
      return var1.toString();
   }

   public Object waitForResult() throws Throwable {
      try {
         this.join();
      } catch (InterruptedException var2) {
         throw new RuntimeException("thread join [force] was interrupted");
      }

      Throwable var1 = this.closure.exception;
      if(var1 != null) {
         throw var1;
      } else {
         return this.closure.result;
      }
   }
}
