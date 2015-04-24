package kawa.standard;

import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.LambdaExp;
import gnu.expr.LetExp;
import gnu.expr.QuoteExp;
import gnu.expr.ScopeExp;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Symbol;
import java.util.Stack;
import kawa.lang.Macro;
import kawa.lang.Syntax;
import kawa.lang.SyntaxForm;
import kawa.lang.TemplateScope;
import kawa.lang.Translator;

public class let_syntax extends Syntax {

   public static final let_syntax let_syntax = new let_syntax(false, "let-syntax");
   public static final let_syntax letrec_syntax = new let_syntax(true, "letrec-syntax");
   boolean recursive;


   public let_syntax(boolean var1, String var2) {
      super(var2);
      this.recursive = var1;
   }

   private void push(LetExp var1, Translator var2, Stack var3) {
      var2.push(var1);
      if(var3 != null) {
         int var4 = var3.size();

         while(true) {
            --var4;
            if(var4 < 0) {
               break;
            }

            var2.pushRenamedAlias((Declaration)var3.pop());
         }
      }

   }

   public Expression rewrite(Object var1, Translator var2) {
      if(!(var1 instanceof Pair)) {
         return var2.syntaxError("missing let-syntax arguments");
      } else {
         Pair var3 = (Pair)var1;
         var1 = var3.getCar();
         Object var10 = var3.getCdr();
         int var20 = Translator.listLength(var1);
         if(var20 < 0) {
            return var2.syntaxError("bindings not a proper list");
         } else {
            Stack var24 = null;
            int var17 = 0;
            Expression[] var11 = new Expression[var20];
            Declaration[] var12 = new Declaration[var20];
            Macro[] var13 = new Macro[var20];
            Pair[] var14 = new Pair[var20];
            SyntaxForm[] var15 = new SyntaxForm[var20];
            LetExp var9 = new LetExp(var11);
            SyntaxForm var4 = null;

            int var19;
            int var18;
            for(var18 = 0; var18 < var20; var17 = var19) {
               Object var5;
               for(var5 = var1; var5 instanceof SyntaxForm; var5 = var4.getDatum()) {
                  var4 = (SyntaxForm)var5;
               }

               SyntaxForm var21 = var4;
               Pair var16 = (Pair)var5;
               Object var6 = var16.getCar();
               var5 = var6;
               if(var6 instanceof SyntaxForm) {
                  var21 = (SyntaxForm)var6;
                  var5 = var21.getDatum();
               }

               if(!(var5 instanceof Pair)) {
                  return var2.syntaxError(this.getName() + " binding is not a pair");
               }

               Pair var27 = (Pair)var5;
               Object var7 = var27.getCar();

               SyntaxForm var29;
               for(var29 = var21; var7 instanceof SyntaxForm; var7 = var29.getDatum()) {
                  var29 = (SyntaxForm)var7;
               }

               if(!(var7 instanceof String) && !(var7 instanceof Symbol)) {
                  return var2.syntaxError("variable in " + this.getName() + " binding is not a symbol");
               }

               Object var8 = var27.getCdr();

               SyntaxForm var32;
               for(var32 = var21; var8 instanceof SyntaxForm; var8 = var32.getDatum()) {
                  var32 = (SyntaxForm)var8;
               }

               if(!(var8 instanceof Pair)) {
                  return var2.syntaxError(this.getName() + " has no value for \'" + var7 + "\'");
               }

               Pair var22 = (Pair)var8;
               if(var22.getCdr() != LList.Empty) {
                  return var2.syntaxError("let binding for \'" + var7 + "\' is improper list");
               }

               Declaration var36 = new Declaration(var7);
               Macro var37 = Macro.make(var36);
               var13[var18] = var37;
               var14[var18] = var22;
               var15[var18] = var32;
               var9.addDeclaration(var36);
               TemplateScope var23;
               if(var29 == null) {
                  var23 = null;
               } else {
                  var23 = var29.getScope();
               }

               Stack var33 = var24;
               var19 = var17;
               if(var23 != null) {
                  Declaration var30 = var2.makeRenamedAlias(var36, var23);
                  Stack var25 = var24;
                  if(var24 == null) {
                     var25 = new Stack();
                  }

                  var25.push(var30);
                  var19 = var17 + 1;
                  var33 = var25;
               }

               if(var32 != null) {
                  var1 = var32.getScope();
               } else if(this.recursive) {
                  var1 = var9;
               } else {
                  var1 = var2.currentScope();
               }

               var37.setCapturedScope((ScopeExp)var1);
               var12[var18] = var36;
               var11[var18] = QuoteExp.nullExp;
               var1 = var16.getCdr();
               ++var18;
               var24 = var33;
            }

            if(this.recursive) {
               this.push(var9, var2, var24);
            }

            Macro var28 = var2.currentMacroDefinition;

            for(var18 = 0; var18 < var20; ++var18) {
               Macro var35 = var13[var18];
               var2.currentMacroDefinition = var35;
               Expression var38 = var2.rewrite_car(var14[var18], var15[var18]);
               var11[var18] = var38;
               Declaration var26 = var12[var18];
               var35.expander = var38;
               var26.noteValue(new QuoteExp(var35));
               if(var38 instanceof LambdaExp) {
                  LambdaExp var34 = (LambdaExp)var38;
                  var34.nameDecl = var26;
                  var34.setSymbol(var26.getSymbol());
               }
            }

            var2.currentMacroDefinition = var28;
            if(!this.recursive) {
               this.push(var9, var2, var24);
            }

            Expression var31 = var2.rewrite_body(var10);
            var2.pop(var9);
            var2.popRenamedAlias(var17);
            return var31;
         }
      }
   }
}
