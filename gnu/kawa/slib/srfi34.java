package gnu.kawa.slib;

import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.PairWithPosition;
import gnu.mapping.CallContext;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Values;
import kawa.lang.Macro;
import kawa.lang.SyntaxPattern;
import kawa.lang.SyntaxRule;
import kawa.lang.SyntaxRules;
import kawa.standard.Scheme;

public class srfi34 extends ModuleBody {

   public static final Class $Prvt$$Lsraise$Mnobject$Mnexception$Gr;
   public static final Macro $Prvt$guard$Mnaux;
   public static final srfi34 $instance;
   static final SimpleSymbol Lit0;
   static final SimpleSymbol Lit1;
   static final SimpleSymbol Lit10 = (SimpleSymbol)(new SimpleSymbol("if")).readResolve();
   static final SimpleSymbol Lit11 = (SimpleSymbol)(new SimpleSymbol("begin")).readResolve();
   static final SimpleSymbol Lit12 = (SimpleSymbol)(new SimpleSymbol("ex")).readResolve();
   static final SimpleSymbol Lit13 = (SimpleSymbol)(new SimpleSymbol("<raise-object-exception>")).readResolve();
   static final SimpleSymbol Lit2;
   static final SyntaxRules Lit3;
   static final SimpleSymbol Lit4;
   static final SyntaxRules Lit5;
   static final SimpleSymbol Lit6 = (SimpleSymbol)(new SimpleSymbol("else")).readResolve();
   static final SimpleSymbol Lit7 = (SimpleSymbol)(new SimpleSymbol("=>")).readResolve();
   static final SimpleSymbol Lit8 = (SimpleSymbol)(new SimpleSymbol("temp")).readResolve();
   static final SimpleSymbol Lit9 = (SimpleSymbol)(new SimpleSymbol("let")).readResolve();
   public static final Macro guard;
   public static final ModuleMethod raise;
   public static final ModuleMethod with$Mnexception$Mnhandler;


