package kawa.lib;

import gnu.expr.Expression;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.expr.SynchronizedExp;
import gnu.expr.TryExp;
import gnu.kawa.reflect.StaticFieldLocation;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.CallContext;
import gnu.mapping.Location;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.math.IntNum;
import kawa.lang.Macro;
import kawa.lang.Quote;
import kawa.lang.SyntaxForms;
import kawa.lang.SyntaxPattern;
import kawa.lang.SyntaxRule;
import kawa.lang.SyntaxRules;
import kawa.lang.SyntaxTemplate;
import kawa.lang.TemplateScope;
import kawa.lang.Translator;
import kawa.lib.lists;
import kawa.lib.prim_syntax;
import kawa.lib.std_syntax;
import kawa.standard.IfFeature;
import kawa.standard.Scheme;
import kawa.standard.syntax_case;

public class syntax extends ModuleBody {

   public static final Macro $Pccond$Mnexpand;
   public static final Macro $Pcimport;
   public static final Location $Prvt$and;
   public static final Location $Prvt$define$Mnconstant;
   public static final Location $Prvt$define$Mnsyntax;
   public static final Location $Prvt$if;
   public static final Location $Prvt$let;
   public static final Location $Prvt$or;
   public static final Location $Prvt$try$Mncatch;
   public static final syntax $instance;
   static final SyntaxPattern Lit0;
   static final SyntaxTemplate Lit1;
   static final SyntaxPattern Lit10;
   static final SimpleSymbol Lit100;
   static final SyntaxRules Lit101;
   static final SimpleSymbol Lit102 = (SimpleSymbol)(new SimpleSymbol("$lookup$")).readResolve();
   static final SimpleSymbol Lit103 = (SimpleSymbol)(new SimpleSymbol("gnu.mapping.LocationProc")).readResolve();
   static final SimpleSymbol Lit104 = (SimpleSymbol)(new SimpleSymbol("quasiquote")).readResolve();
   static final SimpleSymbol Lit105 = (SimpleSymbol)(new SimpleSymbol("arg")).readResolve();
   static final SimpleSymbol Lit106 = (SimpleSymbol)(new SimpleSymbol("as")).readResolve();
   static final SimpleSymbol Lit107 = (SimpleSymbol)(new SimpleSymbol("wt")).readResolve();
   static final SimpleSymbol Lit108 = (SimpleSymbol)(new SimpleSymbol("quote")).readResolve();
   static final SimpleSymbol Lit109 = (SimpleSymbol)(new SimpleSymbol("lambda")).readResolve();
   static final SimpleSymbol Lit11;
   static final SimpleSymbol Lit110 = (SimpleSymbol)(new SimpleSymbol("begin")).readResolve();
   static final SimpleSymbol Lit111 = (SimpleSymbol)(new SimpleSymbol("else")).readResolve();
   static final SimpleSymbol Lit112 = (SimpleSymbol)(new SimpleSymbol("and")).readResolve();
   static final SimpleSymbol Lit113 = (SimpleSymbol)(new SimpleSymbol("or")).readResolve();
   static final SimpleSymbol Lit114 = (SimpleSymbol)(new SimpleSymbol("not")).readResolve();
   static final SimpleSymbol Lit115 = (SimpleSymbol)(new SimpleSymbol("let")).readResolve();
   static final SimpleSymbol Lit116 = (SimpleSymbol)(new SimpleSymbol("call-with-values")).readResolve();
   static final SimpleSymbol Lit117 = (SimpleSymbol)(new SimpleSymbol("x")).readResolve();
   static final SimpleSymbol Lit118 = (SimpleSymbol)(new SimpleSymbol("kawa.standard.ImportFromLibrary")).readResolve();
   static final SimpleSymbol Lit119 = (SimpleSymbol)(new SimpleSymbol("instance")).readResolve();
   static final SyntaxRules Lit12;
   static final SimpleSymbol Lit120 = (SimpleSymbol)(new SimpleSymbol("prefix")).readResolve();
   static final SimpleSymbol Lit121 = (SimpleSymbol)(new SimpleSymbol("if")).readResolve();
   static final SimpleSymbol Lit122 = (SimpleSymbol)(new SimpleSymbol("form")).readResolve();
   static final SimpleSymbol Lit123 = (SimpleSymbol)(new SimpleSymbol("%define-macro")).readResolve();
   static final SimpleSymbol Lit13;
   static final SyntaxRules Lit14;
   static final SimpleSymbol Lit15;
   static final SyntaxRules Lit16;
   static final SimpleSymbol Lit17;
   static final SyntaxRules Lit18;
   static final SimpleSymbol Lit19;
   static final SyntaxTemplate Lit2;
   static final SyntaxRules Lit20;
   static final SimpleSymbol Lit21;
   static final SyntaxPattern Lit22;
   static final SyntaxTemplate Lit23;
   static final SyntaxTemplate Lit24;
   static final SimpleSymbol Lit25;
   static final SyntaxPattern Lit26;
   static final SyntaxTemplate Lit27;
   static final SyntaxTemplate Lit28;
   static final SimpleSymbol Lit29;
   static final SyntaxPattern Lit3;
   static final SimpleSymbol Lit30;
   static final SimpleSymbol Lit31;
   static final SimpleSymbol Lit32;
   static final SimpleSymbol Lit33;
   static final SimpleSymbol Lit34;
   static final SimpleSymbol Lit35;
   static final SimpleSymbol Lit36;
   static final SyntaxRules Lit37;
   static final SimpleSymbol Lit38;
   static final SyntaxPattern Lit39;
   static final SyntaxPattern Lit4;
   static final SyntaxTemplate Lit40;
   static final SyntaxTemplate Lit41;
   static final SyntaxTemplate Lit42;
   static final SyntaxTemplate Lit43;
   static final SyntaxTemplate Lit44;
   static final SyntaxTemplate Lit45;
   static final SyntaxPattern Lit46;
   static final SyntaxTemplate Lit47;
   static final SyntaxTemplate Lit48;
   static final SyntaxTemplate Lit49;
   static final SyntaxPattern Lit5;
   static final SyntaxTemplate Lit50;
   static final SyntaxTemplate Lit51;
   static final SyntaxTemplate Lit52;
   static final SyntaxPattern Lit53;
   static final SyntaxTemplate Lit54;
   static final SyntaxTemplate Lit55;
   static final SyntaxTemplate Lit56;
   static final SyntaxTemplate Lit57;
   static final SyntaxTemplate Lit58;
   static final SyntaxTemplate Lit59;
   static final SyntaxTemplate Lit6;
   static final SyntaxPattern Lit60;
   static final SyntaxTemplate Lit61;
   static final SyntaxTemplate Lit62;
   static final SyntaxTemplate Lit63;
   static final SyntaxTemplate Lit64;
   static final SyntaxPattern Lit65;
   static final SyntaxTemplate Lit66;
   static final SyntaxPattern Lit67;
   static final SyntaxTemplate Lit68;
   static final SyntaxTemplate Lit69;
   static final SyntaxTemplate Lit7;
   static final SyntaxTemplate Lit70;
   static final SyntaxTemplate Lit71;
   static final SyntaxPattern Lit72;
   static final SyntaxTemplate Lit73;
   static final SyntaxTemplate Lit74;
   static final SyntaxTemplate Lit75;
   static final SyntaxTemplate Lit76;
   static final SimpleSymbol Lit77;
   static final SyntaxRules Lit78;
   static final SimpleSymbol Lit79;
   static final SyntaxTemplate Lit8;
   static final SyntaxRules Lit80;
   static final SimpleSymbol Lit81;
   static final SyntaxPattern Lit82;
   static final SyntaxTemplate Lit83;
   static final SyntaxTemplate Lit84;
   static final SyntaxPattern Lit85;
   static final SyntaxTemplate Lit86;
   static final SyntaxTemplate Lit87;
   static final SyntaxPattern Lit88;
   static final SyntaxPattern Lit89;
   static final SyntaxPattern Lit9;
   static final SyntaxTemplate Lit90;
   static final SimpleSymbol Lit91;
   static final SyntaxRules Lit92;
   static final SimpleSymbol Lit93;
   static final SyntaxPattern Lit94;
   static final SyntaxTemplate Lit95;
   static final SyntaxTemplate Lit96;
   static final SyntaxTemplate Lit97;
   static final SimpleSymbol Lit98;
   static final SyntaxRules Lit99;
   public static final Macro case$Mnlambda;
   public static final Macro cond$Mnexpand;
   public static final Macro define$Mnalias$Mnparameter;
   public static final Macro define$Mnmacro;
   public static final Macro define$Mnsyntax$Mncase;
   public static final Macro defmacro;
   public static final ModuleMethod identifier$Mnlist$Qu;
   public static final ModuleMethod identifier$Mnpair$Mnlist$Qu;
   public static final Macro import;
   public static final ModuleMethod import$Mnhandle$Mnexcept;
   public static final ModuleMethod import$Mnhandle$Mnonly;
   public static final ModuleMethod import$Mnhandle$Mnprefix;
   public static final ModuleMethod import$Mnhandle$Mnrename;
   public static final ModuleMethod import$Mnmapper;
   public static final Macro let$Mnvalues;
   public static final Macro let$St$Mnvalues;
   public static final Macro receive;
   public static final Macro synchronized;
   public static final Macro try$Mnfinally;
   public static final Macro unless;
   public static final Macro when;


