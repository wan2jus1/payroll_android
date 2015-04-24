package gnu.lists;

import gnu.lists.CharSeq;
import gnu.lists.Consumer;
import gnu.lists.FString;
import gnu.lists.StableVector;
import gnu.lists.SubCharSeq;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;

public class CharBuffer extends StableVector implements CharSeq, Serializable {

   private FString string;


   protected CharBuffer() {
   }

   public CharBuffer(int var1) {
      this(new FString(var1));
   }

   public CharBuffer(FString var1) {
      super(var1);
      this.string = var1;
   }

   public char charAt(int var1) {
      int var2 = var1;
      if(var1 >= this.gapStart) {
         var2 = var1 + (this.gapEnd - this.gapStart);
      }

      return this.string.charAt(var2);
   }

   public void consume(int var1, int var2, Consumer var3) {
      char[] var4 = this.string.data;
      int var6 = var1;
      int var5 = var2;
      if(var1 < this.gapStart) {
         var6 = this.gapStart - var1;
         var5 = var6;
         if(var6 > var2) {
            var5 = var2;
         }

         var3.write((char[])var4, var1, var5);
         var5 = var2 - var5;
         var6 = var1 + var5;
      }

      if(var5 > 0) {
         var3.write((char[])var4, var6 + (this.gapEnd - this.gapStart), var5);
      }

   }

   public void delete(int var1, int var2) {
      var1 = this.createPos(var1, false);
      this.removePos(var1, var2);
      this.releasePos(var1);
   }

   public void dump() {
      int var3 = 0;
      System.err.println("Buffer Content dump.  size:" + this.size() + "  buffer:" + this.getArray().length);
      System.err.print("before gap: \"");
      System.err.print(new String(this.getArray(), 0, this.gapStart));
      System.err.println("\" (gapStart:" + this.gapStart + " gapEnd:" + this.gapEnd + ')');
      System.err.print("after gap: \"");
      System.err.print(new String(this.getArray(), this.gapEnd, this.getArray().length - this.gapEnd));
      System.err.println("\"");
      if(this.positions != null) {
         var3 = this.positions.length;
      }

      System.err.println("Positions (size: " + var3 + " free:" + this.free + "):");
      boolean[] var1 = null;
      int var4;
      if(this.free != -2) {
         boolean[] var2 = new boolean[this.positions.length];
         var4 = this.free;

         while(true) {
            var1 = var2;
            if(var4 < 0) {
               break;
            }

            var2[var4] = true;
            var4 = this.positions[var4];
         }
      }

      for(var4 = 0; var4 < var3; ++var4) {
         int var5 = this.positions[var4];
         if(this.free == -2) {
            if(var5 == -2) {
               continue;
            }
         } else if(var1[var4]) {
            continue;
         }

         System.err.println("position#" + var4 + ": " + (var5 >> 1) + " isAfter:" + (var5 & 1));
      }

   }

   public final void fill(char var1) {
      char[] var2 = this.string.data;
      int var3 = var2.length;

      while(true) {
         --var3;
         if(var3 < this.gapEnd) {
            var3 = this.gapStart;

            while(true) {
               --var3;
               if(var3 < 0) {
                  return;
               }

               var2[var3] = var1;
            }
         }

         var2[var3] = var1;
      }
   }

   public void fill(int var1, int var2, char var3) {
      char[] var4 = this.string.data;
      int var5 = var1;
      if(this.gapStart < var2) {
         var1 = this.gapStart;
      } else {
         var1 = var2;
      }

      while(var5 < var1) {
         var4[var5] = var3;
         ++var5;
      }

      for(var5 = var1 + (this.gapEnd - this.gapStart); var5 < var1 + var2; ++var5) {
         var4[var5] = var3;
      }

   }

   public char[] getArray() {
      return (char[])((char[])this.base.getBuffer());
   }

   public void getChars(int var1, int var2, char[] var3, int var4) {
      char[] var5 = this.string.data;
      int var7 = var1;
      int var6 = var4;
      if(var1 < this.gapStart) {
         if(var2 < this.gapStart) {
            var6 = var2;
         } else {
            var6 = this.gapStart;
         }

         int var8 = var6 - var1;
         var7 = var1;
         var6 = var4;
         if(var8 > 0) {
            System.arraycopy(var5, var1, var3, var4, var8);
            var7 = var1 + var8;
            var6 = var4 + var8;
         }
      }

      var1 = this.gapEnd - this.gapStart;
      var4 = var7 + var1;
      var1 = var2 + var1 - var4;
      if(var1 > 0) {
         System.arraycopy(var5, var4, var3, var6, var1);
      }

   }

