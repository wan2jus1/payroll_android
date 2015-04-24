package gnu.expr;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Field;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.expr.AccessExp;
import gnu.expr.ApplyExp;
import gnu.expr.BindingInitializer;
import gnu.expr.ClassExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.ExpVisitor;
import gnu.expr.Expression;
import gnu.expr.IgnoreTarget;
import gnu.expr.LambdaExp;
import gnu.expr.Language;
import gnu.expr.ModuleExp;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.ScopeExp;
import gnu.expr.Target;
import gnu.kawa.functions.AddOp;
import gnu.mapping.CallContext;
import gnu.mapping.Environment;
import gnu.mapping.EnvironmentKey;
import gnu.mapping.Location;
import gnu.mapping.OutPort;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.math.IntNum;

public class SetExp extends AccessExp {

   public static final int BAD_SHORT = 65536;
   public static final int DEFINING_FLAG = 2;
   public static final int GLOBAL_FLAG = 4;
   public static final int HAS_VALUE = 64;
   public static final int PREFER_BINDING2 = 8;
   public static final int PROCEDURE = 16;
   public static final int SET_IF_UNBOUND = 32;
   Expression new_value;


   public SetExp(Declaration var1, Expression var2) {
      this.binding = var1;
      this.symbol = var1.getSymbol();
      this.new_value = var2;
   }

   public SetExp(Object var1, Expression var2) {
      this.symbol = var1;
      this.new_value = var2;
   }

   public static int canUseInc(Expression var0, Declaration var1) {
      Variable var2 = var1.getVariable();
      if(var1.isSimple() && var2.getType().getImplementationType().promote() == Type.intType && var0 instanceof ApplyExp) {
         ApplyExp var8 = (ApplyExp)var0;
         if(var8.getArgCount() == 2) {
            Object var10 = var8.getFunction().valueIfConstant();
            byte var5;
            if(var10 == AddOp.$Pl) {
               var5 = 1;
            } else {
               if(var10 != AddOp.$Mn) {
                  return 65536;
               }

               var5 = -1;
            }

            Expression var12 = var8.getArg(0);
            Expression var3 = var8.getArg(1);
            Expression var4 = var12;
            var0 = var3;
            if(var12 instanceof QuoteExp) {
               var4 = var12;
               var0 = var3;
               if(var5 > 0) {
                  var0 = var12;
                  var4 = var3;
               }
            }

            if(var4 instanceof ReferenceExp) {
               ReferenceExp var13 = (ReferenceExp)var4;
               if(var13.getBinding() == var1 && !var13.getDontDereference()) {
                  Object var9 = var0.valueIfConstant();
                  int var6;
                  int var7;
                  if(var9 instanceof Integer) {
                     var7 = ((Integer)var9).intValue();
                     var6 = var7;
                     if(var5 < 0) {
                        var6 = -var7;
                     }

                     if((short)var6 == var6) {
                        return var6;
                     }
                  } else if(var9 instanceof IntNum) {
                     IntNum var11 = (IntNum)var9;
                     var6 = 32767;
                     var7 = -32767;
                     if(var5 > 0) {
                        --var7;
                     } else {
                        var6 = 32767 + 1;
                     }

                     if(IntNum.compare(var11, (long)var7) >= 0 && IntNum.compare(var11, (long)var6) <= 0) {
                        return var5 * var11.intValue();
                     }
                  }
               }
            }
         }
      }

      return 65536;
   }

   public static SetExp makeDefinition(Declaration var0, Expression var1) {
      SetExp var2 = new SetExp(var0, var1);
      var2.setDefining(true);
      return var2;
   }

   public static SetExp makeDefinition(Object var0, Expression var1) {
      SetExp var2 = new SetExp(var0, var1);
      var2.setDefining(true);
      return var2;
   }

   public void apply(CallContext var1) throws Throwable {
      Environment var5 = Environment.getCurrent();
      Symbol var2;
      if(this.symbol instanceof Symbol) {
         var2 = (Symbol)this.symbol;
      } else {
         var2 = var5.getSymbol(this.symbol.toString());
      }

      Object var4 = null;
      Language var6 = Language.getDefaultLanguage();
      Object var3 = var4;
      if(this.isFuncDef()) {
         var3 = var4;
         if(var6.hasSeparateFunctionNamespace()) {
            var3 = EnvironmentKey.FUNCTION;
         }
      }

      if(this.isSetIfUnbound()) {
         Location var7 = var5.getLocation(var2, var3);
         if(!var7.isBound()) {
            var7.set(this.new_value.eval((Environment)var5));
         }

         if(this.getHasValue()) {
            var1.writeValue(var7);
         }
      } else {
         var4 = this.new_value.eval((Environment)var5);
         if(this.binding != null && !(this.binding.context instanceof ModuleExp)) {
            Object[] var8 = var1.evalFrames[ScopeExp.nesting(this.binding.context)];
            if(this.binding.isIndirectBinding()) {
               if(this.isDefining()) {
                  var8[this.binding.evalIndex] = Location.make((Symbol)var2);
               }

               ((Location)var8[this.binding.evalIndex]).set(this.new_value);
            } else {
               var8[this.binding.evalIndex] = var4;
            }
         } else if(this.isDefining()) {
            var5.define(var2, var3, var4);
         } else {
            var5.put(var2, var3, var4);
         }

         if(this.getHasValue()) {
            var1.writeValue(var4);
            return;
         }
      }

   }

