package gnu.mapping;

import gnu.mapping.EnvironmentKey;
import gnu.mapping.InheritingEnvironment;
import gnu.mapping.Location;
import gnu.mapping.LocationEnumeration;
import gnu.mapping.NamedLocation;
import gnu.mapping.Namespace;
import gnu.mapping.PropertySet;
import gnu.mapping.SharedLocation;
import gnu.mapping.SimpleEnvironment;
import gnu.mapping.Symbol;
import gnu.mapping.UnboundLocationException;
import java.util.Hashtable;

public abstract class Environment extends PropertySet {

   static final int CAN_DEFINE = 1;
   static final int CAN_IMPLICITLY_DEFINE = 4;
   static final int CAN_REDEFINE = 2;
   static final int DIRECT_INHERITED_ON_SET = 16;
   public static final int INDIRECT_DEFINES = 32;
   static final int THREAD_SAFE = 8;
   protected static final Environment.InheritedLocal curEnvironment = new Environment.InheritedLocal();
   static final Hashtable envTable = new Hashtable(50);
   static Environment global;
   int flags = 23;


   public static Environment current() {
      return getCurrent();
   }

   public static Environment getCurrent() {
      Environment var1 = (Environment)curEnvironment.get();
      Object var0 = var1;
      if(var1 == null) {
         var0 = make(Thread.currentThread().getName(), global);
         ((Environment)var0).flags |= 8;
         curEnvironment.set(var0);
      }

      return (Environment)var0;
   }

   public static Environment getGlobal() {
      return global;
   }

   public static Environment getInstance(String param0) {
      // $FF: Couldn't be decompiled
   }

   public static InheritingEnvironment make(String var0, Environment var1) {
      return new InheritingEnvironment(var0, var1);
   }

   public static SimpleEnvironment make() {
      return new SimpleEnvironment();
   }

   public static SimpleEnvironment make(String var0) {
      return new SimpleEnvironment(var0);
   }

   public static void restoreCurrent(Environment var0) {
      curEnvironment.set(var0);
   }

   public static void setCurrent(Environment var0) {
      curEnvironment.set(var0);
   }

   public static void setGlobal(Environment var0) {
      global = var0;
   }

   public static Environment setSaveCurrent(Environment var0) {
      Environment var1 = (Environment)curEnvironment.get();
      curEnvironment.set(var0);
      return var1;
   }

   public static Environment user() {
      return getCurrent();
   }

   public abstract NamedLocation addLocation(Symbol var1, Object var2, Location var3);

   public final void addLocation(EnvironmentKey var1, Location var2) {
      this.addLocation(var1.getKeySymbol(), var1.getKeyProperty(), var2);
   }

   public final void addLocation(NamedLocation var1) {
      this.addLocation(var1.getKeySymbol(), var1.getKeyProperty(), var1);
   }

   SimpleEnvironment cloneForThread() {
      InheritingEnvironment var3 = new InheritingEnvironment((String)null, this);
      if(this instanceof InheritingEnvironment) {
         InheritingEnvironment var1 = (InheritingEnvironment)this;
         int var8 = var1.numInherited;
         var3.numInherited = var8;
         var3.inherited = new Environment[var8];

         for(int var7 = 0; var7 < var8; ++var7) {
            var3.inherited[var7] = var1.inherited[var7];
         }
      }

      LocationEnumeration var4 = this.enumerateLocations();

      while(var4.hasMoreElements()) {
         Location var9 = var4.nextLocation();
         Symbol var5 = var9.getKeySymbol();
         Object var6 = var9.getKeyProperty();
         if(var5 != null && var9 instanceof NamedLocation) {
            NamedLocation var2 = (NamedLocation)var9;
            Object var10 = var2;
            if(var2.base == null) {
               var10 = new SharedLocation(var5, var6, 0);
               ((SharedLocation)var10).value = var2.value;
               var2.base = (Location)var10;
               var2.value = null;
            }

            var3.addUnboundLocation(var5, var6, var5.hashCode() ^ System.identityHashCode(var6)).base = (Location)var10;
         }
      }

      return var3;
   }

