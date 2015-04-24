package kawa.lib;

import gnu.expr.GenericProc;
import gnu.expr.Keyword;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.expr.Special;
import gnu.lists.Consumer;
import gnu.lists.FVector;
import gnu.lists.LList;
import gnu.mapping.CallContext;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import kawa.lib.lists;

public class vectors extends ModuleBody {

   public static final vectors $instance = new vectors();
   static final Keyword Lit0 = Keyword.make("setter");
   static final SimpleSymbol Lit1 = (SimpleSymbol)(new SimpleSymbol("vector?")).readResolve();
   static final SimpleSymbol Lit2 = (SimpleSymbol)(new SimpleSymbol("make-vector")).readResolve();
   static final SimpleSymbol Lit3 = (SimpleSymbol)(new SimpleSymbol("vector-length")).readResolve();
   static final SimpleSymbol Lit4 = (SimpleSymbol)(new SimpleSymbol("vector-set!")).readResolve();
   static final SimpleSymbol Lit5 = (SimpleSymbol)(new SimpleSymbol("vector-ref")).readResolve();
   static final SimpleSymbol Lit6 = (SimpleSymbol)(new SimpleSymbol("vector->list")).readResolve();
   static final SimpleSymbol Lit7 = (SimpleSymbol)(new SimpleSymbol("list->vector")).readResolve();
   static final SimpleSymbol Lit8 = (SimpleSymbol)(new SimpleSymbol("vector-fill!")).readResolve();
   public static final ModuleMethod list$Mn$Grvector;
   public static final ModuleMethod make$Mnvector;
   public static final ModuleMethod vector$Mn$Grlist;
   public static final ModuleMethod vector$Mnfill$Ex;
   public static final ModuleMethod vector$Mnlength;
   public static final GenericProc vector$Mnref;
   static final ModuleMethod vector$Mnref$Fn1;
   public static final ModuleMethod vector$Mnset$Ex;
   public static final ModuleMethod vector$Qu;


   static {
      vectors var0 = $instance;
      vector$Qu = new ModuleMethod(var0, 1, Lit1, 4097);
      make$Mnvector = new ModuleMethod(var0, 2, Lit2, 8193);
      vector$Mnlength = new ModuleMethod(var0, 4, Lit3, 4097);
      vector$Mnset$Ex = new ModuleMethod(var0, 5, Lit4, 12291);
      vector$Mnref$Fn1 = new ModuleMethod(var0, 6, Lit5, 8194);
      vector$Mn$Grlist = new ModuleMethod(var0, 7, Lit6, 4097);
      list$Mn$Grvector = new ModuleMethod(var0, 8, Lit7, 4097);
      vector$Mnfill$Ex = new ModuleMethod(var0, 9, Lit8, 8194);
      $instance.run();
   }

   public vectors() {
      ModuleInfo.register(this);
   }

   public static boolean isVector(Object var0) {
      return var0 instanceof FVector;
   }

   public static FVector list$To$Vector(LList var0) {
      return new FVector(var0);
   }

   public static FVector makeVector(int var0) {
      return makeVector(var0, Special.undefined);
   }

   public static FVector makeVector(int var0, Object var1) {
      return new FVector(var0, var1);
   }

   public static LList vector$To$List(FVector var0) {
      Object var1 = LList.Empty;
      int var2 = vectorLength(var0);

      while(true) {
         --var2;
         if(var2 < 0) {
            return (LList)var1;
         }

         var1 = lists.cons(vector$Mnref.apply2(var0, Integer.valueOf(var2)), var1);
      }
   }

   public static void vectorFill$Ex(FVector var0, Object var1) {
      var0.setAll(var1);
   }

   public static int vectorLength(FVector var0) {
      return var0.size();
   }

   public static Object vectorRef(FVector var0, int var1) {
      return var0.get(var1);
   }

   public static void vectorSet$Ex(FVector var0, int var1, Object var2) {
      var0.set(var1, var2);
   }

   public Object apply1(ModuleMethod var1, Object var2) {
      FVector var9;
      switch(var1.selector) {
      case 1:
         if(isVector(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 2:
         int var3;
         try {
            var3 = ((Number)var2).intValue();
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "make-vector", 1, var2);
         }

         return makeVector(var3);
      case 3:
      case 5:
      case 6:
      default:
         return super.apply1(var1, var2);
      case 4:
         try {
            var9 = (FVector)var2;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "vector-length", 1, var2);
         }

         return Integer.valueOf(vectorLength(var9));
      case 7:
         try {
            var9 = (FVector)var2;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "vector->list", 1, var2);
         }

         return vector$To$List(var9);
      case 8:
         LList var8;
         try {
            var8 = (LList)var2;
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "list->vector", 1, var2);
         }

         return list$To$Vector(var8);
      }
   }

   public Object apply2(ModuleMethod var1, Object var2, Object var3) {
      int var4;
      FVector var9;
      switch(var1.selector) {
      case 2:
         try {
            var4 = ((Number)var2).intValue();
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "make-vector", 1, var2);
         }

         return makeVector(var4, var3);
      case 6:
         try {
            var9 = (FVector)var2;
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "vector-ref", 1, var2);
         }

         try {
            var4 = ((Number)var3).intValue();
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "vector-ref", 2, var3);
         }

         return vectorRef(var9, var4);
      case 9:
         try {
            var9 = (FVector)var2;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "vector-fill!", 1, var2);
         }

         vectorFill$Ex(var9, var3);
         return Values.empty;
      default:
         return super.apply2(var1, var2, var3);
      }
   }

   public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
      if(var1.selector == 5) {
         FVector var8;
         try {
            var8 = (FVector)var2;
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "vector-set!", 1, var2);
         }

         int var5;
         try {
            var5 = ((Number)var3).intValue();
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "vector-set!", 2, var3);
         }

         vectorSet$Ex(var8, var5, var4);
         return Values.empty;
      } else {
         return super.apply3(var1, var2, var3, var4);
      }
   }

   public int match1(ModuleMethod var1, Object var2, CallContext var3) {
      switch(var1.selector) {
      case 1:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 2:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 3:
      case 5:
      case 6:
      default:
         return super.match1(var1, var2, var3);
      case 4:
         if(var2 instanceof FVector) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 7:
         if(var2 instanceof FVector) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 8:
         if(var2 instanceof LList) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return -786431;
         }
      }
   }

   public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
      switch(var1.selector) {
      case 2:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 6:
         if(var2 instanceof FVector) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }

         return -786431;
      case 9:
         if(var2 instanceof FVector) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }

         return -786431;
      default:
         return super.match2(var1, var2, var3, var4);
      }
   }

   public int match3(ModuleMethod var1, Object var2, Object var3, Object var4, CallContext var5) {
      if(var1.selector == 5) {
         if(var2 instanceof FVector) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         } else {
            return -786431;
         }
      } else {
         return super.match3(var1, var2, var3, var4, var5);
      }
   }

   public final void run(CallContext var1) {
      Consumer var5 = var1.consumer;
      vector$Mnref = new GenericProc("vector-ref");
      GenericProc var6 = vector$Mnref;
      Keyword var2 = Lit0;
      ModuleMethod var3 = vector$Mnset$Ex;
      ModuleMethod var4 = vector$Mnref$Fn1;
      var6.setProperties(new Object[]{var2, var3, vector$Mnref$Fn1});
   }
}
