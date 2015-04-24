package gnu.bytecode;

import gnu.bytecode.ClassTypeWriter;
import gnu.bytecode.ConstantPool;
import gnu.bytecode.CpoolEntry;
import gnu.bytecode.CpoolUtf8;
import java.io.DataOutputStream;
import java.io.IOException;

public class CpoolString extends CpoolEntry {

   CpoolUtf8 str;


   CpoolString() {
   }

   CpoolString(ConstantPool var1, int var2, CpoolUtf8 var3) {
      super(var1, var2);
      this.str = var3;
   }

   static final int hashCode(CpoolUtf8 var0) {
      return var0.hashCode() ^ '\uf30f';
   }

   public final CpoolUtf8 getString() {
      return this.str;
   }

   public int getTag() {
      return 8;
   }

   public int hashCode() {
      if(this.hash == 0) {
         this.hash = hashCode(this.str);
      }

      return this.hash;
   }

   public void print(ClassTypeWriter var1, int var2) {
      if(var2 > 0) {
         var1.print("String ");
         if(var2 == 2) {
            var1.printOptionalIndex(this.str);
         }
      }

      var1.printConstantTersely(this.str.index, 1);
   }

   void write(DataOutputStream var1) throws IOException {
      var1.writeByte(8);
      var1.writeShort(this.str.index);
   }
}
