package gnu.kawa.functions;

import gnu.math.DFloNum;
import gnu.math.IntNum;
import gnu.math.Numeric;
import gnu.text.Char;
import gnu.text.ReportFormat;
import java.io.IOException;
import java.io.Writer;
import java.text.FieldPosition;

class LispEscapeFormat extends ReportFormat {

   public static final int ESCAPE_ALL = 242;
   public static final int ESCAPE_NORMAL = 241;
   public static final LispEscapeFormat alwaysTerminate = new LispEscapeFormat(0, -1073741824);
   boolean escapeAll;
   int param1;
   int param2;
   int param3;


   public LispEscapeFormat(int var1, int var2) {
      this.param1 = var1;
      this.param2 = var2;
      this.param3 = -1073741824;
   }

   public LispEscapeFormat(int var1, int var2, int var3) {
      this.param1 = var1;
      this.param2 = var2;
      this.param3 = var3;
   }

   static Numeric getParam(int var0, Object[] var1, int var2) {
      if(var0 == -1342177280) {
         return IntNum.make(var1.length - var2);
      } else if(var0 == -1610612736) {
         Object var3 = var1[var2];
         return (Numeric)(var3 instanceof Numeric?(Numeric)var3:(var3 instanceof Number?(!(var3 instanceof Float) && !(var3 instanceof Double)?IntNum.make(((Number)var3).longValue()):new DFloNum(((Number)var3).doubleValue())):(var3 instanceof Char?new IntNum(((Char)var3).intValue()):(var3 instanceof Character?new IntNum(((Character)var3).charValue()):new DFloNum(Double.NaN)))));
      } else {
         return IntNum.make(var0);
      }
   }

   public int format(Object[] var1, int var2, Writer var3, FieldPosition var4) throws IOException {
      boolean var8 = true;
      boolean var7 = true;
      byte var6 = 0;
      if(this.param1 == -1073741824) {
         if(var2 != var1.length) {
            var7 = false;
         }
      } else if(this.param2 == -1073741824 && this.param1 == 0) {
         var7 = true;
      } else {
         Numeric var10 = getParam(this.param1, var1, var2);
         int var5 = var2;
         if(this.param1 == -1610612736) {
            var5 = var2 + 1;
         }

         if(this.param2 == -1073741824) {
            var7 = var10.isZero();
            var2 = var5;
         } else {
            Numeric var11 = getParam(this.param2, var1, var5);
            var2 = var5;
            if(this.param2 == -1610612736) {
               var2 = var5 + 1;
            }

            if(this.param3 == -1073741824) {
               var7 = var10.equals(var11);
            } else {
               Numeric var9 = getParam(this.param3, var1, var2);
               var5 = var2;
               if(this.param3 == -1610612736) {
                  var5 = var2 + 1;
               }

               if(var11.geq(var10) && var9.geq(var11)) {
                  var7 = var8;
               } else {
                  var7 = false;
               }

               var2 = var5;
            }
         }
      }

      short var12;
      if(!var7) {
         var12 = var6;
      } else if(this.escapeAll) {
         var12 = 242;
      } else {
         var12 = 241;
      }

      return result(var12, var2);
   }
}
