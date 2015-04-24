package gnu.kawa.xml;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.Target;
import gnu.expr.TypeValue;
import gnu.kawa.reflect.InstanceOf;
import gnu.kawa.xml.Base64Binary;
import gnu.kawa.xml.BinaryObject;
import gnu.kawa.xml.HexBinary;
import gnu.kawa.xml.KNode;
import gnu.kawa.xml.Nodes;
import gnu.kawa.xml.UntypedAtomic;
import gnu.kawa.xml.XTimeType;
import gnu.lists.Consumer;
import gnu.lists.SeqPosition;
import gnu.mapping.Procedure;
import gnu.mapping.Values;
import gnu.math.DateTime;
import gnu.math.Duration;
import gnu.math.IntNum;
import gnu.math.RealNum;
import gnu.math.Unit;
import gnu.text.Path;
import gnu.text.Printable;
import gnu.text.URIPath;
import gnu.xml.TextUtils;
import java.math.BigDecimal;

public class XDataType extends Type implements TypeValue {

   public static final int ANY_ATOMIC_TYPE_CODE = 3;
   public static final int ANY_SIMPLE_TYPE_CODE = 2;
   public static final int ANY_URI_TYPE_CODE = 33;
   public static final int BASE64_BINARY_TYPE_CODE = 34;
   public static final int BOOLEAN_TYPE_CODE = 31;
   public static final int BYTE_TYPE_CODE = 11;
   public static final int DATE_TIME_TYPE_CODE = 20;
   public static final int DATE_TYPE_CODE = 21;
   public static final int DAY_TIME_DURATION_TYPE_CODE = 30;
   public static final BigDecimal DECIMAL_ONE = BigDecimal.valueOf(1L);
   public static final int DECIMAL_TYPE_CODE = 4;
   public static final Double DOUBLE_ONE = makeDouble(1.0D);
   public static final int DOUBLE_TYPE_CODE = 19;
   public static final Double DOUBLE_ZERO = makeDouble(0.0D);
   public static final int DURATION_TYPE_CODE = 28;
   public static final int ENTITY_TYPE_CODE = 47;
   public static final Float FLOAT_ONE = makeFloat(1.0F);
   public static final int FLOAT_TYPE_CODE = 18;
   public static final Float FLOAT_ZERO = makeFloat(0.0F);
   public static final int G_DAY_TYPE_CODE = 26;
   public static final int G_MONTH_DAY_TYPE_CODE = 25;
   public static final int G_MONTH_TYPE_CODE = 27;
   public static final int G_YEAR_MONTH_TYPE_CODE = 23;
   public static final int G_YEAR_TYPE_CODE = 24;
   public static final int HEX_BINARY_TYPE_CODE = 35;
   public static final int IDREF_TYPE_CODE = 46;
   public static final int ID_TYPE_CODE = 45;
   public static final int INTEGER_TYPE_CODE = 5;
   public static final int INT_TYPE_CODE = 9;
   public static final int LANGUAGE_TYPE_CODE = 41;
   public static final int LONG_TYPE_CODE = 8;
   public static final int NAME_TYPE_CODE = 43;
   public static final int NCNAME_TYPE_CODE = 44;
   public static final int NEGATIVE_INTEGER_TYPE_CODE = 7;
   public static final int NMTOKEN_TYPE_CODE = 42;
   public static final int NONNEGATIVE_INTEGER_TYPE_CODE = 12;
   public static final int NON_POSITIVE_INTEGER_TYPE_CODE = 6;
   public static final int NORMALIZED_STRING_TYPE_CODE = 39;
   public static final int NOTATION_TYPE_CODE = 36;
   public static final XDataType NotationType = new XDataType("NOTATION", ClassType.make("gnu.kawa.xml.Notation"), 36);
   public static final int POSITIVE_INTEGER_TYPE_CODE = 17;
   public static final int QNAME_TYPE_CODE = 32;
   public static final int SHORT_TYPE_CODE = 10;
   public static final int STRING_TYPE_CODE = 38;
   public static final int TIME_TYPE_CODE = 22;
   public static final int TOKEN_TYPE_CODE = 40;
   public static final int UNSIGNED_BYTE_TYPE_CODE = 16;
   public static final int UNSIGNED_INT_TYPE_CODE = 14;
   public static final int UNSIGNED_LONG_TYPE_CODE = 13;
   public static final int UNSIGNED_SHORT_TYPE_CODE = 15;
   public static final int UNTYPED_ATOMIC_TYPE_CODE = 37;
   public static final int UNTYPED_TYPE_CODE = 48;
   public static final int YEAR_MONTH_DURATION_TYPE_CODE = 29;
   public static final XDataType anyAtomicType = new XDataType("anyAtomicType", Type.objectType, 3);
   public static final XDataType anySimpleType = new XDataType("anySimpleType", Type.objectType, 2);
   public static final XDataType anyURIType = new XDataType("anyURI", ClassType.make("gnu.text.Path"), 33);
   public static final XDataType base64BinaryType = new XDataType("base64Binary", ClassType.make("gnu.kawa.xml.Base64Binary"), 34);
   public static final XDataType booleanType = new XDataType("boolean", Type.booleanType, 31);
   public static final XDataType dayTimeDurationType = new XDataType("dayTimeDuration", ClassType.make("gnu.math.Duration"), 30);
   public static final XDataType decimalType = new XDataType("decimal", ClassType.make("java.lang.Number"), 4);
   public static final XDataType doubleType = new XDataType("double", ClassType.make("java.lang.Double"), 19);
   public static final XDataType durationType = new XDataType("duration", ClassType.make("gnu.math.Duration"), 28);
   public static final XDataType floatType = new XDataType("float", ClassType.make("java.lang.Float"), 18);
   public static final XDataType hexBinaryType = new XDataType("hexBinary", ClassType.make("gnu.kawa.xml.HexBinary"), 35);
   public static final XDataType stringStringType = new XDataType("String", ClassType.make("java.lang.String"), 38);
   public static final XDataType stringType = new XDataType("string", ClassType.make("java.lang.CharSequence"), 38);
   public static final XDataType untypedAtomicType = new XDataType("string", ClassType.make("gnu.kawa.xml.UntypedAtomic"), 37);
   public static final XDataType untypedType = new XDataType("untyped", Type.objectType, 48);
   public static final XDataType yearMonthDurationType = new XDataType("yearMonthDuration", ClassType.make("gnu.math.Duration"), 29);
   XDataType baseType;
   Type implementationType;
   Object name;
   int typeCode;


