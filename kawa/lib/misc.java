package kawa.lib;

import gnu.expr.GenericProc;
import gnu.expr.Keyword;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.expr.Symbols;
import gnu.kawa.lispexpr.LangObjType;
import gnu.kawa.lispexpr.LispLanguage;
import gnu.kawa.xml.KNode;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.CallContext;
import gnu.mapping.Environment;
import gnu.mapping.Namespace;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import gnu.text.Path;
import kawa.Version;
import kawa.lang.Promise;
import kawa.lib.ports;
import kawa.standard.Scheme;
import kawa.standard.throw_name;

public class misc extends ModuleBody {

   public static final misc $instance = new misc();
   static final IntNum Lit0 = IntNum.make(4);
   static final IntNum Lit1 = IntNum.make(5);
   static final SimpleSymbol Lit10 = (SimpleSymbol)(new SimpleSymbol("symbol-prefix")).readResolve();
   static final SimpleSymbol Lit11 = (SimpleSymbol)(new SimpleSymbol("namespace-uri")).readResolve();
   static final SimpleSymbol Lit12 = (SimpleSymbol)(new SimpleSymbol("namespace-prefix")).readResolve();
   static final SimpleSymbol Lit13 = (SimpleSymbol)(new SimpleSymbol("string->symbol")).readResolve();
   static final SimpleSymbol Lit14 = (SimpleSymbol)(new SimpleSymbol("procedure?")).readResolve();
   static final SimpleSymbol Lit15 = (SimpleSymbol)(new SimpleSymbol("values")).readResolve();
   static final SimpleSymbol Lit16 = (SimpleSymbol)(new SimpleSymbol("environment-bound?")).readResolve();
   static final SimpleSymbol Lit17 = (SimpleSymbol)(new SimpleSymbol("null-environment")).readResolve();
   static final SimpleSymbol Lit18 = (SimpleSymbol)(new SimpleSymbol("scheme-report-environment")).readResolve();
   static final SimpleSymbol Lit19 = (SimpleSymbol)(new SimpleSymbol("interaction-environment")).readResolve();
   static final Keyword Lit2 = Keyword.make("setter");
   static final SimpleSymbol Lit20 = (SimpleSymbol)(new SimpleSymbol("scheme-implementation-version")).readResolve();
   static final SimpleSymbol Lit21 = (SimpleSymbol)(new SimpleSymbol("set-procedure-property!")).readResolve();
   static final SimpleSymbol Lit22 = (SimpleSymbol)(new SimpleSymbol("procedure-property")).readResolve();
   static final SimpleSymbol Lit23 = (SimpleSymbol)(new SimpleSymbol("dynamic-wind")).readResolve();
   static final SimpleSymbol Lit24 = (SimpleSymbol)(new SimpleSymbol("force")).readResolve();
   static final SimpleSymbol Lit25 = (SimpleSymbol)(new SimpleSymbol("error")).readResolve();
   static final SimpleSymbol Lit26 = (SimpleSymbol)(new SimpleSymbol("base-uri")).readResolve();
   static final SimpleSymbol Lit27 = (SimpleSymbol)(new SimpleSymbol("gentemp")).readResolve();
   static final SimpleSymbol Lit28 = (SimpleSymbol)(new SimpleSymbol("add-procedure-properties")).readResolve();
   static final SimpleSymbol Lit3 = (SimpleSymbol)(new SimpleSymbol("misc-error")).readResolve();
   static final SimpleSymbol Lit4 = (SimpleSymbol)(new SimpleSymbol("boolean?")).readResolve();
   static final SimpleSymbol Lit5 = (SimpleSymbol)(new SimpleSymbol("symbol?")).readResolve();
   static final SimpleSymbol Lit6 = (SimpleSymbol)(new SimpleSymbol("symbol->string")).readResolve();
   static final SimpleSymbol Lit7 = (SimpleSymbol)(new SimpleSymbol("symbol-local-name")).readResolve();
   static final SimpleSymbol Lit8 = (SimpleSymbol)(new SimpleSymbol("symbol-namespace")).readResolve();
   static final SimpleSymbol Lit9 = (SimpleSymbol)(new SimpleSymbol("symbol-namespace-uri")).readResolve();
   public static final ModuleMethod add$Mnprocedure$Mnproperties;
   public static final ModuleMethod base$Mnuri;
   public static final ModuleMethod boolean$Qu;
   public static final ModuleMethod dynamic$Mnwind;
   public static final ModuleMethod environment$Mnbound$Qu;
   public static final ModuleMethod error;
   public static final ModuleMethod force;
   public static final ModuleMethod gentemp;
   public static final ModuleMethod interaction$Mnenvironment;
   static final ModuleMethod lambda$Fn1;
   static final ModuleMethod lambda$Fn2;
   public static final ModuleMethod namespace$Mnprefix;
   public static final ModuleMethod namespace$Mnuri;
   public static final ModuleMethod null$Mnenvironment;
   public static final GenericProc procedure$Mnproperty;
   static final ModuleMethod procedure$Mnproperty$Fn3;
   public static final ModuleMethod procedure$Qu;
   public static final ModuleMethod scheme$Mnimplementation$Mnversion;
   public static final ModuleMethod scheme$Mnreport$Mnenvironment;
   public static final ModuleMethod set$Mnprocedure$Mnproperty$Ex;
   public static final ModuleMethod string$Mn$Grsymbol;
   public static final GenericProc symbol$Eq$Qu;
   public static final ModuleMethod symbol$Mn$Grstring;
   public static final ModuleMethod symbol$Mnlocal$Mnname;
   public static final ModuleMethod symbol$Mnnamespace;
   public static final ModuleMethod symbol$Mnnamespace$Mnuri;
   public static final ModuleMethod symbol$Mnprefix;
   public static final ModuleMethod symbol$Qu;
   public static final ModuleMethod values;


