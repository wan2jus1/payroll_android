package gnu.kawa.functions;

import gnu.bytecode.ExitableBlock;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.ConsumerTarget;
import gnu.expr.Expression;
import gnu.expr.IgnoreTarget;
import gnu.expr.Inlineable;
import gnu.expr.Target;
import gnu.kawa.functions.AppendValues;
import gnu.mapping.ProcedureN;

class CompileTimeContinuation extends ProcedureN implements Inlineable {

   Target blockTarget;
   ExitableBlock exitableBlock;


   public Object applyN(Object[] var1) throws Throwable {
      throw new Error("internal error");
   }

   public void compile(ApplyExp var1, Compilation var2, Target var3) {
      var2.getCode();
      Expression[] var6 = var1.getArgs();
      int var5 = var6.length;
      boolean var4;
      if(!(this.blockTarget instanceof IgnoreTarget) && !(this.blockTarget instanceof ConsumerTarget)) {
         var4 = false;
      } else {
         var4 = true;
      }

      if(!var4) {
         var3.getType();
      }

      if(!var4 && var5 != 1) {
         AppendValues var7 = AppendValues.appendValues;
         var7.compile(new ApplyExp(var7, var6), var2, this.blockTarget);
      } else {
         for(int var8 = 0; var8 < var5; ++var8) {
            var6[var8].compileWithPosition(var2, this.blockTarget);
         }
      }

      this.exitableBlock.exit();
   }

   public Type getReturnType(Expression[] var1) {
      return Type.neverReturnsType;
   }
}
