package gnu.expr;

import gnu.bytecode.ClassType;
import gnu.bytecode.Method;
import gnu.bytecode.PrimType;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.BeginExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.ExpExpVisitor;
import gnu.expr.Expression;
import gnu.expr.IfExp;
import gnu.expr.LambdaExp;
import gnu.expr.Language;
import gnu.expr.LetExp;
import gnu.expr.ObjectExp;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.ScopeExp;
import gnu.expr.SetExp;
import gnu.expr.TryExp;
import gnu.expr.TypeValue;
import gnu.kawa.reflect.CompileReflect;
import gnu.kawa.reflect.Invoke;
import gnu.kawa.util.IdentityHashTable;
import gnu.mapping.Procedure;
import gnu.mapping.Values;
import gnu.math.IntNum;

public class InlineCalls extends ExpExpVisitor {

   private static Class[] inlinerMethodArgTypes;


   public InlineCalls(Compilation var1) {
      this.setContext(var1);
   }

   public static Integer checkIntValue(Expression var0) {
      if(var0 instanceof QuoteExp) {
         QuoteExp var2 = (QuoteExp)var0;
         Object var1 = var2.getValue();
         if(!var2.isExplicitlyTyped() && var1 instanceof IntNum) {
            IntNum var3 = (IntNum)var1;
            if(var3.inIntRange()) {
               return Integer.valueOf(var3.intValue());
            }
         }
      }

      return null;
   }

   public static Long checkLongValue(Expression var0) {
      if(var0 instanceof QuoteExp) {
         QuoteExp var2 = (QuoteExp)var0;
         Object var1 = var2.getValue();
         if(!var2.isExplicitlyTyped() && var1 instanceof IntNum) {
            IntNum var3 = (IntNum)var1;
            if(var3.inLongRange()) {
               return Long.valueOf(var3.longValue());
            }
         }
      }

      return null;
   }

   private static Class[] getInlinerMethodArgTypes() throws Exception {
      // $FF: Couldn't be decompiled
   }

   public static Expression inlineCall(LambdaExp var0, Expression[] var1, boolean var2) {
      if(var0.keywords == null && (var0.nameDecl == null || var2)) {
         boolean var8;
         if(var0.max_args < 0) {
            var8 = true;
         } else {
            var8 = false;
         }

         if((var0.min_args != var0.max_args || var0.min_args != var1.length) && (!var8 || var0.min_args != 0)) {
            return null;
         } else {
            Declaration var5 = null;
            int var9 = 0;
            Expression[] var3;
            IdentityHashTable var4;
            if(var2) {
               IdentityHashTable var6 = new IdentityHashTable();
               Expression[] var7 = Expression.deepCopy((Expression[])var1, var6);
               var3 = var7;
               var4 = var6;
               if(var7 == null) {
                  var3 = var7;
                  var4 = var6;
                  if(var1 != null) {
                     return null;
                  }
               }
            } else {
               var4 = null;
               var3 = var1;
            }

            if(var8) {
               Expression[] var14 = new Expression[var1.length + 1];
               var14[0] = QuoteExp.getInstance(var0.firstDecl().type);
               System.arraycopy(var1, 0, var14, 1, var1.length);
               var3 = new Expression[]{new ApplyExp(Invoke.make, var14)};
            }

            LetExp var15 = new LetExp(var3);

            for(Declaration var10 = var0.firstDecl(); var10 != null; ++var9) {
               Declaration var13 = var10.nextDecl();
               if(var2) {
                  var5 = var15.addDeclaration(var10.symbol, var10.type);
                  if(var10.typeExp != null) {
                     var5.typeExp = Expression.deepCopy((Expression)var10.typeExp);
                     if(var5.typeExp == null) {
                        return null;
                     }
                  }

                  var4.put(var10, var5);
               } else {
                  var0.remove(var5, var10);
                  var15.add(var5, var10);
               }

               if(!var8 && !var10.getCanWrite()) {
                  var10.setValue(var3[var9]);
               }

               var5 = var10;
               var10 = var13;
            }

            Expression var12 = var0.body;
            Expression var11 = var12;
            if(var2) {
               var12 = Expression.deepCopy((Expression)var12, var4);
               var11 = var12;
               if(var12 == null) {
                  var11 = var12;
                  if(var0.body != null) {
                     return null;
                  }
               }
            }

            var15.body = var11;
            return var15;
         }
      } else {
         return null;
      }
   }

