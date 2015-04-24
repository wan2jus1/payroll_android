package kawa.lib;

import gnu.expr.GenericProc;
import gnu.expr.Keyword;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.reflect.SlotSet;
import gnu.kawa.reflect.StaticFieldLocation;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.CallContext;
import gnu.mapping.Location;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import kawa.standard.Scheme;

public class lists extends ModuleBody {

   public static final Location $Prvt$define$Mnprocedure = StaticFieldLocation.make("kawa.lib.std_syntax", "define$Mnprocedure");
   public static final lists $instance = new lists();
   static final Keyword Lit0 = Keyword.make("setter");
   static final SimpleSymbol Lit1 = (SimpleSymbol)(new SimpleSymbol("car")).readResolve();
   static final SimpleSymbol Lit10 = (SimpleSymbol)(new SimpleSymbol("list-tail")).readResolve();
   static final SimpleSymbol Lit11 = (SimpleSymbol)(new SimpleSymbol("list-ref")).readResolve();
   static final SimpleSymbol Lit12 = (SimpleSymbol)(new SimpleSymbol("list?")).readResolve();
   static final SimpleSymbol Lit13 = (SimpleSymbol)(new SimpleSymbol("reverse!")).readResolve();
   static final SimpleSymbol Lit14 = (SimpleSymbol)(new SimpleSymbol("memq")).readResolve();
   static final SimpleSymbol Lit15 = (SimpleSymbol)(new SimpleSymbol("memv")).readResolve();
   static final SimpleSymbol Lit16 = (SimpleSymbol)(new SimpleSymbol("member")).readResolve();
   static final SimpleSymbol Lit17 = (SimpleSymbol)(new SimpleSymbol("assq")).readResolve();
   static final SimpleSymbol Lit18 = (SimpleSymbol)(new SimpleSymbol("assv")).readResolve();
   static final SimpleSymbol Lit19 = (SimpleSymbol)(new SimpleSymbol("assoc")).readResolve();
   static final SimpleSymbol Lit2 = (SimpleSymbol)(new SimpleSymbol("cdr")).readResolve();
   static final SimpleSymbol Lit3 = (SimpleSymbol)(new SimpleSymbol("pair?")).readResolve();
   static final SimpleSymbol Lit4 = (SimpleSymbol)(new SimpleSymbol("cons")).readResolve();
   static final SimpleSymbol Lit5 = (SimpleSymbol)(new SimpleSymbol("null?")).readResolve();
   static final SimpleSymbol Lit6 = (SimpleSymbol)(new SimpleSymbol("set-car!")).readResolve();
   static final SimpleSymbol Lit7 = (SimpleSymbol)(new SimpleSymbol("set-cdr!")).readResolve();
   static final SimpleSymbol Lit8 = (SimpleSymbol)(new SimpleSymbol("length")).readResolve();
   static final SimpleSymbol Lit9 = (SimpleSymbol)(new SimpleSymbol("reverse")).readResolve();
   public static final ModuleMethod assoc;
   public static final ModuleMethod assq;
   public static final ModuleMethod assv;
   public static final GenericProc caaaar;
   static final ModuleMethod caaaar$Fn28;
   public static final GenericProc caaadr;
   static final ModuleMethod caaadr$Fn30;
   public static final GenericProc caaar;
   static final ModuleMethod caaar$Fn12;
   public static final GenericProc caadar;
   static final ModuleMethod caadar$Fn32;
   public static final GenericProc caaddr;
   static final ModuleMethod caaddr$Fn34;
   public static final GenericProc caadr;
   static final ModuleMethod caadr$Fn14;
   public static final GenericProc caar;
   static final ModuleMethod caar$Fn4;
   public static final GenericProc cadaar;
   static final ModuleMethod cadaar$Fn36;
   public static final GenericProc cadadr;
   static final ModuleMethod cadadr$Fn38;
   public static final GenericProc cadar;
   static final ModuleMethod cadar$Fn16;
   public static final GenericProc caddar;
   static final ModuleMethod caddar$Fn40;
   public static final GenericProc cadddr;
   static final ModuleMethod cadddr$Fn42;
   public static final GenericProc caddr;
   static final ModuleMethod caddr$Fn18;
   public static final GenericProc cadr;
   static final ModuleMethod cadr$Fn6;
   public static final GenericProc car;
   static final ModuleMethod car$Fn1;
   public static final GenericProc cdaaar;
   static final ModuleMethod cdaaar$Fn44;
   public static final GenericProc cdaadr;
   static final ModuleMethod cdaadr$Fn46;
   public static final GenericProc cdaar;
   static final ModuleMethod cdaar$Fn20;
   public static final GenericProc cdadar;
   static final ModuleMethod cdadar$Fn48;
   public static final GenericProc cdaddr;
   static final ModuleMethod cdaddr$Fn50;
   public static final GenericProc cdadr;
   static final ModuleMethod cdadr$Fn22;
   public static final GenericProc cdar;
   static final ModuleMethod cdar$Fn8;
   public static final GenericProc cddaar;
   static final ModuleMethod cddaar$Fn52;
   public static final GenericProc cddadr;
   static final ModuleMethod cddadr$Fn54;
   public static final GenericProc cddar;
   static final ModuleMethod cddar$Fn24;
   public static final GenericProc cdddar;
   static final ModuleMethod cdddar$Fn56;
   public static final GenericProc cddddr;
   static final ModuleMethod cddddr$Fn58;
   public static final GenericProc cdddr;
   static final ModuleMethod cdddr$Fn26;
   public static final GenericProc cddr;
   static final ModuleMethod cddr$Fn10;
   public static final GenericProc cdr;
   static final ModuleMethod cdr$Fn2;
   public static final ModuleMethod cons;
   static final ModuleMethod lambda$Fn11;
   static final ModuleMethod lambda$Fn13;
   static final ModuleMethod lambda$Fn15;
   static final ModuleMethod lambda$Fn17;
   static final ModuleMethod lambda$Fn19;
   static final ModuleMethod lambda$Fn21;
   static final ModuleMethod lambda$Fn23;
   static final ModuleMethod lambda$Fn25;
   static final ModuleMethod lambda$Fn27;
   static final ModuleMethod lambda$Fn29;
   static final ModuleMethod lambda$Fn3;
   static final ModuleMethod lambda$Fn31;
   static final ModuleMethod lambda$Fn33;
   static final ModuleMethod lambda$Fn35;
   static final ModuleMethod lambda$Fn37;
   static final ModuleMethod lambda$Fn39;
   static final ModuleMethod lambda$Fn41;
   static final ModuleMethod lambda$Fn43;
   static final ModuleMethod lambda$Fn45;
   static final ModuleMethod lambda$Fn47;
   static final ModuleMethod lambda$Fn49;
   static final ModuleMethod lambda$Fn5;
   static final ModuleMethod lambda$Fn51;
   static final ModuleMethod lambda$Fn53;
   static final ModuleMethod lambda$Fn55;
   static final ModuleMethod lambda$Fn57;
   static final ModuleMethod lambda$Fn7;
   static final ModuleMethod lambda$Fn9;
   public static final ModuleMethod length;
   public static final ModuleMethod list$Mnref;
   public static final ModuleMethod list$Mntail;
   public static final ModuleMethod list$Qu;
   public static final ModuleMethod member;
   public static final ModuleMethod memq;
   public static final ModuleMethod memv;
   public static final ModuleMethod null$Qu;
   public static final ModuleMethod pair$Qu;
   public static final ModuleMethod reverse;
   public static final ModuleMethod reverse$Ex;
   public static final ModuleMethod set$Mncar$Ex;
   public static final ModuleMethod set$Mncdr$Ex;


