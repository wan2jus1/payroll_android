package gnu.text;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public class ResourceStreamHandler extends URLStreamHandler {

   public static final String CLASS_RESOURCE_URI_PREFIX = "class-resource:/";
   public static final int CLASS_RESOURCE_URI_PREFIX_LENGTH = 16;
   ClassLoader cloader;


   public ResourceStreamHandler(ClassLoader var1) {
      this.cloader = var1;
   }

   public static URL makeURL(Class var0) throws MalformedURLException {
      String var2 = var0.getName();
      int var4 = var2.lastIndexOf(46);
      StringBuilder var3 = new StringBuilder();
      var3.append("class-resource:/");
      String var1 = var2;
      if(var4 >= 0) {
         var3.append(var2.substring(0, var4));
         var3.append('/');
         var1 = var2.substring(var4 + 1);
      }

      var3.append(var1);
      return new URL((URL)null, var3.toString(), new ResourceStreamHandler(var0.getClassLoader()));
   }

   public URLConnection openConnection(URL var1) throws IOException {
      String var3 = var1.toString();
      String var2 = var3.substring(16);
      int var4 = var2.indexOf(47);
      String var5 = var2;
      if(var4 > 0) {
         var5 = var2.substring(0, var4).replace('.', '/') + var2.substring(var4);
      }

      var1 = this.cloader.getResource(var5);
      if(var1 == null) {
         throw new FileNotFoundException(var3);
      } else {
         return var1.openConnection();
      }
   }
}
