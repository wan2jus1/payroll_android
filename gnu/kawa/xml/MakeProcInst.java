package gnu.kawa.xml;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.ConsumerTarget;
import gnu.expr.Expression;
import gnu.expr.Target;
import gnu.kawa.xml.KNode;
import gnu.kawa.xml.NodeConstructor;
import gnu.kawa.xml.UntypedAtomic;
import gnu.lists.Consumer;
import gnu.lists.XConsumer;
import gnu.mapping.CallContext;
import gnu.mapping.Values;
import gnu.xml.TextUtils;
import gnu.xml.XMLFilter;

public class MakeProcInst extends NodeConstructor {

   public static final MakeProcInst makeProcInst = new MakeProcInst();


   public static void procInst$C(Object var0, Object var1, Consumer var2) {
      var0 = KNode.atomicValue(var0);
      if(!(var0 instanceof String) && !(var0 instanceof UntypedAtomic)) {
         throw new ClassCastException("invalid type of processing-instruction target [XPTY0004]");
      } else if(var2 instanceof XConsumer) {
         StringBuffer var3 = new StringBuffer();
         int var4;
         if(var1 instanceof Values) {
            Object[] var6 = ((Values)var1).getValues();

            for(var4 = 0; var4 < var6.length; ++var4) {
               if(var4 > 0) {
                  var3.append(' ');
               }

               TextUtils.stringValue(var6[var4], var3);
            }
         } else {
            TextUtils.stringValue(var1, var3);
         }

         int var5 = var3.length();

         for(var4 = 0; var4 < var5 && Character.isWhitespace(var3.charAt(var4)); ++var4) {
            ;
         }

         char[] var7 = new char[var5 - var4];
         var3.getChars(var4, var5, var7, 0);
         ((XConsumer)var2).writeProcessingInstruction(var0.toString(), var7, 0, var7.length);
      }
   }

   public static void procInst$X(Object var0, Object var1, CallContext var2) {
      Consumer var3 = var2.consumer;
      XMLFilter var4 = NodeConstructor.pushNodeContext(var2);

      try {
         procInst$C(var0, var1, var4);
      } finally {
         NodeConstructor.popNodeContext(var3, var2);
      }

   }

   public void apply(CallContext var1) {
      procInst$X(var1.getNextArg((Object)null), var1.getNextArg((Object)null), var1);
   }

   public void compileToNode(ApplyExp var1, Compilation var2, ConsumerTarget var3) {
      CodeAttr var4 = var2.getCode();
      Expression[] var5 = var1.getArgs();
      var5[0].compile(var2, (Target)Target.pushObject);
      var5[1].compile(var2, (Target)Target.pushObject);
      var4.emitLoad(var3.getConsumerVariable());
      var4.emitInvokeStatic(ClassType.make("gnu.kawa.xml.MakeProcInst").getDeclaredMethod("procInst$C", 3));
   }

   public int numArgs() {
      return 8194;
   }
}
