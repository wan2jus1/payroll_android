package gnu.kawa.functions;

import gnu.mapping.OutPort;
import gnu.text.ReportFormat;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.FieldPosition;
import java.text.ParsePosition;

public class ObjectFormat extends ReportFormat {

   private static ObjectFormat plainFormat;
   private static ObjectFormat readableFormat;
   int maxChars;
   boolean readable;


   public ObjectFormat(boolean var1) {
      this.readable = var1;
      this.maxChars = -1073741824;
   }

   public ObjectFormat(boolean var1, int var2) {
      this.readable = var1;
      this.maxChars = var2;
   }

   public static int format(Object[] var0, int var1, Writer var2, int var3, boolean var4) throws IOException {
      Object var5;
      if(var1 >= var0.length) {
         var5 = "#<missing format argument>";
         --var1;
         var4 = false;
         var3 = -1;
      } else {
         var5 = var0[var1];
      }

      format(var5, var2, var3, var4);
      return var1 + 1;
   }

   public static boolean format(Object var0, Writer var1, int var2, boolean var3) throws IOException {
      if(var2 < 0 && var1 instanceof OutPort) {
         print(var0, (OutPort)var1, var3);
         return true;
      } else if(var2 < 0 && var1 instanceof CharArrayWriter) {
         OutPort var7 = new OutPort(var1);
         print(var0, var7, var3);
         var7.close();
         return true;
      } else {
         CharArrayWriter var4 = new CharArrayWriter();
         OutPort var5 = new OutPort(var4);
         print(var0, var5, var3);
         var5.close();
         int var6 = var4.size();
         if(var2 >= 0 && var6 > var2) {
            var1.write(var4.toCharArray(), 0, var2);
            return false;
         } else {
            var4.writeTo(var1);
            return true;
         }
      }
   }

   public static ObjectFormat getInstance(boolean var0) {
      if(var0) {
         if(readableFormat == null) {
            readableFormat = new ObjectFormat(true);
         }

         return readableFormat;
      } else {
         if(plainFormat == null) {
            plainFormat = new ObjectFormat(false);
         }

         return plainFormat;
      }
   }

   private static void print(Object param0, OutPort param1, boolean param2) {
      // $FF: Couldn't be decompiled
   }

   public int format(Object[] var1, int var2, Writer var3, FieldPosition var4) throws IOException {
      int var6 = getParam(this.maxChars, -1, var1, var2);
      int var5 = var2;
      if(this.maxChars == -1610612736) {
         var5 = var2 + 1;
      }

      return format(var1, var5, var3, var6, this.readable);
   }

   public Object parseObject(String var1, ParsePosition var2) {
      throw new RuntimeException("ObjectFormat.parseObject - not implemented");
   }
}
