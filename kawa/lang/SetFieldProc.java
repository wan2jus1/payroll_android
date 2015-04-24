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
import gnu.mapping.Procedure2;
import gnu.mapping.Values;

public class SetFieldProc extends Procedure2 implements Inlineable {

   ClassType ctype;
   Field field;


   public SetFieldProc(ClassType var1, String var2) {
      this.ctype = var1;
      this.field = Field.searchField(var1.getFields(), var2);
   }

   public SetFieldProc(ClassType var1, String var2, Type var3, int var4) {
      this.ctype = var1;
      this.field = var1.getField(var2);
      if(this.field == null) {
         this.field = var1.addField(var2, var3, var4);
      }

   }

   public SetFieldProc(Class var1, String var2) {
      this((ClassType)((ClassType)Type.make(var1)), var2);
   }

   public Object apply2(Object var1, Object var2) {
      try {
         this.field.getReflectField().set(var1, this.field.getType().coerceFromObject(var2));
      } catch (NoSuchFieldException var3) {
         throw new RuntimeException("no such field " + this.field.getSourceName() + " in " + this.ctype.getName());
      } catch (IllegalAccessException var4) {
         throw new RuntimeException("illegal access for field " + this.field.getSourceName());
      }

      return Values.empty;
   }

   public void compile(ApplyExp var1, Compilation var2, Target var3) {
      if(this.ctype.getReflectClass().getClassLoader() instanceof ArrayClassLoader) {
         ApplyExp.compile(var1, var2, var3);
      } else {
         Expression[] var4 = var1.getArgs();
         var4[0].compile(var2, (Type)this.ctype);
         var4[1].compile(var2, (Type)this.field.getType());
         var2.getCode().emitPutField(this.field);
         var2.compileConstant(Values.empty, var3);
      }
   }

   public Type getReturnType(Expression[] var1) {
      return Type.voidType;
   }
}
