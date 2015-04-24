package kawa.lib;

import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.expr.PrimProcedure;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.PairWithPosition;
import gnu.mapping.CallContext;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.WrongType;
import kawa.lang.Macro;
import kawa.lang.SyntaxPattern;
import kawa.lang.SyntaxRule;
import kawa.lang.SyntaxRules;

public class trace extends ModuleBody {

   public static final Macro $Pcdo$Mntrace;
   public static final trace $instance;
   static final SimpleSymbol Lit0;
   static final SyntaxRules Lit1;
   static final SimpleSymbol Lit2;
   static final SyntaxRules Lit3;
   static final SimpleSymbol Lit4;
   static final SyntaxRules Lit5;
   static final SimpleSymbol Lit6 = (SimpleSymbol)(new SimpleSymbol("disassemble")).readResolve();
   static final SimpleSymbol Lit7 = (SimpleSymbol)(new SimpleSymbol("begin")).readResolve();
   public static final ModuleMethod disassemble;
   public static final Macro trace;
   public static final Macro untrace;


   static {
      SimpleSymbol var0 = (SimpleSymbol)(new SimpleSymbol("untrace")).readResolve();
      Lit4 = var0;
      SyntaxPattern var1 = new SyntaxPattern("\f\u0018\r\u0007\u0000\b\b", new Object[0], 1);
      SimpleSymbol var2 = Lit7;
      SimpleSymbol var3 = (SimpleSymbol)(new SimpleSymbol("%do-trace")).readResolve();
      Lit0 = var3;
      SyntaxRule var4 = new SyntaxRule(var1, "\u0003", "\u0011\u0018\u0004\b\u0005\u0011\u0018\f\t\u0003\u0018\u0014", new Object[]{var2, var3, PairWithPosition.make(Boolean.FALSE, LList.Empty, "trace.scm", 77851)}, 1);
      Lit5 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var4}, 1);
      var0 = (SimpleSymbol)(new SimpleSymbol("trace")).readResolve();
      Lit2 = var0;
      var4 = new SyntaxRule(new SyntaxPattern("\f\u0018\r\u0007\u0000\b\b", new Object[0], 1), "\u0003", "\u0011\u0018\u0004\b\u0005\u0011\u0018\f\t\u0003\u0018\u0014", new Object[]{Lit7, Lit0, PairWithPosition.make(Boolean.TRUE, LList.Empty, "trace.scm", '\ue01b')}, 1);
      Lit3 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var4}, 1);
      var0 = Lit0;
      var4 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\b", new Object[0], 2), "\u0001\u0001", "\u0011\u0018\u0004\t\u0003\b\u0011\u0018\f\u0011\u0018\u0014\u0011\u0018\u001c\t\u0003\b\u000b", new Object[]{(SimpleSymbol)(new SimpleSymbol("set!")).readResolve(), (SimpleSymbol)(new SimpleSymbol("invoke-static")).readResolve(), (SimpleSymbol)(new SimpleSymbol("<kawa.standard.TracedProcedure>")).readResolve(), PairWithPosition.make((SimpleSymbol)(new SimpleSymbol("quote")).readResolve(), PairWithPosition.make((SimpleSymbol)(new SimpleSymbol("doTrace")).readResolve(), LList.Empty, "trace.scm", '耦'), "trace.scm", '耦')}, 0);
      Lit1 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var4}, 2);
      $instance = new trace();
      $Pcdo$Mntrace = Macro.make(Lit0, Lit1, $instance);
      trace = Macro.make(Lit2, Lit3, $instance);
      untrace = Macro.make(Lit4, Lit5, $instance);
      disassemble = new ModuleMethod($instance, 1, Lit6, 4097);
      $instance.run();
   }

   public trace() {
      ModuleInfo.register(this);
   }

   public static Object disassemble(Procedure var0) {
      CallContext var1 = CallContext.getInstance();
      int var2 = var1.startFromContext();
      boolean var4 = false;

      try {
         var4 = true;
         PrimProcedure.disassemble$X(var0, var1);
         var4 = false;
      } finally {
         if(var4) {
            var1.cleanupFromContext(var2);
         }
      }

      return var1.getFromContext(var2);
   }

   public Object apply1(ModuleMethod var1, Object var2) {
      if(var1.selector == 1) {
         Procedure var4;
         try {
            var4 = (Procedure)var2;
         } catch (ClassCastException var3) {
            throw new WrongType(var3, "disassemble", 1, var2);
         }

         return disassemble(var4);
      } else {
         return super.apply1(var1, var2);
      }
   }

   public int match1(ModuleMethod var1, Object var2, CallContext var3) {
      if(var1.selector == 1) {
         if(!(var2 instanceof Procedure)) {
            return -786431;
         } else {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }
      } else {
         return super.match1(var1, var2, var3);
      }
   }

   public final void run(CallContext var1) {
      Consumer var2 = var1.consumer;
   }
}