   public void compile(Compilation var1, Target var2) {
      if(!(this.new_value instanceof LambdaExp) || !(var2 instanceof IgnoreTarget) || !((LambdaExp)this.new_value).getInlineOnly()) {
         CodeAttr var11 = var1.getCode();
         boolean var14;
         if(this.getHasValue() && !(var2 instanceof IgnoreTarget)) {
            var14 = true;
         } else {
            var14 = false;
         }

         boolean var13 = false;
         boolean var16 = false;
         boolean var15 = false;
         boolean var17 = false;
         boolean var18 = false;
         boolean var19 = false;
         boolean var12 = false;
         Declaration var4 = this.binding;
         Expression var3 = var4.getValue();
         if(var3 instanceof LambdaExp && var4.context instanceof ModuleExp && !var4.ignorable() && ((LambdaExp)var3).getName() != null && var3 == this.new_value) {
            ((LambdaExp)this.new_value).compileSetField(var1);
         } else if((var4.shouldEarlyInit() || var4.isAlias()) && var4.context instanceof ModuleExp && this.isDefining() && !var4.ignorable()) {
            if(var4.shouldEarlyInit()) {
               BindingInitializer.create(var4, this.new_value, var1);
            }

            if(var14) {
               var4.load(this, 0, var1, Target.pushObject);
               var12 = true;
            }
         } else {
            Object var20 = this;
            Declaration var5 = this.contextDecl();
            Object var8 = this;
            Declaration var6 = var4;
            Declaration var7 = var5;
            if(!this.isDefining()) {
               while(true) {
                  var8 = var20;
                  var6 = var4;
                  var7 = var5;
                  if(var4 == null) {
                     break;
                  }

                  var8 = var20;
                  var6 = var4;
                  var7 = var5;
                  if(!var4.isAlias()) {
                     break;
                  }

                  Expression var30 = var4.getValue();
                  if(!(var30 instanceof ReferenceExp)) {
                     var7 = var5;
                     var6 = var4;
                     var8 = var20;
                     break;
                  }

                  ReferenceExp var10 = (ReferenceExp)var30;
                  Declaration var9 = var10.binding;
                  var8 = var20;
                  var6 = var4;
                  var7 = var5;
                  if(var9 == null) {
                     break;
                  }

                  if(var5 != null) {
                     var8 = var20;
                     var6 = var4;
                     var7 = var5;
                     if(var9.needsContext()) {
                        break;
                     }
                  }

                  var5 = var10.contextDecl();
                  var20 = var10;
                  var4 = var9;
               }
            }

            if(var6.ignorable()) {
               this.new_value.compile(var1, (Target)Target.Ignore);
            } else if(var6.isAlias() && this.isDefining()) {
               var6.load(this, 2, var1, Target.pushObject);
               ClassType var29 = ClassType.make("gnu.mapping.IndirectableLocation");
               var11.emitCheckcast(var29);
               this.new_value.compile(var1, (Target)Target.pushObject);
               var11.emitInvokeVirtual(var29.getDeclaredMethod("setAlias", 1));
            } else {
               Variable var21;
               if(var6.isIndirectBinding()) {
                  var6.load((AccessExp)var8, 2, var1, Target.pushObject);
                  var12 = var16;
                  if(this.isSetIfUnbound()) {
                     var12 = var13;
                     if(var14) {
                        var11.emitDup();
                        var12 = true;
                     }

                     var11.pushScope();
                     var11.emitDup();
                     var21 = var11.addLocal(Compilation.typeLocation);
                     var11.emitStore(var21);
                     var11.emitInvokeVirtual(Compilation.typeLocation.getDeclaredMethod("isBound", 0));
                     var11.emitIfIntEqZero();
                     var11.emitLoad(var21);
                  }

                  this.new_value.compile(var1, (Target)Target.pushObject);
                  var13 = var12;
                  if(var14) {
                     var13 = var12;
                     if(!this.isSetIfUnbound()) {
                        var11.emitDupX();
                        var13 = true;
                     }
                  }

                  var11.emitInvokeVirtual(Compilation.typeLocation.getDeclaredMethod("set", 1));
                  var12 = var13;
                  if(this.isSetIfUnbound()) {
                     var11.emitFi();
                     var11.popScope();
                     var12 = var13;
                  }
               } else if(var6.isSimple()) {
                  Type var28 = var6.getType();
                  Variable var22 = var6.getVariable();
                  var21 = var22;
                  if(var22 == null) {
                     var21 = var6.allocateVariable(var11);
                  }

                  int var31 = canUseInc(this.new_value, var6);
                  if(var31 != 65536) {
                     var1.getCode().emitInc(var21, (short)var31);
                     if(var14) {
                        var11.emitLoad(var21);
                        var12 = true;
                     }
                  } else {
                     this.new_value.compile(var1, (Declaration)var6);
                     var12 = var15;
                     if(var14) {
                        var11.emitDup(var28);
                        var12 = true;
                     }

                     var11.emitStore(var21);
                  }
               } else if(var6.context instanceof ClassExp && var6.field == null && !this.getFlag(16) && ((ClassExp)var6.context).isMakingClassPair()) {
                  String var23 = ClassExp.slotToMethodName("set", var6.getName());
                  ClassExp var25 = (ClassExp)var6.context;
                  Method var27 = var25.type.getDeclaredMethod(var23, 1);
                  var25.loadHeapFrame(var1);
                  this.new_value.compile(var1, (Declaration)var6);
                  var12 = var17;
                  if(var14) {
                     var11.emitDupX();
                     var12 = true;
                  }

                  var11.emitInvoke(var27);
               } else {
                  Field var26 = var6.field;
                  if(!var26.getStaticFlag()) {
                     var6.loadOwningObject(var7, var1);
                  }

                  Type var24 = var26.getType();
                  this.new_value.compile(var1, (Declaration)var6);
                  var1.usedClass(var26.getDeclaringClass());
                  if(var26.getStaticFlag()) {
                     var12 = var18;
                     if(var14) {
                        var11.emitDup(var24);
                        var12 = true;
                     }

                     var11.emitPutStatic(var26);
                  } else {
                     var12 = var19;
                     if(var14) {
                        var11.emitDupX();
                        var12 = true;
                     }

                     var11.emitPutField(var26);
                  }
               }
            }
         }

         if(var14 && !var12) {
            throw new Error("SetExp.compile: not implemented - return value");
         } else if(var14) {
            var2.compileFromStack(var1, this.getType());
         } else {
            var1.compileConstant(Values.empty, var2);
         }
      }
   }

