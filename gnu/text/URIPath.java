package gnu.text;

import gnu.lists.FString;
import gnu.mapping.WrappedException;
import gnu.mapping.WrongType;
import gnu.text.FilePath;
import gnu.text.Path;
import gnu.text.URIStringPath;
import gnu.text.URLPath;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

public class URIPath extends Path implements Comparable {

   final URI uri;


   URIPath(URI var1) {
      this.uri = var1;
   }

   public static URIPath coerceToURIPathOrNull(Object var0) {
      if(var0 instanceof URIPath) {
         return (URIPath)var0;
      } else if(var0 instanceof URL) {
         return URLPath.valueOf((URL)var0);
      } else if(var0 instanceof URI) {
         return valueOf((URI)((URI)var0));
      } else {
         String var1;
         if(!(var0 instanceof File) && !(var0 instanceof Path) && !(var0 instanceof FString)) {
            if(!(var0 instanceof String)) {
               return null;
            }

            var1 = (String)var0;
         } else {
            var1 = var0.toString();
         }

         return valueOf((String)var1);
      }
   }

   public static String encodeForUri(String var0, char var1) {
      StringBuffer var2 = new StringBuffer();
      int var9 = var0.length();
      int var3 = 0;

      while(var3 < var9) {
         int var4 = var3 + 1;
         char var6 = var0.charAt(var3);
         int var5 = var6;
         var3 = var4;
         if(var6 >= '\ud800') {
            var5 = var6;
            var3 = var4;
            if(var6 < '\udc00') {
               var5 = var6;
               var3 = var4;
               if(var4 < var9) {
                  var5 = (var6 - '\ud800') * 1024 + (var0.charAt(var4) - '\udc00') + 65536;
                  var3 = var4 + 1;
               }
            }
         }

         label143: {
            if(var1 == 72) {
               if(var5 < 32 || var5 > 126) {
                  break label143;
               }
            } else if((var5 < 97 || var5 > 122) && (var5 < 65 || var5 > 90) && (var5 < 48 || var5 > 57) && var5 != 45 && var5 != 95 && var5 != 46 && var5 != 126 && (var1 != 73 || var5 != 59 && var5 != 47 && var5 != 63 && var5 != 58 && var5 != 42 && var5 != 39 && var5 != 40 && var5 != 41 && var5 != 64 && var5 != 38 && var5 != 61 && var5 != 43 && var5 != 36 && var5 != 44 && var5 != 91 && var5 != 93 && var5 != 35 && var5 != 33 && var5 != 37)) {
               break label143;
            }

            var2.append((char)var5);
            continue;
         }

         int var10 = var2.length();
         int var11 = 0;
         if(var5 >= 128 && var5 >= 2048 && var5 < 65536) {
            ;
         }

         while(true) {
            if(var11 == 0) {
               var4 = 7;
            } else {
               var4 = 6 - var11;
            }

            int var7;
            if(var5 < 1 << var4) {
               var4 = var5;
               var5 = var5;
               if(var11 > 0) {
                  var5 = var4 | 'ﾀ' >> var11 & 255;
               }

               var4 = 0;
            } else {
               var4 = var5 & 63 | 128;
               var7 = var5 >> 6;
               var5 = var4;
               var4 = var7;
            }

            int var8 = var11 + 1;

            for(var11 = 0; var11 <= 1; ++var11) {
               var7 = var5 & 15;
               if(var7 <= 9) {
                  var7 += 48;
               } else {
                  var7 = var7 - 10 + 65;
               }

               var2.insert(var10, (char)var7);
               var5 >>= 4;
            }

            var2.insert(var10, '%');
            var5 = var4;
            var11 = var8;
            if(var4 == 0) {
               break;
            }
         }
      }

      return var2.toString();
   }

   public static URIPath makeURI(Object var0) {
      URIPath var1 = coerceToURIPathOrNull(var0);
      if(var1 == null) {
         throw new WrongType((String)null, -4, var0, "URI");
      } else {
         return var1;
      }
   }

   public static URIPath valueOf(String var0) {
      try {
         URIStringPath var2 = new URIStringPath(new URI(encodeForUri(var0, 'I')), var0);
         return var2;
      } catch (Throwable var1) {
         throw WrappedException.wrapIfNeeded(var1);
      }
   }

   public static URIPath valueOf(URI var0) {
      return new URIPath(var0);
   }

   public int compareTo(URIPath var1) {
      return this.uri.compareTo(var1.uri);
   }

   public boolean equals(Object var1) {
      return var1 instanceof URIPath && this.uri.equals(((URIPath)var1).uri);
   }

   public boolean exists() {
      boolean var4 = true;

      long var2;
      try {
         URLConnection var1 = this.toURL().openConnection();
         if(var1 instanceof HttpURLConnection) {
            if(((HttpURLConnection)var1).getResponseCode() == 200) {
               return true;
            }

            return false;
         }

         var2 = var1.getLastModified();
      } catch (Throwable var5) {
         var4 = false;
         return var4;
      }

      if(var2 == 0L) {
         return false;
      } else {
         return var4;
      }
   }

   public String getAuthority() {
      return this.uri.getAuthority();
   }

   public Path getCanonical() {
      if(this.isAbsolute()) {
         URI var1 = this.uri.normalize();
         return var1 == this.uri?this:valueOf((URI)var1);
      } else {
         return this.getAbsolute().getCanonical();
      }
   }

   public long getContentLength() {
      return (long)URLPath.getContentLength(this.toURL());
   }

   public String getFragment() {
      return this.uri.getFragment();
   }

   public String getHost() {
      return this.uri.getHost();
   }

   public long getLastModified() {
      return URLPath.getLastModified(this.toURL());
   }

   public String getPath() {
      return this.uri.getPath();
   }

   public int getPort() {
      return this.uri.getPort();
   }

   public String getQuery() {
      return this.uri.getQuery();
   }

   public String getScheme() {
      return this.uri.getScheme();
   }

   public String getUserInfo() {
      return this.uri.getUserInfo();
   }

   public int hashCode() {
      return this.uri.hashCode();
   }

   public boolean isAbsolute() {
      return this.uri.isAbsolute();
   }

   public InputStream openInputStream() throws IOException {
      return URLPath.openInputStream(this.toURL());
   }

   public OutputStream openOutputStream() throws IOException {
      return URLPath.openOutputStream(this.toURL());
   }

   public Path resolve(String var1) {
      if(Path.uriSchemeSpecified(var1)) {
         return valueOf((String)var1);
      } else {
         char var2 = File.separatorChar;
         String var3 = var1;
         if(var2 != 47) {
            if(var1.length() >= 2 && (var1.charAt(1) == 58 && Character.isLetter(var1.charAt(0)) || var1.charAt(0) == var2 && var1.charAt(1) == var2)) {
               return FilePath.valueOf((File)(new File(var1)));
            }

            var3 = var1.replace(var2, '/');
         }

         URI var5;
         try {
            var5 = this.uri.resolve(new URI((String)null, var3, (String)null));
         } catch (Throwable var4) {
            throw WrappedException.wrapIfNeeded(var4);
         }

         return valueOf((URI)var5);
      }
   }

   public String toString() {
      return this.toURIString();
   }

   public String toURIString() {
      return this.uri.toString();
   }

   public URL toURL() {
      return Path.toURL(this.uri.toString());
   }

   public URI toUri() {
      return this.uri;
   }
}
