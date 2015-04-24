package gnu.expr;

import gnu.expr.ModuleMethod;
import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.Procedure0;
import gnu.mapping.ProcedureN;
import gnu.mapping.Values;
import gnu.mapping.WrappedException;
import gnu.mapping.WrongArguments;

public abstract class ModuleBody extends Procedure0 {

   private static int exitCounter;
   private static boolean mainPrintValues;
   protected boolean runDone;


   public static void exitDecrement() {
      // $FF: Couldn't be decompiled
   }

   public static void exitIncrement() {
      synchronized(ModuleBody.class){}

      try {
         if(exitCounter == 0) {
            ++exitCounter;
         }

         ++exitCounter;
      } finally {
         ;
      }

   }

   public static boolean getMainPrintValues() {
      return mainPrintValues;
   }

   public static void runCleanup(CallContext var0, Throwable var1, Consumer var2) {
      Throwable var3 = var1;
      if(var1 == null) {
         label25: {
            try {
               var0.runUntilDone();
            } catch (Throwable var4) {
               var3 = var4;
               break label25;
            }

            var3 = var1;
         }
      }

      var0.consumer = var2;
      if(var3 != null) {
         if(var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else if(var3 instanceof Error) {
            throw (Error)var3;
         } else {
            throw new WrappedException(var3);
         }
      }
   }

   public static void setMainPrintValues(boolean var0) {
      mainPrintValues = var0;
   }

   public void apply(CallContext var1) throws Throwable {
      if(var1.pc == 0) {
         this.run((CallContext)var1);
      }

   }

   public Object apply0() throws Throwable {
      CallContext var1 = CallContext.getInstance();
      this.match0(var1);
      return var1.runUntilValue();
   }

   public Object apply0(ModuleMethod var1) throws Throwable {
      return this.applyN(var1, Values.noArgs);
   }

   public Object apply1(ModuleMethod var1, Object var2) throws Throwable {
      return this.applyN(var1, new Object[]{var2});
   }

   public Object apply2(ModuleMethod var1, Object var2, Object var3) throws Throwable {
      return this.applyN(var1, new Object[]{var2, var3});
   }

   public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) throws Throwable {
      return this.applyN(var1, new Object[]{var2, var3, var4});
   }

   public Object apply4(ModuleMethod var1, Object var2, Object var3, Object var4, Object var5) throws Throwable {
      return this.applyN(var1, new Object[]{var2, var3, var4, var5});
   }

   public Object applyN(ModuleMethod var1, Object[] var2) throws Throwable {
      int var3 = var2.length;
      int var4 = var1.numArgs();
      if(var3 >= (var4 & 4095) && (var4 < 0 || var3 <= var4 >> 12)) {
         switch(var3) {
         case 0:
            return this.apply0(var1);
         case 1:
            return this.apply1(var1, var2[0]);
         case 2:
            return this.apply2(var1, var2[0], var2[1]);
         case 3:
            return this.apply3(var1, var2[0], var2[1], var2[2]);
         case 4:
            return this.apply4(var1, var2[0], var2[1], var2[2], var2[3]);
         }
      }

      throw new WrongArguments(var1, var3);
   }

   public int match0(ModuleMethod var1, CallContext var2) {
      int var3 = var1.numArgs();
      int var4 = var3 & 4095;
      if(var4 > 0) {
         return -983040 | var4;
      } else if(var3 < 0) {
         return this.matchN(var1, ProcedureN.noArgs, var2);
      } else {
         var2.count = 0;
         var2.where = 0;
         var2.next = 0;
         var2.proc = var1;
         return 0;
      }
   }

   public int match1(ModuleMethod var1, Object var2, CallContext var3) {
      int var4 = var1.numArgs();
      int var5 = var4 & 4095;
      if(var5 > 1) {
         return -983040 | var5;
      } else if(var4 >= 0) {
         var4 >>= 12;
         if(var4 < 1) {
            return -917504 | var4;
         } else {
            var3.value1 = var2;
            var3.count = 1;
            var3.where = 1;
            var3.next = 0;
            var3.proc = var1;
            return 0;
         }
      } else {
         return this.matchN(var1, new Object[]{var2}, var3);
      }
   }

   public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
      int var5 = var1.numArgs();
      int var6 = var5 & 4095;
      if(var6 > 2) {
         return -983040 | var6;
      } else if(var5 >= 0) {
         var5 >>= 12;
         if(var5 < 2) {
            return -917504 | var5;
         } else {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.count = 2;
            var4.where = 33;
            var4.next = 0;
            var4.proc = var1;
            return 0;
         }
      } else {
         return this.matchN(var1, new Object[]{var2, var3}, var4);
      }
   }

   public int match3(ModuleMethod var1, Object var2, Object var3, Object var4, CallContext var5) {
      int var6 = var1.numArgs();
      int var7 = var6 & 4095;
      if(var7 > 3) {
         return -983040 | var7;
      } else if(var6 >= 0) {
         var6 >>= 12;
         if(var6 < 3) {
            return -917504 | var6;
         } else {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.count = 3;
            var5.where = 801;
            var5.next = 0;
            var5.proc = var1;
            return 0;
         }
      } else {
         return this.matchN(var1, new Object[]{var2, var3, var4}, var5);
      }
   }

   public int match4(ModuleMethod var1, Object var2, Object var3, Object var4, Object var5, CallContext var6) {
      int var7 = var1.numArgs();
      int var8 = var7 & 4095;
      if(var8 > 4) {
         return -983040 | var8;
      } else if(var7 >= 0) {
         var7 >>= 12;
         if(var7 < 4) {
            return -917504 | var7;
         } else {
            var6.value1 = var2;
            var6.value2 = var3;
            var6.value3 = var4;
            var6.value4 = var5;
            var6.count = 4;
            var6.where = 17185;
            var6.next = 0;
            var6.proc = var1;
            return 0;
         }
      } else {
         return this.matchN(var1, new Object[]{var2, var3, var4, var5}, var6);
      }
   }

   public int matchN(ModuleMethod var1, Object[] var2, CallContext var3) {
      int var4 = var1.numArgs();
      int var5 = var4 & 4095;
      if(var2.length < var5) {
         return -983040 | var5;
      } else {
         if(var4 >= 0) {
            switch(var2.length) {
            case 0:
               return this.match0(var1, var3);
            case 1:
               return this.match1(var1, var2[0], var3);
            case 2:
               return this.match2(var1, var2[0], var2[1], var3);
            case 3:
               return this.match3(var1, var2[0], var2[1], var2[2], var3);
            case 4:
               return this.match4(var1, var2[0], var2[1], var2[2], var2[3], var3);
            default:
               var4 >>= 12;
               if(var2.length > var4) {
                  return -917504 | var4;
               }
            }
         }

         var3.values = var2;
         var3.count = var2.length;
         var3.where = 0;
         var3.next = 0;
         var3.proc = var1;
         return 0;
      }
   }

   public void run() {
      // $FF: Couldn't be decompiled
   }

   public void run(Consumer var1) {
      CallContext var2 = CallContext.getInstance();
      Consumer var3 = var2.consumer;
      var2.consumer = var1;

      Throwable var5;
      label13: {
         try {
            this.run((CallContext)var2);
         } catch (Throwable var4) {
            var5 = var4;
            break label13;
         }

         var5 = null;
      }

      runCleanup(var2, var5, var3);
   }

   public void run(CallContext var1) throws Throwable {
   }

   public final void runAsMain() {
      // $FF: Couldn't be decompiled
   }
}
