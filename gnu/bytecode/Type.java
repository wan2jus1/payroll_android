package gnu.bytecode;

import gnu.bytecode.ArrayType;
import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;
import gnu.bytecode.PrimType;
import gnu.kawa.util.AbstractWeakHashTable;
import java.io.PrintWriter;
import java.util.HashMap;

public abstract class Type implements java.lang.reflect.Type {

   public static final PrimType booleanType = new PrimType("boolean", "Z", 1, Boolean.TYPE);
   public static final Method booleanValue_method;
   public static final ClassType boolean_ctype;
   public static final PrimType boolean_type = booleanType;
   public static final PrimType byteType = new PrimType("byte", "B", 1, Byte.TYPE);
   public static final PrimType byte_type = byteType;
   public static final PrimType charType = new PrimType("char", "C", 2, Character.TYPE);
   public static final PrimType char_type = charType;
   public static final Method clone_method;
   public static final PrimType doubleType = new PrimType("double", "D", 8, Double.TYPE);
   public static final Method doubleValue_method;
   public static final PrimType double_type = doubleType;
   public static final ObjectType errorType;
   public static final PrimType floatType = new PrimType("float", "F", 4, Float.TYPE);
   public static final Method floatValue_method;
   public static final PrimType float_type = floatType;
   public static final PrimType intType = new PrimType("int", "I", 4, Integer.TYPE);
   public static final Method intValue_method;
   public static final PrimType int_type = intType;
   public static final ClassType java_lang_Class_type;
   public static final ClassType javalangBooleanType;
   public static final ClassType javalangClassType;
   public static final ClassType javalangNumberType;
   public static final ClassType javalangObjectType;
   public static ClassType javalangStringType;
   public static final ClassType javalangThrowableType;
   public static final PrimType longType = new PrimType("long", "J", 8, Long.TYPE);
   public static final Method longValue_method;
   public static final PrimType long_type = longType;
   static Type.ClassToTypeMap mapClassToType;
   static HashMap mapNameToType = new HashMap();
   public static final PrimType neverReturnsType;
   public static final ObjectType nullType;
   public static final ClassType number_type;
   public static final ClassType objectType;
   public static final ClassType pointer_type;
   public static final PrimType shortType = new PrimType("short", "S", 2, Short.TYPE);
   public static final PrimType short_type = shortType;
   public static final ClassType string_type;
   public static final ClassType throwable_type;
   public static final ClassType toStringType;
   public static final Method toString_method;
   public static final ClassType tostring_type;
   public static final Type[] typeArray0;
   public static final PrimType voidType = new PrimType("void", "V", 0, Void.TYPE);
   public static final PrimType void_type = voidType;
   ArrayType array_type;
   protected Class reflectClass;
   String signature;
   int size;
   String this_name;


   static {
      mapNameToType.put("byte", byteType);
      mapNameToType.put("short", shortType);
      mapNameToType.put("int", intType);
      mapNameToType.put("long", longType);
      mapNameToType.put("float", floatType);
      mapNameToType.put("double", doubleType);
      mapNameToType.put("boolean", booleanType);
      mapNameToType.put("char", charType);
      mapNameToType.put("void", voidType);
      neverReturnsType = new PrimType(voidType);
      neverReturnsType.this_name = "(never-returns)";
      nullType = new ObjectType("(type of null)");
      errorType = new ClassType("(error type)");
      javalangStringType = ClassType.make("java.lang.String");
      toStringType = new ClassType("java.lang.String");
      javalangObjectType = ClassType.make("java.lang.Object");
      objectType = javalangObjectType;
      javalangBooleanType = ClassType.make("java.lang.Boolean");
      javalangThrowableType = ClassType.make("java.lang.Throwable");
      typeArray0 = new Type[0];
      toString_method = objectType.getDeclaredMethod("toString", 0);
      javalangNumberType = ClassType.make("java.lang.Number");
      clone_method = Method.makeCloneMethod(objectType);
      intValue_method = javalangNumberType.addMethod("intValue", typeArray0, intType, 1);
      longValue_method = javalangNumberType.addMethod("longValue", typeArray0, longType, 1);
      floatValue_method = javalangNumberType.addMethod("floatValue", typeArray0, floatType, 1);
      doubleValue_method = javalangNumberType.addMethod("doubleValue", typeArray0, doubleType, 1);
      booleanValue_method = javalangBooleanType.addMethod("booleanValue", typeArray0, booleanType, 1);
      javalangClassType = ClassType.make("java.lang.Class");
      pointer_type = javalangObjectType;
      string_type = javalangStringType;
      tostring_type = toStringType;
      java_lang_Class_type = javalangClassType;
      boolean_ctype = javalangBooleanType;
      throwable_type = javalangThrowableType;
      number_type = javalangNumberType;
   }

