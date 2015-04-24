package kawa.lang;

import gnu.bytecode.ArrayClassLoader;
import gnu.bytecode.ClassType;
import gnu.bytecode.Field;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.Inlineable;
import gnu.expr.Target;
import gnu.mapping.Procedure1;

public class GetFieldProc extends Procedure1 implements Inlineable {

   ClassType ctype;
   Field field;


   public GetFieldProc(ClassType var1, String var2) {
      this.ctype = var1;
      this.field = Field.searchField(var1.getFields(), var2);
   }

   public GetFieldProc(ClassType var1, String var2, Type var3, int var4) {
      this.ctype = var1;
      this.field = var1.getField(var2);
      if(this.field == null) {
         this.field = var1.addField(var2, var3, var4);
      }

   }

   public GetFieldProc(Class var1, String var2) {
      this((ClassType)((ClassType)Type.make(var1)), var2);
   }

   private Field getField() {
      return this.field;
   }

   public Object apply1(Object var1) {
      try {
         var1 = this.field.getReflectField().get(var1);
         return var1;
      } catch (NoSuchFieldException var2) {
         throw new RuntimeException("no such field " + this.field.getSourceName() + " in " + this.ctype.getName());
      } catch (IllegalAccessException var3) {
         throw new RuntimeException("illegal access for field " + this.field.getSourceName());
      }
   }

   public void compile(ApplyExp var1, Compilation var2, Target var3) {
      if(this.ctype.getReflectClass().getClassLoader() instanceof ArrayClassLoader) {
         ApplyExp.compile(var1, var2, var3);
      } else {
         var1.getArgs()[0].compile(var2, (Type)this.ctype);
         var2.getCode().emitGetField(this.field);
         var3.compileFromStack(var2, this.field.getType());
      }
   }

   public Type getReturnType(Expression[] var1) {
      return this.getField().getType();
   }
}
