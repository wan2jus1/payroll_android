package gnu.kawa.lispexpr;

import gnu.kawa.lispexpr.ReadTableEntry;
import gnu.text.Lexer;
import gnu.text.SyntaxException;
import java.io.IOException;

public class ReaderString extends ReadTableEntry {

   public static String readString(Lexer param0, int param1, int param2) throws IOException, SyntaxException {
      // $FF: Couldn't be decompiled
   }

   public Object read(Lexer var1, int var2, int var3) throws IOException, SyntaxException {
      return readString(var1, var2, var3);
   }
}
