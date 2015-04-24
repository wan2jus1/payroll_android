package gnu.expr;

import gnu.bytecode.ArrayClassLoader;
import gnu.bytecode.ArrayType;
import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Field;
import gnu.bytecode.Label;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;
import gnu.bytecode.PrimType;
import gnu.bytecode.SwitchState;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.expr.ApplyExp;
import gnu.expr.BeginExp;
import gnu.expr.ChainLambdas;
import gnu.expr.CheckedTarget;
import gnu.expr.ClassExp;
import gnu.expr.ConditionalTarget;
import gnu.expr.Declaration;
import gnu.expr.ErrorExp;
import gnu.expr.Expression;
import gnu.expr.FindTailCalls;
import gnu.expr.IfExp;
import gnu.expr.Initializer;
import gnu.expr.InlineCalls;
import gnu.expr.LambdaExp;
import gnu.expr.Language;
import gnu.expr.LetExp;
import gnu.expr.LitTable;
import gnu.expr.Literal;
import gnu.expr.ModuleExp;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.expr.NameLookup;
import gnu.expr.PairClassType;
import gnu.expr.PushApply;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.ScopeExp;
import gnu.expr.Target;
import gnu.expr.TypeValue;
import gnu.kawa.functions.Convert;
import gnu.mapping.Environment;
import gnu.mapping.EnvironmentKey;
import gnu.mapping.OutPort;
import gnu.mapping.Procedure;
import gnu.mapping.Symbol;
import gnu.text.Lexer;
import gnu.text.Options;
import gnu.text.SourceLocator;
import gnu.text.SourceMessages;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Stack;
import java.util.Vector;
import java.util.jar.JarOutputStream;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Compilation implements SourceLocator {

   public static final int BODY_PARSED = 4;
   public static final int CALL_WITH_CONSUMER = 2;
   public static final int CALL_WITH_CONTINUATIONS = 4;
   public static final int CALL_WITH_RETURN = 1;
   public static final int CALL_WITH_TAILCALLS = 3;
   public static final int CALL_WITH_UNSPECIFIED = 0;
   public static final int CLASS_WRITTEN = 14;
   public static final int COMPILED = 12;
   public static final int COMPILE_SETUP = 10;
   public static final int ERROR_SEEN = 100;
   public static final int MODULE_NONSTATIC = -1;
   public static final int MODULE_STATIC = 1;
   public static final int MODULE_STATIC_DEFAULT = 0;
   public static final int MODULE_STATIC_RUN = 2;
   public static final int PROLOG_PARSED = 2;
   public static final int PROLOG_PARSING = 1;
   public static final int RESOLVED = 6;
   public static final int WALKED = 8;
   public static Type[] apply0args;
   public static Method apply0method;
   public static Type[] apply1args;
   public static Method apply1method;
   public static Type[] apply2args;
   public static Method apply2method;
   public static Method apply3method;
   public static Method apply4method;
   private static Type[] applyCpsArgs;
   public static Method applyCpsMethod;
   public static Type[] applyNargs;
   public static Method applyNmethod;
   public static Method[] applymethods;
   public static Field argsCallContextField;
   private static Compilation chainUninitialized;
   static Method checkArgCountMethod;
   public static String classPrefixDefault;
   private static final ThreadLocal current;
   public static boolean debugPrintExpr = false;
   public static boolean debugPrintFinalExpr;
   public static int defaultCallConvention;
   public static int defaultClassFileVersion = 3211264;
   public static boolean emitSourceDebugExtAttr;
   public static final Field falseConstant = scmBooleanType.getDeclaredField("FALSE");
   public static boolean generateMainDefault;
   public static Method getCallContextInstanceMethod;
   public static Method getCurrentEnvironmentMethod;
   public static final Method getLocation1EnvironmentMethod = typeEnvironment.getDeclaredMethod("getLocation", 1);
   public static final Method getLocation2EnvironmentMethod;
   public static final Method getLocationMethod = typeLocation.addMethod("get", Type.typeArray0, Type.objectType, 1);
   public static final Method getProcedureBindingMethod = typeSymbol.addMethod("getProcedure", Type.typeArray0, typeProcedure, 1);
   public static final Method getSymbolProcedureMethod = typeLanguage.getDeclaredMethod("getSymbolProcedure", 1);
   public static final Method getSymbolValueMethod = typeLanguage.getDeclaredMethod("getSymbolValue", 1);
   public static boolean inlineOk;
   public static final Type[] int1Args = new Type[]{Type.intType};
   public static ClassType javaStringType = typeString;
   static Method makeListMethod;
   public static int moduleStatic = 0;
   public static Field noArgsField;
   public static final ArrayType objArrayType = ArrayType.make((Type)typeObject);
   public static Options options = new Options();
   public static Field pcCallContextField;
   public static Field procCallContextField;
   public static ClassType scmBooleanType = ClassType.make("java.lang.Boolean");
   public static ClassType scmKeywordType = ClassType.make("gnu.expr.Keyword");
   public static ClassType scmListType = ClassType.make("gnu.lists.LList");
   public static ClassType scmSequenceType = ClassType.make("gnu.lists.Sequence");
   static final Method setNameMethod = typeProcedure.getDeclaredMethod("setName", 1);
   public static final Type[] string1Arg = new Type[]{javaStringType};
   public static final Type[] sym1Arg = string1Arg;
   public static final Field trueConstant = scmBooleanType.getDeclaredField("TRUE");
   public static ClassType typeApplet;
   public static ClassType typeCallContext;
   public static ClassType typeClass = Type.javalangClassType;
   public static ClassType typeClassType = ClassType.make("gnu.bytecode.ClassType");
   public static final ClassType typeConsumer;
   public static ClassType typeEnvironment = ClassType.make("gnu.mapping.Environment");
   public static ClassType typeLanguage = ClassType.make("gnu.expr.Language");
   public static ClassType typeLocation = ClassType.make("gnu.mapping.Location");
   public static ClassType typeMethodProc;
   public static ClassType typeModuleBody;
   public static ClassType typeModuleMethod;
   public static ClassType typeModuleWithContext;
   public static ClassType typeObject = Type.objectType;
   public static ClassType typeObjectType = ClassType.make("gnu.bytecode.ObjectType");
   public static ClassType typePair = ClassType.make("gnu.lists.Pair");
   public static ClassType typeProcedure = ClassType.make("gnu.mapping.Procedure");
   public static ClassType typeProcedure0;
   public static ClassType typeProcedure1;
   public static ClassType typeProcedure2;
   public static ClassType typeProcedure3;
   public static ClassType typeProcedure4;
   public static ClassType[] typeProcedureArray;
   public static ClassType typeProcedureN;
   public static ClassType typeRunnable = ClassType.make("java.lang.Runnable");
   public static ClassType typeServlet;
   public static ClassType typeString = ClassType.make("java.lang.String");
   public static ClassType typeSymbol = ClassType.make("gnu.mapping.Symbol");
   public static ClassType typeType = ClassType.make("gnu.bytecode.Type");
   public static ClassType typeValues;
   public static Options.OptionInfo warnAsError = options.add("warn-as-error", 1, Boolean.FALSE, "Make all warnings into errors");
   public static Options.OptionInfo warnInvokeUnknownMethod = options.add("warn-invoke-unknown-method", 1, warnUnknownMember, "warn if invoke calls an unknown method (subsumed by warn-unknown-member)");
   public static Options.OptionInfo warnUndefinedVariable = options.add("warn-undefined-variable", 1, Boolean.TRUE, "warn if no compiler-visible binding for a variable");
   public static Options.OptionInfo warnUnknownMember = options.add("warn-unknown-member", 1, Boolean.TRUE, "warn if referencing an unknown method or field");
   Variable callContextVar;
   Variable callContextVarForInit;
   public String classPrefix;
   ClassType[] classes;
   Initializer clinitChain;
   Method clinitMethod;
   public ClassType curClass;
   public LambdaExp curLambda;
   public Options currentOptions;
   protected ScopeExp current_scope;
   public boolean explicit;
   public Stack exprStack;
   Method forNameHelper;
   SwitchState fswitch;
   Field fswitchIndex;
   public boolean generateMain;
   public boolean immediate;
   private int keyUninitialized;
   int langOptions;
   protected Language language;
   public Lexer lexer;
   public NameLookup lexical;
   LitTable litTable;
   ArrayClassLoader loader;
   int localFieldIndex;
   public ClassType mainClass;
   public ModuleExp mainLambda;
   int maxSelectorValue;
   protected SourceMessages messages;
   public Method method;
   int method_counter;
   public ModuleInfo minfo;
   public ClassType moduleClass;
   Field moduleInstanceMainField;
   Variable moduleInstanceVar;
   public boolean mustCompile;
   private Compilation nextUninitialized;
   int numClasses;
   boolean pedantic;
   public Stack pendingImports;
   private int state;
   public Variable thisDecl;


   static {
      ClassType var0 = typeSymbol;
      ClassType var1 = Type.objectType;
      ClassType var2 = typeEnvironment;
      ClassType var3 = typeLocation;
      getLocation2EnvironmentMethod = var2.addMethod("getLocation", new Type[]{var0, var1}, var3, 17);
      ArrayType var6 = objArrayType;
      PrimType var7 = Type.intType;
      var2 = scmListType;
      var3 = scmListType;
      makeListMethod = var2.addMethod("makeList", new Type[]{var6, var7}, var3, 9);
      getCurrentEnvironmentMethod = typeEnvironment.addMethod("getCurrent", Type.typeArray0, typeEnvironment, 9);
      apply0args = Type.typeArray0;
      apply1args = new Type[]{typeObject};
      apply2args = new Type[]{typeObject, typeObject};
      applyNargs = new Type[]{objArrayType};
      apply0method = typeProcedure.addMethod("apply0", apply0args, typeObject, 17);
      apply1method = typeProcedure.addMethod("apply1", apply1args, typeObject, 1);
      apply2method = typeProcedure.addMethod("apply2", apply2args, typeObject, 1);
      var0 = typeObject;
      var1 = typeObject;
      var2 = typeObject;
      var3 = typeProcedure;
      ClassType var4 = typeObject;
      apply3method = var3.addMethod("apply3", new Type[]{var0, var1, var2}, var4, 1);
      var0 = typeObject;
      var1 = typeObject;
      var2 = typeObject;
      var3 = typeObject;
      var4 = typeProcedure;
      ClassType var5 = typeObject;
      apply4method = var4.addMethod("apply4", new Type[]{var0, var1, var2, var3}, var5, 1);
      applyNmethod = typeProcedure.addMethod("applyN", applyNargs, typeObject, 1);
      var0 = typeProcedure;
      var7 = Type.intType;
      var2 = typeProcedure;
      PrimType var8 = Type.voidType;
      checkArgCountMethod = var2.addMethod("checkArgCount", new Type[]{var0, var7}, var8, 9);
      applymethods = new Method[]{apply0method, apply1method, apply2method, apply3method, apply4method, applyNmethod};
      typeProcedure0 = ClassType.make("gnu.mapping.Procedure0");
      typeProcedure1 = ClassType.make("gnu.mapping.Procedure1");
      typeProcedure2 = ClassType.make("gnu.mapping.Procedure2");
      typeProcedure3 = ClassType.make("gnu.mapping.Procedure3");
      typeProcedure4 = ClassType.make("gnu.mapping.Procedure4");
      typeProcedureN = ClassType.make("gnu.mapping.ProcedureN");
      typeModuleBody = ClassType.make("gnu.expr.ModuleBody");
      typeModuleWithContext = ClassType.make("gnu.expr.ModuleWithContext");
      typeApplet = ClassType.make("java.applet.Applet");
      typeServlet = ClassType.make("gnu.kawa.servlet.KawaServlet");
      typeCallContext = ClassType.make("gnu.mapping.CallContext");
      typeConsumer = ClassType.make("gnu.lists.Consumer");
      getCallContextInstanceMethod = typeCallContext.getDeclaredMethod("getInstance", 0);
      typeValues = ClassType.make("gnu.mapping.Values");
      noArgsField = typeValues.getDeclaredField("noArgs");
      pcCallContextField = typeCallContext.getDeclaredField("pc");
      typeMethodProc = ClassType.make("gnu.mapping.MethodProc");
      typeModuleMethod = ClassType.make("gnu.expr.ModuleMethod");
      argsCallContextField = typeCallContext.getDeclaredField("values");
      procCallContextField = typeCallContext.getDeclaredField("proc");
      applyCpsArgs = new Type[]{typeCallContext};
      applyCpsMethod = typeProcedure.addMethod("apply", applyCpsArgs, Type.voidType, 1);
      typeProcedureArray = new ClassType[]{typeProcedure0, typeProcedure1, typeProcedure2, typeProcedure3, typeProcedure4};
      generateMainDefault = false;
      inlineOk = true;
      classPrefixDefault = "";
      emitSourceDebugExtAttr = true;
      current = new InheritableThreadLocal();
   }

   public Compilation(Language var1, SourceMessages var2, NameLookup var3) {
      this.mustCompile = ModuleExp.alwaysCompile;
      this.currentOptions = new Options(options);
      this.generateMain = generateMainDefault;
      this.classPrefix = classPrefixDefault;
      this.language = var1;
      this.messages = var2;
      this.lexical = var3;
   }

   private void checkLoop() {
      if(((LambdaExp)this.current_scope).getName() != "%do%loop") {
         throw new Error("internal error - bad loop state");
      }
   }

   public static char demangle2(char var0, char var1) {
      char var2 = 37;
      switch(var0 << 16 | var1) {
      case 4259949:
         return '&';
      case 4259956:
         return '@';
      case 4391020:
         return ':';
      case 4391021:
         return ',';
      case 4456561:
         return '\"';
      case 4456564:
         return '.';
      case 4522097:
         return '=';
      case 4522104:
         return '!';
      case 4653170:
         return '>';
      case 4980802:
         return '[';
      case 4980803:
         return '{';
      case 4980816:
         return '(';
      case 4980851:
         return '<';
      case 5046382:
         return '-';
      case 5111917:
         return '#';
      case 5242988:
         return '+';
      case 5308533:
         return '?';
      case 5374018:
         return ']';
      case 5374019:
         return '}';
      case 5374032:
         return ')';
      case 5439555:
         return ';';
      case 5439596:
         return '/';
      case 5439601:
         return '\\';
      case 5439604:
         return '*';
      case 5505132:
         return '~';
      case 5570672:
         return '^';
      case 5636162:
         return '|';
      default:
         var2 = '\uffff';
      case 5046371:
      case 5242979:
         return var2;
      }
   }

   public static String demangleName(String var0) {
      return demangleName(var0, false);
   }

   public static String demangleName(String var0, boolean var1) {
      StringBuffer var6 = new StringBuffer();
      int var15 = var0.length();
      boolean var7 = false;
      boolean var12 = false;
      boolean var9 = false;

      for(int var8 = 0; var8 < var15; ++var8) {
         char var3 = var0.charAt(var8);
         char var2 = var3;
         boolean var10 = var9;
         if(var9) {
            var2 = var3;
            var10 = var9;
            if(!var1) {
               var2 = Character.toLowerCase(var3);
               var10 = false;
            }
         }

         boolean var11;
         if(!var1 && var2 == 105 && var8 == 0 && var15 > 2 && var0.charAt(var8 + 1) == 115) {
            var3 = var0.charAt(var8 + 2);
            if(!Character.isLowerCase(var3)) {
               var11 = true;
               boolean var13 = true;
               int var14 = var8 + 1;
               if(!Character.isUpperCase(var3)) {
                  var9 = var10;
                  var8 = var14;
                  var7 = var11;
                  var12 = var13;
                  if(!Character.isTitleCase(var3)) {
                     continue;
                  }
               }

               var6.append(Character.toLowerCase(var3));
               var8 = var14 + 1;
               var12 = var13;
               var7 = var11;
               var9 = var10;
               continue;
            }
         }

         if(var2 == 36 && var8 + 2 < var15) {
            char var4 = var0.charAt(var8 + 1);
            char var5 = var0.charAt(var8 + 2);
            var3 = demangle2(var4, var5);
            if(var3 != '\uffff') {
               var6.append(var3);
               var8 += 2;
               var7 = true;
               var9 = true;
               continue;
            }

            var3 = var2;
            var11 = var7;
            if(var4 == 84) {
               var3 = var2;
               var11 = var7;
               if(var5 == 111) {
                  var3 = var2;
                  var11 = var7;
                  if(var8 + 3 < var15) {
                     var3 = var2;
                     var11 = var7;
                     if(var0.charAt(var8 + 3) == 36) {
                        var6.append("->");
                        var8 += 3;
                        var7 = true;
                        var9 = true;
                        continue;
                     }
                  }
               }
            }
         } else {
            var3 = var2;
            var11 = var7;
            if(!var1) {
               var3 = var2;
               var11 = var7;
               if(var8 > 1) {
                  label57: {
                     if(!Character.isUpperCase(var2)) {
                        var3 = var2;
                        var11 = var7;
                        if(!Character.isTitleCase(var2)) {
                           break label57;
                        }
                     }

                     var3 = var2;
                     var11 = var7;
                     if(Character.isLowerCase(var0.charAt(var8 - 1))) {
                        var6.append('-');
                        var11 = true;
                        var3 = Character.toLowerCase(var2);
                     }
                  }
               }
            }
         }

         var6.append(var3);
         var9 = var10;
         var7 = var11;
      }

      if(var12) {
         var6.append('?');
      }

      if(var7) {
         var0 = var6.toString();
      }

      return var0;
   }

   private void dumpInitializers(Initializer var1) {
      for(var1 = Initializer.reverse(var1); var1 != null; var1 = var1.next) {
         var1.emit(this);
      }

   }

   public static Compilation findForImmediateLiterals(int param0) {
      // $FF: Couldn't be decompiled
   }

   public static final Method getConstructor(ClassType var0, LambdaExp var1) {
      Method var2 = var0.getDeclaredMethod("<init>", 0);
      if(var2 != null) {
         return var2;
      } else {
         Type[] var3;
         if(var1 instanceof ClassExp && var1.staticLinkField != null) {
            Type[] var4 = new Type[]{var1.staticLinkField.getType()};
            var3 = var4;
         } else {
            var3 = apply0args;
         }

         return var0.addMethod("<init>", 1, var3, Type.voidType);
      }
   }

   public static Compilation getCurrent() {
      return (Compilation)current.get();
   }

   public static boolean isValidJavaName(String var0) {
      int var1 = var0.length();
      if(var1 != 0 && Character.isJavaIdentifierStart(var0.charAt(0))) {
         int var2;
         do {
            var2 = var1 - 1;
            if(var2 <= 0) {
               return true;
            }

            var1 = var2;
         } while(Character.isJavaIdentifierPart(var0.charAt(var2)));

         return false;
      } else {
         return false;
      }
   }

   public static ApplyExp makeCoercion(Expression var0, Expression var1) {
      return new ApplyExp(new QuoteExp(Convert.getInstance()), new Expression[]{var1, var0});
   }

   public static Expression makeCoercion(Expression var0, Type var1) {
      return makeCoercion(var0, (Expression)(new QuoteExp(var1)));
   }

   public static String mangleName(String var0) {
      return mangleName(var0, -1);
   }

   public static String mangleName(String var0, int var1) {
      boolean var9;
      if(var1 >= 0) {
         var9 = true;
      } else {
         var9 = false;
      }

      int var10 = var0.length();
      String var4;
      if(var10 == 6 && var0.equals("*init*")) {
         var4 = "<init>";
      } else {
         StringBuffer var5 = new StringBuffer(var10);
         boolean var8 = false;

         boolean var7;
         for(int var6 = 0; var6 < var10; var8 = var7) {
            char var3 = var0.charAt(var6);
            char var2 = var3;
            var7 = var8;
            if(var8) {
               var2 = Character.toTitleCase(var3);
               var7 = false;
            }

            if(Character.isDigit(var2)) {
               if(var6 == 0) {
                  var5.append("$N");
               }

               var5.append(var2);
            } else if(!Character.isLetter(var2) && var2 != 95) {
               if(var2 == 36) {
                  if(var1 > 1) {
                     var4 = "$$";
                  } else {
                     var4 = "$";
                  }

                  var5.append(var4);
               } else {
                  int var12;
                  switch(var2) {
                  case 33:
                     var5.append("$Ex");
                     var12 = var6;
                     break;
                  case 34:
                     var5.append("$Dq");
                     var12 = var6;
                     break;
                  case 35:
                     var5.append("$Nm");
                     var12 = var6;
                     break;
                  case 37:
                     var5.append("$Pc");
                     var12 = var6;
                     break;
                  case 38:
                     var5.append("$Am");
                     var12 = var6;
                     break;
                  case 39:
                     var5.append("$Sq");
                     var12 = var6;
                     break;
                  case 40:
                     var5.append("$LP");
                     var12 = var6;
                     break;
                  case 41:
                     var5.append("$RP");
                     var12 = var6;
                     break;
                  case 42:
                     var5.append("$St");
                     var12 = var6;
                     break;
                  case 43:
                     var5.append("$Pl");
                     var12 = var6;
                     break;
                  case 44:
                     var5.append("$Cm");
                     var12 = var6;
                     break;
                  case 45:
                     if(var9) {
                        var5.append("$Mn");
                        var12 = var6;
                     } else {
                        if(var6 + 1 < var10) {
                           var2 = var0.charAt(var6 + 1);
                        } else {
                           var2 = 0;
                        }

                        if(var2 == 62) {
                           var5.append("$To$");
                           var12 = var6 + 1;
                        } else {
                           var12 = var6;
                           if(!Character.isLowerCase(var2)) {
                              var5.append("$Mn");
                              var12 = var6;
                           }
                        }
                     }
                     break;
                  case 46:
                     var5.append("$Dt");
                     var12 = var6;
                     break;
                  case 47:
                     var5.append("$Sl");
                     var12 = var6;
                     break;
                  case 58:
                     var5.append("$Cl");
                     var12 = var6;
                     break;
                  case 59:
                     var5.append("$SC");
                     var12 = var6;
                     break;
                  case 60:
                     var5.append("$Ls");
                     var12 = var6;
                     break;
                  case 61:
                     var5.append("$Eq");
                     var12 = var6;
                     break;
                  case 62:
                     var5.append("$Gr");
                     var12 = var6;
                     break;
                  case 63:
                     if(var5.length() > 0) {
                        var2 = var5.charAt(0);
                     } else {
                        var2 = 0;
                     }

                     if(!var9 && var6 + 1 == var10 && Character.isLowerCase(var2)) {
                        var5.setCharAt(0, Character.toTitleCase(var2));
                        var5.insert(0, "is");
                        var12 = var6;
                     } else {
                        var5.append("$Qu");
                        var12 = var6;
                     }
                     break;
                  case 64:
                     var5.append("$At");
                     var12 = var6;
                     break;
                  case 91:
                     var5.append("$LB");
                     var12 = var6;
                     break;
                  case 93:
                     var5.append("$RB");
                     var12 = var6;
                     break;
                  case 94:
                     var5.append("$Up");
                     var12 = var6;
                     break;
                  case 123:
                     var5.append("$LC");
                     var12 = var6;
                     break;
                  case 124:
                     var5.append("$VB");
                     var12 = var6;
                     break;
                  case 125:
                     var5.append("$RC");
                     var12 = var6;
                     break;
                  case 126:
                     var5.append("$Tl");
                     var12 = var6;
                     break;
                  default:
                     var5.append('$');
                     var5.append(Character.forDigit(var2 >> 12 & 15, 16));
                     var5.append(Character.forDigit(var2 >> 8 & 15, 16));
                     var5.append(Character.forDigit(var2 >> 4 & 15, 16));
                     var5.append(Character.forDigit(var2 & 15, 16));
                     var12 = var6;
                  }

                  var6 = var12;
                  if(!var9) {
                     var7 = true;
                     var6 = var12;
                  }
               }
            } else {
               var5.append(var2);
            }

            ++var6;
         }

         String var11 = var5.toString();
         var4 = var0;
         if(!var11.equals(var0)) {
            return var11;
         }
      }

      return var4;
   }

   public static String mangleName(String var0, boolean var1) {
      byte var2;
      if(var1) {
         var2 = 1;
      } else {
         var2 = -1;
      }

      return mangleName(var0, var2);
   }

   public static String mangleNameIfNeeded(String var0) {
      return var0 != null && !isValidJavaName(var0)?mangleName(var0, 0):var0;
   }

   public static String mangleURI(String var0) {
      boolean var5;
      if(var0.indexOf(47) >= 0) {
         var5 = true;
      } else {
         var5 = false;
      }

      int var6 = var0.length();
      if(var6 > 6 && var0.startsWith("class:")) {
         return var0.substring(6);
      } else {
         String var1;
         int var4;
         boolean var7;
         if(var6 > 5 && var0.charAt(4) == 58 && var0.substring(0, 4).equalsIgnoreCase("http")) {
            var1 = var0.substring(5);
            var4 = var6 - 5;
            var7 = true;
         } else {
            var7 = var5;
            var4 = var6;
            var1 = var0;
            if(var6 > 4) {
               var7 = var5;
               var4 = var6;
               var1 = var0;
               if(var0.charAt(3) == 58) {
                  var7 = var5;
                  var4 = var6;
                  var1 = var0;
                  if(var0.substring(0, 3).equalsIgnoreCase("uri")) {
                     var1 = var0.substring(4);
                     var4 = var6 - 4;
                     var7 = var5;
                  }
               }
            }
         }

         int var8 = 0;
         StringBuffer var3 = new StringBuffer();

         while(true) {
            int var11 = var1.indexOf(47, var8);
            int var13;
            if(var11 < 0) {
               var13 = var4;
            } else {
               var13 = var11;
            }

            boolean var9;
            if(var3.length() == 0) {
               var9 = true;
            } else {
               var9 = false;
            }

            if(var9 && var7) {
               String var2 = var1.substring(var8, var13);
               var0 = var2;
               if(var13 - var8 > 4) {
                  var0 = var2;
                  if(var2.startsWith("www.")) {
                     var0 = var2.substring(4);
                  }
               }

               putURLWords(var0, var3);
               var0 = var1;
               var6 = var4;
            } else {
               var6 = var4;
               var0 = var1;
               if(var8 != var13) {
                  if(!var9) {
                     var3.append('.');
                  }

                  int var10 = var13;
                  var6 = var4;
                  var0 = var1;
                  if(var13 == var4) {
                     int var12 = var1.lastIndexOf(46, var4);
                     var10 = var13;
                     var6 = var4;
                     var0 = var1;
                     if(var12 > var8 + 1) {
                        var10 = var13;
                        var6 = var4;
                        var0 = var1;
                        if(!var9) {
                           label67: {
                              int var14 = var4 - var12;
                              if(var14 > 4) {
                                 var10 = var13;
                                 var6 = var4;
                                 var0 = var1;
                                 if(var14 != 5) {
                                    break label67;
                                 }

                                 var10 = var13;
                                 var6 = var4;
                                 var0 = var1;
                                 if(!var1.endsWith("html")) {
                                    break label67;
                                 }
                              }

                              var6 = var4 - var14;
                              var10 = var6;
                              var0 = var1.substring(0, var6);
                           }
                        }
                     }
                  }

                  var3.append(var0.substring(var8, var10));
               }
            }

            if(var11 < 0) {
               return var3.toString();
            }

            var8 = var11 + 1;
            var4 = var6;
            var1 = var0;
         }
      }
   }

   private static void putURLWords(String var0, StringBuffer var1) {
      int var3 = var0.indexOf(46);
      String var2 = var0;
      if(var3 > 0) {
         putURLWords(var0.substring(var3 + 1), var1);
         var1.append('.');
         var2 = var0.substring(0, var3);
      }

      var1.append(var2);
   }

   private void registerClass(ClassType var1) {
      if(this.classes == null) {
         this.classes = new ClassType[20];
      } else if(this.numClasses >= this.classes.length) {
         ClassType[] var2 = new ClassType[this.classes.length * 2];
         System.arraycopy(this.classes, 0, var2, 0, this.numClasses);
         this.classes = var2;
      }

      byte var3;
      if(var1.isInterface()) {
         var3 = 1;
      } else {
         var3 = 33;
      }

      var1.addModifiers(var3);
      ClassType var5 = var1;
      if(var1 == this.mainClass) {
         var5 = var1;
         if(this.numClasses > 0) {
            var5 = this.classes[0];
            this.classes[0] = this.mainClass;
         }
      }

      ClassType[] var4 = this.classes;
      int var6 = this.numClasses;
      this.numClasses = var6 + 1;
      var4[var6] = var5;
   }

   public static int registerForImmediateLiterals(Compilation param0) {
      // $FF: Couldn't be decompiled
   }

   public static void restoreCurrent(Compilation var0) {
      current.set(var0);
   }

   public static void setCurrent(Compilation var0) {
      current.set(var0);
   }

   public static Compilation setSaveCurrent(Compilation var0) {
      Compilation var1 = (Compilation)current.get();
      current.set(var0);
      return var1;
   }

   public static void setupLiterals(int param0) {
      // $FF: Couldn't be decompiled
   }

   private Method startClassInit() {
      this.method = this.curClass.addMethod("<clinit>", apply0args, Type.voidType, 9);
      CodeAttr var1 = this.method.startCode();
      if(this.generateMain || this.generatingApplet() || this.generatingServlet()) {
         Method var2 = ((ClassType)Type.make(this.getLanguage().getClass())).getDeclaredMethod("registerEnvironment", 0);
         if(var2 != null) {
            var1.emitInvokeStatic(var2);
         }
      }

      return this.method;
   }

   private void varArgsToArray(LambdaExp var1, int var2, Variable var3, Type var4, Variable var5) {
      CodeAttr var6 = this.getCode();
      Type var7 = ((ArrayType)var4).getComponentType();
      boolean var9;
      if(!"java.lang.Object".equals(var7.getName())) {
         var9 = true;
      } else {
         var9 = false;
      }

      if(var5 != null && !var9) {
         var6.emitLoad(var5);
         var6.emitPushInt(var2);
         var6.emitInvokeVirtual(typeCallContext.getDeclaredMethod("getRestArgsArray", 1));
      } else if(var2 == 0 && !var9) {
         var6.emitLoad(var6.getArg(2));
      } else {
         var6.pushScope();
         Variable var11 = var3;
         if(var3 == null) {
            var11 = var6.addLocal(Type.intType);
            if(var5 != null) {
               var6.emitLoad(var5);
               var6.emitInvoke(typeCallContext.getDeclaredMethod("getArgCount", 0));
            } else {
               var6.emitLoad(var6.getArg(2));
               var6.emitArrayLength();
            }

            if(var2 != 0) {
               var6.emitPushInt(var2);
               var6.emitSub(Type.intType);
            }

            var6.emitStore(var11);
         }

         var6.emitLoad(var11);
         var6.emitNewArray(var7.getImplementationType());
         Label var10 = new Label(var6);
         Label var8 = new Label(var6);
         var8.setTypes((CodeAttr)var6);
         var6.emitGoto(var10);
         var8.define(var6);
         var6.emitDup(1);
         var6.emitLoad(var11);
         if(var5 != null) {
            var6.emitLoad(var5);
         } else {
            var6.emitLoad(var6.getArg(2));
         }

         var6.emitLoad(var11);
         if(var2 != 0) {
            var6.emitPushInt(var2);
            var6.emitAdd(Type.intType);
         }

         if(var5 != null) {
            var6.emitInvokeVirtual(typeCallContext.getDeclaredMethod("getArgAsObject", 1));
         } else {
            var6.emitArrayLoad(Type.objectType);
         }

         if(var9) {
            CheckedTarget.emitCheckedCoerce(this, var1, var1.getName(), 0, var7, (Variable)null);
         }

         var6.emitArrayStore(var7);
         var10.define(var6);
         var6.emitInc(var11, (short)-1);
         var6.emitLoad(var11);
         var6.emitGotoIfIntGeZero(var8);
         var6.popScope();
      }
   }

   public void addClass(ClassType var1) {
      if(this.mainLambda.filename != null) {
         if(emitSourceDebugExtAttr) {
            var1.setStratum(this.getLanguage().getName());
         }

         var1.setSourceFile(this.mainLambda.filename);
      }

      this.registerClass(var1);
      var1.setClassfileVersion(defaultClassFileVersion);
   }

   public void addMainClass(ModuleExp var1) {
      this.mainClass = var1.classFor(this);
      ClassType var4 = this.mainClass;
      ClassType[] var2 = var1.getInterfaces();
      if(var2 != null) {
         var4.setInterfaces(var2);
      }

      ClassType var3 = var1.getSuperType();
      ClassType var5 = var3;
      if(var3 == null) {
         if(this.generatingApplet()) {
            var5 = typeApplet;
         } else if(this.generatingServlet()) {
            var5 = typeServlet;
         } else {
            var5 = this.getModuleType();
         }
      }

      if(this.makeRunnable()) {
         var4.addInterface(typeRunnable);
      }

      var4.setSuper((ClassType)var5);
      var1.type = var4;
      this.addClass(var4);
      getConstructor(this.mainClass, var1);
   }

   public Field allocLocalField(Type var1, String var2) {
      String var3 = var2;
      if(var2 == null) {
         StringBuilder var5 = (new StringBuilder()).append("tmp_");
         int var4 = this.localFieldIndex + 1;
         this.localFieldIndex = var4;
         var3 = var5.append(var4).toString();
      }

      return this.curClass.addField(var3, var1, 0);
   }

   void callInitMethods(ClassType var1, Vector var2) {
      if(var1 != null) {
         String var3 = var1.getName();
         if(!"java.lang.Object".equals(var3)) {
            int var4 = var2.size();

            while(true) {
               int var5 = var4 - 1;
               if(var5 < 0) {
                  var2.addElement(var1);
                  ClassType[] var9 = var1.getInterfaces();
                  if(var9 != null) {
                     var5 = var9.length;

                     for(var4 = 0; var4 < var5; ++var4) {
                        this.callInitMethods(var9[var4], var2);
                     }
                  }

                  byte var10 = 1;
                  if(var1.isInterface()) {
                     if(var1 instanceof PairClassType) {
                        var1 = ((PairClassType)var1).instanceType;
                     } else {
                        try {
                           var1 = (ClassType)Type.make(Class.forName(var1.getName() + "$class"));
                        } catch (Throwable var6) {
                           return;
                        }
                     }
                  } else {
                     var10 = 0;
                  }

                  Method var7 = var1.getDeclaredMethod("$finit$", var10);
                  if(var7 != null) {
                     CodeAttr var8 = this.getCode();
                     var8.emitPushThis();
                     var8.emitInvoke(var7);
                     return;
                  }
                  break;
               }

               var4 = var5;
               if(((ClassType)var2.elementAt(var5)).getName() == var3) {
                  return;
               }
            }
         }
      }

   }

   public void cleanupAfterCompilation() {
      for(int var1 = 0; var1 < this.numClasses; ++var1) {
         this.classes[var1].cleanupAfterCompilation();
      }

      this.classes = null;
      this.minfo.comp = null;
      if(this.minfo.exp != null) {
         this.minfo.exp.body = null;
      }

      this.mainLambda.body = null;
      this.mainLambda = null;
      if(!this.immediate) {
         this.litTable = null;
      }

   }

   public void compileConstant(Object var1) {
      CodeAttr var2 = this.getCode();
      if(var1 == null) {
         var2.emitPushNull();
      } else if(var1 instanceof String && !this.immediate) {
         var2.emitPushString((String)var1);
      } else {
         var2.emitGetStatic(this.compileConstantToField(var1));
      }
   }

   public void compileConstant(Object param1, Target param2) {
      // $FF: Couldn't be decompiled
   }

   public Field compileConstantToField(Object var1) {
      Literal var2 = this.litTable.findLiteral(var1);
      if(var2.field == null) {
         var2.assign(this.litTable);
      }

      return var2.field;
   }

   public void compileToArchive(ModuleExp var1, String var2) throws IOException {
      boolean var5;
      if(var2.endsWith(".zip")) {
         var5 = false;
      } else if(var2.endsWith(".jar")) {
         var5 = true;
      } else {
         var2 = var2 + ".zip";
         var5 = false;
      }

      this.process(12);
      File var6 = new File(var2);
      if(var6.exists()) {
         var6.delete();
      }

      Object var7;
      if(var5) {
         var7 = new JarOutputStream(new FileOutputStream(var6));
      } else {
         var7 = new ZipOutputStream(new FileOutputStream(var6));
      }

      byte[][] var8 = new byte[this.numClasses][];
      CRC32 var3 = new CRC32();

      for(int var10 = 0; var10 < this.numClasses; ++var10) {
         ClassType var4 = this.classes[var10];
         var8[var10] = var4.writeToArray();
         ZipEntry var9 = new ZipEntry(var4.getName().replace('.', '/') + ".class");
         var9.setSize((long)var8[var10].length);
         var3.reset();
         var3.update(var8[var10], 0, var8[var10].length);
         var9.setCrc(var3.getValue());
         ((ZipOutputStream)var7).putNextEntry(var9);
         ((ZipOutputStream)var7).write(var8[var10]);
      }

      ((ZipOutputStream)var7).close();
   }

   public LambdaExp currentLambda() {
      return this.current_scope.currentLambda();
   }

   public ModuleExp currentModule() {
      return this.current_scope.currentModule();
   }

   public ScopeExp currentScope() {
      return this.current_scope;
   }

   public void error(char var1, Declaration var2, String var3, String var4) {
      this.error(var1, (String)(var3 + var2.getName() + var4), (String)null, (Declaration)var2);
   }

   public void error(char var1, String var2) {
      char var3 = var1;
      if(var1 == 119) {
         var3 = var1;
         if(this.warnAsError()) {
            var3 = 101;
         }
      }

      this.messages.error(var3, (SourceLocator)this, (String)var2);
   }

   public void error(char var1, String var2, SourceLocator var3) {
      int var6;
      int var7;
      String var9;
      label17: {
         String var5 = var3.getFileName();
         int var8 = var3.getLineNumber();
         var7 = var3.getColumnNumber();
         if(var5 != null) {
            var9 = var5;
            var6 = var8;
            if(var8 > 0) {
               break label17;
            }
         }

         var9 = this.getFileName();
         var6 = this.getLineNumber();
         var7 = this.getColumnNumber();
      }

      char var4 = var1;
      if(var1 == 119) {
         var4 = var1;
         if(this.warnAsError()) {
            var4 = 101;
         }
      }

      this.messages.error(var4, var9, var6, var7, var2);
   }

   public void error(char var1, String var2, String var3, Declaration var4) {
      char var5 = var1;
      if(var1 == 119) {
         var5 = var1;
         if(this.warnAsError()) {
            var5 = 101;
         }
      }

      String var6 = this.getFileName();
      int var7 = this.getLineNumber();
      int var8 = this.getColumnNumber();
      int var9 = var4.getLineNumber();
      if(var9 > 0) {
         var6 = var4.getFileName();
         var7 = var9;
         var8 = var4.getColumnNumber();
      }

      this.messages.error(var5, var6, var7, var8, var2, var3);
   }

   public ClassType findNamedClass(String var1) {
      for(int var2 = 0; var2 < this.numClasses; ++var2) {
         if(var1.equals(this.classes[var2].getName())) {
            return this.classes[var2];
         }
      }

      return null;
   }

   public void freeLocalField(Field var1) {
   }

   public void generateApplyMethodsWithContext(LambdaExp var1) {
      int var17;
      if(var1.applyMethods == null) {
         var17 = 0;
      } else {
         var17 = var1.applyMethods.size();
      }

      if(var17 != 0) {
         ClassType var5 = this.curClass;
         this.curClass = var1.getHeapFrameType();
         if(!this.curClass.getSuperclass().isSubtype(typeModuleWithContext)) {
            this.curClass = this.moduleClass;
         }

         ClassType var2 = typeModuleMethod;
         Method var6 = this.method;
         var2 = typeCallContext;
         ClassType var3 = this.curClass;
         PrimType var4 = Type.voidType;
         this.method = var3.addMethod("apply", new Type[]{var2}, var4, 1);
         CodeAttr var7 = this.method.startCode();
         Variable var8 = var7.getArg(1);
         var7.emitLoad(var8);
         var7.emitGetField(pcCallContextField);
         SwitchState var9 = var7.startSwitch();

         for(int var18 = 0; var18 < var17; ++var18) {
            LambdaExp var10 = (LambdaExp)var1.applyMethods.elementAt(var18);
            Method[] var11 = var10.primMethods;
            int var24 = var11.length;

            for(int var19 = 0; var19 < var24; ++var19) {
               boolean var20;
               if(var19 == var24 - 1 && (var10.max_args < 0 || var10.max_args >= var10.min_args + var24)) {
                  var20 = true;
               } else {
                  var20 = false;
               }

               var9.addCase(var10.getSelectorValue(this) + var19, var7);
               SourceLocator var12 = this.messages.swapSourceLocator(var10);
               int var16 = var10.getLineNumber();
               if(var16 > 0) {
                  var7.putLineNumber(var10.getFileName(), var16);
               }

               Method var13 = var11[var19];
               Type[] var14 = var13.getParameterTypes();
               int var25 = var10.min_args + var19;
               var2 = null;
               byte var22 = 0;
               Variable var27 = var2;
               if(var19 > 4) {
                  var27 = var2;
                  if(var24 > 1) {
                     var27 = var7.addLocal(Type.intType);
                     var7.emitLoad(var8);
                     var7.emitInvoke(typeCallContext.getDeclaredMethod("getArgCount", 0));
                     if(var10.min_args != 0) {
                        var7.emitPushInt(var10.min_args);
                        var7.emitSub(Type.intType);
                     }

                     var7.emitStore(var27);
                  }
               }

               byte var31;
               if(var13.getStaticFlag()) {
                  var31 = 0;
               } else {
                  var31 = 1;
               }

               byte var21;
               if(var20) {
                  var21 = 2;
               } else {
                  var21 = 1;
               }

               if(var21 + var25 < var14.length) {
                  var21 = 1;
               } else {
                  var21 = 0;
               }

               if(var31 + var21 > 0) {
                  var7.emitPushThis();
                  if(this.curClass == this.moduleClass && this.mainClass != this.moduleClass) {
                     var7.emitGetField(this.moduleInstanceMainField);
                  }
               }

               Declaration var28 = var10.firstDecl();
               Declaration var26 = var28;
               if(var28 != null) {
                  var26 = var28;
                  if(var28.isThisParameter()) {
                     var26 = var28.nextDecl();
                  }
               }

               byte var23 = 0;
               var16 = var22;

               int var33;
               for(int var32 = var23; var32 < var25; var16 = var33) {
                  var33 = var16;
                  if(var27 != null) {
                     var33 = var16;
                     if(var32 >= var10.min_args) {
                        var7.emitLoad(var27);
                        var7.emitIfIntLEqZero();
                        var7.emitLoad(var8);
                        var7.emitInvoke(var11[var32 - var10.min_args]);
                        var7.emitElse();
                        var33 = var16 + 1;
                        var7.emitInc(var27, (short)-1);
                     }
                  }

                  var7.emitLoad(var8);
                  if(var32 <= 4 && !var20 && var10.max_args <= 4) {
                     var7.emitGetField(typeCallContext.getDeclaredField("value" + (var32 + 1)));
                  } else {
                     var7.emitGetField(typeCallContext.getDeclaredField("values"));
                     var7.emitPushInt(var32);
                     var7.emitArrayLoad(Type.objectType);
                  }

                  Type var30 = var26.getType();
                  if(var30 != Type.objectType) {
                     SourceLocator var15 = this.messages.swapSourceLocator(var26);
                     CheckedTarget.emitCheckedCoerce(this, (LambdaExp)var10, var32 + 1, var30);
                     this.messages.swapSourceLocator(var15);
                  }

                  var26 = var26.nextDecl();
                  ++var32;
               }

               if(var20) {
                  Type var29 = var14[var21 + var25];
                  if(var29 instanceof ArrayType) {
                     this.varArgsToArray(var10, var25, var27, var29, var8);
                  } else if("gnu.lists.LList".equals(var29.getName())) {
                     var7.emitLoad(var8);
                     var7.emitPushInt(var25);
                     var7.emitInvokeVirtual(typeCallContext.getDeclaredMethod("getRestArgsList", 1));
                  } else {
                     if(var29 != typeCallContext) {
                        throw new RuntimeException("unsupported #!rest type:" + var29);
                     }

                     var7.emitLoad(var8);
                  }
               }

               var7.emitLoad(var8);
               var7.emitInvoke(var13);

               while(true) {
                  --var16;
                  if(var16 < 0) {
                     if(defaultCallConvention < 2) {
                        Target.pushObject.compileFromStack(this, var10.getReturnType());
                     }

                     this.messages.swapSourceLocator(var12);
                     var7.emitReturn();
                     break;
                  }

                  var7.emitFi();
               }
            }
         }

         var9.addDefault(var7);
         var7.emitInvokeStatic(typeModuleMethod.getDeclaredMethod("applyError", 0));
         var7.emitReturn();
         var9.finish(var7);
         this.method = var6;
         this.curClass = var5;
      }
   }

   public void generateApplyMethodsWithoutContext(LambdaExp var1) {
      int var23;
      if(var1.applyMethods == null) {
         var23 = 0;
      } else {
         var23 = var1.applyMethods.size();
      }

      if(var23 != 0) {
         ClassType var9 = this.curClass;
         this.curClass = var1.getHeapFrameType();
         ClassType var10 = typeModuleMethod;
         if(!this.curClass.getSuperclass().isSubtype(typeModuleBody)) {
            this.curClass = this.moduleClass;
         }

         Method var11 = this.method;
         CodeAttr var3 = null;
         int var19;
         if(defaultCallConvention >= 2) {
            var19 = 5;
         } else {
            var19 = 0;
         }

         for(; var19 < 6; ++var19) {
            boolean var21 = false;
            SwitchState var4 = null;
            String var2 = null;
            Type[] var5 = null;

            boolean var20;
            int var35;
            int var36;
            for(int var24 = 0; var24 < var23; var21 = var20) {
               LambdaExp var12 = (LambdaExp)var1.applyMethods.elementAt(var24);
               Method[] var13 = var12.primMethods;
               int var26 = var13.length;
               if(var12.max_args >= 0 && var12.max_args < var12.min_args + var26) {
                  var20 = false;
               } else {
                  var20 = true;
               }

               boolean var27 = false;
               boolean var25 = false;
               boolean var22;
               int var41;
               if(var19 < 5) {
                  label189: {
                     var41 = var19 - var12.min_args;
                     if(var41 >= 0 && var41 < var26) {
                        var22 = var25;
                        if(var41 != var26 - 1) {
                           break label189;
                        }

                        var22 = var25;
                        if(!var20) {
                           break label189;
                        }
                     }

                     var22 = true;
                  }

                  var26 = 1;
                  var25 = false;
               } else {
                  int var38 = 5 - var12.min_args;
                  var22 = var27;
                  if(var38 > 0) {
                     var22 = var27;
                     if(var26 <= var38) {
                        var22 = var27;
                        if(!var20) {
                           var22 = true;
                        }
                     }
                  }

                  var41 = var26 - 1;
                  var25 = var20;
               }

               if(var22) {
                  var20 = var21;
               } else {
                  var20 = var21;
                  if(!var21) {
                     if(var19 < 5) {
                        String var29 = "apply" + var19;
                        Type[] var31 = new Type[var19 + 1];
                        var35 = var19;

                        while(true) {
                           var5 = var31;
                           var2 = var29;
                           if(var35 <= 0) {
                              break;
                           }

                           var31[var35] = typeObject;
                           --var35;
                        }
                     } else {
                        var2 = "applyN";
                        var5 = new Type[]{null, objArrayType};
                     }

                     var5[0] = var10;
                     ClassType var32 = this.curClass;
                     Object var30;
                     if(defaultCallConvention >= 2) {
                        var30 = Type.voidType;
                     } else {
                        var30 = Type.objectType;
                     }

                     this.method = var32.addMethod(var2, var5, (Type)var30, 1);
                     var3 = this.method.startCode();
                     var3.emitLoad(var3.getArg(1));
                     var3.emitGetField(var10.getField("selector"));
                     var4 = var3.startSwitch();
                     var20 = true;
                  }

                  var4.addCase(var12.getSelectorValue(this), var3);
                  SourceLocator var14 = this.messages.swapSourceLocator(var12);
                  var36 = var12.getLineNumber();
                  if(var36 > 0) {
                     var3.putLineNumber(var12.getFileName(), var36);
                  }

                  Method var15 = var13[var41];
                  Type[] var16 = var15.getParameterTypes();
                  int var28 = var12.min_args + var41;
                  Declaration var6 = null;
                  byte var40 = 0;
                  Variable var7 = var6;
                  if(var19 > 4) {
                     var7 = var6;
                     if(var26 > 1) {
                        var7 = var3.addLocal(Type.intType);
                        var3.emitLoad(var3.getArg(2));
                        var3.emitArrayLength();
                        if(var12.min_args != 0) {
                           var3.emitPushInt(var12.min_args);
                           var3.emitSub(Type.intType);
                        }

                        var3.emitStore(var7);
                     }
                  }

                  byte var37;
                  if(var15.getStaticFlag()) {
                     var37 = 0;
                  } else {
                     var37 = 1;
                  }

                  byte var39;
                  if(var25) {
                     var39 = 1;
                  } else {
                     var39 = 0;
                  }

                  if(var39 + var28 < var16.length) {
                     var39 = 1;
                  } else {
                     var39 = 0;
                  }

                  if(var37 + var39 > 0) {
                     var3.emitPushThis();
                     if(this.curClass == this.moduleClass && this.mainClass != this.moduleClass) {
                        var3.emitGetField(this.moduleInstanceMainField);
                     }
                  }

                  Declaration var8 = var12.firstDecl();
                  var6 = var8;
                  if(var8 != null) {
                     var6 = var8;
                     if(var8.isThisParameter()) {
                        var6 = var8.nextDecl();
                     }
                  }

                  var26 = 0;

                  for(var36 = var40; var26 < var28; var36 = var41) {
                     var41 = var36;
                     if(var7 != null) {
                        var41 = var36;
                        if(var26 >= var12.min_args) {
                           var3.emitLoad(var7);
                           var3.emitIfIntLEqZero();
                           var3.emitInvoke(var13[var26 - var12.min_args]);
                           var3.emitElse();
                           var41 = var36 + 1;
                           var3.emitInc(var7, (short)-1);
                        }
                     }

                     Variable var34 = null;
                     if(var19 <= 4) {
                        var34 = var3.getArg(var26 + 2);
                        var3.emitLoad(var34);
                     } else {
                        var3.emitLoad(var3.getArg(2));
                        var3.emitPushInt(var26);
                        var3.emitArrayLoad(Type.objectType);
                     }

                     Type var17 = var6.getType();
                     if(var17 != Type.objectType) {
                        SourceLocator var18 = this.messages.swapSourceLocator(var6);
                        CheckedTarget.emitCheckedCoerce(this, var12, var26 + 1, var17, var34);
                        this.messages.swapSourceLocator(var18);
                     }

                     var6 = var6.nextDecl();
                     ++var26;
                  }

                  if(var25) {
                     Type var33 = var16[var39 + var28];
                     if(var33 instanceof ArrayType) {
                        this.varArgsToArray(var12, var28, var7, var33, (Variable)null);
                     } else if("gnu.lists.LList".equals(var33.getName())) {
                        var3.emitLoad(var3.getArg(2));
                        var3.emitPushInt(var28);
                        var3.emitInvokeStatic(makeListMethod);
                     } else {
                        if(var33 != typeCallContext) {
                           throw new RuntimeException("unsupported #!rest type:" + var33);
                        }

                        var3.emitLoad(var3.getArg(2));
                     }
                  }

                  var3.emitInvoke(var15);

                  while(true) {
                     --var36;
                     if(var36 < 0) {
                        if(defaultCallConvention < 2) {
                           Target.pushObject.compileFromStack(this, var12.getReturnType());
                        }

                        this.messages.swapSourceLocator(var14);
                        var3.emitReturn();
                        break;
                     }

                     var3.emitFi();
                  }
               }

               ++var24;
            }

            if(var21) {
               var4.addDefault(var3);
               if(defaultCallConvention >= 2) {
                  var3.emitInvokeStatic(typeModuleMethod.getDeclaredMethod("applyError", 0));
               } else {
                  if(var19 > 4) {
                     var35 = 2;
                  } else {
                     var35 = var19 + 1;
                  }

                  for(var36 = 0; var36 < var35 + 1; ++var36) {
                     var3.emitLoad(var3.getArg(var36));
                  }

                  var3.emitInvokeSpecial(typeModuleBody.getDeclaredMethod(var2, var5));
               }

               var3.emitReturn();
               var4.finish(var3);
            }
         }

         this.method = var11;
         this.curClass = var9;
      }
   }

   void generateBytecode() {
      // $FF: Couldn't be decompiled
   }

   public String generateClassName(String var1) {
      String var2 = mangleName(var1, true);
      if(this.mainClass != null) {
         var1 = this.mainClass.getName() + '$' + var2;
      } else {
         var1 = var2;
         if(this.classPrefix != null) {
            var1 = this.classPrefix + var2;
         }
      }

      if(this.findNamedClass(var1) == null) {
         return var1;
      } else {
         int var3 = 0;

         while(true) {
            var2 = var1 + var3;
            if(this.findNamedClass(var2) == null) {
               return var2;
            }

            ++var3;
         }
      }
   }

   public final void generateConstructor(ClassType var1, LambdaExp var2) {
      Method var3 = this.method;
      Variable var4 = this.callContextVar;
      this.callContextVar = null;
      ClassType var5 = this.curClass;
      this.curClass = var1;
      Method var6 = getConstructor(var1, var2);
      var1.constructor = var6;
      this.method = var6;
      CodeAttr var9 = var6.startCode();
      if(var2 instanceof ClassExp && var2.staticLinkField != null) {
         var9.emitPushThis();
         var9.emitLoad(var9.getCurrentScope().getVariable(1));
         var9.emitPutField(var2.staticLinkField);
      }

      ClassExp.invokeDefaultSuperConstructor(var1.getSuperclass(), this, var2);
      if(this.curClass == this.mainClass && this.minfo != null && this.minfo.sourcePath != null) {
         var9.emitPushThis();
         var9.emitInvokeStatic(ClassType.make("gnu.expr.ModuleInfo").getDeclaredMethod("register", 1));
      }

      if(var2 != null && var2.initChain != null) {
         LambdaExp var8 = this.curLambda;
         this.curLambda = new LambdaExp();
         this.curLambda.closureEnv = var9.getArg(0);
         this.curLambda.outer = var8;

         while(true) {
            Initializer var7 = var2.initChain;
            if(var7 == null) {
               this.curLambda = var8;
               break;
            }

            var2.initChain = null;
            this.dumpInitializers(var7);
         }
      }

      if(var2 instanceof ClassExp) {
         this.callInitMethods(((ClassExp)var2).getCompiledClassType(this), new Vector(10));
      }

      var9.emitReturn();
      this.method = var3;
      this.curClass = var5;
      this.callContextVar = var4;
   }

   public final void generateConstructor(LambdaExp var1) {
      this.generateConstructor(var1.getHeapFrameType(), var1);
   }

   public void generateMatchMethods(LambdaExp var1) {
      int var16;
      if(var1.applyMethods == null) {
         var16 = 0;
      } else {
         var16 = var1.applyMethods.size();
      }

      if(var16 != 0) {
         Method var7 = this.method;
         ClassType var8 = this.curClass;
         ClassType var9 = typeModuleMethod;
         this.curClass = var1.getHeapFrameType();
         if(!this.curClass.getSuperclass().isSubtype(typeModuleBody)) {
            this.curClass = this.moduleClass;
         }

         CodeAttr var4 = null;
         int var17 = 0;

         while(var17 <= 5) {
            boolean var19 = false;
            SwitchState var5 = null;
            String var3 = null;
            Type[] var2 = null;
            int var18 = var16;

            while(true) {
               int var21 = var18 - 1;
               int var24;
               if(var21 < 0) {
                  if(var19) {
                     var5.addDefault(var4);
                     if(var17 > 4) {
                        var18 = 2;
                     } else {
                        var18 = var17 + 1;
                     }

                     for(var24 = 0; var24 <= var18 + 1; ++var24) {
                        var4.emitLoad(var4.getArg(var24));
                     }

                     var4.emitInvokeSpecial(typeModuleBody.getDeclaredMethod(var3, var2.length));
                     var4.emitReturn();
                     var5.finish(var4);
                  }

                  ++var17;
                  break;
               }

               LambdaExp var10 = (LambdaExp)var1.applyMethods.elementAt(var21);
               int var23 = var10.primMethods.length;
               boolean var20;
               if(var10.max_args >= 0 && var10.max_args < var10.min_args + var23) {
                  var20 = false;
               } else {
                  var20 = true;
               }

               if(var17 < 5) {
                  int var22 = var17 - var10.min_args;
                  var18 = var21;
                  if(var22 < 0) {
                     continue;
                  }

                  var18 = var21;
                  if(var22 >= var23) {
                     continue;
                  }

                  if(var22 == var23 - 1) {
                     var18 = var21;
                     if(var20) {
                        continue;
                     }
                  }

                  var18 = var22;
               } else {
                  var18 = 5 - var10.min_args;
                  if(var18 > 0 && var23 <= var18) {
                     var18 = var21;
                     if(!var20) {
                        continue;
                     }
                  }

                  var18 = var23 - 1;
               }

               var20 = var19;
               if(!var19) {
                  if(var17 < 5) {
                     var3 = "match" + var17;
                     var2 = new Type[var17 + 2];

                     for(var24 = var17; var24 >= 0; --var24) {
                        var2[var24 + 1] = typeObject;
                     }

                     var2[var17 + 1] = typeCallContext;
                  } else {
                     var3 = "matchN";
                     var2 = new Type[]{null, objArrayType, typeCallContext};
                  }

                  var2[0] = var9;
                  this.method = this.curClass.addMethod(var3, var2, Type.intType, 1);
                  var4 = this.method.startCode();
                  var4.emitLoad(var4.getArg(1));
                  var4.emitGetField(var9.getField("selector"));
                  var5 = var4.startSwitch();
                  var20 = true;
               }

               var5.addCase(var10.getSelectorValue(this), var4);
               var24 = var10.getLineNumber();
               if(var24 > 0) {
                  var4.putLineNumber(var10.getFileName(), var24);
               }

               if(var17 == 5) {
                  var24 = 3;
               } else {
                  var24 = var17 + 2;
               }

               Variable var11 = var4.getArg(var24);
               if(var17 < 5) {
                  Declaration var6 = var10.firstDecl();

                  for(var24 = 1; var24 <= var17; ++var24) {
                     var4.emitLoad(var11);
                     var4.emitLoad(var4.getArg(var24 + 1));
                     Type var12 = var6.getType();
                     if(var12 != Type.objectType) {
                        if(var12 instanceof TypeValue) {
                           Label var13 = new Label(var4);
                           Label var14 = new Label(var4);
                           ConditionalTarget var15 = new ConditionalTarget(var13, var14, this.getLanguage());
                           var4.emitDup();
                           ((TypeValue)var12).emitIsInstance((Variable)null, this, var15);
                           var14.define(var4);
                           var4.emitPushInt(-786432 | var24);
                           var4.emitReturn();
                           var13.define(var4);
                        } else if(var12 instanceof ClassType && var12 != Type.objectType && var12 != Type.toStringType) {
                           var4.emitDup();
                           var12.emitIsInstance(var4);
                           var4.emitIfIntEqZero();
                           var4.emitPushInt(-786432 | var24);
                           var4.emitReturn();
                           var4.emitFi();
                        }
                     }

                     var4.emitPutField(typeCallContext.getField("value" + var24));
                     var6 = var6.nextDecl();
                  }
               } else {
                  var4.emitLoad(var11);
                  var4.emitLoad(var4.getArg(2));
                  var4.emitPutField(typeCallContext.getField("values"));
               }

               var4.emitLoad(var11);
               if(defaultCallConvention < 2) {
                  var4.emitLoad(var4.getArg(1));
               } else {
                  var4.emitLoad(var4.getArg(0));
               }

               var4.emitPutField(procCallContextField);
               var4.emitLoad(var11);
               if(defaultCallConvention >= 2) {
                  var4.emitPushInt(var10.getSelectorValue(this) + var18);
               } else {
                  var4.emitPushInt(var17);
               }

               var4.emitPutField(pcCallContextField);
               var4.emitPushInt(0);
               var4.emitReturn();
               var18 = var21;
               var19 = var20;
            }
         }

         this.method = var7;
         this.curClass = var8;
      }
   }

   public boolean generatingApplet() {
      return (this.langOptions & 16) != 0;
   }

   public boolean generatingServlet() {
      return (this.langOptions & 32) != 0;
   }

   public final boolean getBooleanOption(String var1) {
      return this.currentOptions.getBoolean((String)var1);
   }

   public final boolean getBooleanOption(String var1, boolean var2) {
      return this.currentOptions.getBoolean((String)var1, var2);
   }

   public final CodeAttr getCode() {
      return this.method.getCode();
   }

   public final int getColumnNumber() {
      return this.messages.getColumnNumber();
   }

   public final Method getConstructor(LambdaExp var1) {
      return getConstructor(var1.getHeapFrameType(), var1);
   }

   public final String getFileName() {
      return this.messages.getFileName();
   }

   public Method getForNameHelper() {
      if(this.forNameHelper == null) {
         Method var1 = this.method;
         this.method = this.curClass.addMethod("class$", 9, string1Arg, typeClass);
         this.forNameHelper = this.method;
         CodeAttr var2 = this.method.startCode();
         var2.emitLoad(var2.getArg(0));
         var2.emitPushInt(0);
         var2.emitPushString(this.mainClass.getName());
         var2.emitInvokeStatic(typeClass.getDeclaredMethod("forName", 1));
         var2.emitInvokeVirtual(typeClass.getDeclaredMethod("getClassLoader", 0));
         var2.emitInvokeStatic(typeClass.getDeclaredMethod("forName", 3));
         var2.emitReturn();
         this.method = var1;
      }

      return this.forNameHelper;
   }

   public Language getLanguage() {
      return this.language;
   }

   public final int getLineNumber() {
      return this.messages.getLineNumber();
   }

   public SourceMessages getMessages() {
      return this.messages;
   }

   public final ModuleExp getModule() {
      return this.mainLambda;
   }

   public final ClassType getModuleType() {
      return defaultCallConvention >= 2?typeModuleWithContext:typeModuleBody;
   }

   public String getPublicId() {
      return this.messages.getPublicId();
   }

   public int getState() {
      return this.state;
   }

   public String getSystemId() {
      return this.messages.getSystemId();
   }

   public boolean inlineOk(Expression var1) {
      if(var1 instanceof LambdaExp) {
         LambdaExp var3 = (LambdaExp)var1;
         Declaration var2 = var3.nameDecl;
         if(var2 == null || var2.getSymbol() == null || !(var2.context instanceof ModuleExp)) {
            return true;
         }

         if(this.immediate && var2.isPublic() && !var3.getFlag(2048) && (this.curLambda == null || var3.topLevel() != this.curLambda.topLevel())) {
            return false;
         }
      }

      return inlineOk;
   }

   public boolean inlineOk(Procedure var1) {
      return this.immediate && var1 instanceof ModuleMethod && ((ModuleMethod)var1).module.getClass().getClassLoader() instanceof ArrayClassLoader?false:inlineOk;
   }

   public boolean isPedantic() {
      return this.pedantic;
   }

   public boolean isStableSourceLocation() {
      return false;
   }

   public boolean isStatic() {
      return this.mainLambda.isStatic();
   }

   public LetExp letDone(Expression var1) {
      LetExp var2 = (LetExp)this.current_scope;
      var2.body = var1;
      this.pop(var2);
      return var2;
   }

   public void letEnter() {
      LetExp var2 = (LetExp)this.current_scope;
      Expression[] var3 = new Expression[var2.countDecls()];
      Declaration var1 = var2.firstDecl();

      for(int var4 = 0; var1 != null; ++var4) {
         var3[var4] = var1.getValue();
         var1 = var1.nextDecl();
      }

      var2.inits = var3;
      this.lexical.push((ScopeExp)var2);
   }

   public void letStart() {
      this.pushScope(new LetExp((Expression[])null));
   }

   public Declaration letVariable(Object var1, Type var2, Expression var3) {
      Declaration var4 = ((LetExp)this.current_scope).addDeclaration(var1, var2);
      var4.noteValue(var3);
      return var4;
   }

   public final void loadCallContext() {
      CodeAttr var1 = this.getCode();
      if(this.callContextVar != null && !this.callContextVar.dead()) {
         var1.emitLoad(this.callContextVar);
      } else if(this.method == this.clinitMethod) {
         this.callContextVar = new Variable("$ctx", typeCallContext);
         this.callContextVar.reserveLocal(var1.getMaxLocals(), var1);
         var1.emitLoad(this.callContextVar);
         this.callContextVarForInit = this.callContextVar;
      } else {
         var1.emitInvokeStatic(getCallContextInstanceMethod);
         var1.emitDup();
         this.callContextVar = new Variable("$ctx", typeCallContext);
         var1.getCurrentScope().addVariable(var1, this.callContextVar);
         var1.emitStore(this.callContextVar);
      }
   }

   public void loadClassRef(ObjectType var1) {
      CodeAttr var2 = this.getCode();
      if(this.curClass.getClassfileVersion() >= 3211264) {
         var2.emitPushClass(var1);
      } else if(var1 == this.mainClass && this.mainLambda.isStatic() && this.moduleInstanceMainField != null) {
         var2.emitGetStatic(this.moduleInstanceMainField);
         var2.emitInvokeVirtual(Type.objectType.getDeclaredMethod("getClass", 0));
      } else {
         String var3;
         if(var1 instanceof ClassType) {
            var3 = var1.getName();
         } else {
            var3 = var1.getInternalName().replace('/', '.');
         }

         var2.emitPushString(var3);
         var2.emitInvokeStatic(this.getForNameHelper());
      }
   }

   public Declaration lookup(Object var1, int var2) {
      return this.lexical.lookup(var1, var2);
   }

   public void loopBody(Expression var1) {
      ((LambdaExp)this.current_scope).body = var1;
   }

   public void loopCond(Expression var1) {
      this.checkLoop();
      this.exprStack.push(var1);
   }

   public void loopEnter() {
      this.checkLoop();
      LambdaExp var1 = (LambdaExp)this.current_scope;
      int var4 = var1.min_args;
      var1.max_args = var4;
      Expression[] var2 = new Expression[var4];

      while(true) {
         --var4;
         if(var4 < 0) {
            LetExp var3 = (LetExp)var1.outer;
            var3.setBody(new ApplyExp(new ReferenceExp(var3.firstDecl()), var2));
            this.lexical.push((ScopeExp)var1);
            return;
         }

         var2[var4] = (Expression)this.exprStack.pop();
      }
   }

   public Expression loopRepeat() {
      return this.loopRepeat((Expression[])Expression.noExpressions);
   }

   public Expression loopRepeat(Expression var1) {
      return this.loopRepeat((Expression[])(new Expression[]{var1}));
   }

   public Expression loopRepeat(Expression[] var1) {
      LambdaExp var2 = (LambdaExp)this.current_scope;
      ScopeExp var3 = var2.outer;
      Declaration var5 = var3.firstDecl();
      Expression var4 = (Expression)this.exprStack.pop();
      ApplyExp var6 = new ApplyExp(new ReferenceExp(var5), var1);
      var2.body = new IfExp(var4, new BeginExp(var2.body, var6), QuoteExp.voidExp);
      this.lexical.pop((ScopeExp)var2);
      this.current_scope = var3.outer;
      return var3;
   }

   public void loopStart() {
      LambdaExp var1 = new LambdaExp();
      LetExp var2 = new LetExp(new Expression[]{var1});
      var2.addDeclaration("%do%loop").noteValue(var1);
      var1.setName("%do%loop");
      var2.outer = this.current_scope;
      var1.outer = var2;
      this.current_scope = var1;
   }

   public Declaration loopVariable(Object var1, Type var2, Expression var3) {
      this.checkLoop();
      LambdaExp var4 = (LambdaExp)this.current_scope;
      Declaration var5 = var4.addDeclaration(var1, var2);
      if(this.exprStack == null) {
         this.exprStack = new Stack();
      }

      this.exprStack.push(var3);
      ++var4.min_args;
      return var5;
   }

   public boolean makeRunnable() {
      return !this.generatingServlet() && !this.generatingApplet() && !this.getModule().staticInitRun();
   }

   public void mustCompileHere() {
      if(!this.mustCompile && !ModuleExp.compilerAvailable) {
         this.error('w', "this expression claimed that it must be compiled, but compiler is unavailable");
      } else {
         this.mustCompile = true;
      }
   }

   public void outputClass(String var1) throws IOException {
      char var2 = File.separatorChar;

      for(int var6 = 0; var6 < this.numClasses; ++var6) {
         ClassType var3 = this.classes[var6];
         String var4 = var1 + var3.getName().replace('.', var2) + ".class";
         String var5 = (new File(var4)).getParent();
         if(var5 != null) {
            (new File(var5)).mkdirs();
         }

         var3.writeToFile(var4);
      }

      this.minfo.cleanupAfterCompilation();
   }

   public Expression parse(Object var1) {
      throw new Error("unimeplemented parse");
   }

   public final void pop() {
      this.pop(this.current_scope);
   }

   public void pop(ScopeExp var1) {
      this.lexical.pop((ScopeExp)var1);
      this.current_scope = var1.outer;
   }

   public void process(int param1) {
      // $FF: Couldn't be decompiled
   }

   public void push(Declaration var1) {
      this.lexical.push((Declaration)var1);
   }

   public void push(ScopeExp var1) {
      this.pushScope(var1);
      this.lexical.push((ScopeExp)var1);
   }

   void pushChain(ScopeExp var1, ScopeExp var2) {
      if(var1 != var2) {
         this.pushChain(var1.outer, var2);
         this.pushScope(var1);
         this.lexical.push((ScopeExp)var1);
      }

   }

   public ModuleExp pushNewModule(Lexer var1) {
      this.lexer = var1;
      return this.pushNewModule((String)var1.getName());
   }

   public ModuleExp pushNewModule(String var1) {
      ModuleExp var2 = new ModuleExp();
      if(var1 != null) {
         var2.setFile(var1);
      }

      if(this.generatingApplet() || this.generatingServlet()) {
         var2.setFlag(131072);
      }

      if(this.immediate) {
         var2.setFlag(1048576);
         (new ModuleInfo()).setCompilation(this);
      }

      this.mainLambda = var2;
      this.push((ScopeExp)var2);
      return var2;
   }

   public void pushPendingImport(ModuleInfo var1, ScopeExp var2, int var3) {
      if(this.pendingImports == null) {
         this.pendingImports = new Stack();
      }

      this.pendingImports.push(var1);
      this.pendingImports.push(var2);
      ReferenceExp var4 = new ReferenceExp((Object)null);
      var4.setLine((Compilation)this);
      this.pendingImports.push(var4);
      this.pendingImports.push(Integer.valueOf(var3));
   }

   public final void pushScope(ScopeExp var1) {
      if(!this.mustCompile && (var1.mustCompile() || ModuleExp.compilerAvailable && var1 instanceof LambdaExp && !(var1 instanceof ModuleExp))) {
         this.mustCompileHere();
      }

      var1.outer = this.current_scope;
      this.current_scope = var1;
   }

   public Object resolve(Object var1, boolean var2) {
      Environment var3 = Environment.getCurrent();
      Symbol var4;
      if(var1 instanceof String) {
         var4 = var3.defaultNamespace().lookup((String)var1);
      } else {
         var4 = (Symbol)var1;
      }

      return var4 == null?null:(var2 && this.getLanguage().hasSeparateFunctionNamespace()?var3.getFunction(var4, (Object)null):var3.get((EnvironmentKey)var4, (Object)null));
   }

   public void setColumn(int var1) {
      this.messages.setColumn(var1);
   }

   public void setCurrentScope(ScopeExp var1) {
      int var5 = ScopeExp.nesting(var1);

      int var4;
      for(var4 = ScopeExp.nesting(this.current_scope); var4 > var5; --var4) {
         this.pop(this.current_scope);
      }

      ScopeExp var2 = var1;

      while(true) {
         ScopeExp var3 = var2;
         if(var5 <= var4) {
            while(var3 != this.current_scope) {
               this.pop(this.current_scope);
               var3 = var3.outer;
            }

            this.pushChain(var1, var3);
            return;
         }

         var2 = var2.outer;
         --var5;
      }
   }

   public void setFile(String var1) {
      this.messages.setFile(var1);
   }

   public void setLine(int var1) {
      this.messages.setLine(var1);
   }

   public final void setLine(Expression var1) {
      this.messages.setLocation(var1);
   }

   public void setLine(Object var1) {
      if(var1 instanceof SourceLocator) {
         this.messages.setLocation((SourceLocator)var1);
      }

   }

   public void setLine(String var1, int var2, int var3) {
      this.messages.setLine(var1, var2, var3);
   }

   public final void setLocation(SourceLocator var1) {
      this.messages.setLocation(var1);
   }

   public void setMessages(SourceMessages var1) {
      this.messages = var1;
   }

   public void setModule(ModuleExp var1) {
      this.mainLambda = var1;
   }

   public void setSharedModuleDefs(boolean var1) {
      if(var1) {
         this.langOptions |= 2;
      } else {
         this.langOptions &= -3;
      }
   }

   public void setState(int var1) {
      this.state = var1;
   }

   public boolean sharedModuleDefs() {
      return (this.langOptions & 2) != 0;
   }

   public Expression syntaxError(String var1) {
      this.error('e', var1);
      return new ErrorExp(var1);
   }

   public String toString() {
      return "<compilation " + this.mainLambda + ">";
   }

   public void usedClass(Type var1) {
      while(var1 instanceof ArrayType) {
         var1 = ((ArrayType)var1).getComponentType();
      }

      if(this.immediate && var1 instanceof ClassType) {
         this.loader.addClass((ClassType)((ClassType)var1));
      }

   }

   public boolean usingCPStyle() {
      return defaultCallConvention == 4;
   }

   public boolean usingTailCalls() {
      return defaultCallConvention >= 3;
   }

   public void walkModule(ModuleExp var1) {
      if(debugPrintExpr) {
         OutPort var2 = OutPort.errDefault();
         var2.println("[Module:" + var1.getName());
         var1.print(var2);
         var2.println(']');
         var2.flush();
      }

      InlineCalls.inlineCalls(var1, this);
      PushApply.pushApply(var1);
      ChainLambdas.chainLambdas(var1, this);
      FindTailCalls.findTailCalls(var1, this);
   }

   public boolean warnAsError() {
      return this.currentOptions.getBoolean((Options.OptionInfo)warnAsError);
   }

   public boolean warnInvokeUnknownMethod() {
      return this.currentOptions.getBoolean((Options.OptionInfo)warnInvokeUnknownMethod);
   }

   public boolean warnUndefinedVariable() {
      return this.currentOptions.getBoolean((Options.OptionInfo)warnUndefinedVariable);
   }

   public boolean warnUnknownMember() {
      return this.currentOptions.getBoolean((Options.OptionInfo)warnUnknownMember);
   }
}
