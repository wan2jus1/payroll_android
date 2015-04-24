package gnu.bytecode;

import gnu.bytecode.ConstantPool;
import gnu.bytecode.CpoolUtf8;
import gnu.bytecode.Type;

public class Location {

   protected String name;
   int name_index;
   int signature_index;
   protected Type type;


   public final String getName() {
      return this.name;
   }

   public final String getSignature() {
      return this.type.getSignature();
   }

   public final Type getType() {
      return this.type;
   }

   public final void setName(int var1, ConstantPool var2) {
      if(var1 <= 0) {
         this.name = null;
      } else {
         this.name = ((CpoolUtf8)var2.getForced(var1, 1)).string;
      }

      this.name_index = var1;
   }

   public final void setName(String var1) {
      this.name = var1;
   }

   public void setSignature(int var1, ConstantPool var2) {
      CpoolUtf8 var3 = (CpoolUtf8)var2.getForced(var1, 1);
      this.signature_index = var1;
      this.type = Type.signatureToType(var3.string);
   }

   public final void setType(Type var1) {
      this.type = var1;
   }
}
