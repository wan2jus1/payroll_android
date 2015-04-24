package gnu.bytecode;

import gnu.bytecode.Access;
import gnu.bytecode.Attribute;
import gnu.bytecode.ClassType;
import gnu.bytecode.ClassTypeWriter;
import gnu.bytecode.ConstantPool;
import gnu.bytecode.CpoolClass;
import gnu.bytecode.CpoolUtf8;
import gnu.bytecode.ObjectType;
import java.io.DataOutputStream;
import java.io.IOException;

public class InnerClassesAttr extends Attribute {

   int count;
   short[] data;


   public InnerClassesAttr(ClassType var1) {
      super("InnerClasses");
      this.addToFrontOf(var1);
   }

   public InnerClassesAttr(short[] var1, ClassType var2) {
      this(var2);
      this.count = (short)(var1.length >> 2);
      this.data = var1;
   }

   public static InnerClassesAttr getFirstInnerClasses(Attribute var0) {
      while(var0 != null && !(var0 instanceof InnerClassesAttr)) {
         var0 = var0.next;
      }

      return (InnerClassesAttr)var0;
   }

   void addClass(CpoolClass var1, ClassType var2) {
      short var3 = 0;
      int var6 = this.count;
      this.count = var6 + 1;
      int var7 = var6 * 4;
      if(this.data == null) {
         this.data = new short[16];
      } else if(var7 >= this.data.length) {
         short[] var4 = new short[var7 * 2];
         System.arraycopy(this.data, 0, var4, 0, var7);
         this.data = var4;
      }

      ConstantPool var9 = var2.constants;
      ClassType var10 = (ClassType)var1.getClassType();
      String var5 = var10.getSimpleName();
      if(var5 != null && var5.length() != 0) {
         var6 = var9.addUtf8(var5).index;
      } else {
         var6 = 0;
      }

      this.data[var7] = (short)var1.index;
      ClassType var8 = var10.getDeclaringClass();
      short[] var11 = this.data;
      if(var8 != null) {
         var3 = (short)var9.addClass((ObjectType)var8).index;
      }

      var11[var7 + 1] = var3;
      this.data[var7 + 2] = (short)var6;
      var6 = var10.getModifiers();
      this.data[var7 + 3] = (short)(var6 & -33);
   }

   public void assignConstants(ClassType var1) {
      super.assignConstants(var1);
   }

   public int getLength() {
      return this.count * 8 + 2;
   }

   public void print(ClassTypeWriter var1) {
      ClassType var6 = (ClassType)this.container;
      ConstantPool var3;
      if(this.data == null) {
         var3 = null;
      } else {
         var3 = var6.getConstants();
      }

      var1.print("Attribute \"");
      var1.print(this.getName());
      var1.print("\", length:");
      var1.print(this.getLength());
      var1.print(", count: ");
      var1.println(this.count);

      for(int var7 = 0; var7 < this.count; ++var7) {
         int var8;
         if(var3 == null) {
            var8 = 0;
         } else {
            var8 = this.data[var7 * 4] & '\uffff';
         }

         CpoolClass var5;
         if(var3 != null && var8 != 0) {
            var5 = var3.getForcedClass(var8);
         } else {
            var5 = null;
         }

         ClassType var4;
         if(var5 != null && var5.clas instanceof ClassType) {
            var4 = (ClassType)var5.clas;
         } else {
            var4 = null;
         }

         var1.print(' ');
         int var9;
         if(var8 == 0 && var4 != null) {
            var9 = var4.getModifiers();
         } else {
            var9 = this.data[var7 * 4 + 3] & '\uffff';
         }

         var1.print(Access.toString(var9, 'I'));
         var1.print(' ');
         String var2;
         if(var8 == 0 && var4 != null) {
            var2 = var4.getSimpleName();
         } else {
            var9 = this.data[var7 * 4 + 2] & '\uffff';
            if(var3 != null && var9 != 0) {
               var1.printOptionalIndex(var9);
               var2 = ((CpoolUtf8)((CpoolUtf8)var3.getForced(var9, 1))).string;
            } else {
               var2 = "(Anonymous)";
            }
         }

         var1.print(var2);
         var1.print(" = ");
         if(var5 != null) {
            var2 = var5.getClassName();
         } else {
            var2 = "(Unknown)";
         }

         var1.print(var2);
         var1.print("; ");
         if(var8 == 0 && var4 != null) {
            label99: {
               String var10 = var4.getName();
               var8 = var10.lastIndexOf(46);
               var2 = var10;
               if(var8 > 0) {
                  var2 = var10.substring(var8 + 1);
               }

               var8 = var2.lastIndexOf(36) + 1;
               if(var8 < var2.length()) {
                  char var11 = var2.charAt(var8);
                  if(var11 >= 48 && var11 <= 57) {
                     var1.print("not a member");
                     break label99;
                  }
               }

               var1.print("member of ");
               var1.print(var6.getName());
            }
         } else {
            var8 = this.data[var7 * 4 + 1] & '\uffff';
            if(var8 == 0) {
               var1.print("not a member");
            } else {
               var1.print("member of ");
               var1.print(((CpoolClass)var3.getForced(var8, 7)).getStringName());
            }
         }

         var1.println();
      }

   }

   public void write(DataOutputStream var1) throws IOException {
      var1.writeShort(this.count);

      for(int var2 = 0; var2 < this.count; ++var2) {
         var1.writeShort(this.data[var2 * 4]);
         var1.writeShort(this.data[var2 * 4 + 1]);
         var1.writeShort(this.data[var2 * 4 + 2]);
         var1.writeShort(this.data[var2 * 4 + 3]);
      }

   }
}
