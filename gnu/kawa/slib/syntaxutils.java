package gnu.kawa.slib;

import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.BeginExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.IfExp;
import gnu.expr.Keyword;
import gnu.expr.LambdaExp;
import gnu.expr.Language;
import gnu.expr.LetExp;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.expr.NameLookup;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.SetExp;
import gnu.expr.Special;
import gnu.kawa.functions.AddOp;
import gnu.kawa.functions.Convert;
import gnu.kawa.functions.Format;
import gnu.kawa.functions.GetNamedPart;
import gnu.kawa.lispexpr.LangObjType;
import gnu.kawa.reflect.SlotGet;
import gnu.lists.Consumer;
import gnu.lists.EofClass;
import gnu.lists.LList;
import gnu.lists.PairWithPosition;
import gnu.mapping.CallContext;
import gnu.mapping.Environment;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import gnu.math.Numeric;
import gnu.text.Char;
import gnu.text.SourceMessages;
import kawa.lang.Macro;
import kawa.lang.Quote;
import kawa.lang.SyntaxPattern;
import kawa.lang.SyntaxRule;
import kawa.lang.SyntaxRules;
import kawa.lang.Translator;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.standard.Scheme;

public class syntaxutils extends ModuleBody {

   public static final Macro $Prvt$$Ex;
   public static final Macro $Prvt$typecase$Pc;
   public static final syntaxutils $instance;
   static final Keyword Lit0;
   static final PairWithPosition Lit1;
   static final PairWithPosition Lit10;
   static final PairWithPosition Lit11;
   static final PairWithPosition Lit12;
   static final SimpleSymbol Lit13;
   static final SyntaxRules Lit14;
   static final SimpleSymbol Lit15;
   static final SyntaxRules Lit16;
   static final SimpleSymbol Lit17 = (SimpleSymbol)(new SimpleSymbol("expand")).readResolve();
   static final SimpleSymbol Lit18 = (SimpleSymbol)(new SimpleSymbol("eql")).readResolve();
   static final SimpleSymbol Lit19 = (SimpleSymbol)(new SimpleSymbol("quote")).readResolve();
   static final Keyword Lit2;
   static final SimpleSymbol Lit20 = (SimpleSymbol)(new SimpleSymbol("or")).readResolve();
   static final SimpleSymbol Lit21 = (SimpleSymbol)(new SimpleSymbol("begin")).readResolve();
   static final SimpleSymbol Lit22 = (SimpleSymbol)(new SimpleSymbol("cond")).readResolve();
   static final SimpleSymbol Lit23 = (SimpleSymbol)(new SimpleSymbol("let")).readResolve();
   static final SimpleSymbol Lit24 = (SimpleSymbol)(new SimpleSymbol("else")).readResolve();
   static final SimpleSymbol Lit25 = (SimpleSymbol)(new SimpleSymbol("as")).readResolve();
   static final SimpleSymbol Lit26 = (SimpleSymbol)(new SimpleSymbol("lambda")).readResolve();
   static final PairWithPosition Lit3;
   static final PairWithPosition Lit4;
   static final PairWithPosition Lit5;
   static final PairWithPosition Lit6;
   static final IntNum Lit7;
   static final IntNum Lit8;
   static final PairWithPosition Lit9;
   public static final ModuleMethod expand;