   public static Expression inlineCalls(Expression var0, Compilation var1) {
      return (new InlineCalls(var1)).visit(var0, (Type)null);
   }

   public Expression checkType(Expression var1, Type var2) {
      boolean var6 = true;
      Object var4 = var1.getType();
      boolean var7;
      if(var2 instanceof ClassType && ((ClassType)var2).isInterface() && ((Type)var4).isSubtype(Compilation.typeProcedure) && !((Type)var4).isSubtype(var2)) {
         if(var1 instanceof LambdaExp) {
            Method var9 = ((ClassType)var2).checkSingleAbstractMethod();
            if(var9 != null) {
               LambdaExp var12 = (LambdaExp)var1;
               ObjectExp var11 = new ObjectExp();
               var11.setLocation(var1);
               var11.supers = new Expression[]{new QuoteExp(var2)};
               var11.setTypes(this.getCompilation());
               String var8 = var9.getName();
               var11.addMethod(var12, var8);
               var11.addDeclaration(var8, Compilation.typeProcedure);
               var11.firstChild = var12;
               var11.declareParts(this.comp);
               return this.visit(var11, (Type)var2);
            }
         }

         var7 = true;
      } else {
         Object var3 = var4;
         if(var4 == Type.toStringType) {
            var3 = Type.javalangStringType;
         }

         if(var2 == null || var2.compare((Type)var3) != -3) {
            var6 = false;
         }

         var4 = var3;
         var7 = var6;
         if(var6) {
            var4 = var3;
            var7 = var6;
            if(var2 instanceof TypeValue) {
               Expression var5 = ((TypeValue)var2).convertValue(var1);
               var4 = var3;
               var7 = var6;
               if(var5 != null) {
                  return var5;
               }
            }
         }
      }

      if(var7) {
         Language var10 = this.comp.getLanguage();
         this.comp.error('w', "type " + var10.formatType((Type)var4) + " is incompatible with required type " + var10.formatType(var2), var1);
      }

      return var1;
   }

   public QuoteExp fixIntValue(Expression var1) {
      Integer var2 = checkIntValue(var1);
      return var2 != null?new QuoteExp(var2, this.comp.getLanguage().getTypeFor((Class)Integer.TYPE)):null;
   }

   public QuoteExp fixLongValue(Expression var1) {
      Long var2 = checkLongValue(var1);
      return var2 != null?new QuoteExp(var2, this.comp.getLanguage().getTypeFor((Class)Long.TYPE)):null;
   }

   public Expression maybeInline(ApplyExp param1, Type param2, Procedure param3) {
      // $FF: Couldn't be decompiled
   }

   public Expression visit(Expression var1, Type var2) {
      Expression var3 = var1;
      if(!var1.getFlag(1)) {
         var1.setFlag(1);
         var3 = (Expression)super.visit(var1, var2);
         var3.setFlag(1);
      }

      return this.checkType(var3, var2);
   }

   protected Expression visitApplyExp(ApplyExp var1, Type var2) {
      Expression var3 = var1.func;
      if(var3 instanceof LambdaExp) {
         LambdaExp var4 = (LambdaExp)var3;
         Expression var5 = inlineCall((LambdaExp)var3, var1.args, false);
         if(var5 != null) {
            return this.visit(var5, (Type)var2);
         }
      }

      var3 = this.visit(var3, (Type)null);
      var1.func = var3;
      return var3.validateApply(var1, this, var2, (Declaration)null);
   }

