package gnu.kawa.functions;

import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.ConsumerTarget;
import gnu.expr.Expression;
import gnu.expr.IgnoreTarget;
import gnu.expr.Inlineable;
import gnu.expr.Special;
import gnu.expr.Target;
import gnu.lists.Consumable;
import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;
import gnu.mapping.Procedure;

public class AppendValues extends MethodProc implements Inlineable {

   public static final AppendValues appendValues = new AppendValues();


   public AppendValues() {
      this.setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileMisc:validateApplyAppendValues");
   }

   public void apply(CallContext var1) {
      Special var2 = Special.dfault;

      while(true) {
         Object var3 = var1.getNextArg(var2);
         if(var3 == var2) {
            return;
         }

         if(var3 instanceof Consumable) {
            ((Consumable)var3).consume(var1.consumer);
         } else {
            var1.writeValue(var3);
         }
      }
   }

   public void compile(ApplyExp var1, Compilation var2, Target var3) {
      Expression[] var4 = var1.getArgs();
      int var6 = var4.length;
      if(!(var3 instanceof ConsumerTarget) && !(var3 instanceof IgnoreTarget)) {
         ConsumerTarget.compileUsingConsumer(var1, var2, var3);
      } else {
         for(int var5 = 0; var5 < var6; ++var5) {
            var4[var5].compileWithPosition(var2, var3);
         }
      }

   }

   public Type getReturnType(Expression[] var1) {
      return Compilation.typeObject;
   }
}
