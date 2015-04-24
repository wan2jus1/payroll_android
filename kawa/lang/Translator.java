package kawa.lang;

import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.ErrorExp;
import gnu.expr.Expression;
import gnu.expr.Keyword;
import gnu.expr.LambdaExp;
import gnu.expr.Language;
import gnu.expr.ModuleExp;
import gnu.expr.ModuleInfo;
import gnu.expr.NameLookup;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.ScopeExp;
import gnu.expr.Special;
import gnu.kawa.functions.AppendValues;
import gnu.kawa.functions.CompileNamedPart;
import gnu.kawa.lispexpr.LispLanguage;
import gnu.kawa.xml.MakeAttribute;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.Environment;
import gnu.mapping.EnvironmentKey;
import gnu.mapping.Namespace;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.text.SourceLocator;
import gnu.text.SourceMessages;
import gnu.xml.NamespaceBinding;
import java.util.Stack;
import java.util.Vector;
import kawa.lang.AutoloadProcedure;
import kawa.lang.Macro;
import kawa.lang.PatternScope;
import kawa.lang.Syntax;
import kawa.lang.SyntaxForm;
import kawa.lang.SyntaxForms;
import kawa.standard.require;

public class Translator extends Compilation {

   private static Expression errorExp;
   public static final Declaration getNamedPartDecl = Declaration.getDeclarationFromStatic("gnu.kawa.functions.GetNamedPart", "getNamedPart");
   public LambdaExp curMethodLambda;
   public Macro currentMacroDefinition;
   Syntax currentSyntax;
   private Environment env;
   public int firstForm;
   public Stack formStack = new Stack();
   Declaration macroContext;
   public Declaration matchArray;
   Vector notedAccess;
   public PatternScope patternScope;
   public Object pendingForm;
   PairWithPosition positionPair;
   Stack renamedAliasStack;
   public Declaration templateScopeDecl;
   public NamespaceBinding xmlElementNamespaces;


   static {
      LispLanguage.getNamedPartLocation.setDeclaration(getNamedPartDecl);
      errorExp = new ErrorExp("unknown syntax error");
   }

   public Translator(Language var1, SourceMessages var2, NameLookup var3) {
      super(var1, var2, var3);
      this.xmlElementNamespaces = NamespaceBinding.predefinedXML;
      this.env = Environment.getCurrent();
   }

   static ReferenceExp getOriginalRef(Declaration var0) {
      if(var0 != null && var0.isAlias() && !var0.isIndirectBinding()) {
         Expression var1 = var0.getValue();
         if(var1 instanceof ReferenceExp) {
            return (ReferenceExp)var1;
         }
      }

      return null;
   }

   public static int listLength(Object var0) {
      int var4 = 0;
      Object var2 = var0;
      var0 = var0;
      Object var1 = var2;

      while(true) {
         while(true) {
            var2 = var0;
            if(var1 instanceof SyntaxForm) {
               var1 = ((SyntaxForm)var1).getDatum();
            } else {
               while(var2 instanceof SyntaxForm) {
                  var2 = ((SyntaxForm)var2).getDatum();
               }

               if(var1 == LList.Empty) {
                  return var4;
               }

               if(!(var1 instanceof Pair)) {
                  return -1 - var4;
               }

               ++var4;

               for(var0 = ((Pair)var1).getCdr(); var0 instanceof SyntaxForm; var0 = ((SyntaxForm)var0).getDatum()) {
                  ;
               }

               if(var0 == LList.Empty) {
                  return var4;
               }

               if(!(var0 instanceof Pair)) {
                  return -1 - var4;
               }

               var2 = ((Pair)var2).getCdr();
               Object var3 = ((Pair)var0).getCdr();
               ++var4;
               var1 = var3;
               var0 = var2;
               if(var3 == var2) {
                  return Integer.MIN_VALUE;
               }
            }
         }
      }
   }