   static {
      lists var0 = $instance;
      pair$Qu = new ModuleMethod(var0, 1, Lit3, 4097);
      cons = new ModuleMethod(var0, 2, Lit4, 8194);
      null$Qu = new ModuleMethod(var0, 3, Lit5, 4097);
      set$Mncar$Ex = new ModuleMethod(var0, 4, Lit6, 8194);
      set$Mncdr$Ex = new ModuleMethod(var0, 5, Lit7, 8194);
      ModuleMethod var1 = new ModuleMethod(var0, 6, "car", 4097);
      var1.setProperty("source-location", "lists.scm:31");
      car$Fn1 = var1;
      var1 = new ModuleMethod(var0, 7, "cdr", 4097);
      var1.setProperty("source-location", "lists.scm:36");
      cdr$Fn2 = var1;
      lambda$Fn3 = new ModuleMethod(var0, 8, (Object)null, 8194);
      caar$Fn4 = new ModuleMethod(var0, 9, "caar", 4097);
      lambda$Fn5 = new ModuleMethod(var0, 10, (Object)null, 8194);
      cadr$Fn6 = new ModuleMethod(var0, 11, "cadr", 4097);
      lambda$Fn7 = new ModuleMethod(var0, 12, (Object)null, 8194);
      cdar$Fn8 = new ModuleMethod(var0, 13, "cdar", 4097);
      lambda$Fn9 = new ModuleMethod(var0, 14, (Object)null, 8194);
      cddr$Fn10 = new ModuleMethod(var0, 15, "cddr", 4097);
      lambda$Fn11 = new ModuleMethod(var0, 16, (Object)null, 8194);
      caaar$Fn12 = new ModuleMethod(var0, 17, "caaar", 4097);
      lambda$Fn13 = new ModuleMethod(var0, 18, (Object)null, 8194);
      caadr$Fn14 = new ModuleMethod(var0, 19, "caadr", 4097);
      lambda$Fn15 = new ModuleMethod(var0, 20, (Object)null, 8194);
      cadar$Fn16 = new ModuleMethod(var0, 21, "cadar", 4097);
      lambda$Fn17 = new ModuleMethod(var0, 22, (Object)null, 8194);
      caddr$Fn18 = new ModuleMethod(var0, 23, "caddr", 4097);
      lambda$Fn19 = new ModuleMethod(var0, 24, (Object)null, 8194);
      cdaar$Fn20 = new ModuleMethod(var0, 25, "cdaar", 4097);
      lambda$Fn21 = new ModuleMethod(var0, 26, (Object)null, 8194);
      cdadr$Fn22 = new ModuleMethod(var0, 27, "cdadr", 4097);
      lambda$Fn23 = new ModuleMethod(var0, 28, (Object)null, 8194);
      cddar$Fn24 = new ModuleMethod(var0, 29, "cddar", 4097);
      lambda$Fn25 = new ModuleMethod(var0, 30, (Object)null, 8194);
      cdddr$Fn26 = new ModuleMethod(var0, 31, "cdddr", 4097);
      lambda$Fn27 = new ModuleMethod(var0, 32, (Object)null, 8194);
      caaaar$Fn28 = new ModuleMethod(var0, 33, "caaaar", 4097);
      lambda$Fn29 = new ModuleMethod(var0, 34, (Object)null, 8194);
      caaadr$Fn30 = new ModuleMethod(var0, 35, "caaadr", 4097);
      lambda$Fn31 = new ModuleMethod(var0, 36, (Object)null, 8194);
      caadar$Fn32 = new ModuleMethod(var0, 37, "caadar", 4097);
      lambda$Fn33 = new ModuleMethod(var0, 38, (Object)null, 8194);
      caaddr$Fn34 = new ModuleMethod(var0, 39, "caaddr", 4097);
      lambda$Fn35 = new ModuleMethod(var0, 40, (Object)null, 8194);
      cadaar$Fn36 = new ModuleMethod(var0, 41, "cadaar", 4097);
      lambda$Fn37 = new ModuleMethod(var0, 42, (Object)null, 8194);
      cadadr$Fn38 = new ModuleMethod(var0, 43, "cadadr", 4097);
      lambda$Fn39 = new ModuleMethod(var0, 44, (Object)null, 8194);
      caddar$Fn40 = new ModuleMethod(var0, 45, "caddar", 4097);
      lambda$Fn41 = new ModuleMethod(var0, 46, (Object)null, 8194);
      cadddr$Fn42 = new ModuleMethod(var0, 47, "cadddr", 4097);
      lambda$Fn43 = new ModuleMethod(var0, 48, (Object)null, 8194);
      cdaaar$Fn44 = new ModuleMethod(var0, 49, "cdaaar", 4097);
      lambda$Fn45 = new ModuleMethod(var0, 50, (Object)null, 8194);
      cdaadr$Fn46 = new ModuleMethod(var0, 51, "cdaadr", 4097);
      lambda$Fn47 = new ModuleMethod(var0, 52, (Object)null, 8194);
      cdadar$Fn48 = new ModuleMethod(var0, 53, "cdadar", 4097);
      lambda$Fn49 = new ModuleMethod(var0, 54, (Object)null, 8194);
      cdaddr$Fn50 = new ModuleMethod(var0, 55, "cdaddr", 4097);
      lambda$Fn51 = new ModuleMethod(var0, 56, (Object)null, 8194);
      cddaar$Fn52 = new ModuleMethod(var0, 57, "cddaar", 4097);
      lambda$Fn53 = new ModuleMethod(var0, 58, (Object)null, 8194);
      cddadr$Fn54 = new ModuleMethod(var0, 59, "cddadr", 4097);
      lambda$Fn55 = new ModuleMethod(var0, 60, (Object)null, 8194);
      cdddar$Fn56 = new ModuleMethod(var0, 61, "cdddar", 4097);
      lambda$Fn57 = new ModuleMethod(var0, 62, (Object)null, 8194);
      cddddr$Fn58 = new ModuleMethod(var0, 63, "cddddr", 4097);
      length = new ModuleMethod(var0, 64, Lit8, 4097);
      reverse = new ModuleMethod(var0, 65, Lit9, 4097);
      list$Mntail = new ModuleMethod(var0, 66, Lit10, 8194);
      list$Mnref = new ModuleMethod(var0, 67, Lit11, 8194);
      list$Qu = new ModuleMethod(var0, 68, Lit12, 4097);
      reverse$Ex = new ModuleMethod(var0, 69, Lit13, 4097);
      memq = new ModuleMethod(var0, 70, Lit14, 8194);
      memv = new ModuleMethod(var0, 71, Lit15, 8194);
      member = new ModuleMethod(var0, 72, Lit16, 12290);
      assq = new ModuleMethod(var0, 74, Lit17, 8194);
      assv = new ModuleMethod(var0, 75, Lit18, 8194);
      assoc = new ModuleMethod(var0, 76, Lit19, 12290);
      $instance.run();
   }

