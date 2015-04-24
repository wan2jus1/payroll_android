package gnu.mapping;

import gnu.mapping.Procedure;
import gnu.mapping.WrongArguments;

public abstract class Procedure1or2 extends Procedure {

   public Procedure1or2() {
   }

   public Procedure1or2(String var1) {
      super(var1);
   }

   public Object apply0() {
      throw new WrongArguments(this, 0);
   }

   public abstract Object apply1(Object var1) throws Throwable;

   public abstract Object apply2(Object var1, Object var2) throws Throwable;

   public Object apply3(Object var1, Object var2, Object var3) {
      throw new WrongArguments(this, 3);
   }

   public Object apply4(Object var1, Object var2, Object var3, Object var4) {
      throw new WrongArguments(this, 4);
   }

   public Object applyN(Object[] var1) throws Throwable {
      if(var1.length == 1) {
         return this.apply1(var1[0]);
      } else if(var1.length == 2) {
         return this.apply2(var1[0], var1[1]);
      } else {
         throw new WrongArguments(this, var1.length);
      }
   }

   public int numArgs() {
      return 8193;
   }
}
