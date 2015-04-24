package kawa.lib;

import gnu.expr.Language;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.expr.QuoteExp;
import gnu.expr.Symbols;
import gnu.kawa.functions.AddOp;
import gnu.kawa.reflect.StaticFieldLocation;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.CallContext;
import gnu.mapping.Location;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import kawa.lang.Eval;
import kawa.lang.Macro;
import kawa.lang.Quote;
import kawa.lang.SyntaxForm;
import kawa.lang.SyntaxForms;
import kawa.lang.SyntaxPattern;
import kawa.lang.SyntaxRule;
import kawa.lang.SyntaxRules;
import kawa.lang.SyntaxTemplate;
import kawa.lang.TemplateScope;
import kawa.lang.Translator;
import kawa.standard.Scheme;
import kawa.standard.syntax_case;

public class std_syntax extends ModuleBody {

   public static final Macro $Prvt$$Pccase;
   public static final Macro $Prvt$$Pccase$Mnmatch;
   public static final Macro $Prvt$$Pcdo$Mninit;
   public static final Macro $Prvt$$Pcdo$Mnlambda1;
   public static final Macro $Prvt$$Pcdo$Mnlambda2;
   public static final Macro $Prvt$$Pcdo$Mnstep;
   public static final Macro $Prvt$$Pclet$Mninit;
   public static final Macro $Prvt$$Pclet$Mnlambda1;
   public static final Macro $Prvt$$Pclet$Mnlambda2;
   public static final Location $Prvt$define;
   public static final Location $Prvt$define$Mnconstant;
   public static final Location $Prvt$if;
   public static final Location $Prvt$letrec;
   public static final std_syntax $instance;
   static final IntNum Lit0;
   static final IntNum Lit1;
   static final SimpleSymbol Lit10;
   static final SyntaxPattern Lit11;
   static final SyntaxPattern Lit12;
   static final SyntaxTemplate Lit13;
   static final SyntaxPattern Lit14;
   static final SyntaxTemplate Lit15;
   static final SimpleSymbol Lit16;
   static final SyntaxPattern Lit17;
   static final SyntaxPattern Lit18;
   static final SyntaxTemplate Lit19;
   static final SimpleSymbol Lit2;
   static final SyntaxPattern Lit20;
   static final SyntaxTemplate Lit21;
   static final SimpleSymbol Lit22;
   static final SyntaxRules Lit23;
   static final SimpleSymbol Lit24;
   static final SyntaxRules Lit25;
   static final SimpleSymbol Lit26;
   static final SyntaxRules Lit27;
   static final SimpleSymbol Lit28;
   static final SyntaxRules Lit29;
   static final SyntaxRules Lit3;
   static final SimpleSymbol Lit30;
   static final SyntaxRules Lit31;
   static final SimpleSymbol Lit32;
   static final SyntaxRules Lit33;
   static final SimpleSymbol Lit34;
   static final SyntaxRules Lit35;
   static final SimpleSymbol Lit36;
   static final SyntaxRules Lit37;
   static final SimpleSymbol Lit38;
   static final SyntaxRules Lit39;
   static final SimpleSymbol Lit4;
   static final SimpleSymbol Lit40;
   static final SyntaxRules Lit41;
   static final SimpleSymbol Lit42;
   static final SyntaxRules Lit43;
   static final SimpleSymbol Lit44;
   static final SyntaxRules Lit45;
   static final SimpleSymbol Lit46;
   static final SimpleSymbol Lit47;
   static final SimpleSymbol Lit48;
   static final SimpleSymbol Lit49;
   static final SyntaxRules Lit5;
   static final SimpleSymbol Lit50;
   static final SimpleSymbol Lit51;
   static final SimpleSymbol Lit52;
   static final SimpleSymbol Lit53;
   static final SimpleSymbol Lit54;
   static final SyntaxPattern Lit55;
   static final SimpleSymbol Lit56;
   static final SyntaxTemplate Lit57;
   static final SyntaxTemplate Lit58;
   static final SimpleSymbol Lit59;
   static final SimpleSymbol Lit6;
   static final SyntaxRules Lit60;
   static final SimpleSymbol Lit61;
   static final SyntaxRules Lit62;
   static final SimpleSymbol Lit63 = (SimpleSymbol)(new SimpleSymbol("syntax-case")).readResolve();
   static final SimpleSymbol Lit64 = (SimpleSymbol)(new SimpleSymbol("::")).readResolve();
   static final SimpleSymbol Lit65 = (SimpleSymbol)(new SimpleSymbol("<gnu.expr.GenericProc>")).readResolve();
   static final SimpleSymbol Lit66 = (SimpleSymbol)(new SimpleSymbol("quote")).readResolve();
   static final SimpleSymbol Lit67 = (SimpleSymbol)(new SimpleSymbol("make")).readResolve();
   static final SimpleSymbol Lit68 = (SimpleSymbol)(new SimpleSymbol("lambda")).readResolve();
   static final SimpleSymbol Lit69 = (SimpleSymbol)(new SimpleSymbol("%syntax-error")).readResolve();
   static final SyntaxRules Lit7;
   static final SimpleSymbol Lit70 = (SimpleSymbol)(new SimpleSymbol("%let")).readResolve();
   static final SimpleSymbol Lit71 = (SimpleSymbol)(new SimpleSymbol("letrec")).readResolve();
   static final SimpleSymbol Lit72 = (SimpleSymbol)(new SimpleSymbol("if")).readResolve();
   static final SimpleSymbol Lit73 = (SimpleSymbol)(new SimpleSymbol("x")).readResolve();
   static final SimpleSymbol Lit74 = (SimpleSymbol)(new SimpleSymbol("eqv?")).readResolve();
   static final SimpleSymbol Lit75 = (SimpleSymbol)(new SimpleSymbol("else")).readResolve();
   static final SimpleSymbol Lit76 = (SimpleSymbol)(new SimpleSymbol("=>")).readResolve();
   static final SimpleSymbol Lit77 = (SimpleSymbol)(new SimpleSymbol("temp")).readResolve();
   static final SimpleSymbol Lit8;
   static final SyntaxRules Lit9;
   public static final Macro and;
   public static final Macro begin$Mnfor$Mnsyntax;
   public static final Macro case;
   public static final Macro cond;
   public static final ModuleMethod datum$Mn$Grsyntax$Mnobject;
   public static final Macro define$Mnfor$Mnsyntax;
   public static final Macro define$Mnprocedure;
   public static final Macro delay;
   public static final Macro do;
   public static final ModuleMethod free$Mnidentifier$Eq$Qu;
   public static final ModuleMethod generate$Mntemporaries;
   public static final ModuleMethod identifier$Qu;
   public static final Macro let;
   public static final Macro let$St;
   public static final Macro or;
   public static final ModuleMethod syntax$Mncolumn;
   public static final ModuleMethod syntax$Mnline;
   public static final ModuleMethod syntax$Mnobject$Mn$Grdatum;
   public static final ModuleMethod syntax$Mnsource;
   public static final Macro with$Mnsyntax;


