package gnu.expr;

import gnu.bytecode.CodeAttr;
import gnu.bytecode.ExitableBlock;
import gnu.bytecode.Label;
import gnu.bytecode.Type;
import gnu.expr.BlockExitException;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.ExpVisitor;
import gnu.expr.Expression;
import gnu.expr.StackTarget;
import gnu.expr.Target;
import gnu.mapping.CallContext;
import gnu.mapping.OutPort;

public class BlockExp extends Expression {

   Expression body;
   Expression exitBody;
   Target exitTarget;
   ExitableBlock exitableBlock;
   Declaration label;


   public void apply(CallContext var1) throws Throwable {
      Object var2;
      try {
         var2 = this.body.eval((CallContext)var1);
      } catch (BlockExitException var3) {
         if(var3.exit.block != this) {
            throw var3;
         }

         var2 = var3.exit.result;
         if(this.exitBody != null) {
            var2 = this.exitBody.eval((CallContext)var1);
         }
      }

      var1.writeValue(var2);
   }

   public void compile(Compilation var1, Target var2) {
      CodeAttr var4 = var1.getCode();
      Type var3;
      if(this.exitBody == null && var2 instanceof StackTarget) {
         var3 = var2.getType();
      } else {
         var3 = null;
      }

      this.exitableBlock = var4.startExitableBlock(var3, true);
      Target var6;
      if(this.exitBody == null) {
         var6 = var2;
      } else {
         var6 = Target.Ignore;
      }

      this.exitTarget = var6;
      this.body.compileWithPosition(var1, var2);
      if(this.exitBody != null) {
         Label var5 = new Label(var4);
         var4.emitGoto(var5);
         var4.endExitableBlock();
         this.exitBody.compileWithPosition(var1, var2);
         var5.define(var4);
      } else {
         var4.endExitableBlock();
      }

      this.exitableBlock = null;
   }

   protected boolean mustCompile() {
      return false;
   }

   public void print(OutPort var1) {
      var1.startLogicalBlock("(Block", ")", 2);
      if(this.label != null) {
         var1.print(' ');
         var1.print((String)this.label.getName());
      }

      var1.writeSpaceLinear();
      this.body.print((OutPort)var1);
      if(this.exitBody != null) {
         var1.writeSpaceLinear();
         var1.print((String)"else ");
         this.exitBody.print((OutPort)var1);
      }

      var1.endLogicalBlock(")");
   }

   public void setBody(Expression var1) {
      this.body = var1;
   }

   public void setBody(Expression var1, Expression var2) {
      this.body = var1;
      this.exitBody = var2;
   }

   public void setLabel(Declaration var1) {
      this.label = var1;
   }

   protected Object visit(ExpVisitor var1, Object var2) {
      return var1.visitBlockExp(this, var2);
   }

   protected void visitChildren(ExpVisitor var1, Object var2) {
      this.body = var1.visitAndUpdate(this.body, var2);
      if(var1.exitValue == null && this.exitBody != null) {
         this.exitBody = var1.visitAndUpdate(this.exitBody, var2);
      }

   }
}
