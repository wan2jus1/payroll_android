package gnu.bytecode;

import gnu.bytecode.ClassType;
import gnu.bytecode.Label;
import gnu.bytecode.ObjectType;
import gnu.bytecode.Type;

public class UninitializedType extends ObjectType {

   ClassType ctype;
   Label label;


   UninitializedType(ClassType var1) {
      super(var1.getName());
      this.setSignature(var1.getSignature());
      this.ctype = var1;
   }

   UninitializedType(ClassType var1, Label var2) {
      this(var1);
      this.label = var2;
   }

   static UninitializedType uninitializedThis(ClassType var0) {
      return new UninitializedType(var0);
   }

   public Type getImplementationType() {
      return this.ctype;
   }

   public String toString() {
      return "Uninitialized<" + this.ctype.getName() + '>';
   }
}
