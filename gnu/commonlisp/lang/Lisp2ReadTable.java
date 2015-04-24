package gnu.commonlisp.lang;

import gnu.commonlisp.lang.Lisp2;
import gnu.kawa.lispexpr.ReadTable;

class Lisp2ReadTable extends ReadTable {

   protected Object makeSymbol(String var1) {
      return Lisp2.asSymbol(var1.intern());
   }
}
