package gnu.expr;

import gnu.bytecode.ArrayType;
import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Field;
import gnu.bytecode.PrimType;
import gnu.bytecode.Type;
import gnu.expr.BuiltinEnvironment;
import gnu.expr.ClassExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.KawaConvert;
import gnu.expr.LambdaExp;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleExp;
import gnu.expr.ModuleInfo;
import gnu.expr.NameLookup;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.SimplePrompter;
import gnu.kawa.lispexpr.ClassNamespace;
import gnu.kawa.reflect.ClassMemberLocation;
import gnu.kawa.reflect.StaticFieldLocation;
import gnu.lists.AbstractFormat;
import gnu.lists.CharSeq;
import gnu.lists.Consumer;
import gnu.lists.Convert;
import gnu.lists.FString;
import gnu.lists.PrintConsumer;
import gnu.mapping.CallContext;
import gnu.mapping.CharArrayInPort;
import gnu.mapping.Environment;
import gnu.mapping.EnvironmentKey;
import gnu.mapping.InPort;
import gnu.mapping.Location;
import gnu.mapping.Named;
import gnu.mapping.NamedLocation;
import gnu.mapping.Namespace;
import gnu.mapping.OutPort;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.mapping.WrappedException;
import gnu.text.Lexer;
import gnu.text.SourceMessages;
import gnu.text.SyntaxException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import kawa.repl;

public abstract class Language {

   public static final int FUNCTION_NAMESPACE = 2;
   public static final int NAMESPACE_PREFIX_NAMESPACE = 4;
   public static final int PARSE_CURRENT_NAMES = 2;
   public static final int PARSE_EXPLICIT = 64;
   public static final int PARSE_FOR_APPLET = 16;
   public static final int PARSE_FOR_EVAL = 3;
   public static final int PARSE_FOR_SERVLET = 32;
   public static final int PARSE_IMMEDIATE = 1;
   public static final int PARSE_ONE_LINE = 4;
   public static final int PARSE_PROLOG = 8;
   public static final int VALUE_NAMESPACE = 1;
   protected static final InheritableThreadLocal current = new InheritableThreadLocal();
   static int envCounter;
   protected static int env_counter;
   protected static Language global;
   static String[][] languages;
   public static boolean requirePedantic;
   protected Environment environ;
   protected Environment userEnv;


   static {
      Environment.setGlobal(BuiltinEnvironment.getInstance());
      String[] var0 = new String[]{"krl", ".krl", "gnu.kawa.brl.BRL"};
      languages = new String[][]{{"scheme", ".scm", ".sc", "kawa.standard.Scheme"}, var0, {"brl", ".brl", "gnu.kawa.brl.BRL"}, {"emacs", "elisp", "emacs-lisp", ".el", "gnu.jemacs.lang.ELisp"}, {"xquery", ".xquery", ".xq", ".xql", "gnu.xquery.lang.XQuery"}, {"q2", ".q2", "gnu.q2.lang.Q2"}, {"xslt", "xsl", ".xsl", "gnu.kawa.xslt.XSLT"}, {"commonlisp", "common-lisp", "clisp", "lisp", ".lisp", ".lsp", ".cl", "gnu.commonlisp.lang.CommonLisp"}};
      env_counter = 0;
   }

   protected Language() {
      Convert.setInstance(KawaConvert.getInstance());
   }

   public static Language detect(InPort var0) throws IOException {
      StringBuffer var1 = new StringBuffer();
      var0.mark(300);
      var0.readLine(var1, 'P');
      var0.reset();
      return detect((String)var1.toString());
   }

   public static Language detect(InputStream var0) throws IOException {
      if(!var0.markSupported()) {
         return null;
      } else {
         StringBuffer var1 = new StringBuffer();
         var0.mark(200);

         while(var1.length() < 200) {
            int var2 = var0.read();
            if(var2 < 0 || var2 == 10 || var2 == 13) {
               break;
            }

            var1.append((char)var2);
         }

         var0.reset();
         return detect((String)var1.toString());
      }
   }

