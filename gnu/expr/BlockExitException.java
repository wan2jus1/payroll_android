package gnu.expr;

import gnu.expr.ExitExp;

class BlockExitException extends RuntimeException {

   ExitExp exit;
   Object result;


   public BlockExitException(ExitExp var1, Object var2) {
      this.exit = var1;
      this.result = var2;
   }
}
