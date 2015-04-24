package gnu.kawa.servlet;

import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.Format;
import gnu.kawa.servlet.HttpRequestContext;
import gnu.kawa.xml.MakeResponseHeader;
import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.InPort;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.text.URIPath;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;

public class HTTP extends ModuleBody {

   public static final HTTP $instance = new HTTP();
   static final SimpleSymbol Lit0 = (SimpleSymbol)(new SimpleSymbol("Content-Type")).readResolve();
   static final SimpleSymbol Lit1 = (SimpleSymbol)(new SimpleSymbol("Status")).readResolve();
   static final SimpleSymbol Lit10 = (SimpleSymbol)(new SimpleSymbol("request-local-port")).readResolve();
   static final SimpleSymbol Lit11 = (SimpleSymbol)(new SimpleSymbol("request-local-host")).readResolve();
   static final SimpleSymbol Lit12 = (SimpleSymbol)(new SimpleSymbol("request-remote-socket-address")).readResolve();
   static final SimpleSymbol Lit13 = (SimpleSymbol)(new SimpleSymbol("request-remote-IP-address")).readResolve();
   static final SimpleSymbol Lit14 = (SimpleSymbol)(new SimpleSymbol("request-remote-port")).readResolve();
   static final SimpleSymbol Lit15 = (SimpleSymbol)(new SimpleSymbol("request-remote-host")).readResolve();
   static final SimpleSymbol Lit16 = (SimpleSymbol)(new SimpleSymbol("request-header")).readResolve();
   static final SimpleSymbol Lit17 = (SimpleSymbol)(new SimpleSymbol("request-header-map")).readResolve();
   static final SimpleSymbol Lit18 = (SimpleSymbol)(new SimpleSymbol("request-URI")).readResolve();
   static final SimpleSymbol Lit19 = (SimpleSymbol)(new SimpleSymbol("request-context-path")).readResolve();
   static final SimpleSymbol Lit2 = (SimpleSymbol)(new SimpleSymbol("response-header")).readResolve();
   static final SimpleSymbol Lit20 = (SimpleSymbol)(new SimpleSymbol("request-script-path")).readResolve();
   static final SimpleSymbol Lit21 = (SimpleSymbol)(new SimpleSymbol("request-local-path")).readResolve();
   static final SimpleSymbol Lit22 = (SimpleSymbol)(new SimpleSymbol("request-path")).readResolve();
   static final SimpleSymbol Lit23 = (SimpleSymbol)(new SimpleSymbol("request-uri")).readResolve();
   static final SimpleSymbol Lit24 = (SimpleSymbol)(new SimpleSymbol("request-url")).readResolve();
   static final SimpleSymbol Lit25 = (SimpleSymbol)(new SimpleSymbol("request-path-translated")).readResolve();
   static final SimpleSymbol Lit26 = (SimpleSymbol)(new SimpleSymbol("request-query-string")).readResolve();
   static final SimpleSymbol Lit27 = (SimpleSymbol)(new SimpleSymbol("request-parameter")).readResolve();
   static final SimpleSymbol Lit28 = (SimpleSymbol)(new SimpleSymbol("request-parameters")).readResolve();
   static final SimpleSymbol Lit29 = (SimpleSymbol)(new SimpleSymbol("request-parameter-map")).readResolve();
   static final SimpleSymbol Lit3 = (SimpleSymbol)(new SimpleSymbol("response-content-type")).readResolve();
   static final SimpleSymbol Lit30 = (SimpleSymbol)(new SimpleSymbol("request-body-string")).readResolve();
   static final SimpleSymbol Lit31 = (SimpleSymbol)(new SimpleSymbol("request-input-stream")).readResolve();
   static final SimpleSymbol Lit32 = (SimpleSymbol)(new SimpleSymbol("request-input-port")).readResolve();
   static final SimpleSymbol Lit4 = (SimpleSymbol)(new SimpleSymbol("response-status")).readResolve();
   static final SimpleSymbol Lit5 = (SimpleSymbol)(new SimpleSymbol("error-response")).readResolve();
   static final SimpleSymbol Lit6 = (SimpleSymbol)(new SimpleSymbol("request-method")).readResolve();
   static final SimpleSymbol Lit7 = (SimpleSymbol)(new SimpleSymbol("request-scheme")).readResolve();
   static final SimpleSymbol Lit8 = (SimpleSymbol)(new SimpleSymbol("request-local-socket-address")).readResolve();
   static final SimpleSymbol Lit9 = (SimpleSymbol)(new SimpleSymbol("request-local-IP-address")).readResolve();
   public static final ModuleMethod error$Mnresponse;
   public static final ModuleMethod request$MnURI;
   public static final ModuleMethod request$Mnbody$Mnstring;
   public static final ModuleMethod request$Mncontext$Mnpath;
   public static final ModuleMethod request$Mnheader;
   public static final ModuleMethod request$Mnheader$Mnmap;
   public static final ModuleMethod request$Mninput$Mnport;
   public static final ModuleMethod request$Mninput$Mnstream;
   public static final ModuleMethod request$Mnlocal$MnIP$Mnaddress;
   public static final ModuleMethod request$Mnlocal$Mnhost;
   public static final ModuleMethod request$Mnlocal$Mnpath;
   public static final ModuleMethod request$Mnlocal$Mnport;
   public static final ModuleMethod request$Mnlocal$Mnsocket$Mnaddress;
   public static final ModuleMethod request$Mnmethod;
   public static final ModuleMethod request$Mnparameter;
   public static final ModuleMethod request$Mnparameter$Mnmap;
   public static final ModuleMethod request$Mnparameters;
   public static final ModuleMethod request$Mnpath;
   public static final ModuleMethod request$Mnpath$Mntranslated;
   public static final ModuleMethod request$Mnquery$Mnstring;
   public static final ModuleMethod request$Mnremote$MnIP$Mnaddress;
   public static final ModuleMethod request$Mnremote$Mnhost;
   public static final ModuleMethod request$Mnremote$Mnport;
   public static final ModuleMethod request$Mnremote$Mnsocket$Mnaddress;
   public static final ModuleMethod request$Mnscheme;
   public static final ModuleMethod request$Mnscript$Mnpath;
   public static final ModuleMethod request$Mnuri;
   public static final ModuleMethod request$Mnurl;
   public static final ModuleMethod response$Mncontent$Mntype;
   public static final ModuleMethod response$Mnheader;
   public static final ModuleMethod response$Mnstatus;


