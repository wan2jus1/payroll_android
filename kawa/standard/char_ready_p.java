package kawa.standard;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public class char_ready_p {

   public static boolean ready(Object var0) {
      try {
         if(!(var0 instanceof InputStream)) {
            if(var0 instanceof Reader) {
               return ((Reader)var0).ready();
            }

            throw new ClassCastException("invalid argument to char-ready?");
         }

         if(((InputStream)var0).available() > 0) {
            return true;
         }
      } catch (IOException var1) {
         ;
      }

      return false;
   }
}