   protected Type() {
   }

   public Type(Type var1) {
      this.this_name = var1.this_name;
      this.signature = var1.signature;
      this.size = var1.size;
      this.reflectClass = var1.reflectClass;
   }

   Type(String var1, String var2) {
      this.this_name = var1;
      this.signature = var2;
   }

   public static Type getType(String param0) {
      // $FF: Couldn't be decompiled
   }

   public static boolean isMoreSpecific(Type[] var0, Type[] var1) {
      if(var0.length != var1.length) {
         return false;
      } else {
         int var2 = var0.length;

         int var3;
         do {
            var3 = var2 - 1;
            if(var3 < 0) {
               return true;
            }

            var2 = var3;
         } while(var0[var3].isSubtype(var1[var3]));

         return false;
      }
   }

   public static boolean isValidJavaTypeName(String var0) {
      boolean var2 = false;

      int var3;
      for(var3 = var0.length(); var3 > 2 && var0.charAt(var3 - 1) == 93 && var0.charAt(var3 - 2) == 91; var3 -= 2) {
         ;
      }

      int var4 = 0;

      while(true) {
         if(var4 >= var3) {
            if(var4 == var3) {
               return true;
            }
            break;
         }

         char var1 = var0.charAt(var4);
         if(var1 == 46) {
            if(!var2) {
               break;
            }

            var2 = false;
         } else {
            if(var2) {
               if(!Character.isJavaIdentifierPart(var1)) {
                  break;
               }
            } else if(!Character.isJavaIdentifierStart(var1)) {
               break;
            }

            var2 = true;
         }

         ++var4;
      }

      return false;
   }

   public static Type lookupType(String param0) {
      // $FF: Couldn't be decompiled
   }

   public static Type lowestCommonSuperType(Type var0, Type var1) {
      Type var2;
      if(var0 == neverReturnsType) {
         var2 = var1;
      } else {
         var2 = var0;
         if(var1 != neverReturnsType) {
            if(var0 == null || var1 == null) {
               return null;
            }

            if(var0 instanceof PrimType && var1 instanceof PrimType) {
               var2 = var0;
               if(var0 != var1) {
                  var0 = ((PrimType)var0).promotedType();
                  var2 = var0;
                  if(var0 != ((PrimType)var1).promotedType()) {
                     return null;
                  }
               }
            } else {
               if(var0.isSubtype(var1)) {
                  return var1;
               }

               var2 = var0;
               if(!var1.isSubtype(var0)) {
                  if(var0 instanceof ClassType && var1 instanceof ClassType) {
                     ClassType var3 = (ClassType)var0;
                     ClassType var4 = (ClassType)var1;
                     if(!var3.isInterface() && !var4.isInterface()) {
                        return lowestCommonSuperType(var3.getSuperclass(), var4.getSuperclass());
                     }

                     return objectType;
                  }

                  return objectType;
               }
            }
         }
      }

      return var2;
   }

   public static Type make(Class param0) {
      // $FF: Couldn't be decompiled
   }

   public static void printSignature(String var0, int var1, int var2, PrintWriter var3) {
      if(var2 != 0) {
         char var4 = var0.charAt(var1);
         if(var2 != 1) {
            if(var4 == 91) {
               printSignature(var0, var1 + 1, var2 - 1, var3);
               var3.print("[]");
               return;
            }

            if(var4 == 76 && var2 > 2 && var0.indexOf(59, var1) == var2 - 1 + var1) {
               var3.print(var0.substring(var1 + 1, var2 - 1 + var1).replace('/', '.'));
               return;
            }

            var3.append(var0, var1, var2 - var1);
            return;
         }

         PrimType var5 = signatureToPrimitive(var4);
         if(var5 != null) {
            var3.print(var5.getName());
            return;
         }
      }

   }

   public static void registerTypeForClass(Class param0, Type param1) {
      // $FF: Couldn't be decompiled
   }

   public static int signatureLength(String var0) {
      return signatureLength(var0, 0);
   }

   public static int signatureLength(String var0, int var1) {
      if(var0.length() > var1) {
         char var2 = var0.charAt(var1);

         int var3;
         for(var3 = 0; var2 == 91; var2 = var0.charAt(var1)) {
            ++var3;
            ++var1;
         }

         if(signatureToPrimitive(var2) != null) {
            return var3 + 1;
         }

         if(var2 == 76) {
            int var4 = var0.indexOf(59, var1);
            if(var4 > 0) {
               return var3 + var4 + 1 - var1;
            }
         }
      }

      return -1;
   }

