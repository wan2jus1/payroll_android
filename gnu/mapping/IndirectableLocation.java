package gnu.mapping;

import gnu.mapping.Environment;
import gnu.mapping.Location;
import gnu.mapping.NamedLocation;
import gnu.mapping.PlainLocation;
import gnu.mapping.Symbol;

public abstract class IndirectableLocation extends Location {

   protected static final Object DIRECT_ON_SET = new String("(direct-on-set)");
   protected static final Object INDIRECT_FLUIDS = new String("(indirect-fluids)");
   protected Location base;
   protected Object value;


   public Location getBase() {
      return (Location)(this.base == null?this:this.base.getBase());
   }

   public Location getBaseForce() {
      return (Location)(this.base == null?new PlainLocation(this.getKeySymbol(), this.getKeyProperty(), this.value):this.base);
   }

   public Environment getEnvironment() {
      return this.base instanceof NamedLocation?((NamedLocation)this.base).getEnvironment():null;
   }

   public Object getKeyProperty() {
      return this.base != null?this.base.getKeyProperty():null;
   }

   public Symbol getKeySymbol() {
      return this.base != null?this.base.getKeySymbol():null;
   }

   public boolean isConstant() {
      return this.base != null && this.base.isConstant();
   }

   public void setAlias(Location var1) {
      this.base = var1;
      this.value = INDIRECT_FLUIDS;
   }

   public void setBase(Location var1) {
      this.base = var1;
      this.value = null;
   }

   public void undefine() {
      this.base = null;
      this.value = UNBOUND;
   }
}
