package gnu.kawa.xml;

import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Expression;
import gnu.expr.InlineCalls;
import gnu.expr.QuoteExp;
import gnu.kawa.xml.MakeUnescapedData;
import gnu.kawa.xml.NodeSetType;
import gnu.kawa.xml.TreeScanner;
import gnu.lists.NodePredicate;
import gnu.mapping.Procedure;

public class CompileXmlFunctions {

   public static Expression validateApplyMakeUnescapedData(ApplyExp var0, InlineCalls var1, Type var2, Procedure var3) {
      var0.visitArgs(var1);
      Expression[] var5 = var0.getArgs();
      Object var4 = var0;
      if(var5.length == 1) {
         var4 = var0;
         if(var5[0] instanceof QuoteExp) {
            var4 = new QuoteExp(((MakeUnescapedData)var3).apply1(((QuoteExp)var5[0]).getValue()));
         }
      }

      return (Expression)var4;
   }

   public static Expression validateApplyTreeScanner(ApplyExp var0, InlineCalls var1, Type var2, Procedure var3) {
      var0.visitArgs(var1);
      NodePredicate var4 = ((TreeScanner)var3).type;
      if(var0.getTypeRaw() == null && var4 instanceof Type) {
         var0.setType(NodeSetType.getInstance((Type)var4));
      }

      return var0;
   }
}
