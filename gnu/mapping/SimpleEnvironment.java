package gnu.mapping;

import gnu.mapping.Environment;
import gnu.mapping.EnvironmentMappings;
import gnu.mapping.IndirectableLocation;
import gnu.mapping.InheritingEnvironment;
import gnu.mapping.Location;
import gnu.mapping.LocationEnumeration;
import gnu.mapping.NamedLocation;
import gnu.mapping.PlainLocation;
import gnu.mapping.SharedLocation;
import gnu.mapping.Symbol;
import gnu.mapping.ThreadLocation;
import gnu.mapping.UnboundLocationException;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;
import java.util.Set;

public class SimpleEnvironment extends Environment {

   int currentTimestamp;
   int log2Size;
   private int mask;
   int num_bindings;
   NamedLocation sharedTail;
   NamedLocation[] table;


   public SimpleEnvironment() {
      this(64);
   }

   public SimpleEnvironment(int var1) {
      for(this.log2Size = 4; var1 > 1 << this.log2Size; ++this.log2Size) {
         ;
      }

      var1 = 1 << this.log2Size;
      this.table = new NamedLocation[var1];
      this.mask = var1 - 1;
      this.sharedTail = new PlainLocation((Symbol)null, (Object)null, this);
   }

   public SimpleEnvironment(String var1) {
      this();
      this.setName(var1);
   }

   public static Location getCurrentLocation(String var0) {
      return getCurrent().getLocation(var0, true);
   }

   public static Object lookup_global(Symbol var0) throws UnboundLocationException {
      Location var1 = getCurrent().lookup(var0);
      if(var1 == null) {
         throw new UnboundLocationException(var0);
      } else {
         return var1.get();
      }
   }

   NamedLocation addLocation(Symbol var1, Object var2, int var3, Location var4) {
      Object var5 = var4;
      if(var4 instanceof ThreadLocation) {
         var5 = var4;
         if(((ThreadLocation)var4).property == var2) {
            var5 = ((ThreadLocation)var4).getLocation();
         }
      }

      NamedLocation var8 = this.lookupDirect(var1, var2, var3);
      if(var5 == var8) {
         return var8;
      } else {
         boolean var6;
         if(var8 != null) {
            var6 = true;
         } else {
            var6 = false;
         }

         if(!var6) {
            var8 = this.addUnboundLocation(var1, var2, var3);
         }

         if((this.flags & 3) != 3) {
            label50: {
               boolean var7 = var6;
               if(var6) {
                  var7 = var8.isBound();
               }

               if(var7) {
                  if((this.flags & 2) != 0) {
                     break label50;
                  }
               } else if((this.flags & 1) != 0 || !((Location)var5).isBound()) {
                  break label50;
               }

               this.redefineError(var1, var2, var8);
            }
         }

         if((this.flags & 32) != 0) {
            var8.base = ((SimpleEnvironment)((InheritingEnvironment)this).getParent(0)).addLocation(var1, var2, var3, (Location)var5);
         } else {
            var8.base = (Location)var5;
         }

         var8.value = IndirectableLocation.INDIRECT_FLUIDS;
         return var8;
      }
   }

   public NamedLocation addLocation(Symbol var1, Object var2, Location var3) {
      return this.addLocation(var1, var2, var1.hashCode() ^ System.identityHashCode(var2), var3);
   }

   protected NamedLocation addUnboundLocation(Symbol var1, Object var2, int var3) {
      NamedLocation var4 = this.newEntry(var1, var2, var3 & this.mask);
      var4.base = null;
      var4.value = Location.UNBOUND;
      return var4;
   }

   public NamedLocation define(Symbol var1, Object var2, int var3, Object var4) {
      var3 &= this.mask;

      for(NamedLocation var5 = this.table[var3]; var5 != null; var5 = var5.next) {
         if(var5.matches(var1, var2)) {
            label19: {
               if(var5.isBound()) {
                  if(this.getCanDefine()) {
                     break label19;
                  }
               } else if(this.getCanRedefine()) {
                  break label19;
               }

               this.redefineError(var1, var2, var5);
            }

            var5.base = null;
            var5.value = var4;
            return var5;
         }
      }

      NamedLocation var6 = this.newEntry(var1, var2, var3);
      var6.set(var4);
      return var6;
   }

   public void define(Symbol var1, Object var2, Object var3) {
      this.define(var1, var2, var1.hashCode() ^ System.identityHashCode(var2), var3);
   }

   public Set entrySet() {
      return new EnvironmentMappings(this);
   }

   public LocationEnumeration enumerateAllLocations() {
      return this.enumerateLocations();
   }

   public LocationEnumeration enumerateLocations() {
      LocationEnumeration var1 = new LocationEnumeration(this.table, 1 << this.log2Size);
      var1.env = this;
      return var1;
   }

