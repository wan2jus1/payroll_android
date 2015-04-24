package gnu.expr;

import gnu.bytecode.Type;
import gnu.expr.Compilation;
import gnu.expr.ExpVisitor;
import gnu.expr.Expression;
import gnu.expr.QuoteExp;
import gnu.expr.Target;
import gnu.lists.Consumer;
import gnu.lists.VoidConsumer;
import gnu.mapping.CallContext;
import gnu.mapping.OutPort;
import java.util.Vector;

public class BeginExp extends Expression {

   Vector compileOptions;
   Expression[] exps;
   int length;


   public BeginExp() {
   }

   public BeginExp(Expression var1, Expression var2) {
      this.exps = new Expression[2];
      this.exps[0] = var1;
      this.exps[1] = var2;
      this.length = 2;
   }

   public BeginExp(Expression[] var1) {
      this.exps = var1;
      this.length = var1.length;
   }

   public static final Expression canonicalize(Expression var0) {
      if(var0 instanceof BeginExp) {
         BeginExp var1 = (BeginExp)var0;
         if(var1.compileOptions == null) {
            int var2 = var1.length;
            if(var2 == 0) {
               return QuoteExp.voidExp;
            }

            if(var2 == 1) {
               return canonicalize((Expression)var1.exps[0]);
            }
         }
      }

      return var0;
   }

   public static final Expression canonicalize(Expression[] var0) {
      int var1 = var0.length;
      return (Expression)(var1 == 0?QuoteExp.voidExp:(var1 == 1?canonicalize((Expression)var0[0]):new BeginExp(var0)));
   }

   public final void add(Expression var1) {
      if(this.exps == null) {
         this.exps = new Expression[8];
      }

      Expression[] var2;
      if(this.length == this.exps.length) {
         var2 = new Expression[this.length * 2];
         System.arraycopy(this.exps, 0, var2, 0, this.length);
         this.exps = var2;
      }

      var2 = this.exps;
      int var3 = this.length;
      this.length = var3 + 1;
      var2[var3] = var1;
   }

   public void apply(CallContext var1) throws Throwable {
      int var5 = this.length;
      Consumer var2 = var1.consumer;
      var1.consumer = VoidConsumer.instance;

      int var4;
      for(var4 = 0; var4 < var5 - 1; ++var4) {
         boolean var7 = false;

         try {
            var7 = true;
            this.exps[var4].eval((CallContext)var1);
            var7 = false;
         } finally {
            if(var7) {
               var1.consumer = var2;
            }
         }
      }

      var1.consumer = var2;
      this.exps[var4].apply(var1);
   }

   public void compile(Compilation param1, Target param2) {
      // $FF: Couldn't be decompiled
   }

   public final int getExpressionCount() {
      return this.length;
   }

   public final Expression[] getExpressions() {
      return this.exps;
   }

   public Type getType() {
      return this.exps[this.length - 1].getType();
   }

   protected boolean mustCompile() {
      return false;
   }

   public void popOptions(Compilation var1) {
      if(this.compileOptions != null && var1 != null) {
         var1.currentOptions.popOptionValues(this.compileOptions);
      }

   }

   public void print(OutPort var1) {
      var1.startLogicalBlock("(Begin", ")", 2);
      var1.writeSpaceFill();
      this.printLineColumn(var1);
      int var4;
      int var5;
      if(this.compileOptions != null) {
         var1.writeSpaceFill();
         var1.startLogicalBlock("(CompileOptions", ")", 2);
         var5 = this.compileOptions.size();

         for(var4 = 0; var4 < var5; var4 += 3) {
            Object var2 = this.compileOptions.elementAt(var4);
            Object var3 = this.compileOptions.elementAt(var4 + 2);
            var1.writeSpaceFill();
            var1.startLogicalBlock("", "", 2);
            var1.print((Object)var2);
            var1.print(':');
            var1.writeSpaceLinear();
            var1.print((Object)var3);
            var1.endLogicalBlock("");
         }

         var1.endLogicalBlock(")");
      }

      var5 = this.length;

      for(var4 = 0; var4 < var5; ++var4) {
         var1.writeSpaceLinear();
         this.exps[var4].print((OutPort)var1);
      }

      var1.endLogicalBlock(")");
   }

   public void pushOptions(Compilation var1) {
      if(this.compileOptions != null && var1 != null) {
         var1.currentOptions.pushOptionValues(this.compileOptions);
      }

   }

   public void setCompileOptions(Vector var1) {
      this.compileOptions = var1;
   }

   public final void setExpressions(Expression[] var1) {
      this.exps = var1;
      this.length = var1.length;
   }

   protected Object visit(ExpVisitor var1, Object var2) {
      this.pushOptions(var1.comp);

      try {
         var2 = var1.visitBeginExp(this, var2);
      } finally {
         this.popOptions(var1.comp);
      }

      return var2;
   }

   protected void visitChildren(ExpVisitor var1, Object var2) {
      this.exps = var1.visitExps(this.exps, this.length, var2);
   }
}
