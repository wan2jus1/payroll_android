package gnu.mapping;

import gnu.mapping.Procedure;
import gnu.mapping.ProcedureN;
import gnu.mapping.Values;

public class Setter extends ProcedureN {

   protected Procedure getter;


   public Setter(Procedure var1) {
      this.getter = var1;
      String var2 = var1.getName();
      if(var2 != null) {
         this.setName("(setter " + var2 + ")");
      }

   }

   public Object applyN(Object[] var1) throws Throwable {
      this.getter.setN(var1);
      return Values.empty;
   }

   public int numArgs() {
      int var1 = this.getter.numArgs();
      return var1 < 0?var1 + 1:var1 + 4097;
   }
}
