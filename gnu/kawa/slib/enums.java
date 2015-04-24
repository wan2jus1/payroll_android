package gnu.kawa.slib;

import gnu.expr.Keyword;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.Apply;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.CallContext;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.WrongType;
import kawa.lang.Macro;
import kawa.lang.Quote;
import kawa.lang.SyntaxPattern;
import kawa.lang.SyntaxTemplate;
import kawa.lang.TemplateScope;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.lib.prim_syntax;
import kawa.lib.std_syntax;
import kawa.lib.strings;
import kawa.standard.Scheme;
import kawa.standard.syntax_case;

public class enums extends ModuleBody {

   public static final Macro $Prvt$$Pcdefine$Mnenum;
   public static final enums $instance;
   static final PairWithPosition Lit0;
   static final PairWithPosition Lit1;
   static final PairWithPosition Lit10;
   static final SimpleSymbol Lit11;
   static final SyntaxPattern Lit12;
   static final SyntaxTemplate Lit13;
   static final SyntaxTemplate Lit14;
   static final SyntaxPattern Lit15;
   static final SyntaxTemplate Lit16;
   static final SyntaxPattern Lit17;
   static final SyntaxPattern Lit18;
   static final SyntaxPattern Lit19;
   static final PairWithPosition Lit2;
   static final SyntaxTemplate Lit20;
   static final SimpleSymbol Lit21 = (SimpleSymbol)(new SimpleSymbol("%define-enum")).readResolve();
   static final SyntaxPattern Lit22 = new SyntaxPattern("\f\u0007\f\u000f\f\u0017,\r\u001f\u0018\b\b\r\' \b\b", new Object[0], 5);
   static final SyntaxTemplate Lit23 = new SyntaxTemplate("\u0001\u0001\u0001\u0003\u0003", "\u000b", new Object[0], 0);
   static final SimpleSymbol Lit24 = (SimpleSymbol)(new SimpleSymbol("[]")).readResolve();
   static final SyntaxTemplate Lit25 = new SyntaxTemplate("\u0001\u0001\u0001\u0003\u0003", "\b\u001d\u001b", new Object[0], 1);
   static final SyntaxTemplate Lit26 = new SyntaxTemplate("\u0001\u0001\u0001\u0003\u0003", "\u0013", new Object[0], 0);
   static final SyntaxTemplate Lit27 = new SyntaxTemplate("\u0001\u0001\u0001\u0003\u0003", "\b%#", new Object[0], 1);
   static final SyntaxTemplate Lit28 = new SyntaxTemplate("\u0001\u0001\u0001\u0003\u0003", "\u0018\u0004", new Object[]{PairWithPosition.make((SimpleSymbol)(new SimpleSymbol("define-simple-class")).readResolve(), LList.Empty, "enums.scm", 262154)}, 0);
   static final SyntaxTemplate Lit29 = new SyntaxTemplate("\u0001\u0001\u0001\u0003\u0003", "\u0018\u0004", new Object[]{PairWithPosition.make(Lit44, LList.Empty, "enums.scm", 266252)}, 0);
   static final PairWithPosition Lit3;
   static final SyntaxTemplate Lit30 = new SyntaxTemplate("\u0001\u0001\u0001\u0003\u0003", "\u0018\u0004", new Object[]{PairWithPosition.make(Lit48, LList.Empty, "enums.scm", 266269)}, 0);
   static final SyntaxTemplate Lit31 = new SyntaxTemplate("\u0001\u0001\u0001\u0003\u0003", "\u0018\u0004", new Object[]{PairWithPosition.make(Lit43, PairWithPosition.make(PairWithPosition.make(Lit52, PairWithPosition.make(Lit53, LList.Empty, "enums.scm", 266284), "enums.scm", 266278), LList.Empty, "enums.scm", 266278), "enums.scm", 266278)}, 0);
   static final SyntaxTemplate Lit32 = new SyntaxTemplate("\u0001\u0001\u0001\u0003\u0003", "\u0018\u0004", new Object[]{PairWithPosition.make(Lit40, PairWithPosition.make(Lit41, PairWithPosition.make(Lit42, PairWithPosition.make(Lit47, LList.Empty, "enums.scm", 282642), "enums.scm", 282640), "enums.scm", 282639), "enums.scm", 282630)}, 0);
   static final SyntaxTemplate Lit33 = new SyntaxTemplate("\u0001\u0001\u0001\u0003\u0003", "\u0018\u0004", new Object[]{PairWithPosition.make(Lit42, LList.Empty, "enums.scm", 282649)}, 0);
   static final SyntaxTemplate Lit34 = new SyntaxTemplate("\u0001\u0001\u0001\u0003\u0003", "\u0018\u0004", new Object[]{PairWithPosition.make(Lit46, LList.Empty, "enums.scm", 286726)}, 0);
   static final SyntaxTemplate Lit35 = new SyntaxTemplate("\u0001\u0001\u0001\u0003\u0003", "\u0018\u0004", new Object[]{PairWithPosition.make(Lit43, PairWithPosition.make(Lit45, LList.Empty, "enums.scm", 286739), "enums.scm", 286739)}, 0);
   static final SyntaxTemplate Lit36 = new SyntaxTemplate("\u0001\u0001\u0001\u0003\u0003", "\u0018\u0004", new Object[]{PairWithPosition.make((SimpleSymbol)(new SimpleSymbol("$lookup$")).readResolve(), Pair.make(Lit44, Pair.make(Pair.make((SimpleSymbol)(new SimpleSymbol("quasiquote")).readResolve(), Pair.make(Lit40, LList.Empty)), LList.Empty)), "enums.scm", 290823)}, 0);
   static final SyntaxTemplate Lit37 = new SyntaxTemplate("\u0001\u0001\u0001\u0003\u0003", "\u0018\u0004", new Object[]{PairWithPosition.make(Lit41, LList.Empty, "enums.scm", 290882)}, 0);
   static final SyntaxTemplate Lit38 = new SyntaxTemplate("\u0001\u0001\u0001\u0003\u0003", "\u0010", new Object[0], 0);
   static final SyntaxTemplate Lit39 = new SyntaxTemplate("\u0001\u0001\u0001\u0003\u0003", "\u0010", new Object[0], 0);
   static final PairWithPosition Lit4;
   static final SimpleSymbol Lit40 = (SimpleSymbol)(new SimpleSymbol("valueOf")).readResolve();
   static final SimpleSymbol Lit41 = (SimpleSymbol)(new SimpleSymbol("s")).readResolve();
   static final SimpleSymbol Lit42 = (SimpleSymbol)(new SimpleSymbol("::")).readResolve();
   static final SimpleSymbol Lit43 = (SimpleSymbol)(new SimpleSymbol("quote")).readResolve();
   static final SimpleSymbol Lit44 = (SimpleSymbol)(new SimpleSymbol("java.lang.Enum")).readResolve();
   static final SimpleSymbol Lit45 = (SimpleSymbol)(new SimpleSymbol("static")).readResolve();
   static final Keyword Lit46 = Keyword.make("allocation");
   static final SimpleSymbol Lit47 = (SimpleSymbol)(new SimpleSymbol("String")).readResolve();
   static final Keyword Lit48 = Keyword.make("access");
   static final SimpleSymbol Lit49 = (SimpleSymbol)(new SimpleSymbol("*init*")).readResolve();
   static final PairWithPosition Lit5;
   static final SimpleSymbol Lit50 = (SimpleSymbol)(new SimpleSymbol("str")).readResolve();
   static final SimpleSymbol Lit51 = (SimpleSymbol)(new SimpleSymbol("num")).readResolve();
   static final SimpleSymbol Lit52 = (SimpleSymbol)(new SimpleSymbol("enum")).readResolve();
   static final SimpleSymbol Lit53 = (SimpleSymbol)(new SimpleSymbol("final")).readResolve();
   static final PairWithPosition Lit6;
   static final PairWithPosition Lit7;
   static final PairWithPosition Lit8;
   static final PairWithPosition Lit9;
   public static final Macro define$Mnenum;