   static {
      SimpleSymbol var0 = (SimpleSymbol)(new SimpleSymbol("!")).readResolve();
      Lit15 = var0;
      SyntaxRule var1 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\r\u0017\u0010\b\b", new Object[0], 3), "\u0001\u0001\u0003", "\u0011\u0018\u0004\t\u000b)\u0011\u0018\f\b\u0003\b\u0015\u0013", new Object[]{(SimpleSymbol)(new SimpleSymbol("invoke")).readResolve(), Lit19}, 1);
      Lit16 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var1}, 3);
      var0 = (SimpleSymbol)(new SimpleSymbol("typecase%")).readResolve();
      Lit13 = var0;
      SimpleSymbol var9 = Lit18;
      SimpleSymbol var2 = Lit20;
      SyntaxRule var3 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007<\f\u0002\r\u000f\b\b\b\r\u0017\u0010\b\b", new Object[]{Boolean.TRUE}, 3), "\u0001\u0003\u0003", "\u0011\u0018\u0004\b\r\u000b", new Object[]{Lit21}, 1);
      SyntaxRule var4 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\\,\f\u0002\f\u000f\b\r\u0017\u0010\b\b\r\u001f\u0018\b\b", new Object[]{Lit18}, 4), "\u0001\u0001\u0003\u0003", "\u0011\u0018\u0004yY\u0011\u0018\f\t\u0003\b\u0011\u0018\u0014\b\u000b\b\u0015\u0013\b\u0011\u0018\u001c\b\u0011\u0018$\t\u0003\b\u001d\u001b", new Object[]{Lit22, (SimpleSymbol)(new SimpleSymbol("eqv?")).readResolve(), Lit19, Lit24, Lit13}, 1);
      SyntaxRule var5 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\\,\f\u0002\f\u000f\b\r\u0017\u0010\b\b\r\u001f\u0018\b\b", new Object[]{Lit20}, 4), "\u0001\u0001\u0003\u0003", "\u0011\u0018\u0004\t\u0003)\t\u000b\b\u0015\u0013\b\u001d\u001b", new Object[]{Lit13}, 1);
      SyntaxRule var6 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007l<\f\u0002\r\u000f\b\b\b\r\u0017\u0010\b\b\r\u001f\u0018\b\b", new Object[]{Lit20}, 4), "\u0001\u0003\u0003\u0003", "\u0011\u0018\u0004\u0091\b\u0011\u0018\f\b\u0011\u0018\u0014\u0011\b\u0003\b\u0011\u0018\u001c\b\u0015\u0013\b\u0011\u0018$\t\u0003I\r\t\u000b\b\u0011\u0018\f\b\u0003\b\u0011\u0018,\b\u0011\u0018$\t\u0003\b\u001d\u001b", new Object[]{Lit23, (SimpleSymbol)(new SimpleSymbol("f")).readResolve(), Lit26, Lit21, Lit13, Boolean.TRUE}, 1);
      SyntaxRule var7 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007<\f\u000f\r\u0017\u0010\b\b\r\u001f\u0018\b\b", new Object[0], 4), "\u0001\u0001\u0003\u0003", "\u0011\u0018\u0004ñ9\u0011\u0018\f\t\u0003\b\u000b\b\u0011\u0018\u0014Q\b\t\u0003\u0011\u0018\u001c\t\u000b\b\u0003\b\u0011\u0018$\b\u0015\u0013\b\u0011\u0018,\b\u0011\u00184\t\u0003\b\u001d\u001b", new Object[]{Lit22, (SimpleSymbol)(new SimpleSymbol("instance?")).readResolve(), Lit23, (SimpleSymbol)(new SimpleSymbol("::")).readResolve(), Lit21, Lit24, Lit13}, 1);
      SyntaxRule var8 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\b", new Object[0], 1), "\u0001", "\u0011\u0018\u0004\u0011\u0018\f\t\u0003\b\u0011\u0018\u0014\u0011\u0018\u001c\b\u0011\u0018$\u0011\u0018,\b\u0003", new Object[]{(SimpleSymbol)(new SimpleSymbol("error")).readResolve(), "typecase% failed", Lit15, (SimpleSymbol)(new SimpleSymbol("getClass")).readResolve(), Lit25, (SimpleSymbol)(new SimpleSymbol("<object>")).readResolve()}, 0);
      Lit14 = new SyntaxRules(new Object[]{var0, var9, var2}, new SyntaxRule[]{var3, var4, var5, var6, var7, var8}, 4);
      Lit12 = PairWithPosition.make((SimpleSymbol)(new SimpleSymbol(":")).readResolve(), LList.Empty, "syntaxutils.scm", 634896);
      Lit11 = PairWithPosition.make(Lit25, LList.Empty, "syntaxutils.scm", 626704);
      Lit10 = PairWithPosition.make(Lit19, LList.Empty, "syntaxutils.scm", 552972);
      Lit9 = PairWithPosition.make(Lit23, LList.Empty, "syntaxutils.scm", 479236);
      Lit8 = IntNum.make(1);
      Lit7 = IntNum.make(0);
      Lit6 = PairWithPosition.make((SimpleSymbol)(new SimpleSymbol("if")).readResolve(), LList.Empty, "syntaxutils.scm", 417799);
      Lit5 = PairWithPosition.make(Lit21, LList.Empty, "syntaxutils.scm", 409627);
      Lit4 = PairWithPosition.make(Lit26, LList.Empty, "syntaxutils.scm", 376839);
      Lit3 = PairWithPosition.make((SimpleSymbol)(new SimpleSymbol("set")).readResolve(), LList.Empty, "syntaxutils.scm", 368647);
      Lit2 = Keyword.make("lang");
      Lit1 = PairWithPosition.make(Lit21, LList.Empty, "syntaxutils.scm", 278557);
      Lit0 = Keyword.make("env");
      $instance = new syntaxutils();
      $Prvt$typecase$Pc = Macro.make(Lit13, Lit14, $instance);
      $Prvt$$Ex = Macro.make(Lit15, Lit16, $instance);
      expand = new ModuleMethod($instance, 1, Lit17, -4095);
      $instance.run();
   }

   public syntaxutils() {
      ModuleInfo.register(this);
   }

   public static Object expand$V(Object var0, Object[] var1) {
      Object var2 = Keyword.searchForKeyword(var1, 0, Lit0);
      Object var3 = var2;
      if(var2 == Special.dfault) {
         var3 = misc.interactionEnvironment();
      }

      return unrewrite(rewriteForm$V(Quote.append$V(new Object[]{Lit1, Quote.consX$V(new Object[]{var0, LList.Empty})}), new Object[]{Lit0, var3}));
   }

   static Expression rewriteForm$V(Object var0, Object[] var1) {
      Object var3 = Keyword.searchForKeyword(var1, 0, Lit2);
      Object var2 = var3;
      if(var3 == Special.dfault) {
         var2 = Language.getDefaultLanguage();
      }

      var3 = Keyword.searchForKeyword(var1, 0, Lit0);
      Object var14 = var3;
      if(var3 == Special.dfault) {
         var14 = misc.interactionEnvironment();
      }

      Environment var19;
      try {
         var19 = (Environment)var14;
      } catch (ClassCastException var12) {
         throw new WrongType(var12, "gnu.expr.NameLookup.getInstance(gnu.mapping.Environment,gnu.expr.Language)", 1, var14);
      }

      Language var15;
      try {
         var15 = (Language)var2;
      } catch (ClassCastException var11) {
         throw new WrongType(var11, "gnu.expr.NameLookup.getInstance(gnu.mapping.Environment,gnu.expr.Language)", 2, var2);
      }

      NameLookup var16 = NameLookup.getInstance(var19, var15);
      SourceMessages var20 = new SourceMessages();

      Language var4;
      try {
         var4 = (Language)var2;
      } catch (ClassCastException var10) {
         throw new WrongType(var10, "kawa.lang.Translator.<init>(gnu.expr.Language,gnu.text.SourceMessages,gnu.expr.NameLookup)", 1, var2);
      }

      Translator var17 = new Translator(var4, var20, var16);
      var17.pushNewModule((String)null);
      Compilation var18 = Compilation.setSaveCurrent(var17);

      Expression var13;
      try {
         var13 = var17.rewrite(var0);
      } finally {
         Compilation.restoreCurrent(var18);
      }

      return var13;
   }

   static Object unrewrite(Expression var0) {
      syntaxutils.frame var1 = new syntaxutils.frame();
      Object var16;
      if(var0 instanceof LetExp) {
         LetExp var14;
         try {
            var14 = (LetExp)var0;
         } catch (ClassCastException var12) {
            throw new WrongType(var12, "exp", -2, var0);
         }

         var16 = unrewriteLet(var14);
      } else {
         if(var0 instanceof QuoteExp) {
            QuoteExp var24;
            try {
               var24 = (QuoteExp)var0;
            } catch (ClassCastException var5) {
               throw new WrongType(var5, "exp", -2, var0);
            }

            return unrewriteQuote(var24);
         }

         if(var0 instanceof SetExp) {
            SetExp var23;
            try {
               var23 = (SetExp)var0;
            } catch (ClassCastException var6) {
               throw new WrongType(var6, "exp", -2, var0);
            }

            return Quote.append$V(new Object[]{Lit3, Quote.consX$V(new Object[]{var23.getSymbol(), Quote.consX$V(new Object[]{unrewrite(var23.getNewValue()), LList.Empty})})});
         }

         if(var0 instanceof LambdaExp) {
            LambdaExp var17;
            try {
               var17 = (LambdaExp)var0;
            } catch (ClassCastException var7) {
               throw new WrongType(var7, "exp", -2, var0);
            }

            PairWithPosition var21 = Lit4;
            var1.pack = LList.Empty;

            for(Declaration var15 = var17.firstDecl(); var15 != null; var15 = var15.nextDecl()) {
               var1.pack = lists.cons(var15.getSymbol(), var1.pack);
            }

            return Quote.append$V(new Object[]{var21, Quote.consX$V(new Object[]{lists.reverse$Ex(var1.pack), Quote.consX$V(new Object[]{unrewrite(var17.body), LList.Empty})})});
         }

         if(var0 instanceof ReferenceExp) {
            ReferenceExp var22;
            try {
               var22 = (ReferenceExp)var0;
            } catch (ClassCastException var8) {
               throw new WrongType(var8, "exp", -2, var0);
            }

            return var22.getSymbol();
         }

         if(var0 instanceof ApplyExp) {
            ApplyExp var20;
            try {
               var20 = (ApplyExp)var0;
            } catch (ClassCastException var9) {
               throw new WrongType(var9, "exp", -2, var0);
            }

            return unrewriteApply(var20);
         }

         if(var0 instanceof BeginExp) {
            BeginExp var19;
            try {
               var19 = (BeginExp)var0;
            } catch (ClassCastException var10) {
               throw new WrongType(var10, "exp", -2, var0);
            }

            return Quote.append$V(new Object[]{Lit5, unrewrite$St(var19.getExpressions())});
         }

         var16 = var0;
         if(var0 instanceof IfExp) {
            IfExp var4;
            try {
               var4 = (IfExp)var0;
            } catch (ClassCastException var11) {
               throw new WrongType(var11, "exp", -2, var0);
            }

            PairWithPosition var18 = Lit6;
            Object var2 = unrewrite(var4.getTest());
            Object var3 = unrewrite(var4.getThenClause());
            var0 = var4.getElseClause();
            Object var13;
            if(var0 == null) {
               var13 = LList.Empty;
            } else {
               var13 = LList.list1(unrewrite(var0));
            }

            return Quote.append$V(new Object[]{var18, Quote.consX$V(new Object[]{var2, Quote.consX$V(new Object[]{var3, Quote.append$V(new Object[]{var13, LList.Empty})})})});
         }
      }

      return var16;
   }

   static Object unrewrite$St(Expression[] var0) {
      syntaxutils.frame0 var2 = new syntaxutils.frame0();
      var2.pack = LList.Empty;
      int var3 = var0.length;

      for(Object var1 = Lit7; Scheme.numEqu.apply2(var1, Integer.valueOf(var3)) == Boolean.FALSE; var1 = AddOp.$Pl.apply2(var1, Lit8)) {
         var2.pack = lists.cons(unrewrite(var0[((Number)var1).intValue()]), var2.pack);
      }

      return lists.reverse$Ex(var2.pack);
   }

   static Object unrewriteApply(ApplyExp var0) {
      Expression var2 = var0.getFunction();
      Object var3 = unrewrite$St(var0.getArgs());
      Declaration var8;
      if(var2 instanceof ReferenceExp) {
         ReferenceExp var1;
         try {
            var1 = (ReferenceExp)var2;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "fun", -2, var2);
         }

         var8 = var1.getBinding();
      } else {
         var8 = null;
      }

      Declaration var4 = Declaration.getDeclarationFromStatic("kawa.standard.Scheme", "applyToArgs");
      Object var7 = var0.getFunctionValue();
      byte var5;
      if(var8 == null) {
         var5 = 1;
      } else {
         var5 = 0;
      }

      int var9 = var5 + 1 & 1;
      if(var9 != 0) {
         label55: {
            if(var4 == null) {
               var5 = 1;
            } else {
               var5 = 0;
            }

            var9 = var5 + 1 & 1;
            if(var9 != 0) {
               if(SlotGet.getSlotValue(false, var8, "field", "field", "getField", "isField", Scheme.instance) != var4.field) {
                  break label55;
               }
            } else if(var9 == 0) {
               break label55;
            }

            return var3;
         }
      } else if(var9 != 0) {
         return var3;
      }

      if(var7 instanceof Convert) {
         var7 = Quote.append$V(new Object[]{Lit11, var3});
      } else if(var7 instanceof GetNamedPart) {
         var7 = Quote.append$V(new Object[]{Lit12, var3});
      } else {
         var7 = Boolean.FALSE;
      }

      return var7 != Boolean.FALSE?var7:Quote.consX$V(new Object[]{unrewrite(var2), var3});
   }

   static Object unrewriteLet(LetExp var0) {
      syntaxutils.frame1 var3 = new syntaxutils.frame1();
      PairWithPosition var4 = Lit9;
      var3.pack = LList.Empty;
      Declaration var2 = var0.firstDecl();

      for(Object var1 = Lit7; var2 != null; var1 = AddOp.$Pl.apply2(var1, Lit8)) {
         var3.pack = lists.cons(LList.list2(var2.getSymbol(), unrewrite(var0.inits[((Number)var1).intValue()])), var3.pack);
         var2 = var2.nextDecl();
      }

      return Quote.append$V(new Object[]{var4, Quote.consX$V(new Object[]{lists.reverse$Ex(var3.pack), Quote.consX$V(new Object[]{unrewrite(var0.body), LList.Empty})})});
   }

   static Object unrewriteQuote(QuoteExp var0) {
      Object var1 = var0.getValue();
      Object var10;
      if(Numeric.asNumericOrNull(var1) != null) {
         try {
            var10 = LangObjType.coerceNumeric(var1);
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "val", -2, var1);
         }
      } else {
         if(var1 instanceof Boolean) {
            Boolean var17;
            try {
               var17 = Boolean.FALSE;
            } catch (ClassCastException var3) {
               throw new WrongType(var3, "val", -2, var1);
            }

            boolean var2;
            if(var1 != var17) {
               var2 = true;
            } else {
               var2 = false;
            }

            if(var2) {
               var17 = Boolean.TRUE;
            } else {
               var17 = Boolean.FALSE;
            }

            return var17;
         }

         if(var1 instanceof Char) {
            try {
               Char var16 = (Char)var1;
               return var16;
            } catch (ClassCastException var4) {
               throw new WrongType(var4, "val", -2, var1);
            }
         }

         if(var1 instanceof Keyword) {
            try {
               Keyword var15 = (Keyword)var1;
               return var15;
            } catch (ClassCastException var5) {
               throw new WrongType(var5, "val", -2, var1);
            }
         }

         if(var1 instanceof CharSequence) {
            try {
               CharSequence var14 = (CharSequence)var1;
               return var14;
            } catch (ClassCastException var6) {
               throw new WrongType(var6, "val", -2, var1);
            }
         }

         var10 = var1;
         if(var1 != Special.undefined) {
            var10 = var1;
            if(var1 != EofClass.eofValue) {
               String var12;
               if(var1 instanceof Type) {
                  Type var11;
                  try {
                     var11 = (Type)var1;
                  } catch (ClassCastException var8) {
                     throw new WrongType(var8, "val", -2, var1);
                  }

                  var12 = var11.getName();
               } else {
                  if(!(var1 instanceof Class)) {
                     return Quote.append$V(new Object[]{Lit10, Quote.consX$V(new Object[]{var1, LList.Empty})});
                  }

                  Class var13;
                  try {
                     var13 = (Class)var1;
                  } catch (ClassCastException var7) {
                     throw new WrongType(var7, "val", -2, var1);
                  }

                  var12 = var13.getName();
               }

               return misc.string$To$Symbol(Format.formatToString(0, new Object[]{"<~a>", var12}));
            }
         }
      }

      return var10;
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
               return expand$V(var5, var3);
            }

            var3[var4] = var2[var4 + 1];
         }
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

      LList pack;


   }

   public class frame0 extends ModuleBody {

      LList pack;


   }

   public class frame1 extends ModuleBody {

      LList pack;


   }
}
