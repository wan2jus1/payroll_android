package kawa.standard;

import gnu.bytecode.ArrayType;
import gnu.bytecode.ClassType;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.Language;
import gnu.expr.ReferenceExp;
import gnu.kawa.functions.Apply;
import gnu.kawa.functions.ApplyToArgs;
import gnu.kawa.functions.IsEq;
import gnu.kawa.functions.IsEqual;
import gnu.kawa.functions.IsEqv;
import gnu.kawa.functions.Map;
import gnu.kawa.functions.Not;
import gnu.kawa.functions.NumberCompare;
import gnu.kawa.functions.NumberPredicate;
import gnu.kawa.lispexpr.LangObjType;
import gnu.kawa.lispexpr.LangPrimType;
import gnu.kawa.lispexpr.LispLanguage;
import gnu.kawa.lispexpr.LispReader;
import gnu.kawa.lispexpr.ReadTable;
import gnu.kawa.lispexpr.ReaderDispatch;
import gnu.kawa.lispexpr.ReaderDispatchMisc;
import gnu.kawa.lispexpr.ReaderParens;
import gnu.kawa.lispexpr.ReaderQuote;
import gnu.kawa.reflect.InstanceOf;
import gnu.lists.AbstractFormat;
import gnu.mapping.CharArrayInPort;
import gnu.mapping.Environment;
import gnu.mapping.InPort;
import gnu.mapping.Namespace;
import gnu.mapping.SimpleEnvironment;
import gnu.mapping.Symbol;
import gnu.mapping.WrappedException;
import gnu.text.SourceMessages;
import gnu.text.SyntaxException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import kawa.lang.Eval;
import kawa.lang.Translator;

public class Scheme extends LispLanguage {

   public static final Apply apply;
   static final Declaration applyFieldDecl;
   public static final ApplyToArgs applyToArgs;
   public static LangPrimType booleanType;
   public static final AbstractFormat displayFormat;
   public static final Map forEach;
   public static final Scheme instance;
   public static final InstanceOf instanceOf;
   public static final IsEq isEq;
   public static final IsEqual isEqual;
   public static final IsEqv isEqv;
   public static final NumberPredicate isEven;
   public static final NumberPredicate isOdd;
   protected static final SimpleEnvironment kawaEnvironment;
   public static final Map map;
   public static final Not not;
   public static final Environment nullEnvironment;
   public static final NumberCompare numEqu;
   public static final NumberCompare numGEq;
   public static final NumberCompare numGrt;
   public static final NumberCompare numLEq;
   public static final NumberCompare numLss;
   public static final Environment r4Environment;
   public static final Environment r5Environment;
   static HashMap typeToStringMap;
   static HashMap types;
   public static final Namespace unitNamespace;
   public static final AbstractFormat writeFormat;


   static {
      // $FF: Couldn't be decompiled
   }

   public Scheme() {
      this.environ = kawaEnvironment;
      this.userEnv = this.getNewEnvironment();
   }

   protected Scheme(Environment var1) {
      this.environ = var1;
   }

   public static Environment builtin() {
      return kawaEnvironment;
   }

   public static Object eval(InPort var0, Environment var1) {
      SourceMessages var2 = new SourceMessages();

      try {
         Object var8 = ReaderParens.readList((LispReader)Language.getDefaultLanguage().getLexer(var0, var2), 0, 1, -1);
         if(var2.seenErrors()) {
            throw new SyntaxException(var2);
         } else {
            var8 = Eval.evalBody(var8, var1, var2);
            return var8;
         }
      } catch (SyntaxException var3) {
         throw new RuntimeException("eval: errors while compiling:\n" + var3.getMessages().toString(20));
      } catch (IOException var4) {
         throw new RuntimeException("eval: I/O exception: " + var4.toString());
      } catch (RuntimeException var5) {
         throw var5;
      } catch (Error var6) {
         throw var6;
      } catch (Throwable var7) {
         throw new WrappedException(var7);
      }
   }

   public static Object eval(Object var0, Environment var1) {
      try {
         var0 = Eval.eval(var0, var1);
         return var0;
      } catch (RuntimeException var2) {
         throw var2;
      } catch (Error var3) {
         throw var3;
      } catch (Throwable var4) {
         throw new WrappedException(var4);
      }
   }

   public static Object eval(String var0, Environment var1) {
      return eval((InPort)(new CharArrayInPort(var0)), var1);
   }

   public static Type exp2Type(Expression var0) {
      return getInstance().getTypeFor((Expression)var0);
   }

   public static Scheme getInstance() {
      return instance;
   }

   public static Type getNamedType(String var0) {
      getTypeMap();
      Type var2 = (Type)types.get(var0);
      Type var1 = var2;
      if(var2 == null) {
         if(!var0.startsWith("elisp:")) {
            var1 = var2;
            if(!var0.startsWith("clisp:")) {
               return var1;
            }
         }

         int var4 = var0.indexOf(58);
         Class var5 = getNamedType(var0.substring(var4 + 1)).getReflectClass();
         String var6 = var0.substring(0, var4);
         Language var3 = Language.getInstance(var6);
         if(var3 == null) {
            throw new RuntimeException("unknown type \'" + var0 + "\' - unknown language \'" + var6 + '\'');
         }

         var2 = var3.getTypeFor((Class)var5);
         var1 = var2;
         if(var2 != null) {
            types.put(var0, var2);
            var1 = var2;
         }
      }

      return var1;
   }

   static HashMap getTypeMap() {
      synchronized(Scheme.class){}

      HashMap var0;
      try {
         if(types == null) {
            booleanType = new LangPrimType(Type.booleanType, getInstance());
            types = new HashMap();
            types.put("void", LangPrimType.voidType);
            types.put("int", LangPrimType.intType);
            types.put("char", LangPrimType.charType);
            types.put("boolean", booleanType);
            types.put("byte", LangPrimType.byteType);
            types.put("short", LangPrimType.shortType);
            types.put("long", LangPrimType.longType);
            types.put("float", LangPrimType.floatType);
            types.put("double", LangPrimType.doubleType);
            types.put("never-returns", Type.neverReturnsType);
            types.put("Object", Type.objectType);
            types.put("String", Type.toStringType);
            types.put("object", Type.objectType);
            types.put("number", LangObjType.numericType);
            types.put("quantity", ClassType.make("gnu.math.Quantity"));
            types.put("complex", ClassType.make("gnu.math.Complex"));
            types.put("real", LangObjType.realType);
            types.put("rational", LangObjType.rationalType);
            types.put("integer", LangObjType.integerType);
            types.put("symbol", ClassType.make("gnu.mapping.Symbol"));
            types.put("namespace", ClassType.make("gnu.mapping.Namespace"));
            types.put("keyword", ClassType.make("gnu.expr.Keyword"));
            types.put("pair", ClassType.make("gnu.lists.Pair"));
            types.put("pair-with-position", ClassType.make("gnu.lists.PairWithPosition"));
            types.put("constant-string", ClassType.make("java.lang.String"));
            types.put("abstract-string", ClassType.make("gnu.lists.CharSeq"));
            types.put("character", ClassType.make("gnu.text.Char"));
            types.put("vector", LangObjType.vectorType);
            types.put("string", LangObjType.stringType);
            types.put("list", LangObjType.listType);
            types.put("function", ClassType.make("gnu.mapping.Procedure"));
            types.put("procedure", ClassType.make("gnu.mapping.Procedure"));
            types.put("input-port", ClassType.make("gnu.mapping.InPort"));
            types.put("output-port", ClassType.make("gnu.mapping.OutPort"));
            types.put("string-output-port", ClassType.make("gnu.mapping.CharArrayOutPort"));
            types.put("record", ClassType.make("kawa.lang.Record"));
            types.put("type", LangObjType.typeType);
            types.put("class-type", LangObjType.typeClassType);
            types.put("class", LangObjType.typeClass);
            types.put("s8vector", ClassType.make("gnu.lists.S8Vector"));
            types.put("u8vector", ClassType.make("gnu.lists.U8Vector"));
            types.put("s16vector", ClassType.make("gnu.lists.S16Vector"));
            types.put("u16vector", ClassType.make("gnu.lists.U16Vector"));
            types.put("s32vector", ClassType.make("gnu.lists.S32Vector"));
            types.put("u32vector", ClassType.make("gnu.lists.U32Vector"));
            types.put("s64vector", ClassType.make("gnu.lists.S64Vector"));
            types.put("u64vector", ClassType.make("gnu.lists.U64Vector"));
            types.put("f32vector", ClassType.make("gnu.lists.F32Vector"));
            types.put("f64vector", ClassType.make("gnu.lists.F64Vector"));
            types.put("document", ClassType.make("gnu.kawa.xml.KDocument"));
            types.put("readtable", ClassType.make("gnu.kawa.lispexpr.ReadTable"));
         }

         var0 = types;
      } finally {
         ;
      }

      return var0;
   }