   public final Expression visitApplyOnly(ApplyExp var1, Type var2) {
      return var1.func.validateApply(var1, this, var2, (Declaration)null);
   }

   protected Expression visitBeginExp(BeginExp var1, Type var2) {
      int var7 = var1.length - 1;

      for(int var6 = 0; var6 <= var7; ++var6) {
         Expression[] var4 = var1.exps;
         Expression var5 = var1.exps[var6];
         Type var3;
         if(var6 < var7) {
            var3 = null;
         } else {
            var3 = var2;
         }

         var4[var6] = this.visit(var5, (Type)var3);
      }

      return var1;
   }

   protected Expression visitIfExp(IfExp var1, Type var2) {
      Expression var4 = (Expression)var1.test.visit(this, (Object)null);
      Expression var3 = var4;
      if(var4 instanceof ReferenceExp) {
         Declaration var5 = ((ReferenceExp)var4).getBinding();
         var3 = var4;
         if(var5 != null) {
            Expression var8 = var5.getValue();
            var3 = var4;
            if(var8 instanceof QuoteExp) {
               var3 = var4;
               if(var8 != QuoteExp.undefined_exp) {
                  var3 = var8;
               }
            }
         }
      }

      var1.test = var3;
      if(this.exitValue == null) {
         var1.then_clause = this.visit(var1.then_clause, (Type)var2);
      }

      if(this.exitValue == null && var1.else_clause != null) {
         var1.else_clause = this.visit(var1.else_clause, (Type)var2);
      }

      Object var7;
      if(var3 instanceof QuoteExp) {
         var7 = var1.select(this.comp.getLanguage().isTrue(((QuoteExp)var3).getValue()));
      } else {
         var7 = var1;
         if(var3.getType().isVoid()) {
            boolean var6 = this.comp.getLanguage().isTrue(Values.empty);
            this.comp.error('w', "void-valued condition is always " + var6);
            return new BeginExp(var3, var1.select(var6));
         }
      }

      return (Expression)var7;
   }

   protected Expression visitLambdaExp(LambdaExp var1, Type var2) {
      Declaration var3 = var1.firstDecl();
      if(var3 != null && var3.isThisParameter() && !var1.isClassMethod() && var3.type == null) {
         var3.setType(this.comp.mainClass);
      }

      return this.visitScopeExp(var1, (Type)var2);
   }

   protected Expression visitLetExp(LetExp var1, Type var2) {
      Declaration var3 = var1.firstDecl();
      int var7 = var1.inits.length;

      for(int var6 = 0; var6 < var7; var3 = var3.nextDecl()) {
         Expression var5 = var1.inits[var6];
         boolean var8 = var3.getFlag(8192L);
         Type var4;
         if(var8 && var5 != QuoteExp.undefined_exp) {
            var4 = var3.getType();
         } else {
            var4 = null;
         }

         Expression var13 = this.visit(var5, (Type)var4);
         var1.inits[var6] = var13;
         if(var3.value == var5) {
            var3.value = var13;
            if(!var8) {
               var3.setType(var13.getType());
            }
         }

         ++var6;
      }

      if(this.exitValue == null) {
         var1.body = this.visit(var1.body, (Type)var2);
      }

      if(var1.body instanceof ReferenceExp) {
         ReferenceExp var10 = (ReferenceExp)var1.body;
         var3 = var10.getBinding();
         if(var3 != null && var3.context == var1 && !var10.getDontDereference() && var7 == 1) {
            Expression var11 = var1.inits[0];
            Expression var12 = var3.getTypeExp();
            Expression var9 = var11;
            if(var12 != QuoteExp.classObjectExp) {
               var9 = this.visitApplyOnly(Compilation.makeCoercion(var11, (Expression)var12), (Type)null);
            }

            return var9;
         }
      }

      return var1;
   }

