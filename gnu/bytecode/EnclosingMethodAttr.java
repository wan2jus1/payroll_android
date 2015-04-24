package gnu.bytecode;

import gnu.bytecode.Attribute;
import gnu.bytecode.ClassType;
import gnu.bytecode.ClassTypeWriter;
import gnu.bytecode.ConstantPool;
import gnu.bytecode.CpoolClass;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;
import java.io.DataOutputStream;
import java.io.IOException;

public class EnclosingMethodAttr extends Attribute {

   int class_index;
   Method method;
   int method_index;


   public EnclosingMethodAttr(int var1, int var2, ClassType var3) {
      this(var3);
      this.class_index = var1;
      this.method_index = var2;
   }

   public EnclosingMethodAttr(ClassType var1) {
      super("EnclosingMethod");
      this.addToFrontOf(var1);
   }

   public static EnclosingMethodAttr getFirstEnclosingMethod(Attribute var0) {
      while(var0 != null && !(var0 instanceof EnclosingMethodAttr)) {
         var0 = var0.next;
      }

      return (EnclosingMethodAttr)var0;
   }

   public void assignConstants(ClassType var1) {
      super.assignConstants(var1);
      if(this.method != null) {
         ConstantPool var2 = var1.getConstants();
         this.class_index = var2.addClass((ObjectType)this.method.getDeclaringClass()).getIndex();
         this.method_index = var2.addNameAndType((Method)this.method).getIndex();
      }

   }

   public int getLength() {
      return 4;
   }

   public void print(ClassTypeWriter var1) {
      ConstantPool var2 = ((ClassType)this.container).getConstants();
      var1.print("Attribute \"");
      var1.print(this.getName());
      var1.print("\", length:");
      var1.println(this.getLength());
      var1.print("  class: ");
      var1.printOptionalIndex(this.class_index);
      var1.print(((CpoolClass)var2.getForced(this.class_index, 7)).getStringName());
      var1.print(", method: ");
      var1.printOptionalIndex(this.method_index);
      var2.getForced(this.method_index, 12).print(var1, 0);
      var1.println();
   }

   public void write(DataOutputStream var1) throws IOException {
      var1.writeShort(this.class_index);
      var1.writeShort(this.method_index);
   }
}
