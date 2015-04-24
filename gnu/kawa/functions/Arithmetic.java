package gnu.kawa.functions;

import gnu.bytecode.ClassType;
import gnu.bytecode.PrimType;
import gnu.bytecode.Type;
import gnu.kawa.lispexpr.LangObjType;
import gnu.kawa.lispexpr.LangPrimType;
import gnu.math.DFloNum;
import gnu.math.IntNum;
import gnu.math.Numeric;
import gnu.math.RatNum;
import gnu.math.RealNum;
import java.math.BigDecimal;
import java.math.BigInteger;

public class Arithmetic {

   public static final int BIGDECIMAL_CODE = 5;
   public static final int BIGINTEGER_CODE = 3;
   public static final int DOUBLE_CODE = 8;
   public static final int FLOAT_CODE = 7;
   public static final int FLONUM_CODE = 9;
   public static final int INTNUM_CODE = 4;
   public static final int INT_CODE = 1;
   public static final int LONG_CODE = 2;
   public static final int NUMERIC_CODE = 11;
   public static final int RATNUM_CODE = 6;
   public static final int REALNUM_CODE = 10;
   static LangObjType typeDFloNum = LangObjType.dflonumType;
   static LangObjType typeIntNum = LangObjType.integerType;
   static ClassType typeNumber = ClassType.make("java.lang.Number");
   static ClassType typeNumeric = ClassType.make("gnu.math.Numeric");
   static LangObjType typeRatNum = LangObjType.rationalType;
   static LangObjType typeRealNum = LangObjType.realType;


   public static BigDecimal asBigDecimal(Object var0) {
      return var0 instanceof BigDecimal?(BigDecimal)var0:(var0 instanceof BigInteger?new BigDecimal((BigInteger)var0):(!(var0 instanceof Long) && !(var0 instanceof Integer) && !(var0 instanceof Short) && !(var0 instanceof Byte)?new BigDecimal(var0.toString()):BigDecimal.valueOf(((Number)var0).longValue())));
   }

   public static BigInteger asBigInteger(Object var0) {
      return var0 instanceof BigInteger?(BigInteger)var0:(var0 instanceof IntNum?new BigInteger(var0.toString()):BigInteger.valueOf(((Number)var0).longValue()));
   }

   public static double asDouble(Object var0) {
      return ((Number)var0).doubleValue();
   }

   public static float asFloat(Object var0) {
      return ((Number)var0).floatValue();
   }

   public static int asInt(Object var0) {
      return ((Number)var0).intValue();
   }

   public static IntNum asIntNum(Object var0) {
      return var0 instanceof IntNum?(IntNum)var0:(var0 instanceof BigInteger?IntNum.valueOf(var0.toString(), 10):(var0 instanceof BigDecimal?asIntNum((BigDecimal)((BigDecimal)var0)):IntNum.make(((Number)var0).longValue())));
   }

   public static IntNum asIntNum(BigDecimal var0) {
      return IntNum.valueOf(var0.toBigInteger().toString(), 10);
   }

   public static IntNum asIntNum(BigInteger var0) {
      return IntNum.valueOf(var0.toString(), 10);
   }

   public static long asLong(Object var0) {
      return ((Number)var0).longValue();
   }

   public static Numeric asNumeric(Object var0) {
      Numeric var1 = Numeric.asNumericOrNull(var0);
      return var1 != null?var1:(Numeric)var0;
   }

   public static RatNum asRatNum(Object var0) {
      return (RatNum)(var0 instanceof RatNum?(RatNum)var0:(var0 instanceof BigInteger?IntNum.valueOf(var0.toString(), 10):(var0 instanceof BigDecimal?RatNum.valueOf((BigDecimal)var0):IntNum.make(((Number)var0).longValue()))));
   }

   public static int classifyType(Type var0) {
      byte var2 = 8;
      if(var0 instanceof PrimType) {
         char var3 = var0.getSignature().charAt(0);
         if(var3 != 86 && var3 != 90 && var3 != 67) {
            if(var3 != 68) {
               if(var3 == 70) {
                  return 7;
               }

               if(var3 == 74) {
                  return 2;
               }

               return 1;
            }
         } else {
            var2 = 0;
         }
      } else {
         String var1 = var0.getName();
         if(var0.isSubtype(typeIntNum)) {
            return 4;
         }

         if(var0.isSubtype(typeRatNum)) {
            return 6;
         }

         if(var0.isSubtype(typeDFloNum)) {
            return 9;
         }

         if(!"java.lang.Double".equals(var1)) {
            if("java.lang.Float".equals(var1)) {
               return 7;
            }

            if("java.lang.Long".equals(var1)) {
               return 2;
            }

            if(!"java.lang.Integer".equals(var1) && !"java.lang.Short".equals(var1) && !"java.lang.Byte".equals(var1)) {
               if("java.math.BigInteger".equals(var1)) {
                  return 3;
               }

               if("java.math.BigDecimal".equals(var1)) {
                  return 5;
               }

               if(var0.isSubtype(typeRealNum)) {
                  return 10;
               }

               if(var0.isSubtype(typeNumeric)) {
                  return 11;
               }

               return 0;
            }

            return 1;
         }
      }

      return var2;
   }

