package gnu.bytecode;

import gnu.bytecode.CodeAttr;
import gnu.bytecode.Label;
import gnu.bytecode.TryState;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;

public class ExitableBlock {

   CodeAttr code;
   TryState currentTryState;
   Label endLabel;
   TryState initialTryState;
   ExitableBlock nextCase;
   ExitableBlock outer;
   Type resultType;
   Variable resultVariable;
   int switchCase;


   ExitableBlock(Type var1, CodeAttr var2, boolean var3) {
      this.code = var2;
      this.resultType = var1;
      this.initialTryState = var2.try_stack;
      if(var3 && var1 != null) {
         var2.pushScope();
         Variable var5 = var2.addLocal(var1);
         this.resultVariable = var5;
         var2.emitStoreDefaultValue(var5);
         int var4 = var2.exitableBlockLevel + 1;
         var2.exitableBlockLevel = var4;
         this.switchCase = var4;
      }

      this.endLabel = new Label(var2);
   }

   public void exit() {
      if(this.resultVariable != null) {
         this.code.emitStore(this.resultVariable);
      }

      this.exit(TryState.outerHandler(this.code.try_stack, this.initialTryState));
   }

   void exit(TryState var1) {
      if(var1 == this.initialTryState) {
         this.code.emitGoto(this.endLabel);
      } else if(this.code.useJsr()) {
         for(var1 = this.code.try_stack; var1 != this.initialTryState; var1 = var1.previous) {
            if(var1.finally_subr != null && var1.finally_ret_addr == null) {
               this.code.emitJsr(var1.finally_subr);
            }
         }

         this.code.emitGoto(this.endLabel);
      } else {
         if(this.currentTryState == null) {
            this.linkCase(var1);
         }

         if(var1.saved_result != null) {
            this.code.emitStoreDefaultValue(var1.saved_result);
         }

         this.code.emitPushInt(this.switchCase);
         this.code.emitPushNull();
         this.code.emitGoto(var1.finally_subr);
      }
   }

   public Label exitIsGoto() {
      return TryState.outerHandler(this.code.try_stack, this.initialTryState) == this.initialTryState?this.endLabel:null;
   }

   void finish() {
      if(this.resultVariable != null && this.code.reachableHere()) {
         this.code.emitStore(this.resultVariable);
      }

      this.endLabel.define(this.code);
      if(this.resultVariable != null) {
         this.code.emitLoad(this.resultVariable);
         this.code.popScope();
         CodeAttr var1 = this.code;
         --var1.exitableBlockLevel;
      }

   }

   void linkCase(TryState var1) {
      if(this.currentTryState != var1) {
         if(this.currentTryState != null) {
            throw new Error();
         }

         this.nextCase = var1.exitCases;
         var1.exitCases = this;
         this.currentTryState = var1;
      }

   }
}
