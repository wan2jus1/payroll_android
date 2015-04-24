package gnu.kawa.xml;

import gnu.bytecode.ClassType;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.ConsumerTarget;
import gnu.expr.Expression;
import gnu.expr.Inlineable;
import gnu.expr.Target;
import gnu.kawa.xml.Nodes;
import gnu.mapping.Procedure1;
import gnu.mapping.Values;

public class CoerceNodes extends Procedure1 implements Inlineable {

   public static final CoerceNodes coerceNodes = new CoerceNodes();
   public static final Method makeNodesMethod = typeNodes.getDeclaredMethod("<init>", 0);
   public static final ClassType typeNodes = ClassType.make("gnu.kawa.xml.Nodes");


   public Object apply1(Object var1) {
      Nodes var2 = new Nodes();
      Values.writeValues(var1, var2);
      return var2;
   }

   public void compile(ApplyExp var1, Compilation var2, Target var3) {
      Expression[] var4 = var1.getArgs();
      if(var4.length != 1) {
         ApplyExp.compile(var1, var2, var3);
      } else {
         ConsumerTarget.compileUsingConsumer(var4[0], var2, var3, makeNodesMethod, (Method)null);
      }
   }

   public Type getReturnType(Expression[] var1) {
      return typeNodes;
   }
}
