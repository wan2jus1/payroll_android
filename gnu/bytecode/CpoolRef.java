package gnu.bytecode;

import gnu.bytecode.ClassTypeWriter;
import gnu.bytecode.ConstantPool;
import gnu.bytecode.CpoolClass;
import gnu.bytecode.CpoolEntry;
import gnu.bytecode.CpoolNameAndType;
import java.io.DataOutputStream;
import java.io.IOException;

public class CpoolRef extends CpoolEntry {

   CpoolClass clas;
   CpoolNameAndType nameAndType;
   int tag;


   CpoolRef(int var1) {
      this.tag = var1;
   }

   CpoolRef(ConstantPool var1, int var2, int var3, CpoolClass var4, CpoolNameAndType var5) {
      super(var1, var2);
      this.tag = var3;
      this.clas = var4;
      this.nameAndType = var5;
   }

   static final int hashCode(CpoolClass var0, CpoolNameAndType var1) {
      return var0.hashCode() ^ var1.hashCode();
   }

   public final CpoolClass getCpoolClass() {
      return this.clas;
   }

   public final CpoolNameAndType getNameAndType() {
      return this.nameAndType;
   }

   public int getTag() {
      return this.tag;
   }

   public int hashCode() {
      if(this.hash == 0) {
         this.hash = hashCode(this.clas, this.nameAndType);
      }

      return this.hash;
   }

   public void print(ClassTypeWriter var1, int var2) {
      String var3;
      switch(this.tag) {
      case 9:
         var3 = "Field";
         break;
      case 10:
         var3 = "Method";
         break;
      case 11:
         var3 = "InterfaceMethod";
         break;
      default:
         var3 = "<Unknown>Ref";
      }

      if(var2 > 0) {
         var1.print(var3);
         if(var2 == 2) {
            var1.print(" class: ");
            var1.printOptionalIndex(this.clas);
         } else {
            var1.print(' ');
         }
      }

      this.clas.print(var1, 0);
      if(var2 < 2) {
         var1.print('.');
      } else {
         var1.print(" name_and_type: ");
         var1.printOptionalIndex(this.nameAndType);
         var1.print('<');
      }

      this.nameAndType.print(var1, 0);
      if(var2 == 2) {
         var1.print('>');
      }

   }

   void write(DataOutputStream var1) throws IOException {
      var1.writeByte(this.tag);
      var1.writeShort(this.clas.index);
      var1.writeShort(this.nameAndType.index);
   }
}
