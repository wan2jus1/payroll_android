package gnu.expr;

import gnu.expr.ApplyExp;
import gnu.expr.BeginExp;
import gnu.expr.BlockExp;
import gnu.expr.ClassExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.ErrorExp;
import gnu.expr.ExitExp;
import gnu.expr.Expression;
import gnu.expr.FluidLetExp;
import gnu.expr.IfExp;
import gnu.expr.LambdaExp;
import gnu.expr.LangExp;
import gnu.expr.LetExp;
import gnu.expr.ModuleExp;
import gnu.expr.ObjectExp;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.ScopeExp;
import gnu.expr.SetExp;
import gnu.expr.SynchronizedExp;
import gnu.expr.ThisExp;
import gnu.expr.TryExp;
import gnu.text.SourceLocator;
import gnu.text.SourceMessages;

public class ExpVisitor implements SourceLocator {

   Compilation comp;
   protected LambdaExp currentLambda = null;
   protected Object exitValue = null;
   protected SourceMessages messages;


   protected Object defaultValue(Expression var1, Object var2) {
      return null;
   }

   public void error(char var1, String var2) {
      char var3 = var1;
      if(var1 == 119) {
         var3 = var1;
         if(this.comp.warnAsError()) {
            var3 = 101;
         }
      }

      if(this.messages != null) {
         this.messages.error(var3, var2);
      } else {
         new Error("internal error: " + var2);
      }
   }

   public final int getColumnNumber() {
      return this.messages.getColumnNumber();
   }

   public Compilation getCompilation() {
      return this.comp;
   }

   public final LambdaExp getCurrentLambda() {
      return this.currentLambda;
   }

   public Object getExitValue() {
      return this.exitValue;
   }

   public final String getFileName() {
      return this.messages.getFileName();
   }

   public final int getLineNumber() {
      return this.messages.getLineNumber();
   }

   public SourceMessages getMessages() {
      return this.messages;
   }

   public String getPublicId() {
      return this.messages.getPublicId();
   }

   public String getSystemId() {
      return this.messages.getSystemId();
   }

   public boolean isStableSourceLocation() {
      return false;
   }

   public Expression noteError(String var1) {
      if(this.messages != null) {
         this.messages.error('e', var1);
      }

      return new ErrorExp(var1);
   }

   public void setColumn(int var1) {
      this.messages.setColumn(var1);
   }

   public void setContext(Compilation var1) {
      this.comp = var1;
      this.messages = var1.getMessages();
   }

   public void setFile(String var1) {
      this.messages.setFile(var1);
   }

   public void setLine(int var1) {
      this.messages.setLine(var1);
   }

   public void setLine(String var1, int var2, int var3) {
      this.messages.setLine(var1, var2, var3);
   }

   protected Expression update(Expression var1, Object var2) {
      return var1;
   }

   public Object visit(Expression var1, Object var2) {
      int var4 = var1.getLineNumber();
      if(this.messages != null && var4 > 0) {
         String var3 = this.messages.getFileName();
         int var5 = this.messages.getLineNumber();
         int var6 = this.messages.getColumnNumber();
         this.messages.setLine(var1.getFileName(), var4, var1.getColumnNumber());
         Object var7 = var1.visit(this, var2);
         this.messages.setLine(var3, var5, var6);
         return var7;
      } else {
         return var1.visit(this, var2);
      }
   }

   public Expression visitAndUpdate(Expression var1, Object var2) {
      return this.update(var1, this.visit(var1, var2));
   }

   protected Object visitApplyExp(ApplyExp var1, Object var2) {
      return this.visitExpression(var1, var2);
   }

   protected Object visitBeginExp(BeginExp var1, Object var2) {
      return this.visitExpression(var1, var2);
   }

   protected Object visitBlockExp(BlockExp var1, Object var2) {
      return this.visitExpression(var1, var2);
   }

