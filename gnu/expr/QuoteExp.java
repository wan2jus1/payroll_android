package gnu.expr;

import gnu.bytecode.ObjectType;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.ExpVisitor;
import gnu.expr.Expression;
import gnu.expr.IgnoreTarget;
import gnu.expr.InlineCalls;
import gnu.expr.Language;
import gnu.expr.PrimProcedure;
import gnu.expr.ReferenceExp;
import gnu.expr.Special;
import gnu.expr.StackTarget;
import gnu.expr.Target;
import gnu.kawa.util.IdentityHashTable;
import gnu.lists.AbstractFormat;
import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;
import gnu.mapping.OutPort;
import gnu.mapping.Procedure;
import gnu.mapping.Values;
import gnu.mapping.WrongArguments;
import gnu.text.SourceLocator;

public class QuoteExp extends Expression {

   public static final int EXPLICITLY_TYPED = 2;
   public static final int SHARED_CONSTANT = 4;
   public static QuoteExp abstractExp = makeShared(Special.abstractSpecial);
   public static final QuoteExp classObjectExp = makeShared(Type.objectType);
   public static QuoteExp falseExp = makeShared(Boolean.FALSE);
   public static QuoteExp nullExp = makeShared((Object)null, Type.nullType);
   public static QuoteExp trueExp = makeShared(Boolean.TRUE);
   public static QuoteExp undefined_exp = makeShared(Special.undefined);
   public static QuoteExp voidExp = makeShared(Values.empty, Type.voidType);
   protected Type type;
   Object value;


   public QuoteExp(Object var1) {
      this.value = var1;
   }

   public QuoteExp(Object var1, Type var2) {
      this.value = var1;
      this.setType(var2);
   }

   public static QuoteExp getInstance(Object var0) {
      return getInstance(var0, (SourceLocator)null);
   }

   public static QuoteExp getInstance(Object var0, SourceLocator var1) {
      if(var0 == null) {
         return nullExp;
      } else if(var0 == Type.pointer_type) {
         return classObjectExp;
      } else if(var0 == Special.undefined) {
         return undefined_exp;
      } else if(var0 == Values.empty) {
         return voidExp;
      } else if(var0 instanceof Boolean) {
         return ((Boolean)var0).booleanValue()?trueExp:falseExp;
      } else {
         QuoteExp var2 = new QuoteExp(var0);
         if(var1 != null) {
            var2.setLocation(var1);
         }

         return var2;
      }
   }

   static QuoteExp makeShared(Object var0) {
      QuoteExp var1 = new QuoteExp(var0);
      var1.setFlag(4);
      return var1;
   }

   static QuoteExp makeShared(Object var0, Type var1) {
      QuoteExp var2 = new QuoteExp(var0, var1);
      var2.setFlag(4);
      return var2;
   }

   public void apply(CallContext var1) {
      var1.writeValue(this.value);
   }

   public void compile(Compilation var1, Target var2) {
      if(this.type != null && this.type != Type.pointer_type && !(var2 instanceof IgnoreTarget) && (!(this.type instanceof ObjectType) || !this.type.isInstance(this.value))) {
         var1.compileConstant(this.value, StackTarget.getInstance(this.type));
         var2.compileFromStack(var1, this.type);
      } else {
         var1.compileConstant(this.value, var2);
      }
   }

   public Expression deepCopy(IdentityHashTable var1) {
      return this;
   }

   public final Type getRawType() {
      return this.type;
   }

   public final Type getType() {
      return (Type)(this.type != null?this.type:(this.value == Values.empty?Type.voidType:(this.value == null?Type.nullType:(this == undefined_exp?Type.pointer_type:Type.make(this.value.getClass())))));
   }

   public final Object getValue() {
      return this.value;
   }

   public boolean isExplicitlyTyped() {
      return this.getFlag(2);
   }

   public boolean isSharedConstant() {
      return this.getFlag(4);
   }

   protected boolean mustCompile() {
      return false;
   }

