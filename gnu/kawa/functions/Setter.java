package gnu.kawa.functions;

import gnu.expr.Language;
import gnu.kawa.functions.SetArray;
import gnu.kawa.functions.SetList;
import gnu.mapping.HasSetter;
import gnu.mapping.Procedure;
import gnu.mapping.Procedure1;
import java.util.List;

public class Setter extends Procedure1 implements HasSetter {

   public static final Setter setter = new Setter();


   static {
      setter.setName("setter");
      setter.setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompilationHelpers:validateSetter");
   }

   public static Object setter(Procedure var0) {
      return var0.getSetter();
   }

   public Object apply1(Object var1) {
      if(!(var1 instanceof Procedure)) {
         if(var1 instanceof List) {
            return new SetList((List)var1);
         }

         if(var1.getClass().isArray()) {
            return new SetArray(var1, Language.getDefaultLanguage());
         }
      }

      return ((Procedure)var1).getSetter();
   }

   public void set1(Object var1, Object var2) throws Throwable {
      ((Procedure)var1).setSetter((Procedure)var2);
   }
}
