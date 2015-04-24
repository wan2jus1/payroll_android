package gnu.kawa.reflect;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.PrimType;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.ConditionalTarget;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.Inlineable;
import gnu.expr.Language;
import gnu.expr.QuoteExp;
import gnu.expr.Target;
import gnu.expr.TypeValue;
import gnu.mapping.Procedure;
import gnu.mapping.Procedure2;

public class InstanceOf extends Procedure2 implements Inlineable {

   static Method instanceMethod;
   static ClassType typeType;
   protected Language language;


   public InstanceOf(Language var1) {
      this.language = var1;
      this.setProperty(Procedure.validateApplyKey, "gnu.kawa.reflect.CompileReflect:validateApplyInstanceOf");
   }

   public InstanceOf(Language var1, String var2) {
      this(var1);
      this.setName(var2);
   }

   public static void emitIsInstance(TypeValue var0, Variable var1, Compilation var2, Target var3) {
      CodeAttr var5 = var2.getCode();
      var0.emitTestIf((Variable)null, (Declaration)null, var2);
      ConditionalTarget var4 = null;
      if(var3 instanceof ConditionalTarget) {
         var4 = (ConditionalTarget)var3;
         var5.emitGoto(var4.ifTrue);
      } else {
         var5.emitPushInt(1);
      }

      var5.emitElse();
      if(var4 != null) {
         var5.emitGoto(var4.ifFalse);
      } else {
         var5.emitPushInt(0);
      }

      var5.emitFi();
      if(var4 == null) {
         var3.compileFromStack(var2, var2.getLanguage().getTypeFor((Class)Boolean.TYPE));
      }

   }

   public Object apply2(Object var1, Object var2) {
      Type var3 = this.language.asType(var2);
      var2 = var3;
      if(var3 instanceof PrimType) {
         var2 = ((PrimType)var3).boxedType();
      }

      return this.language.booleanObject(((Type)var2).isInstance(var1));
   }

   public void compile(ApplyExp var1, Compilation var2, Target var3) {
      Expression[] var5 = var1.getArgs();
      CodeAttr var6 = var2.getCode();
      Type var8 = null;
      Expression var4 = var5[1];
      if(var4 instanceof QuoteExp) {
         label31: {
            Type var9;
            try {
               var9 = this.language.asType(((QuoteExp)var4).getValue());
            } catch (Exception var7) {
               var2.error('w', "unknown type spec: " + null);
               break label31;
            }

            var8 = var9;
         }
      } else {
         var8 = this.language.getTypeFor((Expression)var4);
      }

      if(var8 != null) {
         Object var10 = var8;
         if(var8 instanceof PrimType) {
            var10 = ((PrimType)var8).boxedType();
         }

         var5[0].compile(var2, (Target)Target.pushObject);
         if(var10 instanceof TypeValue) {
            ((TypeValue)var10).emitIsInstance((Variable)null, var2, var3);
            return;
         }

         ((Type)var10).emitIsInstance(var6);
         var2.usedClass((Type)var10);
      } else {
         if(typeType == null) {
            typeType = ClassType.make("gnu.bytecode.Type");
            instanceMethod = typeType.addMethod("isInstance", Compilation.apply1args, Type.boolean_type, 1);
         }

         var5[1].compile(var2, (Type)typeType);
         var5[0].compile(var2, (Target)Target.pushObject);
         var6.emitInvokeVirtual(instanceMethod);
      }

      var3.compileFromStack(var2, this.language.getTypeFor((Class)Boolean.TYPE));
   }

   public Type getReturnType(Expression[] var1) {
      return this.language.getTypeFor((Class)Boolean.TYPE);
   }
}