   private Expression makeBody(int var1, ScopeExp var2) {
      int var5 = this.formStack.size() - var1;
      if(var5 == 0) {
         return QuoteExp.voidExp;
      } else if(var5 == 1) {
         return (Expression)this.formStack.pop();
      } else {
         Expression[] var3 = new Expression[var5];

         for(int var4 = 0; var4 < var5; ++var4) {
            var3[var4] = (Expression)this.formStack.elementAt(var1 + var4);
         }

         this.formStack.setSize(var1);
         return (Expression)(var2 instanceof ModuleExp?new ApplyExp(AppendValues.appendValues, var3):((LispLanguage)this.getLanguage()).makeBody(var3));
      }
   }

   public static Pair makePair(Pair var0, Object var1, Object var2) {
      return (Pair)(var0 instanceof PairWithPosition?new PairWithPosition((PairWithPosition)var0, var1, var2):new Pair(var1, var2));
   }

   private void rewriteBody(LList var1) {
      Pair var2;
      for(; var1 != LList.Empty; var1 = (LList)var2.getCdr()) {
         var2 = (Pair)var1;
         Object var5 = this.pushPositionOf(var2);

         try {
            this.rewriteInBody(var2.getCar());
         } finally {
            this.popPositionOf(var5);
         }
      }

   }

   public static Object safeCar(Object var0) {
      while(var0 instanceof SyntaxForm) {
         var0 = ((SyntaxForm)var0).getDatum();
      }

      if(!(var0 instanceof Pair)) {
         return null;
      } else {
         return stripSyntax(((Pair)var0).getCar());
      }
   }

   public static Object safeCdr(Object var0) {
      while(var0 instanceof SyntaxForm) {
         var0 = ((SyntaxForm)var0).getDatum();
      }

      if(!(var0 instanceof Pair)) {
         return null;
      } else {
         return stripSyntax(((Pair)var0).getCdr());
      }
   }

   public static void setLine(Declaration var0, Object var1) {
      if(var1 instanceof SourceLocator) {
         var0.setLocation((SourceLocator)var1);
      }

   }

   public static void setLine(Expression var0, Object var1) {
      if(var1 instanceof SourceLocator) {
         var0.setLocation((SourceLocator)var1);
      }

   }

   public static Object stripSyntax(Object var0) {
      while(var0 instanceof SyntaxForm) {
         var0 = ((SyntaxForm)var0).getDatum();
      }

      return var0;
   }

   static void vectorReverse(Vector var0, int var1, int var2) {
      int var4 = var2 / 2;
      int var5 = var1 + var2 - 1;

      for(var2 = 0; var2 < var4; ++var2) {
         Object var3 = var0.elementAt(var1 + var2);
         var0.setElementAt(var0.elementAt(var5 - var2), var1 + var2);
         var0.setElementAt(var3, var5 - var2);
      }

   }

   public static Object wrapSyntax(Object var0, SyntaxForm var1) {
      return var1 != null && !(var0 instanceof Expression)?SyntaxForms.fromDatumIfNeeded(var0, var1):var0;
   }

   Expression apply_rewrite(Syntax var1, Pair var2) {
      Expression var3 = errorExp;
      Syntax var7 = this.currentSyntax;
      this.currentSyntax = var1;

      Expression var6;
      try {
         var6 = var1.rewriteForm((Pair)var2, this);
      } finally {
         this.currentSyntax = var7;
      }

      return var6;
   }

   Syntax check_if_Syntax(Declaration param1) {
      // $FF: Couldn't be decompiled
   }

   public Declaration define(Object var1, SyntaxForm var2, ScopeExp var3) {
      boolean var5;
      if(var2 != null && var2.getScope() != this.currentScope()) {
         var5 = true;
      } else {
         var5 = false;
      }

      Object var4;
      if(var5) {
         var4 = new String(var1.toString());
      } else {
         var4 = var1;
      }

      Declaration var7 = var3.getDefine(var4, 'w', this);
      if(var5) {
         Declaration var6 = this.makeRenamedAlias(var1, var7, var2.getScope());
         var2.getScope().addDeclaration(var6);
      }

      this.push(var7);
      return var7;
   }

