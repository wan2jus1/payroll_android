package kawa.lib;

import gnu.expr.Expression;
import gnu.expr.IfExp;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.expr.Special;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.CallContext;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Values;
import gnu.math.IntNum;
import kawa.lang.Macro;
import kawa.lang.Quote;
import kawa.lang.SyntaxForms;
import kawa.lang.SyntaxPattern;
import kawa.lang.SyntaxRule;
import kawa.lang.SyntaxRules;
import kawa.lang.SyntaxTemplate;
import kawa.lang.TemplateScope;
import kawa.standard.syntax_case;
import kawa.standard.syntax_error;
import kawa.standard.try_catch;

public class prim_syntax extends ModuleBody {

   public static final prim_syntax $instance;
   static final SimpleSymbol Lit0;
   static final SyntaxRules Lit1;
   static final SyntaxRules Lit10;
   static final SimpleSymbol Lit11;
   static final SyntaxRules Lit12;
   static final SimpleSymbol Lit13 = (SimpleSymbol)(new SimpleSymbol("if")).readResolve();
   static final SyntaxPattern Lit14 = new SyntaxPattern("\f\u0007\f\u000f\f\u0017\b", new Object[0], 3);
   static final SyntaxTemplate Lit15 = new SyntaxTemplate("\u0001\u0001\u0001", "\u000b", new Object[0], 0);
   static final SyntaxTemplate Lit16 = new SyntaxTemplate("\u0001\u0001\u0001", "\u0013", new Object[0], 0);
   static final SyntaxPattern Lit17 = new SyntaxPattern("\f\u0007\f\u000f\f\u0017\f\u001f\b", new Object[0], 4);
   static final SyntaxTemplate Lit18 = new SyntaxTemplate("\u0001\u0001\u0001\u0001", "\u000b", new Object[0], 0);
   static final SyntaxTemplate Lit19 = new SyntaxTemplate("\u0001\u0001\u0001\u0001", "\u0013", new Object[0], 0);
   static final SimpleSymbol Lit2;
   static final SyntaxTemplate Lit20 = new SyntaxTemplate("\u0001\u0001\u0001\u0001", "\u001b", new Object[0], 0);
   static final SyntaxPattern Lit21 = new SyntaxPattern("\f\u0007\f\u000f\f\u0017\f\u001f\f\'+", new Object[0], 6);
   static final SyntaxTemplate Lit22 = new SyntaxTemplate("\u0001\u0001\u0001\u0001\u0001\u0000", "#", new Object[0], 0);
   static final SyntaxPattern Lit23 = new SyntaxPattern("\f\u0007\u000b", new Object[0], 2);
   static final SyntaxTemplate Lit24 = new SyntaxTemplate("\u0001\u0000", "\n", new Object[0], 0);
   static final SimpleSymbol Lit25 = (SimpleSymbol)(new SimpleSymbol("try-catch")).readResolve();
   static final SyntaxPattern Lit26 = new SyntaxPattern("\f\u0007\f\u000f-\f\u0017\f\u001f#\u0010\u0018\b", new Object[0], 5);
   static final SyntaxTemplate Lit27 = new SyntaxTemplate("\u0001\u0001\u0003\u0003\u0002", "\u000b", new Object[0], 0);
   static final SyntaxTemplate Lit28 = new SyntaxTemplate("\u0001\u0001\u0003\u0003\u0002", "(\b\u0015A\b\t\u0013\u0011\u0018\u0004\b\u001b\"", new Object[]{Lit49}, 1);
   static final SimpleSymbol Lit29 = (SimpleSymbol)(new SimpleSymbol("letrec")).readResolve();
   static final SyntaxRules Lit3;
   static final SyntaxPattern Lit30 = new SyntaxPattern("\f\u0007\f\u000f\u0013", new Object[0], 3);
   static final SyntaxTemplate Lit31 = new SyntaxTemplate("\u0001\u0001\u0000", "\u000b", new Object[0], 0);
   static final SyntaxTemplate Lit32 = new SyntaxTemplate("\u0001\u0001\u0000", "\u0018\u0004", new Object[]{PairWithPosition.make((SimpleSymbol)(new SimpleSymbol("%let")).readResolve(), LList.Empty, "prim_syntax.scm", 512011)}, 0);
   static final SyntaxTemplate Lit33 = new SyntaxTemplate("\u0001\u0001\u0000", "\u0012", new Object[0], 0);
   static final SyntaxPattern Lit34 = new SyntaxPattern("\b", new Object[0], 3);
   static final SyntaxPattern Lit35 = new SyntaxPattern(",\f\u001f\f\'\b+", new Object[0], 6);
   static final SyntaxTemplate Lit36 = new SyntaxTemplate("\u0001\u0001\u0000\u0001\u0001\u0000", "\t\u001b\u0018\u0004", new Object[]{PairWithPosition.make(Special.undefined, LList.Empty, "prim_syntax.scm", 450594)}, 0);
   static final SyntaxTemplate Lit37 = new SyntaxTemplate("\u0001\u0001\u0000\u0001\u0001\u0000", "\u0011\u0018\u0004\t\u001b\b#", new Object[]{Lit45}, 0);
   static final SyntaxTemplate Lit38 = new SyntaxTemplate("\u0001\u0001\u0000\u0001\u0001\u0000", "*", new Object[0], 0);
   static final SyntaxPattern Lit39 = new SyntaxPattern("L\f\u001f\f\'\f/\f7\b;", new Object[0], 8);
   static final SimpleSymbol Lit4;
   static final SyntaxTemplate Lit40 = new SyntaxTemplate("\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000", "\t\u001b\t#\t+\u0018\u0004", new Object[]{PairWithPosition.make(Special.undefined, LList.Empty, "prim_syntax.scm", 471102)}, 0);
   static final SyntaxTemplate Lit41 = new SyntaxTemplate("\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000", "\u0011\u0018\u0004\t\u001b\b3", new Object[]{Lit45}, 0);
   static final SyntaxTemplate Lit42 = new SyntaxTemplate("\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000", ":", new Object[0], 0);
   static final SyntaxPattern Lit43 = new SyntaxPattern("\u001c\f\u001f\b#", new Object[0], 5);
   static final SyntaxPattern Lit44 = new SyntaxPattern("\u001b", new Object[0], 4);
   static final SimpleSymbol Lit45 = (SimpleSymbol)(new SimpleSymbol("set!")).readResolve();
   static final SimpleSymbol Lit46 = (SimpleSymbol)(new SimpleSymbol("$lookup$")).readResolve();
   static final SimpleSymbol Lit47 = (SimpleSymbol)(new SimpleSymbol("kawa.lang.SyntaxForms")).readResolve();
   static final SimpleSymbol Lit48 = (SimpleSymbol)(new SimpleSymbol("quasiquote")).readResolve();
   static final SimpleSymbol Lit49 = (SimpleSymbol)(new SimpleSymbol("::")).readResolve();
   static final SyntaxRules Lit5;
   static final SimpleSymbol Lit50 = (SimpleSymbol)(new SimpleSymbol("%define")).readResolve();
   static final IntNum Lit51 = IntNum.make(9);
   static final IntNum Lit52 = IntNum.make(8);
   static final IntNum Lit53 = IntNum.make(5);
   static final IntNum Lit54 = IntNum.make(4);
   static final IntNum Lit55 = IntNum.make(1);
   static final IntNum Lit56 = IntNum.make(0);
   static final SimpleSymbol Lit57 = (SimpleSymbol)(new SimpleSymbol("%define-syntax")).readResolve();
   static final SimpleSymbol Lit58 = (SimpleSymbol)(new SimpleSymbol("lambda")).readResolve();
   static final SimpleSymbol Lit6;
   static final SyntaxRules Lit7;
   static final SimpleSymbol Lit8;
   static final SimpleSymbol Lit9;
   public static final Macro define;
   public static final Macro define$Mnconstant;
   public static final Macro define$Mnprivate;
   public static final Macro define$Mnsyntax;
   public static final Macro if;
   public static final Macro letrec;
   public static final Macro syntax$Mn$Grexpression;
   public static final Macro syntax$Mnbody$Mn$Grexpression;
   public static final ModuleMethod syntax$Mnerror;
   public static final Macro try$Mncatch;


