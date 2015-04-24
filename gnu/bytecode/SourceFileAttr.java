package gnu.bytecode;

import gnu.bytecode.Attribute;
import gnu.bytecode.ClassType;
import gnu.bytecode.ClassTypeWriter;
import gnu.bytecode.CpoolUtf8;
import java.io.DataOutputStream;
import java.io.IOException;

public class SourceFileAttr extends Attribute {

   String filename;
   int filename_index;


   public SourceFileAttr(int var1, ClassType var2) {
      super("SourceFile");
      this.filename = ((CpoolUtf8)var2.constants.getForced(var1, 1)).string;
      this.filename_index = var1;
   }

   public SourceFileAttr(String var1) {
      super("SourceFile");
      this.filename = var1;
   }

   public static String fixSourceFile(String var0) {
      String var3 = System.getProperty("file.separator", "/");
      String var2 = var0;
      if(var3 != null) {
         var2 = var0;
         if(var3.length() == 1) {
            char var1 = var3.charAt(0);
            var2 = var0;
            if(var1 != 47) {
               var2 = var0.replace(var1, '/');
            }
         }
      }

      return var2;
   }

   public static void setSourceFile(ClassType var0, String var1) {
      Attribute var2 = Attribute.get(var0, "SourceFile");
      if(var2 != null && var2 instanceof SourceFileAttr) {
         ((SourceFileAttr)var2).setSourceFile(var1);
      } else {
         (new SourceFileAttr(var1)).addToFrontOf(var0);
      }
   }

   public void assignConstants(ClassType var1) {
      super.assignConstants(var1);
      if(this.filename_index == 0) {
         this.filename_index = var1.getConstants().addUtf8(this.filename).getIndex();
      }

   }

   public final int getLength() {
      return 2;
   }

   public String getSourceFile() {
      return this.filename;
   }

   public void print(ClassTypeWriter var1) {
      var1.print("Attribute \"");
      var1.print(this.getName());
      var1.print("\", length:");
      var1.print(this.getLength());
      var1.print(", ");
      var1.printOptionalIndex(this.filename_index);
      var1.print('\"');
      var1.print(this.getSourceFile());
      var1.println('\"');
   }

   public void setSourceFile(String var1) {
      this.filename = var1;
      this.filename_index = 0;
   }

   public void write(DataOutputStream var1) throws IOException {
      var1.writeShort(this.filename_index);
   }
}
