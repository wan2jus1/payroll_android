package gnu.expr;

import gnu.bytecode.Type;
import gnu.expr.Compilation;
import gnu.expr.IgnoreTarget;
import gnu.expr.StackTarget;

public abstract class Target {

   public static final Target Ignore = new IgnoreTarget();
   public static final Target pushObject = new StackTarget(Type.pointer_type);


   public static Target pushValue(Type var0) {
      return var0.isVoid()?Ignore:StackTarget.getInstance(var0);
   }

   public abstract void compileFromStack(Compilation var1, Type var2);

   public abstract Type getType();
}
