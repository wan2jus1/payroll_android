package gnu.kawa.reflect;

import gnu.bytecode.Type;
import gnu.mapping.Procedure;
import gnu.mapping.Procedure3;
import gnu.mapping.Values;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Array;

public class ArraySet extends Procedure3 implements Externalizable {

   Type element_type;


   public ArraySet(Type var1) {
      this.element_type = var1;
      this.setProperty(Procedure.validateApplyKey, "gnu.kawa.reflect.CompileArrays:validateArraySet");
      Procedure.compilerKey.set(this, "*gnu.kawa.reflect.CompileArrays:getForArraySet");
   }

   public Object apply3(Object var1, Object var2, Object var3) {
      Array.set(var1, ((Number)var2).intValue(), this.element_type.coerceFromObject(var3));
      return Values.empty;
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      this.element_type = (Type)var1.readObject();
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      var1.writeObject(this.element_type);
   }
}
