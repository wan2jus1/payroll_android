package gnu.mapping;

import gnu.mapping.Location;
import gnu.mapping.NamedLocation;
import gnu.mapping.Symbol;

public class SharedLocation extends NamedLocation {

   int timestamp;


   public SharedLocation(Symbol var1, Object var2, int var3) {
      super(var1, var2);
      this.timestamp = var3;
   }

   public final Object get(Object var1) {
      synchronized(this){}

      try {
         if(this.base != null) {
            var1 = this.base.get(var1);
         } else if(this.value != Location.UNBOUND) {
            var1 = this.value;
         }
      } finally {
         ;
      }

      return var1;
   }

   public boolean isBound() {
      synchronized(this){}
      boolean var5 = false;

      Object var1;
      String var2;
      boolean var3;
      try {
         var5 = true;
         if(this.base != null) {
            var3 = this.base.isBound();
            var5 = false;
            return var3;
         }

         var1 = this.value;
         var2 = Location.UNBOUND;
         var5 = false;
      } finally {
         if(var5) {
            ;
         }
      }

      if(var1 != var2) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public final void set(Object var1) {
      synchronized(this){}

      try {
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
      } finally {
         ;
      }

   }
}
