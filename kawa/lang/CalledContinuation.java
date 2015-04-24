package kawa.lang;

import gnu.mapping.CallContext;
import kawa.lang.Continuation;

public class CalledContinuation extends RuntimeException {

   public Continuation continuation;
   public CallContext ctx;
   public Object[] values;


   CalledContinuation(Object[] var1, Continuation var2, CallContext var3) {
      super("call/cc called");
      this.values = var1;
      this.continuation = var2;
      this.ctx = var3;
   }
}
