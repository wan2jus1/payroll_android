package gnu.kawa.lispexpr;

import gnu.bytecode.ClassType;
import gnu.kawa.functions.GetNamedPart;
import gnu.mapping.Namespace;
import gnu.mapping.WrappedException;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;

public class ClassNamespace extends Namespace implements Externalizable {

   ClassType ctype;


   public ClassNamespace() {
   }

   public ClassNamespace(ClassType var1) {
      this.setName("class:" + var1.getName());
      this.ctype = var1;
   }

   public static ClassNamespace getInstance(String param0, ClassType param1) {
      // $FF: Couldn't be decompiled
   }

   public Object get(String var1) {
      try {
         Object var3 = GetNamedPart.getTypePart(this.ctype, var1);
         return var3;
      } catch (Throwable var2) {
         throw WrappedException.wrapIfNeeded(var2);
      }
   }

   public ClassType getClassType() {
      return this.ctype;
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      this.ctype = (ClassType)var1.readObject();
      this.setName("class:" + this.ctype.getName());
   }

   public Object readResolve() throws ObjectStreamException {
      String var1 = this.getName();
      if(var1 != null) {
         Namespace var2 = (Namespace)nsTable.get(var1);
         if(var2 instanceof ClassNamespace) {
            return var2;
         }

         nsTable.put(var1, this);
      }

      return this;
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      var1.writeObject(this.ctype);
   }
}
