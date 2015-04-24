package gnu.ecmascript;

import gnu.mapping.InPort;
import gnu.mapping.Procedure1;

class Prompter extends Procedure1 {

   public Object apply1(Object var1) {
      return this.prompt((InPort)var1);
   }

   String prompt(InPort var1) {
      return "(EcmaScript:" + (var1.getLineNumber() + 1) + ") ";
   }
}
