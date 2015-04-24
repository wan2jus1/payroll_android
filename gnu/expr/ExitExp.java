package gnu.expr;

import gnu.bytecode.Type;
import gnu.expr.BlockExitException;
import gnu.expr.BlockExp;
import gnu.expr.Compilation;
import gnu.expr.ExpVisitor;
import gnu.expr.Expression;
import gnu.expr.QuoteExp;
import gnu.expr.Target;
import gnu.kawa.util.IdentityHashTable;
import gnu.mapping.CallContext;
import gnu.mapping.OutPort;

public class ExitExp extends Expression {

   BlockExp block;
   Expression result;


   public ExitExp(BlockExp var1) {
      this.result = QuoteExp.voidExp;
      this.block = var1;
   }

   public ExitExp(Expression var1, BlockExp var2) {
      this.result = var1;
      this.block = var2;
   }

   public void apply(CallContext var1) throws Throwable {
      throw new BlockExitException(this, this.result.eval((CallContext)var1));
   }

   public void compile(Compilation var1, Target var2) {
      var1.getCode();
      Object var3;
      if(this.result == null) {
         var3 = QuoteExp.voidExp;
      } else {
         var3 = this.result;
      }

      ((Expression)var3).compileWithPosition(var1, this.block.exitTarget);
      this.block.exitableBlock.exit();
   }

   protected Expression deepCopy(IdentityHashTable var1) {
      Expression var2 = deepCopy(this.result, var1);
      if(var2 == null && this.result != null) {
         return null;
      } else {
         Object var3 = var1.get(this.block);
         BlockExp var4;
         if(var3 == null) {
            var4 = this.block;
         } else {
            var4 = (BlockExp)var3;
         }

         ExitExp var5 = new ExitExp(var2, var4);
         var5.flags = this.getFlags();
         return var5;
      }
   }

   public Type getType() {
      return Type.neverReturnsType;
   }

   protected boolean mustCompile() {
      return false;
   }

   public void print(OutPort var1) {
      var1.startLogicalBlock("(Exit", false, ")");
      var1.writeSpaceFill();
      if(this.block != null && this.block.label != null) {
         var1.print((String)this.block.label.getName());
      } else {
         var1.print((String)"<unknown>");
      }

      if(this.result != null) {
         var1.writeSpaceLinear();
         this.result.print((OutPort)var1);
      }

      var1.endLogicalBlock(")");
   }

   protected Object visit(ExpVisitor var1, Object var2) {
      return var1.visitExitExp(this, var2);
   }

   protected void visitChildren(ExpVisitor var1, Object var2) {
      this.result = var1.visitAndUpdate(this.result, var2);
   }
}
