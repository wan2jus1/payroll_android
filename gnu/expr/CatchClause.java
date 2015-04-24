package gnu.expr;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Variable;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.ExpVisitor;
import gnu.expr.Expression;
import gnu.expr.LambdaExp;
import gnu.expr.LetExp;
import gnu.expr.QuoteExp;
import gnu.expr.Target;
import gnu.mapping.CallContext;
import gnu.mapping.OutPort;

public class CatchClause extends LetExp {

   CatchClause next;


   public CatchClause() {
      super(new Expression[]{QuoteExp.voidExp});
   }

   public CatchClause(LambdaExp var1) {
      this();
      Declaration var2 = var1.firstDecl();
      var1.remove((Declaration)null, var2);
      this.add(var2);
      this.body = var1.body;
   }

   public CatchClause(Object var1, ClassType var2) {
      this();
      this.addDeclaration(var1, var2);
   }

   public void compile(Compilation var1, Target var2) {
      CodeAttr var3 = var1.getCode();
      Variable var4 = this.firstDecl().allocateVariable(var3);
      var3.enterScope(this.getVarScope());
      var3.emitCatchStart(var4);
      this.body.compileWithPosition(var1, var2);
      var3.emitCatchEnd();
      var3.popScope();
   }

   protected Object evalVariable(int var1, CallContext var2) throws Throwable {
      return var2.value1;
   }

   public final Expression getBody() {
      return this.body;
   }

   public final CatchClause getNext() {
      return this.next;
   }

   protected boolean mustCompile() {
      return false;
   }

   public void print(OutPort var1) {
      var1.writeSpaceLinear();
      var1.startLogicalBlock("(Catch", ")", 2);
      var1.writeSpaceFill();
      this.decls.printInfo((OutPort)var1);
      var1.writeSpaceLinear();
      this.body.print((OutPort)var1);
      var1.endLogicalBlock(")");
   }

   public final void setBody(Expression var1) {
      this.body = var1;
   }

   public final void setNext(CatchClause var1) {
      this.next = var1;
   }

   protected void visitChildren(ExpVisitor var1, Object var2) {
      this.body = var1.visitAndUpdate(this.body, var2);
   }
}
