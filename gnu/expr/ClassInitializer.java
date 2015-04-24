package gnu.expr;

import gnu.bytecode.CodeAttr;
import gnu.expr.ClassExp;
import gnu.expr.Compilation;
import gnu.expr.Initializer;
import gnu.expr.LambdaExp;
import gnu.expr.Target;

public class ClassInitializer extends Initializer {

   ClassExp cexp;


   public ClassInitializer(ClassExp var1, Compilation var2) {
      this.field = var1.allocFieldFor(var2);
      var1.compileMembers(var2);
      this.cexp = var1;
      if(this.field.getStaticFlag()) {
         this.next = var2.clinitChain;
         var2.clinitChain = this;
      } else {
         LambdaExp var3 = var1.getOwningLambda();
         this.next = var3.initChain;
         var3.initChain = this;
      }
   }

   public void emit(Compilation var1) {
      CodeAttr var2 = var1.getCode();
      if(!this.field.getStaticFlag()) {
         var2.emitPushThis();
      }

      this.cexp.compilePushClass(var1, Target.pushValue(Compilation.typeClassType));
      if(this.field.getStaticFlag()) {
         var2.emitPutStatic(this.field);
      } else {
         var2.emitPutField(this.field);
      }
   }
}
