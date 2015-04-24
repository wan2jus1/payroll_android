package gnu.kawa.lispexpr;

import gnu.kawa.lispexpr.ReaderMisc;
import gnu.mapping.Procedure;
import gnu.math.IntNum;
import gnu.text.Char;
import gnu.text.Lexer;
import gnu.text.LineBufferedReader;
import gnu.text.SyntaxException;
import java.io.IOException;

public class ReaderDispatchMacro extends ReaderMisc {

   Procedure procedure;


   public ReaderDispatchMacro(Procedure var1) {
      super(5);
      this.procedure = var1;
   }

   public Procedure getProcedure() {
      return this.procedure;
   }

   public Object read(Lexer var1, int var2, int var3) throws IOException, SyntaxException {
      LineBufferedReader var4 = var1.getPort();

      try {
         Object var8 = this.procedure.apply3(var4, Char.make(var2), IntNum.make(var3));
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
