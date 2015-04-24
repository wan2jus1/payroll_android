package kawa.lib;

import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.CallContext;
import gnu.mapping.Location;
import gnu.mapping.LocationProc;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.ThreadLocation;
import gnu.mapping.WrongType;
import kawa.lang.Macro;
import kawa.lang.SyntaxPattern;
import kawa.lang.SyntaxRule;
import kawa.lang.SyntaxRules;
import kawa.standard.Scheme;

public class parameters extends ModuleBody {

   public static final ModuleMethod $Prvt$as$Mnlocation$Pc;
   public static final Macro $Prvt$parameterize$Pc;
   public static final parameters $instance;
   static final SimpleSymbol Lit0;
   static final SimpleSymbol Lit1;
   static final SimpleSymbol Lit10 = (SimpleSymbol)(new SimpleSymbol("gnu.mapping.Location")).readResolve();
   static final SimpleSymbol Lit11 = (SimpleSymbol)(new SimpleSymbol("quasiquote")).readResolve();
   static final SimpleSymbol Lit12 = (SimpleSymbol)(new SimpleSymbol("save")).readResolve();
   static final SimpleSymbol Lit2;
   static final SyntaxRules Lit3;
   static final SimpleSymbol Lit4;
   static final SyntaxRules Lit5;
   static final SimpleSymbol Lit6 = (SimpleSymbol)(new SimpleSymbol("begin")).readResolve();
   static final SimpleSymbol Lit7 = (SimpleSymbol)(new SimpleSymbol("p")).readResolve();
   static final SimpleSymbol Lit8 = (SimpleSymbol)(new SimpleSymbol("v")).readResolve();
   static final SimpleSymbol Lit9 = (SimpleSymbol)(new SimpleSymbol("$lookup$")).readResolve();
   public static final ModuleMethod make$Mnparameter;
   public static final Macro parameterize;