   static {
      HTTP var0 = $instance;
      response$Mnheader = new ModuleMethod(var0, 1, Lit2, 8194);
      response$Mncontent$Mntype = new ModuleMethod(var0, 2, Lit3, 4097);
      response$Mnstatus = new ModuleMethod(var0, 3, Lit4, 8193);
      error$Mnresponse = new ModuleMethod(var0, 5, Lit5, 8193);
      request$Mnmethod = new ModuleMethod(var0, 7, Lit6, 0);
      request$Mnscheme = new ModuleMethod(var0, 8, Lit7, 0);
      request$Mnlocal$Mnsocket$Mnaddress = new ModuleMethod(var0, 9, Lit8, 0);
      request$Mnlocal$MnIP$Mnaddress = new ModuleMethod(var0, 10, Lit9, 0);
      request$Mnlocal$Mnport = new ModuleMethod(var0, 11, Lit10, 0);
      request$Mnlocal$Mnhost = new ModuleMethod(var0, 12, Lit11, 0);
      request$Mnremote$Mnsocket$Mnaddress = new ModuleMethod(var0, 13, Lit12, 0);
      request$Mnremote$MnIP$Mnaddress = new ModuleMethod(var0, 14, Lit13, 0);
      request$Mnremote$Mnport = new ModuleMethod(var0, 15, Lit14, 0);
      request$Mnremote$Mnhost = new ModuleMethod(var0, 16, Lit15, 0);
      request$Mnheader = new ModuleMethod(var0, 17, Lit16, 4097);
      request$Mnheader$Mnmap = new ModuleMethod(var0, 18, Lit17, 0);
      request$MnURI = new ModuleMethod(var0, 19, Lit18, 0);
      request$Mncontext$Mnpath = new ModuleMethod(var0, 20, Lit19, 0);
      request$Mnscript$Mnpath = new ModuleMethod(var0, 21, Lit20, 0);
      request$Mnlocal$Mnpath = new ModuleMethod(var0, 22, Lit21, 0);
      request$Mnpath = new ModuleMethod(var0, 23, Lit22, 0);
      request$Mnuri = new ModuleMethod(var0, 24, Lit23, 0);
      request$Mnurl = new ModuleMethod(var0, 25, Lit24, 0);
      request$Mnpath$Mntranslated = new ModuleMethod(var0, 26, Lit25, 0);
      request$Mnquery$Mnstring = new ModuleMethod(var0, 27, Lit26, 0);
      request$Mnparameter = new ModuleMethod(var0, 28, Lit27, 8193);
      request$Mnparameters = new ModuleMethod(var0, 30, Lit28, 4097);
      request$Mnparameter$Mnmap = new ModuleMethod(var0, 31, Lit29, 0);
      request$Mnbody$Mnstring = new ModuleMethod(var0, 32, Lit30, 0);
      request$Mninput$Mnstream = new ModuleMethod(var0, 33, Lit31, 0);
      request$Mninput$Mnport = new ModuleMethod(var0, 34, Lit32, 0);
      $instance.run();
   }