   public Type exp2Type(Pair param1) {
      // $FF: Couldn't be decompiled
   }

   public void finishModule(ModuleExp var1) {
      boolean var4 = var1.isStatic();

      for(Declaration var3 = var1.firstDecl(); var3 != null; var3 = var3.nextDecl()) {
         if(var3.getFlag(512L)) {
            String var2;
            if(var3.getFlag(1024L)) {
               var2 = "\' exported but never defined";
            } else if(var3.getFlag(2048L)) {
               var2 = "\' declared static but never defined";
            } else {
               var2 = "\' declared but never defined";
            }

            this.error('e', var3, "\'", var2);
         }

         if(var1.getFlag(16384) || this.generateMain && !this.immediate) {
            if(var3.getFlag(1024L)) {
               if(var3.isPrivate()) {
                  if(var3.getFlag(16777216L)) {
                     this.error('e', var3, "\'", "\' is declared both private and exported");
                  }

                  var3.setPrivate(false);
               }
            } else {
               var3.setPrivate(true);
            }
         }

         if(var4) {
            var3.setFlag(2048L);
         } else if(var1.getFlag(65536) && !var3.getFlag(2048L) || Compilation.moduleStatic < 0 || var1.getFlag(131072)) {
            var3.setFlag(4096L);
         }
      }

   }

   public Syntax getCurrentSyntax() {
      return this.currentSyntax;
   }

   public final Environment getGlobalEnvironment() {
      return this.env;
   }

   public Declaration lookup(Object var1, int var2) {
      Declaration var3 = this.lexical.lookup(var1, var2);
      return var3 != null && this.getLanguage().hasNamespace(var3, var2)?var3:this.currentModule().lookup(var1, this.getLanguage(), var2);
   }

   public Declaration lookupGlobal(Object var1) {
      return this.lookupGlobal(var1, -1);
   }

   public Declaration lookupGlobal(Object var1, int var2) {
      ModuleExp var5 = this.currentModule();
      Declaration var4 = var5.lookup(var1, this.getLanguage(), var2);
      Declaration var3 = var4;
      if(var4 == null) {
         var3 = var5.getNoDefine(var1);
         var3.setIndirectBinding(true);
      }

      return var3;
   }

   public Declaration makeRenamedAlias(Declaration var1, ScopeExp var2) {
      return var2 == null?var1:this.makeRenamedAlias(var1.getSymbol(), var1, var2);
   }

   public Declaration makeRenamedAlias(Object var1, Declaration var2, ScopeExp var3) {
      Declaration var4 = new Declaration(var1);
      var4.setAlias(true);
      var4.setPrivate(true);
      var4.context = var3;
      ReferenceExp var5 = new ReferenceExp(var2);
      var5.setDontDereference(true);
      var4.noteValue(var5);
      return var4;
   }

   public Object matchQuoted(Pair var1) {
      if(this.matches(var1.getCar(), "quote") && var1.getCdr() instanceof Pair) {
         var1 = (Pair)var1.getCdr();
         if(var1.getCdr() == LList.Empty) {
            return var1.getCar();
         }
      }

      return null;
   }

   public final boolean matches(Object var1, String var2) {
      return this.matches(var1, (SyntaxForm)null, (String)var2);
   }

   public boolean matches(Object var1, SyntaxForm var2, Symbol var3) {
      if(var2 != null) {
         ;
      }

      Object var5 = var1;
      if(var1 instanceof SyntaxForm) {
         var5 = ((SyntaxForm)var1).getDatum();
      }

      var1 = var5;
      if(var5 instanceof SimpleSymbol) {
         var1 = var5;
         if(!this.selfEvaluatingSymbol(var5)) {
            ReferenceExp var4 = getOriginalRef(this.lexical.lookup(var5, -1));
            var1 = var5;
            if(var4 != null) {
               var1 = var4.getSymbol();
            }
         }
      }

      return var1 == var3;
   }