   public static Type getTypeValue(Expression var0) {
      return getInstance().getTypeFor((Expression)var0);
   }

   private void initScheme() {
      this.environ = nullEnvironment;
      this.environ.addLocation(LispLanguage.lookup_sym, (Object)null, getNamedPartLocation);
      this.defSntxStFld("lambda", "kawa.standard.SchemeCompilation", "lambda");
      this.defSntxStFld("quote", "kawa.lang.Quote", "plainQuote");
      this.defSntxStFld("%define", "kawa.standard.define", "defineRaw");
      this.defSntxStFld("define", "kawa.lib.prim_syntax");
      this.defSntxStFld("if", "kawa.lib.prim_syntax");
      this.defSntxStFld("set!", "kawa.standard.set_b", "set");
      this.defSntxStFld("cond", "kawa.lib.std_syntax");
      this.defSntxStFld("case", "kawa.lib.std_syntax");
      this.defSntxStFld("and", "kawa.lib.std_syntax");
      this.defSntxStFld("or", "kawa.lib.std_syntax");
      this.defSntxStFld("%let", "kawa.standard.let", "let");
      this.defSntxStFld("let", "kawa.lib.std_syntax");
      this.defSntxStFld("let*", "kawa.lib.std_syntax");
      this.defSntxStFld("letrec", "kawa.lib.prim_syntax");
      this.defSntxStFld("begin", "kawa.standard.begin", "begin");
      this.defSntxStFld("do", "kawa.lib.std_syntax");
      this.defSntxStFld("delay", "kawa.lib.std_syntax");
      this.defProcStFld("%make-promise", "kawa.lib.std_syntax");
      this.defSntxStFld("quasiquote", "kawa.lang.Quote", "quasiQuote");
      this.defSntxStFld("define-syntax", "kawa.lib.prim_syntax");
      this.defSntxStFld("let-syntax", "kawa.standard.let_syntax", "let_syntax");
      this.defSntxStFld("letrec-syntax", "kawa.standard.let_syntax", "letrec_syntax");
      this.defSntxStFld("syntax-rules", "kawa.standard.syntax_rules", "syntax_rules");
      nullEnvironment.setLocked();
      this.environ = r4Environment;
      this.defProcStFld("not", "kawa.standard.Scheme");
      this.defProcStFld("boolean?", "kawa.lib.misc");
      this.defProcStFld("eq?", "kawa.standard.Scheme", "isEq");
      this.defProcStFld("eqv?", "kawa.standard.Scheme", "isEqv");
      this.defProcStFld("equal?", "kawa.standard.Scheme", "isEqual");
      this.defProcStFld("pair?", "kawa.lib.lists");
      this.defProcStFld("cons", "kawa.lib.lists");
      this.defProcStFld("car", "kawa.lib.lists");
      this.defProcStFld("cdr", "kawa.lib.lists");
      this.defProcStFld("set-car!", "kawa.lib.lists");
      this.defProcStFld("set-cdr!", "kawa.lib.lists");
      this.defProcStFld("caar", "kawa.lib.lists");
      this.defProcStFld("cadr", "kawa.lib.lists");
      this.defProcStFld("cdar", "kawa.lib.lists");
      this.defProcStFld("cddr", "kawa.lib.lists");
      this.defProcStFld("caaar", "kawa.lib.lists");
      this.defProcStFld("caadr", "kawa.lib.lists");
      this.defProcStFld("cadar", "kawa.lib.lists");
      this.defProcStFld("caddr", "kawa.lib.lists");
      this.defProcStFld("cdaar", "kawa.lib.lists");
      this.defProcStFld("cdadr", "kawa.lib.lists");
      this.defProcStFld("cddar", "kawa.lib.lists");
      this.defProcStFld("cdddr", "kawa.lib.lists");
      this.defProcStFld("caaaar", "kawa.lib.lists");
      this.defProcStFld("caaadr", "kawa.lib.lists");
      this.defProcStFld("caadar", "kawa.lib.lists");
      this.defProcStFld("caaddr", "kawa.lib.lists");
      this.defProcStFld("cadaar", "kawa.lib.lists");
      this.defProcStFld("cadadr", "kawa.lib.lists");
      this.defProcStFld("caddar", "kawa.lib.lists");
      this.defProcStFld("cadddr", "kawa.lib.lists");
      this.defProcStFld("cdaaar", "kawa.lib.lists");
      this.defProcStFld("cdaadr", "kawa.lib.lists");
      this.defProcStFld("cdadar", "kawa.lib.lists");
      this.defProcStFld("cdaddr", "kawa.lib.lists");
      this.defProcStFld("cddaar", "kawa.lib.lists");
      this.defProcStFld("cddadr", "kawa.lib.lists");
      this.defProcStFld("cdddar", "kawa.lib.lists");
      this.defProcStFld("cddddr", "kawa.lib.lists");
      this.defProcStFld("null?", "kawa.lib.lists");
      this.defProcStFld("list?", "kawa.lib.lists");
      this.defProcStFld("length", "kawa.lib.lists");
      this.defProcStFld("append", "kawa.standard.append", "append");
      this.defProcStFld("reverse", "kawa.lib.lists");
      this.defProcStFld("reverse!", "kawa.lib.lists");
      this.defProcStFld("list-tail", "kawa.lib.lists");
      this.defProcStFld("list-ref", "kawa.lib.lists");
      this.defProcStFld("memq", "kawa.lib.lists");
      this.defProcStFld("memv", "kawa.lib.lists");
      this.defProcStFld("member", "kawa.lib.lists");
      this.defProcStFld("assq", "kawa.lib.lists");
      this.defProcStFld("assv", "kawa.lib.lists");
      this.defProcStFld("assoc", "kawa.lib.lists");
      this.defProcStFld("symbol?", "kawa.lib.misc");
      this.defProcStFld("symbol->string", "kawa.lib.misc");
      this.defProcStFld("string->symbol", "kawa.lib.misc");
      this.defProcStFld("symbol=?", "kawa.lib.misc");
      this.defProcStFld("symbol-local-name", "kawa.lib.misc");
      this.defProcStFld("symbol-namespace", "kawa.lib.misc");
      this.defProcStFld("symbol-namespace-uri", "kawa.lib.misc");
      this.defProcStFld("symbol-prefix", "kawa.lib.misc");
      this.defProcStFld("namespace-uri", "kawa.lib.misc");
      this.defProcStFld("namespace-prefix", "kawa.lib.misc");
      this.defProcStFld("number?", "kawa.lib.numbers");
      this.defProcStFld("quantity?", "kawa.lib.numbers");
      this.defProcStFld("complex?", "kawa.lib.numbers");
      this.defProcStFld("real?", "kawa.lib.numbers");
      this.defProcStFld("rational?", "kawa.lib.numbers");
      this.defProcStFld("integer?", "kawa.lib.numbers");
      this.defProcStFld("exact?", "kawa.lib.numbers");
      this.defProcStFld("inexact?", "kawa.lib.numbers");
      this.defProcStFld("=", "kawa.standard.Scheme", "numEqu");
      this.defProcStFld("<", "kawa.standard.Scheme", "numLss");
      this.defProcStFld(">", "kawa.standard.Scheme", "numGrt");
      this.defProcStFld("<=", "kawa.standard.Scheme", "numLEq");
      this.defProcStFld(">=", "kawa.standard.Scheme", "numGEq");
      this.defProcStFld("zero?", "kawa.lib.numbers");
      this.defProcStFld("positive?", "kawa.lib.numbers");
      this.defProcStFld("negative?", "kawa.lib.numbers");
      this.defProcStFld("odd?", "kawa.standard.Scheme", "isOdd");
      this.defProcStFld("even?", "kawa.standard.Scheme", "isEven");
      this.defProcStFld("max", "kawa.lib.numbers");
      this.defProcStFld("min", "kawa.lib.numbers");
      this.defProcStFld("+", "gnu.kawa.functions.AddOp", "$Pl");
      this.defProcStFld("-", "gnu.kawa.functions.AddOp", "$Mn");
      this.defProcStFld("*", "gnu.kawa.functions.MultiplyOp", "$St");
      this.defProcStFld("/", "gnu.kawa.functions.DivideOp", "$Sl");
      this.defProcStFld("abs", "kawa.lib.numbers");
      this.defProcStFld("quotient", "gnu.kawa.functions.DivideOp", "quotient");
      this.defProcStFld("remainder", "gnu.kawa.functions.DivideOp", "remainder");
      this.defProcStFld("modulo", "gnu.kawa.functions.DivideOp", "modulo");
      this.defProcStFld("div", "gnu.kawa.functions.DivideOp", "div");
      this.defProcStFld("mod", "gnu.kawa.functions.DivideOp", "mod");
      this.defProcStFld("div0", "gnu.kawa.functions.DivideOp", "div0");
      this.defProcStFld("mod0", "gnu.kawa.functions.DivideOp", "mod0");
      this.defProcStFld("div-and-mod", "kawa.lib.numbers");
      this.defProcStFld("div0-and-mod0", "kawa.lib.numbers");
      this.defProcStFld("gcd", "kawa.lib.numbers");
      this.defProcStFld("lcm", "kawa.lib.numbers");
      this.defProcStFld("numerator", "kawa.lib.numbers");
      this.defProcStFld("denominator", "kawa.lib.numbers");
      this.defProcStFld("floor", "kawa.lib.numbers");
      this.defProcStFld("ceiling", "kawa.lib.numbers");
      this.defProcStFld("truncate", "kawa.lib.numbers");
      this.defProcStFld("round", "kawa.lib.numbers");
      this.defProcStFld("rationalize", "kawa.lib.numbers");
      this.defProcStFld("exp", "kawa.lib.numbers");
      this.defProcStFld("log", "kawa.lib.numbers");
      this.defProcStFld("sin", "kawa.lib.numbers");
      this.defProcStFld("cos", "kawa.lib.numbers");
      this.defProcStFld("tan", "kawa.lib.numbers");
      this.defProcStFld("asin", "kawa.lib.numbers");
      this.defProcStFld("acos", "kawa.lib.numbers");
      this.defProcStFld("atan", "kawa.lib.numbers");
      this.defProcStFld("sqrt", "kawa.lib.numbers");
      this.defProcStFld("expt", "kawa.standard.expt");
      this.defProcStFld("make-rectangular", "kawa.lib.numbers");
      this.defProcStFld("make-polar", "kawa.lib.numbers");
      this.defProcStFld("real-part", "kawa.lib.numbers");
      this.defProcStFld("imag-part", "kawa.lib.numbers");
      this.defProcStFld("magnitude", "kawa.lib.numbers");
      this.defProcStFld("angle", "kawa.lib.numbers");
      this.defProcStFld("inexact", "kawa.lib.numbers");
      this.defProcStFld("exact", "kawa.lib.numbers");
      this.defProcStFld("exact->inexact", "kawa.lib.numbers");
      this.defProcStFld("inexact->exact", "kawa.lib.numbers");
      this.defProcStFld("number->string", "kawa.lib.numbers");
      this.defProcStFld("string->number", "kawa.lib.numbers");
      this.defProcStFld("char?", "kawa.lib.characters");
      this.defProcStFld("char=?", "kawa.lib.characters");
      this.defProcStFld("char<?", "kawa.lib.characters");
      this.defProcStFld("char>?", "kawa.lib.characters");
      this.defProcStFld("char<=?", "kawa.lib.characters");
      this.defProcStFld("char>=?", "kawa.lib.characters");
      this.defProcStFld("char-ci=?", "kawa.lib.rnrs.unicode");
      this.defProcStFld("char-ci<?", "kawa.lib.rnrs.unicode");
      this.defProcStFld("char-ci>?", "kawa.lib.rnrs.unicode");
      this.defProcStFld("char-ci<=?", "kawa.lib.rnrs.unicode");
      this.defProcStFld("char-ci>=?", "kawa.lib.rnrs.unicode");
      this.defProcStFld("char-alphabetic?", "kawa.lib.rnrs.unicode");
      this.defProcStFld("char-numeric?", "kawa.lib.rnrs.unicode");
      this.defProcStFld("char-whitespace?", "kawa.lib.rnrs.unicode");
      this.defProcStFld("char-upper-case?", "kawa.lib.rnrs.unicode");
      this.defProcStFld("char-lower-case?", "kawa.lib.rnrs.unicode");
      this.defProcStFld("char-title-case?", "kawa.lib.rnrs.unicode");
      this.defProcStFld("char->integer", "kawa.lib.characters");
      this.defProcStFld("integer->char", "kawa.lib.characters");
      this.defProcStFld("char-upcase", "kawa.lib.rnrs.unicode");
      this.defProcStFld("char-downcase", "kawa.lib.rnrs.unicode");
      this.defProcStFld("char-titlecase", "kawa.lib.rnrs.unicode");
      this.defProcStFld("char-foldcase", "kawa.lib.rnrs.unicode");
      this.defProcStFld("char-general-category", "kawa.lib.rnrs.unicode");
      this.defProcStFld("string?", "kawa.lib.strings");
      this.defProcStFld("make-string", "kawa.lib.strings");
      this.defProcStFld("string-length", "kawa.lib.strings");
      this.defProcStFld("string-ref", "kawa.lib.strings");
      this.defProcStFld("string-set!", "kawa.lib.strings");
      this.defProcStFld("string=?", "kawa.lib.strings");
      this.defProcStFld("string<?", "kawa.lib.strings");
      this.defProcStFld("string>?", "kawa.lib.strings");
      this.defProcStFld("string<=?", "kawa.lib.strings");
      this.defProcStFld("string>=?", "kawa.lib.strings");
      this.defProcStFld("string-ci=?", "kawa.lib.rnrs.unicode");
      this.defProcStFld("string-ci<?", "kawa.lib.rnrs.unicode");
      this.defProcStFld("string-ci>?", "kawa.lib.rnrs.unicode");
      this.defProcStFld("string-ci<=?", "kawa.lib.rnrs.unicode");
      this.defProcStFld("string-ci>=?", "kawa.lib.rnrs.unicode");
      this.defProcStFld("string-normalize-nfd", "kawa.lib.rnrs.unicode");
      this.defProcStFld("string-normalize-nfkd", "kawa.lib.rnrs.unicode");
      this.defProcStFld("string-normalize-nfc", "kawa.lib.rnrs.unicode");
      this.defProcStFld("string-normalize-nfkc", "kawa.lib.rnrs.unicode");
      this.defProcStFld("substring", "kawa.lib.strings");
      this.defProcStFld("string-append", "kawa.lib.strings");
      this.defProcStFld("string-append/shared", "kawa.lib.strings");
      this.defProcStFld("string->list", "kawa.lib.strings");
      this.defProcStFld("list->string", "kawa.lib.strings");
      this.defProcStFld("string-copy", "kawa.lib.strings");
      this.defProcStFld("string-fill!", "kawa.lib.strings");
      this.defProcStFld("vector?", "kawa.lib.vectors");
      this.defProcStFld("make-vector", "kawa.lib.vectors");
      this.defProcStFld("vector-length", "kawa.lib.vectors");
      this.defProcStFld("vector-ref", "kawa.lib.vectors");
      this.defProcStFld("vector-set!", "kawa.lib.vectors");
      this.defProcStFld("list->vector", "kawa.lib.vectors");
      this.defProcStFld("vector->list", "kawa.lib.vectors");
      this.defProcStFld("vector-fill!", "kawa.lib.vectors");
      this.defProcStFld("vector-append", "kawa.standard.vector_append", "vectorAppend");
      this.defProcStFld("values-append", "gnu.kawa.functions.AppendValues", "appendValues");
      this.defProcStFld("procedure?", "kawa.lib.misc");
      this.defProcStFld("apply", "kawa.standard.Scheme", "apply");
      this.defProcStFld("map", "kawa.standard.Scheme", "map");
      this.defProcStFld("for-each", "kawa.standard.Scheme", "forEach");
      this.defProcStFld("call-with-current-continuation", "gnu.kawa.functions.CallCC", "callcc");
      this.defProcStFld("call/cc", "kawa.standard.callcc", "callcc");
      this.defProcStFld("force", "kawa.lib.misc");
      this.defProcStFld("call-with-input-file", "kawa.lib.ports");
      this.defProcStFld("call-with-output-file", "kawa.lib.ports");
      this.defProcStFld("input-port?", "kawa.lib.ports");
      this.defProcStFld("output-port?", "kawa.lib.ports");
      this.defProcStFld("current-input-port", "kawa.lib.ports");
      this.defProcStFld("current-output-port", "kawa.lib.ports");
      this.defProcStFld("with-input-from-file", "kawa.lib.ports");
      this.defProcStFld("with-output-to-file", "kawa.lib.ports");
      this.defProcStFld("open-input-file", "kawa.lib.ports");
      this.defProcStFld("open-output-file", "kawa.lib.ports");
      this.defProcStFld("close-input-port", "kawa.lib.ports");
      this.defProcStFld("close-output-port", "kawa.lib.ports");
      this.defProcStFld("read", "kawa.lib.ports");
      this.defProcStFld("read-line", "kawa.lib.ports");
      this.defProcStFld("read-char", "kawa.standard.readchar", "readChar");
      this.defProcStFld("peek-char", "kawa.standard.readchar", "peekChar");
      this.defProcStFld("eof-object?", "kawa.lib.ports");
      this.defProcStFld("char-ready?", "kawa.lib.ports");
      this.defProcStFld("write", "kawa.lib.ports");
      this.defProcStFld("display", "kawa.lib.ports");
      this.defProcStFld("print-as-xml", "gnu.xquery.lang.XQuery", "writeFormat");
      this.defProcStFld("write-char", "kawa.lib.ports");
      this.defProcStFld("newline", "kawa.lib.ports");
      this.defProcStFld("load", "kawa.standard.load", "load");
      this.defProcStFld("load-relative", "kawa.standard.load", "loadRelative");
      this.defProcStFld("transcript-off", "kawa.lib.ports");
      this.defProcStFld("transcript-on", "kawa.lib.ports");
      this.defProcStFld("call-with-input-string", "kawa.lib.ports");
      this.defProcStFld("open-input-string", "kawa.lib.ports");
      this.defProcStFld("open-output-string", "kawa.lib.ports");
      this.defProcStFld("get-output-string", "kawa.lib.ports");
      this.defProcStFld("call-with-output-string", "kawa.lib.ports");
      this.defProcStFld("force-output", "kawa.lib.ports");
      this.defProcStFld("port-line", "kawa.lib.ports");
      this.defProcStFld("set-port-line!", "kawa.lib.ports");
      this.defProcStFld("port-column", "kawa.lib.ports");
      this.defProcStFld("current-error-port", "kawa.lib.ports");
      this.defProcStFld("input-port-line-number", "kawa.lib.ports");
      this.defProcStFld("set-input-port-line-number!", "kawa.lib.ports");
      this.defProcStFld("input-port-column-number", "kawa.lib.ports");
      this.defProcStFld("input-port-read-state", "kawa.lib.ports");
      this.defProcStFld("default-prompter", "kawa.lib.ports");
      this.defProcStFld("input-port-prompter", "kawa.lib.ports");
      this.defProcStFld("set-input-port-prompter!", "kawa.lib.ports");
      this.defProcStFld("base-uri", "kawa.lib.misc");
      this.defProcStFld("%syntax-error", "kawa.standard.syntax_error", "syntax_error");
      this.defProcStFld("syntax-error", "kawa.lib.prim_syntax");
      r4Environment.setLocked();
      this.environ = r5Environment;
      this.defProcStFld("values", "kawa.lib.misc");
      this.defProcStFld("call-with-values", "kawa.standard.call_with_values", "callWithValues");
      this.defSntxStFld("let-values", "kawa.lib.syntax");
      this.defSntxStFld("let*-values", "kawa.lib.syntax");
      this.defSntxStFld("case-lambda", "kawa.lib.syntax");
      this.defSntxStFld("receive", "kawa.lib.syntax");
      this.defProcStFld("eval", "kawa.lang.Eval");
      this.defProcStFld("repl", "kawa.standard.SchemeCompilation", "repl");
      this.defProcStFld("scheme-report-environment", "kawa.lib.misc");
      this.defProcStFld("null-environment", "kawa.lib.misc");
      this.defProcStFld("interaction-environment", "kawa.lib.misc");
      this.defProcStFld("dynamic-wind", "kawa.lib.misc");
      r5Environment.setLocked();
      this.environ = kawaEnvironment;
      this.defSntxStFld("define-private", "kawa.lib.prim_syntax");
      this.defSntxStFld("define-constant", "kawa.lib.prim_syntax");
      this.defSntxStFld("define-autoload", "kawa.standard.define_autoload", "define_autoload");
      this.defSntxStFld("define-autoloads-from-file", "kawa.standard.define_autoload", "define_autoloads_from_file");
      this.defProcStFld("exit", "kawa.lib.rnrs.programs");
      this.defProcStFld("command-line", "kawa.lib.rnrs.programs");
      this.defProcStFld("bitwise-arithmetic-shift", "gnu.kawa.functions.BitwiseOp", "ashift");
      this.defProcStFld("arithmetic-shift", "gnu.kawa.functions.BitwiseOp", "ashift");
      this.defProcStFld("ash", "gnu.kawa.functions.BitwiseOp", "ashift");
      this.defProcStFld("bitwise-arithmetic-shift-left", "gnu.kawa.functions.BitwiseOp", "ashiftl");
      this.defProcStFld("bitwise-arithmetic-shift-right", "gnu.kawa.functions.BitwiseOp", "ashiftr");
      this.defProcStFld("bitwise-and", "gnu.kawa.functions.BitwiseOp", "and");
      this.defProcStFld("logand", "gnu.kawa.functions.BitwiseOp", "and");
      this.defProcStFld("bitwise-ior", "gnu.kawa.functions.BitwiseOp", "ior");
      this.defProcStFld("logior", "gnu.kawa.functions.BitwiseOp", "ior");
      this.defProcStFld("bitwise-xor", "gnu.kawa.functions.BitwiseOp", "xor");
      this.defProcStFld("logxor", "gnu.kawa.functions.BitwiseOp", "xor");
      this.defProcStFld("bitwise-if", "kawa.lib.numbers");
      this.defProcStFld("bitwise-not", "gnu.kawa.functions.BitwiseOp", "not");
      this.defProcStFld("lognot", "gnu.kawa.functions.BitwiseOp", "not");
      this.defProcStFld("logop", "kawa.lib.numbers");
      this.defProcStFld("bitwise-bit-set?", "kawa.lib.numbers");
      this.defProcStFld("logbit?", "kawa.lib.numbers", Compilation.mangleNameIfNeeded("bitwise-bit-set?"));
      this.defProcStFld("logtest", "kawa.lib.numbers");
      this.defProcStFld("bitwise-bit-count", "kawa.lib.numbers");
      this.defProcStFld("logcount", "kawa.lib.numbers");
      this.defProcStFld("bitwise-copy-bit", "kawa.lib.numbers");
      this.defProcStFld("bitwise-copy-bit-field", "kawa.lib.numbers");
      this.defProcStFld("bitwise-bit-field", "kawa.lib.numbers");
      this.defProcStFld("bit-extract", "kawa.lib.numbers", Compilation.mangleNameIfNeeded("bitwise-bit-field"));
      this.defProcStFld("bitwise-length", "kawa.lib.numbers");
      this.defProcStFld("integer-length", "kawa.lib.numbers", "bitwise$Mnlength");
      this.defProcStFld("bitwise-first-bit-set", "kawa.lib.numbers");
      this.defProcStFld("bitwise-rotate-bit-field", "kawa.lib.numbers");
      this.defProcStFld("bitwise-reverse-bit-field", "kawa.lib.numbers");
      this.defProcStFld("string-upcase!", "kawa.lib.strings");
      this.defProcStFld("string-downcase!", "kawa.lib.strings");
      this.defProcStFld("string-capitalize!", "kawa.lib.strings");
      this.defProcStFld("string-upcase", "kawa.lib.rnrs.unicode");
      this.defProcStFld("string-downcase", "kawa.lib.rnrs.unicode");
      this.defProcStFld("string-titlecase", "kawa.lib.rnrs.unicode");
      this.defProcStFld("string-foldcase", "kawa.lib.rnrs.unicode");
      this.defProcStFld("string-capitalize", "kawa.lib.strings");
      this.defSntxStFld("primitive-virtual-method", "kawa.standard.prim_method", "virtual_method");
      this.defSntxStFld("primitive-static-method", "kawa.standard.prim_method", "static_method");
      this.defSntxStFld("primitive-interface-method", "kawa.standard.prim_method", "interface_method");
      this.defSntxStFld("primitive-constructor", "kawa.lib.reflection");
      this.defSntxStFld("primitive-op1", "kawa.standard.prim_method", "op1");
      this.defSntxStFld("primitive-get-field", "kawa.lib.reflection");
      this.defSntxStFld("primitive-set-field", "kawa.lib.reflection");
      this.defSntxStFld("primitive-get-static", "kawa.lib.reflection");
      this.defSntxStFld("primitive-set-static", "kawa.lib.reflection");
      this.defSntxStFld("primitive-array-new", "kawa.lib.reflection");
      this.defSntxStFld("primitive-array-get", "kawa.lib.reflection");
      this.defSntxStFld("primitive-array-set", "kawa.lib.reflection");
      this.defSntxStFld("primitive-array-length", "kawa.lib.reflection");
      this.defProcStFld("subtype?", "kawa.lib.reflection");
      this.defProcStFld("primitive-throw", "kawa.standard.prim_throw", "primitiveThrow");
      this.defSntxStFld("try-finally", "kawa.lib.syntax");
      this.defSntxStFld("try-catch", "kawa.lib.prim_syntax");
      this.defProcStFld("throw", "kawa.standard.throw_name", "throwName");
      this.defProcStFld("catch", "kawa.lib.system");
      this.defProcStFld("error", "kawa.lib.misc");
      this.defProcStFld("as", "gnu.kawa.functions.Convert", "as");
      this.defProcStFld("instance?", "kawa.standard.Scheme", "instanceOf");
      this.defSntxStFld("synchronized", "kawa.lib.syntax");
      this.defSntxStFld("object", "kawa.standard.object", "objectSyntax");
      this.defSntxStFld("define-class", "kawa.standard.define_class", "define_class");
      this.defSntxStFld("define-simple-class", "kawa.standard.define_class", "define_simple_class");
      this.defSntxStFld("this", "kawa.standard.thisRef", "thisSyntax");
      this.defProcStFld("make", "gnu.kawa.reflect.Invoke", "make");
      this.defProcStFld("slot-ref", "gnu.kawa.reflect.SlotGet", "slotRef");
      this.defProcStFld("slot-set!", "gnu.kawa.reflect.SlotSet", "set$Mnfield$Ex");
      this.defProcStFld("field", "gnu.kawa.reflect.SlotGet");
      this.defProcStFld("class-methods", "gnu.kawa.reflect.ClassMethods", "classMethods");
      this.defProcStFld("static-field", "gnu.kawa.reflect.SlotGet", "staticField");
      this.defProcStFld("invoke", "gnu.kawa.reflect.Invoke", "invoke");
      this.defProcStFld("invoke-static", "gnu.kawa.reflect.Invoke", "invokeStatic");
      this.defProcStFld("invoke-special", "gnu.kawa.reflect.Invoke", "invokeSpecial");
      this.defSntxStFld("define-macro", "kawa.lib.syntax");
      this.defSntxStFld("%define-macro", "kawa.standard.define_syntax", "define_macro");
      this.defSntxStFld("define-syntax-case", "kawa.lib.syntax");
      this.defSntxStFld("syntax-case", "kawa.standard.syntax_case", "syntax_case");
      this.defSntxStFld("%define-syntax", "kawa.standard.define_syntax", "define_syntax");
      this.defSntxStFld("syntax", "kawa.standard.syntax", "syntax");
      this.defSntxStFld("quasisyntax", "kawa.standard.syntax", "quasiSyntax");
      this.defProcStFld("syntax-object->datum", "kawa.lib.std_syntax");
      this.defProcStFld("datum->syntax-object", "kawa.lib.std_syntax");
      this.defProcStFld("syntax->expression", "kawa.lib.prim_syntax");
      this.defProcStFld("syntax-body->expression", "kawa.lib.prim_syntax");
      this.defProcStFld("generate-temporaries", "kawa.lib.std_syntax");
      this.defSntxStFld("with-syntax", "kawa.lib.std_syntax");
      this.defProcStFld("identifier?", "kawa.lib.std_syntax");
      this.defProcStFld("free-identifier=?", "kawa.lib.std_syntax");
      this.defProcStFld("syntax-source", "kawa.lib.std_syntax");
      this.defProcStFld("syntax-line", "kawa.lib.std_syntax");
      this.defProcStFld("syntax-column", "kawa.lib.std_syntax");
      this.defSntxStFld("begin-for-syntax", "kawa.lib.std_syntax");
      this.defSntxStFld("define-for-syntax", "kawa.lib.std_syntax");
      this.defSntxStFld("include", "kawa.lib.misc_syntax");
      this.defSntxStFld("include-relative", "kawa.lib.misc_syntax");
      this.defProcStFld("file-exists?", "kawa.lib.files");
      this.defProcStFld("file-directory?", "kawa.lib.files");
      this.defProcStFld("file-readable?", "kawa.lib.files");
      this.defProcStFld("file-writable?", "kawa.lib.files");
      this.defProcStFld("delete-file", "kawa.lib.files");
      this.defProcStFld("system-tmpdir", "kawa.lib.files");
      this.defProcStFld("make-temporary-file", "kawa.lib.files");
      this.defProcStFld("rename-file", "kawa.lib.files");
      this.defProcStFld("copy-file", "kawa.lib.files");
      this.defProcStFld("create-directory", "kawa.lib.files");
      this.defProcStFld("->pathname", "kawa.lib.files");
      this.define("port-char-encoding", Boolean.TRUE);
      this.define("symbol-read-case", "P");
      this.defProcStFld("system", "kawa.lib.system");
      this.defProcStFld("make-process", "kawa.lib.system");
      this.defProcStFld("tokenize-string-to-string-array", "kawa.lib.system");
      this.defProcStFld("tokenize-string-using-shell", "kawa.lib.system");
      this.defProcStFld("command-parse", "kawa.lib.system");
      this.defProcStFld("process-command-line-assignments", "kawa.lib.system");
      this.defProcStFld("record-accessor", "kawa.lib.reflection");
      this.defProcStFld("record-modifier", "kawa.lib.reflection");
      this.defProcStFld("record-predicate", "kawa.lib.reflection");
      this.defProcStFld("record-constructor", "kawa.lib.reflection");
      this.defProcStFld("make-record-type", "kawa.lib.reflection");
      this.defProcStFld("record-type-descriptor", "kawa.lib.reflection");
      this.defProcStFld("record-type-name", "kawa.lib.reflection");
      this.defProcStFld("record-type-field-names", "kawa.lib.reflection");
      this.defProcStFld("record?", "kawa.lib.reflection");
      this.defSntxStFld("define-record-type", "gnu.kawa.slib.DefineRecordType");
      this.defSntxStFld("when", "kawa.lib.syntax");
      this.defSntxStFld("unless", "kawa.lib.syntax");
      this.defSntxStFld("fluid-let", "kawa.standard.fluid_let", "fluid_let");
      this.defSntxStFld("constant-fold", "kawa.standard.constant_fold", "constant_fold");
      this.defProcStFld("make-parameter", "kawa.lib.parameters");
      this.defSntxStFld("parameterize", "kawa.lib.parameters");
      this.defProcStFld("compile-file", "kawa.lib.system");
      this.defProcStFld("environment-bound?", "kawa.lib.misc");
      this.defProcStFld("scheme-implementation-version", "kawa.lib.misc");
      this.defProcStFld("scheme-window", "kawa.lib.windows");
      this.defSntxStFld("define-procedure", "kawa.lib.std_syntax");
      this.defProcStFld("add-procedure-properties", "kawa.lib.misc");
      this.defProcStFld("make-procedure", "gnu.kawa.functions.MakeProcedure", "makeProcedure");
      this.defProcStFld("procedure-property", "kawa.lib.misc");
      this.defProcStFld("set-procedure-property!", "kawa.lib.misc");
      this.defSntxStFld("provide", "kawa.lib.misc_syntax");
      this.defSntxStFld("test-begin", "kawa.lib.misc_syntax");
      this.defProcStFld("quantity->number", "kawa.lib.numbers");
      this.defProcStFld("quantity->unit", "kawa.lib.numbers");
      this.defProcStFld("make-quantity", "kawa.lib.numbers");
      this.defSntxStFld("define-namespace", "gnu.kawa.lispexpr.DefineNamespace", "define_namespace");
      this.defSntxStFld("define-xml-namespace", "gnu.kawa.lispexpr.DefineNamespace", "define_xml_namespace");
      this.defSntxStFld("define-private-namespace", "gnu.kawa.lispexpr.DefineNamespace", "define_private_namespace");
      this.defSntxStFld("define-unit", "kawa.standard.define_unit", "define_unit");
      this.defSntxStFld("define-base-unit", "kawa.standard.define_unit", "define_base_unit");
      this.defProcStFld("duration", "kawa.lib.numbers");
      this.defProcStFld("gentemp", "kawa.lib.misc");
      this.defSntxStFld("defmacro", "kawa.lib.syntax");
      this.defProcStFld("setter", "gnu.kawa.functions.Setter", "setter");
      this.defSntxStFld("resource-url", "kawa.lib.misc_syntax");
      this.defSntxStFld("module-uri", "kawa.lib.misc_syntax");
      this.defSntxStFld("future", "kawa.lib.thread");
      this.defProcStFld("sleep", "kawa.lib.thread");
      this.defProcStFld("runnable", "kawa.lib.thread");
      this.defSntxStFld("trace", "kawa.lib.trace");
      this.defSntxStFld("untrace", "kawa.lib.trace");
      this.defSntxStFld("disassemble", "kawa.lib.trace");
      this.defProcStFld("format", "gnu.kawa.functions.Format");
      this.defProcStFld("parse-format", "gnu.kawa.functions.ParseFormat", "parseFormat");
      this.defProcStFld("make-element", "gnu.kawa.xml.MakeElement", "makeElement");
      this.defProcStFld("make-attribute", "gnu.kawa.xml.MakeAttribute", "makeAttribute");
      this.defProcStFld("map-values", "gnu.kawa.functions.ValuesMap", "valuesMap");
      this.defProcStFld("children", "gnu.kawa.xml.Children", "children");
      this.defProcStFld("attributes", "gnu.kawa.xml.Attributes");
      this.defProcStFld("unescaped-data", "gnu.kawa.xml.MakeUnescapedData", "unescapedData");
      this.defProcStFld("keyword?", "kawa.lib.keywords");
      this.defProcStFld("keyword->string", "kawa.lib.keywords");
      this.defProcStFld("string->keyword", "kawa.lib.keywords");
      this.defSntxStFld("location", "kawa.standard.location", "location");
      this.defSntxStFld("define-alias", "kawa.standard.define_alias", "define_alias");
      this.defSntxStFld("define-variable", "kawa.standard.define_variable", "define_variable");
      this.defSntxStFld("define-member-alias", "kawa.standard.define_member_alias", "define_member_alias");
      this.defSntxStFld("define-enum", "gnu.kawa.slib.enums");
      this.defSntxStFld("import", "kawa.lib.syntax");
      this.defSntxStFld("require", "kawa.standard.require", "require");
      this.defSntxStFld("module-name", "kawa.standard.module_name", "module_name");
      this.defSntxStFld("module-extends", "kawa.standard.module_extends", "module_extends");
      this.defSntxStFld("module-implements", "kawa.standard.module_implements", "module_implements");
      this.defSntxStFld("module-static", "kawa.standard.module_static", "module_static");
      this.defSntxStFld("module-export", "kawa.standard.export", "module_export");
      this.defSntxStFld("export", "kawa.standard.export", "export");
      this.defSntxStFld("module-compile-options", "kawa.standard.module_compile_options", "module_compile_options");
      this.defSntxStFld("with-compile-options", "kawa.standard.with_compile_options", "with_compile_options");
      this.defProcStFld("array?", "kawa.lib.arrays");
      this.defProcStFld("array-rank", "kawa.lib.arrays");
      this.defProcStFld("make-array", "kawa.lib.arrays");
      this.defProcStFld("array", "kawa.lib.arrays");
      this.defProcStFld("array-start", "kawa.lib.arrays");
      this.defProcStFld("array-end", "kawa.lib.arrays");
      this.defProcStFld("shape", "kawa.lib.arrays");
      this.defProcStFld("array-ref", "gnu.kawa.functions.ArrayRef", "arrayRef");
      this.defProcStFld("array-set!", "gnu.kawa.functions.ArraySet", "arraySet");
      this.defProcStFld("share-array", "kawa.lib.arrays");
      this.defProcStFld("s8vector?", "kawa.lib.uniform");
      this.defProcStFld("make-s8vector", "kawa.lib.uniform");
      this.defProcStFld("s8vector", "kawa.lib.uniform");
      this.defProcStFld("s8vector-length", "kawa.lib.uniform");
      this.defProcStFld("s8vector-ref", "kawa.lib.uniform");
      this.defProcStFld("s8vector-set!", "kawa.lib.uniform");
      this.defProcStFld("s8vector->list", "kawa.lib.uniform");
      this.defProcStFld("list->s8vector", "kawa.lib.uniform");
      this.defProcStFld("u8vector?", "kawa.lib.uniform");
      this.defProcStFld("make-u8vector", "kawa.lib.uniform");
      this.defProcStFld("u8vector", "kawa.lib.uniform");
      this.defProcStFld("u8vector-length", "kawa.lib.uniform");
      this.defProcStFld("u8vector-ref", "kawa.lib.uniform");
      this.defProcStFld("u8vector-set!", "kawa.lib.uniform");
      this.defProcStFld("u8vector->list", "kawa.lib.uniform");
      this.defProcStFld("list->u8vector", "kawa.lib.uniform");
      this.defProcStFld("s16vector?", "kawa.lib.uniform");
      this.defProcStFld("make-s16vector", "kawa.lib.uniform");
      this.defProcStFld("s16vector", "kawa.lib.uniform");
      this.defProcStFld("s16vector-length", "kawa.lib.uniform");
      this.defProcStFld("s16vector-ref", "kawa.lib.uniform");
      this.defProcStFld("s16vector-set!", "kawa.lib.uniform");
      this.defProcStFld("s16vector->list", "kawa.lib.uniform");
      this.defProcStFld("list->s16vector", "kawa.lib.uniform");
      this.defProcStFld("u16vector?", "kawa.lib.uniform");
      this.defProcStFld("make-u16vector", "kawa.lib.uniform");
      this.defProcStFld("u16vector", "kawa.lib.uniform");
      this.defProcStFld("u16vector-length", "kawa.lib.uniform");
      this.defProcStFld("u16vector-ref", "kawa.lib.uniform");
      this.defProcStFld("u16vector-set!", "kawa.lib.uniform");
      this.defProcStFld("u16vector->list", "kawa.lib.uniform");
      this.defProcStFld("list->u16vector", "kawa.lib.uniform");
      this.defProcStFld("s32vector?", "kawa.lib.uniform");
      this.defProcStFld("make-s32vector", "kawa.lib.uniform");
      this.defProcStFld("s32vector", "kawa.lib.uniform");
      this.defProcStFld("s32vector-length", "kawa.lib.uniform");
      this.defProcStFld("s32vector-ref", "kawa.lib.uniform");
      this.defProcStFld("s32vector-set!", "kawa.lib.uniform");
      this.defProcStFld("s32vector->list", "kawa.lib.uniform");
      this.defProcStFld("list->s32vector", "kawa.lib.uniform");
      this.defProcStFld("u32vector?", "kawa.lib.uniform");
      this.defProcStFld("make-u32vector", "kawa.lib.uniform");
      this.defProcStFld("u32vector", "kawa.lib.uniform");
      this.defProcStFld("u32vector-length", "kawa.lib.uniform");
      this.defProcStFld("u32vector-ref", "kawa.lib.uniform");
      this.defProcStFld("u32vector-set!", "kawa.lib.uniform");
      this.defProcStFld("u32vector->list", "kawa.lib.uniform");
      this.defProcStFld("list->u32vector", "kawa.lib.uniform");
      this.defProcStFld("s64vector?", "kawa.lib.uniform");
      this.defProcStFld("make-s64vector", "kawa.lib.uniform");
      this.defProcStFld("s64vector", "kawa.lib.uniform");
      this.defProcStFld("s64vector-length", "kawa.lib.uniform");
      this.defProcStFld("s64vector-ref", "kawa.lib.uniform");
      this.defProcStFld("s64vector-set!", "kawa.lib.uniform");
      this.defProcStFld("s64vector->list", "kawa.lib.uniform");
      this.defProcStFld("list->s64vector", "kawa.lib.uniform");
      this.defProcStFld("u64vector?", "kawa.lib.uniform");
      this.defProcStFld("make-u64vector", "kawa.lib.uniform");
      this.defProcStFld("u64vector", "kawa.lib.uniform");
      this.defProcStFld("u64vector-length", "kawa.lib.uniform");
      this.defProcStFld("u64vector-ref", "kawa.lib.uniform");
      this.defProcStFld("u64vector-set!", "kawa.lib.uniform");
      this.defProcStFld("u64vector->list", "kawa.lib.uniform");
      this.defProcStFld("list->u64vector", "kawa.lib.uniform");
      this.defProcStFld("f32vector?", "kawa.lib.uniform");
      this.defProcStFld("make-f32vector", "kawa.lib.uniform");
      this.defProcStFld("f32vector", "kawa.lib.uniform");
      this.defProcStFld("f32vector-length", "kawa.lib.uniform");
      this.defProcStFld("f32vector-ref", "kawa.lib.uniform");
      this.defProcStFld("f32vector-set!", "kawa.lib.uniform");
      this.defProcStFld("f32vector->list", "kawa.lib.uniform");
      this.defProcStFld("list->f32vector", "kawa.lib.uniform");
      this.defProcStFld("f64vector?", "kawa.lib.uniform");
      this.defProcStFld("make-f64vector", "kawa.lib.uniform");
      this.defProcStFld("f64vector", "kawa.lib.uniform");
      this.defProcStFld("f64vector-length", "kawa.lib.uniform");
      this.defProcStFld("f64vector-ref", "kawa.lib.uniform");
      this.defProcStFld("f64vector-set!", "kawa.lib.uniform");
      this.defProcStFld("f64vector->list", "kawa.lib.uniform");
      this.defProcStFld("list->f64vector", "kawa.lib.uniform");
      this.defSntxStFld("cut", "gnu.kawa.slib.cut");
      this.defSntxStFld("cute", "gnu.kawa.slib.cut");
      this.defSntxStFld("cond-expand", "kawa.lib.syntax");
      this.defSntxStFld("%cond-expand", "kawa.lib.syntax");
      this.defAliasStFld("*print-base*", "gnu.kawa.functions.DisplayFormat", "outBase");
      this.defAliasStFld("*print-radix*", "gnu.kawa.functions.DisplayFormat", "outRadix");
      this.defAliasStFld("*print-right-margin*", "gnu.text.PrettyWriter", "lineLengthLoc");
      this.defAliasStFld("*print-miser-width*", "gnu.text.PrettyWriter", "miserWidthLoc");
      this.defAliasStFld("html", "gnu.kawa.xml.XmlNamespace", "HTML");
      this.defAliasStFld("unit", "kawa.standard.Scheme", "unitNamespace");
      this.defAliasStFld("path", "gnu.kawa.lispexpr.LangObjType", "pathType");
      this.defAliasStFld("filepath", "gnu.kawa.lispexpr.LangObjType", "filepathType");
      this.defAliasStFld("URI", "gnu.kawa.lispexpr.LangObjType", "URIType");
      this.defProcStFld("resolve-uri", "kawa.lib.files");
      this.defAliasStFld("vector", "gnu.kawa.lispexpr.LangObjType", "vectorType");
      this.defAliasStFld("string", "gnu.kawa.lispexpr.LangObjType", "stringType");
      this.defAliasStFld("list", "gnu.kawa.lispexpr.LangObjType", "listType");
      this.defAliasStFld("regex", "gnu.kawa.lispexpr.LangObjType", "regexType");
      this.defProcStFld("path?", "kawa.lib.files");
      this.defProcStFld("filepath?", "kawa.lib.files");
      this.defProcStFld("URI?", "kawa.lib.files");
      this.defProcStFld("absolute-path?", "kawa.lib.files");
      this.defProcStFld("path-scheme", "kawa.lib.files");
      this.defProcStFld("path-authority", "kawa.lib.files");
      this.defProcStFld("path-user-info", "kawa.lib.files");
      this.defProcStFld("path-host", "kawa.lib.files");
      this.defProcStFld("path-port", "kawa.lib.files");
      this.defProcStFld("path-file", "kawa.lib.files");
      this.defProcStFld("path-parent", "kawa.lib.files");
      this.defProcStFld("path-directory", "kawa.lib.files");
      this.defProcStFld("path-last", "kawa.lib.files");
      this.defProcStFld("path-extension", "kawa.lib.files");
      this.defProcStFld("path-fragment", "kawa.lib.files");
      this.defProcStFld("path-query", "kawa.lib.files");
      kawaEnvironment.setLocked();
   }

