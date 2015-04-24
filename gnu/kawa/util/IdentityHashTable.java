package gnu.kawa.util;

import gnu.kawa.util.GeneralHashTable;

public class IdentityHashTable extends GeneralHashTable {

   public IdentityHashTable() {
   }

   public IdentityHashTable(int var1) {
      super(var1);
   }

   public int hash(Object var1) {
      return System.identityHashCode(var1);
   }

   public boolean matches(Object var1, Object var2) {
      return var1 == var2;
   }
}
