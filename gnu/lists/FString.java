package gnu.lists;

import gnu.lists.CharSeq;
import gnu.lists.Consumable;
import gnu.lists.Consumer;
import gnu.lists.Convert;
import gnu.lists.Sequence;
import gnu.lists.SimpleVector;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Writer;
import java.util.Collection;

public class FString extends SimpleVector implements Comparable, Appendable, CharSeq, Externalizable, Consumable {

   protected static char[] empty = new char[0];
   public char[] data;


   public FString() {
      this.data = empty;
   }

   public FString(int var1) {
      this.size = var1;
      this.data = new char[var1];
   }

   public FString(int var1, char var2) {
      char[] var3 = new char[var1];
      this.data = var3;
      this.size = var1;

      while(true) {
         --var1;
         if(var1 < 0) {
            return;
         }

         var3[var1] = var2;
      }
   }

   public FString(CharSeq var1) {
      this((CharSeq)var1, 0, var1.size());
   }

   public FString(CharSeq var1, int var2, int var3) {
      char[] var4 = new char[var3];
      var1.getChars(var2, var2 + var3, var4, 0);
      this.data = var4;
      this.size = var3;
   }

   public FString(Sequence var1) {
      this.data = new char[var1.size()];
      this.addAll((Collection)var1);
   }

   public FString(CharSequence var1) {
      this((CharSequence)var1, 0, var1.length());
   }

   public FString(CharSequence var1, int var2, int var3) {
      char[] var4 = new char[var3];
      int var5 = var3;

      while(true) {
         --var5;
         if(var5 < 0) {
            this.data = var4;
            this.size = var3;
            return;
         }

         var4[var5] = var1.charAt(var2 + var5);
      }
   }

   public FString(String var1) {
      this.data = var1.toCharArray();
      this.size = this.data.length;
   }

   public FString(StringBuffer var1) {
      this((StringBuffer)var1, 0, var1.length());
   }

   public FString(StringBuffer var1, int var2, int var3) {
      this.size = var3;
      this.data = new char[var3];
      if(var3 > 0) {
         var1.getChars(var2, var2 + var3, this.data, 0);
      }

   }

   public FString(char[] var1) {
      this.size = var1.length;
      this.data = var1;
   }

   public FString(char[] var1, int var2, int var3) {
      this.size = var3;
      this.data = new char[var3];
      System.arraycopy(var1, var2, this.data, 0, var3);
   }

   public boolean addAll(FString var1) {
      boolean var3 = false;
      int var2 = this.size + var1.size;
      if(this.data.length < var2) {
         this.setBufferLength(var2);
      }

      System.arraycopy(var1.data, 0, this.data, this.size, var1.size);
      this.size = var2;
      if(var1.size > 0) {
         var3 = true;
      }

      return var3;
   }

   public boolean addAll(CharSequence var1) {
      boolean var5 = false;
      int var3 = var1.length();
      int var4 = this.size + var3;
      if(this.data.length < var4) {
         this.setBufferLength(var4);
      }

      if(var1 instanceof FString) {
         System.arraycopy(((FString)var1).data, 0, this.data, this.size, var3);
      } else if(var1 instanceof String) {
         ((String)var1).getChars(0, var3, this.data, this.size);
      } else if(var1 instanceof CharSeq) {
         ((CharSeq)var1).getChars(0, var3, this.data, this.size);
      } else {
         int var2 = var3;

         while(true) {
            --var2;
            if(var2 < 0) {
               break;
            }

            this.data[this.size + var2] = var1.charAt(var2);
         }
      }

      this.size = var4;
      if(var3 > 0) {
         var5 = true;
      }

      return var5;
   }

   public void addAllStrings(Object[] var1, int var2) {
      int var4 = this.size;

      for(int var3 = var2; var3 < var1.length; ++var3) {
         var4 += ((CharSequence)var1[var3]).length();
      }

      if(this.data.length < var4) {
         this.setBufferLength(var4);
      }

      while(var2 < var1.length) {
         this.addAll((CharSequence)((CharSequence)var1[var2]));
         ++var2;
      }

   }

   public FString append(char var1) {
      int var2 = this.size;
      if(var2 >= this.data.length) {
         this.ensureBufferLength(var2 + 1);
      }

      this.data[var2] = var1;
      this.size = var2 + 1;
      return this;
   }

