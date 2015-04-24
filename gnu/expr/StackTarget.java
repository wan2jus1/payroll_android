package gnu.expr;

import gnu.bytecode.ArrayType;
import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.PrimType;
import gnu.bytecode.Type;
import gnu.expr.Compilation;
import gnu.expr.Target;
import gnu.kawa.reflect.OccurrenceType;
import gnu.mapping.Values;

public class StackTarget extends Target {

   Type type;


   public StackTarget(Type var1) {
      this.type = var1;
   }

   static boolean compileFromStack0(Compilation var0, Type var1, Type var2) {
      if(var2 != var1) {
         CodeAttr var3 = var0.getCode();
         Object var4;
         if(var1.isVoid()) {
            var0.compileConstant(Values.empty);
            var4 = Type.pointer_type;
         } else {
            var4 = var1;
            if(var1 instanceof PrimType) {
               var4 = var1;
               if(var2 instanceof PrimType) {
                  var3.emitConvert(var1, var2);
                  return true;
               }
            }
         }

         if(var4 instanceof ArrayType) {
            if(var2 == Type.pointer_type || "java.lang.Cloneable".equals(var2.getName())) {
               return true;
            }
         } else {
            var2.emitConvertFromPrimitive((Type)var4, var3);
            var4 = var3.topType();
         }

         if(CodeAttr.castNeeded(((Type)var4).getImplementationType(), var2.getImplementationType())) {
            return false;
         }
      }

      return true;
   }

   public static void convert(Compilation var0, Type var1, Type var2) {
      if(!compileFromStack0(var0, var1, var2)) {
         emitCoerceFromObject(var2, var0);
      }

   }

   protected static void emitCoerceFromObject(Type var0, Compilation var1) {
      CodeAttr var2 = var1.getCode();
      if(var0 instanceof OccurrenceType) {
         var1.compileConstant(var0, Target.pushObject);
         var2.emitSwap();
         var2.emitInvokeVirtual(ClassType.make("gnu.bytecode.Type").getDeclaredMethod("coerceFromObject", 1));
      } else {
         var1.usedClass(var0);
         var0.emitCoerceFromObject(var2);
      }
   }

   public static Target getInstance(Type var0) {
      return (Target)(var0.isVoid()?Target.Ignore:(var0 == Type.pointer_type?Target.pushObject:new StackTarget(var0)));
   }

   public void compileFromStack(Compilation var1, Type var2) {
      if(!this.compileFromStack0(var1, var2)) {
         emitCoerceFromObject(this.type, var1);
      }

   }

   protected boolean compileFromStack0(Compilation var1, Type var2) {
      return compileFromStack0(var1, var2, this.type);
   }

   public Type getType() {
      return this.type;
   }
}
