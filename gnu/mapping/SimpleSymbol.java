package gnu.mapping;

import gnu.mapping.Namespace;
import gnu.mapping.Symbol;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;

public class SimpleSymbol extends Symbol {

   public SimpleSymbol() {
   }

   public SimpleSymbol(String var1) {
      super(Namespace.EmptyNamespace, var1);
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      this.name = ((String)var1.readObject()).intern();
   }

   public Object readResolve() throws ObjectStreamException {
      return Namespace.EmptyNamespace.getSymbol(this.getName().intern());
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      var1.writeObject(this.getName());
   }
}
