package gnu.mapping;

import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Environment;
import gnu.mapping.Location;
import gnu.mapping.Namespace;
import gnu.mapping.Symbol;

public class PropertyLocation extends Location {

   Pair pair;


   public static Object getProperty(Object var0, Object var1, Object var2) {
      return getProperty(var0, var1, var2, Environment.getCurrent());
   }

   public static Object getProperty(Object var0, Object var1, Object var2, Environment var3) {
      Object var4 = var0;
      if(!(var0 instanceof Symbol)) {
         if(!(var0 instanceof String)) {
            return plistGet(var3.get(Symbol.PLIST, var0, LList.Empty), var1, var2);
         }

         var4 = Namespace.getDefaultSymbol((String)var0);
      }

      return var3.get((Symbol)var4, var1, var2);
   }

   public static Object getPropertyList(Object var0) {
      return Environment.getCurrent().get(Symbol.PLIST, var0, LList.Empty);
   }

   public static Object getPropertyList(Object var0, Environment var1) {
      return var1.get(Symbol.PLIST, var0, LList.Empty);
   }

   public static Object plistGet(Object var0, Object var1, Object var2) {
      while(true) {
         Object var3 = var2;
         if(var0 instanceof Pair) {
            Pair var4 = (Pair)var0;
            if(var4.getCar() != var1) {
               continue;
            }

            var3 = ((Pair)var4.getCdr()).getCar();
         }

         return var3;
      }
   }

   public static Object plistPut(Object var0, Object var1, Object var2) {
      Pair var4;
      for(Object var3 = var0; var3 instanceof Pair; var3 = var4.getCdr()) {
         Pair var5 = (Pair)var3;
         var4 = (Pair)var5.getCdr();
         if(var5.getCar() == var1) {
            var4.setCar(var2);
            return var0;
         }
      }

      return new Pair(var1, new Pair(var2, var0));
   }

   public static Object plistRemove(Object var0, Object var1) {
      Pair var2 = null;
      Object var3 = var0;

      Object var4;
      while(true) {
         var4 = var0;
         if(!(var3 instanceof Pair)) {
            break;
         }

         Pair var5 = (Pair)var3;
         Pair var6 = (Pair)var5.getCdr();
         var3 = var6.getCdr();
         if(var5.getCar() == var1) {
            if(var2 != null) {
               var2.setCdr(var3);
               return var0;
            }

            var4 = var3;
            break;
         }

         var2 = var6;
      }

      return var4;
   }

   public static void putProperty(Object var0, Object var1, Object var2) {
      putProperty(var0, var1, var2, Environment.getCurrent());
   }

   public static void putProperty(Object var0, Object var1, Object var2, Environment var3) {
      Object var4 = var0;
      Location var5;
      if(!(var0 instanceof Symbol)) {
         if(!(var0 instanceof String)) {
            var5 = var3.getLocation(Symbol.PLIST, var0);
            var5.set(plistPut(var5.get(LList.Empty), var1, var2));
            return;
         }

         var4 = Namespace.getDefaultSymbol((String)var0);
      }

      var5 = var3.lookup((Symbol)var4, var1);
      if(var5 != null) {
         var5 = var5.getBase();
         if(var5 instanceof PropertyLocation) {
            ((PropertyLocation)var5).set(var2);
            return;
         }
      }

      var5 = var3.getLocation(Symbol.PLIST, var4);
      Pair var7 = new Pair(var2, var5.get(LList.Empty));
      var5.set(new Pair(var1, var7));
      PropertyLocation var6 = new PropertyLocation();
      var6.pair = var7;
      var3.addLocation((Symbol)var4, var1, var6);
   }

   public static boolean removeProperty(Object var0, Object var1) {
      return removeProperty(var0, var1, Environment.getCurrent());
   }

   public static boolean removeProperty(Object var0, Object var1, Environment var2) {
      Location var5 = var2.lookup(Symbol.PLIST, var0);
      if(var5 != null) {
         Object var3 = var5.get(LList.Empty);
         if(var3 instanceof Pair) {
            Pair var7 = (Pair)var3;
            Pair var4 = null;

            while(true) {
               if(var7.getCar() == var1) {
                  var3 = ((Pair)var7.getCdr()).getCdr();
                  if(var4 == null) {
                     var5.set(var3);
                  } else {
                     var4.setCdr(var3);
                  }

                  if(var0 instanceof Symbol) {
                     var2.remove((Symbol)var0, var1);
                  }

                  return true;
               }

               Object var6 = var7.getCdr();
               if(!(var6 instanceof Pair)) {
                  break;
               }

               var4 = var7;
               var7 = (Pair)var6;
            }
         }
      }

      return false;
   }

   public static void setPropertyList(Object var0, Object var1) {
      setPropertyList(var0, var1, Environment.getCurrent());
   }

   public static void setPropertyList(Object param0, Object param1, Environment param2) {
      // $FF: Couldn't be decompiled
   }

   public final Object get(Object var1) {
      return this.pair.getCar();
   }

   public boolean isBound() {
      return true;
   }

   public final void set(Object var1) {
      this.pair.setCar(var1);
   }
}