   public static Language detect(String var0) {
      var0 = var0.trim();
      int var2 = var0.indexOf("kawa:");
      if(var2 >= 0) {
         int var3 = var2 + 5;

         for(var2 = var3; var2 < var0.length() && Character.isJavaIdentifierPart(var0.charAt(var2)); ++var2) {
            ;
         }

         if(var2 > var3) {
            Language var1 = getInstance(var0.substring(var3, var2));
            if(var1 != null) {
               return var1;
            }
         }
      }

      return var0.indexOf("-*- scheme -*-") >= 0?getInstance("scheme"):(var0.indexOf("-*- xquery -*-") >= 0?getInstance("xquery"):(var0.indexOf("-*- emacs-lisp -*-") >= 0?getInstance("elisp"):(var0.indexOf("-*- common-lisp -*-") < 0 && var0.indexOf("-*- lisp -*-") < 0?((var0.charAt(0) != 40 || var0.charAt(1) != 58) && (var0.length() < 7 || !var0.substring(0, 7).equals("xquery "))?(var0.charAt(0) == 59 && var0.charAt(1) == 59?getInstance("scheme"):null):getInstance("xquery")):getInstance("common-lisp"))));
   }

   public static Language getDefaultLanguage() {
      Language var0 = (Language)current.get();
      return var0 != null?var0:global;
   }

   public static Language getInstance(String var0) {
      int var7 = languages.length;
      int var3 = 0;

      while(var3 < var7) {
         String[] var1 = languages[var3];
         int var5 = var1.length - 1;
         int var4 = var5;

         while(true) {
            int var6 = var4 - 1;
            if(var6 >= 0) {
               if(var0 != null) {
                  var4 = var6;
                  if(!var1[var6].equalsIgnoreCase(var0)) {
                     continue;
                  }
               }

               try {
                  Class var2 = Class.forName(var1[var5]);
                  return getInstance(var1[0], var2);
               } catch (ClassNotFoundException var8) {
                  ;
               }
            }

            ++var3;
            break;
         }
      }

      return null;
   }

   public static Language getInstance(String param0, Class param1) {
      // $FF: Couldn't be decompiled
   }

   public static Language getInstanceFromFilenameExtension(String var0) {
      int var1 = var0.lastIndexOf(46);
      if(var1 > 0) {
         Language var2 = getInstance(var0.substring(var1));
         if(var2 != null) {
            return var2;
         }
      }

      return null;
   }

   public static String[][] getLanguages() {
      return languages;
   }

   public static void registerLanguage(String[] var0) {
      String[][] var1 = new String[languages.length + 1][];
      System.arraycopy(languages, 0, var1, 0, languages.length);
      var1[var1.length - 1] = var0;
      languages = var1;
   }

   public static void restoreCurrent(Language var0) {
      current.set(var0);
   }

   public static void setCurrentLanguage(Language var0) {
      current.set(var0);
   }

   public static void setDefaults(Language var0) {
      synchronized(Language.class){}

      try {
         setCurrentLanguage(var0);
         global = var0;
         if(Environment.getGlobal() == BuiltinEnvironment.getInstance()) {
            Environment.setGlobal(Environment.getCurrent());
         }
      } finally {
         ;
      }

   }

   public static Language setSaveCurrent(Language var0) {
      Language var1 = (Language)current.get();
      current.set(var0);
      return var1;
   }

   public static Type string2Type(String var0) {
      if(var0.endsWith("[]")) {
         Type var1 = string2Type(var0.substring(0, var0.length() - 2));
         return var1 == null?null:ArrayType.make((Type)var1);
      } else {
         return Type.isValidJavaTypeName(var0)?Type.getType(var0):null;
      }
   }

   public static Type unionType(Type var0, Type var1) {
      Object var2 = var0;
      if(var0 == Type.toStringType) {
         var2 = Type.javalangStringType;
      }

      Object var5 = var1;
      if(var1 == Type.toStringType) {
         var5 = Type.javalangStringType;
      }

      if(var2 != var5) {
         if(!(var2 instanceof PrimType) || !(var5 instanceof PrimType)) {
            return Type.objectType;
         }

         char var3 = ((Type)var2).getSignature().charAt(0);
         char var4 = ((Type)var5).getSignature().charAt(0);
         if(var3 != var4) {
            if((var3 == 66 || var3 == 83 || var3 == 73) && (var4 == 73 || var4 == 74)) {
               return (Type)var5;
            }

            if(var4 != 66 && var4 != 83 && var4 != 73 || var3 != 73 && var3 != 74) {
               if(var3 == 70 && var4 == 68) {
                  return (Type)var5;
               }

               if(var4 != 70 || var3 != 68) {
                  return Type.objectType;
               }
            }
         }
      }

      return (Type)var2;
   }