   public HTTP() {
      ModuleInfo.register(this);
   }

   public static Object errorResponse(int var0) {
      return errorResponse(var0, "Error");
   }

   public static Object errorResponse(int var0, String var1) {
      return responseHeader(Lit1, Format.formatToString(0, new Object[]{"~d ~a", Integer.valueOf(var0), var1}));
   }

   public static URIPath request$MnURI() {
      return URIPath.makeURI(HttpRequestContext.getInstance("request-URI").getRequestURI());
   }

   public static CharSequence requestBodyString() {
      return HttpRequestContext.getInstance("request-body-string").getRequestBodyChars();
   }

   public static URIPath requestContextPath() {
      return URIPath.makeURI(HttpRequestContext.getInstance("request-context-path").getContextPath());
   }

   public static String requestHeader(Object var0) {
      HttpRequestContext var1 = HttpRequestContext.getInstance("request-header");
      String var2;
      if(var0 == null) {
         var2 = null;
      } else {
         var2 = var0.toString();
      }

      return var1.getRequestHeader(var2);
   }

   public static Map requestHeaderMap() {
      return HttpRequestContext.getInstance("request-header-map").getRequestHeaders();
   }

   public static InPort requestInputPort() {
      return HttpRequestContext.getInstance("request-input-port").getRequestPort();
   }

   public static InputStream requestInputStream() {
      return HttpRequestContext.getInstance("request-input-stream").getRequestStream();
   }

   public static String requestLocal$MnIPAddress() {
      return HttpRequestContext.getInstance("request-local-IP-address").getLocalIPAddress();
   }

   public static InetAddress requestLocalHost() {
      return HttpRequestContext.getInstance("request-local-host").getLocalHost();
   }

   public static URIPath requestLocalPath() {
      return URIPath.makeURI(HttpRequestContext.getInstance("request-local-path").getLocalPath());
   }

   public static int requestLocalPort() {
      return HttpRequestContext.getInstance("request-local-port").getLocalPort();
   }

   public static InetSocketAddress requestLocalSocketAddress() {
      return HttpRequestContext.getInstance("request-local-socket-address").getLocalSocketAddress();
   }

   public static String requestMethod() {
      return HttpRequestContext.getInstance("request-method").getRequestMethod();
   }

   public static String requestParameter(String var0) {
      return requestParameter(var0, (Object)null);
   }

   public static String requestParameter(String var0, Object var1) {
      var0 = HttpRequestContext.getInstance("request-parameter").getRequestParameter(var0);
      return var0 == null?(var1 == null?null:var1.toString()):var0;
   }

   public static Map requestParameterMap() {
      return HttpRequestContext.getInstance("request-parameter-map").getRequestParameters();
   }

   public static Object requestParameters(String var0) {
      Object var3 = HttpRequestContext.getInstance("request-parameters").getRequestParameters().get(var0);

      List var1;
      try {
         var1 = (List)var3;
      } catch (ClassCastException var2) {
         throw new WrongType(var2, "plist", -2, var3);
      }

      return Values.make((List)var1);
   }

   public static String requestPath() {
      URIPath var0 = URIPath.makeURI(HttpRequestContext.getInstance("request-path").getRequestPath());
      return var0 == null?null:var0.toString();
   }

   public static String requestPathTranslated() {
      return HttpRequestContext.getInstance("request-path-translated").getPathTranslated();
   }

   public static Object requestQueryString() {
      String var1 = HttpRequestContext.getInstance("request-query-string").getQueryString();
      Object var0 = var1;
      if(var1 == null) {
         var0 = Boolean.FALSE;
      }

      return var0;
   }

   public static String requestRemote$MnIPAddress() {
      return HttpRequestContext.getInstance("request-remote-IP-address").getRemoteIPAddress();
   }

   public static InetAddress requestRemoteHost() {
      return HttpRequestContext.getInstance("request-remote-host").getRemoteHost();
   }

   public static int requestRemotePort() {
      return HttpRequestContext.getInstance("request-remote-port").getRemotePort();
   }

   public static InetSocketAddress requestRemoteSocketAddress() {
      return HttpRequestContext.getInstance("request-remote-socket-address").getRemoteSocketAddress();
   }

   public static String requestScheme() {
      return HttpRequestContext.getInstance("request-scheme").getRequestScheme();
   }

   public static URIPath requestScriptPath() {
      return URIPath.makeURI(HttpRequestContext.getInstance("request-script-path").getScriptPath());
   }

   public static String requestUri() {
      return requestPath();
   }

   public static StringBuffer requestUrl() {
      return HttpRequestContext.getInstance("request-path").getRequestURLBuffer();
   }

   public static Object responseContentType(Object var0) {
      return responseHeader(Lit0, var0);
   }

