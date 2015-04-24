package gnu.lists;

import gnu.lists.Sequence;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;

public class EofClass implements Externalizable {

   public static final EofClass eofValue = new EofClass();


   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
   }

   public Object readResolve() throws ObjectStreamException {
      return Sequence.eofValue;
   }

   public final String toString() {
      return "#!eof";
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
   }
}
