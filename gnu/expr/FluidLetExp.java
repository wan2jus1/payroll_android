package gnu.expr;

import gnu.bytecode.CodeAttr;
import gnu.bytecode.Scope;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.expr.AccessExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.ExpVisitor;
import gnu.expr.Expression;
import gnu.expr.IgnoreTarget;
import gnu.expr.LetExp;
import gnu.expr.StackTarget;
import gnu.expr.Target;
import gnu.mapping.OutPort;

public class FluidLetExp extends LetExp {

   public FluidLetExp(Expression[] var1) {
      super(var1);
   }

   private void doInits(Declaration var1, int var2, Variable[] var3, Compilation var4, Variable var5) {
      if(var2 < this.inits.length) {
         CodeAttr var6 = var4.getCode();
         var3[var2] = var6.addLocal(Type.pointer_type);
         var1.allocateVariable(var6);
         var1.base.load((AccessExp)null, 2, var4, Target.pushObject);
         var6.emitDup();
         var6.emitStore(var1.getVariable());
         this.inits[var2].compile(var4, (Target)Target.pushObject);
         this.doInits(var1.nextDecl(), var2 + 1, var3, var4, var5);
         var6.emitInvokeVirtual(Compilation.typeLocation.getDeclaredMethod("setWithSave", 1));
         var6.emitStore(var3[var2]);
      }
   }

   public void compile(Compilation var1, Target var2) {
      CodeAttr var6 = var1.getCode();
      Type var4;
      if(var2 instanceof IgnoreTarget) {
         var4 = null;
      } else {
         var4 = this.getType();
      }

      Object var3;
      if(var4 == null) {
         var3 = Target.Ignore;
      } else if(var4 == Type.pointer_type) {
         var3 = Target.pushObject;
      } else {
         var3 = new StackTarget(var4);
      }

      Scope var5 = this.getVarScope();
      var6.enterScope(var5);
      Variable var8 = var5.addVariable(var6, Compilation.typeCallContext, (String)null);
      var1.loadCallContext();
      var6.emitStore(var8);
      Variable[] var7 = new Variable[this.inits.length];
      Declaration var11 = this.firstDecl();
      this.doInits(var11, 0, var7, var1, var8);
      var6.emitTryStart(true, var4);
      this.body.compileWithPosition(var1, (Target)var3);
      var6.emitFinallyStart();
      int var9 = 0;

      for(Declaration var10 = var11; var9 < this.inits.length; var10 = var10.nextDecl()) {
         var10.load((AccessExp)null, 2, var1, Target.pushObject);
         var6.emitLoad(var7[var9]);
         var6.emitInvokeVirtual(Compilation.typeLocation.getDeclaredMethod("setRestore", 1));
         ++var9;
      }

      var6.emitTryCatchEnd();
      this.popScope(var6);
      if(var4 != null) {
         var2.compileFromStack(var1, var4);
      }

   }

   protected boolean mustCompile() {
      return true;
   }

   public void print(OutPort var1) {
      this.print(var1, "(FluidLet", ")");
   }

   protected Object visit(ExpVisitor var1, Object var2) {
      return var1.visitFluidLetExp(this, var2);
   }
}
