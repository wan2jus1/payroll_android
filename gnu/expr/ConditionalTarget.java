package gnu.expr;

import gnu.bytecode.CodeAttr;
import gnu.bytecode.Label;
import gnu.bytecode.Type;
import gnu.expr.Compilation;
import gnu.expr.Language;
import gnu.expr.Target;

public class ConditionalTarget extends Target {

   public Label ifFalse;
   public Label ifTrue;
   Language language;
   public boolean trueBranchComesFirst = true;


   public ConditionalTarget(Label var1, Label var2, Language var3) {
      this.ifTrue = var1;
      this.ifFalse = var2;
      this.language = var3;
   }

   public void compileFromStack(Compilation var1, Type var2) {
      CodeAttr var3 = var1.getCode();
      switch(var2.getSignature().charAt(0)) {
      case 68:
         var3.emitPushDouble(0.0D);
         break;
      case 70:
         var3.emitPushFloat(0.0F);
         break;
      case 74:
         var3.emitPushLong(0L);
         break;
      case 76:
      case 91:
         Object var4;
         if(this.language == null) {
            var4 = Boolean.FALSE;
         } else {
            var4 = this.language.booleanObject(false);
         }

         var1.compileConstant(var4);
         break;
      default:
         if(this.trueBranchComesFirst) {
            var3.emitGotoIfIntEqZero(this.ifFalse);
            var3.emitGoto(this.ifTrue);
            return;
         }

         var3.emitGotoIfIntNeZero(this.ifTrue);
         var3.emitGoto(this.ifFalse);
         return;
      }

      if(this.trueBranchComesFirst) {
         var3.emitGotoIfEq(this.ifFalse);
      } else {
         var3.emitGotoIfNE(this.ifTrue);
      }

      this.emitGotoFirstBranch(var3);
   }

   public final void emitGotoFirstBranch(CodeAttr var1) {
      Label var2;
      if(this.trueBranchComesFirst) {
         var2 = this.ifTrue;
      } else {
         var2 = this.ifFalse;
      }

      var1.emitGoto(var2);
   }

   public Type getType() {
      return Type.booleanType;
   }
}