   public static int classifyValue(Object var0) {
      byte var2 = -1;
      byte var1;
      if(var0 instanceof Numeric) {
         if(!(var0 instanceof IntNum)) {
            if(var0 instanceof RatNum) {
               return 6;
            }

            if(var0 instanceof DFloNum) {
               return 9;
            }

            if(var0 instanceof RealNum) {
               return 10;
            }

            return 11;
         }

         var1 = 4;
      } else {
         var1 = var2;
         if(var0 instanceof Number) {
            if(var0 instanceof Integer || var0 instanceof Short || var0 instanceof Byte) {
               return 1;
            }

            if(var0 instanceof Long) {
               return 2;
            }

            if(var0 instanceof Float) {
               return 7;
            }

            if(var0 instanceof Double) {
               return 8;
            }

            if(var0 instanceof BigInteger) {
               return 3;
            }

            var1 = var2;
            if(var0 instanceof BigDecimal) {
               return 5;
            }
         }
      }

      return var1;
   }

   public static Object convert(Object var0, int var1) {
      Object var2;
      switch(var1) {
      case 1:
         var2 = var0;
         if(!(var0 instanceof Integer)) {
            return Integer.valueOf(((Number)var0).intValue());
         }
         break;
      case 2:
         var2 = var0;
         if(!(var0 instanceof Long)) {
            return Long.valueOf(((Number)var0).longValue());
         }
         break;
      case 3:
         return asBigInteger(var0);
      case 4:
         return asIntNum((Object)var0);
      case 5:
         return asBigDecimal(var0);
      case 6:
         return asRatNum(var0);
      case 7:
         var2 = var0;
         if(!(var0 instanceof Float)) {
            return Float.valueOf(asFloat(var0));
         }
         break;
      case 8:
         var2 = var0;
         if(!(var0 instanceof Double)) {
            return Double.valueOf(asDouble(var0));
         }
         break;
      case 9:
         var2 = var0;
         if(!(var0 instanceof DFloNum)) {
            return DFloNum.make(asDouble(var0));
         }
         break;
      case 10:
         return (RealNum)asNumeric(var0);
      case 11:
         return asNumeric(var0);
      default:
         var2 = (Number)var0;
      }

      return var2;
   }

   public static boolean isExact(Number var0) {
      return var0 instanceof Numeric?((Numeric)var0).isExact():!(var0 instanceof Double) && !(var0 instanceof Float) && !(var0 instanceof BigDecimal);
   }

   public static Type kindType(int var0) {
      switch(var0) {
      case 1:
         return LangPrimType.intType;
      case 2:
         return LangPrimType.longType;
      case 3:
         return ClassType.make("java.math.BigInteger");
      case 4:
         return typeIntNum;
      case 5:
         return ClassType.make("java.math.BigDecimal");
      case 6:
         return typeRatNum;
      case 7:
         return LangPrimType.floatType;
      case 8:
         return LangPrimType.doubleType;
      case 9:
         return typeDFloNum;
      case 10:
         return typeRealNum;
      case 11:
         return typeNumeric;
      default:
         return Type.pointer_type;
      }
   }

   public static Number toExact(Number var0) {
      Object var1;
      if(var0 instanceof Numeric) {
         var1 = ((Numeric)var0).toExact();
         return (Number)var1;
      } else {
         if(!(var0 instanceof Double) && !(var0 instanceof Float)) {
            var1 = var0;
            if(!(var0 instanceof BigDecimal)) {
               return (Number)var1;
            }
         }

         return DFloNum.toExact(var0.doubleValue());
      }
   }

   public static Number toInexact(Number var0) {
      Object var1;
      if(var0 instanceof Numeric) {
         var1 = ((Numeric)var0).toInexact();
      } else {
         var1 = var0;
         if(!(var0 instanceof Double)) {
            var1 = var0;
            if(!(var0 instanceof Float)) {
               var1 = var0;
               if(!(var0 instanceof BigDecimal)) {
                  return Double.valueOf(var0.doubleValue());
               }
            }
         }
      }

      return (Number)var1;
   }

   public static String toString(Object var0, int var1) {
      switch(classifyValue(var0)) {
      case 1:
         return Integer.toString(asInt(var0), var1);
      case 2:
         return Long.toString(asLong(var0), var1);
      case 3:
         return asBigInteger(var0).toString(var1);
      case 4:
         return asIntNum((Object)var0).toString(var1);
      case 5:
         if(var1 == 10) {
            return asBigDecimal(var0).toString();
         }
      case 7:
         if(var1 == 10) {
            return Float.toString(asFloat(var0));
         }
      case 8:
      case 9:
         if(var1 == 10) {
            return Double.toString(asDouble(var0));
         }
      case 6:
      default:
         return asNumeric(var0).toString(var1);
      }
   }
}
