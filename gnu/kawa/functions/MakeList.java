package gnu.kawa.functions;

import gnu.bytecode.CodeAttr;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.Inlineable;
import gnu.expr.QuoteExp;
import gnu.expr.Target;
import gnu.kawa.lispexpr.LangObjType;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.ProcedureN;

public class MakeList extends ProcedureN implements Inlineable {

   public static final MakeList list = new MakeList();


   static {
      list.setName("list");
   }

   public static void compile(Expression[] var0, int var1, Compilation var2) {
      int var5 = var0.length - var1;
      CodeAttr var3 = var2.getCode();
      if(var5 == 0) {
         (new QuoteExp(LList.Empty)).compile(var2, Target.pushObject);
      } else {
         int var4;
         if(var5 <= 4) {
            for(var4 = 0; var4 < var5; ++var4) {
               var0[var1 + var4].compile(var2, (Target)Target.pushObject);
            }

            var3.emitInvokeStatic(Compilation.scmListType.getDeclaredMethod("list" + var5, (Type[])null));
         } else {
            var0[var1].compile(var2, (Target)Target.pushObject);
            var3.emitInvokeStatic(Compilation.scmListType.getDeclaredMethod("list1", (Type[])null));
            var3.emitDup(1);
            ++var1;
            var4 = var5 - 1;

            while(true) {
               var5 = var4;
               int var6 = var1;
               if(var4 < 4) {
                  while(var5 > 0) {
                     var0[var6].compile(var2, (Target)Target.pushObject);
                     --var5;
                     ++var6;
                     var3.emitInvokeStatic(Compilation.scmListType.getDeclaredMethod("chain1", (Type[])null));
                  }

                  var3.emitPop(1);
                  return;
               }

               var0[var1].compile(var2, (Target)Target.pushObject);
               var0[var1 + 1].compile(var2, (Target)Target.pushObject);
               var0[var1 + 2].compile(var2, (Target)Target.pushObject);
               var0[var1 + 3].compile(var2, (Target)Target.pushObject);
               var4 -= 4;
               var1 += 4;
               var3.emitInvokeStatic(Compilation.scmListType.getDeclaredMethod("chain4", (Type[])null));
            }
         }
      }
   }

   public static Object list$V(Object[] var0) {
      Object var1 = LList.Empty;
      int var2 = var0.length;

      while(true) {
         --var2;
         if(var2 < 0) {
            return var1;
         }

         var1 = new Pair(var0[var2], var1);
      }
   }

   public Object applyN(Object[] var1) {
      return list$V(var1);
   }

   public void compile(ApplyExp var1, Compilation var2, Target var3) {
      compile(var1.getArgs(), 0, var2);
      var3.compileFromStack(var2, var1.getType());
   }

   public Type getReturnType(Expression[] var1) {
      return (Type)(var1.length > 0?Compilation.typePair:LangObjType.listType);
   }
}
