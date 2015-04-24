package gnu.kawa.functions;

import gnu.kawa.functions.SetNamedInstancePart;
import gnu.kawa.reflect.Invoke;
import gnu.kawa.reflect.SlotGet;
import gnu.mapping.HasSetter;
import gnu.mapping.Procedure;
import gnu.mapping.ProcedureN;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class GetNamedInstancePart extends ProcedureN implements Externalizable, HasSetter {

   boolean isField;
   String pname;


   public GetNamedInstancePart() {
      this.setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileNamedPart:validateGetNamedInstancePart");
   }

   public GetNamedInstancePart(String var1) {
      this();
      this.setPartName(var1);
   }

   public Object applyN(Object[] var1) throws Throwable {
      checkArgCount(this, var1.length);
      if(this.isField) {
         return SlotGet.field(var1[0], this.pname);
      } else {
         Object[] var2 = new Object[var1.length + 1];
         var2[0] = var1[0];
         var2[1] = this.pname;
         System.arraycopy(var1, 1, var2, 2, var1.length - 1);
         return Invoke.invoke.applyN(var2);
      }
   }

   public Procedure getSetter() {
      if(!this.isField) {
         throw new RuntimeException("no setter for instance method call");
      } else {
         return new SetNamedInstancePart(this.pname);
      }
   }

   public int numArgs() {
      return this.isField?4097:-4095;
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      this.setPartName((String)var1.readObject());
   }

   public void setPartName(String var1) {
      this.setName("get-instance-part:" + var1);
      if(var1.length() > 1 && var1.charAt(0) == 46) {
         this.isField = true;
         this.pname = var1.substring(1);
      } else {
         this.isField = false;
         this.pname = var1;
      }
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      String var2;
      if(this.isField) {
         var2 = "." + this.pname;
      } else {
         var2 = this.pname;
      }

      var1.writeObject(var2);
   }
}
