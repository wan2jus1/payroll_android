package gnu.kawa.reflect;

import gnu.bytecode.ClassType;
import gnu.bytecode.Field;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.Inlineable;
import gnu.expr.Target;
import gnu.mapping.Procedure0;

public class StaticGet extends Procedure0 implements Inlineable {

   ClassType ctype;
   Field field;
   String fname;
   java.lang.reflect.Field reflectField;


   public StaticGet(ClassType var1, String var2, Type var3, int var4) {
      this.ctype = var1;
      this.fname = var2;
      this.field = var1.getField(var2);
      if(this.field == null) {
         this.field = var1.addField(var2, var3, var4);
      }

   }

   StaticGet(Class var1, String var2) {
      this.ctype = (ClassType)Type.make(var1);
      this.fname = var2;
   }

   private Field getField() {
      if(this.field == null) {
         this.field = this.ctype.getField(this.fname);
         if(this.field == null) {
            this.field = this.ctype.addField(this.fname, Type.make(this.reflectField.getType()), this.reflectField.getModifiers());
         }
      }

      return this.field;
   }

   public Object apply0() {
      if(this.reflectField == null) {
         Class var1 = this.ctype.getReflectClass();

         try {
            this.reflectField = var1.getField(this.fname);
         } catch (NoSuchFieldException var4) {
            throw new RuntimeException("no such field " + this.fname + " in " + var1.getName());
         }
      }

      try {
         Object var5 = this.reflectField.get((Object)null);
         return var5;
      } catch (IllegalAccessException var3) {
         throw new RuntimeException("illegal access for field " + this.fname);
      }
   }

   public void compile(ApplyExp var1, Compilation var2, Target var3) {
      this.getField();
      var2.getCode().emitGetStatic(this.field);
      var3.compileFromStack(var2, this.field.getType());
   }

   public Type getReturnType(Expression[] var1) {
      return this.getField().getType();
   }
}
