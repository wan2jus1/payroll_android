package gnu.commonlisp.lisp;

import gnu.commonlisp.lang.CommonLisp;
import gnu.commonlisp.lang.Lisp2;
import gnu.commonlisp.lang.Symbols;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.AddOp;
import gnu.kawa.functions.Apply;
import gnu.lists.Consumer;
import gnu.lists.FString;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.Sequence;
import gnu.lists.SimpleVector;
import gnu.mapping.CallContext;
import gnu.mapping.Environment;
import gnu.mapping.Procedure;
import gnu.mapping.PropertyLocation;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.lib.strings;
import kawa.standard.Scheme;

public class PrimOps extends ModuleBody {

   public static final PrimOps $instance = new PrimOps();
   static final SimpleSymbol Lit0 = (SimpleSymbol)(new SimpleSymbol("t")).readResolve();
   static final IntNum Lit1 = IntNum.make(0);
   static final SimpleSymbol Lit10 = (SimpleSymbol)(new SimpleSymbol("setplist")).readResolve();
   static final SimpleSymbol Lit11 = (SimpleSymbol)(new SimpleSymbol("plist-get")).readResolve();
   static final SimpleSymbol Lit12 = (SimpleSymbol)(new SimpleSymbol("plist-put")).readResolve();
   static final SimpleSymbol Lit13 = (SimpleSymbol)(new SimpleSymbol("plist-remprop")).readResolve();
   static final SimpleSymbol Lit14 = (SimpleSymbol)(new SimpleSymbol("plist-member")).readResolve();
   static final SimpleSymbol Lit15 = (SimpleSymbol)(new SimpleSymbol("get")).readResolve();
   static final SimpleSymbol Lit16 = (SimpleSymbol)(new SimpleSymbol("put")).readResolve();
   static final SimpleSymbol Lit17 = (SimpleSymbol)(new SimpleSymbol("symbol-value")).readResolve();
   static final SimpleSymbol Lit18 = (SimpleSymbol)(new SimpleSymbol("set")).readResolve();
   static final SimpleSymbol Lit19 = (SimpleSymbol)(new SimpleSymbol("symbol-function")).readResolve();
   static final SimpleSymbol Lit2 = (SimpleSymbol)(new SimpleSymbol("car")).readResolve();
   static final SimpleSymbol Lit20 = (SimpleSymbol)(new SimpleSymbol("fset")).readResolve();
   static final SimpleSymbol Lit21 = (SimpleSymbol)(new SimpleSymbol("apply")).readResolve();
   static final SimpleSymbol Lit22 = (SimpleSymbol)(new SimpleSymbol("length")).readResolve();
   static final SimpleSymbol Lit23 = (SimpleSymbol)(new SimpleSymbol("arrayp")).readResolve();
   static final SimpleSymbol Lit24 = (SimpleSymbol)(new SimpleSymbol("aref")).readResolve();
   static final SimpleSymbol Lit25 = (SimpleSymbol)(new SimpleSymbol("aset")).readResolve();
   static final SimpleSymbol Lit26 = (SimpleSymbol)(new SimpleSymbol("fillarray")).readResolve();
   static final SimpleSymbol Lit27 = (SimpleSymbol)(new SimpleSymbol("stringp")).readResolve();
   static final SimpleSymbol Lit28 = (SimpleSymbol)(new SimpleSymbol("make-string")).readResolve();
   static final SimpleSymbol Lit29 = (SimpleSymbol)(new SimpleSymbol("substring")).readResolve();
   static final SimpleSymbol Lit3 = (SimpleSymbol)(new SimpleSymbol("cdr")).readResolve();
   static final SimpleSymbol Lit30 = (SimpleSymbol)(new SimpleSymbol("char-to-string")).readResolve();
   static final SimpleSymbol Lit31 = (SimpleSymbol)(new SimpleSymbol("functionp")).readResolve();
   static final SimpleSymbol Lit4 = (SimpleSymbol)(new SimpleSymbol("setcar")).readResolve();
   static final SimpleSymbol Lit5 = (SimpleSymbol)(new SimpleSymbol("setcdr")).readResolve();
   static final SimpleSymbol Lit6 = (SimpleSymbol)(new SimpleSymbol("boundp")).readResolve();
   static final SimpleSymbol Lit7 = (SimpleSymbol)(new SimpleSymbol("symbolp")).readResolve();
   static final SimpleSymbol Lit8 = (SimpleSymbol)(new SimpleSymbol("symbol-name")).readResolve();
   static final SimpleSymbol Lit9 = (SimpleSymbol)(new SimpleSymbol("symbol-plist")).readResolve();
   public static final ModuleMethod apply;
   public static final ModuleMethod aref;
   public static final ModuleMethod arrayp;
   public static final ModuleMethod aset;
   public static final ModuleMethod boundp;
   public static final ModuleMethod car;
   public static final ModuleMethod cdr;
   public static final ModuleMethod char$Mnto$Mnstring;
   public static final ModuleMethod fillarray;
   public static final ModuleMethod fset;
   public static final ModuleMethod functionp;
   public static final ModuleMethod get;
   public static final ModuleMethod length;
   public static final ModuleMethod make$Mnstring;
   public static final ModuleMethod plist$Mnget;
   public static final ModuleMethod plist$Mnmember;
   public static final ModuleMethod plist$Mnput;
   public static final ModuleMethod plist$Mnremprop;
   public static final ModuleMethod put;
   public static final ModuleMethod set;
   public static final ModuleMethod setcar;
   public static final ModuleMethod setcdr;
   public static final ModuleMethod setplist;
   public static final ModuleMethod stringp;
   public static final ModuleMethod substring;
   public static final ModuleMethod symbol$Mnfunction;
   public static final ModuleMethod symbol$Mnname;
   public static final ModuleMethod symbol$Mnplist;
   public static final ModuleMethod symbol$Mnvalue;
   public static final ModuleMethod symbolp;