   static {
      SimpleSymbol var0 = (SimpleSymbol)(new SimpleSymbol("parameterize")).readResolve();
      Lit4 = var0;
      SyntaxRule var1 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\b\u0003", new Object[0], 1), "\u0000", "\u0011\u0018\u0004\u0002", new Object[]{Lit6}, 0);
      SyntaxPattern var2 = new SyntaxPattern("\f\u0018<,\f\u0007\f\u000f\b\u0013\u001b", new Object[0], 4);
      SimpleSymbol var3 = (SimpleSymbol)(new SimpleSymbol("parameterize%")).readResolve();
      Lit2 = var3;
      SyntaxRule var9 = new SyntaxRule(var2, "\u0001\u0001\u0000\u0000", "\u0011\u0018\u00041!\t\u0003\b\u000b\u0012\t\u0010\u001a", new Object[]{var3}, 0);
      Lit5 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var1, var9}, 4);
      var0 = Lit2;
      var1 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\b\f\u0007\u000b", new Object[0], 2), "\u0001\u0000", "\u0011\u0018\u0004!\u0011\u0018\f\n\b\u0011\u0018\f\u0003", new Object[]{(SimpleSymbol)(new SimpleSymbol("try-finally")).readResolve(), Lit6}, 0);
      var2 = new SyntaxPattern("\f\u0018<,\f\u0007\f\u000f\b\u0013\f\u001f#", new Object[0], 5);
      var3 = (SimpleSymbol)(new SimpleSymbol("let*")).readResolve();
      SimpleSymbol var4 = Lit7;
      SimpleSymbol var5 = (SimpleSymbol)(new SimpleSymbol("::")).readResolve();
      SimpleSymbol var6 = (SimpleSymbol)(new SimpleSymbol("<gnu.mapping.Location>")).readResolve();
      SimpleSymbol var7 = (SimpleSymbol)(new SimpleSymbol("as-location%")).readResolve();
      Lit1 = var7;
      var9 = new SyntaxRule(var2, "\u0001\u0001\u0000\u0001\u0000", "\u0011\u0018\u0004Áy\u0011\u0018\f\u0011\u0018\u0014\u0011\u0018\u001c\b\u0011\u0018$\b\u0003)\u0011\u0018,\b\u000b\u00184\b\u0011\u0018<\t\u0012!\u0011\u0018D\u001b\"", new Object[]{var3, var4, var5, var6, var7, Lit8, PairWithPosition.make(PairWithPosition.make(Lit12, PairWithPosition.make(PairWithPosition.make(PairWithPosition.make(Lit9, Pair.make(Lit10, Pair.make(Pair.make(Lit11, Pair.make((SimpleSymbol)(new SimpleSymbol("setWithSave")).readResolve(), LList.Empty)), LList.Empty)), "parameters.scm", 122893), PairWithPosition.make(Lit7, PairWithPosition.make(Lit8, LList.Empty, "parameters.scm", 122928), "parameters.scm", 122926), "parameters.scm", 122892), LList.Empty, "parameters.scm", 122892), "parameters.scm", 122886), LList.Empty, "parameters.scm", 122886), Lit2, PairWithPosition.make(PairWithPosition.make(Lit9, Pair.make(Lit10, Pair.make(Pair.make(Lit11, Pair.make((SimpleSymbol)(new SimpleSymbol("setRestore")).readResolve(), LList.Empty)), LList.Empty)), "parameters.scm", 131083), PairWithPosition.make(Lit7, PairWithPosition.make(Lit12, LList.Empty, "parameters.scm", 131117), "parameters.scm", 131115), "parameters.scm", 131082)}, 0);
      Lit3 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var1, var9}, 5);
      Lit0 = (SimpleSymbol)(new SimpleSymbol("make-parameter")).readResolve();
      $instance = new parameters();
      parameters var8 = $instance;
      make$Mnparameter = new ModuleMethod(var8, 1, Lit0, 8193);
      $Prvt$as$Mnlocation$Pc = new ModuleMethod(var8, 3, Lit1, 4097);
      $Prvt$parameterize$Pc = Macro.make(Lit2, Lit3, $instance);
      parameterize = Macro.make(Lit4, Lit5, $instance);
      $instance.run();
   }

   public parameters() {
      ModuleInfo.register(this);
   }

   public static Location asLocation$Pc(Object var0) {
      Location var4;
      if(var0 instanceof LocationProc) {
         LocationProc var1;
         try {
            var1 = (LocationProc)var0;
         } catch (ClassCastException var3) {
            throw new WrongType(var3, "gnu.mapping.LocationProc.getLocation()", 1, var0);
         }

         var4 = var1.getLocation();
      } else {
         var4 = (Location)var0;
      }

      Object var5 = var4;
      if(var4 instanceof ThreadLocation) {
         ThreadLocation var6;
         try {
            var6 = (ThreadLocation)var4;
         } catch (ClassCastException var2) {
            throw new WrongType(var2, "gnu.mapping.ThreadLocation.getLocation()", 1, var4);
         }

         var5 = var6.getLocation();
      }

      return (Location)var5;
   }

   public static LocationProc makeParameter(Object var0) {
      return makeParameter(var0, (Object)null);
   }

   public static LocationProc makeParameter(Object var0, Object var1) {
      Object var2 = var0;
      if(var1 != null) {
         var2 = Scheme.applyToArgs.apply2(var1, var0);
      }

      ThreadLocation var4 = new ThreadLocation();
      var4.setGlobal(var2);

      Procedure var5;
      try {
         var5 = (Procedure)var1;
      } catch (ClassCastException var3) {
         throw new WrongType(var3, "gnu.mapping.LocationProc.<init>(gnu.mapping.Location,gnu.mapping.Procedure)", 2, var1);
      }

      return new LocationProc(var4, var5);
   }

   public Object apply1(ModuleMethod var1, Object var2) {
      switch(var1.selector) {
      case 1:
         return makeParameter(var2);
      case 2:
      default:
         return super.apply1(var1, var2);
      case 3:
         return asLocation$Pc(var2);
      }
   }

   public Object apply2(ModuleMethod var1, Object var2, Object var3) {
      return var1.selector == 1?makeParameter(var2, var3):super.apply2(var1, var2, var3);
   }

   public int match1(ModuleMethod var1, Object var2, CallContext var3) {
      switch(var1.selector) {
      case 1:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 2:
      default:
         return super.match1(var1, var2, var3);
      case 3:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      }
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

   public final void run(CallContext var1) {
      Consumer var2 = var1.consumer;
   }
}
