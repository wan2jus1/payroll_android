package gnu.expr;

import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.Target;
import gnu.mapping.OutPort;
import gnu.text.SourceMessages;

public class ErrorExp extends Expression {

   String message;


   public ErrorExp(String var1) {
      this.message = var1;
   }

   public ErrorExp(String var1, Compilation var2) {
      var2.getMessages().error('e', var1);
      this.message = var1;
   }

   public ErrorExp(String var1, SourceMessages var2) {
      var2.error('e', var1);
      this.message = var1;
   }

   public void compile(Compilation var1, Target var2) {
      throw new Error(var1.getFileName() + ":" + var1.getLineNumber() + ": internal error: compiling error expression: " + this.message);
   }

   protected boolean mustCompile() {
      return false;
   }

   public void print(OutPort var1) {
      var1.startLogicalBlock("(Error", false, ")");
      var1.writeSpaceLinear();
      var1.print((String)this.message);
      var1.endLogicalBlock(")");
   }
}
