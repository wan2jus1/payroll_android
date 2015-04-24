package gnu.bytecode;

import gnu.bytecode.ArrayType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Field;
import gnu.bytecode.Filter;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import java.util.List;
import java.util.Vector;

public class ObjectType extends Type {

   static final int ADD_ENCLOSING_DONE = 8;
   static final int ADD_FIELDS_DONE = 1;
   static final int ADD_MEMBERCLASSES_DONE = 4;
   static final int ADD_METHODS_DONE = 2;
   static final int EXISTING_CLASS = 16;
   static final int HAS_OUTER_LINK = 32;
   public int flags;


   protected ObjectType() {
      this.size = 4;
   }

   public ObjectType(String var1) {
      this.this_name = var1;
      this.size = 4;
   }

   public static Class getContextClass(String var0) throws ClassNotFoundException {
      try {
         Class var1 = Class.forName(var0, false, ObjectType.class.getClassLoader());
         return var1;
      } catch (ClassNotFoundException var2) {
         return Class.forName(var0, false, getContextClassLoader());
      }
   }

   public static ClassLoader getContextClassLoader() {
      try {
         ClassLoader var0 = ClassLoader.getSystemClassLoader();
         return var0;
      } catch (SecurityException var1) {
         return ObjectType.class.getClassLoader();
      }
   }

   public Object coerceFromObject(Object var1) {
      Object var2 = var1;
      if(var1 != null) {
         if(this == Type.toStringType) {
            var2 = var1.toString();
         } else {
            Class var3 = this.getReflectClass();
            Class var4 = var1.getClass();
            var2 = var1;
            if(!var3.isAssignableFrom(var4)) {
               throw new ClassCastException("don\'t know how to coerce " + var4.getName() + " to " + this.getName());
            }
         }
      }

      return var2;
   }

   public int compare(Type var1) {
      return var1 == nullType?0:-1;
   }

   public void emitCoerceFromObject(CodeAttr var1) {
      if(this == Type.toStringType) {
         var1.emitDup();
         var1.emitIfNull();
         var1.emitPop(1);
         var1.emitPushNull();
         var1.emitElse();
         var1.emitInvokeVirtual(Type.toString_method);
         var1.emitFi();
      } else if(this != Type.objectType) {
         var1.emitCheckcast(this);
         return;
      }

   }

   public Field getField(String var1, int var2) {
      return null;
   }

   public Type getImplementationType() {
      Object var1;
      if(this == nullType) {
         var1 = objectType;
      } else {
         var1 = this;
         if(this == toStringType) {
            return javalangStringType;
         }
      }

      return (Type)var1;
   }

   public String getInternalName() {
      return this.getName().replace('.', '/');
   }

   public Method getMethod(String var1, Type[] var2) {
      return Type.objectType.getMethod(var1, var2);
   }

   public int getMethods(Filter var1, int var2, List var3) {
      return Type.objectType.getMethods(var1, var2, var3);
   }

   public final int getMethods(Filter var1, int var2, Vector var3, String var4) {
      return Type.objectType.getMethods(var1, var2, var3, var4);
   }

   public Class getReflectClass() {
      try {
         if(this.reflectClass == null) {
            this.reflectClass = getContextClass(this.getInternalName().replace('/', '.'));
         }

         this.flags |= 16;
      } catch (ClassNotFoundException var3) {
         if((this.flags & 16) != 0) {
            RuntimeException var2 = new RuntimeException("no such class: " + this.getName());
            var2.initCause(var3);
            throw var2;
         }
      }

      return this.reflectClass;
   }

   public final boolean isExisting() {
      boolean var3 = false;
      Type var2 = this.getImplementationType();
      Type var1 = var2;
      if(var2 instanceof ArrayType) {
         var1 = ((ArrayType)var2).getComponentType();
      }

      if(var1 == this) {
         return (this.flags & 16) != 0;
      } else {
         if(!(var1 instanceof ObjectType) || ((ObjectType)var1).isExisting()) {
            var3 = true;
         }

         return var3;
      }
   }

   public boolean isInstance(Object var1) {
      return this == nullType?var1 == null:super.isInstance(var1);
   }

   public Type promote() {
      Object var1 = this;
      if(this == nullType) {
         var1 = objectType;
      }

      return (Type)var1;
   }

   public final void setExisting(boolean var1) {
      if(var1) {
         this.flags |= 16;
      } else {
         this.flags &= -17;
      }
   }
}
