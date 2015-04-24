package kawa;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class TelnetOutputStream extends FilterOutputStream {

   public TelnetOutputStream(OutputStream var1) {
      super(var1);
   }

   public void write(int var1) throws IOException {
      if(var1 == 255) {
         this.out.write(var1);
      }

      this.out.write(var1);
   }

   public void write(byte[] var1) throws IOException {
      this.write(var1, 0, var1.length);
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      int var5 = var2 + var3;
      var3 = var2;

      int var4;
      for(var2 = var2; var2 < var5; var3 = var4) {
         var4 = var3;
         if(var1[var2] == -1) {
            this.out.write(var1, var3, var2 + 1 - var3);
            var4 = var2;
         }

         ++var2;
      }

      this.out.write(var1, var3, var5 - var3);
   }

   public void writeCommand(int var1) throws IOException {
      this.out.write(255);
      this.out.write(var1);
   }

   public final void writeCommand(int var1, int var2) throws IOException {
      this.out.write(255);
      this.out.write(var1);
      this.out.write(var2);
   }

   public final void writeDo(int var1) throws IOException {
      this.writeCommand(253, var1);
   }

   public final void writeDont(int var1) throws IOException {
      this.writeCommand(254, var1);
   }

   public final void writeSubCommand(int var1, byte[] var2) throws IOException {
      this.writeCommand(250, var1);
      this.write(var2);
      this.writeCommand(240);
   }

   public final void writeWill(int var1) throws IOException {
      this.writeCommand(251, var1);
   }

   public final void writeWont(int var1) throws IOException {
      this.writeCommand(252, var1);
   }
}
