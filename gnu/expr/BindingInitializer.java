package gnu.expr;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.Initializer;
import gnu.expr.QuoteExp;
import gnu.expr.StackTarget;
import gnu.expr.Target;
import gnu.mapping.EnvironmentKey;
import gnu.mapping.Namespace;
import gnu.mapping.Symbol;
import gnu.text.SourceLocator;
import gnu.text.SourceMessages;

public class BindingInitializer extends Initializer {

   static final ClassType typeThreadLocation = ClassType.make("gnu.mapping.ThreadLocation");
   Declaration decl;
   Expression value;


   public BindingInitializer(Declaration var1, Expression var2) {
      this.decl = var1;
      this.value = var2;
      this.field = var1.field;
   }

   public static void create(Declaration var0, Expression var1, Compilation var2) {
      BindingInitializer var3;
      label13: {
         var3 = new BindingInitializer(var0, var1);
         if(var0.field != null) {
            if(var0.field.getStaticFlag()) {
               break label13;
            }
         } else if(var0.isStatic()) {
            break label13;
         }

         var3.next = var2.mainLambda.initChain;
         var2.mainLambda.initChain = var3;
         return;
      }

      var3.next = var2.clinitChain;
      var2.clinitChain = var3;
   }

   public static Method makeLocationMethod(Object var0) {
      Type[] var1 = new Type[1];
      if(var0 instanceof Symbol) {
         var1[0] = Compilation.typeSymbol;
      } else {
         var1[0] = Type.javalangStringType;
      }

      return Compilation.typeLocation.getDeclaredMethod("make", var1);
   }

   public void emit(Compilation var1) {
      if(this.decl.ignorable()) {
         if(this.value != null) {
            this.value.compile(var1, (Target)Target.Ignore);
         }
      } else {
         CodeAttr var5 = var1.getCode();
         Object var2;
         if(this.value instanceof QuoteExp) {
            var2 = ((QuoteExp)this.value).getValue();
            if(var2 != null && !(var2 instanceof String) && var1.litTable.findLiteral(var2).field == this.field) {
               return;
            }
         }

         int var8 = this.decl.getLineNumber();
         SourceMessages var6 = var1.getMessages();
         SourceLocator var7 = var6.swapSourceLocator(this.decl);
         if(var8 > 0) {
            var5.putLineNumber(this.decl.getFileName(), var8);
         }

         if(this.field != null && !this.field.getStaticFlag()) {
            var5.emitPushThis();
         }

         if(this.value == null) {
            if(var1.getLanguage().hasSeparateFunctionNamespace() && this.decl.isProcedureDecl()) {
               var2 = EnvironmentKey.FUNCTION;
            } else {
               var2 = null;
            }

            Object var4 = this.decl.getSymbol();
            if(this.decl.getFlag(268500992L)) {
               Object var3 = var4;
               if(var4 instanceof String) {
                  var3 = Namespace.EmptyNamespace.getSymbol((String)var4);
               }

               var1.compileConstant(var3, Target.pushObject);
               if(var2 == null) {
                  var5.emitPushNull();
               } else {
                  var1.compileConstant(var2, Target.pushObject);
               }

               var5.emitInvokeStatic(typeThreadLocation.getDeclaredMethod("getInstance", 2));
            } else if(this.decl.isFluid()) {
               ClassType var10;
               if(var4 instanceof Symbol) {
                  var10 = Compilation.typeSymbol;
               } else {
                  var10 = Type.toStringType;
               }

               var1.compileConstant(var4, Target.pushObject);
               var5.emitInvokeStatic(typeThreadLocation.getDeclaredMethod("makeAnonymous", new Type[]{var10}));
            } else {
               var1.compileConstant(var4, Target.pushObject);
               var5.emitInvokeStatic(makeLocationMethod(var4));
            }
         } else {
            Type var11;
            if(this.field == null) {
               var11 = this.decl.getType();
            } else {
               var11 = this.field.getType();
            }

            this.value.compileWithPosition(var1, StackTarget.getInstance(var11));
         }

         if(this.field == null) {
            Variable var12 = this.decl.getVariable();
            Variable var9 = var12;
            if(var12 == null) {
               var9 = this.decl.allocateVariable(var5);
            }

            var5.emitStore(var9);
         } else if(this.field.getStaticFlag()) {
            var5.emitPutStatic(this.field);
         } else {
            var5.emitPutField(this.field);
         }

         var6.swapSourceLocator(var7);
         return;
      }

   }
}