   public final Type asType(Object var1) {
      Type var2 = this.getTypeFor((Object)var1, true);
      return var2 == null?(Type)var1:var2;
   }

   public Object booleanObject(boolean var1) {
      return var1?Boolean.TRUE:Boolean.FALSE;
   }

   public Object coerceFromObject(Class var1, Object var2) {
      return this.getTypeFor((Class)var1).coerceFromObject(var2);
   }

   public Object coerceToObject(Class var1, Object var2) {
      return this.getTypeFor((Class)var1).coerceToObject(var2);
   }

   public Declaration declFromField(ModuleExp var1, Object var2, Field var3) {
      String var4 = var3.getName();
      Type var5 = var3.getType();
      boolean var9 = var5.isSubtype(Compilation.typeLocation);
      boolean var8 = false;
      boolean var6 = false;
      boolean var7;
      if((var3.getModifiers() & 16) != 0) {
         var7 = true;
      } else {
         var7 = false;
      }

      boolean var10 = var4.endsWith("$instance");
      String var12;
      if(var10) {
         var2 = var4;
      } else if(var7 && var2 instanceof Named) {
         var2 = ((Named)var2).getSymbol();
      } else {
         var6 = var8;
         var12 = var4;
         if(var4.startsWith("$Prvt$")) {
            var6 = true;
            var12 = var4.substring("$Prvt$".length());
         }

         var2 = Compilation.demangleName(var12, true).intern();
      }

      Object var13 = var2;
      if(var2 instanceof String) {
         var4 = var1.getNamespaceUri();
         var12 = (String)var2;
         if(var4 == null) {
            var13 = SimpleSymbol.valueOf(var12);
         } else {
            var13 = Symbol.make(var4, var12);
         }
      }

      if(var9) {
         var2 = Type.objectType;
      } else {
         var2 = this.getTypeFor((Class)var5.getReflectClass());
      }

      Declaration var11 = var1.addDeclaration(var13, (Type)var2);
      if((var3.getModifiers() & 8) != 0) {
         var8 = true;
      } else {
         var8 = false;
      }

      if(var9) {
         var11.setIndirectBinding(true);
         if(var5 instanceof ClassType && ((ClassType)var5).isSubclass((String)"gnu.mapping.ThreadLocation")) {
            var11.setFlag(268435456L);
         }
      } else if(var7 && var5 instanceof ClassType) {
         if(var5.isSubtype(Compilation.typeProcedure)) {
            var11.setProcedureDecl(true);
         } else if(((ClassType)var5).isSubclass((String)"gnu.mapping.Namespace")) {
            var11.setFlag(2097152L);
         }
      }

      if(var8) {
         var11.setFlag(2048L);
      }

      var11.field = var3;
      if(var7 && !var9) {
         var11.setFlag(16384L);
      }

      if(var10) {
         var11.setFlag(1073741824L);
      }

      var11.setSimple(false);
      if(var6) {
         var11.setFlag(524320L);
      }

      return var11;
   }

   protected void defAliasStFld(String var1, String var2, String var3) {
      StaticFieldLocation.define(this.environ, this.getSymbol(var1), (Object)null, var2, var3);
   }

   protected void defProcStFld(String var1, String var2) {
      this.defProcStFld(var1, var2, Compilation.mangleNameIfNeeded(var1));
   }

   protected void defProcStFld(String var1, String var2, String var3) {
      Object var4;
      if(this.hasSeparateFunctionNamespace()) {
         var4 = EnvironmentKey.FUNCTION;
      } else {
         var4 = null;
      }

      Symbol var5 = this.getSymbol(var1);
      StaticFieldLocation.define(this.environ, var5, var4, var2, var3).setProcedure();
   }

   public void define(String var1, Object var2) {
      Symbol var3 = this.getSymbol(var1);
      this.environ.define(var3, (Object)null, var2);
   }

   public final void defineFunction(Named var1) {
      Object var2 = var1.getSymbol();
      Symbol var4;
      if(var2 instanceof Symbol) {
         var4 = (Symbol)var2;
      } else {
         var4 = this.getSymbol(var2.toString());
      }

      Object var3;
      if(this.hasSeparateFunctionNamespace()) {
         var3 = EnvironmentKey.FUNCTION;
      } else {
         var3 = null;
      }

      this.environ.define(var4, var3, var1);
   }

