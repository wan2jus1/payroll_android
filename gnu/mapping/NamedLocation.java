package gnu.mapping;

import gnu.mapping.Environment;
import gnu.mapping.EnvironmentKey;
import gnu.mapping.IndirectableLocation;
import gnu.mapping.Location;
import gnu.mapping.Symbol;
import gnu.mapping.ThreadLocation;

public abstract class NamedLocation extends IndirectableLocation implements java.util.Map.Entry, EnvironmentKey {

   final Symbol name;
   NamedLocation next;
   final Object property;


   public NamedLocation(NamedLocation var1) {
      this.name = var1.name;
      this.property = var1.property;
   }

   public NamedLocation(Symbol var1, Object var2) {
      this.name = var1;
      this.property = var2;
   }

   public boolean entered() {
      return this.next != null;
   }

   public boolean equals(Object var1) {
      if(var1 instanceof NamedLocation) {
         NamedLocation var2 = (NamedLocation)var1;
         if(this.name == null) {
            if(var2.name != null) {
               return false;
            }
         } else if(!this.name.equals(var2.name)) {
            return false;
         }

         if(this.property == var2.property) {
            var1 = this.getValue();
            Object var3 = var2.getValue();
            if(var1 == var3) {
               return true;
            }

            if(var1 != null && var3 != null) {
               return var1.equals(var3);
            }
         }
      }

      return false;
   }

   public Environment getEnvironment() {
      for(NamedLocation var1 = this; var1 != null; var1 = var1.next) {
         if(var1.name == null) {
            Environment var2 = (Environment)var1.value;
            if(var2 != null) {
               return var2;
            }
         }
      }

      return super.getEnvironment();
   }

   public final Object getKey() {
      Object var1 = this;
      if(this.property == null) {
         var1 = this.name;
      }

      return var1;
   }

   public final Object getKeyProperty() {
      return this.property;
   }

   public final Symbol getKeySymbol() {
      return this.name;
   }

   public int hashCode() {
      int var3 = this.name.hashCode() ^ System.identityHashCode(this.property);
      Object var1 = this.getValue();
      int var2 = var3;
      if(var1 != null) {
         var2 = var3 ^ var1.hashCode();
      }

      return var2;
   }

   public final boolean matches(EnvironmentKey var1) {
      return Symbol.equals(var1.getKeySymbol(), this.name) && var1.getKeyProperty() == this.property;
   }

   public final boolean matches(Symbol var1, Object var2) {
      return Symbol.equals(var1, this.name) && var2 == this.property;
   }

   public void setRestore(Object var1) {
      synchronized(this){}

      try {
         if(this.value == INDIRECT_FLUIDS) {
            this.base.setRestore(var1);
         } else if(var1 instanceof Location) {
            this.value = null;
            this.base = (Location)var1;
         } else {
            this.value = var1;
            this.base = null;
         }
      } finally {
         ;
      }

   }

   public Object setWithSave(Object var1) {
      synchronized(this){}

      try {
         if(this.value == INDIRECT_FLUIDS) {
            var1 = this.base.setWithSave(var1);
         } else {
            ThreadLocation var2 = ThreadLocation.makeAnonymous((Symbol)this.name);
            var2.global.base = this.base;
            var2.global.value = this.value;
            this.setAlias(var2);
            NamedLocation var3 = var2.getLocation();
            var3.value = var1;
            var3.base = null;
            var1 = var2.global;
         }
      } finally {
         ;
      }

      return var1;
   }
}
