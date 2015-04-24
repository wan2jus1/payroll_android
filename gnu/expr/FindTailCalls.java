package gnu.expr;

import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.BeginExp;
import gnu.expr.BlockExp;
import gnu.expr.CatchClause;
import gnu.expr.ClassExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.ExpExpVisitor;
import gnu.expr.Expression;
import gnu.expr.FluidLetExp;
import gnu.expr.IfExp;
import gnu.expr.LambdaExp;
import gnu.expr.LetExp;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.ScopeExp;
import gnu.expr.SetExp;
import gnu.expr.SynchronizedExp;
import gnu.expr.TryExp;
import gnu.kawa.functions.AppendValues;
import java.util.HashSet;

public class FindTailCalls extends ExpExpVisitor {

   public static void findTailCalls(Expression var0, Compilation var1) {
      FindTailCalls var2 = new FindTailCalls();
      var2.setContext(var1);
      var2.visit(var0, var0);
   }

   public void postVisitDecls(ScopeExp var1) {
      for(Declaration var4 = var1.firstDecl(); var4 != null; var4 = var4.nextDecl()) {
         Expression var2 = var4.getValue();
         if(var2 instanceof LambdaExp) {
            LambdaExp var3 = (LambdaExp)var2;
            if(var4.getCanRead()) {
               var3.setCanRead(true);
            }

            if(var4.getCanCall()) {
               var3.setCanCall(true);
            }
         }

         if(var4.getFlag(1024L) && var2 instanceof ReferenceExp) {
            Declaration var5 = ((ReferenceExp)var2).contextDecl();
            if(var5 != null && var5.isPrivate()) {
               var5.setFlag(524288L);
            }
         }
      }

   }

   protected Expression visitApplyExp(ApplyExp var1, Expression var2) {
      boolean var6;
      if(var2 == this.currentLambda.body) {
         var6 = true;
      } else {
         var6 = false;
      }

      if(var6) {
         var1.setTailCall(true);
      }

      var1.context = this.currentLambda;
      Object var4 = null;
      LambdaExp var3;
      if(var1.func instanceof ReferenceExp) {
         Declaration var5 = Declaration.followAliases(((ReferenceExp)var1.func).binding);
         var3 = (LambdaExp)var4;
         if(var5 != null) {
            if(!var5.getFlag(2048L)) {
               var1.nextCall = var5.firstCall;
               var5.firstCall = var1;
            }

            Compilation var7 = this.getCompilation();
            var5.setCanCall();
            if(!var7.mustCompile) {
               var5.setCanRead();
            }

            Expression var8 = var5.getValue();
            var3 = (LambdaExp)var4;
            if(var8 instanceof LambdaExp) {
               var3 = (LambdaExp)var8;
            }
         }
      } else if(var1.func instanceof LambdaExp && !(var1.func instanceof ClassExp)) {
         var3 = (LambdaExp)var1.func;
         this.visitLambdaExp(var3, false);
         var3.setCanCall(true);
      } else if(var1.func instanceof QuoteExp && ((QuoteExp)var1.func).getValue() == AppendValues.appendValues) {
         var3 = (LambdaExp)var4;
      } else {
         var1.func = this.visitExpression(var1.func, (Expression)var1.func);
         var3 = (LambdaExp)var4;
      }

      if(var3 != null && var3.returnContinuation != var2 && (var3 != this.currentLambda || !var6)) {
         if(var6) {
            if(var3.tailCallers == null) {
               var3.tailCallers = new HashSet();
            }

            var3.tailCallers.add(this.currentLambda);
         } else if(var3.returnContinuation == null) {
            var3.returnContinuation = var2;
            var3.inlineHome = this.currentLambda;
         } else {
            var3.returnContinuation = LambdaExp.unknownContinuation;
            var3.inlineHome = null;
         }
      }

      var1.args = this.visitExps(var1.args);
      return var1;
   }

   protected Expression visitBeginExp(BeginExp var1, Expression var2) {
      int var7 = var1.length - 1;

      for(int var6 = 0; var6 <= var7; ++var6) {
         Expression[] var4 = var1.exps;
         Expression var5 = var1.exps[var6];
         Expression var3;
         if(var6 == var7) {
            var3 = var2;
         } else {
            var3 = var1.exps[var6];
         }

         var4[var6] = (Expression)var5.visit(this, var3);
      }

      return var1;
   }

   protected Expression visitBlockExp(BlockExp var1, Expression var2) {
      var1.body = (Expression)var1.body.visit(this, var2);
      if(var1.exitBody != null) {
         var1.exitBody = (Expression)var1.exitBody.visit(this, var1.exitBody);
      }

      return var1;
   }

   protected Expression visitClassExp(ClassExp param1, Expression param2) {
      // $FF: Couldn't be decompiled
   }

   protected Expression visitExpression(Expression var1, Expression var2) {
      return (Expression)super.visitExpression(var1, var1);
   }

   public Expression[] visitExps(Expression[] var1) {
      int var4 = var1.length;

      for(int var3 = 0; var3 < var4; ++var3) {
         Expression var2 = var1[var3];
         var1[var3] = (Expression)this.visit(var2, var2);
      }

      return var1;
   }

