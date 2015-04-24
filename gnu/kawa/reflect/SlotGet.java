package gnu.kawa.reflect;

import gnu.bytecode.ArrayType;
import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Field;
import gnu.bytecode.Member;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.ClassExp;
import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.Inlineable;
import gnu.expr.Language;
import gnu.expr.QuoteExp;
import gnu.expr.Target;
import gnu.kawa.lispexpr.LangPrimType;
import gnu.kawa.reflect.ClassMethods;
import gnu.kawa.reflect.SlotSet;
import gnu.mapping.HasSetter;
import gnu.mapping.Procedure;
import gnu.mapping.Procedure2;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.WrongArguments;
import gnu.mapping.WrongType;

public class SlotGet extends Procedure2 implements HasSetter, Inlineable {

   public static final SlotGet field = new SlotGet("field", false, SlotSet.set$Mnfield$Ex);
   static Class[] noClasses = new Class[0];
   public static final SlotGet slotRef = new SlotGet("slot-ref", false, SlotSet.set$Mnfield$Ex);
   public static final SlotGet staticField = new SlotGet("static-field", true, SlotSet.set$Mnstatic$Mnfield$Ex);
   boolean isStatic;
   Procedure setter;


   public SlotGet(String var1, boolean var2) {
      super(var1);
      this.isStatic = var2;
      this.setProperty(Procedure.validateApplyKey, "gnu.kawa.reflect.CompileReflect:validateApplySlotGet");
   }

   public SlotGet(String var1, boolean var2, Procedure var3) {
      this(var1, var2);
      this.setter = var3;
   }

   static Class coerceToClass(Object var0) {
      if(var0 instanceof Class) {
         return (Class)var0;
      } else if(var0 instanceof Type) {
         return ((Type)var0).getReflectClass();
      } else {
         throw new RuntimeException("argument is neither Class nor Type");
      }
   }

   public static Object field(Object var0, String var1) {
      return field.apply2(var0, var1);
   }

   public static Object getSlotValue(boolean param0, Object param1, String param2, String param3, String param4, String param5, Language param6) {
      // $FF: Couldn't be decompiled
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

      Method var5 = var0.getMethod(ClassExp.slotToMethodName("get", var1), Type.typeArray0);
      if(var5 != null) {
         return var5;
      } else {
         return var4;
      }
   }

   public static ApplyExp makeGetField(Expression var0, String var1) {
      QuoteExp var2 = new QuoteExp(var1);
      return new ApplyExp(field, new Expression[]{var0, var2});
   }

   public static Object staticField(Object var0, String var1) {
      return staticField.apply2(var0, var1);
   }

   public Object apply2(Object var1, Object var2) {
      String var3 = null;
      String var6 = null;
      Object var7 = null;
      String var5 = null;
      String var4;
      String var8;
      if(var2 instanceof Field) {
         var8 = ((Field)var2).getName();
         var3 = Compilation.demangleName(var8, true);
      } else if(var2 instanceof Method) {
         var8 = ((Method)var2).getName();
         var6 = Compilation.demangleName(var8, false);
         if(var8.startsWith("get")) {
            var5 = (String)var7;
            var4 = var8;
         } else {
            var4 = var3;
            var5 = (String)var7;
            if(var8.startsWith("is")) {
               var4 = var3;
               var5 = var8;
            }
         }

         var8 = null;
         var3 = var6;
         var6 = var4;
      } else {
         if(!(var2 instanceof SimpleSymbol) && !(var2 instanceof CharSequence)) {
            throw new WrongType(this, 2, var2, "string");
         }

         var3 = var2.toString();
         var8 = Compilation.mangleNameIfNeeded(var3);
      }

      if("class".equals(var8)) {
         var4 = "class";
      } else {
         var4 = var8;
         if("length".equals(var8)) {
            var4 = "length";
         }
      }

      return getSlotValue(this.isStatic, var1, var3, var4, var6, var5, Language.getDefaultLanguage());
   }

   public void compile(ApplyExp var1, Compilation var2, Target var3) {
      Expression[] var7 = var1.getArgs();
      Expression var4 = var7[0];
      Expression var9 = var7[1];
      Language var6 = var2.getLanguage();
      Type var14;
      if(this.isStatic) {
         var14 = var6.getTypeFor((Expression)var4);
      } else {
         var14 = var4.getType();
      }

      CodeAttr var5 = var2.getCode();
      if(var14 instanceof ObjectType && var9 instanceof QuoteExp) {
         ObjectType var8 = (ObjectType)var14;
         Object var10 = ((QuoteExp)var9).getValue();
         Target var13;
         if(var10 instanceof Field) {
            Field var16 = (Field)var10;
            boolean var11;
            if((var16.getModifiers() & 8) != 0) {
               var11 = true;
            } else {
               var11 = false;
            }

            Expression var19 = var7[0];
            if(var11) {
               var13 = Target.Ignore;
            } else {
               var13 = Target.pushValue(var8);
            }

            var19.compile(var2, (Target)var13);
            if(var11) {
               if(true) {
                  var5.emitGetStatic(var16);
               }
            } else {
               var5.emitGetField(var16);
            }

            var3.compileFromStack(var2, var6.getLangTypeFor(var16.getType()));
            return;
         }

         if(var10 instanceof Method) {
            Method var15 = (Method)var10;
            var15.getModifiers();
            boolean var12 = var15.getStaticFlag();
            Expression var17 = var7[0];
            if(var12) {
               var13 = Target.Ignore;
            } else {
               var13 = Target.pushValue(var8);
            }

            var17.compile(var2, (Target)var13);
            if(var12) {
               var5.emitInvokeStatic(var15);
            } else {
               var5.emitInvoke(var15);
            }

            var3.compileFromStack(var2, var15.getReturnType());
            return;
         }
      }

      String var18 = ClassMethods.checkName(var9);
      if(var14 instanceof ArrayType && "length".equals(var18) && !this.isStatic) {
         var7[0].compile(var2, (Target)Target.pushValue(var14));
         var5.emitArrayLength();
         var3.compileFromStack(var2, LangPrimType.intType);
      } else {
         ApplyExp.compile(var1, var2, var3);
      }
   }

   public Type getReturnType(Expression[] var1) {
      if(var1.length == 2) {
         Expression var2 = var1[0];
         Expression var4 = var1[1];
         if(var4 instanceof QuoteExp) {
            Object var3 = ((QuoteExp)var4).getValue();
            if(var3 instanceof Field) {
               return ((Field)var3).getType();
            }

            if(var3 instanceof Method) {
               return ((Method)var3).getReturnType();
            }

            if(!this.isStatic && var2.getType() instanceof ArrayType && "length".equals(ClassMethods.checkName(var4, true))) {
               return LangPrimType.intType;
            }
         }
      }

      return Type.pointer_type;
   }

   public Procedure getSetter() {
      return this.setter == null?super.getSetter():this.setter;
   }

   public void set2(Object var1, Object var2, Object var3) {
      SlotSet.apply(this.isStatic, var1, (String)var2, var3);
   }

   public void setN(Object[] var1) {
      int var2 = var1.length;
      if(var2 != 3) {
         throw new WrongArguments(this.getSetter(), var2);
      } else {
         this.set2(var1[0], var1[1], var1[2]);
      }
   }
}
