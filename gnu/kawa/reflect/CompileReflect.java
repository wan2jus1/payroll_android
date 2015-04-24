package gnu.kawa.reflect;

import gnu.bytecode.ArrayType;
import gnu.bytecode.ClassType;
import gnu.bytecode.Field;
import gnu.bytecode.Member;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;
import gnu.bytecode.PrimType;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.ClassExp;
import gnu.expr.Compilation;
import gnu.expr.ErrorExp;
import gnu.expr.Expression;
import gnu.expr.InlineCalls;
import gnu.expr.LambdaExp;
import gnu.expr.Language;
import gnu.expr.QuoteExp;
import gnu.kawa.reflect.Invoke;
import gnu.kawa.reflect.SlotGet;
import gnu.kawa.reflect.SlotSet;
import gnu.lists.FString;
import gnu.mapping.Procedure;
import gnu.mapping.Symbol;

public class CompileReflect {

   public static int checkKnownClass(Type var0, Compilation var1) {
      if(var0 instanceof ClassType && var0.isExisting()) {
         try {
            var0.getReflectClass();
            return 1;
         } catch (Exception var3) {
            var1.error('e', "unknown class: " + var0.getName());
            return -1;
         }
      } else {
         return 0;
      }
   }

   public static ApplyExp inlineClassName(ApplyExp var0, int var1, InlineCalls var2) {
      Compilation var3 = var2.getCompilation();
      Language var4 = var3.getLanguage();
      Expression[] var5 = var0.getArgs();
      if(var5.length > var1) {
         Type var8 = var4.getTypeFor((Expression)var5[var1]);
         if(var8 instanceof Type) {
            checkKnownClass(var8, var3);
            Expression[] var7 = new Expression[var5.length];
            System.arraycopy(var5, 0, var7, 0, var5.length);
            var7[var1] = new QuoteExp(var8);
            ApplyExp var6 = new ApplyExp(var0.getFunction(), var7);
            var6.setLine(var0);
            return var6;
         }
      }

      return var0;
   }

   public static Expression validateApplyInstanceOf(ApplyExp var0, InlineCalls var1, Type var2, Procedure var3) {
      var0.visitArgs(var1);
      ApplyExp var9 = inlineClassName(var0, 1, var1);
      Expression[] var5 = var9.getArgs();
      if(var5.length == 2) {
         Expression var10 = var5[0];
         Expression var6 = var5[1];
         if(var6 instanceof QuoteExp) {
            Object var7 = ((QuoteExp)var6).getValue();
            if(var7 instanceof Type) {
               Type var8 = (Type)var7;
               var7 = var8;
               if(var8 instanceof PrimType) {
                  var7 = ((PrimType)var8).boxedType();
               }

               if(var10 instanceof QuoteExp) {
                  if(((Type)var7).isInstance(((QuoteExp)var10).getValue())) {
                     return QuoteExp.trueExp;
                  }

                  return QuoteExp.falseExp;
               }

               if(!var10.side_effects()) {
                  int var4 = ((Type)var7).compare(var10.getType());
                  if(var4 == 1 || var4 == 0) {
                     return QuoteExp.trueExp;
                  }

                  if(var4 == -3) {
                     return QuoteExp.falseExp;
                  }
               }
            }
         }
      }

      return var9;
   }

