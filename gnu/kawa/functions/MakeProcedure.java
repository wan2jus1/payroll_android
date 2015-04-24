package gnu.kawa.functions;

import gnu.expr.GenericProc;
import gnu.mapping.Procedure;
import gnu.mapping.ProcedureN;

public class MakeProcedure extends ProcedureN {

   public static final MakeProcedure makeProcedure = new MakeProcedure("make-procedure");


   public MakeProcedure(String var1) {
      super(var1);
      this.setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileMisc:validateApplyMakeProcedure");
   }

   public static GenericProc makeProcedure$V(Object[] var0) {
      return GenericProc.make(var0);
   }

   public Object applyN(Object[] var1) {
      return GenericProc.make(var1);
   }
}
