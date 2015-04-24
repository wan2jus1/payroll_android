package gnu.kawa.functions;

import gnu.kawa.functions.NamedPart;
import gnu.mapping.Procedure;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

class NamedPartSetter extends gnu.mapping.Setter implements Externalizable {

   public NamedPartSetter(NamedPart var1) {
      super(var1);
      this.setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileNamedPart:validateNamedPartSetter");
   }

   Procedure getGetter() {
      return this.getter;
   }

   public int numArgs() {
      return ((NamedPart)this.getter).kind == 68?8193:-4096;
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      this.getter = (Procedure)var1.readObject();
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      var1.writeObject(this.getter);
   }
}
