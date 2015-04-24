package gnu.q2.lang;

import gnu.mapping.InPort;
import gnu.mapping.Procedure1;

class Prompter extends Procedure1 {

   public Object apply1(Object var1) {
      InPort var5 = (InPort)var1;
      int var4 = var5.getLineNumber() + 1;
      char var3 = var5.readState;
      if(var3 == 93) {
         return "<!--Q2:" + var4 + "-->";
      } else {
         char var2 = var3;
         if(var3 == 10) {
            var2 = 45;
         }

         return "#|--Q2:" + var4 + "|#" + var2;
      }
   }
}
