package gnu.expr;

import gnu.bytecode.Variable;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.Target;
import gnu.mapping.Procedure;
import java.lang.reflect.Type;

public interface TypeValue extends Type {

   Expression convertValue(Expression var1);

   void emitIsInstance(Variable var1, Compilation var2, Target var3);

   void emitTestIf(Variable var1, Declaration var2, Compilation var3);

   Procedure getConstructor();

   gnu.bytecode.Type getImplementationType();
}