   public boolean matches(Object var1, SyntaxForm var2, String var3) {
      if(var2 != null) {
         ;
      }

      Object var5 = var1;
      if(var1 instanceof SyntaxForm) {
         var5 = ((SyntaxForm)var1).getDatum();
      }

      var1 = var5;
      if(var5 instanceof SimpleSymbol) {
         var1 = var5;
         if(!this.selfEvaluatingSymbol(var5)) {
            ReferenceExp var4 = getOriginalRef(this.lexical.lookup(var5, -1));
            var1 = var5;
            if(var4 != null) {
               var1 = var4.getSymbol();
            }
         }
      }

      return var1 instanceof SimpleSymbol && ((Symbol)var1).getLocalPart() == var3;
   }

   public Symbol namespaceResolve(Expression var1, Expression var2) {
      return this.namespaceResolve((Namespace)this.namespaceResolvePrefix(var1), var2);
   }

   public Symbol namespaceResolve(Namespace var1, Expression var2) {
      return var1 != null && var2 instanceof QuoteExp?var1.getSymbol(((QuoteExp)var2).getValue().toString().intern()):null;
   }

   public Object namespaceResolve(Object var1) {
      if(!(var1 instanceof SimpleSymbol) && var1 instanceof Pair) {
         Pair var2 = (Pair)var1;
         if(safeCar(var2) == LispLanguage.lookup_sym && var2.getCdr() instanceof Pair) {
            Pair var3 = (Pair)var2.getCdr();
            if(var3.getCdr() instanceof Pair) {
               Expression var5 = this.rewrite(var3.getCar());
               Expression var7 = this.rewrite(((Pair)var3.getCdr()).getCar());
               Symbol var4 = this.namespaceResolve((Expression)var5, var7);
               if(var4 != null) {
                  return var4;
               }

               String var6 = CompileNamedPart.combineName(var5, var7);
               if(var6 != null) {
                  return Namespace.EmptyNamespace.getSymbol(var6);
               }
            }
         }
      }

      return var1;
   }

   public Namespace namespaceResolvePrefix(Expression var1) {
      if(var1 instanceof ReferenceExp) {
         ReferenceExp var4 = (ReferenceExp)var1;
         Declaration var2 = var4.getBinding();
         Object var5;
         if(var2 != null && !var2.getFlag(65536L)) {
            if(var2.isNamespaceDecl()) {
               var5 = var2.getConstantValue();
            } else {
               var5 = null;
            }
         } else {
            var5 = var4.getSymbol();
            Symbol var6;
            if(var5 instanceof Symbol) {
               var6 = (Symbol)var5;
            } else {
               var6 = this.env.getSymbol(var5.toString());
            }

            var5 = this.env.get((EnvironmentKey)var6, (Object)null);
         }

         if(var5 instanceof Namespace) {
            Namespace var7 = (Namespace)var5;
            String var3 = var7.getName();
            Namespace var8 = var7;
            if(var3 != null) {
               var8 = var7;
               if(var3.startsWith("class:")) {
                  var8 = null;
               }
            }

            return var8;
         }
      }

      return null;
   }

   public void noteAccess(Object var1, ScopeExp var2) {
      if(this.notedAccess == null) {
         this.notedAccess = new Vector();
      }

      this.notedAccess.addElement(var1);
      this.notedAccess.addElement(var2);
   }

   public Expression parse(Object var1) {
      return this.rewrite(var1);
   }

   public Object popForms(int var1) {
      int var4 = this.formStack.size();
      if(var4 == var1) {
         return Values.empty;
      } else {
         Object var2;
         if(var4 == var1 + 1) {
            var2 = this.formStack.elementAt(var1);
         } else {
            var2 = new Values();

            for(int var3 = var1; var3 < var4; ++var3) {
               ((Values)var2).writeObject(this.formStack.elementAt(var3));
            }
         }

         this.formStack.setSize(var1);
         return var2;
      }
   }

