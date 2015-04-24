package gnu.expr;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Label;
import gnu.bytecode.Method;
import gnu.bytecode.PrimType;
import gnu.bytecode.Scope;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.LambdaExp;
import gnu.expr.StackTarget;
import gnu.expr.Target;

public class CheckedTarget extends StackTarget {

   static Method initWrongTypeProcMethod;
   static Method initWrongTypeStringMethod;
   static ClassType typeClassCastException;
   static ClassType typeWrongType;
   int argno;
   LambdaExp proc;
   String procname;


   public CheckedTarget(Type var1) {
      super(var1);
      this.argno = -4;
   }

   public CheckedTarget(Type var1, LambdaExp var2, int var3) {
      super(var1);
      this.proc = var2;
      this.procname = var2.getName();
      this.argno = var3;
   }

   public CheckedTarget(Type var1, String var2, int var3) {
      super(var1);
      this.procname = var2;
      this.argno = var3;
   }

   public static void emitCheckedCoerce(Compilation var0, LambdaExp var1, int var2, Type var3) {
      emitCheckedCoerce(var0, var1, var1.getName(), var2, var3, (Variable)null);
   }

   public static void emitCheckedCoerce(Compilation var0, LambdaExp var1, int var2, Type var3, Variable var4) {
      emitCheckedCoerce(var0, var1, var1.getName(), var2, var3, var4);
   }

   static void emitCheckedCoerce(Compilation var0, LambdaExp var1, String var2, int var3, Type var4, Variable var5) {
      CodeAttr var7 = var0.getCode();
      boolean var13 = var7.isInTry();
      initWrongType();
      Label var8 = new Label(var7);
      Scope var6;
      if(var5 == null && var4 != Type.toStringType) {
         var6 = var7.pushScope();
         var5 = var7.addLocal(Type.objectType);
         var7.emitDup(1);
         var7.emitStore(var5);
      } else {
         var6 = null;
      }

      int var10 = var7.getPC();
      var8.define(var7);
      emitCoerceFromObject(var4, var0);
      if(var7.getPC() != var10 && var4 != Type.toStringType) {
         Label var9 = new Label(var7);
         var9.define(var7);
         Label var16 = new Label(var7);
         var16.setTypes((CodeAttr)var7);
         if(var13) {
            var7.emitGoto(var16);
         }

         var10 = 0;
         var7.setUnreachable();
         if(!var13) {
            var10 = var7.beginFragment(var16);
         }

         var7.addHandler(var8, var9, typeClassCastException);
         boolean var12 = false;
         boolean var11 = var12;
         if(var1 != null) {
            var11 = var12;
            if(var1.isClassGenerated()) {
               var11 = var12;
               if(!var0.method.getStaticFlag()) {
                  var11 = var12;
                  if(var0.method.getDeclaringClass() == var1.getCompiledClassType(var0)) {
                     var11 = true;
                  }
               }
            }
         }

         int var17 = var0.getLineNumber();
         if(var17 > 0) {
            var7.putLineNumber(var17);
         }

         var7.emitNew(typeWrongType);
         var7.emitDupX();
         var7.emitSwap();
         if(var11) {
            var7.emitPushThis();
         } else {
            String var14 = var2;
            if(var2 == null) {
               var14 = var2;
               if(var3 != -4) {
                  var14 = "lambda";
               }
            }

            var7.emitPushString(var14);
         }

         var7.emitPushInt(var3);
         var7.emitLoad(var5);
         Method var15;
         if(var11) {
            var15 = initWrongTypeProcMethod;
         } else {
            var15 = initWrongTypeStringMethod;
         }

         var7.emitInvokeSpecial(var15);
         if(var6 != null) {
            var7.popScope();
         }

         var7.emitThrow();
         if(var13) {
            var16.define(var7);
         } else {
            var7.endFragment(var10);
         }
      } else {
         if(var6 != null) {
            var7.popScope();
         }

      }
   }

   public static void emitCheckedCoerce(Compilation var0, String var1, int var2, Type var3) {
      emitCheckedCoerce(var0, (LambdaExp)null, var1, var2, var3, (Variable)null);
   }

   public static Target getInstance(Type var0) {
      return (Target)(var0 == Type.objectType?Target.pushObject:new CheckedTarget(var0));
   }

   public static Target getInstance(Type var0, LambdaExp var1, int var2) {
      return (Target)(var0 == Type.objectType?Target.pushObject:new CheckedTarget(var0, var1, var2));
   }

   public static Target getInstance(Type var0, String var1, int var2) {
      return (Target)(var0 == Type.objectType?Target.pushObject:new CheckedTarget(var0, var1, var2));
   }

   public static Target getInstance(Declaration var0) {
      return getInstance(var0.getType(), (String)var0.getName(), -2);
   }

   private static void initWrongType() {
      if(typeClassCastException == null) {
         typeClassCastException = ClassType.make("java.lang.ClassCastException");
      }

      if(typeWrongType == null) {
         typeWrongType = ClassType.make("gnu.mapping.WrongType");
         ClassType var0 = typeClassCastException;
         ClassType var1 = Compilation.javaStringType;
         PrimType var2 = Type.intType;
         ClassType var3 = Type.objectType;
         ClassType var4 = typeWrongType;
         PrimType var5 = Type.voidType;
         initWrongTypeStringMethod = var4.addMethod("<init>", 1, new Type[]{var0, var1, var2, var3}, var5);
         var0 = typeClassCastException;
         var1 = Compilation.typeProcedure;
         var2 = Type.intType;
         var3 = Type.objectType;
         var4 = typeWrongType;
         var5 = Type.voidType;
         initWrongTypeProcMethod = var4.addMethod("<init>", 1, new Type[]{var0, var1, var2, var3}, var5);
      }

   }

   public void compileFromStack(Compilation var1, Type var2) {
      if(!this.compileFromStack0(var1, var2)) {
         emitCheckedCoerce(var1, this.proc, this.procname, this.argno, this.type, (Variable)null);
      }

   }
}
