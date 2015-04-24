package com.google.appinventor.components.runtime.util;

import gnu.mapping.InPort;
import gnu.mapping.OutPort;
import gnu.mapping.Procedure;
import gnu.mapping.RunnableClosure;

public class BiggerFuture extends Thread {

   public BiggerFuture(Procedure var1, InPort var2, OutPort var3, OutPort var4, String var5, long var6) {
      super(new ThreadGroup("biggerthreads"), new RunnableClosure(var1, var2, var3, var4), var5, var6);
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append("#<future ");
      var1.append(this.getName());
      var1.append(">");
      return var1.toString();
   }
}