   public void popPositionOf(Object var1) {
      if(var1 != null) {
         this.setLine(var1);
         this.positionPair = (PairWithPosition)var1;
         if(this.positionPair.getCar() == Special.eof) {
            this.positionPair = (PairWithPosition)this.positionPair.getCdr();
            return;
         }
      }

   }

   public void popRenamedAlias(int var1) {
      while(true) {
         int var4 = var1 - 1;
         if(var4 < 0) {
            return;
         }

         ScopeExp var2 = (ScopeExp)this.renamedAliasStack.pop();
         Declaration var3 = (Declaration)this.renamedAliasStack.pop();
         getOriginalRef(var3).getBinding().setSymbol(var3.getSymbol());
         var2.remove(var3);
         Object var5 = this.renamedAliasStack.pop();
         var1 = var4;
         if(var5 != null) {
            var2.addDeclaration((Declaration)((Declaration)var5));
            var1 = var4;
         }
      }
   }

   public void processAccesses() {
      if(this.notedAccess != null) {
         int var5 = this.notedAccess.size();
         ScopeExp var1 = this.current_scope;

         for(int var4 = 0; var4 < var5; var4 += 2) {
            Object var2 = this.notedAccess.elementAt(var4);
            ScopeExp var3 = (ScopeExp)this.notedAccess.elementAt(var4 + 1);
            if(this.current_scope != var3) {
               this.setCurrentScope(var3);
            }

            Declaration var6 = this.lexical.lookup(var2, -1);
            if(var6 != null && !var6.getFlag(65536L)) {
               var6.getContext().currentLambda().capture(var6);
               var6.setCanRead(true);
               var6.setSimple(false);
               var6.setFlag(524288L);
            }
         }

         if(this.current_scope != var1) {
            this.setCurrentScope(var1);
            return;
         }
      }

   }

   public Object pushPositionOf(Object var1) {
      Object var2 = var1;
      if(var1 instanceof SyntaxForm) {
         var2 = ((SyntaxForm)var1).getDatum();
      }

      if(!(var2 instanceof PairWithPosition)) {
         return null;
      } else {
         PairWithPosition var3 = (PairWithPosition)var2;
         PairWithPosition var4;
         if(this.positionPair != null && this.positionPair.getFileName() == this.getFileName() && this.positionPair.getLineNumber() == this.getLineNumber() && this.positionPair.getColumnNumber() == this.getColumnNumber()) {
            var4 = this.positionPair;
         } else {
            var4 = new PairWithPosition(this, Special.eof, this.positionPair);
         }

         this.setLine(var2);
         this.positionPair = var3;
         return var4;
      }
   }

   public void pushRenamedAlias(Declaration var1) {
      Declaration var3 = getOriginalRef(var1).getBinding();
      ScopeExp var2 = var1.context;
      var3.setSymbol((Object)null);
      var3 = var2.lookup(var3.getSymbol());
      if(var3 != null) {
         var2.remove(var3);
      }

      var2.addDeclaration((Declaration)var1);
      if(this.renamedAliasStack == null) {
         this.renamedAliasStack = new Stack();
      }

      this.renamedAliasStack.push(var3);
      this.renamedAliasStack.push(var1);
      this.renamedAliasStack.push(var2);
   }