   public final boolean getHasValue() {
      return (this.flags & 64) != 0;
   }

   public final Expression getNewValue() {
      return this.new_value;
   }

   public final Type getType() {
      return (Type)(!this.getHasValue()?Type.voidType:(this.binding == null?Type.pointer_type:this.binding.getType()));
   }

   public final boolean isDefining() {
      return (this.flags & 2) != 0;
   }

   public final boolean isFuncDef() {
      return (this.flags & 16) != 0;
   }

   public final boolean isSetIfUnbound() {
      return (this.flags & 32) != 0;
   }

   protected boolean mustCompile() {
      return false;
   }

   public void print(OutPort var1) {
      String var2;
      if(this.isDefining()) {
         var2 = "(Define";
      } else {
         var2 = "(Set";
      }

      var1.startLogicalBlock(var2, ")", 2);
      var1.writeSpaceFill();
      this.printLineColumn(var1);
      if(this.binding == null || this.symbol.toString() != this.binding.getName()) {
         var1.print('/');
         var1.print((Object)this.symbol);
      }

      if(this.binding != null) {
         var1.print('/');
         var1.print((Object)this.binding);
      }

      var1.writeSpaceLinear();
      this.new_value.print((OutPort)var1);
      var1.endLogicalBlock(")");
   }

   public final void setDefining(boolean var1) {
      if(var1) {
         this.flags |= 2;
      } else {
         this.flags &= -3;
      }
   }

   public final void setFuncDef(boolean var1) {
      if(var1) {
         this.flags |= 16;
      } else {
         this.flags &= -17;
      }
   }

   public final void setHasValue(boolean var1) {
      if(var1) {
         this.flags |= 64;
      } else {
         this.flags &= -65;
      }
   }

   public final void setSetIfUnbound(boolean var1) {
      if(var1) {
         this.flags |= 32;
      } else {
         this.flags &= -33;
      }
   }

   public String toString() {
      return "SetExp[" + this.symbol + ":=" + this.new_value + ']';
   }

   protected Object visit(ExpVisitor var1, Object var2) {
      return var1.visitSetExp(this, var2);
   }

   protected void visitChildren(ExpVisitor var1, Object var2) {
      this.new_value = var1.visitAndUpdate(this.new_value, var2);
   }
}
