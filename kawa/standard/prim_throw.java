package kawa.standard;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.Inlineable;
import gnu.expr.Target;
import gnu.mapping.Procedure1;
import gnu.mapping.Values;

public class prim_throw extends Procedure1 implements Inlineable {

   private static ClassType javaThrowableType;
   public static final prim_throw primitiveThrow = new prim_throw();


   public static void throw_it(Object var0) throws Throwable {
      throw (Throwable)var0;
   }

   public Object apply1(Object var1) throws Throwable {
      throw_it(var1);
      return Values.empty;
   }

   public void compile(ApplyExp var1, Compilation var2, Target var3) {
      CodeAttr var4 = var2.getCode();
      var1.getArgs()[0].compile(var2, (Target)Target.pushObject);
      if(javaThrowableType == null) {
         javaThrowableType = new ClassType("java.lang.Throwable");
      }

      var4.emitCheckcast(javaThrowableType);
      var4.emitThrow();
   }

   public Type getReturnType(Expression[] var1) {
      return Type.neverReturnsType;
   }
}
