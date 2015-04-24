package gnu.kawa.functions;

import gnu.expr.QuoteExp;
import gnu.mapping.Procedure;
import gnu.mapping.Procedure0;

public class ConstantFunction0 extends Procedure0 {

   final QuoteExp constant;
   final Object value;


   public ConstantFunction0(String var1, QuoteExp var2) {
      super(var1);
      this.value = var2.getValue();
      this.constant = var2;
      this.setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileMisc:validateApplyConstantFunction0");
   }

   public ConstantFunction0(String var1, Object var2) {
      this(var1, (QuoteExp)QuoteExp.getInstance(var2));
   }

   public Object apply0() {
      return this.value;
   }
}