   static {
      PrimOps var0 = $instance;
      car = new ModuleMethod(var0, 1, Lit2, 4097);
      cdr = new ModuleMethod(var0, 2, Lit3, 4097);
      setcar = new ModuleMethod(var0, 3, Lit4, 8194);
      setcdr = new ModuleMethod(var0, 4, Lit5, 8194);
      boundp = new ModuleMethod(var0, 5, Lit6, 4097);
      symbolp = new ModuleMethod(var0, 6, Lit7, 4097);
      symbol$Mnname = new ModuleMethod(var0, 7, Lit8, 4097);
      symbol$Mnplist = new ModuleMethod(var0, 8, Lit9, 4097);
      setplist = new ModuleMethod(var0, 9, Lit10, 8194);
      plist$Mnget = new ModuleMethod(var0, 10, Lit11, 12290);
      plist$Mnput = new ModuleMethod(var0, 12, Lit12, 12291);
      plist$Mnremprop = new ModuleMethod(var0, 13, Lit13, 8194);
      plist$Mnmember = new ModuleMethod(var0, 14, Lit14, 8194);
      get = new ModuleMethod(var0, 15, Lit15, 12290);
      put = new ModuleMethod(var0, 17, Lit16, 12291);
      symbol$Mnvalue = new ModuleMethod(var0, 18, Lit17, 4097);
      set = new ModuleMethod(var0, 19, Lit18, 8194);
      symbol$Mnfunction = new ModuleMethod(var0, 20, Lit19, 4097);
      fset = new ModuleMethod(var0, 21, Lit20, 8194);
      apply = new ModuleMethod(var0, 22, Lit21, -4095);
      length = new ModuleMethod(var0, 23, Lit22, 4097);
      arrayp = new ModuleMethod(var0, 24, Lit23, 4097);
      aref = new ModuleMethod(var0, 25, Lit24, 8194);
      aset = new ModuleMethod(var0, 26, Lit25, 12291);
      fillarray = new ModuleMethod(var0, 27, Lit26, 8194);
      stringp = new ModuleMethod(var0, 28, Lit27, 4097);
      make$Mnstring = new ModuleMethod(var0, 29, Lit28, 8194);
      substring = new ModuleMethod(var0, 30, Lit29, 12290);
      char$Mnto$Mnstring = new ModuleMethod(var0, 32, Lit30, 4097);
      functionp = new ModuleMethod(var0, 33, Lit31, 4097);
      $instance.run();
   }

