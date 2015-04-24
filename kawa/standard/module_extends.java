package kawa.standard;

import gnu.bytecode.ClassType;
import gnu.bytecode.Type;
import gnu.expr.ModuleExp;
import gnu.expr.ScopeExp;
import gnu.lists.Pair;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class module_extends extends Syntax {

   public static final module_extends module_extends = new module_extends();


   static {
      module_extends.setName("module-extends");
   }

   public void scanForm(Pair var1, ScopeExp var2, Translator var3) {
      Type var4 = var3.exp2Type((Pair)var1.getCdr());
      ModuleExp var5 = var3.getModule();
      var5.setSuperType((ClassType)var4);
      var5.setFlag(131072);
   }
}
