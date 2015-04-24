package gnu.kawa.slib;

import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.Apply;
import gnu.kawa.functions.IsEq;
import gnu.kawa.slib.condition;
import gnu.kawa.slib.srfi1;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.CallContext;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import kawa.lang.Macro;
import kawa.lang.SyntaxPattern;
import kawa.lang.SyntaxRule;
import kawa.lang.SyntaxRules;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.standard.Scheme;
import kawa.standard.append;

public class conditions extends ModuleBody {

   public static Object $Amcondition;
   public static Object $Amerror;
   public static Object $Ammessage;
   public static Object $Amserious;
   static final Class $Lscondition$Mntype$Gr;
   public static final Class $Prvt$$Lscondition$Gr;
   public static final ModuleMethod $Prvt$type$Mnfield$Mnalist$Mn$Grcondition;
   public static final conditions $instance;
   static final SimpleSymbol Lit0;
   static final SimpleSymbol Lit1;
   static final SimpleSymbol Lit10;
   static final SimpleSymbol Lit11;
   static final SimpleSymbol Lit12;
   static final SimpleSymbol Lit13;
   static final SyntaxRules Lit14;
   static final SimpleSymbol Lit15;
   static final SimpleSymbol Lit16;
   static final SimpleSymbol Lit17;
   static final SimpleSymbol Lit18;
   static final SyntaxRules Lit19;
   static final PairWithPosition Lit2;
   static final SimpleSymbol Lit20 = (SimpleSymbol)(new SimpleSymbol("type-field-alist->condition")).readResolve();
   static final SimpleSymbol Lit21 = (SimpleSymbol)(new SimpleSymbol("quote")).readResolve();
   static final SimpleSymbol Lit22 = (SimpleSymbol)(new SimpleSymbol("thing")).readResolve();
   static final SimpleSymbol Lit3;
   static final SimpleSymbol Lit4;
   static final SimpleSymbol Lit5;
   static final SimpleSymbol Lit6;
   static final SimpleSymbol Lit7;
   static final SimpleSymbol Lit8;
   static final SyntaxRules Lit9;
   public static final Macro condition;
   public static final ModuleMethod condition$Mnhas$Mntype$Qu;
   public static final ModuleMethod condition$Mnref;
   static final Macro condition$Mntype$Mnfield$Mnalist;
   public static final ModuleMethod condition$Mntype$Qu;
   public static final ModuleMethod condition$Qu;
   public static final Macro define$Mncondition$Mntype;
   public static final ModuleMethod extract$Mncondition;
   public static final ModuleMethod make$Mncompound$Mncondition;
   public static final ModuleMethod make$Mncondition;
   public static final ModuleMethod make$Mncondition$Mntype;


