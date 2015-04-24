package gnu.bytecode;

import gnu.bytecode.ClassTypeWriter;
import gnu.bytecode.ConstantPool;
import gnu.bytecode.CpoolEntry;
import java.io.DataOutputStream;
import java.io.IOException;

public class CpoolValue1 extends CpoolEntry {

   int tag;
   int value;


   CpoolValue1(int var1) {
      this.tag = var1;
   }

   CpoolValue1(ConstantPool var1, int var2, int var3, int var4) {
      super(var1, var3);
      this.tag = var2;
      this.value = var4;
   }

   static int hashCode(int var0) {
      return var0;
   }

   public int getTag() {
      return this.tag;
   }

   public final int getValue() {
      return this.value;
   }

   public int hashCode() {
      if(this.hash == 0) {
         this.hash = this.value;
      }

      return this.hash;
   }

   public void print(ClassTypeWriter var1, int var2) {
      if(this.tag == 3) {
         if(var2 > 0) {
            var1.print("Integer ");
         }

         var1.print(this.value);
         if(var2 > 1 && this.value != 0) {
            var1.print("=0x");
            var1.print(Integer.toHexString(this.value));
         }
      } else {
         if(var2 > 0) {
            var1.print("Float ");
         }

         var1.print(Float.intBitsToFloat(this.value));
         if(var2 > 1) {
            var1.print("=0x");
            var1.print(Integer.toHexString(this.value));
            return;
         }
      }

   }

   void write(DataOutputStream var1) throws IOException {
      var1.writeByte(this.tag);
      var1.writeInt(this.value);
   }
}
