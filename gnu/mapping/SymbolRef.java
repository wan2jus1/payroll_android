package gnu.mapping;

import gnu.mapping.Namespace;
import gnu.mapping.Symbol;
import java.lang.ref.WeakReference;

class SymbolRef extends WeakReference {

   SymbolRef next;


   SymbolRef(Symbol var1, Namespace var2) {
      super(var1);
   }

   Symbol getSymbol() {
      return (Symbol)this.get();
   }

   public String toString() {
      return "SymbolRef[" + this.getSymbol() + "]";
   }
}
