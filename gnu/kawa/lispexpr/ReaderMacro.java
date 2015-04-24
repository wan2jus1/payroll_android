package gnu.kawa.lispexpr;

import gnu.kawa.lispexpr.ReaderMisc;
import gnu.mapping.Procedure;
import gnu.text.Char;
import gnu.text.Lexer;
import gnu.text.LineBufferedReader;
import gnu.text.SyntaxException;
import java.io.IOException;

public class ReaderMacro extends ReaderMisc {

   Procedure procedure;


   public ReaderMacro(Procedure var1) {
      super(5);
      this.procedure = var1;
   }

   public ReaderMacro(Procedure var1, boolean var2) {
      byte var3;
      if(var2) {
         var3 = 6;
      } else {
         var3 = 5;
      }

      super(var3);
      this.procedure = var1;
   }

   public Procedure getProcedure() {
      return this.procedure;
   }

   public boolean isNonTerminating() {
      return this.kind == 6;
   }

   public Object read(Lexer var1, int var2, int var3) throws IOException, SyntaxException {
      LineBufferedReader var4 = var1.getPort();

      try {
         Object var8 = this.procedure.apply2(var4, Char.make(var2));
         return var8;
      } catch (IOException var5) {
         throw var5;
      } catch (SyntaxException var6) {
         throw var6;
      } catch (Throwable var7) {
         var1.fatal("reader macro \'" + this.procedure + "\' threw: " + var7);
         return null;
      }
   }
}
