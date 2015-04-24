package gnu.kawa.functions;

import gnu.bytecode.CodeAttr;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.Inlineable;
import gnu.expr.Language;
import gnu.expr.StackTarget;
import gnu.expr.Target;
import gnu.kawa.functions.Arithmetic;
import gnu.kawa.lispexpr.LangObjType;
import gnu.mapping.Procedure;
import gnu.mapping.Procedure1;
import gnu.math.IntNum;

public class NumberPredicate extends Procedure1 implements Inlineable {

   public static final int EVEN = 2;
   public static final int ODD = 1;
   Language language;
   final int op;


   public NumberPredicate(Language var1, String var2, int var3) {
      super(var2);
      this.language = var1;
      this.op = var3;
      this.setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileArith:validateApplyNumberPredicate");
   }

   public Object apply1(Object var1) {
      IntNum var3 = LangObjType.coerceIntNum(var1);
      boolean var2;
      switch(this.op) {
      case 1:
         var2 = var3.isOdd();
         break;
      case 2:
         if(!var3.isOdd()) {
            var2 = true;
         } else {
            var2 = false;
         }
         break;
      default:
         throw new Error();
      }

      return this.getLanguage().booleanObject(var2);
   }

   public void compile(ApplyExp var1, Compilation var2, Target var3) {
      Expression[] var4 = var1.getArgs();
      if(var4.length == 1 && (this.op == 1 || this.op == 2)) {
         Expression var7 = var4[0];
         if(Arithmetic.classifyType(var7.getType()) <= 4) {
            Target var6 = StackTarget.getInstance(Type.intType);
            CodeAttr var5 = var2.getCode();
            if(this.op == 2) {
               var5.emitPushInt(1);
            }

            var7.compile(var2, (Target)var6);
            var5.emitPushInt(1);
            var5.emitAnd();
            if(this.op == 2) {
               var5.emitSub(Type.intType);
            }

            var3.compileFromStack(var2, Type.booleanType);
            return;
         }
      }

      ApplyExp.compile(var1, var2, var3);
   }

   protected final Language getLanguage() {
      return this.language;
   }

   public int numArgs() {
      return 4097;
   }
}
