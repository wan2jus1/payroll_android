package kawa.lib;

import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.Arrays;
import gnu.lists.Array;
import gnu.lists.Consumer;
import gnu.lists.FVector;
import gnu.mapping.CallContext;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.WrongType;

public class arrays extends ModuleBody {

   public static final Class $Lsarray$Gr = Array.class;
   public static final arrays $instance = new arrays();
   static final SimpleSymbol Lit0 = (SimpleSymbol)(new SimpleSymbol("array?")).readResolve();
   static final SimpleSymbol Lit1 = (SimpleSymbol)(new SimpleSymbol("shape")).readResolve();
   static final SimpleSymbol Lit2 = (SimpleSymbol)(new SimpleSymbol("make-array")).readResolve();
   static final SimpleSymbol Lit3 = (SimpleSymbol)(new SimpleSymbol("array")).readResolve();
   static final SimpleSymbol Lit4 = (SimpleSymbol)(new SimpleSymbol("array-rank")).readResolve();
   static final SimpleSymbol Lit5 = (SimpleSymbol)(new SimpleSymbol("array-start")).readResolve();
   static final SimpleSymbol Lit6 = (SimpleSymbol)(new SimpleSymbol("array-end")).readResolve();
   static final SimpleSymbol Lit7 = (SimpleSymbol)(new SimpleSymbol("share-array")).readResolve();
   public static final ModuleMethod array;
   public static final ModuleMethod array$Mnend;
   public static final ModuleMethod array$Mnrank;
   public static final ModuleMethod array$Mnstart;
   public static final ModuleMethod array$Qu;
   public static final ModuleMethod make$Mnarray;
   public static final ModuleMethod shape;
   public static final ModuleMethod share$Mnarray;


   static {
      arrays var0 = $instance;
      array$Qu = new ModuleMethod(var0, 1, Lit0, 4097);
      shape = new ModuleMethod(var0, 2, Lit1, -4096);
      make$Mnarray = new ModuleMethod(var0, 3, Lit2, 8193);
      array = new ModuleMethod(var0, 5, Lit3, -4095);
      array$Mnrank = new ModuleMethod(var0, 6, Lit4, 4097);
      array$Mnstart = new ModuleMethod(var0, 7, Lit5, 8194);
      array$Mnend = new ModuleMethod(var0, 8, Lit6, 8194);
      share$Mnarray = new ModuleMethod(var0, 9, Lit7, 12291);
      $instance.run();
   }

   public arrays() {
      ModuleInfo.register(this);
   }

   public static Array array(Array var0, Object ... var1) {
      return Arrays.makeSimple(var0, new FVector(var1));
   }

   public static int arrayEnd(Array var0, int var1) {
      return var0.getLowBound(var1) + var0.getSize(var1);
   }

   public static int arrayRank(Array var0) {
      return var0.rank();
   }

   public static int arrayStart(Array var0, int var1) {
      return var0.getLowBound(var1);
   }

   public static boolean isArray(Object var0) {
      return var0 instanceof Array;
   }

   public static Array makeArray(Array var0) {
      return makeArray(var0, (Object)null);
   }

   public static Array makeArray(Array var0, Object var1) {
      return Arrays.make(var0, var1);
   }

   public static Array shape(Object ... var0) {
      return Arrays.shape(var0);
   }

   public static Array shareArray(Array var0, Array var1, Procedure var2) {
      return Arrays.shareArray(var0, var1, var2);
   }

