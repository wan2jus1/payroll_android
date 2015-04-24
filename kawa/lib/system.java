package kawa.lib;

import gnu.expr.ApplicationMainSupport;
import gnu.expr.Compilation;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.AddOp;
import gnu.kawa.functions.IsEqual;
import gnu.lists.Consumer;
import gnu.lists.FVector;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.CallContext;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import gnu.text.SourceMessages;
import gnu.text.SyntaxException;
import java.io.InputStream;
import java.util.StringTokenizer;
import kawa.lang.CompileFile;
import kawa.lang.NamedException;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.lib.strings;
import kawa.lib.vectors;
import kawa.standard.Scheme;

public class system extends ModuleBody {

   public static final system $instance = new system();
   static final IntNum Lit0 = IntNum.make(0);
   static final IntNum Lit1 = IntNum.make(1);
   static final SimpleSymbol Lit10 = (SimpleSymbol)(new SimpleSymbol("catch")).readResolve();
   static final SimpleSymbol Lit11 = (SimpleSymbol)(new SimpleSymbol("process-command-line-assignments")).readResolve();
   static final SimpleSymbol Lit2 = (SimpleSymbol)(new SimpleSymbol("make-process")).readResolve();
   static final SimpleSymbol Lit3 = (SimpleSymbol)(new SimpleSymbol("open-input-pipe")).readResolve();
   static final SimpleSymbol Lit4 = (SimpleSymbol)(new SimpleSymbol("system")).readResolve();
   static final SimpleSymbol Lit5 = (SimpleSymbol)(new SimpleSymbol("convert-vector-to-string-array")).readResolve();
   static final SimpleSymbol Lit6 = (SimpleSymbol)(new SimpleSymbol("convert-list-to-string-array")).readResolve();
   static final SimpleSymbol Lit7 = (SimpleSymbol)(new SimpleSymbol("tokenize-string-to-string-array")).readResolve();
   static final SimpleSymbol Lit8 = (SimpleSymbol)(new SimpleSymbol("tokenize-string-using-shell")).readResolve();
   static final SimpleSymbol Lit9 = (SimpleSymbol)(new SimpleSymbol("compile-file")).readResolve();
   public static final ModuleMethod catch;
   public static Procedure command$Mnparse;
   public static final ModuleMethod compile$Mnfile;
   public static final ModuleMethod convert$Mnlist$Mnto$Mnstring$Mnarray;
   public static final ModuleMethod convert$Mnvector$Mnto$Mnstring$Mnarray;
   public static final ModuleMethod make$Mnprocess;
   public static final ModuleMethod open$Mninput$Mnpipe;
   public static final ModuleMethod process$Mncommand$Mnline$Mnassignments;
   public static final ModuleMethod system;
   public static final ModuleMethod tokenize$Mnstring$Mnto$Mnstring$Mnarray;
   public static final ModuleMethod tokenize$Mnstring$Mnusing$Mnshell;


   static {
      system var0 = $instance;
      make$Mnprocess = new ModuleMethod(var0, 1, Lit2, 8194);
      open$Mninput$Mnpipe = new ModuleMethod(var0, 2, Lit3, 4097);
      system = new ModuleMethod(var0, 3, Lit4, 4097);
      convert$Mnvector$Mnto$Mnstring$Mnarray = new ModuleMethod(var0, 4, Lit5, 4097);
      convert$Mnlist$Mnto$Mnstring$Mnarray = new ModuleMethod(var0, 5, Lit6, 4097);
      tokenize$Mnstring$Mnto$Mnstring$Mnarray = new ModuleMethod(var0, 6, Lit7, 4097);
      tokenize$Mnstring$Mnusing$Mnshell = new ModuleMethod(var0, 7, Lit8, 4097);
      compile$Mnfile = new ModuleMethod(var0, 8, Lit9, 8194);
      catch = new ModuleMethod(var0, 9, Lit10, 12291);
      process$Mncommand$Mnline$Mnassignments = new ModuleMethod(var0, 10, Lit11, 0);
      $instance.run();
   }

   public system() {
      ModuleInfo.register(this);
   }

