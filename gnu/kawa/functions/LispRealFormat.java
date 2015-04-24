package gnu.kawa.functions;

import gnu.math.ExponentialFormat;
import gnu.math.FixedRealFormat;
import gnu.math.RealNum;
import gnu.text.ReportFormat;
import java.io.IOException;
import java.io.Writer;
import java.text.FieldPosition;

class LispRealFormat extends ReportFormat {

   int arg1;
   int arg2;
   int arg3;
   int arg4;
   int arg5;
   int arg6;
   int arg7;
   int argsUsed;
   boolean internalPad;
   char op;
   boolean showPlus;


   LispRealFormat() {
      byte var1;
      if(this.arg1 != -1342177280 && this.arg2 != -1342177280 && this.arg3 != -1342177280 && this.arg4 != -1342177280 && this.arg5 != -1342177280 && this.arg6 != -1342177280 && this.arg7 != -1342177280) {
         var1 = 0;
      } else {
         var1 = 1;
      }

      this.argsUsed = var1;
      if(this.arg1 == -1610612736) {
         this.argsUsed += 2;
      }

      if(this.arg2 == -1610612736) {
         this.argsUsed += 2;
      }

      if(this.arg3 == -1610612736) {
         this.argsUsed += 2;
      }

      if(this.arg4 == -1610612736) {
         this.argsUsed += 2;
      }

      if(this.arg5 == -1610612736) {
         this.argsUsed += 2;
      }

      if(this.arg6 == -1610612736) {
         this.argsUsed += 2;
      }

      if(this.arg7 == -1610612736) {
         this.argsUsed += 2;
      }

   }

   public int format(Object[] var1, int var2, Writer var3, FieldPosition var4) throws IOException {
      StringBuffer var5 = new StringBuffer(100);
      java.text.Format var6 = this.resolve(var1, var2);
      var2 += this.argsUsed >> 1;
      var6.format((RealNum)var1[var2], var5, var4);
      var3.write(var5.toString());
      return var2 + 1;
   }

   public java.text.Format resolve(Object[] var1, int var2) {
      char var3;
      int var5;
      int var6;
      int var7;
      int var8;
      FixedRealFormat var10;
      if(this.op == 36) {
         var10 = new FixedRealFormat();
         var6 = getParam(this.arg1, 2, var1, var2);
         var5 = var2;
         if(this.arg1 == -1610612736) {
            var5 = var2 + 1;
         }

         var7 = getParam(this.arg2, 1, var1, var5);
         var2 = var5;
         if(this.arg2 == -1610612736) {
            var2 = var5 + 1;
         }

         var8 = getParam(this.arg3, 0, var1, var2);
         var5 = var2;
         if(this.arg3 == -1610612736) {
            var5 = var2 + 1;
         }

         var3 = getParam(this.arg4, ' ', var1, var5);
         if(this.arg4 == -1610612736) {
            ;
         }

         var10.setMaximumFractionDigits(var6);
         var10.setMinimumIntegerDigits(var7);
         var10.width = var8;
         var10.padChar = var3;
         var10.internalPad = this.internalPad;
         var10.showPlus = this.showPlus;
         return var10;
      } else if(this.op == 70) {
         var10 = new FixedRealFormat();
         var6 = getParam(this.arg1, 0, var1, var2);
         var5 = var2;
         if(this.arg1 == -1610612736) {
            var5 = var2 + 1;
         }

         var7 = getParam(this.arg2, -1, var1, var5);
         var2 = var5;
         if(this.arg2 == -1610612736) {
            var2 = var5 + 1;
         }

         var8 = getParam(this.arg3, 0, var1, var2);
         var5 = var2;
         if(this.arg3 == -1610612736) {
            var5 = var2 + 1;
         }

         var10.overflowChar = getParam(this.arg4, '\u0000', var1, var5);
         var2 = var5;
         if(this.arg4 == -1610612736) {
            var2 = var5 + 1;
         }

         var3 = getParam(this.arg5, ' ', var1, var2);
         if(this.arg5 == -1610612736) {
            ;
         }

         var10.setMaximumFractionDigits(var7);
         var10.setMinimumIntegerDigits(0);
         var10.width = var6;
         var10.scale = var8;
         var10.padChar = var3;
         var10.internalPad = this.internalPad;
         var10.showPlus = this.showPlus;
         return var10;
      } else {
         ExponentialFormat var4 = new ExponentialFormat();
         var4.exponentShowSign = true;
         var4.width = getParam(this.arg1, 0, var1, var2);
         var5 = var2;
         if(this.arg1 == -1610612736) {
            var5 = var2 + 1;
         }

         var4.fracDigits = getParam(this.arg2, -1, var1, var5);
         var2 = var5;
         if(this.arg2 == -1610612736) {
            var2 = var5 + 1;
         }

         var4.expDigits = getParam(this.arg3, 0, var1, var2);
         var5 = var2;
         if(this.arg3 == -1610612736) {
            var5 = var2 + 1;
         }

         var4.intDigits = getParam(this.arg4, 1, var1, var5);
         var2 = var5;
         if(this.arg4 == -1610612736) {
            var2 = var5 + 1;
         }

         var4.overflowChar = getParam(this.arg5, '\u0000', var1, var2);
         var5 = var2;
         if(this.arg5 == -1610612736) {
            var5 = var2 + 1;
         }

         var4.padChar = getParam(this.arg6, ' ', var1, var5);
         var2 = var5;
         if(this.arg6 == -1610612736) {
            var2 = var5 + 1;
         }

         var4.exponentChar = getParam(this.arg7, 'E', var1, var2);
         if(this.arg7 == -1610612736) {
            ;
         }

         boolean var9;
         if(this.op == 71) {
            var9 = true;
         } else {
            var9 = false;
         }

         var4.general = var9;
         var4.showPlus = this.showPlus;
         return var4;
      }
   }
}
