package gnu.mapping;

import gnu.lists.Sequence;
import gnu.mapping.Values;

public class ValueStack extends Values implements Sequence {

   public void clear() {
      this.oindex = 0;
      super.clear();
   }
}
