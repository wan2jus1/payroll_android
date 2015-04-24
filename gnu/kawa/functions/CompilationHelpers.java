package gnu.kawa.functions;

import gnu.bytecode.ArrayType;
import gnu.bytecode.ClassType;
import gnu.bytecode.Field;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.InlineCalls;
import gnu.expr.LambdaExp;
import gnu.expr.Language;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.kawa.functions.IsEqv;
import gnu.kawa.functions.SetArrayExp;
import gnu.kawa.functions.SetListExp;
import gnu.kawa.functions.Setter;
import gnu.kawa.reflect.ArrayGet;
import gnu.kawa.reflect.CompileReflect;
import gnu.kawa.reflect.Invoke;
import gnu.mapping.Procedure;
import gnu.mapping.Symbol;
import gnu.math.Numeric;
import gnu.text.Char;
import java.io.Externalizable;

public class CompilationHelpers {

   public static final Declaration setterDecl = new Declaration("setter", setterField);
   static final Field setterField = setterType.getDeclaredField("setter");
   static final ClassType setterType = ClassType.make("gnu.kawa.functions.Setter");
   static final ClassType typeList = ClassType.make("java.util.List");


   static {
      setterDecl.noteValue(new QuoteExp(Setter.setter));
   }

   private static boolean nonNumeric(Expression var0) {
      boolean var2 = false;
      boolean var1 = var2;
      if(var0 instanceof QuoteExp) {
         Object var3 = ((QuoteExp)var0).getValue();
         var1 = var2;
         if(!(var3 instanceof Numeric)) {
            var1 = var2;
            if(!(var3 instanceof Char)) {
               var1 = var2;
               if(!(var3 instanceof Symbol)) {
                  var1 = true;
               }
            }
         }
      }

      return var1;
   }

   public static Expression validateApplyToArgs(ApplyExp var0, InlineCalls var1, Type var2, Procedure var3) {
      Expression[] var5 = var0.getArgs();
      int var9 = var5.length - 1;
      if(var9 >= 0) {
         Expression var4 = var5[0];
         Expression var10 = var4;
         if(!var4.getFlag(1)) {
            if(var4 instanceof LambdaExp) {
               Expression[] var15 = new Expression[var9];
               System.arraycopy(var5, 1, var15, 0, var9);
               return var1.visit((new ApplyExp(var4, var15)).setLine(var0), (Type)var2);
            }

            var10 = var1.visit(var4, (Type)null);
            var5[0] = var10;
         }

         Type var6 = var10.getType().getRealType();
         Compilation var7 = var1.getCompilation();
         Language var8 = var7.getLanguage();
         if(var6.isSubtype(Compilation.typeProcedure)) {
            Expression[] var12 = new Expression[var9];
            System.arraycopy(var5, 1, var12, 0, var9);
            ApplyExp var13 = new ApplyExp(var10, var12);
            var13.setLine(var0);
            return var10.validateApply(var13, var1, var2, (Declaration)null);
         }

         var4 = null;
         ApplyExp var11;
         if(CompileReflect.checkKnownClass(var6, var7) < 0) {
            var11 = var4;
         } else if(!var6.isSubtype(Compilation.typeType) && var8.getTypeFor((Expression)var10, false) == null) {
            if(var6 instanceof ArrayType) {
               var11 = new ApplyExp(new ArrayGet(((ArrayType)var6).getComponentType()), var5);
            } else {
               var11 = var4;
               if(var6 instanceof ClassType) {
                  ClassType var14 = (ClassType)var6;
                  var11 = var4;
                  if(var14.isSubclass((ClassType)typeList)) {
                     var11 = var4;
                     if(var9 == 1) {
                        var11 = new ApplyExp(var14.getMethod("get", new Type[]{Type.intType}), var5);
                     }
                  }
               }
            }
         } else {
            var11 = new ApplyExp(Invoke.make, var5);
         }

         if(var11 != null) {
            var11.setLine(var0);
            return var1.visitApplyOnly(var11, var2);
         }
      }

      var0.visitArgs(var1);
      return var0;
   }

   public static Expression validateIsEqv(ApplyExp var0, InlineCalls var1, Type var2, Procedure var3) {
      var0.visitArgs(var1);
      Expression[] var4 = var0.getArgs();
      if(nonNumeric(var4[0]) || nonNumeric(var4[1])) {
         var0 = new ApplyExp(((IsEqv)var3).isEq, var4);
      }

      return var0;
   }

   public static Expression validateSetter(ApplyExp var0, InlineCalls var1, Type var2, Procedure var3) {
      var0.visitArgs(var1);
      Expression[] var6 = var0.getArgs();
      Object var4 = var0;
      if(var6.length == 1) {
         Expression var5 = var6[0];
         Type var8 = var5.getType();
         if(var8 instanceof ArrayType) {
            var4 = new SetArrayExp(var5, (ArrayType)var8);
         } else if(var8 instanceof ClassType && ((ClassType)var8).isSubclass((ClassType)typeList)) {
            var4 = var0;
            if(!(var0 instanceof SetListExp)) {
               return new SetListExp(var0.getFunction(), var6);
            }
         } else {
            Expression var7 = var5;
            if(var5 instanceof ReferenceExp) {
               Declaration var9 = ((ReferenceExp)var5).getBinding();
               var7 = var5;
               if(var9 != null) {
                  var7 = var9.getValue();
               }
            }

            var4 = var0;
            if(var7 instanceof QuoteExp) {
               Object var10 = ((QuoteExp)var7).getValue();
               var4 = var0;
               if(var10 instanceof Procedure) {
                  Procedure var11 = ((Procedure)var10).getSetter();
                  var4 = var0;
                  if(var11 instanceof Procedure) {
                     if(var11 instanceof Externalizable) {
                        return new QuoteExp(var11);
                     }

                     Declaration var12 = Declaration.getDeclaration((Procedure)var11);
                     var4 = var0;
                     if(var12 != null) {
                        return new ReferenceExp(var12);
                     }
                  }
               }
            }
         }
      }

      return (Expression)var4;
   }
}