   static {
      SimpleSymbol var0 = (SimpleSymbol)(new SimpleSymbol("define-enum")).readResolve();
      Lit11 = var0;
      Lit20 = new SyntaxTemplate("\u0001\u0001\u0003", "\u0011\u0018\u0004\u0011\u0018\f\t\u000b\t\u0010\b\u0015\u0013", new Object[]{var0, "findkeywords"}, 1);
      Lit19 = new SyntaxPattern("\f\u0007\f\u000f\r\u0017\u0010\b\b", new Object[0], 3);
      Lit18 = new SyntaxPattern("\f\u0007\f\u000f\b", new Object[0], 2);
      Lit17 = new SyntaxPattern("\f\u0007\b", new Object[0], 1);
      Lit16 = new SyntaxTemplate("\u0001\u0001\u0003\u0003", "\u0011\u0018\u0004\t\u000b\u0019\b\u0015\u0013\b\u001d\u001b", new Object[]{Lit21}, 1);
      Lit15 = new SyntaxPattern("\f\u0007\f\u0002\f\u000f,\r\u0017\u0010\b\b\r\u001f\u0018\b\b", new Object[]{"findkeywords"}, 4);
      Lit14 = new SyntaxTemplate("\u0001\u0001\u0003\u0001\u0001\u0003", "\u0011\u0018\u0004\u0011\u0018\f\t\u000b9\t\u001b\t#\b\u0015\u0013\b-+", new Object[]{Lit11, "findkeywords"}, 1);
      Lit13 = new SyntaxTemplate("\u0001\u0001\u0003\u0001\u0001\u0003", "\u001b", new Object[0], 0);
      Lit12 = new SyntaxPattern("\f\u0007\f\u0002\f\u000f,\r\u0017\u0010\b\b\f\u001f\f\'\r/(\b\b", new Object[]{"findkeywords"}, 6);
      Lit10 = PairWithPosition.make(Lit43, PairWithPosition.make(Lit45, LList.Empty, "enums.scm", 127013), "enums.scm", 127013);
      Lit9 = PairWithPosition.make(Lit46, LList.Empty, "enums.scm", 127000);
      Lit8 = PairWithPosition.make(Lit42, LList.Empty, "enums.scm", 126990);
      Lit7 = PairWithPosition.make((SimpleSymbol)(new SimpleSymbol("values")).readResolve(), LList.Empty, "enums.scm", 126981);
      Lit6 = PairWithPosition.make(PairWithPosition.make(Lit49, PairWithPosition.make(PairWithPosition.make(Lit50, PairWithPosition.make(Lit42, PairWithPosition.make(Lit47, LList.Empty, "enums.scm", 90133), "enums.scm", 90130), "enums.scm", 90125), PairWithPosition.make(PairWithPosition.make(Lit51, PairWithPosition.make(Lit42, PairWithPosition.make((SimpleSymbol)(new SimpleSymbol("int")).readResolve(), LList.Empty, "enums.scm", 90149), "enums.scm", 90146), "enums.scm", 90141), LList.Empty, "enums.scm", 90141), "enums.scm", 90125), "enums.scm", 90117), PairWithPosition.make(Lit48, PairWithPosition.make(PairWithPosition.make(Lit43, PairWithPosition.make((SimpleSymbol)(new SimpleSymbol("private")).readResolve(), LList.Empty, "enums.scm", 94222), "enums.scm", 94222), PairWithPosition.make(PairWithPosition.make((SimpleSymbol)(new SimpleSymbol("invoke-special")).readResolve(), PairWithPosition.make(Lit44, PairWithPosition.make(PairWithPosition.make((SimpleSymbol)(new SimpleSymbol("this")).readResolve(), LList.Empty, "enums.scm", 98340), PairWithPosition.make(PairWithPosition.make(Lit43, PairWithPosition.make(Lit49, LList.Empty, "enums.scm", 98348), "enums.scm", 98348), PairWithPosition.make(Lit50, PairWithPosition.make(Lit51, LList.Empty, "enums.scm", 98359), "enums.scm", 98355), "enums.scm", 98347), "enums.scm", 98340), "enums.scm", 98325), "enums.scm", 98309), LList.Empty, "enums.scm", 98309), "enums.scm", 94221), "enums.scm", 94213), "enums.scm", 90116);
      Lit5 = PairWithPosition.make(Keyword.make("init"), LList.Empty, "enums.scm", 73741);
      Lit4 = PairWithPosition.make(Lit43, PairWithPosition.make(PairWithPosition.make(Lit52, PairWithPosition.make(Lit53, LList.Empty, "enums.scm", 69680), "enums.scm", 69674), LList.Empty, "enums.scm", 69674), "enums.scm", 69674);
      Lit3 = PairWithPosition.make(Lit48, LList.Empty, "enums.scm", 69665);
      Lit2 = PairWithPosition.make(Lit43, PairWithPosition.make(Lit45, LList.Empty, "enums.scm", 69658), "enums.scm", 69658);
      Lit1 = PairWithPosition.make(Lit46, LList.Empty, "enums.scm", 69645);
      Lit0 = PairWithPosition.make(Lit42, LList.Empty, "enums.scm", 65549);
      $instance = new enums();
      var0 = Lit11;
      enums var1 = $instance;
      define$Mnenum = Macro.make(var0, new ModuleMethod(var1, 1, (Object)null, 4097), $instance);
      $Prvt$$Pcdefine$Mnenum = Macro.make(Lit21, new ModuleMethod(var1, 2, (Object)null, 4097), $instance);
      $instance.run();
   }

