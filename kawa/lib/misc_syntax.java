package kawa.lib;

import gnu.expr.Compilation;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.GetModuleClass;
import gnu.kawa.reflect.StaticFieldLocation;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.CallContext;
import gnu.mapping.InPort;
import gnu.mapping.Location;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import gnu.text.Path;
import kawa.lang.Macro;
import kawa.lang.SyntaxPattern;
import kawa.lang.SyntaxRule;
import kawa.lang.SyntaxRules;
import kawa.lang.SyntaxTemplate;
import kawa.lang.TemplateScope;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.lib.ports;
import kawa.lib.prim_syntax;
import kawa.lib.std_syntax;
import kawa.lib.strings;
import kawa.standard.syntax_case;

public class misc_syntax extends ModuleBody {

   public static final Location $Prvt$define$Mnconstant;
   public static final misc_syntax $instance;
   static final SimpleSymbol Lit0;
   static final SyntaxPattern Lit1;
   static final SimpleSymbol Lit10;
   static final SyntaxRules Lit11;
   static final SimpleSymbol Lit12 = (SimpleSymbol)(new SimpleSymbol("include")).readResolve();
   static final SyntaxPattern Lit13 = new SyntaxPattern("\f\u0007\f\u000f\b", new Object[0], 2);
   static final SyntaxTemplate Lit14 = new SyntaxTemplate("\u0001\u0001", "\u000b", new Object[0], 0);
   static final SyntaxTemplate Lit15 = new SyntaxTemplate("\u0001\u0001", "\u0003", new Object[0], 0);
   static final SyntaxPattern Lit16 = new SyntaxPattern("\r\u0017\u0010\b\b", new Object[0], 3);
   static final SyntaxTemplate Lit17 = new SyntaxTemplate("\u0001\u0001\u0003", "\u0011\u0018\u0004\b\u0015\u0013", new Object[]{Lit25}, 1);
   static final SimpleSymbol Lit18 = (SimpleSymbol)(new SimpleSymbol("include-relative")).readResolve();
   static final SyntaxPattern Lit19 = new SyntaxPattern("\f\u0007\f\u000f\b", new Object[0], 2);
   static final SyntaxTemplate Lit2;
   static final SyntaxTemplate Lit20 = new SyntaxTemplate("\u0001\u0001", "\b\u000b", new Object[0], 0);
   static final SyntaxTemplate Lit21 = new SyntaxTemplate("\u0001\u0001", "\u000b", new Object[0], 0);
   static final SyntaxTemplate Lit22 = new SyntaxTemplate("\u0001\u0001", "\u000b", new Object[0], 0);
   static final SimpleSymbol Lit23 = (SimpleSymbol)(new SimpleSymbol("$lookup$")).readResolve();
   static final SimpleSymbol Lit24 = (SimpleSymbol)(new SimpleSymbol("quasiquote")).readResolve();
   static final SimpleSymbol Lit25 = (SimpleSymbol)(new SimpleSymbol("begin")).readResolve();
   static final SimpleSymbol Lit26 = (SimpleSymbol)(new SimpleSymbol("srfi-64")).readResolve();
   static final SimpleSymbol Lit27 = (SimpleSymbol)(new SimpleSymbol("cond-expand")).readResolve();
   static final SimpleSymbol Lit28 = (SimpleSymbol)(new SimpleSymbol("else")).readResolve();
   static final SimpleSymbol Lit29 = (SimpleSymbol)(new SimpleSymbol("require")).readResolve();
   static final SyntaxTemplate Lit3;
   static final SimpleSymbol Lit30 = (SimpleSymbol)(new SimpleSymbol("quote")).readResolve();
   static final SimpleSymbol Lit31 = (SimpleSymbol)(new SimpleSymbol("%test-begin")).readResolve();
   static final SyntaxTemplate Lit4;
   static final SyntaxPattern Lit5;
   static final SimpleSymbol Lit6;
   static final SyntaxRules Lit7;
   static final SimpleSymbol Lit8;
   static final SyntaxPattern Lit9;
   public static final Macro include;
   public static final Macro include$Mnrelative;
   public static final Macro module$Mnuri;
   public static final Macro provide;
   public static final Macro resource$Mnurl;
   public static final Macro test$Mnbegin;


