package kawa.standard;

import gnu.expr.Expression;
import gnu.expr.ScopeExp;
import gnu.lists.Pair;
import java.util.Vector;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class export extends Syntax {

   public static final export export;
   public static final export module_export = new export();


   static {
      module_export.setName("module-export");
      export = new export();
      module_export.setName("export");
   }

   public Expression rewriteForm(Pair var1, Translator var2) {
      return null;
   }

   public boolean scanForDefinitions(Pair param1, Vector param2, ScopeExp param3, Translator param4) {
      // $FF: Couldn't be decompiled
   }
}
