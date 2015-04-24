package gnu.expr;

import gnu.expr.Language;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleExp;
import gnu.lists.AbstractFormat;
import gnu.lists.Consumer;
import gnu.lists.VoidConsumer;
import gnu.mapping.CallContext;
import gnu.mapping.Environment;
import gnu.mapping.OutPort;
import java.io.Writer;
import kawa.Shell;

public class CompiledModule {

   Object cookie;
   Language language;
   ModuleExp mexp;


   public CompiledModule(ModuleExp var1, Object var2, Language var3) {
      this.mexp = var1;
      this.cookie = var2;
      this.language = var3;
   }

   public static CompiledModule make(Class var0, Language var1) {
      return new CompiledModule((ModuleExp)null, var0, var1);
   }

   public void evalModule(Environment var1, CallContext var2) throws Throwable {
      Language var3 = Language.setSaveCurrent(this.language);
      Environment var4 = Environment.setSaveCurrent(var1);

      try {
         ModuleExp.evalModule2(var1, var2, this.language, this.mexp, this.cookie);
      } finally {
         Language.restoreCurrent(var3);
         Environment.restoreCurrent(var4);
      }

   }

   public void evalModule(Environment var1, OutPort var2) throws Throwable {
      CallContext var4 = CallContext.getInstance();
      Consumer var5 = var4.consumer;
      boolean var7 = ModuleBody.getMainPrintValues();
      AbstractFormat var6 = var2.objectFormat;
      Object var3;
      if(var7) {
         var3 = Shell.getOutputConsumer(var2);
      } else {
         var3 = new VoidConsumer();
      }

      var4.consumer = (Consumer)var3;

      try {
         this.evalModule(var1, (CallContext)var4);
      } finally {
         if(var4.consumer instanceof Writer) {
            ((Writer)var4.consumer).flush();
         }

         var4.consumer = var5;
         var2.objectFormat = var6;
      }

   }

   public Object evalToResultValue(Environment var1, CallContext var2) throws Throwable {
      int var3 = var2.startFromContext();

      try {
         this.evalModule(var1, (CallContext)var2);
         Object var5 = var2.getFromContext(var3);
         return var5;
      } catch (Throwable var4) {
         var2.cleanupFromContext(var3);
         throw var4;
      }
   }
}
