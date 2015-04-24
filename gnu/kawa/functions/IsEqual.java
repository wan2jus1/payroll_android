package gnu.kawa.functions;

import gnu.expr.Language;
import gnu.kawa.functions.Arithmetic;
import gnu.kawa.functions.NumberCompare;
import gnu.lists.FVector;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Procedure2;

public class IsEqual extends Procedure2 {

   Language language;


   public IsEqual(Language var1, String var2) {
      this.language = var1;
      this.setName(var2);
   }

   public static boolean apply(Object var0, Object var1) {
      if(var0 == var1) {
         return true;
      } else if(var0 != null && var1 != null) {
         if(var0 instanceof Number && var1 instanceof Number) {
            return numberEquals((Number)var0, (Number)var1);
         } else {
            int var3;
            int var4;
            if(var0 instanceof CharSequence) {
               if(!(var1 instanceof CharSequence)) {
                  return false;
               } else {
                  CharSequence var9 = (CharSequence)var0;
                  CharSequence var12 = (CharSequence)var1;
                  var3 = var9.length();
                  if(var3 != var12.length()) {
                     return false;
                  } else {
                     do {
                        var4 = var3 - 1;
                        if(var4 < 0) {
                           return true;
                        }

                        var3 = var4;
                     } while(var9.charAt(var4) == var12.charAt(var4));

                     return false;
                  }
               }
            } else if(var0 instanceof FVector) {
               if(!(var1 instanceof FVector)) {
                  return false;
               } else {
                  FVector var6 = (FVector)var0;
                  FVector var10 = (FVector)var1;
                  var3 = var6.size;
                  if(var10.data != null && var10.size == var3) {
                     Object[] var8 = var6.data;
                     Object[] var11 = var10.data;

                     do {
                        var4 = var3 - 1;
                        if(var4 < 0) {
                           return true;
                        }

                        var3 = var4;
                     } while(apply(var8[var4], var11[var4]));

                     return false;
                  } else {
                     return false;
                  }
               }
            } else if(!(var0 instanceof LList)) {
               return var0.equals(var1);
            } else if(var0 instanceof Pair && var1 instanceof Pair) {
               Pair var2 = (Pair)var0;
               Pair var5 = (Pair)var1;
               Pair var7 = var2;

               while(apply(var7.getCar(), var5.getCar())) {
                  var1 = var7.getCdr();
                  var0 = var5.getCdr();
                  if(var1 == var0) {
                     return true;
                  }

                  if(var1 != null && var0 != null) {
                     if(var1 instanceof Pair && var0 instanceof Pair) {
                        var7 = (Pair)var1;
                        var5 = (Pair)var0;
                        continue;
                     }

                     return apply(var1, var0);
                  }

                  return false;
               }

               return false;
            } else {
               return false;
            }
         }
      } else {
         return false;
      }
   }

   public static boolean numberEquals(Number var0, Number var1) {
      boolean var2 = Arithmetic.isExact(var0);
      boolean var3 = Arithmetic.isExact(var1);
      return var2 && var3?NumberCompare.$Eq(var0, var1):var2 == var3 && var0.equals(var1);
   }

   public Object apply2(Object var1, Object var2) {
      return this.language.booleanObject(apply(var1, var2));
   }
}
