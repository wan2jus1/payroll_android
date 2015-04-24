package gnu.kawa.functions;

import gnu.text.ReportFormat;
import java.io.IOException;
import java.io.Writer;
import java.text.FieldPosition;

class LispChoiceFormat extends ReportFormat {

   java.text.Format[] choices;
   boolean lastIsDefault;
   int param;
   boolean skipIfFalse;
   boolean testBoolean;


   public int format(Object[] var1, int var2, Writer var3, FieldPosition var4) throws IOException {
      byte var6 = 0;
      java.text.Format var8;
      if(this.testBoolean) {
         java.text.Format[] var5 = this.choices;
         if(var1[var2] != Boolean.FALSE) {
            var6 = 1;
         }

         var8 = var5[var6];
         ++var2;
      } else if(!this.skipIfFalse) {
         int var7 = getParam(this.param, -1610612736, var1, var2);
         int var9 = var2;
         if(this.param == -1610612736) {
            var9 = var2 + 1;
         }

         label38: {
            if(var7 >= 0) {
               var2 = var7;
               if(var7 < this.choices.length) {
                  break label38;
               }
            }

            if(!this.lastIsDefault) {
               return var9;
            }

            var2 = this.choices.length - 1;
         }

         var8 = this.choices[var2];
         var2 = var9;
      } else {
         if(var1[var2] == Boolean.FALSE) {
            return var2 + 1;
         }

         var8 = this.choices[0];
      }

      return ReportFormat.format(var8, var1, var2, (Writer)var3, var4);
   }
}
