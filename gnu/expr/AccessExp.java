package gnu.expr;

import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.mapping.Symbol;

public abstract class AccessExp extends Expression {

   Declaration binding;
   private Declaration context;
   Object symbol;


   public final Declaration contextDecl() {
      return this.context;
   }

   public final Declaration getBinding() {
      return this.binding;
   }

   public final String getName() {
      return this.symbol instanceof Symbol?((Symbol)this.symbol).getName():this.symbol.toString();
   }

   public final String getSimpleName() {
      if(this.symbol instanceof String) {
         return (String)this.symbol;
      } else {
         if(this.symbol instanceof Symbol) {
            Symbol var1 = (Symbol)this.symbol;
            if(var1.hasEmptyNamespace()) {
               return var1.getLocalName();
            }
         }

         return null;
      }
   }

   public final Object getSymbol() {
      return this.symbol;
   }

   public final void setBinding(Declaration var1) {
      this.binding = var1;
   }

   public final void setContextDecl(Declaration var1) {
      this.context = var1;
   }

   public String string_name() {
      return this.symbol.toString();
   }
}
