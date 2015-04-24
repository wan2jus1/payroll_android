package gnu.expr;

import gnu.expr.ClassExp;
import gnu.expr.Compilation;
import gnu.expr.ExpExpVisitor;
import gnu.expr.Expression;
import gnu.expr.LambdaExp;
import gnu.expr.ScopeExp;

public class ChainLambdas extends ExpExpVisitor {

   public static void chainLambdas(Expression var0, Compilation var1) {
      ChainLambdas var2 = new ChainLambdas();
      var2.setContext(var1);
      var2.visit(var0, (Object)null);
   }

   protected Expression visitClassExp(ClassExp var1, ScopeExp var2) {
      LambdaExp var3 = this.currentLambda;
      if(var3 != null && !(var3 instanceof ClassExp)) {
         var1.nextSibling = var3.firstChild;
         var3.firstChild = var1;
      }

      this.visitScopeExp(var1, (ScopeExp)var2);
      return var1;
   }

   protected Expression visitLambdaExp(LambdaExp var1, ScopeExp var2) {
      LambdaExp var3 = this.currentLambda;
      if(var3 != null && !(var3 instanceof ClassExp)) {
         var1.nextSibling = var3.firstChild;
         var3.firstChild = var1;
      }

      var1.outer = var2;
      var1.firstChild = null;
      var1.visitChildrenOnly(this, var1);
      var1.visitProperties(this, var1);
      var3 = null;

      LambdaExp var4;
      for(LambdaExp var5 = var1.firstChild; var5 != null; var5 = var4) {
         var4 = var5.nextSibling;
         var5.nextSibling = var3;
         var3 = var5;
      }

      var1.firstChild = var3;
      if(var1.getName() == null && var1.nameDecl != null) {
         var1.setName(var1.nameDecl.getName());
      }

      var1.setIndexes();
      if(var1.mustCompile()) {
         this.comp.mustCompileHere();
      }

      return var1;
   }

   protected Expression visitScopeExp(ScopeExp var1, ScopeExp var2) {
      var1.outer = var2;
      var1.visitChildren(this, var1);
      var1.setIndexes();
      if(var1.mustCompile()) {
         this.comp.mustCompileHere();
      }

      return var1;
   }
}
