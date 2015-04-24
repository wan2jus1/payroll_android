package kawa.lib.rnrs;

import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.UnicodeUtils;
import gnu.lists.Consumer;
import gnu.lists.FString;
import gnu.mapping.CallContext;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.WrongType;
import gnu.text.Char;
import java.util.Locale;
import kawa.lib.misc;

public class unicode extends ModuleBody {

   public static final unicode $instance = new unicode();
   static final SimpleSymbol Lit0 = (SimpleSymbol)(new SimpleSymbol("char-upcase")).readResolve();
   static final SimpleSymbol Lit1 = (SimpleSymbol)(new SimpleSymbol("char-downcase")).readResolve();
   static final SimpleSymbol Lit10 = (SimpleSymbol)(new SimpleSymbol("char-ci=?")).readResolve();
   static final SimpleSymbol Lit11 = (SimpleSymbol)(new SimpleSymbol("char-ci<?")).readResolve();
   static final SimpleSymbol Lit12 = (SimpleSymbol)(new SimpleSymbol("char-ci>?")).readResolve();
   static final SimpleSymbol Lit13 = (SimpleSymbol)(new SimpleSymbol("char-ci<=?")).readResolve();
   static final SimpleSymbol Lit14 = (SimpleSymbol)(new SimpleSymbol("char-ci>=?")).readResolve();
   static final SimpleSymbol Lit15 = (SimpleSymbol)(new SimpleSymbol("char-general-category")).readResolve();
   static final SimpleSymbol Lit16 = (SimpleSymbol)(new SimpleSymbol("string-upcase")).readResolve();
   static final SimpleSymbol Lit17 = (SimpleSymbol)(new SimpleSymbol("string-downcase")).readResolve();
   static final SimpleSymbol Lit18 = (SimpleSymbol)(new SimpleSymbol("string-titlecase")).readResolve();
   static final SimpleSymbol Lit19 = (SimpleSymbol)(new SimpleSymbol("string-foldcase")).readResolve();
   static final SimpleSymbol Lit2 = (SimpleSymbol)(new SimpleSymbol("char-titlecase")).readResolve();
   static final SimpleSymbol Lit20 = (SimpleSymbol)(new SimpleSymbol("string-ci=?")).readResolve();
   static final SimpleSymbol Lit21 = (SimpleSymbol)(new SimpleSymbol("string-ci<?")).readResolve();
   static final SimpleSymbol Lit22 = (SimpleSymbol)(new SimpleSymbol("string-ci>?")).readResolve();
   static final SimpleSymbol Lit23 = (SimpleSymbol)(new SimpleSymbol("string-ci<=?")).readResolve();
   static final SimpleSymbol Lit24 = (SimpleSymbol)(new SimpleSymbol("string-ci>=?")).readResolve();
   static final SimpleSymbol Lit25 = (SimpleSymbol)(new SimpleSymbol("string-normalize-nfd")).readResolve();
   static final SimpleSymbol Lit26 = (SimpleSymbol)(new SimpleSymbol("string-normalize-nfkd")).readResolve();
   static final SimpleSymbol Lit27 = (SimpleSymbol)(new SimpleSymbol("string-normalize-nfc")).readResolve();
   static final SimpleSymbol Lit28 = (SimpleSymbol)(new SimpleSymbol("string-normalize-nfkc")).readResolve();
   static final SimpleSymbol Lit3 = (SimpleSymbol)(new SimpleSymbol("char-alphabetic?")).readResolve();
   static final SimpleSymbol Lit4 = (SimpleSymbol)(new SimpleSymbol("char-numeric?")).readResolve();
   static final SimpleSymbol Lit5 = (SimpleSymbol)(new SimpleSymbol("char-whitespace?")).readResolve();
   static final SimpleSymbol Lit6 = (SimpleSymbol)(new SimpleSymbol("char-upper-case?")).readResolve();
   static final SimpleSymbol Lit7 = (SimpleSymbol)(new SimpleSymbol("char-lower-case?")).readResolve();
   static final SimpleSymbol Lit8 = (SimpleSymbol)(new SimpleSymbol("char-title-case?")).readResolve();
   static final SimpleSymbol Lit9 = (SimpleSymbol)(new SimpleSymbol("char-foldcase")).readResolve();
   public static final ModuleMethod char$Mnalphabetic$Qu;
   public static final ModuleMethod char$Mnci$Eq$Qu;
   public static final ModuleMethod char$Mnci$Gr$Eq$Qu;
   public static final ModuleMethod char$Mnci$Gr$Qu;
   public static final ModuleMethod char$Mnci$Ls$Eq$Qu;
   public static final ModuleMethod char$Mnci$Ls$Qu;
   public static final ModuleMethod char$Mndowncase;
   public static final ModuleMethod char$Mnfoldcase;
   public static final ModuleMethod char$Mngeneral$Mncategory;
   public static final ModuleMethod char$Mnlower$Mncase$Qu;
   public static final ModuleMethod char$Mnnumeric$Qu;
   public static final ModuleMethod char$Mntitle$Mncase$Qu;
   public static final ModuleMethod char$Mntitlecase;
   public static final ModuleMethod char$Mnupcase;
   public static final ModuleMethod char$Mnupper$Mncase$Qu;
   public static final ModuleMethod char$Mnwhitespace$Qu;
   public static final ModuleMethod string$Mnci$Eq$Qu;
   public static final ModuleMethod string$Mnci$Gr$Eq$Qu;
   public static final ModuleMethod string$Mnci$Gr$Qu;
   public static final ModuleMethod string$Mnci$Ls$Eq$Qu;
   public static final ModuleMethod string$Mnci$Ls$Qu;
   public static final ModuleMethod string$Mndowncase;
   public static final ModuleMethod string$Mnfoldcase;
   public static final ModuleMethod string$Mnnormalize$Mnnfc;
   public static final ModuleMethod string$Mnnormalize$Mnnfd;
   public static final ModuleMethod string$Mnnormalize$Mnnfkc;
   public static final ModuleMethod string$Mnnormalize$Mnnfkd;
   public static final ModuleMethod string$Mntitlecase;
   public static final ModuleMethod string$Mnupcase;


