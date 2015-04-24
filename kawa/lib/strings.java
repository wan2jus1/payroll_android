package kawa.lib;

import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.lists.CharSeq;
import gnu.lists.Consumer;
import gnu.lists.FString;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.Strings;
import gnu.mapping.CallContext;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.text.Char;
import kawa.lib.lists;

public class strings extends ModuleBody {

   public static final strings $instance = new strings();
   public static final ModuleMethod $make$string$;
   static final Char Lit0 = Char.make(32);
   static final SimpleSymbol Lit1 = (SimpleSymbol)(new SimpleSymbol("string?")).readResolve();
   static final SimpleSymbol Lit10 = (SimpleSymbol)(new SimpleSymbol("string<=?")).readResolve();
   static final SimpleSymbol Lit11 = (SimpleSymbol)(new SimpleSymbol("string>=?")).readResolve();
   static final SimpleSymbol Lit12 = (SimpleSymbol)(new SimpleSymbol("substring")).readResolve();
   static final SimpleSymbol Lit13 = (SimpleSymbol)(new SimpleSymbol("string->list")).readResolve();
   static final SimpleSymbol Lit14 = (SimpleSymbol)(new SimpleSymbol("list->string")).readResolve();
   static final SimpleSymbol Lit15 = (SimpleSymbol)(new SimpleSymbol("string-copy")).readResolve();
   static final SimpleSymbol Lit16 = (SimpleSymbol)(new SimpleSymbol("string-fill!")).readResolve();
   static final SimpleSymbol Lit17 = (SimpleSymbol)(new SimpleSymbol("string-upcase!")).readResolve();
   static final SimpleSymbol Lit18 = (SimpleSymbol)(new SimpleSymbol("string-downcase!")).readResolve();
   static final SimpleSymbol Lit19 = (SimpleSymbol)(new SimpleSymbol("string-capitalize!")).readResolve();
   static final SimpleSymbol Lit2 = (SimpleSymbol)(new SimpleSymbol("make-string")).readResolve();
   static final SimpleSymbol Lit20 = (SimpleSymbol)(new SimpleSymbol("string-capitalize")).readResolve();
   static final SimpleSymbol Lit21 = (SimpleSymbol)(new SimpleSymbol("string-append")).readResolve();
   static final SimpleSymbol Lit22 = (SimpleSymbol)(new SimpleSymbol("string-append/shared")).readResolve();
   static final SimpleSymbol Lit3 = (SimpleSymbol)(new SimpleSymbol("$make$string$")).readResolve();
   static final SimpleSymbol Lit4 = (SimpleSymbol)(new SimpleSymbol("string-length")).readResolve();
   static final SimpleSymbol Lit5 = (SimpleSymbol)(new SimpleSymbol("string-ref")).readResolve();
   static final SimpleSymbol Lit6 = (SimpleSymbol)(new SimpleSymbol("string-set!")).readResolve();
   static final SimpleSymbol Lit7 = (SimpleSymbol)(new SimpleSymbol("string=?")).readResolve();
   static final SimpleSymbol Lit8 = (SimpleSymbol)(new SimpleSymbol("string<?")).readResolve();
   static final SimpleSymbol Lit9 = (SimpleSymbol)(new SimpleSymbol("string>?")).readResolve();
   public static final ModuleMethod list$Mn$Grstring;
   public static final ModuleMethod make$Mnstring;
   public static final ModuleMethod string$Eq$Qu;
   public static final ModuleMethod string$Gr$Eq$Qu;
   public static final ModuleMethod string$Gr$Qu;
   public static final ModuleMethod string$Ls$Eq$Qu;
   public static final ModuleMethod string$Ls$Qu;
   public static final ModuleMethod string$Mn$Grlist;
   public static final ModuleMethod string$Mnappend;
   public static final ModuleMethod string$Mnappend$Slshared;
   public static final ModuleMethod string$Mncapitalize;
   public static final ModuleMethod string$Mncapitalize$Ex;
   public static final ModuleMethod string$Mncopy;
   public static final ModuleMethod string$Mndowncase$Ex;
   public static final ModuleMethod string$Mnfill$Ex;
   public static final ModuleMethod string$Mnlength;
   public static final ModuleMethod string$Mnref;
   public static final ModuleMethod string$Mnset$Ex;
   public static final ModuleMethod string$Mnupcase$Ex;
   public static final ModuleMethod string$Qu;
   public static final ModuleMethod substring;


