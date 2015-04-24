package gnu.kawa.slib;

import gnu.expr.GenericProc;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.AddOp;
import gnu.kawa.functions.ApplyToArgs;
import gnu.kawa.functions.IsEqual;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.CallContext;
import gnu.mapping.InPort;
import gnu.mapping.OutPort;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import gnu.text.Path;
import kawa.lang.Eval;
import kawa.lang.Macro;
import kawa.lang.SyntaxPattern;
import kawa.lang.SyntaxRule;
import kawa.lang.SyntaxRules;
import kawa.lang.SyntaxTemplate;
import kawa.lang.TemplateScope;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.lib.numbers;
import kawa.lib.parameters;
import kawa.lib.ports;
import kawa.lib.std_syntax;
import kawa.lib.strings;
import kawa.standard.Scheme;
import kawa.standard.readchar;
import kawa.standard.syntax_case;

public class testing extends ModuleBody {

   public static final ModuleMethod $Pctest$Mnbegin;
   static final ModuleMethod $Pctest$Mnnull$Mncallback;
   public static final ModuleMethod $Prvt$$Pctest$Mnapproximimate$Eq;
   public static final ModuleMethod $Prvt$$Pctest$Mnas$Mnspecifier;
   public static final Macro $Prvt$$Pctest$Mncomp1body;
   public static final Macro $Prvt$$Pctest$Mncomp2body;
   public static final ModuleMethod $Prvt$$Pctest$Mnend;
   public static final Macro $Prvt$$Pctest$Mnerror;
   public static final Macro $Prvt$$Pctest$Mnevaluate$Mnwith$Mncatch;
   public static final ModuleMethod $Prvt$$Pctest$Mnmatch$Mnall;
   public static final ModuleMethod $Prvt$$Pctest$Mnmatch$Mnany;
   public static final ModuleMethod $Prvt$$Pctest$Mnmatch$Mnnth;
   public static final ModuleMethod $Prvt$$Pctest$Mnon$Mntest$Mnbegin;
   public static final ModuleMethod $Prvt$$Pctest$Mnon$Mntest$Mnend;
   public static final ModuleMethod $Prvt$$Pctest$Mnreport$Mnresult;
   public static final ModuleMethod $Prvt$$Pctest$Mnrunner$Mnfail$Mnlist;
   public static final ModuleMethod $Prvt$$Pctest$Mnrunner$Mnfail$Mnlist$Ex;
   public static final ModuleMethod $Prvt$$Pctest$Mnrunner$Mnskip$Mnlist;
   public static final ModuleMethod $Prvt$$Pctest$Mnrunner$Mnskip$Mnlist$Ex;
   public static final ModuleMethod $Prvt$$Pctest$Mnshould$Mnexecute;
   public static final Macro $Prvt$test$Mngroup;
   public static final testing $instance;
   static final IntNum Lit0;
   static final SimpleSymbol Lit1;
   static final PairWithPosition Lit10;
   static final SyntaxPattern Lit100;
   static final SyntaxTemplate Lit101;
   static final SyntaxPattern Lit102;
   static final SyntaxTemplate Lit103;
   static final SimpleSymbol Lit104;
   static final SyntaxTemplate Lit105;
   static final SimpleSymbol Lit106;
   static final SyntaxTemplate Lit107;
   static final SimpleSymbol Lit108;
   static final SyntaxTemplate Lit109;
   static final PairWithPosition Lit11;
   static final SimpleSymbol Lit110;
   static final SyntaxPattern Lit111;
   static final SyntaxTemplate Lit112;
   static final SyntaxPattern Lit113;
   static final SyntaxTemplate Lit114;
   static final SimpleSymbol Lit115;
   static final SyntaxRules Lit116;
   static final SimpleSymbol Lit117;
   static final SyntaxPattern Lit118;
   static final SyntaxTemplate Lit119;
   static final SimpleSymbol Lit12;
   static final SyntaxPattern Lit120;
   static final SyntaxTemplate Lit121;
   static final SyntaxPattern Lit122;
   static final SyntaxTemplate Lit123;
   static final SimpleSymbol Lit124;
   static final SimpleSymbol Lit125;
   static final SyntaxRules Lit126;
   static final SimpleSymbol Lit127;
   static final SimpleSymbol Lit128;
   static final SyntaxRules Lit129;
   static final IntNum Lit13;
   static final SimpleSymbol Lit130;
   static final SimpleSymbol Lit131;
   static final SyntaxRules Lit132;
   static final SimpleSymbol Lit133;
   static final SimpleSymbol Lit134;
   static final SyntaxRules Lit135;
   static final SimpleSymbol Lit136;
   static final SimpleSymbol Lit137;
   static final SyntaxRules Lit138;
   static final SimpleSymbol Lit139;
   static final SimpleSymbol Lit14;
   static final SyntaxRules Lit140;
   static final SimpleSymbol Lit141 = (SimpleSymbol)(new SimpleSymbol("test-match-name")).readResolve();
   static final SimpleSymbol Lit142 = (SimpleSymbol)(new SimpleSymbol("test-read-eval-string")).readResolve();
   static final SimpleSymbol Lit143 = (SimpleSymbol)(new SimpleSymbol("runner")).readResolve();
   static final SimpleSymbol Lit144 = (SimpleSymbol)(new SimpleSymbol("let")).readResolve();
   static final SimpleSymbol Lit145 = (SimpleSymbol)(new SimpleSymbol("cons")).readResolve();
   static final SimpleSymbol Lit146 = (SimpleSymbol)(new SimpleSymbol("test-runner-current")).readResolve();
   static final SimpleSymbol Lit147 = (SimpleSymbol)(new SimpleSymbol("lambda")).readResolve();
   static final SimpleSymbol Lit148 = (SimpleSymbol)(new SimpleSymbol("saved-runner")).readResolve();
   static final SimpleSymbol Lit149 = (SimpleSymbol)(new SimpleSymbol("r")).readResolve();
   static final SimpleSymbol Lit15;
   static final SimpleSymbol Lit150 = (SimpleSymbol)(new SimpleSymbol("let*")).readResolve();
   static final SimpleSymbol Lit151 = (SimpleSymbol)(new SimpleSymbol("ex")).readResolve();
   static final SimpleSymbol Lit152 = (SimpleSymbol)(new SimpleSymbol("expected-error")).readResolve();
   static final SimpleSymbol Lit153 = (SimpleSymbol)(new SimpleSymbol("et")).readResolve();
   static final SimpleSymbol Lit154 = (SimpleSymbol)(new SimpleSymbol("try-catch")).readResolve();
   static final SimpleSymbol Lit155 = (SimpleSymbol)(new SimpleSymbol("actual-value")).readResolve();
   static final SimpleSymbol Lit156 = (SimpleSymbol)(new SimpleSymbol("<java.lang.Throwable>")).readResolve();
   static final SimpleSymbol Lit157 = (SimpleSymbol)(new SimpleSymbol("actual-error")).readResolve();
   static final SimpleSymbol Lit158 = (SimpleSymbol)(new SimpleSymbol("cond")).readResolve();
   static final SimpleSymbol Lit159 = (SimpleSymbol)(new SimpleSymbol("instance?")).readResolve();
   static final SyntaxPattern Lit16;
   static final SimpleSymbol Lit160 = (SimpleSymbol)(new SimpleSymbol("name")).readResolve();
   static final SimpleSymbol Lit161 = (SimpleSymbol)(new SimpleSymbol("if")).readResolve();
   static final SimpleSymbol Lit162 = (SimpleSymbol)(new SimpleSymbol("res")).readResolve();
   static final SimpleSymbol Lit163 = (SimpleSymbol)(new SimpleSymbol("exp")).readResolve();
   static final SimpleSymbol Lit164 = (SimpleSymbol)(new SimpleSymbol("p")).readResolve();
   static final SimpleSymbol Lit165 = (SimpleSymbol)(new SimpleSymbol("dynamic-wind")).readResolve();
   static final SyntaxTemplate Lit17;
   static final SyntaxPattern Lit18;
   static final SyntaxTemplate Lit19;
   static final SimpleSymbol Lit2;
   static final SimpleSymbol Lit20;
   static final SimpleSymbol Lit21;
   static final SimpleSymbol Lit22;
   static final SimpleSymbol Lit23;
   static final SimpleSymbol Lit24;
   static final SimpleSymbol Lit25;
   static final SimpleSymbol Lit26;
   static final SimpleSymbol Lit27;
   static final SimpleSymbol Lit28;
   static final SimpleSymbol Lit29;
   static final SimpleSymbol Lit3;
   static final SimpleSymbol Lit30;
   static final SimpleSymbol Lit31;
   static final SimpleSymbol Lit32;
   static final SimpleSymbol Lit33;
   static final SimpleSymbol Lit34;
   static final SimpleSymbol Lit35;
   static final SimpleSymbol Lit36;
   static final SimpleSymbol Lit37;
   static final SimpleSymbol Lit38;
   static final SimpleSymbol Lit39;
   static final SimpleSymbol Lit4;
   static final SimpleSymbol Lit40;
   static final SimpleSymbol Lit41;
   static final SimpleSymbol Lit42;
   static final SimpleSymbol Lit43;
   static final SimpleSymbol Lit44;
   static final SimpleSymbol Lit45;
   static final SimpleSymbol Lit46;
   static final SimpleSymbol Lit47;
   static final SimpleSymbol Lit48;
   static final SimpleSymbol Lit49;
   static final SimpleSymbol Lit5;
   static final SimpleSymbol Lit50;
   static final SimpleSymbol Lit51;
   static final SimpleSymbol Lit52;
   static final SimpleSymbol Lit53;
   static final SimpleSymbol Lit54;
   static final SimpleSymbol Lit55;
   static final SimpleSymbol Lit56;
   static final SimpleSymbol Lit57;
   static final SimpleSymbol Lit58;
   static final SimpleSymbol Lit59;
   static final SimpleSymbol Lit6;
   static final SimpleSymbol Lit60;
   static final SimpleSymbol Lit61;
   static final SimpleSymbol Lit62;
   static final SimpleSymbol Lit63;
   static final SimpleSymbol Lit64;
   static final SimpleSymbol Lit65;
   static final SimpleSymbol Lit66;
   static final SimpleSymbol Lit67;
   static final SimpleSymbol Lit68;
   static final SimpleSymbol Lit69;
   static final SimpleSymbol Lit7;
   static final SimpleSymbol Lit70;
   static final SyntaxRules Lit71;
   static final SimpleSymbol Lit72;
   static final SyntaxRules Lit73;
   static final SimpleSymbol Lit74;
   static final SimpleSymbol Lit75;
   static final SyntaxRules Lit76;
   static final SimpleSymbol Lit77;
   static final SimpleSymbol Lit78;
   static final SimpleSymbol Lit79;
   static final PairWithPosition Lit8;
   static final SimpleSymbol Lit80;
   static final SimpleSymbol Lit81;
   static final SimpleSymbol Lit82;
   static final SimpleSymbol Lit83;
   static final SimpleSymbol Lit84;
   static final SyntaxRules Lit85;
   static final SimpleSymbol Lit86;
   static final SimpleSymbol Lit87;
   static final SimpleSymbol Lit88;
   static final SimpleSymbol Lit89;
   static final SimpleSymbol Lit9;
   static final SyntaxRules Lit90;
   static final SimpleSymbol Lit91;
   static final SimpleSymbol Lit92;
   static final SyntaxRules Lit93;
   static final SimpleSymbol Lit94;
   static final SyntaxPattern Lit95;
   static final SyntaxTemplate Lit96;
   static final SyntaxPattern Lit97;
   static final SyntaxTemplate Lit98;
   static final SimpleSymbol Lit99;
   static final ModuleMethod lambda$Fn1;
   static final ModuleMethod lambda$Fn2;
   static final ModuleMethod lambda$Fn3;
   public static final ModuleMethod test$Mnapply;
   public static final Macro test$Mnapproximate;
   public static final Macro test$Mnassert;
   public static final Macro test$Mnend;
   public static final Macro test$Mneq;
   public static final Macro test$Mnequal;
   public static final Macro test$Mneqv;
   public static final Macro test$Mnerror;
   public static final Macro test$Mnexpect$Mnfail;
   public static final Macro test$Mngroup$Mnwith$Mncleanup;
   public static Boolean test$Mnlog$Mnto$Mnfile;
   public static final Macro test$Mnmatch$Mnall;
   public static final Macro test$Mnmatch$Mnany;
   public static final ModuleMethod test$Mnmatch$Mnname;
   public static final Macro test$Mnmatch$Mnnth;
   public static final ModuleMethod test$Mnon$Mnbad$Mncount$Mnsimple;
   public static final ModuleMethod test$Mnon$Mnbad$Mnend$Mnname$Mnsimple;
   public static final ModuleMethod test$Mnon$Mnfinal$Mnsimple;
   public static final ModuleMethod test$Mnon$Mngroup$Mnbegin$Mnsimple;
   public static final ModuleMethod test$Mnon$Mngroup$Mnend$Mnsimple;
   static final ModuleMethod test$Mnon$Mntest$Mnbegin$Mnsimple;
   public static final ModuleMethod test$Mnon$Mntest$Mnend$Mnsimple;
   public static final ModuleMethod test$Mnpassed$Qu;
   public static final ModuleMethod test$Mnread$Mneval$Mnstring;
   public static final ModuleMethod test$Mnresult$Mnalist;
   public static final ModuleMethod test$Mnresult$Mnalist$Ex;
   public static final ModuleMethod test$Mnresult$Mnclear;
   public static final ModuleMethod test$Mnresult$Mnkind;
   public static final Macro test$Mnresult$Mnref;
   public static final ModuleMethod test$Mnresult$Mnremove;
   public static final ModuleMethod test$Mnresult$Mnset$Ex;
   static final Class test$Mnrunner;
   public static final ModuleMethod test$Mnrunner$Mnaux$Mnvalue;
   public static final ModuleMethod test$Mnrunner$Mnaux$Mnvalue$Ex;
   public static final ModuleMethod test$Mnrunner$Mncreate;
   public static Object test$Mnrunner$Mncurrent;
   public static Object test$Mnrunner$Mnfactory;
   public static final ModuleMethod test$Mnrunner$Mnfail$Mncount;
   public static final ModuleMethod test$Mnrunner$Mnfail$Mncount$Ex;
   public static final ModuleMethod test$Mnrunner$Mnget;
   public static final ModuleMethod test$Mnrunner$Mngroup$Mnpath;
   public static final ModuleMethod test$Mnrunner$Mngroup$Mnstack;
   public static final ModuleMethod test$Mnrunner$Mngroup$Mnstack$Ex;
   public static final ModuleMethod test$Mnrunner$Mnnull;
   public static final ModuleMethod test$Mnrunner$Mnon$Mnbad$Mncount;
   public static final ModuleMethod test$Mnrunner$Mnon$Mnbad$Mncount$Ex;
   public static final ModuleMethod test$Mnrunner$Mnon$Mnbad$Mnend$Mnname;
   public static final ModuleMethod test$Mnrunner$Mnon$Mnbad$Mnend$Mnname$Ex;
   public static final ModuleMethod test$Mnrunner$Mnon$Mnfinal;
   public static final ModuleMethod test$Mnrunner$Mnon$Mnfinal$Ex;
   public static final ModuleMethod test$Mnrunner$Mnon$Mngroup$Mnbegin;
   public static final ModuleMethod test$Mnrunner$Mnon$Mngroup$Mnbegin$Ex;
   public static final ModuleMethod test$Mnrunner$Mnon$Mngroup$Mnend;
   public static final ModuleMethod test$Mnrunner$Mnon$Mngroup$Mnend$Ex;
   public static final ModuleMethod test$Mnrunner$Mnon$Mntest$Mnbegin;
   public static final ModuleMethod test$Mnrunner$Mnon$Mntest$Mnbegin$Ex;
   public static final ModuleMethod test$Mnrunner$Mnon$Mntest$Mnend;
   public static final ModuleMethod test$Mnrunner$Mnon$Mntest$Mnend$Ex;
   public static final ModuleMethod test$Mnrunner$Mnpass$Mncount;
   public static final ModuleMethod test$Mnrunner$Mnpass$Mncount$Ex;
   public static final ModuleMethod test$Mnrunner$Mnreset;
   public static final ModuleMethod test$Mnrunner$Mnsimple;
   public static final ModuleMethod test$Mnrunner$Mnskip$Mncount;
   public static final ModuleMethod test$Mnrunner$Mnskip$Mncount$Ex;
   public static final ModuleMethod test$Mnrunner$Mntest$Mnname;
   public static final ModuleMethod test$Mnrunner$Mnxfail$Mncount;
   public static final ModuleMethod test$Mnrunner$Mnxfail$Mncount$Ex;
   public static final ModuleMethod test$Mnrunner$Mnxpass$Mncount;
   public static final ModuleMethod test$Mnrunner$Mnxpass$Mncount$Ex;
   public static final ModuleMethod test$Mnrunner$Qu;
   public static final Macro test$Mnskip;
   public static final Macro test$Mnwith$Mnrunner;


   static Object $PcTestAnySpecifierMatches(Object var0, Object var1) {
      Boolean var2;
      for(var2 = Boolean.FALSE; !lists.isNull(var0); var0 = lists.cdr.apply1(var0)) {
         if($PcTestSpecificierMatches(lists.car.apply1(var0), var1) != Boolean.FALSE) {
            var2 = Boolean.TRUE;
         }
      }

      return var2;
   }

   public static Procedure $PcTestApproximimate$Eq(Object var0) {
      testing.frame0 var1 = new testing.frame0();
      var1.error = var0;
      return var1.lambda$Fn4;
   }

   public static Object $PcTestAsSpecifier(Object var0) {
      return misc.isProcedure(var0)?var0:(numbers.isInteger(var0)?$PcTestMatchNth(Lit13, var0):(strings.isString(var0)?testMatchName(var0):misc.error$V("not a valid test specifier", new Object[0])));
   }

