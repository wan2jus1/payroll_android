package gnu.bytecode;


public class Access {

   public static final short ABSTRACT = 1024;
   public static final short ANNOTATION = 8192;
   public static final short BRIDGE = 64;
   public static final char CLASS_CONTEXT = 'C';
   public static final short CLASS_MODIFIERS = 30257;
   public static final short ENUM = 16384;
   public static final char FIELD_CONTEXT = 'F';
   public static final short FIELD_MODIFIERS = 20703;
   public static final short FINAL = 16;
   public static final char INNERCLASS_CONTEXT = 'I';
   public static final short INNERCLASS_MODIFIERS = 30239;
   public static final short INTERFACE = 512;
   public static final char METHOD_CONTEXT = 'M';
   public static final short METHOD_MODIFIERS = 7679;
   public static final short NATIVE = 256;
   public static final short PRIVATE = 2;
   public static final short PROTECTED = 4;
   public static final short PUBLIC = 1;
   public static final short STATIC = 8;
   public static final short STRICT = 2048;
   public static final short SUPER = 32;
   public static final short SYNCHRONIZED = 32;
   public static final short SYNTHETIC = 4096;
   public static final short TRANSIENT = 128;
   public static final short VARARGS = 128;
   public static final short VOLATILE = 64;


   public static String toString(int var0) {
      return toString(var0, '\u0000');
   }

   public static String toString(int var0, char var1) {
      short var4;
      if(var1 == 67) {
         var4 = 30257;
      } else if(var1 == 73) {
         var4 = 30239;
      } else if(var1 == 70) {
         var4 = 20703;
      } else if(var1 == 77) {
         var4 = 7679;
      } else {
         var4 = 32767;
      }

      short var5 = (short)(~var4 & var0);
      var0 &= var4;
      StringBuffer var3 = new StringBuffer();
      if((var0 & 1) != 0) {
         var3.append(" public");
      }

      if((var0 & 2) != 0) {
         var3.append(" private");
      }

      if((var0 & 4) != 0) {
         var3.append(" protected");
      }

      if((var0 & 8) != 0) {
         var3.append(" static");
      }

      if((var0 & 16) != 0) {
         var3.append(" final");
      }

      String var2;
      if((var0 & 32) != 0) {
         if(var1 == 67) {
            var2 = " super";
         } else {
            var2 = " synchronized";
         }

         var3.append(var2);
      }

      if((var0 & 64) != 0) {
         if(var1 == 77) {
            var2 = " bridge";
         } else {
            var2 = " volatile";
         }

         var3.append(var2);
      }

      if((var0 & 128) != 0) {
         if(var1 == 77) {
            var2 = " varargs";
         } else {
            var2 = " transient";
         }

         var3.append(var2);
      }

      if((var0 & 256) != 0) {
         var3.append(" native");
      }

      if((var0 & 512) != 0) {
         var3.append(" interface");
      }

      if((var0 & 1024) != 0) {
         var3.append(" abstract");
      }

      if((var0 & 2048) != 0) {
         var3.append(" strict");
      }

      if((var0 & 16384) != 0) {
         var3.append(" enum");
      }

      if((var0 & 4096) != 0) {
         var3.append(" synthetic");
      }

      if((var0 & 8192) != 0) {
         var3.append(" annotation");
      }

      if(var5 != 0) {
         var3.append(" unknown-flags:0x");
         var3.append(Integer.toHexString(var5));
      }

      return var3.toString();
   }
}
