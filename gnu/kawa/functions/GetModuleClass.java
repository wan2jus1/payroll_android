package gnu.kawa.functions;

import gnu.bytecode.ClassType;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.BindingInitializer;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.Inlineable;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.Target;
import gnu.mapping.Namespace;
import gnu.mapping.ProcedureN;
import gnu.mapping.Symbol;
import gnu.text.Path;
import gnu.text.URLPath;

public class GetModuleClass extends ProcedureN implements Inlineable {

   private static Symbol CLASS_RESOURCE_NAME = Namespace.getDefaultSymbol("$class_resource_URL$");
   public static final GetModuleClass getModuleClass = new GetModuleClass();
   public static final GetModuleClass getModuleUri = new GetModuleClass();
   public static final GetModuleClass getModuleUriDummy = new GetModuleClass();
   static final Method maker = typeURLPath.getDeclaredMethod("classResourcePath", 1);
   static final ClassType typeURLPath = ClassType.make("gnu.text.URLPath");


   public static Expression getModuleClassURI(Compilation var0) {
      Declaration var2 = var0.mainLambda.lookup(CLASS_RESOURCE_NAME);
      Declaration var1 = var2;
      if(var2 == null) {
         Declaration var3 = new Declaration(CLASS_RESOURCE_NAME, typeURLPath);
         var3.setFlag(536889344L);
         Object var6;
         if(var0.immediate) {
            Path var5 = var0.minfo.getSourceAbsPath();
            Path var4 = var5;
            if(var5 == null) {
               var4 = Path.currentPath();
            }

            Object var8 = var4;
            if(!(var4 instanceof URLPath)) {
               var8 = URLPath.valueOf(var4.toURL());
            }

            var6 = QuoteExp.getInstance(var8);
         } else {
            ApplyExp var7 = new ApplyExp(getModuleClass, Expression.noExpressions);
            var6 = new ApplyExp(maker, new Expression[]{var7});
         }

         var3.setValue((Expression)var6);
         var0.mainLambda.add((Declaration)null, var3);
         var1 = var3;
      }

      ReferenceExp var9 = new ReferenceExp(var1);
      return (Expression)(var0.immediate?var9:new ApplyExp(getModuleUriDummy, new Expression[]{var9}));
   }

   public Object applyN(Object[] var1) {
      throw new Error("get-module-class must be inlined");
   }

   public void compile(ApplyExp var1, Compilation var2, Target var3) {
      if(this == getModuleUriDummy) {
         ReferenceExp var4 = (ReferenceExp)var1.getArgs()[0];
         var4.compile(var2, var3);
         Declaration var5 = var4.getBinding();
         Expression var6 = var5.getValue();
         if(var6 != null) {
            BindingInitializer.create(var5, var6, var2);
            var5.setValue((Expression)null);
         }

      } else {
         var2.loadClassRef(var2.mainClass);
         if(this == getModuleUri) {
            var2.getCode().emitInvoke(maker);
         }

         var3.compileFromStack(var2, var1.getType());
      }
   }

   public Type getReturnType(Expression[] var1) {
      return this == getModuleClass?Type.javalangClassType:typeURLPath;
   }

   public int numArgs() {
      return this == getModuleUriDummy?4097:0;
   }
}
