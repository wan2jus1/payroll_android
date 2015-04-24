package gnu.expr;

import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.ExpExpVisitor;
import gnu.expr.Expression;
import gnu.expr.LetExp;
import gnu.expr.ModuleExp;
import gnu.expr.NameLookup;
import gnu.expr.ReferenceExp;
import gnu.expr.ScopeExp;
import gnu.expr.SetExp;

public class ResolveNames extends ExpExpVisitor {

   protected NameLookup lookup;


   public ResolveNames() {
   }

   public ResolveNames(Compilation var1) {
      this.setContext(var1);
      this.lookup = var1.lexical;
   }

   public Declaration lookup(Expression var1, Object var2, boolean var3) {
      return this.lookup.lookup(var2, var3);
   }

   protected void push(ScopeExp var1) {
      this.lookup.push((ScopeExp)var1);
   }

   public void resolveModule(ModuleExp var1) {
      Compilation var2 = Compilation.setSaveCurrent(this.comp);

      try {
         this.push(var1);
         var1.visitChildren(this, (Object)null);
      } finally {
         Compilation.restoreCurrent(var2);
      }

   }

   protected Expression visitLetExp(LetExp var1, Void var2) {
      this.visitDeclarationTypes(var1);
      var1.visitInitializers(this, var2);
      this.push(var1);
      var1.body = (Expression)this.visit(var1.body, var2);
      this.lookup.pop((ScopeExp)var1);
      return var1;
   }

   protected Expression visitReferenceExp(ReferenceExp var1, Void var2) {
      if(var1.getBinding() == null) {
         Declaration var3 = this.lookup(var1, var1.getSymbol(), var1.isProcedureName());
         if(var3 != null) {
            var1.setBinding(var3);
         }
      }

      return var1;
   }

   protected Expression visitScopeExp(ScopeExp var1, Void var2) {
      this.visitDeclarationTypes(var1);
      this.push(var1);
      var1.visitChildren(this, var2);
      this.lookup.pop((ScopeExp)var1);
      return var1;
   }

   protected Expression visitSetExp(SetExp var1, Void var2) {
      if(var1.binding == null) {
         Declaration var3 = this.lookup(var1, var1.getSymbol(), var1.isFuncDef());
         if(var3 != null) {
            var3.setCanWrite(true);
         }

         var1.binding = var3;
      }

      return (Expression)super.visitSetExp(var1, var2);
   }
}
