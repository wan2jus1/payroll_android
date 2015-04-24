package gnu.kawa.util;

import gnu.kawa.util.AbstractHashTable;
import gnu.kawa.util.HashNode;

public class GeneralHashTable extends AbstractHashTable {

   public GeneralHashTable() {
   }

   public GeneralHashTable(int var1) {
      super(var1);
   }

   protected HashNode[] allocEntries(int var1) {
      return (HashNode[])(new HashNode[var1]);
   }

   protected int getEntryHashCode(HashNode var1) {
      return var1.hash;
   }

   protected HashNode getEntryNext(HashNode var1) {
      return var1.next;
   }

   public HashNode getNode(Object var1) {
      return (HashNode)super.getNode(var1);
   }

   protected HashNode makeEntry(Object var1, int var2, Object var3) {
      HashNode var4 = new HashNode(var1, var3);
      var4.hash = var2;
      return var4;
   }

   protected void setEntryNext(HashNode var1, HashNode var2) {
      var1.next = var2;
   }
}