   public int indexOf(int var1, int var2) {
      char var4;
      char var5;
      if(var1 >= 65536) {
         var4 = (char)((var1 - 65536 >> 10) + '\ud800');
         var5 = (char)((var1 & 1023) + '\udc00');
      } else {
         var4 = (char)var1;
         var5 = 0;
      }

      char[] var3 = this.getArray();
      int var6 = var2;
      int var7 = this.gapStart;
      var1 = var2;
      var2 = var7;
      if(var6 >= var7) {
         var1 = var6 + (this.gapEnd - this.gapStart);
         var2 = var3.length;
      }

      while(true) {
         var7 = var1;
         var6 = var2;
         if(var1 == var2) {
            var6 = var3.length;
            if(var1 >= var6) {
               return -1;
            }

            var7 = this.gapEnd;
         }

         if(var3[var7] == var4) {
            if(var5 == 0) {
               break;
            }

            if(var7 + 1 < var6) {
               if(var3[var7 + 1] == var5) {
                  break;
               }
            } else if(this.gapEnd < var3.length && var3[this.gapEnd] == var5) {
               break;
            }
         }

         var1 = var7 + 1;
         var2 = var6;
      }

      return var7 > this.gapStart?var7 - (this.gapEnd - this.gapStart):var7;
   }

   public void insert(int var1, String var2, boolean var3) {
      int var4 = var2.length();
      this.gapReserve(var1, var4);
      var2.getChars(0, var4, this.string.data, var1);
      this.gapStart += var4;
   }

   public int lastIndexOf(int var1, int var2) {
      char var3;
      char var4;
      char var5;
      if(var1 >= 65536) {
         var4 = (char)((var1 - 65536 >> 10) + '\ud800');
         var3 = (char)((var1 & 1023) + '\udc00');
         var5 = var4;
      } else {
         byte var6 = 0;
         var4 = (char)var1;
         var5 = (char)var6;
         var3 = var4;
      }

      while(true) {
         int var7 = var2 - 1;
         if(var7 < 0) {
            return -1;
         }

         var2 = var7;
         if(this.charAt(var7) == var3) {
            if(var5 == 0) {
               return var7;
            }

            var2 = var7;
            if(var7 > 0) {
               var2 = var7;
               if(this.charAt(var7 - 1) == var5) {
                  return var7 - 1;
               }
            }
         }
      }
   }

   public int length() {
      return this.size();
   }

   public void setCharAt(int var1, char var2) {
      int var3 = var1;
      if(var1 >= this.gapStart) {
         var3 = var1 + (this.gapEnd - this.gapStart);
      }

      this.string.setCharAt(var3, var2);
   }

   public CharSequence subSequence(int var1, int var2) {
      int var3 = this.size();
      if(var1 >= 0 && var2 >= var1 && var2 <= var3) {
         return new SubCharSeq(this, this.base.createPos(var1, false), this.base.createPos(var2, true));
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public String substring(int var1, int var2) {
      int var3 = this.size();
      if(var1 >= 0 && var2 >= var1 && var2 <= var3) {
         var2 -= var1;
         var1 = this.getSegment(var1, var2);
         return new String(this.getArray(), var1, var2);
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public String toString() {
      int var1 = this.size();
      int var2 = this.getSegment(0, var1);
      return new String(this.getArray(), var2, var1);
   }

   public void writeTo(int var1, int var2, Writer var3) throws IOException {
      char[] var4 = this.string.data;
      int var6 = var1;
      int var5 = var2;
      if(var1 < this.gapStart) {
         var6 = this.gapStart - var1;
         var5 = var6;
         if(var6 > var2) {
            var5 = var2;
         }

         var3.write(var4, var1, var5);
         var5 = var2 - var5;
         var6 = var1 + var5;
      }

      if(var5 > 0) {
         var3.write(var4, var6 + (this.gapEnd - this.gapStart), var5);
      }

   }

   public void writeTo(int var1, int var2, Appendable var3) throws IOException {
      if(var3 instanceof Writer) {
         this.writeTo(var1, var2, (Writer)((Writer)var3));
      } else {
         var3.append(this, var1, var1 + var2);
      }
   }

   public void writeTo(Writer var1) throws IOException {
      char[] var2 = this.string.data;
      var1.write(var2, 0, this.gapStart);
      var1.write(var2, this.gapEnd, var2.length - this.gapEnd);
   }

   public void writeTo(Appendable var1) throws IOException {
      this.writeTo(0, this.size(), (Appendable)var1);
   }
}
