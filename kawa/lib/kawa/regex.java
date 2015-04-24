package kawa.lib.kawa;

import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.mapping.CallContext;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.WrongType;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.standard.Scheme;

public class regex extends ModuleBody {

   public static final regex $instance = new regex();
   static final SimpleSymbol Lit0 = (SimpleSymbol)(new SimpleSymbol("loop")).readResolve();
   static final SimpleSymbol Lit1 = (SimpleSymbol)(new SimpleSymbol("regex-quote")).readResolve();
   static final SimpleSymbol Lit2 = (SimpleSymbol)(new SimpleSymbol("regex-match?")).readResolve();
   static final SimpleSymbol Lit3 = (SimpleSymbol)(new SimpleSymbol("regex-match")).readResolve();
   static final SimpleSymbol Lit4 = (SimpleSymbol)(new SimpleSymbol("regex-match-positions")).readResolve();
   static final SimpleSymbol Lit5 = (SimpleSymbol)(new SimpleSymbol("regex-split")).readResolve();
   static final SimpleSymbol Lit6 = (SimpleSymbol)(new SimpleSymbol("regex-replace")).readResolve();
   static final SimpleSymbol Lit7 = (SimpleSymbol)(new SimpleSymbol("regex-replace*")).readResolve();
   public static final ModuleMethod regex$Mnmatch;
   public static final ModuleMethod regex$Mnmatch$Mnpositions;
   public static final ModuleMethod regex$Mnmatch$Qu;
   public static final ModuleMethod regex$Mnquote;
   public static final ModuleMethod regex$Mnreplace;
   public static final ModuleMethod regex$Mnreplace$St;
   public static final ModuleMethod regex$Mnsplit;


   static {
      regex var0 = $instance;
      regex$Mnquote = new ModuleMethod(var0, 2, Lit1, 4097);
      regex$Mnmatch$Qu = new ModuleMethod(var0, 3, Lit2, 16386);
      regex$Mnmatch = new ModuleMethod(var0, 6, Lit3, 16386);
      regex$Mnmatch$Mnpositions = new ModuleMethod(var0, 9, Lit4, 16386);
      regex$Mnsplit = new ModuleMethod(var0, 12, Lit5, 8194);
      regex$Mnreplace = new ModuleMethod(var0, 13, Lit6, 12291);
      regex$Mnreplace$St = new ModuleMethod(var0, 14, Lit7, 12291);
      $instance.run();
   }

   public regex() {
      ModuleInfo.register(this);
   }

   public static boolean isRegexMatch(Object var0, CharSequence var1) {
      return isRegexMatch(var0, var1, 0);
   }

   public static boolean isRegexMatch(Object var0, CharSequence var1, int var2) {
      return isRegexMatch(var0, var1, var2, var1.length());
   }

   public static boolean isRegexMatch(Object var0, CharSequence var1, int var2, int var3) {
      Pattern var6;
      if(var0 instanceof Pattern) {
         Pattern var4;
         try {
            var4 = (Pattern)var0;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "rex", -2, var0);
         }

         var6 = var4;
      } else {
         var6 = Pattern.compile(var0.toString());
      }