   public lists() {
      ModuleInfo.register(this);
   }

   public static Object assoc(Object var0, Object var1) {
      return assoc(var0, var1, Scheme.isEqual);
   }

   public static Object assoc(Object var0, Object var1, Procedure var2) {
      while(true) {
         Object var3;
         if(var1 == LList.Empty) {
            var3 = Boolean.FALSE;
         } else {
            var3 = car.apply1(var1);

            Pair var4;
            try {
               var4 = (Pair)var3;
            } catch (ClassCastException var5) {
               throw new WrongType(var5, "pair", -2, var3);
            }

            var3 = var4;
            if(var2.apply2(var4.getCar(), var0) == Boolean.FALSE) {
               var1 = cdr.apply1(var1);
               continue;
            }
         }

         return var3;
      }
   }

   public static Object assq(Object var0, Object var1) {
      while(true) {
         Object var2;
         if(var1 == LList.Empty) {
            var2 = Boolean.FALSE;
         } else {
            var2 = car.apply1(var1);

            Pair var3;
            try {
               var3 = (Pair)var2;
            } catch (ClassCastException var4) {
               throw new WrongType(var4, "pair", -2, var2);
            }

            var2 = var3;
            if(var3.getCar() != var0) {
               var1 = cdr.apply1(var1);
               continue;
            }
         }

         return var2;
      }
   }

