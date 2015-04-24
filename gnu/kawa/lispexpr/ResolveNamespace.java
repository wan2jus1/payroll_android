package gnu.kawa.lispexpr;

import gnu.expr.Expression;
import gnu.expr.QuoteExp;
import gnu.lists.Pair;
import gnu.mapping.Namespace;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class ResolveNamespace extends Syntax {

   public static final ResolveNamespace resolveNamespace = new ResolveNamespace("$resolve-namespace$", false);
   public static final ResolveNamespace resolveQName = new ResolveNamespace("$resolve-qname", true);
   boolean resolvingQName;


   static {
      resolveNamespace.setName("$resolve-namespace$");
   }

   public ResolveNamespace(String var1, boolean var2) {
      super(var1);
      this.resolvingQName = var2;
   }

   public Expression rewriteForm(Pair var1, Translator var2) {
      Pair var4 = (Pair)var1.getCdr();
      Namespace var3 = var2.namespaceResolvePrefix(var2.rewrite_car(var4, false));
      Namespace var5 = var3;
      if(var3 == null) {
         String var6 = var4.getCar().toString();
         if(var6 == "[default-element-namespace]") {
            var5 = Namespace.EmptyNamespace;
         } else {
            Object var7 = var2.pushPositionOf(var4);
            var2.error('e', "unknown namespace prefix " + var6);
            var2.popPositionOf(var7);
            var5 = Namespace.valueOf(var6, (String)var6);
         }
      }

      return this.resolvingQName?new QuoteExp(var5.getSymbol(((Pair)var4.getCdr()).getCar().toString())):new QuoteExp(var5);
   }
}