   static {
      misc var0 = $instance;
      boolean$Qu = new ModuleMethod(var0, 3, Lit4, 4097);
      symbol$Qu = new ModuleMethod(var0, 4, Lit5, 4097);
      symbol$Mn$Grstring = new ModuleMethod(var0, 5, Lit6, 4097);
      ModuleMethod var1 = new ModuleMethod(var0, 6, (Object)null, 8194);
      var1.setProperty("source-location", "misc.scm:25");
      lambda$Fn1 = var1;
      var1 = new ModuleMethod(var0, 7, (Object)null, -4094);
      var1.setProperty("source-location", "misc.scm:27");
      lambda$Fn2 = var1;
      symbol$Mnlocal$Mnname = new ModuleMethod(var0, 8, Lit7, 4097);
      symbol$Mnnamespace = new ModuleMethod(var0, 9, Lit8, 4097);
      symbol$Mnnamespace$Mnuri = new ModuleMethod(var0, 10, Lit9, 4097);
      symbol$Mnprefix = new ModuleMethod(var0, 11, Lit10, 4097);
      namespace$Mnuri = new ModuleMethod(var0, 12, Lit11, 4097);
      namespace$Mnprefix = new ModuleMethod(var0, 13, Lit12, 4097);
      string$Mn$Grsymbol = new ModuleMethod(var0, 14, Lit13, 4097);
      procedure$Qu = new ModuleMethod(var0, 15, Lit14, 4097);
      values = new ModuleMethod(var0, 16, Lit15, -4096);
      environment$Mnbound$Qu = new ModuleMethod(var0, 17, Lit16, 8194);
      null$Mnenvironment = new ModuleMethod(var0, 18, Lit17, 4096);
      scheme$Mnreport$Mnenvironment = new ModuleMethod(var0, 20, Lit18, 4097);
      interaction$Mnenvironment = new ModuleMethod(var0, 21, Lit19, 0);
      scheme$Mnimplementation$Mnversion = new ModuleMethod(var0, 22, Lit20, 0);
      set$Mnprocedure$Mnproperty$Ex = new ModuleMethod(var0, 23, Lit21, 12291);
      procedure$Mnproperty$Fn3 = new ModuleMethod(var0, 24, Lit22, 12290);
      dynamic$Mnwind = new ModuleMethod(var0, 26, Lit23, 12291);
      force = new ModuleMethod(var0, 27, Lit24, 4097);
      error = new ModuleMethod(var0, 28, Lit25, -4095);
      base$Mnuri = new ModuleMethod(var0, 29, Lit26, 4096);
      gentemp = new ModuleMethod(var0, 31, Lit27, 0);
      add$Mnprocedure$Mnproperties = new ModuleMethod(var0, 32, Lit28, -4095);
      $instance.run();
   }

