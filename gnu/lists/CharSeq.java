package gnu.lists;

import gnu.lists.Consumer;
import gnu.lists.Sequence;
import java.io.IOException;

public interface CharSeq extends CharSequence, Sequence {

   char charAt(int var1);

   void consume(int var1, int var2, Consumer var3);

   void fill(char var1);

   void fill(int var1, int var2, char var3);

   void getChars(int var1, int var2, char[] var3, int var4);

   int length();

   void setCharAt(int var1, char var2);

   CharSequence subSequence(int var1, int var2);

   String toString();

   void writeTo(int var1, int var2, Appendable var3) throws IOException;

   void writeTo(Appendable var1) throws IOException;
}