   public NamedLocation getLocation(Symbol param1, Object param2, int param3, boolean param4) {
      // $FF: Couldn't be decompiled
   }

   protected boolean hasMoreElements(LocationEnumeration var1) {
      while(true) {
         if(var1.nextLoc == null) {
            var1.prevLoc = null;
            int var2 = var1.index - 1;
            var1.index = var2;
            if(var2 < 0) {
               return false;
            }

            var1.nextLoc = var1.bindings[var1.index];
            if(var1.nextLoc == null) {
               continue;
            }
         }

         if(var1.nextLoc.name != null) {
            return true;
         }

         var1.nextLoc = var1.nextLoc.next;
      }
   }

   public NamedLocation lookup(Symbol var1, Object var2, int var3) {
      return this.lookupDirect(var1, var2, var3);
   }

   public NamedLocation lookupDirect(Symbol var1, Object var2, int var3) {
      int var5 = this.mask;

      for(NamedLocation var4 = this.table[var3 & var5]; var4 != null; var4 = var4.next) {
         if(var4.matches(var1, var2)) {
            return var4;
         }
      }

      return null;
   }

   NamedLocation newEntry(Symbol var1, Object var2, int var3) {
      NamedLocation var4 = this.newLocation(var1, var2);
      NamedLocation var6 = this.table[var3];
      NamedLocation var5 = var6;
      if(var6 == null) {
         var5 = this.sharedTail;
      }

      var4.next = var5;
      this.table[var3] = var4;
      ++this.num_bindings;
      if(this.num_bindings >= this.table.length) {
         this.rehash();
      }

      return var4;
   }

   NamedLocation newLocation(Symbol var1, Object var2) {
      return (NamedLocation)((this.flags & 8) != 0?new SharedLocation(var1, var2, this.currentTimestamp):new PlainLocation(var1, var2));
   }

   public void put(Symbol var1, Object var2, Object var3) {
      boolean var4;
      if((this.flags & 4) != 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      NamedLocation var5 = this.getLocation(var1, var2, var4);
      if(var5 == null) {
         throw new UnboundLocationException(var1);
      } else if(var5.isConstant()) {
         throw new IllegalStateException("attempt to modify read-only location: " + var1 + " in " + this + " loc:" + var5);
      } else {
         var5.set(var3);
      }
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      this.setSymbol(var1.readObject());
   }

   public Object readResolve() throws ObjectStreamException {
      String var1 = this.getName();
      Environment var2 = (Environment)envTable.get(var1);
      if(var2 != null) {
         return var2;
      } else {
         envTable.put(var1, this);
         return this;
      }
   }

   protected void redefineError(Symbol var1, Object var2, Location var3) {
      throw new IllegalStateException("prohibited define/redefine of " + var1 + " in " + this);
   }

   void rehash() {
      NamedLocation[] var5 = this.table;
      int var7 = var5.length;
      int var8 = var7 * 2;
      NamedLocation[] var6 = new NamedLocation[var8];
      int var9 = var8 - 1;

      while(true) {
         var8 = var7 - 1;
         if(var8 < 0) {
            this.table = var6;
            ++this.log2Size;
            this.mask = var9;
            return;
         }

         NamedLocation var1 = var5[var8];

         while(true) {
            var7 = var8;
            if(var1 == null) {
               break;
            }

            var7 = var8;
            if(var1 == this.sharedTail) {
               break;
            }

            NamedLocation var3 = var1.next;
            Symbol var2 = var1.name;
            Object var4 = var1.property;
            var7 = (var2.hashCode() ^ System.identityHashCode(var4)) & var9;
            NamedLocation var11 = var6[var7];
            NamedLocation var10 = var11;
            if(var11 == null) {
               var10 = this.sharedTail;
            }

            var1.next = var10;
            var6[var7] = var1;
            var1 = var3;
         }
      }
   }

   public int size() {
      return this.num_bindings;
   }

   protected void toStringBase(StringBuffer var1) {
   }

   public String toStringVerbose() {
      StringBuffer var1 = new StringBuffer();
      this.toStringBase(var1);
      return "#<environment " + this.getName() + " num:" + this.num_bindings + " ts:" + this.currentTimestamp + var1 + '>';
   }

   public Location unlink(Symbol var1, Object var2, int var3) {
      var3 &= this.mask;
      NamedLocation var5 = null;

      NamedLocation var6;
      for(NamedLocation var4 = this.table[var3]; var4 != null; var4 = var6) {
         var6 = var4.next;
         if(var4.matches(var1, var2)) {
            if(!this.getCanRedefine()) {
               this.redefineError(var1, var2, var4);
            }

            if(var5 == null) {
               this.table[var3] = var6;
            } else {
               var5.next = var4;
            }

            --this.num_bindings;
            return var4;
         }

         var5 = var4;
      }

      return null;
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      var1.writeObject(this.getSymbol());
   }
}