   static {
      SimpleSymbol var0 = (SimpleSymbol)(new SimpleSymbol("resource-url")).readResolve();
      Lit10 = var0;
      SyntaxPattern var1 = new SyntaxPattern("\f\u0018\f\u0007\b", new Object[0], 1);
      PairWithPosition var2 = PairWithPosition.make(Lit23, Pair.make((SimpleSymbol)(new SimpleSymbol("gnu.text.URLPath")).readResolve(), Pair.make(Pair.make(Lit24, Pair.make((SimpleSymbol)(new SimpleSymbol("valueOf")).readResolve(), LList.Empty)), LList.Empty)), "misc_syntax.scm", 155655);
      SimpleSymbol var3 = Lit23;
      SimpleSymbol var4 = Lit23;
      SimpleSymbol var5 = (SimpleSymbol)(new SimpleSymbol("module-uri")).readResolve();
      Lit8 = var5;
      SyntaxRule var7 = new SyntaxRule(var1, "\u0001", "\u0011\u0018\u0004\b\b\u0011\u0018\f\u0099\b\u0011\u0018\fa\b\u0011\u0018\f)\u0011\u0018\u0014\b\u0003\u0018\u001c\u0018$\u0018,", new Object[]{var2, var3, PairWithPosition.make(var4, Pair.make(PairWithPosition.make(var5, LList.Empty, "misc_syntax.scm", 159755), Pair.make(Pair.make(Lit24, Pair.make((SimpleSymbol)(new SimpleSymbol("resolve")).readResolve(), LList.Empty)), LList.Empty)), "misc_syntax.scm", 159755), Pair.make(Pair.make(Lit24, Pair.make((SimpleSymbol)(new SimpleSymbol("toURL")).readResolve(), LList.Empty)), LList.Empty), Pair.make(Pair.make(Lit24, Pair.make((SimpleSymbol)(new SimpleSymbol("openConnection")).readResolve(), LList.Empty)), LList.Empty), Pair.make(Pair.make(Lit24, Pair.make((SimpleSymbol)(new SimpleSymbol("getURL")).readResolve(), LList.Empty)), LList.Empty)}, 0);
      Lit11 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var7}, 1);
      Lit9 = new SyntaxPattern("\f\u0007\b", new Object[0], 1);
      var0 = (SimpleSymbol)(new SimpleSymbol("test-begin")).readResolve();
      Lit6 = var0;
      var7 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\b", new Object[0], 1), "\u0001", "\u0011\u0018\u0004\u0011\u0018\f\b\u0011\u0018\u0014\t\u0003\u0018\u001c", new Object[]{Lit25, PairWithPosition.make(Lit27, PairWithPosition.make(PairWithPosition.make(Lit26, PairWithPosition.make(Values.empty, LList.Empty, "misc_syntax.scm", 86046), "misc_syntax.scm", 86037), PairWithPosition.make(PairWithPosition.make(Lit28, PairWithPosition.make(PairWithPosition.make(Lit29, PairWithPosition.make(PairWithPosition.make(Lit30, PairWithPosition.make(Lit26, LList.Empty, "misc_syntax.scm", 86070), "misc_syntax.scm", 86070), LList.Empty, "misc_syntax.scm", 86069), "misc_syntax.scm", 86060), LList.Empty, "misc_syntax.scm", 86060), "misc_syntax.scm", 86054), LList.Empty, "misc_syntax.scm", 86054), "misc_syntax.scm", 86037), "misc_syntax.scm", 86024), Lit31, PairWithPosition.make(Boolean.FALSE, LList.Empty, "misc_syntax.scm", 90144)}, 0);
      SyntaxRule var9 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\b", new Object[0], 2), "\u0001\u0001", "\u0011\u0018\u0004\u0011\u0018\f\b\u0011\u0018\u0014\t\u0003\b\u000b", new Object[]{Lit25, PairWithPosition.make(Lit27, PairWithPosition.make(PairWithPosition.make(Lit26, PairWithPosition.make(Values.empty, LList.Empty, "misc_syntax.scm", 102430), "misc_syntax.scm", 102421), PairWithPosition.make(PairWithPosition.make(Lit28, PairWithPosition.make(PairWithPosition.make(Lit29, PairWithPosition.make(PairWithPosition.make(Lit30, PairWithPosition.make(Lit26, LList.Empty, "misc_syntax.scm", 102454), "misc_syntax.scm", 102454), LList.Empty, "misc_syntax.scm", 102453), "misc_syntax.scm", 102444), LList.Empty, "misc_syntax.scm", 102444), "misc_syntax.scm", 102438), LList.Empty, "misc_syntax.scm", 102438), "misc_syntax.scm", 102421), "misc_syntax.scm", 102408), Lit31}, 0);
      Lit7 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var7, var9}, 2);
      Lit5 = new SyntaxPattern("\f\u0007\u000b", new Object[0], 2);
      Lit4 = new SyntaxTemplate("\u0001\u0001\u0001", "\u0018\u0004", new Object[]{PairWithPosition.make((SimpleSymbol)(new SimpleSymbol("::")).readResolve(), PairWithPosition.make((SimpleSymbol)(new SimpleSymbol("<int>")).readResolve(), PairWithPosition.make(IntNum.make(123), LList.Empty, "misc_syntax.scm", '퀖'), "misc_syntax.scm", '퀐'), "misc_syntax.scm", '퀌')}, 0);
      Lit3 = new SyntaxTemplate("\u0001\u0001\u0001", "\u0013", new Object[0], 0);
      Lit2 = new SyntaxTemplate("\u0001\u0001\u0001", "\u0018\u0004", new Object[]{(SimpleSymbol)(new SimpleSymbol("define-constant")).readResolve()}, 0);
      Lit1 = new SyntaxPattern("\f\u0007,\f\u000f\f\u0017\b\b", new Object[0], 3);
      Lit0 = (SimpleSymbol)(new SimpleSymbol("provide")).readResolve();
      $instance = new misc_syntax();
      $Prvt$define$Mnconstant = StaticFieldLocation.make("kawa.lib.prim_syntax", "define$Mnconstant");
      SimpleSymbol var8 = Lit0;
      misc_syntax var6 = $instance;
      provide = Macro.make(var8, new ModuleMethod(var6, 1, (Object)null, 4097), $instance);
      test$Mnbegin = Macro.make(Lit6, Lit7, $instance);
      var8 = Lit8;
      ModuleMethod var10 = new ModuleMethod(var6, 2, (Object)null, 4097);
      var10.setProperty("source-location", "misc_syntax.scm:29");
      module$Mnuri = Macro.make(var8, var10, $instance);
      resource$Mnurl = Macro.make(Lit10, Lit11, $instance);
      var8 = Lit12;
      var10 = new ModuleMethod(var6, 3, (Object)null, 4097);
      var10.setProperty("source-location", "misc_syntax.scm:54");
      include = Macro.make(var8, var10, $instance);
      include$Mnrelative = Macro.make(Lit18, new ModuleMethod(var6, 4, (Object)null, 4097), $instance);
      $instance.run();
   }

   public misc_syntax() {
      ModuleInfo.register(this);
   }

   static Object lambda1(Object var0) {
      Object[] var1 = SyntaxPattern.allocVars(3, (Object[])null);
      if(Lit1.match(var0, var1, 0)) {
         TemplateScope var2 = TemplateScope.make();
         Object var6 = Lit2.execute(var1, var2);
         TemplateScope var3 = TemplateScope.make();
         Object var7 = std_syntax.syntaxObject$To$Datum(Lit3.execute(var1, var3));

         Symbol var4;
         try {
            var4 = (Symbol)var7;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "symbol->string", 1, var7);
         }

         var0 = std_syntax.datum$To$SyntaxObject(var0, misc.string$To$Symbol(strings.stringAppend(new Object[]{"%provide%", misc.symbol$To$String(var4)})));
         var3 = TemplateScope.make();
         return lists.cons(var6, lists.cons(var0, Lit4.execute(var1, var3)));
      } else if(Lit5.match(var0, var1, 0)) {
         if("provide requires a quoted feature-name" instanceof Object[]) {
            var1 = (Object[])"provide requires a quoted feature-name";
         } else {
            var1 = new Object[]{"provide requires a quoted feature-name"};
         }

         return prim_syntax.syntaxError(var0, var1);
      } else {
         return syntax_case.error("syntax-case", var0);
      }
   }

   static Object lambda2(Object var0) {
      Object[] var1 = SyntaxPattern.allocVars(1, (Object[])null);
      return Lit9.match(var0, var1, 0)?GetModuleClass.getModuleClassURI(Compilation.getCurrent()):syntax_case.error("syntax-case", var0);
   }

   static Object lambda3(Object var0) {
      Object[] var1 = SyntaxPattern.allocVars(2, (Object[])null);
      if(Lit13.match(var0, var1, 0)) {
         TemplateScope var5 = TemplateScope.make();
         var0 = std_syntax.syntaxObject$To$Datum(Lit14.execute(var1, var5));
         TemplateScope var2 = TemplateScope.make();
         Object var3 = Lit15.execute(var1, var2);
         misc_syntax.frame var6 = new misc_syntax.frame();
         var6.k = var3;

         Path var7;
         try {
            var7 = Path.valueOf(var0);
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "open-input-file", 1, var0);
         }

         var6.p = ports.openInputFile(var7);
         var0 = var6.lambda4f();
         var1 = SyntaxPattern.allocVars(3, var1);
         if(Lit16.match(var0, var1, 0)) {
            var5 = TemplateScope.make();
            return Lit17.execute(var1, var5);
         } else {
            return syntax_case.error("syntax-case", var0);
         }
      } else {
         return syntax_case.error("syntax-case", var0);
      }
   }

   static Object lambda5(Object var0) {
      Object[] var1 = SyntaxPattern.allocVars(2, (Object[])null);
      if(Lit19.match(var0, var1, 0)) {
         TemplateScope var6 = TemplateScope.make();
         var0 = std_syntax.syntaxObject$To$Datum(Lit20.execute(var1, var6));

         PairWithPosition var2;
         try {
            var2 = (PairWithPosition)var0;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "path-pair", -2, var0);
         }

         Path var7 = Path.valueOf(var2.getFileName());
         String var8 = var2.getCar().toString();
         TemplateScope var3 = TemplateScope.make();
         Object var9 = std_syntax.datum$To$SyntaxObject(Lit21.execute(var1, var3), Lit12);
         TemplateScope var4 = TemplateScope.make();
         return LList.list2(var9, std_syntax.datum$To$SyntaxObject(Lit22.execute(var1, var4), var7.resolve((String)var8).toString()));
      } else {
         return syntax_case.error("syntax-case", var0);
      }
   }

   public Object apply1(ModuleMethod var1, Object var2) {
      switch(var1.selector) {
      case 1:
         return lambda1(var2);
      case 2:
         return lambda2(var2);
      case 3:
         return lambda3(var2);
      case 4:
         return lambda5(var2);
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
      case 3:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 4:
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

   public class frame extends ModuleBody {

      Object k;
      InPort p;


      public Object lambda4f() {
         Object var1 = ports.read(this.p);
         if(ports.isEofObject(var1)) {
            ports.closeInputPort(this.p);
            return LList.Empty;
         } else {
            return new Pair(std_syntax.datum$To$SyntaxObject(this.k, var1), this.lambda4f());
         }
      }
   }
}
