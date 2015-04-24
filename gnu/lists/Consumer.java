package gnu.lists;


public interface Consumer extends Appendable {

   Consumer append(char var1);

   Consumer append(CharSequence var1);

   Consumer append(CharSequence var1, int var2, int var3);

   void endAttribute();

   void endDocument();

   void endElement();

   boolean ignoring();

   void startAttribute(Object var1);

   void startDocument();

   void startElement(Object var1);

   void write(int var1);

   void write(CharSequence var1, int var2, int var3);

   void write(String var1);

   void write(char[] var1, int var2, int var3);

   void writeBoolean(boolean var1);

   void writeDouble(double var1);

   void writeFloat(float var1);

   void writeInt(int var1);

   void writeLong(long var1);

   void writeObject(Object var1);
}
