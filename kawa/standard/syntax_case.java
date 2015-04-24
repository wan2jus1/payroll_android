package kawa.standard;

import gnu.bytecode.ClassType;
import gnu.bytecode.Method;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.LetExp;
import gnu.expr.PrimProcedure;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.lists.Pair;
import gnu.math.IntNum;
import kawa.lang.Syntax;
import kawa.lang.SyntaxForm;
import kawa.lang.SyntaxPattern;
import kawa.lang.Translator;
import kawa.standard.syntax_case_work;

public class syntax_case extends Syntax {

   public static final syntax_case syntax_case = new syntax_case();
   PrimProcedure call_error;


   static {
      syntax_case.setName("syntax-case");
   }

   public static Object error(String var0, Object var1) {
      Translator var3 = (Translator)Compilation.getCurrent();
      if(var3 == null) {
         throw new RuntimeException("no match in syntax-case");
      } else {
         Syntax var2 = var3.getCurrentSyntax();
         if(var2 == null) {
            var0 = "some syntax";
         } else {
            var0 = var2.getName();
         }

         return var3.syntaxError("no matching case while expanding " + var0);
      }
   }

   Expression rewriteClauses(Object param1, syntax_case_work param2, Translator param3) {
      // $FF: Couldn't be decompiled
   }

   public Expression rewriteForm(Pair var1, Translator var2) {
      syntax_case_work var3 = new syntax_case_work();
      Object var7 = var1.getCdr();
      if(var7 instanceof Pair && ((Pair)var7).getCdr() instanceof Pair) {
         Expression[] var9 = new Expression[2];
         LetExp var4 = new LetExp(var9);
         var3.inputExpression = var4.addDeclaration((String)null);
         Declaration var5 = var2.matchArray;
         Declaration var6 = var4.addDeclaration((String)null);
         var6.setType(Compilation.objArrayType);
         var6.setCanRead(true);
         var2.matchArray = var6;
         var3.inputExpression.setCanRead(true);
         var2.push(var4);
         Pair var10 = (Pair)var7;
         var9[0] = var2.rewrite(var10.getCar());
         var3.inputExpression.noteValue(var9[0]);
         var10 = (Pair)var10.getCdr();
         var3.literal_identifiers = SyntaxPattern.getLiteralsList(var10.getCar(), (SyntaxForm)null, var2);
         var4.body = this.rewriteClauses(var10.getCdr(), var3, var2);
         var2.pop(var4);
         Method var11 = ClassType.make("kawa.lang.SyntaxPattern").getDeclaredMethod("allocVars", 2);
         Expression[] var8 = new Expression[]{new QuoteExp(IntNum.make(var3.maxVars)), null};
         if(var5 == null) {
            var8[1] = QuoteExp.nullExp;
         } else {
            var8[1] = new ReferenceExp(var5);
         }

         var9[1] = new ApplyExp(var11, var8);
         var6.noteValue(var9[1]);
         var2.matchArray = var5;
         return var4;
      } else {
         return var2.syntaxError("insufficiant arguments to syntax-case");
      }
   }
}
