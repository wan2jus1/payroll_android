package gnu.q2.lang;

import gnu.kawa.lispexpr.ReaderDispatchMisc;
import gnu.q2.lang.Q2Read;
import gnu.text.Lexer;
import gnu.text.SyntaxException;
import java.io.IOException;

class Q2ReaderParens extends ReaderDispatchMisc {

   public Object read(Lexer var1, int var2, int var3) throws IOException, SyntaxException {
      Q2Read var8 = (Q2Read)var1;
      char var4 = var8.pushNesting('(');

      Object var5;
      try {
         var5 = var8.readCommand(true);
         if(var8.getPort().read() != 41) {
            var8.error("missing \')\'");
         }
      } finally {
         var8.popNesting(var4);
      }

      return var5;
   }
}
