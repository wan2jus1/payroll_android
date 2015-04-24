package gnu.kawa.lispexpr;

import gnu.kawa.lispexpr.LispReader;
import gnu.kawa.lispexpr.ReadTableEntry;
import gnu.lists.PairWithPosition;
import gnu.text.Lexer;
import gnu.text.SyntaxException;
import java.io.IOException;

public class ReaderQuote extends ReadTableEntry {

   Object magicSymbol;
   Object magicSymbol2;
   char next;


   public ReaderQuote(Object var1) {
      this.magicSymbol = var1;
   }

   public ReaderQuote(Object var1, char var2, Object var3) {
      this.next = var2;
      this.magicSymbol = var1;
      this.magicSymbol2 = var3;
   }

   public Object read(Lexer var1, int var2, int var3) throws IOException, SyntaxException {
      LispReader var5 = (LispReader)var1;
      String var6 = var5.getName();
      var2 = var5.getLineNumber();
      var3 = var5.getColumnNumber();
      Object var4 = this.magicSymbol;
      Object var9 = var4;
      int var7;
      if(this.next != 0) {
         var7 = var5.read();
         if(var7 == this.next) {
            var9 = this.magicSymbol2;
         } else {
            var9 = var4;
            if(var7 >= 0) {
               var5.unread(var7);
               var9 = var4;
            }
         }
      }

      var7 = var5.getLineNumber();
      int var8 = var5.getColumnNumber();
      return PairWithPosition.make(var9, PairWithPosition.make(var5.readObject(), var5.makeNil(), var6, var7 + 1, var8 + 1), var6, var2 + 1, var3 + 1);
   }
}
