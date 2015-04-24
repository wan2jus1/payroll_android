package gnu.lists;


public class Convert {

   public static Convert instance = new Convert();


   public static Convert getInstance() {
      return instance;
   }

   public static double parseDouble(String var0) {
      return Double.parseDouble(var0);
   }

   public static void setInstance(Convert var0) {
      instance = var0;
   }

   public static boolean toBoolean(Object var0) {
      return instance.objectToBoolean(var0);
   }

   public static byte toByte(Object var0) {
      return instance.objectToByte(var0);
   }

   public static byte toByteUnsigned(Object var0) {
      return instance.objectToByteUnsigned(var0);
   }

   public static char toChar(Object var0) {
      return instance.objectToChar(var0);
   }

   public static double toDouble(Object var0) {
      return instance.objectToDouble(var0);
   }

   public static float toFloat(Object var0) {
      return instance.objectToFloat(var0);
   }

   public static int toInt(Object var0) {
      return instance.objectToInt(var0);
   }

   public static int toIntUnsigned(Object var0) {
      return instance.objectToIntUnsigned(var0);
   }

   public static long toLong(Object var0) {
      return instance.objectToLong(var0);
   }

   public static long toLongUnsigned(Object var0) {
      return instance.objectToLongUnsigned(var0);
   }

   public static Object toObject(byte var0) {
      return instance.byteToObject(var0);
   }

   public static Object toObject(char var0) {
      return instance.charToObject(var0);
   }

   public static Object toObject(double var0) {
      return instance.doubleToObject(var0);
   }

   public static Object toObject(float var0) {
      return instance.floatToObject(var0);
   }

   public static Object toObject(int var0) {
      return instance.intToObject(var0);
   }

   public static Object toObject(long var0) {
      return instance.longToObject(var0);
   }

   public static Object toObject(short var0) {
      return instance.shortToObject(var0);
   }

   public static Object toObject(boolean var0) {
      return instance.booleanToObject(var0);
   }

   public static Object toObjectUnsigned(byte var0) {
      return instance.byteToObjectUnsigned(var0);
   }

   public static Object toObjectUnsigned(int var0) {
      return instance.intToObjectUnsigned(var0);
   }

   public static Object toObjectUnsigned(long var0) {
      return instance.longToObjectUnsigned(var0);
   }

   public static Object toObjectUnsigned(short var0) {
      return instance.shortToObjectUnsigned(var0);
   }

   public static short toShort(Object var0) {
      return instance.objectToShort(var0);
   }

   public static short toShortUnsigned(Object var0) {
      return instance.objectToShortUnsigned(var0);
   }

   public Object booleanToObject(boolean var1) {
      return var1?Boolean.TRUE:Boolean.FALSE;
   }

   public Object byteToObject(byte var1) {
      return new Byte(var1);
   }

   public Object byteToObjectUnsigned(byte var1) {
      return new Integer(var1 & 255);
   }

   public Object charToObject(char var1) {
      return new Character(var1);
   }

   public Object doubleToObject(double var1) {
      return new Double(var1);
   }

   public Object floatToObject(float var1) {
      return new Float(var1);
   }

   public Object intToObject(int var1) {
      return new Integer(var1);
   }

   public Object intToObjectUnsigned(int var1) {
      return var1 >= 0?new Integer(var1):new Long((long)var1 & 4294967295L);
   }

   public Object longToObject(long var1) {
      return new Long(var1);
   }

   public Object longToObjectUnsigned(long var1) {
      return new Long(var1);
   }

   public boolean objectToBoolean(Object var1) {
      return !(var1 instanceof Boolean) || ((Boolean)var1).booleanValue();
   }

   public byte objectToByte(Object var1) {
      return ((Number)var1).byteValue();
   }

   public byte objectToByteUnsigned(Object var1) {
      return ((Number)var1).byteValue();
   }

   public char objectToChar(Object var1) {
      return ((Character)var1).charValue();
   }

   public double objectToDouble(Object var1) {
      return ((Number)var1).doubleValue();
   }

   public float objectToFloat(Object var1) {
      return ((Number)var1).floatValue();
   }

   public int objectToInt(Object var1) {
      return ((Number)var1).intValue();
   }

   public int objectToIntUnsigned(Object var1) {
      return ((Number)var1).intValue();
   }

   public long objectToLong(Object var1) {
      return ((Number)var1).longValue();
   }

   public long objectToLongUnsigned(Object var1) {
      return ((Number)var1).longValue();
   }

   public short objectToShort(Object var1) {
      return ((Number)var1).shortValue();
   }

   public short objectToShortUnsigned(Object var1) {
      return ((Number)var1).shortValue();
   }

   public Object shortToObject(short var1) {
      return new Short(var1);
   }

   public Object shortToObjectUnsigned(short var1) {
      return new Integer('\uffff' & var1);
   }
}
