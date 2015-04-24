package gnu.text;

import java.io.Reader;

public class QueueReader extends Reader implements Appendable {

   boolean EOFseen;
   char[] buffer;
   int limit;
   int mark;
   int pos;
   int readAheadLimit;


   public QueueReader append(char param1) {
      // $FF: Couldn't be decompiled
   }

   public QueueReader append(CharSequence var1) {
      Object var2 = var1;
      if(var1 == null) {
         var2 = "null";
      }

      return this.append((CharSequence)var2, 0, ((CharSequence)var2).length());
   }

   public QueueReader append(CharSequence param1, int param2, int param3) {
      // $FF: Couldn't be decompiled
   }

   public void append(char[] var1) {
      this.append((char[])var1, 0, var1.length);
   }

   public void append(char[] var1, int var2, int var3) {
      synchronized(this){}

      try {
         this.reserveSpace(var3);
         System.arraycopy(var1, var2, this.buffer, this.limit, var3);
         this.limit += var3;
         this.notifyAll();
      } finally {
         ;
      }

   }

   public void appendEOF() {
      synchronized(this){}

      try {
         this.EOFseen = true;
      } finally {
         ;
      }

   }

   public void checkAvailable() {
   }

   public void close() {
      synchronized(this){}

      try {
         this.pos = 0;
         this.limit = 0;
         this.mark = 0;
         this.EOFseen = true;
         this.buffer = null;
      } finally {
         ;
      }

   }

   public void mark(int var1) {
      synchronized(this){}

      try {
         this.readAheadLimit = var1;
         this.mark = this.pos;
      } finally {
         ;
      }

   }

   public boolean markSupported() {
      return true;
   }

   public int read() {
      // $FF: Couldn't be decompiled
   }

   public int read(char[] param1, int param2, int param3) {
      // $FF: Couldn't be decompiled
   }

   public boolean ready() {
      synchronized(this){}
      boolean var4 = false;

      boolean var2;
      label45: {
         try {
            var4 = true;
            if(this.pos < this.limit) {
               var4 = false;
               break label45;
            }

            var2 = this.EOFseen;
            var4 = false;
         } finally {
            if(var4) {
               ;
            }
         }

         if(!var2) {
            var2 = false;
            return var2;
         }
      }

      var2 = true;
      return var2;
   }

   protected void reserveSpace(int var1) {
      if(this.buffer == null) {
         this.buffer = new char[var1 + 100];
      } else if(this.buffer.length < this.limit + var1) {
         this.resize(var1);
         return;
      }

   }

   public void reset() {
      synchronized(this){}

      try {
         if(this.readAheadLimit > 0) {
            this.pos = this.mark;
         }
      } finally {
         ;
      }

   }

   void resize(int var1) {
      int var3 = this.limit - this.pos;
      if(this.readAheadLimit > 0 && this.pos - this.mark <= this.readAheadLimit) {
         var3 = this.limit - this.mark;
      } else {
         this.mark = this.pos;
      }

      char[] var2;
      if(this.buffer.length < var3 + var1) {
         var2 = new char[var3 * 2 + var1];
      } else {
         var2 = this.buffer;
      }

      System.arraycopy(this.buffer, this.mark, var2, 0, var3);
      this.buffer = var2;
      this.pos -= this.mark;
      this.mark = 0;
      this.limit = var3;
   }
}
