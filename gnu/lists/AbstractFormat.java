package gnu.lists;

import gnu.lists.Consumer;
import gnu.lists.PrintConsumer;
import gnu.mapping.CharArrayOutPort;
import gnu.mapping.OutPort;
import java.io.Writer;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

public abstract class AbstractFormat extends Format {

   public void endAttribute(Consumer var1) {
      this.write(" ", var1);
   }

   public void endElement(Consumer var1) {
      this.write(")", var1);
   }

   public StringBuffer format(Object var1, StringBuffer var2, FieldPosition var3) {
      CharArrayOutPort var4 = new CharArrayOutPort();
      this.writeObject(var1, (PrintConsumer)var4);
      var2.append(var4.toCharArray());
      var4.close();
      return var2;
   }

   public void format(Object var1, Consumer var2) {
      if(var2 instanceof OutPort) {
         OutPort var3 = (OutPort)var2;
         AbstractFormat var4 = var3.objectFormat;

         try {
            var3.objectFormat = this;
            var2.writeObject(var1);
         } finally {
            var3.objectFormat = var4;
         }

      } else {
         var2.writeObject(var1);
      }
   }

   public Object parseObject(String var1, ParsePosition var2) {
      throw new Error(this.getClass().getName() + ".parseObject - not implemented");
   }

   public void startAttribute(Object var1, Consumer var2) {
      this.write(var1.toString(), var2);
      this.write(": ", var2);
   }

   public void startElement(Object var1, Consumer var2) {
      this.write("(", var2);
      this.write(var1.toString(), var2);
      this.write(" ", var2);
   }

   public void write(int var1, Consumer var2) {
      var2.write(var1);
   }

   protected void write(String var1, Consumer var2) {
      var2.write(var1);
   }

   public void writeBoolean(boolean var1, Consumer var2) {
      var2.writeBoolean(var1);
   }

   public void writeInt(int var1, Consumer var2) {
      this.writeLong((long)var1, var2);
   }

   public void writeLong(long var1, Consumer var3) {
      var3.writeLong(var1);
   }

   public abstract void writeObject(Object var1, Consumer var2);

   public final void writeObject(Object var1, PrintConsumer var2) {
      this.writeObject(var1, (Consumer)var2);
   }

   public final void writeObject(Object var1, Writer var2) {
      if(var2 instanceof Consumer) {
         this.writeObject(var1, (Consumer)((Consumer)var2));
      } else {
         OutPort var3 = new OutPort(var2, false, true);
         this.writeObject(var1, (Consumer)((Consumer)var2));
         var3.close();
      }
   }
}
