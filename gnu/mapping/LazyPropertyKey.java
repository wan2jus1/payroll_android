package gnu.mapping;

import gnu.mapping.PropertyKey;
import gnu.mapping.PropertySet;

public class LazyPropertyKey extends PropertyKey {

   public LazyPropertyKey(String var1) {
      super(var1);
   }

   public Object get(PropertySet param1, Object param2) {
      // $FF: Couldn't be decompiled
   }

   public void set(PropertySet var1, String var2) {
      var1.setProperty(this, var2);
   }
}
