package org.acra.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.SecureRandom;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import org.acra.util.NaiveTrustManager;
import org.apache.http.conn.scheme.LayeredSocketFactory;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class FakeSocketFactory implements SocketFactory, LayeredSocketFactory {

   private SSLContext sslcontext = null;


   private static SSLContext createEasySSLContext() throws IOException {
      try {
         SSLContext var0 = SSLContext.getInstance("TLS");
         var0.init((KeyManager[])null, new TrustManager[]{new NaiveTrustManager()}, (SecureRandom)null);
         return var0;
      } catch (Exception var1) {
         throw new IOException(var1.getMessage());
      }
   }

   private SSLContext getSSLContext() throws IOException {
      if(this.sslcontext == null) {
         this.sslcontext = createEasySSLContext();
      }

      return this.sslcontext;
   }

   public Socket connectSocket(Socket var1, String var2, int var3, InetAddress var4, int var5, HttpParams var6) throws IOException {
      int var7 = HttpConnectionParams.getConnectionTimeout(var6);
      int var8 = HttpConnectionParams.getSoTimeout(var6);
      InetSocketAddress var10 = new InetSocketAddress(var2, var3);
      if(var1 == null) {
         var1 = this.createSocket();
      }

      SSLSocket var9 = (SSLSocket)var1;
      if(var4 != null || var5 > 0) {
         var3 = var5;
         if(var5 < 0) {
            var3 = 0;
         }

         var9.bind(new InetSocketAddress(var4, var3));
      }

      var9.connect(var10, var7);
      var9.setSoTimeout(var8);
      return var9;
   }

   public Socket createSocket() throws IOException {
      return this.getSSLContext().getSocketFactory().createSocket();
   }

   public Socket createSocket(Socket var1, String var2, int var3, boolean var4) throws IOException {
      return this.getSSLContext().getSocketFactory().createSocket(var1, var2, var3, var4);
   }

   public boolean isSecure(Socket var1) throws IllegalArgumentException {
      return true;
   }
}
