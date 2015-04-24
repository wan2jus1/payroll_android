package kawa;

import gnu.expr.ApplicationMainSupport;
import gnu.expr.Compilation;
import gnu.expr.Language;
import gnu.lists.FString;
import gnu.mapping.Environment;
import gnu.mapping.Procedure0or1;
import gnu.mapping.Values;
import gnu.text.WriterManager;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import kawa.Shell;

public class repl extends Procedure0or1 {

   public static String compilationTopname = null;
   static int defaultParseOptions = 72;
   public static String homeDirectory;
   public static boolean noConsole;
   static Language previousLanguage;
   static boolean shutdownRegistered = WriterManager.instance.registerShutdownHook();
   Language language;


   public repl(Language var1) {
      this.language = var1;
   }

   static void bad_option(String var0) {
      System.err.println("kawa: bad option \'" + var0 + "\'");
      printOptions(System.err);
      System.exit(-1);
   }

   static void checkInitFile() {
      if(homeDirectory == null) {
         File var0 = null;
         homeDirectory = System.getProperty("user.home");
         Object var1;
         if(homeDirectory != null) {
            var1 = new FString(homeDirectory);
            String var2;
            if("/".equals(System.getProperty("file.separator"))) {
               var2 = ".kawarc.scm";
            } else {
               var2 = "kawarc.scm";
            }

            var0 = new File(homeDirectory, var2);
         } else {
            var1 = Boolean.FALSE;
         }

         Environment.getCurrent().put((String)"home-directory", var1);
         if(var0 != null && var0.exists() && !Shell.runFileOrClass(var0.getPath(), true, 0)) {
            System.exit(-1);
         }
      }

   }

   public static void compileFiles(String[] param0, int param1, int param2) {
      // $FF: Couldn't be decompiled
   }

   public static void getLanguage() {
      if(previousLanguage == null) {
         previousLanguage = Language.getInstance((String)null);
         Language.setDefaults(previousLanguage);
      }

   }

   public static void getLanguageFromFilenameExtension(String var0) {
      if(previousLanguage == null) {
         previousLanguage = Language.getInstanceFromFilenameExtension(var0);
         if(previousLanguage != null) {
            Language.setDefaults(previousLanguage);
            return;
         }
      }

      getLanguage();
   }

   static void internalError(Throwable var0, Compilation var1, Object var2) {
      StringBuffer var3 = new StringBuffer();
      if(var1 != null) {
         String var4 = var1.getFileName();
         int var5 = var1.getLineNumber();
         if(var4 != null && var5 > 0) {
            var3.append(var4);
            var3.append(':');
            var3.append(var5);
            var3.append(": ");
         }
      }

      var3.append("internal error while compiling ");
      var3.append(var2);
      System.err.println(var3.toString());
      var0.printStackTrace(System.err);
      System.exit(-1);
   }

   public static void main(String[] param0) {
      // $FF: Couldn't be decompiled
   }

   public static void printOption(PrintStream var0, String var1, String var2) {
      var0.print(" ");
      var0.print(var1);
      int var4 = var1.length();

      for(int var3 = 0; var3 < 30 - (var4 + 1); ++var3) {
         var0.print(" ");
      }

      var0.print(" ");
      var0.println(var2);
   }

   public static void printOptions(PrintStream var0) {
      var0.println("Usage: [java kawa.repl | kawa] [options ...]");
      var0.println();
      var0.println(" Generic options:");
      printOption(var0, "--help", "Show help about options");
      printOption(var0, "--author", "Show author information");
      printOption(var0, "--version", "Show version information");
      var0.println();
      var0.println(" Options");
      printOption(var0, "-e <expr>", "Evaluate expression <expr>");
      printOption(var0, "-c <expr>", "Same as -e, but make sure ~/.kawarc.scm is run first");
      printOption(var0, "-f <filename>", "File to interpret");
      printOption(var0, "-s| --", "Start reading commands interactively from console");
      printOption(var0, "-w", "Launch the interpreter in a GUI window");
      printOption(var0, "--server <port>", "Start a server accepting telnet connections on <port>");
      printOption(var0, "--debug-dump-zip", "Compiled interactive expressions to a zip archive");
      printOption(var0, "--debug-print-expr", "Print generated internal expressions");
      printOption(var0, "--debug-print-final-expr", "Print expression after any optimizations");
      printOption(var0, "--debug-error-prints-stack-trace", "Print stack trace with errors");
      printOption(var0, "--debug-warning-prints-stack-trace", "Print stack trace with warnings");
      printOption(var0, "--[no-]full-tailcalls", "(Don\'t) use full tail-calls");
      printOption(var0, "-C <filename> ...", "Compile named files to Java class files");
      printOption(var0, "--output-format <format>", "Use <format> when printing top-level output");
      printOption(var0, "--<language>", "Select source language, one of:");
      String[][] var1 = Language.getLanguages();

      int var3;
      for(var3 = 0; var3 < var1.length; ++var3) {
         var0.print("   ");
         String[] var2 = var1[var3];
         int var5 = var2.length;

         for(int var4 = 0; var4 < var5 - 1; ++var4) {
            var0.print(var2[var4] + " ");
         }

         if(var3 == 0) {
            var0.print("[default]");
         }

         var0.println();
      }

      var0.println(" Compilation options, must be specified before -C");
      printOption(var0, "-d <dirname>", "Directory to place .class files in");
      printOption(var0, "-P <prefix>", "Prefix to prepand to class names");
      printOption(var0, "-T <topname>", "name to give to top-level class");
      printOption(var0, "--main", "Generate an application, with a main method");
      printOption(var0, "--applet", "Generate an applet");
      printOption(var0, "--servlet", "Generate a servlet");
      printOption(var0, "--module-static", "Top-level definitions are by default static");
      ArrayList var6 = Compilation.options.keys();

      for(var3 = 0; var3 < var6.size(); ++var3) {
         String var7 = (String)var6.get(var3);
         printOption(var0, "--" + var7, Compilation.options.getDoc(var7));
      }

      var0.println();
      var0.println("For more information go to:  http://www.gnu.org/software/kawa/");
   }

   public static int processArgs(String[] param0, int param1, int param2) {
      // $FF: Couldn't be decompiled
   }

   public static void setArgs(String[] var0, int var1) {
      ApplicationMainSupport.setArgs(var0, var1);
   }

   public static boolean shouldUseGuiConsole() {
      if(!noConsole) {
         Object var0;
         try {
            var0 = Class.forName("java.lang.System").getMethod("console", new Class[0]).invoke(new Object[0], new Object[0]);
         } catch (Throwable var1) {
            return false;
         }

         if(var0 == null) {
            return true;
         } else {
            return false;
         }
      } else {
         return true;
      }
   }

   private static void startGuiConsole() {
      try {
         Class.forName("kawa.GuiConsole").newInstance();
      } catch (Exception var1) {
         System.err.println("failed to create Kawa window: " + var1);
         System.exit(-1);
      }
   }

   public Object apply0() {
      Shell.run(this.language, Environment.getCurrent());
      return Values.empty;
   }

   public Object apply1(Object var1) {
      Shell.run(this.language, (Environment)var1);
      return Values.empty;
   }
}