   public static void $PcTestBegin(Object var0, Object var1) {
      if(((Procedure)test$Mnrunner$Mncurrent).apply0() == Boolean.FALSE) {
         ((Procedure)test$Mnrunner$Mncurrent).apply1(testRunnerCreate());
      }

      Object var2 = ((Procedure)test$Mnrunner$Mncurrent).apply0();
      ApplyToArgs var3 = Scheme.applyToArgs;

      testing.Mnrunner var4;
      try {
         var4 = (testing.Mnrunner)var2;
      } catch (ClassCastException var17) {
         throw new WrongType(var17, "test-runner-on-group-begin", 0, var2);
      }

      var3.apply4(testRunnerOnGroupBegin(var4), var2, var0, var1);

      testing.Mnrunner var20;
      try {
         var20 = (testing.Mnrunner)var2;
      } catch (ClassCastException var16) {
         throw new WrongType(var16, "%test-runner-skip-save!", 0, var2);
      }

      try {
         var4 = (testing.Mnrunner)var2;
      } catch (ClassCastException var15) {
         throw new WrongType(var15, "%test-runner-skip-list", 0, var2);
      }

      Object var21 = $PcTestRunnerSkipList(var4);

      testing.Mnrunner var5;
      try {
         var5 = (testing.Mnrunner)var2;
      } catch (ClassCastException var14) {
         throw new WrongType(var14, "%test-runner-skip-save", 0, var2);
      }

      $PcTestRunnerSkipSave$Ex(var20, lists.cons(var21, $PcTestRunnerSkipSave(var5)));

      try {
         var20 = (testing.Mnrunner)var2;
      } catch (ClassCastException var13) {
         throw new WrongType(var13, "%test-runner-fail-save!", 0, var2);
      }

      try {
         var4 = (testing.Mnrunner)var2;
      } catch (ClassCastException var12) {
         throw new WrongType(var12, "%test-runner-fail-list", 0, var2);
      }

      var21 = $PcTestRunnerFailList(var4);

      try {
         var5 = (testing.Mnrunner)var2;
      } catch (ClassCastException var11) {
         throw new WrongType(var11, "%test-runner-fail-save", 0, var2);
      }

      $PcTestRunnerFailSave$Ex(var20, lists.cons(var21, $PcTestRunnerFailSave(var5)));

      try {
         var20 = (testing.Mnrunner)var2;
      } catch (ClassCastException var10) {
         throw new WrongType(var10, "%test-runner-count-list!", 0, var2);
      }

      try {
         var4 = (testing.Mnrunner)var2;
      } catch (ClassCastException var9) {
         throw new WrongType(var9, "%test-runner-total-count", 0, var2);
      }

      Pair var18 = lists.cons($PcTestRunnerTotalCount(var4), var1);

      try {
         var4 = (testing.Mnrunner)var2;
      } catch (ClassCastException var8) {
         throw new WrongType(var8, "%test-runner-count-list", 0, var2);
      }

      $PcTestRunnerCountList$Ex(var20, lists.cons(var18, $PcTestRunnerCountList(var4)));

      testing.Mnrunner var19;
      try {
         var19 = (testing.Mnrunner)var2;
      } catch (ClassCastException var7) {
         throw new WrongType(var7, "test-runner-group-stack!", 0, var2);
      }

      try {
         var20 = (testing.Mnrunner)var2;
      } catch (ClassCastException var6) {
         throw new WrongType(var6, "test-runner-group-stack", 0, var2);
      }

      testRunnerGroupStack$Ex(var19, lists.cons(var0, testRunnerGroupStack(var20)));
   }

   static Object $PcTestComp2(Object var0, Object var1) {
      Pair var3 = LList.list3(var1, LList.list2(Lit15, $PcTestSourceLine2(var1)), var0);
      Object[] var2 = SyntaxPattern.allocVars(6, (Object[])null);
      TemplateScope var4;
      if(Lit16.match(var3, var2, 0)) {
         var4 = TemplateScope.make();
         return Lit17.execute(var2, var4);
      } else if(Lit18.match(var3, var2, 0)) {
         var4 = TemplateScope.make();
         return Lit19.execute(var2, var4);
      } else {
         return syntax_case.error("syntax-case", var3);
      }
   }

