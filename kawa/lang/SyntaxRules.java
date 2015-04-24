package kawa.lang;

import gnu.expr.Compilation;
import gnu.expr.ErrorExp;
import gnu.expr.ScopeExp;
import gnu.lists.Consumer;
import gnu.mapping.Procedure1;
import gnu.text.Printable;
import gnu.text.ReportFormat;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import kawa.lang.Macro;
import kawa.lang.SyntaxForm;
import kawa.lang.SyntaxRule;
import kawa.lang.TemplateScope;
import kawa.lang.Translator;

public class SyntaxRules extends Procedure1 implements Printable, Externalizable {

   Object[] literal_identifiers;
   int maxVars = 0;
   SyntaxRule[] rules;


   public SyntaxRules() {
   }

   public SyntaxRules(Object[] param1, Object param2, Translator param3) {
      // $FF: Couldn't be decompiled
   }

   public SyntaxRules(Object[] var1, SyntaxRule[] var2, int var3) {
      this.literal_identifiers = var1;
      this.rules = var2;
      this.maxVars = var3;
   }

   public Object apply1(Object var1) {
      if(var1 instanceof SyntaxForm) {
         SyntaxForm var3 = (SyntaxForm)var1;
         Translator var6 = (Translator)Compilation.getCurrent();
         ScopeExp var2 = var6.currentScope();
         var6.setCurrentScope(var3.getScope());

         Object var7;
         try {
            var7 = this.expand(var3, var6);
         } finally {
            var6.setCurrentScope(var2);
         }

         return var7;
      } else {
         return this.expand(var1, (Translator)Compilation.getCurrent());
      }
   }

   public Object expand(Object var1, Translator var2) {
      Object[] var3 = new Object[this.maxVars];
      Macro var4 = (Macro)var2.getCurrentSyntax();

      for(int var6 = 0; var6 < this.rules.length; ++var6) {
         SyntaxRule var5 = this.rules[var6];
         if(var5 == null) {
            return new ErrorExp("error defining " + var4);
         }

         if(var5.pattern.match(var1, var3, 0)) {
            return var5.execute(var3, var2, TemplateScope.make(var2));
         }
      }

      return var2.syntaxError("no matching syntax-rule for " + this.literal_identifiers[0]);
   }

   public void print(Consumer var1) {
      var1.write("#<macro ");
      ReportFormat.print((Object)this.literal_identifiers[0], (Consumer)var1);
      var1.write(62);
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      this.literal_identifiers = (Object[])((Object[])var1.readObject());
      this.rules = (SyntaxRule[])((SyntaxRule[])var1.readObject());
      this.maxVars = var1.readInt();
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      var1.writeObject(this.literal_identifiers);
      var1.writeObject(this.rules);
      var1.writeInt(this.maxVars);
   }
}
