package gnu.mapping;

import gnu.mapping.Environment;
import gnu.mapping.LocationEnumeration;
import gnu.mapping.NamedLocation;
import gnu.mapping.Namespace;
import gnu.mapping.SharedLocation;
import gnu.mapping.SimpleEnvironment;
import gnu.mapping.Symbol;

public class InheritingEnvironment extends SimpleEnvironment {

   int baseTimestamp;
   Environment[] inherited;
   Namespace[] namespaceMap;
   int numInherited;
   Object[] propertyMap;


   public InheritingEnvironment(String var1, Environment var2) {
      super(var1);
      this.addParent(var2);
      if(var2 instanceof SimpleEnvironment) {
         SimpleEnvironment var4 = (SimpleEnvironment)var2;
         int var3 = var4.currentTimestamp + 1;
         var4.currentTimestamp = var3;
         this.baseTimestamp = var3;
         this.currentTimestamp = var3;
      }

   }

   public void addParent(Environment var1) {
      if(this.numInherited == 0) {
         this.inherited = new Environment[4];
      } else if(this.numInherited <= this.inherited.length) {
         Environment[] var2 = new Environment[this.numInherited * 2];
         System.arraycopy(this.inherited, 0, var2, 0, this.numInherited);
         this.inherited = var2;
      }

      this.inherited[this.numInherited] = var1;
      ++this.numInherited;
   }

   public LocationEnumeration enumerateAllLocations() {
      LocationEnumeration var1 = new LocationEnumeration(this.table, 1 << this.log2Size);
      var1.env = this;
      if(this.inherited != null && this.inherited.length > 0) {
         var1.inherited = this.inherited[0].enumerateAllLocations();
         var1.index = 0;
      }

      return var1;
   }

   public NamedLocation getLocation(Symbol param1, Object param2, int param3, boolean param4) {
      // $FF: Couldn't be decompiled
   }

   public final int getNumParents() {
      return this.numInherited;
   }

   public final Environment getParent(int var1) {
      return this.inherited[var1];
   }

   protected boolean hasMoreElements(LocationEnumeration var1) {
      if(var1.inherited != null) {
         while(true) {
            NamedLocation var2 = var1.nextLoc;

            while(true) {
               var1.inherited.nextLoc = var2;
               if(!var1.inherited.hasMoreElements()) {
                  var1.prevLoc = null;
                  var1.nextLoc = var1.inherited.nextLoc;
                  int var3 = var1.index + 1;
                  var1.index = var3;
                  if(var3 == this.numInherited) {
                     var1.inherited = null;
                     var1.bindings = this.table;
                     var1.index = 1 << this.log2Size;
                     return super.hasMoreElements(var1);
                  }

                  var1.inherited = this.inherited[var1.index].enumerateAllLocations();
                  break;
               }

               var2 = var1.inherited.nextLoc;
               if(this.lookup(var2.name, var2.property) == var2) {
                  var1.nextLoc = var2;
                  return true;
               }

               var2 = var2.next;
            }
         }
      } else {
         return super.hasMoreElements(var1);
      }
   }

   public NamedLocation lookup(Symbol var1, Object var2, int var3) {
      NamedLocation var4 = super.lookup(var1, var2, var3);
      return var4 != null && var4.isBound()?var4:this.lookupInherited(var1, var2, var3);
   }

   public NamedLocation lookupInherited(Symbol var1, Object var2, int var3) {
      for(int var9 = 0; var9 < this.numInherited; ++var9) {
         Symbol var5 = var1;
         if(this.namespaceMap != null) {
            var5 = var1;
            if(this.namespaceMap.length > var9 * 2) {
               label60: {
                  Namespace var7 = this.namespaceMap[var9 * 2];
                  Namespace var8 = this.namespaceMap[var9 * 2 + 1];
                  if(var7 == null) {
                     var5 = var1;
                     if(var8 == null) {
                        break label60;
                     }
                  }

                  if(var1.getNamespace() != var8) {
                     continue;
                  }

                  var5 = Symbol.make(var7, var1.getName());
               }
            }
         }

         Object var6 = var2;
         if(this.propertyMap != null) {
            var6 = var2;
            if(this.propertyMap.length > var9 * 2) {
               label61: {
                  Object var10 = this.propertyMap[var9 * 2];
                  Object var11 = this.propertyMap[var9 * 2 + 1];
                  if(var10 == null) {
                     var6 = var2;
                     if(var11 == null) {
                        break label61;
                     }
                  }

                  if(var2 != var11) {
                     continue;
                  }

                  var6 = var10;
               }
            }
         }

         NamedLocation var4 = this.inherited[var9].lookup(var5, var6, var3);
         if(var4 != null && var4.isBound() && (!(var4 instanceof SharedLocation) || ((SharedLocation)var4).timestamp < this.baseTimestamp)) {
            return var4;
         }
      }

      return null;
   }

   protected void toStringBase(StringBuffer var1) {
      var1.append(" baseTs:");
      var1.append(this.baseTimestamp);

      for(int var2 = 0; var2 < this.numInherited; ++var2) {
         var1.append(" base:");
         var1.append(this.inherited[var2].toStringVerbose());
      }

   }
}