   public XDataType(Object var1, Type var2, int var3) {
      super(var2);
      this.name = var1;
      if(var1 != null) {
         this.setName(var1.toString());
      }

      this.implementationType = var2;
      this.typeCode = var3;
   }

   public static Double makeDouble(double var0) {
      return Double.valueOf(var0);
   }

   public static Float makeFloat(float var0) {
      return Float.valueOf(var0);
   }

   public Object cast(Object var1) {
      var1 = KNode.atomicValue(var1);
      if(var1 instanceof UntypedAtomic) {
         if(this.typeCode != 37) {
            return this.valueOf(var1.toString());
         } else {
            return var1;
         }
      } else if(var1 instanceof String) {
         return this.valueOf(var1.toString());
      } else {
         int var4;
         switch(this.typeCode) {
         case 4:
            if(var1 instanceof BigDecimal) {
               return var1;
            }

            if(var1 instanceof RealNum) {
               return ((RealNum)var1).asBigDecimal();
            }

            if(var1 instanceof Float || var1 instanceof Double) {
               return BigDecimal.valueOf(((Number)var1).doubleValue());
            }

            if(var1 instanceof Boolean) {
               IntNum var10;
               if(((Boolean)var1).booleanValue()) {
                  var10 = IntNum.one();
               } else {
                  var10 = IntNum.zero();
               }

               return this.cast(var10);
            }
         case 5:
         case 6:
         case 7:
         case 8:
         case 9:
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         case 16:
         case 17:
         case 32:
         case 36:
         default:
            break;
         case 18:
            if(var1 instanceof Float) {
               return var1;
            }

            if(var1 instanceof Number) {
               return makeFloat(((Number)var1).floatValue());
            }

            if(var1 instanceof Boolean) {
               Float var9;
               if(((Boolean)var1).booleanValue()) {
                  var9 = FLOAT_ONE;
               } else {
                  var9 = FLOAT_ZERO;
               }

               return var9;
            }
            break;
         case 19:
            if(var1 instanceof Double) {
               return var1;
            }

            if(var1 instanceof Number) {
               return makeDouble(((Number)var1).doubleValue());
            }

            if(var1 instanceof Boolean) {
               Double var8;
               if(((Boolean)var1).booleanValue()) {
                  var8 = DOUBLE_ONE;
               } else {
                  var8 = DOUBLE_ZERO;
               }

               return var8;
            }
            break;
         case 20:
         case 21:
         case 22:
            if(var1 instanceof DateTime) {
               var4 = XTimeType.components(((XTimeType)this).typeCode);
               return ((DateTime)var1).cast(var4);
            }
            break;
         case 23:
         case 24:
         case 25:
         case 26:
         case 27:
            if(var1 instanceof DateTime) {
               var4 = XTimeType.components(((XTimeType)this).typeCode);
               DateTime var7 = (DateTime)var1;
               int var5 = var7.components();
               if(var4 != var5 && (var5 & 14) != 14) {
                  throw new ClassCastException();
               }

               return var7.cast(var4);
            }
            break;
         case 28:
            return this.castToDuration(var1, Unit.duration);
         case 29:
            return this.castToDuration(var1, Unit.month);
         case 30:
            return this.castToDuration(var1, Unit.second);
         case 31:
            Boolean var6;
            if(var1 instanceof Boolean) {
               if(((Boolean)var1).booleanValue()) {
                  var6 = Boolean.TRUE;
               } else {
                  var6 = Boolean.FALSE;
               }

               return var6;
            }

            if(var1 instanceof Number) {
               double var2 = ((Number)var1).doubleValue();
               if(var2 != 0.0D && !Double.isNaN(var2)) {
                  var6 = Boolean.TRUE;
               } else {
                  var6 = Boolean.FALSE;
               }

               return var6;
            }
            break;
         case 33:
            return URIPath.makeURI(var1);
         case 34:
            if(var1 instanceof BinaryObject) {
               return new Base64Binary(((BinaryObject)var1).getBytes());
            }
         case 35:
            if(var1 instanceof BinaryObject) {
               return new HexBinary(((BinaryObject)var1).getBytes());
            }
            break;
         case 37:
            return new UntypedAtomic(TextUtils.stringValue(var1));
         case 38:
            return TextUtils.asString(var1);
         }

         return this.coerceFromObject(var1);
      }
   }

