package gnu.bytecode;

import gnu.bytecode.Attribute;
import gnu.bytecode.ClassType;
import gnu.bytecode.ClassTypeWriter;
import gnu.bytecode.ConstantPool;
import gnu.bytecode.CpoolEntry;
import gnu.bytecode.CpoolString;
import gnu.bytecode.CpoolValue1;
import gnu.bytecode.CpoolValue2;
import java.io.DataOutputStream;
import java.io.IOException;

public class ConstantValueAttr extends Attribute {

   Object value;
   int value_index;


   public ConstantValueAttr(int var1) {
      super("ConstantValue");
      this.value_index = var1;
   }

   public ConstantValueAttr(Object var1) {
      super("ConstantValue");
      this.value = var1;
   }

   public void assignConstants(ClassType var1) {
      super.assignConstants(var1);
      if(this.value_index == 0) {
         ConstantPool var2 = var1.getConstants();
         Object var3 = null;
         if(this.value instanceof String) {
            var3 = var2.addString((String)((String)this.value));
         } else if(this.value instanceof Integer) {
            var3 = var2.addInt(((Integer)this.value).intValue());
         } else if(this.value instanceof Long) {
            var3 = var2.addLong(((Long)this.value).longValue());
         } else if(this.value instanceof Float) {
            var3 = var2.addFloat(((Float)this.value).floatValue());
         } else if(this.value instanceof Long) {
            var3 = var2.addDouble(((Double)this.value).doubleValue());
         }

         this.value_index = ((CpoolEntry)var3).getIndex();
      }

   }

   public final int getLength() {
      return 2;
   }

   public Object getValue(ConstantPool var1) {
      if(this.value != null) {
         return this.value;
      } else {
         CpoolEntry var2 = var1.getPoolEntry(this.value_index);
         switch(var2.getTag()) {
         case 3:
            this.value = new Integer(((CpoolValue1)var2).value);
            break;
         case 4:
            this.value = new Float(Float.intBitsToFloat(((CpoolValue1)var2).value));
            break;
         case 5:
            this.value = new Long(((CpoolValue2)var2).value);
            break;
         case 6:
            this.value = new Double(Double.longBitsToDouble(((CpoolValue2)var2).value));
         case 7:
         default:
            break;
         case 8:
            this.value = ((CpoolString)var2).getString().getString();
         }

         return this.value;
      }
   }

   public void print(ClassTypeWriter var1) {
      var1.print("Attribute \"");
      var1.print(this.getName());
      var1.print("\", length:");
      var1.print(this.getLength());
      var1.print(", value: ");
      if(this.value_index == 0) {
         Object var2 = this.getValue(var1.ctype.constants);
         if(var2 instanceof String) {
            var1.printQuotedString((String)var2);
         } else {
            var1.print(var2);
         }
      } else {
         var1.printOptionalIndex(this.value_index);
         var1.ctype.constants.getPoolEntry(this.value_index).print(var1, 1);
      }

      var1.println();
   }

   public void write(DataOutputStream var1) throws IOException {
      var1.writeShort(this.value_index);
   }
}
