package org.json.zip;

import java.io.IOException;
import java.io.InputStream;
import org.json.zip.BitReader;

public class BitInputStream implements BitReader {

   static final int[] mask = new int[]{0, 1, 3, 7, 15, 31, 63, 127, 255};
   private int available = 0;
   private InputStream in;
   private long nrBits = 0L;
   private int unread = 0;


   public BitInputStream(InputStream var1) {
      this.in = var1;
   }

   public BitInputStream(InputStream var1, int var2) {
      this.in = var1;
      this.unread = var2;
      this.available = 8;
   }

   public boolean bit() throws IOException {
      return this.read(1) != 0;
   }

   public long nrBits() {
      return this.nrBits;
   }

   public boolean pad(int var1) throws IOException {
      int var3 = (int)(this.nrBits % (long)var1);
      boolean var4 = true;

      for(int var2 = 0; var2 < var1 - var3; ++var2) {
         if(this.bit()) {
            var4 = false;
         }
      }

      return var4;
   }

   public int read(int var1) throws IOException {
      int var3;
      if(var1 == 0) {
         var3 = 0;
      } else {
         if(var1 < 0 || var1 > 32) {
            throw new IOException("Bad read width.");
         }

         int var2 = 0;

         while(true) {
            var3 = var2;
            if(var1 <= 0) {
               break;
            }

            if(this.available == 0) {
               this.unread = this.in.read();
               if(this.unread < 0) {
                  throw new IOException("Attempt to read past end.");
               }

               this.available = 8;
            }

            var3 = var1;
            if(var1 > this.available) {
               var3 = this.available;
            }

            var2 |= (this.unread >>> this.available - var3 & mask[var3]) << var1 - var3;
            this.nrBits += (long)var3;
            this.available -= var3;
            var1 -= var3;
         }
      }

      return var3;
   }
}
