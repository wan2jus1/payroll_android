package kawa.standard;

import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.LambdaExp;
import gnu.expr.LangExp;
import gnu.expr.ModuleExp;
import gnu.expr.ScopeExp;
import gnu.expr.SetExp;
import gnu.lists.Pair;
import gnu.mapping.Symbol;
import kawa.lang.Lambda;
import kawa.lang.Syntax;
import kawa.lang.SyntaxForm;
import kawa.lang.TemplateScope;
import kawa.lang.Translator;
import kawa.standard.SchemeCompilation;

public class define extends Syntax {

   public static final define defineRaw = new define(SchemeCompilation.lambda);
   Lambda lambda;


   public define(Lambda var1) {
      this.lambda = var1;
   }

   String getName(int var1) {
      return (var1 & 4) != 0?"define-private":((var1 & 8) != 0?"define-constant":"define");
   }

   public Expression rewriteForm(Pair var1, Translator var2) {
      Pair var4 = (Pair)var1.getCdr();
      var1 = (Pair)var4.getCdr();
      Pair var3 = (Pair)((Pair)var1.getCdr()).getCdr();
      Object var11 = Translator.stripSyntax(var4.getCar());
      int var6 = ((Number)Translator.stripSyntax(var1.getCar())).intValue();
      boolean var5;
      if((var6 & 4) != 0) {
         var5 = true;
      } else {
         var5 = false;
      }

      Object var7;
      if(!(var11 instanceof Declaration)) {
         var7 = var2.syntaxError(this.getName(var6) + " is only allowed in a <body>");
      } else {
         Declaration var13 = (Declaration)var11;
         Expression var8;
         if(var13.getFlag(8192L)) {
            var8 = var13.getTypeExp();
            if(var8 instanceof LangExp) {
               var13.setType(var2.exp2Type((Pair)((LangExp)var8).getLangValue()));
            }
         }

         Object var10;
         if((var6 & 2) != 0) {
            LambdaExp var9 = (LambdaExp)var13.getValue();
            var10 = var3.getCdr();
            this.lambda.rewriteBody(var9, var10, var2);
            var10 = var9;
            if(!Compilation.inlineOk) {
               var13.noteValue((Expression)null);
               var10 = var9;
            }
         } else {
            var8 = var2.rewrite(var3.getCar());
            Expression var12;
            if(var13.context instanceof ModuleExp && !var5 && var13.getCanWrite()) {
               var12 = null;
            } else {
               var12 = var8;
            }

            var13.noteValue(var12);
            var10 = var8;
         }

         SetExp var14 = new SetExp(var13, (Expression)var10);
         var14.setDefining(true);
         var7 = var14;
         if(var5) {
            var7 = var14;
            if(!(var2.currentScope() instanceof ModuleExp)) {
               var2.error('w', "define-private not at top level " + var2.currentScope());
               return var14;
            }
         }
      }

      return (Expression)var7;
   }

   public void scanForm(Pair var1, ScopeExp var2, Translator var3) {
      Pair var8 = (Pair)var1.getCdr();
      Pair var5 = (Pair)var8.getCdr();
      Pair var9 = (Pair)var5.getCdr();
      Pair var10 = (Pair)var9.getCdr();
      SyntaxForm var4 = null;

      Object var6;
      for(var6 = var8.getCar(); var6 instanceof SyntaxForm; var6 = var4.getDatum()) {
         var4 = (SyntaxForm)var6;
      }

      int var15 = ((Number)Translator.stripSyntax(var5.getCar())).intValue();
      boolean var13;
      if((var15 & 4) != 0) {
         var13 = true;
      } else {
         var13 = false;
      }

      boolean var14;
      if((var15 & 8) != 0) {
         var14 = true;
      } else {
         var14 = false;
      }

      var3.currentScope();
      Object var7 = var3.namespaceResolve(var6);
      var6 = var7;
      if(!(var7 instanceof Symbol)) {
         var3.error('e', "\'" + var7 + "\' is not a valid identifier");
         var6 = null;
      }

      var7 = var3.pushPositionOf(var8);
      Declaration var17 = var3.define(var6, var4, var2);
      var3.popPositionOf(var7);
      Object var11 = var17.getSymbol();
      if(var13) {
         var17.setFlag(16777216L);
         var17.setPrivate(true);
      }

      if(var14) {
         var17.setFlag(16384L);
      }

      var17.setFlag(262144L);
      Pair var16 = var5;
      if((var15 & 2) != 0) {
         LambdaExp var18 = new LambdaExp();
         var18.setSymbol(var11);
         if(Compilation.inlineOk) {
            var17.setProcedureDecl(true);
            var17.setType(Compilation.typeProcedure);
            var18.nameDecl = var17;
         }

         var11 = var10.getCar();
         Object var19 = var10.getCdr();
         Translator.setLine((Expression)var18, var8);
         this.lambda.rewriteFormals(var18, var11, var3, (TemplateScope)null);
         Object var12 = this.lambda.rewriteAttrs(var18, var19, var3);
         var16 = var5;
         if(var12 != var19) {
            var16 = new Pair(var5.getCar(), new Pair(var9.getCar(), new Pair(var11, var12)));
         }

         var17.noteValue(var18);
      }

      if(var2 instanceof ModuleExp && !var13 && (!Compilation.inlineOk || var3.sharedModuleDefs())) {
         var17.setCanWrite(true);
      }

      if((var15 & 1) != 0) {
         var17.setTypeExp(new LangExp(var9));
         var17.setFlag(8192L);
      }

      var1 = Translator.makePair(var1, this, Translator.makePair(var8, var17, var16));
      Translator.setLine((Declaration)var17, var8);
      var3.formStack.addElement(var1);
   }
}
