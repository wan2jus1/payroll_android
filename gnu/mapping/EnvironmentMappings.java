package gnu.mapping;

import gnu.mapping.LocationEnumeration;
import gnu.mapping.SimpleEnvironment;
import java.util.AbstractSet;
import java.util.Iterator;

class EnvironmentMappings extends AbstractSet {

   SimpleEnvironment env;


   public EnvironmentMappings(SimpleEnvironment var1) {
      this.env = var1;
   }

   public Iterator iterator() {
      return new LocationEnumeration(this.env);
   }

   public int size() {
      return this.env.size();
   }
}
