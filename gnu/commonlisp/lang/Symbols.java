package gnu.commonlisp.lang;

import gnu.commonlisp.lang.Lisp2;
import gnu.mapping.Environment;
import gnu.mapping.EnvironmentKey;
import gnu.mapping.Namespace;
import gnu.mapping.Symbol;

public class Symbols {

   public static Object getFunctionBinding(Environment var0, Object var1) {
      return var0.getFunction(getSymbol(var1));
   }

   public static Object getFunctionBinding(Object var0) {
      return Environment.getCurrent().getFunction(getSymbol(var0));
   }

   public static Object getPrintName(Object var0) {
      return var0 == Lisp2.FALSE?"nil":Lisp2.getString((String)((Symbol)var0).getName());
   }

   public static Symbol getSymbol(Environment var0, Object var1) {
      Object var2 = var1;
      if(var1 == Lisp2.FALSE) {
         var2 = "nil";
      }

      return var2 instanceof Symbol?(Symbol)var2:var0.defaultNamespace().getSymbol((String)var2);
   }

   public static Symbol getSymbol(Object var0) {
      Object var1 = var0;
      if(var0 == Lisp2.FALSE) {
         var1 = "nil";
      }

      return var1 instanceof Symbol?(Symbol)var1:Namespace.getDefaultSymbol((String)var1);
   }

   public static boolean isBound(Object var0) {
      if(var0 != Lisp2.FALSE) {
         Environment var1 = Environment.getCurrent();
         Symbol var2;
         if(var0 instanceof Symbol) {
            var2 = (Symbol)var0;
         } else {
            var2 = var1.defaultNamespace().lookup((String)var0);
         }

         if(var2 == null || !var1.isBound(var2)) {
            return false;
         }
      }

      return true;
   }

   public static boolean isSymbol(Object var0) {
      return var0 instanceof String || var0 == Lisp2.FALSE || var0 instanceof Symbol;
   }

   public static void setFunctionBinding(Environment var0, Object var1, Object var2) {
      var0.put(getSymbol(var1), EnvironmentKey.FUNCTION, var2);
   }
}
