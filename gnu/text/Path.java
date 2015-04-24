package gnu.text;

import gnu.lists.FString;
import gnu.mapping.WrappedException;
import gnu.mapping.WrongType;
import gnu.text.FilePath;
import gnu.text.URIPath;
import gnu.text.URLPath;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public abstract class Path {

   public static Path defaultPath = userDirPath;
   private static ThreadLocal pathLocation = new ThreadLocal();
   public static final FilePath userDirPath = FilePath.valueOf((File)(new File(".")));


   public static Path coerceToPathOrNull(Object var0) {
      if(var0 instanceof Path) {
         return (Path)var0;
      } else if(var0 instanceof URL) {
         return URLPath.valueOf((URL)var0);
      } else if(var0 instanceof URI) {
         return URIPath.valueOf((URI)((URI)var0));
      } else if(var0 instanceof File) {
         return FilePath.valueOf((File)((File)var0));
      } else {
         String var1;
         if(var0 instanceof FString) {
            var1 = var0.toString();
         } else {
            if(!(var0 instanceof String)) {
               return null;
            }

            var1 = (String)var0;
         }

         return (Path)(uriSchemeSpecified(var1)?URIPath.valueOf((String)var1):FilePath.valueOf((String)var1));
      }
   }

   public static Path currentPath() {
      Path var0 = (Path)pathLocation.get();
      return var0 != null?var0:defaultPath;
   }

   public static InputStream openInputStream(Object var0) throws IOException {
      return valueOf(var0).openInputStream();
   }

   public static String relativize(String var0, String var1) throws URISyntaxException, IOException {
      String var3 = (new URI(var1)).normalize().toString();
      String var2 = URLPath.valueOf(var0).toURI().normalize().toString();
      int var7 = var3.length();
      int var8 = var2.length();
      int var4 = 0;
      int var6 = 0;

      int var5;
      for(var5 = 0; var4 < var7 && var4 < var8; ++var4) {
         char var9 = var3.charAt(var4);
         if(var9 != var2.charAt(var4)) {
            break;
         }

         if(var9 == 47) {
            var6 = var4;
         }

         if(var9 == 58) {
            var5 = var4;
         }
      }

      var1 = var0;
      if(var5 > 0) {
         if(var6 <= var5 + 2 && var7 > var5 + 2) {
            var1 = var0;
            if(var3.charAt(var5 + 2) == 47) {
               return var1;
            }
         }

         var0 = var3.substring(var6 + 1);
         var1 = var2.substring(var6 + 1);
         StringBuilder var10 = new StringBuilder();
         var4 = var0.length();

         while(true) {
            var5 = var4 - 1;
            if(var5 < 0) {
               var10.append(var1);
               var1 = var10.toString();
               break;
            }

            var4 = var5;
            if(var0.charAt(var5) == 47) {
               var10.append("../");
               var4 = var5;
            }
         }
      }

      return var1;
   }

   public static void setCurrentPath(Path var0) {
      pathLocation.set(var0);
   }

   public static URL toURL(String var0) {
      String var1 = var0;

      try {
         if(!uriSchemeSpecified(var0)) {
            Path var3 = currentPath().resolve((String)var0);
            if(var3.isAbsolute()) {
               return var3.toURL();
            }

            var1 = var3.toString();
         }

         URL var4 = new URL(var1);
         return var4;
      } catch (Throwable var2) {
         throw WrappedException.wrapIfNeeded(var2);
      }
   }

   public static int uriSchemeLength(String var0) {
      int var3 = var0.length();
      int var2 = 0;

      while(true) {
         if(var2 >= var3) {
            return -1;
         }

         char var1 = var0.charAt(var2);
         if(var1 == 58) {
            return var2;
         }

         if(var2 == 0) {
            if(!Character.isLetter(var1)) {
               break;
            }
         } else if(!Character.isLetterOrDigit(var1) && var1 != 43 && var1 != 45 && var1 != 46) {
            break;
         }

         ++var2;
      }

      return -1;
   }

   public static boolean uriSchemeSpecified(String var0) {
      boolean var2 = true;
      boolean var3 = false;
      int var1 = uriSchemeLength(var0);
      if(var1 == 1 && File.separatorChar == 92) {
         char var4 = var0.charAt(0);
         if(var4 >= 97) {
            var2 = var3;
            if(var4 <= 122) {
               return var2;
            }
         }

         if(var4 >= 65) {
            var2 = var3;
            if(var4 <= 90) {
               return var2;
            }
         }

         var2 = true;
         return var2;
      } else {
         if(var1 <= 0) {
            var2 = false;
         }

         return var2;
      }
   }

   public static Path valueOf(Object var0) {
      Path var1 = coerceToPathOrNull(var0);
      if(var1 == null) {
         throw new WrongType((String)null, -4, var0, "path");
      } else {
         return var1;
      }
   }

   public boolean delete() {
      return false;
   }

   public boolean exists() {
      return this.getLastModified() != 0L;
   }

   public Path getAbsolute() {
      return this == userDirPath?this.resolve((String)""):currentPath().resolve((Path)this);
   }

   public String getAuthority() {
      return null;
   }

   public Path getCanonical() {
      return this.getAbsolute();
   }

   public CharSequence getCharContent(boolean var1) throws IOException {
      throw new UnsupportedOperationException();
   }

   public long getContentLength() {
      return -1L;
   }

   public Path getDirectory() {
      return this.isDirectory()?this:this.resolve((String)"");
   }

   public String getExtension() {
      String var1 = this.getPath();
      if(var1 != null) {
         int var3 = var1.length();

         while(true) {
            int var4 = var3 - 1;
            if(var4 <= 0) {
               break;
            }

            char var5 = var1.charAt(var4);
            boolean var2 = false;
            char var6 = var5;
            if(var5 == 46) {
               var6 = var1.charAt(var4 - 1);
               var2 = true;
            }

            if(var6 == 47 || this instanceof FilePath && var6 == File.separatorChar) {
               break;
            }

            var3 = var4;
            if(var2) {
               return var1.substring(var4 + 1);
            }
         }
      }

      return null;
   }

   public String getFragment() {
      return null;
   }

   public String getHost() {
      return null;
   }

   public String getLast() {
      String var1 = this.getPath();
      if(var1 == null) {
         return null;
      } else {
         int var2 = var1.length();
         int var5 = var2;
         int var3 = var2;

         while(true) {
            int var4 = var3 - 1;
            if(var4 <= 0) {
               return "";
            }

            char var6 = var1.charAt(var4);
            if(var6 != 47) {
               var3 = var4;
               if(!(this instanceof FilePath)) {
                  continue;
               }

               var3 = var4;
               if(var6 != File.separatorChar) {
                  continue;
               }
            }

            if(var4 + 1 != var2) {
               return var1.substring(var4 + 1, var5);
            }

            var5 = var4;
            var3 = var4;
         }
      }
   }

   public abstract long getLastModified();

   public String getName() {
      return this.toString();
   }

   public Path getParent() {
      String var1;
      if(this.isDirectory()) {
         var1 = "..";
      } else {
         var1 = "";
      }

      return this.resolve((String)var1);
   }

   public abstract String getPath();

   public int getPort() {
      return -1;
   }

   public String getQuery() {
      return null;
   }

   public abstract String getScheme();

   public String getUserInfo() {
      return null;
   }

   public abstract boolean isAbsolute();

   public boolean isDirectory() {
      String var1 = this.toString();
      int var2 = var1.length();
      if(var2 > 0) {
         char var3 = var1.charAt(var2 - 1);
         if(var3 == 47 || var3 == File.separatorChar) {
            return true;
         }
      }

      return false;
   }

   public abstract InputStream openInputStream() throws IOException;

   public abstract OutputStream openOutputStream() throws IOException;

   public Reader openReader(boolean var1) throws IOException {
      throw new UnsupportedOperationException();
   }

   public Writer openWriter() throws IOException {
      return new OutputStreamWriter(this.openOutputStream());
   }

   public Path resolve(Path var1) {
      return var1.isAbsolute()?var1:this.resolve((String)var1.toString());
   }

   public abstract Path resolve(String var1);

   public final URI toURI() {
      return this.toUri();
   }

   public String toURIString() {
      return this.toUri().toString();
   }

   public abstract URL toURL();

   public abstract URI toUri();
}