   protected Expression visitQuoteExp(QuoteExp var1, Type var2) {
      QuoteExp var3 = var1;
      if(var1.getRawType() == null) {
         var3 = var1;
         if(!var1.isSharedConstant()) {
            Object var4 = var1.getValue();
            var3 = var1;
            if(var4 != null) {
               Type var8 = this.comp.getLanguage().getTypeFor((Class)var4.getClass());
               Object var7 = var8;
               if(var8 == Type.toStringType) {
                  var7 = Type.javalangStringType;
               }

               var1.type = (Type)var7;
               var3 = var1;
               if(var2 instanceof PrimType) {
                  char var5 = var2.getSignature().charAt(0);
                  QuoteExp var6;
                  if(var5 == 73) {
                     var6 = this.fixIntValue(var1);
                  } else if(var5 == 74) {
                     var6 = this.fixLongValue(var1);
                  } else {
                     var6 = null;
                  }

                  var3 = var1;
                  if(var6 != null) {
                     var3 = var6;
                  }
               }
            }
         }
      }

      return var3;
   }

   protected Expression visitReferenceExp(ReferenceExp var1, Type var2) {
      Declaration var3 = var1.getBinding();
      if(var3 != null && var3.field == null && !var3.getCanWrite()) {
         Expression var4 = var3.getValue();
         if(var4 instanceof QuoteExp && var4 != QuoteExp.undefined_exp) {
            return this.visitQuoteExp((QuoteExp)var4, (Type)var2);
         }

         if(var4 instanceof ReferenceExp && !var3.isAlias()) {
            ReferenceExp var7 = (ReferenceExp)var4;
            Declaration var5 = var7.getBinding();
            Type var6 = var3.getType();
            if(var5 != null && !var5.getCanWrite() && (var6 == null || var6 == Type.pointer_type || var6 == var5.getType()) && !var7.getDontDereference()) {
               return this.visitReferenceExp(var7, (Type)var2);
            }
         }

         if(!var1.isProcedureName() && (var3.flags & 1048704L) == 1048704L) {
            this.comp.error('e', "unimplemented: reference to method " + var3.getName() + " as variable");
            this.comp.error('e', (Declaration)var3, "here is the definition of ", (String)"");
         }
      }

      return (Expression)super.visitReferenceExp(var1, var2);
   }

   protected Expression visitScopeExp(ScopeExp var1, Type var2) {
      var1.visitChildren(this, (Object)null);
      this.visitDeclarationTypes(var1);

      for(Declaration var4 = var1.firstDecl(); var4 != null; var4 = var4.nextDecl()) {
         if(var4.type == null) {
            Expression var3 = var4.getValue();
            var4.type = Type.objectType;
            Object var5;
            if(var3 != null && var3 != QuoteExp.undefined_exp) {
               var5 = var3.getType();
            } else {
               var5 = Type.objectType;
            }

            var4.setType((Type)var5);
         }
      }

      return var1;
   }

   protected Expression visitSetExp(SetExp var1, Type var2) {
      Declaration var3 = var1.getBinding();
      super.visitSetExp(var1, var2);
      if(!var1.isDefining() && var3 != null && (var3.flags & 1048704L) == 1048704L) {
         this.comp.error('e', "can\'t assign to method " + var3.getName(), var1);
      }

      if(var3 != null && var3.getFlag(8192L) && CompileReflect.checkKnownClass(var3.getType(), this.comp) < 0) {
         var3.setType(Type.errorType);
      }

      return var1;
   }

   protected Expression visitSetExpValue(Expression var1, Type var2, Declaration var3) {
      if(var3 != null && !var3.isAlias()) {
         var2 = var3.type;
      } else {
         var2 = null;
      }

      return this.visit(var1, (Type)var2);
   }

   protected Expression visitTryExp(TryExp var1, Type var2) {
      return var1.getCatchClauses() == null && var1.getFinallyClause() == null?this.visit(var1.try_clause, (Type)var2):(Expression)super.visitTryExp(var1, var2);
   }
}
