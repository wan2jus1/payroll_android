package gnu.mapping;

import gnu.lists.CharSeq;
import gnu.lists.FString;
import gnu.mapping.InPort;
import gnu.text.NullReader;
import gnu.text.Path;
import java.io.IOException;
import java.io.Reader;

public class CharArrayInPort extends InPort {

   static final Path stringPath = Path.valueOf("<string>");


   public CharArrayInPort(String var1) {
      this((char[])var1.toCharArray());
   }

   public CharArrayInPort(char[] var1) {
      this(var1, var1.length);
   }

   public CharArrayInPort(char[] var1, int var2) {
      super((Reader)NullReader.nullReader, stringPath);

      try {
         this.setBuffer(var1);
      } catch (IOException var3) {
         throw new Error(var3.toString());
      }

      this.limit = var2;
   }

   public CharArrayInPort make(CharSequence var1) {
      if(var1 instanceof FString) {
         FString var5 = (FString)var1;
         return new CharArrayInPort(var5.data, var5.size);
      } else {
         int var4 = var1.length();
         char[] var2 = new char[var4];
         if(var1 instanceof String) {
            ((String)var1).getChars(0, var4, var2, 0);
         } else if(!(var1 instanceof CharSeq)) {
            int var3 = var4;

            while(true) {
               --var3;
               if(var3 < 0) {
                  break;
               }

               var2[var3] = var1.charAt(var3);
            }
         } else {
            ((CharSeq)var1).getChars(0, var4, var2, 0);
         }

         return new CharArrayInPort(var2, var4);
      }
   }

   public int read() throws IOException {
      return this.pos >= this.limit?-1:super.read();
   }
}
