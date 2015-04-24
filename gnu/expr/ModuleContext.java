package gnu.expr;

import gnu.expr.ModuleInfo;
import gnu.expr.ModuleManager;
import gnu.kawa.util.AbstractWeakHashTable;
import gnu.mapping.WrappedException;

public class ModuleContext {

   public static int IN_HTTP_SERVER = 1;
   public static int IN_SERVLET = 2;
   static ModuleContext global = new ModuleContext(ModuleManager.instance);
   int flags;
   ModuleManager manager;
   private ModuleContext.ClassToInstanceMap table = new ModuleContext.ClassToInstanceMap();


   public ModuleContext(ModuleManager var1) {
      this.manager = var1;
   }

   public static ModuleContext getContext() {
      return global;
   }

   public void addFlags(int var1) {
      this.flags |= var1;
   }

   public void clear() {
      synchronized(this){}

      try {
         this.table.clear();
      } finally {
         ;
      }

   }

   public ModuleInfo findFromInstance(Object param1) {
      // $FF: Couldn't be decompiled
   }

   public Object findInstance(ModuleInfo var1) {
      synchronized(this){}

      Object var8;
      try {
         Class var2;
         try {
            var2 = var1.getModuleClass();
         } catch (ClassNotFoundException var5) {
            String var7 = var1.getClassName();
            throw new WrappedException("cannot find module " + var7, var5);
         }

         var8 = this.findInstance((Class)var2);
      } finally {
         ;
      }

      return var8;
   }

   public Object findInstance(Class param1) {
      // $FF: Couldn't be decompiled
   }

   public int getFlags() {
      return this.flags;
   }

   public ModuleManager getManager() {
      return this.manager;
   }

   public Object searchInstance(Class var1) {
      synchronized(this){}

      Object var4;
      try {
         var4 = this.table.get(var1);
      } finally {
         ;
      }

      return var4;
   }

   public void setFlags(int var1) {
      this.flags = var1;
   }

   public void setInstance(Object var1) {
      synchronized(this){}

      try {
         this.table.put(var1.getClass(), var1);
      } finally {
         ;
      }

   }

   static class ClassToInstanceMap extends AbstractWeakHashTable {

      protected Class getKeyFromValue(Object var1) {
         return var1.getClass();
      }

      protected boolean matches(Class var1, Class var2) {
         return var1 == var2;
      }
   }
}
