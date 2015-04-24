package gnu.kawa.lispexpr;

import gnu.kawa.lispexpr.ReadTableEntry;
import gnu.text.Lexer;
import gnu.text.SyntaxException;
import java.io.IOException;
import java.util.regex.Pattern;

public class ReaderDispatchMisc extends ReadTableEntry {

   private static ReaderDispatchMisc instance = new ReaderDispatchMisc();
   protected int code;


   public ReaderDispatchMisc() {
      this.code = -1;
   }

   public ReaderDispatchMisc(int var1) {
      this.code = var1;
   }

   public static ReaderDispatchMisc getInstance() {
      return instance;
   }

   public static Pattern readRegex(Lexer param0, int param1, int param2) throws IOException, SyntaxException {
      // $FF: Couldn't be decompiled
   }

   public Object read(Lexer param1, int param2, int param3) throws IOException, SyntaxException {
      // $FF: Couldn't be decompiled
   }
}