   Duration castToDuration(Object var1, Unit var2) {
      if(var1 instanceof Duration) {
         Duration var7 = (Duration)var1;
         if(var7.unit() == var2) {
            return var7;
         } else {
            int var3 = var7.getTotalMonths();
            long var5 = var7.getTotalSeconds();
            int var4 = var7.getNanoSecondsOnly();
            if(var2 == Unit.second) {
               var3 = 0;
            }

            if(var2 == Unit.month) {
               var5 = 0L;
               var4 = 0;
            }

            return Duration.make(var3, var5, var4, var2);
         }
      } else {
         return (Duration)this.coerceFromObject(var1);
      }
   }

   public boolean castable(Object var1) {
      try {
         this.cast(var1);
         return true;
      } catch (Throwable var2) {
         return false;
      }
   }

   public Object coerceFromObject(Object var1) {
      if(!this.isInstance(var1)) {
         throw new ClassCastException("cannot cast " + var1 + " to " + this.name);
      } else {
         return var1;
      }
   }

   public int compare(Type var1) {
      return this != var1 && (this != stringStringType || var1 != stringType) && (this != stringType || var1 != stringStringType)?this.implementationType.compare(var1):0;
   }

   public Expression convertValue(Expression var1) {
      return null;
   }

   public void emitCoerceFromObject(CodeAttr var1) {
      Compilation.getCurrent().compileConstant(this, Target.pushObject);
      Method var2 = ClassType.make("gnu.kawa.xml.XDataType").getDeclaredMethod("coerceFromObject", 1);
      var1.emitSwap();
      var1.emitInvokeVirtual(var2);
      this.implementationType.emitCoerceFromObject(var1);
   }

   public void emitCoerceToObject(CodeAttr var1) {
      if(this.typeCode == 31) {
         this.implementationType.emitCoerceToObject(var1);
      } else {
         super.emitCoerceToObject(var1);
      }
   }

   public void emitIsInstance(Variable var1, Compilation var2, Target var3) {
      InstanceOf.emitIsInstance(this, var1, var2, var3);
   }

