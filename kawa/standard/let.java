package kawa.standard;

import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.LetExp;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Symbol;
import java.util.Stack;
import kawa.lang.Syntax;
import kawa.lang.SyntaxForm;
import kawa.lang.TemplateScope;
import kawa.lang.Translator;

public class let extends Syntax {

   public static final let let = new let();


   static {
      let.setName("let");
   }

   public Expression rewrite(Object var1, Translator var2) {
      if(!(var1 instanceof Pair)) {
         return var2.syntaxError("missing let arguments");
      } else {
         Pair var3 = (Pair)var1;
         Object var5 = var3.getCar();
         Object var11 = var3.getCdr();
         int var20 = Translator.listLength(var5);
         if(var20 < 0) {
            return var2.syntaxError("bindings not a proper list");
         } else {
            Expression[] var12 = new Expression[var20];
            LetExp var13 = new LetExp(var12);
            Stack var21 = null;
            int var17 = 0;
            SyntaxForm var4 = null;
            int var18 = 0;

            while(true) {
               if(var18 >= var20) {
                  var18 = var17;

                  while(true) {
                     --var18;
                     if(var18 < 0) {
                        var2.push(var13);
                        var13.body = var2.rewrite_body(var11);
                        var2.pop(var13);
                        var2.popRenamedAlias(var17);
                        return var13;
                     }

                     var2.pushRenamedAlias((Declaration)var21.pop());
                  }
               }

               while(var5 instanceof SyntaxForm) {
                  var4 = (SyntaxForm)var5;
                  var5 = var4.getDatum();
               }

               Pair var14 = (Pair)var5;
               Object var7 = var14.getCar();
               SyntaxForm var24 = var4;
               Object var6 = var7;
               if(var7 instanceof SyntaxForm) {
                  var24 = (SyntaxForm)var7;
                  var6 = var24.getDatum();
               }

               if(!(var6 instanceof Pair)) {
                  return var2.syntaxError("let binding is not a pair:" + var6);
               }

               Pair var8 = (Pair)var6;
               var7 = var8.getCar();
               TemplateScope var25;
               SyntaxForm var26;
               if(var7 instanceof SyntaxForm) {
                  var26 = (SyntaxForm)var7;
                  var7 = var26.getDatum();
                  var25 = var26.getScope();
               } else if(var24 == null) {
                  var25 = null;
               } else {
                  var25 = var24.getScope();
               }

               Object var15 = var2.namespaceResolve(var7);
               if(!(var15 instanceof Symbol)) {
                  return var2.syntaxError("variable " + var15 + " in let binding is not a symbol: " + var1);
               }

               Declaration var16 = var13.addDeclaration(var15);
               var16.setFlag(262144L);
               Stack var30 = var21;
               int var19 = var17;
               if(var25 != null) {
                  Declaration var29 = var2.makeRenamedAlias(var16, var25);
                  Stack var28 = var21;
                  if(var21 == null) {
                     var28 = new Stack();
                  }

                  var28.push(var29);
                  var19 = var17 + 1;
                  var30 = var28;
               }

               var6 = var8.getCdr();
               SyntaxForm var22 = var24;

               for(var5 = var6; var5 instanceof SyntaxForm; var5 = var22.getDatum()) {
                  var22 = (SyntaxForm)var5;
               }

               if(!(var5 instanceof Pair)) {
                  return var2.syntaxError("let has no value for \'" + var15 + "\'");
               }

               Pair var10 = (Pair)var5;

               for(var6 = var10.getCdr(); var6 instanceof SyntaxForm; var6 = var22.getDatum()) {
                  var22 = (SyntaxForm)var6;
               }

               Pair var27 = var10;
               Object var9 = var6;
               SyntaxForm var31 = var22;
               if(var2.matches(var10.getCar(), "::")) {
                  if(!(var6 instanceof Pair)) {
                     break;
                  }

                  var10 = (Pair)var6;
                  if(var10.getCdr() == LList.Empty) {
                     break;
                  }

                  var5 = var10.getCdr();
                  var26 = var22;
                  Object var23 = var5;

                  while(true) {
                     var27 = var10;
                     var9 = var23;
                     var31 = var26;
                     if(!(var23 instanceof SyntaxForm)) {
                        break;
                     }

                     var26 = (SyntaxForm)var23;
                     var23 = var26.getDatum();
                  }
               }

               if(var9 != LList.Empty) {
                  if(!(var9 instanceof Pair)) {
                     return var2.syntaxError("let binding for \'" + var15 + "\' is improper list");
                  }

                  var16.setType(var2.exp2Type(var27));
                  var16.setFlag(8192L);
                  var27 = (Pair)var9;
               }

               var12[var18] = var2.rewrite_car(var27, var31);
               if(var27.getCdr() != LList.Empty) {
                  return var2.syntaxError("junk after declaration of " + var15);
               }

               var16.noteValue(var12[var18]);
               var5 = var14.getCdr();
               ++var18;
               var21 = var30;
               var17 = var19;
            }

            return var2.syntaxError("missing type after \'::\' in let");
         }
      }
   }
}
