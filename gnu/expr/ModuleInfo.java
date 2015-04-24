package gnu.expr;

import gnu.bytecode.ClassType;
import gnu.bytecode.Type;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.ModuleContext;
import gnu.expr.ModuleExp;
import gnu.expr.ModuleManager;
import gnu.expr.ReferenceExp;
import gnu.kawa.reflect.FieldLocation;
import gnu.kawa.util.AbstractWeakHashTable;
import gnu.text.Path;
import java.io.IOException;
import java.net.URL;

public class ModuleInfo {

   static ModuleInfo.ClassToInfoMap mapClassToInfo = new ModuleInfo.ClassToInfoMap();
   private String className;
   Compilation comp;
   ModuleInfo[] dependencies;
   ModuleExp exp;
   public long lastCheckedTime;
   public long lastModifiedTime;
   Class moduleClass;
   int numDependencies;
   Path sourceAbsPath;
   String sourceAbsPathname;
   public String sourcePath;
   String uri;


   public static Path absPath(String var0) {
      return Path.valueOf(var0).getCanonical();
   }

   public static ModuleInfo find(ClassType var0) {
      if(var0.isExisting()) {
         try {
            ModuleInfo var1 = ModuleManager.findWithClass(var0.getReflectClass());
            return var1;
         } catch (Exception var2) {
            ;
         }
      }

      return ModuleManager.getInstance().findWithClassName(var0.getName());
   }

   public static ModuleInfo findFromInstance(Object var0) {
      return ModuleContext.getContext().findFromInstance(var0);
   }

   static void makeDeclInModule2(ModuleExp var0, Declaration var1) {
      Object var2 = var1.getConstantValue();
      if(var2 instanceof FieldLocation) {
         FieldLocation var3 = (FieldLocation)var2;
         Declaration var4 = var3.getDeclaration();
         ReferenceExp var7 = new ReferenceExp(var4);
         var1.setAlias(true);
         var7.setDontDereference(true);
         var1.setValue(var7);
         if(var4.isProcedureDecl()) {
            var1.setProcedureDecl(true);
         }

         if(var4.getFlag(32768L)) {
            var1.setSyntax();
         }

         if(!var1.getFlag(2048L)) {
            String var6 = var3.getDeclaringClass().getName();

            for(Declaration var5 = var0.firstDecl(); var5 != null; var5 = var5.nextDecl()) {
               if(var6.equals(var5.getType().getName()) && var5.getFlag(1073741824L)) {
                  var7.setContextDecl(var5);
                  break;
               }
            }
         }
      }

   }

   public static void register(Object var0) {
      ModuleContext.getContext().setInstance(var0);
   }

   public void addDependency(ModuleInfo param1) {
      // $FF: Couldn't be decompiled
   }

   public boolean checkCurrent(ModuleManager var1, long var2) {
      if(this.sourceAbsPath == null) {
         return true;
      } else if(this.lastCheckedTime + var1.lastModifiedCacheTime >= var2) {
         return this.moduleClass != null;
      } else {
         long var12 = this.sourceAbsPath.getLastModified();
         long var10 = this.lastModifiedTime;
         this.lastModifiedTime = var12;
         this.lastCheckedTime = var2;
         if(this.className == null) {
            return false;
         } else {
            if(this.moduleClass == null) {
               try {
                  this.moduleClass = ClassType.getContextClass(this.className);
               } catch (ClassNotFoundException var14) {
                  return false;
               }
            }

            long var8 = var10;
            int var6;
            if(var10 == 0L) {
               var8 = var10;
               if(this.moduleClass != null) {
                  String var5 = this.className;
                  var6 = var5.lastIndexOf(46);
                  String var4 = var5;
                  if(var6 >= 0) {
                     var4 = var5.substring(var6 + 1);
                  }

                  var4 = var4 + ".class";
                  URL var16 = this.moduleClass.getResource(var4);
                  var8 = var10;
                  URL var17 = var16;
                  if(var16 != null) {
                     label53: {
                        try {
                           var8 = var16.openConnection().getLastModified();
                        } catch (IOException var15) {
                           var17 = null;
                           var8 = var10;
                           break label53;
                        }

                        var17 = var16;
                     }
                  }

                  if(var17 == null) {
                     return true;
                  }
               }
            }

            if(var12 > var8) {
               this.moduleClass = null;
               return false;
            } else {
               var6 = this.numDependencies;

               while(true) {
                  int var7 = var6 - 1;
                  if(var7 < 0) {
                     return true;
                  }

                  ModuleInfo var18 = this.dependencies[var7];
                  var6 = var7;
                  if(var18.comp == null) {
                     var6 = var7;
                     if(!var18.checkCurrent(var1, var2)) {
                        this.moduleClass = null;
                        return false;
                     }
                  }
               }
            }
         }
      }
   }