   public void emitTestIf(Variable var1, Declaration var2, Compilation var3) {
      CodeAttr var4 = var3.getCode();
      if(this.typeCode == 31) {
         if(var1 != null) {
            var4.emitLoad(var1);
         }

         Type.javalangBooleanType.emitIsInstance(var4);
         var4.emitIfIntNotZero();
         if(var2 != null) {
            var4.emitLoad(var1);
            Type.booleanType.emitCoerceFromObject(var4);
            var2.compileStore(var3);
         }
      } else {
         var3.compileConstant(this, Target.pushObject);
         if(var1 == null) {
            var4.emitSwap();
         } else {
            var4.emitLoad(var1);
         }

         var4.emitInvokeVirtual(Compilation.typeType.getDeclaredMethod("isInstance", 1));
         var4.emitIfIntNotZero();
         if(var2 != null) {
            var4.emitLoad(var1);
            this.emitCoerceFromObject(var4);
            var2.compileStore(var3);
            return;
         }
      }

   }

   public Procedure getConstructor() {
      return null;
   }

   public Type getImplementationType() {
      return this.implementationType;
   }

   public Class getReflectClass() {
      return this.implementationType.getReflectClass();
   }

   public boolean isInstance(Object var1) {
      boolean var3 = false;
      boolean var4 = true;
      boolean var2 = var4;
      switch(this.typeCode) {
      case 2:
         if(!(var1 instanceof SeqPosition)) {
            var2 = var4;
            if(!(var1 instanceof Nodes)) {
               break;
            }
         }

         return false;
      case 3:
         if(!(var1 instanceof Values)) {
            var2 = var4;
            if(!(var1 instanceof SeqPosition)) {
               break;
            }
         }

         return false;
      case 4:
         if(!(var1 instanceof BigDecimal)) {
            var2 = var3;
            if(!(var1 instanceof IntNum)) {
               return var2;
            }
         }

         var2 = true;
         return var2;
      case 18:
         return var1 instanceof Float;
      case 19:
         return var1 instanceof Double;
      case 28:
         return var1 instanceof Duration;
      case 29:
         if(var1 instanceof Duration) {
            var2 = var4;
            if(((Duration)var1).unit() == Unit.month) {
               break;
            }
         }

         return false;
      case 30:
         if(var1 instanceof Duration) {
            var2 = var4;
            if(((Duration)var1).unit() == Unit.second) {
               break;
            }
         }

         return false;
      case 31:
         return var1 instanceof Boolean;
      case 33:
         return var1 instanceof Path;
      case 37:
         return var1 instanceof UntypedAtomic;
      case 38:
         return var1 instanceof CharSequence;
      case 48:
         break;
      default:
         var2 = super.isInstance(var1);
      }

      return var2;
   }

   public void print(Object var1, Consumer var2) {
      if(var1 instanceof Printable) {
         ((Printable)var1).print(var2);
      } else {
         var2.write(this.toString(var1));
      }
   }

   public String toString(Object var1) {
      return var1.toString();
   }

   public Object valueOf(String var1) {
      switch(this.typeCode) {
      case 4:
         var1 = var1.trim();
         int var3 = var1.length();

         char var4;
         do {
            --var3;
            if(var3 < 0) {
               return new BigDecimal(var1);
            }

            var4 = var1.charAt(var3);
         } while(var4 != 101 && var4 != 69);

         throw new IllegalArgumentException("not a valid decimal: \'" + var1 + "\'");
      case 18:
      case 19:
         String var2 = var1.trim();
         if("INF".equals(var2)) {
            var1 = "Infinity";
         } else {
            var1 = var2;
            if("-INF".equals(var2)) {
               var1 = "-Infinity";
            }
         }

         if(this.typeCode == 18) {
            return Float.valueOf(var1);
         }

         return Double.valueOf(var1);
      case 28:
         return Duration.parseDuration(var1);
      case 29:
         return Duration.parseYearMonthDuration(var1);
      case 30:
         return Duration.parseDayTimeDuration(var1);
      case 31:
         var1 = var1.trim();
         if(!var1.equals("true") && !var1.equals("1")) {
            if(!var1.equals("false") && !var1.equals("0")) {
               throw new IllegalArgumentException("not a valid boolean: \'" + var1 + "\'");
            }

            return Boolean.FALSE;
         }

         return Boolean.TRUE;
      case 33:
         return URIPath.makeURI(TextUtils.replaceWhitespace(var1, true));
      case 34:
         return Base64Binary.valueOf(var1);
      case 35:
         return HexBinary.valueOf(var1);
      case 37:
         return new UntypedAtomic(var1);
      case 38:
         return var1;
      default:
         throw new RuntimeException("valueOf not implemented for " + this.name);
      }
   }
}
