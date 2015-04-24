package gnu.expr;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Type;
import gnu.expr.ClassExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.ExpVisitor;
import gnu.expr.IgnoreTarget;
import gnu.expr.ModuleExp;
import gnu.expr.ReferenceExp;
import gnu.expr.ScopeExp;
import gnu.expr.Target;
import gnu.mapping.CallContext;

public class ThisExp extends ReferenceExp {

   static int EVAL_TO_CONTEXT = 2;
   public static final String THIS_NAME = new String("$this$");
   ScopeExp context;


   public ThisExp() {
      super((Object)THIS_NAME);
   }

   public ThisExp(ClassType var1) {
      this((Declaration)(new Declaration(THIS_NAME, var1)));
   }

   public ThisExp(Declaration var1) {
      super(THIS_NAME, var1);
   }

   public ThisExp(ScopeExp var1) {
      super((Object)THIS_NAME);
      this.context = var1;
   }

   public static ThisExp makeGivingContext(ScopeExp var0) {
      ThisExp var1 = new ThisExp(var0);
      var1.flags |= EVAL_TO_CONTEXT;
      return var1;
   }

   public void apply(CallContext var1) throws Throwable {
      if(this.isForContext()) {
         var1.writeValue(this.context);
      } else {
         super.apply(var1);
      }
   }

   public void compile(Compilation var1, Target var2) {
      if(!(var2 instanceof IgnoreTarget)) {
         if(this.isForContext()) {
            CodeAttr var3 = var1.getCode();
            if(var1.method.getStaticFlag()) {
               var3.emitGetStatic(var1.moduleInstanceMainField);
            } else {
               var3.emitPushThis();
            }

            var2.compileFromStack(var1, this.getType());
         } else {
            super.compile(var1, var2);
         }
      }
   }

   public ScopeExp getContextScope() {
      return this.context;
   }

   public final Type getType() {
      return (Type)(this.binding != null?this.binding.getType():(!(this.context instanceof ClassExp) && !(this.context instanceof ModuleExp)?Type.pointer_type:this.context.getType()));
   }

   public final boolean isForContext() {
      return (this.flags & EVAL_TO_CONTEXT) != 0;
   }

   protected Object visit(ExpVisitor var1, Object var2) {
      return var1.visitThisExp(this, var2);
   }
}
