package gnu.kawa.lispexpr;

import gnu.kawa.lispexpr.ReadTableEntry;
import gnu.lists.Sequence;
import gnu.mapping.Values;
import gnu.text.Lexer;
import gnu.text.SyntaxException;
import java.io.IOException;

public class ReaderIgnoreRestOfLine extends ReadTableEntry {

   static ReaderIgnoreRestOfLine instance = new ReaderIgnoreRestOfLine();


   public static ReaderIgnoreRestOfLine getInstance() {
      return instance;
   }

   public Object read(Lexer var1, int var2, int var3) throws IOException, SyntaxException {
      do {
         var2 = var1.read();
         if(var2 < 0) {
            return Sequence.eofValue;
         }
      } while(var2 != 10 && var2 != 13);

      return Values.empty;
   }
}