   public final boolean containsKey(Object var1) {
      Object var2 = null;
      Object var3 = var1;
      if(var1 instanceof EnvironmentKey) {
         EnvironmentKey var4 = (EnvironmentKey)var1;
         var3 = var4.getKeySymbol();
         var2 = var4.getKeyProperty();
      }

      Symbol var5;
      if(var3 instanceof Symbol) {
         var5 = (Symbol)var3;
      } else {
         var5 = this.getSymbol((String)var3);
      }

      return this.isBound(var5, var2);
   }

   public Namespace defaultNamespace() {
      return Namespace.getDefault();
   }

   public abstract void define(Symbol var1, Object var2, Object var3);

   public abstract LocationEnumeration enumerateAllLocations();

   public abstract LocationEnumeration enumerateLocations();

   public final Object get(EnvironmentKey var1, Object var2) {
      return this.get(var1.getKeySymbol(), var1.getKeyProperty(), var2);
   }

   public Object get(Symbol var1) {
      String var2 = Location.UNBOUND;
      Object var3 = this.get(var1, (Object)null, var2);
      if(var3 == var2) {
         throw new UnboundLocationException(var1);
      } else {
         return var3;
      }
   }

   public Object get(Symbol var1, Object var2, Object var3) {
      Location var4 = this.lookup(var1, var2);
      return var4 == null?var3:var4.get(var3);
   }

   public final Object get(Object var1) {
      Object var2 = null;
      Object var3 = var1;
      if(var1 instanceof EnvironmentKey) {
         EnvironmentKey var4 = (EnvironmentKey)var1;
         var3 = var4.getKeySymbol();
         var2 = var4.getKeyProperty();
      }

      Symbol var5;
      if(var3 instanceof Symbol) {
         var5 = (Symbol)var3;
      } else {
         var5 = this.getSymbol((String)var3);
      }

      return this.get(var5, var2, (Object)null);
   }

   public final Object get(String var1, Object var2) {
      return this.get(this.getSymbol(var1), (Object)null, var2);
   }

   public boolean getCanDefine() {
      return (this.flags & 1) != 0;
   }

   public boolean getCanRedefine() {
      return (this.flags & 2) != 0;
   }

   public final Object getChecked(String var1) {
      Object var2 = this.get((String)var1, Location.UNBOUND);
      if(var2 == Location.UNBOUND) {
         throw new UnboundLocationException(var1 + " in " + this);
      } else {
         return var2;
      }
   }

   public int getFlags() {
      return this.flags;
   }

   public final Object getFunction(Symbol var1) {
      String var2 = Location.UNBOUND;
      Object var3 = this.get(var1, EnvironmentKey.FUNCTION, var2);
      if(var3 == var2) {
         throw new UnboundLocationException(var1);
      } else {
         return var3;
      }
   }

   public final Object getFunction(Symbol var1, Object var2) {
      return this.get(var1, EnvironmentKey.FUNCTION, var2);
   }

   public final Location getLocation(Symbol var1) {
      return this.getLocation(var1, (Object)null, true);
   }

   public final Location getLocation(Symbol var1, Object var2) {
      return this.getLocation(var1, var2, true);
   }

   public final Location getLocation(Object var1, boolean var2) {
      Object var3 = null;
      Object var4 = var1;
      if(var1 instanceof EnvironmentKey) {
         EnvironmentKey var5 = (EnvironmentKey)var1;
         var4 = var5.getKeySymbol();
         var3 = var5.getKeyProperty();
      }

      Symbol var6;
      if(var4 instanceof Symbol) {
         var6 = (Symbol)var4;
      } else {
         var6 = this.getSymbol((String)var4);
      }

      return this.getLocation(var6, var3, var2);
   }

   public abstract NamedLocation getLocation(Symbol var1, Object var2, int var3, boolean var4);

   public final NamedLocation getLocation(Symbol var1, Object var2, boolean var3) {
      return this.getLocation(var1, var2, var1.hashCode() ^ System.identityHashCode(var2), var3);
   }

   public Symbol getSymbol(String var1) {
      return this.defaultNamespace().getSymbol(var1);
   }

   protected abstract boolean hasMoreElements(LocationEnumeration var1);