   public Object apply1(ModuleMethod var1, Object var2) {
      Array var5;
      switch(var1.selector) {
      case 1:
         if(isArray(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 2:
      case 4:
      case 5:
      default:
         return super.apply1(var1, var2);
      case 3:
         try {
            var5 = (Array)var2;
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "make-array", 1, var2);
         }

         return makeArray(var5);
      case 6:
         try {
            var5 = (Array)var2;
         } catch (ClassCastException var3) {
            throw new WrongType(var3, "array-rank", 1, var2);
         }

         return Integer.valueOf(arrayRank(var5));
      }
   }

   public Object apply2(ModuleMethod var1, Object var2, Object var3) {
      int var4;
      Array var10;
      switch(var1.selector) {
      case 3:
         try {
            var10 = (Array)var2;
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "make-array", 1, var2);
         }

         return makeArray(var10, var3);
      case 4:
      case 5:
      case 6:
      default:
         return super.apply2(var1, var2, var3);
      case 7:
         try {
            var10 = (Array)var2;
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "array-start", 1, var2);
         }

         try {
            var4 = ((Number)var3).intValue();
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "array-start", 2, var3);
         }

         return Integer.valueOf(arrayStart(var10, var4));
      case 8:
         try {
            var10 = (Array)var2;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "array-end", 1, var2);
         }

         try {
            var4 = ((Number)var3).intValue();
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "array-end", 2, var3);
         }

         return Integer.valueOf(arrayEnd(var10, var4));
      }
   }

   public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
      if(var1.selector == 9) {
         Array var8;
         try {
            var8 = (Array)var2;
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "share-array", 1, var2);
         }

         Array var9;
         try {
            var9 = (Array)var3;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "share-array", 2, var3);
         }

         Procedure var10;
         try {
            var10 = (Procedure)var4;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "share-array", 3, var4);
         }

         return shareArray(var8, var9, var10);
      } else {
         return super.apply3(var1, var2, var3, var4);
      }
   }

   public Object applyN(ModuleMethod var1, Object[] var2) {
      switch(var1.selector) {
      case 2:
         return shape(var2);
      case 3:
      case 4:
      default:
         return super.applyN(var1, var2);
      case 5:
         Object var6 = var2[0];

         Array var3;
         try {
            var3 = (Array)var6;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "array", 1, var6);
         }

         int var4 = var2.length - 1;
         Object[] var7 = new Object[var4];

         while(true) {
            --var4;
            if(var4 < 0) {
               return array(var3, var7);
            }

            var7[var4] = var2[var4 + 1];
         }
      }
   }

   public int match1(ModuleMethod var1, Object var2, CallContext var3) {
      int var4 = -786431;
      switch(var1.selector) {
      case 1:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 2:
      case 4:
      case 5:
      default:
         var4 = super.match1(var1, var2, var3);
         break;
      case 3:
         if(var2 instanceof Array) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }
         break;
      case 6:
         if(var2 instanceof Array) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }
      }

      return var4;
   }

   public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
      int var5 = -786431;
      switch(var1.selector) {
      case 3:
         if(var2 instanceof Array) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }
         break;
      case 4:
      case 5:
      case 6:
      default:
         var5 = super.match2(var1, var2, var3, var4);
         break;
      case 7:
         if(var2 instanceof Array) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }
         break;
      case 8:
         if(var2 instanceof Array) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }
      }

      return var5;
   }

   public int match3(ModuleMethod var1, Object var2, Object var3, Object var4, CallContext var5) {
      if(var1.selector == 9) {
         if(!(var2 instanceof Array)) {
            return -786431;
         } else {
            var5.value1 = var2;
            if(!(var3 instanceof Array)) {
               return -786430;
            } else {
               var5.value2 = var3;
               if(!(var4 instanceof Procedure)) {
                  return -786429;
               } else {
                  var5.value3 = var4;
                  var5.proc = var1;
                  var5.pc = 3;
                  return 0;
               }
            }
         }
      } else {
         return super.match3(var1, var2, var3, var4, var5);
      }
   }

   public int matchN(ModuleMethod var1, Object[] var2, CallContext var3) {
      switch(var1.selector) {
      case 2:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 3:
      case 4:
      default:
         return super.matchN(var1, var2, var3);
      case 5:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      }
   }

   public final void run(CallContext var1) {
      Consumer var2 = var1.consumer;
   }
}
