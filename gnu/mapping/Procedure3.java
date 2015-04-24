package gnu.mapping;

import gnu.mapping.Procedure;
import gnu.mapping.WrongArguments;

public abstract class Procedure3 extends Procedure {

   public Procedure3() {
   }

   public Procedure3(String var1) {
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

   public abstract Object apply3(Object var1, Object var2, Object var3) throws Throwable;

   public Object apply4(Object var1, Object var2, Object var3, Object var4) {
      throw new WrongArguments(this, 4);
   }

   public Object applyN(Object[] var1) throws Throwable {
      if(var1.length != 3) {
         throw new WrongArguments(this, var1.length);
      } else {
         return this.apply3(var1[0], var1[1], var1[2]);
      }
   }

   public int numArgs() {
      return 12291;
   }
}
