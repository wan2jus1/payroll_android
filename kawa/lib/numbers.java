package kawa.lib;

import gnu.expr.GenericProc;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.AddOp;
import gnu.kawa.functions.Arithmetic;
import gnu.kawa.functions.DivideOp;
import gnu.kawa.functions.Format;
import gnu.kawa.functions.GetNamedPart;
import gnu.kawa.functions.MultiplyOp;
import gnu.kawa.lispexpr.LangObjType;
import gnu.kawa.lispexpr.LispReader;
import gnu.lists.Consumer;
import gnu.lists.FString;
import gnu.mapping.CallContext;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.WrongType;
import gnu.math.BitOps;
import gnu.math.Complex;
import gnu.math.DComplex;
import gnu.math.DFloNum;
import gnu.math.Duration;
import gnu.math.IntNum;
import gnu.math.Numeric;
import gnu.math.Quantity;
import gnu.math.RatNum;
import gnu.math.RealNum;
import gnu.math.Unit;
import java.math.BigDecimal;
import java.math.BigInteger;
import kawa.lib.misc;
import kawa.standard.Scheme;

public class numbers extends ModuleBody {

   public static final numbers $instance = new numbers();
   static final IntNum Lit0 = IntNum.make(0);
   static final SimpleSymbol Lit1 = (SimpleSymbol)(new SimpleSymbol("signum")).readResolve();
   static final SimpleSymbol Lit10 = (SimpleSymbol)(new SimpleSymbol("inexact?")).readResolve();
   static final SimpleSymbol Lit11 = (SimpleSymbol)(new SimpleSymbol("zero?")).readResolve();
   static final SimpleSymbol Lit12 = (SimpleSymbol)(new SimpleSymbol("positive?")).readResolve();
   static final SimpleSymbol Lit13 = (SimpleSymbol)(new SimpleSymbol("negative?")).readResolve();
   static final SimpleSymbol Lit14 = (SimpleSymbol)(new SimpleSymbol("max")).readResolve();
   static final SimpleSymbol Lit15 = (SimpleSymbol)(new SimpleSymbol("min")).readResolve();
   static final SimpleSymbol Lit16 = (SimpleSymbol)(new SimpleSymbol("abs")).readResolve();
   static final SimpleSymbol Lit17 = (SimpleSymbol)(new SimpleSymbol("div-and-mod")).readResolve();
   static final SimpleSymbol Lit18 = (SimpleSymbol)(new SimpleSymbol("div0-and-mod0")).readResolve();
   static final SimpleSymbol Lit19 = (SimpleSymbol)(new SimpleSymbol("gcd")).readResolve();
   static final IntNum Lit2 = IntNum.make(1);
   static final SimpleSymbol Lit20 = (SimpleSymbol)(new SimpleSymbol("lcm")).readResolve();
   static final SimpleSymbol Lit21 = (SimpleSymbol)(new SimpleSymbol("numerator")).readResolve();
   static final SimpleSymbol Lit22 = (SimpleSymbol)(new SimpleSymbol("denominator")).readResolve();
   static final SimpleSymbol Lit23 = (SimpleSymbol)(new SimpleSymbol("floor")).readResolve();
   static final SimpleSymbol Lit24 = (SimpleSymbol)(new SimpleSymbol("ceiling")).readResolve();
   static final SimpleSymbol Lit25 = (SimpleSymbol)(new SimpleSymbol("truncate")).readResolve();
   static final SimpleSymbol Lit26 = (SimpleSymbol)(new SimpleSymbol("round")).readResolve();
   static final SimpleSymbol Lit27 = (SimpleSymbol)(new SimpleSymbol("rationalize")).readResolve();
   static final SimpleSymbol Lit28 = (SimpleSymbol)(new SimpleSymbol("exp")).readResolve();
   static final SimpleSymbol Lit29 = (SimpleSymbol)(new SimpleSymbol("log")).readResolve();
   static final SimpleSymbol Lit3 = (SimpleSymbol)(new SimpleSymbol("number?")).readResolve();
   static final SimpleSymbol Lit30 = (SimpleSymbol)(new SimpleSymbol("sin")).readResolve();
   static final SimpleSymbol Lit31 = (SimpleSymbol)(new SimpleSymbol("cos")).readResolve();
   static final SimpleSymbol Lit32 = (SimpleSymbol)(new SimpleSymbol("tan")).readResolve();
   static final SimpleSymbol Lit33 = (SimpleSymbol)(new SimpleSymbol("asin")).readResolve();
   static final SimpleSymbol Lit34 = (SimpleSymbol)(new SimpleSymbol("acos")).readResolve();
   static final SimpleSymbol Lit35 = (SimpleSymbol)(new SimpleSymbol("make-rectangular")).readResolve();
   static final SimpleSymbol Lit36 = (SimpleSymbol)(new SimpleSymbol("make-polar")).readResolve();
   static final SimpleSymbol Lit37 = (SimpleSymbol)(new SimpleSymbol("real-part")).readResolve();
   static final SimpleSymbol Lit38 = (SimpleSymbol)(new SimpleSymbol("imag-part")).readResolve();
   static final SimpleSymbol Lit39 = (SimpleSymbol)(new SimpleSymbol("magnitude")).readResolve();
   static final SimpleSymbol Lit4 = (SimpleSymbol)(new SimpleSymbol("quantity?")).readResolve();
   static final SimpleSymbol Lit40 = (SimpleSymbol)(new SimpleSymbol("angle")).readResolve();
   static final SimpleSymbol Lit41 = (SimpleSymbol)(new SimpleSymbol("inexact")).readResolve();
   static final SimpleSymbol Lit42 = (SimpleSymbol)(new SimpleSymbol("exact")).readResolve();
   static final SimpleSymbol Lit43 = (SimpleSymbol)(new SimpleSymbol("exact->inexact")).readResolve();
   static final SimpleSymbol Lit44 = (SimpleSymbol)(new SimpleSymbol("inexact->exact")).readResolve();
   static final SimpleSymbol Lit45 = (SimpleSymbol)(new SimpleSymbol("logop")).readResolve();
   static final SimpleSymbol Lit46 = (SimpleSymbol)(new SimpleSymbol("bitwise-bit-set?")).readResolve();
   static final SimpleSymbol Lit47 = (SimpleSymbol)(new SimpleSymbol("bitwise-copy-bit")).readResolve();
   static final SimpleSymbol Lit48 = (SimpleSymbol)(new SimpleSymbol("bitwise-copy-bit-field")).readResolve();
   static final SimpleSymbol Lit49 = (SimpleSymbol)(new SimpleSymbol("bitwise-bit-field")).readResolve();
   static final SimpleSymbol Lit5 = (SimpleSymbol)(new SimpleSymbol("complex?")).readResolve();
   static final SimpleSymbol Lit50 = (SimpleSymbol)(new SimpleSymbol("bitwise-if")).readResolve();
   static final SimpleSymbol Lit51 = (SimpleSymbol)(new SimpleSymbol("logtest")).readResolve();
   static final SimpleSymbol Lit52 = (SimpleSymbol)(new SimpleSymbol("logcount")).readResolve();
   static final SimpleSymbol Lit53 = (SimpleSymbol)(new SimpleSymbol("bitwise-bit-count")).readResolve();
   static final SimpleSymbol Lit54 = (SimpleSymbol)(new SimpleSymbol("bitwise-length")).readResolve();
   static final SimpleSymbol Lit55 = (SimpleSymbol)(new SimpleSymbol("bitwise-first-bit-set")).readResolve();
   static final SimpleSymbol Lit56 = (SimpleSymbol)(new SimpleSymbol("bitwise-rotate-bit-field")).readResolve();
   static final SimpleSymbol Lit57 = (SimpleSymbol)(new SimpleSymbol("bitwise-reverse-bit-field")).readResolve();
   static final SimpleSymbol Lit58 = (SimpleSymbol)(new SimpleSymbol("number->string")).readResolve();
   static final SimpleSymbol Lit59 = (SimpleSymbol)(new SimpleSymbol("string->number")).readResolve();
   static final SimpleSymbol Lit6 = (SimpleSymbol)(new SimpleSymbol("real?")).readResolve();
   static final SimpleSymbol Lit60 = (SimpleSymbol)(new SimpleSymbol("quantity->number")).readResolve();
   static final SimpleSymbol Lit61 = (SimpleSymbol)(new SimpleSymbol("quantity->unit")).readResolve();
   static final SimpleSymbol Lit62 = (SimpleSymbol)(new SimpleSymbol("make-quantity")).readResolve();
   static final SimpleSymbol Lit63 = (SimpleSymbol)(new SimpleSymbol("duration")).readResolve();
   static final SimpleSymbol Lit7 = (SimpleSymbol)(new SimpleSymbol("rational?")).readResolve();
   static final SimpleSymbol Lit8 = (SimpleSymbol)(new SimpleSymbol("integer?")).readResolve();
   static final SimpleSymbol Lit9 = (SimpleSymbol)(new SimpleSymbol("exact?")).readResolve();
   public static final ModuleMethod abs;
   public static final ModuleMethod acos;
   public static final ModuleMethod angle;
   public static final ModuleMethod asin;
   public static final GenericProc atan;
   public static final ModuleMethod bitwise$Mnbit$Mncount;
   public static final ModuleMethod bitwise$Mnbit$Mnfield;
   public static final ModuleMethod bitwise$Mnbit$Mnset$Qu;
   public static final ModuleMethod bitwise$Mncopy$Mnbit;
   public static final ModuleMethod bitwise$Mncopy$Mnbit$Mnfield;
   public static final ModuleMethod bitwise$Mnfirst$Mnbit$Mnset;
   public static final ModuleMethod bitwise$Mnif;
   public static final ModuleMethod bitwise$Mnlength;
   public static final ModuleMethod bitwise$Mnreverse$Mnbit$Mnfield;
   public static final ModuleMethod bitwise$Mnrotate$Mnbit$Mnfield;
   public static final ModuleMethod ceiling;
   public static final ModuleMethod complex$Qu;
   public static final ModuleMethod cos;
   public static final ModuleMethod denominator;
   public static final ModuleMethod div$Mnand$Mnmod;
   public static final ModuleMethod div0$Mnand$Mnmod0;
   public static final ModuleMethod duration;
   public static final ModuleMethod exact;
   public static final ModuleMethod exact$Mn$Grinexact;
   public static final ModuleMethod exact$Qu;
   public static final ModuleMethod exp;
   public static final ModuleMethod floor;
   public static final ModuleMethod gcd;
   public static final ModuleMethod imag$Mnpart;
   public static final ModuleMethod inexact;
   public static final ModuleMethod inexact$Mn$Grexact;
   public static final ModuleMethod inexact$Qu;
   public static final ModuleMethod integer$Qu;
   static final ModuleMethod lambda$Fn1;
   static final ModuleMethod lambda$Fn2;
   static final ModuleMethod lambda$Fn3;
   static final ModuleMethod lambda$Fn4;
   public static final ModuleMethod lcm;
   public static final ModuleMethod log;
   public static final ModuleMethod logcount;
   public static final ModuleMethod logop;
   public static final ModuleMethod logtest;
   public static final ModuleMethod magnitude;
   public static final ModuleMethod make$Mnpolar;
   public static final ModuleMethod make$Mnquantity;
   public static final ModuleMethod make$Mnrectangular;
   public static final ModuleMethod max;
   public static final ModuleMethod min;
   public static final ModuleMethod negative$Qu;
   public static final ModuleMethod number$Mn$Grstring;
   public static final ModuleMethod number$Qu;
   public static final ModuleMethod numerator;
   public static final ModuleMethod positive$Qu;
   public static final ModuleMethod quantity$Mn$Grnumber;
   public static final ModuleMethod quantity$Mn$Grunit;
   public static final ModuleMethod quantity$Qu;
   public static final ModuleMethod rational$Qu;
   public static final ModuleMethod rationalize;
   public static final ModuleMethod real$Mnpart;
   public static final ModuleMethod real$Qu;
   public static final ModuleMethod round;
   public static final ModuleMethod sin;
   public static final GenericProc sqrt;
   public static final ModuleMethod string$Mn$Grnumber;
   public static final ModuleMethod tan;
   public static final ModuleMethod truncate;
   public static final ModuleMethod zero$Qu;