   public FString append(CharSequence var1) {
      Object var2 = var1;
      if(var1 == null) {
         var2 = "null";
      }

      return this.append((CharSequence)var2, 0, ((CharSequence)var2).length());
   }

   public FString append(CharSequence var1, int var2, int var3) {
      Object var4 = var1;
      if(var1 == null) {
         var4 = "null";
      }

      int var5 = var3 - var2;
      int var6 = this.size;
      if(var6 + var5 > this.data.length) {
         this.ensureBufferLength(var6 + var5);
      }

      char[] var7 = this.data;
      if(var4 instanceof String) {
         ((String)var4).getChars(var2, var3, var7, var6);
      } else if(var4 instanceof CharSeq) {
         ((CharSeq)var4).getChars(var2, var3, var7, var6);
      } else {
         var5 = var2;

         for(var2 = var6; var5 < var3; ++var2) {
            var7[var2] = ((CharSequence)var4).charAt(var5);
            ++var5;
         }
      }

      this.size = var6;
      return this;
   }

   public final char charAt(int var1) {
      if(var1 >= this.size) {
         throw new StringIndexOutOfBoundsException(var1);
      } else {
         return this.data[var1];
      }
   }

   public final char charAtBuffer(int var1) {
      return this.data[var1];
   }

   protected void clearBuffer(int var1, int var2) {
      char[] var3 = this.data;

      while(true) {
         --var2;
         if(var2 < 0) {
            return;
         }

         var3[var1] = 0;
         ++var1;
      }
   }

   public int compareTo(Object var1) {
      FString var9 = (FString)var1;
      char[] var2 = this.data;
      char[] var3 = var9.data;
      int var6 = this.size;
      int var7 = var9.size;
      int var4;
      if(var6 > var7) {
         var4 = var7;
      } else {
         var4 = var6;
      }

      for(int var5 = 0; var5 < var4; ++var5) {
         int var8 = var2[var5] - var3[var5];
         if(var8 != 0) {
            return var8;
         }
      }

      return var6 - var7;
   }

   public void consume(Consumer var1) {
      var1.write((char[])this.data, 0, this.data.length);
   }

   public boolean consumeNext(int var1, Consumer var2) {
      var1 >>>= 1;
      if(var1 >= this.size) {
         return false;
      } else {
         var2.write(this.data[var1]);
         return true;
      }
   }

   public void consumePosRange(int var1, int var2, Consumer var3) {
      if(!var3.ignoring()) {
         int var4 = var1 >>> 1;
         var2 >>>= 1;
         var1 = var2;
         if(var2 > this.size) {
            var1 = this.size;
         }

         if(var1 > var4) {
            var3.write((char[])this.data, var4, var1 - var4);
            return;
         }
      }

   }

   public FString copy(int var1, int var2) {
      char[] var3 = new char[var2 - var1];
      char[] var4 = this.data;

      for(int var5 = var1; var5 < var2; ++var5) {
         var3[var5 - var1] = var4[var5];
      }

      return new FString(var3);
   }

   public void ensureBufferLength(int var1) {
      if(var1 > this.data.length) {
         int var3;
         if(var1 < 60) {
            var3 = 120;
         } else {
            var3 = var1 * 2;
         }

         char[] var2 = new char[var3];
         System.arraycopy(this.data, 0, var2, 0, var1);
         this.data = var2;
      }

   }

   public boolean equals(Object var1) {
      if(var1 != null && var1 instanceof FString) {
         char[] var5 = ((FString)var1).data;
         int var3 = this.size;
         if(var5 != null && var5.length == var3) {
            char[] var2 = this.data;

            int var4;
            do {
               var4 = var3 - 1;
               if(var4 < 0) {
                  return true;
               }

               var3 = var4;
            } while(var2[var4] == var5[var4]);

            return false;
         }
      }

      return false;
   }

   public final void fill(char var1) {
      char[] var2 = this.data;
      int var3 = this.size;

      while(true) {
         --var3;
         if(var3 < 0) {
            return;
         }

         var2[var3] = var1;
      }
   }

