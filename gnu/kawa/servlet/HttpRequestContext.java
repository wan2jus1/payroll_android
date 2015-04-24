package gnu.kawa.servlet;

import gnu.kawa.servlet.ServletPrinter;
import gnu.mapping.InPort;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map;

public abstract class HttpRequestContext {

   public static final int HTTP_NOT_FOUND = 404;
   public static final int HTTP_OK = 200;
   static final int STATUS_SENT = -999;
   public static int importServletDefinitions;
   protected static final ThreadLocal instance = new ThreadLocal();
   ServletPrinter consumer;
   String localPath = "";
   String scriptPath = "";
   public int statusCode = 200;
   public String statusReasonPhrase = null;


   public static HttpRequestContext getInstance() {
      HttpRequestContext var0 = (HttpRequestContext)instance.get();
      if(var0 == null) {
         throw new UnsupportedOperationException("can only be called by http-server");
      } else {
         return var0;
      }
   }

   public static HttpRequestContext getInstance(String var0) {
      HttpRequestContext var1 = (HttpRequestContext)instance.get();
      if(var1 == null) {
         throw new UnsupportedOperationException(var0 + " can only be called within http-server");
      } else {
         return var1;
      }
   }

   public static void setInstance(HttpRequestContext var0) {
      instance.set(var0);
   }

   public abstract Object getAttribute(String var1);

   public ServletPrinter getConsumer() throws IOException {
      if(this.consumer == null) {
         this.consumer = new ServletPrinter(this, 8192);
      }

      return this.consumer;
   }

   public abstract String getContextPath();

   public InetAddress getLocalHost() {
      try {
         InetAddress var1 = InetAddress.getLocalHost();
         return var1;
      } catch (Throwable var2) {
         throw new RuntimeException(var2);
      }
   }

   public String getLocalIPAddress() {
      return this.getLocalHost().getHostAddress();
   }

   public String getLocalPath() {
      return this.localPath;
   }

   public abstract int getLocalPort();

   public InetSocketAddress getLocalSocketAddress() {
      return new InetSocketAddress(this.getLocalHost(), this.getLocalPort());
   }

   public abstract String getPathTranslated();

   public abstract String getQueryString();

   public abstract InetAddress getRemoteHost();

   public abstract String getRemoteIPAddress();

   public abstract int getRemotePort();

   public InetSocketAddress getRemoteSocketAddress() {
      return new InetSocketAddress(this.getRemoteHost(), this.getRemotePort());
   }

   public String getRequestBodyChars() throws IOException {
      InputStreamReader var3 = new InputStreamReader(this.getRequestStream());
      int var6 = 1024;
      char[] var1 = new char[1024];
      int var4 = 0;

      while(true) {
         int var7 = var6 - var4;
         char[] var2 = var1;
         int var5 = var6;
         if(var7 <= 0) {
            var2 = new char[var6 * 2];
            System.arraycopy(var1, 0, var2, 0, var6);
            var5 = var6 + var6;
         }

         var6 = var3.read(var2, var4, var7);
         if(var6 < 0) {
            var3.close();
            return new String(var2, 0, var4);
         }

         var4 += var6;
         var1 = var2;
         var6 = var5;
      }
   }

   public abstract String getRequestHeader(String var1);

   public abstract List getRequestHeaders(String var1);

   public abstract Map getRequestHeaders();

   public abstract String getRequestMethod();

   public String getRequestParameter(String var1) {
      List var2 = (List)this.getRequestParameters().get(var1);
      return var2 != null && !var2.isEmpty()?(String)var2.get(0):null;
   }

   public abstract Map getRequestParameters();

   public String getRequestPath() {
      return this.getRequestURI().getPath();
   }

   public InPort getRequestPort() {
      return new InPort(this.getRequestStream());
   }

   public String getRequestScheme() {
      return "http";
   }

   public abstract InputStream getRequestStream();

   public abstract URI getRequestURI();

   public StringBuffer getRequestURLBuffer() {
      StringBuffer var1 = new StringBuffer();
      var1.append(this.getRequestScheme());
      var1.append("://");
      String var2 = this.getRequestHeader("Host");
      if(var2 != null) {
         var1.append(var2);
      } else {
         var1.append(this.getLocalIPAddress());
         var1.append(':');
         var1.append(this.getLocalPort());
      }

      var1.append(this.getRequestPath());
      return var1;
   }

   public abstract URL getResourceURL(String var1);

   public abstract OutputStream getResponseStream();

   public String getScriptPath() {
      return this.scriptPath;
   }

   public abstract void log(String var1);

   public abstract void log(String var1, Throwable var2);

   protected String normalizeToContext(String var1) {
      if(var1.length() > 0 && var1.charAt(0) == 47) {
         var1 = var1.substring(1);
      } else {
         var1 = this.getScriptPath() + var1;
      }

      String var2 = var1;
      if(var1.indexOf("..") >= 0) {
         var1 = URI.create(var1).normalize().toString();
         var2 = var1;
         if(var1.startsWith("../")) {
            return null;
         }
      }

      return var2;
   }

   public abstract boolean reset(boolean var1);

   public void sendNotFound(String var1) throws IOException {
      byte[] var4 = ("The requested URL " + var1 + " was not found on this server.\r\n").getBytes();
      this.sendResponseHeaders(404, (String)null, (long)var4.length);
      OutputStream var2 = this.getResponseStream();

      try {
         var2.write(var4);
      } catch (IOException var3) {
         throw new RuntimeException(var3);
      }
   }

   public abstract void sendResponseHeaders(int var1, String var2, long var3) throws IOException;

   public abstract void setAttribute(String var1, Object var2);

   public void setContentType(String var1) {
      this.setResponseHeader("Content-Type", var1);
   }

   public abstract void setResponseHeader(String var1, String var2);

   public void setScriptAndLocalPath(String var1, String var2) {
      this.scriptPath = var1;
      this.localPath = var2;
   }
}
