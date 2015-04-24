package kawa.standard;

import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.ProcedureN;
import gnu.mapping.WrongType;

public class append extends ProcedureN {

   public static final append append = new append();


   static {
      append.setName("append");
   }

   public static Object append$V(Object[] var0) {
      int var6 = var0.length;
      Object var2;
      if(var6 == 0) {
         var2 = LList.Empty;
      } else {
         Object var1 = var0[var6 - 1];
         --var6;

         while(true) {
            --var6;
            var2 = var1;
            if(var6 < 0) {
               break;
            }

            Object var4 = var0[var6];
            Pair var3 = null;

            Pair var5;
            for(var2 = null; var4 instanceof Pair; var4 = var5.getCdr()) {
               var5 = (Pair)var4;
               Pair var7 = new Pair(var5.getCar(), (Object)null);
               if(var3 == null) {
                  var2 = var7;
               } else {
                  var3.setCdr(var7);
               }

               var3 = var7;
            }

            if(var4 != LList.Empty) {
               throw new WrongType(append, var6 + 1, var0[var6], "list");
            }

            if(var3 != null) {
               var3.setCdr(var1);
            } else {
               var2 = var1;
            }

            var1 = var2;
         }
      }

      return var2;
   }

   public Object applyN(Object[] var1) {
      return append$V(var1);
   }
}