   static {
      SimpleSymbol var0 = (SimpleSymbol)(new SimpleSymbol("guard-aux")).readResolve();
      Lit4 = var0;
      SimpleSymbol var1 = Lit6;
      SimpleSymbol var2 = Lit7;
      SyntaxRule var3 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007L\f\u0002\f\u000f\r\u0017\u0010\b\b\b", new Object[]{Lit6}, 3), "\u0001\u0001\u0003", "\u0011\u0018\u0004\t\u000b\b\u0015\u0013", new Object[]{Lit11}, 1);
      SyntaxRule var4 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007<\f\u000f\f\u0002\f\u0017\b\b", new Object[]{Lit7}, 3), "\u0001\u0001\u0001", "\u0011\u0018\u00041\b\u0011\u0018\f\b\u000b\b\u0011\u0018\u0014\u0011\u0018\f!\t\u0013\u0018\u001c\b\u0003", new Object[]{Lit9, Lit8, Lit10, PairWithPosition.make(Lit8, LList.Empty, "srfi34.scm", 274452)}, 0);
      SyntaxRule var5 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007<\f\u000f\f\u0002\f\u0017\b\f\u001f\r\' \b\b", new Object[]{Lit7}, 5), "\u0001\u0001\u0001\u0001\u0003", "\u0011\u0018\u00041\b\u0011\u0018\f\b\u000b\b\u0011\u0018\u0014\u0011\u0018\f!\t\u0013\u0018\u001c\b\u0011\u0018$\t\u0003\t\u001b\b%#", new Object[]{Lit9, Lit8, Lit10, PairWithPosition.make(Lit8, LList.Empty, "srfi34.scm", 294932), Lit4}, 1);
      SyntaxRule var6 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\u001c\f\u000f\b\b", new Object[0], 2), "\u0001\u0001", "\u000b", new Object[0], 0);
      SyntaxRule var7 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\u001c\f\u000f\b\f\u0017\r\u001f\u0018\b\b", new Object[0], 4), "\u0001\u0001\u0001\u0003", "\u0011\u0018\u00041\b\u0011\u0018\f\b\u000b\b\u0011\u0018\u0014\u0011\u0018\f\u0011\u0018\f\b\u0011\u0018\u001c\t\u0003\t\u0013\b\u001d\u001b", new Object[]{Lit9, Lit8, Lit10, Lit4}, 1);
      SyntaxRule var8 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007L\f\u000f\f\u0017\r\u001f\u0018\b\b\b", new Object[0], 4), "\u0001\u0001\u0001\u0003", "\u0011\u0018\u0004\t\u000bA\u0011\u0018\f\t\u0013\b\u001d\u001b\b\u0003", new Object[]{Lit10, Lit11}, 1);
      SyntaxRule var9 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007L\f\u000f\f\u0017\r\u001f\u0018\b\b\f\'\r/(\b\b", new Object[0], 6), "\u0001\u0001\u0001\u0003\u0001\u0003", "\u0011\u0018\u0004\t\u000bA\u0011\u0018\f\t\u0013\b\u001d\u001b\b\u0011\u0018\u0014\t\u0003\t#\b-+", new Object[]{Lit10, Lit11, Lit4}, 1);
      Lit5 = new SyntaxRules(new Object[]{var0, var1, var2}, new SyntaxRule[]{var3, var4, var5, var6, var7, var8, var9}, 6);
      var0 = (SimpleSymbol)(new SimpleSymbol("guard")).readResolve();
      Lit2 = var0;
      SyntaxRule var11 = new SyntaxRule(new SyntaxPattern("\f\u0018\u001c\f\u0007\u000b\u0013", new Object[0], 3), "\u0001\u0000\u0000", "\u0011\u0018\u0004!\u0011\u0018\f\u0012\b\u0011\u0018\u0014\u0011\u0018\u001c\b\u0011\u0018$)\b\t\u0003\u0018,\b\u0011\u00184\u0011\u0018<\n", new Object[]{(SimpleSymbol)(new SimpleSymbol("try-catch")).readResolve(), Lit11, Lit12, (SimpleSymbol)(new SimpleSymbol("<java.lang.Throwable>")).readResolve(), Lit9, PairWithPosition.make(PairWithPosition.make(Lit10, PairWithPosition.make(PairWithPosition.make((SimpleSymbol)(new SimpleSymbol("instance?")).readResolve(), PairWithPosition.make(Lit12, PairWithPosition.make(Lit13, LList.Empty, "srfi34.scm", 110614), "srfi34.scm", 110611), "srfi34.scm", 110600), PairWithPosition.make(PairWithPosition.make((SimpleSymbol)(new SimpleSymbol("field")).readResolve(), PairWithPosition.make(PairWithPosition.make((SimpleSymbol)(new SimpleSymbol("as")).readResolve(), PairWithPosition.make(Lit13, PairWithPosition.make(Lit12, LList.Empty, "srfi34.scm", 114732), "srfi34.scm", 114707), "srfi34.scm", 114703), PairWithPosition.make(PairWithPosition.make((SimpleSymbol)(new SimpleSymbol("quote")).readResolve(), PairWithPosition.make((SimpleSymbol)(new SimpleSymbol("value")).readResolve(), LList.Empty, "srfi34.scm", 114737), "srfi34.scm", 114737), LList.Empty, "srfi34.scm", 114736), "srfi34.scm", 114703), "srfi34.scm", 114696), PairWithPosition.make(Lit12, LList.Empty, "srfi34.scm", 118792), "srfi34.scm", 114696), "srfi34.scm", 110600), "srfi34.scm", 110596), LList.Empty, "srfi34.scm", 110596), Lit4, PairWithPosition.make((SimpleSymbol)(new SimpleSymbol("primitive-throw")).readResolve(), PairWithPosition.make(Lit12, LList.Empty, "srfi34.scm", 122914), "srfi34.scm", 122897)}, 0);
      Lit3 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var11}, 3);
      Lit1 = (SimpleSymbol)(new SimpleSymbol("raise")).readResolve();
      Lit0 = (SimpleSymbol)(new SimpleSymbol("with-exception-handler")).readResolve();
      $instance = new srfi34();
      $Prvt$$Lsraise$Mnobject$Mnexception$Gr = srfi34.Mnexception.class;
      srfi34 var10 = $instance;
      with$Mnexception$Mnhandler = new ModuleMethod(var10, 1, Lit0, 8194);
      raise = new ModuleMethod(var10, 2, Lit1, 4097);
      guard = Macro.make(Lit2, Lit3, $instance);
      $Prvt$guard$Mnaux = Macro.make(Lit4, Lit5, $instance);
      $instance.run();
   }

   public srfi34() {
      ModuleInfo.register(this);
   }

   public static void raise(Object var0) {
      throw (Throwable)(var0.new Mnexception());
   }

   public static Object withExceptionHandler(Object var0, Object var1) {
      try {
         var1 = Scheme.applyToArgs.apply1(var1);
         return var1;
      } catch (srfi34.Mnexception var2) {
         return Scheme.applyToArgs.apply2(var0, var2.value);
      } catch (Throwable var3) {
         return Scheme.applyToArgs.apply2(var0, var3);
      }
   }

   public Object apply1(ModuleMethod var1, Object var2) {
      if(var1.selector == 2) {
         raise(var2);
         return Values.empty;
      } else {
         return super.apply1(var1, var2);
      }
   }

   public Object apply2(ModuleMethod var1, Object var2, Object var3) {
      return var1.selector == 1?withExceptionHandler(var2, var3):super.apply2(var1, var2, var3);
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
      if(var1.selector == 1) {
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

   public class Mnexception extends Throwable {

      public Object value = srfi34.this;


   }
}