   static {
      numbers var0 = $instance;
      number$Qu = new ModuleMethod(var0, 1, Lit3, 4097);
      quantity$Qu = new ModuleMethod(var0, 2, Lit4, 4097);
      complex$Qu = new ModuleMethod(var0, 3, Lit5, 4097);
      real$Qu = new ModuleMethod(var0, 4, Lit6, 4097);
      rational$Qu = new ModuleMethod(var0, 5, Lit7, 4097);
      integer$Qu = new ModuleMethod(var0, 6, Lit8, 4097);
      exact$Qu = new ModuleMethod(var0, 7, Lit9, 4097);
      inexact$Qu = new ModuleMethod(var0, 8, Lit10, 4097);
      zero$Qu = new ModuleMethod(var0, 9, Lit11, 4097);
      positive$Qu = new ModuleMethod(var0, 10, Lit12, 4097);
      negative$Qu = new ModuleMethod(var0, 11, Lit13, 4097);
      max = new ModuleMethod(var0, 12, Lit14, -4096);
      min = new ModuleMethod(var0, 13, Lit15, -4096);
      abs = new ModuleMethod(var0, 14, Lit16, 4097);
      div$Mnand$Mnmod = new ModuleMethod(var0, 15, Lit17, 8194);
      div0$Mnand$Mnmod0 = new ModuleMethod(var0, 16, Lit18, 8194);
      gcd = new ModuleMethod(var0, 17, Lit19, -4096);
      lcm = new ModuleMethod(var0, 18, Lit20, -4096);
      numerator = new ModuleMethod(var0, 19, Lit21, 4097);
      denominator = new ModuleMethod(var0, 20, Lit22, 4097);
      floor = new ModuleMethod(var0, 21, Lit23, 4097);
      ceiling = new ModuleMethod(var0, 22, Lit24, 4097);
      truncate = new ModuleMethod(var0, 23, Lit25, 4097);
      round = new ModuleMethod(var0, 24, Lit26, 4097);
      rationalize = new ModuleMethod(var0, 25, Lit27, 8194);
      exp = new ModuleMethod(var0, 26, Lit28, 4097);
      log = new ModuleMethod(var0, 27, Lit29, 4097);
      sin = new ModuleMethod(var0, 28, Lit30, 4097);
      cos = new ModuleMethod(var0, 29, Lit31, 4097);
      tan = new ModuleMethod(var0, 30, Lit32, 4097);
      asin = new ModuleMethod(var0, 31, Lit33, 4097);
      acos = new ModuleMethod(var0, 32, Lit34, 4097);
      ModuleMethod var1 = new ModuleMethod(var0, 33, (Object)null, 8194);
      var1.setProperty("source-location", "numbers.scm:146");
      lambda$Fn1 = var1;
      var1 = new ModuleMethod(var0, 34, (Object)null, 4097);
      var1.setProperty("source-location", "numbers.scm:148");
      lambda$Fn2 = var1;
      var1 = new ModuleMethod(var0, 35, (Object)null, 4097);
      var1.setProperty("source-location", "numbers.scm:152");
      lambda$Fn3 = var1;
      var1 = new ModuleMethod(var0, 36, (Object)null, 4097);
      var1.setProperty("source-location", "numbers.scm:156");
      lambda$Fn4 = var1;
      make$Mnrectangular = new ModuleMethod(var0, 37, Lit35, 8194);
      make$Mnpolar = new ModuleMethod(var0, 38, Lit36, 8194);
      real$Mnpart = new ModuleMethod(var0, 39, Lit37, 4097);
      imag$Mnpart = new ModuleMethod(var0, 40, Lit38, 4097);
      magnitude = new ModuleMethod(var0, 41, Lit39, 4097);
      angle = new ModuleMethod(var0, 42, Lit40, 4097);
      inexact = new ModuleMethod(var0, 43, Lit41, 4097);
      exact = new ModuleMethod(var0, 44, Lit42, 4097);
      exact$Mn$Grinexact = new ModuleMethod(var0, 45, Lit43, 4097);
      inexact$Mn$Grexact = new ModuleMethod(var0, 46, Lit44, 4097);
      logop = new ModuleMethod(var0, 47, Lit45, 12291);
      bitwise$Mnbit$Mnset$Qu = new ModuleMethod(var0, 48, Lit46, 8194);
      bitwise$Mncopy$Mnbit = new ModuleMethod(var0, 49, Lit47, 12291);
      bitwise$Mncopy$Mnbit$Mnfield = new ModuleMethod(var0, 50, Lit48, 16388);
      bitwise$Mnbit$Mnfield = new ModuleMethod(var0, 51, Lit49, 12291);
      bitwise$Mnif = new ModuleMethod(var0, 52, Lit50, 12291);
      logtest = new ModuleMethod(var0, 53, Lit51, 8194);
      logcount = new ModuleMethod(var0, 54, Lit52, 4097);
      bitwise$Mnbit$Mncount = new ModuleMethod(var0, 55, Lit53, 4097);
      bitwise$Mnlength = new ModuleMethod(var0, 56, Lit54, 4097);
      bitwise$Mnfirst$Mnbit$Mnset = new ModuleMethod(var0, 57, Lit55, 4097);
      bitwise$Mnrotate$Mnbit$Mnfield = new ModuleMethod(var0, 58, Lit56, 16388);
      bitwise$Mnreverse$Mnbit$Mnfield = new ModuleMethod(var0, 59, Lit57, 12291);
      number$Mn$Grstring = new ModuleMethod(var0, 60, Lit58, 8193);
      string$Mn$Grnumber = new ModuleMethod(var0, 62, Lit59, 8193);
      quantity$Mn$Grnumber = new ModuleMethod(var0, 64, Lit60, 4097);
      quantity$Mn$Grunit = new ModuleMethod(var0, 65, Lit61, 4097);
      make$Mnquantity = new ModuleMethod(var0, 66, Lit62, 8194);
      duration = new ModuleMethod(var0, 67, Lit63, 4097);
      $instance.run();
   }

