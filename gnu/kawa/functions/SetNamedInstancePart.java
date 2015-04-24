package gnu.kawa.functions;

import gnu.kawa.reflect.SlotSet;
import gnu.mapping.Procedure;
import gnu.mapping.Procedure2;
import gnu.mapping.Values;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

class SetNamedInstancePart extends Procedure2 implements Externalizable {

   String pname;


   public SetNamedInstancePart() {
      this.setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileNamedPart:validateSetNamedInstancePart");
   }

   public SetNamedInstancePart(String var1) {
      this();
      this.setPartName(var1);
   }

   public Object apply2(Object var1, Object var2) throws Throwable {
      SlotSet.setField(var1, this.pname, var2);
      return Values.empty;
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      this.setPartName((String)var1.readObject());
   }

   public void setPartName(String var1) {
      this.setName("set-instance-part:." + var1);
      this.pname = var1;
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      var1.writeObject(this.pname);
   }
}
