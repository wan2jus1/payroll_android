package kawa.lib;

import gnu.bytecode.ClassType;
import gnu.expr.GenericProc;
import gnu.expr.Keyword;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.AddOp;
import gnu.kawa.lispexpr.LispReader;
import gnu.lists.AbstractFormat;
import gnu.lists.Consumer;
import gnu.lists.EofClass;
import gnu.lists.FString;
import gnu.mapping.CallContext;
import gnu.mapping.CharArrayInPort;
import gnu.mapping.CharArrayOutPort;
import gnu.mapping.InPort;
import gnu.mapping.LocationProc;
import gnu.mapping.OutPort;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.TtyInPort;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import gnu.text.Char;
import gnu.text.LineBufferedReader;
import gnu.text.Path;
import gnu.text.SyntaxException;
import java.io.Writer;
import kawa.lib.characters;
import kawa.lib.numbers;
import kawa.lib.strings;
import kawa.standard.Scheme;
import kawa.standard.char_ready_p;
import kawa.standard.read_line;

public class ports extends ModuleBody {

   public static final ports $instance = new ports();
   static final SimpleSymbol Lit0 = (SimpleSymbol)(new SimpleSymbol("current-input-port")).readResolve();
   static final ClassType Lit1 = ClassType.make("gnu.mapping.InPort");
   static final SimpleSymbol Lit10 = (SimpleSymbol)(new SimpleSymbol("open-input-file")).readResolve();
   static final SimpleSymbol Lit11 = (SimpleSymbol)(new SimpleSymbol("open-output-file")).readResolve();
   static final SimpleSymbol Lit12 = (SimpleSymbol)(new SimpleSymbol("call-with-input-file")).readResolve();
   static final SimpleSymbol Lit13 = (SimpleSymbol)(new SimpleSymbol("call-with-output-file")).readResolve();
   static final SimpleSymbol Lit14 = (SimpleSymbol)(new SimpleSymbol("with-input-from-file")).readResolve();
   static final SimpleSymbol Lit15 = (SimpleSymbol)(new SimpleSymbol("with-output-to-file")).readResolve();
   static final SimpleSymbol Lit16 = (SimpleSymbol)(new SimpleSymbol("input-port?")).readResolve();
   static final SimpleSymbol Lit17 = (SimpleSymbol)(new SimpleSymbol("output-port?")).readResolve();
   static final SimpleSymbol Lit18 = (SimpleSymbol)(new SimpleSymbol("write-char")).readResolve();
   static final SimpleSymbol Lit19 = (SimpleSymbol)(new SimpleSymbol("open-input-string")).readResolve();
   static final SimpleSymbol Lit2 = (SimpleSymbol)(new SimpleSymbol("current-output-port")).readResolve();
   static final SimpleSymbol Lit20 = (SimpleSymbol)(new SimpleSymbol("open-output-string")).readResolve();
   static final SimpleSymbol Lit21 = (SimpleSymbol)(new SimpleSymbol("get-output-string")).readResolve();
   static final SimpleSymbol Lit22 = (SimpleSymbol)(new SimpleSymbol("call-with-input-string")).readResolve();
   static final SimpleSymbol Lit23 = (SimpleSymbol)(new SimpleSymbol("call-with-output-string")).readResolve();
   static final SimpleSymbol Lit24 = (SimpleSymbol)(new SimpleSymbol("force-output")).readResolve();
   static final SimpleSymbol Lit25 = (SimpleSymbol)(new SimpleSymbol("newline")).readResolve();
   static final SimpleSymbol Lit26 = (SimpleSymbol)(new SimpleSymbol("eof-object?")).readResolve();
   static final SimpleSymbol Lit27 = (SimpleSymbol)(new SimpleSymbol("char-ready?")).readResolve();
   static final SimpleSymbol Lit28 = (SimpleSymbol)(new SimpleSymbol("write")).readResolve();
   static final SimpleSymbol Lit29 = (SimpleSymbol)(new SimpleSymbol("display")).readResolve();
   static final ClassType Lit3 = ClassType.make("gnu.mapping.OutPort");
   static final SimpleSymbol Lit30 = (SimpleSymbol)(new SimpleSymbol("input-port-read-state")).readResolve();
   static final SimpleSymbol Lit31 = (SimpleSymbol)(new SimpleSymbol("set-port-line!")).readResolve();
   static final SimpleSymbol Lit32 = (SimpleSymbol)(new SimpleSymbol("port-line")).readResolve();
   static final SimpleSymbol Lit33 = (SimpleSymbol)(new SimpleSymbol("set-input-port-line-number!")).readResolve();
   static final SimpleSymbol Lit34 = (SimpleSymbol)(new SimpleSymbol("input-port-line-number")).readResolve();
   static final SimpleSymbol Lit35 = (SimpleSymbol)(new SimpleSymbol("port-column")).readResolve();
   static final SimpleSymbol Lit36 = (SimpleSymbol)(new SimpleSymbol("input-port-column-number")).readResolve();
   static final SimpleSymbol Lit37 = (SimpleSymbol)(new SimpleSymbol("default-prompter")).readResolve();
   static final SimpleSymbol Lit38 = (SimpleSymbol)(new SimpleSymbol("set-input-port-prompter!")).readResolve();
   static final SimpleSymbol Lit39 = (SimpleSymbol)(new SimpleSymbol("input-port-prompter")).readResolve();
   static final SimpleSymbol Lit4 = (SimpleSymbol)(new SimpleSymbol("current-error-port")).readResolve();
   static final SimpleSymbol Lit40 = (SimpleSymbol)(new SimpleSymbol("close-input-port")).readResolve();
   static final SimpleSymbol Lit41 = (SimpleSymbol)(new SimpleSymbol("close-output-port")).readResolve();
   static final SimpleSymbol Lit42 = (SimpleSymbol)(new SimpleSymbol("read")).readResolve();
   static final SimpleSymbol Lit43 = (SimpleSymbol)(new SimpleSymbol("read-line")).readResolve();
   static final SimpleSymbol Lit44 = (SimpleSymbol)(new SimpleSymbol("transcript-on")).readResolve();
   static final SimpleSymbol Lit45 = (SimpleSymbol)(new SimpleSymbol("transcript-off")).readResolve();
   static final Keyword Lit5 = Keyword.make("setter");
   static final IntNum Lit6 = IntNum.make(1);
   static final Char Lit7 = Char.make(10);
   static final Char Lit8 = Char.make(32);
   static final SimpleSymbol Lit9 = (SimpleSymbol)(new SimpleSymbol("trim")).readResolve();
   public static final ModuleMethod call$Mnwith$Mninput$Mnfile;
   public static final ModuleMethod call$Mnwith$Mninput$Mnstring;
   public static final ModuleMethod call$Mnwith$Mnoutput$Mnfile;
   public static final ModuleMethod call$Mnwith$Mnoutput$Mnstring;
   public static final ModuleMethod char$Mnready$Qu;
   public static final ModuleMethod close$Mninput$Mnport;
   public static final ModuleMethod close$Mnoutput$Mnport;
   public static final LocationProc current$Mnerror$Mnport;
   public static final LocationProc current$Mninput$Mnport;
   public static final LocationProc current$Mnoutput$Mnport;
   public static final ModuleMethod default$Mnprompter;
   public static final ModuleMethod display;
   public static final ModuleMethod eof$Mnobject$Qu;
   public static final ModuleMethod force$Mnoutput;
   public static final ModuleMethod get$Mnoutput$Mnstring;
   public static final ModuleMethod input$Mnport$Mncolumn$Mnnumber;
   public static final GenericProc input$Mnport$Mnline$Mnnumber;
   static final ModuleMethod input$Mnport$Mnline$Mnnumber$Fn5;
   public static final GenericProc input$Mnport$Mnprompter;
   static final ModuleMethod input$Mnport$Mnprompter$Fn6;
   public static final ModuleMethod input$Mnport$Mnread$Mnstate;
   public static final ModuleMethod input$Mnport$Qu;
   static final ModuleMethod lambda$Fn1;
   static final ModuleMethod lambda$Fn2;
   static final ModuleMethod lambda$Fn3;
   public static final ModuleMethod newline;
   public static final ModuleMethod open$Mninput$Mnfile;
   public static final ModuleMethod open$Mninput$Mnstring;
   public static final ModuleMethod open$Mnoutput$Mnfile;
   public static final ModuleMethod open$Mnoutput$Mnstring;
   public static final ModuleMethod output$Mnport$Qu;
   public static final ModuleMethod port$Mncolumn;
   public static final GenericProc port$Mnline;
   static final ModuleMethod port$Mnline$Fn4;
   public static final ModuleMethod read;
   public static final ModuleMethod read$Mnline;
   public static final ModuleMethod set$Mninput$Mnport$Mnline$Mnnumber$Ex;
   public static final ModuleMethod set$Mninput$Mnport$Mnprompter$Ex;
   public static final ModuleMethod set$Mnport$Mnline$Ex;
   public static final ModuleMethod transcript$Mnoff;
   public static final ModuleMethod transcript$Mnon;
   public static final ModuleMethod with$Mninput$Mnfrom$Mnfile;
   public static final ModuleMethod with$Mnoutput$Mnto$Mnfile;
   public static final ModuleMethod write;
   public static final ModuleMethod write$Mnchar;


