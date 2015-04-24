package kawa.standard;

import gnu.bytecode.ClassType;
import gnu.expr.ApplyExp;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.LambdaExp;
import gnu.expr.ModuleExp;
import gnu.expr.PrimProcedure;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.ScopeExp;
import gnu.expr.SetExp;
import gnu.expr.ThisExp;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Symbol;
import kawa.lang.Macro;
import kawa.lang.Syntax;
import kawa.lang.SyntaxForm;
import kawa.lang.Translator;

public class define_syntax extends Syntax {

   public static final define_syntax define_macro = new define_syntax("%define-macro", false);
   public static final define_syntax define_syntax = new define_syntax("%define-syntax", true);
   static PrimProcedure makeHygienic = new PrimProcedure(typeMacro.getDeclaredMethod("make", 3));
   static PrimProcedure makeNonHygienic = new PrimProcedure(typeMacro.getDeclaredMethod("makeNonHygienic", 3));
   static PrimProcedure setCapturedScope = new PrimProcedure(typeMacro.getDeclaredMethod("setCapturedScope", 1));
   static ClassType typeMacro = ClassType.make("kawa.lang.Macro");
   boolean hygienic;


   static {
      makeHygienic.setSideEffectFree();
      makeNonHygienic.setSideEffectFree();
   }

   public define_syntax() {
      this.hygienic = true;
   }

   public define_syntax(Object var1, boolean var2) {
      super(var1);
      this.hygienic = var2;
   }

   public Expression rewriteForm(Pair var1, Translator var2) {
      return var2.syntaxError("define-syntax not in a body");
   }

   public void scanForm(Pair var1, ScopeExp var2, Translator var3) {
      SyntaxForm var4 = null;

      Object var5;
      for(var5 = var1.getCdr(); var5 instanceof SyntaxForm; var5 = var4.getDatum()) {
         var4 = (SyntaxForm)var5;
      }

      Object var6 = var5;
      if(var5 instanceof Pair) {
         Pair var16 = (Pair)var5;
         var5 = var16.getCar();
         var6 = var16.getCdr();
      } else {
         var5 = null;
      }

      SyntaxForm var7;
      for(var7 = var4; var5 instanceof SyntaxForm; var5 = var7.getDatum()) {
         var7 = (SyntaxForm)var5;
      }

      Object var8 = var3.namespaceResolve(var5);
      if(!(var8 instanceof Symbol)) {
         var3.formStack.addElement(var3.syntaxError("missing macro name for " + Translator.safeCar(var1)));
      } else {
         if(var6 == null || Translator.safeCdr(var6) != LList.Empty) {
            var3.formStack.addElement(var3.syntaxError("invalid syntax for " + this.getName()));
            return;
         }

         Declaration var18 = var3.define(var8, var7, var2);
         var18.setType(typeMacro);
         var3.push(var18);
         Macro var9 = var3.currentMacroDefinition;
         Macro var20 = Macro.make(var18);
         var20.setHygienic(this.hygienic);
         var3.currentMacroDefinition = var20;
         Expression var15 = var3.rewrite_car((Pair)var6, var4);
         var3.currentMacroDefinition = var9;
         var20.expander = var15;
         if(var15 instanceof LambdaExp) {
            ((LambdaExp)var15).setFlag(256);
         }

         QuoteExp var17 = new QuoteExp(var8);
         ThisExp var19 = ThisExp.makeGivingContext(var2);
         PrimProcedure var10;
         if(this.hygienic) {
            var10 = makeHygienic;
         } else {
            var10 = makeNonHygienic;
         }

         ApplyExp var12 = new ApplyExp(var10, new Expression[]{var17, var15, var19});
         var18.noteValue(var12);
         var18.setProcedureDecl(true);
         if(var18.context instanceof ModuleExp) {
            SetExp var13 = new SetExp(var18, var12);
            var13.setDefining(true);
            if(var3.getLanguage().hasSeparateFunctionNamespace()) {
               var13.setFuncDef(true);
            }

            var3.formStack.addElement(var13);
            if(var3.immediate) {
               ReferenceExp var14 = new ReferenceExp(var18);
               QuoteExp var11 = new QuoteExp(var2);
               var3.formStack.addElement(new ApplyExp(setCapturedScope, new Expression[]{var14, var11}));
               return;
            }
         }
      }

   }
}