   protected Expression visitFluidLetExp(FluidLetExp var1, Expression var2) {
      for(Declaration var3 = var1.firstDecl(); var3 != null; var3 = var3.nextDecl()) {
         var3.setCanRead(true);
         if(var3.base != null) {
            var3.base.setCanRead(true);
         }
      }

      this.visitLetDecls(var1);
      var1.body = (Expression)var1.body.visit(this, var1.body);
      this.postVisitDecls(var1);
      return var1;
   }

   protected Expression visitIfExp(IfExp var1, Expression var2) {
      var1.test = (Expression)var1.test.visit(this, var1.test);
      var1.then_clause = (Expression)var1.then_clause.visit(this, var2);
      Expression var3 = var1.else_clause;
      if(var3 != null) {
         var1.else_clause = (Expression)var3.visit(this, var2);
      }

      return var1;
   }

   protected Expression visitLambdaExp(LambdaExp var1, Expression var2) {
      this.visitLambdaExp(var1, true);
      return var1;
   }

   final void visitLambdaExp(LambdaExp param1, boolean param2) {
      // $FF: Couldn't be decompiled
   }

   void visitLetDecls(LetExp var1) {
      Declaration var2 = var1.firstDecl();
      int var7 = var1.inits.length;

      for(int var6 = 0; var6 < var7; var2 = var2.nextDecl()) {
         Expression var4 = this.visitSetExp((Declaration)var2, (Expression)var1.inits[var6]);
         Expression var3 = var4;
         if(var4 == QuoteExp.undefined_exp) {
            label19: {
               Expression var5 = var2.getValue();
               if(!(var5 instanceof LambdaExp)) {
                  var3 = var4;
                  if(var5 == var4) {
                     break label19;
                  }

                  var3 = var4;
                  if(!(var5 instanceof QuoteExp)) {
                     break label19;
                  }
               }

               var3 = var5;
            }
         }

         var1.inits[var6] = var3;
         ++var6;
      }

   }

   protected Expression visitLetExp(LetExp var1, Expression var2) {
      this.visitLetDecls(var1);
      var1.body = (Expression)var1.body.visit(this, var2);
      this.postVisitDecls(var1);
      return var1;
   }

   protected Expression visitReferenceExp(ReferenceExp var1, Expression var2) {
      Declaration var4 = Declaration.followAliases(var1.binding);
      Object var5;
      if(var4 != null) {
         Type var3 = var4.type;
         if(var3 != null && var3.isVoid()) {
            var5 = QuoteExp.voidExp;
            return (Expression)var5;
         }

         var4.setCanRead(true);
      }

      Declaration var6 = var1.contextDecl();
      var5 = var1;
      if(var6 != null) {
         var6.setCanRead(true);
         return var1;
      } else {
         return (Expression)var5;
      }
   }

   final Expression visitSetExp(Declaration var1, Expression var2) {
      if(var1 != null && var1.getValue() == var2 && var2 instanceof LambdaExp && !(var2 instanceof ClassExp) && !var1.isPublic()) {
         LambdaExp var3 = (LambdaExp)var2;
         this.visitLambdaExp(var3, false);
         return var3;
      } else {
         return (Expression)var2.visit(this, var2);
      }
   }

   protected Expression visitSetExp(SetExp var1, Expression var2) {
      Declaration var3 = var1.binding;
      Declaration var4 = var3;
      if(var3 != null) {
         var4 = var3;
         if(var3.isAlias()) {
            if(var1.isDefining()) {
               var1.new_value = (Expression)var1.new_value.visit(this, var1.new_value);
               return var1;
            }

            var4 = Declaration.followAliases(var3);
         }
      }

      var3 = var1.contextDecl();
      if(var3 != null) {
         var3.setCanRead(true);
      }

      Expression var5 = this.visitSetExp((Declaration)var4, (Expression)var1.new_value);
      if(var4 != null && var4.context instanceof LetExp && var5 == var4.getValue() && (var5 instanceof LambdaExp || var5 instanceof QuoteExp)) {
         return QuoteExp.voidExp;
      } else {
         var1.new_value = var5;
         return var1;
      }
   }

   protected Expression visitSynchronizedExp(SynchronizedExp var1, Expression var2) {
      var1.object = (Expression)var1.object.visit(this, var1.object);
      var1.body = (Expression)var1.body.visit(this, var1.body);
      return var1;
   }

   protected Expression visitTryExp(TryExp var1, Expression var2) {
      Expression var3;
      if(var1.finally_clause == null) {
         var3 = var2;
      } else {
         var3 = var1.try_clause;
      }

      var1.try_clause = (Expression)var1.try_clause.visit(this, var3);

      for(CatchClause var5 = var1.catch_clauses; this.exitValue == null && var5 != null; var5 = var5.getNext()) {
         Expression var4;
         if(var1.finally_clause == null) {
            var4 = var2;
         } else {
            var4 = var5.body;
         }

         var5.body = (Expression)var5.body.visit(this, var4);
      }

      var2 = var1.finally_clause;
      if(var2 != null) {
         var1.finally_clause = (Expression)var2.visit(this, var2);
      }

      return var1;
   }
}