   public void fill(int var1, int var2, char var3) {
      if(var1 >= 0 && var2 <= this.size) {
         for(char[] var4 = this.data; var1 < var2; ++var1) {
            var4[var1] = var3;
         }

      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public final Object get(int var1) {
      if(var1 >= this.size) {
         throw new ArrayIndexOutOfBoundsException();
      } else {
         return Convert.toObject((char)this.data[var1]);
      }
   }

   protected Object getBuffer() {
      return this.data;
   }

   public final Object getBuffer(int var1) {
      return Convert.toObject((char)this.data[var1]);
   }

   public int getBufferLength() {
      return this.data.length;
   }

   public void getChars(int var1, int var2, StringBuffer var3) {
      if(var1 >= 0 && var1 <= var2) {
         if(var2 > this.size) {
            throw new StringIndexOutOfBoundsException(var2);
         } else {
            if(var1 < var2) {
               var3.append(this.data, var1, var2 - var1);
            }

         }
      } else {
         throw new StringIndexOutOfBoundsException(var1);
      }
   }

   public void getChars(int var1, int var2, char[] var3, int var4) {
      if(var1 >= 0 && var1 <= var2) {
         if(var2 > this.size) {
            throw new StringIndexOutOfBoundsException(var2);
         } else if(var4 + var2 - var1 > var3.length) {
            throw new StringIndexOutOfBoundsException(var4);
         } else {
            if(var1 < var2) {
               System.arraycopy(this.data, var1, var3, var4, var2 - var1);
            }

         }
      } else {
         throw new StringIndexOutOfBoundsException(var1);
      }
   }

   public void getChars(StringBuffer var1) {
      var1.append(this.data, 0, this.size);
   }

   public int getElementKind() {
      return 29;
   }

   public int hashCode() {
      char[] var1 = this.data;
      int var4 = this.size;
      int var3 = 0;

      for(int var2 = 0; var2 < var4; ++var2) {
         var3 = var3 * 31 + var1[var2];
      }

      return var3;
   }

   public int length() {
      return this.size;
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      int var4 = var1.readInt();
      char[] var2 = new char[var4];

      for(int var3 = 0; var3 < var4; ++var3) {
         var2[var3] = var1.readChar();
      }

      this.data = var2;
      this.size = var4;
   }

   public void replace(int var1, String var2) {
      var2.getChars(0, var2.length(), this.data, var1);
   }

   public void replace(int var1, char[] var2, int var3, int var4) {
      System.arraycopy(var2, var3, this.data, var1, var4);
   }

   public final Object setBuffer(int var1, Object var2) {
      Object var3 = Convert.toObject((char)this.data[var1]);
      this.data[var1] = Convert.toChar(var2);
      return var3;
   }

   public void setBufferLength(int var1) {
      int var4 = this.data.length;
      if(var4 != var1) {
         char[] var2 = new char[var1];
         char[] var3 = this.data;
         if(var4 < var1) {
            var1 = var4;
         }

         System.arraycopy(var3, 0, var2, 0, var1);
         this.data = var2;
      }

   }

   public void setCharAt(int var1, char var2) {
      if(var1 >= 0 && var1 < this.size) {
         this.data[var1] = var2;
      } else {
         throw new StringIndexOutOfBoundsException(var1);
      }
   }

   public void setCharAtBuffer(int var1, char var2) {
      this.data[var1] = var2;
   }

   public void shift(int var1, int var2, int var3) {
      System.arraycopy(this.data, var1, this.data, var2, var3);
   }

   public CharSequence subSequence(int var1, int var2) {
      return new FString(this.data, var1, var2 - var1);
   }

   public String substring(int var1, int var2) {
      return new String(this.data, var1, var2 - var1);
   }

   public char[] toCharArray() {
      int var2 = this.data.length;
      int var3 = this.size;
      if(var3 == var2) {
         return this.data;
      } else {
         char[] var1 = new char[var3];
         System.arraycopy(this.data, 0, var1, 0, var3);
         return var1;
      }
   }

   public String toString() {
      return new String(this.data, 0, this.size);
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      int var4 = this.size;
      var1.writeInt(var4);
      char[] var2 = this.data;

      for(int var3 = 0; var3 < var4; ++var3) {
         var1.writeChar(var2[var3]);
      }

   }

   public void writeTo(int var1, int var2, Appendable var3) throws IOException {
      if(var3 instanceof Writer) {
         try {
            ((Writer)var3).write(this.data, var1, var2);
         } catch (IOException var4) {
            throw new RuntimeException(var4);
         }
      } else {
         var3.append(this, var1, var1 + var2);
      }
   }

   public void writeTo(Appendable var1) throws IOException {
      this.writeTo(0, this.size, var1);
   }
}
