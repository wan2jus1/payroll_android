package gnu.expr;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Field;
import gnu.bytecode.Method;
import gnu.expr.ClassExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.Initializer;
import gnu.expr.LambdaExp;
import gnu.expr.ModuleExp;
import gnu.expr.ModuleMethod;
import gnu.expr.Target;
import gnu.mapping.Environment;
import gnu.mapping.PropertySet;
import gnu.mapping.Symbol;

public class ProcInitializer extends Initializer {

   LambdaExp proc;


   public ProcInitializer(LambdaExp var1, Compilation var2, Field var3) {
      this.field = var3;
      this.proc = var1;
      Object var4;
      if(var3.getStaticFlag()) {
         var4 = var2.getModule();
      } else {
         var4 = var1.getOwningLambda();
      }

      if(var4 instanceof ModuleExp && var2.isStatic()) {
         this.next = var2.clinitChain;
         var2.clinitChain = this;
      } else {
         this.next = ((LambdaExp)var4).initChain;
         ((LambdaExp)var4).initChain = this;
      }
   }

   public static void emitLoadModuleMethod(LambdaExp var0, Compilation var1) {
      Declaration var5 = var0.nameDecl;
      Object var2;
      if(var5 == null) {
         var2 = var0.getName();
      } else {
         var2 = var5.getSymbol();
      }

      CodeAttr var4 = null;
      ModuleMethod var3 = var4;
      if(var1.immediate) {
         var3 = var4;
         if(var2 != null) {
            var3 = var4;
            if(var5 != null) {
               Environment var9 = Environment.getCurrent();
               Symbol var8;
               if(var2 instanceof Symbol) {
                  var8 = (Symbol)var2;
               } else {
                  var8 = Symbol.make("", var2.toString().intern());
               }

               Object var12 = var9.get(var8, var1.getLanguage().getEnvPropertyFor(var0.nameDecl), (Object)null);
               var3 = var4;
               if(var12 instanceof ModuleMethod) {
                  var3 = (ModuleMethod)var12;
               }
            }
         }
      }

      var4 = var1.getCode();
      ClassType var10 = Compilation.typeModuleMethod;
      String var11;
      if(var3 == null) {
         var4.emitNew(var10);
         var4.emitDup(1);
         var11 = "<init>";
      } else {
         var1.compileConstant(var3, Target.pushValue(var10));
         var11 = "init";
      }

      Method var13 = var10.getDeclaredMethod(var11, 4);
      Object var14;
      if(var0.getNeedsClosureEnv()) {
         var14 = var0.getOwningLambda();
      } else {
         var14 = var1.getModule();
      }

      if(var14 instanceof ClassExp && ((LambdaExp)var14).staticLinkField != null) {
         var4.emitLoad(var4.getCurrentScope().getVariable(1));
      } else if(var14 instanceof ModuleExp && (var1.moduleClass != var1.mainClass || var1.method.getStaticFlag())) {
         if(var1.moduleInstanceVar == null) {
            var1.moduleInstanceVar = var4.locals.current_scope.addVariable(var4, var1.moduleClass, "$instance");
            if(var1.moduleClass != var1.mainClass && !var1.isStatic()) {
               var4.emitNew(var1.moduleClass);
               var4.emitDup(var1.moduleClass);
               var4.emitInvokeSpecial(var1.moduleClass.constructor);
               var1.moduleInstanceMainField = var1.moduleClass.addField("$main", var1.mainClass, 0);
               var4.emitDup(var1.moduleClass);
               var4.emitPushThis();
               var4.emitPutField(var1.moduleInstanceMainField);
            } else {
               var4.emitGetStatic(var1.moduleInstanceMainField);
            }

            var4.emitStore(var1.moduleInstanceVar);
         }

         var4.emitLoad(var1.moduleInstanceVar);
      } else {
         var4.emitPushThis();
      }

      var4.emitPushInt(var0.getSelectorValue(var1));
      var1.compileConstant(var2, Target.pushObject);
      int var7 = var0.min_args;
      int var6;
      if(var0.keywords == null) {
         var6 = var0.max_args;
      } else {
         var6 = -1;
      }

      var4.emitPushInt(var6 << 12 | var7);
      var4.emitInvoke(var13);
      if(var0.properties != null) {
         var7 = var0.properties.length;

         for(var6 = 0; var6 < var7; var6 += 2) {
            var14 = var0.properties[var6];
            if(var14 != null && var14 != PropertySet.nameKey) {
               var2 = var0.properties[var6 + 1];
               var4.emitDup(1);
               var1.compileConstant(var14);
               Target var15 = Target.pushObject;
               if(var2 instanceof Expression) {
                  ((Expression)var2).compile(var1, (Target)var15);
               } else {
                  var1.compileConstant(var2, var15);
               }

               var4.emitInvokeVirtual(ClassType.make("gnu.mapping.PropertySet").getDeclaredMethod("setProperty", 2));
            }
         }
      }

   }

   public void emit(Compilation var1) {
      CodeAttr var2 = var1.getCode();
      if(!this.field.getStaticFlag()) {
         var2.emitPushThis();
      }

      emitLoadModuleMethod(this.proc, var1);
      if(this.field.getStaticFlag()) {
         var2.emitPutStatic(this.field);
      } else {
         var2.emitPutField(this.field);
      }
   }

   public void reportError(String var1, Compilation var2) {
      String var3 = var2.getFileName();
      int var5 = var2.getLineNumber();
      int var6 = var2.getColumnNumber();
      var2.setLocation(this.proc);
      String var4 = this.proc.getName();
      StringBuffer var7 = new StringBuffer(var1);
      if(var4 == null) {
         var7.append("unnamed procedure");
      } else {
         var7.append("procedure ");
         var7.append(var4);
      }

      var2.error('e', var7.toString());
      var2.setLine(var3, var5, var6);
   }
}
