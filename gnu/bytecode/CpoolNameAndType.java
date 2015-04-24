package gnu.bytecode;

import gnu.bytecode.ClassTypeWriter;
import gnu.bytecode.ConstantPool;
import gnu.bytecode.CpoolEntry;
import gnu.bytecode.CpoolUtf8;
import java.io.DataOutputStream;
import java.io.IOException;

public class CpoolNameAndType extends CpoolEntry {

   CpoolUtf8 name;
   CpoolUtf8 type;


   CpoolNameAndType() {
   }

   CpoolNameAndType(ConstantPool var1, int var2, CpoolUtf8 var3, CpoolUtf8 var4) {
      super(var1, var2);
      this.name = var3;
      this.type = var4;
   }

   static final int hashCode(CpoolUtf8 var0, CpoolUtf8 var1) {
      return var0.hashCode() ^ var1.hashCode();
   }

   public final CpoolUtf8 getName() {
      return this.name;
   }

   public int getTag() {
      return 12;
   }

   public final CpoolUtf8 getType() {
      return this.type;
   }

   public int hashCode() {
      if(this.hash == 0) {
         this.hash = hashCode(this.name, this.type);
      }

      return this.hash;
   }

   public void print(ClassTypeWriter var1, int var2) {
      if(var2 == 1) {
         var1.print("NameAndType ");
      } else if(var2 > 1) {
         var1.print("NameAndType name: ");
         var1.printOptionalIndex(this.name);
      }

      var1.printName(this.name.string);
      if(var2 <= 1) {
         var1.print(' ');
      } else {
         var1.print(", signature: ");
         var1.printOptionalIndex(this.type);
      }

      var1.printSignature((String)this.type.string);
   }

   void write(DataOutputStream var1) throws IOException {
      var1.writeByte(12);
      var1.writeShort(this.name.index);
      var1.writeShort(this.type.index);
   }
}
