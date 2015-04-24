package gnu.kawa.reflect;

import gnu.bytecode.Type;
import gnu.mapping.Procedure;
import gnu.mapping.Procedure1;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Array;

public class ArrayNew extends Procedure1 implements Externalizable {

   Type element_type;


   public ArrayNew(Type var1) {
      this.element_type = var1;
      this.setProperty(Procedure.validateApplyKey, "gnu.kawa.reflect.CompileArrays:validateArrayNew");
      Procedure.compilerKey.set(this, "*gnu.kawa.reflect.CompileArrays:getForArrayNew");
   }

   public Object apply1(Object var1) {
      return Array.newInstance(this.element_type.getImplementationType().getReflectClass(), ((Number)var1).intValue());
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