   public PrimOps() {
      ModuleInfo.register(this);
   }

   public static Object apply(Object var0, Object ... var1) {
      Procedure var2;
      if(misc.isSymbol(var0)) {
         var2 = (Procedure)symbolFunction(var0);
      } else {
         var2 = (Procedure)var0;
      }

      return var2.applyN(Apply.getArguments(var1, 0, apply));
   }

   public static Object aref(SimpleVector var0, int var1) {
      return var0.get(var1);
   }

   public static boolean arrayp(Object var0) {
      return var0 instanceof SimpleVector;
   }

   public static Object aset(SimpleVector var0, int var1, Object var2) {
      var0.set(var1, var2);
      return var2;
   }

   public static boolean boundp(Object var0) {
      return Symbols.isBound(var0);
   }

   public static Object car(Object var0) {
      return var0 == LList.Empty?var0:((Pair)var0).getCar();
   }

   public static Object cdr(Object var0) {
      return var0 == LList.Empty?var0:((Pair)var0).getCdr();
   }

   public static FString charToString(Object var0) {
      return new FString(1, CommonLisp.asChar(var0));
   }

   public static Object fillarray(SimpleVector var0, Object var1) {
      var0.fill(var1);
      return var1;
   }

   public static void fset(Object var0, Object var1) {
      Symbols.setFunctionBinding(Environment.getCurrent(), var0, var1);
   }

   public static boolean functionp(Object var0) {
      return var0 instanceof Procedure;
   }

   public static Object get(Symbol var0, Object var1) {
      return get(var0, var1, LList.Empty);
   }

   public static Object get(Symbol var0, Object var1, Object var2) {
      return PropertyLocation.getProperty(var0, var1, var2);
   }

   public static int length(Sequence var0) {
      return var0.size();
   }

   public static FString makeString(int var0, Object var1) {
      return new FString(var0, CommonLisp.asChar(var1));
   }

   public static Object plistGet(Object var0, Object var1) {
      return plistGet(var0, var1, Boolean.FALSE);
   }

   public static Object plistGet(Object var0, Object var1, Object var2) {
      return PropertyLocation.plistGet(var0, var1, var2);
   }

   public static Object plistMember(Object var0, Object var1) {
      return PropertyLocation.plistGet(var0, var1, Values.empty) == Values.empty?LList.Empty:Lit0;
   }

   public static Object plistPut(Object var0, Object var1, Object var2) {
      return PropertyLocation.plistPut(var0, var1, var2);
   }

   public static Object plistRemprop(Object var0, Object var1) {
      return PropertyLocation.plistRemove(var0, var1);
   }

   public static void put(Object var0, Object var1, Object var2) {
      PropertyLocation.putProperty(var0, var1, var2);
   }

   public static void set(Object var0, Object var1) {
      Environment.getCurrent().put((Symbol)Symbols.getSymbol(var0), var1);
   }

   public static void setcar(Pair var0, Object var1) {
      lists.setCar$Ex(var0, var1);
   }

   public static void setcdr(Pair var0, Object var1) {
      lists.setCdr$Ex(var0, var1);
   }

   public static Object setplist(Object var0, Object var1) {
      PropertyLocation.setPropertyList(var0, var1);
      return var1;
   }

   public static boolean stringp(Object var0) {
      return var0 instanceof CharSequence;
   }

   public static FString substring(CharSequence var0, Object var1) {
      return substring(var0, var1, LList.Empty);
   }

