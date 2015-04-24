package gnu.bytecode;

import gnu.bytecode.AttrContainer;
import gnu.bytecode.ClassType;
import gnu.bytecode.ClassTypeWriter;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class Attribute {

   AttrContainer container;
   String name;
   int name_index;
   Attribute next;


   public Attribute(String var1) {
      this.name = var1;
   }

   public static void assignConstants(AttrContainer var0, ClassType var1) {
      for(Attribute var2 = var0.getAttributes(); var2 != null; var2 = var2.next) {
         if(!var2.isSkipped()) {
            var2.assignConstants(var1);
         }
      }

   }

   public static int count(AttrContainer var0) {
      int var1 = 0;

      int var2;
      for(Attribute var3 = var0.getAttributes(); var3 != null; var1 = var2) {
         var2 = var1;
         if(!var3.isSkipped()) {
            var2 = var1 + 1;
         }

         var3 = var3.next;
      }

      return var1;
   }

   public static Attribute get(AttrContainer var0, String var1) {
      for(Attribute var2 = var0.getAttributes(); var2 != null; var2 = var2.next) {
         if(var2.getName() == var1) {
            return var2;
         }
      }

      return null;
   }

   public static int getLengthAll(AttrContainer var0) {
      int var1 = 0;

      int var2;
      for(Attribute var3 = var0.getAttributes(); var3 != null; var1 = var2) {
         var2 = var1;
         if(!var3.isSkipped()) {
            var2 = var1 + var3.getLength() + 6;
         }

         var3 = var3.next;
      }

      return var1;
   }

   public static void writeAll(AttrContainer var0, DataOutputStream var1) throws IOException {
      var1.writeShort(count(var0));

      for(Attribute var2 = var0.getAttributes(); var2 != null; var2 = var2.next) {
         if(!var2.isSkipped()) {
            if(var2.name_index == 0) {
               throw new Error("Attribute.writeAll called without assignConstants");
            }

            var1.writeShort(var2.name_index);
            var1.writeInt(var2.getLength());
            var2.write(var1);
         }
      }

   }

   public void addToFrontOf(AttrContainer var1) {
      this.setContainer(var1);
      this.setNext(var1.getAttributes());
      var1.setAttributes(this);
   }

   public void assignConstants(ClassType var1) {
      if(this.name_index == 0) {
         this.name_index = var1.getConstants().addUtf8(this.name).getIndex();
      }

   }

   public final AttrContainer getContainer() {
      return this.container;
   }

   public abstract int getLength();

   public final String getName() {
      return this.name;
   }

   public final int getNameIndex() {
      return this.name_index;
   }

   public final Attribute getNext() {
      return this.next;
   }

   public final boolean isSkipped() {
      return this.name_index < 0;
   }

   public void print(ClassTypeWriter var1) {
      var1.print("Attribute \"");
      var1.print(this.getName());
      var1.print("\", length:");
      var1.println(this.getLength());
   }

   public final void setContainer(AttrContainer var1) {
      this.container = var1;
   }

   public final void setName(String var1) {
      this.name = var1.intern();
   }

   public final void setNameIndex(int var1) {
      this.name_index = var1;
   }

   public final void setNext(Attribute var1) {
      this.next = var1;
   }

   public final void setSkipped() {
      this.name_index = -1;
   }

   public final void setSkipped(boolean var1) {
      byte var2;
      if(var1) {
         var2 = -1;
      } else {
         var2 = 0;
      }

      this.name_index = var2;
   }

   public abstract void write(DataOutputStream var1) throws IOException;
}
