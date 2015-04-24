package kawa.standard;

import gnu.bytecode.ClassType;
import gnu.bytecode.Type;
import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleManager;
import gnu.expr.ScopeExp;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Procedure;
import gnu.mapping.Symbol;
import gnu.text.Path;
import java.util.Hashtable;
import java.util.Vector;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class require extends Syntax {

   private static final String SLIB_PREFIX = "gnu.kawa.slib.";
   static Hashtable featureMap;
   public static final require require = new require();


   static {
      require.setName("require");
      featureMap = new Hashtable();
      map("generic-write", "gnu.kawa.slib.genwrite");
      map("pretty-print", "gnu.kawa.slib.pp");
      map("pprint-file", "gnu.kawa.slib.ppfile");
      map("printf", "gnu.kawa.slib.printf");
      map("xml", "gnu.kawa.slib.XML");
      map("readtable", "gnu.kawa.slib.readtable");
      map("srfi-10", "gnu.kawa.slib.readtable");
      map("http", "gnu.kawa.servlet.HTTP");
      map("servlets", "gnu.kawa.servlet.servlets");
      map("srfi-1", "gnu.kawa.slib.srfi1");
      map("list-lib", "gnu.kawa.slib.srfi1");
      map("srfi-2", "gnu.kawa.slib.srfi2");
      map("and-let*", "gnu.kawa.slib.srfi2");
      map("srfi-13", "gnu.kawa.slib.srfi13");
      map("string-lib", "gnu.kawa.slib.srfi13");
      map("srfi-34", "gnu.kawa.slib.srfi34");
      map("srfi-35", "gnu.kawa.slib.conditions");
      map("condition", "gnu.kawa.slib.conditions");
      map("conditions", "gnu.kawa.slib.conditions");
      map("srfi-37", "gnu.kawa.slib.srfi37");
      map("args-fold", "gnu.kawa.slib.srfi37");
      map("srfi-64", "gnu.kawa.slib.testing");
      map("testing", "gnu.kawa.slib.testing");
      map("srfi-69", "gnu.kawa.slib.srfi69");
      map("hash-table", "gnu.kawa.slib.srfi69");
      map("basic-hash-tables", "gnu.kawa.slib.srfi69");
      map("srfi-95", "kawa.lib.srfi95");
      map("sorting-and-merging", "kawa.lib.srfi95");
      map("regex", "kawa.lib.kawa.regex");
      map("pregexp", "gnu.kawa.slib.pregexp");
      map("gui", "gnu.kawa.slib.gui");
      map("swing-gui", "gnu.kawa.slib.swing");
      map("android-defs", "gnu.kawa.android.defs");
      map("syntax-utils", "gnu.kawa.slib.syntaxutils");
   }

   public static Object find(String var0) {
      return ModuleManager.getInstance().findWithClassName(var0).getInstance();
   }

   public static boolean importDefinitions(String param0, ModuleInfo param1, Procedure param2, Vector param3, ScopeExp param4, Compilation param5) {
      // $FF: Couldn't be decompiled
   }

   public static ModuleInfo lookupModuleFromSourcePath(String var0, ScopeExp var1) {
      ModuleManager var2 = ModuleManager.getInstance();
      String var3 = var1.getFileName();
      String var4 = var0;
      if(var3 != null) {
         var4 = Path.valueOf(var3).resolve((String)var0).toString();
      }

      return var2.findWithSourcePath(var4);
   }

   static void map(String var0, String var1) {
      featureMap.put(var0, var1);
   }

   public static String mapFeature(String var0) {
      return (String)featureMap.get(var0);
   }

   public Expression rewriteForm(Pair var1, Translator var2) {
      return null;
   }

   public boolean scanForDefinitions(Pair var1, Vector var2, ScopeExp var3, Translator var4) {
      if(var4.getState() == 1) {
         var4.setState(2);
         var4.pendingForm = var1;
         return true;
      } else {
         Object var9;
         ModuleInfo var12;
         label66: {
            Pair var6 = (Pair)var1.getCdr();
            Object var7 = var6.getCar();
            Type var5 = null;
            if(var7 instanceof Pair) {
               var1 = (Pair)var7;
               if(var4.matches(var1.getCar(), "quote")) {
                  var9 = var1.getCdr();
                  if(var9 instanceof Pair) {
                     var1 = (Pair)var9;
                     if(var1.getCdr() == LList.Empty && var1.getCar() instanceof Symbol) {
                        String var11 = mapFeature(var1.getCar().toString());
                        if(var11 == null) {
                           var4.error('e', "unknown feature name \'" + var1.getCar() + "\' for \'require\'");
                           return false;
                        }

                        var9 = ClassType.make((String)var11);
                        break label66;
                     }
                  }

                  var4.error('e', "invalid quoted symbol for \'require\'");
                  return false;
               }
            }

            String var10;
            if(var7 instanceof CharSequence) {
               var10 = var7.toString();
               var12 = lookupModuleFromSourcePath(var10, var3);
               if(var12 == null) {
                  var4.error('e', "malformed URL: " + var10);
                  return false;
               }

               return importDefinitions((String)null, var12, (Procedure)null, var2, var3, var4);
            }

            var9 = var5;
            if(var7 instanceof Symbol) {
               var9 = var5;
               if(!var4.selfEvaluatingSymbol(var7)) {
                  var5 = var4.getLanguage().getTypeFor((Expression)var4.rewrite(var7, false));
                  var9 = var5;
                  if(var5 instanceof ClassType) {
                     var9 = var5;
                     if(var6.getCdr() instanceof Pair) {
                        Object var14 = ((Pair)var6.getCdr()).getCar();
                        var9 = var5;
                        if(var14 instanceof CharSequence) {
                           var10 = var14.toString();
                           ModuleInfo var13 = lookupModuleFromSourcePath(var10, var3);
                           if(var13 == null) {
                              var4.error('e', "malformed URL: " + var10);
                              return false;
                           }

                           return importDefinitions(var5.getName(), var13, (Procedure)null, var2, var3, var4);
                        }
                     }
                  }
               }
            }
         }

         if(!(var9 instanceof ClassType)) {
            var4.error('e', "invalid specifier for \'require\'");
            return false;
         } else {
            try {
               var12 = ModuleInfo.find((ClassType)var9);
            } catch (Exception var8) {
               var4.error('e', "unknown class " + ((Type)var9).getName());
               return false;
            }

            importDefinitions((String)null, var12, (Procedure)null, var2, var3, var4);
            return true;
         }
      }
   }
}
