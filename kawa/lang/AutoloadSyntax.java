package kawa.lang;

import gnu.expr.Expression;
import gnu.expr.ScopeExp;
import gnu.lists.Pair;
import gnu.mapping.Environment;
import gnu.mapping.WrongType;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.PrintWriter;
import kawa.lang.GenericError;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class AutoloadSyntax extends Syntax implements Externalizable {

   String className;
   Environment env;
   Syntax loaded;


   public AutoloadSyntax() {
   }

   public AutoloadSyntax(String var1, String var2) {
      super(var1);
      this.className = var2;
   }

   public AutoloadSyntax(String var1, String var2, Environment var3) {
      super(var1);
      this.className = var2;
      this.env = var3;
   }

   private void throw_error(String var1) {
      StringBuilder var2 = (new StringBuilder()).append(var1).append(this.className).append(" while autoloading ");
      if(this.getName() == null) {
         var1 = "";
      } else {
         var1 = this.getName().toString();
      }

      throw new GenericError(var2.append(var1).toString());
   }

   void load() {
      // $FF: Couldn't be decompiled
   }

   public void print(PrintWriter var1) {
      var1.print(this.toString());
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      this.setName((String)var1.readObject());
      this.className = (String)var1.readObject();
   }

   public Expression rewriteForm(Pair var1, Translator var2) {
      if(this.loaded == null) {
         try {
            this.load();
         } catch (GenericError var8) {
            return var2.syntaxError(var8.getMessage());
         } catch (WrongType var9) {
            return var2.syntaxError(var9.getMessage());
         }
      }

      Syntax var3 = var2.currentSyntax;
      var2.currentSyntax = this.loaded;

      Expression var10;
      try {
         var10 = this.loaded.rewriteForm((Pair)var1, var2);
      } finally {
         var2.currentSyntax = var3;
      }

      return var10;
   }

   public void scanForm(Pair var1, ScopeExp var2, Translator var3) {
      if(this.loaded == null) {
         try {
            this.load();
         } catch (RuntimeException var4) {
            var3.syntaxError(var4.getMessage());
            return;
         }
      }

      this.loaded.scanForm(var1, var2, var3);
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer(100);
      var1.append("#<syntax ");
      if(this.getName() != null) {
         var1.append(this.getName());
         var1.append(' ');
      }

      if(this.loaded != null) {
         var1.append("autoloaded>");
      } else {
         var1.append("autoload ");
         var1.append(this.className);
         var1.append(">");
      }

      return var1.toString();
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      var1.writeObject(this.getName());
      var1.writeObject(this.className);
   }
}
