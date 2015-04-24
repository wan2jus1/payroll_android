package gnu.kawa.android;

import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.CallContext;
import gnu.mapping.SimpleSymbol;
import kawa.lang.Macro;
import kawa.lang.Quote;
import kawa.lang.SyntaxPattern;
import kawa.lang.SyntaxTemplate;
import kawa.lang.TemplateScope;
import kawa.lib.lists;
import kawa.standard.syntax_case;

public class defs extends ModuleBody {

   public static final ModuleMethod $Pcprocess$Mnactivity;
   public static final defs $instance = new defs();
   static final SyntaxPattern Lit0 = new SyntaxPattern("<\f\u0002\r\u0007\u0000\b\b\u000b", new Object[]{(SimpleSymbol)(new SimpleSymbol("on-create")).readResolve()}, 2);
   static final SyntaxTemplate Lit1 = new SyntaxTemplate("\u0003\u0000", "\u0011\u0018\u0004\u0011\u0018\f\u0011\u0018\u0014\u0011\u0018\u001c\b\u0005\u0003", new Object[]{PairWithPosition.make(Lit19, PairWithPosition.make(PairWithPosition.make(Lit20, PairWithPosition.make(Lit17, PairWithPosition.make(Lit22, LList.Empty, "defs.scm", 16433), "defs.scm", 16430), "defs.scm", 16410), LList.Empty, "defs.scm", 16410), "defs.scm", 16400), Lit17, Lit23, PairWithPosition.make(Lit24, PairWithPosition.make(Lit18, PairWithPosition.make(PairWithPosition.make(Lit21, LList.Empty, "defs.scm", 20526), PairWithPosition.make(PairWithPosition.make(Lit25, PairWithPosition.make(Lit19, LList.Empty, "defs.scm", 20534), "defs.scm", 20534), PairWithPosition.make(Lit20, LList.Empty, "defs.scm", 20543), "defs.scm", 20533), "defs.scm", 20526), "defs.scm", 20505), "defs.scm", 20489)}, 1);
   static final SimpleSymbol Lit10 = (SimpleSymbol)(new SimpleSymbol("%process-activity")).readResolve();
   static final SimpleSymbol Lit11 = (SimpleSymbol)(new SimpleSymbol("activity")).readResolve();
   static final SyntaxPattern Lit12 = new SyntaxPattern("\f\u0007\f\u000f\u0013", new Object[0], 3);
   static final SyntaxTemplate Lit13 = new SyntaxTemplate("\u0001\u0001\u0000", "\u0011\u0018\u0004\b\u000b", new Object[]{(SimpleSymbol)(new SimpleSymbol("define-simple-class")).readResolve()}, 0);
   static final SyntaxTemplate Lit14 = new SyntaxTemplate("\u0001\u0001\u0000", "\u0018\u0004", new Object[]{PairWithPosition.make(Lit18, LList.Empty, "defs.scm", 86048)}, 0);
   static final SyntaxTemplate Lit15 = new SyntaxTemplate("\u0001\u0001\u0000", "\u0012", new Object[0], 0);
   static final SyntaxTemplate Lit16 = new SyntaxTemplate("\u0001\u0001\u0000", "\u0010", new Object[0], 0);
   static final SimpleSymbol Lit17 = (SimpleSymbol)(new SimpleSymbol("::")).readResolve();
   static final SimpleSymbol Lit18 = (SimpleSymbol)(new SimpleSymbol("android.app.Activity")).readResolve();
   static final SimpleSymbol Lit19 = (SimpleSymbol)(new SimpleSymbol("onCreate")).readResolve();
   static final SyntaxTemplate Lit2 = new SyntaxTemplate("\u0003\u0000", "\n", new Object[0], 0);
   static final SimpleSymbol Lit20 = (SimpleSymbol)(new SimpleSymbol("savedInstanceState")).readResolve();
   static final SimpleSymbol Lit21 = (SimpleSymbol)(new SimpleSymbol("this")).readResolve();
   static final SimpleSymbol Lit22 = (SimpleSymbol)(new SimpleSymbol("android.os.Bundle")).readResolve();
   static final SimpleSymbol Lit23 = (SimpleSymbol)(new SimpleSymbol("void")).readResolve();
   static final SimpleSymbol Lit24 = (SimpleSymbol)(new SimpleSymbol("invoke-special")).readResolve();
   static final SimpleSymbol Lit25 = (SimpleSymbol)(new SimpleSymbol("quote")).readResolve();
   static final SyntaxPattern Lit3 = new SyntaxPattern("T\f\u0002\r\u0007\u0000\b\u0016\f\u000f\b\u0013", new Object[]{(SimpleSymbol)(new SimpleSymbol("on-create-view")).readResolve()}, 3);
   static final SyntaxTemplate Lit4 = new SyntaxTemplate("\u0003\u0001\u0000", "\u0011\u0018\u0004\u0011\u0018\f\u0011\u0018\u0014\u0011\u0018\u001c\u0011\u0005\u0003\b\u0011\u0018$\b\u000b", new Object[]{PairWithPosition.make(Lit19, PairWithPosition.make(PairWithPosition.make(Lit20, PairWithPosition.make(Lit17, PairWithPosition.make(Lit22, LList.Empty, "defs.scm", '週'), "defs.scm", '逮'), "defs.scm", '通'), LList.Empty, "defs.scm", '通'), "defs.scm", '逐'), Lit17, Lit23, PairWithPosition.make(Lit24, PairWithPosition.make(Lit18, PairWithPosition.make(PairWithPosition.make(Lit21, LList.Empty, "defs.scm", 'ꀮ'), PairWithPosition.make(PairWithPosition.make(Lit25, PairWithPosition.make(Lit19, LList.Empty, "defs.scm", 'ꀶ'), "defs.scm", 'ꀶ'), PairWithPosition.make(Lit20, LList.Empty, "defs.scm", 'ꀿ'), "defs.scm", 'ꀵ'), "defs.scm", 'ꀮ'), "defs.scm", 'ꀙ'), "defs.scm", 'ꀉ'), PairWithPosition.make((SimpleSymbol)(new SimpleSymbol("$lookup$")).readResolve(), Pair.make(PairWithPosition.make(Lit21, LList.Empty, "defs.scm", '쀊'), Pair.make(Pair.make((SimpleSymbol)(new SimpleSymbol("quasiquote")).readResolve(), Pair.make((SimpleSymbol)(new SimpleSymbol("setContentView")).readResolve(), LList.Empty)), LList.Empty)), "defs.scm", '쀊')}, 1);
   static final SyntaxTemplate Lit5 = new SyntaxTemplate("\u0003\u0001\u0000", "\u0012", new Object[0], 0);
   static final SyntaxPattern Lit6 = new SyntaxPattern("\f\u0007\u000b", new Object[0], 2);
   static final SyntaxTemplate Lit7 = new SyntaxTemplate("\u0001\u0000", "\u0003", new Object[0], 0);
   static final SyntaxTemplate Lit8 = new SyntaxTemplate("\u0001\u0000", "\n", new Object[0], 0);
   static final SyntaxPattern Lit9 = new SyntaxPattern("\b", new Object[0], 0);
   public static final Macro activity;


