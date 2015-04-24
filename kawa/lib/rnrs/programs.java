package kawa.lib.rnrs;

import gnu.expr.ApplicationMainSupport;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.mapping.CallContext;
import gnu.mapping.OutPort;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import kawa.lib.lists;
import kawa.lib.numbers;

public class programs extends ModuleBody {

   public static final programs $instance = new programs();
   static final IntNum Lit0 = IntNum.make(0);
   static final SimpleSymbol Lit1 = (SimpleSymbol)(new SimpleSymbol("command-line")).readResolve();
   static final SimpleSymbol Lit2 = (SimpleSymbol)(new SimpleSymbol("exit")).readResolve();
   public static final ModuleMethod command$Mnline;
   public static final ModuleMethod exit;


   static {
      programs var0 = $instance;
      command$Mnline = new ModuleMethod(var0, 1, Lit1, 0);
      exit = new ModuleMethod(var0, 2, Lit2, 4096);
      $instance.run();
   }

   public programs() {
      ModuleInfo.register(this);
   }

   public static LList commandLine() {
      return lists.cons("kawa", LList.makeList(ApplicationMainSupport.commandLineArgArray, 0));
   }

   public static void exit() {
      exit(Lit0);
   }

   public static void exit(Object var0) {
      OutPort.runCleanups();
      int var2;
      if(numbers.isInteger(var0)) {
         try {
            var2 = ((Number)var0).intValue();
         } catch (ClassCastException var3) {
            throw new WrongType(var3, "status", -2, var0);
         }
      } else if(var0 != Boolean.FALSE) {
         var2 = 0;
      } else {
         var2 = -1;
      }

      System.exit(var2);
   }

   public Object apply0(ModuleMethod var1) {
      switch(var1.selector) {
      case 1:
         return commandLine();
      case 2:
         exit();
         return Values.empty;
      default:
         return super.apply0(var1);
      }
   }

   public Object apply1(ModuleMethod var1, Object var2) {
      if(var1.selector == 2) {
         exit(var2);
         return Values.empty;
      } else {
         return super.apply1(var1, var2);
      }
   }

   public int match0(ModuleMethod var1, CallContext var2) {
      switch(var1.selector) {
      case 1:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 2:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      default:
         return super.match0(var1, var2);
      }
   }

   public int match1(ModuleMethod var1, Object var2, CallContext var3) {
      if(var1.selector == 2) {
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      } else {
         return super.match1(var1, var2, var3);
      }
   }

   public final void run(CallContext var1) {
      Consumer var2 = var1.consumer;
   }
}
