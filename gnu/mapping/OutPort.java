package gnu.mapping;

import gnu.lists.AbstractFormat;
import gnu.lists.Consumable;
import gnu.lists.Consumer;
import gnu.lists.PrintConsumer;
import gnu.mapping.Environment;
import gnu.mapping.LogWriter;
import gnu.mapping.ThreadLocation;
import gnu.text.Path;
import gnu.text.PrettyWriter;
import gnu.text.Printable;
import gnu.text.WriterManager;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.NumberFormat;

public class OutPort extends PrintConsumer implements Printable {

   private static OutPort errInitial = new OutPort(new LogWriter(new OutputStreamWriter(System.err)), true, true, Path.valueOf("/dev/stderr"));
   public static final ThreadLocation errLocation;
   static Writer logFile;
   static OutPort outInitial = new OutPort(new LogWriter(new BufferedWriter(new OutputStreamWriter(System.out))), true, true, Path.valueOf("/dev/stdout"));
   public static final ThreadLocation outLocation = new ThreadLocation("out-default");
   private Writer base;
   protected PrettyWriter bout;
   NumberFormat numberFormat;
   public AbstractFormat objectFormat;
   Path path;
   public boolean printReadable;
   protected Object unregisterRef;


   static {
      outLocation.setGlobal(outInitial);
      errLocation = new ThreadLocation("err-default");
      errLocation.setGlobal(errInitial);
   }

   protected OutPort(OutPort var1, boolean var2) {
      this(var1, var1.bout, var2);
   }

   public OutPort(OutputStream var1) {
      this((OutputStream)var1, (Path)null);
   }

   public OutPort(OutputStream var1, Path var2) {
      this(new OutputStreamWriter(var1), true, var2);
   }

   public OutPort(Writer var1) {
      PrettyWriter var2;
      if(var1 instanceof OutPort) {
         var2 = ((OutPort)var1).bout;
      } else {
         var2 = new PrettyWriter(var1, false);
      }

      this(var1, var2, false);
   }

   public OutPort(Writer var1, Path var2) {
      this(var1, false, false);
      this.path = var2;
   }

   protected OutPort(Writer var1, PrettyWriter var2, boolean var3) {
      super((Writer)var2, var3);
      this.bout = var2;
      this.base = var1;
      if(this.closeOnExit()) {
         this.unregisterRef = WriterManager.instance.register(var2);
      }

   }

   protected OutPort(Writer var1, boolean var2) {
      PrettyWriter var3;
      if(var1 instanceof OutPort) {
         var3 = ((OutPort)var1).bout;
      } else {
         var3 = new PrettyWriter(var1, true);
      }

      this(var1, var3, var2);
   }

   public OutPort(Writer var1, boolean var2, Path var3) {
      this(var1, false, var2);
      this.path = var3;
   }

   public OutPort(Writer var1, boolean var2, boolean var3) {
      this(var1, new PrettyWriter(var1, var2), var3);
   }

   public OutPort(Writer var1, boolean var2, boolean var3, Path var4) {
      this(var1, new PrettyWriter(var1, var2), var3);
      this.path = var4;
   }

   public static void closeLogFile() throws IOException {
      if(logFile != null) {
         logFile.close();
         logFile = null;
      }

      if(outInitial.base instanceof LogWriter) {
         ((LogWriter)outInitial.base).setLogFile((Writer)((Writer)null));
      }

      if(errInitial.base instanceof LogWriter) {
         ((LogWriter)errInitial.base).setLogFile((Writer)((Writer)null));
      }

   }

   public static OutPort errDefault() {
      return (OutPort)errLocation.get();
   }

   protected static final boolean isWordChar(char var0) {
      return Character.isJavaIdentifierPart(var0) || var0 == 45 || var0 == 43;
   }

   public static OutPort openFile(Object var0) throws IOException {
      Object var1 = Environment.user().get((Object)"port-char-encoding");
      Path var2 = Path.valueOf(var0);
      BufferedOutputStream var3 = new BufferedOutputStream(var2.openOutputStream());
      OutputStreamWriter var4;
      if(var1 != null && var1 != Boolean.TRUE) {
         var0 = var1;
         if(var1 == Boolean.FALSE) {
            var0 = "8859_1";
         }

         var4 = new OutputStreamWriter(var3, var0.toString());
      } else {
         var4 = new OutputStreamWriter(var3);
      }

      return new OutPort(var4, var2);
   }

   public static OutPort outDefault() {
      return (OutPort)outLocation.get();
   }

   public static void runCleanups() {
      WriterManager.instance.run();
   }

   public static void setErrDefault(OutPort var0) {
      errLocation.set(var0);
   }

   public static void setLogFile(String var0) throws IOException {
      if(logFile != null) {
         closeLogFile();
      }

      logFile = new PrintWriter(new BufferedWriter(new FileWriter(var0)));
      if(outInitial.base instanceof LogWriter) {
         ((LogWriter)outInitial.base).setLogFile((Writer)logFile);
      }

      if(errInitial.base instanceof LogWriter) {
         ((LogWriter)errInitial.base).setLogFile((Writer)logFile);
      }

   }

