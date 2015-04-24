package gnu.mapping;

import gnu.mapping.Procedure;
import gnu.mapping.Setter;
import gnu.mapping.Values;
import gnu.mapping.WrongArguments;

public class Setter0 extends Setter {

   public Setter0(Procedure var1) {
      super(var1);
   }

   public Object apply1(Object var1) throws Throwable {
      this.getter.set0(var1);
      return Values.empty;
   }

   public Object applyN(Object[] var1) throws Throwable {
      int var2 = var1.length;
      if(var2 != 1) {
         throw new WrongArguments(this, var2);
      } else {
         this.getter.set0(var1[0]);
         return Values.empty;
      }
   }

   public int numArgs() {
      return 4097;
   }
}
