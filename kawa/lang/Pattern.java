package kawa.lang;

import gnu.bytecode.ClassType;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.expr.Compilation;
import gnu.text.Printable;

public abstract class Pattern implements Printable {

   private static Type[] matchArgs = new Type[]{Type.pointer_type, Compilation.objArrayType, Type.intType};
   public static final Method matchPatternMethod = typePattern.addMethod("match", matchArgs, Type.booleanType, 1);
   public static ClassType typePattern = ClassType.make("kawa.lang.Pattern");


   public abstract boolean match(Object var1, Object[] var2, int var3);

   public Object[] match(Object var1) {
      Object[] var2 = new Object[this.varCount()];
      return this.match(var1, var2, 0)?var2:null;
   }

   public abstract int varCount();
}
