package gnu.mapping;

import gnu.mapping.Location;
import gnu.mapping.Procedure;
import gnu.mapping.Symbol;
import gnu.mapping.WrappedException;

public class ConstrainedLocation extends Location {

   protected Location base;
   protected Procedure converter;


   public static ConstrainedLocation make(Location var0, Procedure var1) {
      ConstrainedLocation var2 = new ConstrainedLocation();
      var2.base = var0;
      var2.converter = var1;
      return var2;
   }

   protected Object coerce(Object var1) {
      try {
         var1 = this.converter.apply1(var1);
         return var1;
      } catch (Throwable var2) {
         throw WrappedException.wrapIfNeeded(var2);
      }
   }

   public final Object get(Object var1) {
      return this.base.get(var1);
   }

   public Object getKeyProperty() {
      return this.base.getKeyProperty();
   }

   public Symbol getKeySymbol() {
      return this.base.getKeySymbol();
   }

   public boolean isBound() {
      return this.base.isBound();
   }

   public boolean isConstant() {
      return this.base.isConstant();
   }

   public final void set(Object var1) {
      this.base.set(this.coerce(var1));
   }

   public void setRestore(Object var1) {
      this.base.setRestore(var1);
   }

   public Object setWithSave(Object var1) {
      return this.base.setWithSave(this.coerce(var1));
   }
}
