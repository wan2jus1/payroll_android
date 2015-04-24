package gnu.bytecode;

import gnu.bytecode.AttrContainer;
import gnu.bytecode.Attribute;
import gnu.bytecode.ClassType;
import gnu.bytecode.ConstantPool;
import gnu.bytecode.ConstantValueAttr;
import gnu.bytecode.CpoolEntry;
import gnu.bytecode.Location;
import gnu.bytecode.Member;
import gnu.bytecode.PrimType;
import java.io.DataOutputStream;
import java.io.IOException;

public class Field extends Location implements AttrContainer, Member {

   Attribute attributes;
   int flags;
   Field next;
   ClassType owner;
   java.lang.reflect.Field rfield;
   String sourceName;


   public Field(ClassType var1) {
      if(var1.last_field == null) {
         var1.fields = this;
      } else {
         var1.last_field.next = this;
      }

      var1.last_field = this;
      ++var1.fields_count;
      this.owner = var1;
   }

   public static Field searchField(Field var0, String var1) {
      while(var0 != null) {
         if(var0.getSourceName() == var1) {
            return var0;
         }

         var0 = var0.next;
      }

      return null;
   }

   void assign_constants(ClassType var1) {
      ConstantPool var2 = var1.constants;
      if(this.name_index == 0 && this.name != null) {
         this.name_index = var2.addUtf8(this.name).index;
      }

      if(this.signature_index == 0 && this.type != null) {
         this.signature_index = var2.addUtf8(this.type.getSignature()).index;
      }

      Attribute.assignConstants(this, var1);
   }

   public final Attribute getAttributes() {
      return this.attributes;
   }

   public final ClassType getDeclaringClass() {
      return this.owner;
   }

   public final int getFlags() {
      return this.flags;
   }

   public final int getModifiers() {
      return this.flags;
   }

   public final Field getNext() {
      return this.next;
   }

   public java.lang.reflect.Field getReflectField() throws NoSuchFieldException {
      synchronized(this){}

      java.lang.reflect.Field var1;
      try {
         if(this.rfield == null) {
            this.rfield = this.owner.getReflectClass().getDeclaredField(this.getName());
         }

         var1 = this.rfield;
      } finally {
         ;
      }

      return var1;
   }

   public String getSourceName() {
      if(this.sourceName == null) {
         this.sourceName = this.getName().intern();
      }

      return this.sourceName;
   }

   public final boolean getStaticFlag() {
      return (this.flags & 8) != 0;
   }

   public final void setAttributes(Attribute var1) {
      this.attributes = var1;
   }

   public final void setConstantValue(Object var1, ClassType var2) {
      byte var5 = 0;
      ConstantPool var4 = var2.constants;
      ConstantPool var3 = var4;
      if(var4 == null) {
         var3 = new ConstantPool();
         var2.constants = var3;
      }

      switch(this.getType().getSignature().charAt(0)) {
      case 67:
         if(var1 instanceof Character) {
            var1 = var3.addInt(((Character)var1).charValue());
            break;
         }
      case 66:
      case 73:
      case 83:
         var1 = var3.addInt(((Number)var1).intValue());
         break;
      case 68:
         var1 = var3.addDouble(((Number)var1).doubleValue());
         break;
      case 70:
         var1 = var3.addFloat(((Number)var1).floatValue());
         break;
      case 74:
         var1 = var3.addLong(((Number)var1).longValue());
         break;
      case 90:
         if(PrimType.booleanValue(var1)) {
            var5 = 1;
         }

         var1 = var3.addInt(var5);
         break;
      default:
         var1 = var3.addString((String)var1.toString());
      }

      (new ConstantValueAttr(((CpoolEntry)var1).getIndex())).addToFrontOf(this);
   }

   public void setSourceName(String var1) {
      this.sourceName = var1;
   }

   public final void setStaticFlag(boolean var1) {
      if(var1) {
         this.flags |= 8;
      } else {
         this.flags ^= -9;
      }
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer(100);
      var1.append("Field:");
      var1.append(this.getDeclaringClass().getName());
      var1.append('.');
      var1.append(this.name);
      return var1.toString();
   }

   void write(DataOutputStream var1, ClassType var2) throws IOException {
      var1.writeShort(this.flags);
      var1.writeShort(this.name_index);
      var1.writeShort(this.signature_index);
      Attribute.writeAll(this, var1);
   }
}
