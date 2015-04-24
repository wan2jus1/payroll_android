package gnu.kawa.reflect;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.ObjectType;
import gnu.bytecode.Type;
import gnu.kawa.reflect.OccurrenceType;
import gnu.mapping.Values;

public class SingletonType extends ObjectType {

   static final SingletonType instance = new SingletonType("singleton");


   public SingletonType(String var1) {
      super(var1);
   }

   public static Object coerceToSingleton(Object var0) {
      Object var1 = var0;
      if(var0 instanceof Values) {
         var1 = ((Values)var0).canonicalize();
      }

      if(var1 != null && !(var1 instanceof Values)) {
         return var1;
      } else {
         throw new ClassCastException("value is not a singleton");
      }
   }

   public static final SingletonType getInstance() {
      return instance;
   }

   public Object coerceFromObject(Object var1) {
      return coerceToSingleton(var1);
   }

   public int compare(Type var1) {
      byte var3 = -1;
      int var4 = OccurrenceType.itemCountRange(var1);
      int var2 = var4 & 4095;
      var4 >>= 12;
      byte var5;
      if(var4 != 0 && var2 <= 1) {
         if(var2 == 1 && var4 == 1) {
            return Type.pointer_type.compare(var1);
         }

         var4 = Type.pointer_type.compare(var1);
         var5 = var3;
         if(var4 != 0) {
            var5 = var3;
            if(var4 != -1) {
               return -2;
            }
         }
      } else {
         var5 = -3;
      }

      return var5;
   }

   public void emitCoerceFromObject(CodeAttr var1) {
      var1.emitInvokeStatic(ClassType.make("gnu.kawa.reflect.SingletonType").getDeclaredMethod("coerceToSingleton", 1));
   }

   public Type getImplementationType() {
      return Type.pointer_type;
   }

   public Class getReflectClass() {
      return this.getImplementationType().getReflectClass();
   }

   public boolean isInstance(Object var1) {
      return var1 != null && !(var1 instanceof Values);
   }
}
