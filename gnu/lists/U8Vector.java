package gnu.lists;

import gnu.lists.ByteVector;
import gnu.lists.Convert;
import gnu.lists.Sequence;

public class U8Vector extends ByteVector {

   public U8Vector() {
      this.data = ByteVector.empty;
   }

   public U8Vector(int var1) {
      this.data = new byte[var1];
      this.size = var1;
   }

   public U8Vector(int var1, byte var2) {
      byte[] var3 = new byte[var1];
      this.data = var3;
      this.size = var1;

      while(true) {
         --var1;
         if(var1 < 0) {
            return;
         }

         var3[var1] = var2;
      }
   }

   public U8Vector(Sequence var1) {
      this.data = new byte[var1.size()];
      this.addAll(var1);
   }

   public U8Vector(byte[] var1) {
      this.data = var1;
      this.size = var1.length;
   }

   public int compareTo(Object var1) {
      return compareToInt(this, (U8Vector)var1);
   }

   public final Object get(int var1) {
      if(var1 > this.size) {
         throw new IndexOutOfBoundsException();
      } else {
         return Convert.toObjectUnsigned((byte)this.data[var1]);
      }
   }

   public final Object getBuffer(int var1) {
      return Convert.toObjectUnsigned((byte)this.data[var1]);
   }

   public int getElementKind() {
      return 17;
   }

   public String getTag() {
      return "u8";
   }

   public final int intAtBuffer(int var1) {
      return this.data[var1] & 255;
   }

   public Object setBuffer(int var1, Object var2) {
      byte var3 = this.data[var1];
      this.data[var1] = Convert.toByteUnsigned(var2);
      return Convert.toObjectUnsigned((byte)var3);
   }
}
