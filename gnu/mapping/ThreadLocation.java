package gnu.mapping;

import gnu.mapping.Environment;
import gnu.mapping.IndirectableLocation;
import gnu.mapping.Location;
import gnu.mapping.Named;
import gnu.mapping.NamedLocation;
import gnu.mapping.SharedLocation;
import gnu.mapping.SimpleEnvironment;
import gnu.mapping.Symbol;

public class ThreadLocation extends NamedLocation implements Named {

   public static final String ANONYMOUS = new String("(dynamic)");
   static int counter;
   static SimpleEnvironment env;
   SharedLocation global;
   private int hash;
   private ThreadLocal thLocal;


   public ThreadLocation() {
      this((String)("param#" + nextCounter()));
   }

   private ThreadLocation(Symbol var1) {
      super(var1, ANONYMOUS);
      this.thLocal = new ThreadLocation.InheritingLocation();
      String var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = var1.toString();
      }

      this.global = new SharedLocation(Symbol.makeUninterned(var2), (Object)null, 0);
   }

   public ThreadLocation(Symbol var1, Object var2, SharedLocation var3) {
      super(var1, var2);
      this.hash = var1.hashCode() ^ System.identityHashCode(var2);
      this.global = var3;
   }

   public ThreadLocation(String var1) {
      super(Symbol.makeUninterned(var1), ANONYMOUS);
      this.thLocal = new ThreadLocation.InheritingLocation();
      this.global = new SharedLocation(this.name, (Object)null, 0);
   }

   public static ThreadLocation getInstance(Symbol var0, Object var1) {
      synchronized(ThreadLocation.class){}

      ThreadLocation var5;
      try {
         if(env == null) {
            env = new SimpleEnvironment("[thread-locations]");
         }

         IndirectableLocation var2 = (IndirectableLocation)env.getLocation(var0, var1);
         if(var2.base != null) {
            var5 = (ThreadLocation)var2.base;
         } else {
            var5 = new ThreadLocation(var0, var1, (SharedLocation)null);
            var2.base = var5;
         }
      } finally {
         ;
      }

      return var5;
   }

   public static ThreadLocation makeAnonymous(Symbol var0) {
      return new ThreadLocation(var0);
   }

   public static ThreadLocation makeAnonymous(String var0) {
      return new ThreadLocation(var0);
   }

   private static int nextCounter() {
      synchronized(ThreadLocation.class){}

      int var1;
      try {
         var1 = counter + 1;
         counter = var1;
      } finally {
         ;
      }

      return var1;
   }

   public Object get(Object var1) {
      return this.getLocation().get(var1);
   }

   public NamedLocation getLocation() {
      NamedLocation var1;
      if(this.property != ANONYMOUS) {
         var1 = Environment.getCurrent().getLocation(this.name, this.property, this.hash, true);
      } else {
         NamedLocation var2 = (NamedLocation)this.thLocal.get();
         var1 = var2;
         if(var2 == null) {
            SharedLocation var3 = new SharedLocation(this.name, this.property, 0);
            if(this.global != null) {
               var3.setBase(this.global);
            }

            this.thLocal.set(var3);
            return var3;
         }
      }

      return var1;
   }

   public String getName() {
      return this.name == null?null:this.name.toString();
   }

   public Object getSymbol() {
      return this.name != null && this.property == ANONYMOUS && this.global.getKeySymbol() == this.name?this.name.toString():this.name;
   }

   public void set(Object var1) {
      this.getLocation().set(var1);
   }

   public void setGlobal(Object param1) {
      // $FF: Couldn't be decompiled
   }

   public void setName(String var1) {
      throw new RuntimeException("setName not allowed");
   }

   public void setRestore(Object var1) {
      this.getLocation().setRestore(var1);
   }

   public Object setWithSave(Object var1) {
      return this.getLocation().setWithSave(var1);
   }

   public class InheritingLocation extends InheritableThreadLocal {

      protected SharedLocation childValue(NamedLocation var1) {
         if(ThreadLocation.this.property != ThreadLocation.ANONYMOUS) {
            throw new Error();
         } else {
            Object var2 = var1;
            if(var1 == null) {
               var2 = (SharedLocation)ThreadLocation.this.getLocation();
            }

            Object var3 = var2;
            if(((NamedLocation)var2).base == null) {
               var3 = new SharedLocation(ThreadLocation.this.name, ThreadLocation.this.property, 0);
               ((SharedLocation)var3).value = ((NamedLocation)var2).value;
               ((NamedLocation)var2).base = (Location)var3;
               ((NamedLocation)var2).value = null;
            }

            SharedLocation var4 = new SharedLocation(ThreadLocation.this.name, ThreadLocation.this.property, 0);
            var4.value = null;
            var4.base = (Location)var3;
            return var4;
         }
      }
   }
}
