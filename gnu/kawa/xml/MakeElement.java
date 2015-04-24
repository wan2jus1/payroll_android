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
import gnu.kawa.xml.MakeAttribute;
import gnu.kawa.xml.NodeConstructor;
import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.Symbol;
import gnu.xml.NamespaceBinding;
import gnu.xml.XMLFilter;
import gnu.xml.XName;

public class MakeElement extends NodeConstructor {

   static final Method endElementMethod = typeMakeElement.getDeclaredMethod("endElement", 2);
   public static final MakeElement makeElement = new MakeElement();
   static final Method startElementMethod3 = typeMakeElement.getDeclaredMethod("startElement", 3);
   static final Method startElementMethod4 = typeMakeElement.getDeclaredMethod("startElement", 4);
   static final ClassType typeMakeElement = ClassType.make("gnu.kawa.xml.MakeElement");
   public int copyNamespacesMode = 1;
   private boolean handlingKeywordParameters;
   NamespaceBinding namespaceNodes;
   public Symbol tag;


   public static void endElement(Consumer var0, Object var1) {
      var0.endElement();
   }

   public static Symbol getTagName(ApplyExp var0) {
      Expression[] var1 = var0.getArgs();
      if(var1.length > 0) {
         Expression var2 = var1[0];
         if(var2 instanceof QuoteExp) {
            Object var3 = ((QuoteExp)var2).getValue();
            if(var3 instanceof Symbol) {
               return (Symbol)var3;
            }
         }
      }

      return null;
   }

   public static void startElement(Consumer var0, Object var1, int var2) {
      Symbol var3;
      if(var1 instanceof Symbol) {
         var3 = (Symbol)var1;
      } else {
         var3 = Symbol.make("", var1.toString(), "");
      }

      if(var0 instanceof XMLFilter) {
         ((XMLFilter)var0).copyNamespacesMode = var2;
      }

      var0.startElement(var3);
   }

   public static void startElement(Consumer var0, Object var1, int var2, NamespaceBinding var3) {
      XName var4;
      if(var1 instanceof Symbol) {
         var4 = new XName((Symbol)var1, var3);
      } else {
         var4 = new XName(Symbol.make("", var1.toString(), ""), var3);
      }

      if(var0 instanceof XMLFilter) {
         ((XMLFilter)var0).copyNamespacesMode = var2;
      }

      var0.startElement(var4);
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
      int var6;
      if(this.tag == null) {
         var8[0].compile(var2, (Target)Target.pushObject);
         var6 = 1;
      } else {
         var2.compileConstant(this.tag, Target.pushObject);
         var6 = 0;
      }

      var5.emitDup(1, 1);
      var5.emitPushInt(this.copyNamespacesMode);
      if(this.namespaceNodes != null) {
         var2.compileConstant(this.namespaceNodes, Target.pushObject);
         var5.emitInvokeStatic(startElementMethod4);
      } else {
         var5.emitInvokeStatic(startElementMethod3);
      }

      for(; var6 < var7; ++var6) {
         compileChild(var8[var6], var2, var3);
         if(this.isHandlingKeywordParameters()) {
            var5.emitLoad(var4);
            var5.emitInvokeInterface(MakeAttribute.endAttributeMethod);
         }
      }

      var5.emitInvokeStatic(endElementMethod);
   }

   public NamespaceBinding getNamespaceNodes() {
      return this.namespaceNodes;
   }

   public Type getReturnType(Expression[] var1) {
      return Compilation.typeObject;
   }

   public boolean isHandlingKeywordParameters() {
      return this.handlingKeywordParameters;
   }

   public int numArgs() {
      return this.tag == null?-4095:-4096;
   }

   public void setHandlingKeywordParameters(boolean var1) {
      this.handlingKeywordParameters = var1;
   }

   public void setNamespaceNodes(NamespaceBinding var1) {
      this.namespaceNodes = var1;
   }

   public String toString() {
      return "makeElement[" + this.tag + "]";
   }
}
