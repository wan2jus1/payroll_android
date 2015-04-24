package gnu.kawa.slib;

import gnu.expr.GenericProc;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.AddOp;
import gnu.kawa.functions.Apply;
import gnu.kawa.functions.Map;
import gnu.kawa.functions.MultiplyOp;
import gnu.kawa.lispexpr.LangObjType;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.CallContext;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import gnu.math.Numeric;
import kawa.lang.Continuation;
import kawa.lang.Macro;
import kawa.lang.SyntaxPattern;
import kawa.lang.SyntaxRule;
import kawa.lang.SyntaxRules;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.lib.numbers;
import kawa.standard.Scheme;
import kawa.standard.append;
import kawa.standard.call_with_values;

public class srfi1 extends ModuleBody {

   public static final Macro $Pcevery;
   public static final int $Pcprovide$Pclist$Mnlib = 123;
   public static final int $Pcprovide$Pcsrfi$Mn1 = 123;
   public static final srfi1 $instance;
   static final IntNum Lit0;
   static final IntNum Lit1;
   static final SimpleSymbol Lit10;
   static final SimpleSymbol Lit100 = (SimpleSymbol)(new SimpleSymbol("tail")).readResolve();
   static final SimpleSymbol Lit101 = (SimpleSymbol)(new SimpleSymbol("head")).readResolve();
   static final SimpleSymbol Lit102 = (SimpleSymbol)(new SimpleSymbol("lp")).readResolve();
   static final SimpleSymbol Lit103 = (SimpleSymbol)(new SimpleSymbol("car")).readResolve();
   static final SimpleSymbol Lit104 = (SimpleSymbol)(new SimpleSymbol("cdr")).readResolve();
   static final SimpleSymbol Lit11;
   static final SimpleSymbol Lit12;
   static final SimpleSymbol Lit13;
   static final SimpleSymbol Lit14;
   static final SimpleSymbol Lit15;
   static final SimpleSymbol Lit16;
   static final SimpleSymbol Lit17;
   static final SimpleSymbol Lit18;
   static final SimpleSymbol Lit19;
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
   static final SimpleSymbol Lit71;
   static final SimpleSymbol Lit72;
   static final SimpleSymbol Lit73;
   static final SimpleSymbol Lit74;
   static final SimpleSymbol Lit75;
   static final SimpleSymbol Lit76;
   static final SimpleSymbol Lit77;
   static final SimpleSymbol Lit78;
   static final SimpleSymbol Lit79;
   static final SimpleSymbol Lit8;
   static final SimpleSymbol Lit80;
   static final SimpleSymbol Lit81;
   static final SimpleSymbol Lit82;
   static final SimpleSymbol Lit83;
   static final SimpleSymbol Lit84;
   static final SyntaxRules Lit85;
   static final SimpleSymbol Lit86 = (SimpleSymbol)(new SimpleSymbol("list-index")).readResolve();
   static final SimpleSymbol Lit87 = (SimpleSymbol)(new SimpleSymbol("lset<=")).readResolve();
   static final SimpleSymbol Lit88 = (SimpleSymbol)(new SimpleSymbol("lset=")).readResolve();
   static final SimpleSymbol Lit89 = (SimpleSymbol)(new SimpleSymbol("lset-adjoin")).readResolve();
   static final SimpleSymbol Lit9;
   static final SimpleSymbol Lit90 = (SimpleSymbol)(new SimpleSymbol("lset-union")).readResolve();
   static final SimpleSymbol Lit91 = (SimpleSymbol)(new SimpleSymbol("lset-union!")).readResolve();
   static final SimpleSymbol Lit92 = (SimpleSymbol)(new SimpleSymbol("lset-intersection")).readResolve();
   static final SimpleSymbol Lit93 = (SimpleSymbol)(new SimpleSymbol("lset-intersection!")).readResolve();
   static final SimpleSymbol Lit94 = (SimpleSymbol)(new SimpleSymbol("lset-difference")).readResolve();
   static final SimpleSymbol Lit95 = (SimpleSymbol)(new SimpleSymbol("lset-difference!")).readResolve();
   static final SimpleSymbol Lit96 = (SimpleSymbol)(new SimpleSymbol("lset-xor")).readResolve();
   static final SimpleSymbol Lit97 = (SimpleSymbol)(new SimpleSymbol("lset-xor!")).readResolve();
   static final SimpleSymbol Lit98 = (SimpleSymbol)(new SimpleSymbol("lset-diff+intersection")).readResolve();
   static final SimpleSymbol Lit99 = (SimpleSymbol)(new SimpleSymbol("lset-diff+intersection!")).readResolve();
   public static final ModuleMethod alist$Mncons;
   public static final ModuleMethod alist$Mncopy;
   public static final ModuleMethod alist$Mndelete;
   public static final ModuleMethod alist$Mndelete$Ex;
   public static final ModuleMethod any;
   public static final ModuleMethod append$Ex;
   public static final ModuleMethod append$Mnmap;
   public static final ModuleMethod append$Mnmap$Ex;
   public static final ModuleMethod append$Mnreverse;
   public static final ModuleMethod append$Mnreverse$Ex;
   public static final ModuleMethod break;
   public static final ModuleMethod break$Ex;
   public static final ModuleMethod car$Plcdr;
   public static final ModuleMethod circular$Mnlist;
   public static final ModuleMethod circular$Mnlist$Qu;
   public static final ModuleMethod concatenate;
   public static final ModuleMethod concatenate$Ex;
   public static final ModuleMethod cons$St;
   public static final ModuleMethod count;
   public static final ModuleMethod delete;
   public static final ModuleMethod delete$Ex;
   public static final ModuleMethod delete$Mnduplicates;
   public static final ModuleMethod delete$Mnduplicates$Ex;
   public static final ModuleMethod dotted$Mnlist$Qu;
   public static final ModuleMethod drop;
   public static final ModuleMethod drop$Mnright;
   public static final ModuleMethod drop$Mnright$Ex;
   public static final ModuleMethod drop$Mnwhile;
   public static final ModuleMethod eighth;
   public static final ModuleMethod every;
   public static final ModuleMethod fifth;
   public static final ModuleMethod filter;
   public static final ModuleMethod filter$Ex;
   public static final ModuleMethod filter$Mnmap;
   public static final ModuleMethod find;
   public static final ModuleMethod find$Mntail;
   public static GenericProc first;
   public static final ModuleMethod fold;
   public static final ModuleMethod fold$Mnright;
   public static GenericProc fourth;
   public static final ModuleMethod iota;
   static final ModuleMethod lambda$Fn64;
   static final ModuleMethod lambda$Fn78;
   public static final ModuleMethod last;
   public static final ModuleMethod last$Mnpair;
   public static final ModuleMethod length$Pl;
   public static final ModuleMethod list$Eq;
   public static final ModuleMethod list$Mncopy;
   public static final ModuleMethod list$Mnindex;
   public static final ModuleMethod list$Mntabulate;
   public static final ModuleMethod lset$Eq;
   public static final ModuleMethod lset$Ls$Eq;
   public static final ModuleMethod lset$Mnadjoin;
   public static final ModuleMethod lset$Mndiff$Plintersection;
   public static final ModuleMethod lset$Mndiff$Plintersection$Ex;
   public static final ModuleMethod lset$Mndifference;
   public static final ModuleMethod lset$Mndifference$Ex;
   public static final ModuleMethod lset$Mnintersection;
   public static final ModuleMethod lset$Mnintersection$Ex;
   public static final ModuleMethod lset$Mnunion;
   public static final ModuleMethod lset$Mnunion$Ex;
   public static final ModuleMethod lset$Mnxor;
   public static final ModuleMethod lset$Mnxor$Ex;
   public static final ModuleMethod make$Mnlist;
   public static final ModuleMethod map$Ex;
   public static Map map$Mnin$Mnorder;
   public static final ModuleMethod ninth;
   public static final ModuleMethod not$Mnpair$Qu;
   public static final ModuleMethod null$Mnlist$Qu;
   public static final ModuleMethod pair$Mnfold;
   public static final ModuleMethod pair$Mnfold$Mnright;
   public static final ModuleMethod pair$Mnfor$Mneach;
   public static final ModuleMethod partition;
   public static final ModuleMethod partition$Ex;
   public static final ModuleMethod proper$Mnlist$Qu;
   public static final ModuleMethod reduce;
   public static final ModuleMethod reduce$Mnright;
   public static final ModuleMethod remove;
   public static final ModuleMethod remove$Ex;
   public static GenericProc second;
   public static final ModuleMethod seventh;
   public static final ModuleMethod sixth;
   public static final ModuleMethod span;
   public static final ModuleMethod span$Ex;
   public static final ModuleMethod split$Mnat;
   public static final ModuleMethod split$Mnat$Ex;
   public static final ModuleMethod take;
   public static final ModuleMethod take$Ex;
   public static final ModuleMethod take$Mnright;
   public static final ModuleMethod take$Mnwhile;
   public static final ModuleMethod take$Mnwhile$Ex;
   public static final ModuleMethod tenth;
   public static GenericProc third;
   public static final ModuleMethod unfold;
   public static final ModuleMethod unfold$Mnright;
   public static final ModuleMethod unzip1;
   public static final ModuleMethod unzip2;
   public static final ModuleMethod unzip3;
   public static final ModuleMethod unzip4;
   public static final ModuleMethod unzip5;
   public static final ModuleMethod xcons;
   public static final ModuleMethod zip;


   static Object $PcCars$Pl(Object var0, Object var1) {
      srfi1.frame56 var2 = new srfi1.frame56();
      var2.last$Mnelt = var1;
      return var2.lambda75recur(var0);
   }

   static Object $PcCars$PlCdrs(Object var0) {
      Continuation var1 = new Continuation(CallContext.getInstance());

      try {
         srfi1.frame57 var2 = new srfi1.frame57();
         var2.abort = var1;
         var0 = var2.lambda76recur(var0);
         var1.invoked = true;
         return var0;
      } catch (Throwable var4) {
         return Continuation.handleException(var4, var1);
      }
   }

   static Object $PcCars$PlCdrs$Pl(Object var0, Object var1) {
      srfi1.frame62 var2 = new srfi1.frame62();
      var2.cars$Mnfinal = var1;
      Continuation var6 = new Continuation(CallContext.getInstance());

      try {
         srfi1.frame63 var3 = new srfi1.frame63();
         var3.staticLink = var2;
         var3.abort = var6;
         var0 = var3.lambda85recur(var0);
         var6.invoked = true;
         return var0;
      } catch (Throwable var5) {
         return Continuation.handleException(var5, var6);
      }
   }

   static Object $PcCars$PlCdrs$SlNoTest(Object var0) {
      new srfi1.frame67();
      return srfi1.frame67.lambda92recur(var0);
   }

   static Object $PcCars$PlCdrs$SlNoTest$SlPair(Object var0) {
      srfi1.frame71 var1 = new srfi1.frame71();
      var1.lists = var0;
      return call_with_values.callWithValues(var1.lambda$Fn77, lambda$Fn78);
   }

   static Object $PcCars$PlCdrs$SlPair(Object var0) {
      srfi1.frame61 var1 = new srfi1.frame61();
      var1.lists = var0;
      return call_with_values.callWithValues(var1.lambda$Fn63, lambda$Fn64);
   }

   static Object $PcCdrs(Object var0) {
      Continuation var1 = new Continuation(CallContext.getInstance());

      try {
         srfi1.frame55 var2 = new srfi1.frame55();
         var2.abort = var1;
         var0 = var2.lambda74recur(var0);
         var1.invoked = true;
         return var0;
      } catch (Throwable var4) {
         return Continuation.handleException(var4, var1);
      }
   }

   static Object $PcLset2$Ls$Eq(Object var0, Object var1, Object var2) {
      srfi1.frame72 var3 = new srfi1.frame72();
      var3.$Eq = var0;
      var3.lis2 = var2;
      return every$V(var3.lambda$Fn79, var1, new Object[0]);
   }

