package kawa.lang;

import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.text.ReportFormat;
import kawa.lang.Pattern;

public class ListPat extends Pattern {

   Object default_value;
   int max_length;
   int min_length;


   public ListPat(int var1) {
      this.min_length = var1;
      this.max_length = var1;
   }

   public ListPat(int var1, int var2) {
      this.min_length = var1;
      this.max_length = var2;
   }

   public ListPat(int var1, int var2, Object var3) {
      this.min_length = var1;
      this.max_length = var2;
      this.default_value = var3;
   }

   public static boolean match(int var0, int var1, Object var2, Object var3, Object[] var4, int var5) {
      int var6 = 0;

      while(true) {
         if(var6 < var1) {
            if(var3 instanceof Pair) {
               Pair var7 = (Pair)var3;
               var4[var5 + var6] = var7.getCar();
               var3 = var7.getCdr();
               ++var6;
               continue;
            }

            if(var6 < var0) {
               break;
            }
         }

         var0 = var6;
         if(var6 == var1) {
            if(var3 != LList.Empty) {
               break;
            }

            var0 = var6;
         }

         while(var0 < var1) {
            var4[var5 + var0] = var2;
            ++var0;
         }

         return true;
      }

      return false;
   }

   public static Object[] match(int var0, int var1, Object var2, Object var3) {
      Object[] var4 = new Object[var1];
      return match(var0, var1, var2, var3, var4, 0)?var4:null;
   }

   public boolean match(Object var1, Object[] var2, int var3) {
      return match(this.min_length, this.max_length, this.default_value, var1, var2, var3);
   }

   public void print(Consumer var1) {
      var1.write("#<list-pattern min:");
      var1.write(Integer.toString(this.min_length));
      var1.write(" max:");
      var1.write(Integer.toString(this.max_length));
      var1.write(" default:");
      ReportFormat.print((Object)this.default_value, (Consumer)var1);
      var1.write(62);
   }

   public int varCount() {
      return this.max_length;
   }
}
