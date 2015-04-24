package kawa.lang;

import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.ModuleExp;
import gnu.expr.ModuleInfo;
import gnu.expr.QuoteExp;
import gnu.expr.ScopeExp;
import gnu.lists.Consumer;
import gnu.lists.Pair;
import gnu.mapping.Procedure;
import gnu.text.Printable;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class Macro extends Syntax implements Printable, Externalizable {

   private ScopeExp capturedScope;
   public Object expander;
   private boolean hygienic = true;
   Object instance;


   public Macro() {
   }

   public Macro(Object var1) {
      super(var1);
   }

   public Macro(Object var1, Procedure var2) {
      super(var1);
      this.expander = new QuoteExp(var2);
   }

   public Macro(Macro var1) {
      this.name = var1.name;
      this.expander = var1.expander;
      this.hygienic = var1.hygienic;
   }

   public static Macro make(Declaration var0) {
      Macro var1 = new Macro(var0.getSymbol());
      var0.setSyntax();
      var1.capturedScope = var0.context;
      return var1;
   }

   public static Macro make(Object var0, Procedure var1) {
      return new Macro(var0, var1);
   }

   public static Macro make(Object var0, Procedure var1, Object var2) {
      Macro var3 = new Macro(var0, var1);
      var3.instance = var2;
      return var3;
   }

   public static Macro makeNonHygienic(Object var0, Procedure var1) {
      Macro var2 = new Macro(var0, var1);
      var2.hygienic = false;
      return var2;
   }

   public static Macro makeNonHygienic(Object var0, Procedure var1, Object var2) {
      Macro var3 = new Macro(var0, var1);
      var3.hygienic = false;
      var3.instance = var2;
      return var3;
   }

   public Object expand(Object param1, Translator param2) {
      // $FF: Couldn't be decompiled
   }

   public ScopeExp getCapturedScope() {
      if(this.capturedScope == null) {
         if(this.instance instanceof ModuleExp) {
            this.capturedScope = (ModuleExp)this.instance;
         } else if(this.instance != null) {
            this.capturedScope = ModuleInfo.findFromInstance(this.instance).getModuleExp();
         }
      }

      return this.capturedScope;
   }

   public final boolean isHygienic() {
      return this.hygienic;
   }

   public void print(Consumer var1) {
      var1.write("#<macro ");
      var1.write(this.getName());
      var1.write(62);
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      this.setName((String)var1.readObject());
      this.expander = new QuoteExp(var1.readObject());
   }

   public Expression rewriteForm(Pair var1, Translator var2) {
      return var2.rewrite(this.expand(var1, var2));
   }

   public Expression rewriteForm(Object var1, Translator var2) {
      return var2.rewrite(this.expand(var1, var2));
   }

   public void scanForm(Pair var1, ScopeExp var2, Translator var3) {
      String var4 = var3.getFileName();
      int var6 = var3.getLineNumber();
      int var7 = var3.getColumnNumber();
      Syntax var5 = var3.currentSyntax;

      try {
         var3.setLine(var1);
         var3.currentSyntax = this;
         var3.scanForm(this.expand(var1, var3), var2);
      } finally {
         var3.setLine(var4, var6, var7);
         var3.currentSyntax = var5;
      }

   }

   public void setCapturedScope(ScopeExp var1) {
      this.capturedScope = var1;
   }

   public final void setHygienic(boolean var1) {
      this.hygienic = var1;
   }

   public String toString() {
      return "#<macro " + this.getName() + '>';
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      var1.writeObject(this.getName());
      var1.writeObject(((QuoteExp)this.expander).getValue());
   }
}
