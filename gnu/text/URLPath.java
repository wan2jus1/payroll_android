package gnu.text;

import gnu.mapping.WrappedException;
import gnu.text.Path;
import gnu.text.ResourceStreamHandler;
import gnu.text.URIPath;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

public class URLPath extends URIPath {

   final URL url;


   URLPath(URL var1) {
      super(toUri(var1));
      this.url = var1;
   }

   public static URLPath classResourcePath(Class var0) {
      URL var4;
      URL var5;
      try {
         try {
            var5 = ResourceStreamHandler.makeURL(var0);
         } catch (SecurityException var2) {
            String var1 = var0.getName().replace('.', '/') + ".class";
            var4 = var0.getClassLoader().getResource(var1);
            return valueOf(var4);
         }
      } catch (Throwable var3) {
         throw WrappedException.wrapIfNeeded(var3);
      }

      var4 = var5;
      return valueOf(var4);
   }

   public static int getContentLength(URL var0) {
      try {
         int var1 = var0.openConnection().getContentLength();
         return var1;
      } catch (Throwable var2) {
         return -1;
      }
   }

   public static long getLastModified(URL var0) {
      try {
         long var1 = var0.openConnection().getLastModified();
         return var1;
      } catch (Throwable var3) {
         return 0L;
      }
   }

   public static InputStream openInputStream(URL var0) throws IOException {
      return var0.openConnection().getInputStream();
   }

   public static OutputStream openOutputStream(URL var0) throws IOException {
      String var1 = var0.toString();
      if(var1.startsWith("file:")) {
         try {
            FileOutputStream var4 = new FileOutputStream(new File(new URI(var1)));
            return var4;
         } catch (Throwable var2) {
            ;
         }
      }

      URLConnection var3 = var0.openConnection();
      var3.setDoInput(false);
      var3.setDoOutput(true);
      return var3.getOutputStream();
   }

   public static URI toUri(URL var0) {
      try {
         URI var2 = var0.toURI();
         return var2;
      } catch (Throwable var1) {
         throw WrappedException.wrapIfNeeded(var1);
      }
   }

   public static URLPath valueOf(URL var0) {
      return new URLPath(var0);
   }

   public long getContentLength() {
      return (long)getContentLength(this.url);
   }

   public long getLastModified() {
      return getLastModified(this.url);
   }

   public boolean isAbsolute() {
      return true;
   }

   public InputStream openInputStream() throws IOException {
      return openInputStream(this.url);
   }

   public OutputStream openOutputStream() throws IOException {
      return openOutputStream(this.url);
   }

   public Path resolve(String var1) {
      try {
         URLPath var3 = valueOf(new URL(this.url, var1));
         return var3;
      } catch (Throwable var2) {
         throw WrappedException.wrapIfNeeded(var2);
      }
   }

   public String toURIString() {
      return this.url.toString();
   }

   public URL toURL() {
      return this.url;
   }

   public URI toUri() {
      return toUri(this.url);
   }
}
