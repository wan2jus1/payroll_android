package gnu.bytecode;

import gnu.bytecode.Attribute;
import gnu.bytecode.ClassTypeWriter;
import java.io.DataOutputStream;
import java.io.IOException;

public class MiscAttr extends Attribute {

   byte[] data;
   int dataLength;
   int offset;


   public MiscAttr(String var1, byte[] var2) {
      this(var1, var2, 0, var2.length);
   }

   public MiscAttr(String var1, byte[] var2, int var3, int var4) {
      super(var1);
      this.data = var2;
      this.offset = var3;
      this.dataLength = var4;
   }

   public int getLength() {
      return this.dataLength;
   }

   public void print(ClassTypeWriter var1) {
      super.print(var1);
      int var4 = this.getLength();
      int var2 = 0;

      while(var2 < var4) {
         byte var3 = this.data[var2];
         if(var2 % 20 == 0) {
            var1.print(' ');
         }

         var1.print(' ');
         var1.print(Character.forDigit(var3 >> 4 & 15, 16));
         var1.print(Character.forDigit(var3 & 15, 16));
         int var5 = var2 + 1;
         if(var5 % 20 != 0) {
            var2 = var5;
            if(var5 != var4) {
               continue;
            }
         }

         var1.println();
         var2 = var5;
      }

   }

   protected void put1(int var1) {
      byte[] var2;
      if(this.data == null) {
         this.data = new byte[20];
      } else if(this.dataLength >= this.data.length) {
         var2 = new byte[this.data.length * 2];
         System.arraycopy(this.data, 0, var2, 0, this.dataLength);
         this.data = var2;
      }

      var2 = this.data;
      int var3 = this.dataLength;
      this.dataLength = var3 + 1;
      var2[var3] = (byte)var1;
   }

   protected void put2(int var1) {
      this.put1((byte)(var1 >> 8));
      this.put1((byte)var1);
   }

   protected void put2(int var1, int var2) {
      this.data[var1] = (byte)(var2 >> 8);
      this.data[var1 + 1] = (byte)var2;
   }

   protected int u1() {
      int var1 = this.offset;
      this.offset = var1 + 1;
      return this.u1(var1);
   }

   protected int u1(int var1) {
      return this.data[var1] & 255;
   }

   protected int u2() {
      int var1 = this.u2(this.offset);
      this.offset += 2;
      return var1;
   }

   protected int u2(int var1) {
      return ((this.data[var1] & 255) << 8) + (this.data[var1 + 1] & 255);
   }

   public void write(DataOutputStream var1) throws IOException {
      var1.write(this.data, this.offset, this.dataLength);
   }
}
