package gnu.kawa.lispexpr;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Field;
import gnu.bytecode.Filter;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;
import gnu.bytecode.PrimType;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.Language;
import gnu.expr.PairClassType;
import gnu.expr.PrimProcedure;
import gnu.expr.Target;
import gnu.expr.TypeValue;
import gnu.kawa.functions.MakeList;
import gnu.kawa.reflect.InstanceOf;
import gnu.mapping.Procedure;
import gnu.mapping.WrongType;
import gnu.math.DFloNum;
import gnu.math.IntNum;
import gnu.math.Numeric;
import gnu.math.RatNum;
import gnu.math.RealNum;
import gnu.text.FilePath;
import gnu.text.Path;
import gnu.text.URIPath;
import java.util.List;

public class LangObjType extends ObjectType implements TypeValue {

   private static final int CLASSTYPE_TYPE_CODE = 6;
   private static final int CLASS_TYPE_CODE = 4;
   private static final int DFLONUM_TYPE_CODE = 15;
   private static final int FILEPATH_TYPE_CODE = 2;
   private static final int INTEGER_TYPE_CODE = 7;
   private static final int LIST_TYPE_CODE = 11;
   private static final int NUMERIC_TYPE_CODE = 10;
   private static final int PATH_TYPE_CODE = 1;
   private static final int RATIONAL_TYPE_CODE = 8;
   private static final int REAL_TYPE_CODE = 9;
   private static final int REGEX_TYPE_CODE = 14;
   private static final int STRING_TYPE_CODE = 13;
   private static final int TYPE_TYPE_CODE = 5;
   public static final LangObjType URIType = new LangObjType("URI", "gnu.text.URIPath", 3);
   private static final int URI_TYPE_CODE = 3;
   static final String VARARGS_SUFFIX = "";
   private static final int VECTOR_TYPE_CODE = 12;
   public static final LangObjType dflonumType = new LangObjType("DFloNum", "gnu.math.DFloNum", 15);
   public static final LangObjType filepathType = new LangObjType("filepath", "gnu.text.FilePath", 2);
   public static final LangObjType integerType = new LangObjType("integer", "gnu.math.IntNum", 7);
   public static final LangObjType listType = new LangObjType("list", "gnu.lists.LList", 11);
   static PrimProcedure makeFilepathProc = new PrimProcedure("gnu.text.FilePath", "makeFilePath", 1);
   static PrimProcedure makePathProc = new PrimProcedure("gnu.text.Path", "valueOf", 1);
   static PrimProcedure makeURIProc = new PrimProcedure("gnu.text.URIPath", "makeURI", 1);
   public static final LangObjType numericType = new LangObjType("number", "gnu.math.Numeric", 10);
   public static final LangObjType pathType = new LangObjType("path", "gnu.text.Path", 1);
   public static final LangObjType rationalType = new LangObjType("rational", "gnu.math.RatNum", 8);
   public static final LangObjType realType = new LangObjType("real", "gnu.math.RealNum", 9);
   public static final LangObjType regexType = new LangObjType("regex", "java.util.regex.Pattern", 14);
   public static final LangObjType stringType = new LangObjType("string", "java.lang.CharSequence", 13);
   static final ClassType typeArithmetic = ClassType.make("gnu.kawa.functions.Arithmetic");
   public static final LangObjType typeClass = new LangObjType("class", "java.lang.Class", 4);
   public static final LangObjType typeClassType = new LangObjType("class-type", "gnu.bytecode.ClassType", 6);
   public static final ClassType typeLangObjType = ClassType.make("gnu.kawa.lispexpr.LangObjType");
   public static final LangObjType typeType = new LangObjType("type", "gnu.bytecode.Type", 5);
   public static final LangObjType vectorType = new LangObjType("vector", "gnu.lists.FVector", 12);
   ClassType implementationType;
   final int typeCode;


   LangObjType(String var1, String var2, int var3) {
      super(var1);
      this.implementationType = ClassType.make(var2);
      this.typeCode = var3;
      this.setSignature(this.implementationType.getSignature());
   }

   public static DFloNum coerceDFloNum(Object var0) {
      DFloNum var1 = DFloNum.asDFloNumOrNull(var0);
      if(var1 == null && var0 != null) {
         throw new WrongType(-4, var0, dflonumType);
      } else {
         return var1;
      }
   }

