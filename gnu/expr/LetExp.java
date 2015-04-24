package gnu.expr;

import gnu.bytecode.CodeAttr;
import gnu.bytecode.PrimType;
import gnu.bytecode.Type;
import gnu.expr.BindingInitializer;
import gnu.expr.CheckedTarget;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.ExpVisitor;
import gnu.expr.Expression;
import gnu.expr.QuoteExp;
import gnu.expr.ScopeExp;
import gnu.expr.Target;
import gnu.mapping.CallContext;
import gnu.mapping.OutPort;

public class LetExp extends ScopeExp {

   public Expression body;
   public Expression[] inits;


   public LetExp(Expression[] var1) {
      this.inits = var1;
   }

   public void apply(CallContext param1) throws Throwable {
      // $FF: Couldn't be decompiled
   }

   public void compile(Compilation var1, Target var2) {
      CodeAttr var8 = var1.getCode();
      Declaration var4 = this.firstDecl();

      for(int var10 = 0; var10 < this.inits.length; var4 = var4.nextDecl()) {
         Expression var7 = this.inits[var10];
         boolean var11 = var4.needsInit();
         if(var11 && var4.isSimple()) {
            var4.allocateVariable(var8);
         }

         Object var3;
         Target var5;
         if(var11 && (!var4.isIndirectBinding() || var7 != QuoteExp.undefined_exp)) {
            Type var9 = var4.getType();
            Target var6 = CheckedTarget.getInstance((Declaration)var4);
            var3 = var7;
            var5 = var6;
            if(var7 == QuoteExp.undefined_exp) {
               if(var9 instanceof PrimType) {
                  var3 = new QuoteExp(new Byte((byte)0));
                  var5 = var6;
               } else {
                  var3 = var7;
                  var5 = var6;
                  if(var9 != null) {
                     var3 = var7;
                     var5 = var6;
                     if(var9 != Type.pointer_type) {
                        var3 = QuoteExp.nullExp;
                        var5 = var6;
                     }
                  }
               }
            }
         } else {
            var5 = Target.Ignore;
            var3 = var7;
         }

         ((Expression)var3).compileWithPosition(var1, var5);
         ++var10;
      }

      var8.enterScope(this.getVarScope());
      this.store_rest(var1, 0, this.firstDecl());
      this.body.compileWithPosition(var1, var2);
      this.popScope(var8);
   }

   protected Object evalVariable(int var1, CallContext var2) throws Throwable {
      return this.inits[var1].eval((CallContext)var2);
   }

   public Expression getBody() {
      return this.body;
   }

   public final Type getType() {
      return this.body.getType();
   }

   protected boolean mustCompile() {
      return false;
   }

   public void print(OutPort var1) {
      this.print(var1, "(Let", ")");
   }

   public void print(OutPort var1, String var2, String var3) {
      var1.startLogicalBlock(var2 + "#" + this.id, var3, 2);
      var1.writeSpaceFill();
      this.printLineColumn(var1);
      var1.startLogicalBlock("(", false, ")");
      Declaration var6 = this.firstDecl();

      int var5;
      for(int var4 = 0; var6 != null; var4 = var5) {
         if(var4 > 0) {
            var1.writeSpaceFill();
         }

         var1.startLogicalBlock("(", false, ")");
         var6.printInfo((OutPort)var1);
         var5 = var4;
         if(this.inits != null) {
            var1.writeSpaceFill();
            var1.print('=');
            var1.writeSpaceFill();
            if(var4 >= this.inits.length) {
               var1.print((String)"<missing init>");
            } else if(this.inits[var4] == null) {
               var1.print((String)"<null>");
            } else {
               this.inits[var4].print((OutPort)var1);
            }

            var5 = var4 + 1;
         }

         var1.endLogicalBlock(")");
         var6 = var6.nextDecl();
      }

      var1.endLogicalBlock(")");
      var1.writeSpaceLinear();
      if(this.body == null) {
         var1.print((String)"<null body>");
      } else {
         this.body.print((OutPort)var1);
      }

      var1.endLogicalBlock(var3);
   }

   public void setBody(Expression var1) {
      this.body = var1;
   }

   void store_rest(Compilation var1, int var2, Declaration var3) {
      if(var3 != null) {
         this.store_rest(var1, var2 + 1, var3.nextDecl());
         if(var3.needsInit()) {
            if(var3.isIndirectBinding()) {
               CodeAttr var4 = var1.getCode();
               if(this.inits[var2] == QuoteExp.undefined_exp) {
                  Object var5 = var3.getSymbol();
                  var1.compileConstant(var5, Target.pushObject);
                  var4.emitInvokeStatic(BindingInitializer.makeLocationMethod(var5));
               } else {
                  var3.pushIndirectBinding(var1);
               }
            }

            var3.compileStore(var1);
         }
      }

   }

   protected Object visit(ExpVisitor var1, Object var2) {
      return var1.visitLetExp(this, var2);
   }

   protected void visitChildren(ExpVisitor var1, Object var2) {
      this.visitInitializers(var1, var2);
      if(var1.exitValue == null) {
         this.body = var1.visitAndUpdate(this.body, var2);
      }

   }

   public void visitInitializers(ExpVisitor var1, Object var2) {
      Declaration var3 = this.firstDecl();

      for(int var6 = 0; var6 < this.inits.length; var3 = var3.nextDecl()) {
         Expression var4 = this.inits[var6];
         if(var4 == null) {
            throw new Error("null1 init for " + this + " i:" + var6 + " d:" + var3);
         }

         Expression var5 = var1.visitAndUpdate(var4, var2);
         if(var5 == null) {
            throw new Error("null2 init for " + this + " was:" + var4);
         }

         this.inits[var6] = var5;
         if(var3.value == var4) {
            var3.value = var5;
         }

         ++var6;
      }

   }
}
