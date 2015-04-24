package gnu.kawa.reflect;

import gnu.bytecode.ArrayType;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.InlineCalls;
import gnu.expr.Inlineable;
import gnu.expr.Target;
import gnu.kawa.lispexpr.LangPrimType;
import gnu.kawa.reflect.ArrayGet;
import gnu.kawa.reflect.ArrayLength;
import gnu.kawa.reflect.ArrayNew;
import gnu.kawa.reflect.ArraySet;
import gnu.mapping.Procedure;
import gnu.mapping.Values;

public class CompileArrays implements Inlineable {

   public char code;
   Procedure proc;


   public CompileArrays(Procedure var1, char var2) {
      this.proc = var1;
      this.code = var2;
   }

   public static void compileArrayGet(ArrayGet var0, ApplyExp var1, Compilation var2, Target var3) {
      Type var4 = var0.element_type;
      Expression[] var5 = var1.getArgs();
      var5[0].compile(var2, (Type)ArrayType.make((Type)var4));
      var5[1].compile(var2, (Type)Type.int_type);
      var2.getCode().emitArrayLoad(var4);
      var3.compileFromStack(var2, var4);
   }

   public static void compileArrayLength(ArrayLength var0, ApplyExp var1, Compilation var2, Target var3) {
      Type var4 = var0.element_type;
      var1.getArgs()[0].compile(var2, (Type)ArrayType.make((Type)var4));
      var2.getCode().emitArrayLength();
      var3.compileFromStack(var2, LangPrimType.intType);
   }

   public static void compileArrayNew(ArrayNew var0, ApplyExp var1, Compilation var2, Target var3) {
      Type var4 = var0.element_type;
      var1.getArgs()[0].compile(var2, (Type)Type.intType);
      var2.getCode().emitNewArray(var4.getImplementationType());
      var3.compileFromStack(var2, ArrayType.make((Type)var4));
   }

   public static void compileArraySet(ArraySet var0, ApplyExp var1, Compilation var2, Target var3) {
      Type var4 = var0.element_type;
      Expression[] var5 = var1.getArgs();
      var5[0].compile(var2, (Type)ArrayType.make((Type)var4));
      var5[1].compile(var2, (Type)Type.int_type);
      var5[2].compile(var2, (Type)var4);
      var2.getCode().emitArrayStore(var4);
      var2.compileConstant(Values.empty, var3);
   }

   public static CompileArrays getForArrayGet(Object var0) {
      return new CompileArrays((Procedure)var0, 'G');
   }

   public static CompileArrays getForArrayLength(Object var0) {
      return new CompileArrays((Procedure)var0, 'L');
   }

   public static CompileArrays getForArrayNew(Object var0) {
      return new CompileArrays((Procedure)var0, 'N');
   }

   public static CompileArrays getForArraySet(Object var0) {
      return new CompileArrays((Procedure)var0, 'S');
   }

   public static Expression validateArrayGet(ApplyExp var0, InlineCalls var1, Type var2, Procedure var3) {
      var0.visitArgs(var1);
      var0.setType(((ArrayGet)var3).element_type);
      return var0;
   }

   public static Expression validateArrayLength(ApplyExp var0, InlineCalls var1, Type var2, Procedure var3) {
      var0.visitArgs(var1);
      var0.setType(LangPrimType.intType);
      return var0;
   }

   public static Expression validateArrayNew(ApplyExp var0, InlineCalls var1, Type var2, Procedure var3) {
      var0.visitArgs(var1);
      var0.setType(ArrayType.make((Type)((ArrayNew)var3).element_type));
      return var0;
   }

   public static Expression validateArraySet(ApplyExp var0, InlineCalls var1, Type var2, Procedure var3) {
      var0.visitArgs(var1);
      var0.setType(Type.void_type);
      return var0;
   }

   public void compile(ApplyExp var1, Compilation var2, Target var3) {
      switch(this.code) {
      case 71:
         compileArrayGet((ArrayGet)this.proc, var1, var2, var3);
         return;
      case 78:
         compileArrayNew((ArrayNew)this.proc, var1, var2, var3);
         return;
      case 83:
         compileArraySet((ArraySet)this.proc, var1, var2, var3);
         return;
      default:
         compileArrayLength((ArrayLength)this.proc, var1, var2, var3);
      }
   }
}