   public void defineFunction(String var1, Object var2) {
      Object var3;
      if(this.hasSeparateFunctionNamespace()) {
         var3 = EnvironmentKey.FUNCTION;
      } else {
         var3 = null;
      }

      this.environ.define(this.getSymbol(var1), var3, var2);
   }

   public void emitCoerceToBoolean(CodeAttr var1) {
      this.emitPushBoolean(false, var1);
      var1.emitIfNEq();
      var1.emitPushInt(1);
      var1.emitElse();
      var1.emitPushInt(0);
      var1.emitFi();
   }

   public void emitPushBoolean(boolean var1, CodeAttr var2) {
      Field var3;
      if(var1) {
         var3 = Compilation.trueConstant;
      } else {
         var3 = Compilation.falseConstant;
      }

      var2.emitGetStatic(var3);
   }

   public final Object eval(InPort var1) throws Throwable {
      CallContext var2 = CallContext.getInstance();
      int var3 = var2.startFromContext();

      try {
         this.eval((InPort)var1, (CallContext)var2);
         Object var5 = var2.getFromContext(var3);
         return var5;
      } catch (Throwable var4) {
         var2.cleanupFromContext(var3);
         throw var4;
      }
   }

   public final Object eval(Reader var1) throws Throwable {
      InPort var2;
      if(var1 instanceof InPort) {
         var2 = (InPort)var1;
      } else {
         var2 = new InPort(var1);
      }

      return this.eval((InPort)var2);
   }

   public final Object eval(String var1) throws Throwable {
      return this.eval((InPort)(new CharArrayInPort(var1)));
   }

   public void eval(InPort var1, CallContext var2) throws Throwable {
      SourceMessages var4 = new SourceMessages();
      Language var3 = setSaveCurrent(this);

      try {
         Compilation var7 = this.parse(var1, var4, 3);
         ModuleExp.evalModule(this.getEnvironment(), var2, var7, (URL)null, (OutPort)null);
      } finally {
         restoreCurrent(var3);
      }

      if(var4.seenErrors()) {
         throw new RuntimeException("invalid syntax in eval form:\n" + var4.toString(20));
      }
   }

   public void eval(Reader var1, Consumer var2) throws Throwable {
      InPort var7;
      if(var1 instanceof InPort) {
         var7 = (InPort)var1;
      } else {
         var7 = new InPort(var1);
      }

      CallContext var3 = CallContext.getInstance();
      Consumer var4 = var3.consumer;

      try {
         var3.consumer = var2;
         this.eval((InPort)var7, (CallContext)var3);
      } finally {
         var3.consumer = var4;
      }

   }

   public final void eval(Reader var1, Writer var2) throws Throwable {
      this.eval((Reader)var1, (Consumer)this.getOutputConsumer(var2));
   }

   public final void eval(String var1, Consumer var2) throws Throwable {
      this.eval((Reader)(new CharArrayInPort(var1)), (Consumer)var2);
   }

   public final void eval(String var1, PrintConsumer var2) throws Throwable {
      this.eval((String)var1, (Consumer)this.getOutputConsumer(var2));
   }

   public final void eval(String var1, Writer var2) throws Throwable {
      this.eval((Reader)(new CharArrayInPort(var1)), (Writer)var2);
   }

   public String formatType(Type var1) {
      return var1.getName();
   }

   public Compilation getCompilation(Lexer var1, SourceMessages var2, NameLookup var3) {
      return new Compilation(this, var2, var3);
   }

   public Object getEnvPropertyFor(Declaration var1) {
      return this.hasSeparateFunctionNamespace() && var1.isProcedureDecl()?EnvironmentKey.FUNCTION:null;
   }

   public Object getEnvPropertyFor(java.lang.reflect.Field var1, Object var2) {
      return this.hasSeparateFunctionNamespace() && Compilation.typeProcedure.getReflectClass().isAssignableFrom(var1.getType())?EnvironmentKey.FUNCTION:null;
   }

   public final Environment getEnvironment() {
      return this.userEnv != null?this.userEnv:Environment.getCurrent();
   }

   public AbstractFormat getFormat(boolean var1) {
      return null;
   }

   public Environment getLangEnvironment() {
      return this.environ;
   }