   public static Object responseHeader(Object var0, Object var1) {
      return MakeResponseHeader.makeResponseHeader.apply2(var0, var1);
   }

   public static Object responseStatus(int var0) {
      return responseStatus(var0, (String)null);
   }

   public static Object responseStatus(int var0, String var1) {
      SimpleSymbol var3 = Lit1;
      String var2;
      if(var1 == null) {
         var2 = "~d ";
      } else {
         var2 = "~d ~a";
      }

      return responseHeader(var3, Format.formatToString(0, new Object[]{var2, Integer.valueOf(var0), var1}));
   }

   public Object apply0(ModuleMethod var1) {
      switch(var1.selector) {
      case 7:
         return requestMethod();
      case 8:
         return requestScheme();
      case 9:
         return requestLocalSocketAddress();
      case 10:
         return requestLocal$MnIPAddress();
      case 11:
         return Integer.valueOf(requestLocalPort());
      case 12:
         return requestLocalHost();
      case 13:
         return requestRemoteSocketAddress();
      case 14:
         return requestRemote$MnIPAddress();
      case 15:
         return Integer.valueOf(requestRemotePort());
      case 16:
         return requestRemoteHost();
      case 17:
      case 28:
      case 29:
      case 30:
      default:
         return super.apply0(var1);
      case 18:
         return requestHeaderMap();
      case 19:
         return request$MnURI();
      case 20:
         return requestContextPath();
      case 21:
         return requestScriptPath();
      case 22:
         return requestLocalPath();
      case 23:
         return requestPath();
      case 24:
         return requestUri();
      case 25:
         return requestUrl();
      case 26:
         return requestPathTranslated();
      case 27:
         return requestQueryString();
      case 31:
         return requestParameterMap();
      case 32:
         return requestBodyString();
      case 33:
         return requestInputStream();
      case 34:
         return requestInputPort();
      }
   }

   public Object apply1(ModuleMethod var1, Object var2) {
      Object var4 = null;
      Object var3 = null;
      int var5;
      String var8;
      switch(var1.selector) {
      case 2:
         return responseContentType(var2);
      case 3:
         try {
            var5 = ((Number)var2).intValue();
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "response-status", 1, var2);
         }

         return responseStatus(var5);
      case 5:
         try {
            var5 = ((Number)var2).intValue();
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "error-response", 1, var2);
         }

         return errorResponse(var5);
      case 17:
         return requestHeader(var2);
      case 28:
         if(var2 == null) {
            var8 = (String)var3;
         } else {
            var8 = var2.toString();
         }

         return requestParameter(var8);
      case 30:
         if(var2 == null) {
            var8 = (String)var4;
         } else {
            var8 = var2.toString();
         }

         return requestParameters(var8);
      default:
         return super.apply1(var1, var2);
      }
   }

   public Object apply2(ModuleMethod var1, Object var2, Object var3) {
      Object var5 = null;
      Object var4 = null;
      int var6;
      String var9;
      switch(var1.selector) {
      case 1:
         return responseHeader(var2, var3);
      case 3:
         try {
            var6 = ((Number)var2).intValue();
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "response-status", 1, var2);
         }

         if(var3 == null) {
            var9 = null;
         } else {
            var9 = var3.toString();
         }

         return responseStatus(var6, var9);
      case 5:
         try {
            var6 = ((Number)var2).intValue();
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "error-response", 1, var2);
         }

         if(var3 == null) {
            var9 = (String)var4;
         } else {
            var9 = var3.toString();
         }

         return errorResponse(var6, var9);
      case 28:
         if(var2 == null) {
            var9 = (String)var5;
         } else {
            var9 = var2.toString();
         }

         return requestParameter(var9, var3);
      default:
         return super.apply2(var1, var2, var3);
      }
   }

   public int match0(ModuleMethod var1, CallContext var2) {
      switch(var1.selector) {
      case 7:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 8:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 9:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 10:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 11:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 12:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 13:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 14:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 15:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 16:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 17:
      case 28:
      case 29:
      case 30:
      default:
         return super.match0(var1, var2);
      case 18:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 19:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 20:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 21:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 22:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 23:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 24:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 25:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 26:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 27:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 31:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 32:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 33:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 34:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      }
   }

   public int match1(ModuleMethod var1, Object var2, CallContext var3) {
      switch(var1.selector) {
      case 2:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 3:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 5:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 17:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 28:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 30:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      default:
         return super.match1(var1, var2, var3);
      }
   }

   public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
      switch(var1.selector) {
      case 1:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 3:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 5:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 28:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      default:
         return super.match2(var1, var2, var3, var4);
      }
   }

   public final void run(CallContext var1) {
      Consumer var2 = var1.consumer;
   }
}
