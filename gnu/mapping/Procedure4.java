package gnu.mapping;

import gnu.mapping.Procedure;
import gnu.mapping.WrongArguments;

public abstract class Procedure4 extends Procedure {

   public Procedure4() {
   }

   public Procedure4(String var1) {
      super(var1);
   }

   public Object apply0() {
      throw new WrongArguments(this, 0);
   }

   public Object apply1(Object var1) {
      throw new WrongArguments(this, 1);
   }

   public Object apply2(Object var1, Object var2) {
      throw new WrongArguments(this, 2);
   }

   public Object apply3(Object var1, Object var2, Object var3) {
      throw new WrongArguments(this, 3);
   }

   public abstract Object apply4(Object var1, Object var2, Object var3, Object var4) throws Throwable;

   public Object applyN(Object[] var1) throws Throwable {
      if(var1.length != 4) {
         throw new WrongArguments(this, var1.length);
      } else {
         return this.apply4(var1[0], var1[1], var1[2], var1[3]);
      }
   }

   public int numArgs() {
      return 16388;
   }
}
