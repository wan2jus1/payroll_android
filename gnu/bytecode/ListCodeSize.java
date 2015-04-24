package gnu.bytecode;

import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;

public class ListCodeSize {

   public static final void main(String[] param0) {
      // $FF: Couldn't be decompiled
   }

   static void print(Method var0) {
      System.out.print(var0);
      CodeAttr var1 = var0.getCode();
      if(var1 == null) {
         System.out.print(": no code");
      } else {
         System.out.print(": ");
         System.out.print(var1.getPC());
         System.out.print(" bytes");
      }

      System.out.println();
   }

   public static void usage() {
      System.err.println("Usage: class methodname ...");
      System.exit(-1);
   }
}
