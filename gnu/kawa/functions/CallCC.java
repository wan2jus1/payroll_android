package gnu.kawa.functions;

import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.Inlineable;
import gnu.expr.Target;
import gnu.kawa.functions.CompileMisc;
import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;
import gnu.mapping.Procedure;
import kawa.lang.Continuation;

public class CallCC extends MethodProc implements Inlineable {

   public static final CallCC callcc = new CallCC();


   CallCC() {
      this.setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileMisc:validateApplyCallCC");
   }

   public void apply(CallContext var1) throws Throwable {
      Procedure var3 = (Procedure)var1.value1;
      Continuation var2 = new Continuation(var1);
      var3.check1(var2, var1);
      var3 = var1.proc;
      var1.proc = null;

      try {
         var3.apply(var1);
         var1.runUntilDone();
         var2.invoked = true;
      } catch (Throwable var4) {
         Continuation.handleException$X(var4, var2, var1);
      }
   }

   public void compile(ApplyExp var1, Compilation var2, Target var3) {
      CompileMisc.compileCallCC(var1, var2, var3, this);
   }

   public Type getReturnType(Expression[] var1) {
      return Type.pointer_type;
   }

   public int match1(Object var1, CallContext var2) {
      return !(var1 instanceof Procedure)?-786432:super.match1(var1, var2);
   }

   public int numArgs() {
      return 4097;
   }
}
