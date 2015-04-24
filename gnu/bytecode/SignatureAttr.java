package gnu.bytecode;

import gnu.bytecode.Attribute;
import gnu.bytecode.ClassType;
import gnu.bytecode.ClassTypeWriter;
import gnu.bytecode.CpoolUtf8;
import gnu.bytecode.Member;
import java.io.DataOutputStream;
import java.io.IOException;

public class SignatureAttr extends Attribute {

   String signature;
   int signature_index;


   public SignatureAttr(int var1, Member var2) {
      super("Signature");
      ClassType var3;
      if(var2 instanceof ClassType) {
         var3 = (ClassType)var2;
      } else {
         var3 = var2.getDeclaringClass();
      }

      this.signature = ((CpoolUtf8)var3.constants.getForced(var1, 1)).string;
      this.signature_index = var1;
   }

   public SignatureAttr(String var1) {
      super("Signature");
      this.signature = var1;
   }

   public void assignConstants(ClassType var1) {
      super.assignConstants(var1);
      if(this.signature_index == 0) {
         this.signature_index = var1.getConstants().addUtf8(this.signature).getIndex();
      }

   }

   public final int getLength() {
      return 2;
   }

   public final String getSignature() {
      return this.signature;
   }

   public void print(ClassTypeWriter var1) {
      super.print(var1);
      var1.print("  ");
      var1.printOptionalIndex(this.signature_index);
      var1.print('\"');
      var1.print(this.getSignature());
      var1.println('\"');
   }

   protected void setSignature(String var1) {
      this.signature = var1;
      this.signature_index = 0;
   }

   public void write(DataOutputStream var1) throws IOException {
      var1.writeShort(this.signature_index);
   }
}
