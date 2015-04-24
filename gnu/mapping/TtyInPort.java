package gnu.mapping;

import gnu.mapping.InPort;
import gnu.mapping.OutPort;
import gnu.mapping.Procedure;
import gnu.text.Path;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public class TtyInPort extends InPort {

   protected boolean promptEmitted;
   protected Procedure prompter;
   protected OutPort tie;


   public TtyInPort(InputStream var1, Path var2, OutPort var3) {
      super((InputStream)var1, var2);
      this.setConvertCR(true);
      this.tie = var3;
   }

   public TtyInPort(Reader var1, Path var2, OutPort var3) {
      super((Reader)var1, var2);
      this.setConvertCR(true);
      this.tie = var3;
   }

   public void emitPrompt(String var1) throws IOException {
      this.tie.print((String)var1);
      this.tie.flush();
      this.tie.clearBuffer();
   }

   public int fill(int var1) throws IOException {
      var1 = this.in.read(this.buffer, this.pos, var1);
      if(this.tie != null && var1 > 0) {
         this.tie.echo(this.buffer, this.pos, var1);
      }

      return var1;
   }

   public Procedure getPrompter() {
      return this.prompter;
   }

   public void lineStart(boolean param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public int read() throws IOException {
      if(this.tie != null) {
         this.tie.flush();
      }

      int var2 = super.read();
      if(var2 < 0) {
         boolean var3 = this.promptEmitted;
         boolean var1;
         if(this.tie != null) {
            var1 = true;
         } else {
            var1 = false;
         }

         if(var1 & var3) {
            this.tie.println();
         }
      }

      this.promptEmitted = false;
      return var2;
   }

   public int read(char[] var1, int var2, int var3) throws IOException {
      if(this.tie != null) {
         this.tie.flush();
      }

      var3 = super.read(var1, var2, var3);
      if(var3 < 0) {
         boolean var4 = this.promptEmitted;
         boolean var5;
         if(this.tie != null) {
            var5 = true;
         } else {
            var5 = false;
         }

         if(var5 & var4) {
            this.tie.println();
         }
      }

      this.promptEmitted = false;
      return var3;
   }

   public void setPrompter(Procedure var1) {
      this.prompter = var1;
   }
}