   public static Object catch(Object var0, Procedure var1, Procedure var2) {
      try {
         Object var4 = var1.apply0();
         return var4;
      } catch (NamedException var3) {
         return var3.applyHandler(var0, var2);
      }
   }

   public static void compileFile(CharSequence var0, String var1) {
      SourceMessages var2 = new SourceMessages();
      Compilation var3 = CompileFile.read((String)var0.toString(), var2);
      var3.explicit = true;
      if(var2.seenErrors()) {
         throw (Throwable)(new SyntaxException(var2));
      } else {
         var3.compileToArchive(var3.getModule(), var1);
         if(var2.seenErrors()) {
            throw (Throwable)(new SyntaxException(var2));
         }
      }
   }

   public static Object convertListToStringArray(Object var0) {
      LList var1;
      try {
         var1 = (LList)var0;
      } catch (ClassCastException var5) {
         throw new WrongType(var5, "length", 1, var0);
      }

      String[] var7 = new String[lists.length(var1)];

      for(int var3 = 0; !lists.isNull(var0); ++var3) {
         Pair var2;
         try {
            var2 = (Pair)var0;
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "pp", -2, var0);
         }

         var0 = var2.getCar();
         String var6;
         if(var0 == null) {
            var6 = null;
         } else {
            var6 = var0.toString();
         }

         var7[var3] = var6;
         var0 = var2.getCdr();
      }

