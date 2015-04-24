package kawa.standard;

import gnu.mapping.InPort;
import gnu.mapping.Procedure0or1;
import gnu.mapping.WrongType;
import java.io.InputStream;
import java.io.Reader;

public class readchar extends Procedure0or1 {

   public static final readchar peekChar = new readchar(true);
   public static final readchar readChar = new readchar(false);
   boolean peeking;


   public readchar(boolean var1) {
      String var2;
      if(var1) {
         var2 = "peek-char";
      } else {
         var2 = "read-char";
      }

      super(var2);
      this.peeking = var1;
   }

   public final Object apply0() {
      return this.readChar((InPort)InPort.inDefault());
   }

   public final Object apply1(Object var1) {
      if(var1 instanceof InPort) {
         return this.readChar((InPort)((InPort)var1));
      } else if(var1 instanceof Reader) {
         return this.readChar((Reader)((Reader)var1));
      } else if(var1 instanceof InputStream) {
         return this.readChar((InputStream)((InputStream)var1));
      } else {
         throw new WrongType(this, 1, var1, "<input-port>");
      }
   }

   final Object readChar(InPort param1) {
      // $FF: Couldn't be decompiled
   }

   final Object readChar(InputStream param1) {
      // $FF: Couldn't be decompiled
   }

   final Object readChar(Reader param1) {
      // $FF: Couldn't be decompiled
   }
}