   static {
      SimpleSymbol var0 = (SimpleSymbol)(new SimpleSymbol("with-syntax")).readResolve();
      Lit61 = var0;
      SyntaxPattern var1 = new SyntaxPattern("\f\u0018\f\b\f\u0007\r\u000f\b\b\b", new Object[0], 2);
      SimpleSymbol var2 = (SimpleSymbol)(new SimpleSymbol("begin")).readResolve();
      Lit56 = var2;
      SyntaxRule var12 = new SyntaxRule(var1, "\u0001\u0003", "\u0011\u0018\u0004\t\u0003\b\r\u000b", new Object[]{var2}, 1);
      SyntaxRule var13 = new SyntaxRule(new SyntaxPattern("\f\u0018<,\f\u0007\f\u000f\b\b\f\u0017\r\u001f\u0018\b\b", new Object[0], 4), "\u0001\u0001\u0001\u0003", "\u0011\u0018\u0004\t\u000b\t\u0010\b\t\u0003\b\u0011\u0018\f\t\u0013\b\u001d\u001b", new Object[]{Lit63, Lit56}, 1);
      SyntaxRule var3 = new SyntaxRule(new SyntaxPattern("\f\u0018L-\f\u0007\f\u000f\b\u0000\u0010\b\f\u0017\r\u001f\u0018\b\b", new Object[0], 4), "\u0003\u0003\u0001\u0003", "\u0011\u0018\u00041\u0011\u0018\f\b\r\u000b\t\u0010\b\u0019\b\u0005\u0003\b\u0011\u0018\u0014\t\u0013\b\u001d\u001b", new Object[]{Lit63, (SimpleSymbol)(new SimpleSymbol("list")).readResolve(), Lit56}, 1);
      Lit62 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var12, var13, var3}, 4);
      var0 = (SimpleSymbol)(new SimpleSymbol("define-for-syntax")).readResolve();
      Lit59 = var0;
      var1 = new SyntaxPattern("\f\u0018\u0003", new Object[0], 1);
      var2 = (SimpleSymbol)(new SimpleSymbol("begin-for-syntax")).readResolve();
      Lit54 = var2;
      var12 = new SyntaxRule(var1, "\u0000", "\u0011\u0018\u0004\b\u0011\u0018\f\u0002", new Object[]{var2, (SimpleSymbol)(new SimpleSymbol("define")).readResolve()}, 0);
      Lit60 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var12}, 1);
      Lit58 = new SyntaxTemplate("\u0001\u0000", "\u0018\u0004", new Object[]{Values.empty}, 0);
      Lit57 = new SyntaxTemplate("\u0001\u0000", "\n", new Object[0], 0);
      Lit55 = new SyntaxPattern("\f\u0007\u000b", new Object[0], 2);
      Lit53 = (SimpleSymbol)(new SimpleSymbol("syntax-column")).readResolve();
      Lit52 = (SimpleSymbol)(new SimpleSymbol("syntax-line")).readResolve();
      Lit51 = (SimpleSymbol)(new SimpleSymbol("syntax-source")).readResolve();
      Lit50 = (SimpleSymbol)(new SimpleSymbol("free-identifier=?")).readResolve();
      Lit49 = (SimpleSymbol)(new SimpleSymbol("identifier?")).readResolve();
      Lit48 = (SimpleSymbol)(new SimpleSymbol("generate-temporaries")).readResolve();
      Lit47 = (SimpleSymbol)(new SimpleSymbol("datum->syntax-object")).readResolve();
      Lit46 = (SimpleSymbol)(new SimpleSymbol("syntax-object->datum")).readResolve();
      var0 = (SimpleSymbol)(new SimpleSymbol("define-procedure")).readResolve();
      Lit44 = var0;
      SimpleSymbol var14 = Lit64;
      var2 = Lit65;
      var3 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\r\u000f\b\b\b", new Object[0], 2), "\u0001\u0003", "\u0011\u0018\u0004Á\u0011\u0018\f\t\u0003\u0011\u0018\u0014\u0011\u0018\u001c\b\u0011\u0018$\u0011\u0018\u001c\b\u0011\u0018,\b\u0003\b\u0011\u00184\t\u0003\u0011\u0018<\b\u0011\u0018D\b\r\u000b", new Object[]{Lit56, (SimpleSymbol)(new SimpleSymbol("define-constant")).readResolve(), Lit64, Lit65, Lit67, Lit66, (SimpleSymbol)(new SimpleSymbol("invoke")).readResolve(), PairWithPosition.make(Lit66, PairWithPosition.make((SimpleSymbol)(new SimpleSymbol("setProperties")).readResolve(), LList.Empty, "std_syntax.scm", 1024020), "std_syntax.scm", 1024020), (SimpleSymbol)(new SimpleSymbol("java.lang.Object[]")).readResolve()}, 1);
      Lit45 = new SyntaxRules(new Object[]{var0, var14, var2}, new SyntaxRule[]{var3}, 2);
      var0 = (SimpleSymbol)(new SimpleSymbol("delay")).readResolve();
      Lit42 = var0;
      var12 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\b", new Object[0], 1), "\u0001", "\u0011\u0018\u0004\u0011\u0018\f\b\u0011\u0018\u0014\t\u0010\b\u0003", new Object[]{Lit67, (SimpleSymbol)(new SimpleSymbol("<kawa.lang.Promise>")).readResolve(), Lit68}, 0);
      Lit43 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var12}, 1);
      var0 = (SimpleSymbol)(new SimpleSymbol("do")).readResolve();
      Lit40 = var0;
      var14 = Lit64;
      SyntaxPattern var15 = new SyntaxPattern("\f\u0018,\r\u0007\u0000\b\b\u001c\f\u000f\u0013\r\u001f\u0018\b\b", new Object[0], 4);
      SimpleSymbol var16 = Lit71;
      SimpleSymbol var4 = (SimpleSymbol)(new SimpleSymbol("%do%loop")).readResolve();
      SimpleSymbol var5 = (SimpleSymbol)(new SimpleSymbol("%do-lambda1")).readResolve();
      Lit36 = var5;
      SimpleSymbol var6 = Lit72;
      SimpleSymbol var7 = (SimpleSymbol)(new SimpleSymbol("not")).readResolve();
      SimpleSymbol var8 = Lit56;
      SimpleSymbol var9 = (SimpleSymbol)(new SimpleSymbol("%do-step")).readResolve();
      Lit32 = var9;
      Values var10 = Values.empty;
      SimpleSymbol var11 = (SimpleSymbol)(new SimpleSymbol("%do-init")).readResolve();
      Lit34 = var11;
      var13 = new SyntaxRule(var15, "\u0003\u0001\u0000\u0003", "\u0011\u0018\u0004Ɖ\b\u0011\u0018\f\b\u0011\u0018\u0014\u0019\b\u0005\u0003\t\u0010\b\u0011\u0018\u001c)\u0011\u0018$\b\u000b\u0081\u0011\u0018,\u0011\u001d\u001b\b\u0011\u0018\f\b\u0005\u0011\u00184\u0003\b\u0011\u0018,\u0011\u0018<\u0012\b\u0011\u0018\f\b\u0005\u0011\u0018D\b\u0003", new Object[]{var16, var4, var5, var6, var7, var8, var9, var10, var11}, 1);
      Lit41 = new SyntaxRules(new Object[]{var0, var14}, new SyntaxRule[]{var13}, 4);
      var0 = (SimpleSymbol)(new SimpleSymbol("%do-lambda2")).readResolve();
      Lit38 = var0;
      var12 = new SyntaxRule(new SyntaxPattern("\f\u0018\u001c\f\u0007\u000b\f\u0017\f\u001f\b", new Object[0], 4), "\u0001\u0000\u0001\u0001", "\u0011\u0018\u0004\t\n\u0019\t\u0003\u0013\b\u001b", new Object[]{Lit38}, 0);
      var13 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\b\f\u0007\f\u000f\b", new Object[0], 2), "\u0001\u0001", "\u0011\u0018\u0004\t\u0003\b\u000b", new Object[]{Lit68}, 0);
      Lit39 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var12, var13}, 4);
      var0 = Lit36;
      var14 = Lit64;
      var13 = new SyntaxRule(new SyntaxPattern("\f\u0018l\\\f\u0007\f\u0002\f\u000f\f\u0017\f\u001f\b#\f/\f7\b", new Object[]{Lit64}, 7), "\u0001\u0001\u0001\u0001\u0000\u0001\u0001", "\u0011\u0018\u0004\t\"I9\t\u0003\u0011\u0018\f\b\u000b+\b3", new Object[]{Lit36, Lit64}, 0);
      var3 = new SyntaxRule(new SyntaxPattern("\f\u0018\\L\f\u0007\f\u0002\f\u000f\f\u0017\b\u001b\f\'\f/\b", new Object[]{Lit64}, 6), "\u0001\u0001\u0001\u0000\u0001\u0001", "\u0011\u0018\u0004\t\u001aI9\t\u0003\u0011\u0018\f\b\u000b#\b+", new Object[]{Lit36, Lit64}, 0);
      SyntaxRule var17 = new SyntaxRule(new SyntaxPattern("\f\u0018L<\f\u0007\f\u000f\f\u0017\b\u001b\f\'\f/\b", new Object[0], 6), "\u0001\u0001\u0001\u0000\u0001\u0001", "\u0011\u0018\u0004\t\u001a\u0019\t\u0003#\b+", new Object[]{Lit36}, 0);
      SyntaxRule var18 = new SyntaxRule(new SyntaxPattern("\f\u0018<,\f\u0007\f\u000f\b\u0013\f\u001f\f\'\b", new Object[0], 5), "\u0001\u0001\u0000\u0001\u0001", "\u0011\u0018\u0004\t\u0012\u0019\t\u0003\u001b\b#", new Object[]{Lit36}, 0);
      SyntaxRule var20 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\b\f\u0007\f\u000f\b", new Object[0], 2), "\u0001\u0001", "\u0011\u0018\u0004\t\u0003\t\u0010\b\u000b", new Object[]{Lit38}, 0);
      Lit37 = new SyntaxRules(new Object[]{var0, var14}, new SyntaxRule[]{var13, var3, var17, var18, var20}, 7);
      var0 = Lit34;
      var14 = Lit64;
      var13 = new SyntaxRule(new SyntaxPattern("\f\u0018\\\f\u0007\f\u0002\f\u000f\f\u0017\f\u001f\b\b", new Object[]{Lit64}, 4), "\u0001\u0001\u0001\u0001", "\u0013", new Object[0], 0);
      var3 = new SyntaxRule(new SyntaxPattern("\f\u0018L\f\u0007\f\u0002\f\u000f\f\u0017\b\b", new Object[]{Lit64}, 3), "\u0001\u0001\u0001", "\u0013", new Object[0], 0);
      var17 = new SyntaxRule(new SyntaxPattern("\f\u0018<\f\u0007\f\u000f\f\u0017\b\b", new Object[0], 3), "\u0001\u0001\u0001", "\u000b", new Object[0], 0);
      var18 = new SyntaxRule(new SyntaxPattern("\f\u0018,\f\u0007\f\u000f\b\b", new Object[0], 2), "\u0001\u0001", "\u000b", new Object[0], 0);
      var20 = new SyntaxRule(new SyntaxPattern("\f\u0018<\f\u0007\f\u000f\f\u0017\b\b", new Object[0], 3), "\u0001\u0001\u0001", "\u0013", new Object[0], 0);
      SyntaxRule var22 = new SyntaxRule(new SyntaxPattern("\f\u0018\u001c\f\u0007\b\b", new Object[0], 1), "\u0001", "\u0018\u0004", new Object[]{PairWithPosition.make(Lit69, PairWithPosition.make("do binding with no value", LList.Empty, "std_syntax.scm", 794643), "std_syntax.scm", 794628)}, 0);
      SyntaxRule var23 = new SyntaxRule(new SyntaxPattern("\f\u0018L\f\u0007\f\u000f\f\u0017\f\u001f\b\b", new Object[0], 4), "\u0001\u0001\u0001\u0001", "\u0018\u0004", new Object[]{PairWithPosition.make(Lit69, PairWithPosition.make("do binding must have syntax: (var [:: type] init [step])", LList.Empty, "std_syntax.scm", 806917), "std_syntax.scm", 802820)}, 0);
      Lit35 = new SyntaxRules(new Object[]{var0, var14}, new SyntaxRule[]{var13, var3, var17, var18, var20, var22, var23}, 4);
      var0 = Lit32;
      var14 = Lit64;
      var13 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u0002\f\u000f\f\u0017\f\u001f\b", new Object[]{Lit64}, 4), "\u0001\u0001\u0001\u0001", "\u001b", new Object[0], 0);
      var3 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u0002\f\u000f\f\u0017\b", new Object[]{Lit64}, 3), "\u0001\u0001\u0001", "\u0003", new Object[0], 0);
      var17 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\f\u0017\b", new Object[0], 3), "\u0001\u0001\u0001", "\u0013", new Object[0], 0);
      var18 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\b", new Object[0], 2), "\u0001\u0001", "\u0003", new Object[0], 0);
      Lit33 = new SyntaxRules(new Object[]{var0, var14}, new SyntaxRule[]{var13, var3, var17, var18}, 4);
      var0 = (SimpleSymbol)(new SimpleSymbol("let*")).readResolve();
      Lit30 = var0;
      var12 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\b\u0003", new Object[0], 1), "\u0000", "\u0011\u0018\u0004\t\u0010\u0002", new Object[]{Lit70}, 0);
      var13 = new SyntaxRule(new SyntaxPattern("\f\u0018\u001c\f\u0007\b\u000b", new Object[0], 2), "\u0001\u0000", "\u0011\u0018\u0004\u0011\b\u0003\n", new Object[]{Lit70}, 0);
      var3 = new SyntaxRule(new SyntaxPattern("\f\u0018\u001c\f\u0007\u000b\u0013", new Object[0], 3), "\u0001\u0000\u0000", "\u0011\u0018\u0004\u0011\b\u0003\b\u0011\u0018\f\t\n\u0012", new Object[]{Lit70, Lit30}, 0);
      var17 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\u000b", new Object[0], 2), "\u0001\u0000", "\u0018\u0004", new Object[]{PairWithPosition.make(Lit69, PairWithPosition.make("invalid bindings list in let*", LList.Empty, "std_syntax.scm", 679943), "std_syntax.scm", 675846)}, 0);
      var18 = new SyntaxRule(new SyntaxPattern("\f\u0018\u0003", new Object[0], 1), "\u0000", "\u0018\u0004", new Object[]{PairWithPosition.make(Lit69, PairWithPosition.make("missing bindings list in let*", LList.Empty, "std_syntax.scm", 692231), "std_syntax.scm", 688134)}, 0);
      Lit31 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var12, var13, var3, var17, var18}, 3);
      var0 = (SimpleSymbol)(new SimpleSymbol("let")).readResolve();
      Lit28 = var0;
      var12 = new SyntaxRule(new SyntaxPattern("\f\u0018,\r\u0007\u0000\b\b\u000b", new Object[0], 2), "\u0003\u0000", "\u0011\u0018\u0004\u0019\b\u0005\u0003\n", new Object[]{Lit70}, 1);
      var15 = new SyntaxPattern("\f\u0018\f\u0007,\r\u000f\b\b\b\u0013", new Object[0], 3);
      var16 = Lit71;
      var4 = (SimpleSymbol)(new SimpleSymbol("%let-lambda1")).readResolve();
      Lit22 = var4;
      var5 = (SimpleSymbol)(new SimpleSymbol("%let-init")).readResolve();
      Lit26 = var5;
      var13 = new SyntaxRule(var15, "\u0001\u0003\u0000", "©\u0011\u0018\u0004y\b\t\u0003\b\u0011\u0018\f\u0019\b\r\u000b\t\u0010\b\u0012\b\u0003\b\r\u0011\u0018\u0014\b\u000b", new Object[]{var16, var4, var5}, 1);
      Lit29 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var12, var13}, 3);
      var0 = Lit26;
      var14 = Lit64;
      var13 = new SyntaxRule(new SyntaxPattern("\f\u0018,\f\u0007\f\u000f\b\b", new Object[0], 2), "\u0001\u0001", "\u000b", new Object[0], 0);
      var3 = new SyntaxRule(new SyntaxPattern("\f\u0018L\f\u0007\f\u0002\f\u000f\f\u0017\b\b", new Object[]{Lit64}, 3), "\u0001\u0001\u0001", "\u0013", new Object[0], 0);
      var17 = new SyntaxRule(new SyntaxPattern("\f\u0018<\f\u0007\f\u000f\f\u0017\b\b", new Object[0], 3), "\u0001\u0001\u0001", "\u0013", new Object[0], 0);
      var18 = new SyntaxRule(new SyntaxPattern("\f\u0018\u001c\f\u0007\b\b", new Object[0], 1), "\u0001", "\u0018\u0004", new Object[]{PairWithPosition.make(Lit69, PairWithPosition.make("let binding with no value", LList.Empty, "std_syntax.scm", 552979), "std_syntax.scm", 552964)}, 0);
      var20 = new SyntaxRule(new SyntaxPattern("\f\u0018L\f\u0007\f\u000f\f\u0017\f\u001f\b\b", new Object[0], 4), "\u0001\u0001\u0001\u0001", "\u0018\u0004", new Object[]{PairWithPosition.make(Lit69, PairWithPosition.make("let binding must have syntax: (var [type] init)", LList.Empty, "std_syntax.scm", 565253), "std_syntax.scm", 561156)}, 0);
      Lit27 = new SyntaxRules(new Object[]{var0, var14}, new SyntaxRule[]{var13, var3, var17, var18, var20}, 4);
      var0 = (SimpleSymbol)(new SimpleSymbol("%let-lambda2")).readResolve();
      Lit24 = var0;
      var12 = new SyntaxRule(new SyntaxPattern("\f\u0018\u001c\f\u0007\u000b\f\u0017\f\u001f\b", new Object[0], 4), "\u0001\u0000\u0001\u0001", "\u0011\u0018\u0004\t\n\u0019\t\u0003\u0013\b\u001b", new Object[]{Lit24}, 0);
      var13 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\b\f\u0007\f\u000f\b", new Object[0], 2), "\u0001\u0001", "\u0011\u0018\u0004\t\u0003\u000b", new Object[]{Lit68}, 0);
      Lit25 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var12, var13}, 4);
      var0 = Lit22;
      var14 = Lit64;
      var13 = new SyntaxRule(new SyntaxPattern("\f\u0018L<\f\u0007\f\u000f\f\u0017\b\u001b\f\'\f/\b", new Object[0], 6), "\u0001\u0001\u0001\u0000\u0001\u0001", "\u0011\u0018\u0004\t\u001a1!\t\u0003\b\u000b#\b+", new Object[]{Lit22}, 0);
      var3 = new SyntaxRule(new SyntaxPattern("\f\u0018\\L\f\u0007\f\u0002\f\u000f\f\u0017\b\u001b\f\'\f/\b", new Object[]{Lit64}, 6), "\u0001\u0001\u0001\u0000\u0001\u0001", "\u0011\u0018\u0004\t\u001a1!\t\u0003\b\u000b#\b+", new Object[]{Lit22}, 0);
      var17 = new SyntaxRule(new SyntaxPattern("\f\u0018<,\f\u0007\f\u000f\b\u0013\f\u001f\f\'\b", new Object[0], 5), "\u0001\u0001\u0000\u0001\u0001", "\u0011\u0018\u0004\t\u0012\u0019\t\u0003\u001b\b#", new Object[]{Lit22}, 0);
      var18 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\b\f\u0007\f\u000f\b", new Object[0], 2), "\u0001\u0001", "\u0011\u0018\u0004\t\u0003\t\u0010\b\u000b", new Object[]{Lit24}, 0);
      Lit23 = new SyntaxRules(new Object[]{var0, var14}, new SyntaxRule[]{var13, var3, var17, var18}, 6);
      Lit21 = new SyntaxTemplate("\u0001\u0001\u0003", "\u0011\u0018\u00041\b\u0011\u0018\f\b\u000b\b\u0011\u0018\u0014\u0011\u0018\f\u0011\u0018\f\b\t\u0003\b\u0015\u0013", new Object[]{Lit70, Lit73, Lit72}, 1);
      Lit20 = new SyntaxPattern("\f\u0007\f\u000f\r\u0017\u0010\b\b", new Object[0], 3);
      Lit19 = new SyntaxTemplate("\u0001\u0001", "\u000b", new Object[0], 0);
      Lit18 = new SyntaxPattern("\f\u0007\f\u000f\b", new Object[0], 2);
      Lit17 = new SyntaxPattern("\f\u0007\b", new Object[0], 1);
      Lit16 = (SimpleSymbol)(new SimpleSymbol("or")).readResolve();
      Lit15 = new SyntaxTemplate("\u0001\u0001\u0003", "\u0011\u0018\u00041\b\u0011\u0018\f\b\u000b\b\u0011\u0018\u0014\u0011\u0018\f)\t\u0003\b\u0015\u0013\u0018\u001c", new Object[]{Lit70, Lit73, Lit72, PairWithPosition.make(Lit73, LList.Empty, "std_syntax.scm", 385052)}, 1);
      Lit14 = new SyntaxPattern("\f\u0007\f\u000f\r\u0017\u0010\b\b", new Object[0], 3);
      Lit13 = new SyntaxTemplate("\u0001\u0001", "\u000b", new Object[0], 0);
      Lit12 = new SyntaxPattern("\f\u0007\f\u000f\b", new Object[0], 2);
      Lit11 = new SyntaxPattern("\f\u0007\b", new Object[0], 1);
      Lit10 = (SimpleSymbol)(new SimpleSymbol("and")).readResolve();
      var0 = (SimpleSymbol)(new SimpleSymbol("%case-match")).readResolve();
      Lit8 = var0;
      var12 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\b", new Object[0], 2), "\u0001\u0001", "\u0011\u0018\u0004\t\u0003\b\u0011\u0018\f\b\u000b", new Object[]{Lit74, Lit66}, 0);
      var13 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\r\u0017\u0010\b\b", new Object[0], 3), "\u0001\u0001\u0003", "\u0011\u0018\u0004Y\u0011\u0018\f\t\u0003\b\u0011\u0018\u0014\b\u000b\b\u0011\u0018\u001c\t\u0003\b\u0015\u0013", new Object[]{Lit16, Lit74, Lit66, Lit8}, 1);
      Lit9 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var12, var13}, 3);
      var0 = (SimpleSymbol)(new SimpleSymbol("%case")).readResolve();
      Lit6 = var0;
      var14 = Lit75;
      var13 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007<\f\u0002\r\u000f\b\b\b\b", new Object[]{Lit75}, 2), "\u0001\u0003", "\u0011\u0018\u0004\b\r\u000b", new Object[]{Lit56}, 1);
      var3 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007<\f\u0002\r\u000f\b\b\b\u0013", new Object[]{Lit75}, 3), "\u0001\u0003\u0000", "\u0018\u0004", new Object[]{PairWithPosition.make(Lit69, PairWithPosition.make("junk following else (in case)", LList.Empty, "std_syntax.scm", 241674), "std_syntax.scm", 237577)}, 0);
      var17 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\\,\r\u000f\b\b\b\r\u0017\u0010\b\b\b", new Object[0], 3), "\u0001\u0003\u0003", "\u0011\u0018\u0004A\u0011\u0018\f\t\u0003\b\r\u000b\b\u0011\u0018\u0014\b\u0015\u0013", new Object[]{Lit72, Lit8, Lit56}, 1);
      var18 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\\,\r\u000f\b\b\b\r\u0017\u0010\b\b\f\u001f\r\' \b\b", new Object[0], 5), "\u0001\u0003\u0003\u0001\u0003", "\u0011\u0018\u0004A\u0011\u0018\f\t\u0003\b\r\u000b1\u0011\u0018\u0014\b\u0015\u0013\b\u0011\u0018\u001c\t\u0003\t\u001b\b%#", new Object[]{Lit72, Lit8, Lit56, Lit6}, 1);
      Lit7 = new SyntaxRules(new Object[]{var0, var14}, new SyntaxRule[]{var13, var3, var17, var18}, 5);
      var0 = (SimpleSymbol)(new SimpleSymbol("case")).readResolve();
      Lit4 = var0;
      var12 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\r\u000f\b\b\b", new Object[0], 2), "\u0001\u0003", "\u0011\u0018\u00041\b\u0011\u0018\f\b\u0003\b\u0011\u0018\u0014\u0011\u0018\f\b\r\u000b", new Object[]{Lit70, (SimpleSymbol)(new SimpleSymbol("tmp")).readResolve(), Lit6}, 1);
      Lit5 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var12}, 2);
      var0 = (SimpleSymbol)(new SimpleSymbol("cond")).readResolve();
      Lit2 = var0;
      var14 = Lit75;
      var2 = Lit76;
      var3 = new SyntaxRule(new SyntaxPattern("\f\u0018L\f\u0002\f\u0007\r\u000f\b\b\b\b", new Object[]{Lit75}, 2), "\u0001\u0003", "\u0011\u0018\u0004\t\u0003\b\r\u000b", new Object[]{Lit56}, 1);
      var17 = new SyntaxRule(new SyntaxPattern("\f\u0018L\f\u0002\f\u0007\r\u000f\b\b\b\r\u0017\u0010\b\b", new Object[]{Lit75}, 3), "\u0001\u0003\u0003", "\u0018\u0004", new Object[]{PairWithPosition.make(Lit69, PairWithPosition.make("else clause must be last clause of cond", LList.Empty, "std_syntax.scm", 86035), "std_syntax.scm", 86020)}, 0);
      var18 = new SyntaxRule(new SyntaxPattern("\f\u0018<\f\u0007\f\u0002\f\u000f\b\b", new Object[]{Lit76}, 2), "\u0001\u0001", "\u0011\u0018\u00041\b\u0011\u0018\f\b\u0003\b\u0011\u0018\u0014\u0011\u0018\f\b\t\u000b\u0018\u001c", new Object[]{Lit70, Lit77, Lit72, PairWithPosition.make(Lit77, LList.Empty, "std_syntax.scm", 102423)}, 0);
      var20 = new SyntaxRule(new SyntaxPattern("\f\u0018<\f\u0007\f\u0002\f\u000f\b\f\u0017\r\u001f\u0018\b\b", new Object[]{Lit76}, 4), "\u0001\u0001\u0001\u0003", "\u0011\u0018\u00041\b\u0011\u0018\f\b\u0003\b\u0011\u0018\u0014\u0011\u0018\f!\t\u000b\u0018\u001c\b\u0011\u0018$\t\u0013\b\u001d\u001b", new Object[]{Lit70, Lit77, Lit72, PairWithPosition.make(Lit77, LList.Empty, "std_syntax.scm", 122898), Lit2}, 1);
      var22 = new SyntaxRule(new SyntaxPattern("\f\u0018\u001c\f\u0007\b\b", new Object[0], 1), "\u0001", "\u0003", new Object[0], 0);
      var23 = new SyntaxRule(new SyntaxPattern("\f\u0018\u001c\f\u0007\b\f\u000f\r\u0017\u0010\b\b", new Object[0], 3), "\u0001\u0001\u0003", "\u0011\u0018\u0004\t\u0003\b\u0011\u0018\f\t\u000b\b\u0015\u0013", new Object[]{Lit16, Lit2}, 1);
      SyntaxRule var24 = new SyntaxRule(new SyntaxPattern("\f\u0018L\f\u0007\f\u000f\r\u0017\u0010\b\b\b", new Object[0], 3), "\u0001\u0001\u0003", "\u0011\u0018\u0004\t\u0003\b\u0011\u0018\f\t\u000b\b\u0015\u0013", new Object[]{Lit72, Lit56}, 1);
      SyntaxRule var25 = new SyntaxRule(new SyntaxPattern("\f\u0018L\f\u0007\f\u000f\r\u0017\u0010\b\b\f\u001f\r\' \b\b", new Object[0], 5), "\u0001\u0001\u0003\u0001\u0003", "\u0011\u0018\u0004\t\u0003A\u0011\u0018\f\t\u000b\b\u0015\u0013\b\u0011\u0018\u0014\t\u001b\b%#", new Object[]{Lit72, Lit56, Lit2}, 1);
      Lit3 = new SyntaxRules(new Object[]{var0, var14, var2}, new SyntaxRule[]{var3, var17, var18, var20, var22, var23, var24, var25}, 5);
      Lit1 = IntNum.make(1);
      Lit0 = IntNum.make(0);
      $instance = new std_syntax();
      $Prvt$define = StaticFieldLocation.make("kawa.lib.prim_syntax", "define");
      $Prvt$define$Mnconstant = StaticFieldLocation.make("kawa.lib.prim_syntax", "define$Mnconstant");
      $Prvt$if = StaticFieldLocation.make("kawa.lib.prim_syntax", "if");
      $Prvt$letrec = StaticFieldLocation.make("kawa.lib.prim_syntax", "letrec");
      cond = Macro.make(Lit2, Lit3, $instance);
      case = Macro.make(Lit4, Lit5, $instance);
      $Prvt$$Pccase = Macro.make(Lit6, Lit7, $instance);
      $Prvt$$Pccase$Mnmatch = Macro.make(Lit8, Lit9, $instance);
      var14 = Lit10;
      std_syntax var19 = $instance;
      and = Macro.make(var14, new ModuleMethod(var19, 1, (Object)null, 4097), $instance);
      or = Macro.make(Lit16, new ModuleMethod(var19, 2, (Object)null, 4097), $instance);
      $Prvt$$Pclet$Mnlambda1 = Macro.make(Lit22, Lit23, $instance);
      $Prvt$$Pclet$Mnlambda2 = Macro.make(Lit24, Lit25, $instance);
      $Prvt$$Pclet$Mninit = Macro.make(Lit26, Lit27, $instance);
      let = Macro.make(Lit28, Lit29, $instance);
      let$St = Macro.make(Lit30, Lit31, $instance);
      $Prvt$$Pcdo$Mnstep = Macro.make(Lit32, Lit33, $instance);
      $Prvt$$Pcdo$Mninit = Macro.make(Lit34, Lit35, $instance);
      $Prvt$$Pcdo$Mnlambda1 = Macro.make(Lit36, Lit37, $instance);
      $Prvt$$Pcdo$Mnlambda2 = Macro.make(Lit38, Lit39, $instance);
      do = Macro.make(Lit40, Lit41, $instance);
      delay = Macro.make(Lit42, Lit43, $instance);
      define$Mnprocedure = Macro.make(Lit44, Lit45, $instance);
      syntax$Mnobject$Mn$Grdatum = new ModuleMethod(var19, 3, Lit46, 4097);
      datum$Mn$Grsyntax$Mnobject = new ModuleMethod(var19, 4, Lit47, 8194);
      generate$Mntemporaries = new ModuleMethod(var19, 5, Lit48, 4097);
      identifier$Qu = new ModuleMethod(var19, 6, Lit49, 4097);
      free$Mnidentifier$Eq$Qu = new ModuleMethod(var19, 7, Lit50, 8194);
      syntax$Mnsource = new ModuleMethod(var19, 8, Lit51, 4097);
      syntax$Mnline = new ModuleMethod(var19, 9, Lit52, 4097);
      syntax$Mncolumn = new ModuleMethod(var19, 10, Lit53, 4097);
      var14 = Lit54;
      ModuleMethod var21 = new ModuleMethod(var19, 11, (Object)null, 4097);
      var21.setProperty("source-location", "std_syntax.scm:298");
      begin$Mnfor$Mnsyntax = Macro.make(var14, var21, $instance);
      define$Mnfor$Mnsyntax = Macro.make(Lit59, Lit60, $instance);
      with$Mnsyntax = Macro.make(Lit61, Lit62, $instance);
      $instance.run();
   }

   public std_syntax() {
      ModuleInfo.register(this);
   }

   public static Object datum$To$SyntaxObject(Object var0, Object var1) {
      return SyntaxForms.makeWithTemplate(var0, var1);
   }

   public static Object generateTemporaries(Object var0) {
      Object var2 = Integer.valueOf(Translator.listLength(var0));

      Object var1;
      for(var1 = LList.Empty; Scheme.numEqu.apply2(var2, Lit0) == Boolean.FALSE; var1 = new Pair(datum$To$SyntaxObject(var0, Symbols.gentemp()), var1)) {
         var2 = AddOp.$Mn.apply2(var2, Lit1);
      }

      return var1;
   }

   public static boolean isFreeIdentifier$Eq(Object var0, Object var1) {
      SyntaxForm var2;
      try {
         var2 = (SyntaxForm)var0;
      } catch (ClassCastException var4) {
         throw new WrongType(var4, "kawa.lang.SyntaxForms.freeIdentifierEquals(kawa.lang.SyntaxForm,kawa.lang.SyntaxForm)", 1, var0);
      }

      SyntaxForm var5;
      try {
         var5 = (SyntaxForm)var1;
      } catch (ClassCastException var3) {
         throw new WrongType(var3, "kawa.lang.SyntaxForms.freeIdentifierEquals(kawa.lang.SyntaxForm,kawa.lang.SyntaxForm)", 2, var1);
      }

      return SyntaxForms.freeIdentifierEquals(var2, var5);
   }

   public static boolean isIdentifier(Object var0) {
      boolean var2 = var0 instanceof Symbol;
      if(!var2) {
         boolean var3 = var0 instanceof SyntaxForm;
         var2 = var3;
         if(var3) {
            SyntaxForm var1;
            try {
               var1 = (SyntaxForm)var0;
            } catch (ClassCastException var4) {
               throw new WrongType(var4, "kawa.lang.SyntaxForms.isIdentifier(kawa.lang.SyntaxForm)", 1, var0);
            }

            return SyntaxForms.isIdentifier(var1);
         }
      }

      return var2;
   }

   static Object lambda1(Object var0) {
      Object[] var1 = SyntaxPattern.allocVars(3, (Object[])null);
      if(Lit11.match(var0, var1, 0)) {
         return new QuoteExp(Language.getDefaultLanguage().booleanObject(true));
      } else {
         TemplateScope var2;
         if(Lit12.match(var0, var1, 0)) {
            var2 = TemplateScope.make();
            return Lit13.execute(var1, var2);
         } else if(Lit14.match(var0, var1, 0)) {
            var2 = TemplateScope.make();
            return Lit15.execute(var1, var2);
         } else {
            return syntax_case.error("syntax-case", var0);
         }
      }
   }

   static Object lambda2(Object var0) {
      Object[] var1 = SyntaxPattern.allocVars(3, (Object[])null);
      if(Lit17.match(var0, var1, 0)) {
         return new QuoteExp(Language.getDefaultLanguage().booleanObject(false));
      } else {
         TemplateScope var2;
         if(Lit18.match(var0, var1, 0)) {
            var2 = TemplateScope.make();
            return Lit19.execute(var1, var2);
         } else if(Lit20.match(var0, var1, 0)) {
            var2 = TemplateScope.make();
            return Lit21.execute(var1, var2);
         } else {
            return syntax_case.error("syntax-case", var0);
         }
      }
   }

   static Object lambda3(Object var0) {
      Object[] var1 = SyntaxPattern.allocVars(2, (Object[])null);
      if(Lit55.match(var0, var1, 0)) {
         Eval var2 = Eval.eval;
         SimpleSymbol var3 = Lit56;
         TemplateScope var4 = TemplateScope.make();
         if(var2.apply1(syntaxObject$To$Datum(new Pair(var3, Lit57.execute(var1, var4)))) != Boolean.FALSE) {
            TemplateScope var5 = TemplateScope.make();
            return Lit58.execute(var1, var5);
         }
      }

      return syntax_case.error("syntax-case", var0);
   }

   public static Object syntaxColumn(Object var0) {
      return var0 instanceof SyntaxForm?syntaxLine(((SyntaxForm)var0).getDatum()):(var0 instanceof PairWithPosition?Integer.valueOf(((PairWithPosition)var0).getColumnNumber() + 0):Boolean.FALSE);
   }

   public static Object syntaxLine(Object var0) {
      return var0 instanceof SyntaxForm?syntaxLine(((SyntaxForm)var0).getDatum()):(var0 instanceof PairWithPosition?Integer.valueOf(((PairWithPosition)var0).getLineNumber()):Boolean.FALSE);
   }

   public static Object syntaxObject$To$Datum(Object var0) {
      return Quote.quote(var0);
   }

   public static Object syntaxSource(Object var0) {
      if(var0 instanceof SyntaxForm) {
         var0 = syntaxSource(((SyntaxForm)var0).getDatum());
      } else {
         if(!(var0 instanceof PairWithPosition)) {
            return Boolean.FALSE;
         }

         String var1 = ((PairWithPosition)var0).getFileName();
         var0 = var1;
         if(var1 == null) {
            return Boolean.FALSE;
         }
      }

      return var0;
   }

   public Object apply1(ModuleMethod var1, Object var2) {
      switch(var1.selector) {
      case 1:
         return lambda1(var2);
      case 2:
         return lambda2(var2);
      case 3:
         return syntaxObject$To$Datum(var2);
      case 4:
      case 7:
      default:
         return super.apply1(var1, var2);
      case 5:
         return generateTemporaries(var2);
      case 6:
         if(isIdentifier(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 8:
         return syntaxSource(var2);
      case 9:
         return syntaxLine(var2);
      case 10:
         return syntaxColumn(var2);
      case 11:
         return lambda3(var2);
      }
   }

   public Object apply2(ModuleMethod var1, Object var2, Object var3) {
      switch(var1.selector) {
      case 4:
         return datum$To$SyntaxObject(var2, var3);
      case 5:
      case 6:
      default:
         return super.apply2(var1, var2, var3);
      case 7:
         return isFreeIdentifier$Eq(var2, var3)?Boolean.TRUE:Boolean.FALSE;
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
      case 7:
      default:
         return super.match1(var1, var2, var3);
      case 5:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 6:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 8:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 9:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
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
      }
   }

   public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
      switch(var1.selector) {
      case 4:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 5:
      case 6:
      default:
         return super.match2(var1, var2, var3, var4);
      case 7:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      }
   }

   public final void run(CallContext var1) {
      Consumer var2 = var1.consumer;
   }
}
