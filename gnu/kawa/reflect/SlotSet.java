package gnu.kawa.reflect;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Field;
import gnu.bytecode.Member;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.CheckedTarget;
import gnu.expr.ClassExp;
import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.Inlineable;
import gnu.expr.Language;
import gnu.expr.QuoteExp;
import gnu.expr.Target;
import gnu.lists.FString;
import gnu.mapping.Procedure;
import gnu.mapping.Procedure3;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import kawa.standard.Scheme;

public class SlotSet extends Procedure3 implements Inlineable {

   public static final SlotSet set$Mnfield$Ex = new SlotSet("set-field!", false);
   public static final SlotSet set$Mnstatic$Mnfield$Ex = new SlotSet("set-static-field!", true);
   public static final SlotSet setFieldReturnObject = new SlotSet("set-field-return-object!", false);
   static final Type[] type1Array;
   boolean isStatic;
   boolean returnSelf;


   static {
      setFieldReturnObject.returnSelf = true;
      type1Array = new Type[1];
   }

   public SlotSet(String var1, boolean var2) {
      super(var1);
      this.isStatic = var2;
      this.setProperty(Procedure.validateApplyKey, "gnu.kawa.reflect.CompileReflect:validateApplySlotSet");
   }

   public static void apply(boolean param0, Object param1, Object param2, Object param3) {
      // $FF: Couldn't be decompiled
   }

   static void compileSet(Procedure var0, ObjectType var1, Expression var2, Object var3, Compilation var4) {
      CodeAttr var8 = var4.getCode();
      Language var5 = var4.getLanguage();
      boolean var6;
      if(var0 instanceof SlotSet && ((SlotSet)var0).isStatic) {
         var6 = true;
      } else {
         var6 = false;
      }

      boolean var7;
      if(var3 instanceof Field) {
         Field var9 = (Field)var3;
         var7 = var9.getStaticFlag();
         Type var11 = var5.getLangTypeFor(var9.getType());
         if(var6 && !var7) {
            var4.error('e', "cannot access non-static field `" + var9.getName() + "\' using `" + var0.getName() + '\'');
         }

         var2.compile(var4, (Target)CheckedTarget.getInstance((Type)var11));
         if(!var7) {
            var8.emitPutField(var9);
            return;
         }

         var8.emitPutStatic(var9);
      } else if(var3 instanceof Method) {
         Method var10 = (Method)var3;
         var7 = var10.getStaticFlag();
         if(var6 && !var7) {
            var4.error('e', "cannot call non-static getter method `" + var10.getName() + "\' using `" + var0.getName() + '\'');
         }

         var2.compile(var4, (Target)CheckedTarget.getInstance((Type)var5.getLangTypeFor(var10.getParameterTypes()[0])));
         if(var7) {
            var8.emitInvokeStatic(var10);
         } else {
            var8.emitInvoke(var10);
         }

         if(!var10.getReturnType().isVoid()) {
            var8.emitPop(1);
            return;
         }
      }

   }

   public static Member lookupMember(ObjectType var0, String var1, ClassType var2) {
      Field var4 = var0.getField(Compilation.mangleNameIfNeeded(var1), -1);
      if(var4 != null) {
         ClassType var3 = var2;
         if(var2 == null) {
            var3 = Type.pointer_type;
         }

         if(var3.isAccessible(var4, var0)) {
            return var4;
         }
      }

      Method var5 = var0.getMethod(ClassExp.slotToMethodName("set", var1), type1Array);
      if(var5 != null) {
         return var5;
      } else {
         return var4;
      }
   }

   public static void setField(Object var0, String var1, Object var2) {
      apply(false, var0, var1, var2);
   }

   public static void setStaticField(Object var0, String var1, Object var2) {
      apply(true, var0, var1, var2);
   }

   public Object apply3(Object var1, Object var2, Object var3) {
      apply(this.isStatic, var1, var2, var3);
      return this.returnSelf?var1:Values.empty;
   }

   public void compile(ApplyExp var1, Compilation var2, Target var3) {
      Expression[] var10 = var1.getArgs();
      int var12 = var10.length;
      if(var12 != 3) {
         String var14;
         if(var12 < 3) {
            var14 = "too few";
         } else {
            var14 = "too many";
         }

         var2.error('e', var14 + " arguments to `" + this.getName() + '\'');
         var2.compileConstant((Object)null, var3);
      } else {
         Expression var4 = var10[0];
         Expression var5 = var10[1];
         Expression var6 = var10[2];
         Type var18;
         if(this.isStatic) {
            var18 = Scheme.exp2Type(var4);
         } else {
            var18 = var4.getType();
         }

         Member var15 = null;
         if(var18 instanceof ObjectType && var5 instanceof QuoteExp) {
            Object var16 = ((QuoteExp)var5).getValue();
            ObjectType var11 = (ObjectType)var18;
            ClassType var7;
            if(var2.curClass != null) {
               var7 = var2.curClass;
            } else {
               var7 = var2.mainClass;
            }

            String var17;
            if(!(var16 instanceof String) && !(var16 instanceof FString) && !(var16 instanceof Symbol)) {
               if(var16 instanceof Member) {
                  var15 = (Member)var16;
                  var17 = var15.getName();
               } else {
                  var17 = null;
               }
            } else {
               String var8 = var16.toString();
               Member var9 = lookupMember(var11, var8, var7);
               var17 = var8;
               var15 = var9;
               if(var9 == null) {
                  var17 = var8;
                  var15 = var9;
                  if(var18 != Type.pointer_type) {
                     var17 = var8;
                     var15 = var9;
                     if(var2.warnUnknownMember()) {
                        var2.error('w', "no slot `" + var8 + "\' in " + var11.getName());
                        var15 = var9;
                        var17 = var8;
                     }
                  }
               }
            }

            if(var15 != null) {
               boolean var19;
               if((var15.getModifiers() & 8) != 0) {
                  var19 = true;
               } else {
                  var19 = false;
               }

               if(var7 != null && !var7.isAccessible(var15, var11)) {
                  var2.error('e', "slot \'" + var17 + "\' in " + var15.getDeclaringClass().getName() + " not accessible here");
               }

               var5 = var10[0];
               Target var13;
               if(var19) {
                  var13 = Target.Ignore;
               } else {
                  var13 = Target.pushValue(var11);
               }

               var5.compile(var2, (Target)var13);
               if(this.returnSelf) {
                  var2.getCode().emitDup(var11.getImplementationType());
               }

               compileSet(this, var11, var10[2], var15, var2);
               if(this.returnSelf) {
                  var3.compileFromStack(var2, var11);
                  return;
               }

               var2.compileConstant(Values.empty, var3);
               return;
            }
         }

         ApplyExp.compile(var1, var2, var3);
      }
   }
}