      return var7;
   }

   public static Object convertVectorToStringArray(Object var0) {
      FVector var1;
      try {
         var1 = (FVector)var0;
      } catch (ClassCastException var9) {
         throw new WrongType(var9, "vector-length", 1, var0);
      }

      int var4 = vectors.vectorLength(var1);
      String[] var3 = new String[var4];

      for(Object var10 = Lit0; Scheme.numEqu.apply2(var10, Integer.valueOf(var4)) == Boolean.FALSE; var10 = AddOp.$Pl.apply2(var10, Lit1)) {
         int var5 = ((Number)var10).intValue();

         FVector var2;
         try {
            var2 = (FVector)var0;
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "vector-ref", 1, var0);
         }

         int var6;
         try {
            var6 = ((Number)var10).intValue();
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "vector-ref", 2, var10);
         }

         Object var11 = vectors.vectorRef(var2, var6);
         String var12;
         if(var11 == null) {
            var12 = null;
         } else {
            var12 = var11.toString();
         }

         var3[var5] = var12;
      }

      return var3;
   }

   public static Process makeProcess(Object var0, Object var1) {
      if(vectors.isVector(var0)) {
         var0 = convertVectorToStringArray(var0);
      } else if(lists.isList(var0)) {
         var0 = convertListToStringArray(var0);
      } else if(strings.isString(var0)) {
         var0 = command$Mnparse.apply1(var0);
      } else if(!(var0 instanceof String[])) {
         var0 = misc.error$V("invalid arguments to make-process", new Object[0]);
      }

      Runtime var2 = Runtime.getRuntime();

      String[] var3;
      try {
         var3 = (String[])var0;
      } catch (ClassCastException var5) {
         throw new WrongType(var5, "java.lang.Runtime.exec(java.lang.String[],java.lang.String[])", 2, var0);
      }

      String[] var6;
      try {
         var6 = (String[])var1;
      } catch (ClassCastException var4) {
         throw new WrongType(var4, "java.lang.Runtime.exec(java.lang.String[],java.lang.String[])", 3, var1);
      }

      return var2.exec(var3, var6);
   }

   public static InputStream openInputPipe(Object var0) {
      return makeProcess(var0, (Object)null).getInputStream();
   }

   public static void processCommandLineAssignments() {
      ApplicationMainSupport.processSetProperties();
   }

   public static int system(Object var0) {
      return makeProcess(var0, (Object)null).waitFor();
   }

   public static Object tokenizeStringToStringArray(String var0) {
      StringTokenizer var1 = new StringTokenizer(var0);

      Object var6;
      for(var6 = LList.Empty; var1.hasMoreTokens(); var6 = lists.cons(var1.nextToken(), var6)) {
         ;
      }

      LList var7;
      try {
         var7 = (LList)var6;
      } catch (ClassCastException var5) {
         throw new WrongType(var5, "length", 1, var6);
      }

      int var3 = lists.length(var7);
      String[] var8 = new String[var3];
      --var3;

      while(!lists.isNull(var6)) {
         Pair var2;
         try {
            var2 = (Pair)var6;
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "pp", -2, var6);
         }

         var6 = var2.getCar();
         if(var6 == null) {
            var0 = null;
         } else {
            var0 = var6.toString();
         }

         var8[var3] = var0;
         var6 = var2.getCdr();
         --var3;
      }

      return var8;
   }

   public static String[] tokenizeStringUsingShell(Object var0) {
      String var1;
      if(var0 == null) {
         var1 = null;
      } else {
         var1 = var0.toString();
      }

      return new String[]{"/bin/sh", "-c", var1};
   }

   public Object apply0(ModuleMethod var1) {
      if(var1.selector == 10) {
         processCommandLineAssignments();
         return Values.empty;
      } else {
         return super.apply0(var1);
      }
   }

   public Object apply1(ModuleMethod var1, Object var2) {
      switch(var1.selector) {
      case 2:
         return openInputPipe(var2);
      case 3:
         return Integer.valueOf(system(var2));
      case 4:
         return convertVectorToStringArray(var2);
      case 5:
         return convertListToStringArray(var2);
      case 6:
         String var3;
         if(var2 == null) {
            var3 = null;
         } else {
            var3 = var2.toString();
         }

         return tokenizeStringToStringArray(var3);
      case 7:
         return tokenizeStringUsingShell(var2);
      default:
         return super.apply1(var1, var2);
      }
   }

   public Object apply2(ModuleMethod var1, Object var2, Object var3) {
      switch(var1.selector) {
      case 1:
         return makeProcess(var2, var3);
      case 8:
         CharSequence var4;
         try {
            var4 = (CharSequence)var2;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "compile-file", 1, var2);
         }

         String var6;
         if(var3 == null) {
            var6 = null;
         } else {
            var6 = var3.toString();
         }

         compileFile(var4, var6);
         return Values.empty;
      default:
         return super.apply2(var1, var2, var3);
      }
   }

   public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
      if(var1.selector == 9) {
         Procedure var7;
         try {
            var7 = (Procedure)var3;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "catch", 2, var3);
         }

         Procedure var8;
         try {
            var8 = (Procedure)var4;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "catch", 3, var4);
         }

         return catch(var2, var7, var8);
      } else {
         return super.apply3(var1, var2, var3, var4);
      }
   }

   public int match0(ModuleMethod var1, CallContext var2) {
      if(var1.selector == 10) {
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      } else {
         return super.match0(var1, var2);
      }
   }

   public int match1(ModuleMethod var1, Object var2, CallContext var3) {
      switch(var1.selector) {
      case 2:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 3:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 4:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 5:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 6:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 7:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      default:
         return super.match1(var1, var2, var3);
      }
   }

   public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
      switch(var1.selector) {
      case 1:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 8:
         if(var2 instanceof CharSequence) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }

         return -786431;
      default:
         return super.match2(var1, var2, var3, var4);
      }
   }

   public int match3(ModuleMethod var1, Object var2, Object var3, Object var4, CallContext var5) {
      if(var1.selector == 9) {
         var5.value1 = var2;
         if(!(var3 instanceof Procedure)) {
            return -786430;
         } else {
            var5.value2 = var3;
            if(!(var4 instanceof Procedure)) {
               return -786429;
            } else {
               var5.value3 = var4;
               var5.proc = var1;
               var5.pc = 3;
               return 0;
            }
         }
      } else {
         return super.match3(var1, var2, var3, var4, var5);
      }
   }

   public final void run(CallContext var1) {
      Consumer var2 = var1.consumer;
      ModuleMethod var3;
      if(IsEqual.apply(System.getProperty("file.separator"), "/")) {
         var3 = tokenize$Mnstring$Mnusing$Mnshell;
      } else {
         var3 = tokenize$Mnstring$Mnto$Mnstring$Mnarray;
      }

      command$Mnparse = var3;
   }
}
