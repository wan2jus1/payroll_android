package gnu.kawa.reflect;

import gnu.bytecode.Type;
import gnu.mapping.Procedure;
import gnu.mapping.Procedure2;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Array;

public class ArrayGet extends Procedure2 implements Externalizable {

   Type element_type;


   public ArrayGet(Type var1) {
      this.element_type = var1;
      this.setProperty(Procedure.validateApplyKey, "gnu.kawa.reflect.CompileArrays:validateArrayGet");
      Procedure.compilerKey.set(this, "*gnu.kawa.reflect.CompileArrays:getForArrayGet");
   }

   public Object apply2(Object var1, Object var2) {
      var1 = Array.get(var1, ((Number)var2).intValue());
      return this.element_type.coerceToObject(var1);
   }

   public boolean isSideEffectFree() {
      return true;
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      this.element_type = (Type)var1.readObject();
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      var1.writeObject(this.element_type);
   }
}
