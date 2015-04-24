package gnu.kawa.slib;

import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.SimpleSymbol;
import kawa.lang.Macro;
import kawa.lang.SyntaxPattern;
import kawa.lang.SyntaxTemplate;
import kawa.lang.TemplateScope;
import kawa.lib.prim_syntax;
import kawa.lib.std_syntax;
import kawa.standard.syntax_case;

public class srfi2 extends ModuleBody {

   public static final srfi2 $instance = new srfi2();
   static final SimpleSymbol Lit0 = (SimpleSymbol)(new SimpleSymbol("and-let*")).readResolve();
   static final SyntaxPattern Lit1 = new SyntaxPattern("\f\u0007,\r\u000f\b\b\b\f\u0017\u001b", new Object[0], 4);
   static final SyntaxTemplate Lit10 = new SyntaxTemplate("\u0001\u0001", "\u000b", new Object[0], 0);
   static final SyntaxPattern Lit11 = new SyntaxPattern("\f\u0007<,\f\u000f\f\u0017\b\u001b\b", new Object[0], 4);
   static final SyntaxTemplate Lit12 = new SyntaxTemplate("\u0001\u0001\u0001\u0000", "\u0011\u0018\u0004)\b\t\u000b\b\u0013\b\u0011\u0018\f\t\u000b\b\t\u0003\b\u001a", new Object[]{Lit22, Lit21}, 0);
   static final SyntaxPattern Lit13 = new SyntaxPattern("\f\u0007,\u001c\f\u000f\b\u0013\b", new Object[0], 3);
   static final SyntaxTemplate Lit14 = new SyntaxTemplate("\u0001\u0001\u0000", "\u0011\u0018\u0004\t\u000b\b\t\u0003\b\u0012", new Object[]{Lit21}, 0);
   static final SyntaxPattern Lit15 = new SyntaxPattern("\f\u0007\u001c\f\u000f\u0013\b", new Object[0], 3);
   static final SyntaxTemplate Lit16 = new SyntaxTemplate("\u0001\u0001\u0000", "\u000b", new Object[0], 0);
   static final SyntaxTemplate Lit17 = new SyntaxTemplate("\u0001\u0001\u0000", "\u0011\u0018\u0004\t\u000b\b\t\u0003\b\u0012", new Object[]{Lit21}, 0);
   static final SyntaxTemplate Lit18 = new SyntaxTemplate("\u0001\u0001\u0000", "\u000b", new Object[0], 0);
   static final SyntaxPattern Lit19 = new SyntaxPattern("\f\u0007\f\b\b", new Object[0], 1);
   static final SyntaxTemplate Lit2 = new SyntaxTemplate("\u0001\u0003\u0001\u0000", "\t\u0003\b\u0011\r\u000b\b\b\u0011\u0018\u0004\t\u0013\u001a", new Object[]{(SimpleSymbol)(new SimpleSymbol("begin")).readResolve()}, 1);
   static final SyntaxTemplate Lit20 = new SyntaxTemplate("\u0001", "\u0018\u0004", new Object[]{Boolean.TRUE}, 0);
   static final SimpleSymbol Lit21 = (SimpleSymbol)(new SimpleSymbol("and")).readResolve();
   static final SimpleSymbol Lit22 = (SimpleSymbol)(new SimpleSymbol("let")).readResolve();
   static final SyntaxPattern Lit3 = new SyntaxPattern("\f\u0007<,\f\u000f\f\u0017\b\b\b", new Object[0], 3);
   static final SyntaxTemplate Lit4 = new SyntaxTemplate("\u0001\u0001\u0001", "\u0011\u0018\u0004)\b\t\u000b\b\u0013\b\u000b", new Object[]{Lit22}, 0);
   static final SyntaxPattern Lit5 = new SyntaxPattern("\f\u0007,\u001c\f\u000f\b\b\b", new Object[0], 2);
   static final SyntaxTemplate Lit6 = new SyntaxTemplate("\u0001\u0001", "\u000b", new Object[0], 0);
   static final SyntaxPattern Lit7 = new SyntaxPattern("\f\u0007\u001c\f\u000f\b\b", new Object[0], 2);
   static final SyntaxTemplate Lit8 = new SyntaxTemplate("\u0001\u0001", "\u000b", new Object[0], 0);
   static final SyntaxTemplate Lit9 = new SyntaxTemplate("\u0001\u0001", "\u000b", new Object[0], 0);
   public static final Macro and$Mnlet$St = Macro.make(Lit0, new ModuleMethod($instance, 1, (Object)null, 4097), $instance);


   static {
      $instance.run();
   }

   public srfi2() {
      ModuleInfo.register(this);
   }

   static Object lambda1(Object var0) {
      Object[] var1 = SyntaxPattern.allocVars(4, (Object[])null);
      TemplateScope var2;
      if(Lit1.match(var0, var1, 0)) {
         var2 = TemplateScope.make();
         return Lit2.execute(var1, var2);
      } else if(Lit3.match(var0, var1, 0)) {
         var2 = TemplateScope.make();
         return Lit4.execute(var1, var2);
      } else if(Lit5.match(var0, var1, 0)) {
         var2 = TemplateScope.make();
         return Lit6.execute(var1, var2);
      } else {
         Object var3;
         Object[] var4;
         if(Lit7.match(var0, var1, 0)) {
            var2 = TemplateScope.make();
            if(std_syntax.isIdentifier(Lit8.execute(var1, var2))) {
               var2 = TemplateScope.make();
               return Lit9.execute(var1, var2);
            } else {
               var2 = TemplateScope.make();
               var3 = Lit10.execute(var1, var2);
               if("expected a variable name" instanceof Object[]) {
                  var4 = (Object[])"expected a variable name";
               } else {
                  var4 = new Object[]{"expected a variable name"};
               }

               return prim_syntax.syntaxError(var3, var4);
            }
         } else if(Lit11.match(var0, var1, 0)) {
            var2 = TemplateScope.make();
            return Lit12.execute(var1, var2);
         } else if(Lit13.match(var0, var1, 0)) {
            var2 = TemplateScope.make();
            return Lit14.execute(var1, var2);
         } else if(Lit15.match(var0, var1, 0)) {
            var2 = TemplateScope.make();
            if(std_syntax.isIdentifier(Lit16.execute(var1, var2))) {
               var2 = TemplateScope.make();
               return Lit17.execute(var1, var2);
            } else {
               var2 = TemplateScope.make();
               var3 = Lit18.execute(var1, var2);
               if("expected a variable name" instanceof Object[]) {
                  var4 = (Object[])"expected a variable name";
               } else {
                  var4 = new Object[]{"expected a variable name"};
               }

               return prim_syntax.syntaxError(var3, var4);
            }
         } else if(Lit19.match(var0, var1, 0)) {
            var2 = TemplateScope.make();
            return Lit20.execute(var1, var2);
         } else {
            return syntax_case.error("syntax-case", var0);
         }
      }
   }

   public Object apply1(ModuleMethod var1, Object var2) {
      return var1.selector == 1?lambda1(var2):super.apply1(var1, var2);
   }

   public int match1(ModuleMethod var1, Object var2, CallContext var3) {
      if(var1.selector == 1) {
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      } else {
         return super.match1(var1, var2, var3);
      }
   }

   public final void run(CallContext var1) {
      Consumer var2 = var1.consumer;
   }
}