      Matcher var7 = var6.matcher(var1);
      var7.region(var2, var3);
      return var7.find();
   }

   public static Object regexMatch(Object var0, CharSequence var1) {
      return regexMatch(var0, var1, 0);
   }

   public static Object regexMatch(Object var0, CharSequence var1, int var2) {
      return regexMatch(var0, var1, var2, var1.length());
   }

   public static Object regexMatch(Object var0, CharSequence var1, int var2, int var3) {
      Pattern var7;
      if(var0 instanceof Pattern) {
         Pattern var4;
         try {
            var4 = (Pattern)var0;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "rex", -2, var0);
         }

         var7 = var4;
      } else {
         var7 = Pattern.compile(var0.toString());
      }

      Matcher var5 = var7.matcher(var1);
      var5.region(var2, var3);
      if(var5.find()) {
         var2 = var5.groupCount();

         for(var0 = LList.Empty; var2 >= 0; --var2) {
            var3 = var5.start(var2);
            Object var8;
            if(var3 < 0) {
               var8 = Boolean.FALSE;
            } else {
               var8 = var1.subSequence(var3, var5.end(var2));
            }

            var0 = lists.cons(var8, var0);
         }

         return var0;
      } else {
         return Boolean.FALSE;
      }
   }

   public static Object regexMatchPositions(Object var0, CharSequence var1) {
      return regexMatchPositions(var0, var1, 0);
   }

   public static Object regexMatchPositions(Object var0, CharSequence var1, int var2) {
      return regexMatchPositions(var0, var1, var2, var1.length());
   }

   public static Object regexMatchPositions(Object var0, CharSequence var1, int var2, int var3) {
      Pattern var6;
      if(var0 instanceof Pattern) {
         Pattern var4;
         try {
            var4 = (Pattern)var0;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "rex", -2, var0);
         }

         var6 = var4;
      } else {
         var6 = Pattern.compile(var0.toString());
      }

      Matcher var8 = var6.matcher(var1);
      var8.region(var2, var3);
      if(var8.find()) {
         var2 = var8.groupCount();

         for(var0 = LList.Empty; var2 >= 0; --var2) {
            var3 = var8.start(var2);
            Object var7;
            if(var3 < 0) {
               var7 = Boolean.FALSE;
            } else {
               var7 = lists.cons(Integer.valueOf(var3), Integer.valueOf(var8.end(var2)));
            }

            var0 = lists.cons(var7, var0);
         }

         return var0;
      } else {
         return Boolean.FALSE;
      }
   }

   public static String regexQuote(CharSequence var0) {
      String var1;
      if(var0 == null) {
         var1 = null;
      } else {
         var1 = var0.toString();
      }

      return Pattern.quote(var1);
   }

   public static CharSequence regexReplace(Object var0, CharSequence var1, Object var2) {
      Object var4 = null;
      Object var3 = null;
      Pattern var7;
      if(var0 instanceof Pattern) {
         Pattern var5;
         try {
            var5 = (Pattern)var0;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "rex", -2, var0);
         }

         var7 = var5;
      } else {
         var7 = Pattern.compile(var0.toString());
      }

      Matcher var10 = var7.matcher((CharSequence)var1);
      if(var10.find()) {
         StringBuffer var8 = new StringBuffer();
         String var9;
         if(misc.isProcedure(var2)) {
            var0 = Scheme.applyToArgs.apply2(var2, var10.group());
            if(var0 == null) {
               var9 = (String)var3;
            } else {
               var9 = var0.toString();
            }

            var9 = Matcher.quoteReplacement(var9);
         } else {
            var9 = (String)var4;
            if(var2 != null) {
               var9 = var2.toString();
            }
         }

         var10.appendReplacement(var8, var9);
         var10.appendTail(var8);
         var1 = var8.toString();
      }

      return (CharSequence)var1;
   }

   public static CharSequence regexReplace$St(Object var0, CharSequence var1, Object var2) {
      regex.frame var3 = new regex.frame();
      var3.repl = var2;
      Pattern var5;
      if(var0 instanceof Pattern) {
         Pattern var8;
         try {
            var8 = (Pattern)var0;
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "rex", -2, var0);
         }

         var5 = var8;
      } else {
         var5 = Pattern.compile(var0.toString());
      }

      var3.matcher = var5.matcher(var1);
      var3.sbuf = new StringBuffer();
      if(misc.isProcedure(var3.repl)) {
         var3.loop = var3.loop;
         return var3.lambda1loop();
      } else {
         Matcher var6 = var3.matcher;
         var0 = var3.repl;
         String var7;
         if(var0 == null) {
            var7 = null;
         } else {
            var7 = var0.toString();
         }

         return var6.replaceAll(var7);
      }
   }

   public static LList regexSplit(Object var0, CharSequence var1) {
      Pattern var4;
      if(var0 instanceof Pattern) {
         Pattern var2;
         try {
            var2 = (Pattern)var0;
         } catch (ClassCastException var3) {
            throw new WrongType(var3, "rex", -2, var0);
         }

         var4 = var2;
      } else {
         var4 = Pattern.compile(var0.toString());
      }

      return LList.makeList(var4.split(var1, -1), 0);
   }

   public Object apply1(ModuleMethod var1, Object var2) {
      if(var1.selector == 2) {
         CharSequence var4;
         try {
            var4 = (CharSequence)var2;
         } catch (ClassCastException var3) {
            throw new WrongType(var3, "regex-quote", 1, var2);
         }

         return regexQuote(var4);
      } else {
         return super.apply1(var1, var2);
      }
   }

   public Object apply2(ModuleMethod var1, Object var2, Object var3) {
      CharSequence var8;
      switch(var1.selector) {
      case 3:
         try {
            var8 = (CharSequence)var3;
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "regex-match?", 2, var3);
         }

         if(isRegexMatch(var2, var8)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 6:
         try {
            var8 = (CharSequence)var3;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "regex-match", 2, var3);
         }

         return regexMatch(var2, var8);
      case 9:
         try {
            var8 = (CharSequence)var3;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "regex-match-positions", 2, var3);
         }

         return regexMatchPositions(var2, var8);
      case 12:
         try {
            var8 = (CharSequence)var3;
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "regex-split", 2, var3);
         }

         return regexSplit(var2, var8);
      default:
         return super.apply2(var1, var2, var3);
      }
   }

   public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
      int var5;
      CharSequence var14;
      switch(var1.selector) {
      case 3:
         try {
            var14 = (CharSequence)var3;
         } catch (ClassCastException var13) {
            throw new WrongType(var13, "regex-match?", 2, var3);
         }

         try {
            var5 = ((Number)var4).intValue();
         } catch (ClassCastException var12) {
            throw new WrongType(var12, "regex-match?", 3, var4);
         }

         if(isRegexMatch(var2, var14, var5)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 6:
         try {
            var14 = (CharSequence)var3;
         } catch (ClassCastException var11) {
            throw new WrongType(var11, "regex-match", 2, var3);
         }

         try {
            var5 = ((Number)var4).intValue();
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "regex-match", 3, var4);
         }

         return regexMatch(var2, var14, var5);
      case 9:
         try {
            var14 = (CharSequence)var3;
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "regex-match-positions", 2, var3);
         }

         try {
            var5 = ((Number)var4).intValue();
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "regex-match-positions", 3, var4);
         }

         return regexMatchPositions(var2, var14, var5);
      case 13:
         try {
            var14 = (CharSequence)var3;
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "regex-replace", 2, var3);
         }

         return regexReplace(var2, var14, var4);
      case 14:
         try {
            var14 = (CharSequence)var3;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "regex-replace*", 2, var3);
         }

         return regexReplace$St(var2, var14, var4);
      default:
         return super.apply3(var1, var2, var3, var4);
      }
   }

   public Object apply4(ModuleMethod var1, Object var2, Object var3, Object var4, Object var5) {
      int var6;
      int var7;
      CharSequence var17;
      switch(var1.selector) {
      case 3:
         try {
            var17 = (CharSequence)var3;
         } catch (ClassCastException var16) {
            throw new WrongType(var16, "regex-match?", 2, var3);
         }

         try {
            var6 = ((Number)var4).intValue();
         } catch (ClassCastException var15) {
            throw new WrongType(var15, "regex-match?", 3, var4);
         }

         try {
            var7 = ((Number)var5).intValue();
         } catch (ClassCastException var14) {
            throw new WrongType(var14, "regex-match?", 4, var5);
         }

         if(isRegexMatch(var2, var17, var6, var7)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 6:
         try {
            var17 = (CharSequence)var3;
         } catch (ClassCastException var13) {
            throw new WrongType(var13, "regex-match", 2, var3);
         }

         try {
            var6 = ((Number)var4).intValue();
         } catch (ClassCastException var12) {
            throw new WrongType(var12, "regex-match", 3, var4);
         }

         try {
            var7 = ((Number)var5).intValue();
         } catch (ClassCastException var11) {
            throw new WrongType(var11, "regex-match", 4, var5);
         }

         return regexMatch(var2, var17, var6, var7);
      case 9:
         try {
            var17 = (CharSequence)var3;
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "regex-match-positions", 2, var3);
         }

         try {
            var6 = ((Number)var4).intValue();
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "regex-match-positions", 3, var4);
         }

         try {
            var7 = ((Number)var5).intValue();
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "regex-match-positions", 4, var5);
         }

         return regexMatchPositions(var2, var17, var6, var7);
      default:
         return super.apply4(var1, var2, var3, var4, var5);
      }
   }

   public int match1(ModuleMethod var1, Object var2, CallContext var3) {
      if(var1.selector == 2) {
         if(var2 instanceof CharSequence) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return -786431;
         }
      } else {
         return super.match1(var1, var2, var3);
      }
   }

   public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
      switch(var1.selector) {
      case 3:
         var4.value1 = var2;
         if(var3 instanceof CharSequence) {
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }

         return -786430;
      case 6:
         var4.value1 = var2;
         if(var3 instanceof CharSequence) {
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }

         return -786430;
      case 9:
         var4.value1 = var2;
         if(var3 instanceof CharSequence) {
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }

         return -786430;
      case 12:
         var4.value1 = var2;
         if(var3 instanceof CharSequence) {
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }

         return -786430;
      default:
         return super.match2(var1, var2, var3, var4);
      }
   }

   public int match3(ModuleMethod var1, Object var2, Object var3, Object var4, CallContext var5) {
      switch(var1.selector) {
      case 3:
         var5.value1 = var2;
         if(var3 instanceof CharSequence) {
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         }

         return -786430;
      case 6:
         var5.value1 = var2;
         if(var3 instanceof CharSequence) {
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         }

         return -786430;
      case 9:
         var5.value1 = var2;
         if(var3 instanceof CharSequence) {
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         }

         return -786430;
      case 13:
         var5.value1 = var2;
         if(var3 instanceof CharSequence) {
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         }

         return -786430;
      case 14:
         var5.value1 = var2;
         if(var3 instanceof CharSequence) {
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         }

         return -786430;
      default:
         return super.match3(var1, var2, var3, var4, var5);
      }
   }

   public int match4(ModuleMethod var1, Object var2, Object var3, Object var4, Object var5, CallContext var6) {
      switch(var1.selector) {
      case 3:
         var6.value1 = var2;
         if(var3 instanceof CharSequence) {
            var6.value2 = var3;
            var6.value3 = var4;
            var6.value4 = var5;
            var6.proc = var1;
            var6.pc = 4;
            return 0;
         }

         return -786430;
      case 6:
         var6.value1 = var2;
         if(var3 instanceof CharSequence) {
            var6.value2 = var3;
            var6.value3 = var4;
            var6.value4 = var5;
            var6.proc = var1;
            var6.pc = 4;
            return 0;
         }

         return -786430;
      case 9:
         var6.value1 = var2;
         if(var3 instanceof CharSequence) {
            var6.value2 = var3;
            var6.value3 = var4;
            var6.value4 = var5;
            var6.proc = var1;
            var6.pc = 4;
            return 0;
         }

         return -786430;
      default:
         return super.match4(var1, var2, var3, var4, var5, var6);
      }
   }

   public final void run(CallContext var1) {
      Consumer var2 = var1.consumer;
   }

   public class frame extends ModuleBody {

      Object loop;
      Matcher matcher;
      Object repl;
      StringBuffer sbuf;


      public frame() {
         this.loop = new ModuleMethod(this, 1, regex.Lit0, 0);
      }

      public Object apply0(ModuleMethod var1) {
         return var1.selector == 1?this.lambda1loop():super.apply0(var1);
      }

      public String lambda1loop() {
         if(this.matcher.find()) {
            Matcher var2 = this.matcher;
            StringBuffer var3 = this.sbuf;
            Object var1 = Scheme.applyToArgs.apply2(this.repl, this.matcher.group());
            String var4;
            if(var1 == null) {
               var4 = null;
            } else {
               var4 = var1.toString();
            }

            var2.appendReplacement(var3, Matcher.quoteReplacement(var4));
         }

         this.matcher.appendTail(this.sbuf);
         return this.sbuf.toString();
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 1) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }
   }
}