   static {
      ports var0 = $instance;
      open$Mninput$Mnfile = new ModuleMethod(var0, 1, Lit10, 4097);
      open$Mnoutput$Mnfile = new ModuleMethod(var0, 2, Lit11, 4097);
      call$Mnwith$Mninput$Mnfile = new ModuleMethod(var0, 3, Lit12, 8194);
      call$Mnwith$Mnoutput$Mnfile = new ModuleMethod(var0, 4, Lit13, 8194);
      with$Mninput$Mnfrom$Mnfile = new ModuleMethod(var0, 5, Lit14, 8194);
      with$Mnoutput$Mnto$Mnfile = new ModuleMethod(var0, 6, Lit15, 8194);
      input$Mnport$Qu = new ModuleMethod(var0, 7, Lit16, 4097);
      output$Mnport$Qu = new ModuleMethod(var0, 8, Lit17, 4097);
      lambda$Fn1 = new ModuleMethod(var0, 9, (Object)null, 4097);
      lambda$Fn2 = new ModuleMethod(var0, 10, (Object)null, 4097);
      lambda$Fn3 = new ModuleMethod(var0, 11, (Object)null, 4097);
      write$Mnchar = new ModuleMethod(var0, 12, Lit18, 8193);
      open$Mninput$Mnstring = new ModuleMethod(var0, 14, Lit19, 4097);
      open$Mnoutput$Mnstring = new ModuleMethod(var0, 15, Lit20, 0);
      get$Mnoutput$Mnstring = new ModuleMethod(var0, 16, Lit21, 4097);
      call$Mnwith$Mninput$Mnstring = new ModuleMethod(var0, 17, Lit22, 8194);
      call$Mnwith$Mnoutput$Mnstring = new ModuleMethod(var0, 18, Lit23, 4097);
      force$Mnoutput = new ModuleMethod(var0, 19, Lit24, 4096);
      newline = new ModuleMethod(var0, 21, Lit25, 4096);
      eof$Mnobject$Qu = new ModuleMethod(var0, 23, Lit26, 4097);
      char$Mnready$Qu = new ModuleMethod(var0, 24, Lit27, 4096);
      write = new ModuleMethod(var0, 26, Lit28, 8193);
      display = new ModuleMethod(var0, 28, Lit29, 8193);
      input$Mnport$Mnread$Mnstate = new ModuleMethod(var0, 30, Lit30, 4097);
      set$Mnport$Mnline$Ex = new ModuleMethod(var0, 31, Lit31, 8194);
      port$Mnline$Fn4 = new ModuleMethod(var0, 32, Lit32, 4097);
      set$Mninput$Mnport$Mnline$Mnnumber$Ex = new ModuleMethod(var0, 33, Lit33, 8194);
      input$Mnport$Mnline$Mnnumber$Fn5 = new ModuleMethod(var0, 34, Lit34, 4097);
      port$Mncolumn = new ModuleMethod(var0, 35, Lit35, 4097);
      input$Mnport$Mncolumn$Mnnumber = new ModuleMethod(var0, 36, Lit36, 4097);
      default$Mnprompter = new ModuleMethod(var0, 37, Lit37, 4097);
      set$Mninput$Mnport$Mnprompter$Ex = new ModuleMethod(var0, 38, Lit38, 8194);
      input$Mnport$Mnprompter$Fn6 = new ModuleMethod(var0, 39, Lit39, 4097);
      close$Mninput$Mnport = new ModuleMethod(var0, 40, Lit40, 4097);
      close$Mnoutput$Mnport = new ModuleMethod(var0, 41, Lit41, 4097);
      read = new ModuleMethod(var0, 42, Lit42, 4096);
      read$Mnline = new ModuleMethod(var0, 44, Lit43, 8192);
      transcript$Mnon = new ModuleMethod(var0, 47, Lit44, 4097);
      transcript$Mnoff = new ModuleMethod(var0, 48, Lit45, 0);
      $instance.run();
   }

