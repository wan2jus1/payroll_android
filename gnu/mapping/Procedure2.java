package gnu.mapping;

import gnu.mapping.Procedure;
import gnu.mapping.WrongArguments;

public abstract class Procedure2 extends Procedure {

   public Procedure2() {
   }

   public Procedure2(String var1) {
      super(var1);
   }

   public Object apply0() throws Throwable {
      throw new WrongArguments(this.getName(), 2, "(?)");
   }

   public Object apply1(Object var1) throws Throwable {
      throw new WrongArguments(this, 1);
   }

   public abstract Object apply2(Object var1, Object var2) throws Throwable;

   public Object apply3(Object var1, Object var2, Object var3) {
      throw new WrongArguments(this, 3);
   }

   public Object apply4(Object var1, Object var2, Object var3, Object var4) throws Throwable {
      throw new WrongArguments(this, 4);
   }

   public Object applyN(Object[] var1) throws Throwable {
      if(var1.length != 2) {
         throw new WrongArguments(this, var1.length);
      } else {
         return this.apply2(var1[0], var1[1]);
      }
   }

   public int numArgs() {
      return 8194;
   }
}
