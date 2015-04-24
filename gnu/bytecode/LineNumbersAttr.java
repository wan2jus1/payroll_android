package gnu.bytecode;

import gnu.bytecode.Attribute;
import gnu.bytecode.ClassTypeWriter;
import gnu.bytecode.CodeAttr;
import java.io.DataOutputStream;
import java.io.IOException;

public class LineNumbersAttr extends Attribute {

   int linenumber_count;
   short[] linenumber_table;


   public LineNumbersAttr(CodeAttr var1) {
      super("LineNumberTable");
      this.addToFrontOf(var1);
      var1.lines = this;
   }

   public LineNumbersAttr(short[] var1, CodeAttr var2) {
      this(var2);
      this.linenumber_table = var1;
      this.linenumber_count = var1.length >> 1;
   }

   public final int getLength() {
      return this.linenumber_count * 4 + 2;
   }

   public int getLineCount() {
      return this.linenumber_count;
   }

   public short[] getLineNumberTable() {
      return this.linenumber_table;
   }

   public void print(ClassTypeWriter var1) {
      var1.print("Attribute \"");
      var1.print(this.getName());
      var1.print("\", length:");
      var1.print(this.getLength());
      var1.print(", count: ");
      var1.println(this.linenumber_count);

      for(int var2 = 0; var2 < this.linenumber_count; ++var2) {
         var1.print("  line: ");
         var1.print(this.linenumber_table[var2 * 2 + 1] & '\uffff');
         var1.print(" at pc: ");
         var1.println(this.linenumber_table[var2 * 2] & '\uffff');
      }

   }

   public void put(int var1, int var2) {
      if(this.linenumber_table == null) {
         this.linenumber_table = new short[32];
      } else if(this.linenumber_count * 2 >= this.linenumber_table.length) {
         short[] var3 = new short[this.linenumber_table.length * 2];
         System.arraycopy(this.linenumber_table, 0, var3, 0, this.linenumber_count * 2);
         this.linenumber_table = var3;
      }

      this.linenumber_table[this.linenumber_count * 2] = (short)var2;
      this.linenumber_table[this.linenumber_count * 2 + 1] = (short)var1;
      ++this.linenumber_count;
   }

   public void write(DataOutputStream var1) throws IOException {
      var1.writeShort(this.linenumber_count);
      int var3 = this.linenumber_count;

      for(int var2 = 0; var2 < var3 * 2; ++var2) {
         var1.writeShort(this.linenumber_table[var2]);
      }

   }
}
