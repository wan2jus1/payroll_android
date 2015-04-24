package gnu.text;

import gnu.lists.Consumer;
import gnu.text.CharMap;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;

public class Char implements Comparable, Externalizable {

   static Char[] ascii = new Char[128];
   static char[] charNameValues;
   static String[] charNames;
   static CharMap hashTable = new CharMap();
   int value;


   static {
      int var0 = 128;

      while(true) {
         --var0;
         if(var0 < 0) {
            charNameValues = new char[]{' ', '\t', '\n', '\n', '\r', '\f', '\b', '\u001b', '\u007f', '\u007f', '\u007f', '\u0007', '\u0007', '\u000b', '\u0000'};
            charNames = new String[]{"space", "tab", "newline", "linefeed", "return", "page", "backspace", "esc", "delete", "del", "rubout", "alarm", "bel", "vtab", "nul"};
            return;
         }

         ascii[var0] = new Char(var0);
      }
   }

   public Char() {
   }

   Char(int var1) {
      this.value = var1;
   }

   public static Char make(int param0) {
      // $FF: Couldn't be decompiled
   }

   public static int nameToChar(String var0) {
      int var1 = charNames.length;

      while(true) {
         int var2 = var1 - 1;
         int var3;
         if(var2 >= 0) {
            var1 = var2;
            if(charNames[var2].equals(var0)) {
               var3 = charNameValues[var2];
               return var3;
            }
         } else {
            var1 = charNames.length;

            do {
               var2 = var1 - 1;
               if(var2 < 0) {
                  int var4 = var0.length();
                  if(var4 > 1 && var0.charAt(0) == 117) {
                     var1 = 0;
                     var2 = 1;

                     while(true) {
                        var3 = var1;
                        if(var2 == var4) {
                           return var3;
                        }

                        var3 = Character.digit(var0.charAt(var2), 16);
                        if(var3 < 0) {
                           break;
                        }

                        var1 = (var1 << 4) + var3;
                        ++var2;
                     }
                  }

                  if(var4 == 3 && var0.charAt(1) == 45) {
                     char var5 = var0.charAt(0);
                     if(var5 == 99 || var5 == 67) {
                        return var0.charAt(2) & 31;
                     }
                  }

                  return -1;
               }

               var1 = var2;
            } while(!charNames[var2].equalsIgnoreCase(var0));

            return charNameValues[var2];
         }
      }
   }

   public static void print(int var0, Consumer var1) {
      if(var0 >= 65536) {
         var1.write((char)((var0 - 65536 >> 10) + '\ud800'));
         var1.write((char)((var0 & 1023) + '\udc00'));
      } else {
         var1.write((char)var0);
      }
   }

   public static String toScmReadableString(int var0) {
      StringBuffer var1 = new StringBuffer(20);
      var1.append("#\\");

      for(int var2 = 0; var2 < charNameValues.length; ++var2) {
         if((char)var0 == charNameValues[var2]) {
            var1.append(charNames[var2]);
            return var1.toString();
         }
      }

      if(var0 >= 32 && var0 <= 127) {
         var1.append((char)var0);
      } else {
         var1.append('x');
         var1.append(Integer.toString(var0, 16));
      }

      return var1.toString();
   }

   public final char charValue() {
      return (char)this.value;
   }

   public int compareTo(Object var1) {
      return this.value - ((Char)var1).value;
   }

   public boolean equals(Object var1) {
      return var1 != null && var1 instanceof Char && ((Char)var1).intValue() == this.value;
   }

   public int hashCode() {
      return this.value;
   }

   public final int intValue() {
      return this.value;
   }

   public void print(Consumer var1) {
      print(this.value, var1);
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      this.value = var1.readChar();
      if(this.value >= '\ud800' && this.value < '\udbff') {
         char var2 = var1.readChar();
         if(var2 >= '\udc00' && var2 <= '\udfff') {
            this.value = (this.value - '\ud800' << 10) + (var2 - '\udc00') + 65536;
         }
      }

   }

   public Object readResolve() throws ObjectStreamException {
      return make(this.value);
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append('\'');
      if(this.value >= 32 && this.value < 127 && this.value != 39) {
         var1.append((char)this.value);
      } else {
         var1.append('\\');
         if(this.value == 39) {
            var1.append('\'');
         } else if(this.value == 10) {
            var1.append('n');
         } else if(this.value == 13) {
            var1.append('r');
         } else if(this.value == 9) {
            var1.append('t');
         } else {
            String var2;
            int var3;
            if(this.value < 256) {
               var2 = Integer.toOctalString(this.value);
               var3 = 3 - var2.length();

               while(true) {
                  --var3;
                  if(var3 < 0) {
                     var1.append(var2);
                     break;
                  }

                  var1.append('0');
               }
            } else {
               var1.append('u');
               var2 = Integer.toHexString(this.value);
               var3 = 4 - var2.length();

               while(true) {
                  --var3;
                  if(var3 < 0) {
                     var1.append(var2);
                     break;
                  }

                  var1.append('0');
               }
            }
         }
      }

      var1.append('\'');
      return var1.toString();
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      if(this.value > '\ud800') {
         if(this.value > '\uffff') {
            var1.writeChar((this.value - 65536 >> 10) + '\ud800');
            this.value = (this.value & 1023) + '\udc00';
         } else if(this.value <= '\udbff') {
            var1.writeChar(this.value);
            this.value = 0;
         }
      }

      var1.writeChar(this.value);
   }
}
