package kawa.lang;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import kawa.lang.SyntaxForm;
import kawa.lang.SyntaxPattern;
import kawa.lang.SyntaxTemplate;
import kawa.lang.Translator;

public class SyntaxRule extends SyntaxTemplate implements Externalizable {

   SyntaxPattern pattern;


   public SyntaxRule() {
   }

   public SyntaxRule(SyntaxPattern var1, Object var2, SyntaxForm var3, Translator var4) {
      super(var2, var3, var4);
      this.pattern = var1;
   }

   public SyntaxRule(SyntaxPattern var1, String var2, String var3, Object[] var4, int var5) {
      super(var2, var3, var4, var5);
      this.pattern = var1;
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      this.pattern = (SyntaxPattern)var1.readObject();
      super.readExternal(var1);
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      var1.writeObject(this.pattern);
      super.writeExternal(var1);
   }
}