   public final Type getLangTypeFor(Type var1) {
      Type var2 = var1;
      if(var1.isExisting()) {
         Class var3 = var1.getReflectClass();
         var2 = var1;
         if(var3 != null) {
            var2 = this.getTypeFor((Class)var3);
         }
      }

      return var2;
   }

   public abstract Lexer getLexer(InPort var1, SourceMessages var2);

   public String getName() {
      String var2 = this.getClass().getName();
      int var3 = var2.lastIndexOf(46);
      String var1 = var2;
      if(var3 >= 0) {
         var1 = var2.substring(var3 + 1);
      }

      return var1;
   }

   public int getNamespaceOf(Declaration var1) {
      return 1;
   }

   public final Environment getNewEnvironment() {
      StringBuilder var1 = (new StringBuilder()).append("environment-");
      int var2 = envCounter + 1;
      envCounter = var2;
      return Environment.make(var1.append(var2).toString(), this.environ);
   }

   public Consumer getOutputConsumer(Writer var1) {
      OutPort var2;
      if(var1 instanceof OutPort) {
         var2 = (OutPort)var1;
      } else {
         var2 = new OutPort(var1);
      }

      var2.objectFormat = this.getFormat(false);
      return var2;
   }

   public Procedure getPrompter() {
      Object var1 = null;
      if(this.hasSeparateFunctionNamespace()) {
         var1 = EnvironmentKey.FUNCTION;
      }

      Procedure var2 = (Procedure)this.getEnvironment().get(this.getSymbol("default-prompter"), var1, (Object)null);
      return (Procedure)(var2 != null?var2:new SimplePrompter());
   }

   public Symbol getSymbol(String var1) {
      return this.environ.getSymbol(var1);
   }

   public final Type getTypeFor(Expression var1) {
      return this.getTypeFor((Expression)var1, true);
   }

   public Type getTypeFor(Expression var1, boolean var2) {
      Object var4 = null;
      Type var3;
      if(var1 instanceof QuoteExp) {
         Object var8 = ((QuoteExp)var1).getValue();
         if(!(var8 instanceof Type)) {
            if(var8 instanceof Class) {
               return Type.make((Class)var8);
            }

            return this.getTypeFor((Object)var8, var2);
         }

         var3 = (Type)var8;
      } else if(var1 instanceof ReferenceExp) {
         ReferenceExp var9 = (ReferenceExp)var1;
         Declaration var5 = Declaration.followAliases(var9.getBinding());
         String var12 = var9.getName();
         String var10 = var12;
         if(var5 != null) {
            Expression var6 = var5.getValue();
            if(var6 instanceof QuoteExp && var5.getFlag(16384L) && !var5.isIndirectBinding()) {
               return this.getTypeFor((Object)((QuoteExp)var6).getValue(), var2);
            }

            if(var6 instanceof ClassExp || var6 instanceof ModuleExp) {
               var5.setCanRead(true);
               return ((LambdaExp)var6).getClassType();
            }

            if(var5.isAlias() && var6 instanceof QuoteExp) {
               Object var14 = ((QuoteExp)var6).getValue();
               var10 = var12;
               if(var14 instanceof Location) {
                  Location var11 = (Location)var14;
                  if(var11.isBound()) {
                     return this.getTypeFor((Object)var11.get(), var2);
                  }

                  var3 = (Type)var4;
                  if(!(var11 instanceof Named)) {
                     return var3;
                  }

                  var10 = ((Named)var11).getName();
               }
            } else {
               var10 = var12;
               if(!var5.getFlag(65536L)) {
                  return this.getTypeFor((Expression)var6, var2);
               }
            }
         }

         Object var13 = this.getEnvironment().get((Object)var10);
         if(var13 instanceof Type) {
            return (Type)var13;
         }

         if(var13 instanceof ClassNamespace) {
            return ((ClassNamespace)var13).getClassType();
         }

         int var7 = var10.length();
         var3 = (Type)var4;
         if(var7 > 2) {
            var3 = (Type)var4;
            if(var10.charAt(0) == 60) {
               var3 = (Type)var4;
               if(var10.charAt(var7 - 1) == 62) {
                  return this.getTypeFor((String)var10.substring(1, var7 - 1));
               }
            }
         }
      } else {
         if(!(var1 instanceof ClassExp)) {
            var3 = (Type)var4;
            if(!(var1 instanceof ModuleExp)) {
               return var3;
            }
         }

         return ((LambdaExp)var1).getClassType();
      }

      return var3;
   }

