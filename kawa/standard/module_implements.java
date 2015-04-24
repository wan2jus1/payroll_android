package kawa.standard;

import gnu.bytecode.ClassType;
import gnu.expr.ModuleExp;
import gnu.expr.ScopeExp;
import gnu.lists.LList;
import gnu.lists.Pair;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class module_implements extends Syntax {

   public static final module_implements module_implements = new module_implements();


   static {
      module_implements.setName("module-implements");
   }

   public void scanForm(Pair var1, ScopeExp var2, Translator var3) {
      Object var6 = var1.getCdr();
      int var5 = LList.listLength(var6, false);
      if(var5 < 0) {
         var3.syntaxError("improper argument list for " + this.getName());
      } else {
         ClassType[] var8 = new ClassType[var5];

         for(int var4 = 0; var4 < var5; ++var4) {
            var1 = (Pair)var6;
            var8[var4] = (ClassType)var3.exp2Type(var1);
            var6 = var1.getCdr();
         }

         ModuleExp var7 = var3.getModule();
         var7.setInterfaces(var8);
         var7.setFlag(131072);
      }
   }
}
