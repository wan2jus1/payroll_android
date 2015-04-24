package com.google.appinventor.components.runtime.util;

import android.util.Log;
import com.google.appinventor.components.runtime.util.BiggerFuture;
import gnu.expr.Language;
import gnu.mapping.Environment;
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

   private static final int REPL_STACK_SIZE = 262144;
   Language language;
   Socket socket;


   public TelnetRepl(Language var1, Socket var2) {
      this.language = var1;
      this.socket = var2;
   }

   public static Thread serve(Language var0, Socket var1) throws IOException {
      Telnet var3 = new Telnet(var1, true);
      TelnetOutputStream var2 = var3.getOutputStream();
      TelnetInputStream var6 = var3.getInputStream();
      OutPort var5 = new OutPort(var2, FilePath.valueOf((String)"/dev/stdout"));
      TtyInPort var7 = new TtyInPort(var6, FilePath.valueOf((String)"/dev/stdin"), var5);
      BiggerFuture var4 = new BiggerFuture(new TelnetRepl(var0, var1), var7, var5, var5, "Telnet Repl Thread", 262144L);
      var4.start();
      return var4;
   }

   public Object apply0() {
      Thread var1 = Thread.currentThread();
      if(var1.getContextClassLoader() == null) {
         var1.setContextClassLoader(Telnet.class.getClassLoader());
      }

      Values var10;
      try {
         Shell.run(this.language, Environment.getCurrent());
         var10 = Values.empty;
      } catch (RuntimeException var8) {
         Log.d("TelnetRepl", "Repl is exiting with error " + var8.getMessage());
         var8.printStackTrace();
         throw var8;
      } finally {
         try {
            this.socket.close();
         } catch (IOException var7) {
            ;
         }

      }

      return var10;
   }
}