   protected Object visitClassExp(ClassExp var1, Object var2) {
      return this.visitLambdaExp(var1, var2);
   }

   protected final void visitDeclarationType(Declaration var1) {
      Expression var2 = var1.typeExp;
      if(var2 != null) {
         Expression var3 = this.visitAndUpdate(var2, (Object)null);
         if(var3 != var2) {
            var1.setTypeExp(var3);
         }
      }

   }

   protected final void visitDeclarationTypes(ScopeExp var1) {
      for(Declaration var2 = var1.firstDecl(); var2 != null; var2 = var2.nextDecl()) {
         this.visitDeclarationType(var2);
      }

   }

   public void visitDefaultArgs(LambdaExp var1, Object var2) {
      var1.defaultArgs = this.visitExps(var1.defaultArgs, var2);
   }

   protected Object visitExitExp(ExitExp var1, Object var2) {
      return this.visitExpression(var1, var2);
   }

   protected Object visitExpression(Expression var1, Object var2) {
      var1.visitChildren(this, var2);
      return this.defaultValue(var1, var2);
   }

   public Expression[] visitExps(Expression[] var1, int var2, Object var3) {
      for(int var4 = 0; var4 < var2 && this.exitValue == null; ++var4) {
         var1[var4] = this.visitAndUpdate(var1[var4], var3);
      }

      return var1;
   }

   public Expression[] visitExps(Expression[] var1, Object var2) {
      return var1 == null?null:this.visitExps(var1, var1.length, var2);
   }

   protected Object visitFluidLetExp(FluidLetExp var1, Object var2) {
      return this.visitLetExp(var1, var2);
   }

   protected Object visitIfExp(IfExp var1, Object var2) {
      return this.visitExpression(var1, var2);
   }

   protected Object visitLambdaExp(LambdaExp var1, Object var2) {
      return this.visitScopeExp(var1, var2);
   }

   protected Object visitLangExp(LangExp var1, Object var2) {
      return this.visitExpression(var1, var2);
   }

   protected Object visitLetExp(LetExp var1, Object var2) {
      return this.visitScopeExp(var1, var2);
   }

   protected Object visitModuleExp(ModuleExp var1, Object var2) {
      return this.visitLambdaExp(var1, var2);
   }

   protected Object visitObjectExp(ObjectExp var1, Object var2) {
      return this.visitClassExp(var1, var2);
   }

   protected Object visitQuoteExp(QuoteExp var1, Object var2) {
      return this.visitExpression(var1, var2);
   }

   protected Object visitReferenceExp(ReferenceExp var1, Object var2) {
      return this.visitExpression(var1, var2);
   }

   protected Object visitScopeExp(ScopeExp var1, Object var2) {
      this.visitDeclarationTypes(var1);
      return this.visitExpression(var1, var2);
   }

   protected Object visitSetExp(SetExp var1, Object var2) {
      Declaration var3 = var1.binding;
      boolean var4;
      if(var3 != null && var3.value == var1.new_value) {
         var4 = true;
      } else {
         var4 = false;
      }

      var1.new_value = this.visitSetExpValue(var1.new_value, var2, var1.getBinding());
      if(var4 && var1.isDefining()) {
         var3.value = var1.new_value;
         if(var1.new_value instanceof LambdaExp) {
            ((LambdaExp)var1.new_value).nameDecl = var3;
         }
      }

      return this.defaultValue(var1, var2);
   }

   protected Expression visitSetExpValue(Expression var1, Object var2, Declaration var3) {
      return this.visitAndUpdate(var1, var2);
   }

   protected Object visitSynchronizedExp(SynchronizedExp var1, Object var2) {
      return this.visitExpression(var1, var2);
   }

   protected Object visitThisExp(ThisExp var1, Object var2) {
      return this.visitReferenceExp(var1, var2);
   }

   protected Object visitTryExp(TryExp var1, Object var2) {
      return this.visitExpression(var1, var2);
   }
}
