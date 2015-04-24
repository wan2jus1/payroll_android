package gnu.text;

import gnu.lists.FString;
import gnu.mapping.WrappedException;
import gnu.mapping.WrongType;
import gnu.text.Path;
import gnu.text.URIPath;
import gnu.text.URLPath;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;

public class FilePath extends Path implements Comparable {

   final File file;
   final String path;


   private FilePath(File var1) {
      this.file = var1;
      this.path = var1.toString();
   }

   private FilePath(File var1, String var2) {
      this.file = var1;
      this.path = var2;
   }

   public static FilePath coerceToFilePathOrNull(Object var0) {
      if(var0 instanceof FilePath) {
         return (FilePath)var0;
      } else if(var0 instanceof URIPath) {
         return valueOf((File)(new File(((URIPath)var0).uri)));
      } else if(var0 instanceof URI) {
         return valueOf((File)(new File((URI)var0)));
      } else if(var0 instanceof File) {
         return valueOf((File)((File)var0));
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

         return valueOf((String)var1);
      }
   }

   public static FilePath makeFilePath(Object var0) {
      FilePath var1 = coerceToFilePathOrNull(var0);
      if(var1 == null) {
         throw new WrongType((String)null, -4, var0, "filepath");
      } else {
         return var1;
      }
   }

   private static URI toUri(File param0) {
      // $FF: Couldn't be decompiled
   }

   public static FilePath valueOf(File var0) {
      return new FilePath(var0);
   }

   public static FilePath valueOf(String var0) {
      return new FilePath(new File(var0), var0);
   }

   public int compareTo(FilePath var1) {
      return this.file.compareTo(var1.file);
   }

   public boolean delete() {
      return this.toFile().delete();
   }

   public boolean equals(Object var1) {
      return var1 instanceof FilePath && this.file.equals(((FilePath)var1).file);
   }

   public boolean exists() {
      return this.file.exists();
   }

   public Path getCanonical() {
      // $FF: Couldn't be decompiled
   }

   public long getContentLength() {
      long var3 = this.file.length();
      long var1 = var3;
      if(var3 == 0L) {
         var1 = var3;
         if(!this.file.exists()) {
            var1 = -1L;
         }
      }

      return var1;
   }

   public String getLast() {
      return this.file.getName();
   }

   public long getLastModified() {
      return this.file.lastModified();
   }

   public FilePath getParent() {
      File var1 = this.file.getParentFile();
      return var1 == null?null:valueOf((File)var1);
   }

   public String getPath() {
      return this.file.getPath();
   }

   public String getScheme() {
      return this.isAbsolute()?"file":null;
   }

   public int hashCode() {
      return this.file.hashCode();
   }

   public boolean isAbsolute() {
      return this == Path.userDirPath || this.file.isAbsolute();
   }

   public boolean isDirectory() {
      if(!this.file.isDirectory()) {
         if(!this.file.exists()) {
            int var1 = this.path.length();
            if(var1 > 0) {
               char var2 = this.path.charAt(var1 - 1);
               if(var2 == 47 || var2 == File.separatorChar) {
                  return true;
               }
            }
         }

         return false;
      } else {
         return true;
      }
   }

   public InputStream openInputStream() throws IOException {
      return new FileInputStream(this.file);
   }

   public OutputStream openOutputStream() throws IOException {
      return new FileOutputStream(this.file);
   }

   public Path resolve(String var1) {
      if(Path.uriSchemeSpecified(var1)) {
         return URLPath.valueOf(var1);
      } else {
         File var3 = new File(var1);
         if(var3.isAbsolute()) {
            return valueOf((File)var3);
         } else {
            char var2 = File.separatorChar;
            String var5 = var1;
            if(var2 != 47) {
               var5 = var1.replace('/', var2);
            }

            File var4;
            if(this == Path.userDirPath) {
               var4 = new File(System.getProperty("user.dir"), var5);
            } else {
               if(this.isDirectory()) {
                  var4 = this.file;
               } else {
                  var4 = this.file.getParentFile();
               }

               var4 = new File(var4, var5);
            }

            return valueOf((File)var4);
         }
      }
   }

   public File toFile() {
      return this.file;
   }

   public String toString() {
      return this.path;
   }

   public URL toURL() {
      if(this == Path.userDirPath) {
         return this.resolve("").toURL();
      } else if(!this.isAbsolute()) {
         return this.getAbsolute().toURL();
      } else {
         try {
            URL var1 = this.file.toURI().toURL();
            return var1;
         } catch (Throwable var2) {
            throw WrappedException.wrapIfNeeded(var2);
         }
      }
   }

   public URI toUri() {
      return this == Path.userDirPath?this.resolve("").toURI():toUri(this.file);
   }
}
