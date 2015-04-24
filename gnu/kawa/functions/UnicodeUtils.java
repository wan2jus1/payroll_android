package gnu.kawa.functions;

import gnu.mapping.Namespace;
import gnu.mapping.Symbol;
import java.text.BreakIterator;

public class UnicodeUtils {

   static final Symbol Cc;
   static final Symbol Cf;
   static final Symbol Cn;
   static final Symbol Co;
   static final Symbol Cs;
   static final Symbol Ll;
   static final Symbol Lm;
   static final Symbol Lo;
   static final Symbol Lt;
   static final Symbol Lu;
   static final Symbol Mc;
   static final Symbol Me;
   static final Symbol Mn;
   static final Symbol Nd;
   static final Symbol Nl;
   static final Symbol No;
   static final Symbol Pc;
   static final Symbol Pd;
   static final Symbol Pe;
   static final Symbol Pf;
   static final Symbol Pi;
   static final Symbol Po;
   static final Symbol Ps;
   static final Symbol Sc;
   static final Symbol Sk;
   static final Symbol Sm;
   static final Symbol So;
   static final Symbol Zl;
   static final Symbol Zp;
   static final Symbol Zs;


   static {
      Namespace var0 = Namespace.EmptyNamespace;
      Mc = var0.getSymbol("Mc");
      Pc = var0.getSymbol("Pc");
      Cc = var0.getSymbol("Cc");
      Sc = var0.getSymbol("Sc");
      Pd = var0.getSymbol("Pd");
      Nd = var0.getSymbol("Nd");
      Me = var0.getSymbol("Me");
      Pe = var0.getSymbol("Pe");
      Pf = var0.getSymbol("Pf");
      Cf = var0.getSymbol("Cf");
      Pi = var0.getSymbol("Pi");
      Nl = var0.getSymbol("Nl");
      Zl = var0.getSymbol("Zl");
      Ll = var0.getSymbol("Ll");
      Sm = var0.getSymbol("Sm");
      Lm = var0.getSymbol("Lm");
      Sk = var0.getSymbol("Sk");
      Mn = var0.getSymbol("Mn");
      Lo = var0.getSymbol("Lo");
      No = var0.getSymbol("No");
      Po = var0.getSymbol("Po");
      So = var0.getSymbol("So");
      Zp = var0.getSymbol("Zp");
      Co = var0.getSymbol("Co");
      Zs = var0.getSymbol("Zs");
      Ps = var0.getSymbol("Ps");
      Cs = var0.getSymbol("Cs");
      Lt = var0.getSymbol("Lt");
      Cn = var0.getSymbol("Cn");
      Lu = var0.getSymbol("Lu");
   }

   public static String capitalize(String var0) {
      StringBuilder var1 = new StringBuilder();
      BreakIterator var2 = BreakIterator.getWordInstance();
      var2.setText(var0);
      int var4 = var2.first();
      int var3 = var2.next();

      while(var3 != -1) {
         boolean var7 = false;
         int var5 = var4;

         while(true) {
            boolean var6 = var7;
            if(var5 < var3) {
               if(!Character.isLetter(var0.codePointAt(var5))) {
                  ++var5;
                  continue;
               }

               var6 = true;
            }

            if(!var6) {
               var1.append(var0, var4, var3);
            } else {
               var1.append(Character.toTitleCase(var0.charAt(var4)));
               var1.append(var0.substring(var4 + 1, var3).toLowerCase());
            }

            var4 = var3;
            var3 = var2.next();
            break;
         }
      }

      return var1.toString();
   }

   public static String foldCase(CharSequence var0) {
      int var9 = var0.length();
      String var2;
      if(var9 == 0) {
         var2 = "";
      } else {
         StringBuilder var10 = null;
         int var6 = 0;
         int var5 = 0;

         while(true) {
            int var4;
            if(var5 == var9) {
               var4 = -1;
            } else {
               var4 = var0.charAt(var5);
            }

            boolean var7;
            if(var4 != 931 && var4 != 963 && var4 != 962) {
               var7 = false;
            } else {
               var7 = true;
            }

            StringBuilder var1;
            int var8;
            label64: {
               if(var4 >= 0 && var4 != 304 && var4 != 305) {
                  var1 = var10;
                  var8 = var6;
                  if(!var7) {
                     break label64;
                  }
               }

               var1 = var10;
               if(var10 == null) {
                  var1 = var10;
                  if(var4 >= 0) {
                     var1 = new StringBuilder();
                  }
               }

               if(var5 > var6) {
                  String var3 = var0.subSequence(var6, var5).toString().toUpperCase().toLowerCase();
                  var2 = var3;
                  if(var1 == null) {
                     break;
                  }

                  var1.append(var3);
               }

               if(var4 < 0) {
                  return var1.toString();
               }

               if(var7) {
                  var4 = 963;
               }

               var1.append((char)var4);
               var8 = var5 + 1;
            }

            ++var5;
            var10 = var1;
            var6 = var8;
         }
      }

      return var2;
   }

   public static Symbol generalCategory(int var0) {
      switch(Character.getType(var0)) {
      case 1:
         return Lu;
      case 2:
         return Ll;
      case 3:
         return Lt;
      case 4:
         return Lm;
      case 5:
         return Lo;
      case 6:
         return Mn;
      case 7:
         return Me;
      case 8:
         return Mc;
      case 9:
         return Nd;
      case 10:
         return Nl;
      case 11:
         return No;
      case 12:
         return Zs;
      case 13:
         return Zl;
      case 14:
         return Zp;
      case 15:
         return Cc;
      case 16:
         return Cf;
      case 17:
      default:
         return Cn;
      case 18:
         return Co;
      case 19:
         return Cs;
      case 20:
         return Pd;
      case 21:
         return Ps;
      case 22:
         return Pe;
      case 23:
         return Pc;
      case 24:
         return Po;
      case 25:
         return Sm;
      case 26:
         return Sc;
      case 27:
         return Sk;
      case 28:
         return So;
      case 29:
         return Pi;
      case 30:
         return Pf;
      }
   }

   public static boolean isWhitespace(int var0) {
      boolean var2 = false;
      boolean var1;
      if(var0 != 32 && (var0 < 9 || var0 > 13)) {
         var1 = var2;
         if(var0 >= 133) {
            if(var0 == 133 || var0 == 160 || var0 == 5760 || var0 == 6158) {
               return true;
            }

            var1 = var2;
            if(var0 >= 8192) {
               var1 = var2;
               if(var0 <= 12288) {
                  if(var0 > 8202 && var0 != 8232 && var0 != 8233 && var0 != 8239 && var0 != 8287) {
                     var1 = var2;
                     if(var0 != 12288) {
                        return var1;
                     }
                  }

                  return true;
               }
            }
         }
      } else {
         var1 = true;
      }

      return var1;
   }
}
