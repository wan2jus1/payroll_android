package kawa.standard;

import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleManager;
import gnu.expr.ScopeExp;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Procedure;
import java.util.Vector;
import kawa.lang.Syntax;
import kawa.lang.Translator;
import kawa.standard.require;

public class ImportFromLibrary extends Syntax {

   private static final String BUILTIN = "";
   private static final String MISSING = null;
   static final String[][] SRFI97Map;
   public static final ImportFromLibrary instance = new ImportFromLibrary();
   public String[] classPrefixPath = new String[]{"", "kawa.lib."};


   static {
      String[] var21 = new String[]{"2", "and-let*", "gnu.kawa.slib.srfi2"};
      String[] var22 = new String[]{"5", "let", MISSING};
      String[] var23 = new String[]{"9", "records", ""};
      String[] var24 = new String[]{"11", "let-values", ""};
      String var0 = MISSING;
      String var1 = MISSING;
      String var2 = MISSING;
      String var3 = MISSING;
      String[] var25 = new String[]{"26", "cut", ""};
      String var4 = MISSING;
      String[] var26 = new String[]{"28", "basic-format-strings", ""};
      String[] var27 = new String[]{"29", "localization", MISSING};
      String var5 = MISSING;
      String var6 = MISSING;
      String[] var28 = new String[]{"39", "parameters", ""};
      String var7 = MISSING;
      String[] var29 = new String[]{"42", "eager-comprehensions", MISSING};
      String var8 = MISSING;
      String var9 = MISSING;
      String var10 = MISSING;
      String var11 = MISSING;
      String[] var30 = new String[]{"47", "arrays", MISSING};
      String[] var31 = new String[]{"48", "intermediate-format-strings", MISSING};
      String[] var32 = new String[]{"51", "rest-values", MISSING};
      String[] var33 = new String[]{"54", "cat", MISSING};
      String var12 = MISSING;
      String var13 = MISSING;
      String var14 = MISSING;
      String var15 = MISSING;
      String var16 = MISSING;
      String[] var34 = new String[]{"66", "octet-vectors", MISSING};
      String var17 = MISSING;
      String[] var35 = new String[]{"71", "let", MISSING};
      String var18 = MISSING;
      String var19 = MISSING;
      String var20 = MISSING;
      String[] var36 = new String[]{"87", "case", MISSING};
      SRFI97Map = new String[][]{{"1", "lists", "gnu.kawa.slib.srfi1"}, var21, var22, {"6", "basic-string-ports", ""}, {"8", "receive", ""}, var23, var24, {"13", "strings", "gnu.kawa.slib.srfi13"}, {"14", "char-sets", var0}, {"16", "case-lambda", ""}, {"17", "generalized-set!", ""}, {"18", "multithreading", var1}, {"19", "time", var2}, {"21", "real-time-multithreading", var3}, {"23", "error", ""}, {"25", "multi-dimensional-arrays", ""}, var25, {"27", "random-bits", var4}, var26, var27, {"31", "rec", var5}, {"38", "with-shared-structure", var6}, var28, {"41", "streams", var7}, var29, {"43", "vectors", var8}, {"44", "collections", var9}, {"45", "lazy", var10}, {"46", "syntax-rules", var11}, var30, var31, var32, var33, {"57", "records", var12}, {"59", "vicinities", var13}, {"60", "integer-bits", var14}, {"61", "cond", var15}, {"63", "arrays", var16}, {"64", "testing", "gnu.kawa.slib.testing"}, var34, {"67", "compare-procedures", var17}, {"69", "basic-hash-tables", "gnu.kawa.slib.srfi69"}, var35, {"74", "blobs", var18}, {"78", "lightweight-testing", var19}, {"86", "mu-and-nu", var20}, var36, {"95", "sorting-and-merging", "kawa.lib.srfi95"}};
   }

   public Expression rewriteForm(Pair var1, Translator var2) {
      return null;
   }

