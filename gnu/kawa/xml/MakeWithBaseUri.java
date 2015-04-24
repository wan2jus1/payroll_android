package gnu.kawa.xml;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.Variable;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.ConsumerTarget;
import gnu.expr.Expression;
import gnu.expr.Target;
import gnu.kawa.xml.NodeConstructor;
import gnu.lists.Consumer;
import gnu.lists.TreeList;
import gnu.lists.XConsumer;
import gnu.mapping.CallContext;
import gnu.mapping.Values;
import gnu.xml.XMLFilter;

public class MakeWithBaseUri extends NodeConstructor {

   static final Method beginEntityMethod = typeXConsumer.getDeclaredMethod("beginEntity", 1);
   static final Method endEntityMethod = typeXConsumer.getDeclaredMethod("endEntity", 0);
   public static final MakeWithBaseUri makeWithBaseUri = new MakeWithBaseUri();
   static final ClassType typeXConsumer = ClassType.make("gnu.lists.XConsumer");


   public void apply(CallContext var1) {
      Consumer var2 = var1.consumer;
      XMLFilter var3 = NodeConstructor.pushNodeContext(var1);
      Object var4 = var1.getNextArg();
      Object var5 = var1.getNextArg();
      if(var3 instanceof XConsumer) {
         ((XConsumer)var3).beginEntity(var4);
      }

      try {
         Values.writeValues(var5, var3);
      } finally {
         if(var3 instanceof XConsumer) {
            ((XConsumer)var3).endEntity();
         }

         if(var3 instanceof TreeList) {
            ((TreeList)var3).dump();
         }

         NodeConstructor.popNodeContext(var2, var1);
      }

   }

   public void compileToNode(ApplyExp var1, Compilation var2, ConsumerTarget var3) {
      Variable var4 = var3.getConsumerVariable();
      Expression[] var7 = var1.getArgs();
      int var6 = var7.length;
      CodeAttr var5 = var2.getCode();
      var5.emitLoad(var4);
      var7[0].compile(var2, (Target)Target.pushObject);
      var5.emitInvokeInterface(beginEntityMethod);
      compileChild(var7[1], var2, var3);
      var5.emitLoad(var4);
      var5.emitInvokeInterface(endEntityMethod);
   }

   public int numArgs() {
      return 8194;
   }
}
