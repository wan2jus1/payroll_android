package kawa.standard;

import gnu.bytecode.ClassType;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.ModuleExp;
import gnu.expr.QuoteExp;
import gnu.expr.ScopeExp;
import gnu.kawa.reflect.Invoke;
import gnu.lists.LList;
import gnu.lists.Pair;
import java.util.Vector;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class define_member_alias extends Syntax {

   public static final define_member_alias define_member_alias = new define_member_alias();


   static {
      define_member_alias.setName("define-member-alias");
   }

   public Expression rewriteForm(Pair var1, Translator var2) {
      Object var7 = var1.getCdr();
      if(var7 instanceof Pair) {
         var1 = (Pair)var7;
         if(var1.getCar() instanceof String || var1.getCar() instanceof Declaration) {
            if(var1.getCdr() instanceof Pair) {
               Object var3 = var1.getCar();
               String var8;
               if(var3 instanceof Declaration) {
                  var8 = ((Declaration)var3).getName();
               } else {
                  var8 = (String)var3;
               }

               var1 = (Pair)var1.getCdr();
               Object var4 = null;
               Expression var5 = var2.rewrite(var1.getCar());
               Object var6 = var1.getCdr();
               if(var6 == LList.Empty) {
                  var7 = new QuoteExp(Compilation.mangleName(var8));
               } else {
                  var7 = var4;
                  if(var6 instanceof Pair) {
                     Pair var9 = (Pair)var6;
                     var7 = var4;
                     if(var9.getCdr() == LList.Empty) {
                        var7 = var2.rewrite(var9.getCar());
                     }
                  }
               }

               if(var7 != null) {
                  return Invoke.makeInvokeStatic(ClassType.make("gnu.kawa.reflect.ClassMemberConstraint"), "define", new Expression[]{new QuoteExp(var8), var5, (Expression)var7});
               }
            }

            return var2.syntaxError("invalid syntax for " + this.getName());
         }
      }

      return var2.syntaxError("missing name in " + this.getName());
   }

   public boolean scanForDefinitions(Pair var1, Vector var2, ScopeExp var3, Translator var4) {
      if(var1.getCdr() instanceof Pair && !(var4.currentScope() instanceof ModuleExp)) {
         Pair var5 = (Pair)var1.getCdr();
         if(var5.getCar() instanceof String) {
            Declaration var6 = var3.addDeclaration((String)var5.getCar(), Compilation.typeSymbol);
            var6.setIndirectBinding(true);
            var2.addElement(Translator.makePair(var1, this, Translator.makePair(var5, var6, var5.getCdr())));
            return true;
         }
      }

      return super.scanForDefinitions(var1, var2, var3, var4);
   }
}
