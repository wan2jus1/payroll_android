package gnu.mapping;

import gnu.mapping.Procedure;
import gnu.mapping.WrongArguments;

public abstract class Procedure0or1 extends Procedure {

   public Procedure0or1() {
   }

   public Procedure0or1(String var1) {
      super(var1);
   }

   public abstract Object apply0() throws Throwable;

   public abstract Object apply1(Object var1) throws Throwable;

   public Object apply2(Object var1, Object var2) {
      throw new WrongArguments(this, 2);
   }

   public Object apply3(Object var1, Object var2, Object var3) {
      throw new WrongArguments(this, 3);
   }

   public Object apply4(Object var1, Object var2, Object var3, Object var4) {
      throw new WrongArguments(this, 4);
   }

   public Object applyN(Object[] var1) throws Throwable {
      if(var1.length == 0) {
         return this.apply0();
      } else if(var1.length == 1) {
         return this.apply1(var1[0]);
      } else {
         throw new WrongArguments(this, var1.length);
      }
   }

   public int numArgs() {
      return 4096;
   }
}