   public misc() {
      ModuleInfo.register(this);
   }

   public static void addProcedureProperties(GenericProc var0, Object ... var1) {
      var0.setProperties(var1);
   }

   public static Object baseUri() {
      return baseUri((Object)null);
   }

   public static Object baseUri(Object var0) {
      Path var2;
      if(var0 == null) {
         var2 = Path.currentPath();
      } else {
         var2 = ((KNode)var0).baseURI();
      }

      Object var1 = var2;
      if(var2 == Values.empty) {
         var1 = Boolean.FALSE;
      }

      return var1;
   }

   public static Object dynamicWind(Object var0, Object var1, Object var2) {
      Scheme.applyToArgs.apply1(var0);

      try {
         var0 = Scheme.applyToArgs.apply1(var1);
      } finally {
         Scheme.applyToArgs.apply1(var2);
      }

      return var0;
   }

   public static Object error$V(Object var0, Object[] var1) {
      misc.frame var2 = new misc.frame();
      var2.msg = var0;
      Object var7 = LList.makeList(var1, 0);
      var2.msg = ports.callWithOutputString(var2.lambda$Fn4);

      misc.frame0 var4;
      for(var0 = LList.Empty; var7 != LList.Empty; var0 = Pair.make(ports.callWithOutputString(var4.lambda$Fn5), var0)) {
         Pair var3;
         try {
            var3 = (Pair)var7;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "arg0", -2, var7);
         }

         var7 = var3.getCdr();
         Object var8 = var3.getCar();
         var4 = new misc.frame0();
         var4.arg = var8;
      }

