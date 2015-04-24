package gnu.kawa.functions;

import gnu.kawa.functions.LispFormat;
import gnu.text.ReportFormat;
import java.io.IOException;
import java.io.Writer;
import java.text.FieldPosition;

class LispIterationFormat extends ReportFormat {

   boolean atLeastOnce;
   java.text.Format body;
   int maxIterations;
   boolean seenAt;
   boolean seenColon;


   public static int format(java.text.Format var0, int var1, Object[] var2, int var3, Writer var4, boolean var5, boolean var6) throws IOException {
      int var8 = 0;

      int var9;
      while(true) {
         if(var8 == var1 && var1 != -1) {
            var9 = var3;
            break;
         }

         if(var3 == var2.length) {
            var9 = var3;
            if(var8 > 0) {
               break;
            }

            var9 = var3;
            if(!var6) {
               break;
            }
         }

         if(var5) {
            Object[] var7 = LispFormat.asArray(var2[var3]);
            if(var7 == null) {
               ;
            }

            int var10 = ReportFormat.format(var0, var7, 0, (Writer)var4, (FieldPosition)null);
            ++var3;
            var9 = var3;
            if(ReportFormat.resultCode(var10) == 242) {
               break;
            }
         } else {
            var9 = ReportFormat.format(var0, var2, var3, (Writer)var4, (FieldPosition)null);
            var3 = var9;
            if(var9 < 0) {
               return ReportFormat.nextArg(var9);
            }
         }

         ++var8;
      }

      return var9;
   }

   public int format(Object[] var1, int var2, Writer var3, FieldPosition var4) throws IOException {
      int var7 = getParam(this.maxIterations, -1, var1, var2);
      int var6 = var2;
      if(this.maxIterations == -1610612736) {
         var6 = var2 + 1;
      }

      java.text.Format var5 = this.body;
      Object var10 = var5;
      var2 = var6;
      if(var5 == null) {
         var2 = var6 + 1;
         var10 = var1[var6];
         if(var10 instanceof java.text.Format) {
            var10 = (java.text.Format)var10;
         } else {
            try {
               var10 = new LispFormat(var10.toString());
            } catch (Exception var8) {
               print(var3, "<invalid argument for \"~{~}\" format>");
               return var1.length;
            }
         }
      }

      if(this.seenAt) {
         return format((java.text.Format)var10, var7, var1, var2, var3, this.seenColon, this.atLeastOnce);
      } else {
         Object var9 = var1[var2];
         Object[] var11 = LispFormat.asArray(var9);
         if(var11 == null) {
            var3.write("{" + var9 + "}".toString());
         } else {
            format((java.text.Format)var10, var7, var11, 0, var3, this.seenColon, this.atLeastOnce);
         }

         return var2 + 1;
      }
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append("LispIterationFormat[");
      var1.append(this.body);
      var1.append("]");
      return var1.toString();
   }
}
