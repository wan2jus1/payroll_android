package gnu.bytecode;

import gnu.bytecode.ArrayType;
import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.Type;

public class PrimType extends Type {

   private static final String numberHierarchy = "A:java.lang.Byte;B:java.lang.Short;C:java.lang.Integer;D:java.lang.Long;E:gnu.math.IntNum;E:java.gnu.math.BitInteger;G:gnu.math.RatNum;H:java.lang.Float;I:java.lang.Double;I:gnu.math.DFloNum;J:gnu.math.RealNum;K:gnu.math.Complex;L:gnu.math.Quantity;K:gnu.math.Numeric;N:java.lang.Number;";


   protected PrimType(PrimType var1) {
      super(var1.this_name, var1.signature);
      this.size = var1.size;
      this.reflectClass = var1.reflectClass;
   }

   public PrimType(String var1, String var2, int var3, Class var4) {
      super(var1, var2);
      this.size = var3;
      this.reflectClass = var4;
      Type.registerTypeForClass(var4, this);
   }

   public static boolean booleanValue(Object var0) {
      return !(var0 instanceof Boolean) || ((Boolean)var0).booleanValue();
   }

   public static int compare(PrimType var0, PrimType var1) {
      byte var3 = -3;
      byte var4 = -1;
      char var5 = var0.signature.charAt(0);
      char var6 = var1.signature.charAt(0);
      byte var2;
      if(var5 == var6) {
         var2 = 0;
      } else {
         if(var5 == 86) {
            return 1;
         }

         var2 = var4;
         if(var6 != 86) {
            if(var5 == 90 || var6 == 90) {
               return -3;
            }

            if(var5 == 67) {
               var2 = var4;
               if(var1.size <= 2) {
                  return -3;
               }
            } else {
               if(var6 == 67) {
                  var2 = var3;
                  if(var0.size > 2) {
                     var2 = 1;
                  }

                  return var2;
               }

               if(var5 == 68) {
                  return 1;
               }

               var2 = var4;
               if(var6 != 68) {
                  if(var5 == 70) {
                     return 1;
                  }

                  var2 = var4;
                  if(var6 != 70) {
                     if(var5 == 74) {
                        return 1;
                     }

                     var2 = var4;
                     if(var6 != 74) {
                        if(var5 == 73) {
                           return 1;
                        }

                        var2 = var4;
                        if(var6 != 73) {
                           if(var5 == 83) {
                              return 1;
                           }

                           var2 = var4;
                           if(var6 != 83) {
                              return -3;
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      return var2;
   }

   private static char findInHierarchy(String var0) {
      int var1 = "A:java.lang.Byte;B:java.lang.Short;C:java.lang.Integer;D:java.lang.Long;E:gnu.math.IntNum;E:java.gnu.math.BitInteger;G:gnu.math.RatNum;H:java.lang.Float;I:java.lang.Double;I:gnu.math.DFloNum;J:gnu.math.RealNum;K:gnu.math.Complex;L:gnu.math.Quantity;K:gnu.math.Numeric;N:java.lang.Number;".indexOf(var0) - 2;
      return var1 < 0?'\u0000':"A:java.lang.Byte;B:java.lang.Short;C:java.lang.Integer;D:java.lang.Long;E:gnu.math.IntNum;E:java.gnu.math.BitInteger;G:gnu.math.RatNum;H:java.lang.Float;I:java.lang.Double;I:gnu.math.DFloNum;J:gnu.math.RealNum;K:gnu.math.Complex;L:gnu.math.Quantity;K:gnu.math.Numeric;N:java.lang.Number;".charAt(var1);
   }

   public ClassType boxedType() {
      String var1;
      switch(this.getSignature().charAt(0)) {
      case 66:
         var1 = "java.lang.Byte";
         break;
      case 67:
         var1 = "java.lang.Character";
         break;
      case 68:
         var1 = "java.lang.Double";
         break;
      case 70:
         var1 = "java.lang.Float";
         break;
      case 73:
         var1 = "java.lang.Integer";
         break;
      case 74:
         var1 = "java.lang.Long";
         break;
      case 83:
         var1 = "java.lang.Short";
         break;
      case 90:
         var1 = "java.lang.Boolean";
         break;
      default:
         var1 = null;
      }

      return ClassType.make(var1);
   }

   public char charValue(Object var1) {
      return ((Character)var1).charValue();
   }

   public Object coerceFromObject(Object var1) {
      if(var1.getClass() == this.reflectClass) {
         return var1;
      } else {
         char var2;
         if(this.signature != null && this.signature.length() == 1) {
            var2 = this.signature.charAt(0);
         } else {
            var2 = 32;
         }

         switch(var2) {
         case 66:
            return Byte.valueOf(((Number)var1).byteValue());
         case 68:
            return Double.valueOf(((Number)var1).doubleValue());
         case 70:
            return Float.valueOf(((Number)var1).floatValue());
         case 73:
            return Integer.valueOf(((Number)var1).intValue());
         case 74:
            return Long.valueOf(((Number)var1).longValue());
         case 83:
            return Short.valueOf(((Number)var1).shortValue());
         case 90:
            return Boolean.valueOf(((Boolean)var1).booleanValue());
         default:
            throw new ClassCastException("don\'t know how to coerce " + var1.getClass().getName() + " to " + this.getName());
         }
      }
   }

   public int compare(Type var1) {
      byte var5 = 0;
      int var4;
      if(var1 instanceof PrimType) {
         if(var1.getImplementationType() == var1) {
            return compare(this, (PrimType)var1);
         } else {
            var4 = swappedCompareResult(var1.compare(this));
            return var4;
         }
      } else if(!(var1 instanceof ClassType)) {
         if(var1 instanceof ArrayType) {
            return -3;
         } else {
            return swappedCompareResult(var1.compare(this));
         }
      } else {
         char var7 = this.signature.charAt(0);
         String var2 = var1.getName();
         if(var2 == null) {
            return -1;
         } else {
            byte var3 = 0;
            switch(var7) {
            case 66:
               var3 = 65;
               break;
            case 68:
               var3 = 73;
               break;
            case 70:
               var3 = 72;
               break;
            case 73:
               var3 = 67;
               break;
            case 74:
               var3 = 68;
               break;
            case 83:
               var3 = 66;
               break;
            case 86:
               return 1;
            case 90:
               var4 = var5;
               if(var2.equals("java.lang.Boolean")) {
                  return var4;
               }
            case 67:
               if(var2.equals("java.lang.Character")) {
                  return 0;
               }
            }

            if(var3 != 0) {
               char var6 = findInHierarchy(var2);
               if(var6 != 0) {
                  var4 = var5;
                  if(var6 != var3) {
                     if(var6 < var3) {
                        return 1;
                     }

                     return -1;
                  }

                  return var4;
               }
            }

            if(!var2.equals("java.lang.Object") && var1 != toStringType) {
               return -3;
            } else {
               return -1;
            }
         }
      }
   }

   public void emitCoerceFromObject(CodeAttr var1) {
      char var2;
      if(this.signature != null && this.signature.length() == 1) {
         var2 = this.signature.charAt(0);
      } else {
         var2 = 32;
      }

      if(var2 == 90) {
         var1.emitCheckcast(javalangBooleanType);
         var1.emitInvokeVirtual(booleanValue_method);
      } else if(var2 == 86) {
         var1.emitPop(1);
      } else {
         var1.emitCheckcast(javalangNumberType);
         if(var2 != 73 && var2 != 83 && var2 != 66) {
            if(var2 == 74) {
               var1.emitInvokeVirtual(longValue_method);
            } else if(var2 == 68) {
               var1.emitInvokeVirtual(doubleValue_method);
            } else if(var2 == 70) {
               var1.emitInvokeVirtual(floatValue_method);
            } else {
               super.emitCoerceFromObject(var1);
            }
         } else {
            var1.emitInvokeVirtual(intValue_method);
         }
      }
   }

   public void emitCoerceToObject(CodeAttr var1) {
      char var4 = this.getSignature().charAt(0);
      ClassType var3 = this.boxedType();
      if(var4 == 90) {
         var1.emitIfIntNotZero();
         var1.emitGetStatic(var3.getDeclaredField("TRUE"));
         var1.emitElse();
         var1.emitGetStatic(var3.getDeclaredField("FALSE"));
         var1.emitFi();
      } else {
         Type[] var2 = new Type[]{this};
         Method var5;
         if(var1.getMethod().getDeclaringClass().classfileFormatVersion >= 3211264) {
            var5 = var3.getDeclaredMethod("valueOf", var2);
         } else {
            var5 = var3.getDeclaredMethod("<init>", var2);
            var1.emitNew(var3);
            var1.emitDupX();
            var1.emitSwap();
         }

         var1.emitInvoke(var5);
      }
   }

   public void emitIsInstance(CodeAttr var1) {
      char var2;
      if(this.signature != null && this.signature.length() == 1) {
         var2 = this.signature.charAt(0);
      } else {
         var2 = 32;
      }

      if(var2 == 90) {
         javalangBooleanType.emitIsInstance(var1);
      } else if(var2 == 86) {
         var1.emitPop(1);
         var1.emitPushInt(1);
      } else {
         javalangNumberType.emitIsInstance(var1);
      }
   }

   public Type promotedType() {
      switch(this.signature.charAt(0)) {
      case 66:
      case 67:
      case 73:
      case 83:
      case 90:
         return Type.intType;
      default:
         return this.getImplementationType();
      }
   }
}
