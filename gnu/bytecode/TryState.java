package gnu.bytecode;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.ExitableBlock;
import gnu.bytecode.Label;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;

public class TryState {

   Label end_label;
   Label end_try;
   Variable exception;
   ExitableBlock exitCases;
   Variable finally_ret_addr;
   Label finally_subr;
   TryState previous;
   Variable[] savedStack;
   Type[] savedTypes;
   Variable saved_result;
   Label start_try;
   boolean tryClauseDone;
   ClassType try_type;


   public TryState(CodeAttr var1) {
      this.previous = var1.try_stack;
      var1.try_stack = this;
      this.start_try = var1.getLabel();
   }

   static TryState outerHandler(TryState var0, TryState var1) {
      while(var0 != var1 && (var0.finally_subr == null || var0.tryClauseDone)) {
         var0 = var0.previous;
      }

      return var0;
   }
}
