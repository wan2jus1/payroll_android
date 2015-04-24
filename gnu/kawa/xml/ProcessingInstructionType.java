package gnu.kawa.xml;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.expr.Compilation;
import gnu.expr.TypeValue;
import gnu.kawa.xml.KProcessingInstruction;
import gnu.kawa.xml.NodeType;
import gnu.lists.AbstractSequence;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class ProcessingInstructionType extends NodeType implements TypeValue, Externalizable {

   static final Method coerceMethod = typeProcessingInstructionType.getDeclaredMethod("coerce", 2);
   static final Method coerceOrNullMethod = typeProcessingInstructionType.getDeclaredMethod("coerceOrNull", 2);
   public static final ProcessingInstructionType piNodeTest = new ProcessingInstructionType((String)null);
   public static final ClassType typeProcessingInstructionType = ClassType.make("gnu.kawa.xml.ProcessingInstructionType");
   String target;


   public ProcessingInstructionType(String var1) {
      String var2;
      if(var1 == null) {
         var2 = "processing-instruction()";
      } else {
         var2 = "processing-instruction(" + var1 + ")";
      }

      super(var2);
      this.target = var1;
   }

   public static KProcessingInstruction coerce(Object var0, String var1) {
      KProcessingInstruction var2 = coerceOrNull(var0, var1);
      if(var2 == null) {
         throw new ClassCastException();
      } else {
         return var2;
      }
   }

   public static KProcessingInstruction coerceOrNull(Object var0, String var1) {
      KProcessingInstruction var2 = (KProcessingInstruction)NodeType.coerceOrNull(var0, 32);
      return var2 != null && (var1 == null || var1.equals(var2.getTarget()))?var2:null;
   }

   public static ProcessingInstructionType getInstance(String var0) {
      return var0 == null?piNodeTest:new ProcessingInstructionType(var0);
   }

   public Object coerceFromObject(Object var1) {
      return coerce(var1, this.target);
   }

   public void emitCoerceFromObject(CodeAttr var1) {
      var1.emitPushString(this.target);
      var1.emitInvokeStatic(coerceMethod);
   }

   protected void emitCoerceOrNullMethod(Variable var1, Compilation var2) {
      CodeAttr var3 = var2.getCode();
      if(var1 != null) {
         var3.emitLoad(var1);
      }

      var3.emitPushString(this.target);
      var3.emitInvokeStatic(coerceOrNullMethod);
   }

   public Type getImplementationType() {
      return ClassType.make("gnu.kawa.xml.KProcessingInstruction");
   }

   public boolean isInstance(Object var1) {
      return coerceOrNull(var1, this.target) != null;
   }

   public boolean isInstancePos(AbstractSequence var1, int var2) {
      boolean var4 = false;
      int var3 = var1.getNextKind(var2);
      if(var3 == 37) {
         if(this.target == null || this.target.equals(var1.getNextTypeObject(var2))) {
            var4 = true;
         }
      } else if(var3 == 32) {
         return this.isInstance(var1.getPosNext(var2));
      }

      return var4;
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      this.target = (String)var1.readObject();
   }

   public String toString() {
      StringBuilder var2 = (new StringBuilder()).append("ProcessingInstructionType ");
      String var1;
      if(this.target == null) {
         var1 = "*";
      } else {
         var1 = this.target;
      }

      return var2.append(var1).toString();
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      var1.writeObject(this.target);
   }
}