   public static CharSequence $make$string$(Object ... var0) {
      int var3 = var0.length;
      FString var1 = new FString(var3);

      for(int var2 = 0; var2 < var3; ++var2) {
         var1.setCharAt(var2, ((Char)var0[var2]).charValue());
      }

      return var1;
   }

   static {
      strings var0 = $instance;
      string$Qu = new ModuleMethod(var0, 1, Lit1, 4097);
      make$Mnstring = new ModuleMethod(var0, 2, Lit2, 8193);
      $make$string$ = new ModuleMethod(var0, 4, Lit3, -4096);
      string$Mnlength = new ModuleMethod(var0, 5, Lit4, 4097);
      string$Mnref = new ModuleMethod(var0, 6, Lit5, 8194);
      string$Mnset$Ex = new ModuleMethod(var0, 7, Lit6, 12291);
      string$Eq$Qu = new ModuleMethod(var0, 8, Lit7, 8194);
      string$Ls$Qu = new ModuleMethod(var0, 9, Lit8, 8194);
      string$Gr$Qu = new ModuleMethod(var0, 10, Lit9, 8194);
      string$Ls$Eq$Qu = new ModuleMethod(var0, 11, Lit10, 8194);
      string$Gr$Eq$Qu = new ModuleMethod(var0, 12, Lit11, 8194);
      substring = new ModuleMethod(var0, 13, Lit12, 12291);
      string$Mn$Grlist = new ModuleMethod(var0, 14, Lit13, 4097);
      list$Mn$Grstring = new ModuleMethod(var0, 15, Lit14, 4097);
      string$Mncopy = new ModuleMethod(var0, 16, Lit15, 4097);
      string$Mnfill$Ex = new ModuleMethod(var0, 17, Lit16, 8194);
      string$Mnupcase$Ex = new ModuleMethod(var0, 18, Lit17, 4097);
      string$Mndowncase$Ex = new ModuleMethod(var0, 19, Lit18, 4097);
      string$Mncapitalize$Ex = new ModuleMethod(var0, 20, Lit19, 4097);
      string$Mncapitalize = new ModuleMethod(var0, 21, Lit20, 4097);
      string$Mnappend = new ModuleMethod(var0, 22, Lit21, -4096);
      string$Mnappend$Slshared = new ModuleMethod(var0, 23, Lit22, -4096);
      $instance.run();
   }

   public strings() {
      ModuleInfo.register(this);
   }

   public static boolean isString(Object var0) {
      return var0 instanceof CharSequence;
   }

   public static boolean isString$Eq(Object var0, Object var1) {
      return var0.toString().equals(var1.toString());
   }

   public static boolean isString$Gr(Object var0, Object var1) {
      return var0.toString().compareTo(var1.toString()) > 0;
   }

   public static boolean isString$Gr$Eq(Object var0, Object var1) {
      return var0.toString().compareTo(var1.toString()) >= 0;
   }

   public static boolean isString$Ls(Object var0, Object var1) {
      return var0.toString().compareTo(var1.toString()) < 0;
   }

   public static boolean isString$Ls$Eq(Object var0, Object var1) {
      return var0.toString().compareTo(var1.toString()) <= 0;
   }

   public static CharSequence list$To$String(LList var0) {
      int var6 = lists.length(var0);
      FString var2 = new FString(var6);

      for(int var5 = 0; var5 < var6; ++var5) {
         Pair var3;
         try {
            var3 = (Pair)var0;
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "pair", -2, var0);
         }

         CharSeq var4;
         try {
            var4 = (CharSeq)var2;
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "string-set!", 0, var2);
         }

         Object var11 = var3.getCar();

