package gnu.mapping;

import gnu.mapping.Location;
import gnu.mapping.NamedLocation;
import gnu.mapping.Symbol;

public class PlainLocation extends NamedLocation {

   public PlainLocation(Symbol var1, Object var2) {
      super(var1, var2);
   }

   public PlainLocation(Symbol var1, Object var2, Object var3) {
      super(var1, var2);
      this.value = var3;
   }

   public final Object get(Object var1) {
      if(this.base != null) {
         var1 = this.base.get(var1);
      } else if(this.value != Location.UNBOUND) {
         return this.value;
      }

      return var1;
   }

   public boolean isBound() {
      return this.base != null?this.base.isBound():this.value != Location.UNBOUND;
   }

   public final void set(Object var1) {
      if(this.base == null) {
         this.value = var1;
      } else if(this.value == DIRECT_ON_SET) {
         this.base = null;
         this.value = var1;
      } else if(this.base.isConstant()) {
         this.getEnvironment().put(this.getKeySymbol(), this.getKeyProperty(), var1);
      } else {
         this.base.set(var1);
      }
   }
}
