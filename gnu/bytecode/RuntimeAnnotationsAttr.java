package gnu.bytecode;

import gnu.bytecode.AttrContainer;
import gnu.bytecode.ClassTypeWriter;
import gnu.bytecode.CpoolEntry;
import gnu.bytecode.CpoolValue1;
import gnu.bytecode.MiscAttr;

public class RuntimeAnnotationsAttr extends MiscAttr {

   int numEntries;


   public RuntimeAnnotationsAttr(String var1, byte[] var2, AttrContainer var3) {
      super(var1, var2, 0, var2.length);
      this.addToFrontOf(var3);
      this.numEntries = this.u2(0);
   }

   public void print(ClassTypeWriter var1) {
      var1.print("Attribute \"");
      var1.print(this.getName());
      var1.print("\", length:");
      var1.print(this.getLength());
      var1.print(", number of entries: ");
      var1.println(this.numEntries);
      int var3 = this.offset;
      this.offset = var3 + 2;

      for(int var2 = 0; var2 < this.numEntries; ++var2) {
         this.printAnnotation(2, var1);
      }

      this.offset = var3;
   }

   public void printAnnotation(int var1, ClassTypeWriter var2) {
      int var3 = this.u2();
      var2.printSpaces(var1);
      var2.printOptionalIndex(var3);
      var2.print('@');
      var2.printContantUtf8AsClass(var3);
      var3 = this.u2();
      var2.println();
      int var4 = var1 + 2;

      for(var1 = 0; var1 < var3; ++var1) {
         int var5 = this.u2();
         var2.printSpaces(var4);
         var2.printOptionalIndex(var5);
         var2.printConstantTersely(var5, 1);
         var2.print(" => ");
         this.printAnnotationElementValue(var4, var2);
         var2.println();
      }

   }

   public void printAnnotationElementValue(int var1, ClassTypeWriter var2) {
      int var9 = this.u1();
      if((var2.flags & 8) != 0) {
         var2.print("[kind:");
         if(var9 >= 65 && var9 <= 122) {
            var2.print((char)var9);
         } else {
            var2.print(var9);
         }

         var2.print("] ");
      }

      byte var4 = 0;
      byte var5 = 0;
      byte var6 = 0;
      byte var8 = 0;
      byte var7 = var8;
      int var13;
      switch(var9) {
      case 64:
         var2.println();
         var2.printSpaces(var1 + 2);
         this.printAnnotation(var1 + 2, var2);
         return;
      case 66:
      case 67:
      case 73:
      case 83:
      case 90:
         var7 = var8;
         if(true) {
            var7 = 3;
         }
      case 74:
         var4 = var7;
         if(var7 == 0) {
            var4 = 5;
         }
      case 68:
         var5 = var4;
         if(var4 == 0) {
            var5 = 6;
         }
      case 70:
         var6 = var5;
         if(var5 == 0) {
            var6 = 4;
         }
      case 91:
         int var14 = this.u2();
         var2.print("array length:");
         var2.print(var14);

         for(var13 = 0; var13 < var14; ++var13) {
            var2.println();
            var2.printSpaces(var1 + 2);
            var2.print(var13);
            var2.print(": ");
            this.printAnnotationElementValue(var1 + 2, var2);
         }
      case 99:
         var1 = this.u2();
         var2.printOptionalIndex(var1);
         var2.printContantUtf8AsClass(var1);
         return;
      case 101:
         var1 = this.u2();
         var13 = this.u2();
         var2.print("enum[");
         if((var2.flags & 8) != 0) {
            var2.print("type:");
         }

         var2.printOptionalIndex(var1);
         var2.printContantUtf8AsClass(var1);
         if((var2.flags & 8) != 0) {
            var2.print(" value:");
         } else {
            var2.print(' ');
         }

         var2.printOptionalIndex(var13);
         var2.printConstantTersely(var13, 1);
         var2.print("]");
         return;
      case 115:
         byte var10 = var6;
         if(var6 == 0) {
            var10 = 1;
         }

         var13 = this.u2();
         CpoolEntry var3 = var2.getCpoolEntry(var13);
         var2.printOptionalIndex(var3);
         if(var9 == 90 && var3 != null && var3.getTag() == 3) {
            CpoolValue1 var11 = (CpoolValue1)var3;
            if(var11.value == 0 || var11.value == 1) {
               String var12;
               if(var11.value == 0) {
                  var12 = "false";
               } else {
                  var12 = "true";
               }

               var2.print(var12);
               return;
            }
         }

         var2.printConstantTersely(var13, var10);
         return;
      default:
      }
   }
}
