package gnu.expr;

import gnu.expr.Compilation;
import gnu.expr.ExpVisitor;
import gnu.expr.Expression;
import gnu.expr.Target;
import gnu.mapping.OutPort;

public class LangExp extends Expression {

   Object hook;


   public LangExp() {
   }

   public LangExp(Object var1) {
      this.hook = var1;
   }

   public void compile(Compilation var1, Target var2) {
      throw new RuntimeException("compile called on LangExp");
   }

   public Object getLangValue() {
      return this.hook;
   }

   protected boolean mustCompile() {
      return false;
   }

   public void print(OutPort var1) {
      var1.print((String)"(LangExp ???)");
   }

   public void setLangValue(Object var1) {
      this.hook = var1;
   }

   protected Object visit(ExpVisitor var1, Object var2) {
      return var1.visitLangExp(this, var2);
   }
}
