package gnu.expr;

import gnu.lists.Convert;
import gnu.math.DFloNum;
import gnu.math.IntNum;
import gnu.text.Char;

public class KawaConvert extends Convert {

   public static Convert instance = new KawaConvert();


   public static Convert getInstance() {
      return instance;
   }

   public static void setInstance(Convert var0) {
      instance = var0;
   }

   public Object byteToObject(byte var1) {
      return IntNum.make(var1);
   }

   public Object byteToObjectUnsigned(byte var1) {
      return IntNum.make(var1 & 255);
   }

   public Object charToObject(char var1) {
      return Char.make(var1);
   }

   public Object doubleToObject(double var1) {
      return DFloNum.make(var1);
   }

   public Object floatToObject(float var1) {
      return DFloNum.make((double)var1);
   }

   public Object intToObject(int var1) {
      return IntNum.make(var1);
   }

   public Object intToObjectUnsigned(int var1) {
      return IntNum.make((long)var1 & 4294967295L);
   }

   public Object longToObject(long var1) {
      return IntNum.make(var1);
   }

   public Object longToObjectUnsigned(long var1) {
      return IntNum.makeU(var1);
   }

   public char objectToChar(Object var1) {
      return ((Char)var1).charValue();
   }

   public Object shortToObject(short var1) {
      return IntNum.make(var1);
   }

   public Object shortToObjectUnsigned(short var1) {
      return IntNum.make('\uffff' & var1);
   }
}
