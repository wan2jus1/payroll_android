package gnu.bytecode;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipLoader extends ClassLoader {

   private Vector loadedClasses;
   int size;
   ZipFile zar;
   private String zipname;


   public ZipLoader(String var1) throws IOException {
      this.zipname = var1;
      this.zar = new ZipFile(var1);
      this.size = 0;
      Enumeration var2 = this.zar.entries();

      while(var2.hasMoreElements()) {
         if(!((ZipEntry)var2.nextElement()).isDirectory()) {
            ++this.size;
         }
      }

      this.loadedClasses = new Vector(this.size);
   }

   public void close() throws IOException {
      if(this.zar != null) {
         this.zar.close();
      }

      this.zar = null;
   }

   public Class loadAllClasses() throws IOException {
      Enumeration var4 = this.zar.entries();

      Class var1;
      Class var8;
      for(var1 = null; var4.hasMoreElements(); var1 = var8) {
         ZipEntry var2 = (ZipEntry)var4.nextElement();
         String var3 = var2.getName().replace('/', '.');
         String var5 = var3.substring(0, var3.length() - "/class".length());
         int var6 = (int)var2.getSize();
         InputStream var7 = this.zar.getInputStream(var2);
         byte[] var9 = new byte[var6];
         (new DataInputStream(var7)).readFully(var9);
         Class var10 = this.defineClass(var5, var9, 0, var6);
         var8 = var1;
         if(var1 == null) {
            var8 = var10;
         }

         this.loadedClasses.addElement(var5);
         this.loadedClasses.addElement(var10);
      }

      this.close();
      return var1;
   }

   public Class loadClass(String param1, boolean param2) throws ClassNotFoundException {
      // $FF: Couldn't be decompiled
   }
}
