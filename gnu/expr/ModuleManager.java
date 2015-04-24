package gnu.expr;

import gnu.bytecode.ClassType;
import gnu.expr.Compilation;
import gnu.expr.ModuleExp;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleSet;
import gnu.mapping.WrappedException;
import gnu.text.Path;
import gnu.text.URLPath;
import java.io.File;
import java.net.URL;

public class ModuleManager {

   public static final long LAST_MODIFIED_CACHE_TIME = 1000L;
   static ModuleManager instance = new ModuleManager();
   private String compilationDirectory = "";
   public long lastModifiedCacheTime = 1000L;
   ModuleInfo[] modules;
   int numModules;
   ModuleSet packageInfoChain;


   private void add(ModuleInfo param1) {
      // $FF: Couldn't be decompiled
   }

   public static ModuleInfo findWithClass(Class param0) {
      // $FF: Couldn't be decompiled
   }

   public static ModuleManager getInstance() {
      return instance;
   }

   private ModuleInfo searchWithAbsSourcePath(String param1) {
      // $FF: Couldn't be decompiled
   }

   public void clear() {
      // $FF: Couldn't be decompiled
   }

   public ModuleInfo find(Compilation var1) {
      synchronized(this){}

      ModuleInfo var7;
      try {
         ModuleExp var2 = var1.getModule();
         ClassType var3 = var2.classFor(var1);
         String var4 = var2.getFileName();
         var7 = this.findWithSourcePath(ModuleInfo.absPath(var4), var4);
         var7.setClassName(var3.getName());
         var7.exp = var2;
         var1.minfo = var7;
         var7.comp = var1;
      } finally {
         ;
      }

      return var7;
   }

   public ModuleInfo findWithClassName(String var1) {
      ModuleInfo var2 = this.searchWithClassName(var1);
      if(var2 != null) {
         return var2;
      } else {
         try {
            ModuleInfo var4 = findWithClass(ClassType.getContextClass(var1));
            return var4;
         } catch (Throwable var3) {
            throw WrappedException.wrapIfNeeded(var3);
         }
      }
   }

   public ModuleInfo findWithSourcePath(Path param1, String param2) {
      // $FF: Couldn't be decompiled
   }

   public ModuleInfo findWithSourcePath(String var1) {
      synchronized(this){}

      ModuleInfo var4;
      try {
         var4 = this.findWithSourcePath(ModuleInfo.absPath(var1), var1);
      } finally {
         ;
      }

      return var4;
   }

   public ModuleInfo findWithURL(URL var1) {
      synchronized(this){}

      ModuleInfo var4;
      try {
         var4 = this.findWithSourcePath(URLPath.valueOf(var1), var1.toExternalForm());
      } finally {
         ;
      }

      return var4;
   }

   public String getCompilationDirectory() {
      return this.compilationDirectory;
   }

   public ModuleInfo getModule(int param1) {
      // $FF: Couldn't be decompiled
   }

   public void loadPackageInfo(String param1) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
      // $FF: Couldn't be decompiled
   }

   public void register(String param1, String param2, String param3) {
      // $FF: Couldn't be decompiled
   }

   public ModuleInfo searchWithClassName(String param1) {
      // $FF: Couldn't be decompiled
   }

   public void setCompilationDirectory(String var1) {
      String var3 = var1;
      if(var1 == null) {
         var3 = "";
      }

      int var4 = var3.length();
      var1 = var3;
      if(var4 > 0) {
         char var2 = File.separatorChar;
         var1 = var3;
         if(var3.charAt(var4 - 1) != var2) {
            var1 = var3 + var2;
         }
      }

      this.compilationDirectory = var1;
   }
}
