package gnu.lists;

import gnu.lists.AbstractSequence;
import gnu.lists.CharSeq;
import gnu.lists.Consumer;
import gnu.lists.SubSequence;
import java.io.IOException;
import java.util.List;

public class SubCharSeq extends SubSequence implements CharSeq {

   public SubCharSeq(AbstractSequence var1, int var2, int var3) {
      super(var1, var2, var3);
   }

   private SubCharSeq subCharSeq(int var1, int var2) {
      int var3 = this.size();
      if(var1 >= 0 && var2 >= var1 && var2 <= var3) {
         return new SubCharSeq(this.base, this.base.createRelativePos(this.ipos0, var1, false), this.base.createRelativePos(this.ipos0, var2, true));
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public char charAt(int var1) {
      if(var1 >= 0 && var1 < this.size()) {
         int var2 = this.base.nextIndex(this.ipos0);
         return ((CharSeq)this.base).charAt(var2 + var1);
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public void consume(int var1, int var2, Consumer var3) {
      int var4 = this.base.nextIndex(this.ipos0);
      int var5 = this.base.nextIndex(this.ipos0);
      if(var1 >= 0 && var2 >= 0 && var4 + var1 + var2 <= var5) {
         ((CharSeq)this.base).consume(var4 + var1, var2, var3);
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public void fill(char var1) {
      int var2 = this.base.nextIndex(this.ipos0);
      int var3 = this.base.nextIndex(this.ipos0);
      ((CharSeq)this.base).fill(var2, var3, var1);
   }

   public void fill(int var1, int var2, char var3) {
      int var4 = this.base.nextIndex(this.ipos0);
      int var5 = this.base.nextIndex(this.ipos0);
      if(var1 >= 0 && var2 >= var1 && var4 + var2 <= var5) {
         ((CharSeq)this.base).fill(var4 + var1, var4 + var2, var3);
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public void getChars(int var1, int var2, char[] var3, int var4) {
      while(var1 < var2) {
         var3[var4] = this.charAt(var1);
         ++var1;
         ++var4;
      }

   }

   public int length() {
      return this.size();
   }

   public void setCharAt(int var1, char var2) {
      if(var1 >= 0 && var1 < this.size()) {
         int var3 = this.base.nextIndex(this.ipos0);
         ((CharSeq)this.base).setCharAt(var3 + var1, var2);
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public List subList(int var1, int var2) {
      return this.subCharSeq(var1, var2);
   }

   public CharSequence subSequence(int var1, int var2) {
      return this.subCharSeq(var1, var2);
   }

   public String toString() {
      int var3 = this.size();
      StringBuffer var1 = new StringBuffer(var3);

      for(int var2 = 0; var2 < var3; ++var2) {
         var1.append(this.charAt(var2));
      }

      return var1.toString();
   }

   public void writeTo(int var1, int var2, Appendable var3) throws IOException {
      int var4 = this.base.nextIndex(this.ipos0);
      int var5 = this.base.nextIndex(this.ipos0);
      if(var1 >= 0 && var2 >= 0 && var4 + var1 + var2 <= var5) {
         ((CharSeq)this.base).writeTo(var4 + var1, var2, var3);
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public void writeTo(Appendable var1) throws IOException {
      int var2 = this.base.nextIndex(this.ipos0);
      ((CharSeq)this.base).writeTo(var2, this.size(), var1);
   }
}
