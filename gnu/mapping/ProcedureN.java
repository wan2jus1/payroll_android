package gnu.mapping;

import gnu.mapping.Procedure;

public abstract class ProcedureN extends Procedure {

   public static final Object[] noArgs = new Object[0];


   public ProcedureN() {
   }

   public ProcedureN(String var1) {
      super(var1);
   }

   public Object apply0() throws Throwable {
      return this.applyN(noArgs);
   }

   public Object apply1(Object var1) throws Throwable {
      return this.applyN(new Object[]{var1});
   }

   public Object apply2(Object var1, Object var2) throws Throwable {
      return this.applyN(new Object[]{var1, var2});
   }

   public Object apply3(Object var1, Object var2, Object var3) throws Throwable {
      return this.applyN(new Object[]{var1, var2, var3});
   }

   public Object apply4(Object var1, Object var2, Object var3, Object var4) throws Throwable {
      return this.applyN(new Object[]{var1, var2, var3, var4});
   }

   public abstract Object applyN(Object[] var1) throws Throwable;
}
