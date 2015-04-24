package gnu.kawa.lispexpr;

import gnu.kawa.lispexpr.LispReader;
import gnu.kawa.lispexpr.ReadTable;
import gnu.kawa.lispexpr.ReadTableEntry;
import gnu.text.Lexer;
import gnu.text.SyntaxException;
import java.io.IOException;

public class ReaderColon extends ReadTableEntry {

   public int getKind() {
      return 6;
   }

   public Object read(Lexer var1, int var2, int var3) throws IOException, SyntaxException {
      LispReader var6 = (LispReader)var1;
      ReadTable var4 = ReadTable.getCurrent();
      int var5 = var6.tokenBufferLength;
      var3 = var2;
      if(var2 == var4.postfixLookupOperator) {
         var3 = var6.read();
         if(var3 == 58) {
            return var4.makeSymbol("::");
         }

         var6.tokenBufferAppend(var2);
      }

      return var6.readAndHandleToken(var3, var5, var4);
   }
}
