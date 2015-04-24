package gnu.expr;

import gnu.mapping.InPort;
import gnu.mapping.Procedure1;

class SimplePrompter extends Procedure1 {

   public String prefix = "[";
   public String suffix = "] ";


   public Object apply1(Object var1) {
      if(var1 instanceof InPort) {
         int var2 = ((InPort)var1).getLineNumber() + 1;
         if(var2 >= 0) {
            return this.prefix + var2 + this.suffix;
         }
      }

      return this.suffix;
   }
}
