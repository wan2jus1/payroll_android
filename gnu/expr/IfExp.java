package gnu.expr;

import gnu.bytecode.CodeAttr;
import gnu.bytecode.Label;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.expr.BlockExp;
import gnu.expr.Compilation;
import gnu.expr.ConditionalTarget;
import gnu.expr.ExitExp;
import gnu.expr.ExpVisitor;
import gnu.expr.Expression;
import gnu.expr.IgnoreTarget;
import gnu.expr.Language;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.Target;
import gnu.mapping.CallContext;
import gnu.mapping.OutPort;
import gnu.mapping.Values;

public class IfExp extends Expression {

   Expression else_clause;
   Expression test;
   Expression then_clause;


   public IfExp(Expression var1, Expression var2, Expression var3) {
      this.test = var1;
      this.then_clause = var2;
      this.else_clause = var3;
   }

   public static void compile(Expression var0, Expression var1, Expression var2, Compilation var3, Target var4) {
      Language var8 = var3.getLanguage();
      CodeAttr var7 = var3.getCode();
      Label var6 = null;
      Label var5;
      boolean var10;
      if(var4 instanceof ConditionalTarget && var2 instanceof QuoteExp) {
         var10 = true;
         if(var8.isTrue(((QuoteExp)var2).getValue())) {
            var5 = ((ConditionalTarget)var4).ifTrue;
         } else {
            var5 = ((ConditionalTarget)var4).ifFalse;
         }
      } else {
         label63: {
            var5 = var6;
            if(var2 instanceof ExitExp) {
               var5 = var6;
               if(((ExitExp)var2).result instanceof QuoteExp) {
                  BlockExp var9 = ((ExitExp)var2).block;
                  var5 = var6;
                  if(var9.exitTarget instanceof IgnoreTarget) {
                     var6 = var9.exitableBlock.exitIsGoto();
                     var5 = var6;
                     if(var6 != null) {
                        var10 = true;
                        var5 = var6;
                        break label63;
                     }
                  }
               }
            }

            var10 = false;
         }
      }

      var6 = var5;
      if(var5 == null) {
         var6 = new Label(var7);
      }

      boolean var11;
      if(var0 == var1 && var4 instanceof ConditionalTarget && var1 instanceof ReferenceExp) {
         var11 = true;
         var5 = ((ConditionalTarget)var4).ifTrue;
      } else {
         var11 = false;
         var5 = new Label(var7);
      }

      ConditionalTarget var13 = new ConditionalTarget(var5, var6, var8);
      if(var11) {
         var13.trueBranchComesFirst = false;
      }

      var0.compile(var3, (Target)var13);
      var7.emitIfThen();
      Variable var12;
      if(!var11) {
         var5.define(var7);
         var12 = var3.callContextVar;
         var1.compileWithPosition(var3, var4);
         var3.callContextVar = var12;
      }

      if(!var10) {
         var7.emitElse();
         var6.define(var7);
         var12 = var3.callContextVar;
         if(var2 == null) {
            var3.compileConstant(Values.empty, var4);
         } else {
            var2.compileWithPosition(var3, var4);
         }

         var3.callContextVar = var12;
      } else {
         var7.setUnreachable();
      }

      var7.emitFi();
   }

   public void apply(CallContext var1) throws Throwable {
      if(this.getLanguage().isTrue(this.test.eval((CallContext)var1))) {
         this.then_clause.apply(var1);
      } else if(this.else_clause != null) {
         this.else_clause.apply(var1);
         return;
      }

   }

   public void compile(Compilation var1, Target var2) {
      Expression var4 = this.test;
      Expression var5 = this.then_clause;
      Object var3;
      if(this.else_clause == null) {
         var3 = QuoteExp.voidExp;
      } else {
         var3 = this.else_clause;
      }

      compile(var4, var5, (Expression)var3, var1, var2);
   }

   public Expression getElseClause() {
      return this.else_clause;
   }

   protected final Language getLanguage() {
      return Language.getDefaultLanguage();
   }

   public Expression getTest() {
      return this.test;
   }

   public Expression getThenClause() {
      return this.then_clause;
   }

   public Type getType() {
      Type var2 = this.then_clause.getType();
      Object var1;
      if(this.else_clause == null) {
         var1 = Type.voidType;
      } else {
         var1 = this.else_clause.getType();
      }

      return Language.unionType(var2, (Type)var1);
   }

   protected boolean mustCompile() {
      return false;
   }

   public void print(OutPort var1) {
      var1.startLogicalBlock("(If ", false, ")");
      var1.setIndentation(-2, false);
      this.test.print((OutPort)var1);
      var1.writeSpaceLinear();
      this.then_clause.print((OutPort)var1);
      if(this.else_clause != null) {
         var1.writeSpaceLinear();
         this.else_clause.print((OutPort)var1);
      }

      var1.endLogicalBlock(")");
   }

   Expression select(boolean var1) {
      return (Expression)(var1?this.then_clause:(this.else_clause == null?QuoteExp.voidExp:this.else_clause));
   }

   protected Object visit(ExpVisitor var1, Object var2) {
      return var1.visitIfExp(this, var2);
   }

   protected void visitChildren(ExpVisitor var1, Object var2) {
      this.test = var1.visitAndUpdate(this.test, var2);
      if(var1.exitValue == null) {
         this.then_clause = var1.visitAndUpdate(this.then_clause, var2);
      }

      if(var1.exitValue == null && this.else_clause != null) {
         this.else_clause = var1.visitAndUpdate(this.else_clause, var2);
      }

   }
}
