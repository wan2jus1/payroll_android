package gnu.expr;

import gnu.bytecode.CodeAttr;
import gnu.bytecode.Scope;
import gnu.bytecode.Type;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.ExpVisitor;
import gnu.expr.Expression;
import gnu.expr.LambdaExp;
import gnu.expr.Language;
import gnu.expr.ModuleExp;

public abstract class ScopeExp extends Expression {

   static int counter;
   Declaration decls;
   protected int frameSize;
   public int id;
   Declaration last;
   public ScopeExp outer;
   private Scope scope;


   public ScopeExp() {
      int var1 = counter + 1;
      counter = var1;
      this.id = var1;
   }

   public static void duplicateDeclarationError(Declaration var0, Declaration var1, Compilation var2) {
      var2.error('e', (Declaration)var1, "duplicate declaration of \'", (String)"\'");
      var2.error('e', (Declaration)var0, "(this is the previous declaration of \'", (String)"\')");
   }

   public static int nesting(ScopeExp var0) {
      int var1;
      for(var1 = 0; var0 != null; ++var1) {
         var0 = var0.outer;
      }

      return var1;
   }

   public void add(Declaration var1) {
      if(this.last == null) {
         this.decls = var1;
      } else {
         this.last.next = var1;
      }

      this.last = var1;
      var1.context = this;
   }

   public void add(Declaration var1, Declaration var2) {
      if(var1 == null) {
         var2.next = this.decls;
         this.decls = var2;
      } else {
         var2.next = var1.next;
         var1.next = var2;
      }

      if(this.last == var1) {
         this.last = var2;
      }

      var2.context = this;
   }

   public final Declaration addDeclaration(Object var1) {
      Declaration var2 = new Declaration(var1);
      this.add(var2);
      return var2;
   }

   public final Declaration addDeclaration(Object var1, Type var2) {
      Declaration var3 = new Declaration(var1, var2);
      this.add(var3);
      return var3;
   }

   public final void addDeclaration(Declaration var1) {
      this.add(var1);
   }

   public int countDecls() {
      int var2 = 0;

      for(Declaration var1 = this.firstDecl(); var1 != null; var1 = var1.nextDecl()) {
         ++var2;
      }

      return var2;
   }

   public int countNonDynamicDecls() {
      int var2 = 0;

      int var3;
      for(Declaration var1 = this.firstDecl(); var1 != null; var2 = var3) {
         var3 = var2;
         if(!var1.getFlag(268435456L)) {
            var3 = var2 + 1;
         }

         var1 = var1.nextDecl();
      }

      return var2;
   }

   public LambdaExp currentLambda() {
      for(ScopeExp var1 = this; var1 != null; var1 = var1.outer) {
         if(var1 instanceof LambdaExp) {
            return (LambdaExp)var1;
         }
      }

      return null;
   }

   public ModuleExp currentModule() {
      for(ScopeExp var1 = this; var1 != null; var1 = var1.outer) {
         if(var1 instanceof ModuleExp) {
            return (ModuleExp)var1;
         }
      }

      return null;
   }

   public Declaration firstDecl() {
      return this.decls;
   }

   public Declaration getDefine(Object var1, char var2, Compilation var3) {
      Declaration var4 = this.lookup(var1);
      if(var4 == null) {
         return this.addDeclaration((Object)var1);
      } else if((var4.flags & 66048L) != 0L) {
         var4.flags &= -66049L;
         return var4;
      } else {
         Declaration var5 = this.addDeclaration((Object)var1);
         duplicateDeclarationError(var4, var5, var3);
         return var5;
      }
   }

   public Declaration getNoDefine(Object var1) {
      Declaration var3 = this.lookup(var1);
      Declaration var2 = var3;
      if(var3 == null) {
         var2 = this.addDeclaration((Object)var1);
         var2.flags |= 66048L;
      }

      return var2;
   }

   public Scope getVarScope() {
      Scope var2 = this.scope;
      Scope var1 = var2;
      if(var2 == null) {
         var1 = new Scope();
         this.scope = var1;
      }

      return var1;
   }

   public Declaration lookup(Object var1) {
      if(var1 != null) {
         for(Declaration var2 = this.firstDecl(); var2 != null; var2 = var2.nextDecl()) {
            if(var1.equals(var2.symbol)) {
               return var2;
            }
         }
      }

      return null;
   }

   public Declaration lookup(Object var1, Language var2, int var3) {
      for(Declaration var4 = this.firstDecl(); var4 != null; var4 = var4.nextDecl()) {
         if(var1.equals(var4.symbol) && var2.hasNamespace(var4, var3)) {
            return var4;
         }
      }

      return null;
   }

   public boolean nestedIn(ScopeExp var1) {
      for(ScopeExp var2 = this; var2 != null; var2 = var2.outer) {
         if(var2 == var1) {
            return true;
         }
      }

      return false;
   }

   public void popScope(CodeAttr var1) {
      for(Declaration var2 = this.firstDecl(); var2 != null; var2 = var2.nextDecl()) {
         var2.var = null;
      }

      var1.popScope();
      this.scope = null;
   }

   public void remove(Declaration var1) {
      Declaration var3 = null;

      for(Declaration var2 = this.firstDecl(); var2 != null; var2 = var2.nextDecl()) {
         if(var2 == var1) {
            this.remove(var3, var1);
            break;
         }

         var3 = var2;
      }

   }

   public void remove(Declaration var1, Declaration var2) {
      if(var1 == null) {
         this.decls = var2.next;
      } else {
         var1.next = var2.next;
      }

      if(this.last == var2) {
         this.last = var1;
      }

   }

   public void replaceFollowing(Declaration var1, Declaration var2) {
      if(var1 == null) {
         var1 = this.decls;
         this.decls = var2;
      } else {
         Declaration var3 = var1.next;
         var1.next = var2;
         var1 = var3;
      }

      var2.next = var1.next;
      if(this.last == var1) {
         this.last = var2;
      }

      var1.next = null;
      var2.context = this;
   }

   protected void setIndexes() {
      Declaration var1 = this.firstDecl();

      int var2;
      for(var2 = 0; var1 != null; ++var2) {
         var1.evalIndex = var2;
         var1 = var1.nextDecl();
      }

      this.frameSize = var2;
   }

   public String toString() {
      return this.getClass().getName() + "#" + this.id;
   }

   public ScopeExp topLevel() {
      ScopeExp var1 = this;

      while(true) {
         ScopeExp var2 = var1.outer;
         if(var2 == null || var2 instanceof ModuleExp) {
            return var1;
         }

         var1 = var2;
      }
   }

   protected Object visit(ExpVisitor var1, Object var2) {
      return var1.visitScopeExp(this, var2);
   }
}
