package gnu.commonlisp.lang;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.commonlisp.lang.Lisp2ReadTable;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.Keyword;
import gnu.expr.QuoteExp;
import gnu.kawa.lispexpr.LangObjType;
import gnu.kawa.lispexpr.LispLanguage;
import gnu.kawa.lispexpr.ReadTable;
import gnu.kawa.reflect.FieldLocation;
import gnu.lists.FString;
import gnu.lists.LList;
import gnu.mapping.Environment;
import gnu.mapping.EnvironmentKey;
import gnu.mapping.Location;
import gnu.mapping.Named;
import gnu.mapping.NamedLocation;
import gnu.mapping.Namespace;
import gnu.mapping.Procedure;
import gnu.mapping.Symbol;
import java.lang.reflect.Field;
import kawa.lang.Syntax;

public abstract class Lisp2 extends LispLanguage {

   public static final LList FALSE = LList.Empty;
   public static final Symbol TRUE = Namespace.getDefault().getSymbol("t");
   public static final Expression nilExpr = new QuoteExp(FALSE);


   public static Object asSymbol(String var0) {
      return var0 == "nil"?FALSE:Environment.getCurrent().getSymbol(var0);
   }

   private void defun(Procedure var1) {
      this.defun((String)var1.getName(), var1);
   }

   public static Object getString(Symbol var0) {
      return getString((String)var0.getName());
   }

   public static Object getString(String var0) {
      return new FString(var0);
   }

   public Object booleanObject(boolean var1) {
      return var1?TRUE:FALSE;
   }

   public ReadTable createReadTable() {
      Lisp2ReadTable var1 = new Lisp2ReadTable();
      var1.initialize();
      var1.setInitialColonIsKeyword(true);
      return var1;
   }

   protected void defun(Symbol var1, Object var2) {
      this.environ.define(var1, EnvironmentKey.FUNCTION, var2);
      if(var2 instanceof Procedure) {
         Procedure var3 = (Procedure)var2;
         if(var3.getSymbol() == null) {
            var3.setSymbol(var1);
         }
      }

   }

   protected void defun(String var1, Object var2) {
      this.environ.define(this.getSymbol(var1), EnvironmentKey.FUNCTION, var2);
      if(var2 instanceof Named) {
         Named var3 = (Named)var2;
         if(var3.getName() == null) {
            var3.setName(var1);
         }
      }

   }

   public void emitPushBoolean(boolean var1, CodeAttr var2) {
      if(var1) {
         var2.emitGetStatic(ClassType.make("gnu.commonlisp.lang.Lisp2").getDeclaredField("TRUE"));
      } else {
         var2.emitGetStatic(Compilation.scmListType.getDeclaredField("Empty"));
      }
   }

   protected Symbol fromLangSymbol(Object var1) {
      return var1 == LList.Empty?this.environ.getSymbol("nil"):super.fromLangSymbol(var1);
   }

   public Object getEnvPropertyFor(Field var1, Object var2) {
      return !Compilation.typeProcedure.getReflectClass().isAssignableFrom(var1.getType()) && !(var2 instanceof Syntax)?null:EnvironmentKey.FUNCTION;
   }

   public int getNamespaceOf(Declaration var1) {
      return var1.isAlias()?3:(var1.isProcedureDecl()?2:1);
   }

   public boolean hasSeparateFunctionNamespace() {
      return true;
   }

   protected void importLocation(Location var1) {
      Symbol var2 = ((NamedLocation)var1).getKeySymbol();
      if(!this.environ.isBound(var2, EnvironmentKey.FUNCTION)) {
         var1 = var1.getBase();
         if(var1 instanceof FieldLocation && ((FieldLocation)var1).isProcedureOrSyntax()) {
            this.environ.addLocation(var2, EnvironmentKey.FUNCTION, var1);
            return;
         }

         Object var3 = var1.get((Object)null);
         if(var3 != null) {
            if(!(var3 instanceof Procedure) && !(var3 instanceof Syntax)) {
               if(var3 instanceof LangObjType) {
                  this.defun((Symbol)var2, ((LangObjType)var3).getConstructor());
                  return;
               }

               this.define(var2.getName(), var3);
               return;
            }

            this.defun((Symbol)var2, var3);
            return;
         }
      }

   }

   public boolean isTrue(Object var1) {
      return var1 != FALSE;
   }

   public Object noValue() {
      return FALSE;
   }

   public boolean selfEvaluatingSymbol(Object var1) {
      return var1 instanceof Keyword || var1 == TRUE || var1 == FALSE;
   }
}
