package gnu.kawa.functions;

import gnu.lists.Array;
import gnu.lists.Sequence;
import gnu.mapping.ProcedureN;
import gnu.mapping.Values;

public class ArraySet extends ProcedureN {

   public static final ArraySet arraySet = new ArraySet();


   public static void arraySet(Array var0, Sequence var1, Object var2) {
      int var5 = var1.size();
      int[] var3 = new int[var5];

      for(int var4 = 0; var4 < var5; ++var4) {
         var3[var4] = ((Number)var1.get(var4)).intValue();
      }

      var0.set(var3, var2);
   }

   public Object apply3(Object var1, Object var2, Object var3) throws Throwable {
      if(var2 instanceof Sequence) {
         arraySet((Array)var1, (Sequence)var2, var3);
         return Values.empty;
      } else {
         return super.apply3(var1, var2, var3);
      }
   }

   public Object applyN(Object[] var1) throws Throwable {
      Array var2 = (Array)var1[0];
      if(var1.length == 3) {
         Object var3 = var1[1];
         if(var3 instanceof Sequence) {
            arraySet(var2, (Sequence)var3, var1[2]);
            return Values.empty;
         }
      }

      int var5 = var1.length - 2;
      int[] var6 = new int[var5];
      int var4 = var5;

      while(true) {
         --var4;
         if(var4 < 0) {
            var2.set(var6, var1[var5 + 1]);
            return Values.empty;
         }

         var6[var4] = ((Number)var1[var4 + 1]).intValue();
      }
   }
}