   public enums() {
      ModuleInfo.register(this);
   }

   static Object lambda1(Object var0) {
      Object[] var1 = SyntaxPattern.allocVars(6, (Object[])null);
      TemplateScope var3;
      if(Lit12.match(var0, var1, 0)) {
         TemplateScope var2 = TemplateScope.make();
         if(std_syntax.isIdentifier(Lit13.execute(var1, var2))) {
            var3 = TemplateScope.make();
            return Lit14.execute(var1, var3);
         }
      }

      if(Lit15.match(var0, var1, 0)) {
         var3 = TemplateScope.make();
         return Lit16.execute(var1, var3);
      } else if(Lit17.match(var0, var1, 0)) {
         if("no enum type name given" instanceof Object[]) {
            var1 = (Object[])"no enum type name given";
         } else {
            var1 = new Object[]{"no enum type name given"};
         }

         return prim_syntax.syntaxError(var0, var1);
      } else if(Lit18.match(var0, var1, 0)) {
         if("no enum constants given" instanceof Object[]) {
            var1 = (Object[])"no enum constants given";
         } else {
            var1 = new Object[]{"no enum constants given"};
         }

         return prim_syntax.syntaxError(var0, var1);
      } else if(Lit19.match(var0, var1, 0)) {
         var3 = TemplateScope.make();
         return Lit20.execute(var1, var3);
      } else {
         return syntax_case.error("syntax-case", var0);
      }
   }

