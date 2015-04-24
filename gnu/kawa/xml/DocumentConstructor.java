package gnu.kawa.xml;

import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.Variable;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.ConsumerTarget;
import gnu.expr.Expression;
import gnu.kawa.xml.NodeConstructor;
import gnu.mapping.CallContext;

public class DocumentConstructor extends NodeConstructor {

   public static final DocumentConstructor documentConstructor = new DocumentConstructor();
   static final Method endDocumentMethod = Compilation.typeConsumer.getDeclaredMethod("endDocument", 0);
   static final Method startDocumentMethod = Compilation.typeConsumer.getDeclaredMethod("startDocument", 0);


   public void apply(CallContext param1) {
      // $FF: Couldn't be decompiled
   }

   public void compileToNode(ApplyExp var1, Compilation var2, ConsumerTarget var3) {
      Variable var4 = var3.getConsumerVariable();
      Expression[] var8 = var1.getArgs();
      int var7 = var8.length;
      CodeAttr var5 = var2.getCode();
      var5.emitLoad(var4);
      var5.emitInvokeInterface(startDocumentMethod);

      for(int var6 = 0; var6 < var7; ++var6) {
         compileChild(var8[var6], var2, var3);
      }

      var5.emitLoad(var4);
      var5.emitInvokeInterface(endDocumentMethod);
   }
}
