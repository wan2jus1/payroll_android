package kawa.lang;

import gnu.lists.Consumer;
import gnu.text.Printable;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import kawa.lang.Pattern;

public class AnyPat extends Pattern implements Printable, Externalizable {

   public static AnyPat make() {
      return new AnyPat();
   }

   public boolean match(Object var1, Object[] var2, int var3) {
      var2[var3] = var1;
      return true;
   }

   public void print(Consumer var1) {
      var1.write("#<match any>");
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
   }

   public int varCount() {
      return 1;
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
   }
}
