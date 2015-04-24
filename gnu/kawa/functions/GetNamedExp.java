package gnu.kawa.functions;

import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.InlineCalls;
import gnu.expr.PrimProcedure;
import gnu.expr.ReferenceExp;
import gnu.kawa.functions.GetNamedPart;
import gnu.mapping.CallContext;
import gnu.mapping.Environment;
import gnu.mapping.Location;
import gnu.mapping.Procedure;
import gnu.mapping.Symbol;

class GetNamedExp extends ApplyExp {

   static final Declaration castDecl = Declaration.getDeclarationFromStatic("gnu.kawa.functions.Convert", "as");
   static final Declaration fieldDecl = Declaration.getDeclarationFromStatic("gnu.kawa.reflect.SlotGet", "field");
   static final Declaration instanceOfDecl = Declaration.getDeclarationFromStatic("kawa.standard.Scheme", "instanceOf");
   static final Declaration invokeDecl = Declaration.getDeclarationFromStatic("gnu.kawa.reflect.Invoke", "invoke");
   static final Declaration invokeStaticDecl = Declaration.getDeclarationFromStatic("gnu.kawa.reflect.Invoke", "invokeStatic");
   static final Declaration makeDecl = Declaration.getDeclarationFromStatic("gnu.kawa.reflect.Invoke", "make");
   static final Declaration staticFieldDecl = Declaration.getDeclarationFromStatic("gnu.kawa.reflect.SlotGet", "staticField");
   public String combinedName;
   char kind;
   PrimProcedure[] methods;


   public GetNamedExp(Expression[] var1) {
      super((Procedure)GetNamedPart.getNamedPart, var1);
   }

   public void apply(CallContext var1) throws Throwable {
      if(this.combinedName != null) {
         Environment var3 = Environment.getCurrent();
         Symbol var4 = var3.getSymbol(this.combinedName);
         String var2 = Location.UNBOUND;
         Object var5 = var3.get(var4, (Object)null, var2);
         if(var5 != var2) {
            var1.writeValue(var5);
            return;
         }
      }

      super.apply(var1);
   }

   protected GetNamedExp setProcedureKind(char var1) {
      this.type = Compilation.typeProcedure;
      this.kind = var1;
      return this;
   }

   public boolean side_effects() {
      return this.kind != 83 && this.kind != 78 && this.kind != 67 && this.kind != 73?(this.kind == 77?this.getArgs()[0].side_effects():true):false;
   }

   public Expression validateApply(ApplyExp var1, InlineCalls var2, Type var3, Declaration var4) {
      Expression[] var8 = this.getArgs();
      Expression var7 = var8[0];
      Expression[] var6 = var1.getArgs();
      Declaration var5;
      Expression[] var9;
      switch(this.kind) {
      case 67:
         var5 = castDecl;
         var9 = new Expression[var6.length + 1];
         System.arraycopy(var6, 1, var9, 2, var6.length - 1);
         var9[0] = var7;
         var9[1] = var6[0];
         break;
      case 73:
         var5 = instanceOfDecl;
         var9 = new Expression[var6.length + 1];
         System.arraycopy(var6, 1, var9, 2, var6.length - 1);
         var9[0] = var6[0];
         var9[1] = var7;
         break;
      case 77:
         var5 = invokeDecl;
         var9 = new Expression[var6.length + 2];
         var9[0] = var8[0];
         var9[1] = var8[1];
         System.arraycopy(var6, 0, var9, 2, var6.length);
         break;
      case 78:
         var5 = makeDecl;
         var9 = new Expression[var6.length + 1];
         System.arraycopy(var6, 0, var9, 1, var6.length);
         var9[0] = var7;
         break;
      case 83:
         var5 = invokeStaticDecl;
         var9 = new Expression[var6.length + 2];
         var9[0] = var7;
         var9[1] = var8[1];
         System.arraycopy(var6, 0, var9, 2, var6.length);
         break;
      default:
         return var1;
      }

      ApplyExp var10 = new ApplyExp(new ReferenceExp(var5), var9);
      var10.setLine(var1);
      return var2.visit(var10, (Type)var3);
   }
}
