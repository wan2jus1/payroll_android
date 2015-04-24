package kawa.standard;

import gnu.lists.FVector;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.ProcedureN;
import gnu.mapping.WrongType;

public class vector_append extends ProcedureN {

   public static final vector_append vectorAppend = new vector_append("vector-append");


   public vector_append(String var1) {
      super(var1);
   }

   public static FVector apply$V(Object[] var0) {
      int var3 = 0;
      int var6 = var0.length;
      int var4 = var6;

      while(true) {
         --var4;
         Object var1;
         int var5;
         if(var4 < 0) {
            Object[] var2 = new Object[var3];
            var3 = 0;

            for(var5 = 0; var5 < var6; var3 = var4) {
               var1 = var0[var5];
               if(var1 instanceof FVector) {
                  FVector var8 = (FVector)var1;
                  int var7 = var8.size();

                  for(var4 = 0; var4 < var7; ++var3) {
                     var2[var3] = var8.get(var4);
                     ++var4;
                  }

                  var4 = var3;
               } else {
                  var4 = var3;
                  if(var1 instanceof Pair) {
                     while(true) {
                        var4 = var3;
                        if(var1 == LList.Empty) {
                           break;
                        }

                        Pair var9 = (Pair)var1;
                        var2[var3] = var9.getCar();
                        var1 = var9.getCdr();
                        ++var3;
                     }
                  }
               }

               ++var5;
            }

            return new FVector(var2);
         }

         var1 = var0[var4];
         if(var1 instanceof FVector) {
            var3 += ((FVector)var1).size();
         } else {
            var5 = LList.listLength(var1, false);
            if(var5 < 0) {
               throw new WrongType(vectorAppend, var4, var1, "list or vector");
            }

            var3 += var5;
         }
      }
   }

   public Object applyN(Object[] var1) {
      return apply$V(var1);
   }
}