         char var1;
         try {
            var1 = ((Char)var11).charValue();
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "string-set!", 2, var11);
         }

         stringSet$Ex(var4, var5, var1);
         Object var12 = var3.getCdr();

         try {
            var0 = (LList)var12;
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "list", -2, var12);
         }
      }

      return var2;
   }

   public static CharSequence makeString(int var0) {
      return makeString(var0, Lit0);
   }

   public static CharSequence makeString(int var0, Object var1) {
      char var2;
      try {
         var2 = ((Char)var1).charValue();
      } catch (ClassCastException var4) {
         throw new WrongType(var4, "gnu.lists.FString.<init>(int,char)", 2, var1);
      }

      return new FString(var0, var2);
   }

   public static LList string$To$List(CharSequence var0) {
      Object var1 = LList.Empty;
      int var2 = stringLength(var0);

      while(true) {
         --var2;
         if(var2 < 0) {
            return (LList)var1;
         }

         var1 = new Pair(Char.make(stringRef(var0, var2)), var1);
      }
   }

   public static FString stringAppend(Object ... var0) {
      FString var1 = new FString();
      var1.addAllStrings(var0, 0);
      return var1;
   }

   public static CharSequence stringAppend$SlShared(Object ... var0) {
      if(var0.length == 0) {
         return new FString();
      } else {
         Object var2 = var0[0];
         FString var1;
         if(var2 instanceof FString) {
            try {
               var1 = (FString)var2;
            } catch (ClassCastException var4) {
               throw new WrongType(var4, "fstr", -2, var2);
            }
         } else {
            CharSequence var5;
            try {
               var5 = (CharSequence)var2;
            } catch (ClassCastException var3) {
               throw new WrongType(var3, "string-copy", 0, var2);
            }

            var1 = stringCopy(var5);
         }

         var1.addAllStrings(var0, 1);
         return var1;
      }
   }

   public static CharSequence stringCapitalize(CharSequence var0) {
      FString var1 = stringCopy(var0);
      Strings.makeCapitalize(var1);
      return var1;
   }

   public static CharSequence stringCapitalize$Ex(CharSeq var0) {
      Strings.makeCapitalize(var0);
      return var0;
   }

   public static FString stringCopy(CharSequence var0) {
      return new FString(var0);
   }

   public static CharSequence stringDowncase$Ex(CharSeq var0) {
      Strings.makeLowerCase(var0);
      return var0;
   }

   public static void stringFill$Ex(CharSeq var0, char var1) {
      var0.fill(var1);
   }

   public static int stringLength(CharSequence var0) {
      return var0.length();
   }

   public static char stringRef(CharSequence var0, int var1) {
      return var0.charAt(var1);
   }

   public static void stringSet$Ex(CharSeq var0, int var1, char var2) {
      var0.setCharAt(var1, var2);
   }

   public static CharSequence stringUpcase$Ex(CharSeq var0) {
      Strings.makeUpperCase(var0);
      return var0;
   }

   public static CharSequence substring(CharSequence var0, int var1, int var2) {
      return new FString(var0, var1, var2 - var1);
   }

   public Object apply1(ModuleMethod var1, Object var2) {
      CharSequence var13;
      CharSeq var14;
      switch(var1.selector) {
      case 1:
         if(isString(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 2:
         int var3;
         try {
            var3 = ((Number)var2).intValue();
         } catch (ClassCastException var12) {
            throw new WrongType(var12, "make-string", 1, var2);
         }

         return makeString(var3);
      case 3:
      case 4:
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      case 11:
      case 12:
      case 13:
      case 17:
      default:
         return super.apply1(var1, var2);
      case 5:
         try {
            var13 = (CharSequence)var2;
         } catch (ClassCastException var11) {
            throw new WrongType(var11, "string-length", 1, var2);
         }

         return Integer.valueOf(stringLength(var13));
      case 14:
         try {
            var13 = (CharSequence)var2;
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "string->list", 1, var2);
         }

         return string$To$List(var13);
      case 15:
         LList var15;
         try {
            var15 = (LList)var2;
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "list->string", 1, var2);
         }

         return list$To$String(var15);
      case 16:
         try {
            var13 = (CharSequence)var2;
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "string-copy", 1, var2);
         }

         return stringCopy(var13);
      case 18:
         try {
            var14 = (CharSeq)var2;
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "string-upcase!", 1, var2);
         }

         return stringUpcase$Ex(var14);
      case 19:
         try {
            var14 = (CharSeq)var2;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "string-downcase!", 1, var2);
         }

         return stringDowncase$Ex(var14);
      case 20:
         try {
            var14 = (CharSeq)var2;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "string-capitalize!", 1, var2);
         }

         return stringCapitalize$Ex(var14);
      case 21:
         try {
            var13 = (CharSequence)var2;
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "string-capitalize", 1, var2);
         }

         return stringCapitalize(var13);
      }
   }

   public Object apply2(ModuleMethod var1, Object var2, Object var3) {
      int var5;
      switch(var1.selector) {
      case 2:
         try {
            var5 = ((Number)var2).intValue();
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "make-string", 1, var2);
         }

         return makeString(var5, var3);
      case 3:
      case 4:
      case 5:
      case 7:
      case 13:
      case 14:
      case 15:
      case 16:
      default:
         return super.apply2(var1, var2, var3);
      case 6:
         CharSequence var12;
         try {
            var12 = (CharSequence)var2;
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "string-ref", 1, var2);
         }

         try {
            var5 = ((Number)var3).intValue();
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "string-ref", 2, var3);
         }

         return Char.make(stringRef(var12, var5));
      case 8:
         if(isString$Eq(var2, var3)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 9:
         if(isString$Ls(var2, var3)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 10:
         if(isString$Gr(var2, var3)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 11:
         if(isString$Ls$Eq(var2, var3)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 12:
         if(isString$Gr$Eq(var2, var3)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 17:
         CharSeq var11;
         try {
            var11 = (CharSeq)var2;
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "string-fill!", 1, var2);
         }

         char var4;
         try {
            var4 = ((Char)var3).charValue();
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "string-fill!", 2, var3);
         }

         stringFill$Ex(var11, var4);
         return Values.empty;
      }
   }

   public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
      int var6;
      switch(var1.selector) {
      case 7:
         CharSeq var15;
         try {
            var15 = (CharSeq)var2;
         } catch (ClassCastException var13) {
            throw new WrongType(var13, "string-set!", 1, var2);
         }

         try {
            var6 = ((Number)var3).intValue();
         } catch (ClassCastException var12) {
            throw new WrongType(var12, "string-set!", 2, var3);
         }

         char var5;
         try {
            var5 = ((Char)var4).charValue();
         } catch (ClassCastException var11) {
            throw new WrongType(var11, "string-set!", 3, var4);
         }

         stringSet$Ex(var15, var6, var5);
         return Values.empty;
      case 13:
         CharSequence var14;
         try {
            var14 = (CharSequence)var2;
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "substring", 1, var2);
         }

         try {
            var6 = ((Number)var3).intValue();
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "substring", 2, var3);
         }

         int var7;
         try {
            var7 = ((Number)var4).intValue();
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "substring", 3, var4);
         }

         return substring(var14, var6, var7);
      default:
         return super.apply3(var1, var2, var3, var4);
      }
   }

   public Object applyN(ModuleMethod var1, Object[] var2) {
      switch(var1.selector) {
      case 4:
         return $make$string$(var2);
      case 22:
         return stringAppend(var2);
      case 23:
         return stringAppend$SlShared(var2);
      default:
         return super.applyN(var1, var2);
      }
   }

   public int match1(ModuleMethod var1, Object var2, CallContext var3) {
      switch(var1.selector) {
      case 1:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 2:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 3:
      case 4:
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      case 11:
      case 12:
      case 13:
      case 17:
      default:
         return super.match1(var1, var2, var3);
      case 5:
         if(var2 instanceof CharSequence) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 14:
         if(var2 instanceof CharSequence) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 15:
         if(var2 instanceof LList) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 16:
         if(var2 instanceof CharSequence) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 18:
         if(!(var2 instanceof CharSeq)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 19:
         if(!(var2 instanceof CharSeq)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 20:
         if(!(var2 instanceof CharSeq)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 21:
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
      int var5 = -786431;
      switch(var1.selector) {
      case 2:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 3:
      case 4:
      case 5:
      case 7:
      case 13:
      case 14:
      case 15:
      case 16:
      default:
         var5 = super.match2(var1, var2, var3, var4);
         break;
      case 6:
         if(var2 instanceof CharSequence) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }
         break;
      case 8:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 9:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 10:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 11:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 12:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 17:
         if(var2 instanceof CharSeq) {
            var4.value1 = var2;
            if(var3 instanceof Char) {
               var4.value2 = var3;
               var4.proc = var1;
               var4.pc = 2;
               return 0;
            }

            return -786430;
         }
      }

      return var5;
   }

   public int match3(ModuleMethod var1, Object var2, Object var3, Object var4, CallContext var5) {
      switch(var1.selector) {
      case 7:
         if(!(var2 instanceof CharSeq)) {
            return -786431;
         } else {
            var5.value1 = var2;
            var5.value2 = var3;
            if(var4 instanceof Char) {
               var5.value3 = var4;
               var5.proc = var1;
               var5.pc = 3;
               return 0;
            }

            return -786429;
         }
      case 13:
         if(var2 instanceof CharSequence) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         }

         return -786431;
      default:
         return super.match3(var1, var2, var3, var4, var5);
      }
   }

   public int matchN(ModuleMethod var1, Object[] var2, CallContext var3) {
      switch(var1.selector) {
      case 4:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 22:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 23:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      default:
         return super.matchN(var1, var2, var3);
      }
   }

   public final void run(CallContext var1) {
      Consumer var2 = var1.consumer;
   }
}