   public numbers() {
      ModuleInfo.register(this);
   }

   public static Number abs(Number var0) {
      Object var1;
      if(var0 instanceof Numeric) {
         var1 = ((Numeric)var0).abs();
      } else {
         var1 = var0;
         if(Scheme.numGEq.apply2(var0, Lit0) == Boolean.FALSE) {
            return (Number)AddOp.$Mn.apply1(var0);
         }
      }

      return (Number)var1;
   }

   public static double acos(double var0) {
      return Math.acos(var0);
   }

   public static RealNum angle(Complex var0) {
      return var0.angle();
   }

   public static double asin(double var0) {
      return Math.asin(var0);
   }

   public static int bitwiseBitCount(IntNum var0) {
      return IntNum.compare(var0, 0L) >= 0?BitOps.bitCount(var0):-1 - BitOps.bitCount(BitOps.not(var0));
   }

   public static IntNum bitwiseBitField(IntNum var0, int var1, int var2) {
      return BitOps.extract(var0, var1, var2);
   }

   public static IntNum bitwiseCopyBit(IntNum var0, int var1, int var2) {
      return BitOps.setBitValue(var0, var1, var2);
   }

   public static IntNum bitwiseCopyBitField(IntNum var0, int var1, int var2, IntNum var3) {
      int var5 = IntNum.shift(-1, var1);
      IntNum var4 = BitOps.not(IntNum.make(IntNum.shift(-1, var2)));
      return bitwiseIf(BitOps.and(IntNum.make(var5), var4), IntNum.shift(var3, var1), var0);
   }

   public static int bitwiseFirstBitSet(IntNum var0) {
      return BitOps.lowestBitSet(var0);
   }

   public static IntNum bitwiseIf(IntNum var0, IntNum var1, IntNum var2) {
      return BitOps.ior(BitOps.and(var0, var1), BitOps.and(BitOps.not(var0), var2));
   }

   public static int bitwiseLength(IntNum var0) {
      return var0.intLength();
   }

   public static IntNum bitwiseReverseBitField(IntNum var0, int var1, int var2) {
      return BitOps.reverseBits(var0, var1, var2);
   }

   public static IntNum bitwiseRotateBitField(IntNum var0, int var1, int var2, int var3) {
      int var5 = var2 - var1;
      IntNum var4 = var0;
      if(var5 > 0) {
         var3 %= var5;
         if(var3 < 0) {
            var3 += var5;
         }

         var4 = bitwiseBitField(var0, var1, var2);
         var4 = bitwiseCopyBitField(var0, var1, var2, BitOps.ior(IntNum.shift(var4, var3), IntNum.shift(var4, var3 - var5)));
      }

      return var4;
   }

   public static RealNum ceiling(RealNum var0) {
      return var0.toInt(Numeric.CEILING);
   }

   public static double cos(double var0) {
      return Math.cos(var0);
   }

   public static IntNum denominator(RatNum var0) {
      return var0.denominator();
   }

   public static Object div0AndMod0(RealNum var0, RealNum var1) {
      Object var3 = DivideOp.div0.apply2(var0, var1);

      RealNum var2;
      try {
         var2 = LangObjType.coerceRealNum(var3);
      } catch (ClassCastException var5) {
         throw new WrongType(var5, "q", -2, var3);
      }

      Object var6 = AddOp.$Mn.apply2(var0, MultiplyOp.$St.apply2(var2, var1));

      try {
         var1 = LangObjType.coerceRealNum(var6);
      } catch (ClassCastException var4) {
         throw new WrongType(var4, "r", -2, var6);
      }

      return misc.values(new Object[]{var2, var1});
   }

