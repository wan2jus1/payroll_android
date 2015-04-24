package gnu.kawa.lispexpr;

import gnu.kawa.lispexpr.ReadTable;
import gnu.kawa.lispexpr.ReadTableEntry;
import gnu.kawa.lispexpr.ReaderDispatchMisc;
import gnu.kawa.lispexpr.ReaderQuote;
import gnu.kawa.lispexpr.ReaderVector;
import gnu.kawa.lispexpr.ReaderXmlElement;
import gnu.kawa.util.RangeTable;
import gnu.mapping.Values;
import gnu.text.Lexer;
import gnu.text.SyntaxException;
import java.io.IOException;

public class ReaderDispatch extends ReadTableEntry {

   int kind;
   RangeTable table = new RangeTable();


   public ReaderDispatch() {
      this.kind = 5;
   }

   public ReaderDispatch(boolean var1) {
      byte var2;
      if(var1) {
         var2 = 6;
      } else {
         var2 = 5;
      }

      this.kind = var2;
   }

   public static ReaderDispatch create(ReadTable var0) {
      ReaderDispatch var1 = new ReaderDispatch();
      ReaderDispatchMisc var2 = ReaderDispatchMisc.getInstance();
      var1.set(58, var2);
      var1.set(66, var2);
      var1.set(68, var2);
      var1.set(69, var2);
      var1.set(70, var2);
      var1.set(73, var2);
      var1.set(79, var2);
      var1.set(82, var2);
      var1.set(83, var2);
      var1.set(84, var2);
      var1.set(85, var2);
      var1.set(88, var2);
      var1.set(124, var2);
      var1.set(59, var2);
      var1.set(33, var2);
      var1.set(92, var2);
      var1.set(61, var2);
      var1.set(35, var2);
      var1.set(47, var2);
      var1.set(39, new ReaderQuote(var0.makeSymbol("function")));
      var1.set(40, new ReaderVector(')'));
      var1.set(60, new ReaderXmlElement());
      return var1;
   }

   public int getKind() {
      return this.kind;
   }

   public ReadTableEntry lookup(int var1) {
      return (ReadTableEntry)this.table.lookup(var1, (Object)null);
   }

   public Object read(Lexer var1, int var2, int var3) throws IOException, SyntaxException {
      var2 = -1;

      while(true) {
         var3 = var1.read();
         if(var3 < 0) {
            var1.eofError("unexpected EOF after " + (char)var3);
         }

         if(var3 > 65536) {
            break;
         }

         int var5 = Character.digit((char)var3, 10);
         if(var5 < 0) {
            var3 = Character.toUpperCase((char)var3);
            break;
         }

         if(var2 < 0) {
            var2 = var5;
         } else {
            var2 = var2 * 10 + var5;
         }
      }

      ReadTableEntry var4 = (ReadTableEntry)this.table.lookup(var3, (Object)null);
      if(var4 == null) {
         var1.error('e', var1.getName(), var1.getLineNumber() + 1, var1.getColumnNumber(), "invalid dispatch character \'" + (char)var3 + '\'');
         return Values.empty;
      } else {
         return var4.read(var1, var3, var2);
      }
   }

   public void set(int var1, Object var2) {
      this.table.set(var1, var1, var2);
   }
}