   static {
      SimpleSymbol var0 = (SimpleSymbol)(new SimpleSymbol("syntax-body->expression")).readResolve();
      Lit11 = var0;
      SyntaxRule var1 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\b", new Object[0], 1), "\u0001", "\u0011\u0018\u0004\b\u0003", new Object[]{PairWithPosition.make(Lit46, Pair.make(Lit47, Pair.make(Pair.make(Lit48, Pair.make((SimpleSymbol)(new SimpleSymbol("rewriteBody")).readResolve(), LList.Empty)), LList.Empty)), "prim_syntax.scm", 270343)}, 0);
      Lit12 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var1}, 1);
      var0 = (SimpleSymbol)(new SimpleSymbol("syntax->expression")).readResolve();
      Lit9 = var0;
      var1 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\b", new Object[0], 1), "\u0001", "\u0011\u0018\u0004\b\u0003", new Object[]{PairWithPosition.make(Lit46, Pair.make(Lit47, Pair.make(Pair.make(Lit48, Pair.make((SimpleSymbol)(new SimpleSymbol("rewrite")).readResolve(), LList.Empty)), LList.Empty)), "prim_syntax.scm", 249863)}, 0);
      Lit10 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var1}, 1);
      Lit8 = (SimpleSymbol)(new SimpleSymbol("syntax-error")).readResolve();
      var0 = (SimpleSymbol)(new SimpleSymbol("define-constant")).readResolve();
      Lit6 = var0;
      SimpleSymbol var9 = Lit49;
      SimpleSymbol var2 = Lit46;
      SyntaxRule var3 = new SyntaxRule(new SyntaxPattern("\f\u0018\\\f\u0002\f\u0007,\f\u000f\f\u0017\b\b\f\n\f\u001f\f\'\b", new Object[]{Lit46, Lit49}, 5), "\u0001\u0001\u0001\u0001\u0001", "\u0011\u0018\u0004Q\u0011\u0018\f\t\u0003\b\t\u000b\b\u0013\u0011\u0018\u0014\t\u001b\b#", new Object[]{Lit50, Lit46, Lit51}, 0);
      SyntaxPattern var4 = new SyntaxPattern("\f\u0018\\\f\u0002\f\u0007,\f\u000f\f\u0017\b\b\f\u001f\b", new Object[]{Lit46}, 4);
      Object[] var5 = new Object[]{Lit50, Lit46, Lit52, null};
      SyntaxRule var14 = new SyntaxRule(var4, "\u0001\u0001\u0001\u0001", "\u0011\u0018\u0004Q\u0011\u0018\f\t\u0003\b\t\u000b\b\u0013\u0011\u0018\u0014\u0011\u0018\u001c\b\u001b", var5, 0);
      SyntaxRule var15 = new SyntaxRule(new SyntaxPattern("\f\u0018\u001c\f\u0007\u000b\u0013", new Object[0], 3), "\u0001\u0000\u0000", "\u0011\u0018\u0004\t\u0003\u0011\u0018\f\u0011\u0018\u0014\t\n\u0012", new Object[]{Lit50, IntNum.make(10), Boolean.TRUE}, 0);
      SyntaxRule var6 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u0002\f\u000f\f\u0017\b", new Object[]{Lit49}, 3), "\u0001\u0001\u0001", "\u0011\u0018\u0004\t\u0003\u0011\u0018\f\t\u000b\b\u0013", new Object[]{Lit50, Lit51}, 0);
      SyntaxPattern var7 = new SyntaxPattern("\f\u0018\f\u0007\f\u000f\b", new Object[0], 2);
      Object[] var8 = new Object[]{Lit50, Lit52, null};
      SyntaxRule var16 = new SyntaxRule(var7, "\u0001\u0001", "\u0011\u0018\u0004\t\u0003\u0011\u0018\f\u0011\u0018\u0014\b\u000b", var8, 0);
      Lit7 = new SyntaxRules(new Object[]{var0, var9, var2}, new SyntaxRule[]{var3, var14, var15, var6, var16}, 5);
      var0 = (SimpleSymbol)(new SimpleSymbol("define-private")).readResolve();
      Lit4 = var0;
      var9 = Lit49;
      var2 = Lit46;
      var3 = new SyntaxRule(new SyntaxPattern("\f\u0018\\\f\u0002\f\u0007,\f\u000f\f\u0017\b\b\f\n\f\u001f\f\'\b", new Object[]{Lit46, Lit49}, 5), "\u0001\u0001\u0001\u0001\u0001", "\u0011\u0018\u0004Q\u0011\u0018\f\t\u0003\b\t\u000b\b\u0013\u0011\u0018\u0014\t\u001b\b#", new Object[]{Lit50, Lit46, Lit53}, 0);
      var4 = new SyntaxPattern("\f\u0018\\\f\u0002\f\u0007,\f\u000f\f\u0017\b\b\f\u001f\b", new Object[]{Lit46}, 4);
      var5 = new Object[]{Lit50, Lit46, Lit54, null};
      var14 = new SyntaxRule(var4, "\u0001\u0001\u0001\u0001", "\u0011\u0018\u0004Q\u0011\u0018\f\t\u0003\b\t\u000b\b\u0013\u0011\u0018\u0014\u0011\u0018\u001c\b\u001b", var5, 0);
      var15 = new SyntaxRule(new SyntaxPattern("\f\u0018\u001c\f\u0007\u000b\u0013", new Object[0], 3), "\u0001\u0000\u0000", "\u0011\u0018\u0004\t\u0003\u0011\u0018\f\u0011\u0018\u0014\t\n\u0012", new Object[]{Lit50, IntNum.make(6), Boolean.TRUE}, 0);
      var6 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u0002\f\u000f\f\u0017\b", new Object[]{Lit49}, 3), "\u0001\u0001\u0001", "\u0011\u0018\u0004\t\u0003\u0011\u0018\f\t\u000b\b\u0013", new Object[]{Lit50, Lit53}, 0);
      var7 = new SyntaxPattern("\f\u0018\f\u0007\f\u000f\b", new Object[0], 2);
      var8 = new Object[]{Lit50, Lit54, null};
      var16 = new SyntaxRule(var7, "\u0001\u0001", "\u0011\u0018\u0004\t\u0003\u0011\u0018\f\u0011\u0018\u0014\b\u000b", var8, 0);
      Lit5 = new SyntaxRules(new Object[]{var0, var9, var2}, new SyntaxRule[]{var3, var14, var15, var6, var16}, 5);
      var0 = (SimpleSymbol)(new SimpleSymbol("define")).readResolve();
      Lit2 = var0;
      var9 = Lit49;
      var2 = Lit46;
      var3 = new SyntaxRule(new SyntaxPattern("\f\u0018\\\f\u0002\f\u0007,\f\u000f\f\u0017\b\b\f\n\f\u001f\f\'\b", new Object[]{Lit46, Lit49}, 5), "\u0001\u0001\u0001\u0001\u0001", "\u0011\u0018\u0004Q\u0011\u0018\f\t\u0003\b\t\u000b\b\u0013\u0011\u0018\u0014\t\u001b\b#", new Object[]{Lit50, Lit46, Lit55}, 0);
      var4 = new SyntaxPattern("\f\u0018\\\f\u0002\f\u0007,\f\u000f\f\u0017\b\b\f\u001f\b", new Object[]{Lit46}, 4);
      var5 = new Object[]{Lit50, Lit46, Lit56, null};
      var14 = new SyntaxRule(var4, "\u0001\u0001\u0001\u0001", "\u0011\u0018\u0004Q\u0011\u0018\f\t\u0003\b\t\u000b\b\u0013\u0011\u0018\u0014\u0011\u0018\u001c\b\u001b", var5, 0);
      var15 = new SyntaxRule(new SyntaxPattern("\f\u0018\u001c\f\u0007\u000b\u0013", new Object[0], 3), "\u0001\u0000\u0000", "\u0011\u0018\u0004\t\u0003\u0011\u0018\f\u0011\u0018\u0014\t\n\u0012", new Object[]{Lit50, IntNum.make(2), Boolean.TRUE}, 0);
      var6 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u0002\f\u000f\f\u0017\b", new Object[]{Lit49}, 3), "\u0001\u0001\u0001", "\u0011\u0018\u0004\t\u0003\u0011\u0018\f\t\u000b\b\u0013", new Object[]{Lit50, Lit55}, 0);
      var7 = new SyntaxPattern("\f\u0018\f\u0007\f\u000f\b", new Object[0], 2);
      var8 = new Object[]{Lit50, Lit56, null};
      var16 = new SyntaxRule(var7, "\u0001\u0001", "\u0011\u0018\u0004\t\u0003\u0011\u0018\f\u0011\u0018\u0014\b\u000b", var8, 0);
      Lit3 = new SyntaxRules(new Object[]{var0, var9, var2}, new SyntaxRule[]{var3, var14, var15, var6, var16}, 5);
      var0 = (SimpleSymbol)(new SimpleSymbol("define-syntax")).readResolve();
      Lit0 = var0;
      var9 = Lit46;
      SyntaxRule var12 = new SyntaxRule(new SyntaxPattern("\f\u0018l\\\f\u0002\f\u0007,\f\u000f\f\u0017\b\b\u001b#", new Object[]{Lit46}, 5), "\u0001\u0001\u0001\u0000\u0000", "\u0011\u0018\u0004Q\u0011\u0018\f\t\u0003\b\t\u000b\b\u0013\b\u0011\u0018\u0014\t\u001a\"", new Object[]{Lit57, Lit46, Lit58}, 0);
      var3 = new SyntaxRule(new SyntaxPattern("\f\u0018\\\f\u0002\f\u0007,\f\u000f\f\u0017\b\b\f\u001f\b", new Object[]{Lit46}, 4), "\u0001\u0001\u0001\u0001", "\u0011\u0018\u0004Q\u0011\u0018\f\t\u0003\b\t\u000b\b\u0013\b\u001b", new Object[]{Lit57, Lit46}, 0);
      var14 = new SyntaxRule(new SyntaxPattern("\f\u0018\u001c\f\u0007\u000b\u0013", new Object[0], 3), "\u0001\u0000\u0000", "\u0011\u0018\u0004\t\u0003\b\u0011\u0018\f\t\n\u0012", new Object[]{Lit57, Lit58}, 0);
      var15 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\b", new Object[0], 2), "\u0001\u0001", "\u0011\u0018\u0004\t\u0003\b\u000b", new Object[]{Lit57}, 0);
      Lit1 = new SyntaxRules(new Object[]{var0, var9}, new SyntaxRule[]{var12, var3, var14, var15}, 5);
      $instance = new prim_syntax();
      define$Mnsyntax = Macro.make(Lit0, Lit1, $instance);
      define = Macro.make(Lit2, Lit3, $instance);
      define$Mnprivate = Macro.make(Lit4, Lit5, $instance);
      define$Mnconstant = Macro.make(Lit6, Lit7, $instance);
      prim_syntax var10 = $instance;
      syntax$Mnerror = new ModuleMethod(var10, 1, Lit8, -4095);
      syntax$Mn$Grexpression = Macro.make(Lit9, Lit10, $instance);
      syntax$Mnbody$Mn$Grexpression = Macro.make(Lit11, Lit12, $instance);
      var9 = Lit13;
      ModuleMethod var13 = new ModuleMethod(var10, 2, (Object)null, 4097);
      var13.setProperty("source-location", "prim_syntax.scm:69");
      if = Macro.make(var9, var13, $instance);
      var9 = Lit25;
      var13 = new ModuleMethod(var10, 3, (Object)null, 4097);
      var13.setProperty("source-location", "prim_syntax.scm:89");
      try$Mncatch = Macro.make(var9, var13, $instance);
      var9 = Lit29;
      ModuleMethod var11 = new ModuleMethod(var10, 4, (Object)null, 4097);
      var11.setProperty("source-location", "prim_syntax.scm:98");
      letrec = Macro.make(var9, var11, $instance);
      $instance.run();
   }

   public prim_syntax() {
      ModuleInfo.register(this);
   }

   static Object lambda1(Object var0) {
      Object[] var1 = SyntaxPattern.allocVars(6, (Object[])null);
      TemplateScope var2;
      TemplateScope var4;
      Expression var8;
      if(Lit14.match(var0, var1, 0)) {
         var4 = TemplateScope.make();
         var8 = SyntaxForms.rewrite(Lit15.execute(var1, var4));
         var2 = TemplateScope.make();
         return new IfExp(var8, SyntaxForms.rewrite(Lit16.execute(var1, var2)), (Expression)null);
      } else if(Lit17.match(var0, var1, 0)) {
         var4 = TemplateScope.make();
         var8 = SyntaxForms.rewrite(Lit18.execute(var1, var4));
         var2 = TemplateScope.make();
         Expression var7 = SyntaxForms.rewrite(Lit19.execute(var1, var2));
         TemplateScope var3 = TemplateScope.make();
         return new IfExp(var8, var7, SyntaxForms.rewrite(Lit20.execute(var1, var3)));
      } else {
         Object[] var5;
         Object var6;
         if(Lit21.match(var0, var1, 0)) {
            var4 = TemplateScope.make();
            var6 = Lit22.execute(var1, var4);
            if("too many expressions for \'if\'" instanceof Object[]) {
               var5 = (Object[])"too many expressions for \'if\'";
            } else {
               var5 = new Object[]{"too many expressions for \'if\'"};
            }

            return syntaxError(var6, var5);
         } else if(Lit23.match(var0, var1, 0)) {
            var4 = TemplateScope.make();
            var6 = Lit24.execute(var1, var4);
            if("too few expressions for \'if\'" instanceof Object[]) {
               var5 = (Object[])"too few expressions for \'if\'";
            } else {
               var5 = new Object[]{"too few expressions for \'if\'"};
            }

            return syntaxError(var6, var5);
         } else {
            return syntax_case.error("syntax-case", var0);
         }
      }
   }

   static Object lambda2(Object var0) {
      Object[] var1 = SyntaxPattern.allocVars(5, (Object[])null);
      if(Lit26.match(var0, var1, 0)) {
         TemplateScope var3 = TemplateScope.make();
         var0 = Lit27.execute(var1, var3);
         TemplateScope var2 = TemplateScope.make();
         return try_catch.rewrite(var0, Lit28.execute(var1, var2));
      } else {
         return syntax_case.error("syntax-case", var0);
      }
   }

   static Object lambda3(Object var0) {
      prim_syntax.frame var1 = new prim_syntax.frame();
      LList var2 = LList.Empty;
      var1.out$Mninits = LList.Empty;
      var1.out$Mnbindings = var2;
      var1.$unnamed$0 = SyntaxPattern.allocVars(3, (Object[])null);
      if(Lit30.match(var0, var1.$unnamed$0, 0)) {
         TemplateScope var3 = TemplateScope.make();
         var1.lambda4processBinding(Lit31.execute(var1.$unnamed$0, var3));
         var1.out$Mnbindings = LList.reverseInPlace(var1.out$Mnbindings);
         var1.out$Mninits = LList.reverseInPlace(var1.out$Mninits);
         var3 = TemplateScope.make();
         return Quote.append$V(new Object[]{Lit32.execute(var1.$unnamed$0, var3), Quote.consX$V(new Object[]{var1.out$Mnbindings, Quote.append$V(new Object[]{var1.out$Mninits, Lit33.execute(var1.$unnamed$0, var3)})})});
      } else {
         return syntax_case.error("syntax-case", var0);
      }
   }

   public static Expression syntaxError(Object var0, Object ... var1) {
      return syntax_error.error(var0, var1);
   }

   public Object apply1(ModuleMethod var1, Object var2) {
      switch(var1.selector) {
      case 2:
         return lambda1(var2);
      case 3:
         return lambda2(var2);
      case 4:
         return lambda3(var2);
      default:
         return super.apply1(var1, var2);
      }
   }

   public Object applyN(ModuleMethod var1, Object[] var2) {
      if(var1.selector != 1) {
         return super.applyN(var1, var2);
      } else {
         Object var5 = var2[0];
         int var4 = var2.length - 1;
         Object[] var3 = new Object[var4];

         while(true) {
            --var4;
            if(var4 < 0) {
               return syntaxError(var5, var3);
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

   public int matchN(ModuleMethod var1, Object[] var2, CallContext var3) {
      if(var1.selector == 1) {
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      } else {
         return super.matchN(var1, var2, var3);
      }
   }

   public final void run(CallContext var1) {
      Consumer var2 = var1.consumer;
   }

   public class frame extends ModuleBody {

      Object[] $unnamed$0;
      Object out$Mnbindings;
      Object out$Mninits;


      public Object lambda4processBinding(Object var1) {
         Object[] var2 = SyntaxPattern.allocVars(8, this.$unnamed$0);
         if(prim_syntax.Lit34.match(var1, var2, 0)) {
            return Values.empty;
         } else {
            TemplateScope var3;
            if(prim_syntax.Lit35.match(var1, var2, 0)) {
               var3 = TemplateScope.make();
               this.out$Mnbindings = new Pair(prim_syntax.Lit36.execute(var2, var3), this.out$Mnbindings);
               var3 = TemplateScope.make();
               this.out$Mninits = new Pair(prim_syntax.Lit37.execute(var2, var3), this.out$Mninits);
               var3 = TemplateScope.make();
               return this.lambda4processBinding(prim_syntax.Lit38.execute(var2, var3));
            } else if(prim_syntax.Lit39.match(var1, var2, 0)) {
               var3 = TemplateScope.make();
               this.out$Mnbindings = new Pair(prim_syntax.Lit40.execute(var2, var3), this.out$Mnbindings);
               var3 = TemplateScope.make();
               this.out$Mninits = new Pair(prim_syntax.Lit41.execute(var2, var3), this.out$Mninits);
               var3 = TemplateScope.make();
               return this.lambda4processBinding(prim_syntax.Lit42.execute(var2, var3));
            } else if(prim_syntax.Lit43.match(var1, var2, 0)) {
               if("missing initializion in letrec" instanceof Object[]) {
                  var2 = (Object[])"missing initializion in letrec";
               } else {
                  var2 = new Object[]{"missing initializion in letrec"};
               }

               return syntax_error.error(var1, var2);
            } else if(prim_syntax.Lit44.match(var1, var2, 0)) {
               if("invalid bindings syntax in letrec" instanceof Object[]) {
                  var2 = (Object[])"invalid bindings syntax in letrec";
               } else {
                  var2 = new Object[]{"invalid bindings syntax in letrec"};
               }

               return syntax_error.error(var1, var2);
            } else {
               return syntax_case.error("syntax-case", var1);
            }
         }
      }
   }
}
