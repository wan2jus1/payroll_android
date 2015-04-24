package gnu.bytecode;

import gnu.bytecode.ClassTypeWriter;
import gnu.bytecode.ConstantPool;
import gnu.bytecode.CpoolEntry;
import java.io.DataOutputStream;
import java.io.IOException;

public class CpoolUtf8 extends CpoolEntry {

   String string;


   CpoolUtf8() {
   }

   CpoolUtf8(ConstantPool var1, int var2, String var3) {
      super(var1, var2);
      this.string = var3;
   }

   public final String getString() {
      return this.string;
   }

   public int getTag() {
      return 1;
   }

   public int hashCode() {
      if(this.hash == 0) {
         this.hash = this.string.hashCode();
      }

      return this.hash;
   }

   public final void intern() {
      this.string = this.string.intern();
   }

   public void print(ClassTypeWriter var1, int var2) {
      if(var2 > 0) {
         var1.print("Utf8: ");
      }

      var1.printQuotedString(this.string);
   }

   void write(DataOutputStream var1) throws IOException {
      var1.writeByte(1);
      var1.writeUTF(this.string);
   }
}
