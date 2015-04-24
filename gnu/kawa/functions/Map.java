package gnu.kawa.functions;

import gnu.expr.Declaration;
import gnu.kawa.functions.ApplyToArgs;
import gnu.kawa.functions.IsEq;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Procedure;
import gnu.mapping.ProcedureN;
import gnu.mapping.Values;

public class Map extends ProcedureN {

   final Declaration applyFieldDecl;
   final ApplyToArgs applyToArgs;
   boolean collect;
   final IsEq isEq;


   public Map(boolean var1, ApplyToArgs var2, Declaration var3, IsEq var4) {
      String var5;
      if(var1) {
         var5 = "map";
      } else {
         var5 = "for-each";
      }

      super(var5);
      this.collect = var1;
      this.applyToArgs = var2;
      this.applyFieldDecl = var3;
      this.isEq = var4;
      this.setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileMisc:validateApplyMap");
   }

   public static void forEach1(Procedure var0, Object var1) throws Throwable {
      while(var1 != LList.Empty) {
         Pair var2 = (Pair)var1;
         var0.apply1(var2.getCar());
         var1 = var2.getCdr();
      }

   }

   public static Object map1(Procedure var0, Object var1) throws Throwable {
      Object var2 = LList.Empty;
      Pair var4 = null;
      Object var3 = var1;

      for(Pair var5 = var4; var3 != LList.Empty; var3 = var4.getCdr()) {
         var4 = (Pair)var3;
         Pair var6 = new Pair(var0.apply1(var4.getCar()), LList.Empty);
         if(var5 == null) {
            var2 = var6;
         } else {
            var5.setCdr(var6);
         }

         var5 = var6;
      }

      return var2;
   }

   public Object apply2(Object var1, Object var2) throws Throwable {
      if(var1 instanceof Procedure) {
         Procedure var3 = (Procedure)var1;
         if(this.collect) {
            return map1(var3, var2);
         } else {
            forEach1(var3, var2);
            return Values.empty;
         }
      } else {
         return this.applyN(new Object[]{var1, var2});
      }
   }

   public Object applyN(Object[] var1) throws Throwable {
      int var10 = var1.length - 1;
      Object var2;
      if(var10 == 1 && var1[0] instanceof Procedure) {
         Procedure var13 = (Procedure)((Procedure)var1[0]);
         if(!this.collect) {
            forEach1(var13, var1[1]);
            return Values.empty;
         } else {
            var2 = map1(var13, var1[1]);
            return var2;
         }
      } else {
         Pair var5 = null;
         if(this.collect) {
            var2 = LList.Empty;
         } else {
            var2 = Values.empty;
         }

         Object[] var6 = new Object[var10];
         System.arraycopy(var1, 1, var6, 0, var10);
         Object[] var3;
         Object var4;
         byte var8;
         Object var11;
         if(var1[0] instanceof Procedure) {
            var8 = 0;
            var3 = new Object[var10];
            var4 = (Procedure)var1[0];
            var11 = var2;
         } else {
            var8 = 1;
            var3 = new Object[var10 + 1];
            var3[0] = var1[0];
            var4 = this.applyToArgs;
            var11 = var2;
         }

         while(true) {
            Pair var12;
            for(int var9 = 0; var9 < var10; ++var9) {
               Object var7 = var6[var9];
               var2 = var11;
               if(var7 == LList.Empty) {
                  return var2;
               }

               var12 = (Pair)var7;
               var3[var8 + var9] = var12.getCar();
               var6[var9] = var12.getCdr();
            }

            var2 = ((Procedure)var4).applyN(var3);
            if(this.collect) {
               var12 = new Pair(var2, LList.Empty);
               if(var5 == null) {
                  var11 = var12;
               } else {
                  var5.setCdr(var12);
               }

               var5 = var12;
            }
         }
      }
   }
}