   public static FString substring(CharSequence var0, Object var1, Object var2) {
      Object var3 = var2;
      if(var2 == LList.Empty) {
         var3 = Integer.valueOf(strings.stringLength(var0));
      }

      var2 = var3;
      if(Scheme.numLss.apply2(var3, Lit1) != Boolean.FALSE) {
         var2 = AddOp.$Mn.apply2(Integer.valueOf(strings.stringLength(var0)), var3);
      }

      var3 = var1;
      if(Scheme.numLss.apply2(var1, Lit1) != Boolean.FALSE) {
         var3 = AddOp.$Mn.apply2(Integer.valueOf(strings.stringLength(var0)), var1);
      }

      return new FString(var0, ((Number)var3).intValue(), ((Number)AddOp.$Mn.apply2(var2, var3)).intValue());
   }

   public static Object symbolFunction(Object var0) {
      return Symbols.getFunctionBinding(var0);
   }

   public static Object symbolName(Object var0) {
      return Symbols.getPrintName(var0);
   }

   public static Object symbolPlist(Object var0) {
      return PropertyLocation.getPropertyList(var0);
   }

   public static Object symbolValue(Object var0) {
      return Environment.getCurrent().get((Symbol)Symbols.getSymbol(var0));
   }

   public static boolean symbolp(Object var0) {
      return Symbols.isSymbol(var0);
   }

   public Object apply1(ModuleMethod var1, Object var2) {
      switch(var1.selector) {
      case 1:
         return car(var2);
      case 2:
         return cdr(var2);
      case 5:
         if(boundp(var2)) {
            return Lisp2.TRUE;
         }

         return LList.Empty;
      case 6:
         if(symbolp(var2)) {
            return Lisp2.TRUE;
         }

         return LList.Empty;
      case 7:
         return symbolName(var2);
      case 8:
         return symbolPlist(var2);
      case 18:
         return symbolValue(var2);
      case 20:
         return symbolFunction(var2);
      case 23:
         Sequence var4;
         try {
            var4 = (Sequence)var2;
         } catch (ClassCastException var3) {
            throw new WrongType(var3, "length", 1, var2);
         }

         return Integer.valueOf(length(var4));
      case 24:
         if(arrayp(var2)) {
            return Lisp2.TRUE;
         }

         return LList.Empty;
      case 28:
         if(stringp(var2)) {
            return Lisp2.TRUE;
         }

         return LList.Empty;
      case 32:
         return charToString(var2);
      case 33:
         if(functionp(var2)) {
            return Lisp2.TRUE;
         }

         return LList.Empty;
      default:
         return super.apply1(var1, var2);
      }
   }

   public Object apply2(ModuleMethod var1, Object var2, Object var3) {
      int var4;
      SimpleVector var14;
      Pair var16;
      switch(var1.selector) {
      case 3:
         try {
            var16 = (Pair)var2;
         } catch (ClassCastException var12) {
            throw new WrongType(var12, "setcar", 1, var2);
         }

         setcar(var16, var3);
         return Values.empty;
      case 4:
         try {
            var16 = (Pair)var2;
         } catch (ClassCastException var11) {
            throw new WrongType(var11, "setcdr", 1, var2);
         }

         setcdr(var16, var3);
         return Values.empty;
      case 5:
      case 6:
      case 7:
      case 8:
      case 11:
      case 12:
      case 16:
      case 17:
      case 18:
      case 20:
      case 22:
      case 23:
      case 24:
      case 26:
      case 28:
      default:
         return super.apply2(var1, var2, var3);
      case 9:
         return setplist(var2, var3);
      case 10:
         return plistGet(var2, var3);
      case 13:
         return plistRemprop(var2, var3);
      case 14:
         return plistMember(var2, var3);
      case 15:
         Symbol var15;
         try {
            var15 = (Symbol)var2;
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "get", 1, var2);
         }

         return get(var15, var3);
      case 19:
         set(var2, var3);
         return Values.empty;
      case 21:
         fset(var2, var3);
         return Values.empty;
      case 25:
         try {
            var14 = (SimpleVector)var2;
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "aref", 1, var2);
         }

