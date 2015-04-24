package kawa.lib;

import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.Future;
import gnu.mapping.Procedure;
import gnu.mapping.RunnableClosure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.Quantity;
import kawa.lang.Macro;
import kawa.lang.SyntaxPattern;
import kawa.lang.SyntaxRule;
import kawa.lang.SyntaxRules;
import kawa.standard.sleep;

public class thread extends ModuleBody {

   public static final ModuleMethod $Prvt$$Pcmake$Mnfuture;
   public static final thread $instance;
   static final SimpleSymbol Lit0;
   static final SimpleSymbol Lit1;
   static final SyntaxRules Lit2;
   static final SimpleSymbol Lit3 = (SimpleSymbol)(new SimpleSymbol("%make-future")).readResolve();
   static final SimpleSymbol Lit4 = (SimpleSymbol)(new SimpleSymbol("runnable")).readResolve();
   public static final Macro future;
   public static final ModuleMethod runnable;
   public static final ModuleMethod sleep;


   public static Future $PcMakeFuture(Procedure var0) {
      Future var1 = new Future(var0);
      var1.start();
      return var1;
   }

   static {
      SimpleSymbol var0 = (SimpleSymbol)(new SimpleSymbol("future")).readResolve();
      Lit1 = var0;
      SyntaxRule var1 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\b", new Object[0], 1), "\u0001", "\u0011\u0018\u0004\b\u0011\u0018\f\t\u0010\b\u0003", new Object[]{Lit3, (SimpleSymbol)(new SimpleSymbol("lambda")).readResolve()}, 0);
      Lit2 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var1}, 1);
      Lit0 = (SimpleSymbol)(new SimpleSymbol("sleep")).readResolve();
      $instance = new thread();
      thread var2 = $instance;
      sleep = new ModuleMethod(var2, 1, Lit0, 4097);
      future = Macro.make(Lit1, Lit2, $instance);
      $Prvt$$Pcmake$Mnfuture = new ModuleMethod(var2, 2, Lit3, 4097);
      runnable = new ModuleMethod(var2, 3, Lit4, 4097);
      $instance.run();
   }

   public thread() {
      ModuleInfo.register(this);
   }

   public static RunnableClosure runnable(Procedure var0) {
      return new RunnableClosure(var0);
   }

   public static void sleep(Quantity var0) {
      sleep.sleep(var0);
   }

   public Object apply1(ModuleMethod var1, Object var2) {
      Procedure var6;
      switch(var1.selector) {
      case 1:
         Quantity var7;
         try {
            var7 = (Quantity)var2;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "sleep", 1, var2);
         }

         sleep(var7);
         return Values.empty;
      case 2:
         try {
            var6 = (Procedure)var2;
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "%make-future", 1, var2);
         }

         return $PcMakeFuture(var6);
      case 3:
         try {
            var6 = (Procedure)var2;
         } catch (ClassCastException var3) {
            throw new WrongType(var3, "runnable", 1, var2);
         }

         return runnable(var6);
      default:
         return super.apply1(var1, var2);
      }
   }

   public int match1(ModuleMethod var1, Object var2, CallContext var3) {
      int var4 = -786431;
      switch(var1.selector) {
      case 1:
         if(var2 instanceof Quantity) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }
         break;
      case 2:
         if(var2 instanceof Procedure) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }
         break;
      case 3:
         if(var2 instanceof Procedure) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }
         break;
      default:
         var4 = super.match1(var1, var2, var3);
      }

      return var4;
   }

   public final void run(CallContext var1) {
      Consumer var2 = var1.consumer;
   }
}
