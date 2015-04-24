package gnu.bytecode;

import gnu.bytecode.ClassType;
import gnu.bytecode.ClassTypeWriter;
import gnu.bytecode.ConstantPool;
import gnu.bytecode.CpoolEntry;
import gnu.bytecode.CpoolUtf8;
import gnu.bytecode.ObjectType;
import gnu.bytecode.Type;
import java.io.DataOutputStream;
import java.io.IOException;

public class CpoolClass extends CpoolEntry {

   ObjectType clas;
   CpoolUtf8 name;


   CpoolClass() {
   }

   CpoolClass(ConstantPool var1, int var2, CpoolUtf8 var3) {
      super(var1, var2);
      this.name = var3;
   }

   static final int hashCode(CpoolUtf8 var0) {
      return var0.hashCode() ^ 3855;
   }

   public final String getClassName() {
      return this.name.string.replace('/', '.');
   }

   public final ObjectType getClassType() {
      ObjectType var1 = this.clas;
      if(var1 != null) {
         return var1;
      } else {
         String var2 = this.name.string;
         Object var3;
         if(var2.charAt(0) == 91) {
            var3 = (ObjectType)Type.signatureToType(var2);
         } else {
            var3 = ClassType.make(var2.replace('/', '.'));
         }

         this.clas = (ObjectType)var3;
         return (ObjectType)var3;
      }
   }

   public final CpoolUtf8 getName() {
      return this.name;
   }

   public final String getStringName() {
      return this.name.string;
   }

   public int getTag() {
      return 7;
   }

   public int hashCode() {
      if(this.hash == 0) {
         this.hash = hashCode(this.name);
      }

      return this.hash;
   }

   public void print(ClassTypeWriter var1, int var2) {
      if(var2 == 1) {
         var1.print("Class ");
      } else if(var2 > 1) {
         var1.print("Class name: ");
         var1.printOptionalIndex(this.name);
      }

      String var3 = this.name.string;
      var2 = var3.length();
      if(var2 > 1 && var3.charAt(0) == 91) {
         Type.printSignature(var3, 0, var2, var1);
      } else {
         var1.print(var3.replace('/', '.'));
      }
   }

   void write(DataOutputStream var1) throws IOException {
      var1.writeByte(7);
      var1.writeShort(this.name.index);
   }
}