   static Object lambda2(Object var0) {
      Object[] var1 = SyntaxPattern.allocVars(5, (Object[])null);
      if(Lit22.match(var0, var1, 0)) {
         TemplateScope var2 = TemplateScope.make();
         Object var3 = Lit23.execute(var1, var2);

         Symbol var13;
         try {
            var13 = (Symbol)var3;
         } catch (ClassCastException var12) {
            throw new WrongType(var12, "t-name", -2, var3);
         }

         SimpleSymbol var5 = symbolAppend$V(new Object[]{var13, Lit24});
         TemplateScope var14 = TemplateScope.make();
         var3 = Lit25.execute(var1, var14);

         LList var6;
         try {
            var6 = (LList)var3;
         } catch (ClassCastException var11) {
            throw new WrongType(var11, "e-names", -2, var3);
         }

         lists.length(var6);
         LList var15 = mapNames(var13, var6, 0);
         PairWithPosition var4 = makeInit();
         Pair var16 = makeValues(var5, var6);
         TemplateScope var17 = TemplateScope.make();
         Object var7 = Lit26.execute(var1, var17);

         try {
            var6 = (LList)var7;
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "opts", -2, var7);
         }

         TemplateScope var18 = TemplateScope.make();
         var7 = Lit27.execute(var1, var18);

         LList var8;
         try {
            var8 = (LList)var7;
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "other-defs", -2, var7);
         }

