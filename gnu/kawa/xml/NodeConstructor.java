package gnu.kawa.xml;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.ConsumerTarget;
import gnu.expr.Expression;
import gnu.expr.IgnoreTarget;
import gnu.expr.Inlineable;
import gnu.expr.QuoteExp;
import gnu.expr.Target;
import gnu.kawa.xml.KNode;
import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;
import gnu.xml.NodeTree;
import gnu.xml.XMLFilter;

public abstract class NodeConstructor extends MethodProc implements Inlineable {

   static final Method popNodeConsumerMethod = typeNodeConstructor.getDeclaredMethod("popNodeConsumer", 2);
   static final Method popNodeContextMethod = typeNodeConstructor.getDeclaredMethod("popNodeContext", 2);
   static final Method pushNodeConsumerMethod = typeNodeConstructor.getDeclaredMethod("pushNodeConsumer", 1);
   static final Method pushNodeContextMethod = typeNodeConstructor.getDeclaredMethod("pushNodeContext", 1);
   static final ClassType typeKNode = ClassType.make("gnu.kawa.xml.KNode");
   static final ClassType typeNodeConstructor = ClassType.make("gnu.kawa.xml.NodeConstructor");
   static final ClassType typeXMLFilter = ClassType.make("gnu.xml.XMLFilter");


   public static void compileChild(Expression var0, Compilation var1, ConsumerTarget var2) {
      if(var0 instanceof ApplyExp) {
         ApplyExp var3 = (ApplyExp)var0;
         Expression var4 = var3.getFunction();
         if(var4 instanceof QuoteExp) {
            Object var5 = ((QuoteExp)var4).getValue();
            if(var5 instanceof NodeConstructor) {
               ((NodeConstructor)var5).compileToNode(var3, var1, var2);
               return;
            }
         }
      }

      var0.compileWithPosition(var1, var2);
   }

   public static void compileUsingNodeTree(Expression var0, Compilation var1, Target var2) {
      ConsumerTarget.compileUsingConsumer(var0, var1, var2, typeNodeConstructor.getDeclaredMethod("makeNode", 0), typeNodeConstructor.getDeclaredMethod("finishNode", 1));
   }

   public static KNode finishNode(XMLFilter var0) {
      return KNode.make((NodeTree)var0.out);
   }

   public static XMLFilter makeNode() {
      return new XMLFilter(new NodeTree());
   }

   public static void popNodeConsumer(Consumer var0, Consumer var1) {
      if(var0 != var1) {
         Object var2 = var1;
         if(var1 instanceof XMLFilter) {
            var2 = KNode.make((NodeTree)((XMLFilter)var1).out);
         }

         var0.writeObject(var2);
      }

   }

   public static void popNodeContext(Consumer var0, CallContext var1) {
      Consumer var3 = var1.consumer;
      if(var0 != var3) {
         Object var2 = var3;
         if(var3 instanceof XMLFilter) {
            var2 = KNode.make((NodeTree)((XMLFilter)var3).out);
         }

         var0.writeObject(var2);
         var1.consumer = var0;
      }

   }

   public static XMLFilter pushNodeConsumer(Consumer var0) {
      return var0 instanceof XMLFilter?(XMLFilter)var0:new XMLFilter(new NodeTree());
   }

   public static XMLFilter pushNodeContext(CallContext var0) {
      Consumer var1 = var0.consumer;
      if(var1 instanceof XMLFilter) {
         return (XMLFilter)var1;
      } else {
         XMLFilter var2 = new XMLFilter(new NodeTree());
         var0.consumer = var2;
         return var2;
      }
   }

   public void compile(ApplyExp var1, Compilation var2, Target var3) {
      if(var3 instanceof IgnoreTarget) {
         ApplyExp.compile(var1, var2, var3);
      } else if(!(var3 instanceof ConsumerTarget)) {
         compileUsingNodeTree(var1, var2, var3);
      } else {
         ConsumerTarget var8 = (ConsumerTarget)var3;
         Variable var4 = var8.getConsumerVariable();
         if(var4.getType().isSubtype(typeXMLFilter)) {
            this.compileToNode(var1, var2, var8);
         } else {
            int var7 = var1.getArgs().length;
            CodeAttr var5 = var2.getCode();
            Variable var6 = var5.pushScope().addVariable(var5, typeXMLFilter, (String)null);
            if(var8.isContextTarget()) {
               var2.loadCallContext();
               var5.emitInvokeStatic(pushNodeContextMethod);
            } else {
               var5.emitLoad(var4);
               var5.emitInvokeStatic(pushNodeConsumerMethod);
            }

            var5.emitStore(var6);
            var5.emitTryStart(true, Type.void_type);
            this.compileToNode(var1, var2, new ConsumerTarget(var6));
            var5.emitTryEnd();
            var5.emitFinallyStart();
            var5.emitLoad(var4);
            if(var8.isContextTarget()) {
               var2.loadCallContext();
               var5.emitInvokeStatic(popNodeContextMethod);
            } else {
               var5.emitLoad(var6);
               var5.emitInvokeStatic(popNodeConsumerMethod);
            }

            var5.emitFinallyEnd();
            var5.emitTryCatchEnd();
            var5.popScope();
         }
      }
   }

   public abstract void compileToNode(ApplyExp var1, Compilation var2, ConsumerTarget var3);

   public Type getReturnType(Expression[] var1) {
      return Compilation.typeObject;
   }
}
