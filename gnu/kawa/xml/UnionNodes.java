package gnu.kawa.xml;

import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.ConsumerTarget;
import gnu.expr.Expression;
import gnu.expr.Inlineable;
import gnu.expr.Target;
import gnu.kawa.functions.AppendValues;
import gnu.kawa.xml.SortNodes;
import gnu.kawa.xml.SortedNodes;
import gnu.mapping.Procedure2;
import gnu.mapping.Values;

public class UnionNodes extends Procedure2 implements Inlineable {

   public static final UnionNodes unionNodes = new UnionNodes();


   public Object apply2(Object var1, Object var2) {
      SortedNodes var3 = new SortedNodes();
      Values.writeValues(var1, var3);
      Values.writeValues(var2, var3);
      return var3;
   }

   public void compile(ApplyExp var1, Compilation var2, Target var3) {
      ConsumerTarget.compileUsingConsumer(new ApplyExp(AppendValues.appendValues, var1.getArgs()), var2, var3, SortNodes.makeSortedNodesMethod, (Method)null);
   }

   public Type getReturnType(Expression[] var1) {
      return Compilation.typeObject;
   }
}
