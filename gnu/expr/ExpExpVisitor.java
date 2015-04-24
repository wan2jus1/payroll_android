package gnu.expr;

import gnu.expr.ExpVisitor;
import gnu.expr.Expression;

public abstract class ExpExpVisitor extends ExpVisitor {

   protected Expression defaultValue(Expression var1, Object var2) {
      return var1;
   }

   protected Expression update(Expression var1, Expression var2) {
      return var2;
   }
}