   public ports() {
      ModuleInfo.register(this);
   }

   public static Object callWithInputFile(Path var0, Procedure var1) {
      InPort var4 = openInputFile(var0);

      Object var5;
      try {
         var5 = var1.apply1(var4);
      } finally {
         closeInputPort(var4);
      }

      return var5;
   }

   public static Object callWithInputString(CharSequence var0, Procedure var1) {
      String var2;
      if(var0 == null) {
         var2 = null;
      } else {
         var2 = var0.toString();
      }

      CharArrayInPort var3 = new CharArrayInPort(var2);
      Object var4 = var1.apply1(var3);
      closeInputPort(var3);
      return var4;
   }

   public static Object callWithOutputFile(Path var0, Procedure var1) {
      OutPort var4 = openOutputFile(var0);

      Object var5;
      try {
         var5 = var1.apply1(var4);
      } finally {
         closeOutputPort(var4);
      }

      return var5;
   }

   public static FString callWithOutputString(Procedure var0) {
      CharArrayOutPort var1 = new CharArrayOutPort();
      var0.apply1(var1);
      char[] var2 = var1.toCharArray();
      var1.close();
      return new FString(var2);
   }

   public static Object closeInputPort(InPort var0) {
      var0.close();
      return Values.empty;
   }

   public static Object closeOutputPort(OutPort var0) {
      var0.close();
      return Values.empty;
   }