   public static void setOutDefault(OutPort var0) {
      outLocation.set(var0);
   }

   public void clearBuffer() {
      this.bout.clearBuffer();
   }

   public void close() {
      try {
         if(this.base instanceof OutPort && ((OutPort)this.base).bout == this.bout) {
            this.base.close();
         } else {
            this.out.close();
         }
      } catch (IOException var2) {
         this.setError();
      }

      WriterManager.instance.unregister(this.unregisterRef);
   }

   protected boolean closeOnExit() {
      return true;
   }

   public void closeThis() {
      try {
         if(!(this.base instanceof OutPort) || ((OutPort)this.base).bout != this.bout) {
            this.bout.closeThis();
         }
      } catch (IOException var2) {
         this.setError();
      }

      WriterManager.instance.unregister(this.unregisterRef);
   }

   public void echo(char[] var1, int var2, int var3) throws IOException {
      if(this.base instanceof LogWriter) {
         ((LogWriter)this.base).echo(var1, var2, var3);
      }

   }

   public void endAttribute() {
      if(this.objectFormat != null) {
         this.objectFormat.endAttribute(this);
      } else {
         this.print(' ');
      }
   }

   public void endElement() {
      if(this.objectFormat != null) {
         this.objectFormat.endElement(this);
      } else {
         this.print(')');
      }
   }

   public void endLogicalBlock(String var1) {
      this.bout.endLogicalBlock(var1);
   }

   public void freshLine() {
      if(this.bout.getColumnNumber() != 0) {
         this.println();
      }

   }

   public int getColumnNumber() {
      return this.bout.getColumnNumber();
   }

   public void print(double var1) {
      if(this.numberFormat == null) {
         super.print(var1);
      } else {
         this.print((String)this.numberFormat.format(var1));
      }
   }

   public void print(float var1) {
      if(this.numberFormat == null) {
         super.print(var1);
      } else {
         this.print((String)this.numberFormat.format((double)var1));
      }
   }

   public void print(int var1) {
      if(this.numberFormat == null) {
         super.print(var1);
      } else {
         this.print((String)this.numberFormat.format((long)var1));
      }
   }

   public void print(long var1) {
      if(this.numberFormat == null) {
         super.print(var1);
      } else {
         this.print((String)this.numberFormat.format(var1));
      }
   }

   public void print(Consumer var1) {
      var1.write("#<output-port");
      if(this.path != null) {
         var1.write(32);
         var1.write(this.path.toString());
      }

      var1.write(62);
   }

   public void print(Object var1) {
      if(this.objectFormat != null) {
         this.objectFormat.writeObject(var1, (PrintConsumer)this);
      } else if(var1 instanceof Consumable) {
         ((Consumable)var1).consume(this);
      } else {
         Object var2 = var1;
         if(var1 == null) {
            var2 = "null";
         }

         super.print(var2);
      }
   }

   public void print(String var1) {
      String var2 = var1;
      if(var1 == null) {
         var2 = "(null)";
      }

      this.write(var2);
   }

   public void print(boolean var1) {
      if(this.objectFormat == null) {
         super.print(var1);
      } else {
         this.objectFormat.writeBoolean(var1, this);
      }
   }

   public void setColumnNumber(int var1) {
      this.bout.setColumnNumber(var1);
   }

   public void setIndentation(int var1, boolean var2) {
      this.bout.addIndentation(var1, var2);
   }

   public void startAttribute(Object var1) {
      if(this.objectFormat != null) {
         this.objectFormat.startAttribute(var1, this);
      } else {
         this.print(' ');
         this.print((Object)var1);
         this.print((String)": ");
      }
   }

   public void startElement(Object var1) {
      if(this.objectFormat != null) {
         this.objectFormat.startElement(var1, this);
      } else {
         this.print('(');
         this.print((Object)var1);
      }
   }

   public void startLogicalBlock(String var1, String var2, int var3) {
      this.bout.startLogicalBlock(var1, false, var2);
      PrettyWriter var4 = this.bout;
      if(var1 != null) {
         var3 -= var1.length();
      }

      var4.addIndentation(var3, false);
   }

   public void startLogicalBlock(String var1, boolean var2, String var3) {
      this.bout.startLogicalBlock(var1, var2, var3);
   }

   public void writeBreak(int var1) {
      this.bout.writeBreak(var1);
   }

   public void writeBreakFill() {
      this.writeBreak(70);
   }

   public void writeBreakLinear() {
      this.writeBreak(78);
   }

   public void writeSpaceFill() {
      this.write(32);
      this.writeBreak(70);
   }

   public void writeSpaceLinear() {
      this.write(32);
      this.writeBreak(78);
   }

   public void writeWordEnd() {
      this.bout.writeWordEnd();
   }

   public void writeWordStart() {
      this.bout.writeWordStart();
   }
}
