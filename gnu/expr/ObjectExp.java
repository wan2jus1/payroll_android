package gnu.expr;

import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.expr.ClassExp;
import gnu.expr.Compilation;
import gnu.expr.ExpVisitor;
import gnu.expr.LambdaExp;
import gnu.expr.Target;

public class ObjectExp extends ClassExp {

   public ObjectExp() {
      super(true);
   }

   public void compile(Compilation var1, Target var2) {
      this.compileMembers(var1);
      CodeAttr var4 = var1.getCode();
      var4.emitNew(this.type);
      var4.emitDup(1);
      Method var5 = Compilation.getConstructor(this.type, this);
      if(this.closureEnvField != null) {
         LambdaExp var3 = this.outerLambda();
         Variable var6;
         if(Compilation.defaultCallConvention < 2) {
            var6 = this.getOwningLambda().heapFrame;
         } else if(var3.heapFrame != null) {
            var6 = var3.heapFrame;
         } else {
            var6 = var3.closureEnv;
         }

         if(var6 == null) {
            var4.emitPushThis();
         } else {
            var4.emitLoad(var6);
         }
      }

      var4.emitInvokeSpecial(var5);
      var2.compileFromStack(var1, this.getCompiledClassType(var1));
   }

   public Type getType() {
      return this.type;
   }

   protected Object visit(ExpVisitor var1, Object var2) {
      return var1.visitObjectExp(this, var2);
   }
}
