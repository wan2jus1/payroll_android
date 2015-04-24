package gnu.expr;

import gnu.bytecode.CodeAttr;
import gnu.bytecode.Type;
import gnu.expr.CatchClause;
import gnu.expr.Compilation;
import gnu.expr.ConditionalTarget;
import gnu.expr.ConsumerTarget;
import gnu.expr.ExpVisitor;
import gnu.expr.Expression;
import gnu.expr.IgnoreTarget;
import gnu.expr.StackTarget;
import gnu.expr.Target;
import gnu.mapping.CallContext;
import gnu.mapping.OutPort;

public class TryExp extends Expression {

   CatchClause catch_clauses;
   Expression finally_clause;
   Expression try_clause;


   public TryExp(Expression var1, Expression var2) {
      this.try_clause = var1;
      this.finally_clause = var2;
   }

   public void apply(CallContext param1) throws Throwable {
      // $FF: Couldn't be decompiled
   }

   public void compile(Compilation var1, Target var2) {
      CodeAttr var5 = var1.getCode();
      boolean var6;
      if(this.finally_clause != null) {
         var6 = true;
      } else {
         var6 = false;
      }

      Target var3;
      if(!(var2 instanceof StackTarget) && !(var2 instanceof ConsumerTarget) && !(var2 instanceof IgnoreTarget) && (!(var2 instanceof ConditionalTarget) || this.finally_clause != null)) {
         var3 = Target.pushValue(var2.getType());
      } else {
         var3 = var2;
      }

      Type var4;
      if(var3 instanceof StackTarget) {
         var4 = var3.getType();
      } else {
         var4 = null;
      }

      var5.emitTryStart(var6, var4);
      this.try_clause.compileWithPosition(var1, var3);

      for(CatchClause var7 = this.catch_clauses; var7 != null; var7 = var7.getNext()) {
         var7.compile(var1, var3);
      }

      if(this.finally_clause != null) {
         var5.emitFinallyStart();
         this.finally_clause.compileWithPosition(var1, Target.Ignore);
         var5.emitFinallyEnd();
      }

      var5.emitTryCatchEnd();
      if(var3 != var2) {
         var2.compileFromStack(var1, var2.getType());
      }

   }

   public final CatchClause getCatchClauses() {
      return this.catch_clauses;
   }

   public final Expression getFinallyClause() {
      return this.finally_clause;
   }

   public Type getType() {
      return this.catch_clauses == null?this.try_clause.getType():super.getType();
   }

   protected boolean mustCompile() {
      return false;
   }

   public void print(OutPort var1) {
      var1.startLogicalBlock("(Try", ")", 2);
      var1.writeSpaceFill();
      this.try_clause.print((OutPort)var1);

      for(CatchClause var2 = this.catch_clauses; var2 != null; var2 = var2.getNext()) {
         var2.print(var1);
      }

      if(this.finally_clause != null) {
         var1.writeSpaceLinear();
         var1.print((String)" finally: ");
         this.finally_clause.print((OutPort)var1);
      }

      var1.endLogicalBlock(")");
   }

   public final void setCatchClauses(CatchClause var1) {
      this.catch_clauses = var1;
   }

   protected Object visit(ExpVisitor var1, Object var2) {
      return var1.visitTryExp(this, var2);
   }

   protected void visitChildren(ExpVisitor var1, Object var2) {
      this.try_clause = var1.visitAndUpdate(this.try_clause, var2);

      for(CatchClause var3 = this.catch_clauses; var1.exitValue == null && var3 != null; var3 = var3.getNext()) {
         var1.visit(var3, var2);
      }

      if(var1.exitValue == null && this.finally_clause != null) {
         this.finally_clause = var1.visitAndUpdate(this.finally_clause, var2);
      }

   }
}
