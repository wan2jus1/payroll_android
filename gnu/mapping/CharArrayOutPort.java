package gnu.mapping;

import gnu.lists.Consumer;
import gnu.mapping.CharArrayInPort;
import gnu.mapping.OutPort;
import java.io.Writer;

public class CharArrayOutPort extends OutPort {

   public CharArrayOutPort() {
      super((Writer)null, false, CharArrayInPort.stringPath);
   }

   public void close() {
   }

   protected boolean closeOnExit() {
      return false;
   }

   public int length() {
      return this.bout.bufferFillPointer;
   }

   public void reset() {
      this.bout.bufferFillPointer = 0;
   }

   public void setLength(int var1) {
      this.bout.bufferFillPointer = var1;
   }

   public char[] toCharArray() {
      int var2 = this.bout.bufferFillPointer;
      char[] var1 = new char[var2];
      System.arraycopy(this.bout.buffer, 0, var1, 0, var2);
      return var1;
   }

   public String toString() {
      return new String(this.bout.buffer, 0, this.bout.bufferFillPointer);
   }

   public String toSubString(int var1) {
      return new String(this.bout.buffer, var1, this.bout.bufferFillPointer - var1);
   }

   public String toSubString(int var1, int var2) {
      if(var2 > this.bout.bufferFillPointer) {
         throw new IndexOutOfBoundsException();
      } else {
         return new String(this.bout.buffer, var1, var2 - var1);
      }
   }

   public void writeTo(int var1, int var2, Consumer var3) {
      var3.write((char[])this.bout.buffer, var1, var2);
   }

   public void writeTo(Consumer var1) {
      var1.write((char[])this.bout.buffer, 0, this.bout.bufferFillPointer);
   }
}