   public void print(OutPort var1) {
      var1.startLogicalBlock("(Quote", ")", 2);
      var1.writeSpaceLinear();
      Object var3 = this.value;
      Object var2 = var3;
      if(var3 instanceof Expression) {
         var2 = var3.toString();
      }

      AbstractFormat var6 = var1.objectFormat;

      try {
         var1.objectFormat = Language.getDefaultLanguage().getFormat(true);
         var1.print((Object)var2);
         if(this.type != null) {
            var1.print((String)" ::");
            var1.print((String)this.type.getName());
         }
      } finally {
         var1.objectFormat = var6;
      }

      var1.endLogicalBlock(")");
   }

   public void setType(Type var1) {
      this.type = var1;
      this.setFlag(2);
   }

   public boolean side_effects() {
      return false;
   }

   public String toString() {
      return "QuoteExp[" + this.value + "]";
   }

   public Expression validateApply(ApplyExp var1, InlineCalls var2, Type var3, Declaration var4) {
      if(this != undefined_exp) {
         Object var5 = this.getValue();
         if(!(var5 instanceof Procedure)) {
            String var12;
            if(var4 != null && var5 != null) {
               var12 = "calling " + var4.getName() + " which is a " + var5.getClass().getName();
            } else {
               var12 = "called value is not a procedure";
            }

            return var2.noteError(var12);
         }

         Procedure var8 = (Procedure)var5;
         int var11 = var1.getArgCount();
         String var17 = WrongArguments.checkArgCount(var8, var11);
         if(var17 != null) {
            return var2.noteError(var17);
         }

         Expression var19 = var2.maybeInline(var1, var3, var8);
         if(var19 != null) {
            return var19;
         }

         Expression[] var9 = var1.args;
         MethodProc var6;
         if(var8 instanceof MethodProc) {
            var6 = (MethodProc)var8;
         } else {
            var6 = null;
         }

         for(int var10 = 0; var10 < var11; ++var10) {
            Type var18;
            if(var6 != null) {
               var18 = var6.getParameterType(var10);
            } else {
               var18 = null;
            }

            Type var7 = var18;
            if(var10 == var11 - 1) {
               var7 = var18;
               if(var18 != null) {
                  var7 = var18;
                  if(var6.maxArgs() < 0) {
                     var7 = var18;
                     if(var10 == var6.minArgs()) {
                        var7 = null;
                     }
                  }
               }
            }

            var9[var10] = var2.visit(var9[var10], (Type)var7);
         }

         if(var1.getFlag(4)) {
            var19 = var1.inlineIfConstant(var8, (InlineCalls)var2);
            if(var19 != var1) {
               return var2.visit(var19, (Type)var3);
            }
         }

         Compilation var13 = var2.getCompilation();
         if(var13.inlineOk((Procedure)var8)) {
            if(ApplyExp.asInlineable(var8) != null) {
               if(var1.getFunction() != this) {
                  return (new ApplyExp(this, var1.getArgs())).setLine(var1);
               }
            } else {
               PrimProcedure var14 = PrimProcedure.getMethodFor(var8, var4, (Expression[])var1.args, var13.getLanguage());
               if(var14 != null) {
                  ApplyExp var15;
                  if(!var14.getStaticFlag() && var4 != null) {
                     if(var4.base == null) {
                        return var1;
                     }

                     Expression[] var16 = new Expression[var11 + 1];
                     System.arraycopy(var1.getArgs(), 0, var16, 1, var11);
                     var16[0] = new ReferenceExp(var4.base);
                     var15 = new ApplyExp(var14, var16);
                  } else {
                     var15 = new ApplyExp(var14, var1.args);
                  }

                  return var15.setLine(var1);
               }
            }
         }
      }

      return var1;
   }

   public final Object valueIfConstant() {
      return this.value;
   }

   protected Object visit(ExpVisitor var1, Object var2) {
      return var1.visitQuoteExp(this, var2);
   }
}
