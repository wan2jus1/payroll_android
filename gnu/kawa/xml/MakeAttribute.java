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
import gnu.expr.QuoteExp;
import gnu.expr.Target;
import gnu.kawa.xml.NodeConstructor;
import gnu.lists.Consumer;
import gnu.mapping.CallContext;

public class MakeAttribute extends NodeConstructor {

   static final Method endAttributeMethod = Compilation.typeConsumer.getDeclaredMethod("endAttribute", 0);
   public static final MakeAttribute makeAttribute = new MakeAttribute();
   public static final QuoteExp makeAttributeExp = new QuoteExp(makeAttribute);
   static final Method startAttributeMethod = typeMakeAttribute.getDeclaredMethod("startAttribute", 2);
   static final ClassType typeMakeAttribute = ClassType.make("gnu.kawa.xml.MakeAttribute");


   public static void startAttribute(Consumer var0, Object var1) {
      var0.startAttribute(var1);
   }

   public void apply(CallContext param1) {
      // $FF: Couldn't be decompiled
   }

   public void compileToNode(ApplyExp var1, Compilation var2, ConsumerTarget var3) {
      Variable var4 = var3.getConsumerVariable();
      Expression[] var8 = var1.getArgs();
      int var7 = var8.length;
      CodeAttr var5 = var2.getCode();
      var5.emitLoad(var4);
      var5.emitDup();
      var8[0].compile(var2, (Target)Target.pushObject);
      var5.emitInvokeStatic(startAttributeMethod);

      for(int var6 = 1; var6 < var7; ++var6) {
         compileChild(var8[var6], var2, var3);
      }

      var5.emitInvokeInterface(endAttributeMethod);
   }

   public Type getReturnType(Expression[] var1) {
      return Compilation.typeObject;
   }

   public int numArgs() {
      return -4095;
   }
}
