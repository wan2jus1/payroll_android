package kawa;

import gnu.expr.Compilation;
import gnu.expr.CompiledModule;
import gnu.expr.Language;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleExp;
import gnu.expr.ModuleManager;
import gnu.lists.AbstractFormat;
import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.Environment;
import gnu.mapping.InPort;
import gnu.mapping.OutPort;
import gnu.mapping.Procedure;
import gnu.mapping.TtyInPort;
import gnu.mapping.Values;
import gnu.mapping.WrongArguments;
import gnu.text.Path;
import gnu.text.SourceMessages;
import gnu.text.SyntaxException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.URL;

public class Shell {

   private static Class[] boolClasses = new Class[]{Boolean.TYPE};
   public static ThreadLocal currentLoadPath = new ThreadLocal();
   public static Object[] defaultFormatInfo;
   public static Method defaultFormatMethod;
   public static String defaultFormatName;
   static Object[][] formats;
   private static Class[] httpPrinterClasses = new Class[]{OutPort.class};
   private static Class[] noClasses = new Class[0];
   private static Object portArg = "(port)";
   private static Class[] xmlPrinterClasses = new Class[]{OutPort.class, Object.class};


   static {
      Class[] var0 = boolClasses;
      Boolean var1 = Boolean.FALSE;
      Class[] var2 = boolClasses;
      Boolean var3 = Boolean.TRUE;
      Object[] var19 = new Object[]{"elisp", "gnu.kawa.functions.DisplayFormat", "getEmacsLispFormat", boolClasses, Boolean.FALSE};
      Object[] var20 = new Object[]{"readable-elisp", "gnu.kawa.functions.DisplayFormat", "getEmacsLispFormat", boolClasses, Boolean.TRUE};
      Object[] var21 = new Object[]{"clisp", "gnu.kawa.functions.DisplayFormat", "getCommonLispFormat", boolClasses, Boolean.FALSE};
      Class[] var4 = boolClasses;
      Boolean var5 = Boolean.TRUE;
      Class[] var6 = boolClasses;
      Boolean var7 = Boolean.FALSE;
      Class[] var8 = boolClasses;
      Boolean var9 = Boolean.TRUE;
      Class[] var10 = xmlPrinterClasses;
      Object var11 = portArg;
      Class[] var12 = xmlPrinterClasses;
      Object var13 = portArg;
      Class[] var14 = xmlPrinterClasses;
      Object var15 = portArg;
      Class[] var16 = httpPrinterClasses;
      Object var17 = portArg;
      Class[] var18 = noClasses;
      formats = new Object[][]{{"scheme", "gnu.kawa.functions.DisplayFormat", "getSchemeFormat", var0, var1}, {"readable-scheme", "gnu.kawa.functions.DisplayFormat", "getSchemeFormat", var2, var3}, var19, var20, var21, {"readable-clisp", "gnu.kawa.functions.DisplayFormat", "getCommonLispFormat", var4, var5}, {"commonlisp", "gnu.kawa.functions.DisplayFormat", "getCommonLispFormat", var6, var7}, {"readable-commonlisp", "gnu.kawa.functions.DisplayFormat", "getCommonLispFormat", var8, var9}, {"xml", "gnu.xml.XMLPrinter", "make", var10, var11, null}, {"html", "gnu.xml.XMLPrinter", "make", var12, var13, "html"}, {"xhtml", "gnu.xml.XMLPrinter", "make", var14, var15, "xhtml"}, {"cgi", "gnu.kawa.xml.HttpPrinter", "make", var16, var17}, {"ignore", "gnu.lists.VoidConsumer", "getInstance", var18}, {null}};
   }

   public static final CompiledModule checkCompiledZip(InputStream param0, Path param1, Environment param2, Language param3) throws IOException {
      // $FF: Couldn't be decompiled
   }

   static CompiledModule compileSource(InPort var0, Environment var1, URL var2, Language var3, SourceMessages var4) throws SyntaxException, IOException {
      Compilation var5 = var3.parse(var0, var4, 1, ModuleManager.getInstance().findWithSourcePath(var0.getName()));
      CallContext.getInstance().values = Values.noArgs;
      Object var6 = ModuleExp.evalModule1(var1, var5, var2, (OutPort)null);
      return var6 != null && !var4.seenErrors()?new CompiledModule(var5.getModule(), var6, var3):null;
   }

