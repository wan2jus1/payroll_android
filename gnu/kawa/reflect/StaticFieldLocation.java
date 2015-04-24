package gnu.kawa.reflect;

import gnu.bytecode.ClassType;
import gnu.expr.Declaration;
import gnu.kawa.reflect.FieldLocation;
import gnu.mapping.Environment;
import gnu.mapping.Symbol;
import java.lang.reflect.Field;
import kawa.lang.Macro;

public class StaticFieldLocation extends FieldLocation {

   public StaticFieldLocation(ClassType var1, String var2) {
      super((Object)null, (ClassType)var1, var2);
   }

   public StaticFieldLocation(String var1, String var2) {
      super((Object)null, (ClassType)ClassType.make(var1), var2);
   }

   public StaticFieldLocation(Field var1) {
      super((Object)null, var1);
   }

   public static StaticFieldLocation define(Environment var0, Symbol var1, Object var2, String var3, String var4) {
      StaticFieldLocation var5 = new StaticFieldLocation(var3, var4);
      var0.addLocation(var1, var2, var5);
      return var5;
   }

   public static StaticFieldLocation make(Declaration var0) {
      gnu.bytecode.Field var1 = var0.field;
      StaticFieldLocation var2 = new StaticFieldLocation(var1.getDeclaringClass(), var1.getName());
      var2.setDeclaration(var0);
      return var2;
   }

   public static StaticFieldLocation make(String var0, String var1) {
      return new StaticFieldLocation(var0, var1);
   }

   public Object get(Object var1) {
      var1 = super.get(var1);
      if(var1 instanceof Macro) {
         this.getDeclaration();
      }

      return var1;
   }
}
