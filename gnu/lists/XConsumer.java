package gnu.lists;

import gnu.lists.Consumer;

public interface XConsumer extends Consumer {

   void beginEntity(Object var1);

   void endEntity();

   void writeCDATA(char[] var1, int var2, int var3);

   void writeComment(char[] var1, int var2, int var3);

   void writeProcessingInstruction(String var1, char[] var2, int var3, int var4);
}
