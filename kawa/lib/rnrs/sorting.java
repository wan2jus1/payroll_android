package kawa.lib.rnrs;

import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.Sequence;
import gnu.mapping.CallContext;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import kawa.lib.srfi95;
import kawa.standard.append;

public class sorting extends ModuleBody {

   public static final sorting $instance = new sorting();
   static final SimpleSymbol Lit0 = (SimpleSymbol)(new SimpleSymbol("list-sort")).readResolve();
   static final SimpleSymbol Lit1 = (SimpleSymbol)(new SimpleSymbol("vector-sort")).readResolve();
   static final SimpleSymbol Lit2 = (SimpleSymbol)(new SimpleSymbol("vector-sort!")).readResolve();
   public static final ModuleMethod list$Mnsort;
   public static final ModuleMethod vector$Mnsort;
   public static final ModuleMethod vector$Mnsort$Ex;


   static {
      sorting var0 = $instance;
      list$Mnsort = new ModuleMethod(var0, 1, Lit0, 8194);
      vector$Mnsort = new ModuleMethod(var0, 2, Lit1, 8194);
      vector$Mnsort$Ex = new ModuleMethod(var0, 3, Lit2, 8194);
      $instance.run();
   }

   public sorting() {
      ModuleInfo.register(this);
   }

   public static Object listSort(Object var0, Object var1) {
      return srfi95.$PcSortList(append.append$V(new Object[]{var1, LList.Empty}), var0, Boolean.FALSE);
   }

   public static void vectorSort(Object var0, Object var1) {
      Sequence var2;
      try {
         var2 = (Sequence)var1;
      } catch (ClassCastException var3) {
         throw new WrongType(var3, "%sort-vector", 1, var1);
      }

      srfi95.$PcSortVector(var2, var0, Boolean.FALSE);
   }

   public static void vectorSort$Ex(Object var0, Object var1) {
      Sequence var2;
      try {
         var2 = (Sequence)var1;
      } catch (ClassCastException var3) {
         throw new WrongType(var3, "%vector-sort!", 1, var1);
      }

      srfi95.$PcVectorSort$Ex(var2, var0, Boolean.FALSE);
   }

   public Object apply2(ModuleMethod var1, Object var2, Object var3) {
      switch(var1.selector) {
      case 1:
         return listSort(var2, var3);
      case 2:
         vectorSort(var2, var3);
         return Values.empty;
      case 3:
         vectorSort$Ex(var2, var3);
         return Values.empty;
      default:
         return super.apply2(var1, var2, var3);
      }
   }

   public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
      switch(var1.selector) {
      case 1:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 2:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 3:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      default:
         return super.match2(var1, var2, var3, var4);
      }
   }

   public final void run(CallContext var1) {
      Consumer var2 = var1.consumer;
   }
}
