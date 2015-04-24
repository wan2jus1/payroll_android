package kawa.standard;

import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.ModuleContext;
import gnu.mapping.SimpleSymbol;
import kawa.lang.SyntaxForm;

public class IfFeature {

   public static boolean hasFeature(String var0) {
      if(var0 != "kawa" && var0 != "srfi-0" && var0 != "srfi-4" && var0 != "srfi-6" && var0 != "srfi-8" && var0 != "srfi-9" && var0 != "srfi-11" && var0 != "srfi-16" && var0 != "srfi-17" && var0 != "srfi-23" && var0 != "srfi-25" && var0 != "srfi-26" && var0 != "srfi-28" && var0 != "srfi-30" && var0 != "srfi-39") {
         if(var0 == "in-http-server" || var0 == "in-servlet") {
            int var1 = ModuleContext.getContext().getFlags();
            if(var0 == "in-http-server") {
               if((ModuleContext.IN_HTTP_SERVER & var1) == 0) {
                  return false;
               }

               return true;
            }

            if(var0 == "in-servlet") {
               if((ModuleContext.IN_SERVLET & var1) == 0) {
                  return false;
               }

               return true;
            }
         }

         var0 = ("%provide%" + var0).intern();
         Declaration var2 = Compilation.getCurrent().lookup(var0, -1);
         if(var2 == null || var2.getFlag(65536L)) {
            return false;
         }
      }

      return true;
   }

   public static boolean testFeature(Object var0) {
      Object var1 = var0;
      if(var0 instanceof SyntaxForm) {
         var1 = ((SyntaxForm)var0).getDatum();
      }

      return !(var1 instanceof String) && !(var1 instanceof SimpleSymbol)?false:hasFeature(var1.toString());
   }
}