   public static Object $PcTestEnd(Object var0, Object var1) {
      Object var2 = testRunnerGet();

      testing.Mnrunner var3;
      try {
         var3 = (testing.Mnrunner)var2;
      } catch (ClassCastException var25) {
         throw new WrongType(var25, "test-runner-group-stack", 0, var2);
      }

      Object var30 = testRunnerGroupStack(var3);
      Object var4 = $PcTestFormatLine(var2);

      testing.Mnrunner var5;
      try {
         var5 = (testing.Mnrunner)var2;
      } catch (ClassCastException var24) {
         throw new WrongType(var24, "test-result-alist!", 0, var2);
      }

      testResultAlist$Ex(var5, var1);
      if(lists.isNull(var30)) {
         misc.error$V(strings.stringAppend(new Object[]{var4, "test-end not in a group"}), new Object[0]);
      }

      ApplyToArgs var27;
      testing.Mnrunner var31;
      label175: {
         if(var0 != Boolean.FALSE) {
            if(IsEqual.apply(var0, lists.car.apply1(var30))) {
               break label175;
            }
         } else if(var0 == Boolean.FALSE) {
            break label175;
         }

         var27 = Scheme.applyToArgs;

         try {
            var31 = (testing.Mnrunner)var2;
         } catch (ClassCastException var23) {
            throw new WrongType(var23, "test-runner-on-bad-end-name", 0, var2);
         }

         var27.apply4(testRunnerOnBadEndName(var31), var2, var0, lists.car.apply1(var30));
      }

      testing.Mnrunner var26;
      try {
         var26 = (testing.Mnrunner)var2;
      } catch (ClassCastException var22) {
         throw new WrongType(var22, "%test-runner-count-list", 0, var2);
      }

      var0 = $PcTestRunnerCountList(var26);
      var1 = lists.cdar.apply1(var0);
      var30 = lists.caar.apply1(var0);
      AddOp var32 = AddOp.$Mn;

      try {
         var5 = (testing.Mnrunner)var2;
      } catch (ClassCastException var21) {
         throw new WrongType(var21, "%test-runner-total-count", 0, var2);
      }

      label176: {
         var30 = var32.apply2($PcTestRunnerTotalCount(var5), var30);
         if(var1 != Boolean.FALSE) {
            if(Scheme.numEqu.apply2(var1, var30) != Boolean.FALSE) {
               break label176;
            }
         } else if(var1 == Boolean.FALSE) {
            break label176;
         }

         ApplyToArgs var34 = Scheme.applyToArgs;

         try {
            var5 = (testing.Mnrunner)var2;
         } catch (ClassCastException var20) {
            throw new WrongType(var20, "test-runner-on-bad-count", 0, var2);
         }

         var34.apply4(testRunnerOnBadCount(var5), var2, var30, var1);
      }

      var27 = Scheme.applyToArgs;

      try {
         var3 = (testing.Mnrunner)var2;
      } catch (ClassCastException var19) {
         throw new WrongType(var19, "test-runner-on-group-end", 0, var2);
      }

      var27.apply2(testRunnerOnGroupEnd(var3), var2);

      testing.Mnrunner var29;
      try {
         var29 = (testing.Mnrunner)var2;
      } catch (ClassCastException var18) {
         throw new WrongType(var18, "test-runner-group-stack!", 0, var2);
      }

      GenericProc var33 = lists.cdr;

      try {
         var31 = (testing.Mnrunner)var2;
      } catch (ClassCastException var17) {
         throw new WrongType(var17, "test-runner-group-stack", 0, var2);
      }

      testRunnerGroupStack$Ex(var29, var33.apply1(testRunnerGroupStack(var31)));

      try {
         var29 = (testing.Mnrunner)var2;
      } catch (ClassCastException var16) {
         throw new WrongType(var16, "%test-runner-skip-list!", 0, var2);
      }

      var33 = lists.car;

      try {
         var31 = (testing.Mnrunner)var2;
      } catch (ClassCastException var15) {
         throw new WrongType(var15, "%test-runner-skip-save", 0, var2);
      }

      $PcTestRunnerSkipList$Ex(var29, var33.apply1($PcTestRunnerSkipSave(var31)));

      try {
         var29 = (testing.Mnrunner)var2;
      } catch (ClassCastException var14) {
         throw new WrongType(var14, "%test-runner-skip-save!", 0, var2);
      }

      var33 = lists.cdr;

      try {
         var31 = (testing.Mnrunner)var2;
      } catch (ClassCastException var13) {
         throw new WrongType(var13, "%test-runner-skip-save", 0, var2);
      }

      $PcTestRunnerSkipSave$Ex(var29, var33.apply1($PcTestRunnerSkipSave(var31)));

      try {
         var29 = (testing.Mnrunner)var2;
      } catch (ClassCastException var12) {
         throw new WrongType(var12, "%test-runner-fail-list!", 0, var2);
      }

      var33 = lists.car;

      try {
         var31 = (testing.Mnrunner)var2;
      } catch (ClassCastException var11) {
         throw new WrongType(var11, "%test-runner-fail-save", 0, var2);
      }

      $PcTestRunnerFailList$Ex(var29, var33.apply1($PcTestRunnerFailSave(var31)));

      try {
         var29 = (testing.Mnrunner)var2;
      } catch (ClassCastException var10) {
         throw new WrongType(var10, "%test-runner-fail-save!", 0, var2);
      }

      var33 = lists.cdr;

      try {
         var31 = (testing.Mnrunner)var2;
      } catch (ClassCastException var9) {
         throw new WrongType(var9, "%test-runner-fail-save", 0, var2);
      }

      $PcTestRunnerFailSave$Ex(var29, var33.apply1($PcTestRunnerFailSave(var31)));

      try {
         var29 = (testing.Mnrunner)var2;
      } catch (ClassCastException var8) {
         throw new WrongType(var8, "%test-runner-count-list!", 0, var2);
      }

      $PcTestRunnerCountList$Ex(var29, lists.cdr.apply1(var0));

      try {
         var26 = (testing.Mnrunner)var2;
      } catch (ClassCastException var7) {
         throw new WrongType(var7, "test-runner-group-stack", 0, var2);
      }

      if(lists.isNull(testRunnerGroupStack(var26))) {
         ApplyToArgs var28 = Scheme.applyToArgs;

         try {
            var29 = (testing.Mnrunner)var2;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "test-runner-on-final", 0, var2);
         }

         return var28.apply2(testRunnerOnFinal(var29), var2);
      } else {
         return Values.empty;
      }
   }

   static void $PcTestFinalReport1(Object var0, Object var1, Object var2) {
      if(Scheme.numGrt.apply2(var0, Lit0) != Boolean.FALSE) {
         ports.display(var1, var2);
         ports.display(var0, var2);
         ports.newline(var2);
      }

   }

   static void $PcTestFinalReportSimple(Object var0, Object var1) {
      testing.Mnrunner var2;
      try {
         var2 = (testing.Mnrunner)var0;
      } catch (ClassCastException var7) {
         throw new WrongType(var7, "test-runner-pass-count", 0, var0);
      }

      $PcTestFinalReport1(testRunnerPassCount(var2), "# of expected passes      ", var1);

      try {
         var2 = (testing.Mnrunner)var0;
      } catch (ClassCastException var6) {
         throw new WrongType(var6, "test-runner-xfail-count", 0, var0);
      }

      $PcTestFinalReport1(testRunnerXfailCount(var2), "# of expected failures    ", var1);

      try {
         var2 = (testing.Mnrunner)var0;
      } catch (ClassCastException var5) {
         throw new WrongType(var5, "test-runner-xpass-count", 0, var0);
      }

      $PcTestFinalReport1(testRunnerXpassCount(var2), "# of unexpected successes ", var1);

      try {
         var2 = (testing.Mnrunner)var0;
      } catch (ClassCastException var4) {
         throw new WrongType(var4, "test-runner-fail-count", 0, var0);
      }

      $PcTestFinalReport1(testRunnerFailCount(var2), "# of unexpected failures  ", var1);

      try {
         var2 = (testing.Mnrunner)var0;
      } catch (ClassCastException var3) {
         throw new WrongType(var3, "test-runner-skip-count", 0, var0);
      }

      $PcTestFinalReport1(testRunnerSkipCount(var2), "# of skipped tests        ", var1);
   }

   static Object $PcTestFormatLine(Object var0) {
      testing.Mnrunner var1;
      try {
         var1 = (testing.Mnrunner)var0;
      } catch (ClassCastException var4) {
         throw new WrongType(var4, "test-result-alist", 0, var0);
      }

      Object var5 = testResultAlist(var1);
      var0 = lists.assq(Lit4, var5);
      var5 = lists.assq(Lit5, var5);
      if(var0 != Boolean.FALSE) {
         var0 = lists.cdr.apply1(var0);
      } else {
         var0 = "";
      }

      if(var5 != Boolean.FALSE) {
         var5 = lists.cdr.apply1(var5);

         Number var2;
         try {
            var2 = (Number)var5;
         } catch (ClassCastException var3) {
            throw new WrongType(var3, "number->string", 1, var5);
         }

         return strings.stringAppend(new Object[]{var0, ":", numbers.number$To$String(var2), ": "});
      } else {
         return "";
      }
   }

   public static Procedure $PcTestMatchAll$V(Object[] var0) {
      testing.frame3 var1 = new testing.frame3();
      var1.pred$Mnlist = LList.makeList(var0, 0);
      return var1.lambda$Fn12;
   }

   public static Procedure $PcTestMatchAny$V(Object[] var0) {
      testing.frame4 var1 = new testing.frame4();
      var1.pred$Mnlist = LList.makeList(var0, 0);
      return var1.lambda$Fn13;
   }

   public static Procedure $PcTestMatchNth(Object var0, Object var1) {
      testing.frame2 var2 = new testing.frame2();
      var2.n = var0;
      var2.count = var1;
      var2.i = Lit0;
      return var2.lambda$Fn11;
   }

   static Boolean $PcTestNullCallback(Object var0) {
      return Boolean.FALSE;
   }

   static void $PcTestOnBadCountWrite(Object var0, Object var1, Object var2, Object var3) {
      ports.display("*** Total number of tests was ", var3);
      ports.display(var1, var3);
      ports.display(" but should be ", var3);
      ports.display(var2, var3);
      ports.display(". ***", var3);
      ports.newline(var3);
      ports.display("*** Discrepancy indicates testsuite error or exceptions. ***", var3);
      ports.newline(var3);
   }

   public static boolean $PcTestOnTestBegin(Object var0) {
      $PcTestShouldExecute(var0);
      ApplyToArgs var1 = Scheme.applyToArgs;

      testing.Mnrunner var2;
      try {
         var2 = (testing.Mnrunner)var0;
      } catch (ClassCastException var6) {
         throw new WrongType(var6, "test-runner-on-test-begin", 0, var0);
      }

      var1.apply2(testRunnerOnTestBegin(var2), var0);
      SimpleSymbol var7 = Lit2;
      SimpleSymbol var8 = Lit1;

      testing.Mnrunner var3;
      try {
         var3 = (testing.Mnrunner)var0;
      } catch (ClassCastException var5) {
         throw new WrongType(var5, "test-result-alist", 0, var0);
      }

      var0 = lists.assq(var8, testResultAlist(var3));
      if(var0 != Boolean.FALSE) {
         var0 = lists.cdr.apply1(var0);
      } else {
         var0 = Boolean.FALSE;
      }

      byte var4;
      if(var7 == var0) {
         var4 = 1;
      } else {
         var4 = 0;
      }

      return (boolean)(var4 + 1 & 1);
   }

   public static Object $PcTestOnTestEnd(Object var0, Object var1) {
      SimpleSymbol var3 = Lit1;
      SimpleSymbol var2 = Lit1;

      testing.Mnrunner var4;
      try {
         var4 = (testing.Mnrunner)var0;
      } catch (ClassCastException var5) {
         throw new WrongType(var5, "test-result-alist", 0, var0);
      }

      Object var7 = lists.assq(var2, testResultAlist(var4));
      if(var7 != Boolean.FALSE) {
         var7 = lists.cdr.apply1(var7);
      } else {
         var7 = Boolean.FALSE;
      }

      SimpleSymbol var6;
      if(var7 == Lit3) {
         if(var1 != Boolean.FALSE) {
            var6 = Lit9;
         } else {
            var6 = Lit3;
         }
      } else if(var1 != Boolean.FALSE) {
         var6 = Lit12;
      } else {
         var6 = Lit14;
      }

      return testResultSet$Ex(var0, var3, var6);
   }

   public static Object $PcTestReportResult() {
      Object var0 = testRunnerGet();
      Object var1 = testResultKind$V(new Object[]{var0});
      AddOp var2;
      IntNum var3;
      testing.Mnrunner var4;
      testing.Mnrunner var18;
      if(Scheme.isEqv.apply2(var1, Lit12) != Boolean.FALSE) {
         try {
            var18 = (testing.Mnrunner)var0;
         } catch (ClassCastException var17) {
            throw new WrongType(var17, "test-runner-pass-count!", 0, var0);
         }

         var2 = AddOp.$Pl;
         var3 = Lit13;

         try {
            var4 = (testing.Mnrunner)var0;
         } catch (ClassCastException var16) {
            throw new WrongType(var16, "test-runner-pass-count", 0, var0);
         }

         testRunnerPassCount$Ex(var18, var2.apply2(var3, testRunnerPassCount(var4)));
      } else if(Scheme.isEqv.apply2(var1, Lit14) != Boolean.FALSE) {
         try {
            var18 = (testing.Mnrunner)var0;
         } catch (ClassCastException var15) {
            throw new WrongType(var15, "test-runner-fail-count!", 0, var0);
         }

         var2 = AddOp.$Pl;
         var3 = Lit13;

         try {
            var4 = (testing.Mnrunner)var0;
         } catch (ClassCastException var14) {
            throw new WrongType(var14, "test-runner-fail-count", 0, var0);
         }

         testRunnerFailCount$Ex(var18, var2.apply2(var3, testRunnerFailCount(var4)));
      } else if(Scheme.isEqv.apply2(var1, Lit9) != Boolean.FALSE) {
         try {
            var18 = (testing.Mnrunner)var0;
         } catch (ClassCastException var13) {
            throw new WrongType(var13, "test-runner-xpass-count!", 0, var0);
         }

         var2 = AddOp.$Pl;
         var3 = Lit13;

         try {
            var4 = (testing.Mnrunner)var0;
         } catch (ClassCastException var12) {
            throw new WrongType(var12, "test-runner-xpass-count", 0, var0);
         }

         testRunnerXpassCount$Ex(var18, var2.apply2(var3, testRunnerXpassCount(var4)));
      } else if(Scheme.isEqv.apply2(var1, Lit3) != Boolean.FALSE) {
         try {
            var18 = (testing.Mnrunner)var0;
         } catch (ClassCastException var11) {
            throw new WrongType(var11, "test-runner-xfail-count!", 0, var0);
         }

         var2 = AddOp.$Pl;
         var3 = Lit13;

         try {
            var4 = (testing.Mnrunner)var0;
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "test-runner-xfail-count", 0, var0);
         }

         testRunnerXfailCount$Ex(var18, var2.apply2(var3, testRunnerXfailCount(var4)));
      } else {
         try {
            var18 = (testing.Mnrunner)var0;
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "test-runner-skip-count!", 0, var0);
         }

         var2 = AddOp.$Pl;
         var3 = Lit13;

         try {
            var4 = (testing.Mnrunner)var0;
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "test-runner-skip-count", 0, var0);
         }

         testRunnerSkipCount$Ex(var18, var2.apply2(var3, testRunnerSkipCount(var4)));
      }

      try {
         var18 = (testing.Mnrunner)var0;
      } catch (ClassCastException var7) {
         throw new WrongType(var7, "%test-runner-total-count!", 0, var0);
      }

      var2 = AddOp.$Pl;
      var3 = Lit13;

      try {
         var4 = (testing.Mnrunner)var0;
      } catch (ClassCastException var6) {
         throw new WrongType(var6, "%test-runner-total-count", 0, var0);
      }

      $PcTestRunnerTotalCount$Ex(var18, var2.apply2(var3, $PcTestRunnerTotalCount(var4)));
      ApplyToArgs var19 = Scheme.applyToArgs;

      testing.Mnrunner var20;
      try {
         var20 = (testing.Mnrunner)var0;
      } catch (ClassCastException var5) {
         throw new WrongType(var5, "test-runner-on-test-end", 0, var0);
      }

      return var19.apply2(testRunnerOnTestEnd(var20), var0);
   }

   static testing.Mnrunner $PcTestRunnerAlloc() {
      return new testing.Mnrunner();
   }

   static Object $PcTestRunnerCountList(testing.Mnrunner var0) {
      return var0.count$Mnlist;
   }

   static void $PcTestRunnerCountList$Ex(testing.Mnrunner var0, Object var1) {
      var0.count$Mnlist = var1;
   }

   public static Object $PcTestRunnerFailList(testing.Mnrunner var0) {
      return var0.fail$Mnlist;
   }

   public static void $PcTestRunnerFailList$Ex(testing.Mnrunner var0, Object var1) {
      var0.fail$Mnlist = var1;
   }

   static Object $PcTestRunnerFailSave(testing.Mnrunner var0) {
      return var0.fail$Mnsave;
   }

   static void $PcTestRunnerFailSave$Ex(testing.Mnrunner var0, Object var1) {
      var0.fail$Mnsave = var1;
   }

   static Object $PcTestRunnerRunList(testing.Mnrunner var0) {
      return var0.run$Mnlist;
   }

   static void $PcTestRunnerRunList$Ex(testing.Mnrunner var0, Object var1) {
      var0.run$Mnlist = var1;
   }

   public static Object $PcTestRunnerSkipList(testing.Mnrunner var0) {
      return var0.skip$Mnlist;
   }

   public static void $PcTestRunnerSkipList$Ex(testing.Mnrunner var0, Object var1) {
      var0.skip$Mnlist = var1;
   }

   static Object $PcTestRunnerSkipSave(testing.Mnrunner var0) {
      return var0.skip$Mnsave;
   }

   static void $PcTestRunnerSkipSave$Ex(testing.Mnrunner var0, Object var1) {
      var0.skip$Mnsave = var1;
   }

   static Object $PcTestRunnerTotalCount(testing.Mnrunner var0) {
      return var0.total$Mncount;
   }

   static void $PcTestRunnerTotalCount$Ex(testing.Mnrunner var0, Object var1) {
      var0.total$Mncount = var1;
   }

   public static Object $PcTestShouldExecute(Object var0) {
      testing.Mnrunner var1;
      try {
         var1 = (testing.Mnrunner)var0;
      } catch (ClassCastException var7) {
         throw new WrongType(var7, "%test-runner-run-list", 0, var0);
      }

      Object var8 = $PcTestRunnerRunList(var1);
      byte var3;
      if(var8 == Boolean.TRUE) {
         var3 = 1;
      } else {
         var3 = 0;
      }

      if(var3 == 0) {
         var8 = $PcTestAnySpecifierMatches(var8, var0);

         Boolean var2;
         try {
            var2 = Boolean.FALSE;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "x", -2, var8);
         }

         if(var8 != var2) {
            var3 = 1;
         } else {
            var3 = 0;
         }
      }

      label52: {
         int var9 = var3 + 1 & 1;
         if(var9 != 0) {
            if(var9 == 0) {
               break label52;
            }
         } else {
            try {
               var1 = (testing.Mnrunner)var0;
            } catch (ClassCastException var5) {
               throw new WrongType(var5, "%test-runner-skip-list", 0, var0);
            }

            if($PcTestAnySpecifierMatches($PcTestRunnerSkipList(var1), var0) == Boolean.FALSE) {
               break label52;
            }
         }

         testResultSet$Ex(var0, Lit1, Lit2);
         return Boolean.FALSE;
      }

      try {
         var1 = (testing.Mnrunner)var0;
      } catch (ClassCastException var4) {
         throw new WrongType(var4, "%test-runner-fail-list", 0, var0);
      }

      if($PcTestAnySpecifierMatches($PcTestRunnerFailList(var1), var0) != Boolean.FALSE) {
         testResultSet$Ex(var0, Lit1, Lit3);
         return Lit3;
      } else {
         return Boolean.TRUE;
      }
   }

   static Pair $PcTestSourceLine2(Object var0) {
      Object var1 = std_syntax.syntaxLine(var0);
      Object var2 = $PcTestSyntaxFile(var0);
      if(var1 != Boolean.FALSE) {
         var1 = LList.list1(lists.cons(Lit5, var1));
      } else {
         var1 = LList.Empty;
      }

      Pair var3 = lists.cons(Lit6, std_syntax.syntaxObject$To$Datum(var0));
      var0 = var1;
      if(var2 != Boolean.FALSE) {
         var0 = lists.cons(lists.cons(Lit4, var2), var1);
      }

      return lists.cons(var3, var0);
   }

   static Object $PcTestSpecificierMatches(Object var0, Object var1) {
      return Scheme.applyToArgs.apply2(var0, var1);
   }

   static Object $PcTestSyntaxFile(Object var0) {
      return std_syntax.syntaxSource(var0);
   }

   static Object $PcTestWriteResult1(Object var0, Object var1) {
      ports.display("  ", var1);
      ports.display(lists.car.apply1(var0), var1);
      ports.display(": ", var1);
      ports.write(lists.cdr.apply1(var0), var1);
      ports.newline(var1);
      return Values.empty;
   }

   static {
      SimpleSymbol var0 = (SimpleSymbol)(new SimpleSymbol("test-expect-fail")).readResolve();
      Lit139 = var0;
      SyntaxPattern var1 = new SyntaxPattern("\f\u0018\r\u0007\u0000\b\b", new Object[0], 1);
      SimpleSymbol var2 = Lit144;
      SimpleSymbol var3 = Lit143;
      SimpleSymbol var4 = (SimpleSymbol)(new SimpleSymbol("test-runner-get")).readResolve();
      Lit60 = var4;
      PairWithPosition var17 = PairWithPosition.make(PairWithPosition.make(var3, PairWithPosition.make(PairWithPosition.make(var4, LList.Empty, "testing.scm", 3952660), LList.Empty, "testing.scm", 3952660), "testing.scm", 3952652), LList.Empty, "testing.scm", 3952651);
      var4 = (SimpleSymbol)(new SimpleSymbol("%test-runner-fail-list!")).readResolve();
      Lit34 = var4;
      SimpleSymbol var5 = Lit143;
      SimpleSymbol var6 = Lit145;
      SimpleSymbol var7 = (SimpleSymbol)(new SimpleSymbol("test-match-all")).readResolve();
      Lit131 = var7;
      SimpleSymbol var8 = (SimpleSymbol)(new SimpleSymbol("%test-as-specifier")).readResolve();
      Lit136 = var8;
      SimpleSymbol var9 = (SimpleSymbol)(new SimpleSymbol("%test-runner-fail-list")).readResolve();
      Lit33 = var9;
      SyntaxRule var16 = new SyntaxRule(var1, "\u0003", "\u0011\u0018\u0004\u0011\u0018\f\b\u0011\u0018\u0014\u0011\u0018\u001c\b\u0011\u0018$Q\u0011\u0018,\b\u0005\u0011\u00184\b\u0003\u0018<", new Object[]{var2, var17, var4, var5, var6, var7, var8, PairWithPosition.make(PairWithPosition.make(var9, PairWithPosition.make(Lit143, LList.Empty, "testing.scm", 3964958), "testing.scm", 3964934), LList.Empty, "testing.scm", 3964934)}, 1);
      Lit140 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var16}, 1);
      var0 = (SimpleSymbol)(new SimpleSymbol("test-skip")).readResolve();
      Lit137 = var0;
      var1 = new SyntaxPattern("\f\u0018\r\u0007\u0000\b\b", new Object[0], 1);
      var2 = Lit144;
      var17 = PairWithPosition.make(PairWithPosition.make(Lit143, PairWithPosition.make(PairWithPosition.make(Lit60, LList.Empty, "testing.scm", 3919892), LList.Empty, "testing.scm", 3919892), "testing.scm", 3919884), LList.Empty, "testing.scm", 3919883);
      var4 = (SimpleSymbol)(new SimpleSymbol("%test-runner-skip-list!")).readResolve();
      Lit32 = var4;
      var5 = Lit143;
      var6 = Lit145;
      var7 = Lit131;
      var8 = Lit136;
      var9 = (SimpleSymbol)(new SimpleSymbol("%test-runner-skip-list")).readResolve();
      Lit31 = var9;
      var16 = new SyntaxRule(var1, "\u0003", "\u0011\u0018\u0004\u0011\u0018\f\b\u0011\u0018\u0014\u0011\u0018\u001c\b\u0011\u0018$Q\u0011\u0018,\b\u0005\u0011\u00184\b\u0003\u0018<", new Object[]{var2, var17, var4, var5, var6, var7, var8, PairWithPosition.make(PairWithPosition.make(var9, PairWithPosition.make(Lit143, LList.Empty, "testing.scm", 3932190), "testing.scm", 3932166), LList.Empty, "testing.scm", 3932166)}, 1);
      Lit138 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var16}, 1);
      var0 = (SimpleSymbol)(new SimpleSymbol("test-match-any")).readResolve();
      Lit134 = var0;
      var1 = new SyntaxPattern("\f\u0018\r\u0007\u0000\b\b", new Object[0], 1);
      var2 = (SimpleSymbol)(new SimpleSymbol("%test-match-any")).readResolve();
      Lit133 = var2;
      var16 = new SyntaxRule(var1, "\u0003", "\u0011\u0018\u0004\b\u0005\u0011\u0018\f\b\u0003", new Object[]{var2, Lit136}, 1);
      Lit135 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var16}, 1);
      var0 = Lit131;
      var1 = new SyntaxPattern("\f\u0018\r\u0007\u0000\b\b", new Object[0], 1);
      var2 = (SimpleSymbol)(new SimpleSymbol("%test-match-all")).readResolve();
      Lit130 = var2;
      var16 = new SyntaxRule(var1, "\u0003", "\u0011\u0018\u0004\b\u0005\u0011\u0018\f\b\u0003", new Object[]{var2, Lit136}, 1);
      Lit132 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var16}, 1);
      var0 = (SimpleSymbol)(new SimpleSymbol("test-match-nth")).readResolve();
      Lit128 = var0;
      var1 = new SyntaxPattern("\f\u0018\f\u0007\b", new Object[0], 1);
      var2 = Lit128;
      IntNum var20 = IntNum.make(1);
      Lit13 = var20;
      var16 = new SyntaxRule(var1, "\u0001", "\u0011\u0018\u0004\t\u0003\u0018\f", new Object[]{var2, PairWithPosition.make(var20, LList.Empty, "testing.scm", 3727384)}, 0);
      SyntaxPattern var18 = new SyntaxPattern("\f\u0018\f\u0007\f\u000f\b", new Object[0], 2);
      var3 = (SimpleSymbol)(new SimpleSymbol("%test-match-nth")).readResolve();
      Lit127 = var3;
      SyntaxRule var19 = new SyntaxRule(var18, "\u0001\u0001", "\u0011\u0018\u0004\t\u0003\b\u000b", new Object[]{var3}, 0);
      Lit129 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var16, var19}, 2);
      var0 = (SimpleSymbol)(new SimpleSymbol("test-with-runner")).readResolve();
      Lit125 = var0;
      var16 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\r\u000f\b\b\b", new Object[0], 2), "\u0001\u0003", "\u0011\u0018\u0004\u0011\u0018\f\b\u0011\u0018\u0014Y\u0011\u0018\u001c\t\u0010\b\u0011\u0018$\b\u0003A\u0011\u0018\u001c\t\u0010\b\r\u000b\u0018,", new Object[]{Lit144, PairWithPosition.make(PairWithPosition.make(Lit148, PairWithPosition.make(PairWithPosition.make(Lit146, LList.Empty, "testing.scm", 3657754), LList.Empty, "testing.scm", 3657754), "testing.scm", 3657740), LList.Empty, "testing.scm", 3657739), Lit165, Lit147, Lit146, PairWithPosition.make(PairWithPosition.make(Lit147, PairWithPosition.make(LList.Empty, PairWithPosition.make(PairWithPosition.make(Lit146, PairWithPosition.make(Lit148, LList.Empty, "testing.scm", 3674156), "testing.scm", 3674135), LList.Empty, "testing.scm", 3674135), "testing.scm", 3674132), "testing.scm", 3674124), LList.Empty, "testing.scm", 3674124)}, 1);
      Lit126 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var16}, 2);
      Lit124 = (SimpleSymbol)(new SimpleSymbol("test-apply")).readResolve();
      var0 = Lit150;
      PairWithPosition var21 = PairWithPosition.make(PairWithPosition.make(Lit149, PairWithPosition.make(PairWithPosition.make(Lit60, LList.Empty, "testing.scm", 3514382), LList.Empty, "testing.scm", 3514382), "testing.scm", 3514379), LList.Empty, "testing.scm", 3514378);
      var2 = (SimpleSymbol)(new SimpleSymbol("test-result-alist!")).readResolve();
      Lit52 = var2;
      var3 = Lit149;
      var4 = (SimpleSymbol)(new SimpleSymbol("%test-error")).readResolve();
      Lit115 = var4;
      Lit123 = new SyntaxTemplate("\u0001\u0001\u0001", "\u0011\u0018\u0004\u0011\u0018\fA\u0011\u0018\u0014\u0011\u0018\u001c\b\u0013\b\u0011\u0018$\u0011\u0018\u001c\u0011\u0018,\b\u000b", new Object[]{var0, var21, var2, var3, var4, Boolean.TRUE}, 0);
      Lit122 = new SyntaxPattern(",\f\u0007\f\u000f\b\f\u0017\b", new Object[0], 3);
      Lit121 = new SyntaxTemplate("\u0001\u0001\u0001\u0001", "\u0011\u0018\u0004\u0011\u0018\fA\u0011\u0018\u0014\u0011\u0018\u001c\b\u001b\b\u0011\u0018$\u0011\u0018\u001c\t\u000b\b\u0013", new Object[]{Lit150, PairWithPosition.make(PairWithPosition.make(Lit149, PairWithPosition.make(PairWithPosition.make(Lit60, LList.Empty, "testing.scm", 3493902), LList.Empty, "testing.scm", 3493902), "testing.scm", 3493899), LList.Empty, "testing.scm", 3493898), Lit52, Lit149, Lit115}, 0);
      Lit120 = new SyntaxPattern("<\f\u0007\f\u000f\f\u0017\b\f\u001f\b", new Object[0], 4);
      var0 = Lit150;
      var21 = PairWithPosition.make(Lit149, PairWithPosition.make(PairWithPosition.make(Lit60, LList.Empty, "testing.scm", 3469326), LList.Empty, "testing.scm", 3469326), "testing.scm", 3469323);
      var2 = Lit160;
      var3 = Lit52;
      var4 = Lit149;
      var5 = Lit145;
      var6 = (SimpleSymbol)(new SimpleSymbol("quote")).readResolve();
      Lit15 = var6;
      var7 = (SimpleSymbol)(new SimpleSymbol("test-name")).readResolve();
      Lit7 = var7;
      Lit119 = new SyntaxTemplate("\u0001\u0001\u0001\u0001\u0001", "\u0011\u0018\u0004I\u0011\u0018\f\b\u0011\u0018\u0014\b\u000b©\u0011\u0018\u001c\u0011\u0018$\b\u0011\u0018,A\u0011\u0018,\u0011\u00184\b\u000b\b#\b\u0011\u0018<\u0011\u0018$\t\u0013\b\u001b", new Object[]{var0, var21, var2, var3, var4, var5, PairWithPosition.make(var6, PairWithPosition.make(var7, LList.Empty, "testing.scm", 3477545), "testing.scm", 3477545), Lit115}, 0);
      Lit118 = new SyntaxPattern("L\f\u0007\f\u000f\f\u0017\f\u001f\b\f\'\b", new Object[0], 5);
      Lit117 = (SimpleSymbol)(new SimpleSymbol("test-error")).readResolve();
      var0 = Lit115;
      var1 = new SyntaxPattern("\f\u0018\f\u0007\f\u0002\f\u000f\b", new Object[]{Boolean.TRUE}, 2);
      var2 = Lit158;
      var3 = (SimpleSymbol)(new SimpleSymbol("%test-on-test-begin")).readResolve();
      Lit86 = var3;
      var4 = (SimpleSymbol)(new SimpleSymbol("test-result-set!")).readResolve();
      Lit78 = var4;
      PairWithPosition var22 = PairWithPosition.make(PairWithPosition.make(Lit15, PairWithPosition.make(Lit152, LList.Empty, "testing.scm", 3223581), "testing.scm", 3223581), PairWithPosition.make(Boolean.TRUE, LList.Empty, "testing.scm", 3223596), "testing.scm", 3223580);
      var6 = (SimpleSymbol)(new SimpleSymbol("%test-on-test-end")).readResolve();
      Lit87 = var6;
      var7 = Lit154;
      var8 = Lit144;
      PairWithPosition var27 = PairWithPosition.make(Lit15, PairWithPosition.make(Lit155, LList.Empty, "testing.scm", 3239966), "testing.scm", 3239966);
      PairWithPosition var10 = PairWithPosition.make(Boolean.FALSE, LList.Empty, "testing.scm", 3244041);
      SimpleSymbol var11 = Lit151;
      SimpleSymbol var12 = Lit156;
      PairWithPosition var13 = PairWithPosition.make(PairWithPosition.make(Lit15, PairWithPosition.make(Lit157, LList.Empty, "testing.scm", 3252256), "testing.scm", 3252256), PairWithPosition.make(Lit151, LList.Empty, "testing.scm", 3252269), "testing.scm", 3252255);
      PairWithPosition var14 = PairWithPosition.make(Boolean.TRUE, LList.Empty, "testing.scm", 3256331);
      SimpleSymbol var15 = (SimpleSymbol)(new SimpleSymbol("%test-report-result")).readResolve();
      Lit83 = var15;
      var16 = new SyntaxRule(var1, "\u0001\u0001", "\u0011\u0018\u0004\b)\u0011\u0018\f\b\u00039\u0011\u0018\u0014\t\u0003\u0018\u001cũ\u0011\u0018$\t\u0003\b\u0011\u0018,\u0091\u0011\u00184\t\u0010Q\u0011\u0018\u0014\t\u0003\u0011\u0018<\b\u000b\u0018D\b\u0011\u0018L\u0011\u0018T9\u0011\u0018\u0014\t\u0003\u0018\\\u0018d\u0018l", new Object[]{var2, var3, var4, var22, var6, var7, var8, var27, var10, var11, var12, var13, var14, PairWithPosition.make(PairWithPosition.make(var15, LList.Empty, "testing.scm", 3260424), LList.Empty, "testing.scm", 3260424)}, 0);
      var19 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\f\u0017\b", new Object[0], 3), "\u0001\u0001\u0001", "\u0011\u0018\u0004)\u0011\u0018\f\b\u0003\b\u0011\u0018\u00141\b\u0011\u0018\u001c\b\u000b9\u0011\u0018$\t\u0003\u0018,ũ\u0011\u00184\t\u0003\b\u0011\u0018<\u0091\u0011\u0018\u0014\t\u0010Q\u0011\u0018$\t\u0003\u0011\u0018D\b\u0013\u0018L\b\u0011\u0018T\u0011\u0018\\9\u0011\u0018$\t\u0003\u0018d\u0018l\u0018t", new Object[]{Lit161, Lit86, Lit144, Lit153, Lit78, PairWithPosition.make(PairWithPosition.make(Lit15, PairWithPosition.make(Lit152, LList.Empty, "testing.scm", 3276828), "testing.scm", 3276828), PairWithPosition.make(Lit153, LList.Empty, "testing.scm", 3276843), "testing.scm", 3276827), Lit87, Lit154, PairWithPosition.make(Lit15, PairWithPosition.make(Lit155, LList.Empty, "testing.scm", 3293213), "testing.scm", 3293213), PairWithPosition.make(Boolean.FALSE, LList.Empty, "testing.scm", 3297288), Lit151, Lit156, PairWithPosition.make(PairWithPosition.make(Lit15, PairWithPosition.make(Lit157, LList.Empty, "testing.scm", 3305503), "testing.scm", 3305503), PairWithPosition.make(Lit151, LList.Empty, "testing.scm", 3305516), "testing.scm", 3305502), PairWithPosition.make(PairWithPosition.make(Lit158, PairWithPosition.make(PairWithPosition.make(PairWithPosition.make((SimpleSymbol)(new SimpleSymbol("and")).readResolve(), PairWithPosition.make(PairWithPosition.make(Lit159, PairWithPosition.make(Lit153, PairWithPosition.make((SimpleSymbol)(new SimpleSymbol("<gnu.bytecode.ClassType>")).readResolve(), LList.Empty, "testing.scm", 3309604), "testing.scm", 3309601), "testing.scm", 3309590), PairWithPosition.make(PairWithPosition.make(PairWithPosition.make((SimpleSymbol)(new SimpleSymbol("$lookup$")).readResolve(), Pair.make((SimpleSymbol)(new SimpleSymbol("gnu.bytecode.ClassType")).readResolve(), Pair.make(Pair.make((SimpleSymbol)(new SimpleSymbol("quasiquote")).readResolve(), Pair.make((SimpleSymbol)(new SimpleSymbol("isSubclass")).readResolve(), LList.Empty)), LList.Empty)), "testing.scm", 3313673), PairWithPosition.make(Lit153, PairWithPosition.make(Lit156, LList.Empty, "testing.scm", 3313710), "testing.scm", 3313707), "testing.scm", 3313672), LList.Empty, "testing.scm", 3313672), "testing.scm", 3309590), "testing.scm", 3309585), PairWithPosition.make(PairWithPosition.make(Lit159, PairWithPosition.make(Lit151, PairWithPosition.make(Lit153, LList.Empty, "testing.scm", 3317784), "testing.scm", 3317781), "testing.scm", 3317770), LList.Empty, "testing.scm", 3317770), "testing.scm", 3309584), PairWithPosition.make(PairWithPosition.make((SimpleSymbol)(new SimpleSymbol("else")).readResolve(), PairWithPosition.make(Boolean.TRUE, LList.Empty, "testing.scm", 3321871), "testing.scm", 3321865), LList.Empty, "testing.scm", 3321865), "testing.scm", 3309584), "testing.scm", 3309578), LList.Empty, "testing.scm", 3309578), PairWithPosition.make(PairWithPosition.make(Lit83, LList.Empty, "testing.scm", 3325959), LList.Empty, "testing.scm", 3325959)}, 0);
      Lit116 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var16, var19}, 3);
      var0 = Lit150;
      var21 = PairWithPosition.make(PairWithPosition.make(Lit149, PairWithPosition.make(PairWithPosition.make(Lit60, LList.Empty, "testing.scm", 2916364), LList.Empty, "testing.scm", 2916364), "testing.scm", 2916361), LList.Empty, "testing.scm", 2916360);
      var2 = Lit52;
      var3 = Lit149;
      var4 = (SimpleSymbol)(new SimpleSymbol("%test-comp2body")).readResolve();
      Lit89 = var4;
      var5 = (SimpleSymbol)(new SimpleSymbol("%test-approximimate=")).readResolve();
      Lit91 = var5;
      Lit114 = new SyntaxTemplate("\u0001\u0001\u0001\u0001\u0001", "\u0011\u0018\u0004\u0011\u0018\fA\u0011\u0018\u0014\u0011\u0018\u001c\b#\b\u0011\u0018$\u0011\u0018\u001c)\u0011\u0018,\b\u001b\t\u000b\b\u0013", new Object[]{var0, var21, var2, var3, var4, var5}, 0);
      Lit113 = new SyntaxPattern("L\f\u0007\f\u000f\f\u0017\f\u001f\b\f\'\b", new Object[0], 5);
      Lit112 = new SyntaxTemplate("\u0001\u0001\u0001\u0001\u0001\u0001", "\u0011\u0018\u0004I\u0011\u0018\f\b\u0011\u0018\u0014\b\u000b©\u0011\u0018\u001c\u0011\u0018$\b\u0011\u0018,A\u0011\u0018,\u0011\u00184\b\u000b\b+\b\u0011\u0018<\u0011\u0018$)\u0011\u0018D\b#\t\u0013\b\u001b", new Object[]{Lit150, PairWithPosition.make(Lit149, PairWithPosition.make(PairWithPosition.make(Lit60, LList.Empty, "testing.scm", 2891788), LList.Empty, "testing.scm", 2891788), "testing.scm", 2891785), Lit160, Lit52, Lit149, Lit145, PairWithPosition.make(Lit15, PairWithPosition.make(Lit7, LList.Empty, "testing.scm", 2900007), "testing.scm", 2900007), Lit89, Lit91}, 0);
      Lit111 = new SyntaxPattern("\\\f\u0007\f\u000f\f\u0017\f\u001f\f\'\b\f/\b", new Object[0], 6);
      Lit110 = (SimpleSymbol)(new SimpleSymbol("test-approximate")).readResolve();
      Lit109 = new SyntaxTemplate("", "\u0018\u0004", new Object[]{(SimpleSymbol)(new SimpleSymbol("equal?")).readResolve()}, 0);
      Lit108 = (SimpleSymbol)(new SimpleSymbol("test-equal")).readResolve();
      Lit107 = new SyntaxTemplate("", "\u0018\u0004", new Object[]{(SimpleSymbol)(new SimpleSymbol("eq?")).readResolve()}, 0);
      Lit106 = (SimpleSymbol)(new SimpleSymbol("test-eq")).readResolve();
      Lit105 = new SyntaxTemplate("", "\u0018\u0004", new Object[]{(SimpleSymbol)(new SimpleSymbol("eqv?")).readResolve()}, 0);
      Lit104 = (SimpleSymbol)(new SimpleSymbol("test-eqv")).readResolve();
      var0 = Lit150;
      var21 = PairWithPosition.make(PairWithPosition.make(Lit149, PairWithPosition.make(PairWithPosition.make(Lit60, LList.Empty, "testing.scm", 2781198), LList.Empty, "testing.scm", 2781198), "testing.scm", 2781195), LList.Empty, "testing.scm", 2781194);
      var2 = Lit52;
      var3 = Lit149;
      var4 = (SimpleSymbol)(new SimpleSymbol("%test-comp1body")).readResolve();
      Lit92 = var4;
      Lit103 = new SyntaxTemplate("\u0001\u0001\u0001", "\u0011\u0018\u0004\u0011\u0018\fA\u0011\u0018\u0014\u0011\u0018\u001c\b\u0013\b\u0011\u0018$\u0011\u0018\u001c\b\u000b", new Object[]{var0, var21, var2, var3, var4}, 0);
      Lit102 = new SyntaxPattern(",\f\u0007\f\u000f\b\f\u0017\b", new Object[0], 3);
      Lit101 = new SyntaxTemplate("\u0001\u0001\u0001\u0001", "\u0011\u0018\u0004I\u0011\u0018\f\b\u0011\u0018\u0014\b\u000b©\u0011\u0018\u001c\u0011\u0018$\b\u0011\u0018,A\u0011\u0018,\u0011\u00184\b\u000b\b\u001b\b\u0011\u0018<\u0011\u0018$\b\u0013", new Object[]{Lit150, PairWithPosition.make(Lit149, PairWithPosition.make(PairWithPosition.make(Lit60, LList.Empty, "testing.scm", 2756622), LList.Empty, "testing.scm", 2756622), "testing.scm", 2756619), Lit160, Lit52, Lit149, Lit145, PairWithPosition.make(Lit15, PairWithPosition.make(Lit7, LList.Empty, "testing.scm", 2764841), "testing.scm", 2764841), Lit92}, 0);
      Lit100 = new SyntaxPattern("<\f\u0007\f\u000f\f\u0017\b\f\u001f\b", new Object[0], 4);
      Lit99 = (SimpleSymbol)(new SimpleSymbol("test-assert")).readResolve();
      var0 = (SimpleSymbol)(new SimpleSymbol("%test-end")).readResolve();
      Lit69 = var0;
      Lit98 = new SyntaxTemplate("\u0001\u0001", "\u0011\u0018\u0004\u0011\u0018\f\b\u000b", new Object[]{var0, Boolean.FALSE}, 0);
      Lit97 = new SyntaxPattern("\u001c\f\u0007\b\f\u000f\b", new Object[0], 2);
      Lit96 = new SyntaxTemplate("\u0001\u0001\u0001", "\u0011\u0018\u0004\t\u000b\b\u0013", new Object[]{Lit69}, 0);
      Lit95 = new SyntaxPattern(",\f\u0007\f\u000f\b\f\u0017\b", new Object[0], 3);
      Lit94 = (SimpleSymbol)(new SimpleSymbol("test-end")).readResolve();
      var0 = Lit92;
      var1 = new SyntaxPattern("\f\u0018\f\u0007\f\u000f\b", new Object[0], 2);
      var2 = Lit144;
      var3 = Lit161;
      var4 = Lit86;
      var5 = Lit162;
      var6 = (SimpleSymbol)(new SimpleSymbol("%test-evaluate-with-catch")).readResolve();
      Lit84 = var6;
      var16 = new SyntaxRule(var1, "\u0001\u0001", "\u0011\u0018\u0004\t\u0010ű\u0011\u0018\f)\u0011\u0018\u0014\b\u0003\b\u0011\u0018\u0004\t\u0010\b\u0011\u0018\u0004Q\b\u0011\u0018\u001c\b\u0011\u0018$\b\u000b9\u0011\u0018,\t\u0003\u00184\b\u0011\u0018<\t\u0003\u0018D\u0018L", new Object[]{var2, var3, var4, var5, var6, Lit78, PairWithPosition.make(PairWithPosition.make(Lit15, PairWithPosition.make(Lit155, LList.Empty, "testing.scm", 2666526), "testing.scm", 2666526), PairWithPosition.make(Lit162, LList.Empty, "testing.scm", 2666539), "testing.scm", 2666525), Lit87, PairWithPosition.make(Lit162, LList.Empty, "testing.scm", 2670622), PairWithPosition.make(PairWithPosition.make(Lit83, LList.Empty, "testing.scm", 2674696), LList.Empty, "testing.scm", 2674696)}, 0);
      Lit93 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var16}, 2);
      var0 = Lit89;
      var16 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\f\u0017\f\u001f\b", new Object[0], 4), "\u0001\u0001\u0001\u0001", "\u0011\u0018\u0004\t\u0010Ǳ\u0011\u0018\f)\u0011\u0018\u0014\b\u0003\b\u0011\u0018\u00041\b\u0011\u0018\u001c\b\u00139\u0011\u0018$\t\u0003\u0018,\b\u0011\u0018\u0004Q\b\u0011\u00184\b\u0011\u0018<\b\u001b9\u0011\u0018$\t\u0003\u0018D\b\u0011\u0018L\t\u0003\b\t\u000b\u0018T\u0018\\", new Object[]{Lit144, Lit161, Lit86, Lit163, Lit78, PairWithPosition.make(PairWithPosition.make(Lit15, PairWithPosition.make((SimpleSymbol)(new SimpleSymbol("expected-value")).readResolve(), LList.Empty, "testing.scm", 2592794), "testing.scm", 2592794), PairWithPosition.make(Lit163, LList.Empty, "testing.scm", 2592809), "testing.scm", 2592793), Lit162, Lit84, PairWithPosition.make(PairWithPosition.make(Lit15, PairWithPosition.make(Lit155, LList.Empty, "testing.scm", 2600988), "testing.scm", 2600988), PairWithPosition.make(Lit162, LList.Empty, "testing.scm", 2601001), "testing.scm", 2600987), Lit87, PairWithPosition.make(Lit163, PairWithPosition.make(Lit162, LList.Empty, "testing.scm", 2605094), "testing.scm", 2605090), PairWithPosition.make(PairWithPosition.make(Lit83, LList.Empty, "testing.scm", 2609158), LList.Empty, "testing.scm", 2609158)}, 0);
      Lit90 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var16}, 4);
      Lit88 = (SimpleSymbol)(new SimpleSymbol("test-runner-test-name")).readResolve();
      var0 = Lit84;
      var16 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\b", new Object[0], 1), "\u0001", "\u0011\u0018\u0004\t\u0003\u0018\f", new Object[]{Lit154, PairWithPosition.make(PairWithPosition.make(Lit151, PairWithPosition.make(Lit156, PairWithPosition.make(PairWithPosition.make(Lit78, PairWithPosition.make(PairWithPosition.make(Lit146, LList.Empty, "testing.scm", 2347035), PairWithPosition.make(PairWithPosition.make(Lit15, PairWithPosition.make(Lit157, LList.Empty, "testing.scm", 2347058), "testing.scm", 2347058), PairWithPosition.make(Lit151, LList.Empty, "testing.scm", 2347071), "testing.scm", 2347057), "testing.scm", 2347035), "testing.scm", 2347017), PairWithPosition.make(Boolean.FALSE, LList.Empty, "testing.scm", 2351113), "testing.scm", 2347017), "testing.scm", 2342921), "testing.scm", 2342917), LList.Empty, "testing.scm", 2342917)}, 0);
      Lit85 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var16}, 1);
      Lit82 = (SimpleSymbol)(new SimpleSymbol("test-passed?")).readResolve();
      Lit81 = (SimpleSymbol)(new SimpleSymbol("test-result-kind")).readResolve();
      Lit80 = (SimpleSymbol)(new SimpleSymbol("test-result-remove")).readResolve();
      Lit79 = (SimpleSymbol)(new SimpleSymbol("test-result-clear")).readResolve();
      Lit77 = (SimpleSymbol)(new SimpleSymbol("test-on-test-end-simple")).readResolve();
      var0 = (SimpleSymbol)(new SimpleSymbol("test-result-ref")).readResolve();
      Lit75 = var0;
      var16 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\b", new Object[0], 2), "\u0001\u0001", "\u0011\u0018\u0004\t\u0003\t\u000b\u0018\f", new Object[]{Lit75, PairWithPosition.make(Boolean.FALSE, LList.Empty, "testing.scm", 1933348)}, 0);
      var18 = new SyntaxPattern("\f\u0018\f\u0007\f\u000f\f\u0017\b", new Object[0], 3);
      var3 = Lit144;
      var4 = Lit164;
      var5 = (SimpleSymbol)(new SimpleSymbol("assq")).readResolve();
      var6 = (SimpleSymbol)(new SimpleSymbol("test-result-alist")).readResolve();
      Lit51 = var6;
      var19 = new SyntaxRule(var18, "\u0001\u0001\u0001", "\u0011\u0018\u0004\u0081\b\u0011\u0018\f\b\u0011\u0018\u0014\t\u000b\b\u0011\u0018\u001c\b\u0003\b\u0011\u0018$\u0011\u0018\f\u0011\u0018,\b\u0013", new Object[]{var3, var4, var5, var6, Lit161, PairWithPosition.make((SimpleSymbol)(new SimpleSymbol("cdr")).readResolve(), PairWithPosition.make(Lit164, LList.Empty, "testing.scm", 1945619), "testing.scm", 1945614)}, 0);
      Lit76 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var16, var19}, 3);
      Lit74 = (SimpleSymbol)(new SimpleSymbol("test-on-test-begin-simple")).readResolve();
      var0 = (SimpleSymbol)(new SimpleSymbol("test-group-with-cleanup")).readResolve();
      Lit72 = var0;
      var1 = new SyntaxPattern("\f\u0018\f\u0007\f\u000f\f\u0017\b", new Object[0], 3);
      var2 = (SimpleSymbol)(new SimpleSymbol("test-group")).readResolve();
      Lit70 = var2;
      var16 = new SyntaxRule(var1, "\u0001\u0001\u0001", "\u0011\u0018\u0004\t\u0003\b\u0011\u0018\f\u0011\u0018\u00149\u0011\u0018\u001c\t\u0010\b\u000b\b\u0011\u0018\u001c\t\u0010\b\u0013", new Object[]{var2, Lit165, PairWithPosition.make(Lit147, PairWithPosition.make(LList.Empty, PairWithPosition.make(Boolean.FALSE, LList.Empty, "testing.scm", 1826831), "testing.scm", 1826828), "testing.scm", 1826820), Lit147}, 0);
      var19 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\b", new Object[0], 2), "\u0001\u0001", "\u0011\u0018\u0004\t\u0003\u0011\u0018\f\b\u000b", new Object[]{Lit72, Boolean.FALSE}, 0);
      SyntaxRule var24 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\f\u0017\f\u001f#", new Object[0], 5), "\u0001\u0001\u0001\u0001\u0000", "\u0011\u0018\u0004\t\u00039\u0011\u0018\f\t\u000b\b\u0013\t\u001b\"", new Object[]{Lit72, (SimpleSymbol)(new SimpleSymbol("begin")).readResolve()}, 0);
      Lit73 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var16, var19, var24}, 5);
      var0 = Lit70;
      var1 = new SyntaxPattern("\f\u0018\f\u0007\u000b", new Object[0], 2);
      var2 = Lit144;
      var17 = PairWithPosition.make(PairWithPosition.make(Lit149, PairWithPosition.make(PairWithPosition.make(Lit146, LList.Empty, "testing.scm", 1769487), LList.Empty, "testing.scm", 1769487), "testing.scm", 1769484), LList.Empty, "testing.scm", 1769483);
      var4 = Lit52;
      var5 = Lit149;
      var6 = (SimpleSymbol)(new SimpleSymbol("list")).readResolve();
      var7 = Lit145;
      PairWithPosition var26 = PairWithPosition.make(Lit15, PairWithPosition.make(Lit7, LList.Empty, "testing.scm", 1777707), "testing.scm", 1777707);
      var9 = Lit161;
      SimpleSymbol var29 = (SimpleSymbol)(new SimpleSymbol("%test-should-execute")).readResolve();
      Lit62 = var29;
      var16 = new SyntaxRule(var1, "\u0001\u0000", "\u0011\u0018\u0004\u0011\u0018\f\u0099\u0011\u0018\u0014\u0011\u0018\u001c\b\u0011\u0018$\b\u0011\u0018,\u0011\u00184\b\u0003\b\u0011\u0018<\u0011\u0018D\b\u0011\u0018LY\u0011\u0018T\t\u0010\b\u0011\u0018\\\b\u00031\u0011\u0018T\t\u0010\n\b\u0011\u0018T\t\u0010\b\u0011\u0018d\b\u0003", new Object[]{var2, var17, var4, var5, var6, var7, var26, var9, PairWithPosition.make(var29, PairWithPosition.make(Lit149, LList.Empty, "testing.scm", 1781794), "testing.scm", 1781772), Lit165, Lit147, (SimpleSymbol)(new SimpleSymbol("test-begin")).readResolve(), Lit94}, 0);
      Lit71 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var16}, 2);
      Lit68 = (SimpleSymbol)(new SimpleSymbol("test-on-final-simple")).readResolve();
      Lit67 = (SimpleSymbol)(new SimpleSymbol("test-on-bad-end-name-simple")).readResolve();
      Lit66 = (SimpleSymbol)(new SimpleSymbol("test-on-bad-count-simple")).readResolve();
      Lit65 = (SimpleSymbol)(new SimpleSymbol("test-on-group-end-simple")).readResolve();
      Lit64 = (SimpleSymbol)(new SimpleSymbol("test-on-group-begin-simple")).readResolve();
      Lit63 = (SimpleSymbol)(new SimpleSymbol("%test-begin")).readResolve();
      Lit61 = (SimpleSymbol)(new SimpleSymbol("test-runner-create")).readResolve();
      Lit59 = (SimpleSymbol)(new SimpleSymbol("test-runner-simple")).readResolve();
      Lit58 = (SimpleSymbol)(new SimpleSymbol("test-runner-null")).readResolve();
      Lit57 = (SimpleSymbol)(new SimpleSymbol("%test-null-callback")).readResolve();
      Lit56 = (SimpleSymbol)(new SimpleSymbol("test-runner-group-path")).readResolve();
      Lit55 = (SimpleSymbol)(new SimpleSymbol("test-runner-reset")).readResolve();
      Lit54 = (SimpleSymbol)(new SimpleSymbol("test-runner-aux-value!")).readResolve();
      Lit53 = (SimpleSymbol)(new SimpleSymbol("test-runner-aux-value")).readResolve();
      Lit50 = (SimpleSymbol)(new SimpleSymbol("test-runner-on-bad-end-name!")).readResolve();
      Lit49 = (SimpleSymbol)(new SimpleSymbol("test-runner-on-bad-end-name")).readResolve();
      Lit48 = (SimpleSymbol)(new SimpleSymbol("test-runner-on-bad-count!")).readResolve();
      Lit47 = (SimpleSymbol)(new SimpleSymbol("test-runner-on-bad-count")).readResolve();
      Lit46 = (SimpleSymbol)(new SimpleSymbol("test-runner-on-final!")).readResolve();
      Lit45 = (SimpleSymbol)(new SimpleSymbol("test-runner-on-final")).readResolve();
      Lit44 = (SimpleSymbol)(new SimpleSymbol("test-runner-on-group-end!")).readResolve();
      Lit43 = (SimpleSymbol)(new SimpleSymbol("test-runner-on-group-end")).readResolve();
      Lit42 = (SimpleSymbol)(new SimpleSymbol("test-runner-on-group-begin!")).readResolve();
      Lit41 = (SimpleSymbol)(new SimpleSymbol("test-runner-on-group-begin")).readResolve();
      Lit40 = (SimpleSymbol)(new SimpleSymbol("test-runner-on-test-end!")).readResolve();
      Lit39 = (SimpleSymbol)(new SimpleSymbol("test-runner-on-test-end")).readResolve();
      Lit38 = (SimpleSymbol)(new SimpleSymbol("test-runner-on-test-begin!")).readResolve();
      Lit37 = (SimpleSymbol)(new SimpleSymbol("test-runner-on-test-begin")).readResolve();
      Lit36 = (SimpleSymbol)(new SimpleSymbol("test-runner-group-stack!")).readResolve();
      Lit35 = (SimpleSymbol)(new SimpleSymbol("test-runner-group-stack")).readResolve();
      Lit30 = (SimpleSymbol)(new SimpleSymbol("test-runner-skip-count!")).readResolve();
      Lit29 = (SimpleSymbol)(new SimpleSymbol("test-runner-skip-count")).readResolve();
      Lit28 = (SimpleSymbol)(new SimpleSymbol("test-runner-xfail-count!")).readResolve();
      Lit27 = (SimpleSymbol)(new SimpleSymbol("test-runner-xfail-count")).readResolve();
      Lit26 = (SimpleSymbol)(new SimpleSymbol("test-runner-xpass-count!")).readResolve();
      Lit25 = (SimpleSymbol)(new SimpleSymbol("test-runner-xpass-count")).readResolve();
      Lit24 = (SimpleSymbol)(new SimpleSymbol("test-runner-fail-count!")).readResolve();
      Lit23 = (SimpleSymbol)(new SimpleSymbol("test-runner-fail-count")).readResolve();
      Lit22 = (SimpleSymbol)(new SimpleSymbol("test-runner-pass-count!")).readResolve();
      Lit21 = (SimpleSymbol)(new SimpleSymbol("test-runner-pass-count")).readResolve();
      Lit20 = (SimpleSymbol)(new SimpleSymbol("test-runner?")).readResolve();
      Lit19 = new SyntaxTemplate("\u0001\u0001\u0001\u0001\u0001", "\u0011\u0018\u0004\u0011\u0018\fA\u0011\u0018\u0014\u0011\u0018\u001c\b\u001b\b\u0011\u0018$\u0011\u0018\u001c\t#\t\u000b\b\u0013", new Object[]{Lit150, PairWithPosition.make(PairWithPosition.make(Lit149, PairWithPosition.make(PairWithPosition.make(Lit60, LList.Empty, "testing.scm", 2834444), LList.Empty, "testing.scm", 2834444), "testing.scm", 2834441), LList.Empty, "testing.scm", 2834440), Lit52, Lit149, Lit89}, 0);
      Lit18 = new SyntaxPattern("<\f\u0007\f\u000f\f\u0017\b\f\u001f\f\'\b", new Object[0], 5);
      Lit17 = new SyntaxTemplate("\u0001\u0001\u0001\u0001\u0001\u0001", "\u0011\u0018\u0004I\u0011\u0018\f\b\u0011\u0018\u0014\b\u000b©\u0011\u0018\u001c\u0011\u0018$\b\u0011\u0018,A\u0011\u0018,\u0011\u00184\b\u000b\b#\b\u0011\u0018<\u0011\u0018$\t+\t\u0013\b\u001b", new Object[]{Lit150, PairWithPosition.make(Lit149, PairWithPosition.make(PairWithPosition.make(Lit60, LList.Empty, "testing.scm", 2809868), LList.Empty, "testing.scm", 2809868), "testing.scm", 2809865), Lit160, Lit52, Lit149, Lit145, PairWithPosition.make(Lit15, PairWithPosition.make(Lit7, LList.Empty, "testing.scm", 2818087), "testing.scm", 2818087), Lit89}, 0);
      Lit16 = new SyntaxPattern("L\f\u0007\f\u000f\f\u0017\f\u001f\b\f\'\f/\b", new Object[0], 6);
      Lit14 = (SimpleSymbol)(new SimpleSymbol("fail")).readResolve();
      Lit12 = (SimpleSymbol)(new SimpleSymbol("pass")).readResolve();
      var0 = Lit12;
      SimpleSymbol var28 = (SimpleSymbol)(new SimpleSymbol("xpass")).readResolve();
      Lit9 = var28;
      Lit11 = PairWithPosition.make(var0, PairWithPosition.make(var28, LList.Empty, "testing.scm", 2220088), "testing.scm", 2220082);
      var0 = Lit7;
      var28 = (SimpleSymbol)(new SimpleSymbol("source-file")).readResolve();
      Lit4 = var28;
      var2 = (SimpleSymbol)(new SimpleSymbol("source-line")).readResolve();
      Lit5 = var2;
      var3 = (SimpleSymbol)(new SimpleSymbol("source-form")).readResolve();
      Lit6 = var3;
      Lit10 = PairWithPosition.make(var0, PairWithPosition.make(var28, PairWithPosition.make(var2, PairWithPosition.make(var3, LList.Empty, "testing.scm", 2072618), "testing.scm", 2072606), "testing.scm", 2072594), "testing.scm", 2072583);
      Lit8 = PairWithPosition.make(Lit14, PairWithPosition.make(Lit9, LList.Empty, "testing.scm", 1966107), "testing.scm", 1966101);
      Lit3 = (SimpleSymbol)(new SimpleSymbol("xfail")).readResolve();
      Lit2 = (SimpleSymbol)(new SimpleSymbol("skip")).readResolve();
      Lit1 = (SimpleSymbol)(new SimpleSymbol("result-kind")).readResolve();
      Lit0 = IntNum.make(0);
      $instance = new testing();
      test$Mnrunner = testing.Mnrunner.class;
      testing var23 = $instance;
      test$Mnrunner$Qu = new ModuleMethod(var23, 12, Lit20, 4097);
      test$Mnrunner$Mnpass$Mncount = new ModuleMethod(var23, 13, Lit21, 4097);
      test$Mnrunner$Mnpass$Mncount$Ex = new ModuleMethod(var23, 14, Lit22, 8194);
      test$Mnrunner$Mnfail$Mncount = new ModuleMethod(var23, 15, Lit23, 4097);
      test$Mnrunner$Mnfail$Mncount$Ex = new ModuleMethod(var23, 16, Lit24, 8194);
      test$Mnrunner$Mnxpass$Mncount = new ModuleMethod(var23, 17, Lit25, 4097);
      test$Mnrunner$Mnxpass$Mncount$Ex = new ModuleMethod(var23, 18, Lit26, 8194);
      test$Mnrunner$Mnxfail$Mncount = new ModuleMethod(var23, 19, Lit27, 4097);
      test$Mnrunner$Mnxfail$Mncount$Ex = new ModuleMethod(var23, 20, Lit28, 8194);
      test$Mnrunner$Mnskip$Mncount = new ModuleMethod(var23, 21, Lit29, 4097);
      test$Mnrunner$Mnskip$Mncount$Ex = new ModuleMethod(var23, 22, Lit30, 8194);
      $Prvt$$Pctest$Mnrunner$Mnskip$Mnlist = new ModuleMethod(var23, 23, Lit31, 4097);
      $Prvt$$Pctest$Mnrunner$Mnskip$Mnlist$Ex = new ModuleMethod(var23, 24, Lit32, 8194);
      $Prvt$$Pctest$Mnrunner$Mnfail$Mnlist = new ModuleMethod(var23, 25, Lit33, 4097);
      $Prvt$$Pctest$Mnrunner$Mnfail$Mnlist$Ex = new ModuleMethod(var23, 26, Lit34, 8194);
      test$Mnrunner$Mngroup$Mnstack = new ModuleMethod(var23, 27, Lit35, 4097);
      test$Mnrunner$Mngroup$Mnstack$Ex = new ModuleMethod(var23, 28, Lit36, 8194);
      test$Mnrunner$Mnon$Mntest$Mnbegin = new ModuleMethod(var23, 29, Lit37, 4097);
      test$Mnrunner$Mnon$Mntest$Mnbegin$Ex = new ModuleMethod(var23, 30, Lit38, 8194);
      test$Mnrunner$Mnon$Mntest$Mnend = new ModuleMethod(var23, 31, Lit39, 4097);
      test$Mnrunner$Mnon$Mntest$Mnend$Ex = new ModuleMethod(var23, 32, Lit40, 8194);
      test$Mnrunner$Mnon$Mngroup$Mnbegin = new ModuleMethod(var23, 33, Lit41, 4097);
      test$Mnrunner$Mnon$Mngroup$Mnbegin$Ex = new ModuleMethod(var23, 34, Lit42, 8194);
      test$Mnrunner$Mnon$Mngroup$Mnend = new ModuleMethod(var23, 35, Lit43, 4097);
      test$Mnrunner$Mnon$Mngroup$Mnend$Ex = new ModuleMethod(var23, 36, Lit44, 8194);
      test$Mnrunner$Mnon$Mnfinal = new ModuleMethod(var23, 37, Lit45, 4097);
      test$Mnrunner$Mnon$Mnfinal$Ex = new ModuleMethod(var23, 38, Lit46, 8194);
      test$Mnrunner$Mnon$Mnbad$Mncount = new ModuleMethod(var23, 39, Lit47, 4097);
      test$Mnrunner$Mnon$Mnbad$Mncount$Ex = new ModuleMethod(var23, 40, Lit48, 8194);
      test$Mnrunner$Mnon$Mnbad$Mnend$Mnname = new ModuleMethod(var23, 41, Lit49, 4097);
      test$Mnrunner$Mnon$Mnbad$Mnend$Mnname$Ex = new ModuleMethod(var23, 42, Lit50, 8194);
      test$Mnresult$Mnalist = new ModuleMethod(var23, 43, Lit51, 4097);
      test$Mnresult$Mnalist$Ex = new ModuleMethod(var23, 44, Lit52, 8194);
      test$Mnrunner$Mnaux$Mnvalue = new ModuleMethod(var23, 45, Lit53, 4097);
      test$Mnrunner$Mnaux$Mnvalue$Ex = new ModuleMethod(var23, 46, Lit54, 8194);
      test$Mnrunner$Mnreset = new ModuleMethod(var23, 47, Lit55, 4097);
      test$Mnrunner$Mngroup$Mnpath = new ModuleMethod(var23, 48, Lit56, 4097);
      $Pctest$Mnnull$Mncallback = new ModuleMethod(var23, 49, Lit57, 4097);
      ModuleMethod var30 = new ModuleMethod(var23, 50, (Object)null, 12291);
      var30.setProperty("source-location", "testing.scm:182");
      lambda$Fn1 = var30;
      var30 = new ModuleMethod(var23, 51, (Object)null, 12291);
      var30.setProperty("source-location", "testing.scm:187");
      lambda$Fn2 = var30;
      var30 = new ModuleMethod(var23, 52, (Object)null, 12291);
      var30.setProperty("source-location", "testing.scm:188");
      lambda$Fn3 = var30;
      test$Mnrunner$Mnnull = new ModuleMethod(var23, 53, Lit58, 0);
      test$Mnrunner$Mnsimple = new ModuleMethod(var23, 54, Lit59, 0);
      test$Mnrunner$Mnget = new ModuleMethod(var23, 55, Lit60, 0);
      test$Mnrunner$Mncreate = new ModuleMethod(var23, 56, Lit61, 0);
      $Prvt$$Pctest$Mnshould$Mnexecute = new ModuleMethod(var23, 57, Lit62, 4097);
      $Pctest$Mnbegin = new ModuleMethod(var23, 58, Lit63, 8194);
      test$Mnon$Mngroup$Mnbegin$Mnsimple = new ModuleMethod(var23, 59, Lit64, 12291);
      test$Mnon$Mngroup$Mnend$Mnsimple = new ModuleMethod(var23, 60, Lit65, 4097);
      test$Mnon$Mnbad$Mncount$Mnsimple = new ModuleMethod(var23, 61, Lit66, 12291);
      test$Mnon$Mnbad$Mnend$Mnname$Mnsimple = new ModuleMethod(var23, 62, Lit67, 12291);
      test$Mnon$Mnfinal$Mnsimple = new ModuleMethod(var23, 63, Lit68, 4097);
      $Prvt$$Pctest$Mnend = new ModuleMethod(var23, 64, Lit69, 8194);
      $Prvt$test$Mngroup = Macro.make(Lit70, Lit71, $instance);
      test$Mngroup$Mnwith$Mncleanup = Macro.make(Lit72, Lit73, $instance);
      test$Mnon$Mntest$Mnbegin$Mnsimple = new ModuleMethod(var23, 65, Lit74, 4097);
      test$Mnresult$Mnref = Macro.make(Lit75, Lit76, $instance);
      test$Mnon$Mntest$Mnend$Mnsimple = new ModuleMethod(var23, 66, Lit77, 4097);
      test$Mnresult$Mnset$Ex = new ModuleMethod(var23, 67, Lit78, 12291);
      test$Mnresult$Mnclear = new ModuleMethod(var23, 68, Lit79, 4097);
      test$Mnresult$Mnremove = new ModuleMethod(var23, 69, Lit80, 8194);
      test$Mnresult$Mnkind = new ModuleMethod(var23, 70, Lit81, -4096);
      test$Mnpassed$Qu = new ModuleMethod(var23, 71, Lit82, -4096);
      $Prvt$$Pctest$Mnreport$Mnresult = new ModuleMethod(var23, 72, Lit83, 0);
      $Prvt$$Pctest$Mnevaluate$Mnwith$Mncatch = Macro.make(Lit84, Lit85, $instance);
      $Prvt$$Pctest$Mnon$Mntest$Mnbegin = new ModuleMethod(var23, 73, Lit86, 4097);
      $Prvt$$Pctest$Mnon$Mntest$Mnend = new ModuleMethod(var23, 74, Lit87, 8194);
      test$Mnrunner$Mntest$Mnname = new ModuleMethod(var23, 75, Lit88, 4097);
      $Prvt$$Pctest$Mncomp2body = Macro.make(Lit89, Lit90, $instance);
      $Prvt$$Pctest$Mnapproximimate$Eq = new ModuleMethod(var23, 76, Lit91, 4097);
      $Prvt$$Pctest$Mncomp1body = Macro.make(Lit92, Lit93, $instance);
      var28 = Lit94;
      ModuleMethod var25 = new ModuleMethod(var23, 77, (Object)null, 4097);
      var25.setProperty("source-location", "testing.scm:660");
      test$Mnend = Macro.make(var28, var25, $instance);
      var28 = Lit99;
      var25 = new ModuleMethod(var23, 78, (Object)null, 4097);
      var25.setProperty("source-location", "testing.scm:669");
      test$Mnassert = Macro.make(var28, var25, $instance);
      var28 = Lit104;
      var25 = new ModuleMethod(var23, 79, (Object)null, 4097);
      var25.setProperty("source-location", "testing.scm:696");
      test$Mneqv = Macro.make(var28, var25, $instance);
      var28 = Lit106;
      var25 = new ModuleMethod(var23, 80, (Object)null, 4097);
      var25.setProperty("source-location", "testing.scm:698");
      test$Mneq = Macro.make(var28, var25, $instance);
      var28 = Lit108;
      var25 = new ModuleMethod(var23, 81, (Object)null, 4097);
      var25.setProperty("source-location", "testing.scm:700");
      test$Mnequal = Macro.make(var28, var25, $instance);
      var28 = Lit110;
      var25 = new ModuleMethod(var23, 82, (Object)null, 4097);
      var25.setProperty("source-location", "testing.scm:702");
      test$Mnapproximate = Macro.make(var28, var25, $instance);
      $Prvt$$Pctest$Mnerror = Macro.make(Lit115, Lit116, $instance);
      var28 = Lit117;
      var25 = new ModuleMethod(var23, 83, (Object)null, 4097);
      var25.setProperty("source-location", "testing.scm:843");
      test$Mnerror = Macro.make(var28, var25, $instance);
      test$Mnapply = new ModuleMethod(var23, 84, Lit124, -4095);
      test$Mnwith$Mnrunner = Macro.make(Lit125, Lit126, $instance);
      $Prvt$$Pctest$Mnmatch$Mnnth = new ModuleMethod(var23, 85, Lit127, 8194);
      test$Mnmatch$Mnnth = Macro.make(Lit128, Lit129, $instance);
      $Prvt$$Pctest$Mnmatch$Mnall = new ModuleMethod(var23, 86, Lit130, -4096);
      test$Mnmatch$Mnall = Macro.make(Lit131, Lit132, $instance);
      $Prvt$$Pctest$Mnmatch$Mnany = new ModuleMethod(var23, 87, Lit133, -4096);
      test$Mnmatch$Mnany = Macro.make(Lit134, Lit135, $instance);
      $Prvt$$Pctest$Mnas$Mnspecifier = new ModuleMethod(var23, 88, Lit136, 4097);
      test$Mnskip = Macro.make(Lit137, Lit138, $instance);
      test$Mnexpect$Mnfail = Macro.make(Lit139, Lit140, $instance);
      test$Mnmatch$Mnname = new ModuleMethod(var23, 89, Lit141, 4097);
      test$Mnread$Mneval$Mnstring = new ModuleMethod(var23, 90, Lit142, 4097);
      $instance.run();
   }

   public testing() {
      ModuleInfo.register(this);
   }

   public static Object isTestPassed$V(Object[] var0) {
      LList var4 = LList.makeList(var0, 0);
      Object var5;
      if(lists.isPair(var4)) {
         var5 = lists.car.apply1(var4);
      } else {
         var5 = testRunnerGet();
      }

      SimpleSymbol var1 = Lit1;

      testing.Mnrunner var2;
      try {
         var2 = (testing.Mnrunner)var5;
      } catch (ClassCastException var3) {
         throw new WrongType(var3, "test-result-alist", 0, var5);
      }

      var5 = lists.assq(var1, testResultAlist(var2));
      if(var5 != Boolean.FALSE) {
         var5 = lists.cdr.apply1(var5);
      } else {
         var5 = Boolean.FALSE;
      }

      return lists.memq(var5, Lit11);
   }

   public static boolean isTestRunner(Object var0) {
      return var0 instanceof testing.Mnrunner;
   }

   static Boolean lambda1(Object var0, Object var1, Object var2) {
      return Boolean.FALSE;
   }

   static Object lambda16(Object var0) {
      Pair var1 = LList.list2(var0, LList.list2(Lit15, $PcTestSourceLine2(var0)));
      Object[] var2 = SyntaxPattern.allocVars(3, (Object[])null);
      TemplateScope var3;
      if(Lit95.match(var1, var2, 0)) {
         var3 = TemplateScope.make();
         return Lit96.execute(var2, var3);
      } else if(Lit97.match(var1, var2, 0)) {
         var3 = TemplateScope.make();
         return Lit98.execute(var2, var3);
      } else {
         return syntax_case.error("syntax-case", var1);
      }
   }

   static Object lambda17(Object var0) {
      Pair var1 = LList.list2(var0, LList.list2(Lit15, $PcTestSourceLine2(var0)));
      Object[] var2 = SyntaxPattern.allocVars(4, (Object[])null);
      TemplateScope var3;
      if(Lit100.match(var1, var2, 0)) {
         var3 = TemplateScope.make();
         return Lit101.execute(var2, var3);
      } else if(Lit102.match(var1, var2, 0)) {
         var3 = TemplateScope.make();
         return Lit103.execute(var2, var3);
      } else {
         return syntax_case.error("syntax-case", var1);
      }
   }

   static Object lambda18(Object var0) {
      TemplateScope var1 = TemplateScope.make();
      return $PcTestComp2(Lit105.execute((Object[])null, var1), var0);
   }

   static Object lambda19(Object var0) {
      TemplateScope var1 = TemplateScope.make();
      return $PcTestComp2(Lit107.execute((Object[])null, var1), var0);
   }

   static Boolean lambda2(Object var0, Object var1, Object var2) {
      return Boolean.FALSE;
   }

   static Object lambda20(Object var0) {
      TemplateScope var1 = TemplateScope.make();
      return $PcTestComp2(Lit109.execute((Object[])null, var1), var0);
   }

   static Object lambda21(Object var0) {
      Pair var1 = LList.list2(var0, LList.list2(Lit15, $PcTestSourceLine2(var0)));
      Object[] var2 = SyntaxPattern.allocVars(6, (Object[])null);
      TemplateScope var3;
      if(Lit111.match(var1, var2, 0)) {
         var3 = TemplateScope.make();
         return Lit112.execute(var2, var3);
      } else if(Lit113.match(var1, var2, 0)) {
         var3 = TemplateScope.make();
         return Lit114.execute(var2, var3);
      } else {
         return syntax_case.error("syntax-case", var1);
      }
   }

   static Object lambda22(Object var0) {
      Pair var1 = LList.list2(var0, LList.list2(Lit15, $PcTestSourceLine2(var0)));
      Object[] var2 = SyntaxPattern.allocVars(5, (Object[])null);
      TemplateScope var3;
      if(Lit118.match(var1, var2, 0)) {
         var3 = TemplateScope.make();
         return Lit119.execute(var2, var3);
      } else if(Lit120.match(var1, var2, 0)) {
         var3 = TemplateScope.make();
         return Lit121.execute(var2, var3);
      } else if(Lit122.match(var1, var2, 0)) {
         var3 = TemplateScope.make();
         return Lit123.execute(var2, var3);
      } else {
         return syntax_case.error("syntax-case", var1);
      }
   }

   static Boolean lambda3(Object var0, Object var1, Object var2) {
      return Boolean.FALSE;
   }

   public static Object testApply$V(Object var0, Object[] var1) {
      testing.frame1 var2 = new testing.frame1();
      var2.first = var0;
      var2.rest = LList.makeList(var1, 0);
      if(isTestRunner(var2.first)) {
         var2.saved$Mnrunner$1 = ((Procedure)test$Mnrunner$Mncurrent).apply0();
         return misc.dynamicWind(var2.lambda$Fn5, var2.lambda$Fn6, var2.lambda$Fn7);
      } else {
         Object var13 = ((Procedure)test$Mnrunner$Mncurrent).apply0();
         if(var13 != Boolean.FALSE) {
            testing.Mnrunner var11;
            try {
               var11 = (testing.Mnrunner)var13;
            } catch (ClassCastException var9) {
               throw new WrongType(var9, "%test-runner-run-list", 0, var13);
            }

            Object var16 = $PcTestRunnerRunList(var11);
            if(lists.isNull(var2.rest)) {
               try {
                  var11 = (testing.Mnrunner)var13;
               } catch (ClassCastException var6) {
                  throw new WrongType(var6, "%test-runner-run-list!", 0, var13);
               }

               LList var15;
               try {
                  var15 = (LList)var16;
               } catch (ClassCastException var5) {
                  throw new WrongType(var5, "reverse!", 1, var16);
               }

               $PcTestRunnerRunList$Ex(var11, lists.reverse$Ex(var15));
               return Scheme.applyToArgs.apply1(var2.first);
            } else {
               testing.Mnrunner var4;
               try {
                  var4 = (testing.Mnrunner)var13;
               } catch (ClassCastException var8) {
                  throw new WrongType(var8, "%test-runner-run-list!", 0, var13);
               }

               Pair var12;
               if(var16 == Boolean.TRUE) {
                  var12 = LList.list1(var2.first);
               } else {
                  var12 = lists.cons(var2.first, var16);
               }

               $PcTestRunnerRunList$Ex(var4, var12);
               Scheme.apply.apply2(test$Mnapply, var2.rest);

               try {
                  var11 = (testing.Mnrunner)var13;
               } catch (ClassCastException var7) {
                  throw new WrongType(var7, "%test-runner-run-list!", 0, var13);
               }

               $PcTestRunnerRunList$Ex(var11, var16);
               return Values.empty;
            }
         } else {
            var2.r = testRunnerCreate();
            var2.saved$Mnrunner = ((Procedure)test$Mnrunner$Mncurrent).apply0();
            misc.dynamicWind(var2.lambda$Fn8, var2.lambda$Fn9, var2.lambda$Fn10);
            ApplyToArgs var14 = Scheme.applyToArgs;
            var0 = var2.r;

            testing.Mnrunner var3;
            try {
               var3 = (testing.Mnrunner)var0;
            } catch (ClassCastException var10) {
               throw new WrongType(var10, "test-runner-on-final", 0, var0);
            }

            return var14.apply2(testRunnerOnFinal(var3), var2.r);
         }
      }
   }

   public static Procedure testMatchName(Object var0) {
      testing.frame5 var1 = new testing.frame5();
      var1.name = var0;
      return var1.lambda$Fn14;
   }

   public static void testOnBadCountSimple(Object var0, Object var1, Object var2) {
      $PcTestOnBadCountWrite(var0, var1, var2, ports.current$Mnoutput$Mnport.apply0());

      testing.Mnrunner var3;
      try {
         var3 = (testing.Mnrunner)var0;
      } catch (ClassCastException var4) {
         throw new WrongType(var4, "test-runner-aux-value", 0, var0);
      }

      Object var5 = testRunnerAuxValue(var3);
      if(ports.isOutputPort(var5)) {
         $PcTestOnBadCountWrite(var0, var1, var2, var5);
      }

   }

   public static Object testOnBadEndNameSimple(Object var0, Object var1, Object var2) {
      return misc.error$V(strings.stringAppend(new Object[]{$PcTestFormatLine(var0), "test-end ", var1, " does not match test-begin ", var2}), new Object[0]);
   }

   public static void testOnFinalSimple(Object var0) {
      $PcTestFinalReportSimple(var0, ports.current$Mnoutput$Mnport.apply0());

      testing.Mnrunner var1;
      try {
         var1 = (testing.Mnrunner)var0;
      } catch (ClassCastException var2) {
         throw new WrongType(var2, "test-runner-aux-value", 0, var0);
      }

      Object var3 = testRunnerAuxValue(var1);
      if(ports.isOutputPort(var3)) {
         $PcTestFinalReportSimple(var0, var3);
      }

   }

   public static Boolean testOnGroupBeginSimple(Object var0, Object var1, Object var2) {
      testing.Mnrunner var9;
      try {
         var9 = (testing.Mnrunner)var0;
      } catch (ClassCastException var8) {
         throw new WrongType(var8, "test-runner-group-stack", 0, var0);
      }

      if(lists.isNull(testRunnerGroupStack(var9))) {
         ports.display("%%%% Starting test ");
         ports.display(var1);
         if(strings.isString(Boolean.TRUE)) {
            var2 = Boolean.TRUE;
         } else {
            var2 = strings.stringAppend(new Object[]{var1, ".log"});
         }

         Path var3;
         try {
            var3 = Path.valueOf(var2);
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "open-output-file", 1, var2);
         }

         OutPort var10 = ports.openOutputFile(var3);
         ports.display("%%%% Starting test ", var10);
         ports.display(var1, var10);
         ports.newline(var10);

         testing.Mnrunner var4;
         try {
            var4 = (testing.Mnrunner)var0;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "test-runner-aux-value!", 0, var0);
         }

         testRunnerAuxValue$Ex(var4, var10);
         ports.display("  (Writing full log to \"");
         ports.display(var2);
         ports.display("\")");
         ports.newline();
      }

      try {
         var9 = (testing.Mnrunner)var0;
      } catch (ClassCastException var5) {
         throw new WrongType(var5, "test-runner-aux-value", 0, var0);
      }

      var0 = testRunnerAuxValue(var9);
      if(ports.isOutputPort(var0)) {
         ports.display("Group begin: ", var0);
         ports.display(var1, var0);
         ports.newline(var0);
      }

      return Boolean.FALSE;
   }

   public static Boolean testOnGroupEndSimple(Object var0) {
      testing.Mnrunner var1;
      try {
         var1 = (testing.Mnrunner)var0;
      } catch (ClassCastException var5) {
         throw new WrongType(var5, "test-runner-aux-value", 0, var0);
      }

      Object var6 = testRunnerAuxValue(var1);
      if(ports.isOutputPort(var6)) {
         ports.display("Group end: ", var6);
         GenericProc var2 = lists.car;

         testing.Mnrunner var3;
         try {
            var3 = (testing.Mnrunner)var0;
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "test-runner-group-stack", 0, var0);
         }

         ports.display(var2.apply1(testRunnerGroupStack(var3)), var6);
         ports.newline(var6);
      }

      return Boolean.FALSE;
   }

   static Object testOnTestBeginSimple(Object var0) {
      testing.Mnrunner var1;
      try {
         var1 = (testing.Mnrunner)var0;
      } catch (ClassCastException var6) {
         throw new WrongType(var6, "test-runner-aux-value", 0, var0);
      }

      Object var7 = testRunnerAuxValue(var1);
      if(ports.isOutputPort(var7)) {
         testing.Mnrunner var2;
         try {
            var2 = (testing.Mnrunner)var0;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "test-result-alist", 0, var0);
         }

         Object var4 = testResultAlist(var2);
         var0 = lists.assq(Lit4, var4);
         Object var8 = lists.assq(Lit5, var4);
         Object var3 = lists.assq(Lit6, var4);
         var4 = lists.assq(Lit7, var4);
         ports.display("Test begin:", var7);
         ports.newline(var7);
         if(var4 != Boolean.FALSE) {
            $PcTestWriteResult1(var4, var7);
         }

         if(var0 != Boolean.FALSE) {
            $PcTestWriteResult1(var0, var7);
         }

         if(var8 != Boolean.FALSE) {
            $PcTestWriteResult1(var8, var7);
         }

         return var0 != Boolean.FALSE?$PcTestWriteResult1(var3, var7):Values.empty;
      } else {
         return Values.empty;
      }
   }

   public static Object testOnTestEndSimple(Object var0) {
      testing.Mnrunner var1;
      try {
         var1 = (testing.Mnrunner)var0;
      } catch (ClassCastException var9) {
         throw new WrongType(var9, "test-runner-aux-value", 0, var0);
      }

      Object var2 = testRunnerAuxValue(var1);
      SimpleSymbol var10 = Lit1;

      testing.Mnrunner var3;
      try {
         var3 = (testing.Mnrunner)var0;
      } catch (ClassCastException var8) {
         throw new WrongType(var8, "test-result-alist", 0, var0);
      }

      Object var11 = lists.assq(var10, testResultAlist(var3));
      if(var11 != Boolean.FALSE) {
         var11 = lists.cdr.apply1(var11);
      } else {
         var11 = Boolean.FALSE;
      }

      if(lists.memq(var11, Lit8) != Boolean.FALSE) {
         try {
            var3 = (testing.Mnrunner)var0;
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "test-result-alist", 0, var0);
         }

         Object var13 = testResultAlist(var3);
         Object var4 = lists.assq(Lit4, var13);
         Object var5 = lists.assq(Lit5, var13);
         var13 = lists.assq(Lit7, var13);
         if(var4 != Boolean.FALSE || var5 != Boolean.FALSE) {
            if(var4 != Boolean.FALSE) {
               ports.display(lists.cdr.apply1(var4));
            }

            ports.display(":");
            if(var5 != Boolean.FALSE) {
               ports.display(lists.cdr.apply1(var5));
            }

            ports.display(": ");
         }

         String var12;
         if(var11 == Lit9) {
            var12 = "XPASS";
         } else {
            var12 = "FAIL";
         }

         ports.display(var12);
         if(var13 != Boolean.FALSE) {
            ports.display(" ");
            ports.display(lists.cdr.apply1(var13));
         }

         ports.newline();
      }

      if(ports.isOutputPort(var2)) {
         ports.display("Test end:", var2);
         ports.newline(var2);

         try {
            var1 = (testing.Mnrunner)var0;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "test-result-alist", 0, var0);
         }

         for(var0 = testResultAlist(var1); lists.isPair(var0); var0 = lists.cdr.apply1(var0)) {
            var11 = lists.car.apply1(var0);
            if(lists.memq(lists.car.apply1(var11), Lit10) == Boolean.FALSE) {
               $PcTestWriteResult1(var11, var2);
            }
         }

         return Values.empty;
      } else {
         return Values.empty;
      }
   }

   public static Object testReadEvalString(Object var0) {
      CharSequence var1;
      try {
         var1 = (CharSequence)var0;
      } catch (ClassCastException var2) {
         throw new WrongType(var2, "open-input-string", 1, var0);
      }

      InPort var3 = ports.openInputString(var1);
      Object var4 = ports.read(var3);
      return ports.isEofObject(readchar.readChar.apply1(var3))?Eval.eval.apply1(var4):misc.error$V("(not at eof)", new Object[0]);
   }

   public static Object testResultAlist(testing.Mnrunner var0) {
      return var0.result$Mnalist;
   }

   public static void testResultAlist$Ex(testing.Mnrunner var0, Object var1) {
      var0.result$Mnalist = var1;
   }

   public static void testResultClear(Object var0) {
      testing.Mnrunner var1;
      try {
         var1 = (testing.Mnrunner)var0;
      } catch (ClassCastException var2) {
         throw new WrongType(var2, "test-result-alist!", 0, var0);
      }

      testResultAlist$Ex(var1, LList.Empty);
   }

   public static Object testResultKind$V(Object[] var0) {
      LList var4 = LList.makeList(var0, 0);
      Object var5;
      if(lists.isPair(var4)) {
         var5 = lists.car.apply1(var4);
      } else {
         var5 = ((Procedure)test$Mnrunner$Mncurrent).apply0();
      }

      SimpleSymbol var1 = Lit1;

      testing.Mnrunner var2;
      try {
         var2 = (testing.Mnrunner)var5;
      } catch (ClassCastException var3) {
         throw new WrongType(var3, "test-result-alist", 0, var5);
      }

      var5 = lists.assq(var1, testResultAlist(var2));
      return var5 != Boolean.FALSE?lists.cdr.apply1(var5):Boolean.FALSE;
   }

   public static void testResultRemove(Object var0, Object var1) {
      testing.frame var2 = new testing.frame();

      testing.Mnrunner var3;
      try {
         var3 = (testing.Mnrunner)var0;
      } catch (ClassCastException var5) {
         throw new WrongType(var5, "test-result-alist", 0, var0);
      }

      Object var7 = testResultAlist(var3);
      var2.p = lists.assq(var1, var7);
      if(var2.p != Boolean.FALSE) {
         testing.Mnrunner var6;
         try {
            var6 = (testing.Mnrunner)var0;
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "test-result-alist!", 0, var0);
         }

         testResultAlist$Ex(var6, var2.lambda4loop(var7));
      }

   }

   public static Object testResultSet$Ex(Object var0, Object var1, Object var2) {
      testing.Mnrunner var3;
      try {
         var3 = (testing.Mnrunner)var0;
      } catch (ClassCastException var7) {
         throw new WrongType(var7, "test-result-alist", 0, var0);
      }

      Object var4 = testResultAlist(var3);
      Object var9 = lists.assq(var1, var4);
      if(var9 != Boolean.FALSE) {
         Pair var8;
         try {
            var8 = (Pair)var9;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "set-cdr!", 1, var9);
         }

         lists.setCdr$Ex(var8, var2);
         return Values.empty;
      } else {
         try {
            var3 = (testing.Mnrunner)var0;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "test-result-alist!", 0, var0);
         }

         testResultAlist$Ex(var3, lists.cons(lists.cons(var1, var2), var4));
         return Values.empty;
      }
   }

   public static Object testRunnerAuxValue(testing.Mnrunner var0) {
      return var0.aux$Mnvalue;
   }

   public static void testRunnerAuxValue$Ex(testing.Mnrunner var0, Object var1) {
      var0.aux$Mnvalue = var1;
   }

   public static Object testRunnerCreate() {
      return Scheme.applyToArgs.apply1(((Procedure)test$Mnrunner$Mnfactory).apply0());
   }

   public static Object testRunnerFailCount(testing.Mnrunner var0) {
      return var0.fail$Mncount;
   }

   public static void testRunnerFailCount$Ex(testing.Mnrunner var0, Object var1) {
      var0.fail$Mncount = var1;
   }

   public static Object testRunnerGet() {
      Object var0 = ((Procedure)test$Mnrunner$Mncurrent).apply0();
      if(var0 == Boolean.FALSE) {
         misc.error$V("test-runner not initialized - test-begin missing?", new Object[0]);
      }

      return var0;
   }

   public static LList testRunnerGroupPath(Object var0) {
      testing.Mnrunner var1;
      try {
         var1 = (testing.Mnrunner)var0;
      } catch (ClassCastException var3) {
         throw new WrongType(var3, "test-runner-group-stack", 0, var0);
      }

      var0 = testRunnerGroupStack(var1);

      LList var4;
      try {
         var4 = (LList)var0;
      } catch (ClassCastException var2) {
         throw new WrongType(var2, "reverse", 1, var0);
      }

      return lists.reverse(var4);
   }

   public static Object testRunnerGroupStack(testing.Mnrunner var0) {
      return var0.group$Mnstack;
   }

   public static void testRunnerGroupStack$Ex(testing.Mnrunner var0, Object var1) {
      var0.group$Mnstack = var1;
   }

   public static testing.Mnrunner testRunnerNull() {
      testing.Mnrunner var0 = $PcTestRunnerAlloc();
      testRunnerReset(var0);
      testRunnerOnGroupBegin$Ex(var0, lambda$Fn1);
      testRunnerOnGroupEnd$Ex(var0, $Pctest$Mnnull$Mncallback);
      testRunnerOnFinal$Ex(var0, $Pctest$Mnnull$Mncallback);
      testRunnerOnTestBegin$Ex(var0, $Pctest$Mnnull$Mncallback);
      testRunnerOnTestEnd$Ex(var0, $Pctest$Mnnull$Mncallback);
      testRunnerOnBadCount$Ex(var0, lambda$Fn2);
      testRunnerOnBadEndName$Ex(var0, lambda$Fn3);
      return var0;
   }

   public static Object testRunnerOnBadCount(testing.Mnrunner var0) {
      return var0.on$Mnbad$Mncount;
   }

   public static void testRunnerOnBadCount$Ex(testing.Mnrunner var0, Object var1) {
      var0.on$Mnbad$Mncount = var1;
   }

   public static Object testRunnerOnBadEndName(testing.Mnrunner var0) {
      return var0.on$Mnbad$Mnend$Mnname;
   }

   public static void testRunnerOnBadEndName$Ex(testing.Mnrunner var0, Object var1) {
      var0.on$Mnbad$Mnend$Mnname = var1;
   }

   public static Object testRunnerOnFinal(testing.Mnrunner var0) {
      return var0.on$Mnfinal;
   }

   public static void testRunnerOnFinal$Ex(testing.Mnrunner var0, Object var1) {
      var0.on$Mnfinal = var1;
   }

   public static Object testRunnerOnGroupBegin(testing.Mnrunner var0) {
      return var0.on$Mngroup$Mnbegin;
   }

   public static void testRunnerOnGroupBegin$Ex(testing.Mnrunner var0, Object var1) {
      var0.on$Mngroup$Mnbegin = var1;
   }

   public static Object testRunnerOnGroupEnd(testing.Mnrunner var0) {
      return var0.on$Mngroup$Mnend;
   }

   public static void testRunnerOnGroupEnd$Ex(testing.Mnrunner var0, Object var1) {
      var0.on$Mngroup$Mnend = var1;
   }

   public static Object testRunnerOnTestBegin(testing.Mnrunner var0) {
      return var0.on$Mntest$Mnbegin;
   }

   public static void testRunnerOnTestBegin$Ex(testing.Mnrunner var0, Object var1) {
      var0.on$Mntest$Mnbegin = var1;
   }

   public static Object testRunnerOnTestEnd(testing.Mnrunner var0) {
      return var0.on$Mntest$Mnend;
   }

   public static void testRunnerOnTestEnd$Ex(testing.Mnrunner var0, Object var1) {
      var0.on$Mntest$Mnend = var1;
   }

   public static Object testRunnerPassCount(testing.Mnrunner var0) {
      return var0.pass$Mncount;
   }

   public static void testRunnerPassCount$Ex(testing.Mnrunner var0, Object var1) {
      var0.pass$Mncount = var1;
   }

   public static void testRunnerReset(Object var0) {
      testing.Mnrunner var1;
      try {
         var1 = (testing.Mnrunner)var0;
      } catch (ClassCastException var15) {
         throw new WrongType(var15, "test-result-alist!", 0, var0);
      }

      testResultAlist$Ex(var1, LList.Empty);

      try {
         var1 = (testing.Mnrunner)var0;
      } catch (ClassCastException var14) {
         throw new WrongType(var14, "test-runner-pass-count!", 0, var0);
      }

      testRunnerPassCount$Ex(var1, Lit0);

      try {
         var1 = (testing.Mnrunner)var0;
      } catch (ClassCastException var13) {
         throw new WrongType(var13, "test-runner-fail-count!", 0, var0);
      }

      testRunnerFailCount$Ex(var1, Lit0);

      try {
         var1 = (testing.Mnrunner)var0;
      } catch (ClassCastException var12) {
         throw new WrongType(var12, "test-runner-xpass-count!", 0, var0);
      }

      testRunnerXpassCount$Ex(var1, Lit0);

      try {
         var1 = (testing.Mnrunner)var0;
      } catch (ClassCastException var11) {
         throw new WrongType(var11, "test-runner-xfail-count!", 0, var0);
      }

      testRunnerXfailCount$Ex(var1, Lit0);

      try {
         var1 = (testing.Mnrunner)var0;
      } catch (ClassCastException var10) {
         throw new WrongType(var10, "test-runner-skip-count!", 0, var0);
      }

      testRunnerSkipCount$Ex(var1, Lit0);

      try {
         var1 = (testing.Mnrunner)var0;
      } catch (ClassCastException var9) {
         throw new WrongType(var9, "%test-runner-total-count!", 0, var0);
      }

      $PcTestRunnerTotalCount$Ex(var1, Lit0);

      try {
         var1 = (testing.Mnrunner)var0;
      } catch (ClassCastException var8) {
         throw new WrongType(var8, "%test-runner-count-list!", 0, var0);
      }

      $PcTestRunnerCountList$Ex(var1, LList.Empty);

      try {
         var1 = (testing.Mnrunner)var0;
      } catch (ClassCastException var7) {
         throw new WrongType(var7, "%test-runner-run-list!", 0, var0);
      }

      $PcTestRunnerRunList$Ex(var1, Boolean.TRUE);

      try {
         var1 = (testing.Mnrunner)var0;
      } catch (ClassCastException var6) {
         throw new WrongType(var6, "%test-runner-skip-list!", 0, var0);
      }

      $PcTestRunnerSkipList$Ex(var1, LList.Empty);

      try {
         var1 = (testing.Mnrunner)var0;
      } catch (ClassCastException var5) {
         throw new WrongType(var5, "%test-runner-fail-list!", 0, var0);
      }

      $PcTestRunnerFailList$Ex(var1, LList.Empty);

      try {
         var1 = (testing.Mnrunner)var0;
      } catch (ClassCastException var4) {
         throw new WrongType(var4, "%test-runner-skip-save!", 0, var0);
      }

      $PcTestRunnerSkipSave$Ex(var1, LList.Empty);

      try {
         var1 = (testing.Mnrunner)var0;
      } catch (ClassCastException var3) {
         throw new WrongType(var3, "%test-runner-fail-save!", 0, var0);
      }

      $PcTestRunnerFailSave$Ex(var1, LList.Empty);

      try {
         var1 = (testing.Mnrunner)var0;
      } catch (ClassCastException var2) {
         throw new WrongType(var2, "test-runner-group-stack!", 0, var0);
      }

      testRunnerGroupStack$Ex(var1, LList.Empty);
   }

   public static testing.Mnrunner testRunnerSimple() {
      testing.Mnrunner var0 = $PcTestRunnerAlloc();
      testRunnerReset(var0);
      testRunnerOnGroupBegin$Ex(var0, test$Mnon$Mngroup$Mnbegin$Mnsimple);
      testRunnerOnGroupEnd$Ex(var0, test$Mnon$Mngroup$Mnend$Mnsimple);
      testRunnerOnFinal$Ex(var0, test$Mnon$Mnfinal$Mnsimple);
      testRunnerOnTestBegin$Ex(var0, test$Mnon$Mntest$Mnbegin$Mnsimple);
      testRunnerOnTestEnd$Ex(var0, test$Mnon$Mntest$Mnend$Mnsimple);
      testRunnerOnBadCount$Ex(var0, test$Mnon$Mnbad$Mncount$Mnsimple);
      testRunnerOnBadEndName$Ex(var0, test$Mnon$Mnbad$Mnend$Mnname$Mnsimple);
      return var0;
   }

   public static Object testRunnerSkipCount(testing.Mnrunner var0) {
      return var0.skip$Mncount;
   }

   public static void testRunnerSkipCount$Ex(testing.Mnrunner var0, Object var1) {
      var0.skip$Mncount = var1;
   }

   public static Object testRunnerTestName(Object var0) {
      SimpleSymbol var1 = Lit7;

      testing.Mnrunner var2;
      try {
         var2 = (testing.Mnrunner)var0;
      } catch (ClassCastException var3) {
         throw new WrongType(var3, "test-result-alist", 0, var0);
      }

      var0 = lists.assq(var1, testResultAlist(var2));
      return var0 != Boolean.FALSE?lists.cdr.apply1(var0):"";
   }

   public static Object testRunnerXfailCount(testing.Mnrunner var0) {
      return var0.xfail$Mncount;
   }

   public static void testRunnerXfailCount$Ex(testing.Mnrunner var0, Object var1) {
      var0.xfail$Mncount = var1;
   }

   public static Object testRunnerXpassCount(testing.Mnrunner var0) {
      return var0.xpass$Mncount;
   }

   public static void testRunnerXpassCount$Ex(testing.Mnrunner var0, Object var1) {
      var0.xpass$Mncount = var1;
   }

   public Object apply0(ModuleMethod var1) {
      switch(var1.selector) {
      case 53:
         return testRunnerNull();
      case 54:
         return testRunnerSimple();
      case 55:
         return testRunnerGet();
      case 56:
         return testRunnerCreate();
      case 72:
         return $PcTestReportResult();
      default:
         return super.apply0(var1);
      }
   }

   public Object apply1(ModuleMethod var1, Object var2) {
      testing.Mnrunner var20;
      switch(var1.selector) {
      case 12:
         if(isTestRunner(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 13:
         try {
            var20 = (testing.Mnrunner)var2;
         } catch (ClassCastException var19) {
            throw new WrongType(var19, "test-runner-pass-count", 1, var2);
         }

         return testRunnerPassCount(var20);
      case 14:
      case 16:
      case 18:
      case 20:
      case 22:
      case 24:
      case 26:
      case 28:
      case 30:
      case 32:
      case 34:
      case 36:
      case 38:
      case 40:
      case 42:
      case 44:
      case 46:
      case 50:
      case 51:
      case 52:
      case 53:
      case 54:
      case 55:
      case 56:
      case 58:
      case 59:
      case 61:
      case 62:
      case 64:
      case 67:
      case 69:
      case 70:
      case 71:
      case 72:
      case 74:
      case 84:
      case 85:
      case 86:
      case 87:
      default:
         return super.apply1(var1, var2);
      case 15:
         try {
            var20 = (testing.Mnrunner)var2;
         } catch (ClassCastException var18) {
            throw new WrongType(var18, "test-runner-fail-count", 1, var2);
         }

         return testRunnerFailCount(var20);
      case 17:
         try {
            var20 = (testing.Mnrunner)var2;
         } catch (ClassCastException var17) {
            throw new WrongType(var17, "test-runner-xpass-count", 1, var2);
         }

         return testRunnerXpassCount(var20);
      case 19:
         try {
            var20 = (testing.Mnrunner)var2;
         } catch (ClassCastException var16) {
            throw new WrongType(var16, "test-runner-xfail-count", 1, var2);
         }

         return testRunnerXfailCount(var20);
      case 21:
         try {
            var20 = (testing.Mnrunner)var2;
         } catch (ClassCastException var15) {
            throw new WrongType(var15, "test-runner-skip-count", 1, var2);
         }

         return testRunnerSkipCount(var20);
      case 23:
         try {
            var20 = (testing.Mnrunner)var2;
         } catch (ClassCastException var14) {
            throw new WrongType(var14, "%test-runner-skip-list", 1, var2);
         }

         return $PcTestRunnerSkipList(var20);
      case 25:
         try {
            var20 = (testing.Mnrunner)var2;
         } catch (ClassCastException var13) {
            throw new WrongType(var13, "%test-runner-fail-list", 1, var2);
         }

         return $PcTestRunnerFailList(var20);
      case 27:
         try {
            var20 = (testing.Mnrunner)var2;
         } catch (ClassCastException var12) {
            throw new WrongType(var12, "test-runner-group-stack", 1, var2);
         }

         return testRunnerGroupStack(var20);
      case 29:
         try {
            var20 = (testing.Mnrunner)var2;
         } catch (ClassCastException var11) {
            throw new WrongType(var11, "test-runner-on-test-begin", 1, var2);
         }

         return testRunnerOnTestBegin(var20);
      case 31:
         try {
            var20 = (testing.Mnrunner)var2;
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "test-runner-on-test-end", 1, var2);
         }

         return testRunnerOnTestEnd(var20);
      case 33:
         try {
            var20 = (testing.Mnrunner)var2;
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "test-runner-on-group-begin", 1, var2);
         }

         return testRunnerOnGroupBegin(var20);
      case 35:
         try {
            var20 = (testing.Mnrunner)var2;
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "test-runner-on-group-end", 1, var2);
         }

         return testRunnerOnGroupEnd(var20);
      case 37:
         try {
            var20 = (testing.Mnrunner)var2;
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "test-runner-on-final", 1, var2);
         }

         return testRunnerOnFinal(var20);
      case 39:
         try {
            var20 = (testing.Mnrunner)var2;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "test-runner-on-bad-count", 1, var2);
         }

         return testRunnerOnBadCount(var20);
      case 41:
         try {
            var20 = (testing.Mnrunner)var2;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "test-runner-on-bad-end-name", 1, var2);
         }

         return testRunnerOnBadEndName(var20);
      case 43:
         try {
            var20 = (testing.Mnrunner)var2;
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "test-result-alist", 1, var2);
         }

         return testResultAlist(var20);
      case 45:
         try {
            var20 = (testing.Mnrunner)var2;
         } catch (ClassCastException var3) {
            throw new WrongType(var3, "test-runner-aux-value", 1, var2);
         }

         return testRunnerAuxValue(var20);
      case 47:
         testRunnerReset(var2);
         return Values.empty;
      case 48:
         return testRunnerGroupPath(var2);
      case 49:
         return $PcTestNullCallback(var2);
      case 57:
         return $PcTestShouldExecute(var2);
      case 60:
         return testOnGroupEndSimple(var2);
      case 63:
         testOnFinalSimple(var2);
         return Values.empty;
      case 65:
         return testOnTestBeginSimple(var2);
      case 66:
         return testOnTestEndSimple(var2);
      case 68:
         testResultClear(var2);
         return Values.empty;
      case 73:
         if($PcTestOnTestBegin(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 75:
         return testRunnerTestName(var2);
      case 76:
         return $PcTestApproximimate$Eq(var2);
      case 77:
         return lambda16(var2);
      case 78:
         return lambda17(var2);
      case 79:
         return lambda18(var2);
      case 80:
         return lambda19(var2);
      case 81:
         return lambda20(var2);
      case 82:
         return lambda21(var2);
      case 83:
         return lambda22(var2);
      case 88:
         return $PcTestAsSpecifier(var2);
      case 89:
         return testMatchName(var2);
      case 90:
         return testReadEvalString(var2);
      }
   }

   public Object apply2(ModuleMethod var1, Object var2, Object var3) {
      testing.Mnrunner var21;
      switch(var1.selector) {
      case 14:
         try {
            var21 = (testing.Mnrunner)var2;
         } catch (ClassCastException var20) {
            throw new WrongType(var20, "test-runner-pass-count!", 1, var2);
         }

         testRunnerPassCount$Ex(var21, var3);
         return Values.empty;
      case 16:
         try {
            var21 = (testing.Mnrunner)var2;
         } catch (ClassCastException var19) {
            throw new WrongType(var19, "test-runner-fail-count!", 1, var2);
         }

         testRunnerFailCount$Ex(var21, var3);
         return Values.empty;
      case 18:
         try {
            var21 = (testing.Mnrunner)var2;
         } catch (ClassCastException var18) {
            throw new WrongType(var18, "test-runner-xpass-count!", 1, var2);
         }

         testRunnerXpassCount$Ex(var21, var3);
         return Values.empty;
      case 20:
         try {
            var21 = (testing.Mnrunner)var2;
         } catch (ClassCastException var17) {
            throw new WrongType(var17, "test-runner-xfail-count!", 1, var2);
         }

         testRunnerXfailCount$Ex(var21, var3);
         return Values.empty;
      case 22:
         try {
            var21 = (testing.Mnrunner)var2;
         } catch (ClassCastException var16) {
            throw new WrongType(var16, "test-runner-skip-count!", 1, var2);
         }

         testRunnerSkipCount$Ex(var21, var3);
         return Values.empty;
      case 24:
         try {
            var21 = (testing.Mnrunner)var2;
         } catch (ClassCastException var15) {
            throw new WrongType(var15, "%test-runner-skip-list!", 1, var2);
         }

         $PcTestRunnerSkipList$Ex(var21, var3);
         return Values.empty;
      case 26:
         try {
            var21 = (testing.Mnrunner)var2;
         } catch (ClassCastException var14) {
            throw new WrongType(var14, "%test-runner-fail-list!", 1, var2);
         }

         $PcTestRunnerFailList$Ex(var21, var3);
         return Values.empty;
      case 28:
         try {
            var21 = (testing.Mnrunner)var2;
         } catch (ClassCastException var13) {
            throw new WrongType(var13, "test-runner-group-stack!", 1, var2);
         }

         testRunnerGroupStack$Ex(var21, var3);
         return Values.empty;
      case 30:
         try {
            var21 = (testing.Mnrunner)var2;
         } catch (ClassCastException var12) {
            throw new WrongType(var12, "test-runner-on-test-begin!", 1, var2);
         }

         testRunnerOnTestBegin$Ex(var21, var3);
         return Values.empty;
      case 32:
         try {
            var21 = (testing.Mnrunner)var2;
         } catch (ClassCastException var11) {
            throw new WrongType(var11, "test-runner-on-test-end!", 1, var2);
         }

         testRunnerOnTestEnd$Ex(var21, var3);
         return Values.empty;
      case 34:
         try {
            var21 = (testing.Mnrunner)var2;
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "test-runner-on-group-begin!", 1, var2);
         }

         testRunnerOnGroupBegin$Ex(var21, var3);
         return Values.empty;
      case 36:
         try {
            var21 = (testing.Mnrunner)var2;
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "test-runner-on-group-end!", 1, var2);
         }

         testRunnerOnGroupEnd$Ex(var21, var3);
         return Values.empty;
      case 38:
         try {
            var21 = (testing.Mnrunner)var2;
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "test-runner-on-final!", 1, var2);
         }

         testRunnerOnFinal$Ex(var21, var3);
         return Values.empty;
      case 40:
         try {
            var21 = (testing.Mnrunner)var2;
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "test-runner-on-bad-count!", 1, var2);
         }

         testRunnerOnBadCount$Ex(var21, var3);
         return Values.empty;
      case 42:
         try {
            var21 = (testing.Mnrunner)var2;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "test-runner-on-bad-end-name!", 1, var2);
         }

         testRunnerOnBadEndName$Ex(var21, var3);
         return Values.empty;
      case 44:
         try {
            var21 = (testing.Mnrunner)var2;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "test-result-alist!", 1, var2);
         }

         testResultAlist$Ex(var21, var3);
         return Values.empty;
      case 46:
         try {
            var21 = (testing.Mnrunner)var2;
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "test-runner-aux-value!", 1, var2);
         }

         testRunnerAuxValue$Ex(var21, var3);
         return Values.empty;
      case 58:
         $PcTestBegin(var2, var3);
         return Values.empty;
      case 64:
         return $PcTestEnd(var2, var3);
      case 69:
         testResultRemove(var2, var3);
         return Values.empty;
      case 74:
         return $PcTestOnTestEnd(var2, var3);
      case 85:
         return $PcTestMatchNth(var2, var3);
      default:
         return super.apply2(var1, var2, var3);
      }
   }

   public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
      switch(var1.selector) {
      case 50:
         return lambda1(var2, var3, var4);
      case 51:
         return lambda2(var2, var3, var4);
      case 52:
         return lambda3(var2, var3, var4);
      case 59:
         return testOnGroupBeginSimple(var2, var3, var4);
      case 61:
         testOnBadCountSimple(var2, var3, var4);
         return Values.empty;
      case 62:
         return testOnBadEndNameSimple(var2, var3, var4);
      case 67:
         return testResultSet$Ex(var2, var3, var4);
      default:
         return super.apply3(var1, var2, var3, var4);
      }
   }

   public Object applyN(ModuleMethod var1, Object[] var2) {
      switch(var1.selector) {
      case 70:
         return testResultKind$V(var2);
      case 71:
         return isTestPassed$V(var2);
      case 84:
         Object var5 = var2[0];
         int var4 = var2.length - 1;
         Object[] var3 = new Object[var4];

         while(true) {
            --var4;
            if(var4 < 0) {
               return testApply$V(var5, var3);
            }

            var3[var4] = var2[var4 + 1];
         }
      case 86:
         return $PcTestMatchAll$V(var2);
      case 87:
         return $PcTestMatchAny$V(var2);
      default:
         return super.applyN(var1, var2);
      }
   }

   public int match0(ModuleMethod var1, CallContext var2) {
      switch(var1.selector) {
      case 53:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 54:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 55:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 56:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 72:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      default:
         return super.match0(var1, var2);
      }
   }

   public int match1(ModuleMethod var1, Object var2, CallContext var3) {
      switch(var1.selector) {
      case 12:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 13:
         if(!(var2 instanceof testing.Mnrunner)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 14:
      case 16:
      case 18:
      case 20:
      case 22:
      case 24:
      case 26:
      case 28:
      case 30:
      case 32:
      case 34:
      case 36:
      case 38:
      case 40:
      case 42:
      case 44:
      case 46:
      case 50:
      case 51:
      case 52:
      case 53:
      case 54:
      case 55:
      case 56:
      case 58:
      case 59:
      case 61:
      case 62:
      case 64:
      case 67:
      case 69:
      case 70:
      case 71:
      case 72:
      case 74:
      case 84:
      case 85:
      case 86:
      case 87:
      default:
         return super.match1(var1, var2, var3);
      case 15:
         if(!(var2 instanceof testing.Mnrunner)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 17:
         if(!(var2 instanceof testing.Mnrunner)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 19:
         if(!(var2 instanceof testing.Mnrunner)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 21:
         if(!(var2 instanceof testing.Mnrunner)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 23:
         if(!(var2 instanceof testing.Mnrunner)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 25:
         if(!(var2 instanceof testing.Mnrunner)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 27:
         if(!(var2 instanceof testing.Mnrunner)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 29:
         if(!(var2 instanceof testing.Mnrunner)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 31:
         if(!(var2 instanceof testing.Mnrunner)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 33:
         if(!(var2 instanceof testing.Mnrunner)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 35:
         if(!(var2 instanceof testing.Mnrunner)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 37:
         if(!(var2 instanceof testing.Mnrunner)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 39:
         if(!(var2 instanceof testing.Mnrunner)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 41:
         if(!(var2 instanceof testing.Mnrunner)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 43:
         if(!(var2 instanceof testing.Mnrunner)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 45:
         if(!(var2 instanceof testing.Mnrunner)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 47:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 48:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 49:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 57:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 60:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 63:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 65:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 66:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 68:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 73:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 75:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 76:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 77:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 78:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 79:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 80:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 81:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 82:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 83:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 88:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 89:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 90:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      }
   }

   public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
      switch(var1.selector) {
      case 14:
         if(!(var2 instanceof testing.Mnrunner)) {
            return -786431;
         }

         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 16:
         if(!(var2 instanceof testing.Mnrunner)) {
            return -786431;
         }

         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 18:
         if(!(var2 instanceof testing.Mnrunner)) {
            return -786431;
         }

         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 20:
         if(!(var2 instanceof testing.Mnrunner)) {
            return -786431;
         }

         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 22:
         if(!(var2 instanceof testing.Mnrunner)) {
            return -786431;
         }

         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 24:
         if(!(var2 instanceof testing.Mnrunner)) {
            return -786431;
         }

         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 26:
         if(!(var2 instanceof testing.Mnrunner)) {
            return -786431;
         }

         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 28:
         if(!(var2 instanceof testing.Mnrunner)) {
            return -786431;
         }

         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 30:
         if(!(var2 instanceof testing.Mnrunner)) {
            return -786431;
         }

         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 32:
         if(!(var2 instanceof testing.Mnrunner)) {
            return -786431;
         }

         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 34:
         if(!(var2 instanceof testing.Mnrunner)) {
            return -786431;
         }

         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 36:
         if(!(var2 instanceof testing.Mnrunner)) {
            return -786431;
         }

         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 38:
         if(!(var2 instanceof testing.Mnrunner)) {
            return -786431;
         }

         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 40:
         if(!(var2 instanceof testing.Mnrunner)) {
            return -786431;
         }

         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 42:
         if(!(var2 instanceof testing.Mnrunner)) {
            return -786431;
         }

         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 44:
         if(!(var2 instanceof testing.Mnrunner)) {
            return -786431;
         }

         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 46:
         if(!(var2 instanceof testing.Mnrunner)) {
            return -786431;
         }

         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 58:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 64:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 69:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 74:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 85:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      default:
         return super.match2(var1, var2, var3, var4);
      }
   }

   public int match3(ModuleMethod var1, Object var2, Object var3, Object var4, CallContext var5) {
      switch(var1.selector) {
      case 50:
         var5.value1 = var2;
         var5.value2 = var3;
         var5.value3 = var4;
         var5.proc = var1;
         var5.pc = 3;
         return 0;
      case 51:
         var5.value1 = var2;
         var5.value2 = var3;
         var5.value3 = var4;
         var5.proc = var1;
         var5.pc = 3;
         return 0;
      case 52:
         var5.value1 = var2;
         var5.value2 = var3;
         var5.value3 = var4;
         var5.proc = var1;
         var5.pc = 3;
         return 0;
      case 59:
         var5.value1 = var2;
         var5.value2 = var3;
         var5.value3 = var4;
         var5.proc = var1;
         var5.pc = 3;
         return 0;
      case 61:
         var5.value1 = var2;
         var5.value2 = var3;
         var5.value3 = var4;
         var5.proc = var1;
         var5.pc = 3;
         return 0;
      case 62:
         var5.value1 = var2;
         var5.value2 = var3;
         var5.value3 = var4;
         var5.proc = var1;
         var5.pc = 3;
         return 0;
      case 67:
         var5.value1 = var2;
         var5.value2 = var3;
         var5.value3 = var4;
         var5.proc = var1;
         var5.pc = 3;
         return 0;
      default:
         return super.match3(var1, var2, var3, var4, var5);
      }
   }

   public int matchN(ModuleMethod var1, Object[] var2, CallContext var3) {
      switch(var1.selector) {
      case 70:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 71:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 84:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 86:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 87:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      default:
         return super.matchN(var1, var2, var3);
      }
   }

   public final void run(CallContext var1) {
      Consumer var2 = var1.consumer;
      test$Mnlog$Mnto$Mnfile = Boolean.TRUE;
      test$Mnrunner$Mncurrent = parameters.makeParameter(Boolean.FALSE);
      test$Mnrunner$Mnfactory = parameters.makeParameter(test$Mnrunner$Mnsimple);
   }

   public class Mnrunner {

      public Object aux$Mnvalue;
      public Object count$Mnlist;
      public Object fail$Mncount;
      public Object fail$Mnlist;
      public Object fail$Mnsave;
      public Object group$Mnstack;
      public Object on$Mnbad$Mncount;
      public Object on$Mnbad$Mnend$Mnname;
      public Object on$Mnfinal;
      public Object on$Mngroup$Mnbegin;
      public Object on$Mngroup$Mnend;
      public Object on$Mntest$Mnbegin;
      public Object on$Mntest$Mnend;
      public Object pass$Mncount;
      public Object result$Mnalist;
      public Object run$Mnlist;
      public Object skip$Mncount;
      public Object skip$Mnlist;
      public Object skip$Mnsave;
      public Object total$Mncount;
      public Object xfail$Mncount;
      public Object xpass$Mncount;


   }

   public class frame extends ModuleBody {

      Object p;


      public Object lambda4loop(Object var1) {
         return var1 == this.p?lists.cdr.apply1(var1):lists.cons(lists.car.apply1(var1), this.lambda4loop(lists.cdr.apply1(var1)));
      }
   }

   public class frame0 extends ModuleBody {

      Object error;
      final ModuleMethod lambda$Fn4;


      public frame0() {
         ModuleMethod var1 = new ModuleMethod(this, 1, (Object)null, 8194);
         var1.setProperty("source-location", "testing.scm:640");
         this.lambda$Fn4 = var1;
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 1?(this.lambda5(var2, var3)?Boolean.TRUE:Boolean.FALSE):super.apply2(var1, var2, var3);
      }

      boolean lambda5(Object var1, Object var2) {
         Object var3 = Scheme.numGEq.apply2(var1, AddOp.$Mn.apply2(var2, this.error));

         boolean var4;
         try {
            var4 = ((Boolean)var3).booleanValue();
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "x", -2, var3);
         }

         boolean var5 = var4;
         if(var4) {
            var5 = ((Boolean)Scheme.numLEq.apply2(var1, AddOp.$Pl.apply2(var2, this.error))).booleanValue();
         }

         return var5;
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
   }

   public class frame1 extends ModuleBody {

      Object first;
      final ModuleMethod lambda$Fn10;
      final ModuleMethod lambda$Fn5 = new ModuleMethod(this, 2, (Object)null, 0);
      final ModuleMethod lambda$Fn6 = new ModuleMethod(this, 3, (Object)null, 0);
      final ModuleMethod lambda$Fn7;
      final ModuleMethod lambda$Fn8;
      final ModuleMethod lambda$Fn9;
      Object r;
      LList rest;
      Object saved$Mnrunner;
      Object saved$Mnrunner$1;


      public frame1() {
         ModuleMethod var1 = new ModuleMethod(this, 4, (Object)null, 0);
         var1.setProperty("source-location", "testing.scm:897");
         this.lambda$Fn7 = var1;
         this.lambda$Fn8 = new ModuleMethod(this, 5, (Object)null, 0);
         this.lambda$Fn9 = new ModuleMethod(this, 6, (Object)null, 0);
         var1 = new ModuleMethod(this, 7, (Object)null, 0);
         var1.setProperty("source-location", "testing.scm:897");
         this.lambda$Fn10 = var1;
      }

      public Object apply0(ModuleMethod var1) {
         switch(var1.selector) {
         case 2:
            return this.lambda6();
         case 3:
            return this.lambda7();
         case 4:
            return this.lambda8();
         case 5:
            return this.lambda9();
         case 6:
            return this.lambda10();
         case 7:
            return this.lambda11();
         default:
            return super.apply0(var1);
         }
      }

      Object lambda10() {
         return Scheme.apply.apply3(testing.test$Mnapply, this.first, this.rest);
      }

      Object lambda11() {
         return ((Procedure)testing.test$Mnrunner$Mncurrent).apply1(this.saved$Mnrunner);
      }

      Object lambda6() {
         return ((Procedure)testing.test$Mnrunner$Mncurrent).apply1(this.first);
      }

      Object lambda7() {
         return Scheme.apply.apply2(testing.test$Mnapply, this.rest);
      }

      Object lambda8() {
         return ((Procedure)testing.test$Mnrunner$Mncurrent).apply1(this.saved$Mnrunner$1);
      }

      Object lambda9() {
         return ((Procedure)testing.test$Mnrunner$Mncurrent).apply1(this.r);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         switch(var1.selector) {
         case 2:
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         case 3:
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         case 4:
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         case 5:
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         case 6:
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         case 7:
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         default:
            return super.match0(var1, var2);
         }
      }
   }

   public class frame2 extends ModuleBody {

      Object count;
      Object i;
      final ModuleMethod lambda$Fn11;
      Object n;


      public frame2() {
         ModuleMethod var1 = new ModuleMethod(this, 8, (Object)null, 4097);
         var1.setProperty("source-location", "testing.scm:903");
         this.lambda$Fn11 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 8?(this.lambda12(var2)?Boolean.TRUE:Boolean.FALSE):super.apply1(var1, var2);
      }

      boolean lambda12(Object var1) {
         this.i = AddOp.$Pl.apply2(this.i, testing.Lit13);
         var1 = Scheme.numGEq.apply2(this.i, this.n);

         boolean var3;
         try {
            var3 = ((Boolean)var1).booleanValue();
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "x", -2, var1);
         }

         boolean var4 = var3;
         if(var3) {
            var4 = ((Boolean)Scheme.numLss.apply2(this.i, AddOp.$Pl.apply2(this.n, this.count))).booleanValue();
         }

         return var4;
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         if(var1.selector == 8) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return super.match1(var1, var2, var3);
         }
      }
   }

   public class frame3 extends ModuleBody {

      final ModuleMethod lambda$Fn12;
      LList pred$Mnlist;


      public frame3() {
         ModuleMethod var1 = new ModuleMethod(this, 9, (Object)null, 4097);
         var1.setProperty("source-location", "testing.scm:915");
         this.lambda$Fn12 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 9?this.lambda13(var2):super.apply1(var1, var2);
      }

      Object lambda13(Object var1) {
         Boolean var3 = Boolean.TRUE;

         for(Object var2 = this.pred$Mnlist; !lists.isNull(var2); var2 = lists.cdr.apply1(var2)) {
            if(Scheme.applyToArgs.apply2(lists.car.apply1(var2), var1) == Boolean.FALSE) {
               var3 = Boolean.FALSE;
            }
         }

         return var3;
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         if(var1.selector == 9) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return super.match1(var1, var2, var3);
         }
      }
   }

   public class frame4 extends ModuleBody {

      final ModuleMethod lambda$Fn13;
      LList pred$Mnlist;


      public frame4() {
         ModuleMethod var1 = new ModuleMethod(this, 10, (Object)null, 4097);
         var1.setProperty("source-location", "testing.scm:931");
         this.lambda$Fn13 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 10?this.lambda14(var2):super.apply1(var1, var2);
      }

      Object lambda14(Object var1) {
         Boolean var3 = Boolean.FALSE;

         for(Object var2 = this.pred$Mnlist; !lists.isNull(var2); var2 = lists.cdr.apply1(var2)) {
            if(Scheme.applyToArgs.apply2(lists.car.apply1(var2), var1) != Boolean.FALSE) {
               var3 = Boolean.TRUE;
            }
         }

         return var3;
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         if(var1.selector == 10) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return super.match1(var1, var2, var3);
         }
      }
   }

   public class frame5 extends ModuleBody {

      final ModuleMethod lambda$Fn14;
      Object name;


      public frame5() {
         ModuleMethod var1 = new ModuleMethod(this, 11, (Object)null, 4097);
         var1.setProperty("source-location", "testing.scm:971");
         this.lambda$Fn14 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 11?(this.lambda15(var2)?Boolean.TRUE:Boolean.FALSE):super.apply1(var1, var2);
      }

      boolean lambda15(Object var1) {
         return IsEqual.apply(this.name, testing.testRunnerTestName(var1));
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         if(var1.selector == 11) {
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
