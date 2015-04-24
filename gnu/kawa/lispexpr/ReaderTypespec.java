package gnu.kawa.lispexpr;

import gnu.kawa.lispexpr.ReadTableEntry;
import gnu.text.Lexer;
import gnu.text.SyntaxException;
import java.io.IOException;

public class ReaderTypespec extends ReadTableEntry {

   public int getKind() {
      return 6;
   }

   public Object read(Lexer param1, int param2, int param3) throws IOException, SyntaxException {
      // $FF: Couldn't be decompiled
   }
}