   public static Object divAndMod(RealNum var0, RealNum var1) {
      Object var3 = DivideOp.div.apply2(var0, var1);

      RealNum var2;
      try {
         var2 = LangObjType.coerceRealNum(var3);
      } catch (ClassCastException var5) {
         throw new WrongType(var5, "q", -2, var3);
      }

      Object var6 = AddOp.$Mn.apply2(var0, MultiplyOp.$St.apply2(var2, var1));

      try {
         var1 = LangObjType.coerceRealNum(var6);
      } catch (ClassCastException var4) {
         throw new WrongType(var4, "r", -2, var6);
      }

      return misc.values(new Object[]{var2, var1});
   }

   public static Duration duration(Object var0) {
      String var1;
      if(var0 == null) {
         var1 = null;
      } else {
         var1 = var0.toString();
      }

      return Duration.parseDuration(var1);
   }

   public static Number exact(Number var0) {
      return Arithmetic.toExact(var0);
   }

   public static Number exact$To$Inexact(Number var0) {
      return Arithmetic.toInexact(var0);
   }

   public static Complex exp(Complex var0) {
      return var0.exp();
   }

   public static RealNum floor(RealNum var0) {
      return var0.toInt(Numeric.FLOOR);
   }

   public static IntNum gcd(IntNum ... var0) {
      int var4 = var0.length;
      IntNum var2;
      if(var4 == 0) {
         var2 = Lit0;
      } else {
         IntNum var1 = var0[0];
         int var3 = 1;

         while(true) {
            var2 = var1;
            if(var3 >= var4) {
               break;
            }

            var1 = IntNum.gcd(var1, var0[var3]);
            ++var3;
         }
      }

      return var2;
   }

   public static RealNum imagPart(Complex var0) {
      return var0.im();
   }

   public static Number inexact(Number var0) {
      return Arithmetic.toInexact(var0);
   }

   public static Number inexact$To$Exact(Number var0) {
      return Arithmetic.toExact(var0);
   }

   public static boolean isBitwiseBitSet(IntNum var0, int var1) {
      return BitOps.bitValue(var0, var1);
   }

   public static boolean isComplex(Object var0) {
      return var0 instanceof Complex;
   }

   public static boolean isExact(Object var0) {
      boolean var2 = var0 instanceof Number;
      boolean var1 = var2;
      if(var2) {
         var1 = Arithmetic.isExact((Number)var0);
      }

      return var1;
   }