   static {
      SimpleSymbol var0 = (SimpleSymbol)(new SimpleSymbol("%every")).readResolve();
      Lit84 = var0;
      SyntaxPattern var1 = new SyntaxPattern("\f\u0018\f\u0007\f\u000f\b", new Object[0], 2);
      SimpleSymbol var2 = (SimpleSymbol)(new SimpleSymbol("let")).readResolve();
      SimpleSymbol var3 = Lit102;
      SimpleSymbol var4 = Lit101;
      SimpleSymbol var5 = Lit103;
      SimpleSymbol var6 = Lit100;
      SimpleSymbol var7 = Lit104;
      SimpleSymbol var8 = (SimpleSymbol)(new SimpleSymbol("and")).readResolve();
      SimpleSymbol var9 = (SimpleSymbol)(new SimpleSymbol("null-list?")).readResolve();
      Lit14 = var9;
      SyntaxRule var11 = new SyntaxRule(var1, "\u0001\u0001", "\u0011\u0018\u0004\u0011\u0018\f¡I\u0011\u0018\u0014\b\u0011\u0018\u001c\b\u000b\b\u0011\u0018$\b\u0011\u0018,\b\u000b\b\u0011\u00184\u0011\u0018<!\t\u0003\u0018D\u0018L", new Object[]{var2, var3, var4, var5, var6, var7, var8, PairWithPosition.make(var9, PairWithPosition.make(Lit100, LList.Empty, "srfi1.scm", 5722136), "srfi1.scm", 5722124), PairWithPosition.make(Lit101, LList.Empty, "srfi1.scm", 5722148), PairWithPosition.make(PairWithPosition.make(Lit102, PairWithPosition.make(PairWithPosition.make(Lit103, PairWithPosition.make(Lit100, LList.Empty, "srfi1.scm", 5722163), "srfi1.scm", 5722158), PairWithPosition.make(PairWithPosition.make(Lit104, PairWithPosition.make(Lit100, LList.Empty, "srfi1.scm", 5722174), "srfi1.scm", 5722169), LList.Empty, "srfi1.scm", 5722169), "srfi1.scm", 5722158), "srfi1.scm", 5722154), LList.Empty, "srfi1.scm", 5722154)}, 0);
      Lit85 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var11}, 2);
      Lit83 = (SimpleSymbol)(new SimpleSymbol("every")).readResolve();
      Lit82 = (SimpleSymbol)(new SimpleSymbol("any")).readResolve();
      Lit81 = (SimpleSymbol)(new SimpleSymbol("break!")).readResolve();
      Lit80 = (SimpleSymbol)(new SimpleSymbol("break")).readResolve();
      Lit79 = (SimpleSymbol)(new SimpleSymbol("span!")).readResolve();
      Lit78 = (SimpleSymbol)(new SimpleSymbol("span")).readResolve();
      Lit77 = (SimpleSymbol)(new SimpleSymbol("take-while!")).readResolve();
      Lit76 = (SimpleSymbol)(new SimpleSymbol("drop-while")).readResolve();
      Lit75 = (SimpleSymbol)(new SimpleSymbol("take-while")).readResolve();
      Lit74 = (SimpleSymbol)(new SimpleSymbol("find-tail")).readResolve();
      Lit73 = (SimpleSymbol)(new SimpleSymbol("find")).readResolve();
      Lit72 = (SimpleSymbol)(new SimpleSymbol("alist-delete!")).readResolve();
      Lit71 = (SimpleSymbol)(new SimpleSymbol("alist-delete")).readResolve();
      Lit70 = (SimpleSymbol)(new SimpleSymbol("alist-copy")).readResolve();
      Lit69 = (SimpleSymbol)(new SimpleSymbol("alist-cons")).readResolve();
      Lit68 = (SimpleSymbol)(new SimpleSymbol("delete-duplicates!")).readResolve();
      Lit67 = (SimpleSymbol)(new SimpleSymbol("delete-duplicates")).readResolve();
      Lit66 = (SimpleSymbol)(new SimpleSymbol("delete!")).readResolve();
      Lit65 = (SimpleSymbol)(new SimpleSymbol("delete")).readResolve();
      Lit64 = (SimpleSymbol)(new SimpleSymbol("remove!")).readResolve();
      Lit63 = (SimpleSymbol)(new SimpleSymbol("remove")).readResolve();
      Lit62 = (SimpleSymbol)(new SimpleSymbol("partition!")).readResolve();
      Lit61 = (SimpleSymbol)(new SimpleSymbol("partition")).readResolve();
      Lit60 = (SimpleSymbol)(new SimpleSymbol("filter!")).readResolve();
      Lit59 = (SimpleSymbol)(new SimpleSymbol("filter")).readResolve();
      Lit58 = (SimpleSymbol)(new SimpleSymbol("filter-map")).readResolve();
      Lit57 = (SimpleSymbol)(new SimpleSymbol("map!")).readResolve();
      Lit56 = (SimpleSymbol)(new SimpleSymbol("pair-for-each")).readResolve();
      Lit55 = (SimpleSymbol)(new SimpleSymbol("append-map!")).readResolve();
      Lit54 = (SimpleSymbol)(new SimpleSymbol("append-map")).readResolve();
      Lit53 = (SimpleSymbol)(new SimpleSymbol("reduce-right")).readResolve();
      Lit52 = (SimpleSymbol)(new SimpleSymbol("reduce")).readResolve();
      Lit51 = (SimpleSymbol)(new SimpleSymbol("pair-fold")).readResolve();
      Lit50 = (SimpleSymbol)(new SimpleSymbol("pair-fold-right")).readResolve();
      Lit49 = (SimpleSymbol)(new SimpleSymbol("fold-right")).readResolve();
      Lit48 = (SimpleSymbol)(new SimpleSymbol("fold")).readResolve();
      Lit47 = (SimpleSymbol)(new SimpleSymbol("unfold")).readResolve();
      Lit46 = (SimpleSymbol)(new SimpleSymbol("unfold-right")).readResolve();
      Lit45 = (SimpleSymbol)(new SimpleSymbol("count")).readResolve();
      Lit44 = (SimpleSymbol)(new SimpleSymbol("concatenate!")).readResolve();
      Lit43 = (SimpleSymbol)(new SimpleSymbol("concatenate")).readResolve();
      Lit42 = (SimpleSymbol)(new SimpleSymbol("append-reverse!")).readResolve();
      Lit41 = (SimpleSymbol)(new SimpleSymbol("append-reverse")).readResolve();
      Lit40 = (SimpleSymbol)(new SimpleSymbol("append!")).readResolve();
      Lit39 = (SimpleSymbol)(new SimpleSymbol("unzip5")).readResolve();
      Lit38 = (SimpleSymbol)(new SimpleSymbol("unzip4")).readResolve();
      Lit37 = (SimpleSymbol)(new SimpleSymbol("unzip3")).readResolve();
      Lit36 = (SimpleSymbol)(new SimpleSymbol("unzip2")).readResolve();
      Lit35 = (SimpleSymbol)(new SimpleSymbol("unzip1")).readResolve();
      Lit34 = (SimpleSymbol)(new SimpleSymbol("last-pair")).readResolve();
      Lit33 = (SimpleSymbol)(new SimpleSymbol("last")).readResolve();
      Lit32 = (SimpleSymbol)(new SimpleSymbol("split-at!")).readResolve();
      Lit31 = (SimpleSymbol)(new SimpleSymbol("split-at")).readResolve();
      Lit30 = (SimpleSymbol)(new SimpleSymbol("drop-right!")).readResolve();
      Lit29 = (SimpleSymbol)(new SimpleSymbol("drop-right")).readResolve();
      Lit28 = (SimpleSymbol)(new SimpleSymbol("take-right")).readResolve();
      Lit27 = (SimpleSymbol)(new SimpleSymbol("take!")).readResolve();
      Lit26 = (SimpleSymbol)(new SimpleSymbol("drop")).readResolve();
      Lit25 = (SimpleSymbol)(new SimpleSymbol("take")).readResolve();
      Lit24 = (SimpleSymbol)(new SimpleSymbol("car+cdr")).readResolve();
      Lit23 = (SimpleSymbol)(new SimpleSymbol("tenth")).readResolve();
      Lit22 = (SimpleSymbol)(new SimpleSymbol("ninth")).readResolve();
      Lit21 = (SimpleSymbol)(new SimpleSymbol("eighth")).readResolve();
      Lit20 = (SimpleSymbol)(new SimpleSymbol("seventh")).readResolve();
      Lit19 = (SimpleSymbol)(new SimpleSymbol("sixth")).readResolve();
      Lit18 = (SimpleSymbol)(new SimpleSymbol("fifth")).readResolve();
      Lit17 = (SimpleSymbol)(new SimpleSymbol("zip")).readResolve();
      Lit16 = (SimpleSymbol)(new SimpleSymbol("length+")).readResolve();
      Lit15 = (SimpleSymbol)(new SimpleSymbol("list=")).readResolve();
      Lit13 = (SimpleSymbol)(new SimpleSymbol("not-pair?")).readResolve();
      Lit12 = (SimpleSymbol)(new SimpleSymbol("circular-list?")).readResolve();
      Lit11 = (SimpleSymbol)(new SimpleSymbol("dotted-list?")).readResolve();
      Lit10 = (SimpleSymbol)(new SimpleSymbol("proper-list?")).readResolve();
      Lit9 = (SimpleSymbol)(new SimpleSymbol("circular-list")).readResolve();
      Lit8 = (SimpleSymbol)(new SimpleSymbol("iota")).readResolve();
      Lit7 = (SimpleSymbol)(new SimpleSymbol("list-copy")).readResolve();
      Lit6 = (SimpleSymbol)(new SimpleSymbol("cons*")).readResolve();
      Lit5 = (SimpleSymbol)(new SimpleSymbol("list-tabulate")).readResolve();
      Lit4 = (SimpleSymbol)(new SimpleSymbol("make-list")).readResolve();
      Lit3 = (SimpleSymbol)(new SimpleSymbol("xcons")).readResolve();
      Lit2 = (SimpleSymbol)(new SimpleSymbol("tmp")).readResolve();
      Lit1 = IntNum.make(1);
      Lit0 = IntNum.make(0);
      $instance = new srfi1();
      $Pcprovide$Pcsrfi$Mn1 = 123;
      $Pcprovide$Pclist$Mnlib = 123;
      srfi1 var10 = $instance;
      xcons = new ModuleMethod(var10, 78, Lit3, 8194);
      make$Mnlist = new ModuleMethod(var10, 79, Lit4, -4095);
      list$Mntabulate = new ModuleMethod(var10, 80, Lit5, 8194);
      cons$St = new ModuleMethod(var10, 81, Lit6, -4096);
      list$Mncopy = new ModuleMethod(var10, 82, Lit7, 4097);
      iota = new ModuleMethod(var10, 83, Lit8, 12289);
      circular$Mnlist = new ModuleMethod(var10, 86, Lit9, -4095);
      proper$Mnlist$Qu = new ModuleMethod(var10, 87, Lit10, 4097);
      dotted$Mnlist$Qu = new ModuleMethod(var10, 88, Lit11, 4097);
      circular$Mnlist$Qu = new ModuleMethod(var10, 89, Lit12, 4097);
      not$Mnpair$Qu = new ModuleMethod(var10, 90, Lit13, 4097);
      null$Mnlist$Qu = new ModuleMethod(var10, 91, Lit14, 4097);
      list$Eq = new ModuleMethod(var10, 92, Lit15, -4095);
      length$Pl = new ModuleMethod(var10, 93, Lit16, 4097);
      zip = new ModuleMethod(var10, 94, Lit17, -4095);
      fifth = new ModuleMethod(var10, 95, Lit18, 4097);
      sixth = new ModuleMethod(var10, 96, Lit19, 4097);
      seventh = new ModuleMethod(var10, 97, Lit20, 4097);
      eighth = new ModuleMethod(var10, 98, Lit21, 4097);
      ninth = new ModuleMethod(var10, 99, Lit22, 4097);
      tenth = new ModuleMethod(var10, 100, Lit23, 4097);
      car$Plcdr = new ModuleMethod(var10, 101, Lit24, 4097);
      take = new ModuleMethod(var10, 102, Lit25, 8194);
      drop = new ModuleMethod(var10, 103, Lit26, 8194);
      take$Ex = new ModuleMethod(var10, 104, Lit27, 8194);
      take$Mnright = new ModuleMethod(var10, 105, Lit28, 8194);
      drop$Mnright = new ModuleMethod(var10, 106, Lit29, 8194);
      drop$Mnright$Ex = new ModuleMethod(var10, 107, Lit30, 8194);
      split$Mnat = new ModuleMethod(var10, 108, Lit31, 8194);
      split$Mnat$Ex = new ModuleMethod(var10, 109, Lit32, 8194);
      last = new ModuleMethod(var10, 110, Lit33, 4097);
      last$Mnpair = new ModuleMethod(var10, 111, Lit34, 4097);
      unzip1 = new ModuleMethod(var10, 112, Lit35, 4097);
      unzip2 = new ModuleMethod(var10, 113, Lit36, 4097);
      unzip3 = new ModuleMethod(var10, 114, Lit37, 4097);
      unzip4 = new ModuleMethod(var10, 115, Lit38, 4097);
      unzip5 = new ModuleMethod(var10, 116, Lit39, 4097);
      append$Ex = new ModuleMethod(var10, 117, Lit40, -4096);
      append$Mnreverse = new ModuleMethod(var10, 118, Lit41, 8194);
      append$Mnreverse$Ex = new ModuleMethod(var10, 119, Lit42, 8194);
      concatenate = new ModuleMethod(var10, 120, Lit43, 4097);
      concatenate$Ex = new ModuleMethod(var10, 121, Lit44, 4097);
      count = new ModuleMethod(var10, 122, Lit45, -4094);
      unfold$Mnright = new ModuleMethod(var10, 123, Lit46, 20484);
      unfold = new ModuleMethod(var10, 125, Lit47, -4092);
      fold = new ModuleMethod(var10, 126, Lit48, -4093);
      fold$Mnright = new ModuleMethod(var10, 127, Lit49, -4093);
      pair$Mnfold$Mnright = new ModuleMethod(var10, 128, Lit50, -4093);
      pair$Mnfold = new ModuleMethod(var10, 129, Lit51, -4093);
      reduce = new ModuleMethod(var10, 130, Lit52, 12291);
      reduce$Mnright = new ModuleMethod(var10, 131, Lit53, 12291);
      append$Mnmap = new ModuleMethod(var10, 132, Lit54, -4094);
      append$Mnmap$Ex = new ModuleMethod(var10, 133, Lit55, -4094);
      pair$Mnfor$Mneach = new ModuleMethod(var10, 134, Lit56, -4094);
      map$Ex = new ModuleMethod(var10, 135, Lit57, -4094);
      filter$Mnmap = new ModuleMethod(var10, 136, Lit58, -4094);
      filter = new ModuleMethod(var10, 137, Lit59, 8194);
      filter$Ex = new ModuleMethod(var10, 138, Lit60, 8194);
      partition = new ModuleMethod(var10, 139, Lit61, 8194);
      partition$Ex = new ModuleMethod(var10, 140, Lit62, 8194);
      remove = new ModuleMethod(var10, 141, Lit63, 8194);
      remove$Ex = new ModuleMethod(var10, 142, Lit64, 8194);
      delete = new ModuleMethod(var10, 143, Lit65, 12290);
      delete$Ex = new ModuleMethod(var10, 145, Lit66, 12290);
      delete$Mnduplicates = new ModuleMethod(var10, 147, Lit67, 8193);
      delete$Mnduplicates$Ex = new ModuleMethod(var10, 149, Lit68, 8193);
      alist$Mncons = new ModuleMethod(var10, 151, Lit69, 12291);
      alist$Mncopy = new ModuleMethod(var10, 152, Lit70, 4097);
      alist$Mndelete = new ModuleMethod(var10, 153, Lit71, 12290);
      alist$Mndelete$Ex = new ModuleMethod(var10, 155, Lit72, 12290);
      find = new ModuleMethod(var10, 157, Lit73, 8194);
      find$Mntail = new ModuleMethod(var10, 158, Lit74, 8194);
      take$Mnwhile = new ModuleMethod(var10, 159, Lit75, 8194);
      drop$Mnwhile = new ModuleMethod(var10, 160, Lit76, 8194);
      take$Mnwhile$Ex = new ModuleMethod(var10, 161, Lit77, 8194);
      span = new ModuleMethod(var10, 162, Lit78, 8194);
      span$Ex = new ModuleMethod(var10, 163, Lit79, 8194);
      break = new ModuleMethod(var10, 164, Lit80, 8194);
      break$Ex = new ModuleMethod(var10, 165, Lit81, 8194);
      any = new ModuleMethod(var10, 166, Lit82, -4094);
      every = new ModuleMethod(var10, 167, Lit83, -4094);
      $Pcevery = Macro.make(Lit84, Lit85, $instance);
      list$Mnindex = new ModuleMethod(var10, 168, Lit86, -4094);
      lset$Ls$Eq = new ModuleMethod(var10, 169, Lit87, -4095);
      lset$Eq = new ModuleMethod(var10, 170, Lit88, -4095);
      lset$Mnadjoin = new ModuleMethod(var10, 171, Lit89, -4094);
      lset$Mnunion = new ModuleMethod(var10, 172, Lit90, -4095);
      lset$Mnunion$Ex = new ModuleMethod(var10, 173, Lit91, -4095);
      lset$Mnintersection = new ModuleMethod(var10, 174, Lit92, -4094);
      lset$Mnintersection$Ex = new ModuleMethod(var10, 175, Lit93, -4094);
      lset$Mndifference = new ModuleMethod(var10, 176, Lit94, -4094);
      lset$Mndifference$Ex = new ModuleMethod(var10, 177, Lit95, -4094);
      lset$Mnxor = new ModuleMethod(var10, 178, Lit96, -4095);
      lset$Mnxor$Ex = new ModuleMethod(var10, 179, Lit97, -4095);
      lset$Mndiff$Plintersection = new ModuleMethod(var10, 180, Lit98, -4094);
      lset$Mndiff$Plintersection$Ex = new ModuleMethod(var10, 181, Lit99, -4094);
      lambda$Fn64 = new ModuleMethod(var10, 182, (Object)null, 8194);
      lambda$Fn78 = new ModuleMethod(var10, 183, (Object)null, 8194);
      $instance.run();
   }

   public srfi1() {
      ModuleInfo.register(this);
   }

   public static Pair alistCons(Object var0, Object var1, Object var2) {
      return lists.cons(lists.cons(var0, var1), var2);
   }

   public static LList alistCopy(Object var0) {
      LList var2 = LList.Empty;
      Object var1 = var0;

      Object var5;
      for(var0 = var2; var1 != LList.Empty; var0 = Pair.make(lists.cons(lists.car.apply1(var5), lists.cdr.apply1(var5)), var0)) {
         Pair var4;
         try {
            var4 = (Pair)var1;
         } catch (ClassCastException var3) {
            throw new WrongType(var3, "arg0", -2, var1);
         }

         var1 = var4.getCdr();
         var5 = var4.getCar();
      }

      return LList.reverseInPlace(var0);
   }

   public static Object alistDelete(Object var0, Object var1) {
      return alistDelete(var0, var1, Scheme.isEqual);
   }

   public static Object alistDelete(Object var0, Object var1, Object var2) {
      srfi1.frame21 var3 = new srfi1.frame21();
      var3.key = var0;
      var3.maybe$Mn$Eq = var2;
      return filter(var3.lambda$Fn18, var1);
   }

   public static Object alistDelete$Ex(Object var0, Object var1) {
      return alistDelete$Ex(var0, var1, Scheme.isEqual);
   }

   public static Object alistDelete$Ex(Object var0, Object var1, Object var2) {
      srfi1.frame22 var3 = new srfi1.frame22();
      var3.key = var0;
      var3.maybe$Mn$Eq = var2;
      return filter$Ex(var3.lambda$Fn19, var1);
   }

   public static Object any$V(Procedure var0, Object var1, Object[] var2) {
      byte var4 = 0;
      srfi1.frame26 var3 = new srfi1.frame26();
      var3.pred = var0;
      var3.lis1 = var1;
      var3.lists = LList.makeList(var2, 0);
      if(lists.isPair(var3.lists)) {
         var1 = call_with_values.callWithValues(var3.lambda$Fn22, var3.lambda$Fn23);
      } else {
         Object var6 = isNullList(var3.lis1);

         Boolean var7;
         try {
            var7 = Boolean.FALSE;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "x", -2, var6);
         }

         if(var6 != var7) {
            var4 = 1;
         }

         int var9 = var4 + 1 & 1;
         if(var9 == 0) {
            if(var9 != 0) {
               return Boolean.TRUE;
            }

            return Boolean.FALSE;
         }

         var1 = lists.car.apply1(var3.lis1);
         var6 = lists.cdr.apply1(var3.lis1);

         while(true) {
            if(isNullList(var6) != Boolean.FALSE) {
               return var3.pred.apply1(var1);
            }

            Object var8 = var3.pred.apply1(var1);
            var1 = var8;
            if(var8 != Boolean.FALSE) {
               break;
            }

            var1 = lists.car.apply1(var6);
            var6 = lists.cdr.apply1(var6);
         }
      }

      return var1;
   }

   public static Object append$Ex$V(Object[] var0) {
      Object var8 = LList.makeList(var0, 0);

      Object var1;
      Object var2;
      for(var2 = LList.Empty; lists.isPair(var8); var8 = var1) {
         var2 = lists.car.apply1(var8);
         var1 = lists.cdr.apply1(var8);
         if(lists.isPair(var2)) {
            Pair var9;
            try {
               var9 = (Pair)var2;
            } catch (ClassCastException var7) {
               throw new WrongType(var7, "last-pair", 0, var2);
            }

            var8 = lastPair(var9);

            while(lists.isPair(var1)) {
               Object var3 = lists.car.apply1(var1);
               var1 = lists.cdr.apply1(var1);

               Pair var4;
               try {
                  var4 = (Pair)var8;
               } catch (ClassCastException var6) {
                  throw new WrongType(var6, "set-cdr!", 1, var8);
               }

               lists.setCdr$Ex(var4, var3);
               if(lists.isPair(var3)) {
                  try {
                     var9 = (Pair)var3;
                  } catch (ClassCastException var5) {
                     throw new WrongType(var5, "last-pair", 0, var3);
                  }

                  var8 = lastPair(var9);
               }
            }

            return var2;
         }
      }

      return var2;
   }

   public static Object appendMap$Ex$V(Object var0, Object var1, Object[] var2) {
      LList var7 = LList.makeList(var2, 0);
      if(lists.isPair(var7)) {
         return Scheme.apply.apply2(append$Ex, Scheme.apply.apply4(Scheme.map, var0, var1, var7));
      } else {
         Apply var3 = Scheme.apply;
         ModuleMethod var4 = append$Ex;

         Pair var5;
         Object var8;
         for(var8 = LList.Empty; var1 != LList.Empty; var8 = Pair.make(Scheme.applyToArgs.apply2(var0, var5.getCar()), var8)) {
            try {
               var5 = (Pair)var1;
            } catch (ClassCastException var6) {
               throw new WrongType(var6, "arg0", -2, var1);
            }

            var1 = var5.getCdr();
         }

         return var3.apply2(var4, LList.reverseInPlace(var8));
      }
   }

   public static Object appendMap$V(Object var0, Object var1, Object[] var2) {
      LList var7 = LList.makeList(var2, 0);
      if(lists.isPair(var7)) {
         return Scheme.apply.apply2(append.append, Scheme.apply.apply4(Scheme.map, var0, var1, var7));
      } else {
         Apply var3 = Scheme.apply;
         append var4 = append.append;

         Pair var5;
         Object var8;
         for(var8 = LList.Empty; var1 != LList.Empty; var8 = Pair.make(Scheme.applyToArgs.apply2(var0, var5.getCar()), var8)) {
            try {
               var5 = (Pair)var1;
            } catch (ClassCastException var6) {
               throw new WrongType(var6, "arg0", -2, var1);
            }

            var1 = var5.getCdr();
         }

         return var3.apply2(var4, LList.reverseInPlace(var8));
      }
   }

   public static Object appendReverse(Object var0, Object var1) {
      while(isNullList(var0) == Boolean.FALSE) {
         Object var2 = lists.cdr.apply1(var0);
         var1 = lists.cons(lists.car.apply1(var0), var1);
         var0 = var2;
      }

      return var1;
   }

   public static Object appendReverse$Ex(Object var0, Object var1) {
      while(isNullList(var0) == Boolean.FALSE) {
         Object var2 = lists.cdr.apply1(var0);

         Pair var3;
         try {
            var3 = (Pair)var0;
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "set-cdr!", 1, var0);
         }

         lists.setCdr$Ex(var3, var1);
         var1 = var0;
         var0 = var2;
      }

      return var1;
   }

   public static Object break(Object var0, Object var1) {
      srfi1.frame24 var2 = new srfi1.frame24();
      var2.pred = var0;
      return span(var2.lambda$Fn20, var1);
   }

   public static Object break$Ex(Object var0, Object var1) {
      srfi1.frame25 var2 = new srfi1.frame25();
      var2.pred = var0;
      return span$Ex(var2.lambda$Fn21, var1);
   }

   public static Object car$PlCdr(Object var0) {
      return misc.values(new Object[]{lists.car.apply1(var0), lists.cdr.apply1(var0)});
   }

   public static Pair circularList$V(Object var0, Object[] var1) {
      Pair var4 = lists.cons(var0, LList.makeList(var1, 0));
      var0 = lastPair(var4);

      Pair var2;
      try {
         var2 = (Pair)var0;
      } catch (ClassCastException var3) {
         throw new WrongType(var3, "set-cdr!", 1, var0);
      }

      lists.setCdr$Ex(var2, var4);
      return var4;
   }

   public static Object concatenate(Object var0) {
      return reduceRight(append.append, LList.Empty, var0);
   }

   public static Object concatenate$Ex(Object var0) {
      return reduceRight(append$Ex, LList.Empty, var0);
   }

   public static Object cons$St(Object ... var0) {
      return LList.consX(var0);
   }

   public static Object count$V(Procedure var0, Object var1, Object[] var2) {
      Object var3 = LList.makeList(var2, 0);
      Object var7;
      Object var8;
      if(lists.isPair(var3)) {
         IntNum var4 = Lit0;
         var7 = var1;
         var1 = var4;

         while(true) {
            if(isNullList(var7) != Boolean.FALSE) {
               var3 = var1;
               break;
            }

            var3 = $PcCars$PlCdrs$SlPair(var3);
            Object var6 = lists.car.apply1(var3);
            var8 = lists.cdr.apply1(var3);
            var3 = var1;
            if(lists.isNull(var6)) {
               break;
            }

            Object var5 = lists.cdr.apply1(var7);
            var3 = var1;
            if(Scheme.apply.apply3(var0, lists.car.apply1(var7), var6) != Boolean.FALSE) {
               var3 = AddOp.$Pl.apply2(var1, Lit1);
            }

            var1 = var3;
            var3 = var8;
            var7 = var5;
         }
      } else {
         IntNum var9 = Lit0;
         var7 = var1;
         var1 = var9;

         while(true) {
            var3 = var1;
            if(isNullList(var7) != Boolean.FALSE) {
               break;
            }

            var8 = lists.cdr.apply1(var7);
            var3 = var1;
            if(var0.apply1(lists.car.apply1(var7)) != Boolean.FALSE) {
               var3 = AddOp.$Pl.apply2(var1, Lit1);
            }

            var7 = var8;
            var1 = var3;
         }
      }

      return var3;
   }

   public static Object delete(Object var0, Object var1) {
      return delete(var0, var1, Scheme.isEqual);
   }

   public static Object delete(Object var0, Object var1, Object var2) {
      srfi1.frame17 var3 = new srfi1.frame17();
      var3.x = var0;
      var3.maybe$Mn$Eq = var2;
      return filter(var3.lambda$Fn16, var1);
   }

   public static Object delete$Ex(Object var0, Object var1) {
      return delete$Ex(var0, var1, Scheme.isEqual);
   }

   public static Object delete$Ex(Object var0, Object var1, Object var2) {
      srfi1.frame18 var3 = new srfi1.frame18();
      var3.x = var0;
      var3.maybe$Mn$Eq = var2;
      return filter$Ex(var3.lambda$Fn17, var1);
   }

   public static Object deleteDuplicates(Object var0) {
      return deleteDuplicates(var0, Scheme.isEqual);
   }

   public static Object deleteDuplicates(Object var0, Procedure var1) {
      srfi1.frame19 var2 = new srfi1.frame19();
      var2.maybe$Mn$Eq = var1;
      return var2.lambda30recur(var0);
   }

   public static Object deleteDuplicates$Ex(Object var0) {
      return deleteDuplicates$Ex(var0, Scheme.isEqual);
   }

   public static Object deleteDuplicates$Ex(Object var0, Procedure var1) {
      srfi1.frame20 var2 = new srfi1.frame20();
      var2.maybe$Mn$Eq = var1;
      return var2.lambda31recur(var0);
   }

   public static Object drop(Object var0, IntNum var1) {
      while(true) {
         Number var2;
         try {
            var2 = (Number)var1;
         } catch (ClassCastException var3) {
            throw new WrongType(var3, "zero?", 1, var1);
         }

         if(numbers.isZero(var2)) {
            return var0;
         }

         var0 = lists.cdr.apply1(var0);
         var1 = AddOp.$Mn.apply2(var1, Lit1);
      }
   }

   public static Object dropRight(Object var0, IntNum var1) {
      return lambda1recur(var0, drop(var0, var1));
   }

   public static Object dropRight$Ex(Object var0, IntNum var1) {
      Object var4 = drop(var0, var1);
      if(!lists.isPair(var4)) {
         return LList.Empty;
      } else {
         Object var2 = lists.cdr.apply1(var4);

         for(var4 = var0; lists.isPair(var2); var2 = lists.cdr.apply1(var2)) {
            var4 = lists.cdr.apply1(var4);
         }

         Pair var5;
         try {
            var5 = (Pair)var4;
         } catch (ClassCastException var3) {
            throw new WrongType(var3, "set-cdr!", 1, var4);
         }

         lists.setCdr$Ex(var5, LList.Empty);
         return var0;
      }
   }

   public static Object dropWhile(Procedure var0, Object var1) {
      while(true) {
         Object var2;
         if(isNullList(var1) != Boolean.FALSE) {
            var2 = LList.Empty;
         } else {
            var2 = var1;
            if(var0.apply1(lists.car.apply1(var1)) != Boolean.FALSE) {
               var1 = lists.cdr.apply1(var1);
               continue;
            }
         }

         return var2;
      }
   }

   public static Object eighth(Object var0) {
      return lists.cadddr.apply1(lists.cddddr.apply1(var0));
   }

   public static Object every$V(Procedure var0, Object var1, Object[] var2) {
      srfi1.frame27 var3 = new srfi1.frame27();
      var3.pred = var0;
      var3.lis1 = var1;
      var3.lists = LList.makeList(var2, 0);
      Object var4;
      if(lists.isPair(var3.lists)) {
         var4 = call_with_values.callWithValues(var3.lambda$Fn24, var3.lambda$Fn25);
      } else {
         var1 = isNullList(var3.lis1);
         var4 = var1;
         if(var1 == Boolean.FALSE) {
            var4 = lists.car.apply1(var3.lis1);
            var1 = lists.cdr.apply1(var3.lis1);

            while(true) {
               if(isNullList(var1) != Boolean.FALSE) {
                  return var3.pred.apply1(var4);
               }

               Object var5 = var3.pred.apply1(var4);
               var4 = var5;
               if(var5 == Boolean.FALSE) {
                  break;
               }

               var4 = lists.car.apply1(var1);
               var1 = lists.cdr.apply1(var1);
            }
         }
      }

      return var4;
   }

   public static Object fifth(Object var0) {
      return lists.car.apply1(lists.cddddr.apply1(var0));
   }

   public static Object filter(Procedure var0, Object var1) {
      Object var2 = LList.Empty;

      while(isNullList(var1) == Boolean.FALSE) {
         Object var3 = lists.car.apply1(var1);
         var1 = lists.cdr.apply1(var1);
         if(var0.apply1(var3) != Boolean.FALSE) {
            var2 = lists.cons(var3, var2);
         }
      }

      LList var5;
      try {
         var5 = (LList)var2;
      } catch (ClassCastException var4) {
         throw new WrongType(var4, "reverse!", 1, var2);
      }

      return lists.reverse$Ex(var5);
   }

   public static Object filter$Ex(Procedure var0, Object var1) {
      Object var2;
      for(var2 = var1; isNullList(var2) == Boolean.FALSE; var2 = lists.cdr.apply1(var2)) {
         if(var0.apply1(lists.car.apply1(var2)) != Boolean.FALSE) {
            var1 = lists.cdr.apply1(var2);
            Object var3 = var2;

            label41:
            while(lists.isPair(var1)) {
               Object var8;
               if(var0.apply1(lists.car.apply1(var1)) != Boolean.FALSE) {
                  var8 = lists.cdr.apply1(var1);
                  var3 = var1;
                  var1 = var8;
               } else {
                  for(var1 = lists.cdr.apply1(var1); lists.isPair(var1); var1 = lists.cdr.apply1(var1)) {
                     if(var0.apply1(lists.car.apply1(var1)) != Boolean.FALSE) {
                        Pair var4;
                        try {
                           var4 = (Pair)var3;
                        } catch (ClassCastException var6) {
                           throw new WrongType(var6, "set-cdr!", 1, var3);
                        }

                        lists.setCdr$Ex(var4, var1);
                        var8 = lists.cdr.apply1(var1);
                        var3 = var1;
                        var1 = var8;
                        continue label41;
                     }
                  }

                  Pair var7;
                  try {
                     var7 = (Pair)var3;
                  } catch (ClassCastException var5) {
                     throw new WrongType(var5, "set-cdr!", 1, var3);
                  }

                  lists.setCdr$Ex(var7, var1);
                  return var2;
               }
            }

            return var2;
         }
      }

      return var2;
   }

   public static Object filterMap$V(Procedure var0, Object var1, Object[] var2) {
      srfi1.frame13 var3 = new srfi1.frame13();
      var3.f = var0;
      LList var5 = LList.makeList(var2, 0);
      if(lists.isPair(var5)) {
         return var3.lambda23recur(lists.cons(var1, var5), LList.Empty);
      } else {
         LList var7 = LList.Empty;
         Object var6 = var1;
         var1 = var7;

         while(isNullList(var6) == Boolean.FALSE) {
            Object var8 = var3.f.apply1(lists.car.apply1(var6));
            var6 = lists.cdr.apply1(var6);
            if(var8 != Boolean.FALSE) {
               var1 = lists.cons(var8, var1);
            }
         }

         try {
            var5 = (LList)var1;
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "reverse!", 1, var1);
         }

         return lists.reverse$Ex(var5);
      }
   }

   public static Object find(Object var0, Object var1) {
      Procedure var2;
      try {
         var2 = (Procedure)var0;
      } catch (ClassCastException var3) {
         throw new WrongType(var3, "find-tail", 0, var0);
      }

      var0 = findTail(var2, var1);
      return var0 != Boolean.FALSE?lists.car.apply1(var0):Boolean.FALSE;
   }

   public static Object findTail(Procedure var0, Object var1) {
      while(true) {
         Object var2 = isNullList(var1);

         Boolean var3;
         try {
            var3 = Boolean.FALSE;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "x", -2, var2);
         }

         byte var4;
         if(var2 != var3) {
            var4 = 1;
         } else {
            var4 = 0;
         }

         int var6 = var4 + 1 & 1;
         if(var6 == 0) {
            if(var6 != 0) {
               return Boolean.TRUE;
            }

            return Boolean.FALSE;
         }

         if(var0.apply1(lists.car.apply1(var1)) != Boolean.FALSE) {
            return var1;
         }

         var1 = lists.cdr.apply1(var1);
      }
   }

   public static Object fold$V(Procedure var0, Object var1, Object var2, Object[] var3) {
      srfi1.frame7 var4 = new srfi1.frame7();
      var4.kons = var0;
      LList var5 = LList.makeList(var3, 0);
      if(lists.isPair(var5)) {
         var2 = var4.lambda14lp(lists.cons(var2, var5), var1);
      } else {
         Object var6 = var2;

         while(true) {
            var2 = var1;
            if(isNullList(var6) != Boolean.FALSE) {
               break;
            }

            var2 = lists.cdr.apply1(var6);
            var1 = var4.kons.apply2(lists.car.apply1(var6), var1);
            var6 = var2;
         }
      }

      return var2;
   }

   public static Object foldRight$V(Procedure var0, Object var1, Object var2, Object[] var3) {
      srfi1.frame9 var4 = new srfi1.frame9();
      var4.kons = var0;
      var4.knil = var1;
      LList var5 = LList.makeList(var3, 0);
      return lists.isPair(var5)?var4.lambda17recur(lists.cons(var2, var5)):var4.lambda18recur(var2);
   }

   public static Object iota(IntNum var0) {
      return iota(var0, Lit0, Lit1);
   }

   public static Object iota(IntNum var0, Numeric var1) {
      return iota(var0, var1, Lit1);
   }

   public static Object iota(IntNum var0, Numeric var1, Numeric var2) {
      if(IntNum.compare(var0, 0L) < 0) {
         misc.error$V("Negative step count", new Object[]{iota, var0});
      }

      Object var3 = AddOp.$Pl.apply2(var1, MultiplyOp.$St.apply2(IntNum.add((IntNum)var0, -1), var2));

      Object var7;
      try {
         var7 = (Numeric)var3;
      } catch (ClassCastException var5) {
         throw new WrongType(var5, "last-val", -2, var3);
      }

      LList var4 = LList.Empty;
      var3 = var0;

      Object var6;
      Object var8;
      for(var6 = var4; Scheme.numLEq.apply2(var3, Lit0) == Boolean.FALSE; var7 = var8) {
         var3 = AddOp.$Mn.apply2(var3, Lit1);
         var8 = AddOp.$Mn.apply2(var7, var2);
         var6 = lists.cons(var7, var6);
      }

      return var6;
   }

   public static Object isCircularList(Object var0) {
      Object var2 = var0;
      var0 = var0;

      boolean var3;
      do {
         boolean var4 = lists.isPair(var2);
         if(!var4) {
            if(var4) {
               return Boolean.TRUE;
            }

            return Boolean.FALSE;
         }

         Object var1 = lists.cdr.apply1(var2);
         var4 = lists.isPair(var1);
         if(!var4) {
            if(var4) {
               return Boolean.TRUE;
            }

            return Boolean.FALSE;
         }

         var2 = lists.cdr.apply1(var1);
         var0 = lists.cdr.apply1(var0);
         if(var2 == var0) {
            var3 = true;
         } else {
            var3 = false;
         }
      } while(!var3);

      if(var3) {
         return Boolean.TRUE;
      } else {
         return Boolean.FALSE;
      }
   }

   public static Object isDottedList(Object var0) {
      Object var2 = var0;
      var0 = var0;

      int var4;
      do {
         if(!lists.isPair(var2)) {
            if(lists.isNull(var2)) {
               return Boolean.FALSE;
            }

            return Boolean.TRUE;
         }

         Object var1 = lists.cdr.apply1(var2);
         if(!lists.isPair(var1)) {
            if(lists.isNull(var1)) {
               return Boolean.FALSE;
            }

            return Boolean.TRUE;
         }

         var2 = lists.cdr.apply1(var1);
         var0 = lists.cdr.apply1(var0);
         byte var3;
         if(var2 == var0) {
            var3 = 1;
         } else {
            var3 = 0;
         }

         var4 = var3 + 1 & 1;
      } while(var4 != 0);

      if(var4 != 0) {
         return Boolean.TRUE;
      } else {
         return Boolean.FALSE;
      }
   }

   public static boolean isNotPair(Object var0) {
      throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:783)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:662)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:722)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:706)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e2expr(TypeTransformer.java:632)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:706)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e2expr(TypeTransformer.java:632)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:706)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:813)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:843)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n\tat sun.reflect.NativeMethodAccessorImpl.invoke(Unknown Source)\r\n\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source)\r\n\tat java.lang.reflect.Method.invoke(Unknown Source)\r\n\tat com.exe4j.runtime.LauncherEngine.launch(Unknown Source)\r\n\tat com.exe4j.runtime.WinLauncher.main(Unknown Source)\r\n");
   }

   public static Object isNullList(Object var0) {
      return var0 instanceof Pair?Boolean.FALSE:(var0 == LList.Empty?Boolean.TRUE:misc.error$V("null-list?: argument out of domain", new Object[]{var0}));
   }

   public static Object isProperList(Object var0) {
      Object var2 = var0;
      var0 = var0;

      int var4;
      do {
         if(!lists.isPair(var2)) {
            if(lists.isNull(var2)) {
               return Boolean.TRUE;
            }

            return Boolean.FALSE;
         }

         Object var1 = lists.cdr.apply1(var2);
         if(!lists.isPair(var1)) {
            if(lists.isNull(var1)) {
               return Boolean.TRUE;
            }

            return Boolean.FALSE;
         }

         var2 = lists.cdr.apply1(var1);
         var0 = lists.cdr.apply1(var0);
         byte var3;
         if(var2 == var0) {
            var3 = 1;
         } else {
            var3 = 0;
         }

         var4 = var3 + 1 & 1;
      } while(var4 != 0);

      if(var4 != 0) {
         return Boolean.TRUE;
      } else {
         return Boolean.FALSE;
      }
   }

   public static Object lambda1recur(Object var0, Object var1) {
      return lists.isPair(var1)?lists.cons(lists.car.apply1(var0), lambda1recur(lists.cdr.apply1(var0), lists.cdr.apply1(var1))):LList.Empty;
   }

   public static Object last(Object var0) {
      GenericProc var1 = lists.car;

      Pair var2;
      try {
         var2 = (Pair)var0;
      } catch (ClassCastException var3) {
         throw new WrongType(var3, "last-pair", 0, var0);
      }

      return var1.apply1(lastPair(var2));
   }

   public static Object lastPair(Pair var0) {
      while(true) {
         Object var1 = lists.cdr.apply1(var0);
         if(!lists.isPair(var1)) {
            return var0;
         }

         var0 = var1;
      }
   }

   public static Object length$Pl(Object var0) {
      Object var1 = Lit0;
      Object var3 = var0;
      var0 = var0;

      int var5;
      do {
         if(!lists.isPair(var3)) {
            return var1;
         }

         Object var2 = lists.cdr.apply1(var3);
         var1 = AddOp.$Pl.apply2(var1, Lit1);
         if(!lists.isPair(var2)) {
            return var1;
         }

         var3 = lists.cdr.apply1(var2);
         var0 = lists.cdr.apply1(var0);
         var1 = AddOp.$Pl.apply2(var1, Lit1);
         byte var4;
         if(var3 == var0) {
            var4 = 1;
         } else {
            var4 = 0;
         }

         var5 = var4 + 1 & 1;
      } while(var5 != 0);

      if(var5 != 0) {
         return Boolean.TRUE;
      } else {
         return Boolean.FALSE;
      }
   }

   public static Object list$Eq$V(Object var0, Object[] var1) {
      LList var2 = LList.makeList(var1, 0);
      boolean var7 = lists.isNull(var2);
      if(var7) {
         return var7?Boolean.TRUE:Boolean.FALSE;
      } else {
         Object var9 = lists.car.apply1(var2);
         Object var10 = lists.cdr.apply1(var2);

         while(true) {
            var7 = lists.isNull(var10);
            if(var7) {
               if(var7) {
                  return Boolean.TRUE;
               }

               return Boolean.FALSE;
            }

            Object var3 = lists.car.apply1(var10);
            Object var4 = lists.cdr.apply1(var10);
            if(var9 == var3) {
               var9 = var3;
               var10 = var4;
            } else {
               for(var10 = var3; isNullList(var9) == Boolean.FALSE; var10 = lists.cdr.apply1(var10)) {
                  var3 = isNullList(var10);

                  Boolean var5;
                  try {
                     var5 = Boolean.FALSE;
                  } catch (ClassCastException var8) {
                     throw new WrongType(var8, "x", -2, var3);
                  }

                  byte var6;
                  if(var3 != var5) {
                     var6 = 1;
                  } else {
                     var6 = 0;
                  }

                  int var11 = var6 + 1 & 1;
                  if(var11 == 0) {
                     if(var11 != 0) {
                        return Boolean.TRUE;
                     }

                     return Boolean.FALSE;
                  }

                  var3 = Scheme.applyToArgs.apply3(var0, lists.car.apply1(var9), lists.car.apply1(var10));
                  if(var3 == Boolean.FALSE) {
                     return var3;
                  }

                  var9 = lists.cdr.apply1(var9);
               }

               var9 = isNullList(var10);
               if(var9 == Boolean.FALSE) {
                  return var9;
               }

               var9 = var10;
               var10 = var4;
            }
         }
      }
   }

   public static LList listCopy(LList var0) {
      Object var2 = LList.Empty;

      for(Object var1 = LList.Empty; lists.isPair(var0); var0 = (LList)lists.cdr.apply1(var0)) {
         Pair var3 = lists.cons(lists.car.apply1(var0), LList.Empty);
         if(var1 == LList.Empty) {
            var2 = var3;
         } else {
            Pair var4;
            try {
               var4 = (Pair)var1;
            } catch (ClassCastException var5) {
               throw new WrongType(var5, "set-cdr!", 1, var1);
            }

            lists.setCdr$Ex(var4, var3);
         }

         var1 = var3;
      }

      return (LList)var2;
   }

   public static Object listIndex$V(Procedure var0, Object var1, Object[] var2) {
      srfi1.frame30 var3 = new srfi1.frame30();
      var3.pred = var0;
      LList var7 = LList.makeList(var2, 0);
      Object var9;
      if(lists.isPair(var7)) {
         var9 = var3.lambda44lp(lists.cons(var1, var7), Lit0);
      } else {
         Object var8 = Lit0;

         while(true) {
            var9 = isNullList(var1);

            Boolean var4;
            try {
               var4 = Boolean.FALSE;
            } catch (ClassCastException var6) {
               throw new WrongType(var6, "x", -2, var9);
            }

            byte var5;
            if(var9 != var4) {
               var5 = 1;
            } else {
               var5 = 0;
            }

            int var10 = var5 + 1 & 1;
            if(var10 == 0) {
               if(var10 != 0) {
                  return Boolean.TRUE;
               }

               return Boolean.FALSE;
            }

            var9 = var8;
            if(var3.pred.apply1(lists.car.apply1(var1)) != Boolean.FALSE) {
               break;
            }

            var1 = lists.cdr.apply1(var1);
            var8 = AddOp.$Pl.apply2(var8, Lit1);
         }
      }

      return var9;
   }

   public static Object listTabulate(Object var0, Procedure var1) {
      throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:783)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:662)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:722)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:706)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e2expr(TypeTransformer.java:632)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:706)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e2expr(TypeTransformer.java:632)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:706)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s2stmt(TypeTransformer.java:823)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:846)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n\tat sun.reflect.NativeMethodAccessorImpl.invoke(Unknown Source)\r\n\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source)\r\n\tat java.lang.reflect.Method.invoke(Unknown Source)\r\n\tat com.exe4j.runtime.LauncherEngine.launch(Unknown Source)\r\n\tat com.exe4j.runtime.WinLauncher.main(Unknown Source)\r\n");
   }

   public static Object lset$Eq$V(Procedure var0, Object[] var1) {
      throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:783)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:662)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:722)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:706)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e2expr(TypeTransformer.java:632)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:706)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e2expr(TypeTransformer.java:632)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:706)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s2stmt(TypeTransformer.java:823)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:846)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n\tat sun.reflect.NativeMethodAccessorImpl.invoke(Unknown Source)\r\n\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source)\r\n\tat java.lang.reflect.Method.invoke(Unknown Source)\r\n\tat com.exe4j.runtime.LauncherEngine.launch(Unknown Source)\r\n\tat com.exe4j.runtime.WinLauncher.main(Unknown Source)\r\n");
   }

   public static Object lset$Ls$Eq$V(Procedure var0, Object[] var1) {
      throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:783)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:662)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:722)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:706)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e2expr(TypeTransformer.java:632)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:706)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e2expr(TypeTransformer.java:632)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:706)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s2stmt(TypeTransformer.java:823)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:846)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n\tat sun.reflect.NativeMethodAccessorImpl.invoke(Unknown Source)\r\n\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source)\r\n\tat java.lang.reflect.Method.invoke(Unknown Source)\r\n\tat com.exe4j.runtime.LauncherEngine.launch(Unknown Source)\r\n\tat com.exe4j.runtime.WinLauncher.main(Unknown Source)\r\n");
   }

   public static Object lsetAdjoin$V(Procedure var0, Object var1, Object[] var2) {
      srfi1.frame32 var3 = new srfi1.frame32();
      var3.$Eq = var0;
      LList var4 = LList.makeList(var2, 0);
      return fold$V(var3.lambda$Fn30, var1, var4, new Object[0]);
   }

   public static Object lsetDiff$PlIntersection$Ex$V(Procedure var0, Object var1, Object[] var2) {
      srfi1.frame53 var3 = new srfi1.frame53();
      var3.$Eq = var0;
      var3.lists = LList.makeList(var2, 0);
      return every$V(null$Mnlist$Qu, var3.lists, new Object[0]) != Boolean.FALSE?misc.values(new Object[]{var1, LList.Empty}):(lists.memq(var1, var3.lists) != Boolean.FALSE?misc.values(new Object[]{LList.Empty, var1}):partition$Ex(var3.lambda$Fn55, var1));
   }

   public static Object lsetDiff$PlIntersection$V(Procedure var0, Object var1, Object[] var2) {
      srfi1.frame51 var3 = new srfi1.frame51();
      var3.$Eq = var0;
      var3.lists = LList.makeList(var2, 0);
      return every$V(null$Mnlist$Qu, var3.lists, new Object[0]) != Boolean.FALSE?misc.values(new Object[]{var1, LList.Empty}):(lists.memq(var1, var3.lists) != Boolean.FALSE?misc.values(new Object[]{LList.Empty, var1}):partition(var3.lambda$Fn53, var1));
   }

   public static Object lsetDifference$Ex$V(Procedure var0, Object var1, Object[] var2) {
      srfi1.frame43 var3 = new srfi1.frame43();
      var3.$Eq = var0;
      LList var4 = LList.makeList(var2, 0);
      var3.lists = filter(lists.pair$Qu, var4);
      return lists.isNull(var3.lists)?var1:(lists.memq(var1, var3.lists) != Boolean.FALSE?LList.Empty:filter$Ex(var3.lambda$Fn43, var1));
   }

   public static Object lsetDifference$V(Procedure var0, Object var1, Object[] var2) {
      srfi1.frame41 var3 = new srfi1.frame41();
      var3.$Eq = var0;
      LList var4 = LList.makeList(var2, 0);
      var3.lists = filter(lists.pair$Qu, var4);
      return lists.isNull(var3.lists)?var1:(lists.memq(var1, var3.lists) != Boolean.FALSE?LList.Empty:filter(var3.lambda$Fn41, var1));
   }

   public static Object lsetIntersection$Ex$V(Procedure var0, Object var1, Object[] var2) {
      srfi1.frame39 var3 = new srfi1.frame39();
      var3.$Eq = var0;
      var3.lists = delete(var1, LList.makeList(var2, 0), Scheme.isEq);
      Object var4;
      if(any$V(null$Mnlist$Qu, var3.lists, new Object[0]) != Boolean.FALSE) {
         var4 = LList.Empty;
      } else {
         var4 = var1;
         if(!lists.isNull(var3.lists)) {
            return filter$Ex(var3.lambda$Fn39, var1);
         }
      }

      return var4;
   }

   public static Object lsetIntersection$V(Procedure var0, Object var1, Object[] var2) {
      srfi1.frame37 var3 = new srfi1.frame37();
      var3.$Eq = var0;
      var3.lists = delete(var1, LList.makeList(var2, 0), Scheme.isEq);
      Object var4;
      if(any$V(null$Mnlist$Qu, var3.lists, new Object[0]) != Boolean.FALSE) {
         var4 = LList.Empty;
      } else {
         var4 = var1;
         if(!lists.isNull(var3.lists)) {
            return filter(var3.lambda$Fn37, var1);
         }
      }

      return var4;
   }

   public static Object lsetUnion$Ex$V(Procedure var0, Object[] var1) {
      srfi1.frame35 var2 = new srfi1.frame35();
      var2.$Eq = var0;
      LList var3 = LList.makeList(var1, 0);
      return reduce(var2.lambda$Fn34, LList.Empty, var3);
   }

   public static Object lsetUnion$V(Procedure var0, Object[] var1) {
      srfi1.frame33 var2 = new srfi1.frame33();
      var2.$Eq = var0;
      LList var3 = LList.makeList(var1, 0);
      return reduce(var2.lambda$Fn31, LList.Empty, var3);
   }

   public static Object lsetXor$Ex$V(Procedure var0, Object[] var1) {
      srfi1.frame48 var2 = new srfi1.frame48();
      var2.$Eq = var0;
      LList var3 = LList.makeList(var1, 0);
      return reduce(var2.lambda$Fn49, LList.Empty, var3);
   }

   public static Object lsetXor$V(Procedure var0, Object[] var1) {
      srfi1.frame45 var2 = new srfi1.frame45();
      var2.$Eq = var0;
      LList var3 = LList.makeList(var1, 0);
      return reduce(var2.lambda$Fn45, LList.Empty, var3);
   }

   public static Object makeList$V(Object var0, Object[] var1) {
      throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:783)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:662)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:722)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:706)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e2expr(TypeTransformer.java:632)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:706)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e2expr(TypeTransformer.java:632)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:706)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s2stmt(TypeTransformer.java:823)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:846)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n\tat sun.reflect.NativeMethodAccessorImpl.invoke(Unknown Source)\r\n\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source)\r\n\tat java.lang.reflect.Method.invoke(Unknown Source)\r\n\tat com.exe4j.runtime.LauncherEngine.launch(Unknown Source)\r\n\tat com.exe4j.runtime.WinLauncher.main(Unknown Source)\r\n");
   }

   public static Object map$Ex$V(Procedure var0, Object var1, Object[] var2) {
      srfi1.frame12 var3 = new srfi1.frame12();
      var3.f = var0;
      Object var8 = LList.makeList(var2, 0);
      if(lists.isPair(var8)) {
         for(Object var7 = var1; isNullList(var7) == Boolean.FALSE; var7 = lists.cdr.apply1(var7)) {
            var8 = $PcCars$PlCdrs$SlNoTest$SlPair(var8);
            Object var4 = lists.car.apply1(var8);
            var8 = lists.cdr.apply1(var8);

            Pair var5;
            try {
               var5 = (Pair)var7;
            } catch (ClassCastException var6) {
               throw new WrongType(var6, "set-car!", 1, var7);
            }

            lists.setCar$Ex(var5, Scheme.apply.apply3(var3.f, lists.car.apply1(var7), var4));
         }
      } else {
         pairForEach$V(var3.lambda$Fn11, var1, new Object[0]);
      }

      return var1;
   }

   public static Object ninth(Object var0) {
      return lists.car.apply1(lists.cddddr.apply1(lists.cddddr.apply1(var0)));
   }

   public static Object pairFold$V(Procedure var0, Object var1, Object var2, Object[] var3) {
      LList var4 = LList.makeList(var3, 0);
      Object var5;
      if(lists.isPair(var4)) {
         var2 = lists.cons(var2, var4);

         while(true) {
            var5 = $PcCdrs(var2);
            if(lists.isNull(var5)) {
               var5 = var1;
               break;
            }

            var1 = Scheme.apply.apply2(var0, append$Ex$V(new Object[]{var2, LList.list1(var1)}));
            var2 = var5;
         }
      } else {
         while(true) {
            var5 = var1;
            if(isNullList(var2) != Boolean.FALSE) {
               break;
            }

            var5 = lists.cdr.apply1(var2);
            var1 = var0.apply2(var2, var1);
            var2 = var5;
         }
      }

      return var5;
   }

   public static Object pairFoldRight$V(Procedure var0, Object var1, Object var2, Object[] var3) {
      srfi1.frame10 var4 = new srfi1.frame10();
      var4.f = var0;
      var4.zero = var1;
      LList var5 = LList.makeList(var3, 0);
      return lists.isPair(var5)?var4.lambda19recur(lists.cons(var2, var5)):var4.lambda20recur(var2);
   }

   public static Object pairForEach$V(Procedure var0, Object var1, Object[] var2) {
      LList var3 = LList.makeList(var2, 0);
      Object var4;
      if(lists.isPair(var3)) {
         var1 = lists.cons(var1, var3);

         while(true) {
            var4 = $PcCdrs(var1);
            if(!lists.isPair(var4)) {
               return Values.empty;
            }

            Scheme.apply.apply2(var0, var1);
            var1 = var4;
         }
      } else {
         while(isNullList(var1) == Boolean.FALSE) {
            var4 = lists.cdr.apply1(var1);
            var0.apply1(var1);
            var1 = var4;
         }

         return Values.empty;
      }
   }

   public static Object partition(Procedure var0, Object var1) {
      Object var3 = LList.Empty;
      Object var2 = LList.Empty;

      while(isNullList(var1) == Boolean.FALSE) {
         Object var4 = lists.car.apply1(var1);
         var1 = lists.cdr.apply1(var1);
         if(var0.apply1(var4) != Boolean.FALSE) {
            var3 = lists.cons(var4, var3);
         } else {
            var2 = lists.cons(var4, var2);
         }
      }

      LList var7;
      try {
         var7 = (LList)var3;
      } catch (ClassCastException var6) {
         throw new WrongType(var6, "reverse!", 1, var3);
      }

      var7 = lists.reverse$Ex(var7);

      LList var8;
      try {
         var8 = (LList)var2;
      } catch (ClassCastException var5) {
         throw new WrongType(var5, "reverse!", 1, var2);
      }

      return misc.values(new Object[]{var7, lists.reverse$Ex(var8)});
   }

   public static Object partition$Ex(Procedure var0, Object var1) {
      Pair var5 = lists.cons(Lit2, LList.Empty);
      Pair var6 = lists.cons(Lit2, LList.Empty);
      Object var3 = var5;
      Object var2 = var6;

      while(!isNotPair(var1)) {
         Pair var4;
         Object var12;
         if(var0.apply1(lists.car.apply1(var1)) != Boolean.FALSE) {
            try {
               var4 = (Pair)var3;
            } catch (ClassCastException var9) {
               throw new WrongType(var9, "set-cdr!", 1, var3);
            }

            lists.setCdr$Ex(var4, var1);
            var12 = lists.cdr.apply1(var1);
            var3 = var1;
            var1 = var12;
         } else {
            try {
               var4 = (Pair)var2;
            } catch (ClassCastException var10) {
               throw new WrongType(var10, "set-cdr!", 1, var2);
            }

            lists.setCdr$Ex(var4, var1);
            var12 = lists.cdr.apply1(var1);
            var2 = var1;
            var1 = var12;
         }
      }

      Pair var11;
      try {
         var11 = (Pair)var3;
      } catch (ClassCastException var8) {
         throw new WrongType(var8, "set-cdr!", 1, var3);
      }

      lists.setCdr$Ex(var11, LList.Empty);

      try {
         var11 = (Pair)var2;
      } catch (ClassCastException var7) {
         throw new WrongType(var7, "set-cdr!", 1, var2);
      }

      lists.setCdr$Ex(var11, LList.Empty);
      return misc.values(new Object[]{lists.cdr.apply1(var5), lists.cdr.apply1(var6)});
   }

   public static Object reduce(Procedure var0, Object var1, Object var2) {
      return isNullList(var2) != Boolean.FALSE?var1:fold$V(var0, lists.car.apply1(var2), lists.cdr.apply1(var2), new Object[0]);
   }

   public static Object reduceRight(Procedure var0, Object var1, Object var2) {
      srfi1.frame11 var3 = new srfi1.frame11();
      var3.f = var0;
      return isNullList(var2) != Boolean.FALSE?var1:var3.lambda21recur(lists.car.apply1(var2), lists.cdr.apply1(var2));
   }

   public static Object remove(Object var0, Object var1) {
      srfi1.frame15 var2 = new srfi1.frame15();
      var2.pred = var0;
      return filter(var2.lambda$Fn14, var1);
   }

   public static Object remove$Ex(Object var0, Object var1) {
      srfi1.frame16 var2 = new srfi1.frame16();
      var2.pred = var0;
      return filter$Ex(var2.lambda$Fn15, var1);
   }

   public static Object seventh(Object var0) {
      return lists.caddr.apply1(lists.cddddr.apply1(var0));
   }

   public static Object sixth(Object var0) {
      return lists.cadr.apply1(lists.cddddr.apply1(var0));
   }

   public static Object span(Procedure var0, Object var1) {
      LList var3 = LList.Empty;
      Object var2 = var1;

      LList var6;
      Object var7;
      for(var1 = var3; isNullList(var2) == Boolean.FALSE; var1 = lists.cons(var7, var1)) {
         var7 = lists.car.apply1(var2);
         if(var0.apply1(var7) == Boolean.FALSE) {
            try {
               var6 = (LList)var1;
            } catch (ClassCastException var5) {
               throw new WrongType(var5, "reverse!", 1, var1);
            }

            return misc.values(new Object[]{lists.reverse$Ex(var6), var2});
         }

         var2 = lists.cdr.apply1(var2);
      }

      try {
         var6 = (LList)var1;
      } catch (ClassCastException var4) {
         throw new WrongType(var4, "reverse!", 1, var1);
      }

      return misc.values(new Object[]{lists.reverse$Ex(var6), var2});
   }

   public static Object span$Ex(Procedure var0, Object var1) {
      Object var2 = isNullList(var1);
      if(var2 != Boolean.FALSE) {
         if(var2 != Boolean.FALSE) {
            return misc.values(new Object[]{LList.Empty, var1});
         }
      } else if(var0.apply1(lists.car.apply1(var1)) == Boolean.FALSE) {
         return misc.values(new Object[]{LList.Empty, var1});
      }

      var2 = lists.cdr.apply1(var1);

      Object var4;
      for(Object var3 = var1; isNullList(var2) == Boolean.FALSE; var2 = var4) {
         if(var0.apply1(lists.car.apply1(var2)) == Boolean.FALSE) {
            Pair var6;
            try {
               var6 = (Pair)var3;
            } catch (ClassCastException var5) {
               throw new WrongType(var5, "set-cdr!", 1, var3);
            }

            lists.setCdr$Ex(var6, LList.Empty);
            break;
         }

         var4 = lists.cdr.apply1(var2);
         var3 = var2;
      }

      return misc.values(new Object[]{var1, var2});
   }

   public static Object splitAt(Object var0, IntNum var1) {
      LList var3 = LList.Empty;
      Object var2 = var0;
      var0 = var3;

      while(true) {
         Number var7;
         try {
            var7 = (Number)var1;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "zero?", 1, var1);
         }

         if(numbers.isZero(var7)) {
            LList var6;
            try {
               var6 = (LList)var0;
            } catch (ClassCastException var4) {
               throw new WrongType(var4, "reverse!", 1, var0);
            }

            return misc.values(new Object[]{lists.reverse$Ex(var6), var2});
         }

         var0 = lists.cons(lists.car.apply1(var2), var0);
         var2 = lists.cdr.apply1(var2);
         var1 = AddOp.$Mn.apply2(var1, Lit1);
      }
   }

   public static Object splitAt$Ex(Object var0, IntNum var1) {
      if(numbers.isZero(var1)) {
         return misc.values(new Object[]{LList.Empty, var0});
      } else {
         Object var5 = drop(var0, IntNum.add((IntNum)var1, -1));
         Object var2 = lists.cdr.apply1(var5);

         Pair var3;
         try {
            var3 = (Pair)var5;
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "set-cdr!", 1, var5);
         }

         lists.setCdr$Ex(var3, LList.Empty);
         return misc.values(new Object[]{var0, var2});
      }
   }

   public static Object take(Object var0, IntNum var1) {
      LList var3 = LList.Empty;
      Object var2 = var0;
      var0 = var3;

      while(true) {
         Number var7;
         try {
            var7 = (Number)var1;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "zero?", 1, var1);
         }

         if(numbers.isZero(var7)) {
            LList var6;
            try {
               var6 = (LList)var0;
            } catch (ClassCastException var4) {
               throw new WrongType(var4, "reverse!", 1, var0);
            }

            return lists.reverse$Ex(var6);
         }

         Object var8 = lists.cdr.apply1(var2);
         var1 = AddOp.$Mn.apply2(var1, Lit1);
         var0 = lists.cons(lists.car.apply1(var2), var0);
         var2 = var8;
      }
   }

   public static Object take$Ex(Object var0, IntNum var1) {
      if(numbers.isZero(var1)) {
         return LList.Empty;
      } else {
         Object var4 = drop(var0, IntNum.add((IntNum)var1, -1));

         Pair var2;
         try {
            var2 = (Pair)var4;
         } catch (ClassCastException var3) {
            throw new WrongType(var3, "set-cdr!", 1, var4);
         }

         lists.setCdr$Ex(var2, LList.Empty);
         return var0;
      }
   }

   public static Object takeRight(Object var0, IntNum var1) {
      Object var2 = drop(var0, var1);
      Object var3 = var0;

      for(var0 = var2; lists.isPair(var0); var0 = lists.cdr.apply1(var0)) {
         var3 = lists.cdr.apply1(var3);
      }

      return var3;
   }

   public static Object takeWhile(Procedure var0, Object var1) {
      srfi1.frame23 var2 = new srfi1.frame23();
      var2.pred = var0;
      return var2.lambda34recur(var1);
   }

   public static Object takeWhile$Ex(Procedure var0, Object var1) {
      Object var4;
      label35: {
         Object var2 = isNullList(var1);
         if(var2 != Boolean.FALSE) {
            if(var2 != Boolean.FALSE) {
               break label35;
            }
         } else if(var0.apply1(lists.car.apply1(var1)) == Boolean.FALSE) {
            break label35;
         }

         var2 = lists.cdr.apply1(var1);
         Object var3 = var1;

         while(true) {
            var4 = var1;
            if(!lists.isPair(var2)) {
               return var4;
            }

            if(var0.apply1(lists.car.apply1(var2)) == Boolean.FALSE) {
               Pair var6;
               try {
                  var6 = (Pair)var3;
               } catch (ClassCastException var5) {
                  throw new WrongType(var5, "set-cdr!", 1, var3);
               }

               lists.setCdr$Ex(var6, LList.Empty);
               return var1;
            }

            var4 = lists.cdr.apply1(var2);
            var3 = var2;
            var2 = var4;
         }
      }

      var4 = LList.Empty;
      return var4;
   }

   public static Object tenth(Object var0) {
      return lists.cadr.apply1(lists.cddddr.apply1(lists.cddddr.apply1(var0)));
   }

   public static Object unfold$V(Procedure var0, Procedure var1, Procedure var2, Object var3, Object[] var4) {
      LList var9 = LList.makeList(var4, 0);
      LList var5;
      Object var10;
      Object var11;
      if(lists.isPair(var9)) {
         Object var6 = lists.car.apply1(var9);
         if(lists.isPair(lists.cdr.apply1(var9))) {
            return Scheme.apply.applyN(new Object[]{misc.error, "Too many arguments", unfold, var0, var1, var2, var3, var9});
         } else {
            var5 = LList.Empty;
            var10 = var3;

            for(var3 = var5; var0.apply1(var10) == Boolean.FALSE; var10 = var11) {
               var11 = var2.apply1(var10);
               var3 = lists.cons(var1.apply1(var10), var3);
            }

            return appendReverse$Ex(var3, Scheme.applyToArgs.apply2(var6, var10));
         }
      } else {
         var5 = LList.Empty;
         var10 = var3;

         for(var3 = var5; var0.apply1(var10) == Boolean.FALSE; var10 = var11) {
            var11 = var2.apply1(var10);
            var3 = lists.cons(var1.apply1(var10), var3);
         }

         LList var8;
         try {
            var8 = (LList)var3;
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "reverse!", 1, var3);
         }

         return lists.reverse$Ex(var8);
      }
   }

   public static Object unfoldRight(Procedure var0, Procedure var1, Procedure var2, Object var3) {
      return unfoldRight(var0, var1, var2, var3, LList.Empty);
   }

   public static Object unfoldRight(Procedure var0, Procedure var1, Procedure var2, Object var3, Object var4) {
      while(var0.apply1(var3) == Boolean.FALSE) {
         Object var5 = var2.apply1(var3);
         var4 = lists.cons(var1.apply1(var3), var4);
         var3 = var5;
      }

      return var4;
   }

   public static LList unzip1(Object var0) {
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

   public static Object unzip2(Object var0) {
      new srfi1.frame();
      return srfi1.frame.lambda2recur(var0);
   }

   public static Object unzip3(Object var0) {
      new srfi1.frame1();
      return srfi1.frame1.lambda5recur(var0);
   }

   public static Object unzip4(Object var0) {
      new srfi1.frame3();
      return srfi1.frame3.lambda8recur(var0);
   }

   public static Object unzip5(Object var0) {
      new srfi1.frame5();
      return srfi1.frame5.lambda11recur(var0);
   }

   public static Pair xcons(Object var0, Object var1) {
      return lists.cons(var1, var0);
   }

   public static Object zip$V(Object var0, Object[] var1) {
      LList var2 = LList.makeList(var1, 0);
      return Scheme.apply.apply4(Scheme.map, LangObjType.listType, var0, var2);
   }

   public Object apply1(ModuleMethod var1, Object var2) {
      switch(var1.selector) {
      case 82:
         LList var8;
         try {
            var8 = (LList)var2;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "list-copy", 1, var2);
         }

         return listCopy(var8);
      case 83:
         IntNum var7;
         try {
            var7 = LangObjType.coerceIntNum(var2);
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "iota", 1, var2);
         }

         return iota(var7);
      case 87:
         return isProperList(var2);
      case 88:
         return isDottedList(var2);
      case 89:
         return isCircularList(var2);
      case 90:
         if(isNotPair(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 91:
         return isNullList(var2);
      case 93:
         return length$Pl(var2);
      case 95:
         return fifth(var2);
      case 96:
         return sixth(var2);
      case 97:
         return seventh(var2);
      case 98:
         return eighth(var2);
      case 99:
         return ninth(var2);
      case 100:
         return tenth(var2);
      case 101:
         return car$PlCdr(var2);
      case 110:
         return last(var2);
      case 111:
         Pair var6;
         try {
            var6 = (Pair)var2;
         } catch (ClassCastException var3) {
            throw new WrongType(var3, "last-pair", 1, var2);
         }

         return lastPair(var6);
      case 112:
         return unzip1(var2);
      case 113:
         return unzip2(var2);
      case 114:
         return unzip3(var2);
      case 115:
         return unzip4(var2);
      case 116:
         return unzip5(var2);
      case 120:
         return concatenate(var2);
      case 121:
         return concatenate$Ex(var2);
      case 147:
         return deleteDuplicates(var2);
      case 149:
         return deleteDuplicates$Ex(var2);
      case 152:
         return alistCopy(var2);
      default:
         return super.apply1(var1, var2);
      }
   }

   public Object apply2(ModuleMethod var1, Object var2, Object var3) {
      Procedure var27;
      IntNum var29;
      switch(var1.selector) {
      case 78:
         return xcons(var2, var3);
      case 80:
         try {
            var27 = (Procedure)var3;
         } catch (ClassCastException var26) {
            throw new WrongType(var26, "list-tabulate", 2, var3);
         }

         return listTabulate(var2, var27);
      case 83:
         try {
            var29 = LangObjType.coerceIntNum(var2);
         } catch (ClassCastException var25) {
            throw new WrongType(var25, "iota", 1, var2);
         }

         Numeric var28;
         try {
            var28 = LangObjType.coerceNumeric(var3);
         } catch (ClassCastException var24) {
            throw new WrongType(var24, "iota", 2, var3);
         }

         return iota(var29, var28);
      case 102:
         try {
            var29 = LangObjType.coerceIntNum(var3);
         } catch (ClassCastException var23) {
            throw new WrongType(var23, "take", 2, var3);
         }

         return take(var2, var29);
      case 103:
         try {
            var29 = LangObjType.coerceIntNum(var3);
         } catch (ClassCastException var22) {
            throw new WrongType(var22, "drop", 2, var3);
         }

         return drop(var2, var29);
      case 104:
         try {
            var29 = LangObjType.coerceIntNum(var3);
         } catch (ClassCastException var21) {
            throw new WrongType(var21, "take!", 2, var3);
         }

         return take$Ex(var2, var29);
      case 105:
         try {
            var29 = LangObjType.coerceIntNum(var3);
         } catch (ClassCastException var20) {
            throw new WrongType(var20, "take-right", 2, var3);
         }

         return takeRight(var2, var29);
      case 106:
         try {
            var29 = LangObjType.coerceIntNum(var3);
         } catch (ClassCastException var19) {
            throw new WrongType(var19, "drop-right", 2, var3);
         }

         return dropRight(var2, var29);
      case 107:
         try {
            var29 = LangObjType.coerceIntNum(var3);
         } catch (ClassCastException var18) {
            throw new WrongType(var18, "drop-right!", 2, var3);
         }

         return dropRight$Ex(var2, var29);
      case 108:
         try {
            var29 = LangObjType.coerceIntNum(var3);
         } catch (ClassCastException var17) {
            throw new WrongType(var17, "split-at", 2, var3);
         }

         return splitAt(var2, var29);
      case 109:
         try {
            var29 = LangObjType.coerceIntNum(var3);
         } catch (ClassCastException var16) {
            throw new WrongType(var16, "split-at!", 2, var3);
         }

         return splitAt$Ex(var2, var29);
      case 118:
         return appendReverse(var2, var3);
      case 119:
         return appendReverse$Ex(var2, var3);
      case 137:
         try {
            var27 = (Procedure)var2;
         } catch (ClassCastException var15) {
            throw new WrongType(var15, "filter", 1, var2);
         }

         return filter(var27, var3);
      case 138:
         try {
            var27 = (Procedure)var2;
         } catch (ClassCastException var14) {
            throw new WrongType(var14, "filter!", 1, var2);
         }

         return filter$Ex(var27, var3);
      case 139:
         try {
            var27 = (Procedure)var2;
         } catch (ClassCastException var13) {
            throw new WrongType(var13, "partition", 1, var2);
         }

         return partition(var27, var3);
      case 140:
         try {
            var27 = (Procedure)var2;
         } catch (ClassCastException var12) {
            throw new WrongType(var12, "partition!", 1, var2);
         }

         return partition$Ex(var27, var3);
      case 141:
         return remove(var2, var3);
      case 142:
         return remove$Ex(var2, var3);
      case 143:
         return delete(var2, var3);
      case 145:
         return delete$Ex(var2, var3);
      case 147:
         try {
            var27 = (Procedure)var3;
         } catch (ClassCastException var11) {
            throw new WrongType(var11, "delete-duplicates", 2, var3);
         }

         return deleteDuplicates(var2, var27);
      case 149:
         try {
            var27 = (Procedure)var3;
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "delete-duplicates!", 2, var3);
         }

         return deleteDuplicates$Ex(var2, var27);
      case 153:
         return alistDelete(var2, var3);
      case 155:
         return alistDelete$Ex(var2, var3);
      case 157:
         return find(var2, var3);
      case 158:
         try {
            var27 = (Procedure)var2;
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "find-tail", 1, var2);
         }

         return findTail(var27, var3);
      case 159:
         try {
            var27 = (Procedure)var2;
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "take-while", 1, var2);
         }

         return takeWhile(var27, var3);
      case 160:
         try {
            var27 = (Procedure)var2;
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "drop-while", 1, var2);
         }

         return dropWhile(var27, var3);
      case 161:
         try {
            var27 = (Procedure)var2;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "take-while!", 1, var2);
         }

         return takeWhile$Ex(var27, var3);
      case 162:
         try {
            var27 = (Procedure)var2;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "span", 1, var2);
         }

         return span(var27, var3);
      case 163:
         try {
            var27 = (Procedure)var2;
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "span!", 1, var2);
         }

         return span$Ex(var27, var3);
      case 164:
         return break(var2, var3);
      case 165:
         return break$Ex(var2, var3);
      case 182:
         return lists.cons(var2, var3);
      case 183:
         return lists.cons(var2, var3);
      default:
         return super.apply2(var1, var2, var3);
      }
   }

   public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
      Procedure var10;
      switch(var1.selector) {
      case 83:
         IntNum var11;
         try {
            var11 = LangObjType.coerceIntNum(var2);
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "iota", 1, var2);
         }

         Numeric var12;
         try {
            var12 = LangObjType.coerceNumeric(var3);
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "iota", 2, var3);
         }

         Numeric var13;
         try {
            var13 = LangObjType.coerceNumeric(var4);
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "iota", 3, var4);
         }

         return iota(var11, var12, var13);
      case 130:
         try {
            var10 = (Procedure)var2;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "reduce", 1, var2);
         }

         return reduce(var10, var3, var4);
      case 131:
         try {
            var10 = (Procedure)var2;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "reduce-right", 1, var2);
         }

         return reduceRight(var10, var3, var4);
      case 143:
         return delete(var2, var3, var4);
      case 145:
         return delete$Ex(var2, var3, var4);
      case 151:
         return alistCons(var2, var3, var4);
      case 153:
         return alistDelete(var2, var3, var4);
      case 155:
         return alistDelete$Ex(var2, var3, var4);
      default:
         return super.apply3(var1, var2, var3, var4);
      }
   }

   public Object apply4(ModuleMethod var1, Object var2, Object var3, Object var4, Object var5) {
      if(var1.selector == 123) {
         Procedure var9;
         try {
            var9 = (Procedure)var2;
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "unfold-right", 1, var2);
         }

         Procedure var10;
         try {
            var10 = (Procedure)var3;
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "unfold-right", 2, var3);
         }

         Procedure var11;
         try {
            var11 = (Procedure)var4;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "unfold-right", 3, var4);
         }

         return unfoldRight(var9, var10, var11, var5);
      } else {
         return super.apply4(var1, var2, var3, var4, var5);
      }
   }

   public Object applyN(ModuleMethod var1, Object[] var2) {
      Object var3;
      Object[] var4;
      Object[] var5;
      int var7;
      Procedure var38;
      Object var39;
      Object[] var40;
      Procedure var41;
      Object[] var46;
      Object var44;
      switch(var1.selector) {
      case 79:
         var39 = var2[0];
         var7 = var2.length - 1;
         var46 = new Object[var7];

         while(true) {
            --var7;
            if(var7 < 0) {
               return makeList$V(var39, var46);
            }

            var46[var7] = var2[var7 + 1];
         }
      case 81:
         return cons$St(var2);
      case 86:
         var39 = var2[0];
         var7 = var2.length - 1;
         var46 = new Object[var7];

         while(true) {
            --var7;
            if(var7 < 0) {
               return circularList$V(var39, var46);
            }

            var46[var7] = var2[var7 + 1];
         }
      case 92:
         var39 = var2[0];
         var7 = var2.length - 1;
         var46 = new Object[var7];

         while(true) {
            --var7;
            if(var7 < 0) {
               return list$Eq$V(var39, var46);
            }

            var46[var7] = var2[var7 + 1];
         }
      case 94:
         var39 = var2[0];
         var7 = var2.length - 1;
         var46 = new Object[var7];

         while(true) {
            --var7;
            if(var7 < 0) {
               return zip$V(var39, var46);
            }

            var46[var7] = var2[var7 + 1];
         }
      case 117:
         return append$Ex$V(var2);
      case 122:
         var3 = var2[0];

         try {
            var38 = (Procedure)var3;
         } catch (ClassCastException var37) {
            throw new WrongType(var37, "count", 1, var3);
         }

         var3 = var2[1];
         var7 = var2.length - 2;
         var4 = new Object[var7];

         while(true) {
            --var7;
            if(var7 < 0) {
               return count$V(var38, var3, var4);
            }

            var4[var7] = var2[var7 + 2];
         }
      case 123:
         var7 = var2.length - 4;
         var3 = var2[0];

         try {
            var38 = (Procedure)var3;
         } catch (ClassCastException var36) {
            throw new WrongType(var36, "unfold-right", 1, var3);
         }

         var44 = var2[1];

         try {
            var41 = (Procedure)var44;
         } catch (ClassCastException var35) {
            throw new WrongType(var35, "unfold-right", 2, var44);
         }

         var44 = var2[2];

         Procedure var43;
         try {
            var43 = (Procedure)var44;
         } catch (ClassCastException var34) {
            throw new WrongType(var34, "unfold-right", 3, var44);
         }

         var44 = var2[3];
         if(var7 <= 0) {
            return unfoldRight(var38, var41, var43, var44);
         }

         return unfoldRight(var38, var41, var43, var44, var2[4]);
      case 125:
         var3 = var2[0];

         try {
            var38 = (Procedure)var3;
         } catch (ClassCastException var33) {
            throw new WrongType(var33, "unfold", 1, var3);
         }

         var44 = var2[1];

         try {
            var41 = (Procedure)var44;
         } catch (ClassCastException var32) {
            throw new WrongType(var32, "unfold", 2, var44);
         }

         Object var42 = var2[2];

         Procedure var45;
         try {
            var45 = (Procedure)var42;
         } catch (ClassCastException var31) {
            throw new WrongType(var31, "unfold", 3, var42);
         }

         var42 = var2[3];
         var7 = var2.length - 4;
         Object[] var6 = new Object[var7];

         while(true) {
            --var7;
            if(var7 < 0) {
               return unfold$V(var38, var41, var45, var42, var6);
            }

            var6[var7] = var2[var7 + 4];
         }
      case 126:
         var3 = var2[0];

         try {
            var38 = (Procedure)var3;
         } catch (ClassCastException var30) {
            throw new WrongType(var30, "fold", 1, var3);
         }

         var3 = var2[1];
         var44 = var2[2];
         var7 = var2.length - 3;
         var5 = new Object[var7];

         while(true) {
            --var7;
            if(var7 < 0) {
               return fold$V(var38, var3, var44, var5);
            }

            var5[var7] = var2[var7 + 3];
         }
      case 127:
         var3 = var2[0];

         try {
            var38 = (Procedure)var3;
         } catch (ClassCastException var29) {
            throw new WrongType(var29, "fold-right", 1, var3);
         }

         var3 = var2[1];
         var44 = var2[2];
         var7 = var2.length - 3;
         var5 = new Object[var7];

         while(true) {
            --var7;
            if(var7 < 0) {
               return foldRight$V(var38, var3, var44, var5);
            }

            var5[var7] = var2[var7 + 3];
         }
      case 128:
         var3 = var2[0];

         try {
            var38 = (Procedure)var3;
         } catch (ClassCastException var28) {
            throw new WrongType(var28, "pair-fold-right", 1, var3);
         }

         var3 = var2[1];
         var44 = var2[2];
         var7 = var2.length - 3;
         var5 = new Object[var7];

         while(true) {
            --var7;
            if(var7 < 0) {
               return pairFoldRight$V(var38, var3, var44, var5);
            }

            var5[var7] = var2[var7 + 3];
         }
      case 129:
         var3 = var2[0];

         try {
            var38 = (Procedure)var3;
         } catch (ClassCastException var27) {
            throw new WrongType(var27, "pair-fold", 1, var3);
         }

         var3 = var2[1];
         var44 = var2[2];
         var7 = var2.length - 3;
         var5 = new Object[var7];

         while(true) {
            --var7;
            if(var7 < 0) {
               return pairFold$V(var38, var3, var44, var5);
            }

            var5[var7] = var2[var7 + 3];
         }
      case 132:
         var39 = var2[0];
         var3 = var2[1];
         var7 = var2.length - 2;
         var4 = new Object[var7];

         while(true) {
            --var7;
            if(var7 < 0) {
               return appendMap$V(var39, var3, var4);
            }

            var4[var7] = var2[var7 + 2];
         }
      case 133:
         var39 = var2[0];
         var3 = var2[1];
         var7 = var2.length - 2;
         var4 = new Object[var7];

         while(true) {
            --var7;
            if(var7 < 0) {
               return appendMap$Ex$V(var39, var3, var4);
            }

            var4[var7] = var2[var7 + 2];
         }
      case 134:
         var3 = var2[0];

         try {
            var38 = (Procedure)var3;
         } catch (ClassCastException var26) {
            throw new WrongType(var26, "pair-for-each", 1, var3);
         }

         var3 = var2[1];
         var7 = var2.length - 2;
         var4 = new Object[var7];

         while(true) {
            --var7;
            if(var7 < 0) {
               return pairForEach$V(var38, var3, var4);
            }

            var4[var7] = var2[var7 + 2];
         }
      case 135:
         var3 = var2[0];

         try {
            var38 = (Procedure)var3;
         } catch (ClassCastException var25) {
            throw new WrongType(var25, "map!", 1, var3);
         }

         var3 = var2[1];
         var7 = var2.length - 2;
         var4 = new Object[var7];

         while(true) {
            --var7;
            if(var7 < 0) {
               return map$Ex$V(var38, var3, var4);
            }

            var4[var7] = var2[var7 + 2];
         }
      case 136:
         var3 = var2[0];

         try {
            var38 = (Procedure)var3;
         } catch (ClassCastException var24) {
            throw new WrongType(var24, "filter-map", 1, var3);
         }

         var3 = var2[1];
         var7 = var2.length - 2;
         var4 = new Object[var7];

         while(true) {
            --var7;
            if(var7 < 0) {
               return filterMap$V(var38, var3, var4);
            }

            var4[var7] = var2[var7 + 2];
         }
      case 166:
         var3 = var2[0];

         try {
            var38 = (Procedure)var3;
         } catch (ClassCastException var23) {
            throw new WrongType(var23, "any", 1, var3);
         }

         var3 = var2[1];
         var7 = var2.length - 2;
         var4 = new Object[var7];

         while(true) {
            --var7;
            if(var7 < 0) {
               return any$V(var38, var3, var4);
            }

            var4[var7] = var2[var7 + 2];
         }
      case 167:
         var3 = var2[0];

         try {
            var38 = (Procedure)var3;
         } catch (ClassCastException var22) {
            throw new WrongType(var22, "every", 1, var3);
         }

         var3 = var2[1];
         var7 = var2.length - 2;
         var4 = new Object[var7];

         while(true) {
            --var7;
            if(var7 < 0) {
               return every$V(var38, var3, var4);
            }

            var4[var7] = var2[var7 + 2];
         }
      case 168:
         var3 = var2[0];

         try {
            var38 = (Procedure)var3;
         } catch (ClassCastException var21) {
            throw new WrongType(var21, "list-index", 1, var3);
         }

         var3 = var2[1];
         var7 = var2.length - 2;
         var4 = new Object[var7];

         while(true) {
            --var7;
            if(var7 < 0) {
               return listIndex$V(var38, var3, var4);
            }

            var4[var7] = var2[var7 + 2];
         }
      case 169:
         var39 = var2[0];

         try {
            var41 = (Procedure)var39;
         } catch (ClassCastException var20) {
            throw new WrongType(var20, "lset<=", 1, var39);
         }

         var7 = var2.length - 1;
         var40 = new Object[var7];

         while(true) {
            --var7;
            if(var7 < 0) {
               return lset$Ls$Eq$V(var41, var40);
            }

            var40[var7] = var2[var7 + 1];
         }
      case 170:
         var39 = var2[0];

         try {
            var41 = (Procedure)var39;
         } catch (ClassCastException var19) {
            throw new WrongType(var19, "lset=", 1, var39);
         }

         var7 = var2.length - 1;
         var40 = new Object[var7];

         while(true) {
            --var7;
            if(var7 < 0) {
               return lset$Eq$V(var41, var40);
            }

            var40[var7] = var2[var7 + 1];
         }
      case 171:
         var3 = var2[0];

         try {
            var38 = (Procedure)var3;
         } catch (ClassCastException var18) {
            throw new WrongType(var18, "lset-adjoin", 1, var3);
         }

         var3 = var2[1];
         var7 = var2.length - 2;
         var4 = new Object[var7];

         while(true) {
            --var7;
            if(var7 < 0) {
               return lsetAdjoin$V(var38, var3, var4);
            }

            var4[var7] = var2[var7 + 2];
         }
      case 172:
         var39 = var2[0];

         try {
            var41 = (Procedure)var39;
         } catch (ClassCastException var17) {
            throw new WrongType(var17, "lset-union", 1, var39);
         }

         var7 = var2.length - 1;
         var40 = new Object[var7];

         while(true) {
            --var7;
            if(var7 < 0) {
               return lsetUnion$V(var41, var40);
            }

            var40[var7] = var2[var7 + 1];
         }
      case 173:
         var39 = var2[0];

         try {
            var41 = (Procedure)var39;
         } catch (ClassCastException var16) {
            throw new WrongType(var16, "lset-union!", 1, var39);
         }

         var7 = var2.length - 1;
         var40 = new Object[var7];

         while(true) {
            --var7;
            if(var7 < 0) {
               return lsetUnion$Ex$V(var41, var40);
            }

            var40[var7] = var2[var7 + 1];
         }
      case 174:
         var3 = var2[0];

         try {
            var38 = (Procedure)var3;
         } catch (ClassCastException var15) {
            throw new WrongType(var15, "lset-intersection", 1, var3);
         }

         var3 = var2[1];
         var7 = var2.length - 2;
         var4 = new Object[var7];

         while(true) {
            --var7;
            if(var7 < 0) {
               return lsetIntersection$V(var38, var3, var4);
            }

            var4[var7] = var2[var7 + 2];
         }
      case 175:
         var3 = var2[0];

         try {
            var38 = (Procedure)var3;
         } catch (ClassCastException var14) {
            throw new WrongType(var14, "lset-intersection!", 1, var3);
         }

         var3 = var2[1];
         var7 = var2.length - 2;
         var4 = new Object[var7];

         while(true) {
            --var7;
            if(var7 < 0) {
               return lsetIntersection$Ex$V(var38, var3, var4);
            }

            var4[var7] = var2[var7 + 2];
         }
      case 176:
         var3 = var2[0];

         try {
            var38 = (Procedure)var3;
         } catch (ClassCastException var13) {
            throw new WrongType(var13, "lset-difference", 1, var3);
         }

         var3 = var2[1];
         var7 = var2.length - 2;
         var4 = new Object[var7];

         while(true) {
            --var7;
            if(var7 < 0) {
               return lsetDifference$V(var38, var3, var4);
            }

            var4[var7] = var2[var7 + 2];
         }
      case 177:
         var3 = var2[0];

         try {
            var38 = (Procedure)var3;
         } catch (ClassCastException var12) {
            throw new WrongType(var12, "lset-difference!", 1, var3);
         }

         var3 = var2[1];
         var7 = var2.length - 2;
         var4 = new Object[var7];

         while(true) {
            --var7;
            if(var7 < 0) {
               return lsetDifference$Ex$V(var38, var3, var4);
            }

            var4[var7] = var2[var7 + 2];
         }
      case 178:
         var39 = var2[0];

         try {
            var41 = (Procedure)var39;
         } catch (ClassCastException var11) {
            throw new WrongType(var11, "lset-xor", 1, var39);
         }

         var7 = var2.length - 1;
         var40 = new Object[var7];

         while(true) {
            --var7;
            if(var7 < 0) {
               return lsetXor$V(var41, var40);
            }

            var40[var7] = var2[var7 + 1];
         }
      case 179:
         var39 = var2[0];

         try {
            var41 = (Procedure)var39;
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "lset-xor!", 1, var39);
         }

         var7 = var2.length - 1;
         var40 = new Object[var7];

         while(true) {
            --var7;
            if(var7 < 0) {
               return lsetXor$Ex$V(var41, var40);
            }

            var40[var7] = var2[var7 + 1];
         }
      case 180:
         var3 = var2[0];

         try {
            var38 = (Procedure)var3;
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "lset-diff+intersection", 1, var3);
         }

         var3 = var2[1];
         var7 = var2.length - 2;
         var4 = new Object[var7];

         while(true) {
            --var7;
            if(var7 < 0) {
               return lsetDiff$PlIntersection$V(var38, var3, var4);
            }

            var4[var7] = var2[var7 + 2];
         }
      case 181:
         var3 = var2[0];

         try {
            var38 = (Procedure)var3;
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "lset-diff+intersection!", 1, var3);
         }

         var3 = var2[1];
         var7 = var2.length - 2;
         var4 = new Object[var7];

         while(true) {
            --var7;
            if(var7 < 0) {
               return lsetDiff$PlIntersection$Ex$V(var38, var3, var4);
            }

            var4[var7] = var2[var7 + 2];
         }
      default:
         return super.applyN(var1, var2);
      }
   }

   public int match1(ModuleMethod var1, Object var2, CallContext var3) {
      switch(var1.selector) {
      case 82:
         if(var2 instanceof LList) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 83:
         if(IntNum.asIntNumOrNull(var2) != null) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 87:
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
      case 91:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 93:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 95:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 96:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 97:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 98:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 99:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 100:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 101:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 110:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 111:
         if(!(var2 instanceof Pair)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 112:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 113:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 114:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 115:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 116:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 120:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 121:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 147:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 149:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 152:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      default:
         return super.match1(var1, var2, var3);
      }
   }

   public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
      switch(var1.selector) {
      case 78:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 80:
         var4.value1 = var2;
         if(!(var3 instanceof Procedure)) {
            return -786430;
         }

         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 83:
         if(IntNum.asIntNumOrNull(var2) != null) {
            var4.value1 = var2;
            if(Numeric.asNumericOrNull(var3) != null) {
               var4.value2 = var3;
               var4.proc = var1;
               var4.pc = 2;
               return 0;
            }

            return -786430;
         }

         return -786431;
      case 102:
         var4.value1 = var2;
         if(IntNum.asIntNumOrNull(var3) != null) {
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }

         return -786430;
      case 103:
         var4.value1 = var2;
         if(IntNum.asIntNumOrNull(var3) != null) {
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }

         return -786430;
      case 104:
         var4.value1 = var2;
         if(IntNum.asIntNumOrNull(var3) != null) {
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }

         return -786430;
      case 105:
         var4.value1 = var2;
         if(IntNum.asIntNumOrNull(var3) != null) {
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }

         return -786430;
      case 106:
         var4.value1 = var2;
         if(IntNum.asIntNumOrNull(var3) != null) {
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }

         return -786430;
      case 107:
         var4.value1 = var2;
         if(IntNum.asIntNumOrNull(var3) != null) {
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }

         return -786430;
      case 108:
         var4.value1 = var2;
         if(IntNum.asIntNumOrNull(var3) != null) {
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }

         return -786430;
      case 109:
         var4.value1 = var2;
         if(IntNum.asIntNumOrNull(var3) != null) {
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }

         return -786430;
      case 118:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 119:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 137:
         if(!(var2 instanceof Procedure)) {
            return -786431;
         }

         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 138:
         if(!(var2 instanceof Procedure)) {
            return -786431;
         }

         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 139:
         if(!(var2 instanceof Procedure)) {
            return -786431;
         }

         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 140:
         if(!(var2 instanceof Procedure)) {
            return -786431;
         }

         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 141:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 142:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 143:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 145:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 147:
         var4.value1 = var2;
         if(!(var3 instanceof Procedure)) {
            return -786430;
         }

         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 149:
         var4.value1 = var2;
         if(!(var3 instanceof Procedure)) {
            return -786430;
         }

         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 153:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 155:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 157:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 158:
         if(!(var2 instanceof Procedure)) {
            return -786431;
         }

         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 159:
         if(!(var2 instanceof Procedure)) {
            return -786431;
         }

         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 160:
         if(!(var2 instanceof Procedure)) {
            return -786431;
         }

         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 161:
         if(!(var2 instanceof Procedure)) {
            return -786431;
         }

         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 162:
         if(!(var2 instanceof Procedure)) {
            return -786431;
         }

         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 163:
         if(!(var2 instanceof Procedure)) {
            return -786431;
         }

         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 164:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 165:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 182:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 183:
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
      case 83:
         if(IntNum.asIntNumOrNull(var2) != null) {
            var5.value1 = var2;
            if(Numeric.asNumericOrNull(var3) != null) {
               var5.value2 = var3;
               if(Numeric.asNumericOrNull(var4) != null) {
                  var5.value3 = var4;
                  var5.proc = var1;
                  var5.pc = 3;
                  return 0;
               }

               return -786429;
            }

            return -786430;
         }

         return -786431;
      case 130:
         if(!(var2 instanceof Procedure)) {
            return -786431;
         }

         var5.value1 = var2;
         var5.value2 = var3;
         var5.value3 = var4;
         var5.proc = var1;
         var5.pc = 3;
         return 0;
      case 131:
         if(!(var2 instanceof Procedure)) {
            return -786431;
         }

         var5.value1 = var2;
         var5.value2 = var3;
         var5.value3 = var4;
         var5.proc = var1;
         var5.pc = 3;
         return 0;
      case 143:
         var5.value1 = var2;
         var5.value2 = var3;
         var5.value3 = var4;
         var5.proc = var1;
         var5.pc = 3;
         return 0;
      case 145:
         var5.value1 = var2;
         var5.value2 = var3;
         var5.value3 = var4;
         var5.proc = var1;
         var5.pc = 3;
         return 0;
      case 151:
         var5.value1 = var2;
         var5.value2 = var3;
         var5.value3 = var4;
         var5.proc = var1;
         var5.pc = 3;
         return 0;
      case 153:
         var5.value1 = var2;
         var5.value2 = var3;
         var5.value3 = var4;
         var5.proc = var1;
         var5.pc = 3;
         return 0;
      case 155:
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

   public int match4(ModuleMethod var1, Object var2, Object var3, Object var4, Object var5, CallContext var6) {
      if(var1.selector == 123) {
         if(!(var2 instanceof Procedure)) {
            return -786431;
         } else {
            var6.value1 = var2;
            if(!(var3 instanceof Procedure)) {
               return -786430;
            } else {
               var6.value2 = var3;
               if(!(var4 instanceof Procedure)) {
                  return -786429;
               } else {
                  var6.value3 = var4;
                  var6.value4 = var5;
                  var6.proc = var1;
                  var6.pc = 4;
                  return 0;
               }
            }
         }
      } else {
         return super.match4(var1, var2, var3, var4, var5, var6);
      }
   }

   public int matchN(ModuleMethod var1, Object[] var2, CallContext var3) {
      switch(var1.selector) {
      case 79:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 81:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 86:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 92:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 94:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 117:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 122:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 123:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 125:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 126:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 127:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 128:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 129:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 132:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 133:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 134:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 135:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 136:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 166:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 167:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 168:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 169:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 170:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 171:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 172:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 173:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 174:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 175:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 176:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 177:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 178:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 179:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 180:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 181:
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
      first = lists.car;
      second = lists.cadr;
      third = lists.caddr;
      fourth = lists.cadddr;
      map$Mnin$Mnorder = Scheme.map;
   }

   public class frame extends ModuleBody {

      public static Object lambda2recur(Object var0) {
         srfi1.frame0 var1 = new srfi1.frame0();
         var1.lis = var0;
         if(srfi1.isNullList(var1.lis) != Boolean.FALSE) {
            return misc.values(new Object[]{var1.lis, var1.lis});
         } else {
            var1.elt = lists.car.apply1(var1.lis);
            return call_with_values.callWithValues(var1.lambda$Fn1, var1.lambda$Fn2);
         }
      }
   }

   public class frame0 extends ModuleBody {

      Object elt;
      final ModuleMethod lambda$Fn1 = new ModuleMethod(this, 1, (Object)null, 0);
      final ModuleMethod lambda$Fn2;
      Object lis;


      public frame0() {
         ModuleMethod var1 = new ModuleMethod(this, 2, (Object)null, 8194);
         var1.setProperty("source-location", "srfi1.scm:627");
         this.lambda$Fn2 = var1;
      }

      public Object apply0(ModuleMethod var1) {
         return var1.selector == 1?this.lambda3():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 2?this.lambda4(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda3() {
         return srfi1.frame.lambda2recur(lists.cdr.apply1(this.lis));
      }

      Object lambda4(Object var1, Object var2) {
         return misc.values(new Object[]{lists.cons(lists.car.apply1(this.elt), var1), lists.cons(lists.cadr.apply1(this.elt), var2)});
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 1) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 2) {
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

      public static Object lambda5recur(Object var0) {
         srfi1.frame2 var1 = new srfi1.frame2();
         var1.lis = var0;
         if(srfi1.isNullList(var1.lis) != Boolean.FALSE) {
            return misc.values(new Object[]{var1.lis, var1.lis, var1.lis});
         } else {
            var1.elt = lists.car.apply1(var1.lis);
            return call_with_values.callWithValues(var1.lambda$Fn3, var1.lambda$Fn4);
         }
      }
   }

   public class frame10 extends ModuleBody {

      Procedure f;
      Object zero;


      public Object lambda19recur(Object var1) {
         Object var2 = srfi1.$PcCdrs(var1);
         return lists.isNull(var2)?this.zero:Scheme.apply.apply2(this.f, srfi1.append$Ex$V(new Object[]{var1, LList.list1(this.lambda19recur(var2))}));
      }

      public Object lambda20recur(Object var1) {
         return srfi1.isNullList(var1) != Boolean.FALSE?this.zero:this.f.apply2(var1, this.lambda20recur(lists.cdr.apply1(var1)));
      }
   }

   public class frame11 extends ModuleBody {

      Procedure f;


      public Object lambda21recur(Object var1, Object var2) {
         Object var3 = var1;
         if(lists.isPair(var2)) {
            var3 = this.f.apply2(var1, this.lambda21recur(lists.car.apply1(var2), lists.cdr.apply1(var2)));
         }

         return var3;
      }
   }

   public class frame12 extends ModuleBody {

      Procedure f;
      final ModuleMethod lambda$Fn11;


      public frame12() {
         ModuleMethod var1 = new ModuleMethod(this, 11, (Object)null, 4097);
         var1.setProperty("source-location", "srfi1.scm:961");
         this.lambda$Fn11 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         if(var1.selector == 11) {
            this.lambda22(var2);
            return Values.empty;
         } else {
            return super.apply1(var1, var2);
         }
      }

      void lambda22(Object var1) {
         Pair var2;
         try {
            var2 = (Pair)var1;
         } catch (ClassCastException var3) {
            throw new WrongType(var3, "set-car!", 1, var1);
         }

         lists.setCar$Ex(var2, this.f.apply1(lists.car.apply1(var1)));
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

   public class frame13 extends ModuleBody {

      Procedure f;


      public Object lambda23recur(Object var1, Object var2) {
         srfi1.frame14 var3 = new srfi1.frame14();
         var3.staticLink = this;
         var3.lists = var1;
         var3.res = var2;
         return call_with_values.callWithValues(var3.lambda$Fn12, var3.lambda$Fn13);
      }
   }

   public class frame14 extends ModuleBody {

      final ModuleMethod lambda$Fn12 = new ModuleMethod(this, 12, (Object)null, 0);
      final ModuleMethod lambda$Fn13;
      Object lists;
      Object res;
      srfi1.frame13 staticLink;


      public frame14() {
         ModuleMethod var1 = new ModuleMethod(this, 13, (Object)null, 8194);
         var1.setProperty("source-location", "srfi1.scm:969");
         this.lambda$Fn13 = var1;
      }

      public Object apply0(ModuleMethod var1) {
         return var1.selector == 12?this.lambda24():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 13?this.lambda25(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda24() {
         return srfi1.$PcCars$PlCdrs(this.lists);
      }

      Object lambda25(Object var1, Object var2) {
         if(srfi1.isNotPair(var1)) {
            var1 = this.res;

            LList var4;
            try {
               var4 = (LList)var1;
            } catch (ClassCastException var3) {
               throw new WrongType(var3, "reverse!", 1, var1);
            }

            return lists.reverse$Ex(var4);
         } else {
            var1 = Scheme.apply.apply2(this.staticLink.f, var1);
            return var1 != Boolean.FALSE?this.staticLink.lambda23recur(var2, lists.cons(var1, this.res)):this.staticLink.lambda23recur(var2, this.res);
         }
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 12) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 13) {
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

   public class frame15 extends ModuleBody {

      final ModuleMethod lambda$Fn14;
      Object pred;


      public frame15() {
         ModuleMethod var1 = new ModuleMethod(this, 14, (Object)null, 4097);
         var1.setProperty("source-location", "srfi1.scm:1199");
         this.lambda$Fn14 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 14?(this.lambda26(var2)?Boolean.TRUE:Boolean.FALSE):super.apply1(var1, var2);
      }

      boolean lambda26(Object var1) {
         byte var2;
         if(Scheme.applyToArgs.apply2(this.pred, var1) != Boolean.FALSE) {
            var2 = 1;
         } else {
            var2 = 0;
         }

         return (boolean)(var2 + 1 & 1);
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         if(var1.selector == 14) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return super.match1(var1, var2, var3);
         }
      }
   }

   public class frame16 extends ModuleBody {

      final ModuleMethod lambda$Fn15;
      Object pred;


      public frame16() {
         ModuleMethod var1 = new ModuleMethod(this, 15, (Object)null, 4097);
         var1.setProperty("source-location", "srfi1.scm:1200");
         this.lambda$Fn15 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 15?(this.lambda27(var2)?Boolean.TRUE:Boolean.FALSE):super.apply1(var1, var2);
      }

      boolean lambda27(Object var1) {
         byte var2;
         if(Scheme.applyToArgs.apply2(this.pred, var1) != Boolean.FALSE) {
            var2 = 1;
         } else {
            var2 = 0;
         }

         return (boolean)(var2 + 1 & 1);
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         if(var1.selector == 15) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return super.match1(var1, var2, var3);
         }
      }
   }

   public class frame17 extends ModuleBody {

      final ModuleMethod lambda$Fn16;
      Object maybe$Mn$Eq;
      Object x;


      public frame17() {
         ModuleMethod var1 = new ModuleMethod(this, 16, (Object)null, 4097);
         var1.setProperty("source-location", "srfi1.scm:1222");
         this.lambda$Fn16 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 16?(this.lambda28(var2)?Boolean.TRUE:Boolean.FALSE):super.apply1(var1, var2);
      }

      boolean lambda28(Object var1) {
         byte var2;
         if(Scheme.applyToArgs.apply3(this.maybe$Mn$Eq, this.x, var1) != Boolean.FALSE) {
            var2 = 1;
         } else {
            var2 = 0;
         }

         return (boolean)(var2 + 1 & 1);
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         if(var1.selector == 16) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return super.match1(var1, var2, var3);
         }
      }
   }

   public class frame18 extends ModuleBody {

      final ModuleMethod lambda$Fn17;
      Object maybe$Mn$Eq;
      Object x;


      public frame18() {
         ModuleMethod var1 = new ModuleMethod(this, 17, (Object)null, 4097);
         var1.setProperty("source-location", "srfi1.scm:1225");
         this.lambda$Fn17 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 17?(this.lambda29(var2)?Boolean.TRUE:Boolean.FALSE):super.apply1(var1, var2);
      }

      boolean lambda29(Object var1) {
         byte var2;
         if(Scheme.applyToArgs.apply3(this.maybe$Mn$Eq, this.x, var1) != Boolean.FALSE) {
            var2 = 1;
         } else {
            var2 = 0;
         }

         return (boolean)(var2 + 1 & 1);
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         if(var1.selector == 17) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return super.match1(var1, var2, var3);
         }
      }
   }

   public class frame19 extends ModuleBody {

      Procedure maybe$Mn$Eq;


      public Object lambda30recur(Object var1) {
         if(srfi1.isNullList(var1) == Boolean.FALSE) {
            Object var2 = lists.car.apply1(var1);
            Object var3 = lists.cdr.apply1(var1);
            Object var4 = this.lambda30recur(srfi1.delete(var2, var3, this.maybe$Mn$Eq));
            if(var3 != var4) {
               return lists.cons(var2, var4);
            }
         }

         return var1;
      }
   }

   public class frame2 extends ModuleBody {

      Object elt;
      final ModuleMethod lambda$Fn3 = new ModuleMethod(this, 3, (Object)null, 0);
      final ModuleMethod lambda$Fn4;
      Object lis;


      public frame2() {
         ModuleMethod var1 = new ModuleMethod(this, 4, (Object)null, 12291);
         var1.setProperty("source-location", "srfi1.scm:635");
         this.lambda$Fn4 = var1;
      }

      public Object apply0(ModuleMethod var1) {
         return var1.selector == 3?this.lambda6():super.apply0(var1);
      }

      public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
         return var1.selector == 4?this.lambda7(var2, var3, var4):super.apply3(var1, var2, var3, var4);
      }

      Object lambda6() {
         return srfi1.frame1.lambda5recur(lists.cdr.apply1(this.lis));
      }

      Object lambda7(Object var1, Object var2, Object var3) {
         return misc.values(new Object[]{lists.cons(lists.car.apply1(this.elt), var1), lists.cons(lists.cadr.apply1(this.elt), var2), lists.cons(lists.caddr.apply1(this.elt), var3)});
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 3) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match3(ModuleMethod var1, Object var2, Object var3, Object var4, CallContext var5) {
         if(var1.selector == 4) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         } else {
            return super.match3(var1, var2, var3, var4, var5);
         }
      }
   }

   public class frame20 extends ModuleBody {

      Procedure maybe$Mn$Eq;


      public Object lambda31recur(Object var1) {
         if(srfi1.isNullList(var1) == Boolean.FALSE) {
            Object var2 = lists.car.apply1(var1);
            Object var3 = lists.cdr.apply1(var1);
            Object var4 = this.lambda31recur(srfi1.delete$Ex(var2, var3, this.maybe$Mn$Eq));
            if(var3 != var4) {
               return lists.cons(var2, var4);
            }
         }

         return var1;
      }
   }

   public class frame21 extends ModuleBody {

      Object key;
      final ModuleMethod lambda$Fn18;
      Object maybe$Mn$Eq;


      public frame21() {
         ModuleMethod var1 = new ModuleMethod(this, 18, (Object)null, 4097);
         var1.setProperty("source-location", "srfi1.scm:1280");
         this.lambda$Fn18 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 18?(this.lambda32(var2)?Boolean.TRUE:Boolean.FALSE):super.apply1(var1, var2);
      }

      boolean lambda32(Object var1) {
         byte var2;
         if(Scheme.applyToArgs.apply3(this.maybe$Mn$Eq, this.key, lists.car.apply1(var1)) != Boolean.FALSE) {
            var2 = 1;
         } else {
            var2 = 0;
         }

         return (boolean)(var2 + 1 & 1);
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         if(var1.selector == 18) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return super.match1(var1, var2, var3);
         }
      }
   }

   public class frame22 extends ModuleBody {

      Object key;
      final ModuleMethod lambda$Fn19;
      Object maybe$Mn$Eq;


      public frame22() {
         ModuleMethod var1 = new ModuleMethod(this, 19, (Object)null, 4097);
         var1.setProperty("source-location", "srfi1.scm:1283");
         this.lambda$Fn19 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 19?(this.lambda33(var2)?Boolean.TRUE:Boolean.FALSE):super.apply1(var1, var2);
      }

      boolean lambda33(Object var1) {
         byte var2;
         if(Scheme.applyToArgs.apply3(this.maybe$Mn$Eq, this.key, lists.car.apply1(var1)) != Boolean.FALSE) {
            var2 = 1;
         } else {
            var2 = 0;
         }

         return (boolean)(var2 + 1 & 1);
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         if(var1.selector == 19) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return super.match1(var1, var2, var3);
         }
      }
   }

   public class frame23 extends ModuleBody {

      Procedure pred;


      public Object lambda34recur(Object var1) {
         if(srfi1.isNullList(var1) != Boolean.FALSE) {
            return LList.Empty;
         } else {
            Object var2 = lists.car.apply1(var1);
            return this.pred.apply1(var2) != Boolean.FALSE?lists.cons(var2, this.lambda34recur(lists.cdr.apply1(var1))):LList.Empty;
         }
      }
   }

   public class frame24 extends ModuleBody {

      final ModuleMethod lambda$Fn20;
      Object pred;


      public frame24() {
         ModuleMethod var1 = new ModuleMethod(this, 20, (Object)null, 4097);
         var1.setProperty("source-location", "srfi1.scm:1343");
         this.lambda$Fn20 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 20?(this.lambda35(var2)?Boolean.TRUE:Boolean.FALSE):super.apply1(var1, var2);
      }

      boolean lambda35(Object var1) {
         byte var2;
         if(Scheme.applyToArgs.apply2(this.pred, var1) != Boolean.FALSE) {
            var2 = 1;
         } else {
            var2 = 0;
         }

         return (boolean)(var2 + 1 & 1);
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         if(var1.selector == 20) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return super.match1(var1, var2, var3);
         }
      }
   }

   public class frame25 extends ModuleBody {

      final ModuleMethod lambda$Fn21;
      Object pred;


      public frame25() {
         ModuleMethod var1 = new ModuleMethod(this, 21, (Object)null, 4097);
         var1.setProperty("source-location", "srfi1.scm:1344");
         this.lambda$Fn21 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 21?(this.lambda36(var2)?Boolean.TRUE:Boolean.FALSE):super.apply1(var1, var2);
      }

      boolean lambda36(Object var1) {
         byte var2;
         if(Scheme.applyToArgs.apply2(this.pred, var1) != Boolean.FALSE) {
            var2 = 1;
         } else {
            var2 = 0;
         }

         return (boolean)(var2 + 1 & 1);
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         if(var1.selector == 21) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return super.match1(var1, var2, var3);
         }
      }
   }

   public class frame26 extends ModuleBody {

      final ModuleMethod lambda$Fn22 = new ModuleMethod(this, 22, (Object)null, 0);
      final ModuleMethod lambda$Fn23;
      Object lis1;
      LList lists;
      Procedure pred;


      public frame26() {
         ModuleMethod var1 = new ModuleMethod(this, 23, (Object)null, 8194);
         var1.setProperty("source-location", "srfi1.scm:1350");
         this.lambda$Fn23 = var1;
      }

      public Object apply0(ModuleMethod var1) {
         return var1.selector == 22?this.lambda37():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 23?this.lambda38(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda37() {
         return srfi1.$PcCars$PlCdrs(lists.cons(this.lis1, this.lists));
      }

      Object lambda38(Object var1, Object var2) {
         boolean var4 = lists.isPair(var1);
         if(var4) {
            while(true) {
               var2 = srfi1.$PcCars$PlCdrs$SlPair(var2);
               Object var3 = lists.car.apply1(var2);
               var2 = lists.cdr.apply1(var2);
               if(!lists.isPair(var3)) {
                  return Scheme.apply.apply2(this.pred, var1);
               }

               var1 = Scheme.apply.apply2(this.pred, var1);
               if(var1 != Boolean.FALSE) {
                  return var1;
               }

               var1 = var3;
            }
         } else {
            return var4?Boolean.TRUE:Boolean.FALSE;
         }
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 22) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 23) {
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

   public class frame27 extends ModuleBody {

      final ModuleMethod lambda$Fn24 = new ModuleMethod(this, 26, (Object)null, 0);
      final ModuleMethod lambda$Fn25;
      Object lis1;
      LList lists;
      Procedure pred;


      public frame27() {
         ModuleMethod var1 = new ModuleMethod(this, 27, (Object)null, 8194);
         var1.setProperty("source-location", "srfi1.scm:1378");
         this.lambda$Fn25 = var1;
      }

      public Object apply0(ModuleMethod var1) {
         return var1.selector == 26?this.lambda39():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 27?this.lambda40(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda39() {
         return srfi1.$PcCars$PlCdrs(lists.cons(this.lis1, this.lists));
      }

      Object lambda40(Object var1, Object var2) {
         throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:783)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:662)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:722)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:706)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e2expr(TypeTransformer.java:632)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:706)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e2expr(TypeTransformer.java:632)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:706)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s2stmt(TypeTransformer.java:823)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:846)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n\tat sun.reflect.NativeMethodAccessorImpl.invoke(Unknown Source)\r\n\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source)\r\n\tat java.lang.reflect.Method.invoke(Unknown Source)\r\n\tat com.exe4j.runtime.LauncherEngine.launch(Unknown Source)\r\n\tat com.exe4j.runtime.WinLauncher.main(Unknown Source)\r\n");
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 26) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 27) {
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

   public class frame28 extends ModuleBody {

      srfi1.frame27 staticLink;


      public Object lambda41lp(Object var1, Object var2) {
         srfi1.frame29 var3 = new srfi1.frame29();
         var3.staticLink = this;
         var3.heads = var1;
         var3.tails = var2;
         return call_with_values.callWithValues(var3.lambda$Fn26, var3.lambda$Fn27);
      }
   }

   public class frame29 extends ModuleBody {

      Object heads;
      final ModuleMethod lambda$Fn26 = new ModuleMethod(this, 24, (Object)null, 0);
      final ModuleMethod lambda$Fn27;
      srfi1.frame28 staticLink;
      Object tails;


      public frame29() {
         ModuleMethod var1 = new ModuleMethod(this, 25, (Object)null, 8194);
         var1.setProperty("source-location", "srfi1.scm:1381");
         this.lambda$Fn27 = var1;
      }

      public Object apply0(ModuleMethod var1) {
         return var1.selector == 24?this.lambda42():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 25?this.lambda43(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda42() {
         return srfi1.$PcCars$PlCdrs(this.tails);
      }

      Object lambda43(Object var1, Object var2) {
         if(lists.isPair(var1)) {
            Object var4 = Scheme.apply.apply2(this.staticLink.staticLink.pred, this.heads);
            Object var3 = var4;
            if(var4 != Boolean.FALSE) {
               var3 = this.staticLink.lambda41lp(var1, var2);
            }

            return var3;
         } else {
            return Scheme.apply.apply2(this.staticLink.staticLink.pred, this.heads);
         }
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 24) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 25) {
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

   public class frame3 extends ModuleBody {

      public static Object lambda8recur(Object var0) {
         srfi1.frame4 var1 = new srfi1.frame4();
         var1.lis = var0;
         if(srfi1.isNullList(var1.lis) != Boolean.FALSE) {
            return misc.values(new Object[]{var1.lis, var1.lis, var1.lis, var1.lis});
         } else {
            var1.elt = lists.car.apply1(var1.lis);
            return call_with_values.callWithValues(var1.lambda$Fn5, var1.lambda$Fn6);
         }
      }
   }

   public class frame30 extends ModuleBody {

      Procedure pred;


      public Object lambda44lp(Object var1, Object var2) {
         srfi1.frame31 var3 = new srfi1.frame31();
         var3.staticLink = this;
         var3.lists = var1;
         var3.n = var2;
         return call_with_values.callWithValues(var3.lambda$Fn28, var3.lambda$Fn29);
      }
   }

   public class frame31 extends ModuleBody {

      final ModuleMethod lambda$Fn28 = new ModuleMethod(this, 28, (Object)null, 0);
      final ModuleMethod lambda$Fn29;
      Object lists;
      Object n;
      srfi1.frame30 staticLink;


      public frame31() {
         ModuleMethod var1 = new ModuleMethod(this, 29, (Object)null, 8194);
         var1.setProperty("source-location", "srfi1.scm:1404");
         this.lambda$Fn29 = var1;
      }

      public Object apply0(ModuleMethod var1) {
         return var1.selector == 28?this.lambda45():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 29?this.lambda46(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda45() {
         return srfi1.$PcCars$PlCdrs(this.lists);
      }

      Object lambda46(Object var1, Object var2) {
         boolean var3 = lists.isPair(var1);
         return var3?(Scheme.apply.apply2(this.staticLink.pred, var1) != Boolean.FALSE?this.n:this.staticLink.lambda44lp(var2, AddOp.$Pl.apply2(this.n, srfi1.Lit1))):(var3?Boolean.TRUE:Boolean.FALSE);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 28) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 29) {
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

   public class frame32 extends ModuleBody {

      Procedure $Eq;
      final ModuleMethod lambda$Fn30;


      public frame32() {
         ModuleMethod var1 = new ModuleMethod(this, 30, (Object)null, 8194);
         var1.setProperty("source-location", "srfi1.scm:1466");
         this.lambda$Fn30 = var1;
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 30?this.lambda47(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda47(Object var1, Object var2) {
         return lists.member(var1, var2, this.$Eq) != Boolean.FALSE?var2:lists.cons(var1, var2);
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 30) {
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

   public class frame33 extends ModuleBody {

      Procedure $Eq;
      final ModuleMethod lambda$Fn31;
      final ModuleMethod lambda$Fn32;


      public frame33() {
         ModuleMethod var1 = new ModuleMethod(this, 32, (Object)null, 8194);
         var1.setProperty("source-location", "srfi1.scm:1476");
         this.lambda$Fn32 = var1;
         var1 = new ModuleMethod(this, 33, (Object)null, 8194);
         var1.setProperty("source-location", "srfi1.scm:1471");
         this.lambda$Fn31 = var1;
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         switch(var1.selector) {
         case 32:
            return this.lambda49(var2, var3);
         case 33:
            return this.lambda48(var2, var3);
         default:
            return super.apply2(var1, var2, var3);
         }
      }

      Object lambda48(Object var1, Object var2) {
         if(!lists.isNull(var1)) {
            if(lists.isNull(var2)) {
               return var1;
            }

            if(var1 != var2) {
               return srfi1.fold$V(this.lambda$Fn32, var2, var1, new Object[0]);
            }
         }

         return var2;
      }

      Object lambda49(Object var1, Object var2) {
         srfi1.frame34 var3 = new srfi1.frame34();
         var3.staticLink = this;
         var3.elt = var1;
         return srfi1.any$V(var3.lambda$Fn33, var2, new Object[0]) != Boolean.FALSE?var2:lists.cons(var3.elt, var2);
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         switch(var1.selector) {
         case 32:
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         case 33:
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         default:
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame34 extends ModuleBody {

      Object elt;
      final ModuleMethod lambda$Fn33;
      srfi1.frame33 staticLink;


      public frame34() {
         ModuleMethod var1 = new ModuleMethod(this, 31, (Object)null, 4097);
         var1.setProperty("source-location", "srfi1.scm:1476");
         this.lambda$Fn33 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 31?this.lambda50(var2):super.apply1(var1, var2);
      }

      Object lambda50(Object var1) {
         return this.staticLink.$Eq.apply2(var1, this.elt);
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         if(var1.selector == 31) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return super.match1(var1, var2, var3);
         }
      }
   }

   public class frame35 extends ModuleBody {

      Procedure $Eq;
      final ModuleMethod lambda$Fn34;
      final ModuleMethod lambda$Fn35;


      public frame35() {
         ModuleMethod var1 = new ModuleMethod(this, 35, (Object)null, 8194);
         var1.setProperty("source-location", "srfi1.scm:1488");
         this.lambda$Fn35 = var1;
         var1 = new ModuleMethod(this, 36, (Object)null, 8194);
         var1.setProperty("source-location", "srfi1.scm:1483");
         this.lambda$Fn34 = var1;
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         switch(var1.selector) {
         case 35:
            return this.lambda52(var2, var3);
         case 36:
            return this.lambda51(var2, var3);
         default:
            return super.apply2(var1, var2, var3);
         }
      }

      Object lambda51(Object var1, Object var2) {
         if(!lists.isNull(var1)) {
            if(lists.isNull(var2)) {
               return var1;
            }

            if(var1 != var2) {
               return srfi1.pairFold$V(this.lambda$Fn35, var2, var1, new Object[0]);
            }
         }

         return var2;
      }

      Object lambda52(Object var1, Object var2) {
         srfi1.frame36 var3 = new srfi1.frame36();
         var3.staticLink = this;
         var3.elt = lists.car.apply1(var1);
         if(srfi1.any$V(var3.lambda$Fn36, var2, new Object[0]) != Boolean.FALSE) {
            return var2;
         } else {
            Pair var5;
            try {
               var5 = (Pair)var1;
            } catch (ClassCastException var4) {
               throw new WrongType(var4, "set-cdr!", 1, var1);
            }

            lists.setCdr$Ex(var5, var2);
            return var1;
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         switch(var1.selector) {
         case 35:
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         case 36:
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         default:
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame36 extends ModuleBody {

      Object elt;
      final ModuleMethod lambda$Fn36;
      srfi1.frame35 staticLink;


      public frame36() {
         ModuleMethod var1 = new ModuleMethod(this, 34, (Object)null, 4097);
         var1.setProperty("source-location", "srfi1.scm:1490");
         this.lambda$Fn36 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 34?this.lambda53(var2):super.apply1(var1, var2);
      }

      Object lambda53(Object var1) {
         return this.staticLink.$Eq.apply2(var1, this.elt);
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         if(var1.selector == 34) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return super.match1(var1, var2, var3);
         }
      }
   }

   public class frame37 extends ModuleBody {

      Procedure $Eq;
      final ModuleMethod lambda$Fn37;
      Object lists;


      public frame37() {
         ModuleMethod var1 = new ModuleMethod(this, 38, (Object)null, 4097);
         var1.setProperty("source-location", "srfi1.scm:1501");
         this.lambda$Fn37 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 38?this.lambda54(var2):super.apply1(var1, var2);
      }

      Object lambda54(Object var1) {
         srfi1.frame38 var2 = new srfi1.frame38();
         var2.staticLink = this;
         var2.x = var1;
         return srfi1.every$V(var2.lambda$Fn38, this.lists, new Object[0]);
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         if(var1.selector == 38) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return super.match1(var1, var2, var3);
         }
      }
   }

   public class frame38 extends ModuleBody {

      final ModuleMethod lambda$Fn38;
      srfi1.frame37 staticLink;
      Object x;


      public frame38() {
         ModuleMethod var1 = new ModuleMethod(this, 37, (Object)null, 4097);
         var1.setProperty("source-location", "srfi1.scm:1502");
         this.lambda$Fn38 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 37?this.lambda55(var2):super.apply1(var1, var2);
      }

      Object lambda55(Object var1) {
         return lists.member(this.x, var1, this.staticLink.$Eq);
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         if(var1.selector == 37) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return super.match1(var1, var2, var3);
         }
      }
   }

   public class frame39 extends ModuleBody {

      Procedure $Eq;
      final ModuleMethod lambda$Fn39;
      Object lists;


      public frame39() {
         ModuleMethod var1 = new ModuleMethod(this, 40, (Object)null, 4097);
         var1.setProperty("source-location", "srfi1.scm:1509");
         this.lambda$Fn39 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 40?this.lambda56(var2):super.apply1(var1, var2);
      }

      Object lambda56(Object var1) {
         srfi1.frame40 var2 = new srfi1.frame40();
         var2.staticLink = this;
         var2.x = var1;
         return srfi1.every$V(var2.lambda$Fn40, this.lists, new Object[0]);
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         if(var1.selector == 40) {
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

      Object elt;
      final ModuleMethod lambda$Fn5 = new ModuleMethod(this, 5, (Object)null, 0);
      final ModuleMethod lambda$Fn6;
      Object lis;


      public frame4() {
         ModuleMethod var1 = new ModuleMethod(this, 6, (Object)null, 16388);
         var1.setProperty("source-location", "srfi1.scm:644");
         this.lambda$Fn6 = var1;
      }

      public Object apply0(ModuleMethod var1) {
         return var1.selector == 5?this.lambda9():super.apply0(var1);
      }

      public Object apply4(ModuleMethod var1, Object var2, Object var3, Object var4, Object var5) {
         return var1.selector == 6?this.lambda10(var2, var3, var4, var5):super.apply4(var1, var2, var3, var4, var5);
      }

      Object lambda10(Object var1, Object var2, Object var3, Object var4) {
         return misc.values(new Object[]{lists.cons(lists.car.apply1(this.elt), var1), lists.cons(lists.cadr.apply1(this.elt), var2), lists.cons(lists.caddr.apply1(this.elt), var3), lists.cons(lists.cadddr.apply1(this.elt), var4)});
      }

      Object lambda9() {
         return srfi1.frame3.lambda8recur(lists.cdr.apply1(this.lis));
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 5) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match4(ModuleMethod var1, Object var2, Object var3, Object var4, Object var5, CallContext var6) {
         if(var1.selector == 6) {
            var6.value1 = var2;
            var6.value2 = var3;
            var6.value3 = var4;
            var6.value4 = var5;
            var6.proc = var1;
            var6.pc = 4;
            return 0;
         } else {
            return super.match4(var1, var2, var3, var4, var5, var6);
         }
      }
   }

   public class frame40 extends ModuleBody {

      final ModuleMethod lambda$Fn40;
      srfi1.frame39 staticLink;
      Object x;


      public frame40() {
         ModuleMethod var1 = new ModuleMethod(this, 39, (Object)null, 4097);
         var1.setProperty("source-location", "srfi1.scm:1510");
         this.lambda$Fn40 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 39?this.lambda57(var2):super.apply1(var1, var2);
      }

      Object lambda57(Object var1) {
         return lists.member(this.x, var1, this.staticLink.$Eq);
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         if(var1.selector == 39) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return super.match1(var1, var2, var3);
         }
      }
   }

   public class frame41 extends ModuleBody {

      Procedure $Eq;
      final ModuleMethod lambda$Fn41;
      Object lists;


      public frame41() {
         ModuleMethod var1 = new ModuleMethod(this, 42, (Object)null, 4097);
         var1.setProperty("source-location", "srfi1.scm:1518");
         this.lambda$Fn41 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 42?this.lambda58(var2):super.apply1(var1, var2);
      }

      Object lambda58(Object var1) {
         srfi1.frame42 var2 = new srfi1.frame42();
         var2.staticLink = this;
         var2.x = var1;
         return srfi1.every$V(var2.lambda$Fn42, this.lists, new Object[0]);
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         if(var1.selector == 42) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return super.match1(var1, var2, var3);
         }
      }
   }

   public class frame42 extends ModuleBody {

      final ModuleMethod lambda$Fn42;
      srfi1.frame41 staticLink;
      Object x;


      public frame42() {
         ModuleMethod var1 = new ModuleMethod(this, 41, (Object)null, 4097);
         var1.setProperty("source-location", "srfi1.scm:1519");
         this.lambda$Fn42 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 41?(this.lambda59(var2)?Boolean.TRUE:Boolean.FALSE):super.apply1(var1, var2);
      }

      boolean lambda59(Object var1) {
         byte var2;
         if(lists.member(this.x, var1, this.staticLink.$Eq) != Boolean.FALSE) {
            var2 = 1;
         } else {
            var2 = 0;
         }

         return (boolean)(var2 + 1 & 1);
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         if(var1.selector == 41) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return super.match1(var1, var2, var3);
         }
      }
   }

   public class frame43 extends ModuleBody {

      Procedure $Eq;
      final ModuleMethod lambda$Fn43;
      Object lists;


      public frame43() {
         ModuleMethod var1 = new ModuleMethod(this, 44, (Object)null, 4097);
         var1.setProperty("source-location", "srfi1.scm:1527");
         this.lambda$Fn43 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 44?this.lambda60(var2):super.apply1(var1, var2);
      }

      Object lambda60(Object var1) {
         srfi1.frame44 var2 = new srfi1.frame44();
         var2.staticLink = this;
         var2.x = var1;
         return srfi1.every$V(var2.lambda$Fn44, this.lists, new Object[0]);
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         if(var1.selector == 44) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return super.match1(var1, var2, var3);
         }
      }
   }

   public class frame44 extends ModuleBody {

      final ModuleMethod lambda$Fn44;
      srfi1.frame43 staticLink;
      Object x;


      public frame44() {
         ModuleMethod var1 = new ModuleMethod(this, 43, (Object)null, 4097);
         var1.setProperty("source-location", "srfi1.scm:1528");
         this.lambda$Fn44 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 43?(this.lambda61(var2)?Boolean.TRUE:Boolean.FALSE):super.apply1(var1, var2);
      }

      boolean lambda61(Object var1) {
         byte var2;
         if(lists.member(this.x, var1, this.staticLink.$Eq) != Boolean.FALSE) {
            var2 = 1;
         } else {
            var2 = 0;
         }

         return (boolean)(var2 + 1 & 1);
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         if(var1.selector == 43) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return super.match1(var1, var2, var3);
         }
      }
   }

   public class frame45 extends ModuleBody {

      Procedure $Eq;
      final ModuleMethod lambda$Fn45;


      public frame45() {
         ModuleMethod var1 = new ModuleMethod(this, 48, (Object)null, 8194);
         var1.setProperty("source-location", "srfi1.scm:1534");
         this.lambda$Fn45 = var1;
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 48?this.lambda62(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda62(Object var1, Object var2) {
         srfi1.frame46 var3 = new srfi1.frame46();
         var3.staticLink = this;
         var3.b = var1;
         var3.a = var2;
         return call_with_values.callWithValues(var3.lambda$Fn46, var3.lambda$Fn47);
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 48) {
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

   public class frame46 extends ModuleBody {

      Object a;
      Object b;
      final ModuleMethod lambda$Fn46 = new ModuleMethod(this, 46, (Object)null, 0);
      final ModuleMethod lambda$Fn47;
      srfi1.frame45 staticLink;


      public frame46() {
         ModuleMethod var1 = new ModuleMethod(this, 47, (Object)null, 8194);
         var1.setProperty("source-location", "srfi1.scm:1544");
         this.lambda$Fn47 = var1;
      }

      public Object apply0(ModuleMethod var1) {
         return var1.selector == 46?this.lambda63():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 47?this.lambda64(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda63() {
         return srfi1.lsetDiff$PlIntersection$V(this.staticLink.$Eq, this.a, new Object[]{this.b});
      }

      Object lambda64(Object var1, Object var2) {
         srfi1.frame47 var3 = new srfi1.frame47();
         var3.staticLink = this;
         var3.a$Mnint$Mnb = var2;
         return lists.isNull(var1)?srfi1.lsetDifference$V(this.staticLink.$Eq, this.b, new Object[]{this.a}):(lists.isNull(var3.a$Mnint$Mnb)?append.append$V(new Object[]{this.b, this.a}):srfi1.fold$V(var3.lambda$Fn48, var1, this.b, new Object[0]));
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 46) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 47) {
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

   public class frame47 extends ModuleBody {

      Object a$Mnint$Mnb;
      final ModuleMethod lambda$Fn48;
      srfi1.frame46 staticLink;


      public frame47() {
         ModuleMethod var1 = new ModuleMethod(this, 45, (Object)null, 8194);
         var1.setProperty("source-location", "srfi1.scm:1547");
         this.lambda$Fn48 = var1;
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 45?this.lambda65(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda65(Object var1, Object var2) {
         return lists.member(var1, this.a$Mnint$Mnb, this.staticLink.staticLink.$Eq) != Boolean.FALSE?var2:lists.cons(var1, var2);
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 45) {
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

   public class frame48 extends ModuleBody {

      Procedure $Eq;
      final ModuleMethod lambda$Fn49;


      public frame48() {
         ModuleMethod var1 = new ModuleMethod(this, 52, (Object)null, 8194);
         var1.setProperty("source-location", "srfi1.scm:1555");
         this.lambda$Fn49 = var1;
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 52?this.lambda66(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda66(Object var1, Object var2) {
         srfi1.frame49 var3 = new srfi1.frame49();
         var3.staticLink = this;
         var3.b = var1;
         var3.a = var2;
         return call_with_values.callWithValues(var3.lambda$Fn50, var3.lambda$Fn51);
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 52) {
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

   public class frame49 extends ModuleBody {

      Object a;
      Object b;
      final ModuleMethod lambda$Fn50 = new ModuleMethod(this, 50, (Object)null, 0);
      final ModuleMethod lambda$Fn51;
      srfi1.frame48 staticLink;


      public frame49() {
         ModuleMethod var1 = new ModuleMethod(this, 51, (Object)null, 8194);
         var1.setProperty("source-location", "srfi1.scm:1565");
         this.lambda$Fn51 = var1;
      }

      public Object apply0(ModuleMethod var1) {
         return var1.selector == 50?this.lambda67():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 51?this.lambda68(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda67() {
         return srfi1.lsetDiff$PlIntersection$Ex$V(this.staticLink.$Eq, this.a, new Object[]{this.b});
      }

      Object lambda68(Object var1, Object var2) {
         srfi1.frame50 var3 = new srfi1.frame50();
         var3.staticLink = this;
         var3.a$Mnint$Mnb = var2;
         return lists.isNull(var1)?srfi1.lsetDifference$Ex$V(this.staticLink.$Eq, this.b, new Object[]{this.a}):(lists.isNull(var3.a$Mnint$Mnb)?srfi1.append$Ex$V(new Object[]{this.b, this.a}):srfi1.pairFold$V(var3.lambda$Fn52, var1, this.b, new Object[0]));
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 50) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 51) {
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

   public class frame5 extends ModuleBody {

      public static Object lambda11recur(Object var0) {
         srfi1.frame6 var1 = new srfi1.frame6();
         var1.lis = var0;
         if(srfi1.isNullList(var1.lis) != Boolean.FALSE) {
            return misc.values(new Object[]{var1.lis, var1.lis, var1.lis, var1.lis, var1.lis});
         } else {
            var1.elt = lists.car.apply1(var1.lis);
            return call_with_values.callWithValues(var1.lambda$Fn7, var1.lambda$Fn8);
         }
      }
   }

   public class frame50 extends ModuleBody {

      Object a$Mnint$Mnb;
      final ModuleMethod lambda$Fn52;
      srfi1.frame49 staticLink;


      public frame50() {
         ModuleMethod var1 = new ModuleMethod(this, 49, (Object)null, 8194);
         var1.setProperty("source-location", "srfi1.scm:1568");
         this.lambda$Fn52 = var1;
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 49?this.lambda69(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda69(Object var1, Object var2) {
         if(lists.member(lists.car.apply1(var1), this.a$Mnint$Mnb, this.staticLink.staticLink.$Eq) != Boolean.FALSE) {
            return var2;
         } else {
            Pair var3;
            try {
               var3 = (Pair)var1;
            } catch (ClassCastException var4) {
               throw new WrongType(var4, "set-cdr!", 1, var1);
            }

            lists.setCdr$Ex(var3, var2);
            return var1;
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 49) {
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

   public class frame51 extends ModuleBody {

      Procedure $Eq;
      final ModuleMethod lambda$Fn53;
      LList lists;


      public frame51() {
         ModuleMethod var1 = new ModuleMethod(this, 54, (Object)null, 4097);
         var1.setProperty("source-location", "srfi1.scm:1579");
         this.lambda$Fn53 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 54?(this.lambda70(var2)?Boolean.TRUE:Boolean.FALSE):super.apply1(var1, var2);
      }

      boolean lambda70(Object var1) {
         byte var3 = 0;
         srfi1.frame52 var2 = new srfi1.frame52();
         var2.staticLink = this;
         var2.elt = var1;
         if(srfi1.any$V(var2.lambda$Fn54, this.lists, new Object[0]) != Boolean.FALSE) {
            var3 = 1;
         }

         return (boolean)(var3 + 1 & 1);
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         if(var1.selector == 54) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return super.match1(var1, var2, var3);
         }
      }
   }

   public class frame52 extends ModuleBody {

      Object elt;
      final ModuleMethod lambda$Fn54;
      srfi1.frame51 staticLink;


      public frame52() {
         ModuleMethod var1 = new ModuleMethod(this, 53, (Object)null, 4097);
         var1.setProperty("source-location", "srfi1.scm:1580");
         this.lambda$Fn54 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 53?this.lambda71(var2):super.apply1(var1, var2);
      }

      Object lambda71(Object var1) {
         return lists.member(this.elt, var1, this.staticLink.$Eq);
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         if(var1.selector == 53) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return super.match1(var1, var2, var3);
         }
      }
   }

   public class frame53 extends ModuleBody {

      Procedure $Eq;
      final ModuleMethod lambda$Fn55;
      LList lists;


      public frame53() {
         ModuleMethod var1 = new ModuleMethod(this, 56, (Object)null, 4097);
         var1.setProperty("source-location", "srfi1.scm:1587");
         this.lambda$Fn55 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 56?(this.lambda72(var2)?Boolean.TRUE:Boolean.FALSE):super.apply1(var1, var2);
      }

      boolean lambda72(Object var1) {
         byte var3 = 0;
         srfi1.frame54 var2 = new srfi1.frame54();
         var2.staticLink = this;
         var2.elt = var1;
         if(srfi1.any$V(var2.lambda$Fn56, this.lists, new Object[0]) != Boolean.FALSE) {
            var3 = 1;
         }

         return (boolean)(var3 + 1 & 1);
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         if(var1.selector == 56) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return super.match1(var1, var2, var3);
         }
      }
   }

   public class frame54 extends ModuleBody {

      Object elt;
      final ModuleMethod lambda$Fn56;
      srfi1.frame53 staticLink;


      public frame54() {
         ModuleMethod var1 = new ModuleMethod(this, 55, (Object)null, 4097);
         var1.setProperty("source-location", "srfi1.scm:1588");
         this.lambda$Fn56 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 55?this.lambda73(var2):super.apply1(var1, var2);
      }

      Object lambda73(Object var1) {
         return lists.member(this.elt, var1, this.staticLink.$Eq);
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         if(var1.selector == 55) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return super.match1(var1, var2, var3);
         }
      }
   }

   public class frame55 extends ModuleBody {

      Continuation abort;


      public Object lambda74recur(Object var1) {
         if(lists.isPair(var1)) {
            Object var2 = lists.car.apply1(var1);
            return srfi1.isNullList(var2) != Boolean.FALSE?this.abort.apply1(LList.Empty):lists.cons(lists.cdr.apply1(var2), this.lambda74recur(lists.cdr.apply1(var1)));
         } else {
            return LList.Empty;
         }
      }
   }

   public class frame56 extends ModuleBody {

      Object last$Mnelt;


      public Object lambda75recur(Object var1) {
         return lists.isPair(var1)?lists.cons(lists.caar.apply1(var1), this.lambda75recur(lists.cdr.apply1(var1))):LList.list1(this.last$Mnelt);
      }
   }

   public class frame57 extends ModuleBody {

      Continuation abort;


      public Object lambda76recur(Object var1) {
         srfi1.frame58 var2 = new srfi1.frame58();
         var2.staticLink = this;
         var2.lists = var1;
         return lists.isPair(var2.lists)?call_with_values.callWithValues(var2.lambda$Fn57, var2.lambda$Fn58):misc.values(new Object[]{LList.Empty, LList.Empty});
      }
   }

   public class frame58 extends ModuleBody {

      final ModuleMethod lambda$Fn57 = new ModuleMethod(this, 61, (Object)null, 0);
      final ModuleMethod lambda$Fn58;
      Object lists;
      srfi1.frame57 staticLink;


      public frame58() {
         ModuleMethod var1 = new ModuleMethod(this, 62, (Object)null, 8194);
         var1.setProperty("source-location", "srfi1.scm:762");
         this.lambda$Fn58 = var1;
      }

      public Object apply0(ModuleMethod var1) {
         return var1.selector == 61?this.lambda77():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 62?this.lambda78(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda77() {
         return srfi1.car$PlCdr(this.lists);
      }

      Object lambda78(Object var1, Object var2) {
         srfi1.frame59 var3 = new srfi1.frame59();
         var3.staticLink = this;
         var3.list = var1;
         var3.other$Mnlists = var2;
         return srfi1.isNullList(var3.list) != Boolean.FALSE?this.staticLink.abort.apply2(LList.Empty, LList.Empty):call_with_values.callWithValues(var3.lambda$Fn59, var3.lambda$Fn60);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 61) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 62) {
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

   public class frame59 extends ModuleBody {

      final ModuleMethod lambda$Fn59 = new ModuleMethod(this, 59, (Object)null, 0);
      final ModuleMethod lambda$Fn60;
      Object list;
      Object other$Mnlists;
      srfi1.frame58 staticLink;


      public frame59() {
         ModuleMethod var1 = new ModuleMethod(this, 60, (Object)null, 8194);
         var1.setProperty("source-location", "srfi1.scm:764");
         this.lambda$Fn60 = var1;
      }

      public Object apply0(ModuleMethod var1) {
         return var1.selector == 59?this.lambda79():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 60?this.lambda80(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda79() {
         return srfi1.car$PlCdr(this.list);
      }

      Object lambda80(Object var1, Object var2) {
         srfi1.frame60 var3 = new srfi1.frame60();
         var3.staticLink = this;
         var3.a = var1;
         var3.d = var2;
         return call_with_values.callWithValues(var3.lambda$Fn61, var3.lambda$Fn62);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 59) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 60) {
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

   public class frame6 extends ModuleBody {

      Object elt;
      final ModuleMethod lambda$Fn7 = new ModuleMethod(this, 7, (Object)null, 0);
      final ModuleMethod lambda$Fn8;
      Object lis;


      public frame6() {
         ModuleMethod var1 = new ModuleMethod(this, 8, (Object)null, 20485);
         var1.setProperty("source-location", "srfi1.scm:654");
         this.lambda$Fn8 = var1;
      }

      public Object apply0(ModuleMethod var1) {
         return var1.selector == 7?this.lambda12():super.apply0(var1);
      }

      public Object applyN(ModuleMethod var1, Object[] var2) {
         return var1.selector == 8?this.lambda13(var2[0], var2[1], var2[2], var2[3], var2[4]):super.applyN(var1, var2);
      }

      Object lambda12() {
         return srfi1.frame5.lambda11recur(lists.cdr.apply1(this.lis));
      }

      Object lambda13(Object var1, Object var2, Object var3, Object var4, Object var5) {
         return misc.values(new Object[]{lists.cons(lists.car.apply1(this.elt), var1), lists.cons(lists.cadr.apply1(this.elt), var2), lists.cons(lists.caddr.apply1(this.elt), var3), lists.cons(lists.cadddr.apply1(this.elt), var4), lists.cons(lists.car.apply1(lists.cddddr.apply1(this.elt)), var5)});
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 7) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int matchN(ModuleMethod var1, Object[] var2, CallContext var3) {
         if(var1.selector == 8) {
            var3.values = var2;
            var3.proc = var1;
            var3.pc = 5;
            return 0;
         } else {
            return super.matchN(var1, var2, var3);
         }
      }
   }

   public class frame60 extends ModuleBody {

      Object a;
      Object d;
      final ModuleMethod lambda$Fn61 = new ModuleMethod(this, 57, (Object)null, 0);
      final ModuleMethod lambda$Fn62;
      srfi1.frame59 staticLink;


      public frame60() {
         ModuleMethod var1 = new ModuleMethod(this, 58, (Object)null, 8194);
         var1.setProperty("source-location", "srfi1.scm:765");
         this.lambda$Fn62 = var1;
      }

      public Object apply0(ModuleMethod var1) {
         return var1.selector == 57?this.lambda81():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 58?this.lambda82(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda81() {
         return this.staticLink.staticLink.staticLink.lambda76recur(this.staticLink.other$Mnlists);
      }

      Object lambda82(Object var1, Object var2) {
         return misc.values(new Object[]{lists.cons(this.a, var1), lists.cons(this.d, var2)});
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 57) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 58) {
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

   public class frame61 extends ModuleBody {

      final ModuleMethod lambda$Fn63 = new ModuleMethod(this, 63, (Object)null, 0);
      Object lists;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 63?this.lambda83():super.apply0(var1);
      }

      Object lambda83() {
         return srfi1.$PcCars$PlCdrs(this.lists);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 63) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }
   }

   public class frame62 extends ModuleBody {

      Object cars$Mnfinal;


   }

   public class frame63 extends ModuleBody {

      Continuation abort;
      srfi1.frame62 staticLink;


      public Object lambda85recur(Object var1) {
         srfi1.frame64 var2 = new srfi1.frame64();
         var2.staticLink = this;
         var2.lists = var1;
         return lists.isPair(var2.lists)?call_with_values.callWithValues(var2.lambda$Fn65, var2.lambda$Fn66):misc.values(new Object[]{LList.list1(this.staticLink.cars$Mnfinal), LList.Empty});
      }
   }

   public class frame64 extends ModuleBody {

      final ModuleMethod lambda$Fn65 = new ModuleMethod(this, 68, (Object)null, 0);
      final ModuleMethod lambda$Fn66;
      Object lists;
      srfi1.frame63 staticLink;


      public frame64() {
         ModuleMethod var1 = new ModuleMethod(this, 69, (Object)null, 8194);
         var1.setProperty("source-location", "srfi1.scm:783");
         this.lambda$Fn66 = var1;
      }

      public Object apply0(ModuleMethod var1) {
         return var1.selector == 68?this.lambda86():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 69?this.lambda87(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda86() {
         return srfi1.car$PlCdr(this.lists);
      }

      Object lambda87(Object var1, Object var2) {
         srfi1.frame65 var3 = new srfi1.frame65();
         var3.staticLink = this;
         var3.list = var1;
         var3.other$Mnlists = var2;
         return srfi1.isNullList(var3.list) != Boolean.FALSE?this.staticLink.abort.apply2(LList.Empty, LList.Empty):call_with_values.callWithValues(var3.lambda$Fn67, var3.lambda$Fn68);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 68) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 69) {
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

   public class frame65 extends ModuleBody {

      final ModuleMethod lambda$Fn67 = new ModuleMethod(this, 66, (Object)null, 0);
      final ModuleMethod lambda$Fn68;
      Object list;
      Object other$Mnlists;
      srfi1.frame64 staticLink;


      public frame65() {
         ModuleMethod var1 = new ModuleMethod(this, 67, (Object)null, 8194);
         var1.setProperty("source-location", "srfi1.scm:785");
         this.lambda$Fn68 = var1;
      }

      public Object apply0(ModuleMethod var1) {
         return var1.selector == 66?this.lambda88():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 67?this.lambda89(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda88() {
         return srfi1.car$PlCdr(this.list);
      }

      Object lambda89(Object var1, Object var2) {
         srfi1.frame66 var3 = new srfi1.frame66();
         var3.staticLink = this;
         var3.a = var1;
         var3.d = var2;
         return call_with_values.callWithValues(var3.lambda$Fn69, var3.lambda$Fn70);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 66) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 67) {
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

   public class frame66 extends ModuleBody {

      Object a;
      Object d;
      final ModuleMethod lambda$Fn69 = new ModuleMethod(this, 64, (Object)null, 0);
      final ModuleMethod lambda$Fn70;
      srfi1.frame65 staticLink;


      public frame66() {
         ModuleMethod var1 = new ModuleMethod(this, 65, (Object)null, 8194);
         var1.setProperty("source-location", "srfi1.scm:786");
         this.lambda$Fn70 = var1;
      }

      public Object apply0(ModuleMethod var1) {
         return var1.selector == 64?this.lambda90():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 65?this.lambda91(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda90() {
         return this.staticLink.staticLink.staticLink.lambda85recur(this.staticLink.other$Mnlists);
      }

      Object lambda91(Object var1, Object var2) {
         return misc.values(new Object[]{lists.cons(this.a, var1), lists.cons(this.d, var2)});
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 64) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 65) {
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

   public class frame67 extends ModuleBody {

      public static Object lambda92recur(Object var0) {
         srfi1.frame68 var1 = new srfi1.frame68();
         var1.lists = var0;
         return lists.isPair(var1.lists)?call_with_values.callWithValues(var1.lambda$Fn71, var1.lambda$Fn72):misc.values(new Object[]{LList.Empty, LList.Empty});
      }
   }

   public class frame68 extends ModuleBody {

      final ModuleMethod lambda$Fn71 = new ModuleMethod(this, 74, (Object)null, 0);
      final ModuleMethod lambda$Fn72;
      Object lists;


      public frame68() {
         ModuleMethod var1 = new ModuleMethod(this, 75, (Object)null, 8194);
         var1.setProperty("source-location", "srfi1.scm:794");
         this.lambda$Fn72 = var1;
      }

      public Object apply0(ModuleMethod var1) {
         return var1.selector == 74?this.lambda93():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 75?this.lambda94(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda93() {
         return srfi1.car$PlCdr(this.lists);
      }

      Object lambda94(Object var1, Object var2) {
         srfi1.frame69 var3 = new srfi1.frame69();
         var3.staticLink = this;
         var3.list = var1;
         var3.other$Mnlists = var2;
         return call_with_values.callWithValues(var3.lambda$Fn73, var3.lambda$Fn74);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 74) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 75) {
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

   public class frame69 extends ModuleBody {

      final ModuleMethod lambda$Fn73 = new ModuleMethod(this, 72, (Object)null, 0);
      final ModuleMethod lambda$Fn74;
      Object list;
      Object other$Mnlists;
      srfi1.frame68 staticLink;


      public frame69() {
         ModuleMethod var1 = new ModuleMethod(this, 73, (Object)null, 8194);
         var1.setProperty("source-location", "srfi1.scm:795");
         this.lambda$Fn74 = var1;
      }

      public Object apply0(ModuleMethod var1) {
         return var1.selector == 72?this.lambda95():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 73?this.lambda96(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda95() {
         return srfi1.car$PlCdr(this.list);
      }

      Object lambda96(Object var1, Object var2) {
         srfi1.frame70 var3 = new srfi1.frame70();
         var3.staticLink = this;
         var3.a = var1;
         var3.d = var2;
         return call_with_values.callWithValues(var3.lambda$Fn75, var3.lambda$Fn76);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 72) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 73) {
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

   public class frame7 extends ModuleBody {

      Procedure kons;


      public Object lambda14lp(Object var1, Object var2) {
         srfi1.frame8 var3 = new srfi1.frame8();
         var3.staticLink = this;
         var3.lists = var1;
         var3.ans = var2;
         return call_with_values.callWithValues(var3.lambda$Fn9, var3.lambda$Fn10);
      }
   }

   public class frame70 extends ModuleBody {

      Object a;
      Object d;
      final ModuleMethod lambda$Fn75 = new ModuleMethod(this, 70, (Object)null, 0);
      final ModuleMethod lambda$Fn76;
      srfi1.frame69 staticLink;


      public frame70() {
         ModuleMethod var1 = new ModuleMethod(this, 71, (Object)null, 8194);
         var1.setProperty("source-location", "srfi1.scm:796");
         this.lambda$Fn76 = var1;
      }

      public Object apply0(ModuleMethod var1) {
         return var1.selector == 70?this.lambda97():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 71?this.lambda98(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda97() {
         return srfi1.frame67.lambda92recur(this.staticLink.other$Mnlists);
      }

      Object lambda98(Object var1, Object var2) {
         return misc.values(new Object[]{lists.cons(this.a, var1), lists.cons(this.d, var2)});
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 70) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 71) {
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

   public class frame71 extends ModuleBody {

      final ModuleMethod lambda$Fn77 = new ModuleMethod(this, 76, (Object)null, 0);
      Object lists;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 76?this.lambda99():super.apply0(var1);
      }

      Object lambda99() {
         return srfi1.$PcCars$PlCdrs$SlNoTest(this.lists);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 76) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }
   }

   public class frame72 extends ModuleBody {

      Object $Eq;
      final ModuleMethod lambda$Fn79;
      Object lis2;


      public frame72() {
         ModuleMethod var1 = new ModuleMethod(this, 77, (Object)null, 4097);
         var1.setProperty("source-location", "srfi1.scm:1443");
         this.lambda$Fn79 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 77?this.lambda101(var2):super.apply1(var1, var2);
      }

      Object lambda101(Object var1) {
         Object var3 = this.lis2;
         Object var2 = this.$Eq;

         Procedure var4;
         try {
            var4 = (Procedure)var2;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "member", 3, var2);
         }

         return lists.member(var1, var3, var4);
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         if(var1.selector == 77) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return super.match1(var1, var2, var3);
         }
      }
   }

   public class frame8 extends ModuleBody {

      Object ans;
      final ModuleMethod lambda$Fn10;
      final ModuleMethod lambda$Fn9 = new ModuleMethod(this, 9, (Object)null, 0);
      Object lists;
      srfi1.frame7 staticLink;


      public frame8() {
         ModuleMethod var1 = new ModuleMethod(this, 10, (Object)null, 8194);
         var1.setProperty("source-location", "srfi1.scm:859");
         this.lambda$Fn10 = var1;
      }

      public Object apply0(ModuleMethod var1) {
         return var1.selector == 9?this.lambda15():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 10?this.lambda16(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda15() {
         return srfi1.$PcCars$PlCdrs$Pl(this.lists, this.ans);
      }

      Object lambda16(Object var1, Object var2) {
         return lists.isNull(var1)?this.ans:this.staticLink.lambda14lp(var2, Scheme.apply.apply2(this.staticLink.kons, var1));
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 9) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 10) {
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

   public class frame9 extends ModuleBody {

      Object knil;
      Procedure kons;


      public Object lambda17recur(Object var1) {
         Object var2 = srfi1.$PcCdrs(var1);
         return lists.isNull(var2)?this.knil:Scheme.apply.apply2(this.kons, srfi1.$PcCars$Pl(var1, this.lambda17recur(var2)));
      }

      public Object lambda18recur(Object var1) {
         if(srfi1.isNullList(var1) != Boolean.FALSE) {
            return this.knil;
         } else {
            Object var2 = lists.car.apply1(var1);
            return this.kons.apply2(var2, this.lambda18recur(lists.cdr.apply1(var1)));
         }
      }
   }
}