   public static Object $PcProcessActivity(Object var0) {
      Object[] var1 = SyntaxPattern.allocVars(3, (Object[])null);
      TemplateScope var2;
      TemplateScope var3;
      if(Lit0.match(var0, var1, 0)) {
         var3 = TemplateScope.make();
         var0 = Lit1.execute(var1, var3);
         var2 = TemplateScope.make();
         return lists.cons(var0, $PcProcessActivity(Lit2.execute(var1, var2)));
      } else if(Lit3.match(var0, var1, 0)) {
         var3 = TemplateScope.make();
         var0 = Lit4.execute(var1, var3);
         var2 = TemplateScope.make();
         return lists.cons(var0, $PcProcessActivity(Lit5.execute(var1, var2)));
      } else if(Lit6.match(var0, var1, 0)) {
         var3 = TemplateScope.make();
         var0 = Lit7.execute(var1, var3);
         var2 = TemplateScope.make();
         return lists.cons(var0, $PcProcessActivity(Lit8.execute(var1, var2)));
      } else {
         return Lit9.match(var0, var1, 0)?LList.Empty:syntax_case.error("syntax-case", var0);
      }
   }

   static {
      defs var0 = $instance;
      $Pcprocess$Mnactivity = new ModuleMethod(var0, 1, Lit10, 4097);
      activity = Macro.make(Lit11, new ModuleMethod(var0, 2, (Object)null, 4097), $instance);
      $instance.run();
   }

   public defs() {
      ModuleInfo.register(this);
   }

   static Object lambda1(Object var0) {
      Object[] var1 = SyntaxPattern.allocVars(3, (Object[])null);
      if(Lit12.match(var0, var1, 0)) {
         TemplateScope var2 = TemplateScope.make();
         return Quote.append$V(new Object[]{Lit13.execute(var1, var2), Pair.make(Lit14.execute(var1, var2), Quote.append$V(new Object[]{$PcProcessActivity(Lit15.execute(var1, var2)), Lit16.execute(var1, var2)}))});
      } else {
         return syntax_case.error("syntax-case", var0);
      }
   }

   public Object apply1(ModuleMethod var1, Object var2) {
      switch(var1.selector) {
      case 1:
         return $PcProcessActivity(var2);
      case 2:
         return lambda1(var2);
      default:
         return super.apply1(var1, var2);
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
      default:
         return super.match1(var1, var2, var3);
      }
   }

   public final void run(CallContext var1) {
      Consumer var2 = var1.consumer;
   }
}