   public static Object defaultPrompter(Object var0) {
      char var3 = inputPortReadState(var0);
      if(characters.isChar$Eq(Char.make(var3), Lit7)) {
         return "";
      } else {
         Object var1;
         if(characters.isChar$Eq(Char.make(var3), Lit8)) {
            var1 = "#|kawa:";
         } else {
            var1 = strings.stringAppend(new Object[]{"#|", strings.makeString(1, Char.make(var3)), "---:"});
         }

         var0 = input$Mnport$Mnline$Mnnumber.apply1(var0);

         Number var2;
         try {
            var2 = (Number)var0;
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "number->string", 0, var0);
         }

         return strings.stringAppend(new Object[]{var1, numbers.number$To$String(var2), "|# "});
      }
   }

   public static void display(Object var0) {
      display(var0, current$Mnoutput$Mnport.apply0());
   }

   public static void display(Object var0, Object var1) {
      AbstractFormat var2 = Scheme.displayFormat;

      Consumer var3;
      try {
         var3 = (Consumer)var1;
      } catch (ClassCastException var4) {
         throw new WrongType(var4, "gnu.lists.AbstractFormat.format(java.lang.Object,gnu.lists.Consumer)", 3, var1);
      }

      var2.format(var0, var3);
   }

   public static void forceOutput() {
      forceOutput(current$Mnoutput$Mnport.apply0());
   }

   public static void forceOutput(Object var0) {
      Writer var1;
      try {
         var1 = (Writer)var0;
      } catch (ClassCastException var2) {
         throw new WrongType(var2, "java.io.Writer.flush()", 1, var0);
      }

      var1.flush();
   }

   public static FString getOutputString(CharArrayOutPort var0) {
      return new FString(var0.toCharArray());
   }

   public static int inputPortColumnNumber(Object var0) {
      return portColumn(var0) + 1;
   }

   public static Object inputPortLineNumber(LineBufferedReader var0) {
      return AddOp.$Pl.apply2(Lit6, port$Mnline.apply1(var0));
   }

   public static Procedure inputPortPrompter(TtyInPort var0) {
      return var0.getPrompter();
   }

   public static char inputPortReadState(Object var0) {
      InPort var1;
      try {
         var1 = (InPort)var0;
      } catch (ClassCastException var2) {
         throw new WrongType(var2, "gnu.mapping.InPort.getReadState()", 1, var0);
      }

      return var1.getReadState();
   }

   public static boolean isCharReady() {
      return isCharReady(current$Mninput$Mnport.apply0());
   }

   public static boolean isCharReady(Object var0) {
      return char_ready_p.ready(var0);
   }

   public static boolean isEofObject(Object var0) {
      return var0 == EofClass.eofValue;
   }

   public static boolean isInputPort(Object var0) {
      return var0 instanceof InPort;
   }

   public static boolean isOutputPort(Object var0) {
      return var0 instanceof OutPort;
   }

   static Object lambda1(Object var0) {
      try {
         InPort var1 = (InPort)var0;
         return var1;
      } catch (ClassCastException var2) {
         WrongType var3 = WrongType.make(var2, (Procedure)current$Mninput$Mnport, 1, var0);
         var3.expectedType = Lit1;
         throw (Throwable)var3;
      }
   }

   static Object lambda2(Object var0) {
      try {
         OutPort var1 = (OutPort)var0;
         return var1;
      } catch (ClassCastException var2) {
         WrongType var3 = WrongType.make(var2, (Procedure)current$Mnoutput$Mnport, 1, var0);
         var3.expectedType = Lit3;
         throw (Throwable)var3;
      }
   }

   static Object lambda3(Object var0) {
      try {
         OutPort var1 = (OutPort)var0;
         return var1;
      } catch (ClassCastException var2) {
         WrongType var3 = WrongType.make(var2, (Procedure)current$Mnerror$Mnport, 1, var0);
         var3.expectedType = Lit3;
         throw (Throwable)var3;
      }
   }

   public static void newline() {
      newline(current$Mnoutput$Mnport.apply0());
   }

   public static void newline(Object var0) {
      OutPort var1;
      try {
         var1 = (OutPort)var0;
      } catch (ClassCastException var2) {
         throw new WrongType(var2, "gnu.mapping.OutPort.println()", 1, var0);
      }

      var1.println();
   }

   public static InPort openInputFile(Path var0) {
      return InPort.openFile(var0);
   }

   public static InPort openInputString(CharSequence var0) {
      String var1;
      if(var0 == null) {
         var1 = null;
      } else {
         var1 = var0.toString();
      }

      return new CharArrayInPort(var1);
   }

   public static OutPort openOutputFile(Path var0) {
      return OutPort.openFile(var0);
   }

   public static CharArrayOutPort openOutputString() {
      return new CharArrayOutPort();
   }

   public static int portColumn(Object var0) {
      LineBufferedReader var1;
      try {
         var1 = (LineBufferedReader)var0;
      } catch (ClassCastException var2) {
         throw new WrongType(var2, "gnu.text.LineBufferedReader.getColumnNumber()", 1, var0);
      }

      return var1.getColumnNumber();
   }

   public static int portLine(LineBufferedReader var0) {
      return var0.getLineNumber();
   }

   public static Object read() {
      return read((InPort)current$Mninput$Mnport.apply0());
   }

   public static Object read(InPort var0) {
      LispReader var3 = new LispReader(var0);

      try {
         Object var1 = var3.readObject();
         if(var3.seenErrors()) {
            throw (Throwable)(new SyntaxException(var3.getMessages()));
         } else {
            return var1;
         }
      } catch (SyntaxException var2) {
         var2.setHeader("syntax error in read:");
         throw (Throwable)var2;
      }
   }

   public static Object readLine() {
      return readLine((LineBufferedReader)current$Mninput$Mnport.apply0(), Lit9);
   }

   public static Object readLine(LineBufferedReader var0) {
      return readLine(var0, Lit9);
   }

   public static Object readLine(LineBufferedReader var0, Symbol var1) {
      String var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = var1.toString();
      }

      return read_line.apply(var0, var2);
   }

   public static void setInputPortLineNumber$Ex(Object var0, Object var1) {
      setPortLine$Ex(var0, AddOp.$Mn.apply2(var1, Lit6));
   }

   public static void setInputPortPrompter$Ex(TtyInPort var0, Procedure var1) {
      var0.setPrompter(var1);
   }

   public static void setPortLine$Ex(Object var0, Object var1) {
      LineBufferedReader var2;
      try {
         var2 = (LineBufferedReader)var0;
      } catch (ClassCastException var5) {
         throw new WrongType(var5, "gnu.text.LineBufferedReader.setLineNumber(int)", 1, var0);
      }

      int var3;
      try {
         var3 = ((Number)var1).intValue();
      } catch (ClassCastException var4) {
         throw new WrongType(var4, "gnu.text.LineBufferedReader.setLineNumber(int)", 2, var1);
      }

      var2.setLineNumber(var3);
   }

   public static void transcriptOff() {
      OutPort.closeLogFile();
   }

   public static void transcriptOn(Object var0) {
      OutPort.setLogFile(var0.toString());
   }

   public static Object withInputFromFile(Path var0, Procedure var1) {
      InPort var5 = InPort.openFile(var0);
      InPort var2 = InPort.inDefault();

      Object var6;
      try {
         InPort.setInDefault(var5);
         var6 = var1.apply0();
      } finally {
         InPort.setInDefault(var2);
         var5.close();
      }

      return var6;
   }

   public static Object withOutputToFile(Path var0, Procedure var1) {
      OutPort var5 = OutPort.openFile(var0);
      OutPort var2 = OutPort.outDefault();

      Object var6;
      try {
         OutPort.setOutDefault(var5);
         var6 = var1.apply0();
      } finally {
         OutPort.setOutDefault(var2);
         var5.close();
      }

      return var6;
   }

   public static void write(Object var0) {
      write(var0, current$Mnoutput$Mnport.apply0());
   }

   public static void write(Object var0, Object var1) {
      AbstractFormat var2 = Scheme.writeFormat;

      Consumer var3;
      try {
         var3 = (Consumer)var1;
      } catch (ClassCastException var4) {
         throw new WrongType(var4, "gnu.lists.AbstractFormat.format(java.lang.Object,gnu.lists.Consumer)", 3, var1);
      }

      var2.format(var0, var3);
   }

   public static void writeChar(Object var0) {
      writeChar(var0, OutPort.outDefault());
   }

   public static void writeChar(Object var0, OutPort var1) {
      Char var2;
      try {
         var2 = (Char)var0;
      } catch (ClassCastException var3) {
         throw new WrongType(var3, "char->integer", 1, var0);
      }

      Char.print(characters.char$To$Integer(var2), var1);
   }

   public Object apply0(ModuleMethod var1) {
      switch(var1.selector) {
      case 15:
         return openOutputString();
      case 19:
         forceOutput();
         return Values.empty;
      case 21:
         newline();
         return Values.empty;
      case 24:
         if(isCharReady()) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 42:
         return read();
      case 44:
         return readLine();
      case 48:
         transcriptOff();
         return Values.empty;
      default:
         return super.apply0(var1);
      }
   }

   public Object apply1(ModuleMethod var1, Object var2) {
      LineBufferedReader var15;
      InPort var16;
      Path var22;
      switch(var1.selector) {
      case 1:
         try {
            var22 = Path.valueOf(var2);
         } catch (ClassCastException var14) {
            throw new WrongType(var14, "open-input-file", 1, var2);
         }

         return openInputFile(var22);
      case 2:
         try {
            var22 = Path.valueOf(var2);
         } catch (ClassCastException var13) {
            throw new WrongType(var13, "open-output-file", 1, var2);
         }

         return openOutputFile(var22);
      case 3:
      case 4:
      case 5:
      case 6:
      case 13:
      case 15:
      case 17:
      case 20:
      case 22:
      case 25:
      case 27:
      case 29:
      case 31:
      case 33:
      case 38:
      case 43:
      case 45:
      case 46:
      default:
         return super.apply1(var1, var2);
      case 7:
         if(isInputPort(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 8:
         if(isOutputPort(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 9:
         return lambda1(var2);
      case 10:
         return lambda2(var2);
      case 11:
         return lambda3(var2);
      case 12:
         writeChar(var2);
         return Values.empty;
      case 14:
         CharSequence var21;
         try {
            var21 = (CharSequence)var2;
         } catch (ClassCastException var12) {
            throw new WrongType(var12, "open-input-string", 1, var2);
         }

         return openInputString(var21);
      case 16:
         CharArrayOutPort var20;
         try {
            var20 = (CharArrayOutPort)var2;
         } catch (ClassCastException var11) {
            throw new WrongType(var11, "get-output-string", 1, var2);
         }

         return getOutputString(var20);
      case 18:
         Procedure var19;
         try {
            var19 = (Procedure)var2;
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "call-with-output-string", 1, var2);
         }

         return callWithOutputString(var19);
      case 19:
         forceOutput(var2);
         return Values.empty;
      case 21:
         newline(var2);
         return Values.empty;
      case 23:
         if(isEofObject(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 24:
         if(isCharReady(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 26:
         write(var2);
         return Values.empty;
      case 28:
         display(var2);
         return Values.empty;
      case 30:
         return Char.make(inputPortReadState(var2));
      case 32:
         try {
            var15 = (LineBufferedReader)var2;
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "port-line", 1, var2);
         }

         return Integer.valueOf(portLine(var15));
      case 34:
         try {
            var15 = (LineBufferedReader)var2;
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "input-port-line-number", 1, var2);
         }

         return inputPortLineNumber(var15);
      case 35:
         return Integer.valueOf(portColumn(var2));
      case 36:
         return Integer.valueOf(inputPortColumnNumber(var2));
      case 37:
         return defaultPrompter(var2);
      case 39:
         TtyInPort var18;
         try {
            var18 = (TtyInPort)var2;
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "input-port-prompter", 1, var2);
         }

         return inputPortPrompter(var18);
      case 40:
         try {
            var16 = (InPort)var2;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "close-input-port", 1, var2);
         }

         return closeInputPort(var16);
      case 41:
         OutPort var17;
         try {
            var17 = (OutPort)var2;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "close-output-port", 1, var2);
         }

         return closeOutputPort(var17);
      case 42:
         try {
            var16 = (InPort)var2;
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "read", 1, var2);
         }

         return read(var16);
      case 44:
         try {
            var15 = (LineBufferedReader)var2;
         } catch (ClassCastException var3) {
            throw new WrongType(var3, "read-line", 1, var2);
         }

         return readLine(var15);
      case 47:
         transcriptOn(var2);
         return Values.empty;
      }
   }

   public Object apply2(ModuleMethod var1, Object var2, Object var3) {
      Procedure var25;
      Path var24;
      switch(var1.selector) {
      case 3:
         try {
            var24 = Path.valueOf(var2);
         } catch (ClassCastException var18) {
            throw new WrongType(var18, "call-with-input-file", 1, var2);
         }

         try {
            var25 = (Procedure)var3;
         } catch (ClassCastException var17) {
            throw new WrongType(var17, "call-with-input-file", 2, var3);
         }

         return callWithInputFile(var24, var25);
      case 4:
         try {
            var24 = Path.valueOf(var2);
         } catch (ClassCastException var16) {
            throw new WrongType(var16, "call-with-output-file", 1, var2);
         }

         try {
            var25 = (Procedure)var3;
         } catch (ClassCastException var15) {
            throw new WrongType(var15, "call-with-output-file", 2, var3);
         }

         return callWithOutputFile(var24, var25);
      case 5:
         try {
            var24 = Path.valueOf(var2);
         } catch (ClassCastException var14) {
            throw new WrongType(var14, "with-input-from-file", 1, var2);
         }

         try {
            var25 = (Procedure)var3;
         } catch (ClassCastException var13) {
            throw new WrongType(var13, "with-input-from-file", 2, var3);
         }

         return withInputFromFile(var24, var25);
      case 6:
         try {
            var24 = Path.valueOf(var2);
         } catch (ClassCastException var12) {
            throw new WrongType(var12, "with-output-to-file", 1, var2);
         }

         try {
            var25 = (Procedure)var3;
         } catch (ClassCastException var11) {
            throw new WrongType(var11, "with-output-to-file", 2, var3);
         }

         return withOutputToFile(var24, var25);
      case 12:
         OutPort var22;
         try {
            var22 = (OutPort)var3;
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "write-char", 2, var3);
         }

         writeChar(var2, var22);
         return Values.empty;
      case 17:
         CharSequence var21;
         try {
            var21 = (CharSequence)var2;
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "call-with-input-string", 1, var2);
         }

         try {
            var25 = (Procedure)var3;
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "call-with-input-string", 2, var3);
         }

         return callWithInputString(var21, var25);
      case 26:
         write(var2, var3);
         return Values.empty;
      case 28:
         display(var2, var3);
         return Values.empty;
      case 31:
         setPortLine$Ex(var2, var3);
         return Values.empty;
      case 33:
         setInputPortLineNumber$Ex(var2, var3);
         return Values.empty;
      case 38:
         TtyInPort var20;
         try {
            var20 = (TtyInPort)var2;
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "set-input-port-prompter!", 1, var2);
         }

         try {
            var25 = (Procedure)var3;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "set-input-port-prompter!", 2, var3);
         }

         setInputPortPrompter$Ex(var20, var25);
         return Values.empty;
      case 44:
         LineBufferedReader var19;
         try {
            var19 = (LineBufferedReader)var2;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "read-line", 1, var2);
         }

         Symbol var23;
         try {
            var23 = (Symbol)var3;
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "read-line", 2, var3);
         }

         return readLine(var19, var23);
      default:
         return super.apply2(var1, var2, var3);
      }
   }

   public int match0(ModuleMethod var1, CallContext var2) {
      switch(var1.selector) {
      case 15:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 19:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 21:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 24:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 42:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 44:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 48:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      default:
         return super.match0(var1, var2);
      }
   }

   public int match1(ModuleMethod var1, Object var2, CallContext var3) {
      switch(var1.selector) {
      case 1:
         if(Path.coerceToPathOrNull(var2) != null) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 2:
         if(Path.coerceToPathOrNull(var2) != null) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 3:
      case 4:
      case 5:
      case 6:
      case 13:
      case 15:
      case 17:
      case 20:
      case 22:
      case 25:
      case 27:
      case 29:
      case 31:
      case 33:
      case 38:
      case 43:
      case 45:
      case 46:
      default:
         return super.match1(var1, var2, var3);
      case 7:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 8:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 9:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 10:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 11:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 12:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 14:
         if(var2 instanceof CharSequence) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 16:
         if(!(var2 instanceof CharArrayOutPort)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 18:
         if(!(var2 instanceof Procedure)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 19:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 21:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 23:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 24:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 26:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 28:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 30:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 32:
         if(!(var2 instanceof LineBufferedReader)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 34:
         if(!(var2 instanceof LineBufferedReader)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 35:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 36:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 37:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 39:
         if(!(var2 instanceof TtyInPort)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 40:
         if(!(var2 instanceof InPort)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 41:
         if(!(var2 instanceof OutPort)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 42:
         if(!(var2 instanceof InPort)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 44:
         if(!(var2 instanceof LineBufferedReader)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 47:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      }
   }

   public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
      int var5 = -786431;
      switch(var1.selector) {
      case 3:
         if(Path.coerceToPathOrNull(var2) != null) {
            var4.value1 = var2;
            if(!(var3 instanceof Procedure)) {
               return -786430;
            }

            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }
         break;
      case 4:
         if(Path.coerceToPathOrNull(var2) != null) {
            var4.value1 = var2;
            if(!(var3 instanceof Procedure)) {
               return -786430;
            }

            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }
         break;
      case 5:
         if(Path.coerceToPathOrNull(var2) != null) {
            var4.value1 = var2;
            if(!(var3 instanceof Procedure)) {
               return -786430;
            }

            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }
         break;
      case 6:
         if(Path.coerceToPathOrNull(var2) != null) {
            var4.value1 = var2;
            if(!(var3 instanceof Procedure)) {
               return -786430;
            }

            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }
         break;
      case 12:
         var4.value1 = var2;
         if(!(var3 instanceof OutPort)) {
            return -786430;
         }

         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 17:
         if(var2 instanceof CharSequence) {
            var4.value1 = var2;
            if(!(var3 instanceof Procedure)) {
               return -786430;
            }

            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }
         break;
      case 26:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 28:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 31:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 33:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 38:
         if(var2 instanceof TtyInPort) {
            var4.value1 = var2;
            if(!(var3 instanceof Procedure)) {
               return -786430;
            }

            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }
         break;
      case 44:
         if(var2 instanceof LineBufferedReader) {
            var4.value1 = var2;
            if(!(var3 instanceof Symbol)) {
               return -786430;
            }

            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }
         break;
      default:
         var5 = super.match2(var1, var2, var3, var4);
      }

      return var5;
   }

   public final void run(CallContext var1) {
      Consumer var5 = var1.consumer;
      current$Mninput$Mnport = LocationProc.makeNamed(Lit0, InPort.inLocation);
      current$Mninput$Mnport.pushConverter(lambda$Fn1);
      current$Mnoutput$Mnport = LocationProc.makeNamed(Lit2, OutPort.outLocation);
      current$Mnoutput$Mnport.pushConverter(lambda$Fn2);
      current$Mnerror$Mnport = LocationProc.makeNamed(Lit4, OutPort.errLocation);
      current$Mnerror$Mnport.pushConverter(lambda$Fn3);
      port$Mnline = new GenericProc("port-line");
      GenericProc var6 = port$Mnline;
      Keyword var2 = Lit5;
      ModuleMethod var3 = set$Mnport$Mnline$Ex;
      ModuleMethod var4 = port$Mnline$Fn4;
      var6.setProperties(new Object[]{var2, var3, port$Mnline$Fn4});
      input$Mnport$Mnline$Mnnumber = new GenericProc("input-port-line-number");
      var6 = input$Mnport$Mnline$Mnnumber;
      var2 = Lit5;
      var3 = set$Mninput$Mnport$Mnline$Mnnumber$Ex;
      var4 = input$Mnport$Mnline$Mnnumber$Fn5;
      var6.setProperties(new Object[]{var2, var3, input$Mnport$Mnline$Mnnumber$Fn5});
      input$Mnport$Mnprompter = new GenericProc("input-port-prompter");
      var6 = input$Mnport$Mnprompter;
      var2 = Lit5;
      var3 = set$Mninput$Mnport$Mnprompter$Ex;
      var4 = input$Mnport$Mnprompter$Fn6;
      var6.setProperties(new Object[]{var2, var3, input$Mnport$Mnprompter$Fn6});
   }
}
