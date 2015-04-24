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
import gnu.kawa.xml.KText;
import gnu.kawa.xml.NodeConstructor;
import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.Values;
import gnu.xml.NodeTree;
import gnu.xml.TextUtils;
import gnu.xml.XMLFilter;

public class MakeText extends NodeConstructor {

   public static final MakeText makeText = new MakeText();


   public static void text$X(Object var0, CallContext var1) {
      if(var0 != null && (!(var0 instanceof Values) || !((Values)var0).isEmpty())) {
         Consumer var2 = var1.consumer;
         XMLFilter var3 = NodeConstructor.pushNodeContext(var1);

         try {
            TextUtils.textValue(var0, var3);
         } finally {
            NodeConstructor.popNodeContext(var2, var1);
         }

      }
   }

   public void apply(CallContext var1) {
      text$X(var1.getNextArg((Object)null), var1);
   }

   public Object apply1(Object var1) {
      if(var1 != null && (!(var1 instanceof Values) || !((Values)var1).isEmpty())) {
         NodeTree var2 = new NodeTree();
         TextUtils.textValue(var1, new XMLFilter(var2));
         return KText.make(var2);
      } else {
         return var1;
      }
   }

   public void compile(ApplyExp var1, Compilation var2, Target var3) {
      ApplyExp.compile(var1, var2, var3);
   }

   public void compileToNode(ApplyExp var1, Compilation var2, ConsumerTarget var3) {
      CodeAttr var4 = var2.getCode();
      Expression var5 = var1.getArgs()[0];
      Variable var10 = var3.getConsumerVariable();
      if(var5 instanceof QuoteExp) {
         Object var12 = ((QuoteExp)var5).getValue();
         if(var12 instanceof String) {
            String var11 = (String)var12;
            String var13 = CodeAttr.calculateSplit(var11);
            int var9 = var13.length();
            Method var14 = ((ClassType)var10.getType()).getMethod("write", new Type[]{Type.string_type});
            int var7 = 0;

            for(int var6 = 0; var6 < var9; ++var6) {
               var4.emitLoad(var10);
               int var8 = var7 + var13.charAt(var6);
               var4.emitPushString(var11.substring(var7, var8));
               var4.emitInvoke(var14);
               var7 = var8;
            }

            return;
         }
      }

      var5.compile(var2, (Target)Target.pushObject);
      var4.emitLoad(var10);
      var4.emitInvokeStatic(ClassType.make("gnu.xml.TextUtils").getDeclaredMethod("textValue", 2));
   }

   public int numArgs() {
      return 4097;
   }
}
