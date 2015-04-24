package gnu.kawa.functions;

import gnu.lists.Array;
import gnu.lists.Sequence;
import gnu.mapping.ProcedureN;

public class ArrayRef extends ProcedureN {

   public static final ArrayRef arrayRef = new ArrayRef();


   public static Object arrayRef(Array var0, Sequence var1) {
      int var4 = var1.size();
      int[] var2 = new int[var4];

      for(int var3 = 0; var3 < var4; ++var3) {
         var2[var3] = ((Number)var1.get(var3)).intValue();
      }

      return var0.get(var2);
   }

   public Object apply2(Object var1, Object var2) throws Throwable {
      return var2 instanceof Sequence?arrayRef((Array)var1, (Sequence)var2):super.apply2(var1, var2);
   }

   public Object applyN(Object[] var1) throws Throwable {
      Array var2 = (Array)var1[0];
      if(var1.length == 2) {
         Object var3 = var1[1];
         if(var3 instanceof Sequence) {
            return arrayRef(var2, (Sequence)var3);
         }
      }

      int[] var5 = new int[var1.length - 1];
      int var4 = var1.length - 1;

      while(true) {
         --var4;
         if(var4 < 0) {
            return var2.get(var5);
         }

         var5[var4] = ((Number)var1[var4 + 1]).intValue();
      }
   }
}
