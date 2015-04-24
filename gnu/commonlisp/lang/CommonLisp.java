package gnu.commonlisp.lang;

import gnu.bytecode.Type;
import gnu.commonlisp.lang.Lisp2;
import gnu.commonlisp.lang.UnwindProtect;
import gnu.commonlisp.lang.defun;
import gnu.commonlisp.lang.defvar;
import gnu.commonlisp.lang.function;
import gnu.commonlisp.lang.prog1;
import gnu.commonlisp.lang.setq;
import gnu.expr.Language;
import gnu.kawa.functions.DisplayFormat;
import gnu.kawa.functions.IsEq;
import gnu.kawa.functions.IsEqual;
import gnu.kawa.functions.Not;
import gnu.kawa.functions.NumberCompare;
import gnu.kawa.lispexpr.LangPrimType;
import gnu.kawa.reflect.InstanceOf;
import gnu.lists.AbstractFormat;
import gnu.mapping.Environment;
import gnu.mapping.LocationEnumeration;
import gnu.math.IntNum;
import gnu.math.Numeric;
import gnu.text.Char;
import kawa.lang.Lambda;
import kawa.standard.Scheme;
import kawa.standard.begin;

public class CommonLisp extends Lisp2 {

   static boolean charIsInt = false;
   public static final Environment clispEnvironment = Environment.make("clisp-environment");
   static final AbstractFormat displayFormat;
   public static final CommonLisp instance = new CommonLisp();
   public static final NumberCompare numEqu;
   public static final NumberCompare numGEq;
   public static final NumberCompare numGrt;
   public static final NumberCompare numLEq;
   public static final NumberCompare numLss;
   static final AbstractFormat writeFormat;
   LangPrimType booleanType;


   static {
      instance.define("t", TRUE);
      instance.define("nil", FALSE);
      numEqu = NumberCompare.make(instance, "=", 8);
      numGrt = NumberCompare.make(instance, ">", 16);
      numGEq = NumberCompare.make(instance, ">=", 24);
      numLss = NumberCompare.make(instance, "<", 4);
      numLEq = NumberCompare.make(instance, "<=", 12);
      Environment var0 = Environment.setSaveCurrent(clispEnvironment);

      try {
         instance.initLisp();
      } finally {
         Environment.restoreCurrent(var0);
      }

      writeFormat = new DisplayFormat(true, 'C');
      displayFormat = new DisplayFormat(false, 'C');
   }

   public CommonLisp() {
      this.environ = clispEnvironment;
   }

   public static char asChar(Object var0) {
      if(var0 instanceof Char) {
         return ((Char)var0).charValue();
      } else {
         int var1;
         if(var0 instanceof Numeric) {
            var1 = ((Numeric)var0).intValue();
         } else {
            var1 = -1;
         }

         if(var1 >= 0 && var1 <= '\uffff') {
            return (char)var1;
         } else {
            throw new ClassCastException("not a character value");
         }
      }
   }

   public static Numeric asNumber(Object var0) {
      return (Numeric)(var0 instanceof Char?IntNum.make(((Char)var0).intValue()):(Numeric)var0);
   }

   public static Object getCharacter(int var0) {
      return charIsInt?IntNum.make(var0):Char.make((char)var0);
   }

   public static CommonLisp getInstance() {
      return instance;
   }

   public static void registerEnvironment() {
      Language.setDefaults(instance);
   }

   public AbstractFormat getFormat(boolean var1) {
      return var1?writeFormat:displayFormat;
   }

   public String getName() {
      return "CommonLisp";
   }

   public Type getTypeFor(Class var1) {
      if(var1.isPrimitive()) {
         String var2 = var1.getName();
         if(var2.equals("boolean")) {
            if(this.booleanType == null) {
               this.booleanType = new LangPrimType(Type.booleanType, this);
            }

            return this.booleanType;
         } else {
            return Scheme.getNamedType(var2);
         }
      } else {
         return Type.make(var1);
      }
   }

   public Type getTypeFor(String var1) {
      String var2 = var1;
      if(var1 == "t") {
         var2 = "java.lang.Object";
      }

      return Scheme.string2Type(var2);
   }

   void initLisp() {
      LocationEnumeration var1 = Scheme.builtin().enumerateAllLocations();

      while(var1.hasMoreElements()) {
         this.importLocation(var1.nextLocation());
      }

      try {
         this.loadClass("kawa.lib.prim_syntax");
         this.loadClass("kawa.lib.std_syntax");
         this.loadClass("kawa.lib.lists");
         this.loadClass("kawa.lib.strings");
         this.loadClass("gnu.commonlisp.lisp.PrimOps");
      } catch (ClassNotFoundException var2) {
         ;
      }

      Lambda var3 = new Lambda();
      var3.setKeywords(asSymbol("&optional"), asSymbol("&rest"), asSymbol("&key"));
      var3.defaultDefault = nilExpr;
      this.defun("lambda", var3);
      this.defun("defun", new defun(var3));
      this.defun("defvar", new defvar(false));
      this.defun("defconst", new defvar(true));
      this.defun("defsubst", new defun(var3));
      this.defun("function", new function(var3));
      this.defun("setq", new setq());
      this.defun("prog1", new prog1("prog1", 1));
      this.defun("prog2", prog1.prog2);
      this.defun("progn", new begin());
      this.defun("unwind-protect", new UnwindProtect());
      Not var4 = new Not(this);
      this.defun("not", var4);
      this.defun("null", var4);
      this.defun("eq", new IsEq(this, "eq"));
      this.defun("equal", new IsEqual(this, "equal"));
      this.defun("typep", new InstanceOf(this));
      this.defun("princ", displayFormat);
      this.defun("prin1", writeFormat);
      this.defProcStFld("=", "gnu.commonlisp.lang.CommonLisp", "numEqu");
      this.defProcStFld("<", "gnu.commonlisp.lang.CommonLisp", "numLss");
      this.defProcStFld(">", "gnu.commonlisp.lang.CommonLisp", "numGrt");
      this.defProcStFld("<=", "gnu.commonlisp.lang.CommonLisp", "numLEq");
      this.defProcStFld(">=", "gnu.commonlisp.lang.CommonLisp", "numGEq");
      this.defProcStFld("functionp", "gnu.commonlisp.lisp.PrimOps");
   }
}
