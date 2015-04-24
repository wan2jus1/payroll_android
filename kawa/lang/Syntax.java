package kawa.lang;

import gnu.expr.ErrorExp;
import gnu.expr.Expression;
import gnu.expr.ScopeExp;
import gnu.lists.Consumer;
import gnu.lists.Pair;
import gnu.mapping.Named;
import gnu.mapping.Symbol;
import gnu.text.Printable;
import java.util.Vector;
import kawa.lang.Translator;

public abstract class Syntax implements Printable, Named {

   Object name;


   public Syntax() {
   }

   public Syntax(Object var1) {
      this.setName((Object)var1);
   }

   public final String getName() {
      return this.name == null?null:(this.name instanceof Symbol?((Symbol)this.name).getName():this.name.toString());
   }

   public Object getSymbol() {
      return this.name;
   }

   public void print(Consumer var1) {
      var1.write("#<syntax ");
      String var3 = this.getName();
      String var2 = var3;
      if(var3 == null) {
         var2 = "<unnamed>";
      }

      var1.write(var2);
      var1.write(62);
   }

   public Expression rewrite(Object var1, Translator var2) {
      throw new InternalError("rewrite method not defined");
   }

   public Expression rewriteForm(Pair var1, Translator var2) {
      return this.rewrite(var1.getCdr(), var2);
   }

   public Expression rewriteForm(Object var1, Translator var2) {
      return var1 instanceof Pair?this.rewriteForm((Pair)((Pair)var1), var2):var2.syntaxError("non-list form for " + this);
   }

   public boolean scanForDefinitions(Pair var1, Vector var2, ScopeExp var3, Translator var4) {
      var2.addElement(var1);
      return true;
   }

   public void scanForm(Pair var1, ScopeExp var2, Translator var3) {
      if(!this.scanForDefinitions(var1, var3.formStack, var2, var3)) {
         var3.formStack.add(new ErrorExp("syntax error expanding " + this));
      }

   }

   public void setName(Object var1) {
      this.name = var1;
   }

   public void setName(String var1) {
      this.name = var1;
   }
}
