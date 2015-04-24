package gnu.bytecode;

import gnu.bytecode.Filter;
import gnu.bytecode.ObjectType;
import gnu.bytecode.Type;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;
import java.util.List;

public class ArrayType extends ObjectType implements Externalizable {

   public Type elements;


   public ArrayType(Type var1) {
      this(var1, var1.getName() + "[]");
   }

   ArrayType(Type var1, String var2) {
      this.this_name = var2;
      this.elements = var1;
   }

   public static ArrayType make(Type var0) {
      ArrayType var2 = var0.array_type;
      ArrayType var1 = var2;
      if(var2 == null) {
         var1 = new ArrayType(var0, var0.getName() + "[]");
         var0.array_type = var1;
      }

      return var1;
   }

   static ArrayType make(String var0) {
      Type var3 = Type.getType(var0.substring(0, var0.length() - 2));
      ArrayType var2 = var3.array_type;
      ArrayType var1 = var2;
      if(var2 == null) {
         var1 = new ArrayType(var3, var0);
         var3.array_type = var1;
      }

      return var1;
   }

   public int compare(Type var1) {
      return var1 == nullType?1:(var1 instanceof ArrayType?this.elements.compare(((ArrayType)var1).elements):(!var1.getName().equals("java.lang.Object") && var1 != toStringType?-3:-1));
   }

   public Type getComponentType() {
      return this.elements;
   }

   public Type getImplementationType() {
      Type var1 = this.elements.getImplementationType();
      return this.elements == var1?this:make((Type)var1);
   }

   public String getInternalName() {
      return this.getSignature();
   }

   public int getMethods(Filter var1, int var2, List var3) {
      int var4 = 0;
      if(var2 > 0) {
         int var5 = Type.objectType.getMethods(var1, 0, var3);
         var4 = var5;
         if(var2 > 1) {
            var4 = var5;
            if(var1.select(Type.clone_method)) {
               if(var3 != null) {
                  var3.add(Type.clone_method);
               }

               var4 = var5 + 1;
            }
         }
      }

      return var4;
   }

   public Class getReflectClass() {
      try {
         if(this.reflectClass == null) {
            this.reflectClass = Class.forName(this.getInternalName().replace('/', '.'), false, this.elements.getReflectClass().getClassLoader());
         }

         this.flags |= 16;
      } catch (ClassNotFoundException var3) {
         if((this.flags & 16) != 0) {
            RuntimeException var2 = new RuntimeException("no such array class: " + this.getName());
            var2.initCause(var3);
            throw var2;
         }
      }

      return this.reflectClass;
   }

   public String getSignature() {
      if(this.signature == null) {
         this.setSignature("[" + this.elements.getSignature());
      }

      return this.signature;
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      this.elements = (Type)var1.readObject();
   }

   public Object readResolve() throws ObjectStreamException {
      ArrayType var1 = this.elements.array_type;
      if(var1 != null) {
         return var1;
      } else {
         this.elements.array_type = this;
         return this;
      }
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      var1.writeObject(this.elements);
   }
}
