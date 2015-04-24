package gnu.expr;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.PrimType;
import gnu.bytecode.Scope;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.IgnoreTarget;
import gnu.expr.StackTarget;
import gnu.expr.Target;
import gnu.kawa.reflect.OccurrenceType;

public class ConsumerTarget extends Target {

   Variable consumer;
   boolean isContextTarget;


   public ConsumerTarget(Variable var1) {
      this.consumer = var1;
   }

   public static void compileUsingConsumer(Expression var0, Compilation var1, Target var2) {
      if(!(var2 instanceof ConsumerTarget) && !(var2 instanceof IgnoreTarget)) {
         ClassType var3 = Compilation.typeValues;
         compileUsingConsumer(var0, var1, var2, var3.getDeclaredMethod("make", 0), var3.getDeclaredMethod("canonicalize", 0));
      } else {
         var0.compile(var1, (Target)var2);
      }
   }

   public static void compileUsingConsumer(Expression var0, Compilation var1, Target var2, Method var3, Method var4) {
      CodeAttr var7 = var1.getCode();
      Scope var8 = var7.pushScope();
      Object var9;
      if(var3.getName() == "<init>") {
         ClassType var6 = var3.getDeclaringClass();
         var7.emitNew(var6);
         var7.emitDup(var6);
         var7.emitInvoke(var3);
         var9 = var6;
      } else {
         Type var5 = var3.getReturnType();
         var7.emitInvokeStatic(var3);
         var9 = var5;
      }

      Variable var10 = var8.addVariable(var7, (Type)var9, (String)null);
      ConsumerTarget var11 = new ConsumerTarget(var10);
      var7.emitStore(var10);
      var0.compile(var1, (Target)var11);
      var7.emitLoad(var10);
      if(var4 != null) {
         var7.emitInvoke(var4);
      }

      var7.popScope();
      if(var4 != null) {
         var9 = var4.getReturnType();
      }

      var2.compileFromStack(var1, (Type)var9);
   }

   public static Target makeContextTarget(Compilation var0) {
      CodeAttr var1 = var0.getCode();
      var0.loadCallContext();
      var1.emitGetField(Compilation.typeCallContext.getDeclaredField("consumer"));
      Variable var2 = var1.getCurrentScope().addVariable(var1, Compilation.typeConsumer, "$result");
      var1.emitStore(var2);
      ConsumerTarget var3 = new ConsumerTarget(var2);
      var3.isContextTarget = true;
      return var3;
   }

   public void compileFromStack(Compilation var1, Type var2) {
      this.compileFromStack(var1, var2, -1);
   }

   void compileFromStack(Compilation var1, Type var2, int var3) {
      CodeAttr var6 = var1.getCode();
      Variable var4 = null;
      Object var5 = null;
      Object var10 = null;
      boolean var8 = false;
      Type var7 = var2.getImplementationType();
      char var9;
      String var11;
      if(var7 instanceof PrimType) {
         var9 = var7.getSignature().charAt(0);
         switch(var9) {
         case 66:
         case 73:
         case 83:
            var11 = "writeInt";
            var10 = Type.intType;
            break;
         case 67:
            var11 = "append";
            var10 = Type.charType;
            break;
         case 68:
            var11 = "writeDouble";
            var10 = Type.doubleType;
            var8 = true;
            break;
         case 70:
            var11 = "writeFloat";
            var10 = Type.floatType;
            break;
         case 74:
            var11 = "writeLong";
            var10 = Type.longType;
            var8 = true;
            break;
         case 86:
            return;
         case 90:
            var11 = "writeBoolean";
            var10 = Type.booleanType;
            break;
         default:
            var11 = var4;
         }
      } else {
         var9 = 0;
         if(var3 != 1 && !OccurrenceType.itemCountIsOne(var7)) {
            Method var12 = Compilation.typeValues.getDeclaredMethod("writeValues", 2);
            var6.emitLoad(this.consumer);
            if(var3 == 0) {
               var6.emitSwap();
            }

            var6.emitInvokeStatic(var12);
            return;
         }

         var11 = "writeObject";
         var10 = Type.pointer_type;
      }

      if(var3 < 0) {
         if(var8) {
            var6.pushScope();
            var4 = var6.addLocal(var7);
            var6.emitStore(var4);
            var6.emitLoad(this.consumer);
            var6.emitLoad(var4);
            var6.popScope();
         } else {
            var6.emitLoad(this.consumer);
            var6.emitSwap();
         }
      }

      Method var13 = (Method)var5;
      if(true) {
         var13 = (Method)var5;
         if(var11 != null) {
            var13 = Compilation.typeConsumer.getDeclaredMethod(var11, new Type[]{(Type)var10});
         }
      }

      if(var13 != null) {
         var6.emitInvokeInterface(var13);
      }

      if(var9 == 67) {
         var6.emitPop(1);
      }

   }

   public boolean compileWrite(Expression var1, Compilation var2) {
      Type var3 = var1.getType().getImplementationType();
      if((!(var3 instanceof PrimType) || var3.isVoid()) && !OccurrenceType.itemCountIsOne(var3)) {
         return false;
      } else {
         var2.getCode().emitLoad(this.consumer);
         var1.compile(var2, (Target)StackTarget.getInstance(var3));
         this.compileFromStack(var2, var3, 1);
         return true;
      }
   }

   public Variable getConsumerVariable() {
      return this.consumer;
   }

   public Type getType() {
      return Compilation.scmSequenceType;
   }

   public final boolean isContextTarget() {
      return this.isContextTarget;
   }
}
