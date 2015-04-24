package gnu.expr;

import gnu.expr.Language;
import gnu.mapping.Environment;
import gnu.mapping.Location;
import gnu.mapping.LocationEnumeration;
import gnu.mapping.NamedLocation;
import gnu.mapping.Symbol;
import gnu.mapping.ThreadLocation;

public class BuiltinEnvironment extends Environment {

   static final BuiltinEnvironment instance = new BuiltinEnvironment();


   static {
      instance.setName("language-builtins");
   }

   public static BuiltinEnvironment getInstance() {
      return instance;
   }

   public NamedLocation addLocation(Symbol var1, Object var2, Location var3) {
      throw new RuntimeException();
   }

   public void define(Symbol var1, Object var2, Object var3) {
      throw new RuntimeException();
   }

   public LocationEnumeration enumerateAllLocations() {
      return this.getLangEnvironment().enumerateAllLocations();
   }

   public LocationEnumeration enumerateLocations() {
      return this.getLangEnvironment().enumerateLocations();
   }

   public Environment getLangEnvironment() {
      Language var1 = Language.getDefaultLanguage();
      return var1 == null?null:var1.getLangEnvironment();
   }

   public NamedLocation getLocation(Symbol var1, Object var2, int var3, boolean var4) {
      throw new RuntimeException();
   }

   protected boolean hasMoreElements(LocationEnumeration var1) {
      throw new RuntimeException();
   }

   public NamedLocation lookup(Symbol var1, Object var2, int var3) {
      if(var2 != ThreadLocation.ANONYMOUS) {
         Language var4 = Language.getDefaultLanguage();
         if(var4 != null) {
            return var4.lookupBuiltin(var1, var2, var3);
         }
      }

      return null;
   }
}
