package gnu.kawa.lispexpr;

import gnu.kawa.lispexpr.LispReader;
import gnu.kawa.lispexpr.ReadTableEntry;
import gnu.lists.FVector;
import gnu.text.Lexer;
import gnu.text.LineBufferedReader;
import gnu.text.SyntaxException;
import java.io.IOException;

public class ReaderVector extends ReadTableEntry {

   char close;


   public ReaderVector(char var1) {
      this.close = var1;
   }

   public static FVector readVector(LispReader param0, LineBufferedReader param1, int param2, char param3) throws IOException, SyntaxException {
      // $FF: Couldn't be decompiled
   }

   public Object read(Lexer var1, int var2, int var3) throws IOException, SyntaxException {
      return readVector((LispReader)var1, var1.getPort(), var3, this.close);
   }
}
