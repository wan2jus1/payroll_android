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

public class ExceptionsAttr extends Attribute {

   short[] exception_table;
   ClassType[] exceptions;


   public ExceptionsAttr(Method var1) {
      super("Exceptions");
      this.addToFrontOf(var1);
   }

   public void assignConstants(ClassType var1) {
      super.assignConstants(var1);
      ConstantPool var3 = var1.getConstants();
      int var2 = this.exceptions.length;
      this.exception_table = new short[var2];
      --var2;

      while(var2 >= 0) {
         this.exception_table[var2] = (short)var3.addClass((ObjectType)this.exceptions[var2]).index;
         --var2;
      }

   }

   public final ClassType[] getExceptions() {
      return this.exceptions;
   }

   public final int getLength() {
      int var1;
      if(this.exceptions == null) {
         var1 = 0;
      } else {
         var1 = this.exceptions.length;
      }

      return var1 * 2 + 2;
   }

   public void print(ClassTypeWriter var1) {
      var1.print("Attribute \"");
      var1.print(this.getName());
      var1.print("\", length:");
      var1.print(this.getLength());
      var1.print(", count: ");
      int var3 = this.exceptions.length;
      var1.println(var3);

      for(int var2 = 0; var2 < var3; ++var2) {
         int var4 = this.exception_table[var2] & '\uffff';
         var1.print("  ");
         var1.printOptionalIndex(var4);
         var1.printConstantTersely(var4, 7);
         var1.println();
      }

   }

   public void setExceptions(ClassType[] var1) {
      this.exceptions = var1;
   }

   public void setExceptions(short[] var1, ClassType var2) {
      this.exception_table = var1;
      this.exceptions = new ClassType[var1.length];
      ConstantPool var4 = var2.getConstants();

      for(int var3 = var1.length - 1; var3 >= 0; --var3) {
         this.exceptions[var3] = (ClassType)((CpoolClass)var4.getPoolEntry(var1[var3])).getClassType();
      }

   }

   public void write(DataOutputStream var1) throws IOException {
      int var3 = this.exceptions.length;
      var1.writeShort(var3);

      for(int var2 = 0; var2 < var3; ++var2) {
         var1.writeShort(this.exception_table[var2]);
      }

   }
}