      LList var6 = LList.reverseInPlace(var0);
      return Scheme.apply.apply4(throw_name.throwName, Lit3, var2.msg, var6);
   }

   public static Object force(Object var0) {
      return Promise.force(var0);
   }

   public static Symbol gentemp() {
      return Symbols.gentemp();
   }

   public static Environment interactionEnvironment() {
      return Environment.user();
   }

   public static boolean isBoolean(Object var0) {
      boolean var1;
      if(var0 == Boolean.TRUE) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1?var1:var0 == Boolean.FALSE;
   }

   public static boolean isEnvironmentBound(Environment var0, Object var1) {
      return var0.isBound(LispLanguage.langSymbolToSymbol(var1));
   }

   public static boolean isProcedure(Object var0) {
      boolean var1 = var0 instanceof Procedure;
      return var1?var1:var0 instanceof LangObjType;
   }

   public static boolean isSymbol(Object var0) {
      return var0 instanceof Symbol;
   }

   static boolean lambda1(Symbol var0, Symbol var1) {
      return Symbol.equals(var0, var1);
   }

   static boolean lambda2$V(Symbol var0, Symbol var1, Object[] var2) {
      boolean var3 = false;
      LList var5 = LList.makeList(var2, 0);
      boolean var4 = Symbol.equals(var0, var1);
      if(var4) {
         if(Scheme.apply.apply3(symbol$Eq$Qu, var1, var5) != Boolean.FALSE) {
            var3 = true;
         }

         return var3;
      } else {
         return var4;
      }
   }

   public static CharSequence namespacePrefix(Namespace var0) {
      return var0.getPrefix();
   }

   public static CharSequence namespaceUri(Namespace var0) {
      return var0.getName();
   }

   public static Environment nullEnvironment() {
      return nullEnvironment(Boolean.FALSE);
   }

   public static Environment nullEnvironment(Object var0) {
      return Scheme.nullEnvironment;
   }

   public static Object procedureProperty(Procedure var0, Object var1) {
      return procedureProperty(var0, var1, Boolean.FALSE);
   }

   public static Object procedureProperty(Procedure var0, Object var1, Object var2) {
      return var0.getProperty(var1, var2);
   }

   public static String schemeImplementationVersion() {
      return Version.getVersion();
   }

   public static Object schemeReportEnvironment(Object var0) {
      return Scheme.isEqv.apply2(var0, Lit0) != Boolean.FALSE?Scheme.r4Environment:(Scheme.isEqv.apply2(var0, Lit1) != Boolean.FALSE?Scheme.r5Environment:error$V("scheme-report-environment version must be 4 or 5", new Object[0]));
   }

   public static void setProcedureProperty$Ex(Procedure var0, Object var1, Object var2) {
      var0.setProperty(var1, var2);
   }

   public static SimpleSymbol string$To$Symbol(CharSequence var0) {
      return SimpleSymbol.valueOf(var0.toString());
   }

   public static String symbol$To$String(Symbol var0) {
      return var0.toString();
   }

   public static String symbolLocalName(Symbol var0) {
      return var0.getLocalPart();
   }

   public static Namespace symbolNamespace(Symbol var0) {
      return var0.getNamespace();
   }

   public static String symbolNamespaceUri(Symbol var0) {
      return var0.getNamespaceURI();
   }

   public static String symbolPrefix(Symbol var0) {
      return var0.getPrefix();
   }

   public static Object values(Object ... var0) {
      return Values.make((Object[])var0);
   }

   public Object apply0(ModuleMethod var1) {
      switch(var1.selector) {
      case 18:
         return nullEnvironment();
      case 21:
         return interactionEnvironment();
      case 22:
         return schemeImplementationVersion();
      case 29:
         return baseUri();
      case 31:
         return gentemp();
      default:
         return super.apply0(var1);
      }
   }

   public Object apply1(ModuleMethod var1, Object var2) {
      Namespace var12;
      Symbol var13;
      switch(var1.selector) {
      case 3:
         if(isBoolean(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 4:
         if(isSymbol(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 5:
         try {
            var13 = (Symbol)var2;
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "symbol->string", 1, var2);
         }

         return symbol$To$String(var13);
      case 6:
      case 7:
      case 16:
      case 17:
      case 19:
      case 21:
      case 22:
      case 23:
      case 24:
      case 25:
      case 26:
      case 28:
      default:
         return super.apply1(var1, var2);
      case 8:
         try {
            var13 = (Symbol)var2;
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "symbol-local-name", 1, var2);
         }

         return symbolLocalName(var13);
      case 9:
         try {
            var13 = (Symbol)var2;
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "symbol-namespace", 1, var2);
         }

         return symbolNamespace(var13);
      case 10:
         try {
            var13 = (Symbol)var2;
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "symbol-namespace-uri", 1, var2);
         }

         return symbolNamespaceUri(var13);
      case 11:
         try {
            var13 = (Symbol)var2;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "symbol-prefix", 1, var2);
         }

         return symbolPrefix(var13);
      case 12:
         try {
            var12 = (Namespace)var2;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "namespace-uri", 1, var2);
         }

         return namespaceUri(var12);
      case 13:
         try {
            var12 = (Namespace)var2;
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "namespace-prefix", 1, var2);
         }

         return namespacePrefix(var12);
      case 14:
         CharSequence var11;
         try {
            var11 = (CharSequence)var2;
         } catch (ClassCastException var3) {
            throw new WrongType(var3, "string->symbol", 1, var2);
         }

         return string$To$Symbol(var11);
      case 15:
         if(isProcedure(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 18:
         return nullEnvironment(var2);
      case 20:
         return schemeReportEnvironment(var2);
      case 27:
         return force(var2);
      case 29:
         return baseUri(var2);
      }
   }

   public Object apply2(ModuleMethod var1, Object var2, Object var3) {
      switch(var1.selector) {
      case 6:
         Symbol var10;
         try {
            var10 = (Symbol)var2;
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "lambda", 1, var2);
         }

         Symbol var11;
         try {
            var11 = (Symbol)var3;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "lambda", 2, var3);
         }

         if(lambda1(var10, var11)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 17:
         Environment var9;
         try {
            var9 = (Environment)var2;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "environment-bound?", 1, var2);
         }

         if(isEnvironmentBound(var9, var3)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 24:
         Procedure var8;
         try {
            var8 = (Procedure)var2;
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "procedure-property", 1, var2);
         }

         return procedureProperty(var8, var3);
      default:
         return super.apply2(var1, var2, var3);
      }
   }

   public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
      Procedure var7;
      switch(var1.selector) {
      case 23:
         try {
            var7 = (Procedure)var2;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "set-procedure-property!", 1, var2);
         }

         setProcedureProperty$Ex(var7, var3, var4);
         return Values.empty;
      case 24:
         try {
            var7 = (Procedure)var2;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "procedure-property", 1, var2);
         }

         return procedureProperty(var7, var3, var4);
      case 25:
      default:
         return super.apply3(var1, var2, var3, var4);
      case 26:
         return dynamicWind(var2, var3, var4);
      }
   }

   public Object applyN(ModuleMethod var1, Object[] var2) {
      int var5;
      Object var9;
      Object[] var12;
      switch(var1.selector) {
      case 7:
         Object var13 = var2[0];

         Symbol var11;
         try {
            var11 = (Symbol)var13;
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "lambda", 1, var13);
         }

         var13 = var2[1];

         Symbol var4;
         try {
            var4 = (Symbol)var13;
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "lambda", 2, var13);
         }

         var5 = var2.length - 2;
         var12 = new Object[var5];

         while(true) {
            --var5;
            if(var5 < 0) {
               return lambda2$V(var11, var4, var12)?Boolean.TRUE:Boolean.FALSE;
            }

            var12[var5] = var2[var5 + 2];
         }
      case 16:
         return values(var2);
      case 28:
         var9 = var2[0];
         var5 = var2.length - 1;
         var12 = new Object[var5];

         while(true) {
            --var5;
            if(var5 < 0) {
               return error$V(var9, var12);
            }

            var12[var5] = var2[var5 + 1];
         }
      case 32:
         var9 = var2[0];

         GenericProc var3;
         try {
            var3 = (GenericProc)var9;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "add-procedure-properties", 1, var9);
         }

         var5 = var2.length - 1;
         Object[] var10 = new Object[var5];

         while(true) {
            --var5;
            if(var5 < 0) {
               addProcedureProperties(var3, var10);
               return Values.empty;
            }

            var10[var5] = var2[var5 + 1];
         }
      default:
         return super.applyN(var1, var2);
      }
   }

   public int match0(ModuleMethod var1, CallContext var2) {
      switch(var1.selector) {
      case 18:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 21:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 22:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 29:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 31:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      default:
         return super.match0(var1, var2);
      }
   }

   public int match1(ModuleMethod var1, Object var2, CallContext var3) {
      switch(var1.selector) {
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
         if(!(var2 instanceof Symbol)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 6:
      case 7:
      case 16:
      case 17:
      case 19:
      case 21:
      case 22:
      case 23:
      case 24:
      case 25:
      case 26:
      case 28:
      default:
         return super.match1(var1, var2, var3);
      case 8:
         if(!(var2 instanceof Symbol)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 9:
         if(!(var2 instanceof Symbol)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 10:
         if(!(var2 instanceof Symbol)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 11:
         if(!(var2 instanceof Symbol)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 12:
         if(!(var2 instanceof Namespace)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 13:
         if(!(var2 instanceof Namespace)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 14:
         if(var2 instanceof CharSequence) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 15:
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
      }
   }

   public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
      int var5 = -786431;
      switch(var1.selector) {
      case 6:
         if(var2 instanceof Symbol) {
            var4.value1 = var2;
            if(!(var3 instanceof Symbol)) {
               return -786430;
            }

            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }
         break;
      case 17:
         if(var2 instanceof Environment) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }
         break;
      case 24:
         if(var2 instanceof Procedure) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }
         break;
      default:
         var5 = super.match2(var1, var2, var3, var4);
      }

      return var5;
   }

   public int match3(ModuleMethod var1, Object var2, Object var3, Object var4, CallContext var5) {
      switch(var1.selector) {
      case 23:
         if(!(var2 instanceof Procedure)) {
            return -786431;
         }

         var5.value1 = var2;
         var5.value2 = var3;
         var5.value3 = var4;
         var5.proc = var1;
         var5.pc = 3;
         return 0;
      case 24:
         if(!(var2 instanceof Procedure)) {
            return -786431;
         }

         var5.value1 = var2;
         var5.value2 = var3;
         var5.value3 = var4;
         var5.proc = var1;
         var5.pc = 3;
         return 0;
      case 25:
      default:
         return super.match3(var1, var2, var3, var4, var5);
      case 26:
         var5.value1 = var2;
         var5.value2 = var3;
         var5.value3 = var4;
         var5.proc = var1;
         var5.pc = 3;
         return 0;
      }
   }

   public int matchN(ModuleMethod var1, Object[] var2, CallContext var3) {
      switch(var1.selector) {
      case 7:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 16:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 28:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 32:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      default:
         return super.matchN(var1, var2, var3);
      }
   }

   public final void run(CallContext var1) {
      Consumer var5 = var1.consumer;
      symbol$Eq$Qu = new GenericProc("symbol=?");
      symbol$Eq$Qu.setProperties(new Object[]{lambda$Fn1, lambda$Fn2});
      procedure$Mnproperty = new GenericProc("procedure-property");
      GenericProc var6 = procedure$Mnproperty;
      Keyword var2 = Lit2;
      ModuleMethod var3 = set$Mnprocedure$Mnproperty$Ex;
      ModuleMethod var4 = procedure$Mnproperty$Fn3;
      var6.setProperties(new Object[]{var2, var3, procedure$Mnproperty$Fn3});
   }

   public class frame extends ModuleBody {

      final ModuleMethod lambda$Fn4;
      Object msg;


      public frame() {
         ModuleMethod var1 = new ModuleMethod(this, 2, (Object)null, 4097);
         var1.setProperty("source-location", "misc.scm:104");
         this.lambda$Fn4 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         if(var1.selector == 2) {
            this.lambda3(var2);
            return Values.empty;
         } else {
            return super.apply1(var1, var2);
         }
      }

      void lambda3(Object var1) {
         ports.display(this.msg, var1);
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         if(var1.selector == 2) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return super.match1(var1, var2, var3);
         }
      }
   }

   public class frame0 extends ModuleBody {

      Object arg;
      final ModuleMethod lambda$Fn5;


      public frame0() {
         ModuleMethod var1 = new ModuleMethod(this, 1, (Object)null, 4097);
         var1.setProperty("source-location", "misc.scm:107");
         this.lambda$Fn5 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         if(var1.selector == 1) {
            this.lambda4(var2);
            return Values.empty;
         } else {
            return super.apply1(var1, var2);
         }
      }

      void lambda4(Object var1) {
         ports.write(this.arg, var1);
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