   public static boolean isInexact(Object var0) {
      throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:783)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:662)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:722)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:706)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e2expr(TypeTransformer.java:632)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:706)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e2expr(TypeTransformer.java:632)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:706)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s2stmt(TypeTransformer.java:823)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:846)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n\tat sun.reflect.NativeMethodAccessorImpl.invoke(Unknown Source)\r\n\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source)\r\n\tat java.lang.reflect.Method.invoke(Unknown Source)\r\n\tat com.exe4j.runtime.LauncherEngine.launch(Unknown Source)\r\n\tat com.exe4j.runtime.WinLauncher.main(Unknown Source)\r\n");
   }

   public static boolean isInteger(Object var0) {
      boolean var2 = var0 instanceof IntNum;
      if(!var2) {
         var2 = var0 instanceof DFloNum;
         boolean var3 = var2;
         if(var2) {
            DFloNum var1;
            try {
               var1 = (DFloNum)var0;
            } catch (ClassCastException var4) {
               throw new WrongType(var4, "gnu.math.DFloNum.doubleValue()", 1, var0);
            }

            if(Math.IEEEremainder(var1.doubleValue(), 1.0D) == 0.0D) {
               var2 = true;
            } else {
               var2 = false;
            }

            var3 = var2;
         }

         var2 = var3;
         if(!var3) {
            var3 = var0 instanceof Number;
            var2 = var3;
            if(var3) {
               var3 = var0 instanceof Long;
               var2 = var3;
               if(!var3) {
                  var3 = var0 instanceof Integer;
                  var2 = var3;
                  if(!var3) {
                     var3 = var0 instanceof Short;
                     var2 = var3;
                     if(!var3) {
                        return var0 instanceof BigInteger;
                     }
                  }
               }
            }
         }
      }

      return var2;
   }

   public static boolean isNegative(RealNum var0) {
      return var0.isNegative();
   }

   public static boolean isNumber(Object var0) {
      return var0 instanceof Number;
   }

   public static boolean isPositive(RealNum var0) {
      return var0.sign() > 0;
   }

   public static boolean isQuantity(Object var0) {
      return var0 instanceof Quantity;
   }

   public static boolean isRational(Object var0) {
      return RatNum.asRatNumOrNull(var0) != null;
   }

   public static boolean isReal(Object var0) {
      return RealNum.asRealNumOrNull(var0) != null;
   }

   public static boolean isZero(Number var0) {
      boolean var1 = true;
      if(var0 instanceof Numeric) {
         var1 = ((Numeric)var0).isZero();
      } else if(var0 instanceof BigInteger) {
         if(Scheme.numEqu.apply2(Lit0, GetNamedPart.getNamedPart.apply2((BigInteger)var0, Lit1)) == Boolean.FALSE) {
            return false;
         }
      } else if(var0 instanceof BigDecimal) {
         if(Scheme.numEqu.apply2(Lit0, GetNamedPart.getNamedPart.apply2((BigDecimal)var0, Lit1)) == Boolean.FALSE) {
            return false;
         }
      } else if(var0.doubleValue() != 0.0D) {
         return false;
      }

      return var1;
   }

   static double lambda1(double var0, double var2) {
      return Math.atan2(var0, var2);
   }

   static double lambda2(double var0) {
      return Math.atan(var0);
   }

   static Quantity lambda3(Quantity var0) {
      return Quantity.make(var0.number().sqrt(), var0.unit().sqrt());
   }

   static double lambda4(double var0) {
      return Math.sqrt(var0);
   }

   public static IntNum lcm(IntNum ... var0) {
      int var4 = var0.length;
      IntNum var2;
      if(var4 == 0) {
         var2 = Lit2;
      } else {
         IntNum var1 = IntNum.abs(var0[0]);
         int var3 = 1;

         while(true) {
            var2 = var1;
            if(var3 >= var4) {
               break;
            }

            var1 = IntNum.lcm(var1, var0[var3]);
            ++var3;
         }
      }

      return var2;
   }

   public static Complex log(Complex var0) {
      return var0.log();
   }

   public static int logcount(IntNum var0) {
      if(IntNum.compare(var0, 0L) < 0) {
         var0 = BitOps.not(var0);
      }

      return BitOps.bitCount(var0);
   }

   public static IntNum logop(int var0, IntNum var1, IntNum var2) {
      return BitOps.bitOp(var0, var1, var2);
   }

   public static boolean logtest(IntNum var0, IntNum var1) {
      return BitOps.test(var0, var1);
   }

   public static Number magnitude(Number var0) {
      return abs(var0);
   }

   public static DComplex makePolar(double var0, double var2) {
      return Complex.polar(var0, var2);
   }

   public static Quantity makeQuantity(Object var0, Object var1) {
      Object var2;
      if(var1 instanceof Unit) {
         try {
            var2 = (Unit)var1;
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "u", -2, var1);
         }
      } else {
         String var6;
         if(var1 == null) {
            var6 = null;
         } else {
            var6 = var1.toString();
         }

         var2 = Unit.lookup(var6);
      }

      if(var2 == null) {
         throw (Throwable)(new IllegalArgumentException(Format.formatToString(0, new Object[]{"unknown unit: ~s", var1}).toString()));
      } else {
         Complex var5;
         try {
            var5 = (Complex)var0;
         } catch (ClassCastException var3) {
            throw new WrongType(var3, "gnu.math.Quantity.make(gnu.math.Complex,gnu.math.Unit)", 1, var0);
         }

         return Quantity.make(var5, (Unit)var2);
      }
   }

   public static Complex makeRectangular(RealNum var0, RealNum var1) {
      return Complex.make(var0, var1);
   }

   public static Object max(Object ... var0) {
      int var5 = var0.length;
      Object var2 = var0[0];

      RealNum var1;
      try {
         var1 = LangObjType.coerceRealNum(var2);
      } catch (ClassCastException var7) {
         throw new WrongType(var7, "result", -2, var2);
      }

      for(int var4 = 1; var4 < var5; ++var4) {
         var2 = var0[var4];

         RealNum var3;
         try {
            var3 = LangObjType.coerceRealNum(var2);
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "gnu.math.RealNum.max(real)", 2, var2);
         }

         var1 = var1.max(var3);
      }

      return var1;
   }

   public static Object min(Object ... var0) {
      int var5 = var0.length;
      Object var2 = var0[0];

      RealNum var1;
      try {
         var1 = LangObjType.coerceRealNum(var2);
      } catch (ClassCastException var7) {
         throw new WrongType(var7, "result", -2, var2);
      }

      for(int var4 = 0; var4 < var5; ++var4) {
         var2 = var0[var4];

         RealNum var3;
         try {
            var3 = LangObjType.coerceRealNum(var2);
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "gnu.math.RealNum.min(real)", 2, var2);
         }

         var1 = var1.min(var3);
      }

      return var1;
   }

   public static CharSequence number$To$String(Number var0) {
      return number$To$String(var0, 10);
   }

   public static CharSequence number$To$String(Number var0, int var1) {
      return new FString(Arithmetic.toString(var0, var1));
   }

   public static IntNum numerator(RatNum var0) {
      return var0.numerator();
   }

   public static Complex quantity$To$Number(Quantity var0) {
      var0.unit();
      return var0.doubleValue() == 1.0D?var0.number():Complex.make(var0.reValue(), var0.imValue());
   }

   public static Unit quantity$To$Unit(Quantity var0) {
      return var0.unit();
   }

   public static RealNum rationalize(RealNum var0, RealNum var1) {
      return RatNum.rationalize(LangObjType.coerceRealNum(var0.sub(var1)), LangObjType.coerceRealNum(var0.add(var1)));
   }

   public static RealNum realPart(Complex var0) {
      return var0.re();
   }

   public static RealNum round(RealNum var0) {
      return var0.toInt(Numeric.ROUND);
   }

   public static double sin(double var0) {
      return Math.sin(var0);
   }

   public static Object string$To$Number(CharSequence var0) {
      return string$To$Number(var0, 10);
   }

   public static Object string$To$Number(CharSequence var0, int var1) {
      Object var2 = LispReader.parseNumber(var0, var1);
      return var2 instanceof Numeric?var2:Boolean.FALSE;
   }

   public static double tan(double var0) {
      return Math.tan(var0);
   }

   public static RealNum truncate(RealNum var0) {
      return var0.toInt(Numeric.TRUNCATE);
   }

   public Object apply1(ModuleMethod var1, Object var2) {
      double var3;
      Number var43;
      Quantity var41;
      RealNum var46;
      RatNum var47;
      IntNum var44;
      Complex var45;
      switch(var1.selector) {
      case 1:
         if(isNumber(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 2:
         if(isQuantity(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 3:
         if(isComplex(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 4:
         if(isReal(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 5:
         if(isRational(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 6:
         if(isInteger(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 7:
         if(isExact(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 8:
         if(isInexact(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 9:
         try {
            var43 = (Number)var2;
         } catch (ClassCastException var40) {
            throw new WrongType(var40, "zero?", 1, var2);
         }

         if(isZero(var43)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 10:
         try {
            var46 = LangObjType.coerceRealNum(var2);
         } catch (ClassCastException var39) {
            throw new WrongType(var39, "positive?", 1, var2);
         }

         if(isPositive(var46)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 11:
         try {
            var46 = LangObjType.coerceRealNum(var2);
         } catch (ClassCastException var38) {
            throw new WrongType(var38, "negative?", 1, var2);
         }

         if(isNegative(var46)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 12:
      case 13:
      case 15:
      case 16:
      case 17:
      case 18:
      case 25:
      case 33:
      case 37:
      case 38:
      case 47:
      case 48:
      case 49:
      case 50:
      case 51:
      case 52:
      case 53:
      case 58:
      case 59:
      case 61:
      case 63:
      case 66:
      default:
         return super.apply1(var1, var2);
      case 14:
         try {
            var43 = (Number)var2;
         } catch (ClassCastException var37) {
            throw new WrongType(var37, "abs", 1, var2);
         }

         return abs(var43);
      case 19:
         try {
            var47 = LangObjType.coerceRatNum(var2);
         } catch (ClassCastException var36) {
            throw new WrongType(var36, "numerator", 1, var2);
         }

         return numerator(var47);
      case 20:
         try {
            var47 = LangObjType.coerceRatNum(var2);
         } catch (ClassCastException var35) {
            throw new WrongType(var35, "denominator", 1, var2);
         }

         return denominator(var47);
      case 21:
         try {
            var46 = LangObjType.coerceRealNum(var2);
         } catch (ClassCastException var34) {
            throw new WrongType(var34, "floor", 1, var2);
         }

         return floor(var46);
      case 22:
         try {
            var46 = LangObjType.coerceRealNum(var2);
         } catch (ClassCastException var33) {
            throw new WrongType(var33, "ceiling", 1, var2);
         }

         return ceiling(var46);
      case 23:
         try {
            var46 = LangObjType.coerceRealNum(var2);
         } catch (ClassCastException var32) {
            throw new WrongType(var32, "truncate", 1, var2);
         }

         return truncate(var46);
      case 24:
         try {
            var46 = LangObjType.coerceRealNum(var2);
         } catch (ClassCastException var31) {
            throw new WrongType(var31, "round", 1, var2);
         }

         return round(var46);
      case 26:
         try {
            var45 = (Complex)var2;
         } catch (ClassCastException var30) {
            throw new WrongType(var30, "exp", 1, var2);
         }

         return exp(var45);
      case 27:
         try {
            var45 = (Complex)var2;
         } catch (ClassCastException var29) {
            throw new WrongType(var29, "log", 1, var2);
         }

         return log(var45);
      case 28:
         try {
            var3 = ((Number)var2).doubleValue();
         } catch (ClassCastException var28) {
            throw new WrongType(var28, "sin", 1, var2);
         }

         return Double.valueOf(sin(var3));
      case 29:
         try {
            var3 = ((Number)var2).doubleValue();
         } catch (ClassCastException var27) {
            throw new WrongType(var27, "cos", 1, var2);
         }

         return Double.valueOf(cos(var3));
      case 30:
         try {
            var3 = ((Number)var2).doubleValue();
         } catch (ClassCastException var26) {
            throw new WrongType(var26, "tan", 1, var2);
         }

         return Double.valueOf(tan(var3));
      case 31:
         try {
            var3 = ((Number)var2).doubleValue();
         } catch (ClassCastException var25) {
            throw new WrongType(var25, "asin", 1, var2);
         }

         return Double.valueOf(asin(var3));
      case 32:
         try {
            var3 = ((Number)var2).doubleValue();
         } catch (ClassCastException var24) {
            throw new WrongType(var24, "acos", 1, var2);
         }

         return Double.valueOf(acos(var3));
      case 34:
         try {
            var3 = ((Number)var2).doubleValue();
         } catch (ClassCastException var23) {
            throw new WrongType(var23, "lambda", 1, var2);
         }

         return Double.valueOf(lambda2(var3));
      case 35:
         try {
            var41 = (Quantity)var2;
         } catch (ClassCastException var22) {
            throw new WrongType(var22, "lambda", 1, var2);
         }

         return lambda3(var41);
      case 36:
         try {
            var3 = ((Number)var2).doubleValue();
         } catch (ClassCastException var21) {
            throw new WrongType(var21, "lambda", 1, var2);
         }

         return Double.valueOf(lambda4(var3));
      case 39:
         try {
            var45 = (Complex)var2;
         } catch (ClassCastException var20) {
            throw new WrongType(var20, "real-part", 1, var2);
         }

         return realPart(var45);
      case 40:
         try {
            var45 = (Complex)var2;
         } catch (ClassCastException var19) {
            throw new WrongType(var19, "imag-part", 1, var2);
         }

         return imagPart(var45);
      case 41:
         try {
            var43 = (Number)var2;
         } catch (ClassCastException var18) {
            throw new WrongType(var18, "magnitude", 1, var2);
         }

         return magnitude(var43);
      case 42:
         try {
            var45 = (Complex)var2;
         } catch (ClassCastException var17) {
            throw new WrongType(var17, "angle", 1, var2);
         }

         return angle(var45);
      case 43:
         try {
            var43 = (Number)var2;
         } catch (ClassCastException var16) {
            throw new WrongType(var16, "inexact", 1, var2);
         }

         return inexact(var43);
      case 44:
         try {
            var43 = (Number)var2;
         } catch (ClassCastException var15) {
            throw new WrongType(var15, "exact", 1, var2);
         }

         return exact(var43);
      case 45:
         try {
            var43 = (Number)var2;
         } catch (ClassCastException var14) {
            throw new WrongType(var14, "exact->inexact", 1, var2);
         }

         return exact$To$Inexact(var43);
      case 46:
         try {
            var43 = (Number)var2;
         } catch (ClassCastException var13) {
            throw new WrongType(var13, "inexact->exact", 1, var2);
         }

         return inexact$To$Exact(var43);
      case 54:
         try {
            var44 = LangObjType.coerceIntNum(var2);
         } catch (ClassCastException var12) {
            throw new WrongType(var12, "logcount", 1, var2);
         }

         return Integer.valueOf(logcount(var44));
      case 55:
         try {
            var44 = LangObjType.coerceIntNum(var2);
         } catch (ClassCastException var11) {
            throw new WrongType(var11, "bitwise-bit-count", 1, var2);
         }

         return Integer.valueOf(bitwiseBitCount(var44));
      case 56:
         try {
            var44 = LangObjType.coerceIntNum(var2);
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "bitwise-length", 1, var2);
         }

         return Integer.valueOf(bitwiseLength(var44));
      case 57:
         try {
            var44 = LangObjType.coerceIntNum(var2);
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "bitwise-first-bit-set", 1, var2);
         }

         return Integer.valueOf(bitwiseFirstBitSet(var44));
      case 60:
         try {
            var43 = (Number)var2;
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "number->string", 1, var2);
         }

         return number$To$String(var43);
      case 62:
         CharSequence var42;
         try {
            var42 = (CharSequence)var2;
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "string->number", 1, var2);
         }

         return string$To$Number(var42);
      case 64:
         try {
            var41 = (Quantity)var2;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "quantity->number", 1, var2);
         }

         return quantity$To$Number(var41);
      case 65:
         try {
            var41 = (Quantity)var2;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "quantity->unit", 1, var2);
         }

         return quantity$To$Unit(var41);
      case 67:
         return duration(var2);
      }
   }

   public Object apply2(ModuleMethod var1, Object var2, Object var3) {
      double var4;
      double var6;
      int var8;
      IntNum var31;
      RealNum var34;
      RealNum var33;
      switch(var1.selector) {
      case 15:
         try {
            var33 = LangObjType.coerceRealNum(var2);
         } catch (ClassCastException var28) {
            throw new WrongType(var28, "div-and-mod", 1, var2);
         }

         try {
            var34 = LangObjType.coerceRealNum(var3);
         } catch (ClassCastException var27) {
            throw new WrongType(var27, "div-and-mod", 2, var3);
         }

         return divAndMod(var33, var34);
      case 16:
         try {
            var33 = LangObjType.coerceRealNum(var2);
         } catch (ClassCastException var26) {
            throw new WrongType(var26, "div0-and-mod0", 1, var2);
         }

         try {
            var34 = LangObjType.coerceRealNum(var3);
         } catch (ClassCastException var25) {
            throw new WrongType(var25, "div0-and-mod0", 2, var3);
         }

         return div0AndMod0(var33, var34);
      case 25:
         try {
            var33 = LangObjType.coerceRealNum(var2);
         } catch (ClassCastException var24) {
            throw new WrongType(var24, "rationalize", 1, var2);
         }

         try {
            var34 = LangObjType.coerceRealNum(var3);
         } catch (ClassCastException var23) {
            throw new WrongType(var23, "rationalize", 2, var3);
         }

         return rationalize(var33, var34);
      case 33:
         try {
            var4 = ((Number)var2).doubleValue();
         } catch (ClassCastException var22) {
            throw new WrongType(var22, "lambda", 1, var2);
         }

         try {
            var6 = ((Number)var3).doubleValue();
         } catch (ClassCastException var21) {
            throw new WrongType(var21, "lambda", 2, var3);
         }

         return Double.valueOf(lambda1(var4, var6));
      case 37:
         try {
            var33 = LangObjType.coerceRealNum(var2);
         } catch (ClassCastException var20) {
            throw new WrongType(var20, "make-rectangular", 1, var2);
         }

         try {
            var34 = LangObjType.coerceRealNum(var3);
         } catch (ClassCastException var19) {
            throw new WrongType(var19, "make-rectangular", 2, var3);
         }

         return makeRectangular(var33, var34);
      case 38:
         try {
            var4 = ((Number)var2).doubleValue();
         } catch (ClassCastException var18) {
            throw new WrongType(var18, "make-polar", 1, var2);
         }

         try {
            var6 = ((Number)var3).doubleValue();
         } catch (ClassCastException var17) {
            throw new WrongType(var17, "make-polar", 2, var3);
         }

         return makePolar(var4, var6);
      case 48:
         try {
            var31 = LangObjType.coerceIntNum(var2);
         } catch (ClassCastException var16) {
            throw new WrongType(var16, "bitwise-bit-set?", 1, var2);
         }

         try {
            var8 = ((Number)var3).intValue();
         } catch (ClassCastException var15) {
            throw new WrongType(var15, "bitwise-bit-set?", 2, var3);
         }

         if(isBitwiseBitSet(var31, var8)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 53:
         try {
            var31 = LangObjType.coerceIntNum(var2);
         } catch (ClassCastException var14) {
            throw new WrongType(var14, "logtest", 1, var2);
         }

         IntNum var32;
         try {
            var32 = LangObjType.coerceIntNum(var3);
         } catch (ClassCastException var13) {
            throw new WrongType(var13, "logtest", 2, var3);
         }

         if(logtest(var31, var32)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 60:
         Number var30;
         try {
            var30 = (Number)var2;
         } catch (ClassCastException var12) {
            throw new WrongType(var12, "number->string", 1, var2);
         }

         try {
            var8 = ((Number)var3).intValue();
         } catch (ClassCastException var11) {
            throw new WrongType(var11, "number->string", 2, var3);
         }

         return number$To$String(var30, var8);
      case 62:
         CharSequence var29;
         try {
            var29 = (CharSequence)var2;
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "string->number", 1, var2);
         }

         try {
            var8 = ((Number)var3).intValue();
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "string->number", 2, var3);
         }

         return string$To$Number(var29, var8);
      case 66:
         return makeQuantity(var2, var3);
      default:
         return super.apply2(var1, var2, var3);
      }
   }

   public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
      int var5;
      int var6;
      IntNum var23;
      IntNum var22;
      switch(var1.selector) {
      case 47:
         try {
            var5 = ((Number)var2).intValue();
         } catch (ClassCastException var21) {
            throw new WrongType(var21, "logop", 1, var2);
         }

         try {
            var22 = LangObjType.coerceIntNum(var3);
         } catch (ClassCastException var20) {
            throw new WrongType(var20, "logop", 2, var3);
         }

         try {
            var23 = LangObjType.coerceIntNum(var4);
         } catch (ClassCastException var19) {
            throw new WrongType(var19, "logop", 3, var4);
         }

         return logop(var5, var22, var23);
      case 49:
         try {
            var22 = LangObjType.coerceIntNum(var2);
         } catch (ClassCastException var18) {
            throw new WrongType(var18, "bitwise-copy-bit", 1, var2);
         }

         try {
            var5 = ((Number)var3).intValue();
         } catch (ClassCastException var17) {
            throw new WrongType(var17, "bitwise-copy-bit", 2, var3);
         }

         try {
            var6 = ((Number)var4).intValue();
         } catch (ClassCastException var16) {
            throw new WrongType(var16, "bitwise-copy-bit", 3, var4);
         }

         return bitwiseCopyBit(var22, var5, var6);
      case 51:
         try {
            var22 = LangObjType.coerceIntNum(var2);
         } catch (ClassCastException var15) {
            throw new WrongType(var15, "bitwise-bit-field", 1, var2);
         }

         try {
            var5 = ((Number)var3).intValue();
         } catch (ClassCastException var14) {
            throw new WrongType(var14, "bitwise-bit-field", 2, var3);
         }

         try {
            var6 = ((Number)var4).intValue();
         } catch (ClassCastException var13) {
            throw new WrongType(var13, "bitwise-bit-field", 3, var4);
         }

         return bitwiseBitField(var22, var5, var6);
      case 52:
         try {
            var22 = LangObjType.coerceIntNum(var2);
         } catch (ClassCastException var12) {
            throw new WrongType(var12, "bitwise-if", 1, var2);
         }

         try {
            var23 = LangObjType.coerceIntNum(var3);
         } catch (ClassCastException var11) {
            throw new WrongType(var11, "bitwise-if", 2, var3);
         }

         IntNum var24;
         try {
            var24 = LangObjType.coerceIntNum(var4);
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "bitwise-if", 3, var4);
         }

         return bitwiseIf(var22, var23, var24);
      case 59:
         try {
            var22 = LangObjType.coerceIntNum(var2);
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "bitwise-reverse-bit-field", 1, var2);
         }

         try {
            var5 = ((Number)var3).intValue();
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "bitwise-reverse-bit-field", 2, var3);
         }

         try {
            var6 = ((Number)var4).intValue();
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "bitwise-reverse-bit-field", 3, var4);
         }

         return bitwiseReverseBitField(var22, var5, var6);
      default:
         return super.apply3(var1, var2, var3, var4);
      }
   }

   public Object apply4(ModuleMethod var1, Object var2, Object var3, Object var4, Object var5) {
      int var6;
      int var7;
      IntNum var17;
      switch(var1.selector) {
      case 50:
         try {
            var17 = LangObjType.coerceIntNum(var2);
         } catch (ClassCastException var16) {
            throw new WrongType(var16, "bitwise-copy-bit-field", 1, var2);
         }

         try {
            var6 = ((Number)var3).intValue();
         } catch (ClassCastException var15) {
            throw new WrongType(var15, "bitwise-copy-bit-field", 2, var3);
         }

         try {
            var7 = ((Number)var4).intValue();
         } catch (ClassCastException var14) {
            throw new WrongType(var14, "bitwise-copy-bit-field", 3, var4);
         }

         IntNum var18;
         try {
            var18 = LangObjType.coerceIntNum(var5);
         } catch (ClassCastException var13) {
            throw new WrongType(var13, "bitwise-copy-bit-field", 4, var5);
         }

         return bitwiseCopyBitField(var17, var6, var7, var18);
      case 58:
         try {
            var17 = LangObjType.coerceIntNum(var2);
         } catch (ClassCastException var12) {
            throw new WrongType(var12, "bitwise-rotate-bit-field", 1, var2);
         }

         try {
            var6 = ((Number)var3).intValue();
         } catch (ClassCastException var11) {
            throw new WrongType(var11, "bitwise-rotate-bit-field", 2, var3);
         }

         try {
            var7 = ((Number)var4).intValue();
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "bitwise-rotate-bit-field", 3, var4);
         }

         int var8;
         try {
            var8 = ((Number)var5).intValue();
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "bitwise-rotate-bit-field", 4, var5);
         }

         return bitwiseRotateBitField(var17, var6, var7, var8);
      default:
         return super.apply4(var1, var2, var3, var4, var5);
      }
   }

   public Object applyN(ModuleMethod var1, Object[] var2) {
      IntNum[] var3;
      IntNum var4;
      int var5;
      Object var8;
      switch(var1.selector) {
      case 12:
         return max(var2);
      case 13:
         return min(var2);
      case 14:
      case 15:
      case 16:
      default:
         return super.applyN(var1, var2);
      case 17:
         var5 = var2.length;
         var3 = new IntNum[var5];

         while(true) {
            --var5;
            if(var5 < 0) {
               return gcd(var3);
            }

            var8 = var2[var5];

            try {
               var4 = LangObjType.coerceIntNum(var8);
            } catch (ClassCastException var7) {
               throw new WrongType(var7, "gcd", 0, var8);
            }

            var3[var5] = var4;
         }
      case 18:
         var5 = var2.length;
         var3 = new IntNum[var5];

         while(true) {
            --var5;
            if(var5 < 0) {
               return lcm(var3);
            }

            var8 = var2[var5];

            try {
               var4 = LangObjType.coerceIntNum(var8);
            } catch (ClassCastException var6) {
               throw new WrongType(var6, "lcm", 0, var8);
            }

            var3[var5] = var4;
         }
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
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 7:
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
         if(!(var2 instanceof Number)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 10:
         if(RealNum.asRealNumOrNull(var2) != null) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 11:
         if(RealNum.asRealNumOrNull(var2) != null) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 12:
      case 13:
      case 15:
      case 16:
      case 17:
      case 18:
      case 25:
      case 33:
      case 37:
      case 38:
      case 47:
      case 48:
      case 49:
      case 50:
      case 51:
      case 52:
      case 53:
      case 58:
      case 59:
      case 61:
      case 63:
      case 66:
      default:
         return super.match1(var1, var2, var3);
      case 14:
         if(!(var2 instanceof Number)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 19:
         if(RatNum.asRatNumOrNull(var2) != null) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 20:
         if(RatNum.asRatNumOrNull(var2) != null) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 21:
         if(RealNum.asRealNumOrNull(var2) != null) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 22:
         if(RealNum.asRealNumOrNull(var2) != null) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 23:
         if(RealNum.asRealNumOrNull(var2) != null) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 24:
         if(RealNum.asRealNumOrNull(var2) != null) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 26:
         if(!(var2 instanceof Complex)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 27:
         if(!(var2 instanceof Complex)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 28:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 29:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 30:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 31:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 32:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 34:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 35:
         if(!(var2 instanceof Quantity)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 36:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 39:
         if(!(var2 instanceof Complex)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 40:
         if(!(var2 instanceof Complex)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 41:
         if(!(var2 instanceof Number)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 42:
         if(!(var2 instanceof Complex)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 43:
         if(!(var2 instanceof Number)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 44:
         if(!(var2 instanceof Number)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 45:
         if(!(var2 instanceof Number)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 46:
         if(!(var2 instanceof Number)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 54:
         if(IntNum.asIntNumOrNull(var2) != null) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 55:
         if(IntNum.asIntNumOrNull(var2) != null) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 56:
         if(IntNum.asIntNumOrNull(var2) != null) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 57:
         if(IntNum.asIntNumOrNull(var2) != null) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 60:
         if(!(var2 instanceof Number)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 62:
         if(var2 instanceof CharSequence) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 64:
         if(!(var2 instanceof Quantity)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 65:
         if(!(var2 instanceof Quantity)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 67:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      }
   }

   public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
      switch(var1.selector) {
      case 15:
         if(RealNum.asRealNumOrNull(var2) != null) {
            var4.value1 = var2;
            if(RealNum.asRealNumOrNull(var3) != null) {
               var4.value2 = var3;
               var4.proc = var1;
               var4.pc = 2;
               return 0;
            }

            return -786430;
         }

         return -786431;
      case 16:
         if(RealNum.asRealNumOrNull(var2) != null) {
            var4.value1 = var2;
            if(RealNum.asRealNumOrNull(var3) != null) {
               var4.value2 = var3;
               var4.proc = var1;
               var4.pc = 2;
               return 0;
            }

            return -786430;
         }

         return -786431;
      case 25:
         if(RealNum.asRealNumOrNull(var2) != null) {
            var4.value1 = var2;
            if(RealNum.asRealNumOrNull(var3) != null) {
               var4.value2 = var3;
               var4.proc = var1;
               var4.pc = 2;
               return 0;
            }

            return -786430;
         }

         return -786431;
      case 33:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 37:
         if(RealNum.asRealNumOrNull(var2) != null) {
            var4.value1 = var2;
            if(RealNum.asRealNumOrNull(var3) != null) {
               var4.value2 = var3;
               var4.proc = var1;
               var4.pc = 2;
               return 0;
            }

            return -786430;
         }

         return -786431;
      case 38:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 48:
         if(IntNum.asIntNumOrNull(var2) != null) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }

         return -786431;
      case 53:
         if(IntNum.asIntNumOrNull(var2) != null) {
            var4.value1 = var2;
            if(IntNum.asIntNumOrNull(var3) != null) {
               var4.value2 = var3;
               var4.proc = var1;
               var4.pc = 2;
               return 0;
            }

            return -786430;
         }

         return -786431;
      case 60:
         if(!(var2 instanceof Number)) {
            return -786431;
         }

         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 62:
         if(var2 instanceof CharSequence) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }

         return -786431;
      case 66:
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
      case 47:
         var5.value1 = var2;
         if(IntNum.asIntNumOrNull(var3) != null) {
            var5.value2 = var3;
            if(IntNum.asIntNumOrNull(var4) != null) {
               var5.value3 = var4;
               var5.proc = var1;
               var5.pc = 3;
               return 0;
            }

            return -786429;
         }

         return -786430;
      case 49:
         if(IntNum.asIntNumOrNull(var2) != null) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         }

         return -786431;
      case 51:
         if(IntNum.asIntNumOrNull(var2) != null) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         }

         return -786431;
      case 52:
         if(IntNum.asIntNumOrNull(var2) != null) {
            var5.value1 = var2;
            if(IntNum.asIntNumOrNull(var3) != null) {
               var5.value2 = var3;
               if(IntNum.asIntNumOrNull(var4) != null) {
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
      case 59:
         if(IntNum.asIntNumOrNull(var2) != null) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         }

         return -786431;
      default:
         return super.match3(var1, var2, var3, var4, var5);
      }
   }

   public int match4(ModuleMethod var1, Object var2, Object var3, Object var4, Object var5, CallContext var6) {
      switch(var1.selector) {
      case 50:
         if(IntNum.asIntNumOrNull(var2) != null) {
            var6.value1 = var2;
            var6.value2 = var3;
            var6.value3 = var4;
            if(IntNum.asIntNumOrNull(var5) != null) {
               var6.value4 = var5;
               var6.proc = var1;
               var6.pc = 4;
               return 0;
            }

            return -786428;
         }

         return -786431;
      case 58:
         if(IntNum.asIntNumOrNull(var2) != null) {
            var6.value1 = var2;
            var6.value2 = var3;
            var6.value3 = var4;
            var6.value4 = var5;
            var6.proc = var1;
            var6.pc = 4;
            return 0;
         }

         return -786431;
      default:
         return super.match4(var1, var2, var3, var4, var5, var6);
      }
   }

   public int matchN(ModuleMethod var1, Object[] var2, CallContext var3) {
      switch(var1.selector) {
      case 12:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 13:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 14:
      case 15:
      case 16:
      default:
         return super.matchN(var1, var2, var3);
      case 17:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 18:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      }
   }

   public final void run(CallContext var1) {
      Consumer var2 = var1.consumer;
      atan = new GenericProc("atan");
      atan.setProperties(new Object[]{lambda$Fn1, lambda$Fn2});
      sqrt = new GenericProc("sqrt");
      sqrt.setProperties(new Object[]{lambda$Fn3, lambda$Fn4});
   }
}