   static {
      unicode var0 = $instance;
      char$Mnupcase = new ModuleMethod(var0, 1, Lit0, 4097);
      char$Mndowncase = new ModuleMethod(var0, 2, Lit1, 4097);
      char$Mntitlecase = new ModuleMethod(var0, 3, Lit2, 4097);
      char$Mnalphabetic$Qu = new ModuleMethod(var0, 4, Lit3, 4097);
      char$Mnnumeric$Qu = new ModuleMethod(var0, 5, Lit4, 4097);
      char$Mnwhitespace$Qu = new ModuleMethod(var0, 6, Lit5, 4097);
      char$Mnupper$Mncase$Qu = new ModuleMethod(var0, 7, Lit6, 4097);
      char$Mnlower$Mncase$Qu = new ModuleMethod(var0, 8, Lit7, 4097);
      char$Mntitle$Mncase$Qu = new ModuleMethod(var0, 9, Lit8, 4097);
      char$Mnfoldcase = new ModuleMethod(var0, 10, Lit9, 4097);
      char$Mnci$Eq$Qu = new ModuleMethod(var0, 11, Lit10, 8194);
      char$Mnci$Ls$Qu = new ModuleMethod(var0, 12, Lit11, 8194);
      char$Mnci$Gr$Qu = new ModuleMethod(var0, 13, Lit12, 8194);
      char$Mnci$Ls$Eq$Qu = new ModuleMethod(var0, 14, Lit13, 8194);
      char$Mnci$Gr$Eq$Qu = new ModuleMethod(var0, 15, Lit14, 8194);
      char$Mngeneral$Mncategory = new ModuleMethod(var0, 16, Lit15, 4097);
      string$Mnupcase = new ModuleMethod(var0, 17, Lit16, 4097);
      string$Mndowncase = new ModuleMethod(var0, 18, Lit17, 4097);
      string$Mntitlecase = new ModuleMethod(var0, 19, Lit18, 4097);
      string$Mnfoldcase = new ModuleMethod(var0, 20, Lit19, 4097);
      string$Mnci$Eq$Qu = new ModuleMethod(var0, 21, Lit20, 8194);
      string$Mnci$Ls$Qu = new ModuleMethod(var0, 22, Lit21, 8194);
      string$Mnci$Gr$Qu = new ModuleMethod(var0, 23, Lit22, 8194);
      string$Mnci$Ls$Eq$Qu = new ModuleMethod(var0, 24, Lit23, 8194);
      string$Mnci$Gr$Eq$Qu = new ModuleMethod(var0, 25, Lit24, 8194);
      string$Mnnormalize$Mnnfd = new ModuleMethod(var0, 26, Lit25, 4097);
      string$Mnnormalize$Mnnfkd = new ModuleMethod(var0, 27, Lit26, 4097);
      string$Mnnormalize$Mnnfc = new ModuleMethod(var0, 28, Lit27, 4097);
      string$Mnnormalize$Mnnfkc = new ModuleMethod(var0, 29, Lit28, 4097);
      $instance.run();
   }

