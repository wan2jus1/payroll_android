package kawa.standard;

import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.Language;
import gnu.expr.QuoteExp;
import gnu.expr.ScopeExp;
import gnu.kawa.lispexpr.LispReader;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.Sequence;
import gnu.mapping.InPort;
import gnu.mapping.Symbol;
import gnu.text.SyntaxException;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import kawa.lang.AutoloadProcedure;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class define_autoload extends Syntax {

   public static final define_autoload define_autoload = new define_autoload("define-autoload", false);
   public static final define_autoload define_autoloads_from_file = new define_autoload("define-autoloads-from-file", true);
   boolean fromFile;


   public define_autoload(String var1, boolean var2) {
      super(var1);
      this.fromFile = var2;
   }

   public static void findAutoloadComments(LispReader var0, String var1, ScopeExp var2, Translator var3) throws IOException, SyntaxException {
      boolean var8 = true;
      int var12 = ";;;###autoload".length();

      label78:
      while(true) {
         int var10 = var0.peek();
         if(var10 < 0) {
            return;
         }

         if(var10 != 10 && var10 != 13) {
            int var11 = var10;
            if(var8) {
               var11 = var10;
               if(var10 == 59) {
                  int var9 = 0;
                  int var17 = var10;

                  while(var9 != var12) {
                     var10 = var0.read();
                     if(var10 < 0) {
                        return;
                     }

                     if(var10 == 10 || var10 == 13) {
                        var8 = true;
                        continue label78;
                     }

                     var17 = var10;
                     if(var9 >= 0) {
                        if(var10 == ";;;###autoload".charAt(var9)) {
                           ++var9;
                           var17 = var10;
                        } else {
                           var9 = -1;
                           var17 = var10;
                        }
                     }
                  }

                  var11 = var17;
                  if(var9 > 0) {
                     Object var4 = var0.readObject();
                     if(var4 instanceof Pair) {
                        Pair var7 = (Pair)var4;
                        Object var6 = null;
                        String var5 = null;
                        var4 = var7.getCar();
                        String var13;
                        if(var4 instanceof String) {
                           var13 = var4.toString();
                        } else if(var4 instanceof Symbol) {
                           var13 = ((Symbol)var4).getName();
                        } else {
                           var13 = null;
                        }

                        AutoloadProcedure var15;
                        if(var13 == "defun") {
                           var5 = ((Pair)var7.getCdr()).getCar().toString();
                           var15 = new AutoloadProcedure(var5, var1, var3.getLanguage());
                        } else {
                           var3.error('w', "unsupported ;;;###autoload followed by: " + var7.getCar());
                           var15 = (AutoloadProcedure)var6;
                        }

                        if(var15 != null) {
                           Declaration var14 = var2.getDefine(var5, 'w', var3);
                           QuoteExp var16 = new QuoteExp(var15);
                           var14.setFlag(16384L);
                           var14.noteValue(var16);
                           var14.setProcedureDecl(true);
                           var14.setType(Compilation.typeProcedure);
                        }
                     }

                     var8 = false;
                     continue;
                  }
               }
            }

            boolean var18 = false;
            var0.skip();
            if(var11 == 35 && var0.peek() == 124) {
               var0.skip();
               var0.readNestedComment('#', '|');
               var8 = var18;
            } else {
               var8 = var18;
               if(!Character.isWhitespace((char)var11)) {
                  var8 = var18;
                  if(var0.readObject(var11) == Sequence.eofValue) {
                     return;
                  }
               }
            }
         } else {
            var0.read();
            var8 = true;
         }
      }
   }

   public static boolean process(Object var0, Object var1, Vector var2, ScopeExp var3, Translator var4) {
      if(var0 instanceof Pair) {
         Pair var7 = (Pair)var0;
         if(!process(var7.getCar(), var1, var2, var3, var4) || !process(var7.getCdr(), var1, var2, var3, var4)) {
            return false;
         }
      } else if(var0 != LList.Empty) {
         if(!(var0 instanceof String) && !(var0 instanceof Symbol)) {
            return false;
         }

         String var8 = var0.toString();
         Declaration var10 = var3.getDefine(var8, 'w', var4);
         var0 = var1;
         if(var1 instanceof String) {
            String var5 = (String)var1;
            int var6 = var5.length();
            var0 = var1;
            if(var6 > 2) {
               var0 = var1;
               if(var5.charAt(0) == 60) {
                  var0 = var1;
                  if(var5.charAt(var6 - 1) == 62) {
                     var0 = var5.substring(1, var6 - 1);
                  }
               }
            }
         }

         QuoteExp var9 = new QuoteExp(new AutoloadProcedure(var8, var0.toString(), var4.getLanguage()));
         var10.setFlag(16384L);
         var10.noteValue(var9);
         return true;
      }

      return true;
   }

   public Expression rewriteForm(Pair var1, Translator var2) {
      return null;
   }

   public boolean scanFile(String var1, ScopeExp var2, Translator var3) {
      if(var1.endsWith(".el")) {
         ;
      }

      File var5 = new File(var1);
      File var4 = var5;
      if(!var5.isAbsolute()) {
         var4 = new File((new File(var3.getFileName())).getParent(), var1);
      }

      String var6 = var4.getPath();
      int var8 = var6.lastIndexOf(46);
      if(var8 >= 0) {
         String var11 = var6.substring(var8);
         Language var7 = Language.getInstance(var11);
         if(var7 == null) {
            var3.syntaxError("unknown extension for " + var6);
            return true;
         }

         String var10 = var3.classPrefix;
         var8 = var11.length();

         for(var11 = var1.substring(0, var1.length() - var8); var11.startsWith("../"); var11 = var11.substring(3)) {
            var8 = var10.lastIndexOf(46, var10.length() - 2);
            if(var8 < 0) {
               var3.syntaxError("cannot use relative filename \"" + var1 + "\" with simple prefix \"" + var10 + "\"");
               return false;
            }

            var10 = var10.substring(0, var8 + 1);
         }

         var1 = (var10 + var11).replace('/', '.');

         try {
            findAutoloadComments((LispReader)var7.getLexer(InPort.openFile(var6), var3.getMessages()), var1, var2, var3);
         } catch (Exception var9) {
            var3.syntaxError("error reading " + var6 + ": " + var9);
            return true;
         }
      }

      return true;
   }

   public boolean scanForDefinitions(Pair var1, Vector var2, ScopeExp var3, Translator var4) {
      boolean var7 = false;
      boolean var6;
      if(!(var1.getCdr() instanceof Pair)) {
         var6 = super.scanForDefinitions(var1, var2, var3, var4);
      } else {
         var1 = (Pair)var1.getCdr();
         if(!this.fromFile) {
            Object var5 = var1.getCar();
            if(var1.getCdr() instanceof Pair) {
               return process(var5, ((Pair)var1.getCdr()).getCar(), var2, var3, var4);
            }

            var4.syntaxError("invalid syntax for define-autoload");
            return false;
         }

         while(true) {
            if(var1.getCar() instanceof CharSequence) {
               var6 = var7;
               if(!this.scanFile(var1.getCar().toString(), var3, var4)) {
                  break;
               }

               Object var8 = var1.getCdr();
               if(var8 == LList.Empty) {
                  return true;
               }

               if(var8 instanceof Pair) {
                  var1 = (Pair)var1.getCdr();
                  continue;
               }
            }

            var4.syntaxError("invalid syntax for define-autoloads-from-file");
            return false;
         }
      }

      return var6;
   }
}
