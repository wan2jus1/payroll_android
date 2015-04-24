package kawa.lang;

import gnu.lists.Consumer;
import gnu.lists.Pair;
import kawa.lang.Pattern;

public class VarListPat extends Pattern {

   int min_length;


   public VarListPat(int var1) {
      this.min_length = var1;
   }

   public boolean match(Object var1, Object[] var2, int var3) {
      int var4;
      for(var4 = 0; var4 < this.min_length; ++var4) {
         if(!(var1 instanceof Pair)) {
            return false;
         }

         Pair var5 = (Pair)var1;
         var2[var3 + var4] = var5.getCar();
         var1 = var5.getCdr();
      }

      var2[var3 + var4] = var1;
      return true;
   }

   public void print(Consumer var1) {
      var1.write("#<varlist-pattern min:");
      var1.writeInt(this.min_length);
      var1.write(62);
   }

   public int varCount() {
      return this.min_length + 1;
   }
}
