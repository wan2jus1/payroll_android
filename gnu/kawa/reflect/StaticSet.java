package gnu.kawa.reflect;

import gnu.bytecode.ClassType;
import gnu.bytecode.Field;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.Inlineable;
import gnu.expr.Target;
import gnu.mapping.Procedure1;
import gnu.mapping.Values;

public class StaticSet extends Procedure1 implements Inlineable {

   ClassType ctype;
   Field field;
   String fname;
   java.lang.reflect.Field reflectField;


   public StaticSet(ClassType var1, String var2, Type var3, int var4) {
      this.ctype = var1;
      this.fname = var2;
      this.field = var1.getField(var2);
      if(this.field == null) {
         this.field = var1.addField(var2, var3, var4);
      }

   }

   StaticSet(Class var1, String var2) {
      this.ctype = (ClassType)Type.make(var1);
      this.fname = var2;
   }

   public Object apply1(Object var1) {
      if(this.reflectField == null) {
         Class var2 = this.ctype.getReflectClass();

         try {
            this.reflectField = var2.getField(this.fname);
         } catch (NoSuchFieldException var4) {
            throw new RuntimeException("no such field " + this.fname + " in " + var2.getName());
         }
      }

      try {
         this.reflectField.set((Object)null, var1);
         Values var5 = Values.empty;
         return var5;
      } catch (IllegalAccessException var3) {
         throw new RuntimeException("illegal access for field " + this.fname);
      }
   }

   public void compile(ApplyExp var1, Compilation var2, Target var3) {
      if(this.field == null) {
         this.field = this.ctype.getField(this.fname);
         if(this.field == null) {
            this.field = this.ctype.addField(this.fname, Type.make(this.reflectField.getType()), this.reflectField.getModifiers());
         }
      }

      var1.getArgs()[0].compile(var2, (Type)this.field.getType());
      var2.getCode().emitPutStatic(this.field);
      var2.compileConstant(Values.empty, var3);
   }

   public Type getReturnType(Expression[] var1) {
      return Type.voidType;
   }
}