   public static Object assv(Object var0, Object var1) {
      while(true) {
         Object var2;
         if(var1 == LList.Empty) {
            var2 = Boolean.FALSE;
         } else {
            var2 = car.apply1(var1);

            Pair var3;
            try {
               var3 = (Pair)var2;
            } catch (ClassCastException var4) {
               throw new WrongType(var4, "pair", -2, var2);
            }

            var2 = var3;
            if(Scheme.isEqv.apply2(var3.getCar(), var0) == Boolean.FALSE) {
               var1 = cdr.apply1(var1);
               continue;
            }
         }

         return var2;
      }
   }

   static Object caaaar(Object var0) {
      return ((Pair)((Pair)((Pair)((Pair)var0).getCar()).getCar()).getCar()).getCar();
   }

   static Object caaadr(Object var0) {
      return ((Pair)((Pair)((Pair)((Pair)var0).getCdr()).getCar()).getCar()).getCar();
   }

   static Object caaar(Object var0) {
      return ((Pair)((Pair)((Pair)var0).getCar()).getCar()).getCar();
   }

   static Object caadar(Object var0) {
      return ((Pair)((Pair)((Pair)((Pair)var0).getCar()).getCdr()).getCar()).getCar();
   }

   static Object caaddr(Object var0) {
      return ((Pair)((Pair)((Pair)((Pair)var0).getCdr()).getCdr()).getCar()).getCar();
   }

   static Object caadr(Object var0) {
      return ((Pair)((Pair)((Pair)var0).getCdr()).getCar()).getCar();
   }

   static Object caar(Object var0) {
      return ((Pair)((Pair)var0).getCar()).getCar();
   }

   static Object cadaar(Object var0) {
      return ((Pair)((Pair)((Pair)((Pair)var0).getCar()).getCar()).getCdr()).getCar();
   }

   static Object cadadr(Object var0) {
      return ((Pair)((Pair)((Pair)((Pair)var0).getCdr()).getCar()).getCdr()).getCar();
   }

   static Object cadar(Object var0) {
      return ((Pair)((Pair)((Pair)var0).getCar()).getCdr()).getCar();
   }

   static Object caddar(Object var0) {
      return ((Pair)((Pair)((Pair)((Pair)var0).getCar()).getCdr()).getCdr()).getCar();
   }

   static Object cadddr(Object var0) {
      return ((Pair)((Pair)((Pair)((Pair)var0).getCdr()).getCdr()).getCdr()).getCar();
   }

   static Object caddr(Object var0) {
      return ((Pair)((Pair)((Pair)var0).getCdr()).getCdr()).getCar();
   }

   static Object cadr(Object var0) {
      return ((Pair)((Pair)var0).getCdr()).getCar();
   }

   static Object car(Pair var0) {
      return var0.getCar();
   }

   static Object cdaaar(Object var0) {
      return ((Pair)((Pair)((Pair)((Pair)var0).getCar()).getCar()).getCar()).getCdr();
   }

   static Object cdaadr(Object var0) {
      return ((Pair)((Pair)((Pair)((Pair)var0).getCdr()).getCar()).getCar()).getCdr();
   }

   static Object cdaar(Object var0) {
      return ((Pair)((Pair)((Pair)var0).getCar()).getCar()).getCdr();
   }

   static Object cdadar(Object var0) {
      return ((Pair)((Pair)((Pair)((Pair)var0).getCar()).getCdr()).getCar()).getCdr();
   }

   static Object cdaddr(Object var0) {
      return ((Pair)((Pair)((Pair)((Pair)var0).getCdr()).getCdr()).getCar()).getCdr();
   }

   static Object cdadr(Object var0) {
      return ((Pair)((Pair)((Pair)var0).getCdr()).getCar()).getCdr();
   }

   static Object cdar(Object var0) {
      return ((Pair)((Pair)var0).getCar()).getCdr();
   }

   static Object cddaar(Object var0) {
      return ((Pair)((Pair)((Pair)((Pair)var0).getCar()).getCar()).getCdr()).getCdr();
   }

   static Object cddadr(Object var0) {
      return ((Pair)((Pair)((Pair)((Pair)var0).getCdr()).getCar()).getCdr()).getCdr();
   }

   static Object cddar(Object var0) {
      return ((Pair)((Pair)((Pair)var0).getCar()).getCdr()).getCdr();
   }

   static Object cdddar(Object var0) {
      return ((Pair)((Pair)((Pair)((Pair)var0).getCar()).getCdr()).getCdr()).getCdr();
   }

   static Object cddddr(Object var0) {
      return ((Pair)((Pair)((Pair)((Pair)var0).getCdr()).getCdr()).getCdr()).getCdr();
   }

   static Object cdddr(Object var0) {
      return ((Pair)((Pair)((Pair)var0).getCdr()).getCdr()).getCdr();
   }

   static Object cddr(Object var0) {
      return ((Pair)((Pair)var0).getCdr()).getCdr();
   }

   static Object cdr(Pair var0) {
      return var0.getCdr();
   }

   public static Pair cons(Object var0, Object var1) {
      return new Pair(var0, var1);
   }

   public static boolean isList(Object var0) {
      boolean var1 = false;
      if(LList.listLength(var0, false) >= 0) {
         var1 = true;
      }

      return var1;
   }

   public static boolean isNull(Object var0) {
      return var0 == LList.Empty;
   }

   public static boolean isPair(Object var0) {
      return var0 instanceof Pair;
   }

   static void lambda1(Object var0, Object var1) {
      SlotSet.set$Mnfield$Ex.apply3(((Pair)var0).getCar(), Lit1, var1);
   }

   static void lambda10(Object var0, Object var1) {
      SlotSet.set$Mnfield$Ex.apply3(((Pair)((Pair)var0).getCdr()).getCar(), Lit2, var1);
   }

