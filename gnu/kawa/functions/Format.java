package gnu.kawa.functions;

import gnu.kawa.functions.ParseFormat;
import gnu.lists.FString;
import gnu.mapping.CharArrayOutPort;
import gnu.mapping.OutPort;
import gnu.mapping.Procedure;
import gnu.mapping.ProcedureN;
import gnu.mapping.Values;
import gnu.text.ReportFormat;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.text.FieldPosition;
import java.text.MessageFormat;

public class Format extends ProcedureN {

   public static final Format format = new Format();


   static {
      format.setName("format");
      format.setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileMisc:validateApplyFormat");
   }

   static Object[] drop2(Object[] var0) {
      int var2 = var0.length - 2;
      Object[] var1 = new Object[var2];
      System.arraycopy(var0, 2, var1, 0, var2);
      return var1;
   }

   public static Object format(Object ... var0) {
      Object var1 = var0[0];
      if(var1 == Boolean.TRUE) {
         format(OutPort.outDefault(), var0, 1);
         return Values.empty;
      } else if(var1 == Boolean.FALSE) {
         return formatToString(1, var0);
      } else if(!(var1 instanceof MessageFormat) && !(var1 instanceof CharSequence) && !(var1 instanceof ReportFormat)) {
         if(var1 instanceof Writer) {
            format((Writer)var1, var0, 1);
            return Values.empty;
         } else if(var1 instanceof OutputStream) {
            formatToOutputStream((OutputStream)var1, var0[1], drop2(var0));
            return Values.empty;
         } else {
            throw new RuntimeException("bad first argument to format");
         }
      } else {
         return formatToString(0, var0);
      }
   }

   public static void format(Writer var0, Object[] var1, int var2) {
      int var5 = var2 + 1;
      Object var3 = var1[var2];
      Object[] var4 = new Object[var1.length - var5];
      System.arraycopy(var1, var5, var4, 0, var4.length);
      formatToWriter(var0, var3, var4);
   }

   public static FString formatToFString(char var0, Object var1, Object[] var2) {
      ReportFormat var3 = ParseFormat.asFormat(var1, var0);
      CharArrayOutPort var5 = new CharArrayOutPort();

      try {
         var3.format((Object[])var2, 0, (Writer)var5, (FieldPosition)null);
      } catch (IOException var4) {
         throw new RuntimeException("Error in format: " + var4);
      }

      char[] var6 = var5.toCharArray();
      var5.close();
      return new FString(var6);
   }

   public static void formatToOutputStream(OutputStream var0, Object var1, Object ... var2) {
      OutPort var3 = new OutPort(var0);
      format(new Object[]{var3, var1, var2});
      var3.closeThis();
   }

   public static String formatToString(int var0, Object ... var1) {
      CharArrayOutPort var2 = new CharArrayOutPort();
      format(var2, var1, var0);
      String var3 = var2.toString();
      var2.close();
      return var3;
   }

   public static void formatToWriter(Writer param0, Object param1, Object ... param2) {
      // $FF: Couldn't be decompiled
   }

   public Object applyN(Object[] var1) {
      return format(var1);
   }
}