   public static void registerEnvironment() {
      Language.setDefaults(getInstance());
   }

   public static Type string2Type(String var0) {
      Object var1;
      if(var0.endsWith("[]")) {
         Type var2 = string2Type(var0.substring(0, var0.length() - 2));
         var1 = var2;
         if(var2 != null) {
            var1 = ArrayType.make((Type)var2);
         }
      } else {
         var1 = getNamedType(var0);
      }

      if(var1 != null) {
         return (Type)var1;
      } else {
         Type var3 = Language.string2Type(var0);
         if(var3 != null) {
            types.put(var0, var3);
         }

         return var3;
      }
   }

   public Symbol asSymbol(String var1) {
      return Namespace.EmptyNamespace.getSymbol(var1);
   }

   public Expression checkDefaultBinding(Symbol param1, Translator param2) {
      // $FF: Couldn't be decompiled
   }

   public ReadTable createReadTable() {
      ReadTable var1 = ReadTable.createInitial();
      var1.postfixLookupOperator = 58;
      ReaderDispatch var2 = (ReaderDispatch)var1.lookup(35);
      var2.set(39, new ReaderQuote(this.asSymbol("syntax")));
      var2.set(96, new ReaderQuote(this.asSymbol("quasisyntax")));
      var2.set(44, ReaderDispatchMisc.getInstance());
      var1.putReaderCtorFld("path", "gnu.kawa.lispexpr.LangObjType", "pathType");
      var1.putReaderCtorFld("filepath", "gnu.kawa.lispexpr.LangObjType", "filepathType");
      var1.putReaderCtorFld("URI", "gnu.kawa.lispexpr.LangObjType", "URIType");
      var1.putReaderCtor("symbol", (Type)ClassType.make("gnu.mapping.Symbol"));
      var1.putReaderCtor("namespace", (Type)ClassType.make("gnu.mapping.Namespace"));
      var1.putReaderCtorFld("duration", "kawa.lib.numbers", "duration");
      var1.setFinalColonIsKeyword(true);
      return var1;
   }

