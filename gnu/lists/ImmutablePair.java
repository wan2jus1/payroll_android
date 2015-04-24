package gnu.lists;

import gnu.lists.Pair;

public class ImmutablePair extends Pair {

   public ImmutablePair() {
   }

   public ImmutablePair(Object var1, Object var2) {
      this.car = var1;
      this.cdr = var2;
   }

   public void setCar(Object var1) {
      throw new UnsupportedOperationException("cannot modify read-only pair");
   }

   public void setCdr(Object var1) {
      throw new UnsupportedOperationException("cannot modify read-only pair");
   }
}
