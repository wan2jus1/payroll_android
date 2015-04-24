package gnu.kawa.xml;

import gnu.bytecode.ClassType;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.ConsumerTarget;
import gnu.expr.Expression;
import gnu.expr.Inlineable;
import gnu.expr.StackTarget;
import gnu.expr.Target;
import gnu.kawa.xml.SortedNodes;
import gnu.mapping.Procedure1;
import gnu.mapping.Values;

public class SortNodes extends Procedure1 implements Inlineable {

   public static final Method canonicalizeMethod = Compilation.typeValues.getDeclaredMethod("canonicalize", 0);
   public static final Method makeSortedNodesMethod = typeSortedNodes.getDeclaredMethod("<init>", 0);
   public static final SortNodes sortNodes = new SortNodes();
   public static final ClassType typeSortedNodes = ClassType.make("gnu.kawa.xml.SortedNodes");


   public Object apply1(Object var1) {
      SortedNodes var2 = new SortedNodes();
      Values.writeValues(var1, var2);
      return var2.count > 1?var2:(var2.count == 0?Values.empty:var2.get(0));
   }

   public void compile(ApplyExp var1, Compilation var2, Target var3) {
      Expression[] var4 = var1.getArgs();
      if(var4.length == 1 && var2.mustCompile) {
         Method var5;
         if(!(var3 instanceof ConsumerTarget) && (!(var3 instanceof StackTarget) || !var3.getType().isSubtype(Compilation.typeValues))) {
            var5 = canonicalizeMethod;
         } else {
            var5 = null;
         }

         ConsumerTarget.compileUsingConsumer(var4[0], var2, var3, makeSortedNodesMethod, var5);
      } else {
         ApplyExp.compile(var1, var2, var3);
      }
   }

   public Type getReturnType(Expression[] var1) {
      return Compilation.typeObject;
   }
}
