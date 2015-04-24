package gnu.mapping;

import gnu.mapping.ConstrainedLocation;
import gnu.mapping.Location;
import gnu.mapping.Symbol;

public class ReadOnlyLocation extends ConstrainedLocation {

   public static ReadOnlyLocation make(Location var0) {
      ReadOnlyLocation var1 = new ReadOnlyLocation();
      var1.base = var0;
      return var1;
   }

   protected Object coerce(Object var1) {
      StringBuffer var3 = new StringBuffer("attempt to modify read-only location");
      Symbol var2 = this.getKeySymbol();
      if(var2 != null) {
         var3.append(": ");
         var3.append(var2);
      }

      throw new IllegalStateException(var3.toString());
   }

   public boolean isConstant() {
      return true;
   }
}
