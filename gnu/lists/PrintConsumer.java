package gnu.lists;

import gnu.lists.Consumer;
import gnu.lists.ConsumerWriter;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;

public class PrintConsumer extends PrintWriter implements Appendable, Consumer {

   public PrintConsumer(Consumer var1, boolean var2) {
      Object var3;
      if(var1 instanceof Writer) {
         var3 = (Writer)var1;
      } else {
         var3 = new ConsumerWriter(var1);
      }

      super((Writer)var3, var2);
   }

   public PrintConsumer(OutputStream var1, boolean var2) {
      super(var1, var2);
   }

   public PrintConsumer(Writer var1) {
      super(var1);
   }

   public PrintConsumer(Writer var1, boolean var2) {
      super(var1, var2);
   }

   public PrintConsumer append(char var1) {
      this.print(var1);
      return this;
   }

   public PrintConsumer append(CharSequence var1) {
      Object var2 = var1;
      if(var1 == null) {
         var2 = "null";
      }

      this.append((CharSequence)var2, 0, ((CharSequence)var2).length());
      return this;
   }

   public PrintConsumer append(CharSequence var1, int var2, int var3) {
      Object var4 = var1;
      if(var1 == null) {
         var4 = "null";
      }

      while(var2 < var3) {
         this.append(((CharSequence)var4).charAt(var2));
         ++var2;
      }

      return this;
   }

   public void endAttribute() {
   }

   public void endDocument() {
   }

   public void endElement() {
   }

   protected void endNumber() {
   }

   public boolean ignoring() {
      return false;
   }

   public void startAttribute(Object var1) {
   }

   public void startDocument() {
   }

   public void startElement(Object var1) {
   }

   protected void startNumber() {
   }

   public void write(CharSequence var1, int var2, int var3) {
      if(var1 instanceof String) {
         this.write((String)var1, var2, var3);
      } else {
         while(var2 < var3) {
            this.write(var1.charAt(var2));
            ++var2;
         }
      }

   }

   public void writeBoolean(boolean var1) {
      this.print(var1);
   }

   public void writeDouble(double var1) {
      this.startNumber();
      this.print(var1);
      this.endNumber();
   }

   public void writeFloat(float var1) {
      this.startNumber();
      this.print(var1);
      this.endNumber();
   }

   public void writeInt(int var1) {
      this.startNumber();
      this.print(var1);
      this.endNumber();
   }

   public void writeLong(long var1) {
      this.startNumber();
      this.print(var1);
      this.endNumber();
   }

   public void writeObject(Object var1) {
      this.print(var1);
   }
}
