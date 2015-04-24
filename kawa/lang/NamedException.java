package kawa.lang;

import gnu.mapping.Procedure;
import gnu.mapping.Symbol;

public class NamedException extends RuntimeException {

   Object[] args;
   Symbol name;


   public NamedException(Symbol var1, Object[] var2) {
      this.name = var1;
      this.args = var2;
   }

   public Object applyHandler(Object var1, Procedure var2) throws Throwable {
      this.checkMatch(var1);
      return var2.applyN(this.args);
   }

   public void checkMatch(Object var1) {
      if(var1 != this.name && var1 != Boolean.TRUE) {
         throw this;
      }
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append("#<ERROR");
      byte var3 = 0;
      int var4 = this.args.length;
      int var2 = var3;
      if(var4 > 1) {
         var2 = var3;
         if(this.args[0] == "misc-error") {
            var2 = 0 + 1;
         }
      }

      while(var2 < var4) {
         var1.append(' ');
         var1.append(this.args[var2]);
         ++var2;
      }

      var1.append(">");
      return var1.toString();
   }
}
