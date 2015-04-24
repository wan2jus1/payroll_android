package gnu.kawa.lispexpr;

import gnu.bytecode.Field;
import gnu.expr.ApplyExp;
import gnu.expr.BeginExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.Keyword;
import gnu.expr.Language;
import gnu.expr.ModuleExp;
import gnu.expr.NameLookup;
import gnu.kawa.lispexpr.LispReader;
import gnu.kawa.lispexpr.ReadTable;
import gnu.kawa.reflect.StaticFieldLocation;
import gnu.mapping.EnvironmentKey;
import gnu.mapping.InPort;
import gnu.mapping.Namespace;
import gnu.mapping.Symbol;
import gnu.text.Lexer;
import gnu.text.SourceMessages;
import gnu.text.SyntaxException;
import java.io.IOException;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public abstract class LispLanguage extends Language {

   public static final Symbol bracket_apply_sym = Namespace.EmptyNamespace.getSymbol("$bracket-apply$");
   public static final Symbol bracket_list_sym = Namespace.EmptyNamespace.getSymbol("$bracket-list$");
   public static StaticFieldLocation getNamedPartLocation = new StaticFieldLocation("gnu.kawa.functions.GetNamedPart", "getNamedPart");
   public static final Symbol lookup_sym = Namespace.EmptyNamespace.getSymbol("$lookup$");
   public static final String quasiquote_sym = "quasiquote";
   public static final String quote_sym = "quote";
   public static final String unquote_sym = "unquote";
   public static final String unquotesplicing_sym = "unquote-splicing";
   public ReadTable defaultReadTable = this.createReadTable();


   static {
      getNamedPartLocation.setProcedure();
   }

   public static Symbol langSymbolToSymbol(Object var0) {
      return ((LispLanguage)Language.getDefaultLanguage()).fromLangSymbol(var0);
   }

   public Expression checkDefaultBinding(Symbol var1, Translator var2) {
      return null;
   }

   public abstract ReadTable createReadTable();

   public Declaration declFromField(ModuleExp var1, Object var2, Field var3) {
      Declaration var5 = super.declFromField(var1, var2, var3);
      boolean var4;
      if((var3.getModifiers() & 16) != 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      if(var4 && var2 instanceof Syntax) {
         var5.setSyntax();
      }

      return var5;
   }

   protected void defSntxStFld(String var1, String var2) {
      this.defSntxStFld(var1, var2, Compilation.mangleNameIfNeeded(var1));
   }

   protected void defSntxStFld(String var1, String var2, String var3) {
      Object var4;
      if(this.hasSeparateFunctionNamespace()) {
         var4 = EnvironmentKey.FUNCTION;
      } else {
         var4 = null;
      }

      StaticFieldLocation.define(this.environ, this.environ.getSymbol(var1), var4, var2, var3).setSyntax();
   }

   protected Symbol fromLangSymbol(Object var1) {
      return var1 instanceof String?this.getSymbol((String)var1):(Symbol)var1;
   }

   public Compilation getCompilation(Lexer var1, SourceMessages var2, NameLookup var3) {
      return new Translator(this, var2, var3);
   }

   public Lexer getLexer(InPort var1, SourceMessages var2) {
      return new LispReader(var1, var2);
   }

   public Expression makeApply(Expression var1, Expression[] var2) {
      return new ApplyExp(var1, var2);
   }

   public Expression makeBody(Expression[] var1) {
      return new BeginExp(var1);
   }

   public boolean parse(Compilation param1, int param2) throws IOException, SyntaxException {
      // $FF: Couldn't be decompiled
   }

   public void resolve(Compilation var1) {
      Translator var2 = (Translator)var1;
      var2.resolveModule(var2.getModule());
   }

   public boolean selfEvaluatingSymbol(Object var1) {
      return var1 instanceof Keyword;
   }
}
