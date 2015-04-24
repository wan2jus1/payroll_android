package gnu.text;

import gnu.kawa.util.AbstractWeakHashTable;
import gnu.text.Char;

class CharMap extends AbstractWeakHashTable {

   public Char get(int var1) {
      this.cleanup();
      int var4 = this.hashToIndex(var1);

      for(AbstractWeakHashTable.WEntry var2 = ((AbstractWeakHashTable.WEntry[])this.table)[var4]; var2 != null; var2 = var2.next) {
         Char var3 = (Char)var2.getValue();
         if(var3.intValue() == var1) {
            return var3;
         }
      }

      Char var5 = new Char(var1);
      super.put(var5, var5);
      return var5;
   }

   protected Char getKeyFromValue(Char var1) {
      return var1;
   }

   protected boolean matches(Char var1, Char var2) {
      return var1.intValue() == var2.intValue();
   }
}
