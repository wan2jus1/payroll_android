package gnu.bytecode;

import gnu.bytecode.ClassType;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Hashtable;

public class ArrayClassLoader extends ClassLoader {

   Hashtable cmap = new Hashtable(100);
   URL context;
   Hashtable map = new Hashtable(100);


   public ArrayClassLoader() {
   }

   public ArrayClassLoader(ClassLoader var1) {
      super(var1);
   }

   public ArrayClassLoader(String[] var1, byte[][] var2) {
      int var3 = var2.length;

      while(true) {
         --var3;
         if(var3 < 0) {
            return;
         }

         this.addClass(var1[var3], var2[var3]);
      }
   }

   public ArrayClassLoader(byte[][] var1) {
      int var2 = var1.length;

      while(true) {
         --var2;
         if(var2 < 0) {
            return;
         }

         this.addClass("lambda" + var2, var1[var2]);
      }
   }

   public static Package getContextPackage(String var0) {
      try {
         ClassLoader var1 = Thread.currentThread().getContextClassLoader();
         if(var1 instanceof ArrayClassLoader) {
            Package var3 = ((ArrayClassLoader)var1).getPackage(var0);
            return var3;
         }
      } catch (SecurityException var2) {
         ;
      }

      return Package.getPackage(var0);
   }

   public void addClass(ClassType var1) {
      this.map.put(var1.getName(), var1);
   }

   public void addClass(Class var1) {
      this.cmap.put(var1.getName(), var1);
   }

   public void addClass(String var1, byte[] var2) {
      this.map.put(var1, var2);
   }

   protected URL findResource(String var1) {
      if(this.context != null) {
         try {
            URL var2 = new URL(this.context, var1);
            var2.openConnection().connect();
            return var2;
         } catch (Throwable var3) {
            ;
         }
      }

      return super.findResource(var1);
   }

   public InputStream getResourceAsStream(String var1) {
      InputStream var3 = super.getResourceAsStream(var1);
      Object var2 = var3;
      if(var3 == null) {
         var2 = var3;
         if(var1.endsWith(".class")) {
            var1 = var1.substring(0, var1.length() - 6).replace('/', '.');
            Object var4 = this.map.get(var1);
            var2 = var3;
            if(var4 instanceof byte[]) {
               var2 = new ByteArrayInputStream((byte[])((byte[])var4));
            }
         }
      }

      return (InputStream)var2;
   }

   public URL getResourceContext() {
      return this.context;
   }

   public Class loadClass(String param1) throws ClassNotFoundException {
      // $FF: Couldn't be decompiled
   }

   public Class loadClass(String var1, boolean var2) throws ClassNotFoundException {
      Class var3 = this.loadClass(var1);
      if(var2) {
         this.resolveClass(var3);
      }

      return var3;
   }

   public void setResourceContext(URL var1) {
      this.context = var1;
   }
}
