package gnu.mapping;

import gnu.mapping.Procedure;
import gnu.mapping.Setter;
import gnu.mapping.Values;
import gnu.mapping.WrongArguments;

public class Setter1 extends Setter {

   public Setter1(Procedure var1) {
      super(var1);
   }

   public Object apply2(Object var1, Object var2) throws Throwable {
      this.getter.set1(var1, var2);
      return Values.empty;
   }

   public Object applyN(Object[] var1) throws Throwable {
      int var2 = var1.length;
      if(var2 != 2) {
         throw new WrongArguments(this, var2);
      } else {
         this.getter.set1(var1[0], var1[1]);
         return Values.empty;
      }
   }

   public int numArgs() {
      return 8194;
   }
}
