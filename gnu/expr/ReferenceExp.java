package gnu.expr;

import gnu.bytecode.Type;
import gnu.expr.AccessExp;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.ConsumerTarget;
import gnu.expr.Declaration;
import gnu.expr.ExpVisitor;
import gnu.expr.Expression;
import gnu.expr.InlineCalls;
import gnu.expr.QuoteExp;
import gnu.expr.Target;
import gnu.kawa.util.IdentityHashTable;
import gnu.mapping.CallContext;
import gnu.mapping.Environment;
import gnu.mapping.OutPort;
import gnu.mapping.Procedure;
import gnu.mapping.Symbol;

public class ReferenceExp extends AccessExp {

   public static final int DONT_DEREFERENCE = 2;
   public static final int PREFER_BINDING2 = 8;
   public static final int PROCEDURE_NAME = 4;
   public static final int TYPE_NAME = 16;
   static int counter;
   int id;


   public ReferenceExp(Declaration var1) {
      this(var1.getSymbol(), var1);
   }

   public ReferenceExp(Object var1) {
      int var2 = counter + 1;
      counter = var2;
      this.id = var2;
      this.symbol = var1;
   }

   public ReferenceExp(Object var1, Declaration var2) {
      int var3 = counter + 1;
      counter = var3;
      this.id = var3;
      this.symbol = var1;
      this.binding = var2;
   }

   public void apply(CallContext param1) throws Throwable {
      // $FF: Couldn't be decompiled
   }

   public void compile(Compilation var1, Target var2) {
      if(!(var2 instanceof ConsumerTarget) || !((ConsumerTarget)var2).compileWrite(this, var1)) {
         this.binding.load(this, this.flags, var1, var2);
      }

   }

   protected Expression deepCopy(IdentityHashTable var1) {
      Declaration var2 = (Declaration)var1.get(this.binding, this.binding);
      ReferenceExp var3 = new ReferenceExp(var1.get(this.symbol, this.symbol), var2);
      var3.flags = this.getFlags();
      return var3;
   }

   public final boolean getDontDereference() {
      return (this.flags & 2) != 0;
   }

   public Type getType() {
      Declaration var1 = this.binding;
      Object var2;
      if(var1 != null && !var1.isFluid()) {
         if(this.getDontDereference()) {
            return Compilation.typeLocation;
         }

         Type var5;
         label24: {
            Declaration var3 = Declaration.followAliases(var1);
            Type var6 = var3.getType();
            if(var6 != null) {
               var5 = var6;
               if(var6 != Type.pointer_type) {
                  break label24;
               }
            }

            Expression var4 = var3.getValue();
            var5 = var6;
            if(var4 != null) {
               var5 = var6;
               if(var4 != QuoteExp.undefined_exp) {
                  Expression var7 = var3.value;
                  var3.value = null;
                  var5 = var4.getType();
                  var3.value = var7;
               }
            }
         }

         var2 = var5;
         if(var5 == Type.toStringType) {
            return Type.javalangStringType;
         }
      } else {
         var2 = Type.pointer_type;
      }

      return (Type)var2;
   }

   public final boolean isProcedureName() {
      return (this.flags & 4) != 0;
   }

   public boolean isSingleValue() {
      return this.binding != null && this.binding.getFlag(262144L)?true:super.isSingleValue();
   }

   public final boolean isUnknown() {
      return Declaration.isUnknown(this.binding);
   }

   protected boolean mustCompile() {
      return false;
   }

   public void print(OutPort var1) {
      var1.print((String)"(Ref/");
      var1.print(this.id);
      if(this.symbol != null && (this.binding == null || this.symbol.toString() != this.binding.getName())) {
         var1.print('/');
         var1.print((Object)this.symbol);
      }

      if(this.binding != null) {
         var1.print('/');
         var1.print((Object)this.binding);
      }

      var1.print((String)")");
   }

   public final void setDontDereference(boolean var1) {
      this.setFlag(var1, 2);
   }

   public final void setProcedureName(boolean var1) {
      this.setFlag(var1, 4);
   }

   public boolean side_effects() {
      return this.binding == null || !this.binding.isLexical();
   }

   public String toString() {
      return "RefExp/" + this.symbol + '/' + this.id + '/';
   }

   public Expression validateApply(ApplyExp var1, InlineCalls var2, Type var3, Declaration var4) {
      var4 = this.binding;
      if(var4 != null && !var4.getFlag(65536L)) {
         var4 = Declaration.followAliases(var4);
         if(!var4.isIndirectBinding()) {
            Expression var5 = var4.getValue();
            if(var5 != null) {
               return var5.validateApply(var1, var2, var3, var4);
            }
         }
      } else if(this.getSymbol() instanceof Symbol) {
         Symbol var6 = (Symbol)this.getSymbol();
         Object var7 = Environment.getCurrent().getFunction(var6, (Object)null);
         if(var7 instanceof Procedure) {
            return (new QuoteExp(var7)).validateApply(var1, var2, var3, (Declaration)null);
         }
      }

      var1.visitArgs(var2);
      return var1;
   }

   public final Object valueIfConstant() {
      if(this.binding != null) {
         Expression var1 = this.binding.getValue();
         if(var1 != null) {
            return var1.valueIfConstant();
         }
      }

      return null;
   }

   protected Object visit(ExpVisitor var1, Object var2) {
      return var1.visitReferenceExp(this, var2);
   }
}
