package gnu.q2.lang;

import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.Language;
import gnu.expr.ModuleBody;
import gnu.kawa.functions.AppendValues;
import gnu.kawa.lispexpr.ReadTable;
import gnu.lists.Consumer;
import gnu.lists.FString;
import gnu.mapping.InPort;
import gnu.mapping.Procedure;
import gnu.q2.lang.Prompter;
import gnu.q2.lang.Q2Apply;
import gnu.q2.lang.Q2Read;
import gnu.q2.lang.Q2ReaderParens;
import gnu.text.Lexer;
import gnu.text.SourceMessages;
import gnu.xml.XMLPrinter;
import java.io.Writer;
import kawa.standard.Scheme;

public class Q2 extends Scheme {

   static final Object emptyForm = new FString();
   static Q2 instance;


   public Q2() {
      instance = this;
      ModuleBody.setMainPrintValues(true);
   }

   public static int compareIndentation(int var0, int var1) {
      int var2 = var0 >>> 16;
      int var3 = var0 >>> 16;
      var0 &= 255;
      var1 &= 255;
      return var2 == var3?var0 - var1:((var2 >= var3 || var0 > var1) && (var2 <= var3 || var0 < var1)?Integer.MIN_VALUE:(var2 - var3) * 8);
   }

   public static Q2 getQ2Instance() {
      if(instance == null) {
         new Q2();
      }

      return instance;
   }

   public static void registerEnvironment() {
      Language.setDefaults(new Q2());
   }

   public ReadTable createReadTable() {
      ReadTable var1 = ReadTable.createInitial();
      var1.set(40, new Q2ReaderParens());
      var1.setFinalColonIsKeyword(true);
      return var1;
   }

   public Lexer getLexer(InPort var1, SourceMessages var2) {
      Compilation.defaultCallConvention = 2;
      return new Q2Read(var1, var2);
   }

   public Consumer getOutputConsumer(Writer var1) {
      return new XMLPrinter(var1, false);
   }

   public Procedure getPrompter() {
      return new Prompter();
   }

   public Expression makeApply(Expression var1, Expression[] var2) {
      Expression[] var3 = new Expression[var2.length + 1];
      var3[0] = var1;
      System.arraycopy(var2, 0, var3, 1, var2.length);
      return new ApplyExp(Q2Apply.q2Apply, var3);
   }

   public Expression makeBody(Expression[] var1) {
      return new ApplyExp(AppendValues.appendValues, var1);
   }
}
