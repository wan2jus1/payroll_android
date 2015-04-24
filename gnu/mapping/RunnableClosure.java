package gnu.mapping;

import gnu.mapping.CallContext;
import gnu.mapping.InPort;
import gnu.mapping.OutPort;
import gnu.mapping.Procedure;
import java.util.concurrent.Callable;

public class RunnableClosure implements Callable, Runnable {

   static int nrunnables = 0;
   Procedure action;
   CallContext context;
   private OutPort err;
   Throwable exception;
   private InPort in;
   String name;
   private OutPort out;
   Object result;


   public RunnableClosure(Procedure var1) {
      this(var1, CallContext.getInstance());
   }

   public RunnableClosure(Procedure var1, CallContext var2) {
      StringBuilder var4 = (new StringBuilder()).append("r");
      int var3 = nrunnables;
      nrunnables = var3 + 1;
      this.setName(var4.append(var3).toString());
      this.action = var1;
   }

   public RunnableClosure(Procedure var1, InPort var2, OutPort var3, OutPort var4) {
      this(var1, CallContext.getInstance());
      this.in = var2;
      this.out = var3;
      this.err = var4;
   }

   public Object call() throws Exception {
      this.run();
      Throwable var1 = this.exception;
      if(var1 != null) {
         if(var1 instanceof Exception) {
            throw (Exception)var1;
         } else if(var1 instanceof Error) {
            throw (Error)var1;
         } else {
            throw new RuntimeException(var1);
         }
      } else {
         return this.result;
      }
   }

   public final CallContext getCallContext() {
      return this.context;
   }

   public String getName() {
      return this.name;
   }

   Object getResult() throws Throwable {
      Throwable var1 = this.exception;
      if(var1 != null) {
         throw var1;
      } else {
         return this.result;
      }
   }

   public void run() {
      // $FF: Couldn't be decompiled
   }

   public void setName(String var1) {
      this.name = var1;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append("#<runnable ");
      var1.append(this.getName());
      var1.append(">");
      return var1.toString();
   }
}