   public boolean scanForDefinitions(Pair var1, Vector var2, ScopeExp var3, Translator var4) {
      String var6 = null;
      Object var13 = var1.getCdr();
      if(!(var13 instanceof Pair)) {
         return false;
      } else {
         var1 = (Pair)var13;
         Object var7 = var1.getCar();
         if(LList.listLength(var7, false) <= 0) {
            var4.error('e', "expected <library reference> which must be a list");
            return false;
         } else {
            var13 = var1.getCdr();
            Procedure var5 = var6;
            if(var13 instanceof Pair) {
               var5 = var6;
               if(((Pair)var13).getCar() instanceof Procedure) {
                  var5 = (Procedure)((Pair)var13).getCar();
               }
            }

            var13 = null;
            var6 = null;
            StringBuffer var9 = new StringBuffer();

            Object var8;
            while(var7 instanceof Pair) {
               Pair var15 = (Pair)var7;
               var8 = var15.getCar();
               var7 = var15.getCdr();
               if(var8 instanceof Pair) {
                  if(var13 != null) {
                     var4.error('e', "duplicate version reference - was " + var13);
                  }

                  var13 = var8;
                  System.err.println("import version " + var8);
               } else if(var8 instanceof String) {
                  if(var7 instanceof Pair) {
                     var4.error('e', "source specifier must be last elemnt in library reference");
                  }

                  var6 = (String)var8;
               } else {
                  if(var9.length() > 0) {
                     var9.append('.');
                  }

                  var9.append(Compilation.mangleNameIfNeeded(var8.toString()));
               }
            }

            ModuleInfo var14 = null;
            ModuleInfo var16;
            if(var6 != null) {
               var16 = require.lookupModuleFromSourcePath(var6, var3);
               var14 = var16;
               if(var16 == null) {
                  var4.error('e', "malformed URL: " + var6);
                  return false;
               }
            }

            String var18 = var9.toString();
            var6 = var18;
            int var10;
            int var11;
            if(var18.startsWith("srfi.")) {
               String var19 = Compilation.demangleName(var18.substring(5));
               var10 = var19.indexOf(46);
               if(var10 < 0) {
                  var6 = null;
                  var10 = var19.length();
               } else {
                  var6 = var19.substring(var10 + 1);
               }

               label136: {
                  var8 = null;
                  if(var10 < 2) {
                     var18 = (String)var8;
                     if(var19.charAt(0) != 58) {
                        break label136;
                     }
                  }

                  var11 = 1;

                  while(true) {
                     if(var11 == var10) {
                        var18 = var19.substring(1, var10);
                        break;
                     }

                     var18 = (String)var8;
                     if(Character.digit(var19.charAt(var11), 10) < 0) {
                        break;
                     }

                     ++var11;
                  }
               }

               if(var18 == null) {
                  var4.error('e', "SRFI library reference must have the form: (srfi :NNN [name])");
                  return false;
               }

               var10 = SRFI97Map.length;

               do {
                  var11 = var10 - 1;
                  if(var11 < 0) {
                     var4.error('e', "unknown SRFI number \'" + var18 + "\' in SRFI library reference");
                     return false;
                  }

                  var10 = var11;
               } while(!SRFI97Map[var11][0].equals(var18));

               var19 = SRFI97Map[var11][1];
               String var17 = SRFI97Map[var11][2];
               if(var6 != null && !var6.equals(var19)) {
                  var4.error('w', "the name of SRFI " + var18 + " should be \'" + var19 + '\'');
               }

               if(var17 == "") {
                  return true;
               }

               if(var17 == MISSING) {
                  var4.error('e', "sorry - Kawa does not support SRFI " + var18 + " (" + var19 + ')');
                  return false;
               }

               var6 = var17;
            }

            var11 = this.classPrefixPath.length;

            for(var10 = 0; var10 < var11; ++var10) {
               var18 = this.classPrefixPath[var10] + var6;

               try {
                  var16 = ModuleManager.getInstance().findWithClassName(var18);
               } catch (Exception var12) {
                  continue;
               }

               var14 = var16;
            }

            if(var14 == null) {
               var4.error('e', "unknown class " + var6);
               return false;
            } else {
               require.importDefinitions((String)null, var14, var5, var2, var3, var4);
               return true;
            }
         }
      }
   }
}