   public String formatType(Type var1) {
      if(typeToStringMap == null) {
         typeToStringMap = new HashMap();
         Iterator var2 = getTypeMap().entrySet().iterator();

         while(var2.hasNext()) {
            Entry var4 = (Entry)var2.next();
            String var3 = (String)var4.getKey();
            Type var7 = (Type)var4.getValue();
            typeToStringMap.put(var7, var3);
            Type var5 = var7.getImplementationType();
            if(var5 != var7) {
               typeToStringMap.put(var5, var3);
            }
         }
      }

      String var6 = (String)typeToStringMap.get(var1);
      return var6 != null?var6:super.formatType(var1);
   }

   public AbstractFormat getFormat(boolean var1) {
      return var1?writeFormat:displayFormat;
   }

   public String getName() {
      return "Scheme";
   }

   public int getNamespaceOf(Declaration var1) {
      return 3;
   }

   public Type getTypeFor(Class var1) {
      String var2 = var1.getName();
      return (Type)(var1.isPrimitive()?getNamedType(var2):("java.lang.String".equals(var2)?Type.toStringType:("gnu.math.IntNum".equals(var2)?LangObjType.integerType:("gnu.math.DFloNum".equals(var2)?LangObjType.dflonumType:("gnu.math.RatNum".equals(var2)?LangObjType.rationalType:("gnu.math.RealNum".equals(var2)?LangObjType.realType:("gnu.math.Numeric".equals(var2)?LangObjType.numericType:("gnu.lists.FVector".equals(var2)?LangObjType.vectorType:("gnu.lists.LList".equals(var2)?LangObjType.listType:("gnu.text.Path".equals(var2)?LangObjType.pathType:("gnu.text.URIPath".equals(var2)?LangObjType.URIType:("gnu.text.FilePath".equals(var2)?LangObjType.filepathType:("java.lang.Class".equals(var2)?LangObjType.typeClass:("gnu.bytecode.Type".equals(var2)?LangObjType.typeType:("gnu.bytecode.ClassType".equals(var2)?LangObjType.typeClassType:Type.make(var1))))))))))))))));
   }

   public Type getTypeFor(String var1) {
      return string2Type(var1);
   }

   public Expression makeApply(Expression var1, Expression[] var2) {
      Expression[] var3 = new Expression[var2.length + 1];
      var3[0] = var1;
      System.arraycopy(var2, 0, var3, 1, var2.length);
      return new ApplyExp(new ReferenceExp(applyFieldDecl), var3);
   }
}