   public Type getTypeFor(Class var1) {
      return Type.make(var1);
   }

   public final Type getTypeFor(Object var1, boolean var2) {
      if(var1 instanceof Type) {
         return (Type)var1;
      } else if(var1 instanceof Class) {
         return this.getTypeFor((Class)((Class)var1));
      } else if(var2 && (var1 instanceof FString || var1 instanceof String || var1 instanceof Symbol && ((Symbol)var1).hasEmptyNamespace() || var1 instanceof CharSeq)) {
         return this.getTypeFor((String)var1.toString());
      } else {
         if(var1 instanceof Namespace) {
            String var3 = ((Namespace)var1).getName();
            if(var3 != null && var3.startsWith("class:")) {
               return this.getLangTypeFor(string2Type(var3.substring(6)));
            }
         }

         return null;
      }
   }

   public Type getTypeFor(String var1) {
      return string2Type(var1);
   }

   public boolean hasNamespace(Declaration var1, int var2) {
      return (this.getNamespaceOf(var1) & var2) != 0;
   }

   public boolean hasSeparateFunctionNamespace() {
      return false;
   }

   public boolean isTrue(Object var1) {
      return var1 != Boolean.FALSE;
   }

   public void loadClass(String var1) throws ClassNotFoundException {
      Class var2;
      try {
         var2 = Class.forName(var1);
      } catch (ClassNotFoundException var4) {
         throw var4;
      }

      try {
         Object var5 = var2.newInstance();
         ClassMemberLocation.defineAll(var5, this, Environment.getCurrent());
         if(var5 instanceof ModuleBody) {
            ((ModuleBody)var5).run();
         }

      } catch (Exception var3) {
         throw new WrappedException("cannot load " + var1, var3);
      }
   }

   public Object lookup(String var1) {
      return this.environ.get((Object)var1);
   }

   public NamedLocation lookupBuiltin(Symbol var1, Object var2, int var3) {
      return this.environ == null?null:this.environ.lookup(var1, var2, var3);
   }

   public Object noValue() {
      return Values.empty;
   }

   public final Compilation parse(InPort var1, SourceMessages var2, int var3) throws IOException, SyntaxException {
      return this.parse(this.getLexer(var1, var2), var3, (ModuleInfo)null);
   }

   public final Compilation parse(InPort var1, SourceMessages var2, int var3, ModuleInfo var4) throws IOException, SyntaxException {
      return this.parse(this.getLexer(var1, var2), var3, var4);
   }

   public final Compilation parse(InPort var1, SourceMessages var2, ModuleInfo var3) throws IOException, SyntaxException {
      return this.parse(this.getLexer(var1, var2), 8, var3);
   }

   public final Compilation parse(Lexer var1, int var2, ModuleInfo var3) throws IOException, SyntaxException {
      SourceMessages var5 = var1.getMessages();
      NameLookup var4;
      if((var2 & 2) != 0) {
         var4 = NameLookup.getInstance(this.getEnvironment(), this);
      } else {
         var4 = new NameLookup(this);
      }

      boolean var6;
      if((var2 & 1) != 0) {
         var6 = true;
      } else {
         var6 = false;
      }

      Compilation var8 = this.getCompilation(var1, var5, var4);
      if(requirePedantic) {
         var8.pedantic = true;
      }

      if(!var6) {
         var8.mustCompile = true;
      }

      var8.immediate = var6;
      var8.langOptions = var2;
      if((var2 & 64) != 0) {
         var8.explicit = true;
      }

      if((var2 & 8) != 0) {
         var8.setState(1);
      }

      var8.pushNewModule((Lexer)var1);
      if(var3 != null) {
         var3.setCompilation(var8);
      }

      Compilation var7;
      if(!this.parse(var8, var2)) {
         var7 = null;
      } else {
         var7 = var8;
         if(var8.getState() == 1) {
            var8.setState(2);
            return var8;
         }
      }

      return var7;
   }

   public abstract boolean parse(Compilation var1, int var2) throws IOException, SyntaxException;

   public void resolve(Compilation var1) {
   }

   public void runAsApplication(String[] var1) {
      setDefaults(this);
      repl.main(var1);
   }
}