   public final boolean isBound(Symbol var1) {
      return this.isBound(var1, (Object)null);
   }

   public boolean isBound(Symbol var1, Object var2) {
      Location var3 = this.lookup(var1, var2);
      return var3 == null?false:var3.isBound();
   }

   public final boolean isLocked() {
      return (this.flags & 3) == 0;
   }

   public final Location lookup(Symbol var1) {
      return this.getLocation(var1, (Object)null, false);
   }

   public final Location lookup(Symbol var1, Object var2) {
      return this.getLocation(var1, var2, false);
   }

   public abstract NamedLocation lookup(Symbol var1, Object var2, int var3);

   public final Object put(Object var1, Object var2) {
      Location var4 = this.getLocation(var1, true);
      Object var3 = var4.get((Object)null);
      var4.set(var2);
      return var3;
   }

   public final Object put(String var1, Object var2) {
      return this.put((Object)var1, var2);
   }

   public final void put(Symbol var1, Object var2) {
      this.put(var1, (Object)null, var2);
   }

   public void put(Symbol var1, Object var2, Object var3) {
      Location var4 = this.getLocation(var1, var2);
      if(var4.isConstant()) {
         this.define(var1, var2, var3);
      } else {
         var4.set(var3);
      }
   }

   public final void putFunction(Symbol var1, Object var2) {
      this.put(var1, EnvironmentKey.FUNCTION, var2);
   }

   public final Object remove(EnvironmentKey var1) {
      Symbol var2 = var1.getKeySymbol();
      Object var3 = var1.getKeyProperty();
      return this.remove(var2, var3, var2.hashCode() ^ System.identityHashCode(var3));
   }

   public final Object remove(Symbol var1, Object var2) {
      return this.remove(var1, var2, var1.hashCode() ^ System.identityHashCode(var2));
   }

   public Object remove(Symbol var1, Object var2, int var3) {
      Location var4 = this.unlink(var1, var2, var3);
      if(var4 == null) {
         return null;
      } else {
         var2 = var4.get((Object)null);
         var4.undefine();
         return var2;
      }
   }

   public final Object remove(Object var1) {
      if(var1 instanceof EnvironmentKey) {
         EnvironmentKey var3 = (EnvironmentKey)var1;
         return this.remove(var3.getKeySymbol(), var3.getKeyProperty());
      } else {
         Symbol var2;
         if(var1 instanceof Symbol) {
            var2 = (Symbol)var1;
         } else {
            var2 = this.getSymbol((String)var1);
         }

         return this.remove(var2, (Object)null, var2.hashCode() ^ System.identityHashCode((Object)null));
      }
   }

   public final void remove(Symbol var1) {
      this.remove(var1, (Object)null, var1.hashCode());
   }

   public final void removeFunction(Symbol var1) {
      this.remove(var1, EnvironmentKey.FUNCTION);
   }

   public void setCanDefine(boolean var1) {
      if(var1) {
         this.flags |= 1;
      } else {
         this.flags &= -2;
      }
   }

   public void setCanRedefine(boolean var1) {
      if(var1) {
         this.flags |= 2;
      } else {
         this.flags &= -3;
      }
   }

   public void setFlag(boolean var1, int var2) {
      if(var1) {
         this.flags |= var2;
      } else {
         this.flags &= ~var2;
      }
   }

   public final void setIndirectDefines() {
      this.flags |= 32;
      ((InheritingEnvironment)this).baseTimestamp = Integer.MAX_VALUE;
   }

   public void setLocked() {
      this.flags &= -8;
   }

   public String toString() {
      return "#<environment " + this.getName() + '>';
   }

   public String toStringVerbose() {
      return this.toString();
   }

   public Location unlink(Symbol var1, Object var2, int var3) {
      throw new RuntimeException("unsupported operation: unlink (aka undefine)");
   }

   static class InheritedLocal extends InheritableThreadLocal {

      protected Environment childValue(Environment var1) {
         Environment var2 = var1;
         if(var1 == null) {
            var2 = Environment.getCurrent();
         }

         SimpleEnvironment var3 = var2.cloneForThread();
         var3.flags |= 8;
         var3.flags &= -17;
         return var3;
      }
   }
}