         var18 = TemplateScope.make();
         return Quote.append$V(new Object[]{Lit28.execute(var1, var18), Quote.consX$V(new Object[]{std_syntax.datum$To$SyntaxObject(var0, var13), Pair.make(Lit29.execute(var1, var18), Quote.append$V(new Object[]{Lit30.execute(var1, var18), Pair.make(Lit31.execute(var1, var18), Quote.append$V(new Object[]{std_syntax.datum$To$SyntaxObject(var0, var6), Quote.consX$V(new Object[]{std_syntax.datum$To$SyntaxObject(var0, var4), Quote.consX$V(new Object[]{std_syntax.datum$To$SyntaxObject(var0, var16), Pair.make(Pair.make(Lit32.execute(var1, var18), Quote.append$V(new Object[]{Lit33.execute(var1, var18), Quote.consX$V(new Object[]{std_syntax.datum$To$SyntaxObject(var0, var13), Quote.append$V(new Object[]{Lit34.execute(var1, var18), Pair.make(Lit35.execute(var1, var18), Pair.make(Pair.make(Lit36.execute(var1, var18), Quote.consX$V(new Object[]{std_syntax.datum$To$SyntaxObject(var0, var13), Lit37.execute(var1, var18)})), Lit38.execute(var1, var18)))})})})), Quote.append$V(new Object[]{std_syntax.datum$To$SyntaxObject(var0, var15), Quote.append$V(new Object[]{std_syntax.datum$To$SyntaxObject(var0, var8), Lit39.execute(var1, var18)})}))})})}))}))})});
      } else {
         return syntax_case.error("syntax-case", var0);
      }
   }

   static Object makeFieldDesc(Symbol var0, Symbol var1, int var2) {
      return Quote.consX$V(new Object[]{var1, Quote.append$V(new Object[]{Lit0, Quote.consX$V(new Object[]{var0, Quote.append$V(new Object[]{Lit1, Pair.make(Lit2, Quote.append$V(new Object[]{Lit3, Pair.make(Lit4, Quote.append$V(new Object[]{Lit5, Pair.make(Quote.consX$V(new Object[]{var0, Quote.consX$V(new Object[]{misc.symbol$To$String(var1), Quote.consX$V(new Object[]{Integer.valueOf(var2), LList.Empty})})}), LList.Empty)}))}))})})})});
   }

   static PairWithPosition makeInit() {
      return Lit6;
   }

   static Pair makeValues(Symbol var0, LList var1) {
      return Pair.make(Lit7, Quote.append$V(new Object[]{Lit8, Quote.consX$V(new Object[]{var0, Quote.append$V(new Object[]{Lit9, Pair.make(Lit10, Pair.make(Quote.consX$V(new Object[]{var0, Quote.append$V(new Object[]{var1, LList.Empty})}), LList.Empty))})})}));
   }

   static LList mapNames(Symbol var0, LList var1, int var2) {
      if(lists.isNull(var1)) {
         return LList.Empty;
      } else {
         Object var3 = lists.car.apply1(var1);

         Symbol var4;
         try {
            var4 = (Symbol)var3;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "make-field-desc", 1, var3);
         }

         var3 = makeFieldDesc(var0, var4, var2);
         Object var7 = lists.cdr.apply1(var1);

         LList var8;
         try {
            var8 = (LList)var7;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "map-names", 1, var7);
         }

         return lists.cons(var3, mapNames(var0, var8, var2 + 1));
      }
   }

   static SimpleSymbol symbolAppend$V(Object[] var0) {
      Object var1 = LList.makeList(var0, 0);
      Apply var2 = Scheme.apply;
      ModuleMethod var3 = strings.string$Mnappend;

      Symbol var5;
      Object var9;
      for(var9 = LList.Empty; var1 != LList.Empty; var9 = Pair.make(misc.symbol$To$String(var5), var9)) {
         Pair var4;
         try {
            var4 = (Pair)var1;
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "arg0", -2, var1);
         }

         var1 = var4.getCdr();
         Object var11 = var4.getCar();

         try {
            var5 = (Symbol)var11;
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "symbol->string", 1, var11);
         }
      }

      var9 = var2.apply2(var3, LList.reverseInPlace(var9));

      CharSequence var10;
      try {
         var10 = (CharSequence)var9;
      } catch (ClassCastException var6) {
         throw new WrongType(var6, "string->symbol", 1, var9);
      }

      return misc.string$To$Symbol(var10);
   }

   public Object apply1(ModuleMethod var1, Object var2) {
      switch(var1.selector) {
      case 1:
         return lambda1(var2);
      case 2:
         return lambda2(var2);
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
