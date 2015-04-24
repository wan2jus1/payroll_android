package gnu.expr;

import gnu.bytecode.ClassType;
import gnu.bytecode.Type;

public class PairClassType extends ClassType {

   public ClassType instanceType;
   Object staticLink;


   public PairClassType() {
   }

   PairClassType(Class var1, Class var2) {
      super(var1.getName());
      this.setExisting(true);
      this.reflectClass = var1;
      Type.registerTypeForClass(var1, this);
      this.instanceType = (ClassType)Type.make(var2);
   }

   public static Object extractStaticLink(ClassType var0) {
      return ((PairClassType)var0).staticLink;
   }

   public static PairClassType make(Class var0, Class var1) {
      return new PairClassType(var0, var1);
   }

   public static PairClassType make(Class var0, Class var1, Object var2) {
      PairClassType var3 = new PairClassType(var0, var1);
      var3.staticLink = var2;
      return var3;
   }

   public Object getStaticLink() {
      return this.staticLink;
   }
}