   static void lambda11(Object var0, Object var1) {
      SlotSet.set$Mnfield$Ex.apply3(((Pair)((Pair)var0).getCar()).getCdr(), Lit2, var1);
   }

   static void lambda12(Object var0, Object var1) {
      SlotSet.set$Mnfield$Ex.apply3(((Pair)((Pair)var0).getCdr()).getCdr(), Lit2, var1);
   }

   static void lambda13(Object var0, Object var1) {
      SlotSet.set$Mnfield$Ex.apply3(((Pair)((Pair)((Pair)var0).getCar()).getCar()).getCar(), Lit1, var1);
   }

   static void lambda14(Object var0, Object var1) {
      SlotSet.set$Mnfield$Ex.apply3(((Pair)((Pair)((Pair)var0).getCdr()).getCar()).getCar(), Lit1, var1);
   }

   static void lambda15(Object var0, Object var1) {
      SlotSet.set$Mnfield$Ex.apply3(((Pair)((Pair)((Pair)var0).getCar()).getCdr()).getCar(), Lit1, var1);
   }

   static void lambda16(Object var0, Object var1) {
      SlotSet.set$Mnfield$Ex.apply3(((Pair)((Pair)((Pair)var0).getCdr()).getCdr()).getCar(), Lit1, var1);
   }

   static void lambda17(Object var0, Object var1) {
      SlotSet.set$Mnfield$Ex.apply3(((Pair)((Pair)((Pair)var0).getCar()).getCar()).getCdr(), Lit1, var1);
   }

   static void lambda18(Object var0, Object var1) {
      SlotSet.set$Mnfield$Ex.apply3(((Pair)((Pair)((Pair)var0).getCdr()).getCar()).getCdr(), Lit1, var1);
   }

   static void lambda19(Object var0, Object var1) {
      SlotSet.set$Mnfield$Ex.apply3(((Pair)((Pair)((Pair)var0).getCar()).getCdr()).getCdr(), Lit1, var1);
   }

   static void lambda2(Object var0, Object var1) {
      SlotSet.set$Mnfield$Ex.apply3(((Pair)var0).getCdr(), Lit1, var1);
   }

   static void lambda20(Object var0, Object var1) {
      SlotSet.set$Mnfield$Ex.apply3(((Pair)((Pair)((Pair)var0).getCdr()).getCdr()).getCdr(), Lit1, var1);
   }

   static void lambda21(Object var0, Object var1) {
      SlotSet.set$Mnfield$Ex.apply3(((Pair)((Pair)((Pair)var0).getCar()).getCar()).getCar(), Lit2, var1);
   }

   static void lambda22(Object var0, Object var1) {
      SlotSet.set$Mnfield$Ex.apply3(((Pair)((Pair)((Pair)var0).getCdr()).getCar()).getCar(), Lit2, var1);
   }

   static void lambda23(Object var0, Object var1) {
      SlotSet.set$Mnfield$Ex.apply3(((Pair)((Pair)((Pair)var0).getCar()).getCdr()).getCar(), Lit2, var1);
   }

   static void lambda24(Object var0, Object var1) {
      SlotSet.set$Mnfield$Ex.apply3(((Pair)((Pair)((Pair)var0).getCdr()).getCdr()).getCar(), Lit2, var1);
   }

   static void lambda25(Object var0, Object var1) {
      SlotSet.set$Mnfield$Ex.apply3(((Pair)((Pair)((Pair)var0).getCar()).getCar()).getCdr(), Lit2, var1);
   }

   static void lambda26(Object var0, Object var1) {
      SlotSet.set$Mnfield$Ex.apply3(((Pair)((Pair)((Pair)var0).getCdr()).getCar()).getCdr(), Lit2, var1);
   }

   static void lambda27(Object var0, Object var1) {
      SlotSet.set$Mnfield$Ex.apply3(((Pair)((Pair)((Pair)var0).getCar()).getCdr()).getCdr(), Lit2, var1);
   }

   static void lambda28(Object var0, Object var1) {
      SlotSet.set$Mnfield$Ex.apply3(((Pair)((Pair)((Pair)var0).getCdr()).getCdr()).getCdr(), Lit2, var1);
   }

   static void lambda3(Object var0, Object var1) {
      SlotSet.set$Mnfield$Ex.apply3(((Pair)var0).getCar(), Lit2, var1);
   }

   static void lambda4(Object var0, Object var1) {
      SlotSet.set$Mnfield$Ex.apply3(((Pair)var0).getCdr(), Lit2, var1);
   }

   static void lambda5(Object var0, Object var1) {
      SlotSet.set$Mnfield$Ex.apply3(((Pair)((Pair)var0).getCar()).getCar(), Lit1, var1);
   }

   static void lambda6(Object var0, Object var1) {
      SlotSet.set$Mnfield$Ex.apply3(((Pair)((Pair)var0).getCdr()).getCar(), Lit1, var1);
   }

   static void lambda7(Object var0, Object var1) {
      SlotSet.set$Mnfield$Ex.apply3(((Pair)((Pair)var0).getCar()).getCdr(), Lit1, var1);
   }

   static void lambda8(Object var0, Object var1) {
      SlotSet.set$Mnfield$Ex.apply3(((Pair)((Pair)var0).getCdr()).getCdr(), Lit1, var1);
   }

