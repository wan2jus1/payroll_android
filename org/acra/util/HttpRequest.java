package org.acra.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import org.acra.ACRA;
import org.acra.util.FakeSocketFactory;
import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public final class HttpRequest {

   private int connectionTimeOut = 3000;
   private String login;
   private int maxNrRetries = 3;
   private String password;
   private int socketTimeOut = 3000;


   private UsernamePasswordCredentials getCredentials() {
      return this.login == null && this.password == null?null:new UsernamePasswordCredentials(this.login, this.password);
   }

   private HttpClient getHttpClient() {
      BasicHttpParams var1 = new BasicHttpParams();
      var1.setParameter("http.protocol.cookie-policy", "rfc2109");
      HttpConnectionParams.setConnectionTimeout(var1, this.connectionTimeOut);
      HttpConnectionParams.setSoTimeout(var1, this.socketTimeOut);
      HttpConnectionParams.setSocketBufferSize(var1, 8192);
      SchemeRegistry var2 = new SchemeRegistry();
      var2.register(new Scheme("http", new PlainSocketFactory(), 80));
      if(ACRA.getConfig().disableSSLCertValidation()) {
         var2.register(new Scheme("https", new FakeSocketFactory(), 443));
      } else {
         var2.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
      }

      DefaultHttpClient var3 = new DefaultHttpClient(new ThreadSafeClientConnManager(var1, var2), var1);
      var3.setHttpRequestRetryHandler(new HttpRequest.SocketTimeOutRetryHandler(var1, this.maxNrRetries, null));
      return var3;
   }

   private HttpPost getHttpPost(URL var1, Map var2) throws UnsupportedEncodingException {
      HttpPost var4 = new HttpPost(var1.toString());
      UsernamePasswordCredentials var3 = this.getCredentials();
      if(var3 != null) {
         var4.addHeader(BasicScheme.authenticate(var3, "UTF-8", false));
      }

      var4.setHeader("User-Agent", "Android");
      var4.setHeader("Accept", "text/html,application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5");
      var4.setHeader("Content-Type", "application/x-www-form-urlencoded");
      var4.setEntity(new StringEntity(this.getParamsAsString(var2), "UTF-8"));
      return var4;
   }

   private String getParamsAsString(Map var1) throws UnsupportedEncodingException {
      StringBuilder var3 = new StringBuilder();
      Iterator var4 = var1.keySet().iterator();

      while(var4.hasNext()) {
         Object var5 = var4.next();
         if(var3.length() != 0) {
            var3.append('&');
         }

         Object var2 = var1.get(var5);
         if(var2 == null) {
            var2 = "";
         }

         var3.append(URLEncoder.encode(var5.toString(), "UTF-8"));
         var3.append('=');
         var3.append(URLEncoder.encode(var2.toString(), "UTF-8"));
      }

      return var3.toString();
   }

   public void sendPost(URL var1, Map var2) throws IOException {
      HttpClient var3 = this.getHttpClient();
      HttpPost var4 = this.getHttpPost(var1, var2);
      ACRA.log.d(ACRA.LOG_TAG, "Sending request to " + var1);
      Iterator var5 = var2.keySet().iterator();

      while(var5.hasNext()) {
         var5.next();
      }

      HttpResponse var6 = var3.execute(var4, new BasicHttpContext());
      if(var6 != null) {
         if(var6.getStatusLine() != null) {
            String var7 = Integer.toString(var6.getStatusLine().getStatusCode());
            if(var7.startsWith("4") || var7.startsWith("5")) {
               throw new IOException("Host returned error code " + var7);
            }
         }

         EntityUtils.toString(var6.getEntity());
      }

   }

   public void setConnectionTimeOut(int var1) {
      this.connectionTimeOut = var1;
   }

   public void setLogin(String var1) {
      this.login = var1;
   }

   public void setMaxNrRetries(int var1) {
      this.maxNrRetries = var1;
   }

   public void setPassword(String var1) {
      this.password = var1;
   }

   public void setSocketTimeOut(int var1) {
      this.socketTimeOut = var1;
   }

   private static class SocketTimeOutRetryHandler implements HttpRequestRetryHandler {

      private final HttpParams httpParams;
      private final int maxNrRetries;


      private SocketTimeOutRetryHandler(HttpParams var1, int var2) {
         this.httpParams = var1;
         this.maxNrRetries = var2;
      }

      // $FF: synthetic method
      SocketTimeOutRetryHandler(HttpParams var1, int var2, Object var3) {
         this(var1, var2);
      }

      public boolean retryRequest(IOException var1, int var2, HttpContext var3) {
         if(var1 instanceof SocketTimeoutException) {
            if(var2 <= this.maxNrRetries) {
               if(this.httpParams != null) {
                  var2 = HttpConnectionParams.getSoTimeout(this.httpParams) * 2;
                  HttpConnectionParams.setSoTimeout(this.httpParams, var2);
                  ACRA.log.d(ACRA.LOG_TAG, "SocketTimeOut - increasing time out to " + var2 + " millis and trying again");
               } else {
                  ACRA.log.d(ACRA.LOG_TAG, "SocketTimeOut - no HttpParams, cannot increase time out. Trying again with current settings");
               }

               return true;
            }

            ACRA.log.d(ACRA.LOG_TAG, "SocketTimeOut but exceeded max number of retries : " + this.maxNrRetries);
         }

         return false;
      }
   }
}