         try {
            var4 = ((Number)var3).intValue();
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "aref", 2, var3);
         }

         return aref(var14, var4);
      case 27:
         try {
            var14 = (SimpleVector)var2;
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "fillarray", 1, var2);
         }

         return fillarray(var14, var3);
      case 29:
         try {
            var4 = ((Number)var2).intValue();
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "make-string", 1, var2);
         }

         return makeString(var4, var3);
      case 30:
         CharSequence var13;
         try {
            var13 = (CharSequence)var2;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "substring", 1, var2);
         }

         return substring(var13, var3);
      }
   }

   public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
      switch(var1.selector) {
      case 10:
         return plistGet(var2, var3, var4);
      case 12:
         return plistPut(var2, var3, var4);
      case 15:
         Symbol var12;
         try {
            var12 = (Symbol)var2;
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "get", 1, var2);
         }

         return get(var12, var3, var4);
      case 17:
         put(var2, var3, var4);
         return Values.empty;
      case 26:
         SimpleVector var11;
         try {
            var11 = (SimpleVector)var2;
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "aset", 1, var2);
         }

         int var5;
         try {
            var5 = ((Number)var3).intValue();
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "aset", 2, var3);
         }

         return aset(var11, var5, var4);
      case 30:
         CharSequence var10;
         try {
            var10 = (CharSequence)var2;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "substring", 1, var2);
         }

         return substring(var10, var3, var4);
      default:
         return super.apply3(var1, var2, var3, var4);
      }
   }

   public Object applyN(ModuleMethod var1, Object[] var2) {
      if(var1.selector != 22) {
         return super.applyN(var1, var2);
      } else {
         Object var5 = var2[0];
         int var4 = var2.length - 1;
         Object[] var3 = new Object[var4];

         while(true) {
            --var4;
            if(var4 < 0) {
               return apply(var5, var3);
            }

            var3[var4] = var2[var4 + 1];
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
      case 18:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 20:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 23:
         if(!(var2 instanceof Sequence)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 24:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 28:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 32:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 33:
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
      case 3:
         if(!(var2 instanceof Pair)) {
            return -786431;
         }

         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
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
      case 6:
      case 7:
      case 8:
      case 11:
      case 12:
      case 16:
      case 17:
      case 18:
      case 20:
      case 22:
      case 23:
      case 24:
      case 26:
      case 28:
      default:
         return super.match2(var1, var2, var3, var4);
      case 9:
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
      case 13:
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
      case 15:
         if(!(var2 instanceof Symbol)) {
            return -786431;
         }

         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 19:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 21:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 25:
         if(!(var2 instanceof SimpleVector)) {
            return -786431;
         }

         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 27:
         if(!(var2 instanceof SimpleVector)) {
            return -786431;
         }

         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 29:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 30:
         if(var2 instanceof CharSequence) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return -786431;
         }
      }
   }

   public int match3(ModuleMethod var1, Object var2, Object var3, Object var4, CallContext var5) {
      switch(var1.selector) {
      case 10:
         var5.value1 = var2;
         var5.value2 = var3;
         var5.value3 = var4;
         var5.proc = var1;
         var5.pc = 3;
         return 0;
      case 12:
         var5.value1 = var2;
         var5.value2 = var3;
         var5.value3 = var4;
         var5.proc = var1;
         var5.pc = 3;
         return 0;
      case 15:
         if(!(var2 instanceof Symbol)) {
            return -786431;
         }

         var5.value1 = var2;
         var5.value2 = var3;
         var5.value3 = var4;
         var5.proc = var1;
         var5.pc = 3;
         return 0;
      case 17:
         var5.value1 = var2;
         var5.value2 = var3;
         var5.value3 = var4;
         var5.proc = var1;
         var5.pc = 3;
         return 0;
      case 26:
         if(!(var2 instanceof SimpleVector)) {
            return -786431;
         }

         var5.value1 = var2;
         var5.value2 = var3;
         var5.value3 = var4;
         var5.proc = var1;
         var5.pc = 3;
         return 0;
      case 30:
         if(var2 instanceof CharSequence) {
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

   public int matchN(ModuleMethod var1, Object[] var2, CallContext var3) {
      if(var1.selector == 22) {
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
}
