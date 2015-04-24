package gnu.text;

import gnu.text.Path;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class LineBufferedReader extends Reader {

   public static final int BUFFER_SIZE = 8192;
   private static final int CONVERT_CR = 1;
   private static final int DONT_KEEP_FULL_LINES = 8;
   private static final int PREV_WAS_CR = 4;
   private static final int USER_BUFFER = 2;
   public char[] buffer;
   private int flags;
   int highestPos;
   protected Reader in;
   public int limit;
   protected int lineNumber;
   private int lineStartPos;
   protected int markPos;
   Path path;
   public int pos;
   protected int readAheadLimit = 0;
   public char readState = 10;


   public LineBufferedReader(InputStream var1) {
      this.in = new InputStreamReader(var1);
   }

   public LineBufferedReader(Reader var1) {
      this.in = var1;
   }

   private void clearMark() {
      int var1 = 0;
      this.readAheadLimit = 0;
      if(this.lineStartPos >= 0) {
         var1 = this.lineStartPos;
      }

      while(true) {
         int var2 = var1 + 1;
         if(var2 >= this.pos) {
            return;
         }

         char var3 = this.buffer[var2 - 1];
         if(var3 != 10) {
            var1 = var2;
            if(var3 != 13) {
               continue;
            }

            if(this.getConvertCR()) {
               var1 = var2;
               if(this.buffer[var2] == 10) {
                  continue;
               }
            }
         }

         ++this.lineNumber;
         this.lineStartPos = var2;
         var1 = var2;
      }
   }

   static int countLines(char[] var0, int var1, int var2) {
      int var3 = 0;

      char var5;
      for(char var4 = 0; var1 < var2; var4 = var5) {
         int var6;
         label26: {
            var5 = var0[var1];
            if(var5 != 10 || var4 == 13) {
               var6 = var3;
               if(var5 != 13) {
                  break label26;
               }
            }

            var6 = var3 + 1;
         }

         ++var1;
         var3 = var6;
      }

      return var3;
   }

   private void reserve(char[] var1, int var2) throws IOException {
      int var5 = var2 + this.limit;
      if(var5 <= var1.length) {
         var2 = 0;
      } else {
         var2 = this.pos;
         int var4 = var2;
         if(this.readAheadLimit > 0) {
            var4 = var2;
            if(this.markPos < this.pos) {
               if(this.pos - this.markPos <= this.readAheadLimit && ((this.flags & 2) == 0 || var5 - this.markPos <= var1.length)) {
                  var4 = this.markPos;
               } else {
                  this.clearMark();
                  var4 = var2;
               }
            }
         }

         char[] var3;
         label59: {
            var5 -= var1.length;
            if(var5 <= var4) {
               var2 = var4;
               var3 = var1;
               if(var4 <= this.lineStartPos) {
                  break label59;
               }

               if((this.flags & 8) != 0) {
                  var3 = var1;
                  var2 = var4;
                  break label59;
               }
            }

            if(var5 <= this.lineStartPos && var4 > this.lineStartPos) {
               var2 = this.lineStartPos;
               var3 = var1;
            } else if((this.flags & 2) != 0) {
               var2 = var4 - (var4 - var5 >> 2);
               var3 = var1;
            } else {
               var2 = var4;
               if(this.lineStartPos >= 0) {
                  var2 = this.lineStartPos;
               }

               var3 = new char[var1.length * 2];
            }
         }

         this.lineStartPos -= var2;
         this.limit -= var2;
         this.markPos -= var2;
         this.pos -= var2;
         this.highestPos -= var2;
         var1 = var3;
      }

      if(this.limit > 0) {
         System.arraycopy(this.buffer, var2, var1, 0, this.limit);
      }

      this.buffer = var1;
   }

   public void close() throws IOException {
      this.in.close();
   }

   public int fill(int var1) throws IOException {
      return this.in.read(this.buffer, this.pos, var1);
   }

   public int getColumnNumber() {
      int var2 = 0;
      if(this.pos > 0) {
         char var3 = this.buffer[this.pos - 1];
         if(var3 == 10 || var3 == 13) {
            return 0;
         }
      }

      if(this.readAheadLimit <= 0) {
         return this.pos - this.lineStartPos;
      } else {
         if(this.lineStartPos >= 0) {
            var2 = this.lineStartPos;
         }

         int var4 = var2;

         int var5;
         for(var5 = var2; var4 < this.pos; var4 = var2) {
            char[] var1 = this.buffer;
            var2 = var4 + 1;
            char var6 = var1[var4];
            if(var6 == 10 || var6 == 13) {
               var5 = var2;
            }
         }

         var5 = this.pos - var5;
         var2 = var5;
         if(this.lineStartPos < 0) {
            var2 = var5 - this.lineStartPos;
         }

         return var2;
      }
   }

   public final boolean getConvertCR() {
      return (this.flags & 1) != 0;
   }

   public int getLineNumber() {
      int var3 = this.lineNumber;
      int var2;
      if(this.readAheadLimit != 0) {
         char[] var1 = this.buffer;
         if(this.lineStartPos < 0) {
            var2 = 0;
         } else {
            var2 = this.lineStartPos;
         }

         return var3 + countLines(var1, var2, this.pos);
      } else {
         var2 = var3;
         if(this.pos > 0) {
            var2 = var3;
            if(this.pos > this.lineStartPos) {
               char var4 = this.buffer[this.pos - 1];
               if(var4 != 10) {
                  var2 = var3;
                  if(var4 != 13) {
                     return var2;
                  }
               }

               var2 = var3 + 1;
            }
         }

         return var2;
      }
   }

   public String getName() {
      return this.path == null?null:this.path.toString();
   }

   public Path getPath() {
      return this.path;
   }

   public char getReadState() {
      return this.readState;
   }

   public void incrLineNumber(int var1, int var2) {
      this.lineNumber += var1;
      this.lineStartPos = var2;
   }

   public void lineStart(boolean var1) throws IOException {
   }

   public void mark(int var1) {
      synchronized(this){}

      try {
         if(this.readAheadLimit > 0) {
            this.clearMark();
         }

         this.readAheadLimit = var1;
         this.markPos = this.pos;
      } finally {
         ;
      }

   }

   public boolean markSupported() {
      return true;
   }

   public int peek() throws IOException {
      if(this.pos < this.limit && this.pos > 0) {
         char var1 = this.buffer[this.pos - 1];
         if(var1 != 10 && var1 != 13) {
            char var2 = this.buffer[this.pos];
            var1 = var2;
            if(var2 == 13) {
               var1 = var2;
               if(this.getConvertCR()) {
                  var1 = 10;
               }
            }

            return var1;
         }
      }

      int var3 = this.read();
      if(var3 >= 0) {
         this.unread_quick();
      }

      return var3;
   }

   public int read() throws IOException {
      char var2;
      if(this.pos > 0) {
         var2 = this.buffer[this.pos - 1];
      } else if((this.flags & 4) != 0) {
         var2 = 13;
      } else if(this.lineStartPos >= 0) {
         var2 = 10;
      } else {
         var2 = 0;
      }

      if(var2 == 13 || var2 == 10) {
         if(this.lineStartPos < this.pos && (this.readAheadLimit == 0 || this.pos <= this.markPos)) {
            this.lineStartPos = this.pos;
            ++this.lineNumber;
         }

         boolean var5;
         if(this.pos < this.highestPos) {
            var5 = true;
         } else {
            var5 = false;
         }

         label79: {
            if(var2 == 10) {
               if(this.pos <= 1) {
                  if((this.flags & 4) != 0) {
                     break label79;
                  }
               } else if(this.buffer[this.pos - 2] == 13) {
                  break label79;
               }
            }

            this.lineStart(var5);
         }

         if(!var5) {
            this.highestPos = this.pos + 1;
         }
      }

      int var3;
      if(this.pos >= this.limit) {
         if(this.buffer == null) {
            this.buffer = new char[8192];
         } else if(this.limit == this.buffer.length) {
            this.reserve(this.buffer, 1);
         }

         if(this.pos == 0) {
            if(var2 == 13) {
               this.flags |= 4;
            } else {
               this.flags &= -5;
            }
         }

         var3 = this.fill(this.buffer.length - this.pos);
         if(var3 <= 0) {
            var3 = -1;
            return var3;
         }

         this.limit += var3;
      }

      char[] var1 = this.buffer;
      var3 = this.pos;
      this.pos = var3 + 1;
      char var4 = var1[var3];
      if(var4 == 10) {
         var3 = var4;
         if(var2 == 13) {
            if(this.lineStartPos == this.pos - 1) {
               --this.lineNumber;
               --this.lineStartPos;
            }

            var3 = var4;
            if(this.getConvertCR()) {
               return this.read();
            }
         }
      } else {
         var3 = var4;
         if(var4 == 13) {
            var3 = var4;
            if(this.getConvertCR()) {
               return 10;
            }
         }
      }

      return var3;
   }

   public int read(char[] var1, int var2, int var3) throws IOException {
      int var4;
      if(this.pos >= this.limit) {
         var4 = 0;
      } else if(this.pos > 0) {
         var4 = this.buffer[this.pos - 1];
      } else if((this.flags & 4) == 0 && this.lineStartPos < 0) {
         var4 = 0;
      } else {
         var4 = 10;
      }

      int var6 = var2;
      var2 = var3;
      int var5 = var6;

      while(var2 > 0) {
         if(this.pos < this.limit && var4 != 10 && var4 != 13) {
            int var10 = this.pos;
            int var11 = this.limit;
            var6 = var4;
            int var8 = var11;
            int var7 = var5;
            int var9 = var10;
            if(var2 < var11 - var10) {
               var8 = var10 + var2;
               var9 = var10;
               var7 = var5;
               var6 = var4;
            }

            while(true) {
               var4 = var6;
               if(var9 < var8) {
                  var6 = this.buffer[var9];
                  var4 = var6;
                  if(var6 != 10) {
                     if(var6 != 13) {
                        var1[var7] = (char)var6;
                        ++var9;
                        ++var7;
                        continue;
                     }

                     var4 = var6;
                  }
               }

               var2 -= var9 - this.pos;
               this.pos = var9;
               var5 = var7;
               break;
            }
         } else {
            if(this.pos >= this.limit && var2 < var3) {
               return var3 - var2;
            }

            var4 = this.read();
            if(var4 < 0) {
               var2 = var3 - var2;
               if(var2 <= 0) {
                  return -1;
               }

               return var2;
            }

            var1[var5] = (char)var4;
            --var2;
            ++var5;
         }
      }

      return var3;
   }

   public String readLine() throws IOException {
      int var2 = this.read();
      if(var2 < 0) {
         return null;
      } else if(var2 != 13 && var2 != 10) {
         var2 = this.pos - 1;

         while(true) {
            if(this.pos < this.limit) {
               label46: {
                  char[] var1 = this.buffer;
                  int var3 = this.pos;
                  this.pos = var3 + 1;
                  char var6 = var1[var3];
                  if(var6 != 13 && var6 != 10) {
                     continue;
                  }

                  int var4 = this.pos;
                  if(var6 != 10 && !this.getConvertCR()) {
                     if(this.pos >= this.limit) {
                        --this.pos;
                        break label46;
                     }

                     if(this.buffer[this.pos] == 10) {
                        ++this.pos;
                     }
                  }

                  return new String(this.buffer, var2, var4 - 1 - var2);
               }
            }

            StringBuffer var5 = new StringBuffer(100);
            var5.append(this.buffer, var2, this.pos - var2);
            this.readLine(var5, 'I');
            return var5.toString();
         }
      } else {
         return "";
      }
   }

   public void readLine(StringBuffer var1, char var2) throws IOException {
      label47:
      while(true) {
         if(this.read() >= 0) {
            int var4 = this.pos - 1;
            this.pos = var4;

            char var6;
            do {
               if(this.pos >= this.limit) {
                  var1.append(this.buffer, var4, this.pos - var4);
                  continue label47;
               }

               char[] var3 = this.buffer;
               int var5 = this.pos;
               this.pos = var5 + 1;
               var6 = var3[var5];
            } while(var6 != 13 && var6 != 10);

            var1.append(this.buffer, var4, this.pos - 1 - var4);
            if(var2 == 80) {
               --this.pos;
               return;
            }

            if(!this.getConvertCR() && var6 != 10) {
               if(var2 != 73) {
                  var1.append('\r');
               }

               var4 = this.read();
               if(var4 == 10) {
                  if(var2 != 73) {
                     var1.append('\n');
                     return;
                  }
               } else if(var4 >= 0) {
                  this.unread_quick();
                  return;
               }
            } else if(var2 != 73) {
               var1.append('\n');
               return;
            }
         }

         return;
      }
   }

   public boolean ready() throws IOException {
      return this.pos < this.limit || this.in.ready();
   }

   public void reset() throws IOException {
      if(this.readAheadLimit <= 0) {
         throw new IOException("mark invalid");
      } else {
         if(this.pos > this.highestPos) {
            this.highestPos = this.pos;
         }

         this.pos = this.markPos;
         this.readAheadLimit = 0;
      }
   }

   public void setBuffer(char[] var1) throws IOException {
      if(var1 == null) {
         if(this.buffer != null) {
            var1 = new char[this.buffer.length];
            System.arraycopy(this.buffer, 0, var1, 0, this.buffer.length);
            this.buffer = var1;
         }

         this.flags &= -3;
      } else if(this.limit - this.pos > var1.length) {
         throw new IOException("setBuffer - too short");
      } else {
         this.flags |= 2;
         this.reserve(var1, 0);
      }
   }

   public final void setConvertCR(boolean var1) {
      if(var1) {
         this.flags |= 1;
      } else {
         this.flags &= -2;
      }
   }

   public void setKeepFullLines(boolean var1) {
      if(var1) {
         this.flags &= -9;
      } else {
         this.flags |= 8;
      }
   }

   public void setLineNumber(int var1) {
      this.lineNumber += var1 - this.getLineNumber();
   }

   public void setName(Object var1) {
      this.setPath(Path.valueOf(var1));
   }

   public void setPath(Path var1) {
      this.path = var1;
   }

   public int skip(int var1) throws IOException {
      int var2;
      int var4;
      if(var1 < 0) {
         for(var2 = -var1; var2 > 0 && this.pos > 0; --var2) {
            this.unread();
         }

         var4 = var1 + var2;
      } else {
         int var3 = var1;
         if(this.pos >= this.limit) {
            var2 = 0;
         } else if(this.pos > 0) {
            var2 = this.buffer[this.pos - 1];
         } else if((this.flags & 4) == 0 && this.lineStartPos < 0) {
            var2 = 0;
         } else {
            var2 = 10;
         }

         while(true) {
            var4 = var1;
            if(var3 <= 0) {
               break;
            }

            if(var2 != 10 && var2 != 13 && this.pos < this.limit) {
               int var7 = this.pos;
               int var8 = this.limit;
               var4 = var2;
               int var6 = var8;
               int var5 = var7;
               if(var3 < var8 - var7) {
                  var6 = var7 + var3;
                  var5 = var7;
                  var4 = var2;
               }

               while(true) {
                  var2 = var4;
                  if(var5 >= var6) {
                     break;
                  }

                  var4 = this.buffer[var5];
                  var2 = var4;
                  if(var4 == 10) {
                     break;
                  }

                  if(var4 == 13) {
                     var2 = var4;
                     break;
                  }

                  ++var5;
               }

               var3 -= var5 - this.pos;
               this.pos = var5;
            } else {
               var2 = this.read();
               if(var2 < 0) {
                  return var1 - var3;
               }

               --var3;
            }
         }
      }

      return var4;
   }

   public void skip() throws IOException {
      this.read();
   }

   public void skipRestOfLine() throws IOException {
      while(true) {
         int var1 = this.read();
         if(var1 >= 0) {
            if(var1 != 13) {
               if(var1 != 10) {
                  continue;
               }

               return;
            }

            var1 = this.read();
            if(var1 >= 0 && var1 != 10) {
               this.unread();
               return;
            }
         }

         return;
      }
   }

   public final void skip_quick() throws IOException {
      ++this.pos;
   }

   public void unread() throws IOException {
      if(this.pos == 0) {
         throw new IOException("unread too much");
      } else {
         --this.pos;
         char var2 = this.buffer[this.pos];
         if(var2 == 10 || var2 == 13) {
            if(this.pos > 0 && var2 == 10 && this.getConvertCR() && this.buffer[this.pos - 1] == 13) {
               --this.pos;
            }

            if(this.pos < this.lineStartPos) {
               --this.lineNumber;
               int var5 = this.pos;

               int var3;
               while(true) {
                  var3 = var5;
                  if(var5 <= 0) {
                     break;
                  }

                  char[] var1 = this.buffer;
                  var3 = var5 - 1;
                  char var4 = var1[var3];
                  if(var4 != 13) {
                     var5 = var3;
                     if(var4 != 10) {
                        continue;
                     }
                  }

                  ++var3;
                  break;
               }

               this.lineStartPos = var3;
            }
         }

      }
   }

   public void unread_quick() {
      --this.pos;
   }
}
