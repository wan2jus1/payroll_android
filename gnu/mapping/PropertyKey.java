package gnu.mapping;

import gnu.mapping.PropertySet;

public class PropertyKey {

   String name;


   public PropertyKey(String var1) {
      this.name = var1;
   }

   public final Object get(PropertySet var1) {
      return this.get(var1, (Object)null);
   }

   public Object get(PropertySet var1, Object var2) {
      return var1.getProperty(this, var2);
   }

   public void set(PropertySet var1, Object var2) {
      var1.setProperty(this, var2);
   }
}