   public unicode() {
      ModuleInfo.register(this);
   }

   public static Char charDowncase(Char var0) {
      return Char.make(Character.toLowerCase(var0.intValue()));
   }

   public static Char charFoldcase(Char var0) {
      int var2 = var0.intValue();
      boolean var1;
      if(var2 == 304) {
         var1 = true;
      } else {
         var1 = false;
      }

      if(var1) {
         if(var1) {
            return var0;
         }
      } else if(var2 == 305) {
         return var0;
      }

      return Char.make(Character.toLowerCase(Character.toUpperCase(var2)));
   }

   public static Symbol charGeneralCategory(Char var0) {
      return UnicodeUtils.generalCategory(var0.intValue());
   }

   public static Char charTitlecase(Char var0) {
      return Char.make(Character.toTitleCase(var0.intValue()));
   }

   public static Char charUpcase(Char var0) {
      return Char.make(Character.toUpperCase(var0.intValue()));
   }

   public static boolean isCharAlphabetic(Char var0) {
      return Character.isLetter(var0.intValue());
   }

   public static boolean isCharCi$Eq(Char var0, Char var1) {
      return Character.toUpperCase(var0.intValue()) == Character.toUpperCase(var1.intValue());
   }

   public static boolean isCharCi$Gr(Char var0, Char var1) {
      return Character.toUpperCase(var0.intValue()) > Character.toUpperCase(var1.intValue());
   }

   public static boolean isCharCi$Gr$Eq(Char var0, Char var1) {
      return Character.toUpperCase(var0.intValue()) >= Character.toUpperCase(var1.intValue());
   }

   public static boolean isCharCi$Ls(Char var0, Char var1) {
      return Character.toUpperCase(var0.intValue()) < Character.toUpperCase(var1.intValue());
   }

   public static boolean isCharCi$Ls$Eq(Char var0, Char var1) {
      return Character.toUpperCase(var0.intValue()) <= Character.toUpperCase(var1.intValue());
   }

   public static boolean isCharLowerCase(Char var0) {
      return Character.isLowerCase(var0.intValue());
   }

   public static boolean isCharNumeric(Char var0) {
      return Character.isDigit(var0.intValue());
   }

   public static boolean isCharTitleCase(Char var0) {
      return Character.isTitleCase(var0.intValue());
   }

   public static boolean isCharUpperCase(Char var0) {
      return Character.isUpperCase(var0.intValue());
   }

   public static boolean isCharWhitespace(Char var0) {
      return UnicodeUtils.isWhitespace(var0.intValue());
   }

   public static boolean isStringCi$Eq(CharSequence var0, CharSequence var1) {
      return UnicodeUtils.foldCase(var0).equals(UnicodeUtils.foldCase(var1));
   }

   public static boolean isStringCi$Gr(CharSequence var0, CharSequence var1) {
      return UnicodeUtils.foldCase(var0).compareTo(UnicodeUtils.foldCase(var1)) > 0;
   }

   public static boolean isStringCi$Gr$Eq(CharSequence var0, CharSequence var1) {
      return UnicodeUtils.foldCase(var0).compareTo(UnicodeUtils.foldCase(var1)) >= 0;
   }

   public static boolean isStringCi$Ls(CharSequence var0, CharSequence var1) {
      return UnicodeUtils.foldCase(var0).compareTo(UnicodeUtils.foldCase(var1)) < 0;
   }

   public static boolean isStringCi$Ls$Eq(CharSequence var0, CharSequence var1) {
      return UnicodeUtils.foldCase(var0).compareTo(UnicodeUtils.foldCase(var1)) <= 0;
   }

   public static CharSequence stringDowncase(CharSequence var0) {
      return new FString(var0.toString().toLowerCase(Locale.ENGLISH));
   }

