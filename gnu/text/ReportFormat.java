package gnu.text;

import gnu.lists.Consumer;
import gnu.text.Char;
import gnu.text.Printable;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.FieldPosition;
import java.text.Format;
import java.text.MessageFormat;
import java.text.ParsePosition;

public abstract class ReportFormat extends Format {

   public static final int PARAM_FROM_COUNT = -1342177280;
   public static final int PARAM_FROM_LIST = -1610612736;
   public static final int PARAM_UNSPECIFIED = -1073741824;


   public static int format(Format var0, Object[] var1, int var2, Writer var3, FieldPosition var4) throws IOException {
      if(var0 instanceof ReportFormat) {
         return ((ReportFormat)var0).format((Object[])var1, var2, (Writer)var3, var4);
      } else {
         StringBuffer var5 = new StringBuffer();
         if(var0 instanceof MessageFormat) {
            var2 = format(var0, var1, var2, (StringBuffer)var5, var4);
         } else {
            var0.format(var1[var2], var5, var4);
            ++var2;
         }

         int var6 = var5.length();
         char[] var7 = new char[var6];
         var5.getChars(0, var6, var7, 0);
         var3.write(var7);
         return var2;
      }
   }

   public static int format(Format var0, Object[] var1, int var2, StringBuffer var3, FieldPosition var4) {
      if(var0 instanceof ReportFormat) {
         return ((ReportFormat)var0).format((Object[])var1, var2, (StringBuffer)var3, var4);
      } else {
         int var6;
         if(var0 instanceof MessageFormat) {
            var6 = ((Object[])var1).length - var2;
            if(var2 > 0) {
               Object[] var5 = new Object[((Object[])var1).length - var2];
               System.arraycopy(var1, var2, var5, 0, var5.length);
               var1 = var5;
            }
         } else {
            var1 = ((Object[])var1)[var2];
            var6 = 1;
         }

         var0.format(var1, var3, var4);
         return var2 + var6;
      }
   }

   protected static char getParam(int var0, char var1, Object[] var2, int var3) {
      return (char)getParam(var0, (int)var1, var2, var3);
   }

   protected static int getParam(int var0, int var1, Object[] var2, int var3) {
      int var4;
      if(var0 == -1342177280) {
         var4 = var2.length - var3;
      } else if(var0 == -1610612736) {
         var4 = var1;
         if(var2 != null) {
            return getParam(var2[var3], var1);
         }
      } else {
         var4 = var1;
         if(var0 != -1073741824) {
            return var0;
         }
      }

      return var4;
   }

   public static int getParam(Object var0, int var1) {
      if(var0 instanceof Number) {
         var1 = ((Number)var0).intValue();
      } else {
         if(var0 instanceof Character) {
            return ((Character)var0).charValue();
         }

         if(var0 instanceof Char) {
            return ((Char)var0).charValue();
         }
      }

      return var1;
   }

   public static int nextArg(int var0) {
      return 16777215 & var0;
   }

   public static void print(Writer var0, String var1) throws IOException {
      if(var0 instanceof PrintWriter) {
         ((PrintWriter)var0).print(var1);
      } else {
         var0.write(var1.toCharArray());
      }
   }

   public static void print(Object var0, Consumer var1) {
      if(var0 instanceof Printable) {
         ((Printable)var0).print(var1);
      } else {
         String var2;
         if(var0 == null) {
            var2 = "null";
         } else {
            var2 = var0.toString();
         }

         var1.write(var2);
      }
   }

   public static int result(int var0, int var1) {
      return var0 << 24 | var1;
   }

   public static int resultCode(int var0) {
      return var0 >>> 24;
   }

   public int format(Object var1, int var2, Writer var3, FieldPosition var4) throws IOException {
      return var1 instanceof Object[]?this.format((Object[])((Object[])((Object[])var1)), var2, (Writer)var3, var4):this.format((Object[])(new Object[]{var1}), var2, (Writer)var3, var4);
   }

   public abstract int format(Object[] var1, int var2, Writer var3, FieldPosition var4) throws IOException;

   public int format(Object[] var1, int var2, StringBuffer var3, FieldPosition var4) {
      CharArrayWriter var5 = new CharArrayWriter();

      try {
         var2 = this.format((Object[])var1, var2, (Writer)var5, var4);
      } catch (IOException var6) {
         throw new Error("unexpected exception: " + var6);
      }

      if(var2 < 0) {
         return var2;
      } else {
         var3.append(var5.toCharArray());
         return var2;
      }
   }

   public StringBuffer format(Object var1, StringBuffer var2, FieldPosition var3) {
      this.format((Object[])((Object[])((Object[])var1)), 0, (StringBuffer)var2, var3);
      return var2;
   }

   public Object parseObject(String var1, ParsePosition var2) {
      throw new Error("ReportFormat.parseObject - not implemented");
   }
}
