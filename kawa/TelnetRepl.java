package kawa;

import gnu.expr.Language;
import gnu.mapping.Environment;
import gnu.mapping.Future;
import gnu.mapping.OutPort;
import gnu.mapping.Procedure0;
import gnu.mapping.TtyInPort;
import gnu.mapping.Values;
import gnu.text.FilePath;
import java.io.IOException;
import java.net.Socket;
import kawa.Shell;
import kawa.Telnet;
import kawa.TelnetInputStream;
import kawa.TelnetOutputStream;

public class TelnetRepl extends Procedure0 {

   Language language;
   Socket socket;


   public TelnetRepl(Language var1, Socket var2) {
      this.language = var1;
      this.socket = var2;
   }

   public static void serve(Language var0, Socket var1) throws IOException {
      Telnet var3 = new Telnet(var1, true);
      TelnetOutputStream var2 = var3.getOutputStream();
      TelnetInputStream var5 = var3.getInputStream();
      OutPort var4 = new OutPort(var2, FilePath.valueOf((String)"/dev/stdout"));
      TtyInPort var6 = new TtyInPort(var5, FilePath.valueOf((String)"/dev/stdin"), var4);
      (new Future(new TelnetRepl(var0, var1), var6, var4, var4)).start();
   }

   public Object apply0() {
      Values var1;
      try {
         Shell.run(this.language, Environment.getCurrent());
         var1 = Values.empty;
      } finally {
         try {
            this.socket.close();
         } catch (IOException var6) {
            ;
         }

      }

      return var1;
   }
}