   static {
      SimpleSymbol var0 = (SimpleSymbol)(new SimpleSymbol("condition")).readResolve();
      Lit18 = var0;
      SyntaxRule var1 = new SyntaxRule(new SyntaxPattern("\f\u0018]\f\u0007-\f\u000f\f\u0017\b\b\u0010\b\u0000\u0018\b", new Object[0], 3), "\u0003\u0005\u0005", "\u0011\u0018\u0004\b\u0011\u0018\f\b\u0005\u0011\u0018\u0014\t\u0003\b\u0011\u0018\f\b\r\u0011\u0018\u0014)\u0011\u0018\u001c\b\u000b\b\u0013", new Object[]{Lit20, (SimpleSymbol)(new SimpleSymbol("list")).readResolve(), (SimpleSymbol)(new SimpleSymbol("cons")).readResolve(), Lit21}, 2);
      Lit19 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var1}, 3);
      Lit17 = (SimpleSymbol)(new SimpleSymbol("extract-condition")).readResolve();
      Lit16 = (SimpleSymbol)(new SimpleSymbol("make-compound-condition")).readResolve();
      Lit15 = (SimpleSymbol)(new SimpleSymbol("condition-ref")).readResolve();
      var0 = (SimpleSymbol)(new SimpleSymbol("condition-type-field-alist")).readResolve();
      Lit13 = var0;
      var1 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\b", new Object[0], 1), "\u0001", "\u0011\u0018\u0004\b\u0011\u0018\f\u0011\u0018\u0014\b\u0003", new Object[]{PairWithPosition.make((SimpleSymbol)(new SimpleSymbol("$lookup$")).readResolve(), Pair.make((SimpleSymbol)(new SimpleSymbol("*")).readResolve(), Pair.make(Pair.make((SimpleSymbol)(new SimpleSymbol("quasiquote")).readResolve(), Pair.make((SimpleSymbol)(new SimpleSymbol(".type-field-alist")).readResolve(), LList.Empty)), LList.Empty)), "conditions.scm", 581639), (SimpleSymbol)(new SimpleSymbol("as")).readResolve(), (SimpleSymbol)(new SimpleSymbol("<condition>")).readResolve()}, 0);
      Lit14 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var1}, 1);
      Lit12 = (SimpleSymbol)(new SimpleSymbol("condition-has-type?")).readResolve();
      Lit11 = (SimpleSymbol)(new SimpleSymbol("make-condition")).readResolve();
      Lit10 = (SimpleSymbol)(new SimpleSymbol("condition?")).readResolve();
      var0 = (SimpleSymbol)(new SimpleSymbol("define-condition-type")).readResolve();
      Lit8 = var0;
      SyntaxPattern var6 = new SyntaxPattern("\f\u0018\f\u0007\f\u000f\f\u0017-\f\u001f\f\'\b\u0018\u0010\b", new Object[0], 5);
      SimpleSymbol var2 = (SimpleSymbol)(new SimpleSymbol("begin")).readResolve();
      SimpleSymbol var3 = (SimpleSymbol)(new SimpleSymbol("define")).readResolve();
      SimpleSymbol var4 = (SimpleSymbol)(new SimpleSymbol("make-condition-type")).readResolve();
      Lit7 = var4;
      var1 = new SyntaxRule(var6, "\u0001\u0001\u0001\u0003\u0003", "\u0011\u0018\u0004É\u0011\u0018\f\t\u0003\b\u0011\u0018\u0014)\u0011\u0018\u001c\b\u0003\t\u000b\b\u0011\u0018\u001c\b\b\u001d\u001bÁ\u0011\u0018\f!\t\u0013\u0018$\b\u0011\u0018,\u0011\u00184\b\u0011\u0018<\u0011\u0018D\b\u0003\b%\u0011\u0018\f!\t#\u0018L\b\u0011\u0018TA\u0011\u0018\\\u0011\u0018d\b\u0003\b\u0011\u0018\u001c\b\u001b", new Object[]{var2, var3, var4, Lit21, PairWithPosition.make(Lit22, LList.Empty, "conditions.scm", 327708), (SimpleSymbol)(new SimpleSymbol("and")).readResolve(), PairWithPosition.make(Lit10, PairWithPosition.make(Lit22, LList.Empty, "conditions.scm", 331803), "conditions.scm", 331791), Lit12, Lit22, PairWithPosition.make(Lit18, LList.Empty, "conditions.scm", 339996), Lit15, Lit17, Lit18}, 1);
      Lit9 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var1}, 5);
      Lit6 = (SimpleSymbol)(new SimpleSymbol("condition-type?")).readResolve();
      Lit5 = (SimpleSymbol)(new SimpleSymbol("message")).readResolve();
      Lit4 = (SimpleSymbol)(new SimpleSymbol("&error")).readResolve();
      Lit3 = (SimpleSymbol)(new SimpleSymbol("&serious")).readResolve();
      Lit2 = PairWithPosition.make(Lit5, LList.Empty, "conditions.scm", 925699);
      Lit1 = (SimpleSymbol)(new SimpleSymbol("&message")).readResolve();
      Lit0 = (SimpleSymbol)(new SimpleSymbol("&condition")).readResolve();
      $instance = new conditions();
      $Lscondition$Mntype$Gr = conditions.Mntype.class;
      conditions var5 = $instance;
      condition$Mntype$Qu = new ModuleMethod(var5, 2, Lit6, 4097);
      make$Mncondition$Mntype = new ModuleMethod(var5, 3, Lit7, 12291);
      define$Mncondition$Mntype = Macro.make(Lit8, Lit9, $instance);
      $Prvt$$Lscondition$Gr = condition.class;
      condition$Qu = new ModuleMethod(var5, 4, Lit10, 4097);
      make$Mncondition = new ModuleMethod(var5, 5, Lit11, -4095);
      condition$Mnhas$Mntype$Qu = new ModuleMethod(var5, 6, Lit12, 8194);
      condition$Mntype$Mnfield$Mnalist = Macro.make(Lit13, Lit14, $instance);
      condition$Mnref = new ModuleMethod(var5, 7, Lit15, 8194);
      make$Mncompound$Mncondition = new ModuleMethod(var5, 8, Lit16, -4095);
      extract$Mncondition = new ModuleMethod(var5, 9, Lit17, 8194);
      condition = Macro.make(Lit18, Lit19, $instance);
      $Prvt$type$Mnfield$Mnalist$Mn$Grcondition = new ModuleMethod(var5, 10, Lit20, 4097);
      $instance.run();
   }

   public conditions() {
      ModuleInfo.register(this);
   }

   static Object checkConditionTypeFieldAlist(Object var0) {
      for(Object var1 = var0; !lists.isNull(var1); var1 = lists.cdr.apply1(var1)) {
         Object var3 = lists.car.apply1(var1);
         Object var2 = lists.car.apply1(var3);

         conditions.Mntype var4;
         try {
            var4 = (conditions.Mntype)var2;
         } catch (ClassCastException var14) {
            throw new WrongType(var14, "type", -2, var2);
         }

         var2 = lists.cdr.apply1(var3);

         Pair var5;
         for(var3 = LList.Empty; var2 != LList.Empty; var3 = Pair.make(lists.car.apply1(var5.getCar()), var3)) {
            try {
               var5 = (Pair)var2;
            } catch (ClassCastException var13) {
               throw new WrongType(var13, "arg0", -2, var2);
            }

            var2 = var5.getCdr();
         }

         LList var15 = LList.reverseInPlace(var3);
         var3 = var4.all$Mnfields;
         var2 = srfi1.lsetDifference$V(Scheme.isEq, var3, new Object[]{var15});

         while(var2 != LList.Empty) {
            Pair var16;
            try {
               var16 = (Pair)var2;
            } catch (ClassCastException var12) {
               throw new WrongType(var12, "arg0", -2, var2);
            }

            Object var6 = var16.getCar();
            Object var17 = conditionTypeFieldSupertype(var4, var6);
            var2 = var0;

            while(true) {
               Object var7 = lists.car.apply1(lists.car.apply1(var2));

               conditions.Mntype var8;
               try {
                  var8 = (conditions.Mntype)var7;
               } catch (ClassCastException var11) {
                  throw new WrongType(var11, "condition-subtype?", 0, var7);
               }

               conditions.Mntype var18;
               try {
                  var18 = (conditions.Mntype)var17;
               } catch (ClassCastException var10) {
                  throw new WrongType(var10, "condition-subtype?", 1, var17);
               }

               boolean var9 = isConditionSubtype(var8, var18);
               if(var9) {
                  if(!var9) {
                     misc.error$V("missing field in condition construction", new Object[]{var4, var6});
                  }

                  var2 = var16.getCdr();
                  break;
               }

               var2 = lists.cdr.apply1(var2);
            }
         }
      }

      return Values.empty;
   }

   static Object conditionMessage(Object var0) {
      condition var1;
      try {
         var1 = (condition)var0;
      } catch (ClassCastException var4) {
         throw new WrongType(var4, "extract-condition", 0, var0);
      }

      var0 = $Ammessage;

      conditions.Mntype var2;
      try {
         var2 = (conditions.Mntype)var0;
      } catch (ClassCastException var3) {
         throw new WrongType(var3, "extract-condition", 1, var0);
      }

      return conditionRef(extractCondition(var1, var2), Lit5);
   }

   public static Object conditionRef(condition var0, Object var1) {
      return typeFieldAlistRef(var0.type$Mnfield$Mnalist, var1);
   }

   static Object conditionTypeFieldSupertype(conditions.Mntype var0, Object var1) {
      while(true) {
         Object var2;
         if(var0 == Boolean.FALSE) {
            var2 = Boolean.FALSE;
         } else {
            var2 = var0;
            if(lists.memq(var1, var0.fields) == Boolean.FALSE) {
               var0 = (conditions.Mntype)var0.supertype;
               continue;
            }
         }

         return var2;
      }
   }

   static Object conditionTypes(Object var0) {
      var0 = ((condition)var0).type$Mnfield$Mnalist;

      Object var1;
      Pair var2;
      for(var1 = LList.Empty; var0 != LList.Empty; var1 = Pair.make(lists.car.apply1(var2.getCar()), var1)) {
         try {
            var2 = (Pair)var0;
         } catch (ClassCastException var3) {
            throw new WrongType(var3, "arg0", -2, var0);
         }

         var0 = var2.getCdr();
      }

      return LList.reverseInPlace(var1);
   }

   public static condition extractCondition(condition var0, conditions.Mntype var1) {
      conditions.frame var4 = new conditions.frame();
      var4.type = var1;
      Object var2 = srfi1.find(var4.lambda$Fn1, var0.type$Mnfield$Mnalist);
      if(var2 == Boolean.FALSE) {
         misc.error$V("extract-condition: invalid condition type", new Object[]{var0, var4.type});
      }

      conditions.Mntype var3 = var4.type;
      Object var6 = var4.type.all$Mnfields;

      Object var7;
      Pair var8;
      for(var7 = LList.Empty; var6 != LList.Empty; var7 = Pair.make(lists.assq(var8.getCar(), lists.cdr.apply1(var2)), var7)) {
         try {
            var8 = (Pair)var6;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "arg0", -2, var6);
         }

         var6 = var8.getCdr();
      }

      return new condition(LList.list1(lists.cons(var3, LList.reverseInPlace(var7))));
   }

   public static boolean isCondition(Object var0) {
      return var0 instanceof condition;
   }

   public static boolean isConditionHasType(Object var0, conditions.Mntype var1) {
      var0 = conditionTypes(var0);

      while(true) {
         Object var2 = lists.car.apply1(var0);

         conditions.Mntype var3;
         try {
            var3 = (conditions.Mntype)var2;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "condition-subtype?", 0, var2);
         }

         boolean var4 = isConditionSubtype(var3, var1);
         if(var4) {
            return var4;
         }

         var0 = lists.cdr.apply1(var0);
      }
   }

   static boolean isConditionSubtype(conditions.Mntype var0, conditions.Mntype var1) {
      while(var0 != Boolean.FALSE) {
         if(var0 == var1) {
            return true;
         }

         var0 = (conditions.Mntype)var0.supertype;
      }

      return false;
   }

   public static boolean isConditionType(Object var0) {
      return var0 instanceof conditions.Mntype;
   }

   static boolean isError(Object var0) {
      boolean var4 = isCondition(var0);
      boolean var3 = var4;
      if(var4) {
         Object var1 = $Amerror;

         conditions.Mntype var2;
         try {
            var2 = (conditions.Mntype)var1;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "condition-has-type?", 1, var1);
         }

         var3 = isConditionHasType(var0, var2);
      }

      return var3;
   }

   static boolean isMessageCondition(Object var0) {
      boolean var4 = isCondition(var0);
      boolean var3 = var4;
      if(var4) {
         Object var1 = $Ammessage;

         conditions.Mntype var2;
         try {
            var2 = (conditions.Mntype)var1;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "condition-has-type?", 1, var1);
         }

         var3 = isConditionHasType(var0, var2);
      }

      return var3;
   }

   static boolean isSeriousCondition(Object var0) {
      boolean var4 = isCondition(var0);
      boolean var3 = var4;
      if(var4) {
         Object var1 = $Amserious;

         conditions.Mntype var2;
         try {
            var2 = (conditions.Mntype)var1;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "condition-has-type?", 1, var1);
         }

         var3 = isConditionHasType(var0, var2);
      }

      return var3;
   }

   public static Object lambda1label(Object var0) {
      return lists.isNull(var0)?LList.Empty:lists.cons(lists.cons(lists.car.apply1(var0), lists.cadr.apply1(var0)), lambda1label(lists.cddr.apply1(var0)));
   }

   public static condition makeCompoundCondition$V(Object var0, Object[] var1) {
      LList var6 = LList.makeList(var1, 0);
      Apply var2 = Scheme.apply;
      append var3 = append.append;
      var0 = lists.cons(var0, var6);

      Pair var4;
      Object var7;
      for(var7 = LList.Empty; var0 != LList.Empty; var7 = Pair.make(Scheme.applyToArgs.apply2(condition$Mntype$Mnfield$Mnalist, var4.getCar()), var7)) {
         try {
            var4 = (Pair)var0;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "arg0", -2, var0);
         }

         var0 = var4.getCdr();
      }

      return new condition(var2.apply2(var3, LList.reverseInPlace(var7)));
   }

   public static condition makeCondition$V(conditions.Mntype var0, Object[] var1) {
      Object var3 = lambda1label(LList.makeList(var1, 0));
      IsEq var4 = Scheme.isEq;
      Object var5 = var0.all$Mnfields;
      Object var2 = LList.Empty;

      Pair var6;
      for(Object var8 = var3; var8 != LList.Empty; var2 = Pair.make(lists.car.apply1(var6.getCar()), var2)) {
         try {
            var6 = (Pair)var8;
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "arg0", -2, var8);
         }

         var8 = var6.getCdr();
      }

      if(srfi1.lset$Eq$V(var4, new Object[]{var5, LList.reverseInPlace(var2)}) == Boolean.FALSE) {
         misc.error$V("condition fields don\'t match condition type", new Object[0]);
      }

      return new condition(LList.list1(lists.cons(var0, var3)));
   }

   public static conditions.Mntype makeConditionType(Symbol var0, conditions.Mntype var1, Object var2) {
      if(!lists.isNull(srfi1.lsetIntersection$V(Scheme.isEq, var1.all$Mnfields, new Object[]{var2}))) {
         misc.error$V("duplicate field name", new Object[0]);
      }

      return var0.new Mntype(var1, var2, append.append$V(new Object[]{var1.all$Mnfields, var2}));
   }

   public static condition typeFieldAlist$To$Condition(Object var0) {
      Object var1 = LList.Empty;

      Object var4;
      for(Object var2 = var0; var2 != LList.Empty; var2 = var4) {
         Pair var3;
         try {
            var3 = (Pair)var2;
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "arg0", -2, var2);
         }

         var4 = var3.getCdr();
         Object var6 = var3.getCar();
         Object var7 = lists.car.apply1(var6);
         Object var11 = ((conditions.Mntype)lists.car.apply1(var6)).all$Mnfields;

         Object var5;
         for(var2 = LList.Empty; var11 != LList.Empty; var11 = var5) {
            Pair var8;
            try {
               var8 = (Pair)var11;
            } catch (ClassCastException var9) {
               throw new WrongType(var9, "arg0", -2, var11);
            }

            var5 = var8.getCdr();
            Object var12 = var8.getCar();
            var11 = lists.assq(var12, lists.cdr.apply1(var6));
            if(var11 == Boolean.FALSE) {
               var11 = lists.cons(var12, typeFieldAlistRef(var0, var12));
            }

            var2 = Pair.make(var11, var2);
         }

         var1 = Pair.make(lists.cons(var7, LList.reverseInPlace(var2)), var1);
      }

      return new condition(LList.reverseInPlace(var1));
   }

   static Object typeFieldAlistRef(Object var0, Object var1) {
      while(!lists.isNull(var0)) {
         Object var2 = lists.assq(var1, lists.cdr.apply1(lists.car.apply1(var0)));
         if(var2 != Boolean.FALSE) {
            return lists.cdr.apply1(var2);
         }

         var0 = lists.cdr.apply1(var0);
      }

      return misc.error$V("type-field-alist-ref: field not found", new Object[]{var0, var1});
   }

   public Object apply1(ModuleMethod var1, Object var2) {
      switch(var1.selector) {
      case 2:
         if(isConditionType(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 4:
         if(isCondition(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 10:
         return typeFieldAlist$To$Condition(var2);
      default:
         return super.apply1(var1, var2);
      }
   }

   public Object apply2(ModuleMethod var1, Object var2, Object var3) {
      condition var8;
      switch(var1.selector) {
      case 6:
         conditions.Mntype var9;
         try {
            var9 = (conditions.Mntype)var3;
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "condition-has-type?", 2, var3);
         }

         if(isConditionHasType(var2, var9)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 7:
         try {
            var8 = (condition)var2;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "condition-ref", 1, var2);
         }

         return conditionRef(var8, var3);
      case 8:
      default:
         return super.apply2(var1, var2, var3);
      case 9:
         try {
            var8 = (condition)var2;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "extract-condition", 1, var2);
         }

         conditions.Mntype var10;
         try {
            var10 = (conditions.Mntype)var3;
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "extract-condition", 2, var3);
         }

         return extractCondition(var8, var10);
      }
   }

   public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
      if(var1.selector == 3) {
         Symbol var7;
         try {
            var7 = (Symbol)var2;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "make-condition-type", 1, var2);
         }

         conditions.Mntype var8;
         try {
            var8 = (conditions.Mntype)var3;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "make-condition-type", 2, var3);
         }

         return makeConditionType(var7, var8, var4);
      } else {
         return super.apply3(var1, var2, var3, var4);
      }
   }

   public Object applyN(ModuleMethod var1, Object[] var2) {
      int var4;
      Object var6;
      switch(var1.selector) {
      case 5:
         var6 = var2[0];

         conditions.Mntype var8;
         try {
            var8 = (conditions.Mntype)var6;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "make-condition", 1, var6);
         }

         var4 = var2.length - 1;
         Object[] var7 = new Object[var4];

         while(true) {
            --var4;
            if(var4 < 0) {
               return makeCondition$V(var8, var7);
            }

            var7[var4] = var2[var4 + 1];
         }
      case 6:
      case 7:
      default:
         return super.applyN(var1, var2);
      case 8:
         var6 = var2[0];
         var4 = var2.length - 1;
         Object[] var3 = new Object[var4];

         while(true) {
            --var4;
            if(var4 < 0) {
               return makeCompoundCondition$V(var6, var3);
            }

            var3[var4] = var2[var4 + 1];
         }
      }
   }

   public int match1(ModuleMethod var1, Object var2, CallContext var3) {
      switch(var1.selector) {
      case 2:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 4:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 10:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      default:
         return super.match1(var1, var2, var3);
      }
   }

   public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
      int var5 = -786431;
      switch(var1.selector) {
      case 6:
         var4.value1 = var2;
         if(!(var3 instanceof conditions.Mntype)) {
            return -786430;
         }

         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 7:
         if(var2 instanceof condition) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }
         break;
      case 8:
      default:
         var5 = super.match2(var1, var2, var3, var4);
         break;
      case 9:
         if(var2 instanceof condition) {
            var4.value1 = var2;
            if(!(var3 instanceof conditions.Mntype)) {
               return -786430;
            }

            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }
      }

      return var5;
   }

   public int match3(ModuleMethod var1, Object var2, Object var3, Object var4, CallContext var5) {
      if(var1.selector == 3) {
         if(!(var2 instanceof Symbol)) {
            return -786431;
         } else {
            var5.value1 = var2;
            if(!(var3 instanceof conditions.Mntype)) {
               return -786430;
            } else {
               var5.value2 = var3;
               var5.value3 = var4;
               var5.proc = var1;
               var5.pc = 3;
               return 0;
            }
         }
      } else {
         return super.match3(var1, var2, var3, var4, var5);
      }
   }

   public int matchN(ModuleMethod var1, Object[] var2, CallContext var3) {
      switch(var1.selector) {
      case 5:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 6:
      case 7:
      default:
         return super.matchN(var1, var2, var3);
      case 8:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      }
   }

   public final void run(CallContext var1) {
      Consumer var7 = var1.consumer;
      $Amcondition = Lit0.new Mntype(Boolean.FALSE, LList.Empty, LList.Empty);
      SimpleSymbol var2 = Lit1;
      Object var8 = $Amcondition;

      conditions.Mntype var3;
      try {
         var3 = (conditions.Mntype)var8;
      } catch (ClassCastException var6) {
         throw new WrongType(var6, "make-condition-type", 1, var8);
      }

      $Ammessage = makeConditionType(var2, var3, Lit2);
      var2 = Lit3;
      var8 = $Amcondition;

      try {
         var3 = (conditions.Mntype)var8;
      } catch (ClassCastException var5) {
         throw new WrongType(var5, "make-condition-type", 1, var8);
      }

      $Amserious = makeConditionType(var2, var3, LList.Empty);
      var2 = Lit4;
      var8 = $Amserious;

      try {
         var3 = (conditions.Mntype)var8;
      } catch (ClassCastException var4) {
         throw new WrongType(var4, "make-condition-type", 1, var8);
      }

      $Amerror = makeConditionType(var2, var3, LList.Empty);
   }

   public class Mntype {

      public Object all$Mnfields;
      public Object fields;
      public Object name = conditions.this;
      public Object supertype;


      public Mntype(Object var2, Object var3, Object var4) {
         this.supertype = var2;
         this.fields = var3;
         this.all$Mnfields = var4;
      }

      public String toString() {
         StringBuffer var1 = new StringBuffer("#<condition-type ");
         var1.append(this.name);
         var1.append(">");
         return var1.toString();
      }
   }

   public class frame extends ModuleBody {

      final ModuleMethod lambda$Fn1;
      conditions.Mntype type;


      public frame() {
         ModuleMethod var1 = new ModuleMethod(this, 1, (Object)null, 4097);
         var1.setProperty("source-location", "conditions.scm:166");
         this.lambda$Fn1 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 1?(this.lambda2(var2)?Boolean.TRUE:Boolean.FALSE):super.apply1(var1, var2);
      }

      boolean lambda2(Object var1) {
         var1 = lists.car.apply1(var1);

         conditions.Mntype var2;
         try {
            var2 = (conditions.Mntype)var1;
         } catch (ClassCastException var3) {
            throw new WrongType(var3, "condition-subtype?", 0, var1);
         }

         return conditions.isConditionSubtype(var2, this.type);
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