   public void cleanupAfterCompilation() {
      if(this.comp != null) {
         this.comp.cleanupAfterCompilation();
      }

   }

   public void clearClass() {
      this.moduleClass = null;
      this.numDependencies = 0;
      this.dependencies = null;
   }

   public String getClassName() {
      // $FF: Couldn't be decompiled
   }

   public ClassType getClassType() {
      synchronized(this){}

      ClassType var1;
      try {
         if(this.moduleClass != null) {
            var1 = (ClassType)Type.make(this.moduleClass);
         } else if(this.comp != null && this.comp.mainClass != null) {
            var1 = this.comp.mainClass;
         } else {
            var1 = ClassType.make(this.className);
         }
      } finally {
         ;
      }

      return var1;
   }

   public Compilation getCompilation() {
      return this.comp;
   }

   public Object getInstance() {
      return ModuleContext.getContext().findInstance((ModuleInfo)this);
   }

   public Class getModuleClass() throws ClassNotFoundException {
      // $FF: Couldn't be decompiled
   }

   public Class getModuleClassRaw() {
      return this.moduleClass;
   }

   public ModuleExp getModuleExp() {
      // $FF: Couldn't be decompiled
   }

   public String getNamespaceUri() {
      return this.uri;
   }

   public Object getRunInstance() {
      Object var1 = this.getInstance();
      if(var1 instanceof Runnable) {
         ((Runnable)var1).run();
      }

      return var1;
   }

   public Path getSourceAbsPath() {
      return this.sourceAbsPath;
   }

   public String getSourceAbsPathname() {
      String var2 = this.sourceAbsPathname;
      String var1 = var2;
      if(var2 == null) {
         var1 = var2;
         if(this.sourceAbsPath != null) {
            var1 = this.sourceAbsPath.toString();
            this.sourceAbsPathname = var1;
         }
      }

      return var1;
   }

   public int getState() {
      return this.comp == null?14:this.comp.getState();
   }

   public void loadByStages(int var1) {
      if(this.getState() + 1 < var1) {
         this.loadByStages(var1 - 2);
         int var2 = this.getState();
         if(var2 < var1) {
            this.comp.setState(var2 + 1);
            int var3 = this.numDependencies;

            for(var2 = 0; var2 < var3; ++var2) {
               this.dependencies[var2].loadByStages(var1);
            }

            var2 = this.getState();
            if(var2 < var1) {
               this.comp.setState(var2 & -2);
               this.comp.process(var1);
               return;
            }
         }
      }

   }

   public boolean loadEager(int var1) {
      boolean var5 = true;
      if(this.comp != null || this.className == null) {
         int var3 = this.getState();
         if(var3 >= var1) {
            return true;
         }

         if((var3 & 1) == 0) {
            this.comp.setState(var3 + 1);
            int var4 = this.numDependencies;
            int var2 = 0;

            while(true) {
               if(var2 >= var4) {
                  if(this.getState() == var3 + 1) {
                     this.comp.setState(var3);
                  }

                  this.comp.process(var1);
                  if(this.getState() != var1) {
                     var5 = false;
                  }

                  return var5;
               }

               if(!this.dependencies[var2].loadEager(var1)) {
                  if(this.getState() == var3 + 1) {
                     this.comp.setState(var3);
                     return false;
                  }
                  break;
               }

               ++var2;
            }
         }
      }

      return false;
   }

   public void setClassName(String var1) {
      this.className = var1;
   }

   public void setCompilation(Compilation var1) {
      var1.minfo = this;
      this.comp = var1;
      ModuleExp var2 = var1.mainLambda;
      this.exp = var2;
      if(var2 != null) {
         String var3 = var2.getFileName();
         this.sourcePath = var3;
         this.sourceAbsPath = absPath(var3);
      }

   }

   public void setModuleClass(Class var1) {
      this.moduleClass = var1;
      this.className = var1.getName();
      mapClassToInfo.put(var1, this);
   }

   public void setNamespaceUri(String var1) {
      this.uri = var1;
   }

   public void setSourceAbsPath(Path var1) {
      this.sourceAbsPath = var1;
      this.sourceAbsPathname = null;
   }

   public ModuleExp setupModuleExp() {
      // $FF: Couldn't be decompiled
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append("ModuleInfo[");
      if(this.moduleClass != null) {
         var1.append("class: ");
         var1.append(this.moduleClass);
      } else if(this.className != null) {
         var1.append("class-name: ");
         var1.append(this.className);
      }

      var1.append(']');
      return var1.toString();
   }

   static class ClassToInfoMap extends AbstractWeakHashTable {

      protected Class getKeyFromValue(ModuleInfo var1) {
         return var1.moduleClass;
      }

      protected boolean matches(Class var1, Class var2) {
         return var1 == var2;
      }
   }
}
