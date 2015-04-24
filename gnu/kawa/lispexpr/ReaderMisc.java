package gnu.kawa.lispexpr;

import gnu.kawa.lispexpr.ReadTableEntry;

public class ReaderMisc extends ReadTableEntry {

   int kind;


   public ReaderMisc(int var1) {
      this.kind = var1;
   }

   public int getKind() {
      return this.kind;
   }
}