   public static String signatureToName(String var0) {
      int var5 = var0.length();
      if(var5 != 0) {
         char var1 = var0.charAt(0);
         if(var5 == 1) {
            PrimType var2 = signatureToPrimitive(var1);
            if(var2 != null) {
               return var2.getName();
            }
         }

         if(var1 == 91) {
            byte var4 = 1;
            int var3 = var4;
            if(1 < var5) {
               var3 = var4;
               if(var0.charAt(1) == 91) {
                  var3 = 1 + 1;
               }
            }

            var0 = signatureToName(var0.substring(var3));
            if(var0 != null) {
               StringBuffer var6 = new StringBuffer(50);
               var6.append(var0);

               while(true) {
                  --var3;
                  if(var3 < 0) {
                     return var6.toString();
                  }

                  var6.append("[]");
               }
            }
         } else if(var1 == 76 && var5 > 2 && var0.indexOf(59) == var5 - 1) {
            return var0.substring(1, var5 - 1).replace('/', '.');
         }
      }

      return null;
   }

   public static PrimType signatureToPrimitive(char var0) {
      switch(var0) {
      case 66:
         return byteType;
      case 67:
         return charType;
      case 68:
         return doubleType;
      case 70:
         return floatType;
      case 73:
         return intType;
      case 74:
         return longType;
      case 83:
         return shortType;
      case 86:
         return voidType;
      case 90:
         return booleanType;
      default:
         return null;
      }
   }

   public static Type signatureToType(String var0) {
      return signatureToType(var0, 0, var0.length());
   }

   public static Type signatureToType(String var0, int var1, int var2) {
      if(var2 != 0) {
         char var3 = var0.charAt(var1);
         if(var2 == 1) {
            PrimType var4 = signatureToPrimitive(var3);
            if(var4 != null) {
               return var4;
            }
         }

         if(var3 == 91) {
            Type var5 = signatureToType(var0, var1 + 1, var2 - 1);
            if(var5 != null) {
               return ArrayType.make((Type)var5);
            }
         } else if(var3 == 76 && var2 > 2 && var0.indexOf(59, var1) == var2 - 1 + var1) {
            return ClassType.make(var0.substring(var1 + 1, var2 - 1 + var1).replace('/', '.'));
         }
      }

      return null;
   }

   protected static int swappedCompareResult(int var0) {
      int var1;
      if(var0 == 1) {
         var1 = -1;
      } else {
         var1 = var0;
         if(var0 == -1) {
            return 1;
         }
      }

      return var1;
   }

   public abstract Object coerceFromObject(Object var1);

   public Object coerceToObject(Object var1) {
      return var1;
   }

   public abstract int compare(Type var1);

   public void emitCoerceFromObject(CodeAttr var1) {
      throw new Error("unimplemented emitCoerceFromObject for " + this);
   }

   public void emitCoerceToObject(CodeAttr var1) {
   }

   public void emitConvertFromPrimitive(Type var1, CodeAttr var2) {
      var1.emitCoerceToObject(var2);
   }

   public void emitIsInstance(CodeAttr var1) {
      var1.emitInstanceof(this);
   }

   public Type getImplementationType() {
      return this;
   }

   public final String getName() {
      return this.this_name;
   }

   public Type getRealType() {
      return this;
   }

   public Class getReflectClass() {
      return this.reflectClass;
   }

   public String getSignature() {
      return this.signature;
   }

   public final int getSize() {
      return this.size;
   }

   public int getSizeInWords() {
      return this.size > 4?2:1;
   }

   public int hashCode() {
      String var1 = this.toString();
      return var1 == null?0:var1.hashCode();
   }

   public boolean isExisting() {
      return true;
   }

   public boolean isInstance(Object var1) {
      return this.getReflectClass().isInstance(var1);
   }

   public final boolean isSubtype(Type var1) {
      int var2 = this.compare(var1);
      return var2 == -1 || var2 == 0;
   }

   public final boolean isVoid() {
      return this.size == 0;
   }

   public Type promote() {
      Object var1 = this;
      if(this.size < 4) {
         var1 = intType;
      }

      return (Type)var1;
   }

   protected void setName(String var1) {
      this.this_name = var1;
   }

   public void setReflectClass(Class var1) {
      this.reflectClass = var1;
   }

   protected void setSignature(String var1) {
      this.signature = var1;
   }

   public String toString() {
      return "Type " + this.getName();
   }

   static class ClassToTypeMap extends AbstractWeakHashTable {

      protected Class getKeyFromValue(Type var1) {
         return var1.reflectClass;
      }

      protected boolean matches(Class var1, Class var2) {
         return var1 == var2;
      }
   }
}
