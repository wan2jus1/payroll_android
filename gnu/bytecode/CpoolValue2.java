package gnu.bytecode;

import gnu.bytecode.ClassTypeWriter;
import gnu.bytecode.ConstantPool;
import gnu.bytecode.CpoolEntry;
import java.io.DataOutputStream;
import java.io.IOException;

public class CpoolValue2 extends CpoolEntry {

   int tag;
   long value;


   CpoolValue2(int var1) {
      this.tag = var1;
   }

   CpoolValue2(ConstantPool var1, int var2, int var3, long var4) {
      super(var1, var3);
      this.tag = var2;
      this.value = var4;
      ++var1.count;
   }

   static int hashCode(long var0) {
      return (int)var0;
   }

   public int getTag() {
      return this.tag;
   }

   public final long getValue() {
      return this.value;
   }

   public int hashCode() {
      if(this.hash == 0) {
         this.hash = hashCode(this.value);
      }

      return this.hash;
   }

   public void print(ClassTypeWriter var1, int var2) {
      if(this.tag == 5) {
         if(var2 > 0) {
            var1.print("Long ");
         }

         var1.print(this.value);
         if(var2 > 1 && this.value != 0L) {
            var1.print("=0x");
            var1.print(Long.toHexString(this.value));
         }
      } else {
         if(var2 > 0) {
            var1.print("Double ");
         }

         var1.print(Double.longBitsToDouble(this.value));
         if(var2 > 1) {
            var1.print("=0x");
            var1.print(Long.toHexString(this.value));
            return;
         }
      }

   }

   void write(DataOutputStream var1) throws IOException {
      var1.writeByte(this.tag);
      var1.writeLong(this.value);
   }
}