   static void lambda9(Object var0, Object var1) {
      SlotSet.set$Mnfield$Ex.apply3(((Pair)((Pair)var0).getCar()).getCar(), Lit2, var1);
   }

   public static int length(LList var0) {
      return LList.length(var0);
   }

   public static Object listRef(Object var0, int var1) {
      return car.apply1(listTail(var0, var1));
   }

   public static Object listTail(Object var0, int var1) {
      return LList.listTail(var0, var1);
   }

   public static Object member(Object var0, Object var1) {
      return member(var0, var1, Scheme.isEqual);
   }

   public static Object member(Object var0, Object var1, Procedure var2) {
      while(true) {
         boolean var4 = var1 instanceof Pair;
         if(!var4) {
            if(var4) {
               return Boolean.TRUE;
            }

            return Boolean.FALSE;
         }

         Pair var3;
         try {
            var3 = (Pair)var1;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "p", -2, var1);
         }

         if(var2.apply2(var0, var3.getCar()) != Boolean.FALSE) {
            return var1;
         }

         var1 = var3.getCdr();
      }
   }

   public static Object memq(Object var0, Object var1) {
      while(true) {
         boolean var3 = var1 instanceof Pair;
         if(!var3) {
            if(var3) {
               return Boolean.TRUE;
            }

            return Boolean.FALSE;
         }

         Pair var2;
         try {
            var2 = (Pair)var1;
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "p", -2, var1);
         }

         if(var0 == var2.getCar()) {
            return var1;
         }

         var1 = var2.getCdr();
      }
   }

   public static Object memv(Object var0, Object var1) {
      while(true) {
         boolean var3 = var1 instanceof Pair;
         if(!var3) {
            if(var3) {
               return Boolean.TRUE;
            }

            return Boolean.FALSE;
         }

         Pair var2;
         try {
            var2 = (Pair)var1;
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "p", -2, var1);
         }

         if(Scheme.isEqv.apply2(var0, var2.getCar()) != Boolean.FALSE) {
            return var1;
         }

         var1 = var2.getCdr();
      }
   }

   public static LList reverse(LList var0) {
      Object var1;
      Pair var2;
      for(var1 = LList.Empty; !isNull(var0); var1 = cons(car.apply1(var2), var1)) {
         try {
            var2 = (Pair)var0;
         } catch (ClassCastException var3) {
            throw new WrongType(var3, "pair", -2, var0);
         }

         var0 = cdr.apply1(var2);
      }

      return (LList)var1;
   }

   public static LList reverse$Ex(LList var0) {
      return LList.reverseInPlace(var0);
   }

   public static void setCar$Ex(Pair var0, Object var1) {
      var0.setCar(var1);
   }

   public static void setCdr$Ex(Pair var0, Object var1) {
      var0.setCdr(var1);
   }

   public Object apply1(ModuleMethod var1, Object var2) {
      LList var8;
      Pair var9;
      switch(var1.selector) {
      case 1:
         if(isPair(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 2:
      case 4:
      case 5:
      case 8:
      case 10:
      case 12:
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
      case 48:
      case 50:
      case 52:
      case 54:
      case 56:
      case 58:
      case 60:
      case 62:
      case 66:
      case 67:
      default:
         return super.apply1(var1, var2);
      case 3:
         if(isNull(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 6:
         try {
            var9 = (Pair)var2;
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "car", 1, var2);
         }

         return car(var9);
      case 7:
         try {
            var9 = (Pair)var2;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "cdr", 1, var2);
         }

         return cdr(var9);
      case 9:
         return caar(var2);
      case 11:
         return cadr(var2);
      case 13:
         return cdar(var2);
      case 15:
         return cddr(var2);
      case 17:
         return caaar(var2);
      case 19:
         return caadr(var2);
      case 21:
         return cadar(var2);
      case 23:
         return caddr(var2);
      case 25:
         return cdaar(var2);
      case 27:
         return cdadr(var2);
      case 29:
         return cddar(var2);
      case 31:
         return cdddr(var2);
      case 33:
         return caaaar(var2);
      case 35:
         return caaadr(var2);
      case 37:
         return caadar(var2);
      case 39:
         return caaddr(var2);
      case 41:
         return cadaar(var2);
      case 43:
         return cadadr(var2);
      case 45:
         return caddar(var2);
      case 47:
         return cadddr(var2);
      case 49:
         return cdaaar(var2);
      case 51:
         return cdaadr(var2);
      case 53:
         return cdadar(var2);
      case 55:
         return cdaddr(var2);
      case 57:
         return cddaar(var2);
      case 59:
         return cddadr(var2);
      case 61:
         return cdddar(var2);
      case 63:
         return cddddr(var2);
      case 64:
         try {
            var8 = (LList)var2;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "length", 1, var2);
         }

         return Integer.valueOf(length(var8));
      case 65:
         try {
            var8 = (LList)var2;
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "reverse", 1, var2);
         }

         return reverse(var8);
      case 68:
         if(isList(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 69:
         try {
            var8 = (LList)var2;
         } catch (ClassCastException var3) {
            throw new WrongType(var3, "reverse!", 1, var2);
         }

         return reverse$Ex(var8);
      }
   }

   public Object apply2(ModuleMethod var1, Object var2, Object var3) {
      int var4;
      Pair var9;
      switch(var1.selector) {
      case 2:
         return cons(var2, var3);
      case 3:
      case 6:
      case 7:
      case 9:
      case 11:
      case 13:
      case 15:
      case 17:
      case 19:
      case 21:
      case 23:
      case 25:
      case 27:
      case 29:
      case 31:
      case 33:
      case 35:
      case 37:
      case 39:
      case 41:
      case 43:
      case 45:
      case 47:
      case 49:
      case 51:
      case 53:
      case 55:
      case 57:
      case 59:
      case 61:
      case 63:
      case 64:
      case 65:
      case 68:
      case 69:
      case 73:
      default:
         return super.apply2(var1, var2, var3);
      case 4:
         try {
            var9 = (Pair)var2;
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "set-car!", 1, var2);
         }

         setCar$Ex(var9, var3);
         return Values.empty;
      case 5:
         try {
            var9 = (Pair)var2;
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "set-cdr!", 1, var2);
         }

         setCdr$Ex(var9, var3);
         return Values.empty;
      case 8:
         lambda1(var2, var3);
         return Values.empty;
      case 10:
         lambda2(var2, var3);
         return Values.empty;
      case 12:
         lambda3(var2, var3);
         return Values.empty;
      case 14:
         lambda4(var2, var3);
         return Values.empty;
      case 16:
         lambda5(var2, var3);
         return Values.empty;
      case 18:
         lambda6(var2, var3);
         return Values.empty;
      case 20:
         lambda7(var2, var3);
         return Values.empty;
      case 22:
         lambda8(var2, var3);
         return Values.empty;
      case 24:
         lambda9(var2, var3);
         return Values.empty;
      case 26:
         lambda10(var2, var3);
         return Values.empty;
      case 28:
         lambda11(var2, var3);
         return Values.empty;
      case 30:
         lambda12(var2, var3);
         return Values.empty;
      case 32:
         lambda13(var2, var3);
         return Values.empty;
      case 34:
         lambda14(var2, var3);
         return Values.empty;
      case 36:
         lambda15(var2, var3);
         return Values.empty;
      case 38:
         lambda16(var2, var3);
         return Values.empty;
      case 40:
         lambda17(var2, var3);
         return Values.empty;
      case 42:
         lambda18(var2, var3);
         return Values.empty;
      case 44:
         lambda19(var2, var3);
         return Values.empty;
      case 46:
         lambda20(var2, var3);
         return Values.empty;
      case 48:
         lambda21(var2, var3);
         return Values.empty;
      case 50:
         lambda22(var2, var3);
         return Values.empty;
      case 52:
         lambda23(var2, var3);
         return Values.empty;
      case 54:
         lambda24(var2, var3);
         return Values.empty;
      case 56:
         lambda25(var2, var3);
         return Values.empty;
      case 58:
         lambda26(var2, var3);
         return Values.empty;
      case 60:
         lambda27(var2, var3);
         return Values.empty;
      case 62:
         lambda28(var2, var3);
         return Values.empty;
      case 66:
         try {
            var4 = ((Number)var3).intValue();
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "list-tail", 2, var3);
         }

         return listTail(var2, var4);
      case 67:
         try {
            var4 = ((Number)var3).intValue();
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "list-ref", 2, var3);
         }

         return listRef(var2, var4);
      case 70:
         return memq(var2, var3);
      case 71:
         return memv(var2, var3);
      case 72:
         return member(var2, var3);
      case 74:
         return assq(var2, var3);
      case 75:
         return assv(var2, var3);
      case 76:
         return assoc(var2, var3);
      }
   }

   public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
      Procedure var7;
      switch(var1.selector) {
      case 72:
         try {
            var7 = (Procedure)var4;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "member", 3, var4);
         }

         return member(var2, var3, var7);
      case 76:
         try {
            var7 = (Procedure)var4;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "assoc", 3, var4);
         }

         return assoc(var2, var3, var7);
      default:
         return super.apply3(var1, var2, var3, var4);
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
      case 4:
      case 5:
      case 8:
      case 10:
      case 12:
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
      case 48:
      case 50:
      case 52:
      case 54:
      case 56:
      case 58:
      case 60:
      case 62:
      case 66:
      case 67:
      default:
         return super.match1(var1, var2, var3);
      case 3:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 6:
         if(!(var2 instanceof Pair)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 7:
         if(!(var2 instanceof Pair)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 9:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 11:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 13:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 15:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 17:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 19:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 21:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 23:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 25:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 27:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 29:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 31:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 33:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 35:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 37:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 39:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 41:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 43:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 45:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 47:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 49:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 51:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 53:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 55:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 57:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 59:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 61:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 63:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 64:
         if(var2 instanceof LList) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 65:
         if(var2 instanceof LList) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 68:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 69:
         if(var2 instanceof LList) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return -786431;
         }
      }
   }

   public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
      switch(var1.selector) {
      case 2:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 3:
      case 6:
      case 7:
      case 9:
      case 11:
      case 13:
      case 15:
      case 17:
      case 19:
      case 21:
      case 23:
      case 25:
      case 27:
      case 29:
      case 31:
      case 33:
      case 35:
      case 37:
      case 39:
      case 41:
      case 43:
      case 45:
      case 47:
      case 49:
      case 51:
      case 53:
      case 55:
      case 57:
      case 59:
      case 61:
      case 63:
      case 64:
      case 65:
      case 68:
      case 69:
      case 73:
      default:
         return super.match2(var1, var2, var3, var4);
      case 4:
         if(!(var2 instanceof Pair)) {
            return -786431;
         }

         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 5:
         if(!(var2 instanceof Pair)) {
            return -786431;
         }

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
      case 10:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 12:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 14:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 16:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 18:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 20:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 22:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 24:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 26:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 28:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 30:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 32:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 34:
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
      case 38:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 40:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 42:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 44:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 46:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 48:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 50:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 52:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 54:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 56:
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
      case 60:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 62:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 66:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 67:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 70:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 71:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 72:
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
      case 75:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 76:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      }
   }

   public int match3(ModuleMethod var1, Object var2, Object var3, Object var4, CallContext var5) {
      int var6 = -786429;
      switch(var1.selector) {
      case 72:
         var5.value1 = var2;
         var5.value2 = var3;
         if(var4 instanceof Procedure) {
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         }
         break;
      case 76:
         var5.value1 = var2;
         var5.value2 = var3;
         if(var4 instanceof Procedure) {
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         }
         break;
      default:
         var6 = super.match3(var1, var2, var3, var4, var5);
      }

      return var6;
   }

   public final void run(CallContext var1) {
      Consumer var2 = var1.consumer;
      car = new GenericProc("car");
      car.setProperties(new Object[]{Lit0, set$Mncar$Ex, car$Fn1});
      cdr = new GenericProc("cdr");
      cdr.setProperties(new Object[]{Lit0, set$Mncdr$Ex, cdr$Fn2});
      caar = new GenericProc("caar");
      caar.setProperties(new Object[]{Lit0, lambda$Fn3, caar$Fn4});
      cadr = new GenericProc("cadr");
      cadr.setProperties(new Object[]{Lit0, lambda$Fn5, cadr$Fn6});
      cdar = new GenericProc("cdar");
      cdar.setProperties(new Object[]{Lit0, lambda$Fn7, cdar$Fn8});
      cddr = new GenericProc("cddr");
      cddr.setProperties(new Object[]{Lit0, lambda$Fn9, cddr$Fn10});
      caaar = new GenericProc("caaar");
      caaar.setProperties(new Object[]{Lit0, lambda$Fn11, caaar$Fn12});
      caadr = new GenericProc("caadr");
      caadr.setProperties(new Object[]{Lit0, lambda$Fn13, caadr$Fn14});
      cadar = new GenericProc("cadar");
      cadar.setProperties(new Object[]{Lit0, lambda$Fn15, cadar$Fn16});
      caddr = new GenericProc("caddr");
      caddr.setProperties(new Object[]{Lit0, lambda$Fn17, caddr$Fn18});
      cdaar = new GenericProc("cdaar");
      cdaar.setProperties(new Object[]{Lit0, lambda$Fn19, cdaar$Fn20});
      cdadr = new GenericProc("cdadr");
      cdadr.setProperties(new Object[]{Lit0, lambda$Fn21, cdadr$Fn22});
      cddar = new GenericProc("cddar");
      cddar.setProperties(new Object[]{Lit0, lambda$Fn23, cddar$Fn24});
      cdddr = new GenericProc("cdddr");
      cdddr.setProperties(new Object[]{Lit0, lambda$Fn25, cdddr$Fn26});
      caaaar = new GenericProc("caaaar");
      caaaar.setProperties(new Object[]{Lit0, lambda$Fn27, caaaar$Fn28});
      caaadr = new GenericProc("caaadr");
      caaadr.setProperties(new Object[]{Lit0, lambda$Fn29, caaadr$Fn30});
      caadar = new GenericProc("caadar");
      caadar.setProperties(new Object[]{Lit0, lambda$Fn31, caadar$Fn32});
      caaddr = new GenericProc("caaddr");
      caaddr.setProperties(new Object[]{Lit0, lambda$Fn33, caaddr$Fn34});
      cadaar = new GenericProc("cadaar");
      cadaar.setProperties(new Object[]{Lit0, lambda$Fn35, cadaar$Fn36});
      cadadr = new GenericProc("cadadr");
      cadadr.setProperties(new Object[]{Lit0, lambda$Fn37, cadadr$Fn38});
      caddar = new GenericProc("caddar");
      caddar.setProperties(new Object[]{Lit0, lambda$Fn39, caddar$Fn40});
      cadddr = new GenericProc("cadddr");
      cadddr.setProperties(new Object[]{Lit0, lambda$Fn41, cadddr$Fn42});
      cdaaar = new GenericProc("cdaaar");
      cdaaar.setProperties(new Object[]{Lit0, lambda$Fn43, cdaaar$Fn44});
      cdaadr = new GenericProc("cdaadr");
      cdaadr.setProperties(new Object[]{Lit0, lambda$Fn45, cdaadr$Fn46});
      cdadar = new GenericProc("cdadar");
      cdadar.setProperties(new Object[]{Lit0, lambda$Fn47, cdadar$Fn48});
      cdaddr = new GenericProc("cdaddr");
      cdaddr.setProperties(new Object[]{Lit0, lambda$Fn49, cdaddr$Fn50});
      cddaar = new GenericProc("cddaar");
      cddaar.setProperties(new Object[]{Lit0, lambda$Fn51, cddaar$Fn52});
      cddadr = new GenericProc("cddadr");
      cddadr.setProperties(new Object[]{Lit0, lambda$Fn53, cddadr$Fn54});
      cdddar = new GenericProc("cdddar");
      cdddar.setProperties(new Object[]{Lit0, lambda$Fn55, cdddar$Fn56});
      cddddr = new GenericProc("cddddr");
      cddddr.setProperties(new Object[]{Lit0, lambda$Fn57, cddddr$Fn58});
   }
}
