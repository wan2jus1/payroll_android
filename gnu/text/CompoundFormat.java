package gnu.text;

import gnu.text.ReportFormat;
import java.io.IOException;
import java.io.Writer;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

public class CompoundFormat extends ReportFormat {

   protected Format[] formats;
   protected int length;


   public CompoundFormat(Format[] var1) {
      this.formats = var1;
      this.length = var1.length;
   }

   public CompoundFormat(Format[] var1, int var2) {
      this.formats = var1;
      this.length = var2;
   }

   public int format(Object[] var1, int var2, Writer var3, FieldPosition var4) throws IOException {
      for(int var7 = 0; var7 < this.length; ++var7) {
         Format var5 = this.formats[var7];
         if(var5 instanceof ReportFormat) {
            int var8 = ((ReportFormat)var5).format((Object[])var1, var2, (Writer)var3, var4);
            var2 = var8;
            if(var8 < 0) {
               return var8;
            }
         } else if(var2 >= var1.length) {
            var3.write("#<missing format argument>");
         } else {
            StringBuffer var6 = new StringBuffer();
            var5.format(var1[var2], var6, var4);
            var3.write(var6.toString());
            ++var2;
         }
      }

      return var2;
   }

   public final int format(Object[] var1, int var2, StringBuffer var3, FieldPosition var4) {
      for(int var6 = 0; var6 < this.length; ++var6) {
         Format var5 = this.formats[var6];
         if(var5 instanceof ReportFormat) {
            int var7 = ((ReportFormat)var5).format((Object[])var1, var2, (StringBuffer)var3, var4);
            var2 = var7;
            if(var7 < 0) {
               return var7;
            }
         } else {
            var5.format(var1[var2], var3, var4);
            ++var2;
         }
      }

      return var2;
   }

   public Object parseObject(String var1, ParsePosition var2) {
      throw new Error("CompoundFormat.parseObject - not implemented");
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append("CompoundFormat[");

      for(int var2 = 0; var2 < this.length; ++var2) {
         if(var2 > 0) {
            var1.append(", ");
         }

         var1.append(this.formats[var2]);
      }

      var1.append("]");
      return var1.toString();
   }
}
