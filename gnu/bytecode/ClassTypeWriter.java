package gnu.bytecode;

import gnu.bytecode.Access;
import gnu.bytecode.AttrContainer;
import gnu.bytecode.Attribute;
import gnu.bytecode.ClassType;
import gnu.bytecode.CpoolEntry;
import gnu.bytecode.CpoolUtf8;
import gnu.bytecode.Field;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Writer;

public class ClassTypeWriter extends PrintWriter {

   public static final int PRINT_CONSTANT_POOL = 1;
   public static final int PRINT_CONSTANT_POOL_INDEXES = 2;
   public static final int PRINT_EXTRAS = 8;
   public static final int PRINT_VERBOSE = 15;
   public static final int PRINT_VERSION = 4;
   ClassType ctype;
   int flags;


   public ClassTypeWriter(ClassType var1, OutputStream var2, int var3) {
      super(var2);
      this.ctype = var1;
      this.flags = var3;
   }

   public ClassTypeWriter(ClassType var1, Writer var2, int var3) {
      super(var2);
      this.ctype = var1;
      this.flags = var3;
   }

   public static void print(ClassType var0, PrintStream var1, int var2) {
      ClassTypeWriter var3 = new ClassTypeWriter(var0, var1, var2);
      var3.print();
      var3.flush();
   }

   public static void print(ClassType var0, PrintWriter var1, int var2) {
      ClassTypeWriter var3 = new ClassTypeWriter(var0, var1, var2);
      var3.print();
      var3.flush();
   }

   CpoolEntry getCpoolEntry(int var1) {
      CpoolEntry[] var2 = this.ctype.constants.pool;
      return var2 != null && var1 >= 0 && var1 < var2.length?var2[var1]:null;
   }

   public void print() {
      if((this.flags & 4) != 0) {
         this.print("Classfile format major version: ");
         this.print(this.ctype.getClassfileMajorVersion());
         this.print(", minor version: ");
         this.print(this.ctype.getClassfileMinorVersion());
         this.println('.');
      }

      if((this.flags & 1) != 0) {
         this.printConstantPool();
      }

      this.printClassInfo();
      this.printFields();
      this.printMethods();
      this.printAttributes();
   }

   public void print(ClassType var1) {
      this.ctype = var1;
      this.print();
   }

   public void printAttributes() {
      ClassType var1 = this.ctype;
      this.println();
      this.print("Attributes (count: ");
      this.print(Attribute.count(var1));
      this.println("):");
      this.printAttributes(var1);
   }

   public void printAttributes(AttrContainer var1) {
      for(Attribute var2 = var1.getAttributes(); var2 != null; var2 = var2.next) {
         var2.print(this);
      }

   }

   public void printClassInfo() {
      this.println();
      this.print("Access flags:");
      this.print(Access.toString(this.ctype.getModifiers(), 'C'));
      this.println();
      this.print("This class: ");
      this.printOptionalIndex(this.ctype.thisClassIndex);
      this.printConstantTersely(this.ctype.thisClassIndex, 7);
      this.print(" super: ");
      if(this.ctype.superClassIndex == -1) {
         this.print("<unknown>");
      } else if(this.ctype.superClassIndex == 0) {
         this.print("0");
      } else {
         this.printOptionalIndex(this.ctype.superClassIndex);
         this.printConstantTersely(this.ctype.superClassIndex, 7);
      }

      this.println();
      this.print("Interfaces (count: ");
      int[] var1 = this.ctype.interfaceIndexes;
      int var2;
      if(var1 == null) {
         var2 = 0;
      } else {
         var2 = var1.length;
      }

      this.print(var2);
      this.print("):");
      this.println();

      for(int var3 = 0; var3 < var2; ++var3) {
         this.print("- Implements: ");
         int var4 = var1[var3];
         this.printOptionalIndex(var4);
         this.printConstantTersely(var4, 7);
         this.println();
      }

   }

   final void printConstantOperand(int var1) {
      this.print(' ');
      this.printOptionalIndex(var1);
      CpoolEntry[] var2 = this.ctype.constants.pool;
      if(var2 != null && var1 >= 0 && var1 < var2.length) {
         CpoolEntry var3 = var2[var1];
         if(var3 != null) {
            this.print('<');
            var3.print(this, 1);
            this.print('>');
            return;
         }
      }

      this.print("<invalid constant index>");
   }

   public void printConstantPool() {
      CpoolEntry[] var1 = this.ctype.constants.pool;
      int var4 = this.ctype.constants.count;

      for(int var3 = 1; var3 <= var4; ++var3) {
         CpoolEntry var2 = var1[var3];
         if(var2 != null) {
            this.print('#');
            this.print(var2.index);
            this.print(": ");
            var2.print(this, 2);
            this.println();
         }
      }

   }

   final void printConstantTersely(int var1, int var2) {
      this.printConstantTersely(this.getCpoolEntry(var1), var2);
   }

   final void printConstantTersely(CpoolEntry var1, int var2) {
      if(var1 == null) {
         this.print("<invalid constant index>");
      } else if(var1.getTag() != var2) {
         this.print("<unexpected constant type ");
         var1.print(this, 1);
         this.print('>');
      } else {
         var1.print(this, 0);
      }
   }

