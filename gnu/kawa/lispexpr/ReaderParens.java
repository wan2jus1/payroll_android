package gnu.kawa.lispexpr;

import gnu.kawa.lispexpr.LispReader;
import gnu.kawa.lispexpr.ReadTableEntry;
import gnu.text.Lexer;
import gnu.text.LineBufferedReader;
import gnu.text.SyntaxException;
import java.io.IOException;

public class ReaderParens extends ReadTableEntry {

   private static ReaderParens instance;
   char close;
   Object command;
   int kind;
   char open;


   public ReaderParens(char var1, char var2, int var3, Object var4) {
      this.open = var1;
      this.close = var2;
      this.kind = var3;
      this.command = var4;
   }

   public static ReaderParens getInstance(char var0, char var1) {
      return getInstance(var0, var1, 5);
   }

   public static ReaderParens getInstance(char var0, char var1, int var2) {
      if(var0 == 40 && var1 == 41 && var2 == 5) {
         if(instance == null) {
            instance = new ReaderParens(var0, var1, var2, (Object)null);
         }

         return instance;
      } else {
         return new ReaderParens(var0, var1, var2, (Object)null);
      }
   }

   public static ReaderParens getInstance(char var0, char var1, int var2, Object var3) {
      return var3 == null?getInstance(var0, var1, var2):new ReaderParens(var0, var1, var2, var3);
   }

   public static Object readList(LispReader param0, int param1, int param2, int param3) throws IOException, SyntaxException {
      // $FF: Couldn't be decompiled
   }

   public int getKind() {
      return this.kind;
   }

   public Object read(Lexer var1, int var2, int var3) throws IOException, SyntaxException {
      Object var5 = readList((LispReader)var1, var2, var3, this.close);
      Object var4 = var5;
      if(this.command != null) {
         LineBufferedReader var6 = var1.getPort();
         var2 = var6.getLineNumber();
         var3 = var6.getColumnNumber();
         var4 = ((LispReader)var1).makePair(this.command, var2, var3);
         ((LispReader)var1).setCdr(var4, var5);
      }

      return var4;
   }
}