   public static IntNum coerceIntNum(Object var0) {
      IntNum var1 = IntNum.asIntNumOrNull(var0);
      if(var1 == null && var0 != null) {
         throw new WrongType(-4, var0, integerType);
      } else {
         return var1;
      }
   }

   public static Numeric coerceNumeric(Object var0) {
      Numeric var1 = Numeric.asNumericOrNull(var0);
      if(var1 == null && var0 != null) {
         throw new WrongType(-4, var0, numericType);
      } else {
         return var1;
      }
   }

   public static RatNum coerceRatNum(Object var0) {
      RatNum var1 = RatNum.asRatNumOrNull(var0);
      if(var1 == null && var0 != null) {
         throw new WrongType(-4, var0, rationalType);
      } else {
         return var1;
      }
   }

   public static RealNum coerceRealNum(Object var0) {
      RealNum var1 = RealNum.asRealNumOrNull(var0);
      if(var1 == null && var0 != null) {
         throw new WrongType(-4, var0, realType);
      } else {
         return var1;
      }
   }

   public static Class coerceToClass(Object var0) {
      Class var1 = coerceToClassOrNull(var0);
      if(var1 == null && var0 != null) {
         throw new ClassCastException("cannot cast " + var0 + " to type");
      } else {
         return var1;
      }
   }

   public static Class coerceToClassOrNull(Object var0) {
      return var0 instanceof Class?(Class)var0:(var0 instanceof Type && var0 instanceof ClassType && !(var0 instanceof PairClassType)?((ClassType)var0).getReflectClass():null);
   }

   public static ClassType coerceToClassType(Object var0) {
      ClassType var1 = coerceToClassTypeOrNull(var0);
      if(var1 == null && var0 != null) {
         throw new ClassCastException("cannot cast " + var0 + " to class-type");
      } else {
         return var1;
      }
   }

   public static ClassType coerceToClassTypeOrNull(Object var0) {
      if(var0 instanceof ClassType) {
         return (ClassType)var0;
      } else {
         if(var0 instanceof Class) {
            Type var1 = Language.getDefaultLanguage().getTypeFor((Class)((Class)var0));
            if(var1 instanceof ClassType) {
               return (ClassType)var1;
            }
         }

         return null;
      }
   }

   public static Type coerceToType(Object var0) {
      Type var1 = coerceToTypeOrNull(var0);
      if(var1 == null && var0 != null) {
         throw new ClassCastException("cannot cast " + var0 + " to type");
      } else {
         return var1;
      }
   }

   public static Type coerceToTypeOrNull(Object var0) {
      return var0 instanceof Type?(Type)var0:(var0 instanceof Class?Language.getDefaultLanguage().getTypeFor((Class)((Class)var0)):null);
   }

   public Object coerceFromObject(Object var1) {
      switch(this.typeCode) {
      case 1:
         return Path.valueOf(var1);
      case 2:
         return FilePath.makeFilePath(var1);
      case 3:
         return URIPath.makeURI(var1);
      case 4:
         return coerceToClass(var1);
      case 5:
         return coerceToType(var1);
      case 6:
         return coerceToClassType(var1);
      case 7:
         return coerceIntNum(var1);
      case 8:
         return coerceRatNum(var1);
      case 9:
         return coerceRealNum(var1);
      case 10:
         return coerceNumeric(var1);
      case 11:
      case 12:
      case 13:
      case 14:
      default:
         return super.coerceFromObject(var1);
      case 15:
         return coerceDFloNum(var1);
      }
   }

   Method coercionMethod() {
      switch(this.typeCode) {
      case 4:
         return typeLangObjType.getDeclaredMethod("coerceToClass", 1);
      case 5:
         return typeLangObjType.getDeclaredMethod("coerceToType", 1);
      case 6:
         return typeLangObjType.getDeclaredMethod("coerceToClassType", 1);
      case 7:
         return typeLangObjType.getDeclaredMethod("coerceIntNum", 1);
      case 8:
         return typeLangObjType.getDeclaredMethod("coerceRatNum", 1);
      case 9:
         return typeLangObjType.getDeclaredMethod("coerceRealNum", 1);
      case 10:
         return typeLangObjType.getDeclaredMethod("coerceNumeric", 1);
      case 11:
      case 12:
      case 13:
      case 14:
         return null;
      case 15:
         return typeLangObjType.getDeclaredMethod("coerceDFloNum", 1);
      default:
         return ((PrimProcedure)this.getConstructor()).getMethod();
      }
   }

