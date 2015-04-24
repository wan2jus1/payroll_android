package gnu.kawa.xml;

import gnu.bytecode.ClassType;
import gnu.kawa.functions.Arithmetic;
import gnu.kawa.xml.XDataType;
import gnu.kawa.xml.XInteger;
import gnu.math.IntNum;
import gnu.math.RealNum;
import java.math.BigDecimal;

public class XIntegerType extends XDataType {

   public static final XIntegerType byteType = new XIntegerType("byte", shortType, 11, IntNum.make(-128), IntNum.make(127));
   public static final XIntegerType intType = new XIntegerType("int", longType, 9, IntNum.make(Integer.MIN_VALUE), IntNum.make(Integer.MAX_VALUE));
   public static final XIntegerType integerType = new XIntegerType("integer", decimalType, 5, (IntNum)null, (IntNum)null);
   public static final XIntegerType longType = new XIntegerType("long", integerType, 8, IntNum.make(Long.MIN_VALUE), IntNum.make(Long.MAX_VALUE));
   public static final XIntegerType negativeIntegerType = new XIntegerType("negativeInteger", nonPositiveIntegerType, 7, (IntNum)null, IntNum.minusOne());
   public static final XIntegerType nonNegativeIntegerType = new XIntegerType("nonNegativeInteger", integerType, 12, IntNum.zero(), (IntNum)null);
   public static final XIntegerType nonPositiveIntegerType = new XIntegerType("nonPositiveInteger", integerType, 6, (IntNum)null, IntNum.zero());
   public static final XIntegerType positiveIntegerType = new XIntegerType("positiveInteger", nonNegativeIntegerType, 17, IntNum.one(), (IntNum)null);
   public static final XIntegerType shortType = new XIntegerType("short", intType, 10, IntNum.make(-32768), IntNum.make(32767));
   static ClassType typeIntNum = ClassType.make("gnu.math.IntNum");
   public static final XIntegerType unsignedByteType = new XIntegerType("unsignedByte", unsignedShortType, 16, IntNum.zero(), IntNum.make(255));
   public static final XIntegerType unsignedIntType = new XIntegerType("unsignedInt", unsignedLongType, 14, IntNum.zero(), IntNum.make(4294967295L));
   public static final XIntegerType unsignedLongType = new XIntegerType("unsignedLong", nonNegativeIntegerType, 13, IntNum.zero(), IntNum.valueOf("18446744073709551615"));
   public static final XIntegerType unsignedShortType = new XIntegerType("unsignedShort", unsignedIntType, 15, IntNum.zero(), IntNum.make('\uffff'));
   boolean isUnsignedType;
   public final IntNum maxValue;
   public final IntNum minValue;


   public XIntegerType(Object var1, XDataType var2, int var3, IntNum var4, IntNum var5) {
      super(var1, typeIntNum, var3);
      this.minValue = var4;
      this.maxValue = var5;
      this.baseType = var2;
   }

   public XIntegerType(String var1, XDataType var2, int var3, IntNum var4, IntNum var5) {
      this((Object)var1, var2, var3, var4, var5);
      this.isUnsignedType = var1.startsWith("unsigned");
   }

   public Object cast(Object var1) {
      if(var1 instanceof Boolean) {
         IntNum var2;
         if(((Boolean)var1).booleanValue()) {
            var2 = IntNum.one();
         } else {
            var2 = IntNum.zero();
         }

         return this.valueOf((IntNum)var2);
      } else {
         return var1 instanceof IntNum?this.valueOf((IntNum)((IntNum)var1)):(var1 instanceof BigDecimal?this.valueOf((IntNum)Arithmetic.asIntNum((BigDecimal)((BigDecimal)var1))):(var1 instanceof RealNum?this.valueOf((IntNum)((RealNum)var1).toExactInt(3)):(var1 instanceof Number?this.valueOf((IntNum)RealNum.toExactInt(((Number)var1).doubleValue(), 3)):super.cast(var1))));
      }
   }

   public Object coerceFromObject(Object var1) {
      return this.valueOf((IntNum)((IntNum)var1));
   }

   public boolean isInstance(Object var1) {
      if(var1 instanceof IntNum) {
         if(this == integerType) {
            return true;
         }

         if(var1 instanceof XInteger) {
            var1 = ((XInteger)var1).getIntegerType();
         } else {
            var1 = integerType;
         }

         while(var1 != null) {
            if(var1 == this) {
               return true;
            }

            var1 = ((XDataType)var1).baseType;
         }
      }

      return false;
   }

   public boolean isUnsignedType() {
      return this.isUnsignedType;
   }

   public IntNum valueOf(IntNum var1) {
      Object var2 = var1;
      if(this != integerType) {
         if(this.minValue != null && IntNum.compare(var1, this.minValue) < 0 || this.maxValue != null && IntNum.compare(var1, this.maxValue) > 0) {
            throw new ClassCastException("cannot cast " + var1 + " to " + this.name);
         }

         var2 = new XInteger(var1, this);
      }

      return (IntNum)var2;
   }

   public IntNum valueOf(String var1, int var2) {
      return this.valueOf((IntNum)IntNum.valueOf(var1.trim(), var2));
   }

   public Object valueOf(String var1) {
      return this.valueOf((IntNum)IntNum.valueOf(var1.trim(), 10));
   }
}
