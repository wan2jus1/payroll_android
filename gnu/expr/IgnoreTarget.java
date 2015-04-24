package gnu.expr;

import gnu.bytecode.Type;
import gnu.expr.Compilation;
import gnu.expr.Target;

public class IgnoreTarget extends Target {

   public void compileFromStack(Compilation var1, Type var2) {
      if(!var2.isVoid()) {
         var1.getCode().emitPop(1);
      }

   }

   public Type getType() {
      return Type.voidType;
   }
}