   Method coercionOrNullMethod() {
      ClassType var2 = this.implementationType;
      String var1;
      switch(this.typeCode) {
      case 1:
         var1 = "coerceToPathOrNull";
         break;
      case 2:
         var1 = "coerceToFilePathOrNull";
         break;
      case 3:
         var1 = "coerceToURIPathOrNull";
         break;
      case 4:
         var2 = typeLangObjType;
         var1 = "coerceToClassOrNull";
         break;
      case 5:
         var2 = typeLangObjType;
         var1 = "coerceToTypeOrNull";
         break;
      case 6:
         var2 = typeLangObjType;
         var1 = "coerceToClassTypeOrNull";
         break;
      case 7:
         var2 = this.implementationType;
         var1 = "asIntNumOrNull";
         break;
      case 8:
         var2 = this.implementationType;
         var1 = "asRatNumOrNull";
         break;
      case 9:
         var2 = this.implementationType;
         var1 = "asRealNumOrNull";
         break;
      case 10:
         var2 = this.implementationType;
         var1 = "asNumericOrNull";
         break;
      case 11:
      case 12:
      case 13:
      case 14:
      default:
         return null;
      case 15:
         var2 = this.implementationType;
         var1 = "asDFloNumOrNull";
      }

      return var2.getDeclaredMethod(var1, 1);
   }

   public int compare(Type var1) {
      byte var3 = -1;
      int var2;
      switch(this.typeCode) {
      case 4:
         var2 = var3;
         if(var1 == typeType) {
            return var2;
         }

         var2 = var3;
         if(var1 == typeClassType) {
            return var2;
         }

         var2 = var3;
         if(var1 == typeType.implementationType) {
            return var2;
         }

         if(var1 == typeClassType.implementationType) {
            return -1;
         }
         break;
      case 5:
         if(var1 == typeClass || var1 == typeClassType || var1 == typeClass.implementationType || var1 == typeClassType.implementationType) {
            return 1;
         }
      case 6:
         if(var1 == typeClass || var1 == typeClass.implementationType) {
            return 1;
         }

         var2 = var3;
         if(var1 == typeType) {
            return var2;
         }

         if(var1 == typeClass.implementationType) {
            return -1;
         }
         break;
      case 7:
         if(var1 instanceof PrimType) {
            switch(var1.getSignature().charAt(0)) {
            case 66:
            case 73:
            case 74:
            case 83:
               return 1;
            }
         }
      case 8:
      case 10:
      case 11:
      case 12:
      case 13:
      case 14:
      default:
         break;
      case 9:
      case 15:
         if(var1 instanceof PrimType) {
            switch(var1.getSignature().charAt(0)) {
            case 68:
            case 70:
               return 1;
            case 69:
            }
         }
      }

      var2 = this.getImplementationType().compare(var1.getImplementationType());
      return var2;
   }

   public Expression convertValue(Expression var1) {
      if(this.typeCode != 7 && this.typeCode != 10 && this.typeCode != 9 && this.typeCode != 8 && this.typeCode != 15) {
         Method var2 = this.coercionMethod();
         if(var2 != null) {
            ApplyExp var3 = new ApplyExp(var2, new Expression[]{var1});
            var3.setType(this);
            return var3;
         }
      }

      return null;
   }

   public void emitCoerceFromObject(CodeAttr var1) {
      switch(this.typeCode) {
      case 11:
      case 12:
      case 13:
      case 14:
         var1.emitCheckcast(this.implementationType);
         return;
      default:
         var1.emitInvoke(this.coercionMethod());
      }
   }

