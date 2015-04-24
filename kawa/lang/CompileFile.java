package kawa.lang;

import gnu.expr.Compilation;
import gnu.expr.Language;
import gnu.mapping.InPort;
import gnu.mapping.WrappedException;
import gnu.text.SourceMessages;
import gnu.text.SyntaxException;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CompileFile {

   public static final Compilation read(InPort var0, SourceMessages var1) throws IOException, SyntaxException {
      return Language.getDefaultLanguage().parse(var0, var1, 0);
   }

   public static final Compilation read(String var0, SourceMessages var1) throws IOException, SyntaxException {
      try {
         InPort var2 = InPort.openFile(var0);
         Compilation var5 = read((InPort)var2, var1);
         var2.close();
         return var5;
      } catch (FileNotFoundException var3) {
         throw new WrappedException("compile-file: file not found: " + var0, var3);
      } catch (IOException var4) {
         throw new WrappedException("compile-file: read-error: " + var0, var4);
      }
   }
}