   public static Consumer getOutputConsumer(OutPort param0) {
      // $FF: Couldn't be decompiled
   }

   public static void printError(Throwable var0, SourceMessages var1, OutPort var2) {
      if(var0 instanceof WrongArguments) {
         WrongArguments var4 = (WrongArguments)var0;
         var1.printAll((PrintWriter)var2, 20);
         if(var4.usage != null) {
            var2.println("usage: " + var4.usage);
         }

         var4.printStackTrace(var2);
      } else if(var0 instanceof ClassCastException) {
         var1.printAll((PrintWriter)var2, 20);
         var2.println("Invalid parameter, was: " + var0.getMessage());
         var0.printStackTrace(var2);
      } else {
         if(var0 instanceof SyntaxException) {
            SyntaxException var3 = (SyntaxException)var0;
            if(var3.getMessages() == var1) {
               var3.printAll(var2, 20);
               var3.clear();
               return;
            }
         }

         var1.printAll((PrintWriter)var2, 20);
         var0.printStackTrace(var2);
      }
   }

   public static Throwable run(Language param0, Environment param1, InPort param2, Consumer param3, OutPort param4, URL param5, SourceMessages param6) {
      // $FF: Couldn't be decompiled
   }

   public static Throwable run(Language var0, Environment var1, InPort var2, OutPort var3, OutPort var4, SourceMessages var5) {
      AbstractFormat var6 = null;
      if(var3 != null) {
         var6 = var3.objectFormat;
      }

      Consumer var7 = getOutputConsumer(var3);

      Throwable var10;
      try {
         var10 = run(var0, var1, var2, var7, var4, (URL)null, var5);
      } finally {
         if(var3 != null) {
            var3.objectFormat = var6;
         }

      }

      return var10;
   }

   public static boolean run(Language var0, Environment var1) {
      InPort var4 = InPort.inDefault();
      SourceMessages var3 = new SourceMessages();
      OutPort var6;
      if(var4 instanceof TtyInPort) {
         Procedure var2 = var0.getPrompter();
         if(var2 != null) {
            ((TtyInPort)var4).setPrompter(var2);
         }

         var6 = OutPort.errDefault();
      } else {
         var6 = null;
      }

      Throwable var5 = run(var0, var1, var4, (OutPort)OutPort.outDefault(), var6, (SourceMessages)var3);
      if(var5 == null) {
         return true;
      } else {
         printError(var5, var3, OutPort.errDefault());
         return false;
      }
   }

   public static boolean run(Language var0, Environment var1, InPort var2, Consumer var3, OutPort var4, URL var5) {
      SourceMessages var6 = new SourceMessages();
      Throwable var7 = run(var0, var1, var2, var3, var4, var5, var6);
      if(var7 != null) {
         printError(var7, var6, var4);
      }

      return var7 == null;
   }

   public static final boolean runFile(InputStream param0, Path param1, Environment param2, boolean param3, int param4) throws Throwable {
      // $FF: Couldn't be decompiled
   }

   public static boolean runFileOrClass(String param0, boolean param1, int param2) {
      // $FF: Couldn't be decompiled
   }

   public static void setDefaultFormat(String var0) {
      var0 = var0.intern();
      defaultFormatName = var0;
      int var3 = 0;

      while(true) {
         Object[] var1 = formats[var3];
         Object var2 = var1[0];
         if(var2 == null) {
            System.err.println("kawa: unknown output format \'" + var0 + "\'");
            System.exit(-1);
         } else if(var2 == var0) {
            defaultFormatInfo = var1;

            try {
               defaultFormatMethod = Class.forName((String)var1[1]).getMethod((String)var1[2], (Class[])((Class[])var1[3]));
            } catch (Throwable var4) {
               System.err.println("kawa:  caught " + var4 + " while looking for format \'" + var0 + "\'");
               System.exit(-1);
            }

            if(!defaultFormatInfo[1].equals("gnu.lists.VoidConsumer")) {
               ModuleBody.setMainPrintValues(true);
            }

            return;
         }

         ++var3;
      }
   }
}
