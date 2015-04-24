package gnu.kawa.servlet;

import gnu.expr.Compilation;
import gnu.expr.Language;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleContext;
import gnu.expr.ModuleExp;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleManager;
import gnu.kawa.servlet.HttpRequestContext;
import gnu.kawa.servlet.ServletPrinter;
import gnu.mapping.CallContext;
import gnu.mapping.Environment;
import gnu.mapping.InPort;
import gnu.mapping.OutPort;
import gnu.text.Path;
import gnu.text.SourceMessages;
import gnu.text.SyntaxException;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Hashtable;

public class KawaAutoHandler {

   static final String MODULE_MAP_ATTRIBUTE = "gnu.kawa.module-map";


   public static Object getModule(HttpRequestContext var0, CallContext var1, boolean var2) throws Exception {
      String var6 = var0.getRequestPath().substring(var0.getContextPath().length() - 1);
      Hashtable var3 = (Hashtable)var0.getAttribute("gnu.kawa.module-map");
      Hashtable var8 = var3;
      if(var3 == null) {
         var8 = new Hashtable();
         var0.setAttribute("gnu.kawa.module-map", var8);
      }

      ModuleContext var21 = (ModuleContext)var0.getAttribute("gnu.kawa.module-context");
      ModuleContext var5 = var21;
      if(var21 == null) {
         var5 = ModuleContext.getContext();
      }

      var5.addFlags(ModuleContext.IN_HTTP_SERVER);
      if(var0.getClass().getName().endsWith("KawaServlet$Context")) {
         var5.addFlags(ModuleContext.IN_SERVLET);
      }

      ModuleInfo var11 = (ModuleInfo)var8.get(var6);
      long var14 = System.currentTimeMillis();
      ModuleManager var12 = var5.getManager();
      if(var11 != null && var14 - var11.lastCheckedTime < var12.lastModifiedCacheTime) {
         return var5.findInstance((ModuleInfo)var11);
      } else {
         int var13 = var6.length();
         URL var22;
         if(var13 != 0 && var6.charAt(var13 - 1) != 47) {
            var22 = var0.getResourceURL(var6);
         } else {
            var22 = null;
         }

         String var4 = var6;
         if(var22 == null) {
            String var7 = var6;

            while(true) {
               var13 = var7.lastIndexOf(47);
               if(var13 < 0) {
                  break;
               }

               var7 = var7.substring(0, var13);
               String var10 = var7 + "/+default+";
               URL var9 = var0.getResourceURL(var10);
               var4 = var10;
               var22 = var9;
               if(var9 != null) {
                  var0.setScriptAndLocalPath(var6.substring(1, var13 + 1), var6.substring(var13 + 1));
                  var4 = var10;
                  var22 = var9;
                  break;
               }
            }
         } else {
            var0.setScriptAndLocalPath(var6, "");
         }

         OutputStream var19;
         byte[] var20;
         if(var22 == null) {
            var20 = ("The requested URL " + var6 + " was not found on this server." + " res/:" + var0.getResourceURL("/") + "\r\n").getBytes();
            var0.sendResponseHeaders(404, (String)null, (long)var20.length);
            var19 = var0.getResponseStream();

            try {
               var19.write(var20);
               return null;
            } catch (IOException var16) {
               throw new RuntimeException(var16);
            }
         } else {
            ModuleInfo var28;
            label100: {
               String var29 = var22.toExternalForm();
               if(var11 != null) {
                  var28 = var11;
                  if(var29.equals(var11.getSourceAbsPathname())) {
                     break label100;
                  }
               }

               var28 = var12.findWithURL(var22);
            }

            if(var28.checkCurrent(var12, var14)) {
               return var5.findInstance((ModuleInfo)var28);
            } else {
               var8.put(var6, var28);
               Path var33 = var28.getSourceAbsPath();
               InputStream var32 = var33.openInputStream();
               Object var30 = var32;
               if(!(var32 instanceof BufferedInputStream)) {
                  var30 = new BufferedInputStream(var32);
               }

               Language var31 = Language.getInstanceFromFilenameExtension(var6);
               Language var23;
               if(var31 != null) {
                  var0.log("Compile " + var6 + " - a " + var31.getName() + " source file (based on extension)");
                  var23 = var31;
               } else {
                  var31 = Language.detect((InputStream)var30);
                  if(var31 == null) {
                     if(var6 != var4) {
                        var20 = ("The requested URL " + var6 + " was not found on this server." + " upath=" + var4 + ".\r\n").getBytes();
                        var0.sendResponseHeaders(404, (String)null, (long)var20.length);
                        var19 = var0.getResponseStream();

                        try {
                           var19.write(var20);
                           return null;
                        } catch (IOException var17) {
                           throw new RuntimeException(var17);
                        }
                     }

                     var0.sendResponseHeaders(200, (String)null, var33.getContentLength());
                     var19 = var0.getResponseStream();
                     var20 = new byte[4096];

                     while(true) {
                        var13 = ((InputStream)var30).read(var20);
                        if(var13 < 0) {
                           ((InputStream)var30).close();
                           var19.close();
                           return null;
                        }

                        var19.write(var20, 0, var13);
                     }
                  }

                  var0.log("Compile " + var6 + " - a " + var31.getName() + " source file (detected from content)");
                  var23 = var31;
               }

               InPort var27 = new InPort((InputStream)var30, var33);
               Language.setCurrentLanguage(var23);
               SourceMessages var34 = new SourceMessages();

               Compilation var24;
               try {
                  var24 = var23.parse(var27, var34, 9, var28);
               } catch (SyntaxException var18) {
                  if(var18.getMessages() != var34) {
                     throw var18;
                  }

                  var24 = null;
               }

               Class var26 = null;
               if(!var34.seenErrors()) {
                  var24.getModule();
                  var26 = (Class)ModuleExp.evalModule1(Environment.getCurrent(), var24, var22, (OutPort)null);
               }

               if(var34.seenErrors()) {
                  String var25 = "script syntax error:\n" + var34.toString(20);
                  ((ServletPrinter)var1.consumer).addHeader("Content-type", "text/plain");
                  var0.sendResponseHeaders(500, "Syntax errors", -1L);
                  var1.consumer.write(var25);
                  var28.cleanupAfterCompilation();
                  return null;
               } else {
                  var28.setModuleClass(var26);
                  return var5.findInstance((ModuleInfo)var28);
               }
            }
         }
      }
   }

   public static void run(HttpRequestContext var0, CallContext var1) throws Throwable {
      boolean var2;
      if(var0.getRequestParameter("qexo-save-class") != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      Object var3 = getModule(var0, var1, var2);
      if(var3 instanceof ModuleBody) {
         ((ModuleBody)var3).run((CallContext)var1);
      }

   }
}