   public void resolveModule(ModuleExp var1) {
      int var7;
      if(this.pendingImports == null) {
         var7 = 0;
      } else {
         var7 = this.pendingImports.size();
      }

      int var8 = 0;

      while(var8 < var7) {
         Stack var2 = this.pendingImports;
         int var9 = var8 + 1;
         ModuleInfo var14 = (ModuleInfo)var2.elementAt(var8);
         Stack var3 = this.pendingImports;
         var8 = var9 + 1;
         ScopeExp var16 = (ScopeExp)var3.elementAt(var9);
         Stack var4 = this.pendingImports;
         int var10 = var8 + 1;
         Expression var17 = (Expression)var4.elementAt(var8);
         Stack var5 = this.pendingImports;
         var9 = var10 + 1;
         Integer var18 = (Integer)var5.elementAt(var10);
         var8 = var9;
         if(var1 == var16) {
            ReferenceExp var6 = new ReferenceExp((Object)null);
            var6.setLine((Compilation)this);
            this.setLine(var17);
            var8 = this.formStack.size();
            require.importDefinitions((String)null, var14, (Procedure)null, this.formStack, var16, this);
            var10 = var18.intValue();
            if(var18.intValue() != var8) {
               int var11 = this.formStack.size();
               vectorReverse(this.formStack, var10, var8 - var10);
               vectorReverse(this.formStack, var8, var11 - var8);
               vectorReverse(this.formStack, var10, var11 - var10);
            }

            this.setLine(var6);
            var8 = var9;
         }
      }

      this.pendingImports = null;
      this.processAccesses();
      this.setModule(var1);
      Compilation var15 = Compilation.setSaveCurrent(this);

      try {
         this.rewriteInBody(this.popForms(this.firstForm));
         var1.body = this.makeBody(this.firstForm, var1);
         if(!this.immediate) {
            this.lexical.pop((ScopeExp)var1);
         }
      } finally {
         Compilation.restoreCurrent(var15);
      }

   }

   public Expression rewrite(Object var1) {
      return this.rewrite(var1, false);
   }

   public Expression rewrite(Object param1, boolean param2) {
      // $FF: Couldn't be decompiled
   }

   public void rewriteInBody(Object var1) {
      if(var1 instanceof SyntaxForm) {
         SyntaxForm var2 = (SyntaxForm)var1;
         ScopeExp var6 = this.current_scope;

         try {
            this.setCurrentScope(var2.getScope());
            this.rewriteInBody(var2.getDatum());
         } finally {
            this.setCurrentScope(var6);
         }
      } else {
         if(!(var1 instanceof Values)) {
            this.formStack.add(this.rewrite(var1, false));
            return;
         }

         Object[] var7 = ((Values)var1).getValues();

         for(int var3 = 0; var3 < var7.length; ++var3) {
            this.rewriteInBody(var7[var3]);
         }
      }

   }

   public Expression rewrite_body(Object param1) {
      // $FF: Couldn't be decompiled
   }

   public final Expression rewrite_car(Pair var1, SyntaxForm var2) {
      if(var2 != null && var2.getScope() != this.current_scope && !(var1.getCar() instanceof SyntaxForm)) {
         ScopeExp var3 = this.current_scope;

         Expression var6;
         try {
            this.setCurrentScope(var2.getScope());
            var6 = this.rewrite_car(var1, false);
         } finally {
            this.setCurrentScope(var3);
         }

         return var6;
      } else {
         return this.rewrite_car(var1, false);
      }
   }

   public final Expression rewrite_car(Pair var1, boolean var2) {
      Object var3 = var1.getCar();
      return var1 instanceof PairWithPosition?this.rewrite_with_position(var3, var2, (PairWithPosition)var1):this.rewrite(var3, var2);
   }