   final void printContantUtf8AsClass(int var1) {
      CpoolEntry var2 = this.getCpoolEntry(var1);
      if(var2 != null && var2.getTag() == 1) {
         String var3 = ((CpoolUtf8)var2).string;
         Type.printSignature(var3, 0, var3.length(), this);
      } else {
         this.printConstantTersely(var1, 1);
      }
   }

   public void printFields() {
      this.println();
      this.print("Fields (count: ");
      this.print(this.ctype.fields_count);
      this.print("):");
      this.println();
      int var2 = 0;

      for(Field var1 = this.ctype.fields; var1 != null; var1 = var1.next) {
         this.print("Field name: ");
         if(var1.name_index != 0) {
            this.printOptionalIndex(var1.name_index);
         }

         this.print(var1.getName());
         this.print(Access.toString(var1.flags, 'F'));
         this.print(" Signature: ");
         if(var1.signature_index != 0) {
            this.printOptionalIndex(var1.signature_index);
         }

         this.printSignature((Type)var1.type);
         this.println();
         this.printAttributes(var1);
         ++var2;
      }

   }

   public void printMethod(Method var1) {
      this.println();
      this.print("Method name:");
      if(var1.name_index != 0) {
         this.printOptionalIndex(var1.name_index);
      }

      this.print('\"');
      this.print(var1.getName());
      this.print('\"');
      this.print(Access.toString(var1.access_flags, 'M'));
      this.print(" Signature: ");
      if(var1.signature_index != 0) {
         this.printOptionalIndex(var1.signature_index);
      }

      this.print('(');

      for(int var2 = 0; var2 < var1.arg_types.length; ++var2) {
         if(var2 > 0) {
            this.print(',');
         }

         this.printSignature((Type)var1.arg_types[var2]);
      }

      this.print(')');
      this.printSignature((Type)var1.return_type);
      this.println();
      this.printAttributes(var1);
   }

   public void printMethods() {
      this.println();
      this.print("Methods (count: ");
      this.print(this.ctype.methods_count);
      this.print("):");
      this.println();

      for(Method var1 = this.ctype.methods; var1 != null; var1 = var1.next) {
         this.printMethod(var1);
      }

   }

   void printName(String var1) {
      this.print(var1);
   }

   public final void printOptionalIndex(int var1) {
      if((this.flags & 2) != 0) {
         this.print('#');
         this.print(var1);
         this.print('=');
      }

   }

   public final void printOptionalIndex(CpoolEntry var1) {
      this.printOptionalIndex(var1.index);
   }

   public final void printQuotedString(String var1) {
      this.print('\"');
      int var5 = var1.length();

      for(int var3 = 0; var3 < var5; ++var3) {
         char var2 = var1.charAt(var3);
         if(var2 == 34) {
            this.print("\\\"");
         } else if(var2 >= 32 && var2 < 127) {
            this.print(var2);
         } else if(var2 == 10) {
            this.print("\\n");
         } else {
            this.print("\\u");
            int var4 = 4;

            while(true) {
               --var4;
               if(var4 < 0) {
                  break;
               }

               this.print(Character.forDigit(var2 >> var4 * 4 & 15, 16));
            }
         }
      }

      this.print('\"');
   }

   public final int printSignature(String var1, int var2) {
      int var6 = var1.length();
      if(var2 >= var6) {
         this.print("<empty signature>");
         return var2;
      } else {
         int var5 = Type.signatureLength(var1, var2);
         if(var5 > 0) {
            String var4 = Type.signatureToName(var1.substring(var2, var2 + var5));
            if(var4 != null) {
               this.print(var4);
               return var2 + var5;
            }
         }

         char var3 = var1.charAt(var2);
         if(var3 != 40) {
            this.print(var3);
            return var2 + 1;
         } else {
            var5 = var2 + 1;
            this.print(var3);

            for(var2 = 0; var5 < var6; ++var2) {
               var3 = var1.charAt(var5);
               if(var3 == 41) {
                  this.print(var3);
                  return this.printSignature(var1, var5 + 1);
               }

               if(var2 > 0) {
                  this.print(',');
               }

               var5 = this.printSignature(var1, var5);
            }

            this.print("<truncated method signature>");
            return var5;
         }
      }
   }

   public final void printSignature(Type var1) {
      if(var1 == null) {
         this.print("<unknown type>");
      } else {
         this.printSignature((String)var1.getSignature());
      }
   }

   public final void printSignature(String var1) {
      int var2 = this.printSignature(var1, 0);
      if(var2 < var1.length()) {
         this.print("<trailing junk:");
         this.print(var1.substring(var2));
         this.print('>');
      }

   }

   public void printSpaces(int var1) {
      while(true) {
         --var1;
         if(var1 < 0) {
            return;
         }

         this.print(' ');
      }
   }

   public void setClass(ClassType var1) {
      this.ctype = var1;
   }

   public void setFlags(int var1) {
      this.flags = var1;
   }
}
