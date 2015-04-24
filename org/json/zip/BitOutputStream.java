package org.json.zip;

import java.io.IOException;
import java.io.OutputStream;
import org.json.zip.BitInputStream;
import org.json.zip.BitWriter;

public class BitOutputStream implements BitWriter {

   private long nrBits = 0L;
   private OutputStream out;
   private int unwritten;
   private int vacant = 8;


   public BitOutputStream(OutputStream var1) {
      this.out = var1;
   }

   public long nrBits() {
      return this.nrBits;
   }

   public void one() throws IOException {
      this.write(1, 1);
   }

   public void pad(int var1) throws IOException {
      int var2 = var1 - (int)(this.nrBits % (long)var1);
      int var3 = var2 & 7;
      var1 = var2;
      if(var3 > 0) {
         this.write(0, var3);
         var1 = var2 - var3;
      }

      while(var1 > 0) {
         this.write(0, 8);
         var1 -= 8;
      }

      this.out.flush();
   }

   public void write(int var1, int var2) throws IOException {
      if(var1 != 0 || var2 != 0) {
         if(var2 > 0) {
            int var3 = var2;
            if(var2 <= 32) {
               while(var3 > 0) {
                  var2 = var3;
                  if(var3 > this.vacant) {
                     var2 = this.vacant;
                  }

                  this.unwritten |= (var1 >>> var3 - var2 & BitInputStream.mask[var2]) << this.vacant - var2;
                  int var4 = var3 - var2;
                  this.nrBits += (long)var2;
                  this.vacant -= var2;
                  var3 = var4;
                  if(this.vacant == 0) {
                     this.out.write(this.unwritten);
                     this.unwritten = 0;
                     this.vacant = 8;
                     var3 = var4;
                  }
               }

               return;
            }
         }

         throw new IOException("Bad write width.");
      }
   }

   public void zero() throws IOException {
      this.write(0, 1);
   }
}
