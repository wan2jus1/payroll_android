package gnu.kawa.functions;

import gnu.bytecode.Type;
import gnu.mapping.Procedure;
import gnu.mapping.Procedure2;

public class Convert extends Procedure2 {

   public static final Convert as = new Convert();


   static {
      as.setName("as");
      as.setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileMisc:validateApplyConvert");
      Procedure.compilerKey.set(as, "*gnu.kawa.functions.CompileMisc:forConvert");
   }

   public static Convert getInstance() {
      return as;
   }

   public Object apply2(Object var1, Object var2) {
      Type var3;
      if(var1 instanceof Class) {
         var3 = Type.make((Class)var1);
      } else {
         var3 = (Type)var1;
      }

      return var3.coerceFromObject(var2);
   }
}
