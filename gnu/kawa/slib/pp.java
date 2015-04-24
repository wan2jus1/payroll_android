package gnu.kawa.slib;

import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.slib.genwrite;
import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.SimpleSymbol;
import gnu.math.IntNum;
import kawa.lib.ports;

public class pp extends ModuleBody {

   public static final pp $instance = new pp();
   static final IntNum Lit0 = IntNum.make(79);
   static final SimpleSymbol Lit1 = (SimpleSymbol)(new SimpleSymbol("pretty-print")).readResolve();
   public static final ModuleMethod pretty$Mnprint = new ModuleMethod($instance, 2, Lit1, 8193);


   static {
      $instance.run();
   }

   public pp() {
      ModuleInfo.register(this);
   }

   public static Object prettyPrint(Object var0) {
      return prettyPrint(var0, ports.current$Mnoutput$Mnport.apply0());
   }

   public static Object prettyPrint(Object var0, Object var1) {
      pp.frame var2 = new pp.frame();
      var2.port = var1;
      return genwrite.genericWrite(var0, Boolean.FALSE, Lit0, var2.lambda$Fn1);
   }

   public Object apply1(ModuleMethod var1, Object var2) {
      return var1.selector == 2?prettyPrint(var2):super.apply1(var1, var2);
   }

   public Object apply2(ModuleMethod var1, Object var2, Object var3) {
      return var1.selector == 2?prettyPrint(var2, var3):super.apply2(var1, var2, var3);
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

   public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
      if(var1.selector == 2) {
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      } else {
         return super.match2(var1, var2, var3, var4);
      }
   }

   public final void run(CallContext var1) {
      Consumer var2 = var1.consumer;
   }

   public class frame extends ModuleBody {

      final ModuleMethod lambda$Fn1;
      Object port;


      public frame() {
         ModuleMethod var1 = new ModuleMethod(this, 1, (Object)null, 4097);
         var1.setProperty("source-location", "pp.scm:9");
         this.lambda$Fn1 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 1?this.lambda1(var2):super.apply1(var1, var2);
      }

      Boolean lambda1(Object var1) {
         ports.display(var1, this.port);
         return Boolean.TRUE;
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         if(var1.selector == 1) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return super.match1(var1, var2, var3);
         }
      }
   }
}