   static {
      SimpleSymbol var0 = (SimpleSymbol)(new SimpleSymbol("define-alias-parameter")).readResolve();
      Lit100 = var0;
      SyntaxRule var1 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\f\u0017\b", new Object[0], 3), "\u0001\u0001\u0001", "\u0011\u0018\u0004¹\u0011\u0018\f\t\u0003\u0011\u0018\u0014\u0011\u0018\u001c\b\u0011\u0018$)\u0011\u0018,\b\u0003\b\u0013\b\u0011\u00184\t\u0003\b\u0011\u0018<\u0011\u0018D\b\u0011\u0018L9\u0011\u0018T\t\u000b\u0018\\\b\u0011\u0018d\u0011\u0018l\b\u0011\u0018ty\b\u0011\u0018|\b\u0011\u0018\u0084\u0011\u0018d\t\u0003\u0018\u008cA\u0011\u0018\u0094\u0011\u0018\u009c\b\u000b\u0018¤", new Object[]{Lit110, (SimpleSymbol)(new SimpleSymbol("define-constant")).readResolve(), (SimpleSymbol)(new SimpleSymbol("::")).readResolve(), (SimpleSymbol)(new SimpleSymbol("<gnu.mapping.LocationProc>")).readResolve(), PairWithPosition.make(Lit102, Pair.make(Lit103, Pair.make(Pair.make(Lit104, Pair.make((SimpleSymbol)(new SimpleSymbol("makeNamed")).readResolve(), LList.Empty)), LList.Empty)), "syntax.scm", 1069060), Lit108, PairWithPosition.make(Lit102, Pair.make(Lit103, Pair.make(Pair.make(Lit104, Pair.make((SimpleSymbol)(new SimpleSymbol("pushConverter")).readResolve(), LList.Empty)), LList.Empty)), "syntax.scm", 1073161), Lit109, PairWithPosition.make(Lit105, LList.Empty, "syntax.scm", 1081354), (SimpleSymbol)(new SimpleSymbol("try-catch")).readResolve(), Lit106, PairWithPosition.make(Lit105, LList.Empty, "syntax.scm", 1089550), (SimpleSymbol)(new SimpleSymbol("ex")).readResolve(), (SimpleSymbol)(new SimpleSymbol("<java.lang.ClassCastException>")).readResolve(), Lit115, Lit107, PairWithPosition.make(Lit102, Pair.make((SimpleSymbol)(new SimpleSymbol("gnu.mapping.WrongType")).readResolve(), Pair.make(Pair.make(Lit104, Pair.make((SimpleSymbol)(new SimpleSymbol("make")).readResolve(), LList.Empty)), LList.Empty)), "syntax.scm", 1097748), PairWithPosition.make(PairWithPosition.make(Lit106, PairWithPosition.make((SimpleSymbol)(new SimpleSymbol("<int>")).readResolve(), PairWithPosition.make(IntNum.make(1), LList.Empty, "syntax.scm", 1101846), "syntax.scm", 1101840), "syntax.scm", 1101836), PairWithPosition.make(Lit105, LList.Empty, "syntax.scm", 1101849), "syntax.scm", 1101836), (SimpleSymbol)(new SimpleSymbol("set!")).readResolve(), PairWithPosition.make((SimpleSymbol)(new SimpleSymbol("field")).readResolve(), PairWithPosition.make(Lit107, PairWithPosition.make(PairWithPosition.make(Lit108, PairWithPosition.make((SimpleSymbol)(new SimpleSymbol("expectedType")).readResolve(), LList.Empty, "syntax.scm", 1105941), "syntax.scm", 1105941), LList.Empty, "syntax.scm", 1105940), "syntax.scm", 1105937), "syntax.scm", 1105930), PairWithPosition.make(PairWithPosition.make((SimpleSymbol)(new SimpleSymbol("primitive-throw")).readResolve(), PairWithPosition.make(Lit107, LList.Empty, "syntax.scm", 1110037), "syntax.scm", 1110020), LList.Empty, "syntax.scm", 1110020)}, 0);
      Lit101 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var1}, 3);
      var0 = (SimpleSymbol)(new SimpleSymbol("receive")).readResolve();
      Lit98 = var0;
      var1 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\r\u0017\u0010\b\b", new Object[0], 3), "\u0001\u0001\u0003", "\u0011\u0018\u00049\u0011\u0018\f\t\u0010\b\u000b\b\u0011\u0018\f\t\u0003\b\u0015\u0013", new Object[]{Lit116, Lit109}, 1);
      Lit99 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var1}, 3);
      var0 = (SimpleSymbol)(new SimpleSymbol("cond-expand")).readResolve();
      Lit91 = var0;
      Lit97 = new SyntaxTemplate("\u0001\u0001\u0000\u0000", "\u0011\u0018\u0004\u001a", new Object[]{var0}, 0);
      Lit96 = new SyntaxTemplate("\u0001\u0001\u0000\u0000", "\u0011\u0018\u0004\u0012", new Object[]{Lit110}, 0);
      Lit95 = new SyntaxTemplate("\u0001\u0001\u0000\u0000", "\u000b", new Object[0], 0);
      Lit94 = new SyntaxPattern("\f\u0007\u001c\f\u000f\u0013\u001b", new Object[0], 4);
      Lit93 = (SimpleSymbol)(new SimpleSymbol("%cond-expand")).readResolve();
      var0 = Lit91;
      SimpleSymbol var13 = Lit112;
      SimpleSymbol var2 = Lit113;
      SimpleSymbol var3 = Lit114;
      SimpleSymbol var4 = Lit111;
      SyntaxRule var5 = new SyntaxRule(new SyntaxPattern("\f\u0018\b", new Object[0], 0), "", "\u0018\u0004", new Object[]{PairWithPosition.make((SimpleSymbol)(new SimpleSymbol("%syntax-error")).readResolve(), PairWithPosition.make("Unfulfilled cond-expand", LList.Empty, "syntax.scm", 802851), "syntax.scm", 802836)}, 0);
      SyntaxRule var6 = new SyntaxRule(new SyntaxPattern("\f\u0018<\f\u0002\r\u0007\u0000\b\b\b", new Object[]{Lit111}, 1), "\u0003", "\u0011\u0018\u0004\b\u0005\u0003", new Object[]{Lit110}, 1);
      SyntaxRule var7 = new SyntaxRule(new SyntaxPattern("\f\u0018L\u001c\f\u0002\b\r\u0007\u0000\b\b\r\u000f\b\b\b", new Object[]{Lit112}, 2), "\u0003\u0003", "\u0011\u0018\u0004\b\u0005\u0003", new Object[]{Lit110}, 1);
      SyntaxRule var8 = new SyntaxRule(new SyntaxPattern("\f\u0018|L\f\u0002\f\u0007\r\u000f\b\b\b\r\u0017\u0010\b\b\r\u001f\u0018\b\b", new Object[]{Lit112}, 4), "\u0001\u0003\u0003\u0003", "\u0011\u0018\u0004¡\t\u0003\b\u0011\u0018\u0004Q1\u0011\u0018\f\b\r\u000b\b\u0015\u0013\b\u001d\u001b\b\u001d\u001b", new Object[]{Lit91, Lit112}, 1);
      SyntaxRule var9 = new SyntaxRule(new SyntaxPattern("\f\u0018L\u001c\f\u0002\b\r\u0007\u0000\b\b\r\u000f\b\b\b", new Object[]{Lit113}, 2), "\u0003\u0003", "\u0011\u0018\u0004\b\r\u000b", new Object[]{Lit91}, 1);
      SyntaxRule var10 = new SyntaxRule(new SyntaxPattern("\f\u0018|L\f\u0002\f\u0007\r\u000f\b\b\b\r\u0017\u0010\b\b\r\u001f\u0018\b\b", new Object[]{Lit113}, 4), "\u0001\u0003\u0003\u0003", "\u0011\u0018\u0004I\t\u0003\b\u0011\u0018\f\b\u0015\u0013\b\u0011\u0018\u0014\b\u0011\u0018\u0004Q1\u0011\u0018\u001c\b\r\u000b\b\u0015\u0013\b\u001d\u001b", new Object[]{Lit91, Lit110, Lit111, Lit113}, 1);
      SyntaxRule var11 = new SyntaxRule(new SyntaxPattern("\f\u0018\\,\f\u0002\f\u0007\b\r\u000f\b\b\b\r\u0017\u0010\b\b", new Object[]{Lit114}, 3), "\u0001\u0003\u0003", "\u0011\u0018\u0004I\t\u0003\b\u0011\u0018\u0004\b\u0015\u0013\b\u0011\u0018\f\b\r\u000b", new Object[]{Lit91, Lit111}, 1);
      SyntaxRule var12 = new SyntaxRule(new SyntaxPattern("\f\u0018\u001c\f\u0007\u000b\u0013", new Object[0], 3), "\u0001\u0000\u0000", "\u0011\u0018\u0004\u0019\t\u0003\n\u0012", new Object[]{Lit93}, 0);
      Lit92 = new SyntaxRules(new Object[]{var0, var13, var2, var3, var4}, new SyntaxRule[]{var5, var6, var7, var8, var9, var10, var11, var12}, 4);
      Lit90 = new SyntaxTemplate("\u0001\u0000\u0000", "\u0012", new Object[0], 0);
      Lit89 = new SyntaxPattern("\u0013", new Object[0], 3);
      Lit88 = new SyntaxPattern("\b", new Object[0], 2);
      Lit87 = new SyntaxTemplate("\u0001\u0000\u0001\u0000\u0000", "\"", new Object[0], 0);
      Lit86 = new SyntaxTemplate("\u0001\u0000\u0001\u0000\u0000", "\u0011\u0018\u0004\t\u0013\u001a", new Object[]{Lit109}, 0);
      Lit85 = new SyntaxPattern("\u001c\f\u0017\u001b#", new Object[0], 5);
      Lit84 = new SyntaxTemplate("\u0001\u0000", "\n", new Object[0], 0);
      Lit83 = new SyntaxTemplate("\u0001\u0000", "\u0018\u0004", new Object[]{PairWithPosition.make(Lit102, Pair.make((SimpleSymbol)(new SimpleSymbol("gnu.expr.GenericProc")).readResolve(), Pair.make(Pair.make(Lit104, Pair.make((SimpleSymbol)(new SimpleSymbol("makeWithoutSorting")).readResolve(), LList.Empty)), LList.Empty)), "syntax.scm", 651273)}, 0);
      Lit82 = new SyntaxPattern("\f\u0007\u000b", new Object[0], 2);
      Lit81 = (SimpleSymbol)(new SimpleSymbol("case-lambda")).readResolve();
      var0 = (SimpleSymbol)(new SimpleSymbol("let*-values")).readResolve();
      Lit79 = var0;
      var1 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\b\f\u0007\r\u000f\b\b\b", new Object[0], 2), "\u0001\u0003", "\u0011\u0018\u0004\t\u0003\b\r\u000b", new Object[]{Lit110}, 1);
      SyntaxPattern var14 = new SyntaxPattern("\f\u0018<\f\u0007\r\u000f\b\b\b\f\u0017\r\u001f\u0018\b\b", new Object[0], 4);
      var3 = (SimpleSymbol)(new SimpleSymbol("let-values")).readResolve();
      Lit77 = var3;
      SyntaxRule var15 = new SyntaxRule(var14, "\u0001\u0003\u0001\u0003", "\u0011\u0018\u0004\u0011\b\u0003\b\u0011\u0018\f\u0019\b\r\u000b\t\u0013\b\u001d\u001b", new Object[]{var3, Lit79}, 1);
      Lit80 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var1, var15}, 4);
      var0 = Lit77;
      var1 = new SyntaxRule(new SyntaxPattern("\f\u0018,\r\u0007\u0000\b\b\f\u000f\r\u0017\u0010\b\b", new Object[0], 3), "\u0003\u0001\u0003", "\u0011\u0018\u0004\u0011\u0018\f\u0019\b\u0005\u0003\t\u0010\b\u0011\u0018\u0014\t\u000b\b\u0015\u0013", new Object[]{Lit77, "bind", Lit110}, 1);
      var15 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0002\f\b\f\u0007\f\u000f\b", new Object[]{"bind"}, 2), "\u0001\u0001", "\u0011\u0018\u0004\t\u0003\b\u000b", new Object[]{Lit115}, 0);
      SyntaxRule var16 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0002\\,\f\u0007\f\u000f\b\r\u0017\u0010\b\b\f\u001f\f\'\b", new Object[]{"bind"}, 5), "\u0001\u0001\u0003\u0001\u0001", "\u0011\u0018\u0004\u0011\u0018\f\t\u0003\t\u000b\t\u0010\u0019\b\u0015\u0013\t\u001b\b#", new Object[]{Lit77, "mktmp"}, 1);
      SyntaxRule var17 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0002\f\b\f\u0007\f\u000f\f\u0017\f\u001f\f\'\b", new Object[]{"mktmp"}, 5), "\u0001\u0001\u0001\u0001\u0001", "\u0011\u0018\u00049\u0011\u0018\f\t\u0010\b\u0003\b\u0011\u0018\f\t\u000b\b\u0011\u0018\u0014\u0011\u0018\u001c\t\u0013\t\u001b\b#", new Object[]{Lit116, Lit109, Lit77, "bind"}, 0);
      var5 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0002\u001c\f\u0007\u000b\f\u0017,\r\u001f\u0018\b\b\f\',\r/(\b\b\f7\b", new Object[]{"mktmp"}, 7), "\u0001\u0000\u0001\u0003\u0001\u0003\u0001", "\u0011\u0018\u0004\u0011\u0018\f\t\n\t\u0013)\u0011\u001d\u001b\u0018\u0014\t#A\u0011-+\b\t\u0003\u0018\u001c\b3", new Object[]{Lit77, "mktmp", PairWithPosition.make(Lit117, LList.Empty, "syntax.scm", 569387), PairWithPosition.make(Lit117, LList.Empty, "syntax.scm", 569414)}, 1);
      var6 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0002\f\u0007\f\u000f,\r\u0017\u0010\b\b\f\u001f,\r\' \b\b\f/\b", new Object[]{"mktmp"}, 6), "\u0001\u0001\u0003\u0001\u0003\u0001", "\u0011\u0018\u00049\u0011\u0018\f\t\u0010\b\u000b\b\u0011\u0018\f)\u0011\u0015\u0013\u0018\u0014\b\u0011\u0018\u001c\u0011\u0018$\t\u001bA\u0011%#\b\t\u0003\u0018,\b+", new Object[]{Lit116, Lit109, Lit117, Lit77, "bind", PairWithPosition.make(Lit117, LList.Empty, "syntax.scm", 593973)}, 1);
      Lit78 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var1, var15, var16, var17, var5, var6}, 7);
      Lit76 = new SyntaxTemplate("\u0001\u0001\u0001", "\u0010", new Object[0], 0);
      Lit75 = new SyntaxTemplate("\u0001\u0001\u0001", "\u0013", new Object[0], 0);
      Lit74 = new SyntaxTemplate("\u0001\u0001\u0001", "\b\u000b", new Object[0], 0);
      Lit73 = new SyntaxTemplate("\u0001\u0001\u0001", "\u0018\u0004", new Object[]{PairWithPosition.make(Lit102, Pair.make(Lit118, Pair.make(Pair.make(Lit104, Pair.make(Lit119, LList.Empty)), LList.Empty)), "syntax.scm", 466951)}, 0);
      Lit72 = new SyntaxPattern("\f\u0007\f\u000f\f\u0017\b", new Object[0], 3);
      Lit71 = new SyntaxTemplate("\u0001\u0001\u0001", "\u0010", new Object[0], 0);
      Lit70 = new SyntaxTemplate("\u0001\u0001\u0001", "\u0013", new Object[0], 0);
      Lit69 = new SyntaxTemplate("\u0001\u0001\u0001", "\b\u000b", new Object[0], 0);
      Lit68 = new SyntaxTemplate("\u0001\u0001\u0001", "\u0018\u0004", new Object[]{PairWithPosition.make(Lit102, Pair.make(Lit118, Pair.make(Pair.make(Lit104, Pair.make(Lit119, LList.Empty)), LList.Empty)), "syntax.scm", 458759)}, 0);
      Lit67 = new SyntaxPattern("\f\u0007,\f\u0002\f\u000f\b\f\u0017\b", new Object[]{(SimpleSymbol)(new SimpleSymbol("library")).readResolve()}, 3);
      Lit66 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u0012", new Object[0], 0);
      Lit65 = new SyntaxPattern("\f\u0007,\f\u0002\f\u000f\u0013\f\u001f\b", new Object[]{Lit120}, 4);
      Lit64 = new SyntaxTemplate("\u0001\u0001\u0001\u0001", "\u0010", new Object[0], 0);
      Lit63 = new SyntaxTemplate("\u0001\u0001\u0001\u0001", "\u001b", new Object[0], 0);
      Lit62 = new SyntaxTemplate("\u0001\u0001\u0001\u0001", "\u0013", new Object[0], 0);
      var0 = (SimpleSymbol)(new SimpleSymbol("%import")).readResolve();
      Lit38 = var0;
      Lit61 = new SyntaxTemplate("\u0001\u0001\u0001\u0001", "\u0011\u0018\u0004\b\u000b", new Object[]{var0}, 0);
      Lit60 = new SyntaxPattern("\f\u0007<\f\u0002\f\u000f\f\u0017\b\f\u001f\b", new Object[]{Lit120}, 4);
      Lit59 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u0012", new Object[0], 0);
      Lit58 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u0010", new Object[0], 0);
      Lit57 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u001b", new Object[0], 0);
      Lit56 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u0012", new Object[0], 0);
      Lit55 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u0011\u0018\u0004\b\u000b", new Object[]{Lit38}, 0);
      Lit54 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u0012", new Object[0], 0);
      Lit53 = new SyntaxPattern("\f\u0007,\f\u0002\f\u000f\u0013\f\u001f\b", new Object[]{(SimpleSymbol)(new SimpleSymbol("except")).readResolve()}, 4);
      Lit52 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u0012", new Object[0], 0);
      Lit51 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u0010", new Object[0], 0);
      Lit50 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u001b", new Object[0], 0);
      Lit49 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u0012", new Object[0], 0);
      Lit48 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u0011\u0018\u0004\b\u000b", new Object[]{Lit38}, 0);
      Lit47 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u0012", new Object[0], 0);
      Lit46 = new SyntaxPattern("\f\u0007,\f\u0002\f\u000f\u0013\f\u001f\b", new Object[]{(SimpleSymbol)(new SimpleSymbol("only")).readResolve()}, 4);
      Lit45 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u0018\u0004", new Object[]{(SimpleSymbol)(new SimpleSymbol("rest")).readResolve()}, 0);
      Lit44 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u0010", new Object[0], 0);
      Lit43 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u001b", new Object[0], 0);
      Lit42 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u0012", new Object[0], 0);
      Lit41 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u0011\u0018\u0004\b\u000b", new Object[]{Lit38}, 0);
      Lit40 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u0012", new Object[0], 0);
      Lit39 = new SyntaxPattern("\f\u0007,\f\u0002\f\u000f\u0013\f\u001f\b", new Object[]{(SimpleSymbol)(new SimpleSymbol("rename")).readResolve()}, 4);
      var0 = (SimpleSymbol)(new SimpleSymbol("import")).readResolve();
      Lit36 = var0;
      var1 = new SyntaxRule(new SyntaxPattern("\f\u0018\r\u0007\u0000\b\b", new Object[0], 1), "\u0003", "\u0011\u0018\u0004\b\u0005\u0011\u0018\f\t\u0003\u0018\u0014", new Object[]{Lit110, Lit38, PairWithPosition.make(LList.Empty, LList.Empty, "syntax.scm", 376866)}, 1);
      Lit37 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var1}, 1);
      Lit35 = (SimpleSymbol)(new SimpleSymbol("import-mapper")).readResolve();
      Lit34 = (SimpleSymbol)(new SimpleSymbol("import-handle-rename")).readResolve();
      Lit33 = (SimpleSymbol)(new SimpleSymbol("import-handle-prefix")).readResolve();
      Lit32 = (SimpleSymbol)(new SimpleSymbol("import-handle-except")).readResolve();
      Lit31 = (SimpleSymbol)(new SimpleSymbol("import-handle-only")).readResolve();
      Lit30 = (SimpleSymbol)(new SimpleSymbol("identifier-pair-list?")).readResolve();
      Lit29 = (SimpleSymbol)(new SimpleSymbol("identifier-list?")).readResolve();
      Lit28 = new SyntaxTemplate("\u0001\u0001\u0000", "\u0012", new Object[0], 0);
      Lit27 = new SyntaxTemplate("\u0001\u0001\u0000", "\u000b", new Object[0], 0);
      Lit26 = new SyntaxPattern("\f\u0007\f\u000f\u0013", new Object[0], 3);
      Lit25 = (SimpleSymbol)(new SimpleSymbol("synchronized")).readResolve();
      Lit24 = new SyntaxTemplate("\u0001\u0001\u0001", "\u0013", new Object[0], 0);
      Lit23 = new SyntaxTemplate("\u0001\u0001\u0001", "\u000b", new Object[0], 0);
      Lit22 = new SyntaxPattern("\f\u0007\f\u000f\f\u0017\b", new Object[0], 3);
      Lit21 = (SimpleSymbol)(new SimpleSymbol("try-finally")).readResolve();
      var0 = (SimpleSymbol)(new SimpleSymbol("when")).readResolve();
      Lit17 = var0;
      var1 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\r\u000f\b\b\b", new Object[0], 2), "\u0001\u0003", "\u0011\u0018\u0004)\u0011\u0018\f\b\u0003\b\u0011\u0018\u0014\b\r\u000b", new Object[]{Lit121, Lit114, Lit110}, 1);
      Lit20 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var1}, 2);
      Lit19 = (SimpleSymbol)(new SimpleSymbol("unless")).readResolve();
      var0 = Lit17;
      var1 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\r\u000f\b\b\b", new Object[0], 2), "\u0001\u0003", "\u0011\u0018\u0004\t\u0003\b\u0011\u0018\f\b\r\u000b", new Object[]{Lit121, Lit110}, 1);
      Lit18 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var1}, 2);
      var0 = (SimpleSymbol)(new SimpleSymbol("define-syntax-case")).readResolve();
      Lit15 = var0;
      var1 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\u0013", new Object[0], 3), "\u0001\u0001\u0000", "\u0011\u0018\u0004\t\u0003\b\u0011\u0018\f\u0011\u0018\u0014\b\u0011\u0018\u001c\u0011\u0018$\t\u000b\u0012", new Object[]{(SimpleSymbol)(new SimpleSymbol("define-syntax")).readResolve(), Lit109, PairWithPosition.make(Lit122, LList.Empty, "syntax.scm", 90129), (SimpleSymbol)(new SimpleSymbol("syntax-case")).readResolve(), Lit122}, 0);
      Lit16 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var1}, 3);
      var0 = (SimpleSymbol)(new SimpleSymbol("define-macro")).readResolve();
      Lit13 = var0;
      var1 = new SyntaxRule(new SyntaxPattern("\f\u0018\u001c\f\u0007\u000b\u0013", new Object[0], 3), "\u0001\u0000\u0000", "\u0011\u0018\u0004\t\u0003\b\u0011\u0018\f\t\n\u0012", new Object[]{Lit123, Lit109}, 0);
      var15 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\b", new Object[0], 2), "\u0001\u0001", "\u0011\u0018\u0004\t\u0003\b\u000b", new Object[]{Lit123}, 0);
      Lit14 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var1, var15}, 3);
      var0 = (SimpleSymbol)(new SimpleSymbol("defmacro")).readResolve();
      Lit11 = var0;
      var1 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\u0013", new Object[0], 3), "\u0001\u0001\u0000", "\u0011\u0018\u0004\t\u0003\b\u0011\u0018\f\t\u000b\u0012", new Object[]{Lit123, Lit109}, 0);
      Lit12 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var1}, 3);
      Lit10 = new SyntaxPattern("\u0003", new Object[0], 1);
      Lit9 = new SyntaxPattern("\b", new Object[0], 0);
      Lit8 = new SyntaxTemplate("\u0001\u0001\u0000", "\u0012", new Object[0], 0);
      Lit7 = new SyntaxTemplate("\u0001\u0001\u0000", "\u000b", new Object[0], 0);
      Lit6 = new SyntaxTemplate("\u0001\u0001\u0000", "\u0003", new Object[0], 0);
      Lit5 = new SyntaxPattern(",\f\u0007\f\u000f\b\u0013", new Object[0], 3);
      Lit4 = new SyntaxPattern("\u0003", new Object[0], 1);
      Lit3 = new SyntaxPattern("\b", new Object[0], 0);
      Lit2 = new SyntaxTemplate("\u0001\u0000", "\n", new Object[0], 0);
      Lit1 = new SyntaxTemplate("\u0001\u0000", "\u0003", new Object[0], 0);
      Lit0 = new SyntaxPattern("\f\u0007\u000b", new Object[0], 2);
      $instance = new syntax();
      $Prvt$define$Mnsyntax = StaticFieldLocation.make("kawa.lib.prim_syntax", "define$Mnsyntax");
      $Prvt$define$Mnconstant = StaticFieldLocation.make("kawa.lib.prim_syntax", "define$Mnconstant");
      $Prvt$if = StaticFieldLocation.make("kawa.lib.prim_syntax", "if");
      $Prvt$try$Mncatch = StaticFieldLocation.make("kawa.lib.prim_syntax", "try$Mncatch");
      $Prvt$and = StaticFieldLocation.make("kawa.lib.std_syntax", "and");
      $Prvt$or = StaticFieldLocation.make("kawa.lib.std_syntax", "or");
      $Prvt$let = StaticFieldLocation.make("kawa.lib.std_syntax", "let");
      defmacro = Macro.make(Lit11, Lit12, $instance);
      define$Mnmacro = Macro.make(Lit13, Lit14, $instance);
      define$Mnsyntax$Mncase = Macro.make(Lit15, Lit16, $instance);
      when = Macro.make(Lit17, Lit18, $instance);
      unless = Macro.make(Lit19, Lit20, $instance);
      var13 = Lit21;
      syntax var18 = $instance;
      try$Mnfinally = Macro.make(var13, new ModuleMethod(var18, 2, (Object)null, 4097), $instance);
      synchronized = Macro.make(Lit25, new ModuleMethod(var18, 3, (Object)null, 4097), $instance);
      identifier$Mnlist$Qu = new ModuleMethod(var18, 4, Lit29, 4097);
      identifier$Mnpair$Mnlist$Qu = new ModuleMethod(var18, 5, Lit30, 4097);
      import$Mnhandle$Mnonly = new ModuleMethod(var18, 6, Lit31, 8194);
      import$Mnhandle$Mnexcept = new ModuleMethod(var18, 7, Lit32, 8194);
      import$Mnhandle$Mnprefix = new ModuleMethod(var18, 8, Lit33, 8194);
      import$Mnhandle$Mnrename = new ModuleMethod(var18, 9, Lit34, 8194);
      import$Mnmapper = new ModuleMethod(var18, 10, Lit35, 4097);
      import = Macro.make(Lit36, Lit37, $instance);
      $Pcimport = Macro.make(Lit38, new ModuleMethod(var18, 11, (Object)null, 4097), $instance);
      let$Mnvalues = Macro.make(Lit77, Lit78, $instance);
      let$St$Mnvalues = Macro.make(Lit79, Lit80, $instance);
      case$Mnlambda = Macro.make(Lit81, new ModuleMethod(var18, 12, (Object)null, 4097), $instance);
      cond$Mnexpand = Macro.make(Lit91, Lit92, $instance);
      var13 = Lit93;
      ModuleMethod var19 = new ModuleMethod(var18, 13, (Object)null, 4097);
      var19.setProperty("source-location", "syntax.scm:227");
      $Pccond$Mnexpand = Macro.make(var13, var19, $instance);
      receive = Macro.make(Lit98, Lit99, $instance);
      define$Mnalias$Mnparameter = Macro.make(Lit100, Lit101, $instance);
      $instance.run();
   }

   public syntax() {
      ModuleInfo.register(this);
   }

   public static Object importHandleExcept(Object var0, Object var1) {
      Object var2 = var0;
      if(lists.memq(var0, var1) != Boolean.FALSE) {
         var2 = null;
      }

      return var2;
   }

   public static Object importHandleOnly(Object var0, Object var1) {
      return lists.memq(var0, var1) != Boolean.FALSE?var0:null;
   }

   public static Object importHandlePrefix(Object var0, Object var1) {
      if(var0 == null) {
         ;
      }

      return null;
   }

   public static Object importHandleRename(Object var0, Object var1) {
      return lists.isPair(var1)?(var0 == lists.caar.apply1(var1)?lists.cadar.apply1(var1):importHandleRename(var0, lists.cdr.apply1(var1))):var0;
   }

   public static Procedure importMapper(Object var0) {
      syntax.frame var1 = new syntax.frame();
      var1.list = var0;
      return var1.lambda$Fn1;
   }

   public static boolean isIdentifierList(Object var0) {
      boolean var3;
      if(Translator.listLength(var0) >= 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      boolean var2 = var3;
      if(var3) {
         while(true) {
            Object[] var1 = SyntaxPattern.allocVars(2, (Object[])null);
            if(!Lit0.match(var0, var1, 0)) {
               if(!Lit3.match(var0, var1, 0)) {
                  if(Lit4.match(var0, var1, 0)) {
                     return false;
                  }

                  if(syntax_case.error("syntax-case", var0) != Boolean.FALSE) {
                     return true;
                  }

                  return false;
               }

               var2 = true;
               break;
            }

            TemplateScope var4 = TemplateScope.make();
            var3 = std_syntax.isIdentifier(Lit1.execute(var1, var4));
            var2 = var3;
            if(!var3) {
               break;
            }

            var4 = TemplateScope.make();
            var0 = Lit2.execute(var1, var4);
         }
      }

      return var2;
   }

   public static boolean isIdentifierPairList(Object var0) {
      boolean var3;
      if(Translator.listLength(var0) >= 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      boolean var2 = var3;
      if(var3) {
         while(true) {
            Object[] var1 = SyntaxPattern.allocVars(3, (Object[])null);
            if(!Lit5.match(var0, var1, 0)) {
               if(!Lit9.match(var0, var1, 0)) {
                  if(Lit10.match(var0, var1, 0)) {
                     return false;
                  }

                  if(syntax_case.error("syntax-case", var0) != Boolean.FALSE) {
                     return true;
                  }

                  return false;
               }

               var2 = true;
               break;
            }

            TemplateScope var4 = TemplateScope.make();
            var3 = std_syntax.isIdentifier(Lit6.execute(var1, var4));
            var2 = var3;
            if(!var3) {
               break;
            }

            var4 = TemplateScope.make();
            var3 = std_syntax.isIdentifier(Lit7.execute(var1, var4));
            var2 = var3;
            if(!var3) {
               break;
            }

            var4 = TemplateScope.make();
            var0 = Lit8.execute(var1, var4);
         }
      }

      return var2;
   }

   static Object lambda2(Object var0) {
      Object[] var1 = SyntaxPattern.allocVars(3, (Object[])null);
      if(Lit22.match(var0, var1, 0)) {
         TemplateScope var3 = TemplateScope.make();
         Expression var4 = SyntaxForms.rewrite(Lit23.execute(var1, var3));
         TemplateScope var2 = TemplateScope.make();
         return new TryExp(var4, SyntaxForms.rewrite(Lit24.execute(var1, var2)));
      } else {
         return syntax_case.error("syntax-case", var0);
      }
   }

   static Object lambda3(Object var0) {
      Object[] var1 = SyntaxPattern.allocVars(3, (Object[])null);
      if(Lit26.match(var0, var1, 0)) {
         TemplateScope var3 = TemplateScope.make();
         Expression var4 = SyntaxForms.rewrite(Lit27.execute(var1, var3));
         TemplateScope var2 = TemplateScope.make();
         return new SynchronizedExp(var4, SyntaxForms.rewriteBody(Lit28.execute(var1, var2)));
      } else {
         return syntax_case.error("syntax-case", var0);
      }
   }

   static Object lambda4(Object var0) {
      Object[] var1 = SyntaxPattern.allocVars(4, (Object[])null);
      TemplateScope var2;
      Object var3;
      Object[] var4;
      if(Lit39.match(var0, var1, 0)) {
         var2 = TemplateScope.make();
         if(isIdentifierPairList(Lit40.execute(var1, var2))) {
            var2 = TemplateScope.make();
            return Quote.append$V(new Object[]{Lit41.execute(var1, var2), Quote.consX$V(new Object[]{lists.cons(lists.cons(import$Mnhandle$Mnrename, Lit42.execute(var1, var2)), Lit43.execute(var1, var2)), Lit44.execute(var1, var2)})});
         } else {
            var2 = TemplateScope.make();
            var3 = Lit45.execute(var1, var2);
            if("invalid \'rename\' clause in import" instanceof Object[]) {
               var4 = (Object[])"invalid \'rename\' clause in import";
            } else {
               var4 = new Object[]{"invalid \'rename\' clause in import"};
            }

            return prim_syntax.syntaxError(var3, var4);
         }
      } else if(Lit46.match(var0, var1, 0)) {
         var2 = TemplateScope.make();
         if(isIdentifierList(Lit47.execute(var1, var2))) {
            var2 = TemplateScope.make();
            return Quote.append$V(new Object[]{Lit48.execute(var1, var2), Quote.consX$V(new Object[]{lists.cons(lists.cons(import$Mnhandle$Mnonly, Lit49.execute(var1, var2)), Lit50.execute(var1, var2)), Lit51.execute(var1, var2)})});
         } else {
            var2 = TemplateScope.make();
            var3 = Lit52.execute(var1, var2);
            if("invalid \'only\' identifier list" instanceof Object[]) {
               var4 = (Object[])"invalid \'only\' identifier list";
            } else {
               var4 = new Object[]{"invalid \'only\' identifier list"};
            }

            return prim_syntax.syntaxError(var3, var4);
         }
      } else if(Lit53.match(var0, var1, 0)) {
         var2 = TemplateScope.make();
         if(isIdentifierList(Lit54.execute(var1, var2))) {
            var2 = TemplateScope.make();
            return Quote.append$V(new Object[]{Lit55.execute(var1, var2), Quote.consX$V(new Object[]{lists.cons(lists.cons(import$Mnhandle$Mnexcept, Lit56.execute(var1, var2)), Lit57.execute(var1, var2)), Lit58.execute(var1, var2)})});
         } else {
            var2 = TemplateScope.make();
            var3 = Lit59.execute(var1, var2);
            if("invalid \'except\' identifier list" instanceof Object[]) {
               var4 = (Object[])"invalid \'except\' identifier list";
            } else {
               var4 = new Object[]{"invalid \'except\' identifier list"};
            }

            return prim_syntax.syntaxError(var3, var4);
         }
      } else if(Lit60.match(var0, var1, 0)) {
         var2 = TemplateScope.make();
         return Quote.append$V(new Object[]{Lit61.execute(var1, var2), Quote.consX$V(new Object[]{lists.cons(lists.cons(import$Mnhandle$Mnprefix, Lit62.execute(var1, var2)), Lit63.execute(var1, var2)), Lit64.execute(var1, var2)})});
      } else if(Lit65.match(var0, var1, 0)) {
         var2 = TemplateScope.make();
         var3 = Lit66.execute(var1, var2);
         if("invalid prefix clause in import" instanceof Object[]) {
            var4 = (Object[])"invalid prefix clause in import";
         } else {
            var4 = new Object[]{"invalid prefix clause in import"};
         }

         return prim_syntax.syntaxError(var3, var4);
      } else if(Lit67.match(var0, var1, 0)) {
         var2 = TemplateScope.make();
         return Pair.make(Lit68.execute(var1, var2), Quote.append$V(new Object[]{Lit69.execute(var1, var2), Quote.consX$V(new Object[]{importMapper(std_syntax.syntaxObject$To$Datum(Lit70.execute(var1, var2))), Lit71.execute(var1, var2)})}));
      } else if(Lit72.match(var0, var1, 0)) {
         var2 = TemplateScope.make();
         return Pair.make(Lit73.execute(var1, var2), Quote.append$V(new Object[]{Lit74.execute(var1, var2), Quote.consX$V(new Object[]{importMapper(std_syntax.syntaxObject$To$Datum(Lit75.execute(var1, var2))), Lit76.execute(var1, var2)})}));
      } else {
         return syntax_case.error("syntax-case", var0);
      }
   }

   static Object lambda5(Object var0) {
      syntax.frame0 var1 = new syntax.frame0();
      var1.$unnamed$1 = SyntaxPattern.allocVars(2, (Object[])null);
      if(Lit82.match(var0, var1.$unnamed$1, 0)) {
         var1.$unnamed$0 = TemplateScope.make();
         return Pair.make(Lit83.execute(var1.$unnamed$1, var1.$unnamed$0), var1.lambda6loop(Lit84.execute(var1.$unnamed$1, var1.$unnamed$0)));
      } else {
         return syntax_case.error("syntax-case", var0);
      }
   }

   static Object lambda7(Object var0) {
      Object[] var1 = SyntaxPattern.allocVars(4, (Object[])null);
      if(Lit94.match(var0, var1, 0)) {
         TemplateScope var2 = TemplateScope.make();
         if(IfFeature.testFeature(Lit95.execute(var1, var2))) {
            var2 = TemplateScope.make();
            return Lit96.execute(var1, var2);
         } else {
            var2 = TemplateScope.make();
            return Lit97.execute(var1, var2);
         }
      } else {
         return syntax_case.error("syntax-case", var0);
      }
   }

   public Object apply1(ModuleMethod var1, Object var2) {
      switch(var1.selector) {
      case 2:
         return lambda2(var2);
      case 3:
         return lambda3(var2);
      case 4:
         if(isIdentifierList(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 5:
         if(isIdentifierPairList(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 6:
      case 7:
      case 8:
      case 9:
      default:
         return super.apply1(var1, var2);
      case 10:
         return importMapper(var2);
      case 11:
         return lambda4(var2);
      case 12:
         return lambda5(var2);
      case 13:
         return lambda7(var2);
      }
   }

   public Object apply2(ModuleMethod var1, Object var2, Object var3) {
      switch(var1.selector) {
      case 6:
         return importHandleOnly(var2, var3);
      case 7:
         return importHandleExcept(var2, var3);
      case 8:
         return importHandlePrefix(var2, var3);
      case 9:
         return importHandleRename(var2, var3);
      default:
         return super.apply2(var1, var2, var3);
      }
   }

   public int match1(ModuleMethod var1, Object var2, CallContext var3) {
      switch(var1.selector) {
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
      case 5:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 6:
      case 7:
      case 8:
      case 9:
      default:
         return super.match1(var1, var2, var3);
      case 10:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 11:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 12:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 13:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      }
   }

   public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
      switch(var1.selector) {
      case 6:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 7:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 8:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 9:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      default:
         return super.match2(var1, var2, var3, var4);
      }
   }

   public final void run(CallContext var1) {
      Consumer var2 = var1.consumer;
   }

   public class frame extends ModuleBody {

      final ModuleMethod lambda$Fn1;
      Object list;


      public frame() {
         ModuleMethod var1 = new ModuleMethod(this, 1, (Object)null, 4097);
         var1.setProperty("source-location", "syntax.scm:83");
         this.lambda$Fn1 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 1?this.lambda1(var2):super.apply1(var1, var2);
      }

      Object lambda1(Object var1) {
         Object var3 = this.list;
         Object var2 = var1;
         var1 = var3;

         while(true) {
            boolean var4;
            if(var2 == null) {
               var4 = true;
            } else {
               var4 = false;
            }

            if(var4) {
               if(var4) {
                  break;
               }
            } else if(lists.isNull(var1)) {
               break;
            }

            var3 = lists.cdr.apply1(var1);
            var2 = Scheme.applyToArgs.apply3(lists.caar.apply1(var1), var2, lists.cdar.apply1(var1));
            var1 = var3;
         }

         return var2;
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

   public class frame0 extends ModuleBody {

      TemplateScope $unnamed$0;
      Object[] $unnamed$1;


      public Object lambda6loop(Object var1) {
         Object[] var2 = SyntaxPattern.allocVars(5, this.$unnamed$1);
         if(syntax.Lit85.match(var1, var2, 0)) {
            return Pair.make(syntax.Lit86.execute(var2, this.$unnamed$0), this.lambda6loop(syntax.Lit87.execute(var2, this.$unnamed$0)));
         } else if(syntax.Lit88.match(var1, var2, 0)) {
            return LList.Empty;
         } else if(syntax.Lit89.match(var1, var2, 0)) {
            Object var4 = syntax.Lit90.execute(var2, this.$unnamed$0);
            Object[] var3;
            if("invalid case-lambda clause" instanceof Object[]) {
               var3 = (Object[])"invalid case-lambda clause";
            } else {
               var3 = new Object[]{"invalid case-lambda clause"};
            }

            return LList.list1(prim_syntax.syntaxError(var4, var3));
         } else {
            return syntax_case.error("syntax-case", var1);
         }
      }
   }
}