   public static Expression validateApplySlotGet(ApplyExp var0, InlineCalls var1, Type var2, Procedure var3) {
      var0.visitArgs(var1);
      Compilation var10 = var1.getCompilation();
      Language var6 = var10.getLanguage();
      boolean var15 = ((SlotGet)var3).isStatic;
      Expression[] var7 = var0.getArgs();
      Expression var9 = var7[0];
      Expression var11 = var7[1];
      Object var19 = var11.valueIfConstant();
      ApplyExp var5;
      if(!(var19 instanceof String) && !(var19 instanceof FString)) {
         var5 = var0;
         if(!(var19 instanceof Symbol)) {
            return var5;
         }
      }

      String var8 = var19.toString();
      Type var4;
      int var14;
      ApplyExp var20;
      if(var15) {
         Type var24 = var6.getTypeFor((Expression)var9);
         var14 = checkKnownClass(var24, var10);
         if(var14 < 0) {
            var5 = var0;
            return var5;
         }

         if("class".equals(var8)) {
            if(var14 > 0) {
               return QuoteExp.getInstance(var24.getReflectClass());
            }

            return new ApplyExp(Compilation.typeType.getDeclaredMethod("getReflectClass", 0), new Expression[]{var9});
         }

         var4 = var24;
         var20 = var0;
         if(var24 != null) {
            QuoteExp var21 = new QuoteExp(var24);
            var20 = new ApplyExp(var0.getFunction(), new Expression[]{var21, var11});
            var20.setLine(var0);
            var4 = var24;
         }
      } else {
         var4 = var9.getType();
         var20 = var0;
      }

      var5 = var20;
      if(!(var4 instanceof ArrayType)) {
         QuoteExp var18;
         if(var4 instanceof ObjectType) {
            ObjectType var26 = (ObjectType)var4;
            ClassType var17;
            if(var10.curClass != null) {
               var17 = var10.curClass;
            } else {
               var17 = var10.mainClass;
            }

            Member var28 = SlotGet.lookupMember(var26, var8, var17);
            if(var28 instanceof Field) {
               Field var12 = (Field)var28;
               boolean var32;
               if((var12.getModifiers() & 8) != 0) {
                  var32 = true;
               } else {
                  var32 = false;
               }

               if(var15 && !var32) {
                  return new ErrorExp("cannot access non-static field `" + var8 + "\' using `" + var3.getName() + '\'', var10);
               }

               if(var17 != null && !var17.isAccessible(var12, var26)) {
                  return new ErrorExp("field " + var12.getDeclaringClass().getName() + '.' + var8 + " is not accessible here", var10);
               }
            } else if(var28 instanceof Method) {
               Method var30 = (Method)var28;
               ClassType var13 = var30.getDeclaringClass();
               var14 = var30.getModifiers();
               boolean var16 = var30.getStaticFlag();
               if(var15 && !var16) {
                  return new ErrorExp("cannot call non-static getter method `" + var8 + "\' using `" + var3.getName() + '\'', var10);
               }

               if(var17 != null && !var17.isAccessible(var13, var26, var14)) {
                  return new ErrorExp("method " + var30 + " is not accessible here", var10);
               }
            }

            if(var28 != null) {
               var18 = new QuoteExp(var28);
               var0 = new ApplyExp(var20.getFunction(), new Expression[]{var9, var18});
               var0.setLine(var20);
               return var0;
            }

            if(var4 != Type.pointer_type && var10.warnUnknownMember()) {
               var10.error('e', "no slot `" + var8 + "\' in " + var26.getName());
            }
         }

         String var22 = Compilation.mangleNameIfNeeded(var8).intern();
         String var23 = ClassExp.slotToMethodName("get", var8);
         String var25 = ClassExp.slotToMethodName("is", var8);
         Invoke var27 = Invoke.invokeStatic;
         QuoteExp var29 = QuoteExp.getInstance("gnu.kawa.reflect.SlotGet");
         QuoteExp var31 = QuoteExp.getInstance("getSlotValue");
         if(var15) {
            var18 = QuoteExp.trueExp;
         } else {
            var18 = QuoteExp.falseExp;
         }

         var0 = new ApplyExp(var27, new Expression[]{var29, var31, var18, var7[0], QuoteExp.getInstance(var8), QuoteExp.getInstance(var22), QuoteExp.getInstance(var23), QuoteExp.getInstance(var25), QuoteExp.getInstance(var6)});
         var0.setLine(var20);
         return var1.visitApplyOnly(var0, (Type)null);
      } else {
         return var5;
      }
   }

   public static Expression validateApplySlotSet(ApplyExp var0, InlineCalls var1, Type var2, Procedure var3) {
      var0.visitArgs(var1);
      SlotSet var6 = (SlotSet)var3;
      ApplyExp var5 = var0;
      if(var6.isStatic) {
         var5 = var0;
         if(var1.getCompilation().mustCompile) {
            var5 = inlineClassName(var0, 0, var1);
         }
      }

      Object var4;
      if(var6.returnSelf && var5.getArgCount() == 3) {
         var4 = var5.getArg(0).getType();
      } else {
         var4 = Type.voidType;
      }

      var5.setType((Type)var4);
      return var5;
   }

   public static Expression validateApplyTypeSwitch(ApplyExp var0, InlineCalls var1, Type var2, Procedure var3) {
      var0.visitArgs(var1);
      Expression[] var5 = var0.getArgs();

      for(int var4 = 1; var4 < var5.length; ++var4) {
         if(var5[var4] instanceof LambdaExp) {
            LambdaExp var6 = (LambdaExp)var5[var4];
            var6.setInlineOnly(true);
            var6.returnContinuation = var0;
            var6.inlineHome = var1.getCurrentLambda();
         }
      }

      return var0;
   }
}
