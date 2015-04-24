package gnu.expr;

import gnu.bytecode.CodeAttr;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.expr.Compilation;
import gnu.expr.ConsumerTarget;
import gnu.expr.ExpVisitor;
import gnu.expr.Expression;
import gnu.expr.IgnoreTarget;
import gnu.expr.Target;
import gnu.mapping.CallContext;
import gnu.mapping.OutPort;

public class SynchronizedExp extends Expression {

   Expression body;
   Expression object;


   public SynchronizedExp(Expression var1, Expression var2) {
      this.object = var1;
      this.body = var2;
   }

   public void apply(CallContext param1) throws Throwable {
      // $FF: Couldn't be decompiled
   }

   public void compile(Compilation var1, Target var2) {
      CodeAttr var4 = var1.getCode();
      this.object.compile(var1, (Target)Target.pushObject);
      var4.emitDup(1);
      Variable var5 = var4.pushScope().addVariable(var4, Type.pointer_type, (String)null);
      var4.emitStore(var5);
      var4.emitMonitorEnter();
      Type var3;
      if(!(var2 instanceof IgnoreTarget) && !(var2 instanceof ConsumerTarget)) {
         var3 = var2.getType();
      } else {
         var3 = null;
      }

      var4.emitTryStart(false, var3);
      this.body.compileWithPosition(var1, var2);
      var4.emitLoad(var5);
      var4.emitMonitorExit();
      var4.emitTryEnd();
      var4.emitCatchStart((Variable)null);
      var4.emitLoad(var5);
      var4.emitMonitorExit();
      var4.emitThrow();
      var4.emitCatchEnd();
      var4.emitTryCatchEnd();
      var4.popScope();
   }

   protected boolean mustCompile() {
      return false;
   }

   public void print(OutPort var1) {
      var1.print((String)"(Synchronized ");
      this.object.print((OutPort)var1);
      var1.print((String)" ");
      this.body.print((OutPort)var1);
      var1.print((String)")");
   }

   protected Object visit(ExpVisitor var1, Object var2) {
      return var1.visitSynchronizedExp(this, var2);
   }

   protected void visitChildren(ExpVisitor var1, Object var2) {
      this.object = var1.visitAndUpdate(this.object, var2);
      if(var1.exitValue == null) {
         this.body = var1.visitAndUpdate(this.body, var2);
      }

   }
}