   public static CharSequence stringFoldcase(CharSequence var0) {
      return new FString(UnicodeUtils.foldCase(var0));
   }

   public static CharSequence stringNormalizeNfc(CharSequence var0) {
      return (CharSequence)misc.error$V("unicode string normalization not available", new Object[0]);
   }

   public static CharSequence stringNormalizeNfd(CharSequence var0) {
      return (CharSequence)misc.error$V("unicode string normalization not available", new Object[0]);
   }

   public static CharSequence stringNormalizeNfkc(CharSequence var0) {
      return (CharSequence)misc.error$V("unicode string normalization not available", new Object[0]);
   }

   public static CharSequence stringNormalizeNfkd(CharSequence var0) {
      return (CharSequence)misc.error$V("unicode string normalization not available", new Object[0]);
   }

   public static CharSequence stringTitlecase(CharSequence var0) {
      String var1;
      if(var0 == null) {
         var1 = null;
      } else {
         var1 = var0.toString();
      }

      return new FString(UnicodeUtils.capitalize(var1));
   }

   public static CharSequence stringUpcase(CharSequence var0) {
      return new FString(var0.toString().toUpperCase(Locale.ENGLISH));
   }

   public Object apply1(ModuleMethod var1, Object var2) {
      Char var23;
      CharSequence var22;
      switch(var1.selector) {
      case 1:
         try {
            var23 = (Char)var2;
         } catch (ClassCastException var21) {
            throw new WrongType(var21, "char-upcase", 1, var2);
         }

         return charUpcase(var23);
      case 2:
         try {
            var23 = (Char)var2;
         } catch (ClassCastException var20) {
            throw new WrongType(var20, "char-downcase", 1, var2);
         }

         return charDowncase(var23);
      case 3:
         try {
            var23 = (Char)var2;
         } catch (ClassCastException var19) {
            throw new WrongType(var19, "char-titlecase", 1, var2);
         }

         return charTitlecase(var23);
      case 4:
         try {
            var23 = (Char)var2;
         } catch (ClassCastException var18) {
            throw new WrongType(var18, "char-alphabetic?", 1, var2);
         }

         if(isCharAlphabetic(var23)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 5:
         try {
            var23 = (Char)var2;
         } catch (ClassCastException var17) {
            throw new WrongType(var17, "char-numeric?", 1, var2);
         }

         if(isCharNumeric(var23)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 6:
         try {
            var23 = (Char)var2;
         } catch (ClassCastException var16) {
            throw new WrongType(var16, "char-whitespace?", 1, var2);
         }

         if(isCharWhitespace(var23)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 7:
         try {
            var23 = (Char)var2;
         } catch (ClassCastException var15) {
            throw new WrongType(var15, "char-upper-case?", 1, var2);
         }

         if(isCharUpperCase(var23)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 8:
         try {
            var23 = (Char)var2;
         } catch (ClassCastException var14) {
            throw new WrongType(var14, "char-lower-case?", 1, var2);
         }

         if(isCharLowerCase(var23)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 9:
         try {
            var23 = (Char)var2;
         } catch (ClassCastException var13) {
            throw new WrongType(var13, "char-title-case?", 1, var2);
         }

         if(isCharTitleCase(var23)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 10:
         try {
            var23 = (Char)var2;
         } catch (ClassCastException var12) {
            throw new WrongType(var12, "char-foldcase", 1, var2);
         }

         return charFoldcase(var23);
      case 11:
      case 12:
      case 13:
      case 14:
      case 15:
      case 21:
      case 22:
      case 23:
      case 24:
      case 25:
      default:
         return super.apply1(var1, var2);
      case 16:
         try {
            var23 = (Char)var2;
         } catch (ClassCastException var11) {
            throw new WrongType(var11, "char-general-category", 1, var2);
         }

         return charGeneralCategory(var23);
      case 17:
         try {
            var22 = (CharSequence)var2;
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "string-upcase", 1, var2);
         }

         return stringUpcase(var22);
      case 18:
         try {
            var22 = (CharSequence)var2;
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "string-downcase", 1, var2);
         }

         return stringDowncase(var22);
      case 19:
         try {
            var22 = (CharSequence)var2;
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "string-titlecase", 1, var2);
         }

         return stringTitlecase(var22);
      case 20:
         try {
            var22 = (CharSequence)var2;
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "string-foldcase", 1, var2);
         }

         return stringFoldcase(var22);
      case 26:
         try {
            var22 = (CharSequence)var2;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "string-normalize-nfd", 1, var2);
         }

         return stringNormalizeNfd(var22);
      case 27:
         try {
            var22 = (CharSequence)var2;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "string-normalize-nfkd", 1, var2);
         }

         return stringNormalizeNfkd(var22);
      case 28:
         try {
            var22 = (CharSequence)var2;
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "string-normalize-nfc", 1, var2);
         }

         return stringNormalizeNfc(var22);
      case 29:
         try {
            var22 = (CharSequence)var2;
         } catch (ClassCastException var3) {
            throw new WrongType(var3, "string-normalize-nfkc", 1, var2);
         }

         return stringNormalizeNfkc(var22);
      }
   }

   public Object apply2(ModuleMethod var1, Object var2, Object var3) {
      CharSequence var25;
      CharSequence var24;
      Char var27;
      Char var26;
      switch(var1.selector) {
      case 11:
         try {
            var26 = (Char)var2;
         } catch (ClassCastException var23) {
            throw new WrongType(var23, "char-ci=?", 1, var2);
         }

         try {
            var27 = (Char)var3;
         } catch (ClassCastException var22) {
            throw new WrongType(var22, "char-ci=?", 2, var3);
         }

         if(isCharCi$Eq(var26, var27)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 12:
         try {
            var26 = (Char)var2;
         } catch (ClassCastException var21) {
            throw new WrongType(var21, "char-ci<?", 1, var2);
         }

         try {
            var27 = (Char)var3;
         } catch (ClassCastException var20) {
            throw new WrongType(var20, "char-ci<?", 2, var3);
         }

         if(isCharCi$Ls(var26, var27)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 13:
         try {
            var26 = (Char)var2;
         } catch (ClassCastException var19) {
            throw new WrongType(var19, "char-ci>?", 1, var2);
         }

         try {
            var27 = (Char)var3;
         } catch (ClassCastException var18) {
            throw new WrongType(var18, "char-ci>?", 2, var3);
         }

         if(isCharCi$Gr(var26, var27)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 14:
         try {
            var26 = (Char)var2;
         } catch (ClassCastException var17) {
            throw new WrongType(var17, "char-ci<=?", 1, var2);
         }

         try {
            var27 = (Char)var3;
         } catch (ClassCastException var16) {
            throw new WrongType(var16, "char-ci<=?", 2, var3);
         }

         if(isCharCi$Ls$Eq(var26, var27)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 15:
         try {
            var26 = (Char)var2;
         } catch (ClassCastException var15) {
            throw new WrongType(var15, "char-ci>=?", 1, var2);
         }

         try {
            var27 = (Char)var3;
         } catch (ClassCastException var14) {
            throw new WrongType(var14, "char-ci>=?", 2, var3);
         }

         if(isCharCi$Gr$Eq(var26, var27)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 16:
      case 17:
      case 18:
      case 19:
      case 20:
      default:
         return super.apply2(var1, var2, var3);
      case 21:
         try {
            var24 = (CharSequence)var2;
         } catch (ClassCastException var13) {
            throw new WrongType(var13, "string-ci=?", 1, var2);
         }

         try {
            var25 = (CharSequence)var3;
         } catch (ClassCastException var12) {
            throw new WrongType(var12, "string-ci=?", 2, var3);
         }

         if(isStringCi$Eq(var24, var25)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 22:
         try {
            var24 = (CharSequence)var2;
         } catch (ClassCastException var11) {
            throw new WrongType(var11, "string-ci<?", 1, var2);
         }

         try {
            var25 = (CharSequence)var3;
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "string-ci<?", 2, var3);
         }

         if(isStringCi$Ls(var24, var25)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 23:
         try {
            var24 = (CharSequence)var2;
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "string-ci>?", 1, var2);
         }

         try {
            var25 = (CharSequence)var3;
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "string-ci>?", 2, var3);
         }

         if(isStringCi$Gr(var24, var25)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 24:
         try {
            var24 = (CharSequence)var2;
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "string-ci<=?", 1, var2);
         }

         try {
            var25 = (CharSequence)var3;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "string-ci<=?", 2, var3);
         }

         if(isStringCi$Ls$Eq(var24, var25)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 25:
         try {
            var24 = (CharSequence)var2;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "string-ci>=?", 1, var2);
         }

         try {
            var25 = (CharSequence)var3;
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "string-ci>=?", 2, var3);
         }

         return isStringCi$Gr$Eq(var24, var25)?Boolean.TRUE:Boolean.FALSE;
      }
   }

   public int match1(ModuleMethod var1, Object var2, CallContext var3) {
      switch(var1.selector) {
      case 1:
         if(!(var2 instanceof Char)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 2:
         if(!(var2 instanceof Char)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 3:
         if(!(var2 instanceof Char)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 4:
         if(!(var2 instanceof Char)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 5:
         if(!(var2 instanceof Char)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 6:
         if(!(var2 instanceof Char)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 7:
         if(!(var2 instanceof Char)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 8:
         if(!(var2 instanceof Char)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 9:
         if(!(var2 instanceof Char)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 10:
         if(!(var2 instanceof Char)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 11:
      case 12:
      case 13:
      case 14:
      case 15:
      case 21:
      case 22:
      case 23:
      case 24:
      case 25:
      default:
         return super.match1(var1, var2, var3);
      case 16:
         if(!(var2 instanceof Char)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 17:
         if(var2 instanceof CharSequence) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 18:
         if(var2 instanceof CharSequence) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 19:
         if(var2 instanceof CharSequence) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 20:
         if(var2 instanceof CharSequence) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 26:
         if(var2 instanceof CharSequence) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 27:
         if(var2 instanceof CharSequence) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 28:
         if(var2 instanceof CharSequence) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 29:
         if(var2 instanceof CharSequence) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return -786431;
         }
      }
   }

   public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
      switch(var1.selector) {
      case 11:
         if(!(var2 instanceof Char)) {
            return -786431;
         } else {
            var4.value1 = var2;
            if(!(var3 instanceof Char)) {
               return -786430;
            }

            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }
      case 12:
         if(!(var2 instanceof Char)) {
            return -786431;
         } else {
            var4.value1 = var2;
            if(!(var3 instanceof Char)) {
               return -786430;
            }

            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }
      case 13:
         if(!(var2 instanceof Char)) {
            return -786431;
         } else {
            var4.value1 = var2;
            if(!(var3 instanceof Char)) {
               return -786430;
            }

            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }
      case 14:
         if(!(var2 instanceof Char)) {
            return -786431;
         } else {
            var4.value1 = var2;
            if(!(var3 instanceof Char)) {
               return -786430;
            }

            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }
      case 15:
         if(!(var2 instanceof Char)) {
            return -786431;
         } else {
            var4.value1 = var2;
            if(!(var3 instanceof Char)) {
               return -786430;
            }

            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }
      case 16:
      case 17:
      case 18:
      case 19:
      case 20:
      default:
         return super.match2(var1, var2, var3, var4);
      case 21:
         if(var2 instanceof CharSequence) {
            var4.value1 = var2;
            if(var3 instanceof CharSequence) {
               var4.value2 = var3;
               var4.proc = var1;
               var4.pc = 2;
               return 0;
            }

            return -786430;
         }

         return -786431;
      case 22:
         if(var2 instanceof CharSequence) {
            var4.value1 = var2;
            if(var3 instanceof CharSequence) {
               var4.value2 = var3;
               var4.proc = var1;
               var4.pc = 2;
               return 0;
            }

            return -786430;
         }

         return -786431;
      case 23:
         if(var2 instanceof CharSequence) {
            var4.value1 = var2;
            if(var3 instanceof CharSequence) {
               var4.value2 = var3;
               var4.proc = var1;
               var4.pc = 2;
               return 0;
            }

            return -786430;
         }

         return -786431;
      case 24:
         if(var2 instanceof CharSequence) {
            var4.value1 = var2;
            if(var3 instanceof CharSequence) {
               var4.value2 = var3;
               var4.proc = var1;
               var4.pc = 2;
               return 0;
            }

            return -786430;
         }

         return -786431;
      case 25:
         if(var2 instanceof CharSequence) {
            var4.value1 = var2;
            if(var3 instanceof CharSequence) {
               var4.value2 = var3;
               var4.proc = var1;
               var4.pc = 2;
               return 0;
            } else {
               return -786430;
            }
         } else {
            return -786431;
         }
      }
   }

   public final void run(CallContext var1) {
      Consumer var2 = var1.consumer;
   }
}
