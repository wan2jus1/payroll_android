package gnu.expr;

import gnu.lists.Consumer;
import gnu.lists.Sequence;
import gnu.text.Printable;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;

public class Special implements Printable, Externalizable {

   public static final Special abstractSpecial = new Special("abstract");
   public static final Special dfault = new Special("default");
   public static final Object eof = Sequence.eofValue;
   public static final Special key = new Special("key");
   public static final Special optional = new Special("optional");
   public static final Special rest = new Special("rest");
   public static final Special undefined = new Special("undefined");
   private String name;


   public Special() {
   }

   private Special(String var1) {
      this.name = new String(var1);
   }

   public static Special make(String var0) {
      return var0 == "optional"?optional:(var0 == "rest"?rest:(var0 == "key"?key:(var0 == "default"?dfault:new Special(var0))));
   }

   public int hashCode() {
      return this.name.hashCode();
   }

   public void print(Consumer var1) {
      var1.write("#!");
      var1.write(this.name);
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      this.name = var1.readUTF();
   }

   public Object readResolve() throws ObjectStreamException {
      return make(this.name);
   }

   public final String toString() {
      return "#!" + this.name;
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      var1.writeUTF(this.name);
   }
}
