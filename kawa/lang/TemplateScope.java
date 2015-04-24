package kawa.lang;

import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.LetExp;
import gnu.expr.ScopeExp;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import kawa.lang.Macro;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class TemplateScope extends LetExp implements Externalizable {

   Declaration macroContext;
   private Syntax syntax;


   public TemplateScope() {
      super((Expression[])null);
   }

   public TemplateScope(ScopeExp var1) {
      super((Expression[])null);
      this.outer = var1;
   }

   public static TemplateScope make() {
      return make((Translator)Compilation.getCurrent());
   }

   public static TemplateScope make(Translator var0) {
      TemplateScope var1 = new TemplateScope();
      Syntax var2 = var0.getCurrentSyntax();
      if(var2 instanceof Macro) {
         var1.outer = ((Macro)var2).getCapturedScope();
         var1.macroContext = var0.macroContext;
      }

      var1.syntax = var2;
      return var1;
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      this.outer = (ScopeExp)var1.readObject();
   }

   public String toString() {
      return super.toString() + "(for " + this.syntax + ")";
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      var1.writeObject(this.outer);
   }
}