   public Expression rewrite_pair(Pair var1, boolean var2) {
      Expression var5 = this.rewrite_car(var1, true);
      Object var3;
      if(var5 instanceof QuoteExp) {
         var3 = var5.valueIfConstant();
         if(var3 instanceof Syntax) {
            return this.apply_rewrite((Syntax)var3, var1);
         }
      }

      Expression var15;
      if(var5 instanceof ReferenceExp) {
         ReferenceExp var6 = (ReferenceExp)var5;
         Declaration var4 = var6.getBinding();
         if(var4 == null) {
            var3 = var6.getSymbol();
            Symbol var17;
            if(var3 instanceof Symbol && !this.selfEvaluatingSymbol(var3)) {
               var17 = (Symbol)var3;
               var17.getName();
            } else {
               String var16 = var3.toString();
               var17 = this.env.getSymbol(var16);
            }

            Environment var7 = this.env;
            Object var18;
            if(this.getLanguage().hasSeparateFunctionNamespace()) {
               var18 = EnvironmentKey.FUNCTION;
            } else {
               var18 = null;
            }

            var3 = var7.get(var17, var18, (Object)null);
            if(var3 instanceof Syntax) {
               return this.apply_rewrite((Syntax)var3, var1);
            }

            if(var3 instanceof AutoloadProcedure) {
               try {
                  ((AutoloadProcedure)var3).getLoaded();
               } catch (RuntimeException var12) {
                  ;
               }
            }
         } else {
            Declaration var20 = this.macroContext;
            Syntax var21 = this.check_if_Syntax(var4);
            if(var21 != null) {
               var15 = this.apply_rewrite(var21, var1);
               this.macroContext = var20;
               return var15;
            }
         }

         var6.setProcedureName(true);
         if(this.getLanguage().hasSeparateFunctionNamespace()) {
            var5.setFlag(8);
         }
      }

      var3 = var1.getCdr();
      int var9 = listLength(var3);
      if(var9 == -1) {
         return this.syntaxError("circular list is not allowed after " + var1.getCar());
      } else if(var9 < 0) {
         return this.syntaxError("dotted list [" + var3 + "] is not allowed after " + var1.getCar());
      } else {
         boolean var10 = false;
         Stack var23 = new Stack();
         ScopeExp var24 = this.current_scope;
         int var8 = 0;

         boolean var11;
         Expression var26;
         for(Object var13 = var3; var8 < var9; var10 = var11) {
            var3 = var13;
            if(var13 instanceof SyntaxForm) {
               SyntaxForm var14 = (SyntaxForm)var13;
               var3 = var14.getDatum();
               this.setCurrentScope(var14.getScope());
            }

            Pair var25 = (Pair)var3;
            var26 = this.rewrite_car(var25, false);
            ++var8;
            var13 = var26;
            var11 = var10;
            if(var10) {
               if((var8 & 1) == 0) {
                  var15 = (Expression)var23.pop();
                  var13 = new ApplyExp(MakeAttribute.makeAttribute, new Expression[]{var15, var26});
                  var11 = var10;
               } else {
                  label105: {
                     if(var26 instanceof QuoteExp) {
                        var13 = ((QuoteExp)var26).getValue();
                        if(var13 instanceof Keyword && var8 < var9) {
                           var13 = new QuoteExp(((Keyword)var13).asSymbol());
                           var11 = var10;
                           break label105;
                        }
                     }

                     var11 = false;
                     var13 = var26;
                  }
               }
            }

            var23.addElement(var13);
            var13 = var25.getCdr();
         }

         Expression[] var19 = new Expression[var23.size()];
         var23.copyInto(var19);
         if(var24 != this.current_scope) {
            this.setCurrentScope(var24);
         }

         if(var5 instanceof ReferenceExp && ((ReferenceExp)var5).getBinding() == getNamedPartDecl) {
            var26 = var19[0];
            var15 = var19[1];
            Symbol var22 = this.namespaceResolve((Expression)var26, var15);
            if(var22 != null) {
               return this.rewrite(var22, var2);
            } else {
               return CompileNamedPart.makeExp((Expression)var26, (Expression)var15);
            }
         } else {
            return ((LispLanguage)this.getLanguage()).makeApply(var5, var19);
         }
      }
   }

   public Expression rewrite_with_position(Object param1, boolean param2, PairWithPosition param3) {
      // $FF: Couldn't be decompiled
   }

   public LList scanBody(Object param1, ScopeExp param2, boolean param3) {
      // $FF: Couldn't be decompiled
   }

   public void scanForm(Object param1, ScopeExp param2) {
      // $FF: Couldn't be decompiled
   }

   public final boolean selfEvaluatingSymbol(Object var1) {
      return ((LispLanguage)this.getLanguage()).selfEvaluatingSymbol(var1);
   }

   public void setLineOf(Expression var1) {
      if(!(var1 instanceof QuoteExp)) {
         var1.setLocation(this);
      }
   }
}
