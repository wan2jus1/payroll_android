package gnu.mapping;

import gnu.mapping.EnvironmentKey;
import gnu.mapping.Symbol;

public class KeyPair implements EnvironmentKey {

   Symbol name;
   Object property;


   public KeyPair(Symbol var1, Object var2) {
      this.name = var1;
      this.property = var2;
   }

   public boolean equals(Object var1) {
      if(var1 instanceof KeyPair) {
         KeyPair var2 = (KeyPair)var1;
         if(this.property == var2.property) {
            if(this.name == null) {
               if(var2.name != null) {
                  return false;
               }
            } else if(!this.name.equals(var2.name)) {
               return false;
            }

            return true;
         }
      }

      return false;
   }

   public Object getKeyProperty() {
      return this.property;
   }

   public Symbol getKeySymbol() {
      return this.name;
   }

   public int hashCode() {
      return this.name.hashCode() ^ System.identityHashCode(this.property);
   }

   public final boolean matches(EnvironmentKey var1) {
      return Symbol.equals(var1.getKeySymbol(), this.name) && var1.getKeyProperty() == this.property;
   }

   public final boolean matches(Symbol var1, Object var2) {
      return Symbol.equals(var1, this.name) && var2 == this.property;
   }

   public String toString() {
      return "KeyPair[sym:" + this.name + " prop:" + this.property + "]";
   }
}
