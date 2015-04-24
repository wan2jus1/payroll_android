package gnu.mapping;

import gnu.lists.Consumer;
import gnu.mapping.Environment;
import gnu.mapping.OutPort;
import gnu.mapping.ThreadLocation;
import gnu.mapping.TtyInPort;
import gnu.text.LineBufferedReader;
import gnu.text.Path;
import gnu.text.Printable;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

public class InPort extends LineBufferedReader implements Printable {

   public static final ThreadLocation inLocation = new ThreadLocation("in-default");
   private static InPort systemInPort = new TtyInPort(System.in, Path.valueOf("/dev/stdin"), OutPort.outInitial);


   static {
      inLocation.setGlobal(systemInPort);
   }

   public InPort(InputStream var1) {
      super((InputStream)var1);
   }

   public InPort(InputStream var1, Path var2) {
      this((InputStream)var1);
      this.setPath(var2);
   }

   public InPort(InputStream var1, Path var2, Object var3) throws UnsupportedEncodingException {
      this((Reader)convertToReader(var1, var3), var2);
      if(var3 == Boolean.FALSE) {
         try {
            this.setBuffer(new char[2048]);
         } catch (IOException var4) {
            ;
         }
      } else {
         this.setConvertCR(true);
      }
   }

   public InPort(Reader var1) {
      super((Reader)var1);
   }

   public InPort(Reader var1, Path var2) {
      this((Reader)var1);
      this.setPath(var2);
   }

   public static Reader convertToReader(InputStream var0, Object var1) {
      if(var1 != null && var1 != Boolean.TRUE) {
         String var4;
         if(var1 == Boolean.FALSE) {
            var4 = "8859_1";
         } else {
            var4 = var1.toString();
         }

         try {
            InputStreamReader var3 = new InputStreamReader(var0, var4);
            return var3;
         } catch (UnsupportedEncodingException var2) {
            throw new RuntimeException("unknown character encoding: " + var4);
         }
      } else {
         return new InputStreamReader(var0);
      }
   }

   public static InPort inDefault() {
      return (InPort)inLocation.get();
   }

   public static InPort openFile(InputStream var0, Object var1) throws UnsupportedEncodingException {
      return new InPort(var0, Path.valueOf(var1), Environment.user().get((Object)"port-char-encoding"));
   }

   public static InPort openFile(Object var0) throws IOException {
      Path var1 = Path.valueOf(var0);
      return openFile(new BufferedInputStream(var1.openInputStream()), var1);
   }

   public static void setInDefault(InPort var0) {
      inLocation.set(var0);
   }

   public void print(Consumer var1) {
      var1.write("#<input-port");
      String var2 = this.getName();
      if(var2 != null) {
         var1.write(32);
         var1.write(var2);
      }

      var1.write(62);
   }
}