   public void emitConvertFromPrimitive(Type var1, CodeAttr var2) {
      Object var7 = null;
      Object var8 = null;
      Object var3 = var7;
      String var4 = (String)var8;
      Object var6 = var1;
      Object var5;
      switch(this.typeCode) {
      case 7:
      case 8:
      case 9:
      case 10:
         var3 = var7;
         var4 = (String)var8;
         var6 = var1;
         if(var1 instanceof PrimType) {
            if(var1 != Type.intType && var1 != Type.byteType && var1 != Type.shortType) {
               if(var1 == Type.longType) {
                  var4 = "gnu.math.IntNum";
                  var3 = Type.long_type;
                  var6 = var1;
               } else {
                  label62: {
                     if(this.typeCode != 9) {
                        var3 = var7;
                        var4 = (String)var8;
                        var6 = var1;
                        if(this.typeCode != 10) {
                           break label62;
                        }
                     }

                     var5 = var1;
                     if(var1 == Type.floatType) {
                        var2.emitConvert(Type.float_type, Type.double_type);
                        var5 = Type.doubleType;
                     }

                     var3 = var7;
                     var4 = (String)var8;
                     var6 = var5;
                     if(var5 == Type.doubleType) {
                        var4 = "gnu.math.DFloNum";
                        var3 = Type.doubleType;
                        var6 = var5;
                     }
                  }
               }
            } else {
               var4 = "gnu.math.IntNum";
               var3 = Type.int_type;
               var6 = var1;
            }
         }
      case 11:
      case 12:
      case 13:
      case 14:
         break;
      case 15:
         var3 = var7;
         var4 = (String)var8;
         var6 = var1;
         if(var1 instanceof PrimType) {
            label43: {
               if(var1 != Type.intType && var1 != Type.byteType && var1 != Type.shortType && var1 != Type.longType) {
                  var5 = var1;
                  if(var1 != Type.floatType) {
                     break label43;
                  }
               }

               var2.emitConvert(var1, Type.doubleType);
               var5 = Type.doubleType;
            }

            var3 = var7;
            var4 = (String)var8;
            var6 = var5;
            if(var5 == Type.doubleType) {
               var4 = "gnu.math.DFloNum";
               var3 = var5;
               var6 = var5;
            }
         }
         break;
      default:
         var6 = var1;
         var4 = (String)var8;
         var3 = var7;
      }

      if(var4 != null) {
         var2.emitInvokeStatic(ClassType.make(var4).getDeclaredMethod("make", new Type[]{(Type)var3}));
      } else {
         super.emitConvertFromPrimitive((Type)var6, var2);
      }
   }

   public void emitIsInstance(Variable var1, Compilation var2, Target var3) {
      switch(this.typeCode) {
      case 11:
      case 12:
      case 13:
      case 14:
         this.implementationType.emitIsInstance(var2.getCode());
         var3.compileFromStack(var2, var2.getLanguage().getTypeFor((Class)Boolean.TYPE));
         return;
      default:
         InstanceOf.emitIsInstance(this, var1, var2, var3);
      }
   }

   public void emitTestIf(Variable var1, Declaration var2, Compilation var3) {
      CodeAttr var4 = var3.getCode();
      if(var1 != null) {
         var4.emitLoad(var1);
      }

      Method var5 = this.coercionOrNullMethod();
      if(var5 != null) {
         var4.emitInvokeStatic(var5);
      }

      if(var2 != null) {
         var4.emitDup();
         var2.compileStore(var3);
      }

      if(var5 != null) {
         var4.emitIfNotNull();
      } else {
         this.implementationType.emitIsInstance(var4);
         var4.emitIfIntNotZero();
      }
   }

   public Procedure getConstructor() {
      switch(this.typeCode) {
      case 1:
         return makePathProc;
      case 2:
         return makeFilepathProc;
      case 3:
         return makeURIProc;
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      default:
         return null;
      case 11:
         return MakeList.list;
      case 12:
         return new PrimProcedure("gnu.lists.FVector", "make", 1);
      case 13:
         return new PrimProcedure("kawa.lib.strings", "$make$string$", 1);
      case 14:
         return new PrimProcedure("java.util.regex.Pattern", "compile", 1);
      }
   }

   public Method getDeclaredMethod(String var1, int var2) {
      return this.implementationType.getDeclaredMethod(var1, var2);
   }

   public Field getField(String var1, int var2) {
      return this.implementationType.getField(var1, var2);
   }

   public Type getImplementationType() {
      return this.implementationType;
   }

   public Method getMethod(String var1, Type[] var2) {
      return this.implementationType.getMethod(var1, var2);
   }

   public int getMethods(Filter var1, int var2, List var3) {
      return this.implementationType.getMethods(var1, var2, var3);
   }

   public Type getRealType() {
      return this.implementationType;
   }

   public Class getReflectClass() {
      return this.implementationType.getReflectClass();
   }
}
